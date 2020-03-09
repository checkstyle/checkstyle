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
            "6:16: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn"),
            "11:20: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "14:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "17:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "25:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "29:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "29:27: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation2"),
            "32:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "36:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 8, 4),
            "37:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "38:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "41:24: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "45:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "48:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "56:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "61:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "65:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "70:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "73:24: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "75:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "85:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 11, 8),
            "88:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 10, 8),
            "91:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "98:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 0, 3),
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
            "6:16: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn"),
            "11:20: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "14:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "17:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "25:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "29:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "29:27: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation2"),
            "32:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "36:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 8, 4),
            "37:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "38:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "41:24: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "45:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "48:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "56:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "61:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "65:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "70:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "73:24: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "75:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "85:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 11, 8),
            "88:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 10, 8),
            "91:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "98:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 0, 3),
        };
        verify(checkConfig, getPath("InputAnnotationLocationIncorrect.java"), expected);
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
            "17:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "25:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "32:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "36:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 8, 4),
            "37:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "38:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "48:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "61:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "65:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "70:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "85:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 11, 8),
            "88:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 10, 8),
            "98:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 0, 3),
            };
        verify(checkConfig, getPath("InputAnnotationLocationIncorrect.java"), expected);
    }

    @Test
    public void testWithMultipleAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation11"),
            "3:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation12"),
            "3:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation13"),
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
            "17:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 2, 0),
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
            "21:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 6, 4),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
            "25:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 6, 4),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationClass.java"), expected);
    }

    @Test
    public void testEnum() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "ENUM_DEF, ENUM_CONSTANT_DEF");
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "EnumAnnotation", 2, 0),
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "EnumAnnotation"),
            "21:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "EnumAnnotation", 6, 4),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "EnumAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationEnum.java"), expected);
    }

    @Test
    public void testInterface() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "INTERFACE_DEF, METHOD_DEF");
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "InterfaceAnnotation", 2, 0),
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "InterfaceAnnotation"),
            "21:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "InterfaceAnnotation", 6, 4),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "InterfaceAnnotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationInterface.java"), expected);
    }

    @Test
    public void testPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "PACKAGE_DEF");
        final String[] expected = {
            "11:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "PackageAnnotation", 2, 0),
            "12:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "PackageAnnotation"),
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
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "17:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "17:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "23:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "23:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "25:21: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
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
            "19:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "21:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "25:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "25:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "27:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "27:21: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
        };
        verify(checkConfig, getPath("InputAnnotationLocationSingleParameterless.java"), expected);
    }

}
