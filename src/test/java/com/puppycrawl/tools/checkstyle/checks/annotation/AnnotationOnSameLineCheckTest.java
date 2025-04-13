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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AnnotationOnSameLineCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/annotationonsameline";
    }

    @Test
    public void testGetRequiredTokens() {
        final AnnotationOnSameLineCheck check = new AnnotationOnSameLineCheck();
        assertWithMessage(
                "AnnotationOnSameLineCheck#getRequiredTokens should return empty array by default")
                        .that(check.getRequiredTokens())
                        .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AnnotationOnSameLineCheck constantNameCheckObj = new AnnotationOnSameLineCheck();
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testAnnotationOnSameLineCheckPublicMethodAndVariable() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
            "21:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation2"),
            "30:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationOnSameLineCheckPublicMethodAndVariable.java"), expected);
    }

    @Test
    public void testAnnotationOnSameLineCheckTokensOnMethodAndVar() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation3"),
            "21:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation4"),
            "30:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationOnSameLineCheckTokensOnMethodAndVar.java"), expected);
    }

    @Test
    public void testAnnotationOnSameLineCheckPrivateAndDeprecatedVar() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "25:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "29:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationOnSameLineCheckPrivateAndDeprecatedVar.java"), expected);
    }

    @Test
    public void testAnnotationOnSameLineCheckInterfaceAndEnum() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "17:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "21:8: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "23:71: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "27:35: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "31:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "34:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "39:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "43:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "45:28: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "47:33: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "50:15: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "56:17: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "65:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "68:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationOnSameLineCheckInterfaceAndEnum.java"), expected);
    }

    @Test
    public void testAnnotationOnSameLineRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "NonNull1"),
            "18:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "24:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "29:9: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "35:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "41:13: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "NonNull1"),
            "41:23: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "51:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "58:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAnnotationOnSameLineRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testAnnotationOnSameLinePatternVariables() throws Exception {
        final String[] expected = {
            "37:8: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAnnotationOnSameLinePatternVariables.java"),
            expected);
    }

    @Test
    public void testAnnotationOnSameLine() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "36:14: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "43:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
            "43:17: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAnnotationOnSameLine.java"), expected);
    }

    @Test
    public void testAnnotationOnSameLine2() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "22:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "26:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "30:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAnnotationOnSameLine.java"),
            expected);
    }
}
