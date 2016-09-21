////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammars.comments;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class AllSinglelineCommentsTest extends BaseCheckTestSupport {
    private static final Set<String> ALL_COMMENTS = new LinkedHashSet<>();

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("grammars" + File.separator
                + "comments" + File.separator + filename);
    }

    @Test
    public void testAllBlockComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SinglelineCommentListenerCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFullOfSinglelineComments.java"), expected);
        Assert.assertTrue(ALL_COMMENTS.isEmpty());
    }

    @Test
    public void testAstTree() throws Exception {
        verifyAst(getPath("InputFullOfSinglelineCommentsAst.txt"),
                getPath("InputFullOfSinglelineComments.java"), true);
    }

    private static class SinglelineCommentListenerCheck extends AbstractCheck {
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
            return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public int[] getRequiredTokens() {
            return getAcceptableTokens();
        }

        @Override
        public void init() {
            final int lines = 63;
            for (int i = 0; i < lines; i++) {
                ALL_COMMENTS.add(i + LINE_SEPARATOR);
            }
            ALL_COMMENTS.add(String.valueOf(lines));
        }

        @Override
        public void visitToken(DetailAST ast) {
            final String commentContent = ast.getFirstChild().getText();
            if (!ALL_COMMENTS.remove(commentContent)) {
                Assert.fail("Unexpected comment: " + commentContent);
            }
        }
    }
}
