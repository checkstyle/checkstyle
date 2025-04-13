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

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck;

public class XpathRegressionIndentationTest extends AbstractXpathTestSupport {
    private final String checkName = IndentationCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIndentationDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_ERROR, "method def modifier", 0, 4),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationDefault']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]",

             "/COMPILATION_UNIT/CLASS_DEF"
                     + "[./IDENT[@text='InputXpathIndentationDefault']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationDefault']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]/TYPE",

            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationDefault']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]/TYPE/LITERAL_VOID"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testBasicOffset() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIndentationBasicOffset.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);
        moduleConfig.addProperty("arrayInitIndent", "4");
        moduleConfig.addProperty("basicOffset", "10");
        moduleConfig.addProperty("braceAdjustment", "0");
        moduleConfig.addProperty("caseIndent", "4");
        moduleConfig.addProperty("forceStrictCondition", "false");
        moduleConfig.addProperty("lineWrappingIndentation", "4");
        moduleConfig.addProperty("tabWidth", "4");
        moduleConfig.addProperty("throwsIndent", "4");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_ERROR, "method def modifier", 4, 10),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationBasicOffset']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",

            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationBasicOffset']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",

            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationBasicOffset']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",

            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationBasicOffset']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_VOID"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testCaseIndent() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIndentationSwitchCase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);
        moduleConfig.addProperty("arrayInitIndent", "4");
        moduleConfig.addProperty("basicOffset", "4");
        moduleConfig.addProperty("braceAdjustment", "0");
        moduleConfig.addProperty("caseIndent", "4");
        moduleConfig.addProperty("forceStrictCondition", "false");
        moduleConfig.addProperty("lineWrappingIndentation", "4");
        moduleConfig.addProperty("tabWidth", "4");
        moduleConfig.addProperty("throwsIndent", "4");

        final String[] expectedViolation = {
            "7:9: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_CHILD_ERROR, "case", 8, 12),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationSwitchCase']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/"
                    + "CASE_GROUP",

            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationSwitchCase']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/"
                    + "CASE_GROUP/LITERAL_CASE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testLambdaOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIndentationLambdaOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);
        moduleConfig.addProperty("arrayInitIndent", "4");
        moduleConfig.addProperty("basicOffset", "4");
        moduleConfig.addProperty("braceAdjustment", "0");
        moduleConfig.addProperty("caseIndent", "4");
        moduleConfig.addProperty("forceStrictCondition", "false");
        moduleConfig.addProperty("lineWrappingIndentation", "4");
        moduleConfig.addProperty("tabWidth", "4");
        moduleConfig.addProperty("throwsIndent", "4");

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_ERROR, "(", 8, 12),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationLambdaOne"
                    + "']]/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF"
                    + "[./IDENT[@text='getA']]/ASSIGN/LAMBDA/LPAREN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testLambdaTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIndentationLambdaTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);
        moduleConfig.addProperty("arrayInitIndent", "4");
        moduleConfig.addProperty("basicOffset", "4");
        moduleConfig.addProperty("braceAdjustment", "0");
        moduleConfig.addProperty("caseIndent", "4");
        moduleConfig.addProperty("forceStrictCondition", "false");
        moduleConfig.addProperty("lineWrappingIndentation", "4");
        moduleConfig.addProperty("tabWidth", "4");
        moduleConfig.addProperty("throwsIndent", "4");

        final String[] expectedViolation = {
            "14:15: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_CHILD_ERROR_MULTI, "block", 14, "12, 16"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationLambdaTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF["
                    + "./IDENT[@text='div']]/ASSIGN/LAMBDA/SLIST/LITERAL_RETURN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testIfWithNoCurlies() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathIndentationIfWithoutCurly.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);

        moduleConfig.addProperty("arrayInitIndent", "4");
        moduleConfig.addProperty("basicOffset", "4");
        moduleConfig.addProperty("braceAdjustment", "0");
        moduleConfig.addProperty("caseIndent", "4");
        moduleConfig.addProperty("forceStrictCondition", "false");
        moduleConfig.addProperty("lineWrappingIndentation", "4");
        moduleConfig.addProperty("tabWidth", "4");
        moduleConfig.addProperty("throwsIndent", "4");

        final String[] expectedViolation = {
            "8:9: " + getCheckMessage(IndentationCheck.class,
                IndentationCheck.MSG_CHILD_ERROR, "if", 8, 12),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationIfWithoutCurly']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/EXPR/"
                    + "METHOD_CALL/IDENT[@text='e']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testElseWithNoCurlies() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathIndentationElseWithoutCurly.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);

        moduleConfig.addProperty("arrayInitIndent", "4");
        moduleConfig.addProperty("basicOffset", "4");
        moduleConfig.addProperty("braceAdjustment", "0");
        moduleConfig.addProperty("caseIndent", "4");
        moduleConfig.addProperty("forceStrictCondition", "false");
        moduleConfig.addProperty("lineWrappingIndentation", "4");
        moduleConfig.addProperty("tabWidth", "4");
        moduleConfig.addProperty("throwsIndent", "4");

        final String[] expectedViolation = {
            "12:9: " + getCheckMessage(IndentationCheck.class,
                IndentationCheck.MSG_CHILD_ERROR, "else", 8, 12),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathIndentationElseWithoutCurly']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/LITERAL_ELSE"
                    + "/EXPR/METHOD_CALL/IDENT[@text='exp']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
