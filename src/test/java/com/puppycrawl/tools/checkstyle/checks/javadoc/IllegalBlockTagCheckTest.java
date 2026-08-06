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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.IllegalBlockTagCheck.MSG_ILLEGAL_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalBlockTagCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/illegalblocktag";
    }

    @Test
    public void testGetAcceptableTokens() {
        final IllegalBlockTagCheck checkObj = new IllegalBlockTagCheck();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final IllegalBlockTagCheck checkObj = new IllegalBlockTagCheck();
        assertWithMessage("Required tokens should be empty")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testDefaultSettings() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagDefault.java"), expected);
    }

    @Test
    public void testTagPresence() throws Exception {
        final String[] expected = {
            "17:4: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "todo"),
            "30:8: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "todo"),
        };
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagPresence.java"), expected);
    }

    @Test
    public void testIllegalPattern() throws Exception {
        final String[] expected = {
            "21:8: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "since"),
            "29:8: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "since"),
        };
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagPattern.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagNoJavadoc.java"), expected);
    }

    @Test
    public void testAnnotationJavadoc() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "todo"),
        };
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagAnnotation.java"), expected);
    }

    @Test
    public void testEmptyTagContent() throws Exception {
        final String[] expected = {
            "18:8: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "todo"),
        };
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagEmptyTag.java"), expected);
    }

    @Test
    public void testEnumConstant() throws Exception {
        final String[] expected = {
            "17:8: " + getCheckMessage(MSG_ILLEGAL_PATTERN, "todo"),
        };
        verifyWithInlineConfigParser(getPath("InputIllegalBlockTagEnumConstant.java"), expected);
    }

}
