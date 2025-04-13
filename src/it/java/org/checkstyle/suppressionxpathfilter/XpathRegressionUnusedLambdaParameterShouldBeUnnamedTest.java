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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedLambdaParameterShouldBeUnnamedCheck;

public class XpathRegressionUnusedLambdaParameterShouldBeUnnamedTest
                    extends AbstractXpathTestSupport {

    private final String checkName = UnusedLambdaParameterShouldBeUnnamedCheck.class
                                                .getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathUnusedLambdaParameterShouldBeUnnamedSimple.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedLambdaParameterShouldBeUnnamedCheck.class);

        final String[] expectedViolation = {
            "9:41: " + getCheckMessage(UnusedLambdaParameterShouldBeUnnamedCheck.class,
                    UnusedLambdaParameterShouldBeUnnamedCheck.MSG_UNUSED_LAMBDA_PARAMETER,
                    "x"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT["
                + "@text='f']]/ASSIGN/LAMBDA/PARAMETERS",
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF["
                + "./IDENT[@text='f']]/ASSIGN/LAMBDA/PARAMETERS/PARAMETER_DEF[./IDENT[@text='x']]",
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f']]/ASSIGN/LAMBDA/PARAMETERS"
                + "/PARAMETER_DEF[./IDENT[@text='x']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f']]/ASSIGN/LAMBDA/PARAMETERS/"
                + "PARAMETER_DEF[./IDENT[@text='x']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF["
                + "./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f']]/ASSIGN/LAMBDA/PARAMETERS/PARAMETER_DEF/IDENT[@text='x']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testNested() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathUnusedLambdaParameterShouldBeUnnamedNested.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedLambdaParameterShouldBeUnnamedCheck.class);

        final String[] expectedViolation = {
            "10:45: " + getCheckMessage(UnusedLambdaParameterShouldBeUnnamedCheck.class,
                    UnusedLambdaParameterShouldBeUnnamedCheck.MSG_UNUSED_LAMBDA_PARAMETER,
                    "y"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f1']]/ASSIGN/LAMBDA/SLIST/VARIABLE_DEF[./IDENT[@text='f']]"
                + "/ASSIGN/LAMBDA/PARAMETERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f1']]/ASSIGN/LAMBDA/SLIST/VARIABLE_DEF[./IDENT[@text='f']]"
                + "/ASSIGN/LAMBDA/PARAMETERS/PARAMETER_DEF[./IDENT[@text='y']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f1']]/ASSIGN/LAMBDA/SLIST/VARIABLE_DEF[./IDENT[@text='f']]"
                + "/ASSIGN/LAMBDA/PARAMETERS/PARAMETER_DEF[./IDENT[@text='y']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT"
                + "[@text='f1']]/ASSIGN/LAMBDA/SLIST/VARIABLE_DEF[./IDENT[@text='f']]/ASSIGN"
                + "/LAMBDA/PARAMETERS/PARAMETER_DEF[./IDENT[@text='y']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnusedLambdaParameterShouldBeUnnamedNested']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF["
                + "./IDENT[@text='f1']]/ASSIGN/LAMBDA/SLIST/VARIABLE_DEF["
                + "./IDENT[@text='f']]/ASSIGN/LAMBDA/PARAMETERS/PARAMETER_DEF/IDENT[@text='y']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
