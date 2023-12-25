///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck;

public class XpathRegressionReturnCountTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return ReturnCountCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionReturnCount1.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ReturnCountCheck.class);

        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(ReturnCountCheck.class,
                        ReturnCountCheck.MSG_KEY_VOID, 5, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]/TYPE/LITERAL_VOID"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionReturnCount1.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ReturnCountCheck.class);

        moduleConfig.addProperty("maxForVoid", "3");

        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(ReturnCountCheck.class,
                        ReturnCountCheck.MSG_KEY_VOID, 5, 3),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount1']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testVoid']]/TYPE/LITERAL_VOID"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionReturnCount2.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ReturnCountCheck.class);

        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(ReturnCountCheck.class,
                        ReturnCountCheck.MSG_KEY, 4, 2),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]/TYPE/LITERAL_BOOLEAN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionReturnCount2.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ReturnCountCheck.class);

        moduleConfig.addProperty("max", "3");

        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(ReturnCountCheck.class,
                        ReturnCountCheck.MSG_KEY, 4, 3),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount2']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testNonVoid']]/TYPE/LITERAL_BOOLEAN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionReturnCount3.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ReturnCountCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(ReturnCountCheck.class,
                        ReturnCountCheck.MSG_KEY_VOID, 5, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount3']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionReturnCount3']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount3']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionReturnCount3']]"
                + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionReturnCount3']]"
                + "/OBJBLOCK/CTOR_DEF/IDENT[@text='SuppressionXpathRegressionReturnCount3']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSix() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionReturnCount4.java")
        );

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ReturnCountCheck.class);

        final String[] expectedViolation = {
            "7:42: " + getCheckMessage(ReturnCountCheck.class,
                        ReturnCountCheck.MSG_KEY, 4, 2),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionReturnCount4']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testLambda']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='a']]/ASSIGN/LAMBDA[./IDENT[@text='i']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}

