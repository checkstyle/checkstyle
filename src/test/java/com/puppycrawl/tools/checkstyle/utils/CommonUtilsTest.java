////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Dictionary;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

@RunWith(PowerMockRunner.class)
public class CommonUtilsTest {

    /** After appending to path produces equivalent, but denormalized path. */
    private static final String PATH_DENORMALIZER = "/levelDown/.././";

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(CommonUtils.class, true));
    }

    /**
     * Test CommonUtils.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs() {
        final String s1 = "\t";
        assertEquals("Invalid expanded tabs length", 8,
            CommonUtils.lengthExpandedTabs(s1, s1.length(), 8));

        final String s2 = "  \t";
        assertEquals("Invalid expanded tabs length", 8,
            CommonUtils.lengthExpandedTabs(s2, s2.length(), 8));

        final String s3 = "\t\t";
        assertEquals("Invalid expanded tabs length", 16,
            CommonUtils.lengthExpandedTabs(s3, s3.length(), 8));

        final String s4 = " \t ";
        assertEquals("Invalid expanded tabs length", 9,
            CommonUtils.lengthExpandedTabs(s4, s4.length(), 8));

        assertEquals("Invalid expanded tabs length", 0,
            CommonUtils.lengthMinusTrailingWhitespace(""));
        assertEquals("Invalid expanded tabs length", 0,
            CommonUtils.lengthMinusTrailingWhitespace(" \t "));
        assertEquals("Invalid expanded tabs length", 3,
            CommonUtils.lengthMinusTrailingWhitespace(" 23"));
        assertEquals("Invalid expanded tabs length", 3,
            CommonUtils.lengthMinusTrailingWhitespace(" 23 \t "));
    }

    @Test
    public void testCreatePattern() {
        assertEquals("invalid pattern", "Test", CommonUtils.createPattern("Test").pattern());
        assertEquals("invalid pattern", ".*Pattern.*", CommonUtils.createPattern(".*Pattern.*")
                .pattern());
    }

    @Test
    public void testBadRegex() {
        try {
            CommonUtils.createPattern("[");
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
            CommonUtils.createPattern("[", Pattern.MULTILINE);
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
                CommonUtils.createBlockCommentNode("test_comment");
        assertEquals("Invalid token type",
                TokenTypes.BLOCK_COMMENT_BEGIN, testCommentBlock.getType());
        assertEquals("Invalid text", "/*", testCommentBlock.getText());
        assertEquals("Invalid line number", 0, testCommentBlock.getLineNo());

        final DetailAST contentCommentBlock = testCommentBlock.getFirstChild();
        assertEquals("Invalid tiken type",
                TokenTypes.COMMENT_CONTENT, contentCommentBlock.getType());
        assertEquals("Invalid text", "*test_comment", contentCommentBlock.getText());
        assertEquals("Invalid line number", 0, contentCommentBlock.getLineNo());
        assertEquals("Invalid column number", -1, contentCommentBlock.getColumnNo());

        final DetailAST endCommentBlock = contentCommentBlock.getNextSibling();
        assertEquals("Invalid tiken type", TokenTypes.BLOCK_COMMENT_END, endCommentBlock.getType());
        assertEquals("Invalid text", "*/", endCommentBlock.getText());
    }

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        final File pdfFile = new File("file.pdf");
        assertFalse("Invalid file extension",
            CommonUtils.matchesFileExtension(pdfFile, fileExtensions));
        assertTrue("Invalid file extension",
            CommonUtils.matchesFileExtension(pdfFile));
        assertTrue("Invalid file extension",
            CommonUtils.matchesFileExtension(pdfFile, (String[]) null));
        final File javaFile = new File("file.java");
        assertTrue("Invalid file extension",
            CommonUtils.matchesFileExtension(javaFile, fileExtensions));
        final File emptyExtensionFile = new File("file.");
        assertTrue("Invalid file extension",
            CommonUtils.matchesFileExtension(emptyExtensionFile, ""));
        assertFalse("Invalid file extension",
            CommonUtils.matchesFileExtension(pdfFile, ".noMatch"));
        assertTrue("Invalid file extension",
            CommonUtils.matchesFileExtension(pdfFile, ".pdf"));
    }

    @Test
    public void testHasWhitespaceBefore() {
        assertTrue("Invalid result",
            CommonUtils.hasWhitespaceBefore(0, "a"));
        assertTrue("Invalid result",
            CommonUtils.hasWhitespaceBefore(4, "    a"));
        assertFalse("Invalid result",
            CommonUtils.hasWhitespaceBefore(5, "    a"));
    }

    @Test
    public void testBaseClassNameForCanonicalName() {
        assertEquals("Invalid base class name", "List",
            CommonUtils.baseClassName("java.util.List"));
    }

    @Test
    public void testBaseClassNameForSimpleName() {

        assertEquals("Invalid base class name", "Set",
            CommonUtils.baseClassName("Set"));
    }

    @Test
    public void testRelativeNormalizedPath() {
        final String relativePath = CommonUtils.relativizeAndNormalizePath("/home", "/home/test");

        assertEquals("Invalid relative path", "test", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = CommonUtils.relativizeAndNormalizePath(null, "/tmp");

        assertEquals("Invalid relative path", "/tmp", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = CommonUtils.relativizeAndNormalizePath(basePath,
            absoluteFilePath);

        assertEquals("Invalid relative path", "SampleFile.java", relativePath);
    }

    @Test
    public void testInvalidPattern() {
        final boolean result = CommonUtils.isPatternValid("some[invalidPattern");
        assertFalse("Should return false when pattern is invalid", result);
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        final Constructor<?> constructor = CommonUtils.getConstructor(String.class, String.class);

        assertEquals("Invalid constructor",
            String.class.getConstructor(String.class), constructor);
    }

    @Test
    public void testGetNonExistingConstructor() {
        try {
            CommonUtils.getConstructor(Math.class);
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

        final String constructedString = CommonUtils.invokeConstructor(constructor, "string");

        assertEquals("Invalid construction result", "string", constructedString);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testInvokeConstructorThatFails() throws NoSuchMethodException {
        final Constructor<Dictionary> constructor = Dictionary.class.getConstructor();

        try {
            CommonUtils.invokeConstructor(constructor);
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

        CommonUtils.close(null);
        CommonUtils.close(closeable);

        assertTrue("Should be closed", closeable.closed);
    }

    @Test
    public void testCloseWithException() {
        try {
            CommonUtils.close(() -> {
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
        assertEquals("invalid result", "template", CommonUtils.fillTemplateWithStringsByRegexp(
                "template", "lineToPlaceInTemplate", Pattern.compile("NO MATCH")));
        assertEquals(
                "invalid result",
                "before word after",
                CommonUtils.fillTemplateWithStringsByRegexp("before $0 after", "word",
                        Pattern.compile("\\w+")));
        assertEquals("invalid result", "before word 123 after1 word after2 123 after3",
                CommonUtils.fillTemplateWithStringsByRegexp("before $0 after1 $1 after2 $2 after3",
                        "word 123", Pattern.compile("(\\w+) (\\d+)")));
    }

    @Test
    public void testGetFileNameWithoutExtension() {
        assertEquals("invalid result", "filename",
                CommonUtils.getFileNameWithoutExtension("filename"));
        assertEquals("invalid result", "filename",
                CommonUtils.getFileNameWithoutExtension("filename.extension"));
        assertEquals("invalid result", "filename.subext",
                CommonUtils.getFileNameWithoutExtension("filename.subext.extension"));
    }

    @Test
    public void testGetFileExtension() {
        assertEquals("Invalid extension", "", CommonUtils.getFileExtension("filename"));
        assertEquals("Invalid extension", "extension",
                CommonUtils.getFileExtension("filename.extension"));
        assertEquals("Invalid extension", "extension",
                CommonUtils.getFileExtension("filename.subext.extension"));
    }

    @Test
    public void testIsIdentifier() {
        assertTrue("Should return true when valid identifier is passed",
            CommonUtils.isIdentifier("aValidIdentifier"));
    }

    @Test
    public void testIsIdentifierEmptyString() {
        assertFalse("Should return false when empty string is passed",
            CommonUtils.isIdentifier(""));
    }

    @Test
    public void testIsIdentifierInvalidFirstSymbol() {
        assertFalse("Should return false when invalid identifier is passed",
            CommonUtils.isIdentifier("1InvalidIdentifier"));
    }

    @Test
    public void testIsIdentifierInvalidSymbols() {
        assertFalse("Should return false when invalid identifier is passed",
            CommonUtils.isIdentifier("invalid#Identifier"));
    }

    @Test
    public void testIsName() {
        assertTrue("Should return true when valid name is passed",
            CommonUtils.isName("a.valid.Nam3"));
    }

    @Test
    public void testIsNameEmptyString() {
        assertFalse("Should return false when empty string is passed",
            CommonUtils.isName(""));
    }

    @Test
    public void testIsNameInvalidFirstSymbol() {
        assertFalse("Should return false when invalid name is passed",
            CommonUtils.isName("1.invalid.name"));
    }

    @Test
    public void testIsNameEmptyPart() {
        assertFalse("Should return false when name has empty part",
            CommonUtils.isName("invalid..name"));
    }

    @Test
    public void testIsNameEmptyLastPart() {
        assertFalse("Should return false when name has empty part",
            CommonUtils.isName("invalid.name."));
    }

    @Test
    public void testIsNameInvalidSymbol() {
        assertFalse("Should return false when invalid name is passed",
            CommonUtils.isName("invalid.name#42"));
    }

    @Test
    public void testIsBlank() {
        assertFalse("Should return false when string is not empty",
            CommonUtils.isBlank("string"));
    }

    @Test
    public void testIsBlankAheadWhitespace() {
        assertFalse("Should return false when string is not empty",
            CommonUtils.isBlank("  string"));
    }

    @Test
    public void testIsBlankBehindWhitespace() {
        assertFalse("Should return false when string is not empty",
            CommonUtils.isBlank("string    "));
    }

    @Test
    public void testIsBlankWithWhitespacesAround() {
        assertFalse("Should return false when string is not empty",
            CommonUtils.isBlank("    string    "));
    }

    @Test
    public void testIsBlankWhitespaceInside() {
        assertFalse("Should return false when string is not empty",
            CommonUtils.isBlank("str    ing"));
    }

    @Test
    public void testIsBlankNullString() {
        assertTrue("Should return true when string is null",
            CommonUtils.isBlank(null));
    }

    @Test
    public void testIsBlankWithEmptyString() {
        assertTrue("Should return true when string is empty",
            CommonUtils.isBlank(""));
    }

    @Test
    public void testIsBlankWithWhitespacesOnly() {
        assertTrue("Should return true when string contains only spaces",
            CommonUtils.isBlank("   "));
    }

    @Test
    @PrepareForTest({ CommonUtils.class, CommonUtilsTest.class })
    @SuppressWarnings("unchecked")
    public void testLoadSuppressionsUriSyntaxException() throws Exception {
        final URL configUrl = mock(URL.class);

        when(configUrl.toURI()).thenThrow(URISyntaxException.class);
        mockStatic(CommonUtils.class, Mockito.CALLS_REAL_METHODS);
        final String fileName = "suppressions_none.xml";
        when(CommonUtils.class.getResource(fileName)).thenReturn(configUrl);

        try {
            CommonUtils.getUriByFilename(fileName);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception cause", ex.getCause() instanceof URISyntaxException);
            assertEquals("Invalid exception message",
                "Unable to find: " + fileName, ex.getMessage());
        }
    }

    @Test
    public void testIsIntValidString() {
        assertTrue("Should return true when string is null", CommonUtils.isInt("42"));
    }

    @Test
    public void testIsIntInvalidString() {
        assertFalse("Should return false when object passed is not integer",
            CommonUtils.isInt("foo"));
    }

    @Test
    public void testIsIntNull() {
        assertFalse("Should return false when null is passed",
            CommonUtils.isInt(null));
    }

    private static class TestCloseable implements Closeable {
        private boolean closed;

        @Override
        public void close() {
            closed = true;
        }
    }
}
