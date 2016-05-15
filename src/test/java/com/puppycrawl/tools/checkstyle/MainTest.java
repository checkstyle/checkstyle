////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class MainTest {
    private static final String USAGE = String.format(Locale.ROOT,
          "usage: java com.puppycrawl.tools.checkstyle.Main [options] -c <config.xml>"
        + " file...%n"
        + " -c <arg>                Sets the check configuration file to use.%n"
        + " -d,--debug              Print all debug logging of CheckStyle utility%n"
        + " -f <arg>                Sets the output format. (plain|xml). Defaults to"
        + " plain%n"
        + " -j,--javadocTree        Print Parse tree of the Javadoc comment%n"
        + " -J,--treeWithJavadoc    Print full Abstract Syntax Tree of the file%n"
        + " -o <arg>                Sets the output file. Defaults to stdout%n"
        + " -p <arg>                Loads the properties file%n"
        + " -t,--tree               Print Abstract Syntax Tree(AST) of the file%n"
        + " -T,--treeWithComments   Print Abstract Syntax Tree(AST) of the file"
        + " including comments%n"
        + " -v                      Print product version and exit%n");

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

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(Main.class);
    }

    @Test
    public void testVersionPrint()
            throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Checkstyle version: null" + System.lineSeparator(),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-v");
    }

    @Test
    public void testWrongArgument()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                final String usage = String.format(Locale.ROOT, "Unrecognized option: -w%n")
                        + USAGE;
                assertEquals(usage, systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-w");
    }

    @Test
    public void testNoConfigSpecified()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Must specify a config XML file." + System.lineSeparator(),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main(getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingTargetFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Files to process must be specified, found 0."
                    + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", "NonExistingFile.java");
    }

    @Test
    public void testNonExistingConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(String.format(Locale.ROOT,
                        "Could not find config XML file "
                            + "'src/main/resources/non_existing_config.xml'.%n"),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/main/resources/non_existing_config.xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingOutputFormat() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(String.format(Locale.ROOT, "Invalid output format. "
                        + "Found 'xmlp' but expected 'plain' or 'xml'.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", "-f", "xmlp",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingClass() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                final String expectedExceptionMessage =
                        String.format(Locale.ROOT, "Checkstyle ends with 1 errors.%n");
                assertEquals(expectedExceptionMessage, systemOut.getLog());

                final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                        + " cannot initialize module TreeWalker - ";
                assertTrue(systemErr.getLog().startsWith(cause));
            }
        });

        Main.main("-c", getPath("config-non-existing-classname.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFile() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                        + "Audit done.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileXmlOutput() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
                final String expectedPath = getFilePath("InputMain.java");
                final ResourceBundle compilationProperties =
                        ResourceBundle.getBundle("checkstylecompilation");
                final String version = compilationProperties
                    .getString("checkstyle.compile.version");
                assertEquals(String.format(Locale.ROOT,
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>%n"
                        + "<checkstyle version=\"%s\">%n"
                        + "<file name=\"%s\">%n"
                        + "</file>%n"
                        + "</checkstyle>%n", version, expectedPath), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutput() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                        + "Audit done.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileWithViolations() throws Exception {
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
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
            }
        });
        Main.main("-c", getPath("config-classname2.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileWithError()
            throws Exception {
        exit.expectSystemExitWithStatus(2);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
                final String expectedPath = getFilePath("InputMain.java");
                assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                        + "[ERROR] %1$s:3:14: "
                        + "Name 'InputMain' must match pattern '^[a-z0-9]*$'. [TypeName]%n"
                        + "[ERROR] %1$s:5:7: "
                        + "Name 'InputMainInner' must match pattern '^[a-z0-9]*$'. [TypeName]%n"
                        + "Audit done.%n"
                        + "Checkstyle ends with 2 errors.%n", expectedPath), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c",
                getPath("config-classname2-error.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputToNonExistingFile()
            throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("", systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
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
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("", systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputToFileWithoutRwPermissions()
            throws Exception {
        final File file = temporaryFolder.newFile("file.output");
        assertTrue(file.setReadable(true, true));
        assertTrue(file.setWritable(false, false));
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
                assertEquals("Permission denied : '" + file.getCanonicalPath() + "'."
                        + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingFilePlainOutputToFileWithoutReadAndRwPermissions()
            throws Exception {
        final File file = temporaryFolder.newFile("file.output");
        // That works fine on Linux/Unix, but ....
        // It's not possible to make a file unreadable in Windows NTFS for owner.
        // http://stackoverflow.com/a/4354686
        // https://github.com/google/google-oauth-java-client/issues/55#issuecomment-69403681
        //assertTrue(file.setReadable(false, false));
        assertTrue(file.setWritable(false, false));
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
                assertEquals("Permission denied : '" + file.getCanonicalPath() + "'."
                        + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname.xml"),
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties()
            throws Exception {
        //exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(String.format(Locale.ROOT, "Starting audit...%n"
                        + "Audit done.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname-prop.xml"),
                "-p", getPath("mycheckstyle.properties"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistingProperties()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Could not find file 'nonexisting.properties'."
                        + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("config-classname-prop.xml"),
                "-p", "nonexisting.properties",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                final String output = String.format(Locale.ROOT,
                        "Checkstyle ends with 1 errors.%n");
                assertEquals(output, systemOut.getLog());
                final String errorOuput = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: unable to parse configuration stream - ";
                assertTrue(systemErr.getLog().startsWith(errorOuput));
            }
        });
        Main.main("-c", getPath("config-Incorrect.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                final String output = String.format(Locale.ROOT,
                        "Checkstyle ends with 1 errors.%n");
                assertEquals(output, systemOut.getLog());
                final String errorOuput = "com.puppycrawl.tools.checkstyle.api."
                        + "CheckstyleException: cannot initialize module RegexpSingleline"
                        + " - RegexpSingleline is not allowed as a child in RegexpSingleline";
                assertTrue(systemErr.getLog().startsWith(errorOuput));
            }
        });
        Main.main("-c", getPath("config-incorrectChildren.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile2()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                final String output = String.format(Locale.ROOT,
                        "Checkstyle ends with 1 errors.%n");
                assertEquals(output, systemOut.getLog());
                final String errorOuput = "com.puppycrawl.tools.checkstyle.api."
                        + "CheckstyleException: cannot initialize module TreeWalker"
                        + " - JavadocVariable is not allowed as a child in JavadocMethod";
                assertTrue(systemErr.getLog().startsWith(errorOuput));
            }
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
                method.invoke(null, new File(File.separator + ":invalid"));
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
                final String expectedPath = getFilePath("checks/metrics") + File.separator;
                final StringBuilder sb = new StringBuilder();
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
                getPath("checks/metrics"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFilesNotFile() throws Exception {
        final Method method = Main.class.getDeclaredMethod("listFiles", File.class);
        method.setAccessible(true);

        final File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(false);
        when(fileMock.isFile()).thenReturn(false);

        final List<File> result = (List<File>) method.invoke(null, fileMock);
        assertEquals(0, result.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFilesDirectoryWithNull() throws Exception {
        final Method method = Main.class.getDeclaredMethod("listFiles", File.class);
        method.setAccessible(true);

        final File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(true);
        when(fileMock.listFiles()).thenReturn(null);

        final List<File> result = (List<File>) method.invoke(null, fileMock);
        assertEquals(0, result.size());
    }

    @Test
    public void testFileReferenceDuringException() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
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
            }
        });

        // We put xml as source to cause parse excepion
        Main.main("-c", getPath("config-classname.xml"),
                getNonCompilablePath("InputIncorrectClass.java"));
    }

    @Test
    public void testPrintTreeOnMoreThanOneFile() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Printing AST is allowed for only one file."
                    + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
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

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(expected, systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
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

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(expected, systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-T", getPath("InputMain.java"));
    }

    @Test
    public void testPrintTreeJavadocOption() throws Exception {
        final String expected = Files.toString(new File(
                getPath("astprinter/expectedInputJavadocComment.txt")), Charsets.UTF_8)
                    .replaceAll("\\\\r\\\\n", "\\\\n");

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n"));
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-j", getPath("astprinter/InputJavadocComment.javadoc"));
    }

    @Test
    public void testPrintFullTreeOption() throws Exception {
        final String expected = Files.toString(new File(
                getPath("astprinter/expectedInputAstTreeStringPrinterJavadoc.txt")),
                Charsets.UTF_8).replaceAll("\\\\r\\\\n", "\\\\n");

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals(expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n"));
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-J", getPath("astprinter/InputAstTreeStringPrinterJavadoc.java"));
    }

    @Test
    public void testConflictingOptionsTvsC() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Option '-t' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });

        Main.main("-c", "/google_checks.xml", "-t", getPath("checks/metrics"));
    }

    @Test
    public void testConflictingOptionsTvsP() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Option '-t' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });

        Main.main("-p", getPath("mycheckstyle.properties"), "-t", getPath("checks/metrics"));
    }

    @Test
    public void testConflictingOptionsTvsF() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Option '-t' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });

        Main.main("-f", "plain", "-t", getPath("checks/metrics"));
    }

    @Test
    public void testConflictingOptionsTvsO() throws Exception {
        final File file = temporaryFolder.newFile("file.output");

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertEquals("Option '-t' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });

        Main.main("-o", file.getCanonicalPath(), "-t", getPath("checks/metrics"));
    }

    @Test
    public void testDebugOption() throws Exception {
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() {
                assertNotEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", getPath("InputMain.java"), "-d");
    }
}
