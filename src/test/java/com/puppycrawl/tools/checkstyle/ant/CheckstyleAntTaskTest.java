////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.ant;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.types.resources.FileResource;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.Violation;
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
        final List<String> loggedMessages = antTask.getLoggedMessages();

        assertWithMessage("Scanning path was not logged")
                .that(loggedMessages.stream().filter(
                        msg -> msg.startsWith("1) Scanning path")).count())
                .isEqualTo(1);

        assertWithMessage("Scanning path was not logged")
                .that(loggedMessages.stream().filter(
                        msg -> msg.startsWith("1) Adding 1 files from path")).count())
                .isEqualTo(1);

        assertWithMessage("Scanning empty was logged")
                .that(loggedMessages.stream().filter(
                        msg -> msg.startsWith("2) Adding 0 files from path ")).count())
                .isEqualTo(0);

        assertWithMessage("Checker is not processed")
                .that(TestRootModuleChecker.isProcessed())
                .isTrue();
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertWithMessage("There are more files to check than expected")
                .that(filesToCheck.size())
                .isEqualTo(1);
        assertWithMessage("The path of file differs from expected")
                .that(filesToCheck.get(0).getAbsolutePath())
                .isEqualTo(getPath(FLAWLESS_INPUT));
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
                .hasSize(8);
        assertWithMessage("The path of file differs from expected")
                .that(filesToCheck.get(5).getAbsolutePath())
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
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo("Must specify 'config'.");
        }
    }

    @Test
    public final void testNonExistentConfig() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath(NOT_EXISTING_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .startsWith("Unable to create Root Module: config");
        }
    }

    @Test
    public final void testEmptyConfigFile() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath("InputCheckstyleAntTaskEmptyConfig.xml"));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .startsWith("Unable to create Root Module: config");
        }
    }

    @Test
    public final void testNoFile() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo(
                            "Must specify at least one of 'file' or nested 'fileset' or 'path'.");
        }
    }

    @Test
    public final void testMaxWarningExceeded() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(WARNING_INPUT)));
        antTask.setMaxWarnings(0);
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo("Got 0 errors and 1 warnings.");
        }
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
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ignored) {
            final Map<String, Object> hashtable = project.getProperties();
            final Object propertyValue = hashtable.get(failurePropertyName);
            assertWithMessage("Number of errors is unexpected")
                    .that(propertyValue)
                    .isEqualTo("Got 2 errors and 0 warnings.");
        }
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

        final Violation auditStartedMessage = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted",
                null, null,
                getClass(), null);
        final Violation auditFinishedMessage = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished",
                null, null,
                getClass(), null);

        final List<String> output = FileUtils.readLines(outputFile, StandardCharsets.UTF_8);
        final String errorMessage = "Content of file with violations differs from expected";
        assertWithMessage(errorMessage)
                .that(output.get(0))
                .isEqualTo(auditStartedMessage.getViolation());
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
                .isEqualTo(auditFinishedMessage.getViolation());
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

        final List<String> output = FileUtils.readLines(outputFile, StandardCharsets.UTF_8);
        final int sizeOfOutputWithNoViolations = 2;
        assertWithMessage("No violations expected")
                .that(output.size())
                .isEqualTo(sizeOfOutputWithNoViolations);
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

        final List<String> output = FileUtils.readLines(outputFile, StandardCharsets.UTF_8);
        final int sizeOfOutputWithNoViolations = 2;
        assertWithMessage("No violations expected")
                .that(output.size())
                .isEqualTo(sizeOfOutputWithNoViolations);
    }

    @Test
    public final void testSimultaneousConfiguration() throws IOException {
        final File file = new File(getPath(CONFIG_FILE));
        final URL url = file.toURI().toURL();

        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(url.toString());
        try {
            antTask.setConfig("Any string value");
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            final String expected = "Attribute 'config' has already been set";
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo(expected);
        }
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
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .startsWith("Error loading Properties file");
        }
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

        final List<String> expected = FileUtils.readLines(
                new File(getPath("ExpectedCheckstyleAntTaskXmlOutput.xml")),
                        StandardCharsets.UTF_8);
        final List<String> actual = FileUtils.readLines(outputFile, StandardCharsets.UTF_8);
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
    public final void testCreateListenerException() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/");
        formatter.setTofile(outputFile);
        antTask.addFormatter(formatter);
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .startsWith("Unable to create listeners: formatters");
        }
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
        try {
            antTask.execute();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .startsWith("Unable to create listeners: formatters");
        }
    }

    @Test
    public void testSetInvalidType() {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        try {
            formatterType.setValue("foo");
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo("foo is not a legal value for this attribute");
        }
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
    public void testSetClasspath() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        final Project project = new Project();
        final String path1 = "firstPath";
        final String path2 = "secondPath";
        antTask.setClasspath(new Path(project, path1));
        antTask.setClasspath(new Path(project, path2));

        final Path classpath = Whitebox.getInternalState(antTask, "classpath");
        final String classpathString = classpath.toString();
        assertWithMessage("Classpath should not be null")
                .that(classpath)
                .isNotNull();
        assertWithMessage("Classpath does not contain provided path")
                .that(classpathString)
                .contains(path1);
        assertWithMessage("Classpath does not contain provided path")
                .that(classpathString)
                .contains(path2);
    }

    @Test
    public void testSetClasspathRef() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setClasspathRef(new Reference(new Project(), "id"));

        assertWithMessage("Classpath should not be null")
                .that((Object) Whitebox.getInternalState(antTask, "classpath"))
                .isNotNull();
    }

    /** This test is created to satisfy pitest, it is hard to emulate Reference by Id. */
    @Test
    public void testSetClasspathRef1() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        final Project project = new Project();
        antTask.setClasspath(new Path(project, "firstPath"));
        antTask.setClasspathRef(new Reference(project, "idXX"));

        assertWithMessage("Classpath should not be null")
                .that((Object) Whitebox.getInternalState(antTask, "classpath"))
                .isNotNull();

        final Path classpath = Whitebox.getInternalState(antTask, "classpath");
        try {
            classpath.list();
            assertWithMessage("Exception is expected")
                    .fail();
        }
        catch (BuildException ex) {
            assertWithMessage("unexpected exception message")
                    .that(ex.getMessage())
                    .isEqualTo("Reference idXX not found.");
        }
    }

    @Test
    public void testCreateClasspath() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();

        assertWithMessage("Invalid classpath")
                .that(antTask.createClasspath().toString())
                .isEmpty();

        antTask.setClasspath(new Path(new Project(), "/path"));

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

    private static class CheckstyleAntTaskLogStub extends CheckstyleAntTask {

        private final List<String> loggedMessages = new ArrayList<>();

        @Override
        public void log(String msg, int msgLevel) {
            loggedMessages.add(msg);
        }

        @Override
        public void log(String msg, Throwable t, int msgLevel) {
            loggedMessages.add(msg);
        }

        public List<String> getLoggedMessages() {
            return Collections.unmodifiableList(loggedMessages);
        }

    }

}
