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
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck;

public class XpathRegressionUnusedLocalVariableTest extends AbstractXpathTestSupport {
    private final String checkName = UnusedLocalVariableCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathUnusedLocalVariableOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedLocalVariableCheck.class);

        final String[] expectedViolation = {
            "6:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE, "a"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[./IDENT[@text='a']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='a']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='a']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='a']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathUnusedLocalVariableTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnusedLocalVariableCheck.class);
        moduleConfig.addProperty("allowUnnamedVariables", "false");

        final String[] expectedViolation = {
            "10:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE, "b"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableTwo']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[./IDENT[@text='b']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableTwo']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='b']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableTwo']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='b']]/TYPE", "/COMPILATION_UNIT/CLASS_DEF["
                        + "./IDENT[@text='InputXpathUnusedLocalVariableTwo']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='b']]/TYPE/LITERAL_INT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }
}
