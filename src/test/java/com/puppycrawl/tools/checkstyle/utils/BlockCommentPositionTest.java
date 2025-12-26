///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class BlockCommentPositionTest extends AbstractModuleTestSupport {

    @Test
    public void testPrivateConstr() throws Exception {
        assertWithMessage("Constructor is not private")
                .that(TestUtil.isUtilsClassHasPrivateConstructor(BlockCommentPosition.class))
                .isTrue();
    }

    @Test
    public void testJavaDocsRecognition() throws Exception {
        final List<BlockCommentPositionTestMetadata> metadataList = Arrays.asList(
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnClass.java",
                        BlockCommentPosition::isOnClass, 4),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnMethod.java",
                        BlockCommentPosition::isOnMethod, 6),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnField.java",
                        BlockCommentPosition::isOnField, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnEnum.java",
                        BlockCommentPosition::isOnEnum, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnConstructor.java",
                        BlockCommentPosition::isOnConstructor, 5),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnInterface.java",
                        BlockCommentPosition::isOnInterface, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnAnnotation.java",
                        BlockCommentPosition::isOnAnnotationDef, 3),
                new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnEnumMember.java",
                        BlockCommentPosition::isOnEnumConstant, 2),
                new BlockCommentPositionTestMetadata(
                        "InputBlockCommentPositionOnAnnotationField.java",
                        BlockCommentPosition::isOnAnnotationField, 4),
                new BlockCommentPositionTestMetadata(
                        "inputs/normal/package-info.java",
                        BlockCommentPosition::isOnPackage, 1),
                new BlockCommentPositionTestMetadata(
                        "inputs/annotation/package-info.java",
                        BlockCommentPosition::isOnPackage, 1)
        );

        for (BlockCommentPositionTestMetadata metadata : metadataList) {
            final DetailAST ast = JavaParser.parseFile(new File(getPath(metadata.getFileName())),
                JavaParser.Options.WITH_COMMENTS);
            final var matches = getJavadocsCount(ast, metadata.getAssertion());
            assertWithMessage("Invalid javadoc count")
                    .that(matches)
                    .isEqualTo(metadata.getMatchesNum());
        }
    }

    @Test
    public void testJavaDocsRecognitionNonCompilable() throws Exception {
        final List<BlockCommentPositionTestMetadata> metadataList = Arrays.asList(
            new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnRecord.java",
                BlockCommentPosition::isOnRecord, 3),
            new BlockCommentPositionTestMetadata("InputBlockCommentPositionOnCompactCtor.java",
                BlockCommentPosition::isOnCompactConstructor, 3)
        );

        for (BlockCommentPositionTestMetadata metadata : metadataList) {
            final DetailAST ast = JavaParser.parseFile(
                new File(getPath(metadata.getFileName())),
                    JavaParser.Options.WITH_COMMENTS);
            final var matches = getJavadocsCount(ast, metadata.getAssertion());
            assertWithMessage("Invalid javadoc count")
                    .that(matches)
                    .isEqualTo(metadata.getMatchesNum());
        }
    }

    private static int getJavadocsCount(DetailAST detailAST,
                                        Function<DetailAST, Boolean> assertion) {
        var matchFound = 0;
        DetailAST node = detailAST;
        while (node != null) {
            if (node.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                    && JavadocUtil.isJavadocComment(node)) {
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

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/blockcommentposition";
    }

    /**
     * Metadata for block comment position tests.
     *
     * @param fileName   the test file name
     * @param assertion  the assertion function for DetailAST
     * @param matchesNum number of expected matches
     */
    private record BlockCommentPositionTestMetadata(
            String fileName,
            Function<DetailAST, Boolean> assertion,
            int matchesNum) {

        /**
         * Legacy getter for fileName, for backward compatibility.
         */
        private String getFileName() {
            return fileName;
        }

        /**
         * Legacy getter for assertion, for backward compatibility.
         */
        private Function<DetailAST, Boolean> getAssertion() {
            return assertion;
        }

        /**
         * Legacy getter for matchesNum, for backward compatibility.
         */
        private int getMatchesNum() {
            return matchesNum;
        }
    }
}
