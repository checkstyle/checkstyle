////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

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
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(CommonUtil.class, true));
    }

    /**
     * Test CommonUtil.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs() {
        final String s1 = "\t";
        assertEquals("Invalid expanded tabs length", 8,
            CommonUtil.lengthExpandedTabs(s1, s1.length(), 8));

        final String s2 = "  \t";
        assertEquals("Invalid expanded tabs length", 8,
            CommonUtil.lengthExpandedTabs(s2, s2.length(), 8));

        final String s3 = "\t\t";
        assertEquals("Invalid expanded tabs length", 16,
            CommonUtil.lengthExpandedTabs(s3, s3.length(), 8));

        final String s4 = " \t ";
        assertEquals("Invalid expanded tabs length", 9,
            CommonUtil.lengthExpandedTabs(s4, s4.length(), 8));

        assertEquals("Invalid expanded tabs length", 0,
            CommonUtil.lengthMinusTrailingWhitespace(""));
        assertEquals("Invalid expanded tabs length", 0,
            CommonUtil.lengthMinusTrailingWhitespace(" \t "));
        assertEquals("Invalid expanded tabs length", 3,
            CommonUtil.lengthMinusTrailingWhitespace(" 23"));
        assertEquals("Invalid expanded tabs length", 3,
            CommonUtil.lengthMinusTrailingWhitespace(" 23 \t "));
    }

    @Test
    public void testCreatePattern() {
        assertEquals("invalid pattern", "Test", CommonUtil.createPattern("Test").pattern());
        assertEquals("invalid pattern", ".*Pattern.*", CommonUtil.createPattern(".*Pattern.*")
                .pattern());
    }

    @Test
    public void testBadRegex() {
        try {
            CommonUtil.createPattern("[");
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                "Failed to initialise regular expression [", ex.getMessage());
        }
    }

    @Test
    public void testBadRegex2() {
        try {
            CommonUtil.createPattern("[", Pattern.MULTILINE);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                "Failed to initialise regular expression [", ex.getMessage());
        }
    }

    @Test
    public void testCreationOfFakeCommentBlock() {
        final DetailAST testCommentBlock =
                CommonUtil.createBlockCommentNode("test_comment");
        assertEquals("Invalid token type",
                TokenTypes.BLOCK_COMMENT_BEGIN, testCommentBlock.getType());
        assertEquals("Invalid text", "/*", testCommentBlock.getText());
        assertEquals("Invalid line number", 0, testCommentBlock.getLineNo());

        final DetailAST contentCommentBlock = testCommentBlock.getFirstChild();
        assertEquals("Invalid token type",
                TokenTypes.COMMENT_CONTENT, contentCommentBlock.getType());
        assertEquals("Invalid text", "*test_comment", contentCommentBlock.getText());
        assertEquals("Invalid line number", 0, contentCommentBlock.getLineNo());
        assertEquals("Invalid column number", -1, contentCommentBlock.getColumnNo());

        final DetailAST endCommentBlock = contentCommentBlock.getNextSibling();
        assertEquals("Invalid token type", TokenTypes.BLOCK_COMMENT_END, endCommentBlock.getType());
        assertEquals("Invalid text", "*/", endCommentBlock.getText());
    }

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        final File pdfFile = new File("file.pdf");
        assertFalse("Invalid file extension",
            CommonUtil.matchesFileExtension(pdfFile, fileExtensions));
        assertTrue("Invalid file extension",
            CommonUtil.matchesFileExtension(pdfFile));
        assertTrue("Invalid file extension",
            CommonUtil.matchesFileExtension(pdfFile, (String[]) null));
        final File javaFile = new File("file.java");
        assertTrue("Invalid file extension",
            CommonUtil.matchesFileExtension(javaFile, fileExtensions));
        final File emptyExtensionFile = new File("file.");
        assertTrue("Invalid file extension",
            CommonUtil.matchesFileExtension(emptyExtensionFile, ""));
        assertFalse("Invalid file extension",
            CommonUtil.matchesFileExtension(pdfFile, ".noMatch"));
        assertTrue("Invalid file extension",
            CommonUtil.matchesFileExtension(pdfFile, ".pdf"));
    }

    @Test
    public void testHasWhitespaceBefore() {
        assertTrue("Invalid result",
            CommonUtil.hasWhitespaceBefore(0, "a"));
        assertTrue("Invalid result",
            CommonUtil.hasWhitespaceBefore(4, "    a"));
        assertFalse("Invalid result",
            CommonUtil.hasWhitespaceBefore(5, "    a"));
    }

    @Test
    public void testBaseClassNameForCanonicalName() {
        assertEquals("Invalid base class name", "List",
            CommonUtil.baseClassName("java.util.List"));
    }

    @Test
    public void testBaseClassNameForSimpleName() {
        assertEquals("Invalid base class name", "Set",
            CommonUtil.baseClassName("Set"));
    }

    @Test
    public void testRelativeNormalizedPath() {
        final String relativePath = CommonUtil.relativizeAndNormalizePath("/home", "/home/test");

        assertEquals("Invalid relative path", "test", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = CommonUtil.relativizeAndNormalizePath(null, "/tmp");

        assertEquals("Invalid relative path", "/tmp", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = CommonUtil.relativizeAndNormalizePath(basePath,
            absoluteFilePath);

        assertEquals("Invalid relative path", "SampleFile.java", relativePath);
    }

    @Test
    public void testPattern() {
        final boolean result = CommonUtil.isPatternValid("someValidPattern");
        assertTrue("Should return true when pattern is valid", result);
    }

    @Test
    public void testInvalidPattern() {
        final boolean result = CommonUtil.isPatternValid("some[invalidPattern");
        assertFalse("Should return false when pattern is invalid", result);
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        final Constructor<?> constructor = CommonUtil.getConstructor(String.class, String.class);

        assertEquals("Invalid constructor",
            String.class.getConstructor(String.class), constructor);
    }

    @Test
    public void testGetNonExistentConstructor() {
        try {
            CommonUtil.getConstructor(Math.class);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException expected) {
            assertSame("Invalid exception cause",
                NoSuchMethodException.class, expected.getCause().getClass());
        }
    }

    @Test
    public void testInvokeConstructor() throws NoSuchMethodException {
        final Constructor<String> constructor = String.class.getConstructor(String.class);

        final String constructedString = CommonUtil.invokeConstructor(constructor, "string");

        assertEquals("Invalid construction result", "string", constructedString);
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
            assertSame("Invalid exception cause", InstantiationException.class,
                expected.getCause().getClass());
        }
    }

    @Test
    public void testClose() {
        final TestCloseable closeable = new TestCloseable();

        CommonUtil.close(null);
        CommonUtil.close(closeable);

        assertTrue("Should be closed", closeable.closed);
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
            assertEquals("Invalid exception message",
                "Cannot close the stream", ex.getMessage());
        }
    }

    @Test
    public void testFillTemplateWithStringsByRegexp() {
        assertEquals("invalid result", "template", CommonUtil.fillTemplateWithStringsByRegexp(
                "template", "lineToPlaceInTemplate", Pattern.compile("NO MATCH")));
        assertEquals(
                "invalid result",
                "before word after",
                CommonUtil.fillTemplateWithStringsByRegexp("before $0 after", "word",
                        Pattern.compile("\\w+")));
        assertEquals("invalid result", "before word 123 after1 word after2 123 after3",
                CommonUtil.fillTemplateWithStringsByRegexp("before $0 after1 $1 after2 $2 after3",
                        "word 123", Pattern.compile("(\\w+) (\\d+)")));
    }

    @Test
    public void testGetFileNameWithoutExtension() {
        assertEquals("invalid result", "filename",
                CommonUtil.getFileNameWithoutExtension("filename"));
        assertEquals("invalid result", "filename",
                CommonUtil.getFileNameWithoutExtension("filename.extension"));
        assertEquals("invalid result", "filename.subext",
                CommonUtil.getFileNameWithoutExtension("filename.subext.extension"));
    }

    @Test
    public void testGetFileExtension() {
        assertEquals("Invalid extension", "", CommonUtil.getFileExtension("filename"));
        assertEquals("Invalid extension", "extension",
                CommonUtil.getFileExtension("filename.extension"));
        assertEquals("Invalid extension", "extension",
                CommonUtil.getFileExtension("filename.subext.extension"));
    }

    @Test
    public void testIsIdentifier() {
        assertTrue("Should return true when valid identifier is passed",
            CommonUtil.isIdentifier("aValidIdentifier"));
    }

    @Test
    public void testIsIdentifierEmptyString() {
        assertFalse("Should return false when empty string is passed",
            CommonUtil.isIdentifier(""));
    }

    @Test
    public void testIsIdentifierInvalidFirstSymbol() {
        assertFalse("Should return false when invalid identifier is passed",
            CommonUtil.isIdentifier("1InvalidIdentifier"));
    }

    @Test
    public void testIsIdentifierInvalidSymbols() {
        assertFalse("Should return false when invalid identifier is passed",
            CommonUtil.isIdentifier("invalid#Identifier"));
    }

    @Test
    public void testIsName() {
        assertTrue("Should return true when valid name is passed",
            CommonUtil.isName("a.valid.Nam3"));
    }

    @Test
    public void testIsNameEmptyString() {
        assertFalse("Should return false when empty string is passed",
            CommonUtil.isName(""));
    }

    @Test
    public void testIsNameInvalidFirstSymbol() {
        assertFalse("Should return false when invalid name is passed",
            CommonUtil.isName("1.invalid.name"));
    }

    @Test
    public void testIsNameEmptyPart() {
        assertFalse("Should return false when name has empty part",
            CommonUtil.isName("invalid..name"));
    }

    @Test
    public void testIsNameEmptyLastPart() {
        assertFalse("Should return false when name has empty part",
            CommonUtil.isName("invalid.name."));
    }

    @Test
    public void testIsNameInvalidSymbol() {
        assertFalse("Should return false when invalid name is passed",
            CommonUtil.isName("invalid.name#42"));
    }

    @Test
    public void testIsBlank() {
        assertFalse("Should return false when string is not empty",
            CommonUtil.isBlank("string"));
    }

    @Test
    public void testIsBlankAheadWhitespace() {
        assertFalse("Should return false when string is not empty",
            CommonUtil.isBlank("  string"));
    }

    @Test
    public void testIsBlankBehindWhitespace() {
        assertFalse("Should return false when string is not empty",
            CommonUtil.isBlank("string    "));
    }

    @Test
    public void testIsBlankWithWhitespacesAround() {
        assertFalse("Should return false when string is not empty",
            CommonUtil.isBlank("    string    "));
    }

    @Test
    public void testIsBlankWhitespaceInside() {
        assertFalse("Should return false when string is not empty",
            CommonUtil.isBlank("str    ing"));
    }

    @Test
    public void testIsBlankNullString() {
        assertTrue("Should return true when string is null",
            CommonUtil.isBlank(null));
    }

    @Test
    public void testIsBlankWithEmptyString() {
        assertTrue("Should return true when string is empty",
            CommonUtil.isBlank(""));
    }

    @Test
    public void testIsBlankWithWhitespacesOnly() {
        assertTrue("Should return true when string contains only spaces",
            CommonUtil.isBlank("   "));
    }

    @Test
    public void testIsIntValidString() {
        assertTrue("Should return true when string is null", CommonUtil.isInt("42"));
    }

    @Test
    public void testIsIntInvalidString() {
        assertFalse("Should return false when object passed is not integer",
            CommonUtil.isInt("foo"));
    }

    @Test
    public void testIsIntNull() {
        assertFalse("Should return false when null is passed",
            CommonUtil.isInt(null));
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
