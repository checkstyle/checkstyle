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

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/commonutil";
    }

    @Test
    void isProperUtilsClass() throws Exception {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(CommonUtil.class))
                .isTrue();
    }

    /**
     * Test CommonUtil.countCharInString.
     */
    @Test
    void lengthExpandedTabs() {
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
    void createPattern() {
        assertWithMessage("invalid pattern")
            .that(CommonUtil.createPattern("Test").pattern())
            .isEqualTo("Test");
        assertWithMessage("invalid pattern")
            .that(CommonUtil.createPattern(".*Pattern.*")
                .pattern())
            .isEqualTo(".*Pattern.*");
    }

    @Test
    void badRegex() {
        final IllegalArgumentException ex =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    CommonUtil.createPattern("[");
                });
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Failed to initialise regular expression [");
    }

    @Test
    void badRegex2() {
        final IllegalArgumentException ex =
                getExpectedThrowable(IllegalArgumentException.class, () -> {
                    CommonUtil.createPattern("[", Pattern.MULTILINE);
                });
        assertWithMessage("Invalid exception message")
                .that(ex)
                .hasMessageThat()
                        .isEqualTo("Failed to initialise regular expression [");
    }

    @Test
    void fileExtensions() {
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
    void hasWhitespaceBefore() {
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
    void baseClassNameForCanonicalName() {
        assertWithMessage("Invalid base class name")
            .that(CommonUtil.baseClassName("java.util.List"))
            .isEqualTo("List");
    }

    @Test
    void baseClassNameForSimpleName() {
        assertWithMessage("Invalid base class name")
            .that(CommonUtil.baseClassName("Set"))
            .isEqualTo("Set");
    }

    @Test
    void relativeNormalizedPath() {
        final String relativePath = CommonUtil.relativizePath("/home", "/home/test");

        assertWithMessage("Invalid relative path")
            .that(relativePath)
            .isEqualTo("test");
    }

    @Test
    void relativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = CommonUtil.relativizePath(null, "/tmp");

        assertWithMessage("Invalid relative path")
            .that(relativePath)
            .isEqualTo("/tmp");
    }

    @Test
    void relativeNormalizedPathWithDenormalizedBaseDirectory() throws Exception {
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
    void pattern() {
        final boolean result = CommonUtil.isPatternValid("someValidPattern");
        assertWithMessage("Should return true when pattern is valid")
                .that(result)
                .isTrue();
    }

    @Test
    void invalidPattern() {
        final boolean result = CommonUtil.isPatternValid("some[invalidPattern");
        assertWithMessage("Should return false when pattern is invalid")
                .that(result)
                .isFalse();
    }

    @Test
    void getExistingConstructor() throws Exception {
        final Constructor<?> constructor = CommonUtil.getConstructor(String.class, String.class);

        assertWithMessage("Invalid constructor")
            .that(constructor)
            .isEqualTo(String.class.getConstructor(String.class));
    }

    @Test
    void getNonExistentConstructor() {
        final IllegalStateException ex = getExpectedThrowable(IllegalStateException.class, () -> {
            CommonUtil.getConstructor(Math.class);
        });
        assertWithMessage("Invalid exception cause")
                .that(ex)
                .hasCauseThat()
                        .isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    void invokeConstructor() throws Exception {
        final Constructor<String> constructor = String.class.getConstructor(String.class);

        final String constructedString = CommonUtil.invokeConstructor(constructor, "string");

        assertWithMessage("Invalid construction result")
            .that(constructedString)
            .isEqualTo("string");
    }

    @SuppressWarnings("rawtypes")
    @Test
    void invokeConstructorThatFails() throws Exception {
        final Constructor<Dictionary> constructor = Dictionary.class.getConstructor();
        final IllegalStateException ex = getExpectedThrowable(IllegalStateException.class, () -> {
            CommonUtil.invokeConstructor(constructor);
        });
        assertWithMessage("Invalid exception cause")
                .that(ex)
                .hasCauseThat()
                        .isInstanceOf(InstantiationException.class);
    }

    @Test
    void close() {
        final TestCloseable closeable = new TestCloseable();

        CommonUtil.close(null);
        CommonUtil.close(closeable);

        assertWithMessage("Should be closed")
                .that(closeable.closed)
                .isTrue();
    }

    @Test
    void closeWithException() {
        final IllegalStateException ex = getExpectedThrowable(IllegalStateException.class, () -> {
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
    void fillTemplateWithStringsByRegexp() {
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
    void getFileNameWithoutExtension() {
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
    void getFileExtension() {
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
    void isIdentifier() {
        assertWithMessage("Should return true when valid identifier is passed")
                .that(CommonUtil.isIdentifier("aValidIdentifier"))
                .isTrue();
    }

    @Test
    void isIdentifierEmptyString() {
        assertWithMessage("Should return false when empty string is passed")
                .that(CommonUtil.isIdentifier(""))
                .isFalse();
    }

    @Test
    void isIdentifierInvalidFirstSymbol() {
        assertWithMessage("Should return false when invalid identifier is passed")
                .that(CommonUtil.isIdentifier("1InvalidIdentifier"))
                .isFalse();
    }

    @Test
    void isIdentifierInvalidSymbols() {
        assertWithMessage("Should return false when invalid identifier is passed")
                .that(CommonUtil.isIdentifier("invalid#Identifier"))
                .isFalse();
    }

    @Test
    void isName() {
        assertWithMessage("Should return true when valid name is passed")
                .that(CommonUtil.isName("a.valid.Nam3"))
                .isTrue();
    }

    @Test
    void isNameEmptyString() {
        assertWithMessage("Should return false when empty string is passed")
                .that(CommonUtil.isName(""))
                .isFalse();
    }

    @Test
    void isNameInvalidFirstSymbol() {
        assertWithMessage("Should return false when invalid name is passed")
                .that(CommonUtil.isName("1.invalid.name"))
                .isFalse();
    }

    @Test
    void isNameEmptyPart() {
        assertWithMessage("Should return false when name has empty part")
                .that(CommonUtil.isName("invalid..name"))
                .isFalse();
    }

    @Test
    void isNameEmptyLastPart() {
        assertWithMessage("Should return false when name has empty part")
                .that(CommonUtil.isName("invalid.name."))
                .isFalse();
    }

    @Test
    void isNameInvalidSymbol() {
        assertWithMessage("Should return false when invalid name is passed")
                .that(CommonUtil.isName("invalid.name#42"))
                .isFalse();
    }

    @Test
    void isBlank() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("string"))
                .isFalse();
    }

    @Test
    void isBlankAheadWhitespace() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("  string"))
                .isFalse();
    }

    @Test
    void isBlankBehindWhitespace() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("string    "))
                .isFalse();
    }

    @Test
    void isBlankWithWhitespacesAround() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("    string    "))
                .isFalse();
    }

    @Test
    void isBlankWhitespaceInside() {
        assertWithMessage("Should return false when string is not empty")
                .that(CommonUtil.isBlank("str    ing"))
                .isFalse();
    }

    @Test
    void isBlankNullString() {
        assertWithMessage("Should return true when string is null")
                .that(CommonUtil.isBlank(null))
                .isTrue();
    }

    @Test
    void isBlankWithEmptyString() {
        assertWithMessage("Should return true when string is empty")
                .that(CommonUtil.isBlank(""))
                .isTrue();
    }

    @Test
    void isBlankWithWhitespacesOnly() {
        assertWithMessage("Should return true when string contains only spaces")
                .that(CommonUtil.isBlank("   "))
                .isTrue();
    }

    @Test
    void getUriByFilenameFindsAbsoluteResourceOnClasspath() throws Exception {
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
    void getUriByFilenameFindsRelativeResourceOnClasspath() throws Exception {
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
    void getUriByFilenameFindsResourceRelativeToRootClasspath() throws Exception {
        final String filename =
                getPackageLocation() + "/InputCommonUtilTest_resource.txt";
        final URI uri = CommonUtil.getUriByFilename(filename);
        assertWithMessage("URI is null for: %s", filename)
            .that(uri)
            .isNotNull();
        final String uriRelativeToPackage =
                "com/puppycrawl/tools/checkstyle/utils/"
                        + getPackageLocation() + "/InputCommonUtilTest_resource.txt";
        assertWithMessage("URI is relative to package %s", uriRelativeToPackage)
            .that(uri.toASCIIString())
            .doesNotContain(uriRelativeToPackage);
        final String content = IOUtils.toString(uri.toURL(), StandardCharsets.UTF_8);
        assertWithMessage("Content mismatches for: %s", uri.toASCIIString())
            .that(content)
            .startsWith("good");
    }

    @Test
    void getUriByFilenameClasspathPrefixLoadConfig() throws Exception {
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
    void getUriByFilenameFindsRelativeResourceOnClasspathPrefix() throws Exception {
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
    void isCodePointWhitespace() {
        final int[] codePoints = " 123".codePoints().toArray();
        assertThat(CommonUtil.isCodePointWhitespace(codePoints, 0))
                .isTrue();
        assertThat(CommonUtil.isCodePointWhitespace(codePoints, 1))
                .isFalse();
    }

    @Test
    void loadSuppressionsUriSyntaxException() throws Exception {
        final URL configUrl = mock();
        when(configUrl.toURI()).thenThrow(URISyntaxException.class);
        try (MockedStatic<CommonUtil> utilities =
                     mockStatic(CommonUtil.class, CALLS_REAL_METHODS)) {
            final String fileName = "/suppressions_none.xml";
            utilities.when(() -> CommonUtil.getCheckstyleResource(fileName))
                    .thenReturn(configUrl);

            final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class, () -> {
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
