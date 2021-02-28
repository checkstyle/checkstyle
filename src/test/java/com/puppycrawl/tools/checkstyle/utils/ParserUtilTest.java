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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ParserUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(ParserUtil.class, true),
            "Constructor is not private");
    }

    @Test
    public void testCreationOfFakeCommentBlock() {
        final DetailAST testCommentBlock =
            ParserUtil.createBlockCommentNode("test_comment");
        assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, testCommentBlock.getType(),
            "Invalid token type");
        assertEquals("/*", testCommentBlock.getText(), "Invalid text");
        assertEquals(0, testCommentBlock.getLineNo(), "Invalid line number");

        final DetailAST contentCommentBlock = testCommentBlock.getFirstChild();
        assertEquals(TokenTypes.COMMENT_CONTENT, contentCommentBlock.getType(),
            "Invalid token type");
        assertEquals("*test_comment", contentCommentBlock.getText(), "Invalid text");
        assertEquals(0, contentCommentBlock.getLineNo(), "Invalid line number");
        assertEquals(-1, contentCommentBlock.getColumnNo(), "Invalid column number");

        final DetailAST endCommentBlock = contentCommentBlock.getNextSibling();
        assertEquals(TokenTypes.BLOCK_COMMENT_END, endCommentBlock.getType(), "Invalid token type");
        assertEquals("*/", endCommentBlock.getText(), "Invalid text");
    }
}
