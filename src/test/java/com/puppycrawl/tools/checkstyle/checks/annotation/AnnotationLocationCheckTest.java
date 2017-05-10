////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AnnotationLocationCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "annotation" + File.separator
                + "annotationlocation" + File.separator
                + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotationLocationCheck checkObj = new AnnotationLocationCheck();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputAnnotationLocationCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        final String[] expected = {
            "6: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "11: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "25: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "29: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "29: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation2"),
            "32: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "36: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 8, 4),
            "37: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "38: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "41: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "48: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "61: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "65: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "70: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "73: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation1"),
            "85: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 11, 8),
            "88: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 10, 8),
            "98: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 0, 3),
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
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.TYPE_ARGUMENT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.DOT,
            TokenTypes.ANNOTATION_FIELD_DEF,
            };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testWithoutAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationEmpty.java"), expected);
    }

    @Test
    public void testWithParameters() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "true");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "true");
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "true");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "25: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 8, 4),
            "32: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "36: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 8, 4),
            "37: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation3", 6, 4),
            "38: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation4", 10, 4),
            "48: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation1", 12, 8),
            "61: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "65: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 12, 8),
            "70: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 7, 4),
            "85: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 11, 8),
            "88: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 10, 8),
            "98: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION, "MyAnnotation2", 0, 3),
            };
        verify(checkConfig, getPath("InputAnnotationLocationIncorrect.java"), expected);
    }

    @Test
    public void testWithMultipleAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation11"),
            "3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation12"),
            "3: " + getCheckMessage(MSG_KEY_ANNOTATION_LOCATION_ALONE, "MyAnnotation13"),
            };
        verify(checkConfig, getPath("InputAnnotationLocationCustomAnnotationsDeclared.java"),
                expected);
    }

    @Test
    public void testAllTokens() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                + "CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, "
                + "LITERAL_THROWS, IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, "
                + "ANNOTATION_FIELD_DEF");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationWithoutAnnotations.java"), expected);
    }

    @Test
    public void testAnnotationInForEachLoopParameterAndVariableDef() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        checkConfig.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF,"
            + " CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS,"
            + " IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF,"
            + " TYPE_ARGUMENT");
        checkConfig.addAttribute("allowSamelineMultipleAnnotations", "false");
        checkConfig.addAttribute("allowSamelineSingleParameterlessAnnotation", "false");
        checkConfig.addAttribute("allowSamelineParameterizedAnnotation", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputAnnotationLocationDeprecatedAndCustom.java"), expected);
    }
}
