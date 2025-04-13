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
import com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck;

public class XpathRegressionPackageDeclarationTest extends AbstractXpathTestSupport {

    private final String checkName = PackageDeclarationCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testWrongPackage() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath("InputXpathWrongPackage.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PackageDeclarationCheck.class);

        final String[] expectedViolation = {
            "2:1: " + getCheckMessage(PackageDeclarationCheck.class,
                    PackageDeclarationCheck.MSG_KEY_MISMATCH),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT", "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMissingPackage() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath("InputXpathMissingPackage.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PackageDeclarationCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(PackageDeclarationCheck.class,
                    PackageDeclarationCheck.MSG_KEY_MISSING),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathMissingPackage']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingPackage']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingPackage']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
