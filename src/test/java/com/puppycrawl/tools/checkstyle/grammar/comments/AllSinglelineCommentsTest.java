///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class AllSinglelineCommentsTest extends AbstractModuleTestSupport {

    private static final Set<String> ALL_COMMENTS = new LinkedHashSet<>();

    private static String lineSeparator;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/comments";
    }

    @Test
    public void testAllSinglelineComments() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(SinglelineCommentListenerCheck.class);
        final String path = getPath("InputFullOfSinglelineComments.java");
        lineSeparator = CheckUtil.getLineSeparatorForFile(path, StandardCharsets.UTF_8);
        execute(checkConfig, path);
        assertWithMessage("All comments should be empty")
            .that(ALL_COMMENTS)
            .isEmpty();
    }

    public static class SinglelineCommentListenerCheck extends AbstractCheck {

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
                ALL_COMMENTS.add(i + lineSeparator);
            }
            ALL_COMMENTS.add(String.valueOf(lines));
        }

        @Override
        public void visitToken(DetailAST ast) {
            final String commentContent = ast.getFirstChild().getText();
            assertWithMessage("Unexpected comment: %s", commentContent)
                    .that(ALL_COMMENTS.remove(commentContent))
                    .isTrue();
        }

    }

}
