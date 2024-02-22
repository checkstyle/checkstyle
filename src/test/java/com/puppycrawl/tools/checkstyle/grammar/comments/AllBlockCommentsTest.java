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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AllBlockCommentsTest extends AbstractModuleTestSupport {

    private static final Set<String> ALL_COMMENTS = new LinkedHashSet<>();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/comments";
    }

    @Test
    public void testAllBlockComments() throws Exception {
        final String path = getPath("InputFullOfBlockComments.java");
        final String[] expected = {
            "8:13: " + BlockCommentListenerCheck.VIOLATION_MESSAGE + "violation1",
            "8:34: " + BlockCommentListenerCheck.VIOLATION_MESSAGE + "violation2",
            "17:13: " + BlockCommentListenerCheck.VIOLATION_MESSAGE +  "violation3",
            "22:1: " + BlockCommentListenerCheck.VIOLATION_MESSAGE +  "violation4",
        };
        verifyWithInlineConfigParser(path, expected);
    }

    public static class BlockCommentListenerCheck extends AbstractCheck {
        private static final String VIOLATION_MESSAGE = "violation found in comment: ";

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
        public void init() {
            IntStream.range(0, 62)
                .forEach(i -> ALL_COMMENTS.add(String.valueOf(i)));
        }

        @Override
        public void visitToken(DetailAST ast) {
            final String commentContent = ast.getFirstChild().getText();
            if (commentContent.contains("violation")) {
                log(ast, "violation found in comment: {0}", commentContent);
            }
        }

    }

}
