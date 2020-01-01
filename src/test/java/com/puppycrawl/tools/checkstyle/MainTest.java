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

import static com.puppycrawl.tools.checkstyle.AbstractPathTestSupport.addEndOfLine;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.itsallcode.junit.sysextensions.AssertExit.assertExitWithStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.ExitGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestRootModuleChecker;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

@ExtendWith({ExitGuard.class, SystemErrGuard.class, SystemOutGuard.class})
public class MainTest {

    private static final String SHORT_USAGE = String.format(Locale.ROOT,
            "Usage: checkstyle [OPTIONS]... FILES...%n"
            + "Try 'checkstyle --help' for more information.%n");

    private static final String USAGE = String.format(Locale.ROOT,
          "Usage: checkstyle [-dEghjJtTV] [-b=<xpath>] [-c=<configurationFile>]"
          + " [-C=<checkerThreadsNumber>]%n"
          + "                  [-f=<format>] [-o=<outputPath>] [-p=<propertiesFile>]%n"
          + "                  [-s=<suppressionLineColumnNumber>] [-w=<tabWidth>]"
          + " [-W=<treeWalkerThreadsNumber>]%n"
          + "                  [-e=<exclude>]... [-x=<excludeRegex>]..."
          + " <files>...%n"
          + "Checkstyle verifies that the specified source code files adhere to the specified"
          + " rules. By default%n"
          + "violations are reported to standard out in plain format. Checkstyle requires a"
          + " configuration XML%n"
          + "file that configures the checks to apply.%n"
          + "      <files>...            One or more source files to verify%n"
          + "  -b, --branch-matching-xpath=<xpath>%n"
          + "                            Show Abstract Syntax Tree(AST) branches that match XPath%n"
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

    private static final String EOL = System.lineSeparator();

    @TempDir
    public File temporaryFolder;

    private final LocalizedMessage auditStartMessage = new LocalizedMessage(1,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted", null, null,
            getClass(), null);

    private final LocalizedMessage auditFinishMessage = new LocalizedMessage(1,
            Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished", null, null,
            getClass(), null);

    private final String noViolationsOutput = auditStartMessage.getMessage() + EOL
                    + auditFinishMessage.getMessage() + EOL;

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/main/" + filename;
    }

    private static String getNonCompilablePath(String filename) {
        return "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/main/" + filename;
    }

    private static String getFilePath(String filename) throws IOException {
        return new File(getPath(filename)).getCanonicalPath();
    }

    /**
     * <p>Configures the environment for each test.</p>
     * <ul>
     * <li>Restore original logging level and HANDLERS to prevent bleeding into other tests;</li>
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
        assertTrue(
                isUtilsClassHasPrivateConstructor(Main.class, false), "Constructor is not private");
    }

    @Test
    public void testVersionPrint(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        Main.main("-V");
        assertEquals("Checkstyle version: null" + System.lineSeparator(),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testUsageHelpPrint(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        Main.main("-h");
        assertEquals(USAGE, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testWrongArgument(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        // need to specify a file:
        // <files> is defined as a required positional param;
        // picocli verifies required parameters before checking unknown options
        assertExitWithStatus(-1, () -> invokeMain("-q", "file"));
        final String usage = "Unknown option: '-q'" + EOL + SHORT_USAGE;
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(usage, systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testWrongArgumentMissingFiles(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain("-q"));
        // files is defined as a required positional param;
        // picocli verifies required parameters before checking unknown options
        final String usage = "Missing required parameter: <files>" + EOL + SHORT_USAGE;
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(usage, systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testNoConfigSpecified(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain(getPath("InputMain.java")));
        assertEquals("Must specify a config XML file." + System.lineSeparator(),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testNonExistentTargetFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", "NonExistentFile.java");
        });
        assertEquals("Files to process must be specified, found 0."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFileButWithoutReadAccess(
            @SysErr Capturable systemErr, @SysOut Capturable systemOut) throws IOException {
        final File file = File.createTempFile(
                "testExistingTargetFileButWithoutReadAccess", null, temporaryFolder);
        // skip execution if file is still readable, it is possible on some windows machines
        // see https://github.com/checkstyle/checkstyle/issues/7032 for details
        assumeTrue(file.setReadable(false), "file is still readable");

        final String canonicalPath = file.getCanonicalPath();
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", canonicalPath);
        });
        assertEquals("Files to process must be specified, found 0."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testNonExistentConfigFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "src/main/resources/non_existent_config.xml",
                    getPath("InputMain.java"));
        });
        assertEquals(addEndOfLine("Could not find config XML file "
                    + "'src/main/resources/non_existent_config.xml'."),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testNonExistentOutputFormat(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", "-f", "xmlp", getPath("InputMain.java"));
        });
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("Invalid value for option '-f': expected one of [XML, PLAIN]"
                    + " (case-insensitive) but was 'xmlp'" + EOL + SHORT_USAGE,
                systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testNonExistentClass(@SysErr Capturable systemErr) {
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-non-existent-classname.xml"),
                    getPath("InputMain.java"));
        });
        final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                + " cannot initialize module TreeWalker - ";
        assertTrue(systemErr.getCapturedData().startsWith(cause), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFile(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        Main.main("-c", getPath("InputMainConfig-classname.xml"), getPath("InputMain.java"));
        assertEquals(addEndOfLine(auditStartMessage.getMessage(), auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFileXmlOutput(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        Main.main("-c", getPath("InputMainConfig-classname.xml"), "-f", "xml",
                getPath("InputMain.java"));
        final String expectedPath = getFilePath("InputMain.java");
        final String version = Main.class.getPackage().getImplementationVersion();
        assertEquals(addEndOfLine(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<checkstyle version=\"" + version + "\">",
                "<file name=\"" + expectedPath + "\">",
                "</file>",
                "</checkstyle>"), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    /**
     * This test method is created only to cover
     * pitest mutation survival at Main#getOutputStreamOptions.
     * No ability to test it by out general tests because
     * Main does not produce any output to System.out after report is generated,
     * System.out and System.err should be non-closed streams
     *
     * @throws Exception if there is an error.
     * @noinspection UseOfSystemOutOrSystemErr
     */
    @Test
    public void testNonClosedSystemStreams() throws Exception {
        Main.main("-c", getPath("InputMainConfig-classname.xml"), "-f", "xml",
                getPath("InputMain.java"));

        final Boolean closedOut = (Boolean) TestUtil
                .getClassDeclaredField(System.out.getClass(), "closing").get(System.out);
        assertThat("System.out stream should not be closed", closedOut, is(false));
        final Boolean closedErr = (Boolean) TestUtil
                .getClassDeclaredField(System.err.getClass(), "closing").get(System.err);
        assertThat("System.err stream should not be closed", closedErr, is(false));
    }

    /**
     * This test method is created only to cover
     * pitest mutation survival at Main#getOutputStreamOptions.
     * No ability to test it by out general tests.
     * It is hard test that inner stream is closed, so pure UT is created to validate result
     * of private method Main.getOutputStreamOptions
     *
     * @throws Exception if there is an error.
     */
    @Test
    public void testGetOutputStreamOptionsMethod() throws Exception {
        final Path path = new File(getPath("InputMain.java")).toPath();
        final AutomaticBean.OutputStreamOptions option =
                (AutomaticBean.OutputStreamOptions) TestUtil
                    .getClassDeclaredMethod(Main.class, "getOutputStreamOptions")
                    .invoke(null, path);
        assertThat("Main.getOutputStreamOptions return CLOSE on not null Path",
                option, is(AutomaticBean.OutputStreamOptions.CLOSE));
    }

    @Test
    public void testExistingTargetFilePlainOutput(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        Main.main("-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                getPath("InputMain.java"));
        assertEquals(addEndOfLine(auditStartMessage.getMessage(), auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFileWithViolations(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        Main.main("-c", getPath("InputMainConfig-classname2.xml"), getPath("InputMain.java"));
        final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                null, getClass(), null);
        final LocalizedMessage invalidPatternMessageMainInner = new LocalizedMessage(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain.java");
        assertEquals(
                addEndOfLine(auditStartMessage.getMessage(),
                    "[WARN] " + expectedPath + ":3:14: "
                        + invalidPatternMessageMain.getMessage()
                        + " [TypeName]",
                    "[WARN] " + expectedPath + ":5:7: "
                        + invalidPatternMessageMainInner.getMessage()
                        + " [TypeName]",
                    auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testViolationsByGoogleAndXpathSuppressions(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        System.setProperty("org.checkstyle.google.suppressionxpathfilter.config",
                getPath("InputMainViolationsForGoogleXpathSuppressions.xml"));
        Main.main("-c", "/google_checks.xml",
                getPath("InputMainViolationsForGoogle.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(noViolationsOutput));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testViolationsByGoogleAndSuppressions(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        System.setProperty("org.checkstyle.google.suppressionfilter.config",
                getPath("InputMainViolationsForGoogleSuppressions.xml"));
        Main.main("-c", "/google_checks.xml",
                getPath("InputMainViolationsForGoogle.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(noViolationsOutput));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testExistingTargetFileWithError(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        assertExitWithStatus(2, () -> {
            invokeMain("-c", getPath("InputMainConfig-classname2-error.xml"),
                    getPath("InputMain.java"));
        });
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
        assertEquals(
                addEndOfLine(auditStartMessage.getMessage(),
                    "[ERROR] " + expectedPath + ":3:14: "
                        + invalidPatternMessageMain.getMessage() + " [TypeName]",
                    "[ERROR] " + expectedPath + ":5:7: "
                        + invalidPatternMessageMainInner.getMessage() + " [TypeName]",
                    auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(addEndOfLine(errorCounterTwoMessage.getMessage()),
                systemErr.getCapturedData(), "Unexpected system error log");
    }

    /**
     * Similar test to {@link #testExistingTargetFileWithError}, but for PIT mutation tests:
     * this test fails if the boundary condition is changed from {@code if (exitStatus > 0)}
     * to {@code if (exitStatus > 1)}.
     * @throws Exception should not throw anything
     */
    @Test
    public void testExistingTargetFileWithOneError(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        assertExitWithStatus(1, () -> {
            invokeMain("-c", getPath("InputMainConfig-classname2-error.xml"),
                    getPath("InputMain1.java"));
        });
        final LocalizedMessage errorCounterTwoMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                new String[] {String.valueOf(1)}, null, getClass(), null);
        final LocalizedMessage invalidPatternMessageMain = new LocalizedMessage(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMain1", "^[a-z0-9]*$"},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain1.java");
        assertEquals(
                addEndOfLine(auditStartMessage.getMessage(),
                    "[ERROR] " + expectedPath + ":3:14: "
                        + invalidPatternMessageMain.getMessage() + " [TypeName]",
                    auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(addEndOfLine(errorCounterTwoMessage.getMessage()),
                systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFileWithOneErrorAgainstSunCheck(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        assertExitWithStatus(1, () -> {
            invokeMain("-c", "/sun_checks.xml", getPath("InputMain1.java"));
        });
        final LocalizedMessage errorCounterTwoMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                new String[] {String.valueOf(1)}, null, getClass(), null);
        final LocalizedMessage message = new LocalizedMessage(1,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                "javadoc.packageInfo", new String[] {},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain1.java");
        assertEquals(addEndOfLine(auditStartMessage.getMessage(),
                "[ERROR] " + expectedPath + ":1: " + message.getMessage() + " [JavadocPackage]",
                auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(addEndOfLine(errorCounterTwoMessage.getMessage()),
                systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistentTargetFilePlainOutputToNonExistentFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        Main.main("-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                "-o", temporaryFolder + "/output.txt", getPath("InputMain.java"));
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFilePlainOutputToFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        final String outputFile =
                File.createTempFile("file", ".output", temporaryFolder).getCanonicalPath();
        assertTrue(new File(outputFile).exists(), "File must exist");
        Main.main("-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                "-o", outputFile, getPath("InputMain.java"));
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testCreateNonExistentOutputFile() throws IOException {
        final String outputFile = new File(temporaryFolder, "nonexistent.out").getCanonicalPath();
        assertFalse(new File(outputFile).exists(), "File must not exist");
        Main.main("-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                "-o", outputFile, getPath("InputMain.java"));
        assertTrue(new File(outputFile).exists(), "File must exist");
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        Main.main("-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", getPath("InputMainMycheckstyle.properties"), getPath("InputMain.java"));
        assertEquals(addEndOfLine(auditStartMessage.getMessage(), auditFinishMessage.getMessage()),
                systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistentProperties(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", getPath("InputMainConfig-classname-prop.xml"),
                    "-p", "nonexistent.properties", getPath("InputMain.java"));
        });
        assertEquals("Could not find file 'nonexistent.properties'."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExistingIncorrectConfigFile(@SysErr Capturable systemErr) {
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-Incorrect.xml"), getPath("InputMain.java"));
        });
        final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
            + "CheckstyleException: unable to parse configuration stream - ";
        assertTrue(systemErr.getCapturedData().startsWith(errorOutput),
                "Unexpected system error log");
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile(@SysErr Capturable systemErr) {
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-incorrectChildren.xml"),
                    getPath("InputMain.java"));
        });
        final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: cannot initialize module RegexpSingleline"
                + " - RegexpSingleline is not allowed as a child in RegexpSingleline";
        assertTrue(systemErr.getCapturedData().startsWith(errorOutput),
                "Unexpected system error log");
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile2(@SysErr Capturable systemErr) {
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-incorrectChildren2.xml"),
                    getPath("InputMain.java"));
        });
        final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: cannot initialize module TreeWalker - "
                + "cannot initialize module JavadocMethod - "
                + "JavadocVariable is not allowed as a child in JavadocMethod";
        assertTrue(systemErr.getCapturedData().startsWith(errorOutput),
                "Unexpected system error log");
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
            assertTrue(
                    ex.getCause() instanceof CheckstyleException, "Invalid error cause");
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
            assertTrue(samePrefix || sameSuffix, "Invalid error message");
            assertTrue(causeMessage.contains(".'"), "Invalid error message");
        }
    }

    @Test
    public void testExistingDirectoryWithViolations(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        // we just reference there all violations
        final String[][] outputValues = {
                {"InputMainComplexityOverflow", "1", "172"},
        };

        final int allowedLength = 170;
        final String msgKey = "maxLen.file";
        final String bundle = "com.puppycrawl.tools.checkstyle.checks.sizes.messages";

        Main.main("-c", getPath("InputMainConfig-filelength.xml"),
                getPath(""));
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
        assertEquals(sb.toString(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
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
        assertEquals(0, result.size(), "Invalid result size");
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
        assertEquals(0, result.size(), "Invalid result size");
    }

    @Test
    public void testFileReferenceDuringException(@SysErr Capturable systemErr) {
        // We put xml as source to cause parse exception
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-classname.xml"),
                    getNonCompilablePath("InputMainIncorrectClass.java"));
        });
        final String exceptionFirstLine = addEndOfLine("com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: Exception was thrown while processing "
                + new File(getNonCompilablePath("InputMainIncorrectClass.java")).getPath());
        assertTrue(systemErr.getCapturedData().startsWith(exceptionFirstLine),
                "Unexpected system error log");
    }

    @Test
    public void testPrintTreeOnMoreThanOneFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain("-t", getPath("")));
        assertEquals("Printing AST is allowed for only one file."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintTreeOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String expected = addEndOfLine(
            "PACKAGE_DEF -> package [1:0]",
            "|--ANNOTATIONS -> ANNOTATIONS [1:39]",
            "|--DOT -> . [1:39]",
            "|   |--DOT -> . [1:28]",
            "|   |   |--DOT -> . [1:22]",
            "|   |   |   |--DOT -> . [1:11]",
            "|   |   |   |   |--IDENT -> com [1:8]",
            "|   |   |   |   `--IDENT -> puppycrawl [1:12]",
            "|   |   |   `--IDENT -> tools [1:23]",
            "|   |   `--IDENT -> checkstyle [1:29]",
            "|   `--IDENT -> main [1:40]",
            "`--SEMI -> ; [1:44]",
            "CLASS_DEF -> CLASS_DEF [3:0]",
            "|--MODIFIERS -> MODIFIERS [3:0]",
            "|   `--LITERAL_PUBLIC -> public [3:0]",
            "|--LITERAL_CLASS -> class [3:7]",
            "|--IDENT -> InputMain [3:13]",
            "`--OBJBLOCK -> OBJBLOCK [3:23]",
            "    |--LCURLY -> { [3:23]",
            "    `--RCURLY -> } [4:0]",
            "CLASS_DEF -> CLASS_DEF [5:0]",
            "|--MODIFIERS -> MODIFIERS [5:0]",
            "|--LITERAL_CLASS -> class [5:0]",
            "|--IDENT -> InputMainInner [5:6]",
            "`--OBJBLOCK -> OBJBLOCK [5:21]",
            "    |--LCURLY -> { [5:21]",
            "    `--RCURLY -> } [6:0]");

        Main.main("-t", getPath("InputMain.java"));
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintXpathOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [3:0]",
            "`--OBJBLOCK -> OBJBLOCK [3:28]",
            "    |--METHOD_DEF -> METHOD_DEF [4:4]",
            "    |   `--SLIST -> { [4:20]",
            "    |       |--VARIABLE_DEF -> VARIABLE_DEF [5:8]",
            "    |       |   |--IDENT -> a [5:12]");
        Main.main("-b", "/CLASS_DEF//METHOD_DEF[./IDENT[@text='methodOne']]//VARIABLE_DEF/IDENT",
                getPath("InputMainXPath.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(expected));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testPrintXpathCommentNode(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [17:0]",
            "`--OBJBLOCK -> OBJBLOCK [17:19]",
            "    |--CTOR_DEF -> CTOR_DEF [19:4]",
            "    |   |--BLOCK_COMMENT_BEGIN -> /* [18:4]");
        Main.main("-b", "/CLASS_DEF//BLOCK_COMMENT_BEGIN", getPath("InputMainXPath.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(expected));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testPrintXpathNodeParentNull(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine("PACKAGE_DEF -> package [1:0]");
        Main.main("-b", "/PACKAGE_DEF", getPath("InputMainXPath.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(expected));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testPrintXpathFullOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [3:0]",
            "`--OBJBLOCK -> OBJBLOCK [3:28]",
            "    |--METHOD_DEF -> METHOD_DEF [8:4]",
            "    |   `--SLIST -> { [8:26]",
            "    |       |--VARIABLE_DEF -> VARIABLE_DEF [9:8]",
            "    |       |   |--IDENT -> a [9:12]");
        final String xpath = "/CLASS_DEF//METHOD_DEF[./IDENT[@text='method']]//VARIABLE_DEF/IDENT";
        Main.main("--branch-matching-xpath", xpath, getPath("InputMainXPath.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(expected));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testPrintXpathTwoResults(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String expected = addEndOfLine(
            "CLASS_DEF -> CLASS_DEF [12:0]",
            "`--OBJBLOCK -> OBJBLOCK [12:10]",
            "    |--METHOD_DEF -> METHOD_DEF [13:4]",
            "---------",
            "CLASS_DEF -> CLASS_DEF [12:0]",
            "`--OBJBLOCK -> OBJBLOCK [12:10]",
            "    |--METHOD_DEF -> METHOD_DEF [14:4]");
        Main.main("--branch-matching-xpath", "/CLASS_DEF[./IDENT[@text='Two']]//METHOD_DEF",
                getPath("InputMainXPath.java"));
        assertThat("Unexpected output log", systemOut.getCapturedData(), is(expected));
        assertThat("Unexpected system error log", systemErr.getCapturedData(), is(""));
    }

    @Test
    public void testPrintXpathInvalidXpath(@SysErr Capturable systemErr) throws Exception {
        final String invalidXpath = "\\/CLASS_DEF[./IDENT[@text='Two']]//METHOD_DEF";
        final String filePath = getFilePath("InputMainXPath.java");
        assertExitWithStatus(-2, () -> {
            invokeMain("--branch-matching-xpath", invalidXpath, filePath);
        });
        final String exceptionFirstLine = addEndOfLine("com.puppycrawl.tools.checkstyle.api."
            + "CheckstyleException: Error during evaluation for xpath: " + invalidXpath
            + ", file: " + filePath);
        assertThat("Unexpected system error log",
            systemErr.getCapturedData().startsWith(exceptionFirstLine), is(true));
    }

    @Test
    public void testPrintTreeCommentsOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "PACKAGE_DEF -> package [1:0]",
            "|--ANNOTATIONS -> ANNOTATIONS [1:39]",
            "|--DOT -> . [1:39]",
            "|   |--DOT -> . [1:28]",
            "|   |   |--DOT -> . [1:22]",
            "|   |   |   |--DOT -> . [1:11]",
            "|   |   |   |   |--IDENT -> com [1:8]",
            "|   |   |   |   `--IDENT -> puppycrawl [1:12]",
            "|   |   |   `--IDENT -> tools [1:23]",
            "|   |   `--IDENT -> checkstyle [1:29]",
            "|   `--IDENT -> main [1:40]",
            "`--SEMI -> ; [1:44]",
            "CLASS_DEF -> CLASS_DEF [3:0]",
            "|--MODIFIERS -> MODIFIERS [3:0]",
            "|   |--BLOCK_COMMENT_BEGIN -> /* [2:0]",
            "|   |   |--COMMENT_CONTENT -> comment [2:2]",
            "|   |   `--BLOCK_COMMENT_END -> */ [2:8]",
            "|   `--LITERAL_PUBLIC -> public [3:0]",
            "|--LITERAL_CLASS -> class [3:7]",
            "|--IDENT -> InputMain [3:13]",
            "`--OBJBLOCK -> OBJBLOCK [3:23]",
            "    |--LCURLY -> { [3:23]",
            "    `--RCURLY -> } [4:0]",
            "CLASS_DEF -> CLASS_DEF [5:0]",
            "|--MODIFIERS -> MODIFIERS [5:0]",
            "|--LITERAL_CLASS -> class [5:0]",
            "|--IDENT -> InputMainInner [5:6]",
            "`--OBJBLOCK -> OBJBLOCK [5:21]",
            "    |--LCURLY -> { [5:21]",
            "    `--RCURLY -> } [6:0]");

        Main.main("-T", getPath("InputMain.java"));
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintTreeJavadocOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("InputMainExpectedInputJavadocComment.txt"))), StandardCharsets.UTF_8)
            .replaceAll("\\\\r\\\\n", "\\\\n").replaceAll("\r\n", "\n");

        Main.main("-j", getPath("InputMainJavadocComment.javadoc"));
        assertEquals(expected, systemOut.getCapturedData().replaceAll("\\\\r\\\\n", "\\\\n")
                        .replaceAll("\r\n", "\n"), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]",
                "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/LITERAL_CLASS");

        Main.main(getPath("InputMainSuppressionsStringPrinter.java"), "-s", "3:1");
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionAndTabWidthOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]"
                    + "/SLIST/VARIABLE_DEF[./IDENT[@text='var']]",
                "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST"
                    + "/VARIABLE_DEF[./IDENT[@text='var']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST"
                    + "/VARIABLE_DEF[./IDENT[@text='var']]/TYPE",
                "/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST"
                    + "/VARIABLE_DEF[./IDENT[@text='var']]/TYPE/LITERAL_INT");

        Main.main(getPath("InputMainSuppressionsStringPrinter.java"),
                "-s", "7:9", "--tabWidth", "2");
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsC(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", getPath(""), "-s", "2:4");
        });
        assertEquals("Option '-s' cannot be used with other options."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsP(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-p", getPath("InputMainMycheckstyle.properties"), "-s", "2:4", getPath(""));
        });
        assertEquals("Option '-s' cannot be used with other options."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsF(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain("-f", "plain", "-s", "2:4", getPath("")));
        assertEquals("Option '-s' cannot be used with other options."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsO(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String outputPath = new File(temporaryFolder, "file.output").getCanonicalPath();

        assertExitWithStatus(-1, () -> invokeMain("-o", outputPath, "-s", "2:4", getPath("")));
        assertEquals("Option '-s' cannot be used with other options."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintSuppressionOnMoreThanOneFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain("-s", "2:4", getPath(""), getPath("")));
        assertEquals("Printing xpath suppressions is allowed for only one file."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testGenerateXpathSuppressionOptionOne(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<!DOCTYPE suppressions PUBLIC",
                "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                    + "//EN\"",
                "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">",
                "<suppressions>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"MissingJavadocMethodCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"LeftCurlyCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       checks=\"EmptyBlockCheck\"",
                "       query=\"/CLASS_DEF[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST/LITERAL_IF"
                    + "/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST"
                    + "/LITERAL_IF/SLIST/LITERAL_IF/SLIST/LITERAL_IF/SLIST\"/>",
                "</suppressions>");

        Main.main("-c", "/google_checks.xml", "--generate-xpath-suppression",
                getPath("InputMainComplexityOverflow.java"));
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testGenerateXpathSuppressionOptionTwo(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
            "<!DOCTYPE suppressions PUBLIC",
            "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                + "//EN\"",
            "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">",
            "<suppressions>",
            "<suppress-xpath",
            "       files=\"InputMainGenerateXpathSuppressions.java\"",
            "       checks=\"ExplicitInitializationCheck\"",
            "       query=\"/CLASS_DEF[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>",
            "<suppress-xpath",
            "       files=\"InputMainGenerateXpathSuppressions.java\"",
            "       checks=\"IllegalThrowsCheck\"",
            "       query=\"/CLASS_DEF[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/LITERAL_THROWS"
                + "/IDENT[@text='RuntimeException']\"/>",
            "<suppress-xpath",
            "       files=\"InputMainGenerateXpathSuppressions.java\"",
            "       checks=\"NestedForDepthCheck\"",
            "       query=\"/CLASS_DEF[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST"
                + "/LITERAL_FOR/SLIST/LITERAL_FOR\"/>",
            "</suppressions>");

        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressions.java"));
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testGenerateXpathSuppressionOptionEmptyConfig(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = "";

        Main.main("-c", getPath("InputMainConfig-empty.xml"), "--generate-xpath-suppression",
                getPath("InputMainComplexityOverflow.java"));
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testGenerateXpathSuppressionOptionCustomOutput(@SysErr Capturable systemErr)
            throws IOException {
        final String expected = addEndOfLine(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<!DOCTYPE suppressions PUBLIC",
                "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                    + "//EN\"",
                "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">",
                "<suppressions>",
                "<suppress-xpath",
                "       files=\"InputMainGenerateXpathSuppressionsTabWidth.java\"",
                "       checks=\"ExplicitInitializationCheck\"",
                "       query=\"/CLASS_DEF[./IDENT["
                    + "@text='InputMainGenerateXpathSuppressionsTabWidth']]"
                    + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>",
                "</suppressions>");
        final File file = new File(temporaryFolder, "file.output");
        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"), "-o", file.getPath(),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            final String fileContent = br.lines().collect(Collectors.joining(EOL, "", EOL));
            assertEquals(expected, fileContent, "Unexpected output log");
            assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
        }
    }

    @Test
    public void testGenerateXpathSuppressionOptionDefaultTabWidth(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = addEndOfLine(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<!DOCTYPE suppressions PUBLIC",
                "    \"-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2"
                    + "//EN\"",
                "    \"https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd\">",
                "<suppressions>",
                "<suppress-xpath",
                "       files=\"InputMainGenerateXpathSuppressionsTabWidth.java\"",
                "       checks=\"ExplicitInitializationCheck\"",
                "       query=\"/CLASS_DEF[./IDENT["
                    + "@text='InputMainGenerateXpathSuppressionsTabWidth']]"
                    + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>",
                "</suppressions>");

        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
        assertEquals(
                expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(
                "", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testGenerateXpathSuppressionOptionCustomTabWidth(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = "";

        Main.main("-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression", "--tabWidth", "20",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
        assertEquals(expected, systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testPrintFullTreeOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("InputMainExpectedInputAstTreeStringPrinterJavadoc.txt"))),
            StandardCharsets.UTF_8).replaceAll("\\\\r\\\\n", "\\\\n")
                .replaceAll("\r\n", "\n");

        Main.main("-J", getPath("InputMainAstTreeStringPrinterJavadoc.java"));
        assertEquals(expected, systemOut.getCapturedData().replaceAll("\\\\r\\\\n", "\\\\n")
                        .replaceAll("\r\n", "\n"), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testConflictingOptionsTvsC(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain("-c", "/google_checks.xml", "-t", getPath("")));
        assertEquals("Option '-t' cannot be used with other options."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testConflictingOptionsTvsP(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-p", getPath("InputMainMycheckstyle.properties"), "-t", getPath(""));
        });
        assertEquals("Option '-t' cannot be used with other options."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testConflictingOptionsTvsF(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> invokeMain("-f", "plain", "-t", getPath("")));
        assertEquals("Option '-t' cannot be used with other options."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testConflictingOptionsTvsS(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String outputPath = new File(temporaryFolder, "file.output").getCanonicalPath();

        assertExitWithStatus(-1, () -> invokeMain("-s", outputPath, "-t", getPath("")));
        assertEquals("Option '-t' cannot be used with other options."
                + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testConflictingOptionsTvsO(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String outputPath = new File(temporaryFolder, "file.output").getCanonicalPath();

        assertExitWithStatus(-1, () -> invokeMain("-o", outputPath, "-t", getPath("")));
        assertEquals("Option '-t' cannot be used with other options."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testDebugOption(@SysErr Capturable systemErr) throws IOException {
        Main.main("-c", "/google_checks.xml", getPath("InputMain.java"), "-d");
        assertNotEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExcludeOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String filePath = getFilePath("");
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", filePath, "-e", filePath);
        });
        assertEquals("Files to process must be specified, found 0."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExcludeOptionFile(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String filePath = getFilePath("InputMain.java");
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", filePath, "-e", filePath);
        });
        assertEquals("Files to process must be specified, found 0."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testExcludeRegexpOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String filePath = getFilePath("");
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", filePath, "-x", ".");
        });
        assertEquals("Files to process must be specified, found 0."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected output log");
    }

    @Test
    public void testExcludeRegexpOptionFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String filePath = getFilePath("InputMain.java");
        assertExitWithStatus(-1, () -> {
            invokeMain("-c", "/google_checks.xml", filePath, "-x", ".");
        });
        assertEquals("Files to process must be specified, found 0."
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected output log");
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
        assertNotEquals(0, result.size(), "Invalid result size");
    }

    @Test
    public void testCustomRootModule(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        TestRootModuleChecker.reset();

        Main.main("-c", getPath("InputMainConfig-custom-root-module.xml"),
                getPath("InputMain.java"));
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
        assertTrue(TestRootModuleChecker.isProcessed(), "Invalid Checker state");
        assertTrue(TestRootModuleChecker.isDestroyed(), "RootModule should be destroyed");
    }

    @Test
    public void testCustomSimpleRootModule(@SysErr Capturable systemErr) {
        TestRootModuleChecker.reset();
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-custom-simple-root-module.xml"),
                getPath("InputMain.java"));
        });
        final String checkstylePackage = "com.puppycrawl.tools.checkstyle.";
        final LocalizedMessage unableToInstantiateExceptionMessage = new LocalizedMessage(1,
                Definitions.CHECKSTYLE_BUNDLE,
                "PackageObjectFactory.unableToInstantiateExceptionMessage",
                new String[] {"TestRootModuleChecker", checkstylePackage
                        + "TestRootModuleChecker, "
                        + "TestRootModuleCheckerCheck, " + checkstylePackage
                        + "TestRootModuleCheckerCheck"},
                null, getClass(), null);
        assertTrue(systemErr.getCapturedData().startsWith(checkstylePackage
                + "api.CheckstyleException: " + unableToInstantiateExceptionMessage.getMessage()),
                "Unexpected system error log");
        assertFalse(TestRootModuleChecker.isProcessed(), "Invalid checker state");
    }

    @Test
    public void testExceptionOnExecuteIgnoredModuleWithUnknownModuleName(
            @SysErr Capturable systemErr) {
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-non-existent-classname-ignore.xml"),
                    "--executeIgnoredModules", getPath("InputMain.java"));
        });
        final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                + " cannot initialize module TreeWalker - ";
        assertTrue(systemErr.getCapturedData().startsWith(cause), "Unexpected system error log");
    }

    @Test
    public void testExceptionOnExecuteIgnoredModuleWithBadPropertyValue(
            @SysErr Capturable systemErr) {
        assertExitWithStatus(-2, () -> {
            invokeMain("-c", getPath("InputMainConfig-TypeName-bad-value.xml"),
                    "--executeIgnoredModules", getPath("InputMain.java"));
        });
        final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                + " cannot initialize module TreeWalker - ";
        final String causeDetail = "it is not a boolean";
        assertTrue(systemErr.getCapturedData().startsWith(cause), "Unexpected system error log");
        assertTrue(systemErr.getCapturedData().contains(causeDetail),
                "Unexpected system error log");
    }

    @Test
    public void testNoProblemOnExecuteIgnoredModuleWithBadPropertyValue(
            @SysErr Capturable systemErr) throws IOException {
        Main.main("-c", getPath("InputMainConfig-TypeName-bad-value.xml"),
                    "", getPath("InputMain.java"));
        assertTrue(systemErr.getCapturedData().isEmpty(), "Unexpected system error log");
    }

    @Test
    public void testInvalidCheckerThreadsNumber(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-C", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
        });
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("Invalid value for option '--checker-threads-number': 'invalid' is not an int"
                + EOL + SHORT_USAGE, systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testInvalidTreeWalkerThreadsNumber(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-W", "invalid", "-c", "/google_checks.xml", getPath("InputMain.java"));
        });
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("Invalid value for option '--tree-walker-threads-number': "
                + "'invalid' is not an int" + EOL + SHORT_USAGE, systemErr.getCapturedData(),
                "Unexpected system error log");
    }

    @Test
    public void testZeroCheckerThreadsNumber(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-C", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
        });
        assertEquals("Checker threads number must be greater than zero"
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testZeroTreeWalkerThreadsNumber(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, () -> {
            invokeMain("-W", "0", "-c", "/google_checks.xml", getPath("InputMain.java"));
        });
        assertEquals(
                "TreeWalker threads number must be greater than zero"
            + System.lineSeparator(), systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testCheckerThreadsNumber(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        TestRootModuleChecker.reset();

        Main.main("-C", "4", "-c", getPath("InputMainConfig-custom-root-module.xml"),
                getPath("InputMain.java"));
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
        assertTrue(TestRootModuleChecker.isProcessed(), "Invalid checker state");
        final DefaultConfiguration config =
                (DefaultConfiguration) TestRootModuleChecker.getConfig();
        final ThreadModeSettings multiThreadModeSettings = config.getThreadModeSettings();
        assertEquals(4, multiThreadModeSettings.getCheckerThreadsNumber(),
                "Invalid checker thread number");
        assertEquals(1, multiThreadModeSettings.getTreeWalkerThreadsNumber(),
                "Invalid checker thread number");
    }

    @Test
    public void testTreeWalkerThreadsNumber(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        TestRootModuleChecker.reset();

        Main.main("-W", "4", "-c", getPath("InputMainConfig-custom-root-module.xml"),
                getPath("InputMain.java"));
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
        assertTrue(TestRootModuleChecker.isProcessed(), "Invalid checker state");
        final DefaultConfiguration config =
                (DefaultConfiguration) TestRootModuleChecker.getConfig();
        final ThreadModeSettings multiThreadModeSettings = config.getThreadModeSettings();
        assertEquals(1, multiThreadModeSettings.getCheckerThreadsNumber(),
                "Invalid checker thread number");
        assertEquals(4, multiThreadModeSettings.getTreeWalkerThreadsNumber(),
                "Invalid checker thread number");
    }

    @Test
    public void testModuleNameInSingleThreadMode(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        TestRootModuleChecker.reset();

        Main.main("-C", "1", "-W", "1", "-c", getPath("InputMainConfig-multi-thread-mode.xml"),
                getPath("InputMain.java"));
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals("", systemErr.getCapturedData(), "Unexpected system error log");
        assertTrue(TestRootModuleChecker.isProcessed(), "Invalid checker state");
        final DefaultConfiguration config =
                (DefaultConfiguration) TestRootModuleChecker.getConfig();
        final ThreadModeSettings multiThreadModeSettings =
            config.getThreadModeSettings();
        assertEquals(1, multiThreadModeSettings.getCheckerThreadsNumber(),
                "Invalid checker thread number");
        assertEquals(1, multiThreadModeSettings.getTreeWalkerThreadsNumber(),
                "Invalid checker thread number");
        final Configuration checkerConfiguration = config.getChildren()[0];
        assertEquals("Checker", checkerConfiguration.getName(), "Invalid checker name");
        final Configuration treeWalkerConfig = checkerConfiguration.getChildren()[0];
        assertEquals("TreeWalker", treeWalkerConfig.getName(), "Invalid checker children name");
    }

    @Test
    public void testModuleNameInMultiThreadMode() {
        TestRootModuleChecker.reset();

        try {
            assertExitWithStatus(-1, () -> {
                invokeMain("-C", "4", "-W", "4", "-c",
                        getPath("InputMainConfig-multi-thread-mode.xml"),
                        getPath("InputMain.java"));
            });
            fail("An exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Multi thread mode for Checker module is not implemented",
                ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testMissingFiles(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertExitWithStatus(-1, MainTest::invokeMain);
        final String usage = "Missing required parameter: <files>" + EOL + SHORT_USAGE;
        assertEquals("", systemOut.getCapturedData(), "Unexpected output log");
        assertEquals(usage, systemErr.getCapturedData(), "Unexpected system error log");
    }

    @Test
    public void testOutputFormatToStringLowercase() {
        assertEquals("xml", Main.OutputFormat.XML.toString(), "expected xml");
        assertEquals("plain", Main.OutputFormat.PLAIN.toString(), "expected plain");
    }

    @Test
    public void testXmlOutputFormatCreateListener() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.XML.createListener(out,
                AutomaticBean.OutputStreamOptions.CLOSE);
        assertTrue(listener instanceof XMLLogger, "listener is XMLLogger");
    }

    @Test
    public void testPlainOutputFormatCreateListener() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.PLAIN.createListener(out,
                AutomaticBean.OutputStreamOptions.CLOSE);
        assertTrue(listener instanceof DefaultLogger, "listener is DefaultLogger");
    }

    /**
     * Helper method to run {@link Main#main(String...)} as {@link Runnable}.
     *
     * @param arguments the command line arguments
     */
    private static void invokeMain(String... arguments) {
        try {
            Main.main(arguments);
        }
        catch (IOException exception) {
            fail("Unexpected exception: " + exception);
        }
    }

}
