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

package com.puppycrawl.tools.checkstyle.site;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * A macro that inserts a table of properties for the given checkstyle module.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {

    /**
     * Constant value for cases when tokens set is empty.
     */
    public static final String EMPTY = "empty";

    /** The string '{}'. */
    private static final String CURLY_BRACKET = "{}";

    /** The string 'subset of tokens'. */
    private static final String SUBSET_OF_TOKENS = "subset of tokens";

    /** Represents the relative path to the property types XML. */
    private static final String PROPERTY_TYPES_XML = "property_types.xml";

    /** The string '#'. */
    private static final String HASHTAG = "#";

    /** Represents the format string for constructing URLs with two placeholders. */
    private static final String URL_F = "%s#%s";

    /** Reflects start of a code segment. */
    private static final String CODE_START = "<code>";

    /** Reflects end of a code segment. */
    private static final String CODE_END = "</code>";

    /**
     * This property is used to change the existing properties for javadoc.
     * Tokens always present at the end of all properties.
     */
    private static final String TOKENS_PROPERTY = SiteUtil.TOKENS;

    /** The name of the current module being processed. */
    private static String currentModuleName = "";

    /** The file of the current module being processed. */
    private static Path currentModulePath = Path.of("");

    /**
     * Creates a new {@code PropertiesMacro} instance.
     */
    public PropertiesMacro() {
        // no code by default
    }

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink xdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }

        final String modulePath = (String) request.getParameter("modulePath");

        configureGlobalProperties(modulePath);

        writePropertiesTable(xdocSink);
    }

    /**
     * Configures the global properties for the current module.
     *
     * @param modulePath the path of the current module processed.
     * @throws MacroExecutionException if the module path is invalid.
     */
    private static void configureGlobalProperties(String modulePath)
            throws MacroExecutionException {
        final String normalizedPath = modulePath.replace('\\', '/');
        final Path modulePathObj = Path.of(normalizedPath);
        currentModulePath = modulePathObj;
        final Path fileNamePath = modulePathObj.getFileName();

        if (fileNamePath == null) {
            throw new MacroExecutionException(
                    "Invalid modulePath '" + modulePath + "': No file name present.");
        }

        currentModuleName = CommonUtil.getFileNameWithoutExtension(
                fileNamePath.toString());
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
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_12);
        writeTableHeaderRow(sink);
        writeTablePropertiesRows(sink);
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_10);
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
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_12);
        sink.tableRow_();
    }

    /**
     * Writes a table header cell with the given text.
     *
     * @param sink sink to write to.
     * @param text the text to write.
     */
    private static void writeTableHeaderCell(Sink sink, String text) {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
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
        final Map<String, PropertyDetails> propertiesDetails = SiteUtil
                .buildPropertyDetails(properties, currentModuleName, currentModulePath, instance);

        final List<String> orderedProperties = orderProperties(properties);

        for (String propertyName : orderedProperties) {
            try {
                final PropertyDetails details = Objects
                        .requireNonNull(propertiesDetails.get(propertyName));
                writePropertyRow(sink, details);
            }
            // -@cs[IllegalCatch] we need to get details in wrapping exception
            catch (Exception exc) {
                final String message = String.format(Locale.ROOT,
                        "Exception while handling moduleName: %s propertyName: %s",
                        currentModuleName, propertyName);
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
        final List<String> orderProperties = new ArrayList<>(properties);
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
     * @param details the property details.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertyRow(Sink sink, PropertyDetails details)
            throws MacroExecutionException {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_12);
        sink.tableRow();

        writePropertyNameCell(sink, details.getName());
        writePropertyDescriptionCell(sink, details.getDescription());
        writePropertyTypeCell(sink, details);
        writePropertyDefaultValueCell(sink, details);
        writePropertySinceVersionCell(sink, details.getSinceVersion());

        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_12);
        sink.tableRow_();
    }

    /**
     * Writes a table cell with the given property name.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     */
    private static void writePropertyNameCell(Sink sink, String propertyName) {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
        sink.tableCell();
        sink.rawText("<a id=\"" + propertyName + "\"/>");
        sink.link(HASHTAG + propertyName);
        sink.text(propertyName);
        sink.link_();
        sink.tableCell_();
    }

    /**
     * Writes a table cell with the property description.
     *
     * @param sink sink to write to.
     * @param description the description.
     */
    private static void writePropertyDescriptionCell(Sink sink, String description) {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
        sink.tableCell();
        sink.rawText(description);
        sink.tableCell_();
    }

    /**
     * Writes a table cell with the property type.
     *
     * @param sink sink to write to.
     * @param details the property details.
     * @throws MacroExecutionException if link to the property_types.html file cannot be
     *                                 constructed.
     */
    private static void writePropertyTypeCell(Sink sink, PropertyDetails details)
            throws MacroExecutionException {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
        sink.tableCell();

        final PropertyDetails.TokenPropertyType tokenPropertyType =
                details.getTokenPropertyType();
        if (tokenPropertyType == PropertyDetails.TokenPropertyType.TOKEN_SET) {
            sink.text("set of any supported");
            writeLink(sink);
        }
        else if (tokenPropertyType == PropertyDetails.TokenPropertyType.TOKEN_SUBSET) {
            sink.text(SUBSET_OF_TOKENS);
            writeTokensList(sink, details.getConfigurableTokens(),
                    SiteUtil.PATH_TO_TOKEN_TYPES, true);
        }
        else if (tokenPropertyType == PropertyDetails.TokenPropertyType.JAVADOC_TOKEN_SUBSET) {
            sink.text("subset of javadoc tokens");
            writeTokensList(sink, details.getConfigurableTokens(),
                    SiteUtil.PATH_TO_JAVADOC_TOKEN_TYPES, true);
        }
        else {
            final String type = details.getType();

            if (type != null && type.startsWith(SUBSET_OF_TOKENS)) {
                processLinkForTokenTypes(sink);
            }
            else {
                final String relativePathToPropertyTypes =
                        SiteUtil.getLinkToDocument(currentModuleName, PROPERTY_TYPES_XML);
                final String escapedType;
                if (type == null) {
                    escapedType = "";
                }
                else {
                    escapedType = type.replace("[", ".5B")
                            .replace("]", ".5D");
                }

                final String url =
                        String.format(Locale.ROOT, URL_F, relativePathToPropertyTypes, escapedType);

                sink.link(url);
                sink.text(Objects.requireNonNullElse(type, ""));
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
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_16);
        final String link =
                SiteUtil.getLinkToDocument(currentModuleName, SiteUtil.PATH_TO_TOKEN_TYPES);
        sink.link(link);
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_20);
        sink.text(SiteUtil.TOKENS);
        sink.link_();
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
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
            sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_16);
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
            sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_18);
            sink.text(SiteUtil.DOT);
            sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
        }
        else {
            sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
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
                + HASHTAG + tokenName;
        sink.link(link);
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_20);
        sink.text(tokenName);
        sink.link_();
    }

    /**
     * Writes a table cell with the property default value.
     *
     * @param sink sink to write to.
     * @param details the property details.
     * @throws MacroExecutionException if an error occurs during retrieval of the default value.
     */
    private static void writePropertyDefaultValueCell(Sink sink, PropertyDetails details)
            throws MacroExecutionException {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
        sink.tableCell();

        final PropertyDetails.TokenPropertyType type = details.getTokenPropertyType();
        if (type == PropertyDetails.TokenPropertyType.TOKEN_SET
                && SiteUtil.TOKENS.equals(details.getName())) {
            writeAllTokensDefaultValue(sink, details);
        }
        else if (type == PropertyDetails.TokenPropertyType.TOKEN_SUBSET
                || type == PropertyDetails.TokenPropertyType.JAVADOC_TOKEN_SUBSET
                || !details.getDefaultValueTokens().isEmpty()) {
            writeTokenSubsetDefaultValue(sink, details);
        }
        else {
            writeStandardDefaultValue(sink, details);
        }

        sink.tableCell_();
    }

    /**
     * Writes the default value for properties that represent all tokens.
     *
     * @param sink sink to write to.
     * @param details property details.
     * @throws MacroExecutionException if an error occurs.
     */
    private static void writeAllTokensDefaultValue(Sink sink, PropertyDetails details)
            throws MacroExecutionException {
        final List<String> defaultTokens = details.getDefaultValueTokens();
        if (defaultTokens.size() == 1
                && SiteUtil.TOKEN_TYPES.equals(defaultTokens.getFirst())) {
            sink.text(SiteUtil.TOKEN_TYPES);
        }
        else {
            writeTokensList(sink, defaultTokens, SiteUtil.PATH_TO_TOKEN_TYPES, true);
        }
    }

    /**
     * Writes the default value for token subset properties.
     *
     * @param sink sink to write to.
     * @param details property details.
     * @throws MacroExecutionException if an error occurs.
     */
    private static void writeTokenSubsetDefaultValue(Sink sink, PropertyDetails details)
            throws MacroExecutionException {
        final PropertyDetails.TokenPropertyType type = details.getTokenPropertyType();
        final boolean printDot = type == PropertyDetails.TokenPropertyType.TOKEN_SUBSET
                || type == PropertyDetails.TokenPropertyType.JAVADOC_TOKEN_SUBSET;
        final String tokenTypesLink;
        if (type == PropertyDetails.TokenPropertyType.JAVADOC_TOKEN_SUBSET) {
            tokenTypesLink = SiteUtil.PATH_TO_JAVADOC_TOKEN_TYPES;
        }
        else {
            tokenTypesLink = SiteUtil.PATH_TO_TOKEN_TYPES;
        }
        writeTokensList(sink, details.getDefaultValueTokens(), tokenTypesLink, printDot);
    }

    /**
     * Writes a standard property default value.
     *
     * @param sink sink to write to.
     * @param details property details.
     */
    private static void writeStandardDefaultValue(Sink sink, PropertyDetails details) {
        final String defaultValue =
                getDisplayDefaultValue(details.getName(), details.getDefaultValue());
        if (defaultValue.isEmpty()) {
            sink.rawText("<code/>");
        }
        else {
            sink.rawText(CODE_START);
            sink.text(defaultValue);
            sink.rawText(CODE_END);
        }
    }

    /**
     * Converts a raw default value from PropertyDetails into a human-readable display
     * string for the properties table. This handles cases where the display value differs
     * from the raw stored metadata value. These conversions must NOT be applied during
     * XML metadata generation - they belong here in the macro only.
     *
     * @param propertyName the name of the property.
     * @param rawDefault the raw default value stored in PropertyDetails.
     * @return the display string for the default value table cell.
     */
    private static String getDisplayDefaultValue(String propertyName, String rawDefault) {
        final String result;
        if (SiteUtil.FILE_EXTENSIONS.equals(propertyName)
                && (rawDefault.isEmpty() || CURLY_BRACKET.equals(rawDefault))) {
            result = "all files";
        }
        else if (SiteUtil.CHARSET.equals(propertyName)) {
            result = "the charset property of the parent"
                    + " <a href=\"https://checkstyle.org/config.html#Checker\">Checker</a> module";
        }
        else {
            result = rawDefault;
        }
        return result;
    }

    /**
     * Writes a table cell with the property since version.
     *
     * @param sink sink to write to.
     * @param sinceVersion the since version.
     */
    private static void writePropertySinceVersionCell(Sink sink, String sinceVersion) {
        sink.rawText(ModuleJavadocParsingUtil.INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(sinceVersion);
        sink.tableCell_();
    }

}
