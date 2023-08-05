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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
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

    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        // until https://github.com/checkstyle/checkstyle/issues/13426
        if (!(sink instanceof XdocSink)) {
            throw new MacroExecutionException("Expected Sink to be an XdocSink.");
        }
        final String modulePath = (String) request.getParameter("modulePath");
        final XdocSink xdocSink = (XdocSink) sink;

        PropertiesJavadocScraper.clearModulePropertySetterJavadocs();
        PropertiesJavadocScraper.setModuleName("AbstractClassNameCheck");
        final Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        final DefaultConfiguration scraperCheckConfig =
                        new DefaultConfiguration(PropertiesJavadocScraper.class.getName());
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

        final Map<String, DetailAST> settersJavadocs = PropertiesJavadocScraper
                .getModulePropertySetterJavadocs();
        final Object instance = SiteUtil.getModuleInstance("AbstractClassNameCheck");
        final Set<String> properties = getPropertiesFromInstanceClass(instance.getClass());
        System.out.println("asd");
        // Use block comment to get description
        // BLOCK_COMMENT_BEGIN -> COMMENT_CONTENT -> JAVADOC -> N children
        // Skip 2 first children - NEWLINE and LEADING_ASTERISK
        // Start collecting children
        // stop when we see that next 4 children are NEWLINE, LEADING_ASTERISK, NEWLINE, LEADING_ASTERISK
        // Perform DFS on the collected children to get the description

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

    private static Set<String> getPropertiesFromInstanceClass(Class<?> clss) {
        final Set<String> result = new TreeSet<>();
        final PropertyDescriptor[] map = PropertyUtils.getPropertyDescriptors(clss);

        for (PropertyDescriptor propertyDescriptor : map) {
            if (propertyDescriptor.getWriteMethod() != null) {
                result.add(propertyDescriptor.getName());
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
