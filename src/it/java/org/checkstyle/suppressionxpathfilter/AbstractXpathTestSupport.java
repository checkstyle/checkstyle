///
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

package org.checkstyle.suppressionxpathfilter;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.checkstyle.base.AbstractCheckstyleModuleTestSupport;
import org.junit.jupiter.api.io.TempDir;

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
            Pattern.compile("(\\d+):(\\d+):");

    /**
     * The temporary folder to hold intermediate files.
     */
    @TempDir
    public File temporaryFolder;

    /**
     * Returns name of the check.
     *
     * @return name of the check as a String.
     */
    protected abstract String getCheckName();

    @Override
    protected String getPackageLocation() {
        final String subpackage = getCheckName().toLowerCase(Locale.ENGLISH)
                .replace("check", "");
        return "org/checkstyle/suppressionxpathfilter/" + subpackage;
    }

    /**
     * Returns a list of XPath queries to locate the violation nodes in a Java file.
     *
     * @param fileToProcess the Java file to be processed. {@link File} type object.
     * @param position the position of violation in the file. {@link ViolationPosition} object.
     * @return a list of strings containing XPath queries for locating violation nodes.
     * @throws Exception can throw exceptions while accessing file contents.
     */
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

    /**
     * Verify generated XPath queries by comparing with expected queries.
     *
     * @param generatedXpathQueries a list of generated XPath queries.
     * @param expectedXpathQueries a list of expected XPath queries.
     */
    private static void verifyXpathQueries(List<String> generatedXpathQueries,
                                           List<String> expectedXpathQueries) {
        assertWithMessage("Generated queries do not match expected ones")
            .that(generatedXpathQueries)
            .isEqualTo(expectedXpathQueries);
    }

    /**
     * Returns the path to the generated Suppressions XPath config file.
     * This method creates a Suppressions config file containing the Xpath queries for
     * any check and returns the path to that file.
     *
     * @param checkName the name of the check that is run.
     * @param xpathQueries a list of generated XPath queries for violations in a file.
     * @return path(String) to the generated Suppressions config file.
     * @throws Exception can throw exceptions when writing/creating the config file.
     */
    private String createSuppressionsXpathConfigFile(String checkName,
                                                     List<String> xpathQueries)
            throws Exception {
        final String uniqueFileName =
                "suppressions_xpath_config_" + UUID.randomUUID() + ".xml";
        final File suppressionsXpathConfigPath = new File(temporaryFolder, uniqueFileName);
        try (Writer bw = Files.newBufferedWriter(suppressionsXpathConfigPath.toPath(),
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

    /**
     * Returns the config {@link DefaultConfiguration} for the created Suppression XPath filter.
     *
     * @param checkName the name of the check that is run.
     * @param xpathQueries a list of generated XPath queries for violations in a file.
     * @return the default config for Suppressions XPath filter based on check and xpath queries.
     * @throws Exception can throw exceptions when creating config.
     */
    private DefaultConfiguration createSuppressionXpathFilter(String checkName,
                                           List<String> xpathQueries) throws Exception {
        final DefaultConfiguration suppressionXpathFilterConfig =
                createModuleConfig(SuppressionXpathFilter.class);
        suppressionXpathFilterConfig.addProperty("file",
                createSuppressionsXpathConfigFile(checkName, xpathQueries));

        return suppressionXpathFilterConfig;
    }

    /**
     * Extract line no and column nos from String of expected violations.
     *
     * @param expectedViolations the expected violations generated for the check.
     * @return instance of type {@link ViolationPosition} which contains the line and column nos.
     */
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
     *
     * @param moduleConfig module configuration.
     * @param fileToProcess input class file.
     * @param expectedViolation expected violation message.
     * @param expectedXpathQueries expected generated xpath queries.
     * @throws Exception if an error occurs
     * @throws IllegalArgumentException if length of expectedViolation is more than 1
     */
    protected void runVerifications(DefaultConfiguration moduleConfig,
                                  File fileToProcess,
                                  String[] expectedViolation,
                                  List<String> expectedXpathQueries) throws Exception {
        if (expectedViolation.length != 1) {
            throw new IllegalArgumentException(
                    "Expected violations should contain exactly one element."
                            + " Multiple violations are not supported."
            );
        }

        final ViolationPosition position =
                extractLineAndColumnNumber(expectedViolation);
        final List<String> generatedXpathQueries =
                generateXpathQueries(fileToProcess, position);

        final DefaultConfiguration treeWalkerConfigWithXpath =
                createModuleConfig(TreeWalker.class);
        treeWalkerConfigWithXpath.addChild(moduleConfig);
        treeWalkerConfigWithXpath.addChild(createSuppressionXpathFilter(moduleConfig.getName(),
                generatedXpathQueries));

        final Integer[] warnList = getLinesWithWarn(fileToProcess.getPath());
        verify(moduleConfig, fileToProcess.getPath(), expectedViolation, warnList);
        verifyXpathQueries(generatedXpathQueries, expectedXpathQueries);
        verify(treeWalkerConfigWithXpath, fileToProcess.getPath(), CommonUtil.EMPTY_STRING_ARRAY);
    }

    private static final class ViolationPosition {
        private final int violationLineNumber;
        private final int violationColumnNumber;

        /**
         * Constructor of the class.
         *
         * @param violationLineNumber line no of the violation produced for the check.
         * @param violationColumnNumber column no of the violation produced for the check.
         */
        private ViolationPosition(int violationLineNumber,
                              int violationColumnNumber) {
            this.violationLineNumber = violationLineNumber;
            this.violationColumnNumber = violationColumnNumber;
        }
    }
}
