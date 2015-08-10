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

package com.puppycrawl.tools.checkstyle.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.SeverityLevelCounter;

/**
 * An implementation of a ANT task for calling checkstyle. See the documentation
 * of the task for usage.
 * @author Oliver Burn
 */
public class CheckstyleAntTask extends Task {
    /** poor man's enum for an xml formatter */
    private static final String E_XML = "xml";
    /** poor man's enum for an plain formatter */
    private static final String E_PLAIN = "plain";

    /** class path to locate class files */
    private Path classpath;

    /** name of file to check */
    private String fileName;

    /** config file containing configuration */
    private String configLocation;

    /** whether to fail build on violations */
    private boolean failOnViolation = true;

    /** property to set on violations */
    private String failureProperty;

    /** contains the filesets to process */
    private final List<FileSet> fileSets = Lists.newArrayList();

    /** contains the formatters to log to */
    private final List<Formatter> formatters = Lists.newArrayList();

    /** contains the Properties to override */
    private final List<Property> overrideProps = Lists.newArrayList();

    /** the name of the properties file */
    private File propertiesFile;

    /** the maximum number of errors that are tolerated. */
    private int maxErrors;

    /** the maximum number of warnings that are tolerated. */
    private int maxWarnings = Integer.MAX_VALUE;

    /**
     * whether to omit ignored modules - some modules may log tove
     * their severity depending on their configuration (e.g. WriteTag) so
     * need to be included
     */
    private boolean omitIgnoredModules = true;

    ////////////////////////////////////////////////////////////////////////////
    // Setters for ANT specific attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Tells this task to set the named property to "true" when there
     * is a violation.
     * @param propertyName the name of the property to set
     *                      in the event of an failure.
     */
    public void setFailureProperty(String propertyName) {
        failureProperty = propertyName;
    }

    /** @param fail whether to fail if a violation is found */
    public void setFailOnViolation(boolean fail) {
        failOnViolation = fail;
    }

    /**
     * Sets the maximum number of errors allowed. Default is 0.
     * @param maxErrors the maximum number of errors allowed.
     */
    public void setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
    }

    /**
     * Sets the maximum number of warings allowed. Default is
     * {@link Integer#MAX_VALUE}.
     * @param maxWarnings the maximum number of warnings allowed.
     */
    public void setMaxWarnings(int maxWarnings) {
        this.maxWarnings = maxWarnings;
    }

    /**
     * Adds uset of files (nested fileset attribute).
     * @param fS the file set to add
     */
    public void addFileset(FileSet fS) {
        fileSets.add(fS);
    }

    /**
     * Add a formatter.
     * @param formatter the formatter to add for logging.
     */
    public void addFormatter(Formatter formatter) {
        formatters.add(formatter);
    }

    /**
     * Add an override property.
     * @param property the property to add
     */
    public void addProperty(Property property) {
        overrideProps.add(property);
    }

    /**
     * Set the class path.
     * @param classpath the path to locate cluses
     */
    public void setClasspath(Path classpath) {
        if (this.classpath == null) {
            this.classpath = classpath;
        }
        else {
            this.classpath.append(classpath);
        }
    }

    /**
     * Set the class path from a reference defined elsewhere.
     * @param classpathRef the reference to an instance defining the classpath
     */
    public void setClasspathRef(Reference classpathRef) {
        createClasspath().setRefid(classpathRef);
    }

    /** @return a created path for locating cluses */
    public Path createClasspath() {
        if (classpath == null) {
            classpath = new Path(getProject());
        }
        return classpath.createPath();
    }

    /** @param file the file to be checked */
    public void setFile(File file) {
        fileName = file.getAbsolutePath();
    }

    /** @param file the configuration file to use */
    public void setConfig(File file) {
        setConfigLocation(file.getAbsolutePath());
    }

    /** @param url the URL of the configuration to use */
    public void setConfigURL(URL url) {
        setConfigLocation(url.toExternalForm());
    }

    /**
     * Sets the location of the configuration.
     * @param location the location, which is either a
     */
    private void setConfigLocation(String location) {
        if (configLocation != null) {
            throw new BuildException("Attributes 'config' and 'configURL' "
                    + "must not be set at the same time");
        }
        configLocation = location;
    }

    /** @param omit whether to omit ignored modules */
    public void setOmitIgnoredModules(boolean omit) {
        omitIgnoredModules = omit;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Setters for Checker configuration attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Sets a properties file for use instead
     * of individually setting them.
     * @param props the properties File to use
     */
    public void setProperties(File props) {
        propertiesFile = props;
    }

    ////////////////////////////////////////////////////////////////////////////
    // The doers
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void execute() {
        final long startTime = System.currentTimeMillis();

        try {
            realExecute();
        }
        finally {
            final long endTime = System.currentTimeMillis();
            log("Total execution took " + (endTime - startTime) + " ms.",
                Project.MSG_VERBOSE);
        }
    }

    /**
     * Helper implementation to perform execution.
     */
    private void realExecute() {
        // output version info in debug mode
        final ResourceBundle compilationProperties = ResourceBundle
                .getBundle("checkstylecompilation");
        final String version = compilationProperties
                .getString("checkstyle.compile.version");
        final String compileTimestamp = compilationProperties
                .getString("checkstyle.compile.timestamp");
        log("checkstyle version " + version, Project.MSG_VERBOSE);
        log("compiled on " + compileTimestamp, Project.MSG_VERBOSE);

        // Check for no arguments
        if (fileName == null && fileSets.isEmpty()) {
            throw new BuildException(
                    "Must specify at least one of 'file' or nested 'fileset'.",
                    getLocation());
        }

        if (configLocation == null) {
            throw new BuildException("Must specify 'config'.", getLocation());
        }

        // Create the checker
        Checker checker = null;
        try {
            checker = createChecker();

            final SeverityLevelCounter warningCounter =
                new SeverityLevelCounter(SeverityLevel.WARNING);
            checker.addListener(warningCounter);

            // Process the files
            long startTime = System.currentTimeMillis();
            final List<File> files = scanFileSets();
            long endTime = System.currentTimeMillis();
            log("To locate the files took " + (endTime - startTime) + " ms.",
                Project.MSG_VERBOSE);

            log("Running Checkstyle " + version + " on " + files.size()
                    + " files", Project.MSG_INFO);
            log("Using configuration " + configLocation, Project.MSG_VERBOSE);

            startTime = System.currentTimeMillis();
            final int numErrs = checker.process(files);
            endTime = System.currentTimeMillis();
            log("To process the files took " + (endTime - startTime) + " ms.",
                Project.MSG_VERBOSE);
            final int numWarnings = warningCounter.getCount();
            final boolean ok = numErrs <= maxErrors
                    && numWarnings <= maxWarnings;

            // Handle the return status
            if (!ok) {
                final String failureMsg =
                        "Got " + numErrs + " errors and " + numWarnings
                                + " warnings.";
                if (failureProperty != null) {
                    getProject().setProperty(failureProperty, failureMsg);
                }

                if (failOnViolation) {
                    throw new BuildException(failureMsg, getLocation());
                }
            }
        }
        finally {
            if (checker != null) {
                checker.destroy();
            }
        }
    }

    /**
     * Creates new instance of {@code Checker}.
     * @return new instance of {@code Checker}
     */
    private Checker createChecker() {
        Checker checker;
        try {
            final Properties props = createOverridingProperties();
            final Configuration config =
                ConfigurationLoader.loadConfiguration(
                    configLocation,
                    new PropertiesExpander(props),
                    omitIgnoredModules);

            final DefaultContext context = new DefaultContext();
            final ClassLoader loader = new AntClassLoader(getProject(),
                    classpath);
            context.add("classloader", loader);

            final ClassLoader moduleClassLoader =
                Checker.class.getClassLoader();
            context.add("moduleClassLoader", moduleClassLoader);

            checker = new Checker();

            checker.contextualize(context);
            checker.configure(config);

            // setup the listeners
            final AuditListener[] listeners = getListeners();
            for (AuditListener element : listeners) {
                checker.addListener(element);
            }
        }
        catch (final Exception e) {
            throw new BuildException("Unable to create a Checker: "
                    + e.getMessage(), e);
        }

        return checker;
    }

    /**
     * Create the Properties object based on the arguments specified
     * to the ANT task.
     * @return the properties for property expansion expansion
     * @throws BuildException if an error occurs
     */
    private Properties createOverridingProperties() {
        final Properties retVal = new Properties();

        // Load the properties file if specified
        if (propertiesFile != null) {
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(propertiesFile);
                retVal.load(inStream);
            }
            catch (final IOException e) {
                throw new BuildException("Error loading Properties file '"
                        + propertiesFile + "'", e, getLocation());
            }
            finally {
                Closeables.closeQuietly(inStream);
            }
        }

        // override with Ant properties like ${basedir}
        final Map<String, Object> antProps = getProject().getProperties();
        for (Map.Entry<String, Object> entry : antProps.entrySet()) {
            final String value = String.valueOf(entry.getValue());
            retVal.setProperty(entry.getKey(), value);
        }

        // override with properties specified in subelements
        for (Property p : overrideProps) {
            retVal.setProperty(p.getKey(), p.getValue());
        }

        return retVal;
    }

    /**
     * Return the list of listeners set in this task.
     * @return the list of listeners.
     * @throws IOException if an error occurs
     */
    private AuditListener[] getListeners() throws IOException {
        final int formatterCount = Math.max(1, formatters.size());

        final AuditListener[] listeners = new AuditListener[formatterCount];

        // formatters
        if (formatters.isEmpty()) {
            final OutputStream debug = new LogOutputStream(this,
                    Project.MSG_DEBUG);
            final OutputStream err = new LogOutputStream(this, Project.MSG_ERR);
            listeners[0] = new DefaultLogger(debug, true, err, true);
        }
        else {
            for (int i = 0; i < formatterCount; i++) {
                final Formatter formatter = formatters.get(i);
                listeners[i] = formatter.createListener(this);
            }
        }
        return listeners;
    }

    /**
     * returns the list of files (full path name) to process.
     * @return the list of files included via the filesets.
     */
    protected List<File> scanFileSets() {
        final List<File> list = Lists.newArrayList();
        if (fileName != null) {
            // oops we've got an additional one to process, don't
            // forget it. No sweat, it's fully resolved via the setter.
            log("Adding standalone file for audit", Project.MSG_VERBOSE);
            list.add(new File(fileName));
        }
        for (int i = 0; i < fileSets.size(); i++) {
            final FileSet fs = fileSets.get(i);
            final DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            ds.scan();

            final String[] names = ds.getIncludedFiles();
            log(i + ") Adding " + names.length + " files from directory "
                    + ds.getBasedir(), Project.MSG_VERBOSE);

            for (String element : names) {
                final String pathname = ds.getBasedir() + File.separator
                        + element;
                list.add(new File(pathname));
            }
        }

        return list;
    }

    /**
     * Poor mans enumeration for the formatter types.
     * @author Oliver Burn
     */
    public static class FormatterType extends EnumeratedAttribute {
        /** my possible values */
        private static final String[] VALUES = {E_XML, E_PLAIN};

        @Override
        public String[] getValues() {
            return VALUES.clone();
        }
    }

    /**
     * Details about a formatter to be used.
     * @author Oliver Burn
     */
    public static class Formatter {
        /** the formatter type */
        private FormatterType formatterType;
        /** the file to output to */
        private File toFile;
        /** Whether or not the write to the named file. */
        private boolean useFile = true;

        /**
         * Set the type of the formatter.
         * @param type the type
         */
        public void setType(FormatterType type) {
            final String val = type.getValue();
            if (!E_XML.equals(val) && !E_PLAIN.equals(val)) {
                throw new BuildException("Invalid formatter type: " + val);
            }

            formatterType = type;
        }

        /**
         * Set the file to output to.
         * @param to the file to output to
         */
        public void setTofile(File to) {
            toFile = to;
        }

        /**
         * Sets whether or not we write to a file if it is provided.
         * @param use whether not not to use provided file.
         */
        public void setUseFile(boolean use) {
            useFile = use;
        }

        /**
         * Creates a listener for the formatter.
         * @param task the task running
         * @return a listener
         * @throws IOException if an error occurs
         */
        public AuditListener createListener(Task task) throws IOException {
            if (formatterType != null
                    && E_XML.equals(formatterType.getValue())) {
                return createXMLLogger(task);
            }
            return createDefaultLogger(task);
        }

        /**
         * @param task the task to possibly log to
         * @return a DefaultLogger instance
         * @throws IOException if an error occurs
         */
        private AuditListener createDefaultLogger(Task task)
            throws IOException {
            if (toFile == null || !useFile) {
                return new DefaultLogger(
                    new LogOutputStream(task, Project.MSG_DEBUG),
                    true, new LogOutputStream(task, Project.MSG_ERR), true);
            }
            return new DefaultLogger(new FileOutputStream(toFile), true);
        }

        /**
         * @param task the task to possibly log to
         * @return an XMLLogger instance
         * @throws IOException if an error occurs
         */
        private AuditListener createXMLLogger(Task task) throws IOException {
            if (toFile == null || !useFile) {
                return new XMLLogger(new LogOutputStream(task,
                        Project.MSG_INFO), true);
            }
            return new XMLLogger(new FileOutputStream(toFile), true);
        }
    }

    /**
     * Represents a property that consists of a key and value.
     */
    public static class Property {
        /** the property key */
        private String key;
        /** the property value */
        private String value;

        /** @return the property key */
        public String getKey() {
            return key;
        }

        /** @param key sets the property key */
        public void setKey(String key) {
            this.key = key;
        }

        /** @return the property value */
        public String getValue() {
            return value;
        }

        /** @param value set the property value */
        public void setValue(String value) {
            this.value = value;
        }

        /** @param file set the property value from a File */
        public void setFile(File file) {
            setValue(file.getAbsolutePath());
        }
    }

    /** Represents a custom listener. */
    public static class Listener {
        /** classname of the listener class */
        private String classname;

        /** @return the classname */
        public String getClassname() {
            return classname;
        }

        /** @param classname set the classname */
        public void setClassname(String classname) {
            this.classname = classname;
        }
    }
}
