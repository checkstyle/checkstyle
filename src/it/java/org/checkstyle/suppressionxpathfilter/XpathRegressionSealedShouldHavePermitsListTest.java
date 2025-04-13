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
import com.puppycrawl.tools.checkstyle.checks.design.SealedShouldHavePermitsListCheck;

public class XpathRegressionSealedShouldHavePermitsListTest extends AbstractXpathTestSupport {

    private final String checkName = SealedShouldHavePermitsListCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testInner() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath("InputXpathSealedShouldHavePermitsListInner.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SealedShouldHavePermitsListCheck.class);

        final String[] expectedViolation = {
            "5:4: " + getCheckMessage(SealedShouldHavePermitsListCheck.class,
                SealedShouldHavePermitsListCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathSealedShouldHavePermitsListInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='A']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathSealedShouldHavePermitsListInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='A']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathSealedShouldHavePermitsListInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='A']]/MODIFIERS/LITERAL_SEALED"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTopLevel() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath(
                        "InputXpathSealedShouldHavePermitsListTopLevel.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SealedShouldHavePermitsListCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(SealedShouldHavePermitsListCheck.class,
                SealedShouldHavePermitsListCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListTopLevel']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListTopLevel']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListTopLevel']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
