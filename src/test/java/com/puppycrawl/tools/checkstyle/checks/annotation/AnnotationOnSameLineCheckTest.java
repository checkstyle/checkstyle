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

import static com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens(),
                "AnnotationOnSameLineCheck#getRequiredTokens should return empty array by default");
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
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testCheck() throws Exception {
        final DefaultConfiguration config = createModuleConfig(AnnotationOnSameLineCheck.class);
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
            "14:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
            "20:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
        };
        verify(config, getPath("InputAnnotationOnSameLineCheck.java"), expected);
    }

    @Test
    public void testCheckAcceptableTokens() throws Exception {
        final DefaultConfiguration config = createModuleConfig(AnnotationOnSameLineCheck.class);
        config.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                + "CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, "
                + "LITERAL_THROWS, IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, "
                + "ANNOTATION_FIELD_DEF");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation3"),
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation"),
            "16:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
            "21:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Annotation3"),
        };
        verify(config, getPath("InputAnnotationOnSameLineCheck3.java"), expected);
    }

    @Test
    public void testCheck2() throws Exception {
        final DefaultConfiguration config = createModuleConfig(AnnotationOnSameLineCheck.class);
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "20:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "23:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "24:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
        };
        verify(config, getPath("InputAnnotationOnSameLineCheck2.java"), expected);
    }

    @Test
    public void testCheckOnDifferentTokens() throws Exception {
        final DefaultConfiguration config = createModuleConfig(AnnotationOnSameLineCheck.class);
        config.addAttribute("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                + "CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, "
                + "LITERAL_THROWS, IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, "
                + "ANNOTATION_FIELD_DEF");
        final String[] expected = {
            "5:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "8:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "17:8: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "18:72: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "21:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "22:35: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "25:18: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "28:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "33:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "36:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "37:28: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "38:33: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "40:15: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "46:17: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "55:1: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
            "58:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "Ann"),
        };
        verify(config, getPath("InputAnnotationOnSameLineCheckOnDifferentTokens.java"), expected);
    }

    @Test
    public void testAnnotationOnSameLineRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration config = createModuleConfig(AnnotationOnSameLineCheck.class);
        config.addAttribute("tokens", "CLASS_DEF , INTERFACE_DEF , ENUM_DEF , METHOD_DEF,"
            + " CTOR_DEF , VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF");

        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "NonNull1"),
            "14:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "19:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "24:9: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "29:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "32:27: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "41:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            "47:5: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_SAME_LINE, "SuppressWarnings"),
            };
        verify(config,
            getNonCompilablePath("InputAnnotationOnSameLineRecordsAndCompactCtors.java"),
            expected);
    }

}
