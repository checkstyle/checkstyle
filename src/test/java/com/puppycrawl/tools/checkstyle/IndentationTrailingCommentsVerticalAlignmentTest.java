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

package com.puppycrawl.tools.checkstyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class IndentationTrailingCommentsVerticalAlignmentTest {

    private static final String INDENTATION_TEST_FILES_PATH =
        "com/puppycrawl/tools/checkstyle/checks/indentation/indentation";

    private static final int TAB_WIDTH = 4;

    private static final Set<String> ALLOWED_VIOLATION_FILES = Set.of(
        // reason: IndentationCheckTest tests fail after alignment
        "InputIndentationAnnArrInit.java",
        "InputIndentationDifficultAnnotations.java",
        "InputIndentationZeroArrayInit.java",
        "InputIndentationAnnArrInitWithEmoji.java",
        "InputIndentationOddLineWrappingAndArrayInit.java",
        "InputIndentationArrayInitIndentWithEmoji.java",
        "InputIndentationInvalidArrayInitIndent.java",
        "InputIndentationInvalidArrayInitIndentTwoDimensional.java",
        "InputIndentationAnnArrInit2.java",
        "InputIndentationInvalidArrayInitIndent1.java",

        // reason: checkstyle check: Line gets longer than 100 characters
        "InputIndentationAnnotationClosingParenthesisEndsInSameIndentationAsOpening.java",
        "InputIndentationAnnotationFieldDefinition.java",
        "InputIndentationAnnotationScopeIndentationCheck.java",
        "InputIndentationAnonymousClassInMethodCurlyOnNewLine.java",
        "InputIndentationAnonymousClasses.java",
        "InputIndentationBraceAdjustment.java",
        "InputIndentationChainedMethodCalls.java",
        "InputIndentationCtorCall.java",
        "InputIndentationCustomAnnotation.java",
        "InputIndentationFromGuava1.java",
        "InputIndentationIfAndParameter.java",
        "InputIndentationInvalidArrayInitIndentWithoutTrailingComments.java",
        "InputIndentationInvalidClassDefIndent.java",
        "InputIndentationInvalidClassDefIndent1.java",
        "InputIndentationInvalidForIndent.java",
        "InputIndentationInvalidIfIndent.java",
        "InputIndentationInvalidIfIndent2.java",
        "InputIndentationInvalidLabelIndent.java",
        "InputIndentationInvalidMethodIndent2.java",
        "InputIndentationLambda.java",
        "InputIndentationLambda1.java",
        "InputIndentationLambda2.java",
        "InputIndentationLineWrappedRecordDeclaration.java",
        "InputIndentationMethodPrecededByAnnotationWithParameterOnSeparateLine.java",
        "InputIndentationNewChildren.java",
        "InputIndentationNewWithForceStrictCondition.java",
        "InputIndentationStrictCondition.java",
        "InputIndentationTryResourcesNotStrict.java",
        "InputIndentationTryResourcesNotStrict1.java",
        "InputIndentationTryWithResourcesStrict.java",
        "InputIndentationTryWithResourcesStrict1.java",
        "InputIndentationValidBlockIndent1.java",
        "InputIndentationValidClassDefIndent.java",
        "InputIndentationValidClassDefIndent1.java",
        "InputIndentationCorrectIfAndParameter1.java",
        "InputIndentationPackageDeclaration3.java"
    );

    @ParameterizedTest
    @MethodSource("indentationTestFiles")
    public void testTrailingCommentsAlignment(Path testFile) throws IOException {
        final String fileName = testFile.getFileName().toString();
        if (ALLOWED_VIOLATION_FILES.contains(fileName)) {
            Assumptions.assumeTrue(false, "Skipping file: " + fileName);
        }

        final List<String> lines = Files.readAllLines(testFile);
        int expectedStartIndex = -1;

        for (int idx = 0; idx < lines.size(); idx++) {
            final String line = lines.get(idx);
            final int commentStartIndex = line.indexOf("//indent:");
            if (commentStartIndex > 0) {
                final String codePart = line.substring(0, commentStartIndex);
                if (!codePart.isBlank()) {
                    final int actualStartIndex =
                        CommonUtil.lengthExpandedTabs(line, commentStartIndex, TAB_WIDTH);
                    if (expectedStartIndex == -1) {
                        expectedStartIndex = actualStartIndex;
                    }
                    else {
                        Assertions.assertEquals(expectedStartIndex, actualStartIndex,
                            "Trailing comment alignment mismatch in file: "
                                + testFile + " on line " + (idx + 1));
                    }
                }
            }
        }
    }

    private static Stream<Path> indentationTestFiles() {
        final Path resourcesDir = Path.of("src", "test", "resources");
        final Path indentationDir = resourcesDir.resolve(INDENTATION_TEST_FILES_PATH);

        Stream<Path> testFiles;
        try {
            testFiles = Files.walk(indentationDir)
                .filter(path -> {
                        final String fileName = path.getFileName().toString();
                        return fileName.startsWith("InputIndentation")
                            && fileName.endsWith(".java");
                    }
                );
        }
        catch (IOException exception) {
            Assertions.fail("Failed to find indentation test files", exception);
            testFiles = Stream.empty();
        }
        return testFiles;
    }
}
