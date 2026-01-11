///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.provider.MethodSource;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class IndentationTrailingCommentsVerticalAlignmentTest {

    private static final String INDENTATION_TEST_FILES_PATH =
        "com/puppycrawl/tools/checkstyle/checks/indentation/indentation";

    private static final int TAB_WIDTH = 4;

    private static final Set<String> ALLOWED_VIOLATION_FILES = Set.of(
        // reason: checkstyle check: Line gets longer than 100 characters
        "InputIndentationInvalidLabelIndent.java",
        "InputIndentationInvalidMethodIndent2.java",
        "InputIndentationNewChildren.java",
        "InputIndentationNewWithForceStrictCondition.java",
        "InputIndentationStrictCondition.java",
        "InputIndentationTryResourcesNotStrict.java",
        "InputIndentationTryResourcesNotStrict1.java",
        "InputIndentationTryWithResourcesStrict.java",
        "InputIndentationTryWithResourcesStrict1.java",
        "InputIndentationValidClassDefIndent.java",
        "InputIndentationValidClassDefIndent1.java",
        "InputIndentationCorrectIfAndParameter1.java",
        "InputIndentationPackageDeclaration3.java"
    );

    @MethodSource("indentationTestFiles")
    @ParameterizedTest
    public void testTrailingCommentsAlignment(Path testFile) throws IOException {
        final String fileName = testFile.getFileName().toString();
        if (ALLOWED_VIOLATION_FILES.contains(fileName)) {
            Assumptions.assumeTrue(false, "Skipping file: " + fileName);
        }
        final List<String> lines = Files.readAllLines(testFile);
        int expectedStartIndex = -1;

        for (int idx = 0; idx < lines.size(); idx++) {
            final String line = lines.get(idx);
            if (line.trim().startsWith("import ") || line.trim().startsWith("package ")) {
                continue;
            }
            final int commentStartIndex = line.indexOf("//indent:");
            if (commentStartIndex > 0) {
                final String codePart = line.substring(0, commentStartIndex);
                if (!codePart.isBlank()) {
                    int actualStartIndex =
                        CommonUtil.lengthExpandedTabs(line, commentStartIndex, TAB_WIDTH);

                    // for unicode characters having supplementary code points
                    final long extraWidth = codePart.codePoints().filter(
                        Character::isSupplementaryCodePoint).count();
                    actualStartIndex -= Math.toIntExact(extraWidth);

                    if (expectedStartIndex == -1) {
                        expectedStartIndex = actualStartIndex;
                    }
                    else {
                        assertWithMessage(
                                "Trailing comment alignment mismatch in file: %s on line %s",
                                testFile, idx + 1)
                                .that(actualStartIndex)
                                .isEqualTo(expectedStartIndex);
                    }
                }
            }
        }
    }

    private static Stream<Path> indentationTestFiles() {
        final Path resourcesDir = Path.of("src", "test", "resources");
        final Path indentationDir = resourcesDir.resolve(INDENTATION_TEST_FILES_PATH);
        Stream<Path> result;
        try (Stream<Path> testFiles = Files.walk(indentationDir)) {
            final List<Path> collected = testFiles
                .filter(path -> {
                    final String fileName = path.getFileName().toString();
                    return fileName.startsWith("InputIndentation")
                            && fileName.endsWith(".java");
                }).toList();
            result = collected.stream();
        }
        catch (IOException exception) {
            assertWithMessage("Failed to find indentation test files")
                    .that(exception)
                    .isNull();
            result = Stream.empty();
        }
        return result;
    }
}
