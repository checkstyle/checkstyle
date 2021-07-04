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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AnnotationLocationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/annotationlocation";
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotationLocationCheck checkObj = new AnnotationLocationCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "AnnotationLocationCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputAnnotationLocationCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn"),
            "19:15: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "25:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "33:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "37:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "37:27: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn_21"),
            "40:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 7, 4),
            "44:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 8, 4),
            "45:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "46:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "49:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "53:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "56:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "64:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "69:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 12, 8),
            "73:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 12, 8),
            "78:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 7, 4),
            "81:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "83:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "93:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 11, 8),
            "96:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 10, 8),
            "99:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "106:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 0, 3),
        };
        verify(checkConfig, getPath("InputAnnotationLocationIncorrect.java"), expected);
    }

    @Test
    public void testIncorrectAllTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                + "CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, "
                + "ENUM_CONSTANT_DEF, PACKAGE_DEF");
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn3"),
            "19:15: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "25:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_13", 8, 4),
            "33:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_13", 8, 4),
            "37:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "37:29: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn_23"),
            "40:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 7, 4),
            "44:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 8, 4),
            "45:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_33", 6, 4),
            "46:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_43", 10, 4),
            "49:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "53:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "56:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_13", 12, 8),
            "64:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "69:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 12, 8),
            "73:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 12, 8),
            "78:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 7, 4),
            "81:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "83:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "93:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 11, 8),
            "96:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 10, 8),
            "99:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "106:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 0, 3),
        };
        verify(checkConfig, getPath("InputAnnotationLocationIncorrect3.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AnnotationLocationCheck constantNameCheckObj = new AnnotationLocationCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testWithoutAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationEmpty.java"), expected);
    }

    @Test
    public void testWithParameters() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "true");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "true");
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "true");
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_12", 8, 4),
            "33:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_12", 8, 4),
            "40:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 7, 4),
            "44:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 8, 4),
            "45:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_32", 6, 4),
            "46:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_42", 10, 4),
            "56:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_12", 12, 8),
            "69:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 12, 8),
            "73:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 12, 8),
            "78:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 7, 4),
            "93:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 11, 8),
            "96:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 10, 8),
            "106:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 0, 3),
        };
        verify(checkConfig, getPath("InputAnnotationLocationIncorrect2.java"), expected);
    }

    @Test
    public void testWithMultipleAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation11"),
            "14:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation12"),
            "14:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation13"),
            };
        verify(checkConfig, getPath("InputAnnotationLocationCustomAnnotationsDeclared.java"),
                expected);
    }

    @Test
    public void testAllTokens() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                + "CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationWithoutAnnotations.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "ANNOTATION_DEF, ANNOTATION_FIELD_DEF");
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "AnnotationAnnotation", 2, 0),
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "AnnotationAnnotation"),
            "21:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "AnnotationAnnotation", 6, 4),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "AnnotationAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationAnnotation.java"), expected);
    }

    @Test
    public void testClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "CLASS_DEF, CTOR_DEF, VARIABLE_DEF");
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 2, 0),
            "19:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
            "22:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 6, 4),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
            "26:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 6, 4),
            "27:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationClass.java"), expected);
    }

    @Test
    public void testEnum() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "ENUM_DEF, ENUM_CONSTANT_DEF");
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "EnumAnnotation", 2, 0),
            "19:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "EnumAnnotation"),
            "22:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "EnumAnnotation", 6, 4),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "EnumAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationEnum.java"), expected);
    }

    @Test
    public void testInterface() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "INTERFACE_DEF, METHOD_DEF");
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "InterfaceAnnotation", 2, 0),
            "19:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "InterfaceAnnotation"),
            "22:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "InterfaceAnnotation", 6, 4),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "InterfaceAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationInterface.java"), expected);
    }

    @Test
    public void testPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "PACKAGE_DEF");
        final String[] expected = {
            "12:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "PackageAnnotation", 2, 0),
            "13:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "PackageAnnotation"),
        };
        verify(checkConfig, getPath("inputs/package-info.java"), expected);
    }

    @Test
    public void testAnnotationInForEachLoopParameterAndVariableDef() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF,"
            + " CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF");
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "false");
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationDeprecatedAndCustom.java"), expected);
    }

    @Test
    public void testAnnotationMultiple() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "true");
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationMultiple.java"), expected);
    }

    @Test
    public void testAnnotationParameterized() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "false");
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "true");
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "20:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "26:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "26:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "28:21: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationParameterized.java"), expected);
    }

    @Test
    public void testAnnotationSingleParameterless() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "false");
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "true");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "false");
        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "24:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "28:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "28:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:21: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationSingleParameterless.java"), expected);
    }

    @Test
    public void testAnnotationLocationRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "27:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "33:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "44:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "54:34: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
        };
        verify(checkConfig,
            getNonCompilablePath("InputAnnotationLocationRecordsAndCompactCtors.java"),
            expected);
    }

}
