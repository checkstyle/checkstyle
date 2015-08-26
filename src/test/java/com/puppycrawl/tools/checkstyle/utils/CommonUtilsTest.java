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

import static com.puppycrawl.tools.checkstyle.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Dictionary;

import org.apache.commons.beanutils.ConversionException;
import org.junit.Test;

public class CommonUtilsTest {

    /** After appending to path produces equivalent, but denormalized path */
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

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        File file = new File("file.pdf");
        assertFalse(CommonUtils.fileExtensionMatches(file, fileExtensions));
        assertTrue(CommonUtils.fileExtensionMatches(file, (String[]) null));
        file = new File("file.java");
        assertTrue(CommonUtils.fileExtensionMatches(file, fileExtensions));
        file = new File("file.");
        assertTrue(CommonUtils.fileExtensionMatches(file, ""));
    }

    @Test
    public void testBaseClassnameForCanonicalName() {
        assertEquals("List", CommonUtils.baseClassname("java.util.List"));
    }

    @Test
    public void testBaseClassnameForSimpleName() {
        assertEquals("Set", CommonUtils.baseClassname("Set"));
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

        final String relativePath = CommonUtils.relativizeAndNormalizePath(basePath, absoluteFilePath);

        assertEquals("SampleFile.java", relativePath);
    }

    @Test
    public void testInvalidPattern() {
        boolean result = CommonUtils.isPatternValid("some[invalidPattern");
        assertFalse(result);
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        Constructor<?> constructor = CommonUtils.getConstructor(String.class, String.class);

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
        Constructor<String> constructor = String.class.getConstructor(String.class);

        String constructedString = CommonUtils.invokeConstructor(constructor, "string");

        assertEquals("string", constructedString);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testInvokeConstructorThatFails() throws NoSuchMethodException {
        Constructor<Dictionary> constructor = Dictionary.class.getConstructor();

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
        CommonUtils.close(null);

        CommonUtils.close(new Closeable() {

            @Override
            public void close() throws IOException {
            }
        });
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
}
