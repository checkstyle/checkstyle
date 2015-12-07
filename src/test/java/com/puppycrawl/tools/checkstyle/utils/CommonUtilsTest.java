////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
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

import org.apache.commons.beanutils.ConversionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

@RunWith(PowerMockRunner.class)
public class CommonUtilsTest {

    /** After appending to path produces equivalent, but denormalized path. */
    private static final String PATH_DENORMALIZER = "/levelDown/.././";

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(CommonUtils.class);
    }

    /**
     * Test CommonUtils.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs()
        throws Exception {
        final String s1 = "\t";
        assertEquals(8, CommonUtils.lengthExpandedTabs(s1, s1.length(), 8));

        final String s2 = "  \t";
        assertEquals(8, CommonUtils.lengthExpandedTabs(s2, s2.length(), 8));

        final String s3 = "\t\t";
        assertEquals(16, CommonUtils.lengthExpandedTabs(s3, s3.length(), 8));

        final String s4 = " \t ";
        assertEquals(9, CommonUtils.lengthExpandedTabs(s4, s4.length(), 8));

        assertEquals(0, CommonUtils.lengthMinusTrailingWhitespace(""));
        assertEquals(0, CommonUtils.lengthMinusTrailingWhitespace(" \t "));
        assertEquals(3, CommonUtils.lengthMinusTrailingWhitespace(" 23"));
        assertEquals(3, CommonUtils.lengthMinusTrailingWhitespace(" 23 \t "));
    }

    @Test(expected = ConversionException.class)
    public void testBadRegex() {
        CommonUtils.createPattern("[");
    }

    @Test(expected = ConversionException.class)
    public void testBadRegex2() {
        CommonUtils.createPattern("[", Pattern.MULTILINE);
    }

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        final File pdfFile = new File("file.pdf");
        assertFalse(CommonUtils.matchesFileExtension(pdfFile, fileExtensions));
        assertTrue(CommonUtils.matchesFileExtension(pdfFile, (String[]) null));
        final File javaFile = new File("file.java");
        assertTrue(CommonUtils.matchesFileExtension(javaFile, fileExtensions));
        final File emptyExtensionFile = new File("file.");
        assertTrue(CommonUtils.matchesFileExtension(emptyExtensionFile, ""));
    }

    @Test
    public void testBaseClassNameForCanonicalName() {
        assertEquals("List", CommonUtils.baseClassName("java.util.List"));
    }

    @Test
    public void testBaseClassNameForSimpleName() {
        assertEquals("Set", CommonUtils.baseClassName("Set"));
    }

    @Test
    public void testRelativeNormalizedPath() {
        final String relativePath = CommonUtils.relativizeAndNormalizePath("/home", "/home/test");

        assertEquals("test", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = CommonUtils.relativizeAndNormalizePath(null, "/tmp");

        assertEquals("/tmp", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = CommonUtils.relativizeAndNormalizePath(basePath,
            absoluteFilePath);

        assertEquals("SampleFile.java", relativePath);
    }

    @Test
    public void testInvalidPattern() {
        final boolean result = CommonUtils.isPatternValid("some[invalidPattern");
        assertFalse(result);
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        final Constructor<?> constructor = CommonUtils.getConstructor(String.class, String.class);

        assertEquals(String.class.getConstructor(String.class), constructor);
    }

    @Test
    public void testGetNonExistingConstructor() {
        try {
            CommonUtils.getConstructor(Math.class);
            fail();
        }
        catch (IllegalStateException expected) {
            assertSame(NoSuchMethodException.class, expected.getCause().getClass());
        }
    }

    @Test
    public void testInvokeConstructor() throws NoSuchMethodException {
        final Constructor<String> constructor = String.class.getConstructor(String.class);

        final String constructedString = CommonUtils.invokeConstructor(constructor, "string");

        assertEquals("string", constructedString);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testInvokeConstructorThatFails() throws NoSuchMethodException {
        final Constructor<Dictionary> constructor = Dictionary.class.getConstructor();

        try {
            CommonUtils.invokeConstructor(constructor);
            fail();
        }
        catch (IllegalStateException expected) {
            assertSame(InstantiationException.class, expected.getCause().getClass());
        }
    }

    @Test
    public void testClose() {
        final TestCloseable closeable = new TestCloseable();

        CommonUtils.close(null);
        CommonUtils.close(closeable);

        assertTrue(closeable.closed);
    }

    @Test(expected = IllegalStateException.class)
    public void testCloseWithException() {
        CommonUtils.close(new Closeable() {

            @Override
            public void close() throws IOException {
                throw new IOException("Test IOException");
            }
        });
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
            assertTrue(ex.getCause() instanceof URISyntaxException);
            assertEquals("Unable to find: " + fileName, ex.getMessage());
        }
    }

    private static class TestCloseable implements Closeable {
        private boolean closed;

        @Override
        public void close() {
            closed = true;
        }
    }
}
