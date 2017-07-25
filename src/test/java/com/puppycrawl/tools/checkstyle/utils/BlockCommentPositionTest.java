////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.TestUtils;

public class BlockCommentPositionTest {

    @Test
    public void testJavaDocsRecognition() throws Exception {
        final List<BlockCommentPositionTestMetadata> metadataList = Arrays.asList(
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnClass.java",
                        BlockCommentPosition::isOnClass, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnMethod.java",
                        BlockCommentPosition::isOnMethod, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnField.java",
                        BlockCommentPosition::isOnField, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnEnum.java",
                        BlockCommentPosition::isOnEnum, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnConstructor.java",
                        BlockCommentPosition::isOnConstructor, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnInterface.java",
                        BlockCommentPosition::isOnInterface, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnAnnotation.java",
                        BlockCommentPosition::isOnAnnotationDef, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnEnumMember.java",
                        BlockCommentPosition::isOnEnumConstant, 2)
        );

        for (BlockCommentPositionTestMetadata metadata : metadataList) {
            final DetailAST ast = TestUtils.parseFile(
                    new File(getPath(metadata.getFileName()))
            );
            final int matches = getJavadocsCount(ast, metadata.getAssertion());
            assertEquals("Invalid javadoc count", metadata.getMatchesNum(), matches);
        }
    }

    private static int getJavadocsCount(DetailAST detailAST,
                                        Function<DetailAST, Boolean> assertion) {
        int matchFound = 0;
        DetailAST node = detailAST;
        while (node != null) {
            if (node.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtils.isJavadocComment(node)) {
                if (!assertion.apply(node)) {
                    throw new IllegalStateException("Position of comment is defined correctly");
                }
                matchFound++;
            }
            matchFound += getJavadocsCount(node.getFirstChild(), assertion);
            node = node.getNextSibling();
        }
        return matchFound;
    }

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/utils/blockcommentposition/"
                + filename;
    }

    private static final class BlockCommentPositionTestMetadata {

        private final String fileName;
        private final Function<DetailAST, Boolean> assertion;
        private final int matchesNum;

        BlockCommentPositionTestMetadata(String fileName, Function<DetailAST,
                Boolean> assertion, int matchesNum) {
            this.fileName = fileName;
            this.assertion = assertion;
            this.matchesNum = matchesNum;
        }

        public String getFileName() {
            return fileName;
        }

        public Function<DetailAST, Boolean> getAssertion() {
            return assertion;
        }

        public int getMatchesNum() {
            return matchesNum;
        }
    }
}
