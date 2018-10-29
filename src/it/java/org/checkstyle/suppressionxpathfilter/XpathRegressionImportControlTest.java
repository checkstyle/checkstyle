////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck;

public class XpathRegressionImportControlTest extends AbstractXpathTestSupport {

    private final String checkName = ImportControlCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportControlOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportControlCheck.class);
        moduleConfig.addAttribute("file", getPath(
                "SuppressionXpathRegressionImportControlOne.xml"));

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_DISALLOWED, "java.util.Scanner"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/IMPORT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportControlTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportControlCheck.class);
        moduleConfig.addAttribute("file", getPath(
                "SuppressionXpathRegressionImportControlTwo.xml"));

        final String[] expectedViolation = {
            "1:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_UNKNOWN_PKG),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportControlThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportControlCheck.class);

        final String[] expectedViolation = {
            "1:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_MISSING_FILE),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportControlFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportControlCheck.class);
        moduleConfig.addAttribute("file",
                getPath("SuppressionXpathRegressionImportControlFour.xml"));

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(ImportControlCheck.class,
                ImportControlCheck.MSG_DISALLOWED, "java.util.Scanner"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/IMPORT[./DOT[@text='Scanner']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
