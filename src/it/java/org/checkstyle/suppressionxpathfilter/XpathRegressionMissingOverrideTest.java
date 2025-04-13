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
            "InputXpathMissingOverrideClass.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideInterface.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_BOOLEAN"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

    @Test
    public void testAnonymous() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideAnonymous.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "8:9: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideAnonymous']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/EXPR/"
                + "LITERAL_NEW[./IDENT[@text='Runnable']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideAnonymous']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/EXPR/"
                + "LITERAL_NEW[./IDENT[@text='Runnable']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideAnonymous']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='r']]/ASSIGN/EXPR/"
                + "LITERAL_NEW[./IDENT[@text='Runnable']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

    @Test
    public void testInheritDocInvalidPrivateMethod() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideInheritDocInvalidPrivateMethod.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInheritDocInvalidPrivateMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInheritDocInvalidPrivateMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInheritDocInvalidPrivateMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PRIVATE"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

    @Test
    public void testInheritDocInvalidPublicMethod() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideInheritDocInvalidPublicMethod.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInheritDocInvalidPublicMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInheritDocInvalidPublicMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInheritDocInvalidPublicMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

    @Test
    public void testJavaFiveCompatibilityOne() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideClass.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);
        moduleConfig.addProperty("javaFiveCompatibility", "true");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMissingOverrideClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

    @Test
    public void testJavaFiveCompatibilityTwo() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathMissingOverrideInterface.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(MissingOverrideCheck.class);
        moduleConfig.addProperty("javaFiveCompatibility", "true");

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(MissingOverrideCheck.class,
                MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathMissingOverrideInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_BOOLEAN"

        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);

    }

}
