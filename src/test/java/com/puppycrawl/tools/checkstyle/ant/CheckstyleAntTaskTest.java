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

package com.puppycrawl.tools.checkstyle.ant;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.types.resources.FileResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.CheckerStub;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.TestRootModuleChecker;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CheckstyleAntTask.class, Closeables.class})
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

        assertTrue("Checker is not processed",
            TestRootModuleChecker.isProcessed());
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
        assertTrue("Checker is not processed",
                TestRootModuleChecker.isProcessed());
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertThat("There more files to check then expected",
                filesToCheck.size(), is(1));
        assertThat("The path of file differs from expected",
                filesToCheck.get(0).getAbsolutePath(), is(getPath(FLAWLESS_INPUT)));
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

        assertEquals("Scanning path was not logged", 1, loggedMessages.stream().filter(
            msg -> msg.getMsg().startsWith("1) Scanning path")).count());

        assertEquals("Scanning path was not logged", 1, loggedMessages.stream().filter(
            msg -> msg.getMsg().startsWith("1) Adding 1 files from path")).count());

        assertEquals("Scanning empty was logged", 0, loggedMessages.stream().filter(
            msg -> msg.getMsg().startsWith("2) Adding 0 files from path ")).count());

        assertTrue("Checker is not processed",
                TestRootModuleChecker.isProcessed());
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertThat("There more files to check then expected",
                filesToCheck.size(), is(1));
        assertThat("The path of file differs from expected",
                filesToCheck.get(0).getAbsolutePath(), is(getPath(FLAWLESS_INPUT)));
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
        assertTrue("Checker is not processed",
                TestRootModuleChecker.isProcessed());
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertThat("There more files to check then expected",
                filesToCheck.size(), is(9));
        assertThat("The path of file differs from expected",
                filesToCheck.get(5).getAbsolutePath(), is(getPath(FLAWLESS_INPUT)));
        assertEquals("Amount of logged messages in unxexpected",
                9, antTask.getLoggedMessages().size());
    }

    @Test
    public final void testCustomRootModule() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.execute();

        assertTrue("Checker is not processed",
                TestRootModuleChecker.isProcessed());
    }

    @Test
    public final void testFileSet() throws IOException {
        TestRootModuleChecker.reset();
        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        final FileSet examinationFileSet = new FileSet();
        examinationFileSet.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.addFileset(examinationFileSet);
        antTask.execute();

        assertTrue("Checker is not processed",
            TestRootModuleChecker.isProcessed());
        final List<File> filesToCheck = TestRootModuleChecker.getFilesToCheck();
        assertThat("There more files to check then expected",
            filesToCheck.size(), is(1));
        assertThat("The path of file differs from expected",
            filesToCheck.get(0).getAbsolutePath(), is(getPath(FLAWLESS_INPUT)));
    }

    @Test
    public final void testNoConfigFile() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("Error message is unexpected",
                    "Must specify 'config'.", ex.getMessage());
        }
    }

    @Test
    public final void testNonExistingConfig() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(getPath(NOT_EXISTING_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Unable to create Root Module: config"));
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
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Unable to create Root Module: config"));
        }
    }

    @Test
    public final void testNoFile() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("Error message is unexpected",
                    "Must specify at least one of 'file' or nested 'fileset' or 'path'.",
                ex.getMessage());
        }
    }

    @Test
    public final void testMaxWarningExceeded() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(WARNING_INPUT)));
        antTask.setMaxWarnings(0);
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("Error message is unexpected",
                    "Got 0 errors and 1 warnings.", ex.getMessage());
        }
    }

    @Test
    public final void testMaxErrors() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxErrors(2);
        antTask.execute();

        assertTrue("Checker is not processed",
            TestRootModuleChecker.isProcessed());
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
            fail("Exception is expected");
        }
        catch (BuildException ignored) {
            final Map<String, Object> hashtable = project.getProperties();
            final Object propertyValue = hashtable.get(failurePropertyName);
            assertEquals("Number of errors is unexpected",
                    "Got 2 errors and 0 warnings.", propertyValue);
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

        assertTrue("Checker is not processed",
            TestRootModuleChecker.isProcessed());
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

        final LocalizedMessage auditStartedMessage = new LocalizedMessage(0,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted",
                null, null,
                getClass(), null);
        final LocalizedMessage auditFinishedMessage = new LocalizedMessage(0,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished",
                null, null,
                getClass(), null);

        final List<String> output = FileUtils.readLines(outputFile, StandardCharsets.UTF_8);
        final String errorMessage = "Content of file with violations differs from expected";
        assertEquals(errorMessage, auditStartedMessage.getMessage(), output.get(0));
        assertTrue(errorMessage, output.get(1).startsWith("[WARN]"));
        assertTrue(errorMessage, output.get(1).endsWith("InputCheckstyleAntTaskError.java:4: "
                + "@incomplete=Some javadoc [WriteTag]"));
        assertTrue(errorMessage, output.get(2).startsWith("[ERROR]"));
        assertTrue(errorMessage, output.get(2).endsWith("InputCheckstyleAntTaskError.java:7: "
                + "Line is longer than 70 characters (found 80). [LineLength]"));
        assertTrue(errorMessage, output.get(3).startsWith("[ERROR]"));
        assertTrue(errorMessage, output.get(3).endsWith("InputCheckstyleAntTaskError.java:9: "
                + "Line is longer than 70 characters (found 81). [LineLength]"));
        assertEquals(errorMessage, auditFinishedMessage.getMessage(), output.get(4));
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
        assertEquals("No violations expected", sizeOfOutputWithNoViolations, output.size());
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
        assertEquals("No violations expected", sizeOfOutputWithNoViolations, output.size());
    }

    @Test
    public final void testSimultaneousConfiguration() throws IOException {
        final File file = new File(getPath(CONFIG_FILE));
        final URL url = file.toURI().toURL();
        final String expected = "Attribute 'config' has already been set";
        try {
            final CheckstyleAntTask antTask = new CheckstyleAntTask();
            antTask.setConfig(url.toString());
            antTask.setConfig(file.toString());
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("Error message is unexpected",
                    expected, ex.getMessage());
        }
    }

    @Test
    public final void testSetPropertiesFile() throws IOException {
        //check if input stream finally closed
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(InputStream.class));

        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setProperties(new File(getPath(
                "InputCheckstyleAntTaskCheckstyleAntTest.properties")));
        antTask.execute();

        assertEquals("Property is not set",
                "ignore", TestRootModuleChecker.getProperty());
        verifyStatic(times(1));
        Closeables.closeQuietly(any(InputStream.class));
    }

    @Test
    public final void testSetPropertiesNonExistingFile() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.setProperties(new File(getPath(NOT_EXISTING_FILE)));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Error loading Properties file"));
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
                new File(getPath("InputCheckstyleAntTaskXmlOutput.xml")), StandardCharsets.UTF_8);
        final List<String> actual = FileUtils.readLines(outputFile, StandardCharsets.UTF_8);
        for (int i = 0; i < expected.size(); i++) {
            final String line = expected.get(i);
            if (!line.startsWith("<checkstyle version") && !line.startsWith("<file")) {
                assertEquals("Content of file with violations differs from expected",
                        line, actual.get(i));
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
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Unable to create listeners: formatters"));
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
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Unable to create listeners: formatters"));
        }
    }

    @Test
    public void testSetInvalidType() {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        try {
            formatterType.setValue("foo");
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("Error message is unexpected",
                    "foo is not a legal value for this attribute", ex.getMessage());
        }
    }

    @Test
    public void testSetClassName() {
        final String customName = "customName";
        final CheckstyleAntTask.Listener listener = new CheckstyleAntTask.Listener();
        listener.setClassname(customName);
        assertEquals("Class name is unexpected",
                customName, listener.getClassname());
    }

    @Test
    public void testSetFileValueByFile() throws IOException {
        final String filename = getPath("InputCheckstyleAntTaskCheckstyleAntTest.properties");
        final CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setFile(new File(filename));
        assertEquals("File path is unexpected",
                property.getValue(), new File(filename).getAbsolutePath());
    }

    @Test
    public void testDefaultLoggerListener() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setUseFile(false);
        assertTrue("Listener instance has unexpected type",
                formatter.createListener(null) instanceof DefaultLogger);
    }

    @Test
    public void testDefaultLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertTrue("Listener instance has unexpected type",
                formatter.createListener(null) instanceof DefaultLogger);
    }

    @Test
    public void testXmlLoggerListener() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        assertTrue("Listener instance has unexpected type",
                formatter.createListener(null) instanceof XMLLogger);
    }

    @Test
    public void testXmlLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertTrue("Listener instance has unexpected type",
                formatter.createListener(null) instanceof XMLLogger);
    }

    @Test
    public void testSetClasspath() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        final Project project = new Project();
        final String path1 = "firstPath";
        final String path2 = "secondPath";
        antTask.setClasspath(new Path(project, path1));
        antTask.setClasspath(new Path(project, path2));

        assertNotNull("Classpath should not be null",
                Whitebox.getInternalState(antTask, "classpath"));
        final Path classpath = (Path) Whitebox.getInternalState(antTask, "classpath");
        assertTrue("Classpath contain provided path", classpath.toString().contains(path1));
        assertTrue("Classpath contain provided path", classpath.toString().contains(path2));
    }

    @Test
    public void testSetClasspathRef() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setClasspathRef(new Reference(new Project(), "id"));

        assertNotNull("Classpath should not be null",
            Whitebox.getInternalState(antTask, "classpath"));
    }

    /** This test is created to satisfy pitest, it is hard to emulate Referece by Id */
    @Test
    public void testSetClasspathRef1() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        final Project project = new Project();
        antTask.setClasspath(new Path(project, "firstPath"));
        antTask.setClasspathRef(new Reference(project, "idXX"));

        try {
            assertNotNull("Classpath should not be null",
                    Whitebox.getInternalState(antTask, "classpath"));
            final Path classpath = (Path) Whitebox.getInternalState(antTask, "classpath");
            classpath.list();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("unexpected exception message",
                    "Reference idXX not found.", ex.getMessage());
        }
    }

    @Test
    public void testCreateClasspath() {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();

        assertEquals("Invalid classpath", "", antTask.createClasspath().toString());

        antTask.setClasspath(new Path(new Project(), "/path"));

        assertEquals("Invalid classpath", "", antTask.createClasspath().toString());
    }

    @Test
    public void testDestroyed() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxWarnings(0);
        antTask.execute();

        assertTrue("Checker is not destroyed",
                TestRootModuleChecker.isDestroyed());
    }

    @Test
    public void testMaxWarnings() throws IOException {
        TestRootModuleChecker.reset();

        final CheckstyleAntTask antTask = getCheckstyleAntTask(CUSTOM_ROOT_CONFIG_FILE);
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxWarnings(0);
        antTask.execute();

        assertTrue("Checker is not processed",
                TestRootModuleChecker.isProcessed());
    }

    @Test
    public void testClassloaderInRootModule() throws IOException {
        TestRootModuleChecker.reset();
        CheckerStub.reset();

        final CheckstyleAntTask antTask =
                getCheckstyleAntTask(
                        "InputCheckstyleAntTaskConfigCustomCheckerRootModule.xml");
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));

        antTask.execute();

        final ClassLoader classLoader = CheckerStub.getClassLoader();
        assertTrue("Classloader is not set or has invalid type",
                classLoader instanceof AntClassLoader);
    }

    @Test
    public void testCheckerException() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTaskStub();
        antTask.setConfig(getPath(CONFIG_FILE));
        antTask.setProject(new Project());
        antTask.setFile(new File(""));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith("Unable to process files:"));
        }
    }

    @Test
    public final void testExecuteLogOutput() throws Exception {
        final CheckstyleAntTaskLogStub antTask = new CheckstyleAntTaskLogStub();
        final URL url = new File(getPath(CONFIG_FILE)).toURI().toURL();
        antTask.setProject(new Project());
        antTask.setConfig(url.toString());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));

        mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(1L);

        antTask.execute();

        final LocalizedMessage auditStartedMessage = new LocalizedMessage(0,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditStarted",
                null, null,
                getClass(), null);
        final LocalizedMessage auditFinishedMessage = new LocalizedMessage(0,
                Definitions.CHECKSTYLE_BUNDLE, "DefaultLogger.auditFinished",
                null, null,
                getClass(), null);

        final List<MessageLevelPair> expectedList = Arrays.asList(
                new MessageLevelPair("checkstyle version ", Project.MSG_VERBOSE),
                new MessageLevelPair("compiled on ", Project.MSG_VERBOSE),
                new MessageLevelPair("Adding standalone file for audit", Project.MSG_VERBOSE),
                new MessageLevelPair("To locate the files took 0 ms.", Project.MSG_VERBOSE),
                new MessageLevelPair("Running Checkstyle ", Project.MSG_INFO),
                new MessageLevelPair("Using configuration ", Project.MSG_VERBOSE),
                new MessageLevelPair(auditStartedMessage.getMessage(), Project.MSG_DEBUG),
                new MessageLevelPair(auditFinishedMessage.getMessage(), Project.MSG_DEBUG),
                new MessageLevelPair("To process the files took 0 ms.", Project.MSG_VERBOSE),
                new MessageLevelPair("Total execution took 0 ms.", Project.MSG_VERBOSE)
        );

        final List<MessageLevelPair> loggedMessages = antTask.getLoggedMessages();

        assertEquals("Amount of log messages is unexpected",
                expectedList.size(), loggedMessages.size());
        for (int i = 0; i < expectedList.size(); i++) {
            final MessageLevelPair expected = expectedList.get(i);
            final MessageLevelPair actual = loggedMessages.get(i);
            assertTrue("Log messages were expected",
                    actual.getMsg().startsWith(expected.getMsg()));
            assertEquals("Log messages were expected",
                    expected.getLevel(), actual.getLevel());
        }

    }

    @Test
    public void testPackageNamesLoaderStreamClosed() throws CheckstyleException {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(InputStream.class));

        PackageNamesLoader.getPackageNames(Thread.currentThread().getContextClassLoader());
        verifyStatic();
        Closeables.closeQuietly(any(InputStream.class));
    }

    private static class CheckstyleAntTaskStub extends CheckstyleAntTask {
        @Override
        protected List<File> scanFileSets() {
            final File mock = PowerMockito.mock(File.class);
            // Assume that I/O error is happened when we try to invoke 'lastModified()' method.
            final Exception expectedError = new RuntimeException("");
            when(mock.lastModified()).thenThrow(expectedError);
            final List<File> list = new ArrayList<>();
            list.add(mock);
            return list;
        }
    }

    private static class CheckstyleAntTaskLogStub extends CheckstyleAntTask {

        private final List<MessageLevelPair> loggedMessages = new ArrayList<>();

        @Override
        public void log(String msg, int msgLevel) {
            loggedMessages.add(new MessageLevelPair(msg, msgLevel));
        }

        @Override
        public void log(String msg, Throwable t, int msgLevel) {
            loggedMessages.add(new MessageLevelPair(msg, msgLevel));

        }

        public List<MessageLevelPair> getLoggedMessages() {
            return Collections.unmodifiableList(loggedMessages);
        }
    }

    private static final class MessageLevelPair {
        private final String msg;
        private final int level;

        MessageLevelPair(String msg, int level) {
            this.msg = msg;
            this.level = level;
        }

        public String getMsg() {
            return msg;
        }

        public int getLevel() {
            return level;
        }
    }

}
