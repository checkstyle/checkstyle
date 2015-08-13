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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Dictionary;

import org.apache.commons.beanutils.ConversionException;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UtilsTest {

    /** After appending to path produces equivalent, but denormalized path */
    private static final String PATH_DENORMALIZER = "/levelDown/.././";

    /**
     * Test Utils.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs()
        throws Exception {
        final String s1 = "\t";
        assertEquals(8, Utils.lengthExpandedTabs(s1, s1.length(), 8));

        final String s2 = "  \t";
        assertEquals(8, Utils.lengthExpandedTabs(s2, s2.length(), 8));

        final String s3 = "\t\t";
        assertEquals(16, Utils.lengthExpandedTabs(s3, s3.length(), 8));

        final String s4 = " \t ";
        assertEquals(9, Utils.lengthExpandedTabs(s4, s4.length(), 8));

        assertEquals(0, Utils.lengthMinusTrailingWhitespace(""));
        assertEquals(0, Utils.lengthMinusTrailingWhitespace(" \t "));
        assertEquals(3, Utils.lengthMinusTrailingWhitespace(" 23"));
        assertEquals(3, Utils.lengthMinusTrailingWhitespace(" 23 \t "));
    }

    @Test(expected = ConversionException.class)
    public void testBadRegex() {
        Utils.createPattern("[");
    }

    @Test
    public void testFileExtensions() {
        final String[] fileExtensions = {"java"};
        File file = new File("file.pdf");
        assertFalse(Utils.fileExtensionMatches(file, fileExtensions));
        assertTrue(Utils.fileExtensionMatches(file, (String[]) null));
        file = new File("file.java");
        assertTrue(Utils.fileExtensionMatches(file, fileExtensions));
        file = new File("file.");
        assertTrue(Utils.fileExtensionMatches(file, ""));
    }

    @Test
    public void testBaseClassnameForCanonicalName() {
        assertEquals("List", Utils.baseClassname("java.util.List"));
    }

    @Test
    public void testBaseClassnameForSimpleName() {
        assertEquals("Set", Utils.baseClassname("Set"));
    }

    @Test
    public void testRelativeNormalizedPath() {
        final String relativePath = Utils.relativizeAndNormalizePath("/home", "/home/test");

        assertEquals("test", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory() {
        final String relativePath = Utils.relativizeAndNormalizePath(null, "/tmp");

        assertEquals("/tmp", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = Utils.relativizeAndNormalizePath(basePath, absoluteFilePath);

        assertEquals("SampleFile.java", relativePath);
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(Utils.class);
    }

    @Test
    public void testInvalidPattern() {
        boolean result = Utils.isPatternValid("some[invalidPattern");
        assertFalse(result);
    }

    @Test
    public void testGetExistingConstructor() throws NoSuchMethodException {
        Constructor<?> constructor = Utils.getConstructor(String.class, String.class);

        assertEquals(String.class.getConstructor(String.class), constructor);
    }

    @Test
    public void testGetNonExistingConstructor() throws NoSuchMethodException {
        try {
            Utils.getConstructor(Math.class);
            fail();
        }
        catch (IllegalStateException expected) {
            assertEquals(NoSuchMethodException.class, expected.getCause().getClass());
        }
    }

    @Test
    public void testInvokeConstructor() throws NoSuchMethodException {
        Constructor<String> constructor = String.class.getConstructor(String.class);

        String constructedString = Utils.invokeConstructor(constructor, "string");

        assertEquals("string", constructedString);
    }

    @Test
    public void testInvokeConstructorThatFails() throws NoSuchMethodException {
        Constructor<Dictionary> constructor = Dictionary.class.getConstructor();

        try {
            Utils.invokeConstructor(constructor);
            fail();
        }
        catch (IllegalStateException expected) {
            assertEquals(InstantiationException.class, expected.getCause().getClass());
        }
    }

    @Test
    public void testGetIntFromAccessibleField() throws NoSuchFieldException {
        Field field = Integer.class.getField("MAX_VALUE");

        assertEquals(Integer.MAX_VALUE, Utils.getIntFromField(field, 0));
    }

    @Test
    public void testGetIntFromInaccessibleField() throws NoSuchFieldException {
        Field field = Integer.class.getDeclaredField("value");

        try {
            Utils.getIntFromField(field, 0);
        }
        catch (IllegalStateException expected) {
            // expected
        }
    }

    @Test
    public void testTokenValueIncorrect() throws NoSuchMethodException {
        Integer id = Integer.MAX_VALUE - 1;
        try {
            Utils.getTokenName(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given id " + id, expected.getMessage());
        }
    }

    @Test
    public void testTokenValueIncorrect2() throws NoSuchMethodException, IllegalAccessException {
        Integer id = 0;
        String[] originalValue = null;
        Field fieldToken = null;
        try {
            // overwrite static field with new value
            Field[] fields = Utils.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if ("TOKEN_VALUE_TO_NAME".equals(field.getName())) {
                    fieldToken = field;
                    Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    originalValue = (String[]) field.get(null);
                    field.set(null, new String[] {null});
                }
            }

            Utils.getTokenName(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            // restoring original value, to let other tests pass
            fieldToken.set(null, originalValue);

            assertEquals("given id " + id, expected.getMessage());

        }
        catch (IllegalAccessException e) {
            fail();
        }
        catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testTokenIdIncorrect() throws NoSuchMethodException {
        String id = "NON_EXISTING_VALUE";
        try {
            Utils.getTokenId(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given name " + id, expected.getMessage());
        }
    }

    @Test
    public void testShortDescriptionIncorrect() throws NoSuchMethodException {
        String id = "NON_EXISTING_VALUE";
        try {
            Utils.getShortDescription(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given name " + id, expected.getMessage());
        }
    }

    @Test
    public void testIsCommentType() throws NoSuchMethodException {
        assertTrue(Utils.isCommentType(TokenTypes.SINGLE_LINE_COMMENT));
        assertTrue(Utils.isCommentType(TokenTypes.BLOCK_COMMENT_BEGIN));
        assertTrue(Utils.isCommentType(TokenTypes.BLOCK_COMMENT_END));
        assertTrue(Utils.isCommentType(TokenTypes.COMMENT_CONTENT));
    }
}
