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

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class MainTest {
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
                final String usage = String.format(Locale.ROOT, "Unrecognized option: -w%n"
                    + "usage: java com.puppycrawl.tools.checkstyle.Main [options] -c <config.xml>%n"
                    + "            file...%n"
                    + " -c <arg>   Sets the check configuration file to use.%n"
                    + " -f <arg>   Sets the output format. (plain|xml). Defaults to plain%n"
                    + " -o <arg>   Sets the output file. Defaults to stdout%n"
                    + " -p <arg>   Loads the properties file%n"
                    + " -v         Print product version and exit%n");

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
                assertEquals("Must specify files to process, found 0." + System.lineSeparator(),
                        systemOut.getLog());
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
                        + " cannot initialize module TreeWalker - "
                        + "Unable to instantiate 'NonExistingClass' class, "
                        + "it is also not possible to instantiate it as ";
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
                                + "%1$s:3:14: "
                                + "warning: Name 'InputMain' must match pattern"
                                + " '^[a-z0-9]*$'.%n"
                                + "%1$s:5:7: "
                                + "warning: Name 'InputMainInner' must match pattern"
                                + " '^[a-z0-9]*$'.%n"
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
                        + "%1$s:3:14: error: "
                        + "Name 'InputMain' must match pattern '^[a-z0-9]*$'.%n"
                        + "%1$s:5:7: error: "
                        + "Name 'InputMainInner' must match pattern '^[a-z0-9]*$'.%n"
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
                final String errorOuput = String.format(Locale.ROOT,
                        "com.puppycrawl.tools.checkstyle.api."
                        + "CheckstyleException: unable to parse configuration stream"
                        + " - Content is not allowed in prolog.:7:1%n");
                assertTrue(systemErr.getLog().startsWith(errorOuput));
            }
        });
        Main.main("-c", getPath("config-Incorrect.xml"),
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
            fail();
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
            fail();
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

        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws IOException {
                final String expectedPath = getFilePath("checks/metrics") + File.separator;
                final StringBuilder sb = new StringBuilder();
                sb.append("Starting audit...").append(System.getProperty("line.separator"));
                final String format = "%s.java:%s: warning: File length is %s lines "
                    + "(max allowed is 170).";
                for (String[] outputValue : outputValues) {
                    final String line = String.format(Locale.ROOT, format,
                            expectedPath + outputValue[0], outputValue[1],
                            outputValue[2]);
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

                final String exceptionFirstLine =  String.format(Locale.ROOT,
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

}
