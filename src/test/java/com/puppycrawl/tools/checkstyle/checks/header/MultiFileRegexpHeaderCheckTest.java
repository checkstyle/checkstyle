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
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_STRING_ARRAY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
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

/**
 * Tests for {@link MultiFileRegexpHeaderCheck}.
 *
 * <p>
 * IMPORTANT TEST CONFIGURATION NOTE:
 * Test input files (like {@code Input...Config.java}) are loaded relative to
 * {@link #getPackageLocation()}. The check itself uses paths to header template
 * files. If a Java test input file's path is mistakenly used as a header
 * template path for the check, it will cause errors.
 * Always use distinct paths for test input files versus header template files.
 * </p>
 */
public class MultiFileRegexpHeaderCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/regexpheader";
    }

    @Test
    public void setHeaderUriNotSupport() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        try {
            instance.setHeaderFiles(String.valueOf(URI.create("file://test")));
            assertWithMessage("Expected exception for unsupported URI").fail();
        }
        catch (IllegalArgumentException exc) {
            assertWithMessage("Invalid exception message")
                    .that(exc.getMessage())
                    .isEqualTo("unable to load header file file://test");
        }
    }

    @Test
    public void setHeaderFilesNull() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);

        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();

        instance.configure(checkConfig);

        assertWithMessage(
                "Expected no header files to be configured when"
                + " 'headerFiles' property is not set")
            .that(instance.getExternalResourceLocations())
            .isEmpty();
    }

    @Test
    public void setHeaderFilesIsBlank() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", " , ");

        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();

        instance.configure(checkConfig);

        assertWithMessage(
                "Expected no header files to be configured for a blank input string")
            .that(instance.getExternalResourceLocations())
            .isEmpty();
    }

    @Test
    public void emptyFilename() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", "");

        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        instance.configure(checkConfig);
        assertWithMessage(
                "Expected no header files to be configured for an empty input string")
            .that(instance.getExternalResourceLocations())
            .isEmpty();

        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void defaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        createChecker(checkConfig);
        verify(checkConfig,
                getPath("InputRegexpHeaderDefaultConfig.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void allHeaderLinesMatchedWithEmptyLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeaderNewLines.header"));

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allConfiguredPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$", allConfiguredPaths),
        };
        verify(checkConfig,
                getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void emptyHeaderFile() throws Exception {
        final File emptyFile = new File(temporaryFolder, "empty.header");
        Files.write(emptyFile.toPath(), new ArrayList<>(), StandardCharsets.UTF_8);
        final URI fileUri = emptyFile.toURI();

        try {
            MultiFileRegexpHeaderCheck.getLines("empty.header", fileUri);
            assertWithMessage(
                    "Expected IllegalArgumentException when reading from an empty header file")
                .fail();
        }
        catch (IllegalArgumentException exc) {
            assertWithMessage("Unexpected exception message")
                    .that(exc.getMessage())
                    .contains("Header file is empty: empty.header");
        }
    }

    @Test
    public void regexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader.header"));

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allConfiguredPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "^/*$", allConfiguredPaths),
        };
        verify(checkConfig, getPath("InputRegexpHeaderNonMatching.java"),
                expected);
    }

    @Test
    public void regexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getUriString("InputRegexpHeader.header"));

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allConfiguredPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "^/*$", allConfiguredPaths),
        };
        verify(checkConfig, getPath("InputRegexpHeaderNonMatching.java"),
                expected);
    }

    @Test
    public void inlineRegexpHeaderConsecutiveNewlinesThroughConfigFile() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getUriString("InputRegexpHeaderNewLines.header"));

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allConfiguredPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$", allConfiguredPaths),
        };
        verify(checkConfig,
                getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void logFirstMismatchNoMismatch() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                getPath("InputRegexpHeader.header") + ","
                        + getPath("InputRegexpHeader1.header")
        );
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"),
                EMPTY_STRING_ARRAY);
    }

    @Test
    public void allHeaderLinesMatchedExactly() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                getPath("InputRegexpHeader1.header"));
        verify(checkConfig,
                getPath("InputRegexpHeaderIgnore.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void blankPatternBranch() throws Exception {
        final File headerFile = new File(temporaryFolder, "blankPattern.header");
        Files.write(headerFile.toPath(),
                List.of("// First line", "// Second line", "   "),
                StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "testFile.java");
        Files.write(testFile.toPath(),
                List.of("// First line", "// Second line", "   "),
                StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void mismatchInMiddleOfHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader.header"));

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// .*", allPaths),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void mismatchInHeaderLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader.header"));

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths2 = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// .*", allPaths2),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void noWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader4.header"));
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void emptyPatternInHeader() throws Exception {
        final File headerFile = new File(temporaryFolder, "headerFiles.header");
        Files.write(headerFile.toPath(), List.of("", "valid"),
            StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath() + "," + headerFile.getPath());

        verify(checkConfig, headerFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void getExternalResourceLocationsWithNoPropertySet() throws Exception {
        final DefaultConfiguration configNoHeaderFiles =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        final MultiFileRegexpHeaderCheck checkNoHeaders = new MultiFileRegexpHeaderCheck();
        checkNoHeaders.configure(configNoHeaderFiles);

        final Set<String> locationsNoHeaders = checkNoHeaders.getExternalResourceLocations();
        assertWithMessage(
                "External locations should be empty when headerFiles property is not set")
            .that(locationsNoHeaders)
            .isEmpty();
    }

    @Test
    public void getExternalResourceLocationsFromMultipleFilesSet() throws Exception {
        final DefaultConfiguration configWithHeaderFiles =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        final String header4Path = getPath("InputRegexpHeader4.header");
        final String header1Path = getPath("InputRegexpHeader1.header");
        configWithHeaderFiles.addProperty("headerFiles",
                header4Path + "," + header1Path);

        final MultiFileRegexpHeaderCheck checkWithHeaders = new MultiFileRegexpHeaderCheck();
        checkWithHeaders.configure(configWithHeaderFiles);

        final Set<String> locationsWithHeaders = checkWithHeaders.getExternalResourceLocations();
        assertWithMessage("Should have two external resource locations")
            .that(locationsWithHeaders.size())
            .isEqualTo(2);

        final String expectedUri4 = new File(header4Path).toURI().toASCIIString();
        final String expectedUri1 = new File(header1Path).toURI().toASCIIString();

        assertWithMessage(
                "Locations should include URI for InputRegexpHeader4.header")
            .that(locationsWithHeaders)
            .contains(expectedUri4);
        assertWithMessage(
                "Locations should include URI for InputRegexpHeader1.header")
            .that(locationsWithHeaders)
            .contains(expectedUri1);
    }

    @Test
    public void allLinesMatch() throws Exception {
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
    public void emptyPatternMatch() throws Exception {
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
    public void blankLineMismatch() throws Exception {
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

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$", allPaths),
        };
        verify(checkConfig, fileNoBlank.getPath(), expected);
    }

    @Test
    public void multipleHeaderFilesAllInvalid() throws Exception {
        final File testFile = new File(temporaryFolder, "test.java");
        Files.write(testFile.toPath(), List.of(
            "// Different content",
            "// Not matching any headers"), StandardCharsets.UTF_8);

        final File header1File = new File(temporaryFolder, "header1.header");
        Files.write(header1File.toPath(), List.of("// Header 1"),
            StandardCharsets.UTF_8);

        final File header2File = new File(temporaryFolder, "header2.header");
        Files.write(header2File.toPath(), List.of("// Header 2"),
            StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
            header1File.getPath() + "," + header2File.getPath());

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Header 1", allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void multipleHeaderFiles() throws Exception {
        final File headerFile1 = new File(temporaryFolder, "header1.header");
        Files.write(headerFile1.toPath(), List.of("Copyright", "Author: .*", ""),
            StandardCharsets.UTF_8);

        final File headerFile2 = new File(temporaryFolder, "header2.header");
        Files.write(headerFile2.toPath(), List.of("License", "Year: \\d{4}", ""),
            StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Copyright", "Author: John Doe", ""),
            StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                headerFile1.getPath() + "," + headerFile2.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void invalidRegex() throws Exception {
        final File corruptedHeaderFile = new File(temporaryFolder, "corrupted.header");
        Files.write(corruptedHeaderFile.toPath(), List.of("Invalid regex [a-z"),
            StandardCharsets.UTF_8);

        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();

        try {
            check.setHeaderFiles(corruptedHeaderFile.getPath());
            assertWithMessage("Expected exception for corrupted header file").fail();
        }
        catch (IllegalArgumentException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Invalid regex pattern: Invalid regex [a-z");
        }
    }

    @Test
    public void invalidFileName() {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        final String headerFile = "UnExisted file";
        final IllegalArgumentException thrown =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    check.setHeaderFiles(headerFile);
                });
        assertWithMessage("Exception message did not match for invalid file name.")
                .that(thrown.getMessage())
                .isEqualTo("Error reading or corrupted header file: " + headerFile);
    }

    @Test
    public void headerMoreLinesThanFile() throws Exception {
        final File testFile = new File(temporaryFolder, "shortFile.java");
        Files.write(testFile.toPath(), List.of(
            "// Different content",
            "// Not matching header"), StandardCharsets.UTF_8);

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

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING, headerFile.getPath(), allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void mismatchLineCondition() throws Exception {
        final File testFile = new File(temporaryFolder, "mismatchFile.java");
        Files.write(testFile.toPath(), List.of(
            "// First line matches",
            "// This line doesn't match the header pattern",
            "// Third line matches"), StandardCharsets.UTF_8);

        final File headerFile = new File(temporaryFolder, "mismatchHeader.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line matches",
            "// Second line matches",
            "// Third line matches"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH,
                "// Second line matches", allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void findFirstMismatch() throws Exception {
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

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Different line", allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void noMatchingHeaderFile() throws Exception {
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

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Header content", allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void nonEmptyPatternsWithMismatchedCardinality() throws Exception {
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line with different pattern"
        ), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "testFile.java");
        Files.write(testFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line that does not match"
        ), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
            createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH,
                    "// Third line with different pattern", allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void fileFailsWhenShorterThanHeader() throws Exception {
        final File headerFile = new File(temporaryFolder, "longerHeader.header");
        Files.write(headerFile.toPath(), List.of(
            "// Line 1",
            "// Line 2",
            "// Line 3"
        ), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "shorterFile.java");
        Files.write(testFile.toPath(), List.of(
            "// Line 1"
        ), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
            createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING, headerFile.getPath(), allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void headerWithTrailingBlanksFailsWhenFileIsShorter() throws Exception {
        final File headerFile = new File(temporaryFolder, "headerWithBlanks.header");
        Files.write(headerFile.toPath(), List.of(
            "// Line 1",
            "",
            "// Line 3"
        ), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "shorterFile.java");
        Files.write(testFile.toPath(), List.of(
            "// Line 1"
        ), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
            createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING, headerFile.getPath(), allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void fileSizeGreaterThanHeaderPatternSize() throws Exception {
        final File headerFile = new File(temporaryFolder, "shorterHeader.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line"), StandardCharsets.UTF_8);

        // Create a test file with more lines than the header
        final File testFile = new File(temporaryFolder, "longerFile.java");
        Files.write(testFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line",
            "// Fourth line",
            "// Fifth line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void fileSizeEqualHeaderPatternSize() throws Exception {
        final File headerFile = new File(temporaryFolder, "shorterHeader.header");
        Files.write(headerFile.toPath(), List.of(
                "// First line",
                "// Second line",
                "// Third line"), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "longerFile.java");
        Files.write(testFile.toPath(), List.of(
                "// First line",
                "// Second line",
                "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void specificLineMismatch() throws Exception {
        final File headerFile = new File(temporaryFolder, "multiline.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "// Third line with pattern .*",
            "// Fourth line",
            "// Fifth line"), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "mismatchThirdLine.java");
        Files.write(testFile.toPath(), List.of(
            "// First line",
            "// Second line",
            "This line doesn't match the pattern",
            "// Fourth line",
            "// Fifth line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final MultiFileRegexpHeaderCheck checkInstance = new MultiFileRegexpHeaderCheck();
        checkInstance.configure(checkConfig);
        final String allPaths = checkInstance.getConfiguredHeaderPaths();

        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Third line with pattern .*", allPaths),
        };
        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void multipleHeaderFilesFirstMatchesSecondDoesnt() throws Exception {
        final File matchingHeaderFile = new File(temporaryFolder, "matching.header");
        Files.write(matchingHeaderFile.toPath(), List.of(
            "// This header matches",
            "// The file content"), StandardCharsets.UTF_8);

        final File nonMatchingHeaderFile = new File(temporaryFolder, "nonMatching.header");
        Files.write(nonMatchingHeaderFile.toPath(), List.of(
            "// This header doesn't match",
            "// Different content"), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of(
            "// This header matches",
            "// The file content"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
            matchingHeaderFile.getPath() + "," + nonMatchingHeaderFile.getPath());
        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void multipleHeaderFilesNoneMatch() throws Exception {
        final File matchingHeader = new File(temporaryFolder, "nonMatching1.header");
        Files.write(matchingHeader.toPath(), List.of(
            "// First matching header",
            "// Second matching header"), StandardCharsets.UTF_8);

        final File nonMatchingHeader = new File(temporaryFolder, "nonMatching2.header");
        Files.write(nonMatchingHeader.toPath(), List.of(
            "// First matching header",
            "// Second no-matching header"), StandardCharsets.UTF_8);

        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of(
            "// First matching header",
            "// Second matching header"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig =
                createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
            matchingHeader.getPath() + "," + nonMatchingHeader.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void setHeaderFilesWithNullPathThrowsException() {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();

        final IllegalArgumentException thrown =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    check.setHeaderFiles((String) null);
                });
        assertWithMessage("Exception message mismatch for null header path")
                .that(thrown.getMessage()).isEqualTo("Header file is not set");
    }

    @Test
    public void setHeaderFilesWithNullVarargsArray() {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();

        assertDoesNotThrow(() -> {
            check.setHeaderFiles((String[]) null);
        });

        assertWithMessage(
            "External locations should be empty when setHeaderFiles is called with null array")
            .that(check.getExternalResourceLocations())
            .isEmpty();
    }

    @Test
    public void setHeaderFilesClearsPreviousConfiguration() throws Exception {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();

        final File headerFileA = new File(temporaryFolder, "testHeaderA_clear_check.header");
        Files.write(headerFileA.toPath(), List.of("// Header A for clear check"),
                StandardCharsets.UTF_8);
        final String pathA = headerFileA.getAbsolutePath();
        final URI uriA = headerFileA.toURI();

        check.setHeaderFiles(pathA);
        assertWithMessage("After first set, only header A should be present")
                .that(check.getExternalResourceLocations())
                .containsExactly(uriA.toASCIIString());

        final File headerFileB = new File(temporaryFolder, "testHeaderB_clear_check.header");
        Files.write(headerFileB.toPath(), List.of("// Header B for clear check"),
                StandardCharsets.UTF_8);
        final String pathB = headerFileB.getAbsolutePath();
        final URI uriB = headerFileB.toURI();

        check.setHeaderFiles(pathB);
        assertWithMessage("After second set, only header B should be present")
                .that(check.getExternalResourceLocations())
                .containsExactly(uriB.toASCIIString());
    }

    @Test
    public void headerFileMetadataMethods() throws Exception {
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of(
            "// First line",
            "",
            "// Third line"), StandardCharsets.UTF_8);

        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        check.setHeaderFiles(headerFile.getPath());

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
    public void ioExceptionInGetLines() {
        final File nonExistentFile = new File(temporaryFolder, "nonexistent.header");
        final URI fileUri = nonExistentFile.toURI();

        try {
            MultiFileRegexpHeaderCheck.getLines("nonexistent.header", fileUri);
            assertWithMessage(
                    "Expected IllegalArgumentException when reading from a non-existent file")
                .fail();
        }
        catch (IllegalArgumentException exc) {
            assertWithMessage("Unexpected exception message")
                    .that(exc.getMessage())
                    .contains("unable to load header file nonexistent.header");
        }
    }

    @Test
    public void configuredHeaderPathsNoFiles() {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        assertWithMessage("Expected empty string when no header files are configured")
            .that(check.getConfiguredHeaderPaths())
            .isEmpty();
    }

    @Test
    public void configuredHeaderPathsSingleFile() throws Exception {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        final File headerFile = new File(temporaryFolder, "singleTest.header");
        Files.writeString(headerFile.toPath(), "// Single test header", StandardCharsets.UTF_8);
        final String path = headerFile.getAbsolutePath();

        check.setHeaderFiles(path);

        assertWithMessage("Expected path of the single configured file")
            .that(check.getConfiguredHeaderPaths())
            .isEqualTo(path);
    }

    @Test
    public void configuredHeaderPathsMultipleFiles() throws Exception {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        final File headerFile1 = new File(temporaryFolder, "multiTest1.header");
        Files.writeString(headerFile1.toPath(), "// Multi test header 1", StandardCharsets.UTF_8);
        final String path1 = headerFile1.getAbsolutePath();

        final File headerFile2 = new File(temporaryFolder, "multiTest2.header");
        Files.writeString(headerFile2.toPath(), "// Multi test header 2", StandardCharsets.UTF_8);
        final String path2 = headerFile2.getAbsolutePath();

        check.setHeaderFiles(path1, path2);

        final String expectedPaths = path1 + ", " + path2;
        assertWithMessage("Expected comma-separated paths of multiple configured files")
            .that(check.getConfiguredHeaderPaths())
            .isEqualTo(expectedPaths);

        check.setHeaderFiles(path1, path2);
        assertWithMessage("Expected comma-separated paths when set with distinct args")
            .that(check.getConfiguredHeaderPaths())
            .isEqualTo(expectedPaths);
    }

}
