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
                "InputXpathAnnotationLocationClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationClass']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationClass']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationClass']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='ClassAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationClass']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='ClassAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathAnnotationLocationInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "7:1: " + getCheckMessage(AnnotationLocationCheck.class,
             AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "InterfaceAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='"
                    + "InputXpathAnnotationLocationInterface']]",
            "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='InputXpathAnnotationLocationInterface'"
                    + "]]/MODIFIERS",
            "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='InputXpathAnnotationLocationInterface']]"
                    + "/MODIFIERS/ANNOTATION[./IDENT[@text='InterfaceAnnotation']]",
            "/COMPILATION_UNIT/INTERFACE_DEF"
                    + "[./IDENT[@text='InputXpathAnnotationLocationInterface']]"
                    + "/MODIFIERS/ANNOTATION[./IDENT[@text='InterfaceAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathAnnotationLocationEnum.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "EnumAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT[@text='"
                        + "InputXpathAnnotationLocationEnum']]",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationEnum']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationEnum']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='EnumAnnotation']]",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationEnum']]"
                        + "/MODIFIERS/ANNOTATION[./IDENT[@text='EnumAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathAnnotationLocationMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "4:6: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "MethodAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='MethodAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationMethod']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo1']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='MethodAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathAnnotationLocationVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);
        moduleConfig.addProperty("tokens", "VARIABLE_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "VariableAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='VariableAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathAnnotationLocationVariable']]/"
                        + "OBJBLOCK/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS/"
                        + "ANNOTATION[./IDENT[@text='VariableAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

    @Test
    public void testConstructor() throws Exception {
        final File fileToProcess = new File(getPath(
                "InputXpathAnnotationLocationCTOR.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AnnotationLocationCheck.class);
        moduleConfig.addProperty("tokens", "CTOR_DEF");

        final String[] expectedViolation = {
            "4:5: " + getCheckMessage(AnnotationLocationCheck.class,
                    AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE,
                    "CTORAnnotation"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='CTORAnnotation']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/OBJBLOCK/CTOR_DEF"
                        + "[./IDENT[@text='"
                        + "InputXpathAnnotationLocationCTOR']]/"
                        + "MODIFIERS/ANNOTATION[./IDENT[@text='CTORAnnotation']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);

    }

}
