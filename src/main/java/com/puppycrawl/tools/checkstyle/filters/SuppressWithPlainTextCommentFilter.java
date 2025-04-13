////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.filters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Filter {@code SuppressWithPlainTextCommentFilter} uses plain text to suppress
 * audit events. The filter can be used only to suppress audit events received
 * from the checks which implement FileSetCheck interface. In other words, the
 * checks which have Checker as a parent module. The filter knows nothing about
 * AST, it treats only plain text comments and extracts the information required
 * for suppression from the plain text comments. Currently, the filter supports
 * only single-line comments.
 * </div>
 *
 * <p>
 * Please, be aware of the fact that, it is not recommended to use the filter
 * for Java code anymore, however you still are able to use it to suppress audit
 * events received from the checks which implement FileSetCheck interface.
 * </p>
 *
 * <p>
 * Rationale: Sometimes there are legitimate reasons for violating a check.
 * When this is a matter of the code in question and not personal preference,
 * the best place to override the policy is in the code itself. Semi-structured
 * comments can be associated with the check. This is sometimes superior to
 * a separate suppressions file, which must be kept up-to-date as the source
 * file is edited.
 * </p>
 *
 * <p>
 * Note that the suppression comment should be put before the violation.
 * You can use more than one suppression comment each on separate line.
 * </p>
 *
 * <p>
 * Properties {@code offCommentFormat} and {@code onCommentFormat} must have equal
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Matcher.html#groupCount()">
 * paren counts</a>.
 * </p>
 *
 * <p>
 * SuppressionWithPlainTextCommentFilter can suppress Checks that have Treewalker or
 * Checker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code checkFormat} - Specify check pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * <li>
 * Property {@code idFormat} - Specify check ID pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code messageFormat} - Specify message pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code offCommentFormat} - Specify comment pattern to trigger filter
 * to begin suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "// CHECKSTYLE:OFF"}.
 * </li>
 * <li>
 * Property {@code onCommentFormat} - Specify comment pattern to trigger filter
 * to end suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "// CHECKSTYLE:ON"}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 8.6
 */
public class SuppressWithPlainTextCommentFilter extends AbstractAutomaticBean implements Filter {

    /** Comment format which turns checkstyle reporting off. */
    private static final String DEFAULT_OFF_FORMAT = "// CHECKSTYLE:OFF";

    /** Comment format which turns checkstyle reporting on. */
    private static final String DEFAULT_ON_FORMAT = "// CHECKSTYLE:ON";

    /** Default check format to suppress. By default, the filter suppress all checks. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Specify comment pattern to trigger filter to begin suppression. */
    private Pattern offCommentFormat = CommonUtil.createPattern(DEFAULT_OFF_FORMAT);

    /** Specify comment pattern to trigger filter to end suppression. */
    private Pattern onCommentFormat = CommonUtil.createPattern(DEFAULT_ON_FORMAT);

    /** Specify check pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** Specify message pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String messageFormat;

    /** Specify check ID pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String idFormat;

    /**
     * Setter to specify comment pattern to trigger filter to begin suppression.
     *
     * @param pattern off comment format pattern.
     * @since 8.6
     */
    public final void setOffCommentFormat(Pattern pattern) {
        offCommentFormat = pattern;
    }

    /**
     * Setter to specify comment pattern to trigger filter to end suppression.
     *
     * @param pattern  on comment format pattern.
     * @since 8.6
     */
    public final void setOnCommentFormat(Pattern pattern) {
        onCommentFormat = pattern;
    }

    /**
     * Setter to specify check pattern to suppress.
     *
     * @param format pattern for check format.
     * @since 8.6
     */
    public final void setCheckFormat(String format) {
        checkFormat = format;
    }

    /**
     * Setter to specify message pattern to suppress.
     *
     * @param format pattern for message format.
     * @since 8.6
     */
    public final void setMessageFormat(String format) {
        messageFormat = format;
    }

    /**
     * Setter to specify check ID pattern to suppress.
     *
     * @param format pattern for check ID format
     * @since 8.24
     */
    public final void setIdFormat(String format) {
        idFormat = format;
    }

    @Override
    public boolean accept(AuditEvent event) {
        boolean accepted = true;
        if (event.getViolation() != null) {
            final FileText fileText = getFileText(event.getFileName());
            if (fileText != null) {
                final List<Suppression> suppressions = getSuppressions(fileText);
                accepted = getNearestSuppression(suppressions, event) == null;
            }
        }
        return accepted;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    /**
     * Returns {@link FileText} instance created based on the given file name.
     *
     * @param fileName the name of the file.
     * @return {@link FileText} instance.
     * @throws IllegalStateException if the file could not be read.
     */
    private static FileText getFileText(String fileName) {
        final File file = new File(fileName);
        FileText result = null;

        // some violations can be on a directory, instead of a file
        if (!file.isDirectory()) {
            try {
                result = new FileText(file, StandardCharsets.UTF_8.name());
            }
            catch (IOException ex) {
                throw new IllegalStateException("Cannot read source file: " + fileName, ex);
            }
        }

        return result;
    }

    /**
     * Returns the list of {@link Suppression} instances retrieved from the given {@link FileText}.
     *
     * @param fileText {@link FileText} instance.
     * @return list of {@link Suppression} instances.
     */
    private List<Suppression> getSuppressions(FileText fileText) {
        final List<Suppression> suppressions = new ArrayList<>();
        for (int lineNo = 0; lineNo < fileText.size(); lineNo++) {
            final Optional<Suppression> suppression = getSuppression(fileText, lineNo);
            suppression.ifPresent(suppressions::add);
        }
        return suppressions;
    }

    /**
     * Tries to extract the suppression from the given line.
     *
     * @param fileText {@link FileText} instance.
     * @param lineNo line number.
     * @return {@link Optional} of {@link Suppression}.
     */
    private Optional<Suppression> getSuppression(FileText fileText, int lineNo) {
        final String line = fileText.get(lineNo);
        final Matcher onCommentMatcher = onCommentFormat.matcher(line);
        final Matcher offCommentMatcher = offCommentFormat.matcher(line);

        Suppression suppression = null;
        if (onCommentMatcher.find()) {
            suppression = new Suppression(onCommentMatcher.group(0),
                lineNo + 1, SuppressionType.ON, this);
        }
        if (offCommentMatcher.find()) {
            suppression = new Suppression(offCommentMatcher.group(0),
                lineNo + 1, SuppressionType.OFF, this);
        }

        return Optional.ofNullable(suppression);
    }

    /**
     * Finds the nearest {@link Suppression} instance which can suppress
     * the given {@link AuditEvent}. The nearest suppression is the suppression which scope
     * is before the line and column of the event.
     *
     * @param suppressions collection of {@link Suppression} instances.
     * @param event {@link AuditEvent} instance.
     * @return {@link Suppression} instance.
     */
    private static Suppression getNearestSuppression(Collection<Suppression> suppressions,
                                                     AuditEvent event) {
        return suppressions
            .stream()
            .filter(suppression -> suppression.isMatch(event))
            .reduce((first, second) -> second)
            .filter(suppression -> suppression.suppressionType != SuppressionType.ON)
            .orElse(null);
    }

    /** Enum which represents the type of the suppression. */
    private enum SuppressionType {

        /** On suppression type. */
        ON,
        /** Off suppression type. */
        OFF,

    }

    /** The class which represents the suppression. */
    private static final class Suppression {

        /** The regexp which is used to match the event source.*/
        private final Pattern eventSourceRegexp;
        /** The regexp which is used to match the event message.*/
        private final Pattern eventMessageRegexp;
        /** The regexp which is used to match the event ID.*/
        private final Pattern eventIdRegexp;

        /** Suppression line.*/
        private final int lineNo;

        /** Suppression type. */
        private final SuppressionType suppressionType;

        /**
         * Creates new suppression instance.
         *
         * @param text suppression text.
         * @param lineNo suppression line number.
         * @param suppressionType suppression type.
         * @param filter the {@link SuppressWithPlainTextCommentFilter} with the context.
         * @throws IllegalArgumentException if there is an error in the filter regex syntax.
         */
        private Suppression(
            String text,
            int lineNo,
            SuppressionType suppressionType,
            SuppressWithPlainTextCommentFilter filter
        ) {
            this.lineNo = lineNo;
            this.suppressionType = suppressionType;

            final Pattern commentFormat;
            if (this.suppressionType == SuppressionType.ON) {
                commentFormat = filter.onCommentFormat;
            }
            else {
                commentFormat = filter.offCommentFormat;
            }

            // Expand regexp for check and message
            // Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                    filter.checkFormat, text, commentFormat);
                eventSourceRegexp = Pattern.compile(format);
                if (filter.messageFormat == null) {
                    eventMessageRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.messageFormat, text, commentFormat);
                    eventMessageRegexp = Pattern.compile(format);
                }
                if (filter.idFormat == null) {
                    eventIdRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.idFormat, text, commentFormat);
                    eventIdRegexp = Pattern.compile(format);
                }
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException(
                    "unable to parse expanded comment " + format, ex);
            }
        }

        /**
         * Indicates whether some other object is "equal to" this one.
         *
         * @noinspection EqualsCalledOnEnumConstant
         * @noinspectionreason EqualsCalledOnEnumConstant - enumeration is needed to keep
         *      code consistent
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Suppression suppression = (Suppression) other;
            return Objects.equals(lineNo, suppression.lineNo)
                && Objects.equals(suppressionType, suppression.suppressionType)
                && Objects.equals(eventSourceRegexp, suppression.eventSourceRegexp)
                && Objects.equals(eventMessageRegexp, suppression.eventMessageRegexp)
                && Objects.equals(eventIdRegexp, suppression.eventIdRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                lineNo, suppressionType, eventSourceRegexp, eventMessageRegexp,
                eventIdRegexp);
        }

        /**
         * Checks whether the suppression matches the given {@link AuditEvent}.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the suppression matches {@link AuditEvent}.
         */
        private boolean isMatch(AuditEvent event) {
            return isInScopeOfSuppression(event)
                && isCheckMatch(event)
                && isIdMatch(event)
                && isMessageMatch(event);
        }

        /**
         * Checks whether {@link AuditEvent} is in the scope of the suppression.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if {@link AuditEvent} is in the scope of the suppression.
         */
        private boolean isInScopeOfSuppression(AuditEvent event) {
            return lineNo <= event.getLine();
        }

        /**
         * Checks whether {@link AuditEvent} source name matches the check format.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} source name matches the check format.
         */
        private boolean isCheckMatch(AuditEvent event) {
            final Matcher checkMatcher = eventSourceRegexp.matcher(event.getSourceName());
            return checkMatcher.find();
        }

        /**
         * Checks whether the {@link AuditEvent} module ID matches the ID format.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} module ID matches the ID format.
         */
        private boolean isIdMatch(AuditEvent event) {
            boolean match = true;
            if (eventIdRegexp != null) {
                if (event.getModuleId() == null) {
                    match = false;
                }
                else {
                    final Matcher idMatcher = eventIdRegexp.matcher(event.getModuleId());
                    match = idMatcher.find();
                }
            }
            return match;
        }

        /**
         * Checks whether the {@link AuditEvent} message matches the message format.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} message matches the message format.
         */
        private boolean isMessageMatch(AuditEvent event) {
            boolean match = true;
            if (eventMessageRegexp != null) {
                final Matcher messageMatcher = eventMessageRegexp.matcher(event.getMessage());
                match = messageMatcher.find();
            }
            return match;
        }
    }

}
