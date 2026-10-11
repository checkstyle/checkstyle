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

import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck;

public class XpathRegressionAnnotationLocationTest extends AbstractXpathTestSupport {

    private final String checkName = AnnotationLocationCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/annotationlocation";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "19:1: " + getCheckMessage(AnnotationLocationCheck.class,
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

        verifyXpathWithInlineConfigParser(getPath("InputXpathAnnotationLocationClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "20:1: " + getCheckMessage(AnnotationLocationCheck.class,
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

        verifyXpathWithInlineConfigParser(getPath("InputXpathAnnotationLocationInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expectedViolation = {
            "19:1: " + getCheckMessage(AnnotationLocationCheck.class,
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnnotationLocationEnum.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "16:6: " + getCheckMessage(AnnotationLocationCheck.class,
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
        verifyXpathWithInlineConfigParser(getPath("InputXpathAnnotationLocationMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testVariable() throws Exception {
        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(AnnotationLocationCheck.class,
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

        verifyXpathWithInlineConfigParser(getPath("InputXpathAnnotationLocationVariable.java"),
                expectedXpathQueries,
                expectedViolation);

    }

    @Test
    public void testConstructor() throws Exception {
        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(AnnotationLocationCheck.class,
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

        verifyXpathWithInlineConfigParser(getPath("InputXpathAnnotationLocationCTOR.java"),
                expectedXpathQueries,
                expectedViolation);

    }

}
