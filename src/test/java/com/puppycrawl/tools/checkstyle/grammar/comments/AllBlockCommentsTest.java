///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammar.comments;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AllBlockCommentsTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/comments";
    }

    @Test
    public void testAllBlockComments() throws Exception {
        final String path = getPath("InputFullOfBlockComments.java");
        final String[] expected = {
            "7:1: " + "0",
            "7:6: " + "1",
            "7:18: " + "2",
            "7:27: " + "3",
            "7:33: " + "4",
            "7:48: " + "5",
            "7:54: " + "6",
            "7:64: " + "7",
            "7:70: " + "8",
            "7:93: " + "9",
            "7:99: " + "10",
            "7:113: " + "11",
            "7:120: " + "12",
        };
        verifyWithInlineConfigParser(path, expected);
    }

    public static class BlockCommentListenerCheck extends AbstractCheck {

        @Override
        public boolean isCommentNodesRequired() {
            return true;
        }

        @Override
        public int[] getDefaultTokens() {
            return getAcceptableTokens();
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.BLOCK_COMMENT_BEGIN};
        }

        @Override
        public int[] getRequiredTokens() {
            return getAcceptableTokens();
        }

        @Override
        public void visitToken(DetailAST ast) {
            final String commentContent = ast.getFirstChild().getText();
            if(!commentContent.contains("com")){
                log(ast, "{0}", commentContent);
            }
        }

    }
}
