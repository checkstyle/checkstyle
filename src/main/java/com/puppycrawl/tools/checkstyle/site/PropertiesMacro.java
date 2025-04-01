///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.site;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * A macro that inserts a table of properties for the given checkstyle module.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {

    /**
     * Constant value for cases when tokens set is empty.
     */
    public static final String EMPTY = "empty";

    /** Set of properties not inherited from the base token configuration. */
    public static final Set<String> NON_BASE_TOKEN_PROPERTIES = Collections.unmodifiableSet(
            Arrays.stream(new String[] {
                "AtclauseOrder - target",
                "DescendantToken - limitedTokens",
                "IllegalType - memberModifiers",
                "MagicNumber - constantWaiverParentToken",
                "MultipleStringLiterals - ignoreOccurrenceContext",
            }).collect(Collectors.toSet()));

    /** The precompiled pattern for a comma followed by a space. */
    private static final Pattern COMMA_SPACE_PATTERN = Pattern.compile(", ");

    /** The precompiled pattern for a Check. */
    private static final Pattern CHECK_PATTERN = Pattern.compile("Check$");

    /** The string '{}'. */
    private static final String CURLY_BRACKET = "{}";

    /** Represents the relative path to the property types XML. */
    private static final String PROPERTY_TYPES_XML = "property_types.xml";

    /** Represents the format string for constructing URLs with two placeholders. */
    private static final String URL_F = "%s#%s";

    /** Reflects start of a code segment. */
    private static final String CODE_START = "<code>";

    /** Reflects end of a code segment. */
    private static final String CODE_END = "</code>";

    /** A newline with 10 spaces of indentation. */
    private static final String INDENT_LEVEL_10 = SiteUtil.getNewlineAndIndentSpaces(10);
    /** A newline with 12 spaces of indentation. */
    private static final String INDENT_LEVEL_12 = SiteUtil.getNewlineAndIndentSpaces(12);
    /** A newline with 14 spaces of indentation. */
    private static final String INDENT_LEVEL_14 = SiteUtil.getNewlineAndIndentSpaces(14);
    /** A newline with 16 spaces of indentation. */
    private static final String INDENT_LEVEL_16 = SiteUtil.getNewlineAndIndentSpaces(16);
    /** A newline with 18 spaces of indentation. */
    private static final String INDENT_LEVEL_18 = SiteUtil.getNewlineAndIndentSpaces(18);
    /** A newline with 20 spaces of indentation. */
    private static final String INDENT_LEVEL_20 = SiteUtil.getNewlineAndIndentSpaces(20);

    /**
     * This property is used to change the existing properties for javadoc.
     * Tokens always present at the end of all properties.
     */
    private static final String TOKENS_PROPERTY = SiteUtil.TOKENS;

    /** The name of the current module being processed. */
    private static String currentModuleName = "";

    /** The file of the current module being processed. */
    private static File currentModuleFile = Paths.get("").toFile();

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }

        final String modulePath = (String) request.getParameter("modulePath");

        configureGlobalProperties(modulePath);

        writePropertiesTable((XdocSink) sink);
    }

    /**
     * Configures the global properties for the current module.
     *
     * @param modulePath the path of the current module processed.
     */
    private static void configureGlobalProperties(String modulePath) {
        final Path modulePathObj = Paths.get(modulePath);
        currentModuleFile = modulePathObj.toFile();
        currentModuleName = CommonUtil.getFileNameWithoutExtension(modulePathObj
                            .getFileName().toString());
    }

    /**
     * Writes the properties table for the given module. Expects that the module has been processed
     * with the ClassAndPropertiesSettersJavadocScraper before calling this method.
     *
     * @param sink the sink to write to.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertiesTable(XdocSink sink)
            throws MacroExecutionException {
        sink.table();
        sink.setInsertNewline(false);
        sink.tableRows(null, false);
        sink.rawText(INDENT_LEVEL_12);
        writeTableHeaderRow(sink);
        writeTablePropertiesRows(sink);
        sink.rawText(INDENT_LEVEL_10);
        sink.tableRows_();
        sink.table_();
        sink.setInsertNewline(true);
    }

    /**
     * Writes the table header row with 5 columns - name, description, type, default value, since.
     *
     * @param sink sink to write to.
     */
    private static void writeTableHeaderRow(Sink sink) {
        sink.tableRow();
        writeTableHeaderCell(sink, "name");
        writeTableHeaderCell(sink, "description");
        writeTableHeaderCell(sink, "type");
        writeTableHeaderCell(sink, "default value");
        writeTableHeaderCell(sink, "since");
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow_();
    }

    /**
     * Writes a table header cell with the given text.
     *
     * @param sink sink to write to.
     * @param text the text to write.
     */
    private static void writeTableHeaderCell(Sink sink, String text) {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text(text);
        sink.tableHeaderCell_();
    }

    /**
     * Writes the rows of the table with the 5 columns - name, description, type, default value,
     * since. Each row corresponds to a property of the module.
     *
     * @param sink sink to write to.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writeTablePropertiesRows(Sink sink)
            throws MacroExecutionException {
        final Object instance = SiteUtil.getModuleInstance(currentModuleName);
        final Class<?> clss = instance.getClass();

        final Set<String> properties = SiteUtil.getPropertiesForDocumentation(clss, instance);
        final Map<String, DetailNode> propertiesJavadocs = SiteUtil
                .getPropertiesJavadocs(properties, currentModuleName, currentModuleFile);

        final List<String> orderedProperties = orderProperties(properties);

        for (String property : orderedProperties) {
            try {
                final DetailNode propertyJavadoc = propertiesJavadocs.get(property);
                final DetailNode currentModuleJavadoc = propertiesJavadocs.get(currentModuleName);
                writePropertyRow(sink, property, propertyJavadoc, instance, currentModuleJavadoc);
            }
            // -@cs[IllegalCatch] we need to get details in wrapping exception
            catch (Exception exc) {
                final String message = String.format(Locale.ROOT,
                        "Exception while handling moduleName: %s propertyName: %s",
                        currentModuleName, property);
                throw new MacroExecutionException(message, exc);
            }
        }
    }

    /**
     * Reorder properties to always have the 'tokens' property last (if present).
     *
     * @param properties module properties.
     * @return Collection of ordered properties.
     *
     */
    private static List<String> orderProperties(Set<String> properties) {

        final List<String> orderProperties = new LinkedList<>(properties);

        if (orderProperties.remove(TOKENS_PROPERTY)) {
            orderProperties.add(TOKENS_PROPERTY);
        }
        if (orderProperties.remove(SiteUtil.JAVADOC_TOKENS)) {
            orderProperties.add(SiteUtil.JAVADOC_TOKENS);
        }
        return List.copyOf(orderProperties);

    }

    /**
     * Writes a table row with 5 columns for the given property - name, description, type,
     * default value, since.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param propertyJavadoc the Javadoc of the property.
     * @param instance the instance of the module.
     * @param moduleJavadoc the Javadoc of the module.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertyRow(Sink sink, String propertyName,
                                         DetailNode propertyJavadoc, Object instance,
                                            DetailNode moduleJavadoc)
            throws MacroExecutionException {
        final Field field = SiteUtil.getField(instance.getClass(), propertyName);

        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow();

        writePropertyNameCell(sink, propertyName);
        writePropertyDescriptionCell(sink, propertyName, propertyJavadoc);
        writePropertyTypeCell(sink, propertyName, field, instance);
        writePropertyDefaultValueCell(sink, propertyName, field, instance);
        writePropertySinceVersionCell(
                sink, propertyName, moduleJavadoc, propertyJavadoc);

        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow_();
    }

    /**
     * Writes a table cell with the given property name.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     */
    private static void writePropertyNameCell(Sink sink, String propertyName) {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(propertyName);
        sink.tableCell_();
    }

    /**
     * Writes a table cell with the property description.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param propertyJavadoc the Javadoc of the property containing the description.
     * @throws MacroExecutionException if an error occurs during retrieval of the description.
     */
    private static void writePropertyDescriptionCell(Sink sink, String propertyName,
                                                     DetailNode propertyJavadoc)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        final String description = SiteUtil
                .getPropertyDescription(propertyName, propertyJavadoc, currentModuleName);

        sink.rawText(description);
        sink.tableCell_();
    }

    /**
     * Writes a table cell with the property type.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param field the field of the property.
     * @param instance the instance of the module.
     * @throws MacroExecutionException if link to the property_types.html file cannot be
     *                                 constructed.
     */
    private static void writePropertyTypeCell(Sink sink, String propertyName,
                                              Field field, Object instance)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();

        if (SiteUtil.TOKENS.equals(propertyName)) {
            final AbstractCheck check = (AbstractCheck) instance;
            if (check.getRequiredTokens().length == 0
                    && Arrays.equals(check.getAcceptableTokens(), TokenUtil.getAllTokenIds())) {
                sink.text("set of any supported");
                writeLink(sink);
            }
            else {
                final List<String> configurableTokens = SiteUtil
                        .getDifference(check.getAcceptableTokens(),
                                check.getRequiredTokens())
                        .stream()
                        .map(TokenUtil::getTokenName)
                        .collect(Collectors.toUnmodifiableList());
                sink.text("subset of tokens");

                writeTokensList(sink, configurableTokens, SiteUtil.PATH_TO_TOKEN_TYPES, true);
            }
        }
        else if (SiteUtil.JAVADOC_TOKENS.equals(propertyName)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;
            final List<String> configurableTokens = SiteUtil
                    .getDifference(check.getAcceptableJavadocTokens(),
                            check.getRequiredJavadocTokens())
                    .stream()
                    .map(JavadocUtil::getTokenName)
                    .collect(Collectors.toUnmodifiableList());
            sink.text("subset of javadoc tokens");
            writeTokensList(sink, configurableTokens, SiteUtil.PATH_TO_JAVADOC_TOKEN_TYPES, true);
        }
        else {
            final String type = SiteUtil.getType(field, propertyName, currentModuleName, instance);
            if (PropertyType.TOKEN_ARRAY.getDescription().equals(type)) {
                processLinkForTokenTypes(sink);
            }
            else {
                final String relativePathToPropertyTypes =
                        SiteUtil.getLinkToDocument(currentModuleName, PROPERTY_TYPES_XML);
                final String escapedType = type
                        .replace("[", ".5B")
                        .replace("]", ".5D");

                final String url =
                        String.format(Locale.ROOT, URL_F, relativePathToPropertyTypes, escapedType);

                sink.link(url);
                sink.text(type);
                sink.link_();
            }
        }
        sink.tableCell_();
    }

    /**
     * Writes a formatted link for "TokenTypes" to the given sink.
     *
     * @param sink The output target where the link is written.
     * @throws MacroExecutionException If an error occurs during the link processing.
     */
    private static void processLinkForTokenTypes(Sink sink)
            throws MacroExecutionException {
        final String link =
                SiteUtil.getLinkToDocument(currentModuleName, SiteUtil.PATH_TO_TOKEN_TYPES);

        sink.text("subset of tokens ");
        sink.link(link);
        sink.text("TokenTypes");
        sink.link_();
    }

    /**
     * Write a link when all types of token supported.
     *
     * @param sink sink to write to.
     * @throws MacroExecutionException if link cannot be constructed.
     */
    private static void writeLink(Sink sink)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_16);
        final String link =
                SiteUtil.getLinkToDocument(currentModuleName, SiteUtil.PATH_TO_TOKEN_TYPES);
        sink.link(link);
        sink.rawText(INDENT_LEVEL_20);
        sink.text(SiteUtil.TOKENS);
        sink.link_();
        sink.rawText(INDENT_LEVEL_14);
    }

    /**
     * Write a list of tokens with links to the tokenTypesLink file.
     *
     * @param sink sink to write to.
     * @param tokens the list of tokens to write.
     * @param tokenTypesLink the link to the token types file.
     * @param printDotAtTheEnd defines if printing period symbols is required.
     * @throws MacroExecutionException if link to the tokenTypesLink file cannot be constructed.
     */
    private static void writeTokensList(Sink sink, List<String> tokens, String tokenTypesLink,
                                        boolean printDotAtTheEnd)
            throws MacroExecutionException {
        for (int index = 0; index < tokens.size(); index++) {
            final String token = tokens.get(index);
            sink.rawText(INDENT_LEVEL_16);
            if (index != 0) {
                sink.text(SiteUtil.COMMA_SPACE);
            }
            writeLinkToToken(sink, tokenTypesLink, token);
        }
        if (tokens.isEmpty()) {
            sink.rawText(CODE_START);
            sink.text(EMPTY);
            sink.rawText(CODE_END);
        }
        else if (printDotAtTheEnd) {
            sink.rawText(INDENT_LEVEL_18);
            sink.text(SiteUtil.DOT);
            sink.rawText(INDENT_LEVEL_14);
        }
        else {
            sink.rawText(INDENT_LEVEL_14);
        }
    }

    /**
     * Writes a link to the given token.
     *
     * @param sink sink to write to.
     * @param document the document to link to.
     * @param tokenName the name of the token.
     * @throws MacroExecutionException if link to the document file cannot be constructed.
     */
    private static void writeLinkToToken(Sink sink, String document, String tokenName)
            throws MacroExecutionException {
        final String link = SiteUtil.getLinkToDocument(currentModuleName, document)
                        + "#" + tokenName;
        sink.link(link);
        sink.rawText(INDENT_LEVEL_20);
        sink.text(tokenName);
        sink.link_();
    }

    /**
     * Writes a table cell with the property default value.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param field the field of the property.
     * @param instance the instance of the module.
     * @throws MacroExecutionException if an error occurs during retrieval of the default value.
     */
    private static void writePropertyDefaultValueCell(Sink sink, String propertyName,
                                                      Field field, Object instance)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();

        if (SiteUtil.TOKENS.equals(propertyName)) {
            final AbstractCheck check = (AbstractCheck) instance;
            if (check.getRequiredTokens().length == 0
                    && Arrays.equals(check.getDefaultTokens(), TokenUtil.getAllTokenIds())) {
                sink.text(SiteUtil.TOKEN_TYPES);
            }
            else {
                final List<String> configurableTokens = SiteUtil
                        .getDifference(check.getDefaultTokens(),
                                check.getRequiredTokens())
                        .stream()
                        .map(TokenUtil::getTokenName)
                        .collect(Collectors.toUnmodifiableList());
                writeTokensList(sink, configurableTokens, SiteUtil.PATH_TO_TOKEN_TYPES, true);
            }
        }
        else if (SiteUtil.JAVADOC_TOKENS.equals(propertyName)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;
            final List<String> configurableTokens = SiteUtil
                    .getDifference(check.getDefaultJavadocTokens(),
                            check.getRequiredJavadocTokens())
                    .stream()
                    .map(JavadocUtil::getTokenName)
                    .collect(Collectors.toUnmodifiableList());
            writeTokensList(sink, configurableTokens, SiteUtil.PATH_TO_JAVADOC_TOKEN_TYPES, true);
        }
        else {
            final String defaultValue = getDefaultValue(propertyName, field, instance);
            final String checkName = CHECK_PATTERN
                    .matcher(instance.getClass().getSimpleName()).replaceAll("");

            final boolean isSpecialTokenProp = NON_BASE_TOKEN_PROPERTIES.stream()
                    .anyMatch(tokenProp -> tokenProp.equals(checkName + " - " + propertyName));

            if (isSpecialTokenProp && !CURLY_BRACKET.equals(defaultValue)) {
                final List<String> defaultValuesList =
                        Arrays.asList(COMMA_SPACE_PATTERN.split(defaultValue));
                writeTokensList(sink, defaultValuesList, SiteUtil.PATH_TO_TOKEN_TYPES, false);
            }
            else {
                sink.rawText(CODE_START);
                sink.text(defaultValue);
                sink.rawText(CODE_END);
            }
        }

        sink.tableCell_();
    }

    /**
     * Get the default value of the property.
     *
     * @param propertyName the name of the property.
     * @param field the field of the property.
     * @param instance the instance of the module.
     * @return the default value of the property.
     * @throws MacroExecutionException if an error occurs during retrieval of the default value.
     */
    private static String getDefaultValue(String propertyName, Field field, Object instance)
            throws MacroExecutionException {
        final String result;

        if (field != null) {
            result = SiteUtil.getDefaultValue(
                    propertyName, field, instance, currentModuleName);
        }
        else {
            final Class<?> fieldClass = SiteUtil.getPropertyClass(propertyName, instance);

            if (fieldClass.isArray()) {
                result = CURLY_BRACKET;
            }
            else {
                result = "null";
            }
        }
        return result;
    }

    /**
     * Writes a table cell with the property since version.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param moduleJavadoc the Javadoc of the module.
     * @param propertyJavadoc the Javadoc of the property containing the since version.
     * @throws MacroExecutionException if an error occurs during retrieval of the since version.
     */
    private static void writePropertySinceVersionCell(Sink sink, String propertyName,
                                                      DetailNode moduleJavadoc,
                                                      DetailNode propertyJavadoc)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        final String sinceVersion = SiteUtil.getSinceVersion(
                currentModuleName, moduleJavadoc, propertyName, propertyJavadoc);
        sink.text(sinceVersion);
        sink.tableCell_();
    }
}
