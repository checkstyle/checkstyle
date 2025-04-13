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
                new File(getPath("InputXpathImportOrderOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_ORDERING, "java.util.Set"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathImportOrderTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_SEPARATED_IN_GROUP, "java.util.Set"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='Set']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathImportOrderThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);
        moduleConfig.addProperty("groups", "/^java\\./,javax,org");
        moduleConfig.addProperty("separated", "true");

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_SEPARATION, "org.junit.*"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/DOT/IDENT[@text='org']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathImportOrderFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);
        moduleConfig.addProperty("option", "inflow");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_ORDERING, "java.lang.Math.PI"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathImportOrderFive.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(ImportOrderCheck.class);
        moduleConfig.addProperty("groups", "/^java\\./,javax,org");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(ImportOrderCheck.class,
                        ImportOrderCheck.MSG_ORDERING, "java.util.Date"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='Date']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
