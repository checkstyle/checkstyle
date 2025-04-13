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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck;

public class XpathRegressionModifierOrderTest extends AbstractXpathTestSupport {

    private static final Class<ModifierOrderCheck> CLAZZ = ModifierOrderCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathModifierOrderMethod.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);

        final String[] expectedViolation = {
            "4:13: " + getCheckMessage(CLAZZ,
                    ModifierOrderCheck.MSG_ANNOTATION_ORDER, "@MethodAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathModifierOrderMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/MODIFIERS"
                        + "/ANNOTATION[./IDENT[@text='MethodAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathModifierOrderMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/MODIFIERS"
                        + "/ANNOTATION[./IDENT[@text='MethodAnnotation']]/AT");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathModifierOrderVariable.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);

        final String[] expectedViolation = {
            "3:12: " + getCheckMessage(CLAZZ,
                    ModifierOrderCheck.MSG_MODIFIER_ORDER, "private"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathModifierOrderVariable']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='var']]/MODIFIERS/LITERAL_PRIVATE");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testAnnotation() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathModifierOrderAnnotation.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(CLAZZ);

        final String[] expectedViolation = {
            "3:8: " + getCheckMessage(CLAZZ,
                    ModifierOrderCheck.MSG_ANNOTATION_ORDER, "@InterfaceAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT"
                        + "[@text='InputXpathModifierOrderAnnotation']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='InterfaceAnnotation']]",
                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT"
                        + "[@text='InputXpathModifierOrderAnnotation']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='InterfaceAnnotation']]/AT");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
