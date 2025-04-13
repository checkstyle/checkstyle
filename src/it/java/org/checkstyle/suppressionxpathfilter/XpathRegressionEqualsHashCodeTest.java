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
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck;

public class XpathRegressionEqualsHashCodeTest extends AbstractXpathTestSupport {
    @Override
    protected String getCheckName() {
        return EqualsHashCodeCheck.class.getSimpleName();
    }

    @Test
    public void testEqualsOnly() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEqualsHashCodeEqualsOnly.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(EqualsHashCodeCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(EqualsHashCodeCheck.class,
                    EqualsHashCodeCheck.MSG_KEY_HASHCODE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeEqualsOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeEqualsOnly']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeEqualsOnly']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testHashCodeOnly() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEqualsHashCodeHashCodeOnly.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(EqualsHashCodeCheck.class);

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(EqualsHashCodeCheck.class,
                    EqualsHashCodeCheck.MSG_KEY_EQUALS),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeHashCodeOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='hashCode']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeHashCodeOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='hashCode']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeHashCodeOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='hashCode']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testNestedCase() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathEqualsHashCodeNestedCase.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(EqualsHashCodeCheck.class);

        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(EqualsHashCodeCheck.class,
                    EqualsHashCodeCheck.MSG_KEY_HASHCODE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeNestedCase']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeNestedCase']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeNestedCase']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
