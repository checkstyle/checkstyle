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
import com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck;

public class XpathRegressionVariableDeclarationUsageDistanceTest extends AbstractXpathTestSupport {
    private final String checkName = VariableDeclarationUsageDistanceCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionVariableDeclarationUsageDistance1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        moduleConfig.addAttribute("allowedDistance", "1");
        moduleConfig.addAttribute("ignoreVariablePattern", "");
        moduleConfig.addAttribute("validateBetweenScopes", "true");
        moduleConfig.addAttribute("ignoreFinal", "false");

        final String[] expectedViolation = {
            "26:9: " + getCheckMessage(VariableDeclarationUsageDistanceCheck.class,
                    VariableDeclarationUsageDistanceCheck.MSG_KEY, "temp", 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance1']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='temp']]",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance1']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='temp']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance1']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='temp']]/TYPE",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance1']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='temp']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionVariableDeclarationUsageDistance2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(VariableDeclarationUsageDistanceCheck.class);

        moduleConfig.addAttribute("allowedDistance", "1");
        moduleConfig.addAttribute("ignoreVariablePattern", "");
        moduleConfig.addAttribute("validateBetweenScopes", "true");
        moduleConfig.addAttribute("ignoreFinal", "false");

        final String[] expectedViolation = {
            "25:9: " + getCheckMessage(VariableDeclarationUsageDistanceCheck.class,
                    VariableDeclarationUsageDistanceCheck.MSG_KEY, "count", 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod2']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='count']]",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod2']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='count']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod2']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='count']]/TYPE",
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionVariableDeclarationUsageDistance2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod2']]"
                        + "/SLIST/VARIABLE_DEF[./IDENT[@text='count']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
