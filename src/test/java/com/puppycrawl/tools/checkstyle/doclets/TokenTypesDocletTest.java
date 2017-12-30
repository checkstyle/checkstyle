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

package com.puppycrawl.tools.checkstyle.doclets;

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
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class TokenTypesDocletTest extends AbstractPathTestSupport {

    private static final String EOL = System.lineSeparator();
    private static final String USAGE = String.format(Locale.ROOT,
          "usage: java com.puppycrawl.tools.checkstyle.doclets.TokenTypesDoclet [options]"
              + " <input file>.%n"
              + "    --destfile <arg>   The output file.%n");
    private static final File DESTFILE = new File("target/tokentypes.properties");

    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/doclets/tokentypesdoclet";
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
            TokenTypesDoclet.class, false));
    }

    @Test
    public void testNonExistentArgument() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";

        try {
            TokenTypesDoclet.main("--nonexistent-argument");
            fail("Exception was expected");
        }
        catch (ParseException ex) {
            assertTrue("Invalid error message", ex.getMessage().contains("--nonexistent-argument"));
        }
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
    }

    @Test
    public void testNoDestfileSpecified() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";

        try {
            TokenTypesDoclet.main(getPath("InputMain.java"));
            fail("Exception was expected");
        }
        catch (ParseException ex) {
            assertTrue("Invalid error message",
                ex.getMessage().contains("Missing required option: destfile"));
        }
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
    }

    @Test
    public void testNoInputSpecified() throws Exception {
        final String expectedErr = "";

        TokenTypesDoclet.main("--destfile", DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", USAGE, systemOut.getLog());
    }

    @Test
    public void testNotExistentInputSpecified() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";

        try {
            TokenTypesDoclet.main("--destfile", DESTFILE.getAbsolutePath(), "NotExistent.java");
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid error message", "Failed to write properties file",
                ex.getMessage());

            final Throwable cause = ex.getCause();
            assertTrue("Invalid error message", cause instanceof FileNotFoundException);
            assertTrue("Invalid error message", cause.getMessage().contains("NotExistent.java"));
        }
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
    }

    @Test
    public void testInvalidDestinationSpecified() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";

        try {
            // Passing a folder name will cause the FileNotFoundException.
            TokenTypesDoclet.main("--destfile", "..", getPath("InputMainCorrect.java"));
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            final String expectedError = "Failed to write properties file";
            assertEquals("Invalid error message", expectedError, ex.getMessage());

            final Throwable cause = ex.getCause();
            assertTrue("Invalid error message", cause instanceof FileNotFoundException);
            assertTrue("Invalid error message", cause.getMessage().contains(".."));
        }
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
    }

    @Test
    public void testCorrect() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";
        final String expectedContent = "EOF1=The end of file token." + EOL
            + "EOF2=The end of file token." + EOL
            + "TYPE_EXTENSION_AND='&amp;' symbol when used in a generic upper or lower bounds"
            + " constrain e.g&#46;"
            + " <code>Comparable<T extends Serializable & CharSequence></code>." + EOL
            + "LCURLY=A left curly brace (<code>{</code>)." + EOL;

        TokenTypesDoclet.main(getPath("InputTokenTypesDocletCorrect.java"), "--destfile",
            DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
        final String fileContent = FileUtils.readFileToString(DESTFILE, StandardCharsets.UTF_8);
        assertEquals("File content is not expected", expectedContent, fileContent);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";

        TokenTypesDoclet.main(getPath("InputTokenTypesDocletEmptyJavadoc.java"), "--destfile",
            DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testNotConstants() throws Exception {
        final String expectedErr = "";
        final String expectedOut = "";

        TokenTypesDoclet.main(getPath("InputTokenTypesDocletNotConstants.java"), "--destfile",
            DESTFILE.getAbsolutePath());
        assertEquals("Unexpected error log", expectedErr, systemErr.getLog());
        assertEquals("Unexpected output log", expectedOut, systemOut.getLog());
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testJavadocParseError() throws Exception {
        try {
            TokenTypesDoclet.main(getPath("InputTokenTypesDocletJavadocParseError.java"),
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
    public void testNotCodeTag() throws Exception {
        try {
            TokenTypesDoclet.main(getPath("InputTokenTypesDocletNotCodeTag.java"),
                "--destfile", DESTFILE.getAbsolutePath());
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("Invalid error message", "Expected inline @code tag", ex.getMessage());
        }
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

    @Test
    public void testParseError() throws Exception {
        try {
            TokenTypesDoclet.main(getNonCompilablePath("InputTokenTypesDocletParseError.java"),
                "--destfile", DESTFILE.getAbsolutePath());
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid error message",
                ex.getMessage().contains("InputTokenTypesDocletParseError.java"));

            final Throwable cause = ex.getCause();
            assertTrue("Invalid error message", cause instanceof ANTLRException);
            assertTrue("Invalid error message",
                cause.getMessage().contains("Unexpected character 0x23 in identifier"));
        }
        assertEquals("File '" + DESTFILE + "' must be empty", 0, FileUtils.sizeOf(DESTFILE));
    }

}
