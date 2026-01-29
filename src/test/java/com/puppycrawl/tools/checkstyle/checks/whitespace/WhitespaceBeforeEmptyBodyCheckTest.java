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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceBeforeEmptyBodyCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceBeforeEmptyBodyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacebeforeemptybody";
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceBeforeEmptyBodyCheck checkObj = new WhitespaceBeforeEmptyBodyCheck();
        assertWithMessage(
                "WhitespaceBeforeEmptyBodyCheck#getRequiredTokens should return empty array")
                        .that(checkObj.getRequiredTokens())
                        .isEmpty();
    }

    @Test
    public void testGetAcceptableTokens() {
        final WhitespaceBeforeEmptyBodyCheck checkObj = new WhitespaceBeforeEmptyBodyCheck();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_DO,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_NEW,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyMethod() throws Exception {
        final String[] expected = {
            "17:19: " + getCheckMessage(MSG_KEY, "method1"),
            "19:26: " + getCheckMessage(MSG_KEY, "method2"),
            "22:34: " + getCheckMessage(MSG_KEY, "method3"),
            "25:35: " + getCheckMessage(MSG_KEY, "method4"),
            "28:32: " + getCheckMessage(MSG_KEY, "method5"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyMethod.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyConstructor() throws Exception {
        final String[] expected = {
            "17:48: " + getCheckMessage(MSG_KEY, "InputWhitespaceBeforeEmptyBodyConstructor"),
            "20:60: " + getCheckMessage(MSG_KEY, "InputWhitespaceBeforeEmptyBodyConstructor"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyConstructor.java"), expected);
    }

    @Test
    public void testEmptyTypeDefinitions() throws Exception {
        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_KEY, "InnerClass"),
            "20:29: " + getCheckMessage(MSG_KEY, "InnerInterface"),
            "23:19: " + getCheckMessage(MSG_KEY, "InnerEnum"),
            "26:25: " + getCheckMessage(MSG_KEY, "InnerRecord"),
            "29:31: " + getCheckMessage(MSG_KEY, "InnerAnnotation"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyTypes.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyLoop() throws Exception {
        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_KEY, "while"),
            "19:36: " + getCheckMessage(MSG_KEY, "for"),
            "21:11: " + getCheckMessage(MSG_KEY, "do"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyLoops.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyInitializer() throws Exception {
        final String[] expected = {
            "17:11: " + getCheckMessage(MSG_KEY, "static"),
            "22:25: " + getCheckMessage(MSG_KEY, "instance initializer"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyInitializers.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyLambda() throws Exception {
        final String[] expected = {
            "17:28: " + getCheckMessage(MSG_KEY, "lambda"),
            "20:31: " + getCheckMessage(MSG_KEY, "lambda"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyLambda.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyAnonymousClass() throws Exception {
        final String[] expected = {
            "20:37: " + getCheckMessage(MSG_KEY, "anonymous class"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyAnonymousClass.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyRecord() throws Exception {
        final String[] expected = {
            "17:25: " + getCheckMessage(MSG_KEY, "EmptyRecord"),
            "20:31: " + getCheckMessage(MSG_KEY, "EmptyRecord2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyRecord.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyAllValid() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyValid.java"), expected);
    }

    @Test
    public void testNonEmptyBodies() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyNonEmpty.java"), expected);
    }

    @Test
    public void testLineBreakBeforeBrace() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyLineBreak.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyCompactConstructor() throws Exception {
        final String[] expected = {
            "19:24: " + getCheckMessage(MSG_KEY, "MyRecord"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyCompactCtor.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyAbstractMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyAbstract.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyAllConstructs() throws Exception {
        final String[] expected = {
            "17:50: " + getCheckMessage(MSG_KEY, "InputWhitespaceBeforeEmptyBodyAllConstructs"),
            "19:18: " + getCheckMessage(MSG_KEY, "method"),
            "21:16: " + getCheckMessage(MSG_KEY, "Inner"),
            "23:29: " + getCheckMessage(MSG_KEY, "InnerInterface"),
            "26:19: " + getCheckMessage(MSG_KEY, "InnerEnum"),
            "28:26: " + getCheckMessage(MSG_KEY, "InnerAnnot"),
            "31:11: " + getCheckMessage(MSG_KEY, "static"),
            "33:23: " + getCheckMessage(MSG_KEY, "lambda"),
            "36:21: " + getCheckMessage(MSG_KEY, "while"),
            "40:11: " + getCheckMessage(MSG_KEY, "do"),
            "44:36: " + getCheckMessage(MSG_KEY, "for"),
            "49:12: " + getCheckMessage(MSG_KEY, "try"),
            "50:28: " + getCheckMessage(MSG_KEY, "catch"),
            "52:16: " + getCheckMessage(MSG_KEY, "finally"),
            "56:28: " + getCheckMessage(MSG_KEY, "synchronized"),
            "61:19: " + getCheckMessage(MSG_KEY, "switch"),
            "64:25: " + getCheckMessage(MSG_KEY, "InnerRecord"),
            "69:31: " + getCheckMessage(MSG_KEY, "InnerRecordWithCompact"),
            "72:31: " + getCheckMessage(MSG_KEY, "anonymous class"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyAllConstructs.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodySwitch() throws Exception {
        final String[] expected = {
            "20:19: " + getCheckMessage(MSG_KEY, "switch"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceBeforeEmptyBodySwitch.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyTry() throws Exception {
        final String[] expected = {
            "20:12: " + getCheckMessage(MSG_KEY, "try"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceBeforeEmptyBodyTry.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyCatch() throws Exception {
        final String[] expected = {
            "24:28: " + getCheckMessage(MSG_KEY, "catch"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceBeforeEmptyBodyCatch.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodyFinally() throws Exception {
        final String[] expected = {
            "24:16: " + getCheckMessage(MSG_KEY, "finally"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceBeforeEmptyBodyFinally.java"), expected);
    }

    @Test
    public void testWhitespaceBeforeEmptyBodySynchronized() throws Exception {
        final String[] expected = {
            "22:28: " + getCheckMessage(MSG_KEY, "synchronized"),
        };
        verifyWithInlineConfigParser(
            getPath("InputWhitespaceBeforeEmptyBodySynchronized.java"), expected);
    }

}
