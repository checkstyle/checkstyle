////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.checkstyle.base.AbstractCheckstyleModuleTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.filters.SuppressionXpathFilter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.xpath.XpathQueryGenerator;

public abstract class AbstractXpathTestSupport extends AbstractCheckstyleModuleTestSupport {

    private static final int DEFAULT_TAB_WIDTH = 4;

    private static final String DELIMITER = " | \n";

    private static final Pattern LINE_COLUMN_NUMBER_REGEX =
            Pattern.compile("([0-9]+):([0-9]+):");

    /**
     * <p>
     * The temporary folder to hold intermediate files.
     * </p>
     * <p>
     * Till https://github.com/junit-team/junit5/issues/1786
     * we need to create and clean it manually.
     * Once this issue will be resolved, it should be annotated with &#64;TempDir
     * and methods setupTemporaryFolder and removeTemporaryFolder should be dropped.
     * </p>
     */
    private Path temporaryFolder;

    protected abstract String getCheckName();

    /**
     * <p>
     * Creates temporary folder for intermediate files.
     * </p>
     *
     * @throws IOException if an IO error occurs
     */
    @BeforeEach
    public void setupTemporaryFolder() throws IOException {
        temporaryFolder = Files.createTempDirectory("xpath-test");
    }

    /**
     * <p>
     * Removes temporary folder with intermediate files.
     * </p>
     *
     * @throws IOException if an IO error occurs
     */
    @AfterEach
    public void removeTemporaryFolder() throws IOException {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(temporaryFolder)) {
            for (Path path : paths) {
                Files.delete(path);
            }
        }
        Files.delete(temporaryFolder);
    }

    @Override
    protected String getPackageLocation() {
        final String subpackage = getCheckName().toLowerCase(Locale.ENGLISH)
                .replace("check", "");
        return "org/checkstyle/suppressionxpathfilter/" + subpackage;
    }

    private static List<String> generateXpathQueries(File fileToProcess,
                                                     ViolationPosition position)
            throws Exception {
        final FileText fileText = new FileText(fileToProcess,
                StandardCharsets.UTF_8.name());
        final DetailAST rootAst = JavaParser.parseFile(fileToProcess,
                JavaParser.Options.WITH_COMMENTS);
        final XpathQueryGenerator queryGenerator = new XpathQueryGenerator(rootAst,
                position.violationLineNumber, position.violationColumnNumber,
                fileText, DEFAULT_TAB_WIDTH);

        return queryGenerator.generate();
    }

    private static void verifyXpathQueries(List<String> generatedXpathQueries,
                                           List<String> expectedXpathQueries) {
        assertEquals(expectedXpathQueries,
                generatedXpathQueries, "Generated queries do not match expected ones");
    }

    private String createSuppressionsXpathConfigFile(String checkName,
                                                     List<String> xpathQueries)
            throws Exception {
        final Path suppressionsXpathConfigPath =
                Files.createTempFile(temporaryFolder, "", "");
        try (Writer bw = Files.newBufferedWriter(suppressionsXpathConfigPath,
                StandardCharsets.UTF_8)) {
            bw.write("<?xml version=\"1.0\"?>\n");
            bw.write("<!DOCTYPE suppressions PUBLIC\n");
            bw.write("    \"-//Checkstyle//DTD SuppressionXpathFilter ");
            bw.write("Experimental Configuration 1.2//EN\"\n");
            bw.write("    \"https://checkstyle.org/dtds/");
            bw.write("suppressions_1_2_xpath_experimental.dtd\">\n");
            bw.write("<suppressions>\n");
            bw.write("   <suppress-xpath\n");
            bw.write("       checks=\"");
            bw.write(checkName);
            bw.write("\"\n");
            bw.write("       query=\"");
            bw.write(String.join(DELIMITER, xpathQueries));
            bw.write("\"/>\n");
            bw.write("</suppressions>");
        }

        return suppressionsXpathConfigPath.toString();
    }

    private DefaultConfiguration createSuppressionXpathFilter(String checkName,
                                           List<String> xpathQueries) throws Exception {
        final DefaultConfiguration suppressionXpathFilterConfig =
                createModuleConfig(SuppressionXpathFilter.class);
        suppressionXpathFilterConfig.addAttribute("file",
                createSuppressionsXpathConfigFile(checkName, xpathQueries));

        return suppressionXpathFilterConfig;
    }

    private static ViolationPosition extractLineAndColumnNumber(String... expectedViolations) {
        ViolationPosition violation = null;
        final Matcher matcher =
                LINE_COLUMN_NUMBER_REGEX.matcher(expectedViolations[0]);
        if (matcher.find()) {
            final int violationLineNumber = Integer.parseInt(matcher.group(1));
            final int violationColumnNumber = Integer.parseInt(matcher.group(2));
            violation = new ViolationPosition(violationLineNumber, violationColumnNumber);
        }
        return violation;
    }

    /**
     * Runs three verifications:
     * First one executes checker with defined module configuration and compares output with
     * expected violations.
     * Second one generates xpath queries based on violation message and compares them with expected
     * xpath queries.
     * Third one constructs new configuration with {@code SuppressionXpathFilter} using generated
     * xpath queries, executes checker and checks if no violation occurred.
     * @param moduleConfig module configuration.
     * @param fileToProcess input class file.
     * @param expectedViolations expected violation messages.
     * @param expectedXpathQueries expected generated xpath queries.
     * @throws Exception if an error occurs
     */
    protected void runVerifications(DefaultConfiguration moduleConfig,
                                  File fileToProcess,
                                  String[] expectedViolations,
                                  List<String> expectedXpathQueries) throws Exception {
        final ViolationPosition position =
                extractLineAndColumnNumber(expectedViolations);
        final List<String> generatedXpathQueries =
                generateXpathQueries(fileToProcess, position);

        final DefaultConfiguration treeWalkerConfigWithXpath =
                createModuleConfig(TreeWalker.class);
        treeWalkerConfigWithXpath.addChild(moduleConfig);
        treeWalkerConfigWithXpath.addChild(createSuppressionXpathFilter(moduleConfig.getName(),
                generatedXpathQueries));

        final Integer[] warnList = getLinesWithWarn(fileToProcess.getPath());
        verify(moduleConfig, fileToProcess.getPath(), expectedViolations, warnList);
        verifyXpathQueries(generatedXpathQueries, expectedXpathQueries);
        verify(treeWalkerConfigWithXpath, fileToProcess.getPath(), CommonUtil.EMPTY_STRING_ARRAY);
    }

    private static final class ViolationPosition {
        private final int violationLineNumber;
        private final int violationColumnNumber;

        /* package */ ViolationPosition(int violationLineNumber,
                              int violationColumnNumber) {
            this.violationLineNumber = violationLineNumber;
            this.violationColumnNumber = violationColumnNumber;
        }
    }
}
