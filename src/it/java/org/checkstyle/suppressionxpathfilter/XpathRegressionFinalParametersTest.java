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

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck;

public class XpathRegressionFinalParametersTest extends AbstractXpathTestSupport {

    private final String checkName = FinalParametersCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                  new File(getPath("SuppressionXpathRegressionFinalParameters1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalParametersCheck.class);

        final String[] expectedViolation = {
            "5:24: " + getCheckMessage(FinalParametersCheck.class, FinalParametersCheck.MSG_KEY, "argOne"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionFinalParameters1']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionFinalParameters1']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionFinalParameters1']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionFinalParameters1']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionFinalParameters1']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE/LITERAL_INT"
        );


        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionFinalParameters2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalParametersCheck.class);

        moduleConfig.addProperty("tokens", "CTOR_DEF");

        final String[] expectedViolation = {
                "5:55: " + getCheckMessage(FinalParametersCheck.class, FinalParametersCheck.MSG_KEY, "argOne"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/PARAMETERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='SuppressionXpathRegressionFinalParameters2']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionFinalParameters3.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(FinalParametersCheck.class);

        final String[] expectedViolation = {
                "5:24: " + getCheckMessage(FinalParametersCheck.class, FinalParametersCheck.MSG_KEY, "arg"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/PARAMETERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='arg']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='arg']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='arg']]/TYPE[./IDENT[@text='String']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionFinalParameters3']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='arg']]/TYPE/IDENT[@text='String']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
