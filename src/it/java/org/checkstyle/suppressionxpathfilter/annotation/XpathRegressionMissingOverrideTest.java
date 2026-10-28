///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package org.checkstyle.suppressionxpathfilter.annotation;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck;

public class XpathRegressionMissingOverrideTest extends AbstractXpathTestSupport {

    private final String checkName = MissingOverrideCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/missingoverride";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingOverrideCheck.class,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideClass.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingOverrideCheck.class,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideInterface.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testAnonymous() throws Exception {
        final String[] expectedViolation = {
            "16:9: " + getCheckMessage(MissingOverrideCheck.class,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideAnonymous.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testInheritDocInvalidPrivateMethod() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingOverrideCheck.class,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideInheritDocInvalidPrivateMethod.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testInheritDocInvalidPublicMethod() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingOverrideCheck.class,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideInheritDocInvalidPublicMethod.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testJavaFiveCompatibilityOne() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingOverrideCheck.class,
                    MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingOverrideClassJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingOverrideClassJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathMissingOverrideClassJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"

        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideClassJavaFive.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testJavaFiveCompatibilityTwo() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(MissingOverrideCheck.class,
                    MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                        + "[@text='InputXpathMissingOverrideInterfaceJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                        + "[@text='InputXpathMissingOverrideInterfaceJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                        + "[@text='InputXpathMissingOverrideInterfaceJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                        + "[@text='InputXpathMissingOverrideInterfaceJavaFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/LITERAL_BOOLEAN"

        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMissingOverrideInterfaceJavaFive.java"),
                expectedXpathQueries,
                expectedViolation);

    }

}
