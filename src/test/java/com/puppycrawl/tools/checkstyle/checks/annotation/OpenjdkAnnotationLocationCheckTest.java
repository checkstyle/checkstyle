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
import static com.puppycrawl.tools.checkstyle.checks.annotation.OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ALONE_OR_SAME;
import static com.puppycrawl.tools.checkstyle.checks.annotation.OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OpenjdkAnnotationLocationCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/openjdkannotationlocation";
    }

    @Test
    public void testGetRequiredTokens() {
        final OpenjdkAnnotationLocationCheck obj =
            new OpenjdkAnnotationLocationCheck();
        assertWithMessage(
          "OpenjdkAnnotationLocation#getRequiredTokens should return empty array by default")
                .that(obj.getRequiredTokens())
                .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final OpenjdkAnnotationLocationCheck obj =
                new OpenjdkAnnotationLocationCheck();
        final int[] actual = obj.getAcceptableTokens();
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
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testAnnotation1() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "Annotation2"),
            "15:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation2"),
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation2"),
            "24:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation2"),
            "24:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation2"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation2"),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkAnnotationLocation.java"), expected);
    }

    @Test
    public void testAnnotation2() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation12"),
            "23:21: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation13"),
            "25:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation14"),
            "34:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation12"),
            "34:17: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation13"),
            "39:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "MyAnnotation11"),
            "44:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation12"),
            "44:21: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "MyAnnotation13"),
            "51:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "MyAnnotation13"),
            "55:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "MyAnnotation11"),
            "60:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "MyAnnotation15"),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkAnnotationLocation2.java"), expected);
    }

    @Test
    public void testAnnotation3() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "EnumAnnotation"),
            "15:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "EnumAnnotation"),
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "EnumAnnotation"),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkAnnotationLocation3.java"), expected);
    }

    @Test
    public void testAnnotation4() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation"),
            "19:17: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation"),
            "24:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "Annotation"),
            "24:21: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "Annotation"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "Annotation"),
            "31:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Annotation"),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkAnnotationLocation4.java"), expected);
    }

    @Test
    public void testAnnotationCompact() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "SuppressWarnings"),
            "14:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Deprecated"),
            "25:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "SuppressWarnings"),
            "25:29: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Deprecated"),
            "44:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "Special"),
            "52:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "SuppressWarnings"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputOpenjdkAnnotationLocationCompactSourceFile.java"), expected);
    }

    @Test
    public void testAnnotation5() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "MyAnn"),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkAnnotationLocation5.java"), expected);
    }

}
