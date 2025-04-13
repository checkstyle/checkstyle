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
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;

public class XpathRegressionMultipleVariableDeclarationsTest extends AbstractXpathTestSupport {

    private final String checkName = MultipleVariableDeclarationsCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testCommaSeparator() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathMultipleVariableDeclarationsCommaSeparator.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE_COMMA),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]/TYPE/LITERAL_INT",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMultipleVariableDeclarations() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathMultipleVariableDeclarations.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleVariableDeclarationsCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
