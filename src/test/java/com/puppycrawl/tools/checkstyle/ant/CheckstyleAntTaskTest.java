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

package com.puppycrawl.tools.checkstyle.ant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.XMLLogger;

public class CheckstyleAntTaskTest extends BaseCheckTestSupport {

    private static final String FLAWLESS_INPUT = "ant/InputCheckstyleAntTaskFlawless.java";
    private static final String VIOLATED_INPUT = "ant/InputCheckstyleAntTaskError.java";
    private static final String CONFIG_FILE = "ant/ant_task_test_checks.xml";
    private static final String NOT_EXISTING_FILE = "target/not_existing.xml";
    private static final String FAILURE_PROPERTY_VALUE = "myValue";

    private CheckstyleAntTask getCheckstyleAntTask() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(new File(getPath(CONFIG_FILE)));
        antTask.setProject(new Project());
        return antTask;
    }

    @Test
    public final void testDefaultFlawless() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.execute();
    }

    @Test
    public final void testFileSet() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        final FileSet examinationFileSet = new FileSet();
        examinationFileSet.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.addFileset(examinationFileSet);
        antTask.execute();
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
            assertEquals("Must specify 'config'.", ex.getMessage());
        }
    }

    @Test
    public final void testNonExistingConfig() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(new File(getPath(NOT_EXISTING_FILE)));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue(ex.getMessage().startsWith("Unable to create a Checker: configLocation"));
        }
    }

    @Test
    public final void testEmptyConfigFile() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(new File(getPath("ant/empty_config.xml")));
        antTask.setProject(new Project());
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue(ex.getMessage().startsWith("Unable to create a Checker: configLocation"));
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
            assertEquals("Must specify at least one of 'file' or nested 'fileset'.",
                ex.getMessage());
        }
    }

    @Test
    public final void testMaxWarningExeeded() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath("ant/InputCheckstyleAntTaskWarning.java")));
        antTask.setMaxWarnings(0);
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals("Got 0 errors and 1 warnings.", ex.getMessage());
        }
    }

    @Test
    public final void testMaxErrors() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setMaxErrors(2);
        antTask.execute();
    }

    @Test
    public final void testFailureProperty() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setConfig(new File(getPath(CONFIG_FILE)));
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
        catch (BuildException ex) {
            final Map<String, Object> hashtable = project.getProperties();
            final Object propertyValue = hashtable.get(failurePropertyName);
            assertEquals("Got 2 errors and 0 warnings.", propertyValue);
        }
    }

    @Test
    public final void testOverrideProperty() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        final CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setKey("lineLength.severity");
        property.setValue("ignore");
        antTask.addProperty(property);
        antTask.execute();
    }

    @Test
    public final void testOmitIgnoredModules() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setFailOnViolation(false);
        antTask.setOmitIgnoredModules(false);

        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        final File outputFile = new File("target/ant_task_plain_output.txt");
        formatter.setTofile(outputFile);
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("plain");
        formatter.setType(formatterType);
        formatter.createListener(null);

        antTask.addFormatter(formatter);
        antTask.execute();

        final List<String> output = FileUtils.readLines(outputFile);
        assertEquals("Starting audit...", output.get(0));
        assertTrue(output.get(1).startsWith("[WARN]"));
        assertTrue(output.get(1).endsWith("InputCheckstyleAntTaskError.java:4: "
                + "@incomplete=Some javadoc [WriteTag]"));
        assertTrue(output.get(2).startsWith("[ERROR]"));
        assertTrue(output.get(2).endsWith("InputCheckstyleAntTaskError.java:7: "
                + "Line is longer than 70 characters (found 80). [LineLength]"));
        assertTrue(output.get(3).startsWith("[ERROR]"));
        assertTrue(output.get(3).endsWith("InputCheckstyleAntTaskError.java:9: "
                + "Line is longer than 70 characters (found 81). [LineLength]"));
        assertEquals("Audit done.", output.get(4));
    }

    @Test
    public final void testConfigurationByUrl() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setProject(new Project());
        final URL url = new File(getPath(CONFIG_FILE)).toURI().toURL();
        antTask.setConfigURL(url);
        antTask.setFile(new File(getPath(FLAWLESS_INPUT)));
        antTask.execute();
    }

    @Test
    public final void testSimultaneousConfiguration() throws IOException {
        CheckstyleAntTask antTask;
        final File file = new File(getPath(CONFIG_FILE));
        final URL url = file.toURI().toURL();
        final String expected =
                "Attributes 'config' and 'configURL' must not be set at the same time";
        try {
            antTask = new CheckstyleAntTask();
            antTask.setConfigUrl(url);
            antTask.setConfig(file);
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals(expected, ex.getMessage());
        }
        try {
            antTask = new CheckstyleAntTask();
            antTask.setConfig(file);
            antTask.setConfigUrl(url);
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertEquals(expected, ex.getMessage());
        }
    }

    @Test
    public final void testSetPropertiesFile() throws IOException {
        final CheckstyleAntTask antTask = getCheckstyleAntTask();
        antTask.setFile(new File(getPath(VIOLATED_INPUT)));
        antTask.setProperties(new File(getPath("ant/checkstyleAntTest.properties")));
        antTask.execute();
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
            assertTrue(ex.getMessage().startsWith("Error loading Properties file"));
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
                new File(getPath("ant/ant_task_xml_output.xml")));
        final List<String> actual = FileUtils.readLines(outputFile);
        for (int i = 0; i < expected.size(); i++) {
            final String line = expected.get(i);
            if (!line.startsWith("<checkstyle version") && !line.startsWith("<file")) {
                assertEquals(line, actual.get(i));
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
            assertTrue(ex.getMessage().startsWith("Unable to create listeners: formatters"));
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
            assertEquals("foo is not a legal value for this attribute", ex.getMessage());
        }
    }

    @Test
    public void testSetClassName() {
        final String customName = "customName";
        final CheckstyleAntTask.Listener listener = new CheckstyleAntTask.Listener();
        listener.setClassname(customName);
        assertEquals(customName, listener.getClassname());
    }

    @Test
    public void testSetFileValueByFile() throws IOException {
        final String filename = getPath("ant/checkstyleAntTest.properties");
        final CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setFile(new File(filename));
        assertEquals(property.getValue(), new File(filename).getAbsolutePath());
    }

    @Test
    public void testDefaultLoggerListener() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setUseFile(false);
        assertTrue(formatter.createListener(null) instanceof DefaultLogger);
    }

    @Test
    public void testDefaultLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertTrue(formatter.createListener(null) instanceof DefaultLogger);
    }

    @Test
    public void testXmlLoggerListener() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        assertTrue(formatter.createListener(null) instanceof XMLLogger);
    }

    @Test
    public void testXmlLoggerListenerWithToFile() throws IOException {
        final CheckstyleAntTask.FormatterType formatterType = new CheckstyleAntTask.FormatterType();
        formatterType.setValue("xml");
        final CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
        formatter.setType(formatterType);
        formatter.setUseFile(false);
        formatter.setTofile(new File("target/"));
        assertTrue(formatter.createListener(null) instanceof XMLLogger);
    }

    @Test
    public void testSetClasspath() {
        // temporary fake test
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        final Project project = new Project();
        antTask.setClasspath(new Path(project, "/"));
        antTask.setClasspath(new Path(project, "/checkstyle"));
        antTask.setClasspathRef(new Reference());
    }

    @Test
    public void testSetClasspathRef() {
        // temporary fake test
        final CheckstyleAntTask antTask = new CheckstyleAntTask();
        antTask.setClasspathRef(new Reference());
    }

    @Test
    public void testCheckerException() throws IOException {
        final CheckstyleAntTask antTask = new CheckstyleAntTaskStub();
        antTask.setConfig(new File(getPath(CONFIG_FILE)));
        antTask.setProject(new Project());
        antTask.setFile(new File(""));
        try {
            antTask.execute();
            fail("Exception is expected");
        }
        catch (BuildException ex) {
            assertTrue(ex.getMessage().startsWith("Unable to process files:"));
        }
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
}
