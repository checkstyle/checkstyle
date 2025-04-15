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

package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISSING;
import static com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_STRING_ARRAY;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

class MultiFileRegexpHeaderCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/regexpheader";
    }

    @Test
    public void testSetHeaderUriNotSupport() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        try {
            instance.setHeaderFiles(String.valueOf(URI.create("file://test")));
            assertWithMessage("Expected exception for unsupported URI").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("unable to load header file file://test");
        }
    }

    @Test
    public void testSetHeaderFilesNull() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        try {
            instance.setHeaderFiles(null);
            assertWithMessage("Expected exception for null header files").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("headerFiles cannot be null or empty");
        }
    }

    @Test
    public void testSetHeaderFilesIsBlank() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        try {
            instance.setHeaderFiles(" , ");
            assertWithMessage("Expected exception for blank header files").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("Header file is not set");
        }
    }

    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", "");
        try {
            createChecker(checkConfig);
            assertWithMessage(
                    "Checker creation should not succeed with invalid headerFiles"
            )
                .fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck"
                    + " - Cannot set property 'headerFiles' to ''");
        }
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        createChecker(checkConfig);
        // Content header is conflicting with Input inline header
        verify(checkConfig,
                getPath("InputRegexpHeaderDefaultConfig.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testAllHeaderLinesMatchedWithEmptyLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", getPath("InputRegexpHeaderNewLines.header")
        );
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testEmptyHeaderFile() throws IOException {
        final File emptyFile = new File(temporaryFolder, "empty.header");
        Files.write(emptyFile.toPath(), new ArrayList<>(), StandardCharsets.UTF_8);
        final URI fileUri = emptyFile.toURI();

        try {
            MultiFileRegexpHeaderCheck.getLines("empty.header", fileUri);
            assertWithMessage("Expected IllegalArgumentException"
                    + " when reading from an empty header file").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Unexpected exception message")
                    .that(ex.getMessage())
                    .contains("Header file is empty: empty.header");
        }
    }

    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", getPath("InputRegexpHeader.header"
                ));
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "^/*$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderNonMatching.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", getUriString("InputRegexpHeader.header")
        );
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "^/*$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderNonMatching.java"), expected);
    }

    @Test
    public void testInlineRegexpHeaderConsecutiveNewlinesThroughConfigFile() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", getUriString("InputRegexpHeaderNewLines.header")
        );
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testLogFirstMismatchNoMismatch() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
            getPath("InputRegexpHeader.header") + ","
                    + getPath("InputRegexpHeader1.header"));
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testAllHeaderLinesMatchedExactly() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                getPath("InputRegexpHeader1.header"));
        // Content header is conflicting with Input inline header
        verify(checkConfig,
                getPath("InputRegexpHeaderIgnore.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBlankPatternBranch() throws Exception {
        final File headerFile = new File(temporaryFolder, "blankPattern.header");
        Files.write(headerFile.toPath(),
                List.of("// First line", "// Second line", "   "),
                StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "testFile.java");
        Files.write(testFile.toPath(),
                List.of("// First line", "// Second line"),
                StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testMismatchInMiddleOfHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                getPath("InputRegexpHeader.header"));
        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// .*"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testMismatchInHeaderLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                getPath("InputRegexpHeader.header"));
        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// .*"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", getPath("InputRegexpHeader4.header")
        );
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyPatternInHeader() throws Exception {
        final File headerFile = new File(temporaryFolder, "headerFiles.header");
        Files.write(headerFile.toPath(), List.of("", "valid"),
            StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", headerFile.getPath() + "," + headerFile.getPath()
        );

        verify(checkConfig, headerFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testGetExternalResourceLocations() throws IOException {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        final Set<String> locations = check.getExternalResourceLocations();
        assertWithMessage("Empty header files should return empty locations set")
            .that(locations.size())
            .isEqualTo(0);

        check.setHeaderFiles(getPath("InputRegexpHeader4.header") + ","
            + getPath("InputRegexpHeader1.header"));
        final Set<String> externalResourceLocations = check.getExternalResourceLocations();
        assertWithMessage("Should have two external resource locations")
            .that(externalResourceLocations.size())
            .isEqualTo(2);
        assertWithMessage("Locations should include InputRegexpHeader4.header")
            .that(externalResourceLocations.stream()
                .anyMatch(loc -> loc.contains("InputRegexpHeader4.header")))
            .isTrue();
        assertWithMessage("Locations should include InputRegexpHeader1.header")
            .that(externalResourceLocations.stream()
                .anyMatch(loc -> loc.contains("InputRegexpHeader1.header")))
            .isTrue();
    }

    @Test
    public void testAllLinesMatch() throws Exception {
        final String[] fileLines = {
            "// First line",
            "// Second line",
            "// Third line",
        };

        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(fileLines), StandardCharsets.UTF_8);

        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyPatternMatch() throws Exception {
        final File fileWithBlank = new File(temporaryFolder, "blank.java");
        Files.write(fileWithBlank.toPath(), List.of(
            "// First line",
            "",
            "// Third line"), StandardCharsets.UTF_8);

        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "^$",
            "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, fileWithBlank.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBlankLineMismatch() throws Exception {
        final File fileNoBlank = new File(temporaryFolder, "noblank.java");
        Files.write(fileNoBlank.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line"), StandardCharsets.UTF_8);

        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "^$",
            "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, fileNoBlank.getPath(), expected);
    }

    @Test
    public void testMultipleHeaderFilesAllInvalid() throws Exception {
        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(
            "// Different content",
            "// Not matching any headers"), StandardCharsets.UTF_8);

        final File header1 = new File(temporaryFolder, "header1.header");
        Files.write(header1.toPath(), List.of("// Header 1"),
            StandardCharsets.UTF_8);

        final File header2 = new File(temporaryFolder, "header2.header");
        Files.write(header2.toPath(), List.of("// Header 2"),
            StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
            header1.getPath() + "," + header2.getPath());

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Header 1"),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testMultipleHeaderFiles() throws Exception {
        // Create first header file
        final File headerFile1 = new File(temporaryFolder, "header1.header");
        Files.write(headerFile1.toPath(), List.of("Copyright", "Author: .*", ""),
            StandardCharsets.UTF_8);

        // Create second header file
        final File headerFile2 = new File(temporaryFolder, "header2.header");
        Files.write(headerFile2.toPath(), List.of("License", "Year: \\d{4}", ""),
            StandardCharsets.UTF_8);

        // Create test file that matches the first header
        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Copyright 2023", "Author: John Doe"),
            StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty(
                "headerFiles", headerFile1.getPath() + "," + headerFile2.getPath()
        );

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testInvalidRegex() throws Exception {
        // Create a file with invalid regex pattern
        final File corruptedHeaderFile = new File(temporaryFolder, "corrupted.header");
        Files.write(corruptedHeaderFile.toPath(), List.of("Invalid regex [a-z"),
            StandardCharsets.UTF_8);

        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();

        try {
            check.setHeaderFiles(corruptedHeaderFile.getPath());
            assertWithMessage("Expected exception for corrupted header file").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Invalid regex pattern: Invalid regex [a-z");
        }
    }

    @Test
    public void testInvalidFileName() {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        final String headerFile = "UnExisted file";
        try {
            check.setHeaderFiles(headerFile);
            assertWithMessage("Expected exception for invalid header file").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid header file")
                    .that(ex.getMessage())
                    .isEqualTo("Error reading or corrupted header file: " + headerFile);
        }
    }

    @Test
    public void testHeaderMoreLinesThanFile() throws Exception {
        // Create a test file with fewer lines than the header and different content
        final File testFile = new File(temporaryFolder, "shortFile.java");
        Files.write(testFile.toPath(), List.of(
            "// Different content",
            "// Not matching header"), StandardCharsets.UTF_8);

        // Create a header file with more lines than the test file
        final File headerFile = new File(temporaryFolder, "longHeader.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line",
            "// Fourth line",
            "// Fifth line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // This should trigger the mismatchFound = true condition in logFirstMismatch
        // The check will report MSG_HEADER_MISSING because the header has more lines than the file
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testMismatchLineCondition() throws Exception {
        // Create a test file with enough lines but with content that doesn't match the header pattern
        final File testFile = new File(temporaryFolder, "mismatchFile.java");
        Files.write(testFile.toPath(), List.of(
            "// First line matches",
            "// This line doesn't match the header pattern",
            "// Third line matches"), StandardCharsets.UTF_8);

        // Create a header file with patterns that should match the first and third lines
        // but not the second line
        final File headerFile = new File(temporaryFolder, "mismatchHeader.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line matches",
            "// Second line matches",
            "// Third line matches"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // This should trigger the VALID_LINE_HEADER_CHECKER != mismatchLine condition
        // because the second line of the file doesn't match the second line of the header
        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH,
                "// Second line matches"),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testFindFirstMismatch() throws Exception {
        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line"), StandardCharsets.UTF_8);

        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "// Different line",
            "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Different line"),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testHeaderFileMetadataMethods() throws Exception {
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "",
            "// Third line"), StandardCharsets.UTF_8);

        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        check.setHeaderFiles(headerFile.getPath());

        // Test getExternalResourceLocations
        final Set<String> locations = check.getExternalResourceLocations();
        assertWithMessage("Should have one external resource location")
            .that(locations.size())
            .isEqualTo(1);
        assertWithMessage("Location should include header.header")
            .that(locations.stream()
                .anyMatch(loc -> loc.contains("header.header")))
            .isTrue();
    }

    @Test
    public void testIOExceptionInGetLines() throws Exception {
        final File nonExistentFile = new File(temporaryFolder, "nonexistent.header");
        final URI fileUri = nonExistentFile.toURI();

        try {
            MultiFileRegexpHeaderCheck.getLines("nonexistent.header", fileUri);
            assertWithMessage("Expected IllegalArgumentException"
                    + " when reading from a non-existent file").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Unexpected exception message")
                    .that(ex.getMessage())
                    .contains("unable to load header file nonexistent.header");
        }
    }

    @Test
    public void testNoMatchingHeaderFile() throws Exception {
        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(
            "// Different content",
            "// Not matching any headers"), StandardCharsets.UTF_8);

        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// Header content",
            "// Another line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Header content"),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testNonEmptyPatternsWithMismatchedCardinality() throws Exception {
        // Create a test file with content that will match some but not all patterns
        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(
            "// First line matches",
            "// Second line matches",
            "// Third line doesn't match pattern"), StandardCharsets.UTF_8);

        // Create a header file with patterns that should match the first two lines
        // but not the third line
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line matches",
            "// Second line matches",
            "// Third line with different pattern"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // This should trigger both conditions:
        // 1. !allEmpty is true because we have non-empty patterns
        // 2. lineMatches.cardinality() != headerPatterns.size() is true because
        //    we have 3 patterns but only 2 matches
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Third line with different pattern"),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testAllEmptyVariableLogic() throws Exception {
        // Create a test file with content that will match some patterns but not all
        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(
            "// First line matches",
            "// Second line doesn't match",
            "// Third line matches"), StandardCharsets.UTF_8);

        // Create a header file with patterns that should match the first and third lines
        // but not the second line
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line matches",
            "// Second line with different pattern",
            "// Third line matches"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // This test specifically targets the issue with the allEmpty variable
        // In the findFirstMismatch method, allEmpty is set to true only if all patterns match
        // This is incorrect - it should be true only if all patterns are empty
        // The test verifies that the check correctly identifies a mismatch on line 2
        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Second line with different pattern"),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }
}
