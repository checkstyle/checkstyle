////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;

public class XpathRegressionAnnotationLocationTest extends AbstractXpathTestSupport {

    private final String checkName = AnnotationLocationCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAnnotationLocationClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationClass']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationClass']]"
                        + "/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationClass']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='ClassAnnotation']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationClass']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='ClassAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAnnotationLocationInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "7:1: " + getCheckMessage(AnnotationLocationCheck.class,
             AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "InterfaceAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/INTERFACE_DEF[./IDENT[@text='SuppressionXpathRegression"
                    + "AnnotationLocationInterface']]",
            "/INTERFACE_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationInterface'"
                        + "]]/MODIFIERS",
            "/INTERFACE_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationInterface']]"
                    + "/MODIFIERS/ANNOTATION[./IDENT[@text='InterfaceAnnotation']]",
            "/INTERFACE_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationInterface']]"
                    + "/MODIFIERS/ANNOTATION[./IDENT[@text='InterfaceAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAnnotationLocationEnum.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "EnumAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/ENUM_DEF[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationEnum']]",
                "/ENUM_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationEnum']]"
                        + "/MODIFIERS",
                "/ENUM_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationEnum']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='EnumAnnotation']]",
                "/ENUM_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationEnum']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='EnumAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAnnotationLocationMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);
        moduleConfig.addAttribute("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "4:6: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "MethodAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='MethodAnnotation']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='MethodAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAnnotationLocationVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);
        moduleConfig.addAttribute("tokens", "VARIABLE_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "VariableAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='VariableAnnotation']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='VariableAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testConstructor() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAnnotationLocationCTOR.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);
        moduleConfig.addAttribute("tokens", "CTOR_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "CTORAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='CTORAnnotation']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegression"
                        + "AnnotationLocationCTOR']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='CTORAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

}
