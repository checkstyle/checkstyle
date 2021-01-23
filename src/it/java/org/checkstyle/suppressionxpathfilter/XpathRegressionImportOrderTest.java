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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;

public class XpathRegressionImportOrderTest extends AbstractXpathTestSupport {

    private final String checkName = ImportOrderCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportOrderOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_ORDERING, "java.util.Set"),
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
                new File(getPath("SuppressionXpathRegressionImportOrderTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_SEPARATED_IN_GROUP, "java.util.Set"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/IMPORT[./DOT/IDENT[@text='Set']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportOrderThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);
        moduleConfig.addAttribute("groups", "/^java\\./,javax,org");
        moduleConfig.addAttribute("separated", "true");

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_SEPARATION, "org.junit.*"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/IMPORT[./DOT/DOT/IDENT[@text='org']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportOrderFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);
        moduleConfig.addAttribute("option", "inflow");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_ORDERING, "java.lang.Math.PI"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/STATIC_IMPORT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionImportOrderFive.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);
        moduleConfig.addAttribute("groups", "/^java\\./,javax,org");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_ORDERING, "java.util.Date"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/IMPORT[./DOT/IDENT[@text='Date']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
