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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_JAVADOC_TAG_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_MISPLACED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_REDUNDANT_PARAGRAPH;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_TAG_AFTER;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParagraphCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocParagraphCheck checkObj = new JavadocParagraphCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocParagraphCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        checkConfig.addAttribute("atClauseRequiresEmptyLineBefore", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_MISPLACED_TAG),
            "7: " + getCheckMessage(MSG_LINE_BEFORE),
            "8: " + getCheckMessage(MSG_MISPLACED_TAG),
            "8: " + getCheckMessage(MSG_LINE_BEFORE),
            "14: " + getCheckMessage(MSG_MISPLACED_TAG),
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "16: " + getCheckMessage(MSG_MISPLACED_TAG),
            "23: " + getCheckMessage(MSG_LINE_BEFORE),
            "25: " + getCheckMessage(MSG_MISPLACED_TAG),
            "32: " + getCheckMessage(MSG_MISPLACED_TAG),
            "32: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "33: " + getCheckMessage(MSG_MISPLACED_TAG),
            "33: " + getCheckMessage(MSG_LINE_BEFORE),
            "34: " + getCheckMessage(MSG_MISPLACED_TAG),
            "34: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_MISPLACED_TAG),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "39: " + getCheckMessage(MSG_MISPLACED_TAG),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_MISPLACED_TAG),
            "45: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "48: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50: " + getCheckMessage(MSG_MISPLACED_TAG),
            "50: " + getCheckMessage(MSG_LINE_BEFORE),
            "51: " + getCheckMessage(MSG_MISPLACED_TAG),
            "51: " + getCheckMessage(MSG_LINE_BEFORE),
            "52: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@see"),
            "61: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "62: " + getCheckMessage(MSG_TAG_AFTER),
            "70: " + getCheckMessage(MSG_MISPLACED_TAG),
            "70: " + getCheckMessage(MSG_LINE_BEFORE),
            "72: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75: " + getCheckMessage(MSG_MISPLACED_TAG),
            "75: " + getCheckMessage(MSG_LINE_BEFORE),
            "81: " + getCheckMessage(MSG_TAG_AFTER),
            "82: " + getCheckMessage(MSG_TAG_AFTER),
            "109: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@param"),
        };
        verify(checkConfig, getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    /**
     * Do not check for newline before a paragraph tag when the attribute is set to false. Do not
     * check for an empty line before the group of at-clauses by default, when
     * atClauseRequiresEmptyLineBefore is not set.
     *
     * @throws Exception should not be thrown.
     */
    @Test
    public void testAllowNewlineParagraph() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParagraphCheck.class);
        checkConfig.addAttribute("allowNewlineParagraph", "false");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_LINE_BEFORE),
            "8: " + getCheckMessage(MSG_LINE_BEFORE),
            "14: " + getCheckMessage(MSG_LINE_BEFORE),
            "23: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_LINE_BEFORE),
            "32: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "33: " + getCheckMessage(MSG_LINE_BEFORE),
            "34: " + getCheckMessage(MSG_LINE_BEFORE),
            "35: " + getCheckMessage(MSG_LINE_BEFORE),
            "39: " + getCheckMessage(MSG_LINE_BEFORE),
            "45: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "50: " + getCheckMessage(MSG_LINE_BEFORE),
            "51: " + getCheckMessage(MSG_LINE_BEFORE),
            "61: " + getCheckMessage(MSG_REDUNDANT_PARAGRAPH),
            "62: " + getCheckMessage(MSG_TAG_AFTER),
            "70: " + getCheckMessage(MSG_LINE_BEFORE),
            "75: " + getCheckMessage(MSG_LINE_BEFORE),
            "81: " + getCheckMessage(MSG_TAG_AFTER),
            "82: " + getCheckMessage(MSG_TAG_AFTER),
        };
        verify(checkConfig, getPath("InputJavadocParagraphIncorrect.java"), expected);
    }

    /**
     * Covers the branch in insufficientConsecutiveNewlines's while loop when getPreviousSibling
     * returns null before hitting a node that is not WS, NEWLINE, nor LEADING_ASTERISK.
     */
    @Test
    public void testNullPreviousSibling() {
        // JAVADOC
        //   \__JAVADOC_TAG
        //       \__PARAM_LITERAL
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setType(JavadocTokenTypes.JAVADOC);
        final JavadocNodeImpl paramNode = new JavadocNodeImpl();
        paramNode.setType(JavadocTokenTypes.JAVADOC_TAG);
        javadocNode.setLineNumber(1);
        paramNode.setParent(javadocNode);
        javadocNode.setChildren(paramNode);
        final JavadocNodeImpl paramLiteralNode = new JavadocNodeImpl();
        paramLiteralNode.setType(JavadocTokenTypes.PARAM_LITERAL);
        paramLiteralNode.setLineNumber(1);
        paramLiteralNode.setText("@param");
        paramNode.setChildren(paramLiteralNode);

        assertTrue("A javadoc tag with no previous sibling has insufficient empty newlines.",
                JavadocParagraphCheck.insufficientConsecutiveNewlines(paramNode));
    }
}
