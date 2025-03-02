///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck;

public class XpathRegressionConstructorsDeclarationGroupingTest extends AbstractXpathTestSupport {

    private static Class<ConstructorsDeclarationGroupingCheck> clazz =
            ConstructorsDeclarationGroupingCheck.class;

    @Override
    protected String getCheckName() {
        return clazz.getSimpleName();
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathConstructorsDeclarationGroupingClass.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "10:5: " + getCheckMessage(clazz,
                    ConstructorsDeclarationGroupingCheck.MSG_KEY, 6),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingClass']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingClass']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingClass']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingClass']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingClass']]"
                        + "/OBJBLOCK/CTOR_DEF/IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingClass']"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathConstructorsDeclarationGroupingEnum.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "12:5: " + getCheckMessage(clazz,
                    ConstructorsDeclarationGroupingCheck.MSG_KEY, 8),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingEnum']]"
                        + "/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='InputXpathConstructorsDeclarationGroupingEnum']]",

                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingEnum']]"
                        + "/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='InputXpathConstructorsDeclarationGroupingEnum']]"
                        + "/MODIFIERS",

                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingEnum']]"
                        + "/OBJBLOCK/CTOR_DEF/IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingEnum']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testRecords() throws Exception {
        final File fileToProcess = new File(
                getNonCompilablePath("InputXpathConstructorsDeclarationGroupingRecords.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(clazz,
                    ConstructorsDeclarationGroupingCheck.MSG_KEY, 8),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingRecords']]"
                        + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]"
                        + "/OBJBLOCK/COMPACT_CTOR_DEF[./IDENT[@text='MyRecord']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingRecords']]"
                        + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]"
                        + "/OBJBLOCK/COMPACT_CTOR_DEF[./IDENT[@text='MyRecord']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathConstructorsDeclarationGroupingRecords']]"
                        + "/OBJBLOCK/RECORD_DEF[./IDENT[@text='MyRecord']]"
                        + "/OBJBLOCK/COMPACT_CTOR_DEF[./IDENT[@text='MyRecord']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
