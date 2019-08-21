////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestRootModuleChecker;

public class MainTest {

    private static final String SHORT_USAGE = String.format(Locale.ROOT,
            "Usage: checkstyle [OPTIONS]... FILES...%n"
            + "Try 'checkstyle --help' for more information.%n");

    private static final String USAGE = String.format(Locale.ROOT,
          "Usage: checkstyle [-dEghjJtTV] [-c=<configurationFile>] [-C=<checkerThreadsNumber>]"
          + " [-f=<format>]%n"
          + "                  [-o=<outputPath>] [-p=<propertiesFile>]"
          + " [-s=<suppressionLineColumnNumber>]%n"
          + "                  [-w=<tabWidth>] [-W=<treeWalkerThreadsNumber>]"
          + " [-e=<exclude>]...%n"
          + "                  [-x=<excludeRegex>]..."
          + " <files>...%n"
          + "Checkstyle verifies that the specified source code files adhere to the specified"
          + " rules. By default%n"
          + "errors are reported to standard out in plain format. Checkstyle requires a"
          + " configuration XML file%n"
          + "that configures the checks to apply.%n"
          + "      <files>...            One or more source files to verify%n"
          + "  -c=<configurationFile>    Sets the check configuration file to use.%n"
          + "  -C, --checker-threads-number=<checkerThreadsNumber>%n"
          + "                            (experimental) The number of Checker threads (must be"
          + " greater than zero)%n"
          + "  -d, --debug               Print all debug logging of CheckStyle utility%n"
          + "  -e, --exclude=<exclude>   Directory/File path to exclude from CheckStyle%n"
          + "  -E, --executeIgnoredModules%n"
          + "                            Allows ignored modules to be run.%n"
          + "  -f=<format>               Sets the output format. Valid values: xml, plain."
          + " Defaults to plain%n"
          + "  -g, --generate-xpath-suppression%n"
          + "                            Generates to output a suppression xml to use to suppress"
          + " all violations%n"
          + "                              from user's config%n"
          + "  -h, --help                Show this help message and exit.%n"
          + "  -j, --javadocTree         Print Parse tree of the Javadoc comment%n"
          + "  -J, --treeWithJavadoc     Print full Abstract Syntax Tree of the file%n"
          + "  -o=<outputPath>           Sets the output file. Defaults to stdout%n"
          + "  -p=<propertiesFile>       Loads the properties file%n"
          + "  -s=<suppressionLineColumnNumber>%n"
          + "                            Print xpath suppressions at the file's line and column"
          + " position.%n"
          + "                              Argument is the line and column number (separated by"
          + " a : ) in the%n"
          + "                              file that the suppression should be generated for%n"
          + "  -t, --tree                Print Abstract Syntax Tree(AST) of the file%n"
          + "  -T, --treeWithComments    Print Abstract Syntax Tree(AST) of the file including"
          + " comments%n"
          + "  -V, --version             Print version information and exit.%n"
          + "  -w, --tabWidth=<tabWidth> Sets the length of the tab character. Used only with"
          + " \"-s\" option.%n"
          + "                              Default value is 8%n"
          + "  -W, --tree-walker-threads-number=<treeWalkerThreadsNumber>%n"
          + "                            (experimental) The number of TreeWalker threads (must be"
          + " greater than%n"
          + "                              zero)%n"
          + "  -x, --exclude-regexp=<excludeRegex>%n"
          + "                            Regular expression of directory/file to exclude from"
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

    private final LocalizedMessage auditStartMessage = new LocalizedMessage(1,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted", null, null,
            getClass(), null);

    private final LocalizedMessage auditFinishMessage = new LocalizedMessage(1,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished", null, null,
            getClass(), null);

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
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(Main.class, false));
    }

    @Test
    public void testVersionPrint()
            throws Exception {
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    "Checkstyle version: null" + System.lineSeparator(),
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-V");
    }

    @Test
    public void testUsageHelpPrint()
            throws Exception {
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    USAGE,
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-h");
    }

    @Test
    public void testWrongArgument()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            final String usage = "Unknown option: '-q'" + EOL
                    + SHORT_USAGE;
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", usage, systemErr.getLog());
        });
        // need to specify a file:
        // <files> is defined as a required positional param;
        // picocli verifies required parameters before checking unknown options
        Main.main("-q", "file");
    }

    @Test
    public void testWrongArgumentMissingFiles()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            // files is defined as a required positional param;
            // picocli verifies required parameters before checking unknown options
            final String usage = "Missing required parameter: <files>" + EOL
                    + SHORT_USAGE;
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", usage, systemErr.getLog());
        });
        Main.main("-q");
    }

    @Test
    public void testNoConfigSpecified()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    "Must specify a config XML file." + System.lineSeparator(),
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main(getPath("InputMain.java"));
    }

    @Test
    public void testNonExistentTargetFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "NonExistentFile.java");
    }

    @Test
    public void testExistingTargetFileButWithoutReadAccess()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        final File file = temporaryFolder.newFile("testExistingTargetFileButWithoutReadAccess");
        file.setReadable(false);
        Main.main("-c", "/google_checks.xml", file.getCanonicalPath());
    }

    @Test
    public void testNonExistentConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Could not find config XML file "
                        + "'src/main/resources/non_existent_config.xml'." + EOL,
                    systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "src/main/resources/non_existent_config.xml",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistentOutputFormat() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "Invalid value for option '-f': expected one of [XML, PLAIN]"
                        + " (case-insensitive) but was 'xmlp'"
                    + EOL + SHORT_USAGE, systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "-f", "xmlp",
                getPath("InputMain.java"));
    }

    @Test
    public void testNonExistentClass() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(cause));
        });

        Main.main("-c", getPath("InputMainConfig-non-existent-classname.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFile() throws Exception {
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", auditStartMessage.getMessage() + EOL
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
            final String version = Main.class.getPackage().getImplementationVersion();
            assertEquals("Unexpected output log", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
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
            assertEquals("Unexpected output log", auditStartMessage.getMessage() + EOL
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
            final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(1,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMainInner = new LocalizedMessage(1,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final String expectedPath = getFilePath("InputMain.java");
            assertEquals("Unexpected output log", auditStartMessage.getMessage() + EOL
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
            final LocalizedMessage errorCounterTwoMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                    new String[] {String.valueOf(2)}, null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(1,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMainInner = new LocalizedMessage(1,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final String expectedPath = getFilePath("InputMain.java");
            assertEquals("Unexpected output log", auditStartMessage.getMessage() + EOL
                    + "[ERROR] " + expectedPath + ":3:14: "
                    + invalidPatternMessageMain.getMessage() + " [TypeName]" + EOL
                    + "[ERROR] " + expectedPath + ":5:7: "
                    + invalidPatternMessageMainInner.getMessage() + " [TypeName]" + EOL
                    + auditFinishMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    errorCounterTwoMessage.getMessage() + EOL, systemErr.getLog());
        });
        Main.main("-c",
                getPath("InputMainConfig-classname2-error.xml"),
                getPath("InputMain.java"));
    }

    /**
     * Similar test to {@link #testExistingTargetFileWithError}, but for PIT mutation tests:
     * this test fails if the boundary condition is changed from {@code if (exitStatus > 0)}
     * to {@code if (exitStatus > 1)}.
     * @throws Exception should not throw anything
     */
    @Test
    public void testExistingTargetFileWithOneError()
            throws Exception {
        exit.expectSystemExitWithStatus(1);
        exit.checkAssertionAfterwards(() -> {
            final LocalizedMessage errorCounterTwoMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                    new String[] {String.valueOf(1)}, null, getClass(), null);
            final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(1,
                    "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                    "name.invalidPattern", new String[] {"InputMain1", "^[a-z0-9]*$"},
                    null, getClass(), null);
            final String expectedPath = getFilePath("InputMain1.java");
            assertEquals("Unexpected output log", auditStartMessage.getMessage() + EOL
                    + "[ERROR] " + expectedPath + ":3:14: "
                    + invalidPatternMessageMain.getMessage() + " [TypeName]" + EOL
                    + auditFinishMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    errorCounterTwoMessage.getMessage() + EOL, systemErr.getLog());
        });
        Main.main("-c",
                getPath("InputMainConfig-classname2-error.xml"),
                getPath("InputMain1.java"));
    }

    @Test
    public void testExistentTargetFilePlainOutputToNonExistentFile()
            throws Exception {
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "", systemOut.getLog());
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
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "plain",
                "-o", file.getCanonicalPath(),
                getPath("InputMain.java"));
    }

    @Test
    public void testCreateNonExistentOutputFile() throws Exception {
        final String outputFile = temporaryFolder.getRoot().getCanonicalPath() + "nonexistent.out";
        assertFalse("File must not exist", new File(outputFile).exists());
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                "-f", "plain",
                "-o", outputFile,
                getPath("InputMain.java"));
        assertTrue("File must exist", new File(outputFile).exists());
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties() throws Exception {
        //exit.expectSystemExitWithStatus(0);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", auditStartMessage.getMessage() + EOL
                    + auditFinishMessage.getMessage() + EOL, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", getPath("InputMainMycheckstyle.properties"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistentProperties()
            throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Could not find file 'nonexistent.properties'."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", "nonexistent.properties",
                getPath("InputMain.java"));
    }

    @Test
    public void testExistingIncorrectConfigFile()
            throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
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
            final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: cannot initialize module TreeWalker - "
                    + "cannot initialize module JavadocMethod - "
                    + "JavadocVariable is not allowed as a child in JavadocMethod";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(errorOutput));
        });
        Main.main("-c", getPath("InputMainConfig-incorrectChildren2.xml"),
            getPath("InputMain.java"));
    }

    @Test
    public void testLoadPropertiesIoException() throws Exception {
        final Class<?>[] param = new Class<?>[1];
        param[0] = File.class;
        final Class<?> cliOptionsClass = Class.forName(Main.class.getName());
        final Method method = cliOptionsClass.getDeclaredMethod("loadProperties", param);
        method.setAccessible(true);
        try {
            method.invoke(null, new File("."));
            fail("Exception was expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Invalid error cause",
                    ex.getCause() instanceof CheckstyleException);
            // We do separate validation for message as in Windows
            // disk drive letter appear in message,
            // so we skip that drive letter for compatibility issues
            final LocalizedMessage loadPropertiesMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE, Main.LOAD_PROPERTIES_EXCEPTION,
                    new String[] {""}, null, getClass(), null);
            final String causeMessage = ex.getCause().getLocalizedMessage();
            final String localizedMessage = loadPropertiesMessage.getMessage();
            final boolean samePrefix = causeMessage.substring(0, causeMessage.indexOf(' '))
                    .equals(localizedMessage
                            .substring(0, localizedMessage.indexOf(' ')));
            final boolean sameSuffix =
                    causeMessage.substring(causeMessage.lastIndexOf(' '))
                    .equals(localizedMessage
                            .substring(localizedMessage.lastIndexOf(' ')));
            assertTrue("Invalid error message", samePrefix || sameSuffix);
            assertTrue("Invalid error message", causeMessage.contains(".'"));
        }
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
                final String localizedMessage = new LocalizedMessage(1, bundle,
                        msgKey, new Integer[] {Integer.valueOf(outputValue[2]), allowedLength},
                        null, getClass(), null).getMessage();
                final String line = format + localizedMessage + " [FileLength]";
                sb.append(line).append(EOL);
            }
            sb.append(auditFinishMessage.getMessage())
                    .append(EOL);
            assertEquals("Unexpected output log", sb.toString(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-c", getPath("InputMainConfig-filelength.xml"),
                getPath(""));
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testListFilesNotFile() throws Exception {
        final File fileMock = new File("") {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean isDirectory() {
                return false;
            }

            @Override
            public boolean isFile() {
                return false;
            }
        };

        final List<File> result = Whitebox.invokeMethod(Main.class, "listFiles",
                fileMock, new ArrayList<Pattern>());
        assertEquals("Invalid result size", 0, result.size());
    }

    /**
     * Test doesn't need to be serialized.
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     */
    @Test
    public void testListFilesDirectoryWithNull() throws Exception {
        final File[] nullResult = null;
        final File fileMock = new File("") {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean canRead() {
                return true;
            }

            @Override
            public boolean isDirectory() {
                return true;
            }

            @Override
            public File[] listFiles() {
                return nullResult;
            }
        };

        final List<File> result = Whitebox.invokeMethod(Main.class, "listFiles",
                fileMock, new ArrayList<Pattern>());
        assertEquals("Invalid result size", 0, result.size());
    }

    @Test
    public void testFileReferenceDuringException() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String exceptionFirstLine = "com.puppycrawl.tools.checkstyle.api."
                    + "CheckstyleException: Exception was thrown while processing "
                    + new File(getNonCompilablePath("InputMainIncorrectClass.java")).getPath()
                    + EOL;
            assertTrue("Unexpected system error log",
                    systemErr.getLog().startsWith(exceptionFirstLine));
        });

        // We put xml as source to cause parse exception
        Main.main("-c", getPath("InputMainConfig-classname.xml"),
                getNonCompilablePath("InputMainIncorrectClass.java"));
    }

    @Test
    public void testPrintTreeOnMoreThanOneFile() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Printing AST is allowed for only one file."
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
            assertEquals("Unexpected output log", expected, systemOut.getLog());
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
            assertEquals("Unexpected output log", expected, systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-T", getPath("InputMain.java"));
    }

    @Test
    public void testPrintTreeJavadocOption() throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("InputMainExpectedInputJavadocComment.txt"))), StandardCharsets.UTF_8)
            .replaceAll("\\\\r\\\\n", "\\\\n").replaceAll("\r\n", "\n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n")
                            .replaceAll("\r\n", "\n"));
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-j", getPath("InputMainJavadocComment.javadoc"));
    }

    @Test
    public void testPrintSuppressionOption() throws Exception {
        final String expected = "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]"
                + EOL
                + "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/MODIFIERS" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/LITERAL_CLASS"
                + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main(getPath("InputMainSuppressionsStringPrinter.java"),
                "-s", "3:1");
    }

    @Test
    public void testPrintSuppressionAndTabWidthOption() throws Exception {
        final String expected = "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]"
                + "/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST/VARIABLE_DEF[./IDENT[@text='var']]"
                + EOL
                + "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST/VARIABLE_DEF[./IDENT[@text='var']]"
                + "/MODIFIERS" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST/VARIABLE_DEF[./IDENT[@text='var']]"
                + "/TYPE" + EOL
                + "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST/VARIABLE_DEF[./IDENT[@text='var']]"
                + "/TYPE/LITERAL_INT"
                + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main(getPath("InputMainSuppressionsStringPrinter.java"),
                "-s", "7:9", "--tabWidth", "2");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsC() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-s' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-c", "/google_checks.xml",
                getPath(""), "-s", "2:4");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsP() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-s' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-p", getPath("InputMainMycheckstyle.properties"), "-s", "2:4", getPath(""));
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsF() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-s' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-f", "plain", "-s", "2:4", getPath(""));
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsO() throws Exception {
        final File file = temporaryFolder.newFile("file.output");

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-s' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-o", file.getCanonicalPath(), "-s", "2:4", getPath(""));
    }

    @Test
    public void testPrintSuppressionOnMoreThanOneFile() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Printing xpath suppressions is allowed for "
                    + "only one file."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-s", "2:4", getPath(""), getPath(""));
    }

    @Test
    public void testGenerateXpathSuppressionOptionOne() throws Exception {
        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">"
                + EOL
                + "<suppressions>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"MissingJavadocMethodCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"LeftCurlyCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainComplexityOverflow.java\"" + EOL
                + "       checks=\"EmptyBlockCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF"
                + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>" + EOL
                + "</suppressions>" + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", "--generate-xpath-suppression",
                getPath("InputMainComplexityOverflow.java"));
    }

    @Test
    public void testGenerateXpathSuppressionOptionTwo() throws Exception {
        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
            + "<!DOCTYPE suppressions PUBLIC" + EOL
            + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
            + "//EN\"" + EOL
            + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">" + EOL
            + "<suppressions>" + EOL
            + "<suppress-xpath" + EOL
            + "       files=\"InputMainGenerateXpathSuppressions.java\"" + EOL
            + "       checks=\"ExplicitInitializationCheck\"" + EOL
            + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
            + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>" + EOL
            + "<suppress-xpath" + EOL
            + "       files=\"InputMainGenerateXpathSuppressions.java\"" + EOL
            + "       checks=\"IllegalThrowsCheck\"" + EOL
            + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/LITERAL_THROWS"
            + "/IDENT[@text='RuntimeException']\"/>" + EOL
            + "<suppress-xpath" + EOL
            + "       files=\"InputMainGenerateXpathSuppressions.java\"" + EOL
            + "       checks=\"NestedForDepthCheck\"" + EOL
            + "       query=\"/CLASS_DEF[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
            + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST"
            + "/LITERAL_FOR/SLIST"
            + "/LITERAL_FOR\"/>" + EOL
            + "</suppressions>" + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressions.java"));
    }

    @Test
    public void testGenerateXpathSuppressionOptionEmptyConfig() throws Exception {
        final String expected = "";

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-empty.xml"), "--generate-xpath-suppression",
                getPath("InputMainComplexityOverflow.java"));
    }

    @Test
    public void testGenerateXpathSuppressionOptionCustomOutput() throws Exception {
        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">"
                + EOL
                + "<suppressions>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainGenerateXpathSuppressionsTabWidth.java\"" + EOL
                + "       checks=\"ExplicitInitializationCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT["
                + "@text='InputMainGenerateXpathSuppressionsTabWidth']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>" + EOL
                + "</suppressions>" + EOL;
        final File file = temporaryFolder.newFile();
        exit.checkAssertionAfterwards(() -> {
            try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
                final String fileContent = br.lines().collect(Collectors.joining(EOL)) + EOL;
                assertEquals("Unexpected output log",
                        expected, fileContent);
                assertEquals("Unexpected system error log",
                        "", systemErr.getLog());
            }
        });
        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "-o", file.getPath(),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
    }

    @Test
    public void testGenerateXpathSuppressionOptionDefaultTabWidth() throws Exception {
        final String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + EOL
                + "<!DOCTYPE suppressions PUBLIC" + EOL
                + "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"" + EOL
                + "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">"
                + EOL
                + "<suppressions>" + EOL
                + "<suppress-xpath" + EOL
                + "       files=\"InputMainGenerateXpathSuppressionsTabWidth.java\"" + EOL
                + "       checks=\"ExplicitInitializationCheck\"" + EOL
                + "       query=\"/CLASS_DEF[./IDENT["
                + "@text='InputMainGenerateXpathSuppressionsTabWidth']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>" + EOL
                + "</suppressions>" + EOL;

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
    }

    @Test
    public void testGenerateXpathSuppressionOptionCustomTabWidth() throws Exception {
        final String expected = "";

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "", systemErr.getLog());
        });
        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                "--tabWidth", "20",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
    }

    @Test
    public void testPrintFullTreeOption() throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("InputMainExpectedInputAstTreeStringPrinterJavadoc.txt"))),
            StandardCharsets.UTF_8).replaceAll("\\\\r\\\\n", "\\\\n")
                .replaceAll("\r\n", "\n");

        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
                    expected, systemOut.getLog().replaceAll("\\\\r\\\\n", "\\\\n")
                            .replaceAll("\r\n", "\n"));
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-J", getPath("InputMainAstTreeStringPrinterJavadoc.java"));
    }

    @Test
    public void testConflictingOptionsTvsC() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-c", "/google_checks.xml", "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsP() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-p", getPath("InputMainMycheckstyle.properties"), "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsF() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-f", "plain", "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsS() throws Exception {
        final File file = temporaryFolder.newFile("file.output");

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-t' cannot be used with other options."
                    + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-s", file.getCanonicalPath(), "-t", getPath(""));
    }

    @Test
    public void testConflictingOptionsTvsO() throws Exception {
        final File file = temporaryFolder.newFile("file.output");

        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });

        Main.main("-o", file.getCanonicalPath(), "-t", getPath(""));
    }

    @Test
    public void testDebugOption() throws Exception {
        exit.checkAssertionAfterwards(
            () -> assertNotEquals("Unexpected system error log", "", systemErr.getLog()));
        Main.main("-c", "/google_checks.xml", getPath("InputMain.java"), "-d");
    }

    @Test
    public void testExcludeOption() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath(""), "-e", getFilePath(""));
    }

    @Test
    public void testExcludeOptionFile() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath("InputMain.java"), "-e",
                getFilePath("InputMain.java"));
    }

    @Test
    public void testExcludeRegexpOption() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected output log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath(""), "-x", ".");
    }

    @Test
    public void testExcludeRegexpOptionFile() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Files to process must be specified, found 0."
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected output log", "", systemErr.getLog());
        });
        Main.main("-c", "/google_checks.xml", getFilePath("InputMain.java"), "-x", ".");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExcludeDirectoryNotMatch() throws Exception {
        final Class<?> optionsClass = Class.forName(Main.class.getName());
        final Method method = optionsClass.getDeclaredMethod("listFiles", File.class, List.class);
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
            assertEquals("Unexpected output log", "", systemOut.getLog());
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
            final LocalizedMessage unableToInstantiateExceptionMessage = new LocalizedMessage(1,
                    Definitions.CHECKSTYLE_BUNDLE,
                    "PackageObjectFactory.unableToInstantiateExceptionMessage",
                    new String[] {"TestRootModuleChecker", checkstylePackage
                            + "TestRootModuleChecker, "
                            + "TestRootModuleCheckerCheck, " + checkstylePackage
                            + "TestRootModuleCheckerCheck"},
                    null, getClass(), null);
            assertTrue("Unexpected system error log",
                    systemErr.getLog().startsWith(checkstylePackage + "api.CheckstyleException: "
                    + unableToInstantiateExceptionMessage.getMessage()));
            assertFalse("Invalid checker state", TestRootModuleChecker.isProcessed());
        });
        Main.main("-c", getPath("InputMainConfig-custom-simple-root-module.xml"),
                getPath("InputMain.java"));
    }

    @Test
    public void testExceptionOnExecuteIgnoredModuleWithUnknownModuleName() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(cause));
        });

        Main.main("-c", getPath("InputMainConfig-non-existent-classname-ignore.xml"),
                "--executeIgnoredModules",
                getPath("InputMain.java"));
    }

    @Test
    public void testExceptionOnExecuteIgnoredModuleWithBadPropertyValue() throws Exception {
        exit.expectSystemExitWithStatus(-2);
        exit.checkAssertionAfterwards(() -> {
            final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                    + " cannot initialize module TreeWalker - ";
            final String causeDetail = "it is not a boolean";
            assertTrue("Unexpected system error log", systemErr.getLog().startsWith(cause));
            assertTrue("Unexpected system error log", systemErr.getLog().contains(causeDetail));
        });

        Main.main("-c", getPath("InputMainConfig-TypeName-bad-value.xml"),
                "--executeIgnoredModules",
                getPath("InputMain.java"));
    }

    @Test
    public void testNoProblemOnExecuteIgnoredModuleWithBadPropertyValue() throws Exception {
        exit.checkAssertionAfterwards(() -> {
            assertTrue("Unexpected system error log", systemErr.getLog().isEmpty());
        });

        Main.main("-c", getPath("InputMainConfig-TypeName-bad-value.xml"),
                "",
                getPath("InputMain.java"));
    }

    @Test
    public void testInvalidCheckerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "Invalid value for option '--checker-threads-number': 'invalid' is not an int"
                    + EOL + SHORT_USAGE, systemErr.getLog());
        });
        Main.main("-C", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testInvalidTreeWalkerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log",
                    "Invalid value for option '--tree-walker-threads-number': "
                    + "'invalid' is not an int" + EOL + SHORT_USAGE, systemErr.getLog());
        });
        Main.main("-W", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testZeroCheckerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log", "Checker threads number must be greater than zero"
                + System.lineSeparator(), systemOut.getLog());
            assertEquals("Unexpected system error log", "", systemErr.getLog());
        });
        Main.main("-C", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
    }

    @Test
    public void testZeroTreeWalkerThreadsNumber() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            assertEquals("Unexpected output log",
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
            assertEquals("Unexpected output log", "", systemOut.getLog());
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
            assertEquals("Unexpected output log", "", systemOut.getLog());
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
            assertEquals("Unexpected output log", "", systemOut.getLog());
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
            assertEquals("Invalid checker children name", "TreeWalker", treeWalkerConfig.getName());
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

    @Test
    public void testMissingFiles() throws Exception {
        exit.expectSystemExitWithStatus(-1);
        exit.checkAssertionAfterwards(() -> {
            final String usage = "Missing required parameter: <files>" + EOL + SHORT_USAGE;
            assertEquals("Unexpected output log", "", systemOut.getLog());
            assertEquals("Unexpected system error log", usage, systemErr.getLog());
        });
        Main.main();
    }

    @Test
    public void testOutputFormatToStringLowercase() {
        assertEquals("expected xml", "xml", Main.OutputFormat.XML.toString());
        assertEquals("expected plain", "plain", Main.OutputFormat.PLAIN.toString());
    }

    @Test
    public void testXmlOutputFormatCreateListener() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.XML.createListener(out,
                AutomaticBean.OutputStreamOptions.CLOSE);
        assertTrue("listener is XMLLogger", listener instanceof XMLLogger);
    }

    @Test
    public void testPlainOutputFormatCreateListener() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.PLAIN.createListener(out,
                AutomaticBean.OutputStreamOptions.CLOSE);
        assertTrue("listener is DefaultLogger", listener instanceof DefaultLogger);
    }

}
