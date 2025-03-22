package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISSING;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.io.IOException;
import java.net.URI;
import java.util.Set;

class MultiFileRegexpHeaderCheckTest extends AbstractModuleTestSupport {

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
    public void testSetHeaderFilesIsEmpty() {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class, () -> instance.setHeaderFiles(""));
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti52() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader3.header"));
        final String[] expected = {"1: " + getCheckMessage(MSG_HEADER_MISSING),};
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testHeaderWithInvalidRegexp() throws Exception {
        final MultiFileRegexpHeaderCheck instance = new MultiFileRegexpHeaderCheck();
        assertThrows(IllegalArgumentException.class,
                () -> instance.setHeaderFiles(getPath("InputRegexpHeader.invalid.header")));
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        checkConfig.addProperty("headerFiles", getPath("InputRegexpHeader4.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testOneHeaderValid() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MultiFileRegexpHeaderCheck.class);
        String validHeader = getPath("InputRegexpHeader4.header");
        String invalidHeader = getPath("InputRegexpHeader.header");
        String multiHeaderFiles = validHeader + "," + invalidHeader;
        checkConfig.addProperty("headerFiles", multiHeaderFiles);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig), getPath("InputRegexpHeaderMulti5.java"), expected);
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
        locations = check.getExternalResourceLocations();
        assertWithMessage("Should have two external resource locations")
                .that(locations.size())
                .isEqualTo(2);
        assertWithMessage("Locations should include InputRegexpHeader4.header")
                .that(locations.stream().anyMatch(loc -> loc.contains("InputRegexpHeader4.header")))
                .isTrue();
        assertWithMessage("Locations should include InputRegexpHeader1.header")
                .that(locations.stream().anyMatch(loc -> loc.contains("InputRegexpHeader1.header")))
                .isTrue();
    }

//    @Test
//    public void testEmptyHeaderFile() throws IOException {
//        // Create an empty file in temp folder
//        final File emptyFile = File.createTempFile("empty", ".header", temporaryFolder);
//        final MultiFileRegexpHeaderCheck check = new MultiFileRegexpHeaderCheck();
//        assertWithMessage("Empty header file should throw IllegalArgumentException")
//                .that(() -> check.setHeaderFiles(emptyFile.getAbsolutePath()))
//                .isInstanceOf(IllegalArgumentException.class);
//    }

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

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, testFile.getAbsolutePath(), expected);
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

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, fileWithBlank.getAbsolutePath(), expected);
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
