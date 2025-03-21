package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.header.MultiFileRegexpHeaderCheck.MSG_HEADER_MISMATCH;
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
                    .isEqualTo("Error reading or corrupted header file: file://test");
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
        checkConfig.addProperty("headerFiles","");
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
                "headerFiles",getPath("InputRegexpHeaderNewLines.header")
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
            assertWithMessage("Expected IllegalArgumentException" +
                    " when reading from an empty header file").fail();
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
                "headerFiles",getPath("InputRegexpHeader.header"
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
                "headerFiles",getUriString("InputRegexpHeader.header")
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
                "headerFiles",getUriString("InputRegexpHeaderNewLines.header")
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
        checkConfig.addProperty("headerFiles",headerFile.getPath());

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
        checkConfig.addProperty("headerFiles"
                ,getPath("InputRegexpHeader.header"));
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
                "headerFiles",getPath("InputRegexpHeader4.header")
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
                "headerFiles",headerFile.getPath() + "," + headerFile.getPath()
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
        checkConfig.addProperty("headerFiles",headerFile.getPath());

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
        checkConfig.addProperty("headerFiles",headerFile.getPath());

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
        checkConfig.addProperty("headerFiles",headerFile.getPath());

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
                "headerFiles",headerFile1.getPath() + "," + headerFile2.getPath()
        );

        verify(checkConfig, testFile.getPath(), EMPTY_STRING_ARRAY);
    }
}
