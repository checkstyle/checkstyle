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
import com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck;

public class XpathRegressionDeclarationOrderTest extends XpathTestSupport {

    @Test
    public void testOne() throws Exception {
        final String checkName = DeclarationOrderCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionDeclarationOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DeclarationOrderCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(DeclarationOrderCheck.class,
                DeclarationOrderCheck.MSG_ACCESS),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[@text='SuppressionXpathRegressionDeclarationOne']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='name']",
                "/CLASS_DEF[@text='SuppressionXpathRegressionDeclarationOne']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='name']/MODIFIERS",
                "/CLASS_DEF[@text='SuppressionXpathRegressionDeclarationOne']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='name']/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final String checkName = DeclarationOrderCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionDeclarationTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(DeclarationOrderCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(DeclarationOrderCheck.class,
                DeclarationOrderCheck.MSG_STATIC),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[@text='SuppressionXpathRegressionDeclarationTwo']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='MAX']",
                "/CLASS_DEF[@text='SuppressionXpathRegressionDeclarationTwo']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='MAX']/MODIFIERS",
                "/CLASS_DEF[@text='SuppressionXpathRegressionDeclarationTwo']"
                        + "/OBJBLOCK/VARIABLE_DEF[@text='MAX']/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
