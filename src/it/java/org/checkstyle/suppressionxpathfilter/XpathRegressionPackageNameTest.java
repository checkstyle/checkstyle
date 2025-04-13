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

import static com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck.MSG_KEY;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck;

public class XpathRegressionPackageNameTest extends AbstractXpathTestSupport {

    private final String checkName = PackageNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {

        final File fileToProcess =
            new File(getPath("InputXpathPackageNameOne.java"));

        final String pattern = "[A-Z]+";

        final DefaultConfiguration moduleConfig = createModuleConfig(PackageNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final String[] expectedViolation = {
            "1:9: " + getCheckMessage(PackageNameCheck.class, MSG_KEY,
                "org.checkstyle.suppressionxpathfilter.packagename",
                pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/PACKAGE_DEF/DOT"
                + "[./IDENT[@text='packagename']]/DOT"
                + "[./IDENT[@text='suppressionxpathfilter']]"
                + "/DOT/IDENT[@text='org']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);

    }

    @Test
    public void testThree() throws Exception {

        final File fileToProcess =
            new File(getNonCompilablePath("InputXpathPackageNameThree.java"));

        final String pattern = "^[a-z]+(\\.[a-z][a-z0-9]*)*$";

        final DefaultConfiguration moduleConfig = createModuleConfig(PackageNameCheck.class);
        moduleConfig.addProperty("format", pattern);
        final String[] expectedViolations = {
            "1:9: " + getCheckMessage(PackageNameCheck.class, MSG_KEY,
                "org.checkstyle.suppressionxpathfilter.PACKAGENAME",
                pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/PACKAGE_DEF/DOT[./IDENT"
                + "[@text='PACKAGENAME']]/DOT[./IDENT"
                + "[@text='suppressionxpathfilter']]"
                + "/DOT/IDENT[@text='org']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations,
            expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {

        final File fileToProcess =
            new File(getPath("InputXpathPackageNameTwo.java"));

        final String pattern = "[A-Z]+";

        final DefaultConfiguration moduleConfig = createModuleConfig(PackageNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final String[] expectedViolation = {
            "2:9: " + getCheckMessage(PackageNameCheck.class, MSG_KEY,
                "org.checkstyle.suppressionxpathfilter.packagename",
                pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/PACKAGE_DEF/DOT"
                + "[./IDENT[@text='packagename']]/DOT"
                + "[./IDENT[@text='suppressionxpathfilter']]"
                + "/DOT/IDENT[@text='org']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
