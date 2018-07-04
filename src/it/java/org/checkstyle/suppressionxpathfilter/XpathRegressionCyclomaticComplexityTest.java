////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck;

public class XpathRegressionCyclomaticComplexityTest extends AbstractXpathTestSupport {

    @Test
    public void testOne() throws Exception {
        final String checkName = CyclomaticComplexityCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionCyclomaticOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CyclomaticComplexityCheck.class);
        moduleConfig.addAttribute("max", "0");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 2, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionCyclomaticOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']",
            "/CLASS_DEF[@text='SuppressionXpathRegressionCyclomaticOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/MODIFIERS",
            "/CLASS_DEF[@text='SuppressionXpathRegressionCyclomaticOne']/OBJBLOCK"
                + "/METHOD_DEF[@text='test']/MODIFIERS/LITERAL_PUBLIC"
                );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final String checkName = CyclomaticComplexityCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionCyclomaticTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CyclomaticComplexityCheck.class);
        moduleConfig.addAttribute("max", "0");

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 5, 0),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionCyclomaticTwo']/OBJBLOCK"
                    + "/METHOD_DEF[@text='foo2']",
            "/CLASS_DEF[@text='SuppressionXpathRegressionCyclomaticTwo']/OBJBLOCK"
                    + "/METHOD_DEF[@text='foo2']/MODIFIERS",
            "/CLASS_DEF[@text='SuppressionXpathRegressionCyclomaticTwo']/OBJBLOCK"
                    + "/METHOD_DEF[@text='foo2']/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
