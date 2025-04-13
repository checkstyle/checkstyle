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

import static com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheck;

public class XpathRegressionHideUtilityClassConstructorTest extends AbstractXpathTestSupport {

    private final String checkName = HideUtilityClassConstructorCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathHideUtilityClassConstructorDefault.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(HideUtilityClassConstructorCheck.class);
        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(HideUtilityClassConstructorCheck.class, MSG_KEY),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                    + "InputXpathHideUtilityClassConstructorDefault']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                    + "InputXpathHideUtilityClassConstructorDefault']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                    + "InputXpathHideUtilityClassConstructorDefault']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testPublicConstructor() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathHideUtilityClassConstructorPublic.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(HideUtilityClassConstructorCheck.class);
        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(HideUtilityClassConstructorCheck.class, MSG_KEY),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                    + "InputXpathHideUtilityClassConstructorPublic']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                    + "InputXpathHideUtilityClassConstructorPublic']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                    + "InputXpathHideUtilityClassConstructorPublic']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
