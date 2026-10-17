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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoWhitespaceAfterCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespaceafter";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "."),
            "11:11: " + getCheckMessage(MSG_KEY, "."),
            "30:13: " + getCheckMessage(MSG_KEY, "-"),
            "30:20: " + getCheckMessage(MSG_KEY, "+"),
            "35:13: " + getCheckMessage(MSG_KEY, "++"),
            "35:20: " + getCheckMessage(MSG_KEY, "--"),
            "118:21: " + getCheckMessage(MSG_KEY, "!"),
            "119:22: " + getCheckMessage(MSG_KEY, "~"),
            "136:23: " + getCheckMessage(MSG_KEY, "."),
            "139:10: " + getCheckMessage(MSG_KEY, "."),
            "143:11: " + getCheckMessage(MSG_KEY, "."),
            "271:1: " + getCheckMessage(MSG_KEY, "."),
            "296:5: " + getCheckMessage(MSG_KEY, "@"),
            "297:5: " + getCheckMessage(MSG_KEY, "@"),
            "298:5: " + getCheckMessage(MSG_KEY, "@"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestDefault.java"), expected);
    }

    @Test
    public void testAssignment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAssignment.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY, "."),
            "129:23: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllowLineBreaks.java"), expected);
    }

    @Test
    public void testTypecast() throws Exception {
        final String[] expected = {
            "87:20: " + getCheckMessage(MSG_KEY, ")"),
            "89:13: " + getCheckMessage(MSG_KEY, ")"),
            "241:17: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestTypecast.java"), expected);
    }

    @Test
    public void testArrayDeclarations() throws Exception {
        final String[] expected = {
            "57:27: " + getCheckMessage(MSG_KEY, "}"),
            "61:36: " + getCheckMessage(MSG_KEY, "}"),
            "62:39: " + getCheckMessage(MSG_KEY, "]"),
            "75:35: " + getCheckMessage(MSG_KEY, "mutArr"),
            "76:30: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations.java"), expected);
    }

    @Test
    public void testArrayDeclarations2() throws Exception {
        final String[] expected = {
            "45:15: " + getCheckMessage(MSG_KEY, "e"),
            "45:19: " + getCheckMessage(MSG_KEY, "]"),
            "65:15: " + getCheckMessage(MSG_KEY, "g"),
            "66:18: " + getCheckMessage(MSG_KEY, "]"),
            "67:15: " + getCheckMessage(MSG_KEY, "g"),
            "67:19: " + getCheckMessage(MSG_KEY, "]"),
            "67:23: " + getCheckMessage(MSG_KEY, "]"),
            "107:43: " + getCheckMessage(MSG_KEY, ")"),
            "107:52: " + getCheckMessage(MSG_KEY, "]"),
            "114:46: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations2.java"), expected);
    }

    @Test
    public void testArrayDeclarations3() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarations3.java"));
    }

    @Test
    public void testSynchronized() throws Exception {
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_KEY, "synchronized"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestSynchronized.java"), expected);
    }

    @Test
    public void testNpe() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestNpe.java"));
    }

    @Test
    public void testMethodReference() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestMethodRef.java"), expected);
    }

    @Test
    public void testMethodReferenceAfter() throws Exception {
        final String[] expected = {
            "25:35: " + getCheckMessage(MSG_KEY, "::"),
            "27:64: " + getCheckMessage(MSG_KEY, "::"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestMethodRefAfter.java"), expected);
    }

    @Test
    public void testArrayDeclarator() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestArrayDeclarator.java"), expected);
    }

    @Test
    public void testAllTokens() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "."),
            "11:11: " + getCheckMessage(MSG_KEY, "."),
            "30:13: " + getCheckMessage(MSG_KEY, "-"),
            "30:20: " + getCheckMessage(MSG_KEY, "+"),
            "35:13: " + getCheckMessage(MSG_KEY, "++"),
            "35:20: " + getCheckMessage(MSG_KEY, "--"),
            "94:20: " + getCheckMessage(MSG_KEY, ")"),
            "96:13: " + getCheckMessage(MSG_KEY, ")"),
            "97:13: " + getCheckMessage(MSG_KEY, ")"),
            "118:21: " + getCheckMessage(MSG_KEY, "!"),
            "119:22: " + getCheckMessage(MSG_KEY, "~"),
            "136:23: " + getCheckMessage(MSG_KEY, "."),
            "139:10: " + getCheckMessage(MSG_KEY, "."),
            "143:11: " + getCheckMessage(MSG_KEY, "."),
            "248:17: " + getCheckMessage(MSG_KEY, ")"),
            "271:1: " + getCheckMessage(MSG_KEY, "."),
            "296:5: " + getCheckMessage(MSG_KEY, "@"),
            "297:5: " + getCheckMessage(MSG_KEY, "@"),
            "298:5: " + getCheckMessage(MSG_KEY, "@"),
            "307:18: " + getCheckMessage(MSG_KEY, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTestAllTokens.java"), expected);
    }

    @Test
    public void testArrayDeclarationsAndAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterArrayDeclarationsAndAnno.java"), expected);
    }

    @Test
    public void testArrayNewTypeStructure() throws Exception {
        final String[] expected = {
            "53:17: " + getCheckMessage(MSG_KEY, "ci"),
            "56:62: " + getCheckMessage(MSG_KEY, "cZ"),
            "61:42: " + getCheckMessage(MSG_KEY, "transformers"),
            "63:39: " + getCheckMessage(MSG_KEY, "transformers"),
            "92:21: " + getCheckMessage(MSG_KEY, ")"),
            "128:28: " + getCheckMessage(MSG_KEY, ")"),
            "127:28: " + getCheckMessage(MSG_KEY, ")"),
            "132:29: " + getCheckMessage(MSG_KEY, ")"),
            "228:15: " + getCheckMessage(MSG_KEY, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewTypeStructure.java"), expected);
    }

    @Test
    public void testArrayNewGenericTypeArgument() throws Exception {

        final String[] expected = {
            "67:13: " + getCheckMessage(MSG_KEY, "!"),
            "71:13: " + getCheckMessage(MSG_KEY, "!"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterNewGenericTypeArgument.java"), expected);
    }

    @Test
    public void testNoWhitespaceAfterWithEmoji() throws Exception {

        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_KEY, "{"),
            "29:22: " + getCheckMessage(MSG_KEY, ")"),
            "29:23: " + getCheckMessage(MSG_KEY, "@"),
            "39:17: " + getCheckMessage(MSG_KEY, "!"),
            "39:22: " + getCheckMessage(MSG_KEY, "."),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterWithEmoji.java"), expected);
    }

    @Test
    public void testLiteralToken() throws Exception {
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "catch"),
            "26:11: " + getCheckMessage(MSG_KEY, "while"),
            "34:9: " + getCheckMessage(MSG_KEY, "for"),
            "44:9: " + getCheckMessage(MSG_KEY, "if"),
            "55:9: " + getCheckMessage(MSG_KEY, "while"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfter.java"), expected);
    }

    @Test
    public void testLiteralTokenFalse() throws Exception {
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "catch"),
            "26:11: " + getCheckMessage(MSG_KEY, "while"),
            "29:11: " + getCheckMessage(MSG_KEY, "while"),
            "34:9: " + getCheckMessage(MSG_KEY, "for"),
            "44:9: " + getCheckMessage(MSG_KEY, "if"),
            "47:9: " + getCheckMessage(MSG_KEY, "if"),
            "55:9: " + getCheckMessage(MSG_KEY, "while"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterTwo.java"), expected);
    }

    @Test
    public void testNoWhitespaceAfterSynchronized() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, "synchronized"),
        };

        verifyWithInlineConfigParser(
                getPath("InputNoWhitespaceAfterSynchronized.java"),
                expected);
    }

    @Test
    public void testNoWhitespaceAfterUnnamedPattern() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNoWhitespaceAfterUnnamedPattern.java"),
                expected);
    }

}
