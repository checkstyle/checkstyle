///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.AbstractPathTestSupport.addEndOfLine;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestRootModuleChecker;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.ChainedPropertyUtil;

@ExtendWith({SystemErrGuard.class, SystemOutGuard.class})
public class MainTest {

    private static final String SHORT_USAGE = String.format(Locale.ROOT,
            "Usage: checkstyle [OPTIONS]... file(s) or folder(s) ...%n"
            + "Try 'checkstyle --help' for more information.%n");

    private static final String USAGE = String.format(Locale.ROOT,
          "Usage: checkstyle [-dEghjJtTV] [-b=<xpath>] [-c=<configurationFile>] "
                  + "[-f=<format>]%n"
                  + "                  [-o=<outputPath>] [-p=<propertiesFile>] "
                  + "[-s=<suppressionLineColumnNumber>]%n"
                  + "                  [-w=<tabWidth>] [-e=<exclude>]... [-x=<excludeRegex>]... "
                  + "<files or folders>...%n"
                  + "Checkstyle verifies that the specified source code files adhere to the"
                  + " specified rules. By default,%n"
                  + "violations are reported to standard out in plain format. Checkstyle requires"
                  + " a configuration XML%n"
                  + "file that configures the checks to apply.%n"
                  + "      <files or folders>... One or more source files to verify%n"
                  + "  -b, --branch-matching-xpath=<xpath>%n"
                  + "                            Shows Abstract Syntax Tree(AST) branches that"
                  + " match given XPath query.%n"
                  + "  -c=<configurationFile>    Specifies the location of the file that defines"
                  + " the configuration%n"
                  + "                              modules. The location can either be a"
                  + " filesystem location, or a name%n"
                  + "                              passed to the ClassLoader.getResource()"
                  + " method.%n"
                  + "  -d, --debug               Prints all debug logging of CheckStyle utility.%n"
                  + "  -e, --exclude=<exclude>   Directory/file to exclude from CheckStyle. The"
                  + " path can be the full,%n"
                  + "                              absolute path, or relative to the current"
                  + " path. Multiple excludes are%n"
                  + "                              allowed.%n"
                  + "  -E, --executeIgnoredModules%n"
                  + "                            Allows ignored modules to be run.%n"
                  + "  -f=<format>               Specifies the output format. Valid values: "
                  + "xml, sarif, plain for%n"
                  + "                              XMLLogger, SarifLogger, and "
                  + "DefaultLogger respectively. Defaults to%n"
                  + "                              plain.%n"
                  + "  -g, --generate-xpath-suppression%n"
                  + "                            Generates to output a suppression xml to use"
                  + " to suppress all violations%n"
                  + "                              from user's config. Instead of printing every"
                  + " violation, all%n"
                  + "                              violations will be catched and single"
                  + " suppressions xml file will be%n"
                  + "                              printed out. Used only with -c option. Output"
                  + " location can be%n"
                  + "                              specified with -o option.%n"
                  + "  -h, --help                Show this help message and exit.%n"
                  + "  -j, --javadocTree         This option is used to print the Parse Tree of"
                  + " the Javadoc comment. The%n"
                  + "                              file has to contain only Javadoc comment"
                  + " content excluding '/**' and%n"
                  + "                              '*/' at the beginning and at the end"
                  + " respectively. It can only be%n"
                  + "                              used on a single file and cannot be"
                  + " combined with other options.%n"
                  + "  -J, --treeWithJavadoc     This option is used to display the Abstract"
                  + " Syntax Tree (AST) with%n"
                  + "                              Javadoc nodes of the specified file. It can"
                  + " only be used on a single%n"
                  + "                              file and cannot be combined"
                  + " with other options.%n"
                  + "  -o=<outputPath>           Sets the output file. Defaults to stdout.%n"
                  + "  -p=<propertiesFile>       Sets the property files to load.%n"
                  + "  -s=<suppressionLineColumnNumber>%n"
                  + "                            Prints xpath suppressions at the file's line and"
                  + " column position.%n"
                  + "                              Argument is the line and column number"
                  + " (separated by a : ) in the%n"
                  + "                              file that the suppression should be generated"
                  + " for. The option cannot%n"
                  + "                              be used with other options and requires exactly"
                  + " one file to run on to%n"
                  + "                              be specified. Note that the generated result"
                  + " will have few queries,%n"
                  + "                              joined by pipe(|). Together they will match all"
                  + " AST nodes on%n"
                  + "                              specified line and column. You need to choose"
                  + " only one and recheck%n"
                  + "                              that it works. Usage of all of them is also ok,"
                  + " but might result in%n"
                  + "                              undesirable matching and suppress other"
                  + " issues.%n"
                  + "  -t, --tree                This option is used to display the Abstract"
                  + " Syntax Tree (AST) without%n"
                  + "                              any comments of the specified file. It can"
                  + " only be used on a single%n"
                  + "                              file and cannot be combined with"
                  + " other options.%n"
                  + "  -T, --treeWithComments    This option is used to display the Abstract"
                  + " Syntax Tree (AST) with%n"
                  + "                              comment nodes excluding Javadoc of the"
                  + " specified file. It can only be%n"
                  + "                              used on a single file and cannot be combined"
                  + " with other options.%n"
                  + "  -V, --version             Print version information and exit.%n"
                  + "  -w, --tabWidth=<tabWidth> Sets the length of the tab character. Used only"
                  + " with -s option. Default%n"
                  + "                              value is 8.%n"
                  + "  -x, --exclude-regexp=<excludeRegex>%n"
                  + "                            Directory/file pattern to exclude from CheckStyle."
                  + " Multiple excludes%n"
                  + "                              are allowed.%n");

    private static final Logger LOG = Logger.getLogger(MainTest.class.getName()).getParent();
    private static final Handler[] HANDLERS = LOG.getHandlers();
    private static final Level ORIGINAL_LOG_LEVEL = LOG.getLevel();

    private static final String EOL = System.lineSeparator();

    @TempDir
    public File temporaryFolder;

    private final LocalizedMessage auditStartMessage = new LocalizedMessage(
            Definitions.CHECKSTYLE_BUNDLE, getClass(),
            "DefaultLogger.auditStarted");

    private final LocalizedMessage auditFinishMessage = new LocalizedMessage(
            Definitions.CHECKSTYLE_BUNDLE, getClass(),
            "DefaultLogger.auditFinished");

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
     * Configures the environment for each test.
     * <ul>
     * <li>Restore original logging level and HANDLERS to prevent bleeding into other tests;</li>
     * <li>Turn off colors for picocli to not conflict with tests if they are auto turned on.</li>
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

        System.setProperty("picocli.ansi", "false");

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
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(Main.class))
                .isTrue();
    }

    @Test
    public void testVersionPrint(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-V");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Checkstyle version: null" + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testUsageHelpPrint(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-h");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(USAGE);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testWrongArgument(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        // need to specify a file:
        // <files> is defined as a required positional param;
        // picocli verifies required parameters before checking unknown options
        assertMainReturnCode(-1, "-q", "file");
        final String usage = "Unknown option: '-q'" + EOL + SHORT_USAGE;
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(usage);
    }

    @Test
    public void testWrongArgumentMissingFiles(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-q");
        // files is defined as a required positional param;
        // picocli verifies required parameters before checking unknown options
        final String usage = "Missing required parameter: '<files or folders>'" + EOL + SHORT_USAGE;
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(usage);
    }

    @Test
    public void testNoConfigSpecified(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Must specify a config XML file." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testNonExistentTargetFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-c", "/google_checks.xml", "NonExistentFile.java");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Files to process must be specified, found 0." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingTargetFileButWithoutReadAccess(
            @SysErr Capturable systemErr, @SysOut Capturable systemOut) throws IOException {
        final File file = File.createTempFile(
                "testExistingTargetFileButWithoutReadAccess", null, temporaryFolder);
        // skip execution if file is still readable, it is possible on some Windows machines
        // see https://github.com/checkstyle/checkstyle/issues/7032 for details
        assumeTrue(file.setReadable(false), "file is still readable");

        final String canonicalPath = file.getCanonicalPath();
        assertMainReturnCode(-1, "-c", "/google_checks.xml", canonicalPath);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Files to process must be specified, found 0." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testCustomSeverityVariableForGoogleConfig(@SysOut Capturable systemOut) {
        assertMainReturnCode(1, "-c", "/google_checks.xml",
                "-p", getPath("InputMainCustomSeverityForGoogleConfig.properties"),
                getPath("InputMainCustomSeverityForGoogleConfig.java"));

        final String expectedOutputStart = addEndOfLine(auditStartMessage.getMessage())
            + "[ERROR] ";
        final String expectedOutputEnd = addEndOfLine(
                "InputMainCustomSeverityForGoogleConfig.java:3:1:"
                    + " Missing a Javadoc comment. [MissingJavadocType]",
                auditFinishMessage.getMessage());
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .startsWith(expectedOutputStart);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .endsWith(expectedOutputEnd);
    }

    @Test
    public void testDefaultSeverityVariableForGoogleConfig(@SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", "/google_checks.xml",
                getPath("InputMainCustomSeverityForGoogleConfig.java"));

        final String expectedOutputStart = addEndOfLine(auditStartMessage.getMessage())
                + "[WARN] ";
        final String expectedOutputEnd = addEndOfLine(
                "InputMainCustomSeverityForGoogleConfig.java:3:1:"
                        + " Missing a Javadoc comment. [MissingJavadocType]",
                auditFinishMessage.getMessage());
        assertWithMessage("Unexpected output log")
                .that(systemOut.getCapturedData())
                .startsWith(expectedOutputStart);
        assertWithMessage("Unexpected output log")
                .that(systemOut.getCapturedData())
                .endsWith(expectedOutputEnd);
    }

    @Test
    public void testNonExistentConfigFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-c", "src/main/resources/non_existent_config.xml",
                    getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine("Could not find config XML file "
                    + "'src/main/resources/non_existent_config.xml'."));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testNonExistentOutputFormat(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-c", "/google_checks.xml", "-f", "xmlp",
                getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("Invalid value for option '-f': expected one of [XML, SARIF, PLAIN]"
                    + " (case-insensitive) but was 'xmlp'" + EOL + SHORT_USAGE);
    }

    @Test
    public void testNonExistentClass(@SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-non-existent-classname.xml"),
                    getPath("InputMain.java"));
        final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                + " cannot initialize module TreeWalker - ";
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .startsWith(cause);
    }

    @Test
    public void testExistingTargetFile(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"),
                getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingTargetFileXmlOutput(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"), "-f", "xml",
                getPath("InputMain.java"));
        final String expectedPath = getFilePath("InputMain.java");
        final String version = Main.class.getPackage().getImplementationVersion();
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                "<checkstyle version=\"" + version + "\">",
                "<file name=\"" + expectedPath + "\">",
                "</file>",
                "</checkstyle>"));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    /**
     * This test method is created only to cover
     * pitest mutation survival at {@code Main#getOutputStreamOptions}.
     * Parameters {@code systemErr} and {@code systemOut} are used to restore
     * the original system streams.
     *
     * @param systemErr the system error stream
     * @param systemOut the system output stream
     */
    @Test
    public void testNonClosedSystemStreams(@SysErr Capturable systemErr,
           @SysOut Capturable systemOut) {
        try (ShouldNotBeClosedStream stream = new ShouldNotBeClosedStream()) {
            System.setOut(stream);
            System.setErr(stream);
            assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"), "-f", "xml",
                    getPath("InputMain.java"));
            assertWithMessage("stream should not be closed")
                .that(stream.isClosed)
                .isFalse();
            assertWithMessage("System.err should be not used")
                .that(systemErr.getCapturedData())
                .isEmpty();
            assertWithMessage("System.out should be not used")
                .that(systemOut.getCapturedData())
                .isEmpty();
        }
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
        final OutputStreamOptions option =
                TestUtil.invokeStaticMethod(Main.class, "getOutputStreamOptions", path);
        assertWithMessage("Main.getOutputStreamOptions return CLOSE on not null Path")
                .that(option)
                .isEqualTo(OutputStreamOptions.CLOSE);
    }

    @Test
    public void testExistingTargetFilePlainOutput(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingTargetFileWithViolations(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname2.xml"),
                getPath("InputMain.java"));
        final Violation invalidPatternMessageMain = new Violation(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                null, getClass(), null);
        final Violation invalidPatternMessageMainInner = new Violation(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain.java");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                    "[WARN] " + expectedPath + ":3:14: "
                        + invalidPatternMessageMain.getViolation()
                        + " [TypeName]",
                    "[WARN] " + expectedPath + ":5:7: "
                        + invalidPatternMessageMainInner.getViolation()
                        + " [TypeName]",
                    auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testViolationsByGoogleAndXpathSuppressions(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        System.setProperty("org.checkstyle.google.suppressionxpathfilter.config",
                getPath("InputMainViolationsForGoogleXpathSuppressions.xml"));
        assertMainReturnCode(0, "-c", "/google_checks.xml",
                getPath("InputMainViolationsForGoogle.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(noViolationsOutput);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testViolationsByGoogleAndSuppressions(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        System.setProperty("org.checkstyle.google.suppressionfilter.config",
                getPath("InputMainViolationsForGoogleSuppressions.xml"));
        assertMainReturnCode(0, "-c", "/google_checks.xml",
                getPath("InputMainViolationsForGoogle.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(noViolationsOutput);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingTargetFileWithError(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        assertMainReturnCode(2, "-c", getPath("InputMainConfig-classname2-error.xml"),
                    getPath("InputMain.java"));
        final Violation errorCounterTwoMessage = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                new String[] {String.valueOf(2)}, null, getClass(), null);
        final Violation invalidPatternMessageMain = new Violation(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMain", "^[a-z0-9]*$"},
                null, getClass(), null);
        final Violation invalidPatternMessageMainInner = new Violation(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMainInner", "^[a-z0-9]*$"},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain.java");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                    "[ERROR] " + expectedPath + ":3:14: "
                        + invalidPatternMessageMain.getViolation() + " [TypeName]",
                    "[ERROR] " + expectedPath + ":5:7: "
                        + invalidPatternMessageMainInner.getViolation() + " [TypeName]",
                    auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(addEndOfLine(errorCounterTwoMessage.getViolation()));
    }

    /**
     * Similar test to {@link #testExistingTargetFileWithError}, but for PIT mutation tests:
     * this test fails if the boundary condition is changed from {@code if (exitStatus > 0)}
     * to {@code if (exitStatus > 1)}.
     *
     * @throws Exception should not throw anything
     */
    @Test
    public void testExistingTargetFileWithOneError(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        assertMainReturnCode(1, "-c", getPath("InputMainConfig-classname2-error.xml"),
                    getPath("InputMain1.java"));
        final Violation errorCounterTwoMessage = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                new String[] {String.valueOf(1)}, null, getClass(), null);
        final Violation invalidPatternMessageMain = new Violation(1,
                "com.puppycrawl.tools.checkstyle.checks.naming.messages",
                "name.invalidPattern", new String[] {"InputMain1", "^[a-z0-9]*$"},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain1.java");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                    "[ERROR] " + expectedPath + ":3:14: "
                        + invalidPatternMessageMain.getViolation() + " [TypeName]",
                    auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(addEndOfLine(errorCounterTwoMessage.getViolation()));
    }

    @Test
    public void testExistingTargetFileWithOneErrorAgainstSunCheck(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        assertMainReturnCode(1, "-c", "/sun_checks.xml", getPath("InputMain1.java"));
        final Violation errorCounterTwoMessage = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, Main.ERROR_COUNTER,
                new String[] {String.valueOf(1)}, null, getClass(), null);
        final Violation message = new Violation(1,
                "com.puppycrawl.tools.checkstyle.checks.javadoc.messages",
                "javadoc.packageInfo", new String[] {},
                null, getClass(), null);
        final String expectedPath = getFilePath("InputMain1.java");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                "[ERROR] " + expectedPath + ":1: " + message.getViolation() + " [JavadocPackage]",
                auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(addEndOfLine(errorCounterTwoMessage.getViolation()));
    }

    @Test
    public void testExistentTargetFilePlainOutputToNonExistentFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                "-o", temporaryFolder + "/output.txt", getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingTargetFilePlainOutputToFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws Exception {
        final String outputFile =
                File.createTempFile("file", ".output", temporaryFolder).getCanonicalPath();
        assertWithMessage("File must exist")
                .that(new File(outputFile).exists())
                .isTrue();
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                "-o", outputFile, getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testCreateNonExistentOutputFile() throws IOException {
        final String outputFile = new File(temporaryFolder, "nonexistent.out").getCanonicalPath();
        assertWithMessage("File must not exist")
                .that(new File(outputFile).exists())
                .isFalse();
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname.xml"), "-f", "plain",
                "-o", outputFile, getPath("InputMain.java"));
        assertWithMessage("File must exist")
                .that(new File(outputFile).exists())
                .isTrue();
    }

    @Test
    public void testExistingTargetFilePlainOutputProperties(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", getPath("InputMainMycheckstyle.properties"), getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPropertyFileWithPropertyChaining(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-classname-prop.xml"),
            "-p", getPath("InputMainPropertyChaining.properties"), getPath("InputMain.java"));

        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(addEndOfLine(auditStartMessage.getMessage(),
                auditFinishMessage.getMessage()));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPropertyFileWithPropertyChainingUndefinedProperty(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-classname-prop.xml"),
                "-p", getPath("InputMainPropertyChainingUndefinedProperty.properties"),
                getPath("InputMain.java"));

        assertWithMessage("Invalid error message")
            .that(systemErr.getCapturedData())
            .contains(ChainedPropertyUtil.UNDEFINED_PROPERTY_MESSAGE);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingTargetFilePlainOutputNonexistentProperties(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-c", getPath("InputMainConfig-classname-prop.xml"),
                    "-p", "nonexistent.properties", getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Could not find file 'nonexistent.properties'."
                + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExistingIncorrectConfigFile(@SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-Incorrect.xml"),
                getPath("InputMain.java"));
        final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
            + "CheckstyleException: unable to parse configuration stream - ";
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .startsWith(errorOutput);
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile(@SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-incorrectChildren.xml"),
                    getPath("InputMain.java"));
        final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: cannot initialize module RegexpSingleline"
                + " - RegexpSingleline is not allowed as a child in RegexpSingleline";
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .startsWith(errorOutput);
    }

    @Test
    public void testExistingIncorrectChildrenInConfigFile2(@SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-incorrectChildren2.xml"),
                    getPath("InputMain.java"));
        final String errorOutput = "com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: cannot initialize module TreeWalker - "
                + "cannot initialize module JavadocMethod - "
                + "JavadocVariable is not allowed as a child in JavadocMethod";
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .startsWith(errorOutput);
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
            assertWithMessage("Exception was expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Invalid error cause")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(CheckstyleException.class);
            // We do separate validation for message as in Windows
            // disk drive letter appear in message,
            // so we skip that drive letter for compatibility issues
            final Violation loadPropertiesMessage = new Violation(1,
                    Definitions.CHECKSTYLE_BUNDLE, Main.LOAD_PROPERTIES_EXCEPTION,
                    new String[] {""}, null, getClass(), null);
            final String causeMessage = ex.getCause().getLocalizedMessage();
            final String violation = loadPropertiesMessage.getViolation();
            final boolean samePrefix = causeMessage.substring(0, causeMessage.indexOf(' '))
                    .equals(violation
                            .substring(0, violation.indexOf(' ')));
            final boolean sameSuffix =
                    causeMessage.substring(causeMessage.lastIndexOf(' '))
                    .equals(violation
                            .substring(violation.lastIndexOf(' ')));
            assertWithMessage("Invalid violation")
                    .that(samePrefix || sameSuffix)
                    .isTrue();
            assertWithMessage("Invalid violation")
                    .that(causeMessage)
                    .contains(".'");
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

        assertMainReturnCode(0, "-c", getPath("InputMainConfig-filelength.xml"),
                getPath(""));
        final String expectedPath = getFilePath("") + File.separator;
        final StringBuilder sb = new StringBuilder(28);
        sb.append(auditStartMessage.getMessage())
                .append(EOL);
        final String format = "[WARN] " + expectedPath + outputValues[0][0] + ".java:"
                + outputValues[0][1] + ": ";
        for (String[] outputValue : outputValues) {
            final String violation = new Violation(1, bundle,
                    msgKey, new Integer[] {Integer.valueOf(outputValue[2]), allowedLength},
                    null, getClass(), null).getViolation();
            final String line = format + violation + " [FileLength]";
            sb.append(line).append(EOL);
        }
        sb.append(auditFinishMessage.getMessage())
                .append(EOL);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(sb.toString());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
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

        final List<File> result = TestUtil.invokeStaticMethod(Main.class, "listFiles",
                fileMock, new ArrayList<>());
        assertWithMessage("Invalid result size")
            .that(result)
            .isEmpty();
    }

    /**
     * Test doesn't need to be serialized.
     *
     * @noinspection SerializableInnerClassWithNonSerializableOuterClass
     * @noinspectionreason SerializableInnerClassWithNonSerializableOuterClass - mocked file
     *      for test does not require serialization
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

        final List<File> result = TestUtil.invokeStaticMethod(Main.class, "listFiles",
                fileMock, new ArrayList<>());
        assertWithMessage("Invalid result size")
            .that(result)
            .isEmpty();
    }

    @Test
    public void testFileReferenceDuringException(@SysErr Capturable systemErr) {
        // We put xml as source to cause parse exception
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-classname.xml"),
                    getNonCompilablePath("InputMainIncorrectClass.java"));
        final String exceptionMessage = addEndOfLine("com.puppycrawl.tools.checkstyle.api."
                + "CheckstyleException: Exception was thrown while processing "
                + new File(getNonCompilablePath("InputMainIncorrectClass.java")).getPath());
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .contains(exceptionMessage);
    }

    @Test
    public void testRemoveLexerDefaultErrorListener(@SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-t", getNonCompilablePath("InputMainIncorrectClass.java"));

        assertWithMessage("First line of exception message should not contain lexer error.")
            .that(systemErr.getCapturedData().startsWith("line 2:2 token recognition error"))
                .isFalse();
    }

    @Test
    public void testRemoveParserDefaultErrorListener(@SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-t", getNonCompilablePath("InputMainIncorrectClass.java"));
        final String capturedData = systemErr.getCapturedData();

        assertWithMessage("First line of exception message should not contain parser error.")
            .that(capturedData.startsWith("line 2:0 no viable alternative"))
                .isFalse();
        assertWithMessage("Second line of exception message should not contain parser error.")
            .that(capturedData.startsWith("line 2:0 no viable alternative",
                    capturedData.indexOf('\n') + 1))
                .isFalse();
    }

    @Test
    public void testPrintTreeOnMoreThanOneFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-t", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Printing AST is allowed for only one file." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintTreeOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "|--PACKAGE_DEF -> package [1:0]",
            "|   |--ANNOTATIONS -> ANNOTATIONS [1:39]",
            "|   |--DOT -> . [1:39]",
            "|   |   |--DOT -> . [1:28]",
            "|   |   |   |--DOT -> . [1:22]",
            "|   |   |   |   |--DOT -> . [1:11]",
            "|   |   |   |   |   |--IDENT -> com [1:8]",
            "|   |   |   |   |   `--IDENT -> puppycrawl [1:12]",
            "|   |   |   |   `--IDENT -> tools [1:23]",
            "|   |   |   `--IDENT -> checkstyle [1:29]",
            "|   |   `--IDENT -> main [1:40]",
            "|   `--SEMI -> ; [1:44]",
            "|--CLASS_DEF -> CLASS_DEF [3:0]",
            "|   |--MODIFIERS -> MODIFIERS [3:0]",
            "|   |   `--LITERAL_PUBLIC -> public [3:0]",
            "|   |--LITERAL_CLASS -> class [3:7]",
            "|   |--IDENT -> InputMain [3:13]",
            "|   `--OBJBLOCK -> OBJBLOCK [3:23]",
            "|       |--LCURLY -> { [3:23]",
            "|       `--RCURLY -> } [4:0]",
            "`--CLASS_DEF -> CLASS_DEF [5:0]",
            "    |--MODIFIERS -> MODIFIERS [5:0]",
            "    |--LITERAL_CLASS -> class [5:0]",
            "    |--IDENT -> InputMainInner [5:6]",
            "    `--OBJBLOCK -> OBJBLOCK [5:21]",
            "        |--LCURLY -> { [5:21]",
            "        `--RCURLY -> } [6:0]");

        assertMainReturnCode(0, "-t", getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintXpathOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "|--CLASS_DEF -> CLASS_DEF [3:0]",
            "|   `--OBJBLOCK -> OBJBLOCK [3:28]",
            "|       |--METHOD_DEF -> METHOD_DEF [4:4]",
            "|       |   `--SLIST -> { [4:20]",
            "|       |       |--VARIABLE_DEF -> VARIABLE_DEF [5:8]",
            "|       |       |   |--IDENT -> a [5:12]");
        assertMainReturnCode(0, "-b",
                "/COMPILATION_UNIT/CLASS_DEF//METHOD_DEF[./IDENT[@text='methodOne']]"
                        + "//VARIABLE_DEF/IDENT",
                getPath("InputMainXPath.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintXpathCommentNode(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "`--CLASS_DEF -> CLASS_DEF [17:0]",
            "    `--OBJBLOCK -> OBJBLOCK [17:19]",
            "        |--CTOR_DEF -> CTOR_DEF [19:4]",
            "        |   |--BLOCK_COMMENT_BEGIN -> /* [18:4]");
        assertMainReturnCode(0, "-b", "/COMPILATION_UNIT/CLASS_DEF//BLOCK_COMMENT_BEGIN",
                getPath("InputMainXPath.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintXpathNodeParentNull(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = addEndOfLine("COMPILATION_UNIT -> COMPILATION_UNIT [1:0]");
        assertMainReturnCode(0, "-b", "/COMPILATION_UNIT", getPath("InputMainXPath.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintXpathFullOption(
            @SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "|--CLASS_DEF -> CLASS_DEF [3:0]",
            "|   `--OBJBLOCK -> OBJBLOCK [3:28]",
            "|       |--METHOD_DEF -> METHOD_DEF [8:4]",
            "|       |   `--SLIST -> { [8:26]",
            "|       |       |--VARIABLE_DEF -> VARIABLE_DEF [9:8]",
            "|       |       |   |--IDENT -> a [9:12]");
        final String xpath = "/COMPILATION_UNIT/CLASS_DEF//METHOD_DEF[./IDENT[@text='method']]"
                + "//VARIABLE_DEF/IDENT";
        assertMainReturnCode(0, "--branch-matching-xpath", xpath, getPath("InputMainXPath.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintXpathTwoResults(
            @SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "|--CLASS_DEF -> CLASS_DEF [12:0]",
            "|   `--OBJBLOCK -> OBJBLOCK [12:10]",
            "|       |--METHOD_DEF -> METHOD_DEF [13:4]",
            "---------",
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "|--CLASS_DEF -> CLASS_DEF [12:0]",
            "|   `--OBJBLOCK -> OBJBLOCK [12:10]",
            "|       |--METHOD_DEF -> METHOD_DEF [14:4]");
        assertMainReturnCode(0, "--branch-matching-xpath",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='Two']]//METHOD_DEF",
                getPath("InputMainXPath.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintXpathInvalidXpath(@SysErr Capturable systemErr) throws Exception {
        final String invalidXpath = "\\/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='Two']]"
                + "//METHOD_DEF";
        final String filePath = getFilePath("InputMainXPath.java");
        assertMainReturnCode(-2, "--branch-matching-xpath", invalidXpath, filePath);
        final String exceptionFirstLine = addEndOfLine("com.puppycrawl.tools.checkstyle.api."
            + "CheckstyleException: Error during evaluation for xpath: " + invalidXpath
            + ", file: " + filePath);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .startsWith(exceptionFirstLine);
    }

    @Test
    public void testPrintTreeCommentsOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "COMPILATION_UNIT -> COMPILATION_UNIT [1:0]",
            "|--PACKAGE_DEF -> package [1:0]",
            "|   |--ANNOTATIONS -> ANNOTATIONS [1:39]",
            "|   |--DOT -> . [1:39]",
            "|   |   |--DOT -> . [1:28]",
            "|   |   |   |--DOT -> . [1:22]",
            "|   |   |   |   |--DOT -> . [1:11]",
            "|   |   |   |   |   |--IDENT -> com [1:8]",
            "|   |   |   |   |   `--IDENT -> puppycrawl [1:12]",
            "|   |   |   |   `--IDENT -> tools [1:23]",
            "|   |   |   `--IDENT -> checkstyle [1:29]",
            "|   |   `--IDENT -> main [1:40]",
            "|   `--SEMI -> ; [1:44]",
            "|--CLASS_DEF -> CLASS_DEF [3:0]",
            "|   |--MODIFIERS -> MODIFIERS [3:0]",
            "|   |   |--BLOCK_COMMENT_BEGIN -> /* [2:0]",
            "|   |   |   |--COMMENT_CONTENT -> comment [2:2]",
            "|   |   |   `--BLOCK_COMMENT_END -> */ [2:8]",
            "|   |   `--LITERAL_PUBLIC -> public [3:0]",
            "|   |--LITERAL_CLASS -> class [3:7]",
            "|   |--IDENT -> InputMain [3:13]",
            "|   `--OBJBLOCK -> OBJBLOCK [3:23]",
            "|       |--LCURLY -> { [3:23]",
            "|       `--RCURLY -> } [4:0]",
            "`--CLASS_DEF -> CLASS_DEF [5:0]",
            "    |--MODIFIERS -> MODIFIERS [5:0]",
            "    |--LITERAL_CLASS -> class [5:0]",
            "    |--IDENT -> InputMainInner [5:6]",
            "    `--OBJBLOCK -> OBJBLOCK [5:21]",
            "        |--LCURLY -> { [5:21]",
            "        `--RCURLY -> } [6:0]");

        assertMainReturnCode(0, "-T", getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    /**
     * Verifies the output of the command line parameter "-j".
     *
     * @param systemErr wrapper for {@code System.err}
     * @param systemOut wrapper for {@code System.out}
     * @throws IOException if I/O exception occurs while reading the test input.
     * @noinspection RedundantThrows
     * @noinspectionreason RedundantThrows - false positive
     */
    @Test
    public void testPrintTreeJavadocOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String expected = Files.readString(Path.of(
            getPath("InputMainExpectedInputJavadocComment.txt")))
            .replaceAll("\\\\r\\\\n", "\\\\n").replaceAll("\r\n", "\n");

        assertMainReturnCode(0, "-j", getPath("InputMainJavadocComment.javadoc"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData().replaceAll("\\\\r\\\\n", "\\\\n")
                        .replaceAll("\r\n", "\n"))
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputMainSuppressionsStringPrinter']"
                        + "]/LITERAL_CLASS");

        assertMainReturnCode(0, getPath("InputMainSuppressionsStringPrinter.java"), "-s", "3:1");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionAndTabWidthOption(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = addEndOfLine(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]"
                    + "/SLIST/VARIABLE_DEF[./IDENT[@text='var']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST"
                    + "/VARIABLE_DEF[./IDENT[@text='var']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST"
                    + "/VARIABLE_DEF[./IDENT[@text='var']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputMainSuppressionsStringPrinter']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='getName']]/SLIST"
                    + "/VARIABLE_DEF[./IDENT[@text='var']]/TYPE/LITERAL_INT");

        assertMainReturnCode(0, getPath("InputMainSuppressionsStringPrinter.java"),
                "-s", "7:9", "--tabWidth", "2");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsC(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-c", "/google_checks.xml", getPath(""), "-s", "2:4");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-s' cannot be used with other options."
                + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsP(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-p", getPath("InputMainMycheckstyle.properties"), "-s", "2:4",
                getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-s' cannot be used with other options."
                + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsF(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-f", "plain", "-s", "2:4", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-s' cannot be used with other options."
                + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionConflictingOptionsTvsO(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String outputPath = new File(temporaryFolder, "file.output").getCanonicalPath();

        assertMainReturnCode(-1, "-o", outputPath, "-s", "2:4", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-s' cannot be used with other options."
                + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testPrintSuppressionOnMoreThanOneFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-s", "2:4", getPath(""), getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Printing xpath suppressions is allowed for only one file."
                + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testGenerateXpathSuppressionOptionOne(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
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
                "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]\"/>",
                "<suppress-xpath",
                "       files=\"InputMainComplexityOverflow.java\"",
                "       id=\"LeftCurlyEol\"",
                "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputMainComplexityOverflow']]/OBJBLOCK"
                    + "/METHOD_DEF[./IDENT[@text='provokeNpathIntegerOverflow']]/SLIST\"/>",
                "</suppressions>");

        assertMainReturnCode(0, "-c", "/google_checks.xml", "--generate-xpath-suppression",
                getPath("InputMainComplexityOverflow.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testGenerateXpathSuppressionOptionTwo(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
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
            "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
                + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>",
            "<suppress-xpath",
            "       files=\"InputMainGenerateXpathSuppressions.java\"",
            "       checks=\"IllegalThrowsCheck\"",
            "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/LITERAL_THROWS"
                + "/IDENT[@text='RuntimeException']\"/>",
            "<suppress-xpath",
            "       files=\"InputMainGenerateXpathSuppressions.java\"",
            "       checks=\"NestedForDepthCheck\"",
            "       query=\"/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputMainGenerateXpathSuppressions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST"
                + "/LITERAL_FOR/SLIST/LITERAL_FOR\"/>",
            "</suppressions>");

        assertMainReturnCode(0, "-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressions.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testGenerateXpathSuppressionOptionEmptyConfig(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = "";

        assertMainReturnCode(0, "-c", getPath("InputMainConfig-empty.xml"),
                "--generate-xpath-suppression", getPath("InputMainComplexityOverflow.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
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
                "       query=\"/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputMainGenerateXpathSuppressionsTabWidth']]"
                    + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>",
                "</suppressions>");
        final File file = new File(temporaryFolder, "file.output");
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-xpath-suppressions.xml"), "-o",
                file.getPath(), "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            final String fileContent = br.lines().collect(Collectors.joining(EOL, "", EOL));
            assertWithMessage("Unexpected output log")
                .that(fileContent)
                .isEqualTo(expected);
            assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .isEqualTo("");
        }
    }

    @Test
    public void testGenerateXpathSuppressionOptionDefaultTabWidth(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
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
                "       query=\"/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputMainGenerateXpathSuppressionsTabWidth']]"
                    + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='low']\"/>",
                "</suppressions>");

        assertMainReturnCode(0, "-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testGenerateXpathSuppressionOptionCustomTabWidth(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        final String expected = "";

        assertMainReturnCode(0, "-c", getPath("InputMainConfig-xpath-suppressions.xml"),
                "--generate-xpath-suppression", "--tabWidth", "20",
                getPath("InputMainGenerateXpathSuppressionsTabWidth.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    /**
     * Verifies the output of the command line parameter "-J".
     *
     * @param systemErr wrapper for {@code System.err}
     * @param systemOut wrapper for {@code System.out}
     * @throws IOException if I/O exception occurs while reading the test input.
     * @noinspection RedundantThrows
     * @noinspectionreason RedundantThrows - false positive
     */
    @Test
    public void testPrintFullTreeOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String expected = Files.readString(Path.of(
            getPath("InputMainExpectedInputAstTreeStringPrinterJavadoc.txt")))
                .replaceAll("\\\\r\\\\n", "\\\\n")
                .replaceAll("\r\n", "\n");

        assertMainReturnCode(0, "-J", getPath("InputMainAstTreeStringPrinterJavadoc.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData().replaceAll("\\\\r\\\\n", "\\\\n")
                        .replaceAll("\r\n", "\n"))
            .isEqualTo(expected);
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testConflictingOptionsTvsC(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-c", "/google_checks.xml", "-t", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-t' cannot be used with other options." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testConflictingOptionsTvsP(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-p", getPath("InputMainMycheckstyle.properties"), "-t",
                getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-t' cannot be used with other options." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testConflictingOptionsTvsF(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) {
        assertMainReturnCode(-1, "-f", "plain", "-t", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-t' cannot be used with other options." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testConflictingOptionsTvsS(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String outputPath = new File(temporaryFolder, "file.output").getCanonicalPath();

        assertMainReturnCode(-1, "-s", outputPath, "-t", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-t' cannot be used with other options." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testConflictingOptionsTvsO(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String outputPath = new File(temporaryFolder, "file.output").getCanonicalPath();

        assertMainReturnCode(-1, "-o", outputPath, "-t", getPath(""));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Option '-t' cannot be used with other options." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testDebugOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertMainReturnCode(0, "-c", "/google_checks.xml", getPath("InputMain.java"), "-d");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .contains("FINE: Checkstyle debug logging enabled");
        assertWithMessage("Unexpected system error log")
            .that(systemOut.getCapturedData())
            .contains("Audit done.");

    }

    @Test
    public void testExcludeOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String filePath = getFilePath("");
        assertMainReturnCode(-1, "-c", "/google_checks.xml", filePath, "-e", filePath);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Files to process must be specified, found 0." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExcludeOptionFile(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String filePath = getFilePath("InputMain.java");
        assertMainReturnCode(-1, "-c", "/google_checks.xml", filePath, "-e", filePath);
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Files to process must be specified, found 0." + System.lineSeparator());
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExcludeRegexpOption(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
            throws IOException {
        final String filePath = getFilePath("");
        assertMainReturnCode(-1, "-c", "/google_checks.xml", filePath, "-x", ".");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Files to process must be specified, found 0." + System.lineSeparator());
        assertWithMessage("Unexpected output log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
    }

    @Test
    public void testExcludeRegexpOptionFile(@SysErr Capturable systemErr,
            @SysOut Capturable systemOut) throws IOException {
        final String filePath = getFilePath("InputMain.java");
        assertMainReturnCode(-1, "-c", "/google_checks.xml", filePath, "-x", ".");
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("Files to process must be specified, found 0." + System.lineSeparator());
        assertWithMessage("Unexpected output log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
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
        assertWithMessage("Invalid result size")
            .that(result)
            .isNotEmpty();
    }

    @Test
    public void testCustomRootModule(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        TestRootModuleChecker.reset();

        assertMainReturnCode(0, "-c", getPath("InputMainConfig-custom-root-module.xml"),
                getPath("InputMain.java"));
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Invalid Checker state")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
        assertWithMessage("RootModule should be destroyed")
                .that(TestRootModuleChecker.isDestroyed())
                .isTrue();
    }

    @Test
    public void testCustomSimpleRootModule(@SysErr Capturable systemErr) {
        TestRootModuleChecker.reset();
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-custom-simple-root-module.xml"),
                getPath("InputMain.java"));
        final String checkstylePackage = "com.puppycrawl.tools.checkstyle.";
        final LocalizedMessage unableToInstantiateExceptionMessage = new LocalizedMessage(
                Definitions.CHECKSTYLE_BUNDLE,
                getClass(),
                "PackageObjectFactory.unableToInstantiateExceptionMessage",
                "TestRootModuleChecker",
                checkstylePackage
                        + "TestRootModuleChecker, "
                        + "TestRootModuleCheckerCheck, " + checkstylePackage
                        + "TestRootModuleCheckerCheck");
        assertWithMessage(
                "Unexpected system error log")
                        .that(systemErr.getCapturedData())
                        .startsWith(checkstylePackage + "api.CheckstyleException: "
                                + unableToInstantiateExceptionMessage.getMessage());
        assertWithMessage("Invalid checker state")
                .that(TestRootModuleChecker.isProcessed())
                .isFalse();
    }

    @Test
    public void testExceptionOnExecuteIgnoredModuleWithUnknownModuleName(
            @SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-non-existent-classname-ignore.xml"),
                    "--executeIgnoredModules", getPath("InputMain.java"));
        final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                + " cannot initialize module TreeWalker - ";
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .startsWith(cause);
    }

    @Test
    public void testExceptionOnExecuteIgnoredModuleWithBadPropertyValue(
            @SysErr Capturable systemErr) {
        assertMainReturnCode(-2, "-c", getPath("InputMainConfig-TypeName-bad-value.xml"),
                    "--executeIgnoredModules", getPath("InputMain.java"));
        final String cause = "com.puppycrawl.tools.checkstyle.api.CheckstyleException:"
                + " cannot initialize module TreeWalker - ";
        final String causeDetail = "it is not a boolean";
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .startsWith(cause);
        assertWithMessage("Unexpected system error log")
                .that(systemErr.getCapturedData())
                .contains(causeDetail);
    }

    @Test
    public void testNoProblemOnExecuteIgnoredModuleWithBadPropertyValue(
            @SysErr Capturable systemErr) {
        assertMainReturnCode(0, "-c", getPath("InputMainConfig-TypeName-bad-value.xml"),
                    "", getPath("InputMain.java"));
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
                .isEmpty();
    }

    @Test
    public void testMissingFiles(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        assertMainReturnCode(-1);
        final String usage = "Missing required parameter: '<files or folders>'" + EOL + SHORT_USAGE;
        assertWithMessage("Unexpected output log")
            .that(systemOut.getCapturedData())
            .isEqualTo("");
        assertWithMessage("Unexpected system error log")
            .that(systemErr.getCapturedData())
            .isEqualTo(usage);
    }

    @Test
    public void testOutputFormatToStringLowercase() {
        assertWithMessage("expected xml")
            .that(Main.OutputFormat.XML.toString())
            .isEqualTo("xml");
        assertWithMessage("expected plain")
            .that(Main.OutputFormat.PLAIN.toString())
            .isEqualTo("plain");
    }

    @Test
    public void testXmlOutputFormatCreateListener() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.XML.createListener(out,
                OutputStreamOptions.CLOSE);
        assertWithMessage("listener is XMLLogger")
                .that(listener)
                .isInstanceOf(XMLLogger.class);
    }

    @Test
    public void testSarifOutputFormatCreateListener() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.SARIF.createListener(out,
                OutputStreamOptions.CLOSE);
        assertWithMessage("listener is SarifLogger")
                .that(listener)
                .isInstanceOf(SarifLogger.class);
    }

    @Test
    public void testPlainOutputFormatCreateListener() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AuditListener listener = Main.OutputFormat.PLAIN.createListener(out,
                OutputStreamOptions.CLOSE);
        assertWithMessage("listener is DefaultLogger")
                .that(listener)
                .isInstanceOf(DefaultLogger.class);
    }

    /**
     * Helper method to run {@link Main#main(String...)} and verify the exit code.
     * Uses {@link Mockito#mockStatic(Class)} to mock method {@link Runtime#exit(int)}
     * to avoid VM termination.
     *
     * @param expectedExitCode the expected exit code to verify
     * @param arguments the command line arguments
     * @noinspection CallToSystemExit, ResultOfMethodCallIgnored
     * @noinspectionreason CallToSystemExit - test helper method requires workaround to
     *      verify exit code
     * @noinspectionreason ResultOfMethodCallIgnored - Setup for mockito to only
     *                     mock getRuntime to avoid VM termination.
     */
    private static void assertMainReturnCode(int expectedExitCode, String... arguments) {
        final Runtime mock = mock();
        try (MockedStatic<Runtime> runtime = mockStatic(Runtime.class)) {
            runtime.when(Runtime::getRuntime)
                    .thenReturn(mock);
            Main.main(arguments);
        }
        catch (IOException exception) {
            assertWithMessage("Unexpected exception: %s", exception).fail();
        }
        verify(mock).exit(expectedExitCode);
    }

    /**
     * Print stream that shouldn't be closed. The purpose of this class is to ensure that
     * {@code System.out} and {@code System.err} are not closed by Checkstyle.
     */
    private static final class ShouldNotBeClosedStream extends PrintStream {

        private boolean isClosed;

        private ShouldNotBeClosedStream() {
            super(new ByteArrayOutputStream(), false, StandardCharsets.UTF_8);
        }

        @Override
        public void close() {
            isClosed = true;
            super.close();
        }

    }

}
