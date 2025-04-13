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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ParserUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(ParserUtil.class))
                .isTrue();
    }

    @Test
    public void testCreationOfFakeCommentBlock() {
        final DetailAST testCommentBlock =
            ParserUtil.createBlockCommentNode("test_comment");
        assertWithMessage("Invalid token type")
                .that(testCommentBlock.getType())
                .isEqualTo(TokenTypes.BLOCK_COMMENT_BEGIN);
        assertWithMessage("Invalid text")
                .that(testCommentBlock.getText())
                .isEqualTo("/*");
        assertWithMessage("Invalid line number")
                .that(testCommentBlock.getLineNo())
                .isEqualTo(0);

        final DetailAST contentCommentBlock = testCommentBlock.getFirstChild();
        assertWithMessage("Invalid token type")
                .that(contentCommentBlock.getType())
                .isEqualTo(TokenTypes.COMMENT_CONTENT);
        assertWithMessage("Invalid text")
                .that(contentCommentBlock.getText())
                .isEqualTo("*test_comment");
        assertWithMessage("Invalid line number")
                .that(contentCommentBlock.getLineNo())
                .isEqualTo(0);
        assertWithMessage("Invalid column number")
                .that(contentCommentBlock.getColumnNo())
                .isEqualTo(-1);

        final DetailAST endCommentBlock = contentCommentBlock.getNextSibling();
        assertWithMessage("Invalid token type")
                .that(endCommentBlock.getType())
                .isEqualTo(TokenTypes.BLOCK_COMMENT_END);
        assertWithMessage("Invalid text")
                .that(endCommentBlock.getText())
                .isEqualTo("*/");
    }
}
