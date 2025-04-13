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
import com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck;

public class XpathRegressionInterfaceMemberImpliedModifierTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return InterfaceMemberImpliedModifierCheck.class.getSimpleName();
    }

    @Test
    public void testField() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathInterfaceMemberImpliedModifierField.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(InterfaceMemberImpliedModifierCheck.class,
                InterfaceMemberImpliedModifierCheck.MSG_KEY, "final"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='str']]",

            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='str']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='str']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathInterfaceMemberImpliedModifierMethod.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(InterfaceMemberImpliedModifierCheck.class,
                InterfaceMemberImpliedModifierCheck.MSG_KEY, "public"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='setData']]",

            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='setData']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='setData']]"
                + "/MODIFIERS/ABSTRACT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInner() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathInterfaceMemberImpliedModifierInner.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(InterfaceMemberImpliedModifierCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(InterfaceMemberImpliedModifierCheck.class,
                InterfaceMemberImpliedModifierCheck.MSG_KEY, "static"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierInner']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Data']]",

            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierInner']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Data']]"
                + "/MODIFIERS",

            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceMemberImpliedModifierInner']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Data']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
