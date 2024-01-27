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

import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck;

public class XpathRegressionMethodLengthTest extends AbstractXpathTestSupport {

    private final String checkName = MethodLengthCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMethodLengthOne.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(MethodLengthCheck.class);
        moduleConfig.addProperty("max", "10");

        final String[] expectedViolations = {
            "4:5: " + getCheckMessage(MethodLengthCheck.class, MSG_KEY,
                    11, 10, "SuppressionXpathRegressionMethodLengthOne"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthOne']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthOne']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthOne']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthOne']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthOne']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthOne']]"
                        + "/MODIFIERS/LITERAL_PROTECTED"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMethodLengthTwo.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(MethodLengthCheck.class);
        moduleConfig.addProperty("max", "5");
        moduleConfig.addProperty("countEmpty", "false");
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolations = {
            "15:5: " + getCheckMessage(MethodLengthCheck.class, MSG_KEY, 6, 5, "methodOne"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodOne']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodOne']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodOne']]"
                        + "/MODIFIERS/LITERAL_PROTECTED"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionMethodLengthThree.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(MethodLengthCheck.class);
        moduleConfig.addProperty("max", "1");
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolations = {
            "7:5: " + getCheckMessage(MethodLengthCheck.class, MSG_KEY, 3, 1, "methodTwo"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodTwo']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodTwo']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMethodLengthThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodTwo']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }
}
