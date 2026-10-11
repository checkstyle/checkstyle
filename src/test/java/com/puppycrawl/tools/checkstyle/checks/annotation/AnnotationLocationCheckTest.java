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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck.MSG_KEY_ANNOTATION_LOCATION_ALONE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AnnotationLocationCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/annotationlocation";
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotationLocationCheck checkObj = new AnnotationLocationCheck();
        assertWithMessage(
                "AnnotationLocationCheck#getRequiredTokens should return empty array by default")
                        .that(checkObj.getRequiredTokens())
                        .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationCorrect.java"), expected);
    }

    @Test
    public void testIncorrectOne() throws Exception {
        final String[] expected = {
            "16:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn"),
            "21:15: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "24:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "27:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "33:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "36:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "36:27: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn_21"),
            "42:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 7, 4),
            "46:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 8, 4),
            "47:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "48:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "51:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "55:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "58:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "66:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "71:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 12, 8),
            "75:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 12, 8),
            "80:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 7, 4),
            "83:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "85:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "95:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 11, 8),
            "98:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 10, 8),
            "100:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationIncorrectOne.java"), expected);
    }

    @Test
    public void testIncorrectTwo() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_21", 0, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationIncorrectTwo.java"), expected);
    }

    @Test
    public void testIncorrectAllTokensOne() throws Exception {
        final String[] expected = {
            "15:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn3"),
            "20:15: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "26:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_13", 8, 4),
            "32:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_13", 8, 4),
            "36:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "36:29: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnn_23"),
            "41:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 7, 4),
            "45:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 8, 4),
            "46:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_33", 6, 4),
            "47:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_43", 10, 4),
            "50:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "54:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "57:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_13", 12, 8),
            "65:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "70:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 12, 8),
            "74:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 12, 8),
            "79:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 7, 4),
            "83:19: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "85:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
            "95:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 11, 8),
            "98:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 10, 8),
            "101:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation_13"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationIncorrect3One.java"), expected);
    }

    @Test
    public void testIncorrectAllTokensTwo() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_23", 0, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationIncorrect3Two.java"), expected);
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
            TokenTypes.MODULE_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testWithoutAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationEmpty.java"), expected);
    }

    @Test
    public void testWithParametersOne() throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_12", 8, 4),
            "34:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_12", 8, 4),
            "41:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 7, 4),
            "45:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 8, 4),
            "46:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_32", 6, 4),
            "47:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_42", 10, 4),
            "57:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation_12", 12, 8),
            "70:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 12, 8),
            "74:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 12, 8),
            "79:8: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 7, 4),
            "94:12: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 11, 8),
            "97:11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 10, 8),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationIncorrect2One.java"), expected);
    }

    @Test
    public void testWithParametersTwo() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnn_22", 0, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationIncorrect2Two.java"), expected);
    }

    @Test
    public void testWithMultipleAnnotations() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation11"),
            "18:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation12"),
            "18:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation13"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationCustomAnnotationsDeclared.java"),
                expected);
    }

    @Test
    public void testAllTokens() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationWithoutAnnotations.java"), expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expected = {
            "20:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "AnnotationAnnotation", 2, 0),
            "21:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "AnnotationAnnotation"),
            "24:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "AnnotationAnnotation", 6, 4),
            "25:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "AnnotationAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationAnnotation.java"), expected);
    }

    @Test
    public void testClass() throws Exception {
        final String[] expected = {
            "20:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 2, 0),
            "21:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
            "24:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 6, 4),
            "25:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
            "30:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "ClassAnnotation", 6, 4),
            "31:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "ClassAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationClass.java"), expected);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "EnumAnnotation", 2, 0),
            "19:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "EnumAnnotation"),
            "22:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "EnumAnnotation", 6, 4),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "EnumAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationEnum.java"), expected);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expected = {
            "20:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "InterfaceAnnotation", 2, 0),
            "21:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "InterfaceAnnotation"),
            "26:7: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "InterfaceAnnotation", 6, 4),
            "27:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "InterfaceAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationInterface.java"), expected);
    }

    @Test
    public void testPackage() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "PackageAnnotation", 2, 0),
            "15:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "PackageAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("inputs/package-info.java"), expected);
    }

    @Test
    public void testAnnotationInForEachLoopParameterAndVariableDef() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationDeprecatedAndCustom.java"), expected);
    }

    @Test
    public void testAnnotationMultiple() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationMultiple.java"), expected);
    }

    @Test
    public void testAnnotationParameterized() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "21:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "21:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "37:21: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationParameterized.java"), expected);
    }

    @Test
    public void testAnnotationSingleParameterless() throws Exception {
        final String[] expected = {
            "24:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "28:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "30:33: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "35:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
            "35:21: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Annotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationSingleParameterless.java"), expected);
    }

    @Test
    public void testAnnotationLocationRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "25:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "29:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "31:9: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "38:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "50:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "61:34: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationLocationRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testAnnotationLocationOnLocalAndPatternVariables() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAnnotationLocationLocalAndPatternVariables.java"),
            expected);
    }

    @Test
    public void testAnnotationLocationCompactSourceFile() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "29:1: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
            "29:29: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "Deprecated"),
            "32:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "Deprecated", 4, 0),
            "43:5: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAnnotationLocationCompactSourceFile.java"),
            expected);
    }

    @Test
    public void testModuleAnnotationValid() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/valid/module-info.java"), expected);
    }

    @Test
    public void testModuleAnnotationViolation() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "SuppressWarnings"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/violation/module-info.java"), expected);
    }

}
