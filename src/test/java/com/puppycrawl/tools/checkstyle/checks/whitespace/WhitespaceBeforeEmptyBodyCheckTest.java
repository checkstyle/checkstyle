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

public class WhitespaceBeforeEmptyBodyCheckTest extends AbstractModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacebeforeemptybody";
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceBeforeEmptyBodyCheck checkObj = new WhitespaceBeforeEmptyBodyCheck();
        assertWithMessage(
                "WhitespaceAroundCheck#getRequiredTokens should return empty array by default")
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
            TokenTypes.RECORD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LAMBDA,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testAnonymousClassWithEmptyBody() throws Exception {
        final String[] expected = {
            "22:26: " + getCheckMessage(MSG_KEY, "{"),
            "26:26: " + getCheckMessage(MSG_KEY, "{"),
            "29:26: " + getCheckMessage(MSG_KEY, "{"),
            "32:26: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyAnonymousClass.java"),
                expected);
    }

    @Test
    public void testConstructorWithEmptyBody() throws Exception {
        final String[] expected = {
            "13:48: " + getCheckMessage(MSG_KEY, "{"),
            "16:53: " + getCheckMessage(MSG_KEY, "{"),
            "26:12: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyConstructor.java"), expected);
    }

    @Test
    public void testDefaultWithEmptyBody() throws Exception {
        final String[] expected = {
            "24:36: " + getCheckMessage(MSG_KEY, "{"),
            "28:12: " + getCheckMessage(MSG_KEY, "{"),
            "29:30: " + getCheckMessage(MSG_KEY, "{"),
            "30:18: " + getCheckMessage(MSG_KEY, "{"),
            "35:28: " + getCheckMessage(MSG_KEY, "{"),
            "41:19: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyDefault.java"), expected);
    }

    @Test
    public void testInitializersWithEmptyBody() throws Exception {
        final String[] expected = {
            "12:11: " + getCheckMessage(MSG_KEY, "{"),
            "15:11: " + getCheckMessage(MSG_KEY, "{"),
            "17:11: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyInitializers.java"), expected);
    }

    @Test
    public void testLambdaWithEmptyBody() throws Exception {
        final String[] expected = {
            "21:22: " + getCheckMessage(MSG_KEY, "{"),
            "25:22: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyLambda.java"), expected);
    }

    @Test
    public void testLoopsWithEmptyBody() throws Exception {
        final String[] expected = {
            "18:36: " + getCheckMessage(MSG_KEY, "{"),
            "20:36: " + getCheckMessage(MSG_KEY, "{"),
            "27:23: " + getCheckMessage(MSG_KEY, "{"),
            "30:11: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyLoops.java"), expected);
    }

    @Test
    public void testMethodWithEmptyBody() throws Exception {
        final String[] expected = {
            "14:19: " + getCheckMessage(MSG_KEY, "{"),
            "17:19: " + getCheckMessage(MSG_KEY, "{"),
            "29:36: " + getCheckMessage(MSG_KEY, "{"),
            "31:46: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyMethods.java"), expected);
    }

    @Test
    public void testMultiBlockStatementsWithEmptyBody() throws Exception {
        final String[] expected = {
            "19:20: " + getCheckMessage(MSG_KEY, "{"),
            "23:13: " + getCheckMessage(MSG_KEY, "{"),
            "28:22: " + getCheckMessage(MSG_KEY, "{"),
            "29:15: " + getCheckMessage(MSG_KEY, "{"),
            "34:28: " + getCheckMessage(MSG_KEY, "{"),
            "40:12: " + getCheckMessage(MSG_KEY, "{"),
            "42:28: " + getCheckMessage(MSG_KEY, "{"),
            "49:39: " + getCheckMessage(MSG_KEY, "{"),
            "55:16: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyMultiBlockStatements.java"), expected);
    }

    @Test
    public void testSwitchWithEmptyBody() throws Exception {
        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_KEY, "{"),
            "17:19: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodySwitch.java"), expected);
    }

    @Test
    public void testSynchronizedWithEmptyBody() throws Exception {
        final String[] expected = {
            "13:28: " + getCheckMessage(MSG_KEY, "{"),
            "16:28: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodySynchronized.java"), expected);
    }

    @Test
    public void testTypesWithEmptyBody() throws Exception {
        final String[] expected = {
            "12:12: " + getCheckMessage(MSG_KEY, "{"),
            "19:38: " + getCheckMessage(MSG_KEY, "{"),
            "29:16: " + getCheckMessage(MSG_KEY, "{"),
            "37:25: " + getCheckMessage(MSG_KEY, "{"),
            "48:11: " + getCheckMessage(MSG_KEY, "{"),
            "57:19: " + getCheckMessage(MSG_KEY, "{"),
            "64:14: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("InputWhitespaceBeforeEmptyBodyTypes.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_KEY, "{"),
            "21:25: " + getCheckMessage(MSG_KEY, "{"),
            "24:15: " + getCheckMessage(MSG_KEY, "{"),
            "27:19: " + getCheckMessage(MSG_KEY, "{"),
        };

        final String filename = "compact/InputWhitespaceBeforeEmptyBodyCompactSourceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }
}
