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
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;

public class XpathRegressionMultipleVariableDeclarationsTest extends AbstractXpathRegressionTest {

    @Test
    public void testOne() throws Exception {
        final String checkName = MultipleVariableDeclarationsCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionMultipleVariableDeclarationOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE_COMMA),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                + "/VARIABLE_DEF[@text='i']",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='i']/MODIFIERS",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='i']/TYPE",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='i']/TYPE/LITERAL_INT",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='j']",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='j']/MODIFIERS",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='j']/TYPE",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationOne']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='j']/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, checkName, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final String checkName = MultipleVariableDeclarationsCheck.class.getSimpleName();
        final File fileToProcess =
                new File(getPath(checkName,
                        "SuppressionXpathRegressionMultipleVariableDeclarationTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationTwo']/OBJBLOCK"
                + "/VARIABLE_DEF[@text='i1']",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationTwo']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='i1']/MODIFIERS",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationTwo']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='i1']/TYPE",
            "/CLASS_DEF[@text='SuppressionXpathRegressionMultipleVariableDeclarationTwo']/OBJBLOCK"
                    + "/VARIABLE_DEF[@text='i1']/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, checkName, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
