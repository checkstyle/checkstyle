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

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * A macro that inserts a table of properties for the given checkstyle module.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {

    /** The name of the current module being processed. */
    private static String currentModuleName;

    /** The string 'tokens'. */
    private static final String TOKENS = "tokens";
    /** The string 'checks'. */
    private static final String CHECKS = "checks";
    /** The string 'naming'. */
    private static final String NAMING = "naming";
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

    /** Path to main source code folder. */
    private static final String MAIN_FOLDER_PATH = Paths.get(
            "src", "main", "java", "com", "puppycrawl", "tools", "checkstyle").toString();

    /** List of files who are parent modules and contain certain properties that checks inherit. */
    private static final List<File> PARENT_MODULES_WITH_PROPERTIES = List.of(
        new File(Paths.get(MAIN_FOLDER_PATH,
                CHECKS, NAMING, "AbstractAccessControlNameCheck.java").toString()),
        new File(Paths.get(MAIN_FOLDER_PATH,
                CHECKS, NAMING, "AbstractNameCheck.java").toString()),
        new File(Paths.get(MAIN_FOLDER_PATH,
                CHECKS, "javadoc", "AbstractJavadocCheck.java").toString()),
        new File(Paths.get(MAIN_FOLDER_PATH,
                "api", "AbstractFileSetCheck.java").toString()),
        new File(Paths.get(MAIN_FOLDER_PATH,
                CHECKS, "header", "AbstractHeaderCheck.java").toString())
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
        currentModuleName = CommonUtil.getFileNameWithoutExtension(moduleFile.getName());

        processModule(currentModuleName, moduleFile);
        writePropertiesTable((XdocSink) sink);
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
        defaultConfiguration.addProperty("charset", StandardCharsets.UTF_8.name());
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
        final Map<String, DetailNode> unmodifiableJavadocs =
                ClassAndPropertiesSettersJavadocScraper.getJavadocs();
        final Map<String, DetailNode> javadocs = new LinkedHashMap<>(unmodifiableJavadocs);
        final Object instance = SiteUtil.getModuleInstance(currentModuleName);
        final Class<?> clss = instance.getClass();

        final Set<String> properties = SiteUtil.getProperties(clss);
        SiteUtil.fixCapturedProperties(instance, clss, properties);

        for (String property : properties) {
            javadocs.putIfAbsent(property, PARENT_MODULES_PROPERTIES_JAVADOCS.get(property));
            final DetailNode propertyJavadoc = javadocs.get(property);
            final DetailNode currentModuleJavadoc = javadocs.get(currentModuleName);
            writePropertyRow(sink, property,
                    propertyJavadoc, clss, instance, currentModuleJavadoc);
        }
    }

    /**
     * Writes a table row with 5 columns for the given property - name, description, type,
     * default value, since.
     *
     * @param sink sink to write to.
     * @param propertyName the name of the property.
     * @param propertyJavadoc the Javadoc of the property.
     * @param clss the class of the module.
     * @param instance the instance of the module.
     * @param moduleJavadoc the Javadoc of the module.
     * @throws MacroExecutionException if an error occurs during writing.
     */
    private static void writePropertyRow(Sink sink, String propertyName,
                                         DetailNode propertyJavadoc, Class<?> clss, Object instance,
                                            DetailNode moduleJavadoc)
            throws MacroExecutionException {
        final Field field = SiteUtil.getField(clss, propertyName);

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

        if (TOKENS.equals(propertyName)) {
            final AbstractCheck check = (AbstractCheck) instance;
            final List<Integer> tokens = SiteUtil
                    .subtractTokens(check.getAcceptableTokens(), check.getRequiredTokens());
            sink.text("subset of tokens");
            writeListOfTokens(sink, tokens);
        }
        else {
            final String type = SiteUtil.getType(field, propertyName, currentModuleName, instance);
            final String relativePathToPropertyTypes = SiteUtil
                    .getLinkToDocument(currentModuleName, "property_types.xml");
            final String url = String
                    .format(Locale.ROOT, "%s#%s", relativePathToPropertyTypes,
                            type
                                    .replace("[", ".5B")
                                    .replace("]", ".5D"));
            sink.link(url);
            sink.text(type);
            sink.link_();
        }
        sink.tableCell_();
    }

    /**
     * Write a list of tokens with links to the TokenTypes.html file.
     *
     * @param sink sink to write to.
     * @param tokens the list of tokens to write.
     * @throws MacroExecutionException if link to the TokenTypes.html file cannot be constructed.
     */
    private static void writeListOfTokens(Sink sink, List<Integer> tokens)
            throws MacroExecutionException {
        for (int index = 0; index < tokens.size(); index++) {
            final int token = tokens.get(index);
            final String tokenName = TokenUtil.getTokenName(token);
            sink.rawText(INDENT_LEVEL_16);
            if (index != 0) {
                sink.text(", ");
            }
            sink.link(
                    SiteUtil.getLinkToDocument(currentModuleName,
                            "apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html")
                            + "#"
                            + tokenName
            );
            sink.rawText(INDENT_LEVEL_20);
            sink.text(tokenName);
            sink.link_();
        }
        sink.rawText(INDENT_LEVEL_18);
        sink.text(".");
        sink.rawText(INDENT_LEVEL_14);
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

        if (TOKENS.equals(propertyName)) {
            final AbstractCheck check = (AbstractCheck) instance;
            final List<Integer> tokens = SiteUtil.subtractTokens(
                    check.getDefaultTokens(), check.getRequiredTokens());
            writeListOfTokens(sink, tokens);
        }
        else {
            final String defaultValue = SiteUtil.getDefaultValue(
                    propertyName, field, instance, currentModuleName);
            sink.rawText("<code>");
            sink.text(defaultValue);
            sink.rawText("</code>");
        }

        sink.tableCell_();
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
                currentModuleName, moduleJavadoc, propertyName, propertyJavadoc,
                PARENT_MODULES_PROPERTIES_JAVADOCS);
        sink.text(sinceVersion);
        sink.tableCell_();
    }
}
