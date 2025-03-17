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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.SarifLogger;
import com.puppycrawl.tools.checkstyle.ThreadModeSettings;
import com.puppycrawl.tools.checkstyle.XMLLogger;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.RootModule;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.SeverityLevelCounter;

/**
 * An implementation of an ANT task for calling checkstyle. See the documentation
 * of the task for usage.
 */
public class CheckstyleAntTask extends Task {

    /** Poor man's enum for an xml formatter. */
    private static final String E_XML = "xml";
    /** Poor man's enum for a plain formatter. */
    private static final String E_PLAIN = "plain";
    /** Poor man's enum for a sarif formatter. */
    private static final String E_SARIF = "sarif";

    /** Suffix for time string. */
    private static final String TIME_SUFFIX = " ms.";

    /** Contains the paths to process. */
    private final List<Path> paths = new ArrayList<>();

    /** Contains the filesets to process. */
    private final List<FileSet> fileSets = new ArrayList<>();

    /** Contains the formatters to log to. */
    private final List<Formatter> formatters = new ArrayList<>();

    /** Contains the Properties to override. */
    private final List<Property> overrideProps = new ArrayList<>();

    /** Name of file to check. */
    private String fileName;

    /** Config file containing configuration. */
    private String config;

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
     * Whether to execute ignored modules - some modules may log above
     * their severity depending on their configuration (e.g. WriteTag) so
     * need to be included
     */
    private boolean executeIgnoredModules;

    ////////////////////////////////////////////////////////////////////////////
    // Setters for ANT specific attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Tells this task to write failure message to the named property when there
     * is a violation.
     *
     * @param propertyName the name of the property to set
     *                      in the event of a failure.
     */
    public void setFailureProperty(String propertyName) {
        failureProperty = propertyName;
    }

    /**
     * Sets flag - whether to fail if a violation is found.
     *
     * @param fail whether to fail if a violation is found
     */
    public void setFailOnViolation(boolean fail) {
        failOnViolation = fail;
    }

    /**
     * Sets the maximum number of errors allowed. Default is 0.
     *
     * @param maxErrors the maximum number of errors allowed.
     */
    public void setMaxErrors(int maxErrors) {
        this.maxErrors = 0;
    }

    /**
     * Sets the maximum number of warnings allowed. Default is
     * {@link Integer#MAX_VALUE}.
     *
     * @param maxWarnings the maximum number of warnings allowed.
     */
    public void setMaxWarnings(int maxWarnings) {
        this.maxWarnings = maxWarnings;
    }

    /**
     * Adds a path.
     *
     * @param path the path to add.
     */
    public void addPath(Path path) {
        paths.add(path);
    }

    /**
     * Adds set of files (nested fileset attribute).
     *
     * @param fileSet the file set to add
     */
    public void addFileset(FileSet fileSet) {
        fileSets.add(fileSet);
    }

    /**
     * Add a formatter.
     *
     * @param formatter the formatter to add for logging.
     */
    public void addFormatter(Formatter formatter) {
        formatters.add(formatter);
    }

    /**
     * Add an override property.
     *
     * @param property the property to add
     */
    public void addProperty(Property property) {
        overrideProps.add(property);
    }

    /**
     * Creates classpath.
     *
     * @return a created path for locating classes
     * @deprecated left in implementation until #12556 only to allow users to migrate to new gradle
     *     plugins. This method will be removed in Checkstyle 11.x.x .
     * @noinspection DeprecatedIsStillUsed
     * @noinspectionreason DeprecatedIsStillUsed - until #12556
     */
    @Deprecated(since = "10.7.0")
    public Path createClasspath() {
        return new Path(getProject());
    }

    /**
     * Sets file to be checked.
     *
     * @param file the file to be checked
     */
    public void setFile(File file) {
        fileName = file.getAbsolutePath();
    }

    /**
     * Sets configuration file.
     *
     * @param configuration the configuration file, URL, or resource to use
     * @throws BuildException when config was already set
     */
    public void setConfig(String configuration) {
        if (config != null) {
            throw new BuildException("Attribute 'config' has already been set");
        }
        config = configuration;
    }

    /**
     * Sets flag - whether to execute ignored modules.
     *
     * @param omit whether to execute ignored modules
     */
    public void setExecuteIgnoredModules(boolean omit) {
        executeIgnoredModules = omit;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Setters for Root Module's configuration attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Sets a properties file for use instead
     * of individually setting them.
     *
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
            final String version = CheckstyleAntTask.class.getPackage().getImplementationVersion();

            log("checkstyle version " + version, Project.MSG_VERBOSE);

            // Check for no arguments
            if (fileName == null
                    && fileSets.isEmpty()
                    && paths.isEmpty()) {
                throw new BuildException(
                        "Must specify at least one of 'file' or nested 'fileset' or 'path'.",
                        getLocation());
            }
            if (config == null) {
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
     *
     * @param checkstyleVersion Checkstyle compile version.
     */
    private void realExecute(String checkstyleVersion) {
        // Create the root module
        RootModule rootModule = null;
        try {
            rootModule = createRootModule();

            // setup the listeners
            final AuditListener[] listeners = getListeners();
            for (AuditListener element : listeners) {
                rootModule.addListener(element);
            }
            final SeverityLevelCounter warningCounter =
                new SeverityLevelCounter(SeverityLevel.WARNING);
            rootModule.addListener(warningCounter);

            processFiles(rootModule, warningCounter, checkstyleVersion);
        }
        finally {
            if (rootModule != null) {
                rootModule.destroy();
            }
        }
    }

    /**
     * Scans and processes files by means given root module.
     *
     * @param rootModule Root module to process files
     * @param warningCounter Root Module's counter of warnings
     * @param checkstyleVersion Checkstyle compile version
     * @throws BuildException if the files could not be processed,
     *     or if the build failed due to violations.
     */
    private void processFiles(RootModule rootModule, final SeverityLevelCounter warningCounter,
            final String checkstyleVersion) {
        final long startTime = System.currentTimeMillis();
        final List<File> files = getFilesToCheck();
        final long endTime = System.currentTimeMillis();
        log("To locate the files took " + (endTime - startTime) + TIME_SUFFIX,
            Project.MSG_VERBOSE);

        log("Running Checkstyle "
                + Objects.toString(checkstyleVersion, "")
                + " on " + files.size()
                + " files", Project.MSG_INFO);
        log("Using configuration " + config, Project.MSG_VERBOSE);

        final int numErrs;

        try {
            final long processingStartTime = System.currentTimeMillis();
            numErrs = rootModule.process(files);
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
     * Creates new instance of the root module.
     *
     * @return new instance of the root module
     * @throws BuildException if the root module could not be created.
     */
    private RootModule createRootModule() {
        final RootModule rootModule;
        try {
            final Properties props = createOverridingProperties();
            final ThreadModeSettings threadModeSettings =
                    ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
            final ConfigurationLoader.IgnoredModulesOptions ignoredModulesOptions;
            if (executeIgnoredModules) {
                ignoredModulesOptions = ConfigurationLoader.IgnoredModulesOptions.EXECUTE;
            }
            else {
                ignoredModulesOptions = ConfigurationLoader.IgnoredModulesOptions.OMIT;
            }

            final Configuration configuration = ConfigurationLoader.loadConfiguration(config,
                    new PropertiesExpander(props), ignoredModulesOptions, threadModeSettings);

            final ClassLoader moduleClassLoader =
                Checker.class.getClassLoader();

            final ModuleFactory factory = new PackageObjectFactory(
                    Checker.class.getPackage().getName() + ".", moduleClassLoader);

            rootModule = (RootModule) factory.createModule(configuration.getName());
            rootModule.setModuleClassLoader(moduleClassLoader);
            rootModule.configure(configuration);
        }
        catch (final CheckstyleException ex) {
            throw new BuildException(String.format(Locale.ROOT, "Unable to create Root Module: "
                    + "config {%s}.", config), ex);
        }
        return rootModule;
    }

    /**
     * Create the Properties object based on the arguments specified
     * to the ANT task.
     *
     * @return the properties for property expansion
     * @throws BuildException if the properties file could not be loaded.
     */
    private Properties createOverridingProperties() {
        final Properties returnValue = new Properties();

        // Load the properties file if specified
        if (properties != null) {
            try (InputStream inStream = Files.newInputStream(properties.toPath())) {
                returnValue.load(inStream);
            }
            catch (final IOException ex) {
                throw new BuildException("Error loading Properties file '"
                        + properties + "'", ex, getLocation());
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
     * Return the array of listeners set in this task.
     *
     * @return the array of listeners.
     * @throws BuildException if the listeners could not be created.
     */
    private AuditListener[] getListeners() {
        final int formatterCount = Math.max(1, formatters.size());

        final AuditListener[] listeners = new AuditListener[formatterCount];

        // formatters
        try {
            if (formatters.isEmpty()) {
                final OutputStream debug = new LogOutputStream(this, Project.MSG_DEBUG);
                final OutputStream err = new LogOutputStream(this, Project.MSG_ERR);
                listeners[0] = new DefaultLogger(debug, OutputStreamOptions.CLOSE,
                        err, OutputStreamOptions.CLOSE);
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
     *
     * @return the list of files included via the fileName, filesets and paths.
     */
    private List<File> getFilesToCheck() {
        final List<File> allFiles = new ArrayList<>();
        if (fileName != null) {
            // oops, we've got an additional one to process, don't
            // forget it. No sweat, it's fully resolved via the setter.
            log("Adding standalone file for audit", Project.MSG_VERBOSE);
            allFiles.add(new File(fileName));
        }

        final List<File> filesFromFileSets = scanFileSets();
        allFiles.addAll(filesFromFileSets);

        final List<File> filesFromPaths = scanPaths();
        allFiles.addAll(filesFromPaths);

        return allFiles;
    }

    /**
     * Retrieves all files from the defined paths.
     *
     * @return a list of files defined via paths.
     */
    private List<File> scanPaths() {
        final List<File> allFiles = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            final Path currentPath = paths.get(i);
            final List<File> pathFiles = scanPath(currentPath, i + 1);
            allFiles.addAll(pathFiles);
        }

        return allFiles;
    }

    /**
     * Scans the given path and retrieves all files for the given path.
     *
     * @param path      A path to scan.
     * @param pathIndex The index of the given path. Used in log messages only.
     * @return A list of files, extracted from the given path.
     */
    private List<File> scanPath(Path path, int pathIndex) {
        final String[] resources = path.list();
        log(pathIndex + ") Scanning path " + path, Project.MSG_VERBOSE);
        final List<File> allFiles = new ArrayList<>();
        int concreteFilesCount = 0;

        for (String resource : resources) {
            final File file = new File(resource);
            if (file.isFile()) {
                concreteFilesCount++;
                allFiles.add(file);
            }
            else {
                final DirectoryScanner scanner = new DirectoryScanner();
                scanner.setBasedir(file);
                scanner.scan();
                final List<File> scannedFiles = retrieveAllScannedFiles(scanner, pathIndex);
                allFiles.addAll(scannedFiles);
            }
        }

        if (concreteFilesCount > 0) {
            log(String.format(Locale.ROOT, "%d) Adding %d files from path %s",
                pathIndex, concreteFilesCount, path), Project.MSG_VERBOSE);
        }

        return allFiles;
    }

    /**
     * Returns the list of files (full path name) to process.
     *
     * @return the list of files included via the filesets.
     */
    protected List<File> scanFileSets() {
        final List<File> allFiles = new ArrayList<>();

        for (int i = 0; i < fileSets.size(); i++) {
            final FileSet fileSet = fileSets.get(i);
            final DirectoryScanner scanner = fileSet.getDirectoryScanner(getProject());
            final List<File> scannedFiles = retrieveAllScannedFiles(scanner, i);
            allFiles.addAll(scannedFiles);
        }

        return allFiles;
    }

    /**
     * Retrieves all matched files from the given scanner.
     *
     * @param scanner  A directory scanner. Note, that {@link DirectoryScanner#scan()}
     *                 must be called before calling this method.
     * @param logIndex A log entry index. Used only for log messages.
     * @return A list of files, retrieved from the given scanner.
     */
    private List<File> retrieveAllScannedFiles(DirectoryScanner scanner, int logIndex) {
        final String[] fileNames = scanner.getIncludedFiles();
        log(String.format(Locale.ROOT, "%d) Adding %d files from directory %s",
            logIndex, fileNames.length, scanner.getBasedir()), Project.MSG_VERBOSE);

        return Arrays.stream(fileNames)
            .map(name -> scanner.getBasedir() + File.separator + name)
            .map(File::new)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Poor man enumeration for the formatter types.
     */
    public static class FormatterType extends EnumeratedAttribute {

        /** My possible values. */
        private static final String[] VALUES = {E_XML, E_PLAIN, E_SARIF};

        @Override
        public String[] getValues() {
            return VALUES.clone();
        }

    }

    /**
     * Details about a formatter to be used.
     */
    public static class Formatter {

        /** The formatter type. */
        private FormatterType type;
        /** The file to output to. */
        private File toFile;
        /** Whether or not to write to the named file. */
        private boolean useFile = true;

        /**
         * Set the type of the formatter.
         *
         * @param type the type
         */
        public void setType(FormatterType type) {
            this.type = type;
        }

        /**
         * Set the file to output to.
         *
         * @param destination destination the file to output to
         */
        public void setTofile(File destination) {
            toFile = destination;
        }

        /**
         * Sets whether or not we write to a file if it is provided.
         *
         * @param use whether not to use provided file.
         */
        public void setUseFile(boolean use) {
            useFile = use;
        }

        /**
         * Creates a listener for the formatter.
         *
         * @param task the task running
         * @return a listener
         * @throws IOException if an error occurs
         */
        public AuditListener createListener(Task task) throws IOException {
            final AuditListener listener;
            if (type != null
                    && E_XML.equals(type.getValue())) {
                listener = createXmlLogger(task);
            }
            else if (type != null
                    && E_SARIF.equals(type.getValue())) {
                listener = createSarifLogger(task);
            }
            else {
                listener = createDefaultLogger(task);
            }
            return listener;
        }

        /**
         * Creates Sarif logger.
         *
         * @param task the task to possibly log to
         * @return an SarifLogger instance
         * @throws IOException if an error occurs
         */
        private AuditListener createSarifLogger(Task task) throws IOException {
            final AuditListener sarifLogger;
            if (toFile == null || !useFile) {
                sarifLogger = new SarifLogger(new LogOutputStream(task, Project.MSG_INFO),
                        OutputStreamOptions.CLOSE);
            }
            else {
                sarifLogger = new SarifLogger(Files.newOutputStream(toFile.toPath()),
                        OutputStreamOptions.CLOSE);
            }
            return sarifLogger;
        }

        /**
         * Creates default logger.
         *
         * @param task the task to possibly log to
         * @return a DefaultLogger instance
         * @throws IOException if an error occurs
         */
        private AuditListener createDefaultLogger(Task task)
                throws IOException {
            final AuditListener defaultLogger;
            if (toFile == null || !useFile) {
                defaultLogger = new DefaultLogger(
                    new LogOutputStream(task, Project.MSG_DEBUG),
                        OutputStreamOptions.CLOSE,
                        new LogOutputStream(task, Project.MSG_ERR),
                        OutputStreamOptions.CLOSE
                );
            }
            else {
                final OutputStream infoStream = Files.newOutputStream(toFile.toPath());
                defaultLogger =
                        new DefaultLogger(infoStream, OutputStreamOptions.CLOSE,
                                infoStream, OutputStreamOptions.NONE);
            }
            return defaultLogger;
        }

        /**
         * Creates XML logger.
         *
         * @param task the task to possibly log to
         * @return an XMLLogger instance
         * @throws IOException if an error occurs
         */
        private AuditListener createXmlLogger(Task task) throws IOException {
            final AuditListener xmlLogger;
            if (toFile == null || !useFile) {
                xmlLogger = new XMLLogger(new LogOutputStream(task, Project.MSG_INFO),
                        OutputStreamOptions.CLOSE);
            }
            else {
                xmlLogger = new XMLLogger(Files.newOutputStream(toFile.toPath()),
                        OutputStreamOptions.CLOSE);
            }
            return xmlLogger;
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
         *
         * @return the property key
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets key.
         *
         * @param key sets the property key
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * Gets value.
         *
         * @return the property value
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets value.
         *
         * @param value set the property value
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Sets the property value from a File.
         *
         * @param file set the property value from a File
         */
        public void setFile(File file) {
            value = file.getAbsolutePath();
        }

    }

}
