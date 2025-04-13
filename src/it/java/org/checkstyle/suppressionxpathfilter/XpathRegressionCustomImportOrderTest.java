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
import com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck;

public class XpathRegressionCustomImportOrderTest extends AbstractXpathTestSupport {

    private final String checkName = CustomImportOrderCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathCustomImportOrderOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CustomImportOrderCheck.class);
        moduleConfig.addProperty("customImportOrderRules", "STATIC###STANDARD_JAVA_PACKAGE");
        moduleConfig.addProperty("sortImportsInGroupAlphabetically", "true");

        final String[] expectedViolation = {
            "4:1: " + getCheckMessage(CustomImportOrderCheck.class,
                        CustomImportOrderCheck.MSG_LEX, "java.lang.Math.PI",
                        "java.util.Arrays.sort"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT[./DOT/IDENT[@text='PI']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathCustomImportOrderTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CustomImportOrderCheck.class);
        moduleConfig.addProperty("customImportOrderRules", "STATIC###STANDARD_JAVA_PACKAGE");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(CustomImportOrderCheck.class,
                        CustomImportOrderCheck.MSG_LINE_SEPARATOR, "java.io.File"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='File']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathCustomImportOrderThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CustomImportOrderCheck.class);
        moduleConfig.addProperty("customImportOrderRules", "STATIC###STANDARD_JAVA_PACKAGE");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(CustomImportOrderCheck.class,
                        CustomImportOrderCheck.MSG_SEPARATED_IN_GROUP, "java.lang.Math.PI"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT[./DOT/IDENT[@text='PI']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathCustomImportOrderFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CustomImportOrderCheck.class);
        moduleConfig.addProperty("customImportOrderRules", "STATIC###STANDARD_JAVA_PACKAGE");

        final String[] expectedViolation = {
            "5:1: " + getCheckMessage(CustomImportOrderCheck.class,
                        CustomImportOrderCheck.MSG_NONGROUP_IMPORT,
                        "com.puppycrawl.tools.checkstyle.api.DetailAST"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='DetailAST']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathCustomImportOrderFive.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CustomImportOrderCheck.class);
        moduleConfig.addProperty("customImportOrderRules", "STATIC###STANDARD_JAVA_PACKAGE");

        final String[] expectedViolation = {
            "7:1: " + getCheckMessage(CustomImportOrderCheck.class,
                        CustomImportOrderCheck.MSG_NONGROUP_EXPECTED, "STATIC",
                        "java.lang.Math.PI"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/STATIC_IMPORT[./DOT/IDENT[@text='PI']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testSix() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathCustomImportOrderSix.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(CustomImportOrderCheck.class);
        moduleConfig.addProperty("customImportOrderRules",
            "STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE");

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(CustomImportOrderCheck.class,
                        CustomImportOrderCheck.MSG_ORDER, "THIRD_PARTY_PACKAGE",
                        "STANDARD_JAVA_PACKAGE",
                        "com.puppycrawl.tools.checkstyle.api.DetailAST"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/IMPORT[./DOT/IDENT[@text='DetailAST']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
