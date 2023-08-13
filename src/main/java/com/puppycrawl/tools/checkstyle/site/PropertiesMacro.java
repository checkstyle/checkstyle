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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
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
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;

/**
 * A macro that inserts a table of properties for the given checkstyle module.
 */
@Component(role = Macro.class, hint = "properties")
public class PropertiesMacro extends AbstractMacro {

    /** A newline with 10 spaces of indentation. */
    private static final String INDENT_LEVEL_10 = SiteUtil.getNewlineAndIndentSpaces(10);
    /** A newline with 12 spaces of indentation. */
    private static final String INDENT_LEVEL_12 = SiteUtil.getNewlineAndIndentSpaces(12);
    /** A newline with 14 spaces of indentation. */
    private static final String INDENT_LEVEL_14 = SiteUtil.getNewlineAndIndentSpaces(14);

    private static final List<String> FILESET_LIST = List.of(
        "Header",
        "LineLength",
        "Translation",
        "SeverityMatchFilter",
        "SuppressWithNearbyTextFilter",
        "SuppressWithPlainTextCommentFilter",
        "SuppressionFilter",
        "SuppressionSingleFilter",
        "SuppressWarningsFilter",
        "BeforeExecutionExclusionFileFilter",
        "RegexpHeader",
        "RegexpOnFilename",
        "RegexpSingleline",
        "RegexpMultiline",
        "JavadocPackage",
        "NewlineAtEndOfFile",
        "OrderedProperties",
        "UniqueProperties",
        "FileLength",
        "FileTabCharacter"
    );

    /** Set of properties that every check has. */
    private static final Set<String> CHECK_PROPERTIES = getProperties(AbstractCheck.class);

    /** Set of properties that every Javadoc check has. */
    private static final Set<String> JAVADOC_CHECK_PROPERTIES =
            getProperties(AbstractJavadocCheck.class);

    /** Set of properties that every FileSet check has. */
    private static final Set<String> FILESET_PROPERTIES = getProperties(AbstractFileSetCheck.class);

    /** Set of properties that are undocumented. Those are internal properties. */
    private static final Set<String> UNDOCUMENTED_PROPERTIES = Set.of(
            "Checker.classLoader",
            "Checker.classloader",
            "Checker.moduleClassLoader",
            "Checker.moduleFactory",
            "TreeWalker.classLoader",
            "TreeWalker.moduleFactory",
            "TreeWalker.cacheFile",
            "TreeWalker.upChild",
            "SuppressWithNearbyCommentFilter.fileContents",
            "SuppressionCommentFilter.fileContents"
    );

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }
        final String modulePath = (String) request.getParameter("modulePath");
        final XdocSink xdocSink = (XdocSink) sink;

        ClassAndPropertiesJavadocScraper.clearModulePropertySetterJavadocs();
        ClassAndPropertiesJavadocScraper.setModuleName("AbstractClassNameCheck");
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(ClassAndPropertiesJavadocScraper.class.getName());
        final DefaultConfiguration defaultConfiguration =
                new DefaultConfiguration("configuration");
        final DefaultConfiguration treeWalkerConfig =
                new DefaultConfiguration(TreeWalker.class.getName());
        defaultConfiguration.addProperty("charset", StandardCharsets.UTF_8.name());
        defaultConfiguration.addChild(treeWalkerConfig);
        treeWalkerConfig.addChild(scraperCheckConfig);
        try {
            checker.configure(defaultConfiguration);
            final List<File> filesToProcess = List.of(new File(modulePath));
            checker.process(filesToProcess);
            checker.destroy();
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("fail", ex);
        }

        final Map<String, DetailNode> settersJavadocs = ClassAndPropertiesJavadocScraper
                .getModulePropertySetterJavadocs();
        final Object instance = SiteUtil.getModuleInstance("AbstractClassNameCheck");
        final Class<?> clss = instance.getClass();
        final Set<String> properties = getProperties(clss);
        fixCapturedProperties("AbstractClassNameCheck", instance, clss, properties);

        // TODO: It's good first to match the properties to the setters
        for (Map.Entry<String, DetailNode> entry : settersJavadocs.entrySet()) {
            final StringBuilder sb = new StringBuilder(128);
            // Use block comment to get description
            // BLOCK_COMMENT_BEGIN -> COMMENT_CONTENT -> JAVADOC -> N children
            // Skip 2 first children - NEWLINE and LEADING_ASTERISK
            // Start collecting children
            // stop when we see that next 4 children are NEWLINE, LEADING_ASTERISK, NEWLINE, LEADING_ASTERISK
            final DetailNode javadoc = entry.getValue();
            // First two children are always NEWLINE and LEADING_ASTERISK
            // TODO: Maybe it's better to skip elements until we meet a JavadocTokenTypes.TEXT
            final Stack<DetailNode> stack = new Stack<>();
            final DetailNode[] children = javadoc.getChildren();
            // iterate over children and create new array with item
            final List<DetailNode> setterDescriptionChildren = new ArrayList<>();
            for (int i = 0; i < children.length; i++) {
                if (i + 4 < children.length
                        && children[i].getType() == JavadocTokenTypes.NEWLINE
                        && children[i + 1].getType() == JavadocTokenTypes.LEADING_ASTERISK
                        && children[i + 2].getType() == JavadocTokenTypes.NEWLINE
                        && children[i + 3].getType() == JavadocTokenTypes.LEADING_ASTERISK) {
                    break;
                }
                setterDescriptionChildren.add(children[i]);
            }
//            for (int i = children.length; i >= 2; i--) {
//                stack.push(children[i - 1]);
//            }
            for (int i = setterDescriptionChildren.size() - 1; i >= 0; i--) {
                stack.push(setterDescriptionChildren.get(i));
            }
            // Perform DFS traversal on children
            final StringBuilder description = new StringBuilder(128);
            while (!stack.isEmpty()) {
                final DetailNode node = stack.pop();
                final DetailNode[] nodeChildren = node.getChildren();
                for (int i = nodeChildren.length - 1; i >= 0; i--) {
                    stack.push(nodeChildren[i]);
                }
                if (node.getType() == JavadocTokenTypes.TEXT) {
                    description.append(node.getText());
                }
            }
            final String descriptionString = description.toString().trim();
            System.out.println(descriptionString);
        }
        sink.table();
        xdocSink.setInsertNewline(false);
        sink.tableRows(null, false);
        sink.rawText(INDENT_LEVEL_12);
        writeTableHeader(sink);
        sink.rawText(INDENT_LEVEL_10);
        // TODO: Insert rows here
        sink.tableRows_();
        sink.table_();
        xdocSink.setInsertNewline(true);
    }

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

    private static void fixCapturedProperties(String moduleName, Object instance, Class<?> clss,
            Set<String> properties) {
        // remove global properties that don't need documentation
        if (hasParentModule(moduleName)) {
            if (AbstractJavadocCheck.class.isAssignableFrom(clss)) {
                properties.removeAll(JAVADOC_CHECK_PROPERTIES);

                // override
                properties.add("violateExecutionOnNonTightHtml");
            }
            else if (AbstractCheck.class.isAssignableFrom(clss)) {
                properties.removeAll(CHECK_PROPERTIES);
            }
        }
        if (AbstractFileSetCheck.class.isAssignableFrom(clss)) {
            properties.removeAll(FILESET_PROPERTIES);

            // override
            properties.add("fileExtensions");
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

    private static boolean hasParentModule(String moduleName) {
        boolean result = true;

        for (String find : FILESET_LIST) {
            if (find.contains(moduleName)) {
                result = false;
                break;
            }
        }

        return result;
    }

    private String getSinceVersion(DetailAST blockCommentAst) {
        // TODO: JavadocUtil::getJavadocTags is probably better here but I haven't figured out how
        // to get a TextBlock yet.
        final String text = ((DetailAstImpl) blockCommentAst).getFirstChild().getText();
        final Pattern sinceVersionPattern = Pattern.compile("@since ((\\d+\\.)+\\d+)\n");
        final Matcher matcher = sinceVersionPattern.matcher(text);
        String sinceVersion = "undefined";
        if (matcher.find()) {
            sinceVersion = matcher.group(1);
        }
        return sinceVersion;
    }

    /**
     * Writes the table header with the 5 columns - name, description, type, default value, since.
     *
     * @param sink sink to write to.
     */
    private static void writeTableHeader(Sink sink) {
        sink.tableRow();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("name");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("description");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("type");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("default value");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableHeaderCell();
        sink.text("since");
        sink.tableHeaderCell_();
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow_();
    }

    /**
     * Writes a row of the table with the given property details - name, description, type, default
     * value, since.
     *
     * @param sink sink to write to.
     * @param property property details to write.
     */
    private static void writePropertyRow(Sink sink, ModulePropertyDetails property) {
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getName());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getDescription());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getType());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getDefaultValue());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_14);
        sink.tableCell();
        sink.text(property.getSince());
        sink.tableCell_();
        sink.rawText(INDENT_LEVEL_12);
        sink.tableRow_();
    }
}
