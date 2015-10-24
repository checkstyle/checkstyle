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

package com.puppycrawl.tools.checkstyle.grammars.comments;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AllBlockCommentsTest extends BaseCheckTestSupport {
    private static final Set<String> ALL_COMMENTS = Sets.newLinkedHashSet();

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("grammars" + File.separator
                + "comments" + File.separator + filename);
    }

    @Test
    public void testAllBlockComments() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(BlockCommentListenerCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFullOfBlockComments.java"), expected);
        Assert.assertTrue(ALL_COMMENTS.isEmpty());
    }

    private static class BlockCommentListenerCheck extends Check {
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
            ALL_COMMENTS.addAll(Arrays.asList("0", "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                    "16", "17", "18", "19", "20",
                    LINE_SEPARATOR + "21" + LINE_SEPARATOR,
                    "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                    "33", "34", "35", "36", "37", "38", "  39  ", "40", "41",
                    "42", "43", "44", "45", "46", "47", "48", "49", "50",
                    "51", "52", "53", "54", "55", "56", "57", "58", "59",
                    "60", "61"));
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
