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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Main.class, CommonUtils.class, Closeables.class})
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

    private static final String EOL = System.getProperty("line.separator");

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Rule
    public final SystemErrRule systemErr = new SystemErrRule().enableLog().mute();
    @Rule
    public final SystemOutRule systemOut = new SystemOutRule().enableLog().mute();

    private final LocalizedMessage auditStartMessage = new LocalizedMessage(0,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted", null, null,
            getClass(), null);

    private final LocalizedMessage auditFinishMessage = new LocalizedMessage(0,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished", null, null,
            getClass(), null);

    private final LocalizedMessage errorCounterOneMessage = new LocalizedMessage(0,
            Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
            new String[] {String.valueOf(1)}, null, getClass(), null);

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/main/" + filename;
    }

    private static String getNonCompilablePath(String filename) {
        return "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/main/" + filename;
    }

    private static String getFilePath(String filename) throws IOException {
        return new File(getPath(filename)).getCanonicalPath();
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
        assertUtilsClassHasPrivateConstructor(Main.class, false);
    }

    @Test
    public void testVersionPrint()
            throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log",
                    "Checkstyle version: null" + System.lineSeparator(),
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-v");
    }

    @Test
    public void testWrongArgument()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            final String usage = "Unrecognized option: -w" + EOL
                    + USAGE;
            assertEquals("Unexpected ouput log", usage, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-w");
    }

    @Test
    public void testNoConfigSpecified()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log",
                    "Must specify a config XML file." + System.lineSeparator(),
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main(getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingTargetFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "NonExistingFile.java");
    }

    @Test
    public void testNonExistingConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Could not find config XML file "
                        + "'src/main/resources/non_existing_config.xml'." + EOL,
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "src/main/resources/non_existing_config.xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingOutputFormat() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Invalid output format. "
                    + "Found 'xmlp' but expected 'plain' or 'xml'." + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "-f", "xmlp",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistingClass() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedExceptionMessage = errorCounterOneMessage.getMessage()
                    + EOL;
            assertEquals("Unexpected ouput log", expectedExceptionMessage, systemOut.getLog());

            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(cause));
        });

        Main.main("-c", getPath("InputMainConfig-non-existing-classname.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFile() throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", auditStartMessage.getMessage() + EOL
                    + auditFinishMessage.getMessage() + EOL,
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
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
            assertEquals("Unexpected ouput log", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                    + "<checkstyle version=\"" + version + "\">" + EOL
                    + "<file name=\"" + expectedPath + "\">" + EOL
                    + "</file>" + EOL
                    + "</checkstyle>" + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutput() throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", auditStartMessage.getMessage() + EOL
                    + auditFinishMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "plain",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileWithViolations() throws Exception {
        exit.checkAssertionAfterwards(() -> {
            final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(0,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMainInner = new LocalizedMessage(0,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final String expectedPath = getFilePath("InputMain.java");
            assertEquals("Unexpected ouput log", auditStartMessage.getMessage() + EOL
                            + "[WARN] " + expectedPath + ":3:14: "
                            + invalidPatternMessageMain.getMessage()
                            + " [TypeName]" + EOL
                            + "[WARN] " + expectedPath + ":5:7: "
                            + invalidPatternMessageMainInner.getMessage()
                            + " [TypeName]" + EOL
                            + auditFinishMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname2.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFileWithError()
            throws Exception {
        exit.expectSystemExitWithStatus(2);
        exit.checkAssertionAfterwards(() -> {
            final LocalizedMessage errorCounterTwoMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                    new String[] {String.valueOf(2)}, null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(0,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMainInner = new LocalizedMessage(0,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final String expectedPath = getFilePath("InputMain.java");
            assertEquals("Unexpected ouput log", auditStartMessage.getMessage() + EOL
                    + "[ERROR] " + expectedPath + ":3:14: "
                    + invalidPatternMessageMain.getMessage() + " [TypeName]" + EOL
                    + "[ERROR] " + expectedPath + ":5:7: "
                    + invalidPatternMessageMainInner.getMessage() + " [TypeName]" + EOL
                    + auditFinishMessage.getMessage() + EOL
                    + errorCounterTwoMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c",
                getPath("InputMainConfig-classname2-error.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputToNonExistingFile()
            throws Exception {

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "plain",
                "-o", temporaryFolder.getRoot() + "/output.txt",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputToFile()
            throws Exception {
        final File file = temporaryFolder.newFile("file.output");
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                getPath("InputMain.java"));
    }

    @Test
    public void testCreateNonExistingOutputFile() throws Exception {
        final String outputFile = temporaryFolder.getRoot().getCanonicalPath() + "nonexisting.out";
        assertFalse("File must not exist", new File(outputFile).exists());
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "plain",
                "-o", outputFile,
                getPath("InputMain.java"));
        assertTrue("File must exist", new File(outputFile).exists());
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties() throws Exception {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(InputStream.class));

        //exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", auditStartMessage.getMessage() + EOL
                    + auditFinishMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", getPath("InputMainMycheckstyle.properties"),
                getPath("InputMain.java"));

        verifyStatic(times(1));
        Closeables.closeQuietly(any(InputStream.class));
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistingProperties()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Could not find file 'nonexisting.properties'."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", "nonexisting.properties",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String output = errorCounterOneMessage.getMessage() + EOL;
            assertEquals("Unexpected ouput log", output, systemOut.getLog());
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: unable to parse configuration stream - ";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("InputMainConfig-Incorrect.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String output = errorCounterOneMessage.getMessage() + EOL;
            assertEquals("Unexpected ouput log", output, systemOut.getLog());
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: cannot initialize module RegexpSingleline"
                    + " - RegexpSingleline is not allowed as a child in RegexpSingleline";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("InputMainConfig-incorrectChildren.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile2()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String output = errorCounterOneMessage.getMessage() + EOL;
            assertEquals("Unexpected ouput log", output, systemOut.getLog());
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: cannot initialize module TreeWalker"
                    + " - JavadocVariable is not allowed as a child in JavadocMethod";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("InputMainConfig-incorrectChildren2.xml"),
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
            assertTrue("Invalid error cause",
                    ex.getCause() instanceof CheckstyleException);
            // We do separate validation for message as in Windows
            // disk drive letter appear in message,
            // so we skip that drive letter for compatibility issues
            final LocalizedMessage loadPropertiesMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, Main.LOAD_PROPERTIES_EXCEPTION,
                    new String[] {""}, null, getClass(), null);
            final String causeMessage = ex.getCause().getLocalizedMessage();
            final String localizedMessage = loadPropertiesMessage.getMessage();
            final boolean samePrefix = causeMessage.substring(0, causeMessage.indexOf(' '))
                    .equals(localizedMessage
                            .substring(0, localizedMessage.indexOf(' ')));
            final boolean sameSufix =
                    causeMessage.substring(causeMessage.lastIndexOf(' '), causeMessage.length())
                    .equals(localizedMessage
                            .substring(localizedMessage.lastIndexOf(' '),
                                    localizedMessage.length()));
            assertTrue("Invalid error message", samePrefix || sameSufix);
            assertTrue("Invalid error message",
                    causeMessage.contains(":invalid"));
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
            final LocalizedMessage loadPropertiesMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, Main.CREATE_LISTENER_EXCEPTION,
                    new String[] {"myformat", "plain", "xml"}, null, getClass(), null);
            assertEquals("Invalid error message",
                    loadPropertiesMessage.getMessage(), ex.getCause().getLocalizedMessage());
            assertTrue("Invalid error cause",
                    ex.getCause() instanceof IllegalStateException);
        }
    }

    @Test
    public void testCreateListenerWithLocationIllegalStateException() throws Exception {
        mockStatic(CommonUtils.class);
        doNothing().when(CommonUtils.class);
        CommonUtils.close(any(OutputStream.class));

        final Method method = Main.class.getDeclaredMethod("createListener", String.class,
            String.class);
        method.setAccessible(true);
        final String outDir = "myfolder123";
        try {
            method.invoke(null, "myformat", outDir);
            fail("InvocationTargetException  is expected");
        }
        catch (InvocationTargetException ex) {
            final LocalizedMessage createListenerMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, Main.CREATE_LISTENER_EXCEPTION,
                    new String[] {"myformat", "plain", "xml"}, null, getClass(), null);
            assertEquals("Invalid error message",
                    createListenerMessage.getMessage(), ex.getCause().getLocalizedMessage());
            assertTrue("Invalid error cause",
                    ex.getCause() instanceof IllegalStateException);
        }
        finally {
            // method creates output folder
            FileUtils.deleteQuietly(new File(outDir));
        }

        verifyStatic(times(1));
        CommonUtils.close(any(OutputStream.class));
    }

    @Test
    public void testExistingDirectoryWithViolations() throws Exception {

        // we just reference there all violations
        final String[][] outputValues = {
                {"InputMainComplexityOverflow", "1", "172"},
        };

        final int allowedLength = 170;
        final String msgKey = "maxLen.file";
        final String bundle = "com.puppycrawl.tools.checkstyle.checks.sizes.messages";

        exit.checkAssertionAfterwards(() -> {
            final String expectedPath = getFilePath("") + File.separator;
            final StringBuilder sb = new StringBuilder(28);
            sb.append(auditStartMessage.getMessage())
                    .append(EOL);
            final String format = "[WARN] " + expectedPath + outputValues[0][0] + ".java:"
                    + outputValues[0][1] + ": ";
            for (String[] outputValue : outputValues) {
                final String localizedMessage = new LocalizedMessage(0, bundle,
                        msgKey, new Integer[] {Integer.valueOf(outputValue[2]), allowedLength},
                        null, getClass(), null).getMessage();
                final String line = format + localizedMessage + " [FileLength]";
                sb.append(line).append(EOL);
            }
            sb.append(auditFinishMessage.getMessage())
                    .append(EOL);
            assertEquals("Unexpected ouput log", sb.toString(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-c", getPath("InputMainConfig-filelength.xml"),
                getPath(""));
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
        assertEquals("Invalid result size", 0, result.size());
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
        assertEquals("Invalid result size", 0, result.size());
    }

    @Test
    public void testFileReferenceDuringException() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedExceptionMessage = auditStartMessage.getMessage() + EOL
                            + errorCounterOneMessage.getMessage() + EOL;
            assertEquals("Unexpected ouput log", expectedExceptionMessage, systemOut.getLog());

            final String exceptionFirstLine = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: Exception was thrown while processing "
                    + new File(getNonCompilablePath("InputMainIncorrectClass.java")).getPath()
                    + EOL;
            assertTrue("Unexpected system error log",
                    systemErr.getLog().startsWith(exceptionFirstLine));
        });

        // We put xml as source to cause parse excepion
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                getNonCompilablePath("InputMainIncorrectClass.java"));
    }

    @Test
    public void testPrintTreeOnMoreThanOneFile() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Printing AST is allowed for only one file."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-t", getPath(""));
    }

    @Test
    public void testPrintTreeOption() throws Exception {
        final String expected = "PACKAGE_DEF -> package [1:0]" + EOL
            + "|--ANNOTATIONS -> ANNOTATIONS [1:39]" + EOL
            + "|--DOT -> . [1:39]" + EOL
            + "|   |--DOT -> . [1:28]" + EOL
            + "|   |   |--DOT -> . [1:22]" + EOL
            + "|   |   |   |--DOT -> . [1:11]" + EOL
            + "|   |   |   |   |--IDENT -> com [1:8]" + EOL
            + "|   |   |   |   `--IDENT -> puppycrawl [1:12]" + EOL
            + "|   |   |   `--IDENT -> tools [1:23]" + EOL
            + "|   |   `--IDENT -> checkstyle [1:29]" + EOL
            + "|   `--IDENT -> main [1:40]" + EOL
            + "`--SEMI -> ; [1:44]" + EOL
            + "CLASS_DEF -> CLASS_DEF [3:0]" + EOL
            + "|--MODIFIERS -> MODIFIERS [3:0]" + EOL
            + "|   `--LITERAL_PUBLIC -> public [3:0]" + EOL
            + "|--LITERAL_CLASS -> class [3:7]" + EOL
            + "|--IDENT -> InputMain [3:13]" + EOL
            + "`--OBJBLOCK -> OBJBLOCK [3:23]" + EOL
            + "    |--LCURLY -> { [3:23]" + EOL
            + "    `--RCURLY -> } [4:0]" + EOL
            + "CLASS_DEF -> CLASS_DEF [5:0]" + EOL
            + "|--MODIFIERS -> MODIFIERS [5:0]" + EOL
            + "|--LITERAL_CLASS -> class [5:0]" + EOL
            + "|--IDENT -> InputMainInner [5:6]" + EOL
            + "`--OBJBLOCK -> OBJBLOCK [5:21]" + EOL
            + "    |--LCURLY -> { [5:21]" + EOL
            + "    `--RCURLY -> } [6:0]" + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", expected, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-t", getPath("InputMain.java"));
    }

    @Test
    public void testPrintTreeCommentsOption() throws Exception {
        final String expected = "PACKAGE_DEF -> package [1:0]" + EOL
            + "|--ANNOTATIONS -> ANNOTATIONS [1:39]" + EOL
            + "|--DOT -> . [1:39]" + EOL
            + "|   |--DOT -> . [1:28]" + EOL
            + "|   |   |--DOT -> . [1:22]" + EOL
            + "|   |   |   |--DOT -> . [1:11]" + EOL
            + "|   |   |   |   |--IDENT -> com [1:8]" + EOL
            + "|   |   |   |   `--IDENT -> puppycrawl [1:12]" + EOL
            + "|   |   |   `--IDENT -> tools [1:23]" + EOL
            + "|   |   `--IDENT -> checkstyle [1:29]" + EOL
            + "|   `--IDENT -> main [1:40]" + EOL
            + "`--SEMI -> ; [1:44]" + EOL
            + "CLASS_DEF -> CLASS_DEF [3:0]" + EOL
            + "|--MODIFIERS -> MODIFIERS [3:0]" + EOL
            + "|   |--BLOCK_COMMENT_BEGIN -> /* [2:0]" + EOL
            + "|   |   |--COMMENT_CONTENT -> comment [2:2]" + EOL
            + "|   |   `--BLOCK_COMMENT_END -> */ [2:8]" + EOL
            + "|   `--LITERAL_PUBLIC -> public [3:0]" + EOL
            + "|--LITERAL_CLASS -> class [3:7]" + EOL
            + "|--IDENT -> InputMain [3:13]" + EOL
            + "`--OBJBLOCK -> OBJBLOCK [3:23]" + EOL
            + "    |--LCURLY -> { [3:23]" + EOL
            + "    `--RCURLY -> } [4:0]" + EOL
            + "CLASS_DEF -> CLASS_DEF [5:0]" + EOL
            + "|--MODIFIERS -> MODIFIERS [5:0]" + EOL
            + "|--LITERAL_CLASS -> class [5:0]" + EOL
            + "|--IDENT -> InputMainInner [5:6]" + EOL
            + "`--OBJBLOCK -> OBJBLOCK [5:21]" + EOL
            + "    |--LCURLY -> { [5:21]" + EOL
            + "    `--RCURLY -> } [6:0]" + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", expected, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-T", getPath("InputMain.java"));
    }

    @Test
    public void testPrintTreeJavadocOption() throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("InputMainExpectedInputJavadocComment.txt"))), StandardCharsets.UTF_8)
            .replaceAll("\\\\r\\\\n", "\\\\n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log",
                    expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n"));
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-j", getPath("InputMainJavadocComment.javadoc"));
    }

    @Test
    public void testPrintFullTreeOption() throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("InputMainExpectedInputAstTreeStringPrinterJavadoc.txt"))),
            StandardCharsets.UTF_8).replaceAll("\\\\r\\\\n", "\\\\n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log",
                    expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n"));
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-J", getPath("InputMainAstTreeStringPrinterJavadoc.java"));
    }

    @Test
    public void testConflictingOptionsTvsC() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-c", "/google_checks.xml", "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsP() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-p", getPath("InputMainMycheckstyle.properties"), "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsF() throws Exception {

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-f", "plain", "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsO() throws Exception {
        final File file = temporaryFolder.newFile("file.output");

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-o", file.getCanonicalPath(), "-t", getPath(""));
    }

    @Test
    public void testDebugOption() throws Exception {
        exit.checkAssertionAfterwards(() -> assertNotEquals("Unexpected system error log",
                        "", systemErr.getLog()));
        Main.main("-c", "/google_checks.xml", getPath("InputMain.java"), "-d");
    }

    @Test
    public void testExcludeOption() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath(""), "-e", getFilePath(""));
    }

    @Test
    public void testExcludeRegexpOption() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected ouput log", "", systemErr.getLog());
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
        assertNotEquals("Invalid result size", 0, result.size());
    }

    @Test
    public void testCustomRootModule() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
            assertTrue("Invalid Checker state", TestRootModuleChecker.isProcessed());
        });
        Main.main("-c", getPath("InputMainConfig-custom-root-module.xml"),
                getPath("InputMain.java"));
        assertTrue("RootModule should be destroyed", TestRootModuleChecker.isDestroyed());
    }

    @Test
    public void testCustomSimpleRootModule() throws Exception {
        TestRootModuleChecker.reset();
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String checkstylePackage = "com.puppycrawl.tools.checkstyle.";
            final LocalizedMessage unableToInstantiateExceptionMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE,
                    "PackageObjectFactory.unableToInstantiateExceptionMessage",
                    new String[] {"TestRootModuleChecker", checkstylePackage
                            + "TestRootModuleChecker, "
                            + "TestRootModuleCheckerCheck, " + checkstylePackage
                            + "TestRootModuleCheckerCheck"},
                    null, getClass(), null);
            assertEquals("Unexpected ouput log", errorCounterOneMessage.getMessage() + EOL,
                    systemOut.getLog());
            assertTrue("Unexpected system error log",
                    systemErr.getLog().startsWith(checkstylePackage + "api.CheckstyleException: "
                    + unableToInstantiateExceptionMessage.getMessage()));
            assertFalse("Invalid checker state", TestRootModuleChecker.isProcessed());
        });
        Main.main("-c", getPath("InputMainConfig-custom-simple-root-module.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExecuteIgnoredModule() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String expectedExceptionMessage = errorCounterOneMessage.getMessage() + EOL;
            assertEquals("Unexpected ouput log", expectedExceptionMessage, systemOut.getLog());

            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(cause));
        });

        Main.main("-c", getPath("InputMainConfig-non-existing-classname-ignore.xml"),
                "-executeIgnoredModules",
                getPath("InputMain.java"));
    }

    @Test
    public void testInvalidCheckerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Invalid Checker threads number"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-C", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testInvalidTreeWalkerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Invalid TreeWalker threads number"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-W", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testZeroCheckerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "Checker threads number must be greater than zero"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-C", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testZeroTreeWalkerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log",
                    "TreeWalker threads number must be greater than zero"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-W", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testCheckerThreadsNumber() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
            assertTrue("Invalid checker state", TestRootModuleChecker.isProcessed());
            final DefaultConfiguration config =
                    (DefaultConfiguration) TestRootModuleChecker.getConfig();
            final ThreadModeSettings multiThreadModeSettings = config.getThreadModeSettings();
            assertEquals("Invalid checker thread number",
                    4, multiThreadModeSettings.getCheckerThreadsNumber());
            assertEquals("Invalid checker thread number",
                    1, multiThreadModeSettings.getTreeWalkerThreadsNumber());
        });
        Main.main("-C", "4", "-c", getPath("InputMainConfig-custom-root-module.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testTreeWalkerThreadsNumber() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
            assertTrue("Invalid checker state", TestRootModuleChecker.isProcessed());
            final DefaultConfiguration config =
                    (DefaultConfiguration) TestRootModuleChecker.getConfig();
            final ThreadModeSettings multiThreadModeSettings = config.getThreadModeSettings();
            assertEquals("Invalid checker thread number",
                    1, multiThreadModeSettings.getCheckerThreadsNumber());
            assertEquals("Invalid checker thread number",
                    4, multiThreadModeSettings.getTreeWalkerThreadsNumber());
        });
        Main.main("-W", "4", "-c", getPath("InputMainConfig-custom-root-module.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testModuleNameInSingleThreadMode() throws Exception {
        TestRootModuleChecker.reset();

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected ouput log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
            assertTrue("Invalid checker state", TestRootModuleChecker.isProcessed());
            final DefaultConfiguration config =
                    (DefaultConfiguration) TestRootModuleChecker.getConfig();
            final ThreadModeSettings multiThreadModeSettings =
                config.getThreadModeSettings();
            assertEquals("Invalid checker thread number",
                    1, multiThreadModeSettings.getCheckerThreadsNumber());
            assertEquals("Invalid checker thread number",
                    1, multiThreadModeSettings.getTreeWalkerThreadsNumber());
            final Configuration checkerConfiguration = config
                .getChildren()[0];
            assertEquals("Invalid checker name", "Checker", checkerConfiguration.getName());
            final Configuration treeWalkerConfig = checkerConfiguration.getChildren()[0];
            assertEquals("Invalid checker childs name", "TreeWalker", treeWalkerConfig.getName());
        });
        Main.main("-C", "1", "-W", "1", "-c", getPath("InputMainConfig-multi-thread-mode.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testModuleNameInMultiThreadMode() throws Exception {
        TestRootModuleChecker.reset();

        try {
            Main.main("-C", "4", "-W", "4", "-c", getPath("InputMainConfig-multi-thread-mode.xml"),
                getPath("InputMain.java"));
            fail("An exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid error message",
                    "Multi thread mode for Checker module is not implemented",
                ex.getMessage());
        }
    }
}
