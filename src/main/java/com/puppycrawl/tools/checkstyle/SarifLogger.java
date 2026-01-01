///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.meta.ModuleDetails;
import com.puppycrawl.tools.checkstyle.meta.XmlMetaReader;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Simple SARIF logger.
 * SARIF stands for the static analysis results interchange format.
 * See <a href="https://sarifweb.azurewebsites.net/">reference</a>
 */
public final class SarifLogger extends AbstractAutomaticBean implements AuditListener {

    /** The length of unicode placeholder. */
    private static final int UNICODE_LENGTH = 4;

    /** Unicode escaping upper limit. */
    private static final int UNICODE_ESCAPE_UPPER_LIMIT = 0x1F;

    /** Input stream buffer size. */
    private static final int BUFFER_SIZE = 1024;

    /** The placeholder for message. */
    private static final String MESSAGE_PLACEHOLDER = "${message}";

    /** The placeholder for message text. */
    private static final String MESSAGE_TEXT_PLACEHOLDER = "${messageText}";

    /** The placeholder for message id. */
    private static final String MESSAGE_ID_PLACEHOLDER = "${messageId}";

    /** The placeholder for severity level. */
    private static final String SEVERITY_LEVEL_PLACEHOLDER = "${severityLevel}";

    /** The placeholder for uri. */
    private static final String URI_PLACEHOLDER = "${uri}";

    /** The placeholder for line. */
    private static final String LINE_PLACEHOLDER = "${line}";

    /** The placeholder for column. */
    private static final String COLUMN_PLACEHOLDER = "${column}";

    /** The placeholder for rule id. */
    private static final String RULE_ID_PLACEHOLDER = "${ruleId}";

    /** The placeholder for version. */
    private static final String VERSION_PLACEHOLDER = "${version}";

    /** The placeholder for results. */
    private static final String RESULTS_PLACEHOLDER = "${results}";

    /** The placeholder for rules. */
    private static final String RULES_PLACEHOLDER = "${rules}";

    /** Two backslashes to not duplicate strings. */
    private static final String TWO_BACKSLASHES = "\\\\";

    /** A pattern for two backslashes. */
    private static final Pattern A_SPACE_PATTERN = Pattern.compile(" ");

    /** A pattern for two backslashes. */
    private static final Pattern TWO_BACKSLASHES_PATTERN = Pattern.compile(TWO_BACKSLASHES);

    /** A pattern to match a file with a Windows drive letter. */
    private static final Pattern WINDOWS_DRIVE_LETTER_PATTERN =
            Pattern.compile("\\A[A-Z]:", Pattern.CASE_INSENSITIVE);

    /** Comma and line separator. */
    private static final String COMMA_LINE_SEPARATOR = ",\n";

    /** Helper writer that allows easy encoding and printing. */
    private final PrintWriter writer;

    /** Close output stream in auditFinished. */
    private final boolean closeStream;

    /** The results. */
    private final List<String> results = new ArrayList<>();

    /** Map of all available module metadata by fully qualified name. */
    private final Map<String, ModuleDetails> allModuleMetadata = new HashMap<>();

    /** Map to store rule metadata by composite key (sourceName, moduleId). */
    private final Map<RuleKey, ModuleDetails> ruleMetadata = new LinkedHashMap<>();

    /** Content for the entire report. */
    private final String report;

    /** Content for result representing an error with source line and column. */
    private final String resultLineColumn;

    /** Content for result representing an error with source line only. */
    private final String resultLineOnly;

    /** Content for result representing an error with filename only and without source location. */
    private final String resultFileOnly;

    /** Content for result representing an error without filename or location. */
    private final String resultErrorOnly;

    /** Content for rule. */
    private final String rule;

    /** Content for messageStrings. */
    private final String messageStrings;

    /** Content for message with text only. */
    private final String messageTextOnly;

    /** Content for message with id. */
    private final String messageWithId;

    /**
     * Creates a new {@code SarifLogger} instance.
     *
     * @param outputStream where to log audit events
     * @param outputStreamOptions if {@code CLOSE} that should be closed in auditFinished()
     * @throws IllegalArgumentException if outputStreamOptions is null
     * @throws IOException if there is reading errors.
     * @noinspection deprecation
     * @noinspectionreason We are forced to keep AutomaticBean compatability
     *     because of maven-checkstyle-plugin. Until #12873.
     */
    public SarifLogger(
        OutputStream outputStream,
        AutomaticBean.OutputStreamOptions outputStreamOptions) throws IOException {
        this(outputStream, OutputStreamOptions.valueOf(outputStreamOptions.name()));
    }

    /**
     * Creates a new {@code SarifLogger} instance.
     *
     * @param outputStream where to log audit events
     * @param outputStreamOptions if {@code CLOSE} that should be closed in auditFinished()
     * @throws IllegalArgumentException if outputStreamOptions is null
     * @throws IOException if there is reading errors.
     */
    public SarifLogger(
        OutputStream outputStream,
        OutputStreamOptions outputStreamOptions) throws IOException {
        if (outputStreamOptions == null) {
            throw new IllegalArgumentException("Parameter outputStreamOptions can not be null");
        }
        writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        closeStream = outputStreamOptions == OutputStreamOptions.CLOSE;
        loadModuleMetadata();
        report = readResource("/com/puppycrawl/tools/checkstyle/sarif/SarifReport.template");
        resultLineColumn =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/ResultLineColumn.template");
        resultLineOnly =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/ResultLineOnly.template");
        resultFileOnly =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/ResultFileOnly.template");
        resultErrorOnly =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/ResultErrorOnly.template");
        rule = readResource("/com/puppycrawl/tools/checkstyle/sarif/Rule.template");
        messageStrings =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/MessageStrings.template");
        messageTextOnly =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/MessageTextOnly.template");
        messageWithId =
            readResource("/com/puppycrawl/tools/checkstyle/sarif/MessageWithId.template");
    }

    /**
     * Loads all available module metadata from XML files.
     */
    private void loadModuleMetadata() {
        final List<ModuleDetails> allModules =
                XmlMetaReader.readAllModulesIncludingThirdPartyIfAny();
        for (ModuleDetails module : allModules) {
            allModuleMetadata.put(module.getFullQualifiedName(), module);
        }
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public void auditStarted(AuditEvent event) {
        // No code by default
    }

    @Override
    public void auditFinished(AuditEvent event) {
        String rendered = replaceVersionString(report);
        rendered = rendered
                .replace(RESULTS_PLACEHOLDER, String.join(COMMA_LINE_SEPARATOR, results))
                .replace(RULES_PLACEHOLDER, String.join(COMMA_LINE_SEPARATOR, generateRules()));
        writer.print(rendered);
        if (closeStream) {
            writer.close();
        }
        else {
            writer.flush();
        }
    }

    /**
     * Generates rules from cached rule metadata.
     *
     * @return list of rules
     */
    private List<String> generateRules() {
        final List<String> result = new ArrayList<>();
        for (Map.Entry<RuleKey, ModuleDetails> entry : ruleMetadata.entrySet()) {
            final RuleKey ruleKey = entry.getKey();
            final ModuleDetails module = entry.getValue();
            final String shortDescription;
            final String fullDescription;
            final String messageStringsFragment;
            if (module == null) {
                shortDescription = CommonUtil.baseClassName(ruleKey.sourceName());
                fullDescription = "No description available";
                messageStringsFragment = "";
            }
            else {
                shortDescription = module.getName();
                fullDescription = module.getDescription();
                messageStringsFragment = String.join(COMMA_LINE_SEPARATOR,
                        generateMessageStrings(module));
            }
            result.add(rule
                    .replace(RULE_ID_PLACEHOLDER, ruleKey.toRuleId())
                    .replace("${shortDescription}", shortDescription)
                    .replace("${fullDescription}", escape(fullDescription))
                    .replace("${messageStrings}", messageStringsFragment));
        }
        return result;
    }

    /**
     * Generates message strings for a given module.
     *
     * @param module the module
     * @return the generated message strings
     */
    private List<String> generateMessageStrings(ModuleDetails module) {
        final Map<String, String> messages = getMessages(module);
        return module.getViolationMessageKeys().stream()
                .filter(messages::containsKey).map(key -> {
                    final String message = messages.get(key);
                    return messageStrings
                            .replace("${key}", key)
                            .replace("${text}", escape(message));
                }).toList();
    }

    /**
     * Gets a map of message keys to their message strings for a module.
     *
     * @param moduleDetails the module details
     * @return map of message keys to message strings
     */
    private static Map<String, String> getMessages(ModuleDetails moduleDetails) {
        final String fullQualifiedName = moduleDetails.getFullQualifiedName();
        final Map<String, String> result = new LinkedHashMap<>();
        try {
            final int lastDot = fullQualifiedName.lastIndexOf('.');
            final String packageName = fullQualifiedName.substring(0, lastDot);
            final String bundleName = packageName + ".messages";
            final Class<?> moduleClass = Class.forName(fullQualifiedName);
            final ResourceBundle bundle = ResourceBundle.getBundle(
                    bundleName,
                    Locale.ROOT,
                    moduleClass.getClassLoader(),
                    new LocalizedMessage.Utf8Control()
            );
            for (String key : moduleDetails.getViolationMessageKeys()) {
                result.put(key, bundle.getString(key));
            }
        }
        catch (ClassNotFoundException | MissingResourceException ignored) {
            // Return empty map when module class or resource bundle is not on classpath.
            // Occurs with third-party modules that have XML metadata but missing implementation.
        }
        return result;
    }

    /**
     * Returns the version string.
     *
     * @param report report content where replace should happen
     * @return a version string based on the package implementation version
     */
    private static String replaceVersionString(String report) {
        return report.replace(VERSION_PLACEHOLDER,
                SarifLogger.class.getPackage().getImplementationVersion());
    }

    @Override
    public void addError(AuditEvent event) {
        final RuleKey ruleKey = cacheRuleMetadata(event);
        final String message = generateMessage(ruleKey, event);
        if (event.getColumn() > 0) {
            results.add(resultLineColumn
                .replace(SEVERITY_LEVEL_PLACEHOLDER, renderSeverityLevel(event.getSeverityLevel()))
                .replace(URI_PLACEHOLDER, renderFileNameUri(event.getFileName()))
                .replace(COLUMN_PLACEHOLDER, Integer.toString(event.getColumn()))
                .replace(LINE_PLACEHOLDER, Integer.toString(event.getLine()))
                .replace(MESSAGE_PLACEHOLDER, message)
                .replace(RULE_ID_PLACEHOLDER, ruleKey.toRuleId())
            );
        }
        else {
            results.add(resultLineOnly
                .replace(SEVERITY_LEVEL_PLACEHOLDER, renderSeverityLevel(event.getSeverityLevel()))
                .replace(URI_PLACEHOLDER, renderFileNameUri(event.getFileName()))
                .replace(LINE_PLACEHOLDER, Integer.toString(event.getLine()))
                .replace(MESSAGE_PLACEHOLDER, message)
                .replace(RULE_ID_PLACEHOLDER, ruleKey.toRuleId())
            );
        }
    }

    /**
     * Caches rule metadata for a given audit event.
     *
     * @param event the audit event
     * @return the composite key for the rule
     */
    private RuleKey cacheRuleMetadata(AuditEvent event) {
        final String sourceName = event.getSourceName();
        final RuleKey key = new RuleKey(sourceName, event.getModuleId());
        final ModuleDetails module = allModuleMetadata.get(sourceName);
        ruleMetadata.putIfAbsent(key, module);
        return key;
    }

    /**
     * Generate message for the given rule key and audit event.
     *
     * @param ruleKey the rule key
     * @param event the audit event
     * @return the generated message
     */
    private String generateMessage(RuleKey ruleKey, AuditEvent event) {
        final String violationKey = event.getViolation().getKey();
        final ModuleDetails module = ruleMetadata.get(ruleKey);
        final String result;
        if (module != null && module.getViolationMessageKeys().contains(violationKey)) {
            result = messageWithId
                    .replace(MESSAGE_ID_PLACEHOLDER, violationKey)
                    .replace(MESSAGE_TEXT_PLACEHOLDER, escape(event.getMessage()));
        }
        else {
            result = messageTextOnly
                    .replace(MESSAGE_TEXT_PLACEHOLDER, escape(event.getMessage()));
        }
        return result;
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printer = new PrintWriter(stringWriter);
        throwable.printStackTrace(printer);
        final String message = messageTextOnly
                .replace(MESSAGE_TEXT_PLACEHOLDER, escape(stringWriter.toString()));
        if (event.getFileName() == null) {
            results.add(resultErrorOnly
                .replace(SEVERITY_LEVEL_PLACEHOLDER, renderSeverityLevel(event.getSeverityLevel()))
                .replace(MESSAGE_PLACEHOLDER, message)
            );
        }
        else {
            results.add(resultFileOnly
                .replace(SEVERITY_LEVEL_PLACEHOLDER, renderSeverityLevel(event.getSeverityLevel()))
                .replace(URI_PLACEHOLDER, renderFileNameUri(event.getFileName()))
                .replace(MESSAGE_PLACEHOLDER, message)
            );
        }
    }

    @Override
    public void fileStarted(AuditEvent event) {
        // No need to implement this method in this class
    }

    @Override
    public void fileFinished(AuditEvent event) {
        // No need to implement this method in this class
    }

    /**
     * Render the file name URI for the given file name.
     *
     * @param fileName the file name to render the URI for
     * @return the rendered URI for the given file name
     */
    private static String renderFileNameUri(final String fileName) {
        String normalized =
                A_SPACE_PATTERN
                        .matcher(TWO_BACKSLASHES_PATTERN.matcher(fileName).replaceAll("/"))
                        .replaceAll("%20");
        if (WINDOWS_DRIVE_LETTER_PATTERN.matcher(normalized).find()) {
            normalized = '/' + normalized;
        }
        return "file:" + normalized;
    }

    /**
     * Render the severity level into SARIF severity level.
     *
     * @param severityLevel the Severity level.
     * @return the rendered severity level in string.
     */
    private static String renderSeverityLevel(SeverityLevel severityLevel) {
        return switch (severityLevel) {
            case IGNORE -> "none";
            case INFO -> "note";
            case WARNING -> "warning";
            case ERROR -> "error";
        };
    }

    /**
     * Escape \b, \f, \n, \r, \t, \", \\ and U+0000 through U+001F.
     * See <a href="https://www.ietf.org/rfc/rfc4627.txt">reference</a> - 2.5. Strings
     *
     * @param value the value to escape.
     * @return the escaped value if necessary.
     */
    public static String escape(String value) {
        final int length = value.length();
        final StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            final char chr = value.charAt(i);
            final String replacement = switch (chr) {
                case '"' -> "\\\"";
                case '\\' -> TWO_BACKSLASHES;
                case '\b' -> "\\b";
                case '\f' -> "\\f";
                case '\n' -> "\\n";
                case '\r' -> "\\r";
                case '\t' -> "\\t";
                case '/' -> "\\/";
                default -> {
                    if (chr <= UNICODE_ESCAPE_UPPER_LIMIT) {
                        yield escapeUnicode1F(chr);
                    }
                    yield Character.toString(chr);
                }
            };
            sb.append(replacement);
        }

        return sb.toString();
    }

    /**
     * Escape the character between 0x00 to 0x1F in JSON.
     *
     * @param chr the character to be escaped.
     * @return the escaped string.
     */
    private static String escapeUnicode1F(char chr) {
        final String hexString = Integer.toHexString(chr);
        return "\\u"
                + "0".repeat(UNICODE_LENGTH - hexString.length())
                + hexString.toUpperCase(Locale.US);
    }

    /**
     * Read string from given resource.
     *
     * @param name name of the desired resource
     * @return the string content from the give resource
     * @throws IOException if there is reading errors
     */
    public static String readResource(String name) throws IOException {
        try (InputStream inputStream = SarifLogger.class.getResourceAsStream(name);
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            if (inputStream == null) {
                throw new IOException("Cannot find the resource " + name);
            }
            final byte[] buffer = new byte[BUFFER_SIZE];
            int length = 0;
            while (length != -1) {
                result.write(buffer, 0, length);
                length = inputStream.read(buffer);
            }
            return result.toString(StandardCharsets.UTF_8);
        }
    }

    /**
     * Composite key for uniquely identifying a rule by source name and module ID.
     *
     * @param sourceName  The fully qualified source class name.
     * @param moduleId  The module ID from configuration (can be null).
     */
    private record RuleKey(String sourceName, String moduleId) {
        /**
         * Converts this key to a SARIF rule ID string.
         *
         * @return rule ID in format: sourceName[#moduleId]
         */
        private String toRuleId() {
            final String result;
            if (moduleId == null) {
                result = sourceName;
            }
            else {
                result = sourceName + '#' + moduleId;
            }
            return result;
        }
    }
}
