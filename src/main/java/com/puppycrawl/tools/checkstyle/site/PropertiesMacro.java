///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * A macro that inserts a table of properties for the given checkstyle module.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {

    /** The string 'charset'. */
    private static final String CHARSET = "charset";
    /** The string '{}'. */
    private static final String CURLY_BRACKETS = "{}";
    /** The string 'fileExtensions'. */
    private static final String FILE_EXTENSIONS = "fileExtensions";
    /** The string ', '. */
    private static final String COMMA_SPACE = ", ";

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

    /** Set of properties that every check has. */
    private static final Set<String> CHECK_PROPERTIES = getProperties(AbstractCheck.class);

    /** Set of properties that every Javadoc check has. */
    private static final Set<String> JAVADOC_CHECK_PROPERTIES =
            getProperties(AbstractJavadocCheck.class);

    /** Set of properties that every FileSet check has. */
    private static final Set<String> FILESET_PROPERTIES = getProperties(AbstractFileSetCheck.class);

    /** Set of properties that are undocumented. Those are internal properties. */
    private static final Set<String> UNDOCUMENTED_PROPERTIES = Set.of(
        "SuppressWithNearbyCommentFilter.fileContents",
        "SuppressionCommentFilter.fileContents"
    );

    /** List of files who are parent modules and contain certain properties that checks inherit. */
    private static final List<File> PARENT_MODULES_WITH_PROPERTIES = List.of(
        new File("src/main/java/com/puppycrawl/tools/checkstyle/checks/naming/AbstractAccessControlNameCheck.java"),
        new File("src/main/java/com/puppycrawl/tools/checkstyle/checks/naming/AbstractNameCheck.java"),
        new File("src/main/java/com/puppycrawl/tools/checkstyle/checks/javadoc/AbstractJavadocCheck.java")
    );

    /**
     * Map of properties whose since version is different from module version but
     * are not specified in code because they are inherited from their parent class(es).
     */
    private static final Map<String, String> SINCE_VERSION_EXCEPTIONS = Map.ofEntries(
        Map.entry("MissingDeprecatedCheck.violateExecutionOnNonTightHtml", "8.24")
    );

    /** Mao of all parent modules properties and their javadocs. */
    private static final Map<String, DetailNode> PARENT_MODULES_PROPERTIES_JAVADOCS =
            new HashMap<>();

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }

        // lazy initialization
        if (PARENT_MODULES_PROPERTIES_JAVADOCS.isEmpty()) {
            processParentModules();
        }

        final String modulePath = (String) request.getParameter("modulePath");
        final File moduleFile = new File(modulePath);
        final String moduleName = CommonUtil.getFileNameWithoutExtension(moduleFile.getName());

        processModule(moduleName, moduleFile);
        writePropertiesTable((XdocSink) sink, moduleName);
    }

    /**
     * Collect the properties setters javadocs of the parent modules.
     *
     * @throws MacroExecutionException if an error occurs during processing.
     */
    private static void processParentModules() throws MacroExecutionException {
        for (File parentModule : PARENT_MODULES_WITH_PROPERTIES) {
            final String parentModuleName = CommonUtil
                    .getFileNameWithoutExtension(parentModule.getName());
            processModule(parentModuleName, parentModule);
            final Map<String, DetailNode> parentModuleJavadocs =
                    ClassAndPropertiesSettersJavadocScraper.getJavadocs();
            PARENT_MODULES_PROPERTIES_JAVADOCS.putAll(parentModuleJavadocs);
        }
    }

    /**
     * Scrape the Javadocs of the class and its properties setters with
     * ClassAndPropertiesSettersJavadocScraper.
     *
     * @param moduleName the name of the module.
     * @param moduleFile the module file.
     * @throws MacroExecutionException if an error occurs during processing.
     */
    private static void processModule(String moduleName, File moduleFile)
            throws MacroExecutionException {
        if (!moduleFile.isFile()) {
            final String message = String.format(Locale.ROOT,
                    "File %s is not a file. Please check the 'modulePath' property.", moduleFile);
            throw new MacroExecutionException(message);
        }
        ClassAndPropertiesSettersJavadocScraper.clear();
        ClassAndPropertiesSettersJavadocScraper.setModuleName(moduleName);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(
                                ClassAndPropertiesSettersJavadocScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty(CHARSET, StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        try {
            checker.configure(defaultConfiguration);
            final List<File> filesToProcess = List.of(moduleFile);
            checker.process(filesToProcess);
            checker.destroy();
        }
        catch (CheckstyleException checkstyleException) {
            final String message = String.format(Locale.ROOT, "Failed processing %s", moduleName);
            throw new MacroExecutionException(message, checkstyleException);
        }
    }

    /**
     * Writes the properties table for the given module. Expects that the module has been processed
     * with the ClassAndPropertiesSettersJavadocScraper before calling this method.
     *
     * @param sink the sink to write to.
     * @param moduleName the name of the module.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertiesTable(XdocSink sink, String moduleName)
            throws MacroExecutionException {
        sink.table();
        sink.setInsertNewline(false);
        sink.tableRows(null, false);
        sink.rawText(INDENT_LEVEL_12);
        writeTableHeaderRow(sink);
        writeTablePropertiesRows(sink, moduleName);
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
     * @param moduleName the name of the module.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writeTablePropertiesRows(Sink sink, String moduleName)
            throws MacroExecutionException {
        final Map<String, DetailNode> unmodifiableJavadocs =
                ClassAndPropertiesSettersJavadocScraper.getJavadocs();
        final Map<String, DetailNode> javadocs = new LinkedHashMap<>(unmodifiableJavadocs);
        final Object instance = SiteUtil.getModuleInstance(moduleName);
        final Class<?> clss = instance.getClass();

        final Set<String> properties = getProperties(clss);
        fixCapturedProperties(instance, clss, properties);

        for (String property : properties) {
            javadocs.putIfAbsent(property, PARENT_MODULES_PROPERTIES_JAVADOCS.get(property));
            writePropertyRow(sink, moduleName, property, javadocs.get(property), clss, instance);
        }
    }

    /**
     * Writes a table row with 5 columns for the given property - name, description, type,
     * default value, since.
     *
     * @param sink sink to write to.
     * @param moduleName the name of the module.
     * @param propertyName the name of the property.
     * @param javadoc the Javadoc of the property.
     * @param clss the class of the module.
     * @param instance the instance of the module.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertyRow(Sink sink, String moduleName, String propertyName,
                                         DetailNode javadoc, Class<?> clss, Object instance)
            throws MacroExecutionException {
        final Field field = getField(clss, propertyName);

        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow();

        writePropertyNameCell(sink, propertyName);
        writePropertyDescriptionCell(sink, propertyName, moduleName, javadoc);
        writePropertyTypeCell(sink, propertyName, moduleName, field, instance);
        writePropertyDefaultValueCell(sink, propertyName, instance, field);
        writePropertySinceVersionCell(sink, propertyName, moduleName, javadoc);

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
     * @param moduleName the name of the module.
     * @param javadoc the Javadoc of the property containing the description.
     * @throws MacroExecutionException if an error occurs during retrieval of the description.
     */
    private static void writePropertyDescriptionCell(Sink sink, String propertyName,
                                                     String moduleName, DetailNode javadoc)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        if ("tokens".equals(propertyName)) {
            sink.text("tokens to check");
        }
        else {
            final String descriptionString = getDescription(javadoc, moduleName)
                    .substring("Setter to ".length());
            final String firstLetterCapitalized = descriptionString.substring(0, 1)
                    .toUpperCase(Locale.ROOT);
            sink.rawText(firstLetterCapitalized + descriptionString.substring(1));
        }
        sink.tableCell_();
    }

    /**
     * Writes a table cell with the property type.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param moduleName the name of the module.
     * @param field the field of the property.
     * @param instance the instance of the module.
     * @throws MacroExecutionException if link to the property_types.html file cannot be
     *                                 constructed.
     */
    private static void writePropertyTypeCell(Sink sink, String propertyName,
                                              String moduleName, Field field, Object instance)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();

        if ("tokens".equals(propertyName)) {
            final AbstractCheck check = (AbstractCheck) instance;
            sink.text("subset of tokens");
            final List<Integer> tokens = subtractTokens(check.getAcceptableTokens(),
                    check.getRequiredTokens());
            for (int index = 0; index < tokens.size(); index++) {
                final int token = tokens.get(index);
                final String tokenName = TokenUtil.getTokenName(token);
                sink.rawText(INDENT_LEVEL_16);
                if (index != 0) {
                    sink.text(COMMA_SPACE);
                }
                sink.link("../../apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#" + tokenName);
                sink.rawText(INDENT_LEVEL_20);
                sink.text(tokenName);
                sink.link_();
            }
            sink.rawText(INDENT_LEVEL_18);
            sink.text(".");
            sink.rawText(INDENT_LEVEL_14);
        }
        else if ("javadocTokens".equals(propertyName)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;

        }
        else {
            final String type = getType(field);
            final String relativePathToPropertyTypes = getLinkToPropertyTypes(moduleName);
            sink.link(String.format(Locale.ROOT, "%s#%s", relativePathToPropertyTypes, type));
            sink.text(type);
            sink.link_();
        }
        sink.tableCell_();
    }

    /**
     * Writes a table cell with the property default value.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param instance the instance of the module.
     * @param field the field of the property.
     * @throws MacroExecutionException if an error occurs during retrieval of the default value.
     */
    private static void writePropertyDefaultValueCell(Sink sink, String propertyName,
                                                      Object instance, Field field)
            throws MacroExecutionException {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();

        if ("tokens".equals(propertyName)) {
            final AbstractCheck check = (AbstractCheck) instance;
            final List<Integer> tokens = subtractTokens(check.getDefaultTokens(),
                    check.getRequiredTokens());
            for (int index = 0; index < tokens.size(); index++) {
                final int token = tokens.get(index);
                final String tokenName = TokenUtil.getTokenName(token);
                sink.rawText(INDENT_LEVEL_16);
                if (index != 0) {
                    sink.text(COMMA_SPACE);
                }
                sink.link("../../apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#" + tokenName);
                sink.rawText(INDENT_LEVEL_20);
                sink.text(tokenName);
                sink.link_();
            }
            sink.rawText(INDENT_LEVEL_18);
            sink.text(".");
            sink.rawText(INDENT_LEVEL_14);
        }
        else {
            final String defaultValue = getDefaultValue(propertyName, field, instance);
            // until https://github.com/checkstyle/checkstyle/issues/13426
            // After that, verbatim should be used
            sink.rawText("<code>");
            sink.text(defaultValue);
            sink.rawText("</code>");
        }

        sink.tableCell_();
    }

    private static List<Integer> subtractTokens(int[] tokens, int... subtractions) {
        final List<Integer> result = new ArrayList<>(tokens.length);
        for (int token : tokens) {
            boolean found = false;

            for (int subtraction : subtractions) {
                if (subtraction == token) {
                    found = true;
                    break;
                }
            }

            if (found) {
                continue;
            }

            result.add(token);
        }

        return result;
    }

    /**
     * Writes a table cell with the property since version.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param moduleName the name of the module.
     * @param javadoc the Javadoc of the property containing the since version.
     */
    private static void writePropertySinceVersionCell(Sink sink, String propertyName,
                                                      String moduleName, DetailNode javadoc) {
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        final String sinceVersion;
        if (SINCE_VERSION_EXCEPTIONS.containsKey(moduleName + "." + propertyName)) {
            sinceVersion = SINCE_VERSION_EXCEPTIONS.get(moduleName + "." + propertyName);
        }
        else if (PARENT_MODULES_PROPERTIES_JAVADOCS.containsKey(propertyName)
                || "tokens".equals(propertyName)
                || "javadocTokens".equals(propertyName)) {
            sinceVersion = getSinceVersion(ClassAndPropertiesSettersJavadocScraper.getJavadocs().get(moduleName));
        }
        else {
            sinceVersion = getSinceVersion(javadoc);
        }
        sink.text(sinceVersion);
        sink.tableCell_();
    }

    /**
     * Constructs string with relative link to the property_types.html file.
     *
     * @param moduleName the name of the module.
     * @return relative link to the property_types.html file.
     * @throws MacroExecutionException if link to the property_types.html file cannot be
     */
    private static String getLinkToPropertyTypes(String moduleName)
            throws MacroExecutionException {
        final Path templatePath = SiteUtil.getTemplatePath(moduleName.replace("Check", ""));
        if (templatePath == null) {
            throw new MacroExecutionException(
                    String.format(Locale.ROOT, "Could not find template for %s", moduleName));
        }
        final Path templatePathParent = templatePath.getParent();
        if (templatePathParent == null) {
            throw new MacroExecutionException("Failed to get parent path for " + templatePath);
        }
        return templatePathParent
                .relativize(Paths.get("src", "xdocs", "property_types.xml"))
                .toString()
                .replace(".xml", ".html");
    }

    /**
     * Get the type of the property.
     *
     * @param field the field to get the type of.
     * @return the type of the property.
     * @throws MacroExecutionException if an error occurs during getting the type.
     */
    private static String getType(Field field) throws MacroExecutionException {
        final Class<?> fieldClass = getFieldClass(field);
        return Optional.ofNullable(field)
                .map(nonNullField -> nonNullField.getAnnotation(XdocsPropertyType.class))
                .map(propertyType -> propertyType.value().getDescription())
                .orElse(fieldClass.getSimpleName());
    }

    /**
     * Extract the description of the setter from the Javadoc.
     *
     * @param javadoc the Javadoc to extract the description from.
     * @param moduleName the name of the module.
     * @return the description of the setter.
     * @throws MacroExecutionException if an error occurs during extraction.
     */
    private static String getDescription(DetailNode javadoc, String moduleName)
            throws MacroExecutionException {
        final Stack<DetailNode> stack = new Stack<>();

        final List<DetailNode> descriptionNodes = getDescriptionNodes(javadoc);
        // Prepare for DFS traversal by pushing children onto the stack
        for (int childIndex = descriptionNodes.size() - 1; childIndex >= 0; childIndex--) {
            stack.push(descriptionNodes.get(childIndex));
        }
        // Perform DFS traversal on description nodes
        final StringBuilder description = new StringBuilder(128);
        boolean isInCodeLiteral = false;
        boolean isInHtmlElement = false;
        boolean isInHrefAttribute = false;
        while (!stack.isEmpty()) {
            final DetailNode node = stack.pop();
            final DetailNode[] nodeChildren = node.getChildren();
            for (int childIndex = nodeChildren.length - 1; childIndex >= 0; childIndex--) {
                stack.push(nodeChildren[childIndex]);
            }
            if (node.getType() == JavadocTokenTypes.HTML_TAG_NAME
                    && node.getText().equals("href")) {
                isInHrefAttribute = true;
            }
            if (isInHrefAttribute && node.getType() == JavadocTokenTypes.ATTR_VALUE) {
                final String href = node.getText();
                // Do not touch external links
                if (!href.contains("https://checkstyle.org/")) {
                    continue;
                }

                final Path templatePath = SiteUtil.getTemplatePath(moduleName.replace("Check", ""));

                String finalHref = href.replace("https://checkstyle.org/", "");
                // Remove first and last characters, they are always double quotes
                finalHref = finalHref.substring(1, finalHref.length() - 1);
//                finalHref = finalHref.split("#")[0];
                finalHref = "src/xdocs/" + finalHref;
                finalHref = templatePath.getParent().relativize(Paths.get(finalHref)).toString();
                description.append("\"" + finalHref + "\"");
                isInHrefAttribute = false;
                continue;
            }
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {
                isInHtmlElement = true;
            }
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT_END) {
                isInHtmlElement = false;
            }
            if (node.getType() == JavadocTokenTypes.TEXT
                || (isInHtmlElement && node.getChildren().length == 0)) {
                // Some HTML elements span multiple lines so we avoid the javadoc asterisk
                if (node.getType() == JavadocTokenTypes.LEADING_ASTERISK) {
                    continue;
                }
                description.append(node.getText());
            }
            if (node.getType() == JavadocTokenTypes.CODE_LITERAL) {
                isInCodeLiteral = true;
                description.append("<code>");
            }
            if (node.getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG_END
                    && isInCodeLiteral) {
                isInCodeLiteral = false;
                description.append("</code>");
            }
        }
        return description.toString().trim();
    }

    /**
     * Extracts description nodes from javadoc.
     *
     * @param javadoc the Javadoc to extract the description from.
     * @return the description nodes of the setter.
     */
    private static List<DetailNode> getDescriptionNodes(DetailNode javadoc) {
        final DetailNode[] children = javadoc.getChildren();
        final List<DetailNode> descriptionNodes = new ArrayList<>();
        for (final DetailNode child : children) {
            if (isEndOfDescription(child)) {
                break;
            }
            descriptionNodes.add(child);
        }
        return descriptionNodes;
    }

    /**
     * Determines if the given child index is the end of the description. The end of the description
     * is defined as 4 consecutive nodes of type NEWLINE, LEADING_ASTERISK, NEWLINE,
     * LEADING_ASTERISK. This is an asterisk that is alone on a line. Just like the one below this
     * line.
     *
     * @param child the child to check.
     * @return true if the given child index is the end of the description.
     */
    private static boolean isEndOfDescription(DetailNode child) {
        final DetailNode nextSibling = JavadocUtil.getNextSibling(child);
        final DetailNode secondNextSibling = JavadocUtil.getNextSibling(nextSibling);
        final DetailNode thirdNextSibling = JavadocUtil.getNextSibling(secondNextSibling);

        return child.getType() == JavadocTokenTypes.NEWLINE
                    && nextSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK
                    && secondNextSibling.getType() == JavadocTokenTypes.NEWLINE
                    && thirdNextSibling.getType() == JavadocTokenTypes.LEADING_ASTERISK;
    }

    /**
     * Extract the since version from the Javadoc.
     *
     * @param javadoc the Javadoc to extract the since version from.
     * @return the since version of the setter.
     */
    private static String getSinceVersion(DetailNode javadoc) {
        final DetailNode sinceJavadocTag = getSinceJavadocTag(javadoc);
        final DetailNode description = JavadocUtil.findFirstToken(sinceJavadocTag,
                JavadocTokenTypes.DESCRIPTION);
        final DetailNode text = JavadocUtil.findFirstToken(description, JavadocTokenTypes.TEXT);
        return text.getText();
    }

    /**
     * Find the since Javadoc tag node in the given Javadoc.
     *
     * @param javadoc the Javadoc to search.
     * @return the since Javadoc tag node or null if not found.
     */
    private static DetailNode getSinceJavadocTag(DetailNode javadoc) {
        final DetailNode[] children = javadoc.getChildren();
        DetailNode javadocTagWithSince = null;
        for (final DetailNode child : children) {
            if (child.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                final DetailNode sinceNode = JavadocUtil.findFirstToken(
                        child, JavadocTokenTypes.SINCE_LITERAL);
                if (sinceNode != null) {
                    javadocTagWithSince = child;
                    break;
                }
            }
        }
        return javadocTagWithSince;
    }

    /**
     * Get the default value of the property.
     *
     * @param propertyName the name of the property.
     * @param field the field to get the default value of.
     * @param classInstance the instance of the class to get the default value of.
     * @return the default value of the property.
     * @throws MacroExecutionException if an error occurs during getting the default value.
     * @noinspection IfStatementWithTooManyBranches
     * @noinspectionreason IfStatementWithTooManyBranches - complex nature of getting properties
     *      from XML files requires giant if/else statement
     */
    // -@cs[CyclomaticComplexity] Splitting would not make the code more readable
    private static String getDefaultValue(String propertyName, Field field, Object classInstance)
            throws MacroExecutionException {
        final Object value = SiteUtil.getFieldValue(field, classInstance);
        final Class<?> fieldClass = getFieldClass(field);
        String result = null;
        if (CHARSET.equals(propertyName)) {
            result = "the charset property of the parent Checker module";
        }
        else if ("PropertyCacheFile".equals(fieldClass.getSimpleName())) {
            result = "null (no cache file)";
        }
        else if (fieldClass == boolean.class) {
            result = value.toString();
        }
        else if (fieldClass == int.class) {
            result = value.toString();
        }
        else if (fieldClass == int[].class) {
            result = getIntArrayPropertyValue(value);
        }
        else if (fieldClass == double[].class) {
            result = removeSquareBrackets(Arrays.toString((double[]) value).replace(".0", ""));
            if (result.isEmpty()) {
                result = CURLY_BRACKETS;
            }
        }
        else if (fieldClass == String[].class) {
            result = getStringArrayPropertyValue(propertyName, value);
        }
        else if (fieldClass == URI.class || fieldClass == String.class) {
            if (value != null) {
                result = '"' + value.toString() + '"';
            }
        }
        else if (fieldClass == Pattern.class) {
            if (value != null) {
                result = '"' + value.toString().replace("\n", "\\n").replace("\t", "\\t")
                        .replace("\r", "\\r").replace("\f", "\\f") + '"';
            }
        }
        else if (fieldClass == Pattern[].class) {
            result = getPatternArrayPropertyValue(value);
        }
        else if (fieldClass.isEnum()) {
            if (value != null) {
                result = value.toString().toLowerCase(Locale.ENGLISH);
            }
        }
        else if (fieldClass == AccessModifierOption[].class) {
            result = removeSquareBrackets(Arrays.toString((Object[]) value));
        }
        else {
            final String message = String.format(Locale.ROOT,
                    "Unknown property type: %s", fieldClass.getSimpleName());
            throw new MacroExecutionException(message);
        }

        if (result == null) {
            result = "null";
        }

        return result;
    }

    /**
     * Gets the name of the bean property's default value for the Pattern array class.
     *
     * @param fieldValue The bean property's value
     * @return String form of property's default value
     */
    private static String getPatternArrayPropertyValue(Object fieldValue) {
        Object value = fieldValue;
        String result;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
            final Pattern[] newArray = new Pattern[collection.size()];
            final Iterator<?> iterator = collection.iterator();
            int index = 0;

            while (iterator.hasNext()) {
                final Object next = iterator.next();
                newArray[index] = (Pattern) next;
                index++;
            }

            value = newArray;
        }

        if (value != null && Array.getLength(value) > 0) {
            final String[] newArray = new String[Array.getLength(value)];

            for (int index = 0; index < newArray.length; index++) {
                newArray[index] = ((Pattern) Array.get(value, index)).pattern();
            }

            result = removeSquareBrackets(Arrays.toString(newArray));
        }
        else {
            result = "";
        }

        if (result.isEmpty()) {
            result = CURLY_BRACKETS;
        }
        return result;
    }

    /**
     * Removes square brackets [ and ] from the given string.
     *
     * @param value the string to remove square brackets from.
     * @return the string without square brackets.
     */
    private static String removeSquareBrackets(String value) {
        return value
                .replace("[", "")
                .replace("]", "");
    }

    /**
     * Gets the name of the bean property's default value for the string array class.
     *
     * @param propertyName The bean property's name
     * @param value The bean property's value
     * @return String form of property's default value
     */
    private static String getStringArrayPropertyValue(String propertyName, Object value) {
        String result;
        if (value == null) {
            result = "";
        }
        else {
            final Stream<?> valuesStream;
            if (value instanceof Collection) {
                final Collection<?> collection = (Collection<?>) value;
                valuesStream = collection.stream();
            }
            else {
                final Object[] array = (Object[]) value;
                valuesStream = Arrays.stream(array);
            }
            result = valuesStream
                .map(String.class::cast)
                .sorted()
                .collect(Collectors.joining(COMMA_SPACE));
            valuesStream.close();
        }

        if (result.isEmpty()) {
            if (FILE_EXTENSIONS.equals(propertyName)) {
                result = "all files";
            }
            else {
                result = CURLY_BRACKETS;
            }
        }
        return result;
    }

    /**
     * Returns the name of the bean property's default value for the int array class.
     *
     * @param value The bean property's value.
     * @return String form of property's default value.
     */
    private static String getIntArrayPropertyValue(Object value) {
        final IntStream stream;
        if (value instanceof Collection) {
            final Collection<?> collection = (Collection<?>) value;
            stream = collection.stream()
                    .mapToInt(number -> (int) number);
        }
        else if (value instanceof BitSet) {
            stream = ((BitSet) value).stream();
        }
        else {
            stream = Arrays.stream((int[]) value);
        }
        String result = stream
                .mapToObj(TokenUtil::getTokenName)
                .sorted()
                .collect(Collectors.joining(COMMA_SPACE));
        if (result.isEmpty()) {
            result = CURLY_BRACKETS;
        }
        stream.close();
        return result;
    }

    /**
     * Gets the class of the given field.
     *
     * @param field the field to get the class of.
     * @return the class of the field.
     * @throws MacroExecutionException if an error occurs during getting the class.
     */
    private static Class<?> getFieldClass(Field field) throws MacroExecutionException {
        Class<?> result = null;

        if (field != null) {
            result = field.getType();
        }
        if (result == List.class || result == Set.class) {
            final ParameterizedType type = (ParameterizedType) field.getGenericType();
            final Class<?> parameterClass = (Class<?>) type.getActualTypeArguments()[0];

            if (parameterClass == Integer.class) {
                result = int[].class;
            }
            else if (parameterClass == String.class) {
                result = String[].class;
            }
            else if (parameterClass == Pattern.class) {
                result = Pattern[].class;
            }
            else {
                final String message = "Unknown parameterized type: "
                        + parameterClass.getSimpleName();
                throw new MacroExecutionException(message);
            }
        }
        else if (result == BitSet.class) {
            result = int[].class;
        }

        return result;
    }

    /**
     * Gets the field with the given name from the given class.
     *
     * @param fieldClass the class to get the field from.
     * @param propertyName the name of the field.
     * @return the field we are looking for.
     */
    private static Field getField(Class<?> fieldClass, String propertyName) {
        Field result = null;
        Class<?> currentClass = fieldClass;

        while (!Object.class.equals(currentClass)) {
            try {
                result = currentClass.getDeclaredField(propertyName);
                result.trySetAccessible();
                break;
            }
            catch (NoSuchFieldException ignored) {
                currentClass = currentClass.getSuperclass();
            }
        }

        return result;
    }

    /**
     * Get a set of properties for the given class.
     *
     * @param clss the class to get the properties for.
     * @return a set of properties for the given class.
     */
    private static Set<String> getProperties(Class<?> clss) {
        final Set<String> result = new TreeSet<>();
        final PropertyDescriptor[] map = PropertyUtils.getPropertyDescriptors(clss);

        for (PropertyDescriptor propertyDescriptor : map) {
            if (propertyDescriptor.getWriteMethod() != null) {
                result.add(propertyDescriptor.getName());
            }
        }

        return result;
    }

    /**
     * Fixes the captured properties. This method removes properties that are not documented and
     * adds properties that have to be documented but are not captured.
     *
     * @param instance the instance of the module.
     * @param clss the class of the module.
     * @param properties the properties of the module.
     */
    private static void fixCapturedProperties(Object instance,
                                              Class<?> clss, Set<String> properties) {
        // remove global properties that don't need documentation
        if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(JAVADOC_CHECK_PROPERTIES);

            // override
            properties.add("violateExecutionOnNonTightHtml");
        }
        else if (AbstractCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(CHECK_PROPERTIES);
        }
        if (AbstractFileSetCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(FILESET_PROPERTIES);

            // override
            properties.add(FILE_EXTENSIONS);
        }

        // remove undocumented properties
        new HashSet<>(properties).stream()
            .filter(prop -> UNDOCUMENTED_PROPERTIES.contains(clss.getSimpleName() + "." + prop))
            .forEach(properties::remove);

        if (AbstractCheck.class.isAssignableFrom(clss)) {
            final AbstractCheck check = (AbstractCheck) instance;

            final int[] acceptableTokens = check.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final int[] defaultTokens = check.getDefaultTokens();
            Arrays.sort(defaultTokens);
            final int[] requiredTokens = check.getRequiredTokens();
            Arrays.sort(requiredTokens);

            if (!Arrays.equals(acceptableTokens, defaultTokens)
                    || !Arrays.equals(acceptableTokens, requiredTokens)) {
                properties.add("tokens");
            }
        }

        if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
            final AbstractJavadocCheck check = (AbstractJavadocCheck) instance;

            final int[] acceptableJavadocTokens = check.getAcceptableJavadocTokens();
            Arrays.sort(acceptableJavadocTokens);
            final int[] defaultJavadocTokens = check.getDefaultJavadocTokens();
            Arrays.sort(defaultJavadocTokens);
            final int[] requiredJavadocTokens = check.getRequiredJavadocTokens();
            Arrays.sort(requiredJavadocTokens);

            if (!Arrays.equals(acceptableJavadocTokens, defaultJavadocTokens)
                    || !Arrays.equals(acceptableJavadocTokens, requiredJavadocTokens)) {
                properties.add("javadocTokens");
            }
        }
    }
}
