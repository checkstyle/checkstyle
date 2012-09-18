////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Utils;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.SeverityLevelCounter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
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

/**
 * An implementation of a ANT task for calling checkstyle. See the documentation
 * of the task for usage.
 * @author Oliver Burn
 */
public class CheckStyleTask extends Task
{
    /** poor man's enum for an xml formatter */
    private static final String E_XML = "xml";
    /** poor man's enum for an plain formatter */
    private static final String E_PLAIN = "plain";

    /** class path to locate class files */
    private Path mClasspath;

    /** name of file to check */
    private String mFileName;

    /** config file containing configuration */
    private String mConfigLocation;

    /** whether to fail build on violations */
    private boolean mFailOnViolation = true;

    /** property to set on violations */
    private String mFailureProperty;

    /** contains the filesets to process */
    private final List<FileSet> mFileSets = Lists.newArrayList();

    /** contains the formatters to log to */
    private final List<Formatter> mFormatters = Lists.newArrayList();

    /** contains the Properties to override */
    private final List<Property> mOverrideProps = Lists.newArrayList();

    /** the name of the properties file */
    private File mPropertiesFile;

    /** the maximum number of errors that are tolerated. */
    private int mMaxErrors;

    /** the maximum number of warnings that are tolerated. */
    private int mMaxWarnings = Integer.MAX_VALUE;

    /**
     * whether to omit ignored modules - some modules may log above
     * their severity depending on their configuration (e.g. WriteTag) so
     * need to be included
     */
    private boolean mOmitIgnoredModules = true;

    ////////////////////////////////////////////////////////////////////////////
    // Setters for ANT specific attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Tells this task to set the named property to "true" when there
     * is a violation.
     * @param aPropertyName the name of the property to set
     *                      in the event of an failure.
     */
    public void setFailureProperty(String aPropertyName)
    {
        mFailureProperty = aPropertyName;
    }

    /** @param aFail whether to fail if a violation is found */
    public void setFailOnViolation(boolean aFail)
    {
        mFailOnViolation = aFail;
    }

    /**
     * Sets the maximum number of errors allowed. Default is 0.
     * @param aMaxErrors the maximum number of errors allowed.
     */
    public void setMaxErrors(int aMaxErrors)
    {
        mMaxErrors = aMaxErrors;
    }

    /**
     * Sets the maximum number of warings allowed. Default is
     * {@link Integer#MAX_VALUE}.
     * @param aMaxWarnings the maximum number of warnings allowed.
     */
    public void setMaxWarnings(int aMaxWarnings)
    {
        mMaxWarnings = aMaxWarnings;
    }

    /**
     * Adds a set of files (nested fileset attribute).
     * @param aFS the file set to add
     */
    public void addFileset(FileSet aFS)
    {
        mFileSets.add(aFS);
    }

    /**
     * Add a formatter.
     * @param aFormatter the formatter to add for logging.
     */
    public void addFormatter(Formatter aFormatter)
    {
        mFormatters.add(aFormatter);
    }

    /**
     * Add an override property.
     * @param aProperty the property to add
     */
    public void addProperty(Property aProperty)
    {
        mOverrideProps.add(aProperty);
    }

    /**
     * Set the class path.
     * @param aClasspath the path to locate classes
     */
    public void setClasspath(Path aClasspath)
    {
        if (mClasspath == null) {
            mClasspath = aClasspath;
        }
        else {
            mClasspath.append(aClasspath);
        }
    }

    /**
     * Set the class path from a reference defined elsewhere.
     * @param aClasspathRef the reference to an instance defining the classpath
     */
    public void setClasspathRef(Reference aClasspathRef)
    {
        createClasspath().setRefid(aClasspathRef);
    }

    /** @return a created path for locating classes */
    public Path createClasspath()
    {
        if (mClasspath == null) {
            mClasspath = new Path(getProject());
        }
        return mClasspath.createPath();
    }

    /** @param aFile the file to be checked */
    public void setFile(File aFile)
    {
        mFileName = aFile.getAbsolutePath();
    }

    /** @param aFile the configuration file to use */
    public void setConfig(File aFile)
    {
        setConfigLocation(aFile.getAbsolutePath());
    }

    /** @param aURL the URL of the configuration to use */
    public void setConfigURL(URL aURL)
    {
        setConfigLocation(aURL.toExternalForm());
    }

    /**
     * Sets the location of the configuration.
     * @param aLocation the location, which is either a
     */
    private void setConfigLocation(String aLocation)
    {
        if (mConfigLocation != null) {
            throw new BuildException("Attributes 'config' and 'configURL' "
                    + "must not be set at the same time");
        }
        mConfigLocation = aLocation;
    }

    /** @param aOmit whether to omit ignored modules */
    public void setOmitIgnoredModules(boolean aOmit)
    {
        mOmitIgnoredModules = aOmit;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Setters for Checker configuration attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Sets a properties file for use instead
     * of individually setting them.
     * @param aProps the properties File to use
     */
    public void setProperties(File aProps)
    {
        mPropertiesFile = aProps;
    }

    ////////////////////////////////////////////////////////////////////////////
    // The doers
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void execute() throws BuildException
    {
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
    private void realExecute()
    {
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
        if ((mFileName == null) && (mFileSets.size() == 0)) {
            throw new BuildException(
                    "Must specify atleast one of 'file' or nested 'fileset'.",
                    getLocation());
        }

        if (mConfigLocation == null) {
            throw new BuildException("Must specify 'config'.", getLocation());
        }

        // Create the checker
        Checker c = null;
        try {
            c = createChecker();

            final SeverityLevelCounter warningCounter =
                new SeverityLevelCounter(SeverityLevel.WARNING);
            c.addListener(warningCounter);

            // Process the files
            long startTime = System.currentTimeMillis();
            final List<File> files = scanFileSets();
            long endTime = System.currentTimeMillis();
            log("To locate the files took " + (endTime - startTime) + " ms.",
                Project.MSG_VERBOSE);

            log("Running Checkstyle " + version + " on " + files.size()
                    + " files", Project.MSG_INFO);
            log("Using configuration " + mConfigLocation, Project.MSG_VERBOSE);

            startTime = System.currentTimeMillis();
            final int numErrs = c.process(files);
            endTime = System.currentTimeMillis();
            log("To process the files took " + (endTime - startTime) + " ms.",
                Project.MSG_VERBOSE);
            final int numWarnings = warningCounter.getCount();
            final boolean ok = (numErrs <= mMaxErrors)
                    && (numWarnings <= mMaxWarnings);

            // Handle the return status
            if (!ok) {
                final String failureMsg =
                        "Got " + numErrs + " errors and " + numWarnings
                                + " warnings.";
                if (mFailureProperty != null) {
                    getProject().setProperty(mFailureProperty, failureMsg);
                }

                if (mFailOnViolation) {
                    throw new BuildException(failureMsg, getLocation());
                }
            }
        }
        finally {
            if (c != null) {
                c.destroy();
            }
        }
    }

    /**
     * Creates new instance of <code>Checker</code>.
     * @return new instance of <code>Checker</code>
     */
    private Checker createChecker()
    {
        Checker c = null;
        try {
            final Properties props = createOverridingProperties();
            final Configuration config =
                ConfigurationLoader.loadConfiguration(
                    mConfigLocation,
                    new PropertiesExpander(props),
                    mOmitIgnoredModules);

            final DefaultContext context = new DefaultContext();
            final ClassLoader loader = new AntClassLoader(getProject(),
                    mClasspath);
            context.add("classloader", loader);

            final ClassLoader moduleClassLoader =
                Checker.class.getClassLoader();
            context.add("moduleClassLoader", moduleClassLoader);

            c = new Checker();

            c.contextualize(context);
            c.configure(config);

            // setup the listeners
            final AuditListener[] listeners = getListeners();
            for (AuditListener element : listeners) {
                c.addListener(element);
            }
        }
        catch (final Exception e) {
            throw new BuildException("Unable to create a Checker: "
                    + e.getMessage(), e);
        }

        return c;
    }

    /**
     * Create the Properties object based on the arguments specified
     * to the ANT task.
     * @return the properties for property expansion expansion
     * @throws BuildException if an error occurs
     */
    private Properties createOverridingProperties()
    {
        final Properties retVal = new Properties();

        // Load the properties file if specified
        if (mPropertiesFile != null) {
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(mPropertiesFile);
                retVal.load(inStream);
            }
            catch (final FileNotFoundException e) {
                throw new BuildException("Could not find Properties file '"
                        + mPropertiesFile + "'", e, getLocation());
            }
            catch (final IOException e) {
                throw new BuildException("Error loading Properties file '"
                        + mPropertiesFile + "'", e, getLocation());
            }
            finally {
                Utils.closeQuietly(inStream);
            }
        }

        // override with Ant properties like ${basedir}
        final Hashtable<?, ?> antProps = this.getProject().getProperties();
        for (Object name : antProps.keySet()) {
            final String key = (String) name;
            final String value = String.valueOf(antProps.get(key));
            retVal.put(key, value);
        }

        // override with properties specified in subelements
        for (Property p : mOverrideProps) {
            retVal.put(p.getKey(), p.getValue());
        }

        return retVal;
    }

    /**
     * Return the list of listeners set in this task.
     * @return the list of listeners.
     * @throws ClassNotFoundException if an error occurs
     * @throws InstantiationException if an error occurs
     * @throws IllegalAccessException if an error occurs
     * @throws IOException if an error occurs
     */
    protected AuditListener[] getListeners() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, IOException
    {
        final int formatterCount = Math.max(1, mFormatters.size());

        final AuditListener[] listeners = new AuditListener[formatterCount];

        // formatters
        if (mFormatters.size() == 0) {
            final OutputStream debug = new LogOutputStream(this,
                    Project.MSG_DEBUG);
            final OutputStream err = new LogOutputStream(this, Project.MSG_ERR);
            listeners[0] = new DefaultLogger(debug, true, err, true);
        }
        else {
            for (int i = 0; i < formatterCount; i++) {
                final Formatter f = mFormatters.get(i);
                listeners[i] = f.createListener(this);
            }
        }
        return listeners;
    }

    /**
     * returns the list of files (full path name) to process.
     * @return the list of files included via the filesets.
     */
    protected List<File> scanFileSets()
    {
        final List<File> list = Lists.newArrayList();
        if (mFileName != null) {
            // oops we've got an additional one to process, don't
            // forget it. No sweat, it's fully resolved via the setter.
            log("Adding standalone file for audit", Project.MSG_VERBOSE);
            list.add(new File(mFileName));
        }
        for (int i = 0; i < mFileSets.size(); i++) {
            final FileSet fs = mFileSets.get(i);
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
    public static class FormatterType extends EnumeratedAttribute
    {
        /** my possible values */
        private static final String[] VALUES = {E_XML, E_PLAIN};

        @Override
        public String[] getValues()
        {
            return VALUES.clone();
        }
    }

    /**
     * Details about a formatter to be used.
     * @author Oliver Burn
     */
    public static class Formatter
    {
        /** the formatter type */
        private FormatterType mFormatterType;
        /** the file to output to */
        private File mToFile;
        /** Whether or not the write to the named file. */
        private boolean mUseFile = true;

        /**
         * Set the type of the formatter.
         * @param aType the type
         */
        public void setType(FormatterType aType)
        {
            final String val = aType.getValue();
            if (!E_XML.equals(val) && !E_PLAIN.equals(val)) {
                throw new BuildException("Invalid formatter type: " + val);
            }

            mFormatterType = aType;
        }

        /**
         * Set the file to output to.
         * @param aTo the file to output to
         */
        public void setTofile(File aTo)
        {
            mToFile = aTo;
        }

        /**
         * Sets whether or not we write to a file if it is provided.
         * @param aUse whether not not to use provided file.
         */
        public void setUseFile(boolean aUse)
        {
            mUseFile = aUse;
        }

        /**
         * Creates a listener for the formatter.
         * @param aTask the task running
         * @return a listener
         * @throws IOException if an error occurs
         */
        public AuditListener createListener(Task aTask) throws IOException
        {
            if ((mFormatterType != null)
                    && E_XML.equals(mFormatterType.getValue()))
            {
                return createXMLLogger(aTask);
            }
            return createDefaultLogger(aTask);
        }

        /**
         * @return a DefaultLogger instance
         * @param aTask the task to possibly log to
         * @throws IOException if an error occurs
         */
        private AuditListener createDefaultLogger(Task aTask)
            throws IOException
        {
            if ((mToFile == null) || !mUseFile) {
                return new DefaultLogger(
                    new LogOutputStream(aTask, Project.MSG_DEBUG),
                    true, new LogOutputStream(aTask, Project.MSG_ERR), true);
            }
            return new DefaultLogger(new FileOutputStream(mToFile), true);
        }

        /**
         * @return an XMLLogger instance
         * @param aTask the task to possibly log to
         * @throws IOException if an error occurs
         */
        private AuditListener createXMLLogger(Task aTask) throws IOException
        {
            if ((mToFile == null) || !mUseFile) {
                return new XMLLogger(new LogOutputStream(aTask,
                        Project.MSG_INFO), true);
            }
            return new XMLLogger(new FileOutputStream(mToFile), true);
        }
    }

    /**
     * Represents a property that consists of a key and value.
     */
    public static class Property
    {
        /** the property key */
        private String mKey;
        /** the property value */
        private String mValue;

        /** @return the property key */
        public String getKey()
        {
            return mKey;
        }

        /** @param aKey sets the property key */
        public void setKey(String aKey)
        {
            mKey = aKey;
        }

        /** @return the property value */
        public String getValue()
        {
            return mValue;
        }

        /** @param aValue set the property value */
        public void setValue(String aValue)
        {
            mValue = aValue;
        }

        /** @param aValue set the property value from a File */
        public void setFile(File aValue)
        {
            setValue(aValue.getAbsolutePath());
        }
    }

    /** Represents a custom listener. */
    public static class Listener
    {
        /** classname of the listener class */
        private String mClassname;

        /** @return the classname */
        public String getClassname()
        {
            return mClassname;
        }

        /** @param aClassname set the classname */
        public void setClassname(String aClassname)
        {
            mClassname = aClassname;
        }
    }
}
