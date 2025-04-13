///
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
///

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class CommonUtilTest extends AbstractPathTestSupport {

    /** After appending to path produces equivalent, but denormalized path. */
    private static final String PATH_DENORMALIZER = "/levelDown/.././";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/commonutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(CommonUtil.class))
                .isTrue();
    }

    /**
     * Test CommonUtil.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs() {
        final String s1 = "\t";
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthExpandedTabs(s1, s1.length(), 8))
            .isEqualTo(8);

        final String s2 = "  \t";
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthExpandedTabs(s2, s2.length(), 8))
            .isEqualTo(8);

        final String s3 = "\t\t";
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthExpandedTabs(s3, s3.length(), 8))
            .isEqualTo(16);

        final String s4 = " \t ";
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthExpandedTabs(s4, s4.length(), 8))
            .isEqualTo(9);

        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthMinusTrailingWhitespace(""))
            .isEqualTo(0);
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthMinusTrailingWhitespace(" \t "))
            .isEqualTo(0);
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthMinusTrailingWhitespace(" 23"))
            .isEqualTo(3);
        assertWithMessage("Invalid expanded tabs length")
            .that(CommonUtil.lengthMinusTrailingWhitespace(" 23 \t "))
            .isEqualTo(3);
    }

    @Test
    public void testCreatePattern() {
        assertWithMessage("invalid pattern")
            .that(CommonUtil.createPattern("Test").pattern())
            .isEqualTo("Test");
        assertWithMessage("invalid pattern")
            .that(CommonUtil.createPattern(".*Pattern.*")
                .pattern())
            .isEqualTo(".*Pattern.*");
    }

    @Test
    public void testBadRegex() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            CommonUtil.createPattern("[");
        });
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Failed to initialise regular expression [");
    }

    @Test
    public void testBadRegex2() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            CommonUtil.createPattern("[", Pattern.MULTILINE);
        });
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Failed to initialise regular expression [");
    }

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        final File pdfFile = new File("file.pdf");
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(pdfFile, fileExtensions))
                .isFalse();
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(pdfFile))
                .isTrue();
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(pdfFile, (String[]) null))
                .isTrue();
        final File javaFile = new File("file.java");
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(javaFile, fileExtensions))
                .isTrue();
        final File invalidJavaFile = new File("file,java");
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(invalidJavaFile, fileExtensions))
                .isFalse();
        final File emptyExtensionFile = new File("file.");
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(emptyExtensionFile, ""))
                .isTrue();
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(pdfFile, ".noMatch"))
                .isFalse();
        assertWithMessage("Invalid file extension")
                .that(CommonUtil.matchesFileExtension(pdfFile, ".pdf"))
                .isTrue();
    }

    @Test
    public void testHasWhitespaceBefore() {
        assertWithMessage("Invalid result")
                .that(CommonUtil.hasWhitespaceBefore(0, "a"))
                .isTrue();
        assertWithMessage("Invalid result")
                .that(CommonUtil.hasWhitespaceBefore(4, "    a"))
                .isTrue();
        assertWithMessage("Invalid result")
                .that(CommonUtil.hasWhitespaceBefore(5, "    a"))
                .isFalse();
    }

    @Test
    public void testBaseClassNameForCanonicalName() {
        assertWithMessage("Invalid base class name")
            .that(CommonUtil.baseClassName("java.util.List"))
            .isEqualTo("List");
    }

    @Test
    public void testBaseClassNameForSimpleName() {
        assertWithMessage("Invalid base class name")
            .that(CommonUtil.baseClassName("Set"))
            .isEqualTo("Set");
    }

    @Test
    public void testRelativeNormalizedPath() {
        final String relativePath = CommonUtil.relativizePath("/home", "/home/test");

        assertWithMessage("Invalid relative path")
            .that(relativePath)
            .isEqualTo("test");
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = CommonUtil.relativizePath(null, "/tmp");

        assertWithMessage("Invalid relative path")
            .that(relativePath)
            .isEqualTo("/tmp");
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = CommonUtil.relativizePath(basePath,
            absoluteFilePath);

        assertWithMessage("Invalid relative path")
            .that(relativePath)
            .isEqualTo("SampleFile.java");
    }

    @Test
    public void testPattern() {
        final boolean result = CommonUtil.isPatternValid("someValidPattern");
        assertWithMessage("Should return true when pattern is valid")
                .that(result)
                .isTrue();
    }

    @Test
    public void testInvalidPattern() {
        final boolean result = CommonUtil.isPatternValid("some[invalidPattern");
        assertWithMessage("Should return false when pattern is invalid")
                .that(result)
                .isFalse();
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        final Constructor<?> constructor = CommonUtil.getConstructor(String.class, String.class);

        assertWithMessage("Invalid constructor")
            .that(constructor)
            .isEqualTo(String.class.getConstructor(String.class));
    }

    @Test
    public void testGetNonExistentConstructor() {
        final IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            CommonUtil.getConstructor(Math.class);
        });
        assertWithMessage("Invalid exception cause")
                .that(ex)
                .hasCauseThat()
                        .isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    public void testInvokeConstructor() throws NoSuchMethodException {
        final Constructor<String> constructor = String.class.getConstructor(String.class);

        final String constructedString = CommonUtil.invokeConstructor(constructor, "string");

        assertWithMessage("Invalid construction result")
            .that(constructedString)
            .isEqualTo("string");
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testInvokeConstructorThatFails() throws NoSuchMethodException {
        final Constructor<Dictionary> constructor = Dictionary.class.getConstructor();
        final IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            CommonUtil.invokeConstructor(constructor);
        });
        assertWithMessage("Invalid exception cause")
                .that(ex)
                .hasCauseThat()
                        .isInstanceOf(InstantiationException.class);
    }

    @Test
    public void testClose() {
        final TestCloseable closeable = new TestCloseable();

        CommonUtil.close(null);
        CommonUtil.close(closeable);

        assertWithMessage("Should be closed")
                .that(closeable.closed)
                .isTrue();
    }

    @Test
    public void testCloseWithException() {
        final IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            CommonUtil.close(() -> {
                throw new IOException("Test IOException");
            });
        });
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Cannot close the stream");
    }

    @Test
    public void testFillTemplateWithStringsByRegexp() {
        assertWithMessage("invalid result")
            .that(CommonUtil.fillTemplateWithStringsByRegexp("template",
                "lineToPlaceInTemplate", Pattern.compile("NO MATCH")))
            .isEqualTo("template");
        assertWithMessage("invalid result")
            .that(CommonUtil.fillTemplateWithStringsByRegexp("before $0 after", "word",
                        Pattern.compile("\\w+")))
            .isEqualTo("before word after");
        assertWithMessage("invalid result")
            .that(CommonUtil.fillTemplateWithStringsByRegexp("before $0 after1 $1 after2 $2 after3",
                        "word 123", Pattern.compile("(\\w+) (\\d+)")))
            .isEqualTo("before word 123 after1 word after2 123 after3");
    }

    @Test
    public void testGetFileNameWithoutExtension() {
        assertWithMessage("invalid result")
            .that(CommonUtil.getFileNameWithoutExtension("filename"))
            .isEqualTo("filename");
        assertWithMessage("invalid result")
            .that(CommonUtil.getFileNameWithoutExtension("filename.extension"))
            .isEqualTo("filename");
        assertWithMessage("invalid result")
            .that(CommonUtil.getFileNameWithoutExtension("filename.subext.extension"))
            .isEqualTo("filename.subext");
    }

    @Test
    public void testGetFileExtension() {
        assertWithMessage("Invalid extension")
            .that(CommonUtil.getFileExtension("filename"))
            .isEqualTo("");
        assertWithMessage("Invalid extension")
            .that(CommonUtil.getFileExtension("filename.extension"))
            .isEqualTo("extension");
        assertWithMessage("Invalid extension")
            .that(CommonUtil.getFileExtension("filename.subext.extension"))
            .isEqualTo("extension");
    }

    @Test
    public void testIsIdentifier() {
        assertWithMessage("Should return true when valid identifier is passed")
                .that(CommonUtil.isIdentifier("aValidIdentifier"))
                .isTrue();
    }

    @Test
    public void testIsIdentifierEmptyString() {
        assertWithMessage("Should return false when empty string is passed")
                .that(CommonUtil.isIdentifier(""))
                .isFalse();
    }

    @Test
    public void testIsIdentifierInvalidFirstSymbol() {
        assertWithMessage("Should return false when invalid identifier is passed")
                .that(CommonUtil.isIdentifier("1InvalidIdentifier"))
                .isFalse();
    }

    @Test
    public void testIsIdentifierInvalidSymbols() {
        assertWithMessage("Should return false when invalid identifier is passed")
                .that(CommonUtil.isIdentifier("invalid#Identifier"))
                .isFalse();
    }

    @Test
    public void testIsName() {
        assertWithMessage("Should return true when valid name is passed")
                .that(CommonUtil.isName("a.valid.Nam3"))
                .isTrue();
    }

    @Test
    public void testIsNameEmptyString() {
        assertWithMessage("Should return false when empty string is passed")
                .that(CommonUtil.isName(""))
                .isFalse();
    }

    @Test
    public void testIsNameInvalidFirstSymbol() {
        assertWithMessage("Should return false when invalid name is passed")
                .that(CommonUtil.isName("1.invalid.name"))
                .isFalse();
    }

    @Test
    public void testIsNameEmptyPart() {
        assertWithMessage("Should return false when name has empty part")
                .that(CommonUtil.isName("invalid..name"))
                .isFalse();
    }

    @Test
    public void testIsNameEmptyLastPart() {
        assertWithMessage("Should return false when name has empty part")
                .that(CommonUtil.isName("invalid.name."))
                .isFalse();
    }

    @Test
    public void testIsNameInvalidSymbol() {
        assertWithMessage("Should return false when invalid name is passed")
                .that(CommonUtil.isName("invalid.name#42"))
                .isFalse();
    }

    @Test
    public void testIsBlank() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("string"))
                .isFalse();
    }

    @Test
    public void testIsBlankAheadWhitespace() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("  string"))
                .isFalse();
    }

    @Test
    public void testIsBlankBehindWhitespace() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("string    "))
                .isFalse();
    }

    @Test
    public void testIsBlankWithWhitespacesAround() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("    string    "))
                .isFalse();
    }

    @Test
    public void testIsBlankWhitespaceInside() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("str    ing"))
                .isFalse();
    }

    @Test
    public void testIsBlankNullString() {
        assertWithMessage("Should return true when string is null")
                .that(CommonUtil.isBlank(null))
                .isTrue();
    }

    @Test
    public void testIsBlankWithEmptyString() {
        assertWithMessage("Should return true when string is empty")
                .that(CommonUtil.isBlank(""))
                .isTrue();
    }

    @Test
    public void testIsBlankWithWhitespacesOnly() {
        assertWithMessage("Should return true when string contains only spaces")
                .that(CommonUtil.isBlank("   "))
                .isTrue();
    }

    @Test
    public void testGetUriByFilenameFindsAbsoluteResourceOnClasspath() throws Exception {
        final String filename =
            "/" + getPackageLocation() + "/InputCommonUtilTest_empty_checks.xml";
        final URI uri = CommonUtil.getUriByFilename(filename);

        final Properties properties = System.getProperties();
        final Configuration config = ConfigurationLoader.loadConfiguration(uri.toASCIIString(),
            new PropertiesExpander(properties));
        assertWithMessage("Unexpected config name!")
            .that(config.getName())
            .isEqualTo("Checker");
    }

    @Test
    public void testGetUriByFilenameFindsRelativeResourceOnClasspath() throws Exception {
        final String filename =
            getPackageLocation() + "/InputCommonUtilTest_empty_checks.xml";
        final URI uri = CommonUtil.getUriByFilename(filename);

        final Properties properties = System.getProperties();
        final Configuration config = ConfigurationLoader.loadConfiguration(uri.toASCIIString(),
            new PropertiesExpander(properties));
        assertWithMessage("Unexpected config name!")
            .that(config.getName())
            .isEqualTo("Checker");
    }

    /**
     * This test illustrates #6232.
     * Without fix, the assertion will fail because the URL under test
     * "com/puppycrawl/tools/checkstyle/utils/commonutil/InputCommonUtilTest_resource.txt"
     * will be interpreted relative to the current package
     * "com/puppycrawl/tools/checkstyle/utils/"
     */
    @Test
    public void testGetUriByFilenameFindsResourceRelativeToRootClasspath() throws Exception {
        final String filename =
                getPackageLocation() + "/InputCommonUtilTest_resource.txt";
        final URI uri = CommonUtil.getUriByFilename(filename);
        assertWithMessage("URI is null for: " + filename)
            .that(uri)
            .isNotNull();
        final String uriRelativeToPackage =
                "com/puppycrawl/tools/checkstyle/utils/"
                        + getPackageLocation() + "/InputCommonUtilTest_resource.txt";
        assertWithMessage("URI is relative to package " + uriRelativeToPackage)
            .that(uri.toASCIIString())
            .doesNotContain(uriRelativeToPackage);
        final String content = IOUtils.toString(uri.toURL(), StandardCharsets.UTF_8);
        assertWithMessage("Content mismatches for: " + uri.toASCIIString())
            .that(content)
            .startsWith("good");
    }

    @Test
    public void testGetUriByFilenameClasspathPrefixLoadConfig() throws Exception {
        final String filename = CommonUtil.CLASSPATH_URL_PROTOCOL
            + getPackageLocation() + "/InputCommonUtilTestWithChecks.xml";
        final URI uri = CommonUtil.getUriByFilename(filename);

        final Properties properties = System.getProperties();
        final Configuration config = ConfigurationLoader.loadConfiguration(uri.toASCIIString(),
            new PropertiesExpander(properties));
        assertWithMessage("Unexpected config name!")
            .that(config.getName())
            .isEqualTo("Checker");
    }

    @Test
    public void testGetUriByFilenameFindsRelativeResourceOnClasspathPrefix() throws Exception {
        final String filename = CommonUtil.CLASSPATH_URL_PROTOCOL
            + getPackageLocation() + "/InputCommonUtilTest_empty_checks.xml";
        final URI uri = CommonUtil.getUriByFilename(filename);

        final Properties properties = System.getProperties();
        final Configuration config = ConfigurationLoader.loadConfiguration(uri.toASCIIString(),
            new PropertiesExpander(properties));
        assertWithMessage("Unexpected config name!")
            .that(config.getName())
            .isEqualTo("Checker");
    }

    @Test
    public void testIsCodePointWhitespace() {
        final int[] codePoints = " 123".codePoints().toArray();
        assertThat(CommonUtil.isCodePointWhitespace(codePoints, 0))
                .isTrue();
        assertThat(CommonUtil.isCodePointWhitespace(codePoints, 1))
                .isFalse();
    }

    @Test
    public void testLoadSuppressionsUriSyntaxException() throws Exception {
        final URL configUrl = mock();
        when(configUrl.toURI()).thenThrow(URISyntaxException.class);
        try (MockedStatic<CommonUtil> utilities =
                     mockStatic(CommonUtil.class, CALLS_REAL_METHODS)) {
            final String fileName = "/suppressions_none.xml";
            utilities.when(() -> CommonUtil.getCheckstyleResource(fileName))
                    .thenReturn(configUrl);

            final CheckstyleException ex = assertThrows(CheckstyleException.class, () -> {
                CommonUtil.getUriByFilename(fileName);
            });
            assertWithMessage("Invalid exception cause")
                    .that(ex)
                    .hasCauseThat()
                            .isInstanceOf(URISyntaxException.class);
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                            .isEqualTo("Unable to find: " + fileName);
        }
    }

    private static final class TestCloseable implements Closeable {

        private boolean closed;

        @Override
        public void close() {
            closed = true;
        }

    }

}
