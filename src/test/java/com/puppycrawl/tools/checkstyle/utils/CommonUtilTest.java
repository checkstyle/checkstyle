////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommonUtilTest extends AbstractPathTestSupport {

    /** After appending to path produces equivalent, but denormalized path. */
    private static final String PATH_DENORMALIZER = "/levelDown/.././";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/commonutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(CommonUtil.class, true),
                "Constructor is not private");
    }

    /**
     * Test CommonUtil.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs() {
        final String s1 = "\t";
        assertEquals(8, CommonUtil.lengthExpandedTabs(s1, s1.length(), 8),
                "Invalid expanded tabs length");

        final String s2 = "  \t";
        assertEquals(8, CommonUtil.lengthExpandedTabs(s2, s2.length(), 8),
                "Invalid expanded tabs length");

        final String s3 = "\t\t";
        assertEquals(16, CommonUtil.lengthExpandedTabs(s3, s3.length(), 8),
                "Invalid expanded tabs length");

        final String s4 = " \t ";
        assertEquals(9, CommonUtil.lengthExpandedTabs(s4, s4.length(), 8),
                "Invalid expanded tabs length");

        assertEquals(0, CommonUtil.lengthMinusTrailingWhitespace(""),
                "Invalid expanded tabs length");
        assertEquals(0, CommonUtil.lengthMinusTrailingWhitespace(" \t "),
                "Invalid expanded tabs length");
        assertEquals(3, CommonUtil.lengthMinusTrailingWhitespace(" 23"),
                "Invalid expanded tabs length");
        assertEquals(3, CommonUtil.lengthMinusTrailingWhitespace(" 23 \t "),
                "Invalid expanded tabs length");
    }

    @Test
    public void testCreatePattern() {
        assertEquals("Test", CommonUtil.createPattern("Test").pattern(), "invalid pattern");
        assertEquals(".*Pattern.*", CommonUtil.createPattern(".*Pattern.*")
                .pattern(), "invalid pattern");
    }

    @Test
    public void testBadRegex() {
        try {
            CommonUtil.createPattern("[");
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Failed to initialise regular expression [", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testBadRegex2() {
        try {
            CommonUtil.createPattern("[", Pattern.MULTILINE);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Failed to initialise regular expression [", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testCreationOfFakeCommentBlock() {
        final DetailAST testCommentBlock =
                CommonUtil.createBlockCommentNode("test_comment");
        assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, testCommentBlock.getType(),
                "Invalid token type");
        assertEquals("/*", testCommentBlock.getText(), "Invalid text");
        assertEquals(0, testCommentBlock.getLineNo(), "Invalid line number");

        final DetailAST contentCommentBlock = testCommentBlock.getFirstChild();
        assertEquals(TokenTypes.COMMENT_CONTENT, contentCommentBlock.getType(),
                "Invalid token type");
        assertEquals("*test_comment", contentCommentBlock.getText(), "Invalid text");
        assertEquals(0, contentCommentBlock.getLineNo(), "Invalid line number");
        assertEquals(-1, contentCommentBlock.getColumnNo(), "Invalid column number");

        final DetailAST endCommentBlock = contentCommentBlock.getNextSibling();
        assertEquals(TokenTypes.BLOCK_COMMENT_END, endCommentBlock.getType(), "Invalid token type");
        assertEquals("*/", endCommentBlock.getText(), "Invalid text");
    }

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        final File pdfFile = new File("file.pdf");
        assertFalse(CommonUtil.matchesFileExtension(pdfFile, fileExtensions),
                "Invalid file extension");
        assertTrue(CommonUtil.matchesFileExtension(pdfFile), "Invalid file extension");
        assertTrue(CommonUtil.matchesFileExtension(pdfFile, (String[]) null),
                "Invalid file extension");
        final File javaFile = new File("file.java");
        assertTrue(CommonUtil.matchesFileExtension(javaFile, fileExtensions),
                "Invalid file extension");
        final File emptyExtensionFile = new File("file.");
        assertTrue(CommonUtil.matchesFileExtension(emptyExtensionFile, ""),
                "Invalid file extension");
        assertFalse(CommonUtil.matchesFileExtension(pdfFile, ".noMatch"), "Invalid file extension");
        assertTrue(CommonUtil.matchesFileExtension(pdfFile, ".pdf"), "Invalid file extension");
    }

    @Test
    public void testHasWhitespaceBefore() {
        assertTrue(CommonUtil.hasWhitespaceBefore(0, "a"), "Invalid result");
        assertTrue(CommonUtil.hasWhitespaceBefore(4, "    a"), "Invalid result");
        assertFalse(CommonUtil.hasWhitespaceBefore(5, "    a"), "Invalid result");
    }

    @Test
    public void testBaseClassNameForCanonicalName() {
        assertEquals("List", CommonUtil.baseClassName("java.util.List"), "Invalid base class name");
    }

    @Test
    public void testBaseClassNameForSimpleName() {
        assertEquals("Set", CommonUtil.baseClassName("Set"), "Invalid base class name");
    }

    @Test
    public void testRelativeNormalizedPath() {
        final String relativePath = CommonUtil.relativizeAndNormalizePath("/home", "/home/test");

        assertEquals("test", relativePath, "Invalid relative path");
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = CommonUtil.relativizeAndNormalizePath(null, "/tmp");

        assertEquals("/tmp", relativePath, "Invalid relative path");
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = CommonUtil.relativizeAndNormalizePath(basePath,
            absoluteFilePath);

        assertEquals("SampleFile.java", relativePath, "Invalid relative path");
    }

    @Test
    public void testPattern() {
        final boolean result = CommonUtil.isPatternValid("someValidPattern");
        assertTrue(result, "Should return true when pattern is valid");
    }

    @Test
    public void testInvalidPattern() {
        final boolean result = CommonUtil.isPatternValid("some[invalidPattern");
        assertFalse(result, "Should return false when pattern is invalid");
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        final Constructor<?> constructor = CommonUtil.getConstructor(String.class, String.class);

        assertEquals(String.class.getConstructor(String.class), constructor, "Invalid constructor");
    }

    @Test
    public void testGetNonExistentConstructor() {
        try {
            CommonUtil.getConstructor(Math.class);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException expected) {
            assertSame(NoSuchMethodException.class, expected.getCause().getClass(),
                    "Invalid exception cause");
        }
    }

    @Test
    public void testInvokeConstructor() throws NoSuchMethodException {
        final Constructor<String> constructor = String.class.getConstructor(String.class);

        final String constructedString = CommonUtil.invokeConstructor(constructor, "string");

        assertEquals("string", constructedString, "Invalid construction result");
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testInvokeConstructorThatFails() throws NoSuchMethodException {
        final Constructor<Dictionary> constructor = Dictionary.class.getConstructor();

        try {
            CommonUtil.invokeConstructor(constructor);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException expected) {
            assertSame(InstantiationException.class,
                expected.getCause().getClass(), "Invalid exception cause");
        }
    }

    @Test
    public void testClose() {
        final TestCloseable closeable = new TestCloseable();

        CommonUtil.close(null);
        CommonUtil.close(closeable);

        assertTrue(closeable.closed, "Should be closed");
    }

    @Test
    public void testCloseWithException() {
        try {
            CommonUtil.close(() -> {
                throw new IOException("Test IOException");
            });
            fail("exception expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Cannot close the stream", ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testFillTemplateWithStringsByRegexp() {
        assertEquals("template", CommonUtil.fillTemplateWithStringsByRegexp("template",
                "lineToPlaceInTemplate", Pattern.compile("NO MATCH")), "invalid result");
        assertEquals("before word after",
                CommonUtil.fillTemplateWithStringsByRegexp("before $0 after", "word",
                        Pattern.compile("\\w+")), "invalid result");
        assertEquals("before word 123 after1 word after2 123 after3",
                CommonUtil.fillTemplateWithStringsByRegexp("before $0 after1 $1 after2 $2 after3",
                        "word 123", Pattern.compile("(\\w+) (\\d+)")), "invalid result");
    }

    @Test
    public void testGetFileNameWithoutExtension() {
        assertEquals("filename", CommonUtil.getFileNameWithoutExtension("filename"),
                "invalid result");
        assertEquals("filename", CommonUtil.getFileNameWithoutExtension("filename.extension"),
                "invalid result");
        assertEquals("filename.subext",
                CommonUtil.getFileNameWithoutExtension("filename.subext.extension"),
                "invalid result");
    }

    @Test
    public void testGetFileExtension() {
        assertEquals("", CommonUtil.getFileExtension("filename"), "Invalid extension");
        assertEquals("extension", CommonUtil.getFileExtension("filename.extension"),
                "Invalid extension");
        assertEquals("extension", CommonUtil.getFileExtension("filename.subext.extension"),
                "Invalid extension");
    }

    @Test
    public void testIsIdentifier() {
        assertTrue(CommonUtil.isIdentifier("aValidIdentifier"),
                "Should return true when valid identifier is passed");
    }

    @Test
    public void testIsIdentifierEmptyString() {
        assertFalse(CommonUtil.isIdentifier(""), "Should return false when empty string is passed");
    }

    @Test
    public void testIsIdentifierInvalidFirstSymbol() {
        assertFalse(CommonUtil.isIdentifier("1InvalidIdentifier"),
                "Should return false when invalid identifier is passed");
    }

    @Test
    public void testIsIdentifierInvalidSymbols() {
        assertFalse(CommonUtil.isIdentifier("invalid#Identifier"),
                "Should return false when invalid identifier is passed");
    }

    @Test
    public void testIsName() {
        assertTrue(CommonUtil.isName("a.valid.Nam3"),
                "Should return true when valid name is passed");
    }

    @Test
    public void testIsNameEmptyString() {
        assertFalse(CommonUtil.isName(""), "Should return false when empty string is passed");
    }

    @Test
    public void testIsNameInvalidFirstSymbol() {
        assertFalse(CommonUtil.isName("1.invalid.name"),
                "Should return false when invalid name is passed");
    }

    @Test
    public void testIsNameEmptyPart() {
        assertFalse(CommonUtil.isName("invalid..name"),
                "Should return false when name has empty part");
    }

    @Test
    public void testIsNameEmptyLastPart() {
        assertFalse(CommonUtil.isName("invalid.name."),
                "Should return false when name has empty part");
    }

    @Test
    public void testIsNameInvalidSymbol() {
        assertFalse(CommonUtil.isName("invalid.name#42"),
                "Should return false when invalid name is passed");
    }

    @Test
    public void testIsBlank() {
        assertFalse(CommonUtil.isBlank("string"), "Should return false when string is not empty");
    }

    @Test
    public void testIsBlankAheadWhitespace() {
        assertFalse(CommonUtil.isBlank("  string"), "Should return false when string is not empty");
    }

    @Test
    public void testIsBlankBehindWhitespace() {
        assertFalse(CommonUtil.isBlank("string    "),
                "Should return false when string is not empty");
    }

    @Test
    public void testIsBlankWithWhitespacesAround() {
        assertFalse(CommonUtil.isBlank("    string    "),
                "Should return false when string is not empty");
    }

    @Test
    public void testIsBlankWhitespaceInside() {
        assertFalse(CommonUtil.isBlank("str    ing"),
                "Should return false when string is not empty");
    }

    @Test
    public void testIsBlankNullString() {
        assertTrue(CommonUtil.isBlank(null), "Should return true when string is null");
    }

    @Test
    public void testIsBlankWithEmptyString() {
        assertTrue(CommonUtil.isBlank(""), "Should return true when string is empty");
    }

    @Test
    public void testIsBlankWithWhitespacesOnly() {
        assertTrue(CommonUtil.isBlank("   "),
                "Should return true when string contains only spaces");
    }

    @Test
    public void testIsIntValidString() {
        assertTrue(CommonUtil.isInt("42"), "Should return true when string is null");
    }

    @Test
    public void testIsIntInvalidString() {
        assertFalse(CommonUtil.isInt("foo"),
                "Should return false when object passed is not integer");
    }

    @Test
    public void testIsIntNull() {
        assertFalse(CommonUtil.isInt(null), "Should return false when null is passed");
    }

    @Test
    public void testGetUriByFilenameFindsAbsoluteResourceOnClasspath() throws Exception {
        final String filename =
            "/" + getPackageLocation() + "/InputCommonUtilTest_empty_checks.xml";
        final URI uri = CommonUtil.getUriByFilename(filename);
        assertThat("URI is null for: " + filename, uri, is(not(nullValue())));
    }

    @Test
    public void testGetUriByFilenameFindsRelativeResourceOnClasspath() throws Exception {
        final String filename =
            getPackageLocation() + "/InputCommonUtilTest_empty_checks.xml";
        final URI uri = CommonUtil.getUriByFilename(filename);
        assertThat("URI is null for: " + filename, uri, is(not(nullValue())));
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
        assertThat("URI is null for: " + filename, uri, is(not(nullValue())));
        final String uriRelativeToPackage =
                "com/puppycrawl/tools/checkstyle/utils/"
                        + getPackageLocation() + "/InputCommonUtilTest_resource.txt";
        assertThat("URI is relative to package " + uriRelativeToPackage,
            uri.toString(), not(containsString(uriRelativeToPackage)));
        final String content = IOUtils.toString(uri.toURL(), StandardCharsets.UTF_8);
        assertThat("Content mismatches for: " + uri,
                content, startsWith("good"));
    }

    private static class TestCloseable implements Closeable {

        private boolean closed;

        @Override
        public void close() {
            closed = true;
        }

    }

}
