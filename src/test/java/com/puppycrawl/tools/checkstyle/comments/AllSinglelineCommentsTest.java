////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AllSinglelineCommentsTest extends BaseCheckTestSupport
{
    protected static final Set<String> allComments = Sets.newLinkedHashSet();

    public static class SinglelineCommentListenerCheck extends Check
    {
        @Override
        public boolean isCommentNodesRequired()
        {
            return true;
        }

        @Override
        public int[] getDefaultTokens()
        {
            return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public int[] getAcceptableTokens()
        {
            return new int[] {TokenTypes.SINGLE_LINE_COMMENT};
        }

        @Override
        public void init()
        {
            if (!SystemUtils.IS_OS_WINDOWS) {
                allComments.addAll(Arrays.asList("0\n", "1\n", "2\n", "3\n", "4\n", "5\n", "6\n",
                        "7\n", "8\n", "9\n", "10\n", "12\n", "13\n", "14\n", "15\n", "16\n",
                        "17\n", "18\n", "19\n", "20\n", "21\n", "22\n", "23\n", "24\n", "25\n",
                        "26\n", "27\n", "28\n", "29\n", "30\n", "31\n", "32\n", "33\n", "34\n",
                        "35\n", "36\n", "37\n", "38\n", "39\n", "40\n", "41\n", "42\n", "43\n",
                        "44\n", "45\n", "46\n", "47\n", "48\n", "49\n", "50\n", "51\n", "52\n",
                        "53\n", "54\n", "55\n", "56\n", "57\n", "58\n", "59\n", "60\n", "61\n",
                        "62\n", "63"));
            }
            else {
                allComments.addAll(Arrays.asList("0\r\n", "1\r\n", "2\r\n", "3\r\n", "4\r\n",
                        "5\r\n", "6\r\n", "7\r\n", "8\r\n", "9\r\n", "10\r\n", "12\r\n",
                        "13\r\n", "14\r\n", "15\r\n", "16\r\n", "17\r\n", "18\r\n",
                        "19\r\n", "20\r\n", "21\r\n", "22\r\n", "23\r\n", "24\r\n", "25\r\n",
                        "26\r\n", "27\r\n", "28\r\n", "29\r\n", "30\r\n", "31\r\n", "32\r\n",
                        "33\r\n", "34\r\n", "35\r\n", "36\r\n", "37\r\n", "38\r\n", "39\r\n",
                        "40\r\n", "41\r\n", "42\r\n", "43\r\n", "44\r\n", "45\r\n", "46\r\n",
                        "47\r\n", "48\r\n", "49\r\n", "50\r\n", "51\r\n", "52\r\n", "53\r\n",
                        "54\r\n", "55\r\n", "56\r\n", "57\r\n", "58\r\n", "59\r\n", "60\r\n",
                        "61\r\n", "62\r\n", "63"));
            }
        }

        @Override
        public void visitToken(DetailAST aAST)
        {
            String commentContent = aAST.getFirstChild().getText();
            if (!allComments.remove(commentContent)) {
                Assert.fail("Unexpected comment: " + commentContent);
            }
        }

    }

    @Test
    public void testAllBlockComments() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(SinglelineCommentListenerCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("comments" + File.separator
                + "InputFullOfSinglelineComments.java"), expected);
        Assert.assertTrue(allComments.isEmpty());
    }
}
