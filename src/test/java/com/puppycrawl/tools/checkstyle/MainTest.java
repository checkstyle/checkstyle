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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
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
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(Main.class);
    }

    @Test
    public void testVersionPrint()
            throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
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
            public void checkAssertion() {
                String usage = String.format("Unrecognized option: -w%n"
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
            public void checkAssertion() {
                assertEquals("Must specify a config XML file." + System.lineSeparator(),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testNonExistingTargetFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals("Must specify files to process, found 0." + System.lineSeparator(),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", "NonexistingFile.java");
    }

    @Test
    public void testNonExistingConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(String.format("unable to find src/main/resources/non_existing_config.xml%n"
                                + "Checkstyle ends with 1 errors.%n"),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/main/resources/non_existing_config.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testNonExistingOutputFormat() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(String.format("Invalid output format. "
                        + "Found 'xmlp' but expected 'plain' or 'xml'.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "/google_checks.xml", "-f" , "xmlp",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFile() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(String.format("Starting audit...%n"
                        + "Audit done.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFileXmlOutput() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws IOException {
                String currentPath = new File(".").getCanonicalPath();
                String expectedPath = currentPath
                        + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java"
                        .replace("/", File.separator);
                final ResourceBundle compilationProperties =
                        ResourceBundle.getBundle("checkstylecompilation");
                String version = compilationProperties.getString("checkstyle.compile.version");
                assertEquals(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>%n"
                        + "<checkstyle version=\"%s\">%n"
                        + "<file name=\"%s\">%n"
                        + "</file>%n"
                        + "</checkstyle>%n", version, expectedPath), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "-f", "xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutput() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(String.format("Starting audit...%n"
                        + "Audit done.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "-f", "plain",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFileWithViolations() throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws IOException {
                String currentPath = new File(".").getCanonicalPath();
                String expectedPath = currentPath
                    + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java"
                    .replace("/", File.separator);
                assertEquals(String.format("Starting audit...%n"
                                + "%1$s:3:14: "
                                + "warning: Name 'InputMain' must match pattern '^[a-z0-9]*$'.%n"
                                + "%1$s:5:7: "
                                + "warning: Name 'InputMainInner' must match pattern '^[a-z0-9]*$'.%n"
                                + "Audit done.%n", expectedPath),
                        systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname2.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFileWithError()
            throws Exception {
        exit.expectSystemExitWithStatus(2);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws IOException {
                String currentPath = new File(".").getCanonicalPath();
                String expectedPath = currentPath
                    + "/src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java"
                    .replace("/", File.separator);
                assertEquals(String.format("Starting audit...%n"
                        + "%1$s:3:14: "
                        + "Name 'InputMain' must match pattern '^[a-z0-9]*$'.%n"
                        + "%1$s:5:7: "
                        + "Name 'InputMainInner' must match pattern '^[a-z0-9]*$'.%n"
                        + "Audit done.%n"
                        + "Checkstyle ends with 2 errors.%n", expectedPath), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c",
                "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname2-error.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputToNonExistingFile()
            throws Exception {

        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals("", systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "-f", "plain",
                "-o", temporaryFolder.getRoot() + "/output.txt",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputToFile()
            throws Exception {
        final File file = temporaryFolder.newFile("file.output");
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals("", systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputToFileWithoutRwPermissions()
            throws Exception {
        final File file = temporaryFolder.newFile("file.output");
        assertTrue(file.setReadable(true, true));
        assertTrue(file.setWritable(false, false));
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws IOException {
                assertEquals("Permission denied : '" + file.getCanonicalPath() + "'."
                        + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputToFileWithoutReadAndRwPermissions()
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
            public void checkAssertion() throws IOException {
                assertEquals("Permission denied : '" + file.getCanonicalPath() + "'."
                        + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-classname.xml",
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties()
            throws Exception {
        //exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(String.format("Starting audit...%n"
                        + "Audit done.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/"
                + "config-classname-prop.xml",
                "-p", "src/test/resources/com/puppycrawl/tools/checkstyle/mycheckstyle.properties",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistingProperties()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals("Could not find file 'nonexisting.properties'."
                        + System.lineSeparator(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/"
                + "config-classname-prop.xml",
                "-p", "nonexisting.properties",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testExistingIncorrectConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals(String.format("unable to parse configuration stream - Content is not allowed in prolog.:7:1%n"
                        + "Checkstyle ends with 1 errors.%n"), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });
        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/"
                + "config-Incorrect.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/InputMain.java");
    }

    @Test
    public void testLoadProperties_IOException() throws Exception {
        Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        Method method = Main.class.getDeclaredMethod("loadProperties", param);
        method.setAccessible(true);
        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                // https://support.microsoft.com/en-us/kb/177506 but this only for NTFS
                // WindowsServer 2012 use Resilient File System (ReFS), so any name is ok
                File file = new File(File.separator + ":invalid");
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
        catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof CheckstyleException);
            // We do separate validation for message as in Windows
            // disk drive letter appear in message,
            // so we skip that drive letter for compatibility issues
            assertTrue(e.getCause().getMessage().startsWith("Unable to load properties from file '"));
            assertTrue(e.getCause().getMessage().endsWith(":invalid'."));
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCreateListener_IllegalStateException() throws Exception {
        Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        Method method = Main.class.getDeclaredMethod("createListener", String.class, String.class);
        method.setAccessible(true);
        try {
            method.invoke(null, "myformat", null);
            fail();
        }
        catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertTrue(e.getCause().getMessage().startsWith("Invalid output format. Found"));
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCreateListenerWithLocation_IllegalStateException() throws Exception {
        Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        Method method = Main.class.getDeclaredMethod("createListener", String.class, String.class);
        method.setAccessible(true);
        String outDir = "myfolder123";
        try {
            method.invoke(null, "myformat", outDir);
            fail();
        }
        catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertTrue(e.getCause().getMessage().startsWith("Invalid output format. Found"));
        }
        finally {
            // method creates output folder
            FileUtils.deleteQuietly(new File(outDir));
        }
    }

    @Test
    public void testExistingDirectoryWithViolations() throws Exception {

        // we just reference there all violations
        final String[][] outputValues = new String[][]{
            {"JavaNCSSCheckTestInput", "1", "84"},
        };

        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() throws IOException {
                String currentPath = new File(".").getCanonicalPath();
                String expectedPath = currentPath
                        + "/src/test/resources/com/puppycrawl/tools/checkstyle/metrics/"
                        .replace("/", File.separator);
                String format = "%s.java:%s: warning: File length is %s lines (max allowed is 80).";
                StringBuilder sb = new StringBuilder();
                sb.append("Starting audit..." + System.getProperty("line.separator"));
                for (String[] outputValue : outputValues) {
                    String line = String.format(format,
                            expectedPath + outputValue[0], outputValue[1],
                            outputValue[2]);
                    sb.append(line + System.getProperty("line.separator"));
                }
                sb.append("Audit done." + System.getProperty("line.separator"));
                assertEquals(sb.toString(), systemOut.getLog());
                assertEquals("", systemErr.getLog());
            }
        });

        Main.main("-c", "src/test/resources/com/puppycrawl/tools/checkstyle/config-filelength.xml",
                "src/test/resources/com/puppycrawl/tools/checkstyle/metrics/");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFiles_notFile() throws Exception {
        Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        Method method = Main.class.getDeclaredMethod("listFiles", File.class);
        method.setAccessible(true);

        File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(false);
        when(fileMock.isFile()).thenReturn(false);

        List<File> result = (List<File>) method.invoke(null, fileMock);
        assertTrue(result.size() == 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListFiles_DirectoryWithNull() throws Exception {
        Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        Method method = Main.class.getDeclaredMethod("listFiles", File.class);
        method.setAccessible(true);

        File fileMock = mock(File.class);
        when(fileMock.canRead()).thenReturn(true);
        when(fileMock.isDirectory()).thenReturn(true);
        when(fileMock.listFiles()).thenReturn(null);

        List<File> result = (List<File>) method.invoke(null, fileMock);
        assertTrue(result.size() == 0);
    }
}
