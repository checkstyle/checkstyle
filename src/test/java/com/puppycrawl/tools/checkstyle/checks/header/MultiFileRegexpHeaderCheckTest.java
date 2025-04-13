package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISSING;

import static com.puppycrawl.tools.checkstyle.utils.CommonUtil.EMPTY_STRING_ARRAY;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import java.net.URI;
import java.util.Set;

class MultiFileRegexpHeaderCheckTest<T> extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/regexpheader";
    }

    @TempDir
    public File temporaryFolder;

    /**
     * Test of setHeaderFile method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderUriNotSupport() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class, () -> instance.setHeaderFiles(String.valueOf(URI.create("file://test"))));
    }

    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderFilesNull() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class, () -> instance.setHeaderFiles(null));
    }

    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderFilesIsBlank() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class, () -> instance.setHeaderFiles(" , "));
    }

    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", "");
        try {
            createChecker(checkConfig);
            assertWithMessage("Checker creation should not succeed with invalid headerFiles").fail();
        } catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message").that(ex.getMessage()).isEqualTo(
                    "cannot initialize module" + " com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck" + " - Cannot set property 'headerFiles' to ''");
        }
    }

    /**
     * Test of setHeaderFiles method, of class MultiFileRegexpHeaderCheck.
     */
    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testAllHeaderLinesMatchedWithEmptyLine() throws Exception {
        // Create a header file with 3 lines, including an empty line
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of("Copyright", "Author: .*", ""), StandardCharsets.UTF_8);

        // Create a test file with 2 lines (matching the first two header lines)
        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Copyright 2023", "Author: John Doe"), StandardCharsets.UTF_8);

        // Configure the check
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // Expect no violations (all header lines are matched)

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyHeaderFile() throws IOException {
        // Create an empty header file in the temp folder
        final File emptyFile = File.createTempFile("empty", ".header", temporaryFolder);

        final URI fileUri = emptyFile.toURI();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MultiFileRegexpHeaderCheck.getLines("empty.header", fileUri);
        });

        // Assert the exception message
        String expectedMessage = "Header file is empty: empty.header";
        assertWithMessage("Unexpected message").that(exception.getMessage()).contains(expectedMessage);
    }


    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader.header"));
        final String[] expected = {"3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),};
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getUriString("InputRegexpHeader.header"));
        final String[] expected = {"3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),};
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }



    @Test
    public void testInlineRegexpHeaderConsecutiveNewlinesThroughConfigFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getUriString("InputRegexpHeaderNewLines.header"));
        final String[] expected = {"3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),};
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testRegexpHeaderIgnore() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader1.header"));
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testRegexpHeaderMulti52() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader3.header"));
        final String[] expected = {"1: " + getCheckMessage(MSG_HEADER_MISSING),};
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testLogFirstMismatchNoMismatch() throws Exception {
        // Create a header file with two lines
        final File headerFile1 = new File(temporaryFolder, "header1.header");
        Files.write(headerFile1.toPath(), List.of("Line 1", "Line 2"), StandardCharsets.UTF_8);

        final File headerFile2 = new File(temporaryFolder, "header2.header");
        Files.write(headerFile2.toPath(), List.of("Line 1", "Line 2"), StandardCharsets.UTF_8);

        // Create a test file that matches the header
        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Line 1", "Line 2"), StandardCharsets.UTF_8);

        // Configure the check with the header file
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile1.getPath() + "," + headerFile2.getPath());

        // Expect no violations
        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testAllHeaderLinesMatchedExactly() throws Exception {
        // Create a header file with 3 lines, including an empty line (compiled to "^$")
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of("Copyright", "Author: .*", ""), StandardCharsets.UTF_8);

        // Create a test file with 3 lines (matches all header lines, including the empty line)
        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Copyright 2023", "Author: John Doe", ""), StandardCharsets.UTF_8);

        // Configure the check
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // Expect no violations (all header lines are matched)

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBlankPatternBranch() throws Exception {
        // Header file with blank line as the third line
        final File headerFile = new File(temporaryFolder, "blankPattern.header");
        Files.write(headerFile.toPath(), List.of("// First line", "// Second line", "   "), StandardCharsets.UTF_8);

        // Test file has 2 lines (fewer than the header's 3 lines)
        final File testFile = new File(temporaryFolder, "testFile.java");
        Files.write(testFile.toPath(), List.of("// First line", "// Second line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testHeaderWithInvalidRegexp() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class,
                () -> instance.setHeaderFiles(getPath("InputRegexpHeader.invalid.header")));
    }

    @Test
    public void testMismatchInMiddleOfHeader() throws Exception {
        // Create a header file with two regex patterns
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of("Copyright", "Author: .*"), StandardCharsets.UTF_8);

        // Create a test file where the second line does NOT contain "Author:"
        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Copyright 2023", "Contributor: John"), StandardCharsets.UTF_8);

        // Configure the check
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        // Expected violation at line 2 (1-based index)
        final String[] expected = {
                "2: " + getCheckMessage(MSG_HEADER_MISMATCH, "Author: .*")
        };

        verify(checkConfig, testFile.getPath(), expected);
    }


    @Test
    public void testMismatchInHeaderLine() throws Exception {
        // Header file with a regex pattern requiring "Author:"
        final File headerFile = new File(temporaryFolder, "header.header");
        Files.write(headerFile.toPath(), List.of("Author: .*"), StandardCharsets.UTF_8);

        // Test file line does NOT contain "Author:"
        final File testFile = new File(temporaryFolder, "TestFile.java");
        Files.write(testFile.toPath(), List.of("Contributor: John"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath());

        final String[] expected = {
                "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "Author: .*")
        };

        verify(checkConfig, testFile.getPath(), expected);
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader4.header"));
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), EMPTY_STRING_ARRAY);
    }


    @Test
    public void testEmptyPatternInHeader() throws Exception {
        // Create a temporary header file with an empty pattern
        final File headerFile = new File(temporaryFolder, "headerFile.header");
        Files.write(headerFile.toPath(), List.of("", "valid"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getPath() + "," + headerFile.getPath());

        // Execute the check using the verify method
        verify(checkConfig, headerFile.getPath(), EMPTY_STRING_ARRAY);
    }


    @Test
    public void testGetExternalResourceLocations() throws IOException {
        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
        // Test empty case first
        Set<String> locations = check.getExternalResourceLocations();
        assertWithMessage("Empty header files should return empty locations set")
                .that(locations.size())
                .isEqualTo(0);

        // Test with actual header files
        check.setHeaderFiles(getPath("InputRegexpHeader4.header") + "," +
                getPath("InputRegexpHeader1.header"));
        Set<String> externalResourceLocations = check.getExternalResourceLocations();
        assertWithMessage("Should have two external resource locations")
                .that(externalResourceLocations.size())
                .isEqualTo(2);
        assertWithMessage("Locations should include InputRegexpHeader4.header")
                .that(externalResourceLocations.stream().anyMatch(loc -> loc.contains("InputRegexpHeader4.header")))
                .isTrue();
        assertWithMessage("Locations should include InputRegexpHeader1.header")
                .that(externalResourceLocations.stream().anyMatch(loc -> loc.contains("InputRegexpHeader1.header")))
                .isTrue();
    }

    @Test
    public void testAllLinesMatch() throws Exception {
        final String[] fileLines = {
                "// First line",
                "// Second line",
                "// Third line"
        };

        // Create file with matching content
        final File testFile = File.createTempFile("test", ".java", temporaryFolder);
        Files.write(testFile.toPath(), List.of(fileLines), StandardCharsets.UTF_8);

        // Create matching header file
        final File headerFile = File.createTempFile("header", ".header", temporaryFolder);
        Files.write(headerFile.toPath(), List.of(
                "// First line",
                "// Second line",
                "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getAbsolutePath());

        verify(checkConfig, testFile.getAbsolutePath(), EMPTY_STRING_ARRAY);
    }


    @Test
    public void testEmptyPatternMatch() throws Exception {
        // Create a file with a blank line
        final File fileWithBlank = File.createTempFile("blank", ".java", temporaryFolder);
        Files.write(fileWithBlank.toPath(), List.of(
                "// First line",
                "",
                "// Third line"), StandardCharsets.UTF_8);

        // Create header with regex for blank line
        final File headerFile = File.createTempFile("header", ".header", temporaryFolder);
        Files.write(headerFile.toPath(), List.of(
                "// First line",
                "^$",
                "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getAbsolutePath());

        verify(checkConfig, fileWithBlank.getAbsolutePath(), EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBlankLineMismatch() throws Exception {
        // Create a file without blank lines
        final File fileNoBlank = File.createTempFile("noblank", ".java", temporaryFolder);
        Files.write(fileNoBlank.toPath(), List.of(
                "// First line",
                "// Second line",
                "// Third line"), StandardCharsets.UTF_8);

        // Create header with regex for blank line
        final File headerFile = File.createTempFile("header", ".header", temporaryFolder);
        Files.write(headerFile.toPath(), List.of(
                "// First line",
                "^$",
                "// Third line"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", headerFile.getAbsolutePath());

        final String[] expected = {"2: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$")};
        verify(checkConfig, fileNoBlank.getAbsolutePath(), expected);
    }

    @Test
    public void testMultipleHeaderFilesAllInvalid() throws Exception {
        // Create test file
        final File testFile = File.createTempFile("test", ".java", temporaryFolder);
        Files.write(testFile.toPath(), List.of(
                "// Different content",
                "// Not matching any headers"), StandardCharsets.UTF_8);

        // Create two different header files
        final File header1 = File.createTempFile("header1", ".header", temporaryFolder);
        Files.write(header1.toPath(), List.of("// Header 1"), StandardCharsets.UTF_8);

        final File header2 = File.createTempFile("header2", ".header", temporaryFolder);
        Files.write(header2.toPath(), List.of("// Header 2"), StandardCharsets.UTF_8);

        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles",
                header1.getAbsolutePath() + "," + header2.getAbsolutePath());

        // When both headers don't match, it should log the mismatch from the first header
        final String[] expected = {"1: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Header 1")};
        verify(checkConfig, testFile.getAbsolutePath(), expected);
    }
}
