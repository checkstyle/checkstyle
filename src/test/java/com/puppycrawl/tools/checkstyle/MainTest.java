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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class MainTest {
    private static final String USAGE = String.format(Locale.ROOT,
          "usage: java com.puppycrawl.tools.checkstyle.Main [options] -c <config.xml>"
        + " file...%n"
        + " -c <arg>                                Sets the check configuration file to use.%n"
        + " -C,--checker-threads-number <arg>       (experimental) The number of Checker threads "
        + "(must be%n"
        + "                                         greater than zero)%n"
        + " -d,--debug                              Print all debug logging of CheckStyle utility%n"
        + " -e,--exclude <arg>                      Directory path to exclude from CheckStyle%n"
        + " -executeIgnoredModules                  Allows ignored modules to be run.%n"
        + " -f <arg>                                Sets the output format. (plain|xml). Defaults"
        + " to plain%n"
        + " -j,--javadocTree                        Print Parse tree of the Javadoc comment%n"
        + " -J,--treeWithJavadoc                    Print full Abstract Syntax Tree of the file%n"
        + " -o <arg>                                Sets the output file. Defaults to stdout%n"
        + " -p <arg>                                Loads the properties file%n"
        + " -t,--tree                               Print Abstract Syntax Tree(AST) of the file%n"
        + " -T,--treeWithComments                   Print Abstract Syntax Tree(AST) of the file"
        + " including%n"
        + "                                         comments%n"
        + " -v                                      Print product version and exit%n"
        + " -W,--tree-walker-threads-number <arg>   (experimental) The number of TreeWalker threads"
        + " (must be%n"
        + "                                         greater than zero)%n"
        + " -x,--exclude-regexp <arg>               Regular expression of directory to exclude from"
        + " CheckStyle%n");

    private static final Logger LOG = Logger.getLogger(MainTest.class.getName()).getParent();
    private static final Handler[] HANDLERS = LOG.getHandlers();
    private static final Level ORIGINAL_LOG_LEVEL = LOG.getLevel();

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/" + filename;
    }

    private static String getNonCompilablePath(String filename) {
        return "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/" + filename;
    }

    private static String getFilePath(String filename) throws IOException {
        return new File(getPath(filename)).getCanonicalPath();
    }

    @BeforeClass
    public static void init() {
        // Set locale to root to prevent check message fail
        // in other language environment.
        Locale.setDefault(Locale.ROOT);
    }

    @Before
    public void setUp() {
        // restore original logging level and HANDLERS to prevent bleeding into other tests

        LOG.setLevel(ORIGINAL_LOG_LEVEL);

        for (Handler handler : LOG.getHandlers()) {
            boolean found = false;

            for (Handler savedHandler : HANDLERS) {
                if (handler == savedHandler) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                LOG.removeHandler(handler);
            }
        }
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(Main.class);
    }

    @Test
    public void testVersionPrint()
            throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Checkstyle version: null" + System.lineSeparator(),
                    systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-v");
    }

    @Test
    public void testWrongArgument()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            final String usage = String.format(Locale.ROOT, "Unrecognized option: -w%n")
                    + USAGE;
            assertEquals(usage, systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-w");
    }

    @Test
    public void testNoConfigSpecified()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Must specify a config XML file." + System.lineSeparator(),
                    systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main(getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingTargetFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "NonExistingFile.java");
    }

    @Test
    public void testNonExistingConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals(String.format(Locale.ROOT,
                    "Could not find config XML file "
                        + "'src/main/resources/non_existing_config.xml'.%n"),
                    systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", "src/main/resources/non_existing_config.xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingOutputFormat() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals(String.format(Locale.ROOT, "Invalid output format. "
                    + "Found 'xmlp' but expected 'plain' or 'xml'.%n"), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "-f", "xmlp",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingClass() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedExceptionMessage =
                    String.format(Locale.ROOT, "Checkstyle ends with 1 errors.%n");
            assertEquals(expectedExceptionMessage, systemOut.getLog());

            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            assertTrue(systemErr.getLog().startsWith(cause));
        });

        Main.main("-c", getPath("config-non-existing-classname.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFile() throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                    + "Audit done.%n"), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileXmlOutput() throws Exception {

        exit.checkAssertionAfterwards(() -> {
            final String expectedPath = getFilePath("InputMain.java");
            final ResourceBundle compilationProperties =
                    ResourceBundle.getBundle("checkstylecompilation", Locale.ROOT);
            final String version = compilationProperties
                .getString("checkstyle.compile.version");
            assertEquals(String.format(Locale.ROOT,
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>%n"
                    + "<checkstyle version=\"%s\">%n"
                    + "<file name=\"%s\">%n"
                    + "</file>%n"
                    + "</checkstyle>%n", version, expectedPath), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutput() throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                    + "Audit done.%n"), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileWithViolations() throws Exception {
        exit.checkAssertionAfterwards(() -> {
            final String expectedPath = getFilePath("InputMain.java");
            assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                            + "[WARN] %1$s:3:14: "
                            + "Name 'InputMain' must match pattern"
                            + " '^[a-z0-9]*$'. [TypeName]%n"
                            + "[WARN] %1$s:5:7: "
                            + "Name 'InputMainInner' must match pattern"
                            + " '^[a-z0-9]*$'. [TypeName]%n"
                            + "Audit done.%n", expectedPath),
                    systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname2.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileWithError()
            throws Exception {
        exit.expectSystemExitWithStatus(2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedPath = getFilePath("InputMain.java");
            assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                    + "[ERROR] %1$s:3:14: "
                    + "Name 'InputMain' must match pattern '^[a-z0-9]*$'. [TypeName]%n"
                    + "[ERROR] %1$s:5:7: "
                    + "Name 'InputMainInner' must match pattern '^[a-z0-9]*$'. [TypeName]%n"
                    + "Audit done.%n"
                    + "Checkstyle ends with 2 errors.%n", expectedPath), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c",
                getPath("config-classname2-error.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputToNonExistingFile()
            throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals("", systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                "-o", temporaryFolder.getRoot() + "/output.txt",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputToFile()
            throws Exception {
        final File file = temporaryFolder.newFile("file.output");
        exit.checkAssertionAfterwards(() -> {
            assertEquals("", systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                getPath("InputMain.java"));
    }

    @Test
    public void testCreateNonExistingOutputFile() throws Exception {
        final String outputFile = temporaryFolder.getRoot().getCanonicalPath() + "nonexisting.out";
        assertFalse(new File(outputFile).exists());
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                "-o", outputFile,
                getPath("InputMain.java"));
        assertTrue(new File(outputFile).exists());
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties()
            throws Exception {
        //exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(() -> {
            assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                    + "Audit done.%n"), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname-prop.xml"),
                "-p", getPath("mycheckstyle.properties"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistingProperties()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Could not find file 'nonexisting.properties'."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", getPath("config-classname-prop.xml"),
                "-p", "nonexisting.properties",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String output = String.format(Locale.ROOT,
                    "Checkstyle ends with 1 errors.%n");
            assertEquals(output, systemOut.getLog());
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: unable to parse configuration stream - ";
            assertTrue(systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("config-Incorrect.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String output = String.format(Locale.ROOT,
                    "Checkstyle ends with 1 errors.%n");
            assertEquals(output, systemOut.getLog());
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: cannot initialize module RegexpSingleline"
                    + " - RegexpSingleline is not allowed as a child in RegexpSingleline";
            assertTrue(systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("config-incorrectChildren.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile2()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String output = String.format(Locale.ROOT,
                    "Checkstyle ends with 1 errors.%n");
            assertEquals(output, systemOut.getLog());
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: cannot initialize module TreeWalker"
                    + " - JavadocVariable is not allowed as a child in JavadocMethod";
            assertTrue(systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("config-incorrectChildren2.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testLoadPropertiesIoException() throws Exception {
        final Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        final Method method = Main.class.getDeclaredMethod("loadProperties", param);
        method.setAccessible(true);
        try {
            if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).startsWith("windows")) {
                // https://support.microsoft.com/en-us/kb/177506 but this only for NTFS
                // WindowsServer 2012 use Resilient File System (ReFS), so any name is ok
                final File file = new File(File.separator + ":invalid");
                if (file.exists()) {
                    file.delete();
                }
                method.invoke(null, new File(file.getAbsolutePath()));
            }
            else {
                method.invoke(null, new File(File.separator + "\0:invalid"));
            }
            fail("Exception was expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof CheckstyleException);
            // We do separate validation for message as in Windows
            // disk drive letter appear in message,
            // so we skip that drive letter for compatibility issues
            assertTrue(ex.getCause().getMessage()
                    .startsWith("Unable to load properties from file '"));
            assertTrue(ex.getCause().getMessage().endsWith(":invalid'."));
        }
    }

    @Test
    public void testCreateListenerIllegalStateException() throws Exception {
        final Method method = Main.class.getDeclaredMethod("createListener", String.class,
            String.class);
        method.setAccessible(true);
        try {
            method.invoke(null, "myformat", null);
            fail("InvocationTargetException is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalStateException);
            assertTrue(ex.getCause().getMessage().startsWith("Invalid output format. Found"));
        }
    }

    @Test
    public void testCreateListenerWithLocationIllegalStateException() throws Exception {
        final Method method = Main.class.getDeclaredMethod("createListener", String.class,
            String.class);
        method.setAccessible(true);
        final String outDir = "myfolder123";
        try {
            method.invoke(null, "myformat", outDir);
            fail("InvocationTargetException  is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IllegalStateException);
            assertTrue(ex.getCause().getMessage().startsWith("Invalid output format. Found"));
        }
        finally {
            // method creates output folder
            FileUtils.deleteQuietly(new File(outDir));
        }
    }

    @Test
    public void testExistingDirectoryWithViolations() throws Exception {

        // we just reference there all violations
        final String[][] outputValues = {
                {"InputComplexityOverflow", "1", "172"},
        };

        final int allowedLength = 170;
        final String msgKey = "maxLen.file";
        final String bundle = "com.puppycrawl.tools.checkstyle.checks.sizes.messages";

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
                final String expectedPath = getFilePath("main/") + File.separator;
                final StringBuilder sb = new StringBuilder(28);
                sb.append("Starting audit...").append(System.getProperty("line.separator"));
                final String format = "[WARN] %s.java:%s: %s [FileLength]";
                for (String[] outputValue : outputValues) {
                    final String localizedMessage = new LocalizedMessage(0, bundle,
                            msgKey, new Integer[] {Integer.valueOf(outputValue[2]), allowedLength},
                            null, getClass(), null).getMessage();
                    final String line = String.format(Locale.ROOT, format,
                            expectedPath + outputValue[0], outputValue[1], localizedMessage);
                    sb.append(line).append(System.getProperty("line.separator"));
                }
                sb.append("Audit done.").append(System.getProperty("line.separator"));
                assertEquals(sb.toString(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });

        Main.main("-c", getPath("config-filelength.xml"),
                getPath("main/"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFilesNotFile() throws Exception {
        final Method method = Main.class.getDeclaredMethod("listFiles", File.class, List.class);
        method.setAccessible(true);

        final File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(false);
        when(fileMock.isFile()).thenReturn(false);

        final List<File> result = (List<File>) method.invoke(null, fileMock, null);
        assertEquals(0, result.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFilesDirectoryWithNull() throws Exception {
        final Method method = Main.class.getDeclaredMethod("listFiles", File.class, List.class);
        method.setAccessible(true);

        final File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(true);
        when(fileMock.listFiles()).thenReturn(null);

        final List<File> result = (List<File>) method.invoke(null, fileMock,
                new ArrayList<Pattern>());
        assertEquals(0, result.size());
    }

    @Test
    public void testFileReferenceDuringException() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedExceptionMessage =
                    String.format(Locale.ROOT, "Starting audit...%n"
                            + "Checkstyle ends with 1 errors.%n");
            assertEquals(expectedExceptionMessage, systemOut.getLog());

            final String exceptionFirstLine = String.format(Locale.ROOT,
                    "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: Exception was thrown while processing "
                    + new File(getNonCompilablePath("InputIncorrectClass.java")).getPath()
                    + "%n");
            assertTrue(systemErr.getLog().startsWith(exceptionFirstLine));
        });

        // We put xml as source to cause parse excepion
        Main.main("-c", getPath("config-classname.xml"),
                getNonCompilablePath("InputIncorrectClass.java"));
    }

    @Test
    public void testPrintTreeOnMoreThanOneFile() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Printing AST is allowed for only one file."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });

        Main.main("-t", getPath("checks/metrics"));
    }

    @Test
    public void testPrintTreeOption() throws Exception {
        final String expected = String.format(Locale.ROOT, "PACKAGE_DEF -> package [1:0]%n"
            + "|--ANNOTATIONS -> ANNOTATIONS [1:28]%n"
            + "|--DOT -> . [1:28]%n"
            + "|   |--DOT -> . [1:22]%n"
            + "|   |   |--DOT -> . [1:11]%n"
            + "|   |   |   |--IDENT -> com [1:8]%n"
            + "|   |   |   `--IDENT -> puppycrawl [1:12]%n"
            + "|   |   `--IDENT -> tools [1:23]%n"
            + "|   `--IDENT -> checkstyle [1:29]%n"
            + "`--SEMI -> ; [1:39]%n"
            + "CLASS_DEF -> CLASS_DEF [3:0]%n"
            + "|--MODIFIERS -> MODIFIERS [3:0]%n"
            + "|   `--LITERAL_PUBLIC -> public [3:0]%n"
            + "|--LITERAL_CLASS -> class [3:7]%n"
            + "|--IDENT -> InputMain [3:13]%n"
            + "`--OBJBLOCK -> OBJBLOCK [3:23]%n"
            + "    |--LCURLY -> { [3:23]%n"
            + "    `--RCURLY -> } [4:0]%n"
            + "CLASS_DEF -> CLASS_DEF [5:0]%n"
            + "|--MODIFIERS -> MODIFIERS [5:0]%n"
            + "|--LITERAL_CLASS -> class [5:0]%n"
            + "|--IDENT -> InputMainInner [5:6]%n"
            + "`--OBJBLOCK -> OBJBLOCK [5:21]%n"
            + "    |--LCURLY -> { [5:21]%n"
            + "    `--RCURLY -> } [6:0]%n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals(expected, systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-t", getPath("InputMain.java"));
    }

    @Test
    public void testPrintTreeCommentsOption() throws Exception {
        final String expected = String.format(Locale.ROOT, "PACKAGE_DEF -> package [1:0]%n"
            + "|--ANNOTATIONS -> ANNOTATIONS [1:28]%n"
            + "|--DOT -> . [1:28]%n"
            + "|   |--DOT -> . [1:22]%n"
            + "|   |   |--DOT -> . [1:11]%n"
            + "|   |   |   |--IDENT -> com [1:8]%n"
            + "|   |   |   `--IDENT -> puppycrawl [1:12]%n"
            + "|   |   `--IDENT -> tools [1:23]%n"
            + "|   `--IDENT -> checkstyle [1:29]%n"
            + "`--SEMI -> ; [1:39]%n"
            + "CLASS_DEF -> CLASS_DEF [3:0]%n"
            + "|--MODIFIERS -> MODIFIERS [3:0]%n"
            + "|   |--BLOCK_COMMENT_BEGIN -> /* [2:0]%n"
            + "|   |   |--COMMENT_CONTENT -> comment [2:2]%n"
            + "|   |   `--BLOCK_COMMENT_END -> */ [2:8]%n"
            + "|   `--LITERAL_PUBLIC -> public [3:0]%n"
            + "|--LITERAL_CLASS -> class [3:7]%n"
            + "|--IDENT -> InputMain [3:13]%n"
            + "`--OBJBLOCK -> OBJBLOCK [3:23]%n"
            + "    |--LCURLY -> { [3:23]%n"
            + "    `--RCURLY -> } [4:0]%n"
            + "CLASS_DEF -> CLASS_DEF [5:0]%n"
            + "|--MODIFIERS -> MODIFIERS [5:0]%n"
            + "|--LITERAL_CLASS -> class [5:0]%n"
            + "|--IDENT -> InputMainInner [5:6]%n"
            + "`--OBJBLOCK -> OBJBLOCK [5:21]%n"
            + "    |--LCURLY -> { [5:21]%n"
            + "    `--RCURLY -> } [6:0]%n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals(expected, systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-T", getPath("InputMain.java"));
    }

    @Test
    public void testPrintTreeJavadocOption() throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("astprinter/expectedInputJavadocComment.txt"))), StandardCharsets.UTF_8)
            .replaceAll("\\\\r\\\\n", "\\\\n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals(expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n"));
            assertEquals("", systemErr.getLog());
        });
        Main.main("-j", getPath("astprinter/InputJavadocComment.javadoc"));
    }

    @Test
    public void testPrintFullTreeOption() throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("astprinter/expectedInputAstTreeStringPrinterJavadoc.txt"))),
            StandardCharsets.UTF_8).replaceAll("\\\\r\\\\n", "\\\\n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals(expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n"));
            assertEquals("", systemErr.getLog());
        });
        Main.main("-J", getPath("astprinter/InputAstTreeStringPrinterJavadoc.java"));
    }

    @Test
    public void testConflictingOptionsTvsC() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });

        Main.main("-c", "/google_checks.xml", "-t", getPath("checks/metrics"));
    }

    @Test
    public void testConflictingOptionsTvsP() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });

        Main.main("-p", getPath("mycheckstyle.properties"), "-t", getPath("checks/metrics"));
    }

    @Test
    public void testConflictingOptionsTvsF() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });

        Main.main("-f", "plain", "-t", getPath("checks/metrics"));
    }

    @Test
    public void testConflictingOptionsTvsO() throws Exception {
        final File file = temporaryFolder.newFile("file.output");

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });

        Main.main("-o", file.getCanonicalPath(), "-t", getPath("checks/metrics"));
    }

    @Test
    public void testDebugOption() throws Exception {
        exit.checkAssertionAfterwards(() -> assertNotEquals("", systemErr.getLog()));
        Main.main("-c", "/google_checks.xml", getPath("InputMain.java"), "-d");
    }

    @Test
    public void testExcludeOption() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath(""), "-e", getFilePath(""));
    }

    @Test
    public void testExcludeRegexpOption() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath(""), "-x", ".");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExcludeDirectoryNotMatch() throws Exception {
        final Method method = Main.class.getDeclaredMethod("listFiles", File.class, List.class);
        method.setAccessible(true);
        final List<Pattern> list = new ArrayList<>();
        list.add(Pattern.compile("BAD_PATH"));

        final List<File> result = (List<File>) method.invoke(null, new File(getFilePath("")),
                list);
        assertNotEquals(0, result.size());
    }

    @Test
    public void testCustomRootModule() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("", systemOut.getLog());
            assertEquals("", systemErr.getLog());
            assertTrue(TestRootModuleChecker.isProcessed());
        });
        Main.main("-c", getPath("config-custom-root-module.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testCustomSimpleRootModule() throws Exception {
        TestRootModuleChecker.reset();
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String checkstylePackage = "com.puppycrawl.tools.checkstyle.";

            assertEquals(String.format(Locale.ROOT, "Checkstyle ends with 1 errors.%n"),
                    systemOut.getLog());
            assertTrue(systemErr.getLog().startsWith(
                    checkstylePackage + "api.CheckstyleException: Unable to instantiate "
                            + "'TestRootModuleChecker' class, it is also not possible to "
                            + "instantiate it as " + checkstylePackage
                            + "TestRootModuleChecker, TestRootModuleCheckerCheck, "
                            + checkstylePackage + "TestRootModuleCheckerCheck."));
            assertFalse(TestRootModuleChecker.isProcessed());
        });
        Main.main("-c", getPath("config-custom-simple-root-module.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExecuteIgnoredModule() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedExceptionMessage =
                    String.format(Locale.ROOT, "Checkstyle ends with 1 errors.%n");
            assertEquals(expectedExceptionMessage, systemOut.getLog());

            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            assertTrue(systemErr.getLog().startsWith(cause));
        });

        Main.main("-c", getPath("config-non-existing-classname-ignore.xml"),
                "-executeIgnoredModules",
                getPath("InputMain.java"));
    }

    @Test
    public void testInvalidCheckerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Invalid Checker threads number"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-C", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testInvalidTreeWalkerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Invalid TreeWalker threads number"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-W", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testZeroCheckerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Checker threads number must be greater than zero"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-C", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testZeroTreeWalkerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("TreeWalker threads number must be greater than zero"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("", systemErr.getLog());
        });
        Main.main("-W", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testCheckerThreadsNumber() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("", systemOut.getLog());
            assertEquals("", systemErr.getLog());
            assertTrue(TestRootModuleChecker.isProcessed());
            final DefaultConfiguration config =
                    (DefaultConfiguration) TestRootModuleChecker.getConfig();
            final ThreadModeSettings multiThreadModeSettings = config.getThreadModeSettings();
            assertEquals(4, multiThreadModeSettings.getCheckerThreadsNumber());
            assertEquals(1, multiThreadModeSettings.getTreeWalkerThreadsNumber());
        });
        Main.main("-C", "4", "-c", getPath("config-custom-root-module.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testTreeWalkerThreadsNumber() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("", systemOut.getLog());
            assertEquals("", systemErr.getLog());
            assertTrue(TestRootModuleChecker.isProcessed());
            final DefaultConfiguration config =
                    (DefaultConfiguration) TestRootModuleChecker.getConfig();
            final ThreadModeSettings multiThreadModeSettings = config.getThreadModeSettings();
            assertEquals(1, multiThreadModeSettings.getCheckerThreadsNumber());
            assertEquals(4, multiThreadModeSettings.getTreeWalkerThreadsNumber());
        });
        Main.main("-W", "4", "-c", getPath("config-custom-root-module.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testModuleNameInSingleThreadMode() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("", systemOut.getLog());
            assertEquals("", systemErr.getLog());
            assertTrue(TestRootModuleChecker.isProcessed());
            final DefaultConfiguration config =
                    (DefaultConfiguration) TestRootModuleChecker.getConfig();
            final ThreadModeSettings multiThreadModeSettings =
                config.getThreadModeSettings();
            assertEquals(1, multiThreadModeSettings.getCheckerThreadsNumber());
            assertEquals(1, multiThreadModeSettings.getTreeWalkerThreadsNumber());
            final Configuration checkerConfiguration = config
                .getChildren()[0];
            assertEquals("Checker", checkerConfiguration.getName());
            final Configuration treeWalkerConfig = checkerConfiguration.getChildren()[0];
            assertEquals("TreeWalker", treeWalkerConfig.getName());
        });
        Main.main("-C", "1", "-W", "1", "-c", getPath("config-multi-thread-mode.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testModuleNameInMultiThreadMode() throws Exception {
        TestRootModuleChecker.reset();

        try {
            Main.main("-C", "4", "-W", "4", "-c", getPath("config-multi-thread-mode.xml"),
                getPath("InputMain.java"));
            fail("An exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Multi thread mode for Checker module is not implemented",
                ex.getMessage());
        }
    }
}
