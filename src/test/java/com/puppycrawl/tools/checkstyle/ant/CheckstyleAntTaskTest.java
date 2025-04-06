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

package com.puppycrawl.tools.checkstyle.ant;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.resources.FileResource;
import org.junit.jupiter.api.Test;

import com.google.common.truth.StandardSubjectBuilder;
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.SarifLogger;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.internal.testmodules.CheckstyleAntTaskLogStub;
import com.puppycrawl.tools.checkstyle.internal.testmodules.CheckstyleAntTaskStub;
import com.puppycrawl.tools.checkstyle.internal.testmodules.MessageLevelPair;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestRootModuleChecker;

public class CheckstyleAntTaskTest extends AbstractPathTestSupport {

    private static final String FLAWLESS_INPUT =
            "InputCheckstyleAntTaskFlawless.java";
    private static final String VIOLATED_INPUT =
            "InputCheckstyleAntTaskError.java";
    private static final String WARNING_INPUT =
            "InputCheckstyleAntTaskWarning.java";
    private static final String CONFIG_FILE =
            "InputCheckstyleAntTaskTestChecks.xml";
    private static final String CUSTOM_ROOT_CONFIG_FILE =
            "InputCheckstyleAntTaskConfigCustomRootModule.xml";
    private static final String NOT_EXISTING_FILE = "target/not_existing.xml";
    private static final String FAILURE_PROPERTY_VALUE = "myValue";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/ant/checkstyleanttask/";
    }

    private CheckstyleAntTask getCheckstyleAntTask() throws IOException {
        return getCheckstyleAntTask(CONFIG_FILE);
    }

    private CheckstyleAntTask getCheckstyleAntTask(String configFile) throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath(configFile));
        antTask.setProject(new Project());
        return antTask;
    }

    @Test
    public final void testDefaultFlawless() throws IOException {
        TestRootModuleChecker.reset();
        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.execute();

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
    }

    @Test
    public final void testPathsOneFile() throws IOException {
        // given
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        final FileSet examinationFileSet = new FileSet();
        examinationFileSet.setFile(new File(getPath(FLAWLESS_INPUT)));
        final Path sourcePath = new Path(antTask.getProject());
        sourcePath.addFileset(examinationFileSet);
        antTask.addPath(sourcePath);

        // when
        antTask.execute();

        // then
        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertWithMessage("There are more files to check than expected")
                .that(filesToCheck)
                .hasSize(1);
        assertWithMessage("The path of file differs from expected")
                .that(filesToCheck.get(0).getAbsolutePath())
                .isEqualTo(getPath(FLAWLESS_INPUT));
    }

    @Test
    public final void testPathsFileWithLogVerification() throws IOException {
        // given
        TestRootModuleChecker.reset();
        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        antTask.setConfig(getPath(CUSTOM_ROOT_CONFIG_FILE));
        antTask.setProject(new Project());
        final FileSet examinationFileSet = new FileSet();
        examinationFileSet.setFile(new File(getPath(FLAWLESS_INPUT)));
        final Path sourcePath = new Path(antTask.getProject());
        sourcePath.addFileset(examinationFileSet);
        antTask.addPath(sourcePath);
        antTask.addPath(new Path(new Project()));

        // when
        antTask.execute();

        // then
        final List<MessageLevelPair> loggedMessages = antTask.getLoggedMessages();

        assertWithMessage("Scanning path was not logged")
                .that(loggedMessages.stream().filter(
                        msg -> msg.getMsg().startsWith("1) Scanning path")).count())
                .isEqualTo(1);

        assertWithMessage("Scanning path was not logged")
                .that(loggedMessages.stream().filter(
                        msg -> msg.getMsg().startsWith("1) Adding 1 files from path")).count())
                .isEqualTo(1);

        assertWithMessage("Scanning empty was logged")
                .that(loggedMessages.stream().filter(
                        msg -> msg.getMsg().startsWith("2) Adding 0 files from path ")).count())
                .isEqualTo(0);

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertWithMessage("There are more files to check than expected")
                .that(filesToCheck)
                .hasSize(1);
        assertWithMessage("The path of file differs from expected")
                .that(filesToCheck.get(0).getAbsolutePath())
                .isEqualTo(getPath(FLAWLESS_INPUT));
    }

    @Test
    public final void testBaseDirPresence() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        antTask.setConfig(getPath(CUSTOM_ROOT_CONFIG_FILE));

        final Project project = new Project();
        project.setBaseDir(new File("."));
        antTask.setProject(new Project());

        final FileSet fileSet = new FileSet();
        fileSet.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.addFileset(fileSet);

        antTask.scanFileSets();

        final List<MessageLevelPair> loggedMessages = antTask.getLoggedMessages();

        final String expectedPath = new File(getPath(".")).getAbsolutePath();
        final boolean containsBaseDir = loggedMessages.stream()
                .anyMatch(msg -> msg.getMsg().contains(expectedPath));

        assertWithMessage("Base directory should be present in logs.")
                .that(containsBaseDir)
                .isTrue();
    }

    @Test
    public final void testPathsDirectoryWithNestedFile() throws IOException {
        // given
        TestRootModuleChecker.reset();

        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        antTask.setConfig(getPath(CUSTOM_ROOT_CONFIG_FILE));
        antTask.setProject(new Project());

        final FileResource fileResource = new FileResource(
            antTask.getProject(), getPath(""));
        final Path sourcePath = new Path(antTask.getProject());
        sourcePath.add(fileResource);
        antTask.addPath(sourcePath);

        // when
        antTask.execute();

        // then
        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertWithMessage("There are more files to check than expected")
                .that(filesToCheck)
                .hasSize(9);
        assertWithMessage("The path of file differs from expected")
                .that(filesToCheck.get(6).getAbsolutePath())
                .isEqualTo(getPath(FLAWLESS_INPUT));
        assertWithMessage("Amount of logged messages in unexpected")
                .that(antTask.getLoggedMessages())
                .hasSize(8);
    }

    @Test
    public final void testCustomRootModule() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.execute();

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
    }

    @Test
    public final void testFileSet() throws IOException {
        TestRootModuleChecker.reset();
        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        final FileSet examinationFileSet = new FileSet();
        examinationFileSet.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.addFileset(examinationFileSet);
        antTask.execute();

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertWithMessage("There are more files to check than expected")
                .that(filesToCheck)
                .hasSize(1);
        assertWithMessage("The path of file differs from expected")
                .that(filesToCheck.get(0).getAbsolutePath())
                .isEqualTo(getPath(FLAWLESS_INPUT));
    }

    @Test
    public final void testNoConfigFile() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final Location fileLocation = new Location("build.xml", 42, 10);
        antTask.setLocation(fileLocation);

        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Must specify 'config'.");
        assertWithMessage("Location is missing in exception")
                .that(ex.getLocation())
                .isEqualTo(fileLocation);
    }

    @Test
    public final void testNoFileOrPathSpecified() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setProject(new Project());

        final Location fileLocation = new Location("build.xml", 42, 10);
        antTask.setLocation(fileLocation);

        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");

        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Must specify at least one of 'file' or nested 'fileset' or 'path'.");
        assertWithMessage("Location is missing in the exception")
                .that(ex.getLocation())
                .isEqualTo(fileLocation);
    }

    @Test
    public final void testNonExistentConfig() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath(NOT_EXISTING_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        // Verify exact format of error message (testing String.format mutation)
        final String expectedExceptionFormat = String.format(Locale.ROOT,
                "Unable to create Root Module: config {%s}.", getPath(NOT_EXISTING_FILE));
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo(expectedExceptionFormat);
    }

    @Test
    public final void testEmptyConfigFile() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath("InputCheckstyleAntTaskEmptyConfig.xml"));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        final String expectedMessage = String.format(Locale.ROOT,
                "Unable to create Root Module: config {%s}.",
                getPath("InputCheckstyleAntTaskEmptyConfig.xml"));
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo(expectedMessage);
    }

    @Test
    public final void testNoFile() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Must specify at least one of 'file' or nested 'fileset' or 'path'.");
    }

    @Test
    public final void testMaxWarningExceeded() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(WARNING_INPUT)));
        antTask.setMaxWarnings(0);
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Got 0 errors (max allowed: 0) and 1 warnings.");
    }

    @Test
    public final void testMaxErrorsExceeded() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxErrors(1);

        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Failure message should include maxErrors value")
                .that(ex.getMessage())
                .contains("max allowed: 1");
    }

    @Test
    public final void testMaxErrors() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxErrors(2);
        antTask.execute();

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
    }

    @Test
    public final void testFailureProperty() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath(CONFIG_FILE));
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));

        final Project project = new Project();
        final String failurePropertyName = "myProperty";
        project.setProperty(failurePropertyName, FAILURE_PROPERTY_VALUE);

        antTask.setProject(project);
        antTask.setFailureProperty(failurePropertyName);
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Got 2 errors (max allowed: 0) and 0 warnings.");
        final Map<String, Object> hashtable = project.getProperties();
        final Object propertyValue = hashtable.get(failurePropertyName);
        assertWithMessage("Number of errors is unexpected")
                .that(propertyValue)
                .isEqualTo("Got 2 errors (max allowed: 0) and 0 warnings.");
    }

    @Test
    public final void testOverrideProperty() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        final CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setKey("lineLength.severity");
        property.setValue("ignore");
        antTask.addProperty(property);
        antTask.execute();

        assertWithMessage("Property key should not be empty")
                    .that(property.getKey())
                    .isNotEmpty();
        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
    }

    @Test
    public final void testExecuteIgnoredModules() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setFailOnViolation(false);
        antTask.setExecuteIgnoredModules(true);

        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/ant_task_plain_output.txt");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("plain");
        formatter.setType(formatterType);
        formatter.createListener(null);

        antTask.addFormatter(formatter);
        antTask.execute();

        final ResourceBundle bundle = ResourceBundle.getBundle(
                Definitions.CHECKSTYLE_BUNDLE, Locale.ROOT);
        final String auditStartedMessage = bundle.getString(DefaultLogger.AUDIT_STARTED_MESSAGE);
        final String auditFinishedMessage = bundle.getString(DefaultLogger.AUDIT_FINISHED_MESSAGE);
        final List<String> output = readWholeFile(outputFile);
        final String errorMessage = "Content of file with violations differs from expected";
        assertWithMessage(errorMessage)
                .that(output.get(0))
                .isEqualTo(auditStartedMessage);
        assertWithMessage(errorMessage)
                .that(output.get(1))
                .matches("^\\[WARN].*InputCheckstyleAntTaskError.java:4: .*"
                        + "@incomplete=Some javadoc \\[WriteTag]");
        assertWithMessage(errorMessage)
                .that(output.get(2))
                .matches("^\\[ERROR].*InputCheckstyleAntTaskError.java:7: "
                        + "Line is longer than 70 characters \\(found 80\\). \\[LineLength]");
        assertWithMessage(errorMessage)
                .that(output.get(3))
                .matches("^\\[ERROR].*InputCheckstyleAntTaskError.java:9: "
                        + "Line is longer than 70 characters \\(found 81\\). \\[LineLength]");
        assertWithMessage(errorMessage)
                .that(output.get(4))
                .isEqualTo(auditFinishedMessage);
    }

    @Test
    public final void testConfigurationByUrl() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setProject(new Project());
        final URL url = new File(getPath(CONFIG_FILE)).toURI().toURL();
        antTask.setConfig(url.toString());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));

        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/ant_task_config_by_url.txt");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("plain");
        formatter.setType(formatterType);
        formatter.createListener(null);
        antTask.addFormatter(formatter);

        antTask.execute();

        final List<String> output = readWholeFile(outputFile);
        final int sizeOfOutputWithNoViolations = 2;
        assertWithMessage("No violations expected")
                .that(output)
                .hasSize(sizeOfOutputWithNoViolations);
    }

    @Test
    public final void testConfigurationByResource() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setProject(new Project());
        antTask.setConfig(getPath(CONFIG_FILE));
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));

        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/ant_task_config_by_url.txt");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("plain");
        formatter.setType(formatterType);
        formatter.createListener(null);
        antTask.addFormatter(formatter);

        antTask.execute();

        final List<String> output = readWholeFile(outputFile);
        final int sizeOfOutputWithNoViolations = 2;
        assertWithMessage("No violations expected")
                .that(output)
                .hasSize(sizeOfOutputWithNoViolations);
    }

    @Test
    public final void testSimultaneousConfiguration() throws IOException {
        final File file = new File(getPath(CONFIG_FILE));
        final URL url = file.toURI().toURL();

        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(url.toString());
        final BuildException ex = getExpectedThrowable(BuildException.class,
                () -> antTask.setConfig("Any string value"),
                "BuildException is expected");
        final String expected = "Attribute 'config' has already been set";
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo(expected);
    }

    @Test
    public final void testSetPropertiesFile() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setProperties(new File(getPath(
                "InputCheckstyleAntTaskCheckstyleAntTest.properties")));
        antTask.execute();

        assertWithMessage("Property is not set")
                .that(TestRootModuleChecker.getProperty())
                .isEqualTo("ignore");
    }

    @Test
    public final void testSetPropertiesNonExistentFile() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.setProperties(new File(getPath(NOT_EXISTING_FILE)));
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .startsWith("Error loading Properties file");
    }

    @Test
    public final void testXmlOutput() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setFailOnViolation(false);
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/log.xml");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        formatter.setType(formatterType);
        antTask.addFormatter(formatter);
        antTask.execute();

        final List<String> expected = readWholeFile(
            new File(getPath("ExpectedCheckstyleAntTaskXmlOutput.xml")));
        final List<String> actual = readWholeFile(outputFile);
        for (int i = 0; i < expected.size(); i++) {
            final String line = expected.get(i);
            if (!line.startsWith("<checkstyle version") && !line.startsWith("<file")) {
                assertWithMessage("Content of file with violations differs from expected")
                        .that(actual.get(i))
                        .isEqualTo(line);
            }
        }
    }

    @Test
    public final void testSarifOutput() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setFailOnViolation(false);
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/log.sarif");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("sarif");
        formatter.setType(formatterType);
        antTask.addFormatter(formatter);
        antTask.execute();

        final List<String> expected = readWholeFile(
                new File(getPath("ExpectedCheckstyleAntTaskSarifOutput.sarif")));
        final List<String> actual = readWholeFile(outputFile);
        for (int lineNumber = 0; lineNumber < expected.size(); lineNumber++) {
            final String line = expected.get(lineNumber);
            final StandardSubjectBuilder assertWithMessage =
                    assertWithMessage("Content of file with violations differs from expected");
            if (line.trim().startsWith("\"uri\"")) {
                final String expectedPathEnd = line.split("\\*\\*")[1];
                // normalize windows path
                final String actualLine = actual.get(lineNumber).replaceAll("\\\\", "/");
                assertWithMessage
                        .that(actualLine)
                        .endsWith(expectedPathEnd);
            }
            else {
                assertWithMessage
                        .that(actual.get(lineNumber))
                        .isEqualTo(line);
            }
        }
    }

    @Test
    public final void testCreateListenerException() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/");
        formatter.setTofile(outputFile);
        antTask.addFormatter(formatter);
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Unable to create listeners: formatters "
                        + "{" + List.of(formatter) + "}.");
    }

    @Test
    public final void testCreateListenerExceptionWithXmlLogger() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        formatter.setType(formatterType);
        antTask.addFormatter(formatter);
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .startsWith("Unable to create listeners: formatters");
    }

    @Test
    public final void testCreateListenerExceptionWithSarifLogger() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("sarif");
        formatter.setType(formatterType);
        antTask.addFormatter(formatter);
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .startsWith("Unable to create listeners: formatters");
    }

    @Test
    public void testSetInvalidType() {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        final BuildException ex = getExpectedThrowable(BuildException.class,
                () -> formatterType.setValue("foo"),
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("foo is not a legal value for this attribute");
    }

    @Test
    public void testSetFileValueByFile() throws IOException {
        final String filename = getPath("InputCheckstyleAntTaskCheckstyleAntTest.properties");
        final CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setFile(new File(filename));
        assertWithMessage("File path is unexpected")
                .that(new File(filename).getAbsolutePath())
                .isEqualTo(property.getValue());
    }

    @Test
    public void testDefaultLoggerListener() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setUseFile(false);
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(DefaultLogger.class);
    }

    @Test
    public void testDefaultLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(DefaultLogger.class);
    }

    @Test
    public void testXmlLoggerListener() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(XMLLogger.class);
    }

    @Test
    public void testXmlLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(XMLLogger.class);
    }

    @Test
    public void testDefaultLoggerWithNullToFile() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setTofile(null);
        assertWithMessage("Listener instance has unexpected type")
            .that(formatter.createListener(null))
            .isInstanceOf(DefaultLogger.class);
    }

    @Test
    public void testXmlLoggerWithNullToFile() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setTofile(null);
        assertWithMessage("Listener instance has unexpected type")
            .that(formatter.createListener(null))
            .isInstanceOf(XMLLogger.class);
    }

    @Test
    public void testSarifLoggerListener() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("sarif");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(SarifLogger.class);
    }

    @Test
    public void testSarifLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("sarif");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(SarifLogger.class);
    }

    @Test
    public void testSarifLoggerWithNullToFile() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("sarif");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setTofile(null);
        assertWithMessage("Listener instance has unexpected type")
                .that(formatter.createListener(null))
                .isInstanceOf(SarifLogger.class);
    }

    /**
     * Testing deprecated method.
     */
    @Test
    public void testCreateClasspath() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        final Project mockProject = new Project();
        antTask.setProject(mockProject);

        assertWithMessage("Classpath should belong to the expected project")
                .that(antTask.createClasspath().getProject())
                .isEqualTo(mockProject);

        assertWithMessage("Invalid classpath")
                .that(antTask.createClasspath().toString())
                .isEmpty();
    }

    @Test
    public void testDestroyed() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxWarnings(0);
        antTask.execute();

        assertWithMessage("Checker is not destroyed")
                .that(TestRootModuleChecker.isDestroyed())
                .isTrue();
    }

    @Test
    public void testMaxWarnings() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxWarnings(0);
        antTask.execute();

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
    }

    @Test
    public final void testExecuteLogOutput() throws Exception {
        final ResourceBundle bundle = ResourceBundle.getBundle(
                Definitions.CHECKSTYLE_BUNDLE, Locale.ROOT);
        final List<MessageLevelPair> expectedList = Arrays.asList(
            new MessageLevelPair("checkstyle version .*", Project.MSG_VERBOSE),
            new MessageLevelPair("Adding standalone file for audit", Project.MSG_VERBOSE),
            new MessageLevelPair("File discovery took \\d+ ms.", Project.MSG_VERBOSE),
            new MessageLevelPair("Running Checkstyle on 1 files.", Project.MSG_INFO),
            new MessageLevelPair("Using configuration file:.*", Project.MSG_VERBOSE),
            new MessageLevelPair(bundle.getString(DefaultLogger.AUDIT_STARTED_MESSAGE),
                Project.MSG_DEBUG),
            new MessageLevelPair(bundle.getString(DefaultLogger.AUDIT_FINISHED_MESSAGE),
                Project.MSG_DEBUG),
            new MessageLevelPair("To process the files took \\d+ ms.", Project.MSG_VERBOSE),
            new MessageLevelPair("Total execution took \\d+ ms.", Project.MSG_VERBOSE)
        );

        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        antTask.setProject(new Project());
        antTask.setConfig(new File(getPath(CONFIG_FILE)).toURI().toURL().toString());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.execute();

        final List<MessageLevelPair> loggedMessages = antTask.getLoggedMessages();
        for (int i = 0; i < expectedList.size(); i++) {
            final MessageLevelPair expected = expectedList.get(i);
            final MessageLevelPair actual = loggedMessages.get(i);
            assertWithMessage("Log messages should match")
                .that(actual.getMsg())
                .matches(expected.getMsg());
            assertWithMessage("Log levels should be equal")
                .that(actual.getLevel())
                .isEqualTo(expected.getLevel());
        }
        assertWithMessage("Amount of log messages is not even")
            .that(loggedMessages)
            .hasSize(expectedList.size());
    }

    @Test
    public void testCheckerException() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTaskStub();
        antTask.setConfig(getPath(CONFIG_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(""));
        final BuildException ex = getExpectedThrowable(BuildException.class,
                antTask::execute,
                "BuildException is expected");
        assertWithMessage("Error message is unexpected")
                .that(ex)
                .hasMessageThat()
                        .startsWith("Unable to process files:");
    }

    @Test
    public void testLoggedTime() throws IOException {
        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        antTask.setConfig(getPath(CONFIG_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final long startTime = System.currentTimeMillis();
        antTask.execute();
        final long testingTime = System.currentTimeMillis() - startTime;
        final List<MessageLevelPair> loggedMessages = antTask.getLoggedMessages();

        assertLoggedTime(loggedMessages, testingTime, "Total execution");
        assertLoggedTime(loggedMessages, testingTime, "File discovery took");
        assertLoggedTime(loggedMessages, testingTime, "To process the files");
    }

    private static void assertLoggedTime(List<MessageLevelPair> loggedMessages,
                                         long testingTime, String expectedMsg) {

        final Optional<MessageLevelPair> optionalMessageLevelPair = loggedMessages.stream()
                .filter(msg -> msg.getMsg().startsWith(expectedMsg))
                .findFirst();

        assertWithMessage("Message should be present.")
                .that(optionalMessageLevelPair.isPresent())
                .isTrue();

        assertWithMessage("Logged time in '" + expectedMsg + "' "
                + "must be less than the testing time")
                .that(getNumberFromLine(optionalMessageLevelPair.orElseThrow().getMsg()))
                .isAtMost(testingTime);
    }

    private static List<String> readWholeFile(File outputFile) throws IOException {
        return Files.readAllLines(outputFile.toPath(), StandardCharsets.UTF_8);
    }

    private static long getNumberFromLine(String line) {
        final Matcher matcher = Pattern.compile("(\\d+)").matcher(line);
        matcher.find();
        return Long.parseLong(matcher.group(1));
    }

}
