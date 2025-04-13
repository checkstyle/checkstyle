///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

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
    private static final String DESTFILE_ABSOLUTE_PATH = DESTFILE.getAbsolutePath();

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
     * <div>Configures the environment for each test.</div>
     * <ul>
     * <li>Start output capture for {@link System#err} and {@link System#out}</li>
     * <li>Turn off colors for picocli to not conflict with tests if they are auto turned on.</li>
     * </ul>
     *
     * @param systemErr wrapper for {@code System.err}
     * @param systemOut wrapper for {@code System.out}
     */
    @BeforeEach
    public void setUp(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        systemErr.captureMuted();
        systemOut.captureMuted();

        System.setProperty("picocli.ansi", "false");
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
            .that(TestUtil.isUtilsClassHasPrivateConstructor(
                                            JavadocPropertiesGenerator.class))
            .isTrue();
    }

    @Test
    public void testNonExistentArgument(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main("--nonexistent-argument");

        final String expected = String.format(Locale.ROOT,
                "Missing required options and parameters: "
                + "'--destfile=<outputFile>', '<inputFile>'%n")
                + USAGE;
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testNoDestfileSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputMain.java"));

        final String expected = String.format(Locale.ROOT,
                "Missing required option: '--destfile=<outputFile>'%n") + USAGE;
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testNoInputSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        JavadocPropertiesGenerator.main("--destfile", DESTFILE_ABSOLUTE_PATH);

        final String expected = String.format(Locale.ROOT,
                "Missing required parameter: '<inputFile>'%n") + USAGE;
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testNotClass(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main("--destfile", DESTFILE_ABSOLUTE_PATH,
            getPath("InputJavadocPropertiesGeneratorNotClass.java"));
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testNotExistentInputSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        try {
            JavadocPropertiesGenerator.main(
                "--destfile", DESTFILE_ABSOLUTE_PATH, "NotExistent.java");
            assertWithMessage("Exception was expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Failed to write javadoc properties of 'NotExistent.java' to '"
                    + DESTFILE_ABSOLUTE_PATH + "'");

            final Throwable cause = ex.getCause();
            assertWithMessage("Invalid error message")
                    .that(cause)
                    .isInstanceOf(FileNotFoundException.class);
            assertWithMessage("Invalid error message")
                    .that(cause)
                    .hasMessageThat()
                    .contains("NotExistent.java");
        }
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testInvalidDestinationSpecified(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        try {
            // Passing a folder name will cause the FileNotFoundException.
            JavadocPropertiesGenerator.main("--destfile", "..",
                getPath("InputJavadocPropertiesGeneratorCorrect.java"));
            assertWithMessage("Exception was expected").fail();
        }
        catch (CheckstyleException ex) {
            final String expectedError = "Failed to write javadoc properties of '"
                + getPath("InputJavadocPropertiesGeneratorCorrect.java") + "' to '..'";
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo(expectedError);

            final Throwable cause = ex.getCause();
            assertWithMessage("Invalid error message")
                    .that(cause)
                    .isInstanceOf(FileNotFoundException.class);
            assertWithMessage("Invalid error message")
                    .that(cause)
                    .hasMessageThat()
                    .contains("..");
        }
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
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
            "--destfile", DESTFILE_ABSOLUTE_PATH);
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        final String fileContent = FileUtils.readFileToString(DESTFILE, StandardCharsets.UTF_8);
        assertWithMessage("File content is not expected")
            .that(fileContent)
            .isEqualTo(expectedContent);
    }

    @Test
    public void testEmptyJavadoc(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorEmptyJavadoc.java"),
            "--destfile", DESTFILE_ABSOLUTE_PATH);
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        final long size = FileUtils.sizeOf(DESTFILE);
        assertWithMessage("File '" + DESTFILE + "' must be empty")
            .that(size)
            .isEqualTo(0);
    }

    @Test
    public void testNotConstants(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main(getPath("InputJavadocPropertiesGeneratorNotConstants.java"),
            "--destfile", DESTFILE_ABSOLUTE_PATH);
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        final long size = FileUtils.sizeOf(DESTFILE);
        assertWithMessage("File '" + DESTFILE + "' must be empty")
            .that(size)
            .isEqualTo(0);
    }

    @Test
    public void testHelp(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws Exception {
        JavadocPropertiesGenerator.main("-h");
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(USAGE);
    }

    @Test
    public void testJavadocParseError() throws Exception {
        final String path = getPath("InputJavadocPropertiesGeneratorJavadocParseError.java");
        try {
            JavadocPropertiesGenerator.main(path, "--destfile", DESTFILE_ABSOLUTE_PATH);
            assertWithMessage("Exception was expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid error message")
                    .that(ex.getMessage())
                    .contains("mismatched input '<EOF>' expecting JAVADOC_INLINE_TAG_END");
        }
        final long size = FileUtils.sizeOf(DESTFILE);
        assertWithMessage("File '" + DESTFILE + "' must be empty")
            .that(size)
            .isEqualTo(0);
    }

    @Test
    public void testNotImplementedTag() throws Exception {
        final String path = getPath("InputJavadocPropertiesGeneratorNotImplementedTag.java");
        try {
            JavadocPropertiesGenerator.main(path, "--destfile", DESTFILE_ABSOLUTE_PATH);
            assertWithMessage("Exception was expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unsupported inline tag LINK_LITERAL");
        }
        final long size = FileUtils.sizeOf(DESTFILE);
        assertWithMessage("File '" + DESTFILE + "' must be empty")
            .that(size)
            .isEqualTo(0);
    }

    @Test
    public void testParseError() throws Exception {
        final String path = getNonCompilablePath("InputJavadocPropertiesGeneratorParseError.java");
        try {
            JavadocPropertiesGenerator.main(path, "--destfile", DESTFILE_ABSOLUTE_PATH);
            assertWithMessage("Exception was expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid error message")
                    .that(ex.getMessage())
                    .contains("InputJavadocPropertiesGeneratorParseError.java");

            final Throwable cause = ex.getCause();
            assertWithMessage("Invalid error message")
                    .that(cause)
                    .isInstanceOf(IllegalStateException.class);
            assertWithMessage("Invalid error message")
                    .that(cause)
                    .hasMessageThat()
                    .contains("9:0: mismatched input '!' expecting '}'");
        }
    }

    @Test
    public void testGetFirstJavadocSentence(@SysErr Capturable systemErr,
                                            @SysOut Capturable systemOut) throws Exception {
        final String expectedContent = "EOF1=First Javadoc Sentence.";

        JavadocPropertiesGenerator.main(
                getPath("InputJavadocPropertiesGeneratorGetFirstJavadocSentence.java"),
            "--destfile", DESTFILE_ABSOLUTE_PATH);
        assertWithMessage("Unexpected error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        final String fileContent = FileUtils.readFileToString(DESTFILE, StandardCharsets.UTF_8);
        assertWithMessage("File content is not expected")
            .that(fileContent.trim())
            .isEqualTo(expectedContent.trim());
    }
}
