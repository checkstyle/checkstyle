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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck;

public class XpathRegressionFinalLocalVariableTest extends AbstractXpathTestSupport {

    private final String checkName = FinalLocalVariableCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable1.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expectedViolation = {
            "5:13: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "x"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalLocalVariable1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod']]"
                        + "/SLIST/VARIABLE_DEF/IDENT[@text='x']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable2.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expectedViolation = {
            "6:17: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "x"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalLocalVariable2']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method2']]/SLIST/"
                    + "LITERAL_FOR/SLIST/VARIABLE_DEF/IDENT[@text='x']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable3.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expectedViolation = {
            "8:25: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "foo"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalLocalVariable3']]"
                    + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/"
                    + "LITERAL_SWITCH/CASE_GROUP/SLIST/VARIABLE_DEF/IDENT[@text='foo']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable4.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expectedViolation = {
            "7:17: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "shouldBeFinal"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalLocalVariable4']]"
                    + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test1']]"
                    + "/SLIST/VARIABLE_DEF/IDENT[@text='shouldBeFinal']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable5.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);
        moduleConfig.addProperty("tokens", "PARAMETER_DEF");

        final String[] expectedViolation = {
            "4:28: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "aArg"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalLocalVariable5']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='aArg']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSix() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable6.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);
        moduleConfig.addProperty("validateEnhancedForLoopVariable", "true");

        final String[] expectedViolation = {
            "8:20: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "a"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathFinalLocalVariable6']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method1']]"
                    + "/SLIST/LITERAL_FOR/FOR_EACH_CLAUSE/VARIABLE_DEF/IDENT[@text='a']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSeven() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable7.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);
        moduleConfig.addProperty("tokens", "PARAMETER_DEF");

        final String[] expectedViolation = {
            "4:39: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "a"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathFinalLocalVariable7']]"
                    + "/OBJBLOCK/CTOR_DEF[./IDENT"
                    + "[@text='InputXpathFinalLocalVariable7']]"
                    + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='a']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEight() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable8.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expectedViolation = {
            "6:17: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "start"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalLocalVariable8']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='checkCodeBlock']]"
                    + "/SLIST/LITERAL_TRY/SLIST/VARIABLE_DEF/IDENT[@text='start']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testNine() throws Exception {
        final File fileToProcess = new
                File(getPath("InputXpathFinalLocalVariable9.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(FinalLocalVariableCheck.class);

        final String[] expectedViolation = {
            "11:25: " + getCheckMessage(FinalLocalVariableCheck.class,
                    FinalLocalVariableCheck.MSG_KEY, "body"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathFinalLocalVariable9']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='checkCodeBlock']]/SLIST/LITERAL_TRY"
                    + "/SLIST/LITERAL_IF/LITERAL_ELSE/LITERAL_IF"
                    + "/SLIST/VARIABLE_DEF/IDENT[@text='body']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
