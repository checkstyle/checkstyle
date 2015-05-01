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
import static com.puppycrawl.tools.checkstyle.Utils.baseClassname;
import static com.puppycrawl.tools.checkstyle.Utils.relativizeAndNormalizePath;
import static com.puppycrawl.tools.checkstyle.Utils.fileExtensionMatches;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.beanutils.ConversionException;
import org.junit.Test;

public class UtilsTest
{

    /** After appending to path produces equivalent, but denormalized path */
    private static final String PATH_DENORMALIZER = "/levelDown/.././";

    /**
     * Test Utils.countCharInString.
     */
    @Test
    public void testLengthExpandedTabs()
        throws Exception
    {
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
    public void testBadRegex()
    {
        Utils.createPattern("[");
    }

    @Test
    public void testFileExtensions()
    {
        final String[] fileExtensions = {"java"};
        File file = new File("file.pdf");
        assertFalse(fileExtensionMatches(file, fileExtensions));
        assertTrue(fileExtensionMatches(file, null));
        file = new File("file.java");
        assertTrue(fileExtensionMatches(file, fileExtensions));
    }

    @Test
    public void testBaseClassnameForCanonicalName()
    {
        assertEquals("List", baseClassname("java.util.List"));
    }

    @Test
    public void testBaseClassnameForSimpleName()
    {
        assertEquals("Set", baseClassname("Set"));
    }

    @Test
    public void testRelativeNormalizedPath()
    {
        final String relativePath = relativizeAndNormalizePath("/home", "/home/test");

        assertEquals("test", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithNullBaseDirectory()
    {
        final String relativePath = relativizeAndNormalizePath(null, "/tmp");

        assertEquals("/tmp", relativePath);
    }

    @Test
    public void testRelativeNormalizedPathWithDenormalizedBaseDirectory() throws IOException
    {
        final String sampleAbsolutePath = new File("src/main/java").getCanonicalPath();
        final String absoluteFilePath = sampleAbsolutePath + "/SampleFile.java";
        final String basePath = sampleAbsolutePath + PATH_DENORMALIZER;

        final String relativePath = relativizeAndNormalizePath(basePath, absoluteFilePath);

        assertEquals("SampleFile.java", relativePath);
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException
    {
        assertUtilsClassHasPrivateConstructor(Utils.class);
    }

    @Test
    public void testInvalidPattern()
    {
        boolean result = Utils.isPatternValid("some[invalidPattern");
        assertFalse(result);
    }
}
