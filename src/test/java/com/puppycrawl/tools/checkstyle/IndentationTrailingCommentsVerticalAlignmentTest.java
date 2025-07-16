package com.puppycrawl.tools.checkstyle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class IndentationTrailingCommentsVerticalAlignmentTest {

    private static final String INDENTATION_TEST_FILES_PATH =
            "com/puppycrawl/tools/checkstyle/checks/indentation/indentation";

    private static final int TAB_WIDTH = 4;

    @ParameterizedTest
    @MethodSource("indentationTestFiles")
    void testTrailingCommentsAlignment(Path testFile) throws IOException {
        final List<String> lines = Files.readAllLines(testFile);
        int expectedStartIndex = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int commentStartIndex = line.indexOf("//indent:");
            if (commentStartIndex > 0) {
                String codePart = line.substring(0, commentStartIndex);
                if(!codePart.trim().isEmpty()) {
                    int actualStartIndex= CommonUtil.lengthExpandedTabs(line, commentStartIndex, TAB_WIDTH);
                    if (expectedStartIndex == -1) {
                        expectedStartIndex = actualStartIndex;
                    } else {
                        assertEquals(expectedStartIndex, actualStartIndex,
                                "Trailing comment alignment mismatch in file: " + testFile
                                        + " on line " + (i + 1));
                    }
                }
            }
        }
    }

    private static Stream<Path> indentationTestFiles() {
        final Path resourcesDir = Paths.get("src", "test", "resources");
        final Path indentationDir = resourcesDir.resolve(INDENTATION_TEST_FILES_PATH);
        try {
            return Files.walk(indentationDir)
                    .filter(path -> {
                        String fileName = path.getFileName().toString();
                        return fileName.startsWith("InputIndentation")
                                && fileName.endsWith(".java");
                    });
        } catch (IOException e) {
            fail("Failed to find indentation test files", e);
            return Stream.empty();
        }
    }
}
