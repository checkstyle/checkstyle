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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck;

public class XpathRegressionMissingOverrideTest extends AbstractXpathTestSupport {

    private final String checkName = MissingOverrideCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_BOOLEAN"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testAnonymous() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideAnonymous.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "8:9: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideAnonymous']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/EXPR/"
                        + "LITERAL_NEW[./IDENT[@text='Runnable']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideAnonymous']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/EXPR/"
                        + "LITERAL_NEW[./IDENT[@text='Runnable']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideAnonymous']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/EXPR/"
                        + "LITERAL_NEW[./IDENT[@text='Runnable']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testInheritDocInvalid1() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideInheritDocInvalid1.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInheritDocInvalid1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInheritDocInvalid1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInheritDocInvalid1']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PRIVATE"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testInheritDocInvalid2() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideInheritDocInvalid2.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInheritDocInvalid2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInheritDocInvalid2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
                "/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInheritDocInvalid2']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testJavaFiveCompatibility1() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);
        moduleConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                    MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionMissingOverrideClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testJavaFiveCompatibility2() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionMissingOverrideInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MissingOverrideCheck.class);
        moduleConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                    MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",
                "/INTERFACE_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionMissingOverrideInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_BOOLEAN"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

}
