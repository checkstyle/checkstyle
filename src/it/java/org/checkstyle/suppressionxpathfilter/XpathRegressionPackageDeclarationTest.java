////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import java.util.Collections;
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
    public void test1() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath("SuppressionXpathRegression1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PackageDeclarationCheck.class);

        final String[] expectedViolation = {
            "2:1: " + getCheckMessage(PackageDeclarationCheck.class,
                    PackageDeclarationCheck.MSG_KEY_MISMATCH),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
                new File(getNonCompilablePath("SuppressionXpathRegression2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(PackageDeclarationCheck.class);

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(PackageDeclarationCheck.class,
                    PackageDeclarationCheck.MSG_KEY_MISSING),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression2']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression2']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression2']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
