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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
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
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.SeverityLevelCounter;

/**
 * An implementation of a ANT task for calling checkstyle. See the documentation
 * of the task for usage.
 * @author Oliver Burn
 */
public class CheckstyleAntTask extends Task {
    /** Poor man's enum for an xml formatter. */
    private static final String E_XML = "xml";
    /** Poor man's enum for an plain formatter. */
    private static final String E_PLAIN = "plain";

    /** Suffix for time string. */
    private static final String TIME_SUFFIX = " ms.";

    /** Contains the filesets to process. */
    private final List<FileSet> fileSets = Lists.newArrayList();

    /** Contains the formatters to log to. */
    private final List<Formatter> formatters = Lists.newArrayList();

    /** Contains the Properties to override. */
    private final List<Property> overrideProps = Lists.newArrayList();

    /** Class path to locate class files. */
    private Path classpath;

    /** Name of file to check. */
    private String fileName;

    /** Config file containing configuration. */
    private String configLocation;

    /** Whether to fail build on violations. */
    private boolean failOnViolation = true;

    /** Property to set on violations. */
    private String failureProperty;

    /** The name of the properties file. */
    private File properties;

    /** The maximum number of errors that are tolerated. */
    private int maxErrors;

    /** The maximum number of warnings that are tolerated. */
    private int maxWarnings = Integer.MAX_VALUE;

    /**
     * Whether to omit ignored modules - some modules may log tove
     * their severity depending on their configuration (e.g. WriteTag) so
     * need to be included
     */
    private boolean omitIgnoredModules = true;

    ////////////////////////////////////////////////////////////////////////////
    // Setters for ANT specific attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Tells this task to write failure message to the named property when there
     * is a violation.
     * @param propertyName the name of the property to set
     *                      in the event of an failure.
     */
    public void setFailureProperty(String propertyName) {
        failureProperty = propertyName;
    }

    /**
     * Sets flag - whether to fail if a violation is found.
     * @param fail whether to fail if a violation is found
     */
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
     * Sets the maximum number of warnings allowed. Default is
     * {@link Integer#MAX_VALUE}.
     * @param maxWarnings the maximum number of warnings allowed.
     */
    public void setMaxWarnings(int maxWarnings) {
        this.maxWarnings = maxWarnings;
    }

    /**
     * Adds set of files (nested fileset attribute).
     * @param fileSet the file set to add
     */
    public void addFileset(FileSet fileSet) {
        fileSets.add(fileSet);
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
     * @param classpath the path to locate classes
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

    /**
     * Creates classpath.
     * @return a created path for locating classes
     */
    public Path createClasspath() {
        if (classpath == null) {
            classpath = new Path(getProject());
        }
        return classpath.createPath();
    }

    /**
     * Sets file to be checked.
     * @param file the file to be checked
     */
    public void setFile(File file) {
        fileName = file.getAbsolutePath();
    }

    /**
     * Sets configuration file.
     * @param file the configuration file to use
     */
    public void setConfig(File file) {
        setConfigLocation(file.getAbsolutePath());
    }

    /**
     * Sets URL to the configuration.
     * @param url the URL of the configuration to use
     * @deprecated please use setConfigUrl instead
     */
    @Deprecated
    public void setConfigURL(URL url) {
        setConfigUrl(url);
    }

    /**
     * Sets URL to the configuration.
     * @param url the URL of the configuration to use
     */
    public void setConfigUrl(URL url) {
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

    /**
     * Sets flag - whether to omit ignored modules.
     * @param omit whether to omit ignored modules
     */
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
        properties = props;
    }

    ////////////////////////////////////////////////////////////////////////////
    // The doers
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void execute() {
        final long startTime = System.currentTimeMillis();

        try {
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
            realExecute(version);
        }
        finally {
            final long endTime = System.currentTimeMillis();
            log("Total execution took " + (endTime - startTime) + TIME_SUFFIX,
                Project.MSG_VERBOSE);
        }
    }

    /**
     * Helper implementation to perform execution.
     * @param checkstyleVersion Checkstyle compile version.
     */
    private void realExecute(String checkstyleVersion) {
        // Create the checker
        Checker checker = null;
        try {
            checker = createChecker();

            // setup the listeners
            final AuditListener[] listeners = getListeners();
            for (AuditListener element : listeners) {
                checker.addListener(element);
            }
            final SeverityLevelCounter warningCounter =
                new SeverityLevelCounter(SeverityLevel.WARNING);
            checker.addListener(warningCounter);

            processFiles(checker, warningCounter, checkstyleVersion);
        }
        finally {
            destroyChecker(checker);
        }
    }

    /**
     * Destroy Checker. This method exists only due to bug in cobertura library
     * https://github.com/cobertura/cobertura/issues/170
     * @param checker Checker that was used to process files
     */
    private static void destroyChecker(Checker checker) {
        if (checker != null) {
            checker.destroy();
        }
    }

    /**
     * Scans and processes files by means given checker.
     * @param checker Checker to process files
     * @param warningCounter Checker's counter of warnings
     * @param checkstyleVersion Checkstyle compile version
     */
    private void processFiles(Checker checker, final SeverityLevelCounter warningCounter,
            final String checkstyleVersion) {
        final long startTime = System.currentTimeMillis();
        final List<File> files = scanFileSets();
        final long endTime = System.currentTimeMillis();
        log("To locate the files took " + (endTime - startTime) + TIME_SUFFIX,
            Project.MSG_VERBOSE);

        log("Running Checkstyle " + checkstyleVersion + " on " + files.size()
                + " files", Project.MSG_INFO);
        log("Using configuration " + configLocation, Project.MSG_VERBOSE);

        final int numErrs;

        try {
            final long processingStartTime = System.currentTimeMillis();
            numErrs = checker.process(files);
            final long processingEndTime = System.currentTimeMillis();
            log("To process the files took " + (processingEndTime - processingStartTime)
                + TIME_SUFFIX, Project.MSG_VERBOSE);
        }
        catch (CheckstyleException ex) {
            throw new BuildException("Unable to process files: " + files, ex);
        }
        final int numWarnings = warningCounter.getCount();
        final boolean okStatus = numErrs <= maxErrors && numWarnings <= maxWarnings;

        // Handle the return status
        if (!okStatus) {
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

    /**
     * Creates new instance of {@code Checker}.
     * @return new instance of {@code Checker}
     */
    private Checker createChecker() {
        final Checker checker;
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
        }
        catch (final CheckstyleException ex) {
            throw new BuildException(String.format(Locale.ROOT, "Unable to create a Checker: "
                    + "configLocation {%s}, classpath {%s}.", configLocation, classpath), ex);
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
        final Properties returnValue = new Properties();

        // Load the properties file if specified
        if (properties != null) {
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(properties);
                returnValue.load(inStream);
            }
            catch (final IOException ex) {
                throw new BuildException("Error loading Properties file '"
                        + properties + "'", ex, getLocation());
            }
            finally {
                Closeables.closeQuietly(inStream);
            }
        }

        // override with Ant properties like ${basedir}
        final Map<String, Object> antProps = getProject().getProperties();
        for (Map.Entry<String, Object> entry : antProps.entrySet()) {
            final String value = String.valueOf(entry.getValue());
            returnValue.setProperty(entry.getKey(), value);
        }

        // override with properties specified in subelements
        for (Property p : overrideProps) {
            returnValue.setProperty(p.getKey(), p.getValue());
        }

        return returnValue;
    }

    /**
     * Return the list of listeners set in this task.
     * @return the list of listeners.
     */
    private AuditListener[] getListeners() {
        final int formatterCount = Math.max(1, formatters.size());

        final AuditListener[] listeners = new AuditListener[formatterCount];

        // formatters
        try {
            if (formatters.isEmpty()) {
                final OutputStream debug = new LogOutputStream(this, Project.MSG_DEBUG);
                final OutputStream err = new LogOutputStream(this, Project.MSG_ERR);
                listeners[0] = new DefaultLogger(debug, true, err, true);
            }
            else {
                for (int i = 0; i < formatterCount; i++) {
                    final Formatter formatter = formatters.get(i);
                    listeners[i] = formatter.createListener(this);
                }
            }
        }
        catch (IOException ex) {
            throw new BuildException(String.format(Locale.ROOT, "Unable to create listeners: "
                    + "formatters {%s}.", formatters), ex);
        }
        return listeners;
    }

    /**
     * Returns the list of files (full path name) to process.
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
            final FileSet fileSet = fileSets.get(i);
            final DirectoryScanner scanner = fileSet.getDirectoryScanner(getProject());
            scanner.scan();

            final String[] names = scanner.getIncludedFiles();
            log(i + ") Adding " + names.length + " files from directory "
                    + scanner.getBasedir(), Project.MSG_VERBOSE);

            for (String element : names) {
                final String pathname = scanner.getBasedir() + File.separator
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
        /** My possible values. */
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
        /** The formatter type. */
        private FormatterType type;
        /** The file to output to. */
        private File toFile;
        /** Whether or not the write to the named file. */
        private boolean useFile = true;

        /**
         * Set the type of the formatter.
         * @param type the type
         */
        public void setType(FormatterType type) {
            this.type = type;
        }

        /**
         * Set the file to output to.
         * @param destination destination the file to output to
         */
        public void setTofile(File destination) {
            toFile = destination;
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
            if (type != null
                    && E_XML.equals(type.getValue())) {
                return createXmlLogger(task);
            }
            return createDefaultLogger(task);
        }

        /**
         * Creates default logger.
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
            final FileOutputStream infoStream = new FileOutputStream(toFile);
            return new DefaultLogger(infoStream, true, infoStream, false);
        }

        /**
         * Creates XML logger.
         * @param task the task to possibly log to
         * @return an XMLLogger instance
         * @throws IOException if an error occurs
         */
        private AuditListener createXmlLogger(Task task) throws IOException {
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
        /** The property key. */
        private String key;
        /** The property value. */
        private String value;

        /**
         * Gets key.
         * @return the property key
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets key.
         * @param key sets the property key
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * Gets value.
         * @return the property value
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets value.
         * @param value set the property value
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Sets the property value from a File.
         * @param file set the property value from a File
         */
        public void setFile(File file) {
            value = file.getAbsolutePath();
        }
    }

    /** Represents a custom listener. */
    public static class Listener {
        /** Class name of the listener class. */
        private String className;

        /**
         * Gets class name.
         * @return the class name
         */
        public String getClassname() {
            return className;
        }

        /**
         * Sets class name.
         * @param name set the class name
         */
        public void setClassname(String name) {
            className = name;
        }
    }
}
