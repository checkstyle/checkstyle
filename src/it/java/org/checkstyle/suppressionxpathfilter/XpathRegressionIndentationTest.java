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

import java.io.File;
import java.util.Arrays;
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
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionIndentationTestOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_ERROR, "method def modifier", 0, 4),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]",

             "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]/MODIFIERS",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]/TYPE",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='wrongIntend']]/TYPE/LITERAL_VOID"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testBasicOffset() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionIndentationTestTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);
        moduleConfig.addAttribute("arrayInitIndent", "4");
        moduleConfig.addAttribute("basicOffset", "10");
        moduleConfig.addAttribute("braceAdjustment", "0");
        moduleConfig.addAttribute("caseIndent", "4");
        moduleConfig.addAttribute("forceStrictCondition", "false");
        moduleConfig.addAttribute("lineWrappingIndentation", "4");
        moduleConfig.addAttribute("tabWidth", "4");
        moduleConfig.addAttribute("throwsIndent", "4");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_ERROR, "method def modifier", 4, 10),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_VOID"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testCaseIndent() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionIndentationTestThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(IndentationCheck.class);
        moduleConfig.addAttribute("arrayInitIndent", "4");
        moduleConfig.addAttribute("basicOffset", "4");
        moduleConfig.addAttribute("braceAdjustment", "0");
        moduleConfig.addAttribute("caseIndent", "4");
        moduleConfig.addAttribute("forceStrictCondition", "false");
        moduleConfig.addAttribute("lineWrappingIndentation", "4");
        moduleConfig.addAttribute("tabWidth", "4");
        moduleConfig.addAttribute("throwsIndent", "4");

        final String[] expectedViolation = {
            "7:9: " + getCheckMessage(IndentationCheck.class,
                    IndentationCheck.MSG_CHILD_ERROR, "case", 8, 12),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestThree']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/"
                    + "CASE_GROUP",

            "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionIndentationTestThree']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_SWITCH/"
                    + "CASE_GROUP/LITERAL_CASE"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
