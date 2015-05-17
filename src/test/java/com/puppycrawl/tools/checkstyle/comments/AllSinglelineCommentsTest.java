////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.comments;

import java.io.File;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AllSinglelineCommentsTest extends BaseCheckTestSupport {
    protected static final Set<String> allComments = Sets.newLinkedHashSet();

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static class SinglelineCommentListenerCheck extends Check {
        @Override
        public boolean isCommentNodesRequired() {
            return true;
        }

        @Override
        public int[] getDefaultTokens() {
            return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public int[] getAcceptableTokens() {
            return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public void init() {
            int lines = 63;
            for (int i = 0; i < lines; i++) {
                allComments.add(i + LINE_SEPARATOR);
            }
            allComments.add(String.valueOf(lines));
        }

        @Override
        public void visitToken(DetailAST aAST) {
            String commentContent = aAST.getFirstChild().getText();
            if (!allComments.remove(commentContent)) {
                Assert.fail("Unexpected comment: " + commentContent);
            }
        }

    }

    @Test
    public void testAllBlockComments() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(SinglelineCommentListenerCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("comments" + File.separator
                + "InputFullOfSinglelineComments.java"), expected);
        Assert.assertTrue(allComments.isEmpty());
    }
}
