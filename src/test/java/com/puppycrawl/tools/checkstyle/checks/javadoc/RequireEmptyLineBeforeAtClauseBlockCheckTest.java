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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.RequireEmptyLineBeforeAtClauseBlockCheck.MSG_JAVADOC_TAG_LINE_BEFORE;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RequireEmptyLineBeforeAtClauseBlockCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/requireemptylinebeforeatclauseblock";
    }

    @Test
    public void testGetRequiredTokens() {
        final RequireEmptyLineBeforeAtClauseBlockCheck checkObj =
                new RequireEmptyLineBeforeAtClauseBlockCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                RequireEmptyLineBeforeAtClauseBlockCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputRequireEmptyLineBeforeAtClauseBlockCorrect.java"),
                expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RequireEmptyLineBeforeAtClauseBlockCheck.class);
        final String[] expected = {
            "5: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@since"),
            "11: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@param"),
            "19: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@param"),
        };
        verify(checkConfig,
                getPath("InputRequireEmptyLineBeforeAtClauseBlockIncorrect.java"),
                expected);
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

        assertTrue(
            "A javadoc tag with no previous sibling has insufficient empty newlines.",
            RequireEmptyLineBeforeAtClauseBlockCheck.insufficientConsecutiveNewlines(paramNode));
    }
}
