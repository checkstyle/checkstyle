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

package com.puppycrawl.tools.checkstyle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import antlr.MismatchedTokenException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

@ExtendWith({SystemErrGuard.class, SystemOutGuard.class})
public class JavadocPropertiesGeneratorTest extends AbstractPathTestSupport {

    private static final String EOL = System.lineSeparator();
    private static final String USAGE = String.format(Locale.ROOT,
            "Usage: java com.puppycrawl.tools.checkstyle.JavadocPropertiesGenerator [-hV]%n"
                    + "       --destfile=<outputFile> <inputFile>%n"
                    + "      <inputFile>   The input file.%n"
                    + "      --destfile=<outputFile>%n"
                    + "                    The output file.%n"
                    + "  -h, --help        Show this help message and exit.%n"
                    + "  -V, --version     Print version information and exit.%n");
    private static final File DESTFILE = new File("target/tokentypes.properties");

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javadocpropertiesgenerator";
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     * This implementation uses 'src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/'
     * as a non-compilable resource location.
     *
     * @param filename file name.
     * @return canonical path for the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getNonCompilablePath(String filename) throws IOException {
        return new File("src/test/resources-noncompilable/" + getPackageLocation() + "/"
                + filename).getCanonicalPath();
    }

    /**
     * <p>Configures the environment for each test.</p>
     * <ul>
     * <li>Start output capture for {@link System#err} and {@link System#out}</li>
     * </ul>
     *
     * @param systemErr wrapper for {@code System.err}
     * @param systemOut wrapper for {@code System.out}
     */
    @BeforeEach
    public void setUp(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        systemErr.captureMuted();
        systemOut.captureMuted();
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(TestUtil.isUtilsClassHasPrivateConstructor(
            JavadocPropertiesGenerator.class, false), "Constructor is not private");
    }

    @Test
    public void testNonExistentArgument(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main("--nonexistent-argument");

        final String expected = String.format(Locale.ROOT, "Missing required options "
                + "[--destfile=<outputFile>, params[0]=<inputFile>]%n")
                + USAGE;
        assertEquals(expected, systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testNoDestfileSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputMain.java"));

        final String expected = String.format(Locale.ROOT,
                "Missing required option '--destfile=<outputFile>'%n") + USAGE;
        assertEquals(expected, systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testNoInputSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        JavadocPropertiesGenerator.main("--destfile", DESTFILE.getAbsolutePath());

        final String expected = String.format(Locale.ROOT,
                "Missing required parameter: <inputFile>%n") + USAGE;
        assertEquals(expected, systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testNotClass(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main("--destfile", DESTFILE.getAbsolutePath(),
            getPath("InputJavadocPropertiesGeneratorNotClass.java"));
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testNotExistentInputSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        try {
            JavadocPropertiesGenerator.main(
                "--destfile", DESTFILE.getAbsolutePath(), "NotExistent.java");
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            assertEquals(
                    "Failed to write javadoc properties of 'NotExistent.java' to '"
                    + DESTFILE.getAbsolutePath() + "'",
                ex.getMessage(), "Invalid error message");

            final Throwable cause = ex.getCause();
            assertTrue(cause instanceof FileNotFoundException, "Invalid error message");
            assertTrue(cause.getMessage().contains("NotExistent.java"), "Invalid error message");
        }
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testInvalidDestinationSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        try {
            // Passing a folder name will cause the FileNotFoundException.
            JavadocPropertiesGenerator.main("--destfile", "..",
                getPath("InputJavadocPropertiesGeneratorCorrect.java"));
            fail("Exception was expected");
        }
        catch (CheckstyleException ex) {
            final String expectedError = "Failed to write javadoc properties of '"
                + getPath("InputJavadocPropertiesGeneratorCorrect.java") + "' to '..'";
            assertEquals(expectedError, ex.getMessage(), "Invalid error message");

            final Throwable cause = ex.getCause();
            assertTrue(cause instanceof FileNotFoundException, "Invalid error message");
            assertTrue(cause.getMessage().contains(".."), "Invalid error message");
        }
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testCorrect(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        final String expectedContent = "EOF1=The end of file token." + EOL
            + "EOF2=The end of file token." + EOL
            + "TYPE_EXTENSION_AND='&amp;' symbol when used in a generic upper or lower bounds"
            + " constrain e.g&#46;"
            + " <code>Comparable<T extends Serializable & CharSequence></code>!" + EOL
            + "LCURLY=A left curly brace (<code>{</code>)." + EOL
            + "DEPRECATED_LITERAL='@deprecated' literal in @deprecated Javadoc tag?" + EOL;

        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorCorrect.java"),
            "--destfile", DESTFILE.getAbsolutePath());
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        final String fileContent = FileUtils.readFileToString(DESTFILE, StandardCharsets.UTF_8);
        assertEquals(expectedContent, fileContent, "File content is not expected");
    }

    @Test
    public void testEmptyJavadoc(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorEmptyJavadoc.java"),
            "--destfile", DESTFILE.getAbsolutePath());
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        final long size = FileUtils.sizeOf(DESTFILE);
        assertEquals(0, size, "File '" + DESTFILE + "' must be empty");
    }

    @Test
    public void testNotConstants(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorNotConstants.java"),
            "--destfile", DESTFILE.getAbsolutePath());
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        final long size = FileUtils.sizeOf(DESTFILE);
        assertEquals(0, size, "File '" + DESTFILE + "' must be empty");
    }

    @Test
    public void testHelp(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main("-h");
        assertEquals("", systemErr.getCapturedData(), "Unexpected error log");
        assertEquals(USAGE, systemOut.getCapturedData(), "Unexpected output log");
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
            assertTrue(ex.getMessage()
                            .contains("mismatched input '<EOF>' expecting JAVADOC_INLINE_TAG_END"),
                    "Invalid error message");
        }
        final long size = FileUtils.sizeOf(DESTFILE);
        assertEquals(0, size, "File '" + DESTFILE + "' must be empty");
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
            assertEquals("Unsupported inline tag LINK_LITERAL",
                ex.getMessage(), "Invalid error message");
        }
        final long size = FileUtils.sizeOf(DESTFILE);
        assertEquals(0, size, "File '" + DESTFILE + "' must be empty");
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
            assertTrue(ex.getMessage().contains("InputJavadocPropertiesGeneratorParseError.java"),
                    "Invalid error message");

            final Throwable cause = ex.getCause().getCause();
            assertTrue(cause instanceof MismatchedTokenException, "Invalid error message");
            assertTrue(cause.getMessage().contains("expecting RCURLY, found '!'"),
                    "Invalid error message");
        }
    }

}
