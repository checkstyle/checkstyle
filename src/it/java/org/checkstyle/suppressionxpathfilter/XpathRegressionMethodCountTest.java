///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.checks.sizes.MethodCountCheck;

public class XpathRegressionMethodCountTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return MethodCountCheck.class.getSimpleName();
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionMethodCount1.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxTotal", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_MANY_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionMethodCount1']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionMethodCount1']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionMethodCount1']]"
                    + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionMethodCount2.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxPrivate", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PRIVATE_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount2']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount2']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount2']]"
                    + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionMethodCount1.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxPackage", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PACKAGE_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount1']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount1']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount1']]"
                    + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionMethodCount3.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxProtected", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PROTECTED_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount3']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount3']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount3']]"
                    + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess = new File(
            getPath("SuppressionXpathRegressionMethodCount4.java")
        );

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MethodCountCheck.class);
        moduleConfig.addProperty("maxPublic", "1");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(MethodCountCheck.class,
                MethodCountCheck.MSG_PUBLIC_METHODS, 2, 1),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount4']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount4']]"
                    + "/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='SuppressionXpathRegressionMethodCount4']]"
                    + "/LITERAL_CLASS"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
