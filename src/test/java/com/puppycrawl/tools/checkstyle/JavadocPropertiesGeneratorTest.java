////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

import antlr.ANTLRException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class JavadocPropertiesGeneratorTest extends AbstractPathTestSupport {

    private static final String EOL = System.lineSeparator();
    private static final String USAGE = String.format(Locale.ROOT,
          "usage: java com.puppycrawl.tools.checkstyle.JavadocPropertiesGenerator [options]"
              + " <input file>.%n"
              + "    --destfile <arg>   The output file.%n");
    private static final File DESTFILE = new File("target/tokentypes.properties");

    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javadocpropertiesgenerator";
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     * This implementation uses 'src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/'
     * as a non-compilable resource location.
     * @param filename file name.
     * @return canonical path for the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getNonCompilablePath(String filename) throws IOException {
        return new File("src/test/resources-noncompilable/" + getPackageLocation() + "/"
                + filename).getCanonicalPath();
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private", TestUtil.isUtilsClassHasPrivateConstructor(
            JavadocPropertiesGenerator.class, false));
    }

    @Test
    public void testNonExistentArgument() throws Exception {
        try {
            JavadocPropertiesGenerator.main("--nonexistent-argument");
            fail("Exception was expected");
        }
        catch (ParseException ex) {
            assertTrue("Invalid error message", ex.getMessage().contains("--nonexistent-argument"));
        }
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
    }

    @Test
    public void testNoDestfileSpecified() throws Exception {
        try {
            JavadocPropertiesGenerator.main(getPath("InputMain.java"));
            fail("Exception was expected");
        }
        catch (ParseException ex) {
            assertTrue("Invalid error message",
                ex.getMessage().contains("Missing required option: destfile"));
        }
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
    }

    @Test
    public void testNoInputSpecified() throws Exception {
        JavadocPropertiesGenerator.main("--destfile", DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", USAGE, systemOut.getLog());
    }

    @Test
    public void testNotClass() throws Exception {
        JavadocPropertiesGenerator.main("--destfile", DESTFILE.getAbsolutePath(),
            getPath("InputJavadocPropertiesGeneratorNotClass.java"));
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
    }

    @Test
    public void testNotExistentInputSpecified() throws Exception {
        try {
            JavadocPropertiesGenerator.main(
                "--destfile", DESTFILE.getAbsolutePath(), "NotExistent.java");
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid error message",
                "Failed to write javadoc properties of 'NotExistent.java' to '"
                    + DESTFILE.getAbsolutePath() + "'",
                ex.getMessage());

            final Throwable cause = ex.getCause();
            assertTrue("Invalid error message", cause instanceof FileNotFoundException);
            assertTrue("Invalid error message", cause.getMessage().contains("NotExistent.java"));
        }
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
    }

    @Test
    public void testInvalidDestinationSpecified() throws Exception {

        try {
            // Passing a folder name will cause the FileNotFoundException.
            JavadocPropertiesGenerator.main("--destfile", "..",
                getPath("InputJavadocPropertiesGeneratorCorrect.java"));
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            final String expectedError = "Failed to write javadoc properties of '"
                + getPath("InputJavadocPropertiesGeneratorCorrect.java") + "' to '..'";
            assertEquals("Invalid error message", expectedError, ex.getMessage());

            final Throwable cause = ex.getCause();
            assertTrue("Invalid error message", cause instanceof FileNotFoundException);
            assertTrue("Invalid error message", cause.getMessage().contains(".."));
        }
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
    }

    @Test
    public void testCorrect() throws Exception {
        final String expectedContent = "EOF1=The end of file token." + EOL
            + "EOF2=The end of file token." + EOL
            + "TYPE_EXTENSION_AND='&amp;' symbol when used in a generic upper or lower bounds"
            + " constrain e.g&#46;"
            + " <code>Comparable<T extends Serializable & CharSequence></code>!" + EOL
            + "LCURLY=A left curly brace (<code>{</code>)." + EOL
            + "DEPRECATED_LITERAL='@deprecated' literal in @deprecated Javadoc tag?" + EOL;

        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorCorrect.java"),
            "--destfile", DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
        final String fileContent = FileUtils.readFileToString(DESTFILE, StandardCharsets.UTF_8);
        assertEquals("File content is not expected", expectedContent, fileContent);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorEmptyJavadoc.java"),
            "--destfile", DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testNotConstants() throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorNotConstants.java"),
            "--destfile", DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", "", systemErr.getLog());
        assertEquals("Unexpected output log", "", systemOut.getLog());
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testJavadocParseError() throws Exception {
        try {
            JavadocPropertiesGenerator.main(
                getPath("InputJavadocPropertiesGeneratorJavadocParseError.java"),
                "--destfile", DESTFILE.getAbsolutePath());
            fail("Exception was expected");
        }
        catch (IllegalArgumentException ex) {
            assertTrue("Invalid error message", ex.getMessage().contains(
                "mismatched input '<EOF>' expecting JAVADOC_INLINE_TAG_END"));
        }
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testNotImplementedTag() throws Exception {
        try {
            JavadocPropertiesGenerator.main(
                getPath("InputJavadocPropertiesGeneratorNotImplementedTag.java"),
                "--destfile", DESTFILE.getAbsolutePath());
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid error message", "Unsupported inline tag LINK_LITERAL",
                ex.getMessage());
        }
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testParseError() throws Exception {
        try {
            JavadocPropertiesGenerator.main(
                getNonCompilablePath("InputJavadocPropertiesGeneratorParseError.java"),
                "--destfile", DESTFILE.getAbsolutePath());
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid error message",
                ex.getMessage().contains("InputJavadocPropertiesGeneratorParseError.java"));

            final Throwable cause = ex.getCause();
            assertTrue("Invalid error message", cause instanceof ANTLRException);
            assertTrue("Invalid error message",
                cause.getMessage().contains("Unexpected character 0x23 in identifier"));
        }
    }

}
