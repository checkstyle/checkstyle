////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.regexp.RESyntaxException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.apache.tools.ant.types.FileSet;

/**
 * An implementation of a ANT task for calling checkstyle. See the documentation
 * of the task for usage.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public class CheckStyleTask
    extends Task
{
    /** poor man's enum for an xml formatter **/
    private static final String E_XML = "xml";
    /** poor man's enum for an plain formatter **/
    private static final String E_PLAIN = "plain";

    /** name of file to check **/
    private String mFileName;

    /** whether to fail build on violations **/
    private boolean mFailOnViolation = true;

    /** contains the filesets to process **/
    private final List mFileSets = new ArrayList();

    /** contains the formatters to log to **/
    private final List mFormatters = new ArrayList();

    /** the configuration to pass to the checker **/
    private final Configuration mConfig = new Configuration();

    ////////////////////////////////////////////////////////////////////////////
    // Setters for attributes
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Adds a set of files (nested fileset attribute).
     * @param aFS the file set to add
     */
    public void addFileset(FileSet aFS)
    {
        mFileSets.add(aFS);
    }

    /**
     * Add a formatter
     * @param aFormatter the formatter to add for logging.
     */
    public void addFormatter(Formatter aFormatter)
    {
        mFormatters.add(aFormatter);
    }


    /** @param aFile the file to be checked **/
    public void setFile(File aFile)
    {
        mFileName = aFile.getAbsolutePath();
    }

    /** @param aAllowed whether tabs are allowed **/
    public void setAllowTabs(boolean aAllowed)
    {
        mConfig.setAllowTabs(aAllowed);
    }

    /** @param aAllowed whether protected data is allowed **/
    public void setAllowProtected(boolean aAllowed)
    {
        mConfig.setAllowProtected(aAllowed);
    }

    /** @param aAllowed whether allow having no author **/
    public void setAllowNoAuthor(boolean aAllowed)
    {
        mConfig.setAllowNoAuthor(aAllowed);
    }

    /** @param aLen max allowed line length **/
    public void setMaxLineLen(int aLen)
    {
        mConfig.setMaxLineLength(aLen);
    }

    /** @param aLen max allowed method length **/
    public void setMaxMethodLen(int aLen)
    {
        mConfig.setMaxMethodLength(aLen);
    }

    /** @param aLen max allowed constructor length **/
    public void setMaxConstructorLen(int aLen)
    {
        mConfig.setMaxConstructorLength(aLen);
    }

    /** @param aLen max allowed file length **/
    public void setMaxFileLen(int aLen)
    {
        mConfig.setMaxFileLength(aLen);
    }

    /** @param aIgnore whether max line length should be ignored for
     *                 import statements
     */
    public void setIgnoreImportLength(boolean aIgnore)
    {
        mConfig.setIgnoreImportLength(aIgnore);
    }

    /** @param aPat pattern for member variables **/
    public void setMemberPattern(String aPat)
    {
        try {
            mConfig.setMemberPat(aPat);
        }
        catch (RESyntaxException ex) {
            throw new BuildException("Unable to parse memberPattern - ", ex);
        }
    }

    /** @param aPat pattern for public member variables **/
    public void setPublicMemberPattern(String aPat)
    {
        try {
            mConfig.setPublicMemberPat(aPat);
        }
        catch (RESyntaxException ex) {
            throw new BuildException(
                "Unable to parse publicMemberPattern - ", ex);
        }
    }

    /** @param aPat pattern for parameters **/
    public void setParamPattern(String aPat)
    {
        try {
            mConfig.setParamPat(aPat);
        }
        catch (RESyntaxException ex) {
            throw new BuildException("Unable to parse paramPattern - ", ex);
        }
    }

    /** @param aPat pattern for constant variables **/
    public void setConstPattern(String aPat)
    {
        try {
            mConfig.setStaticFinalPat(aPat);
        }
        catch (RESyntaxException ex) {
            throw new BuildException("Unable to parse constPattern - " , ex);
        }
    }

    /** @param aPat pattern for static variables **/
    public void setStaticPattern(String aPat)
    {
        try {
            mConfig.setStaticPat(aPat);
        }
        catch (RESyntaxException ex) {
            throw new BuildException("Unable to parse staticPattern - ", ex);
        }
    }

    /** @param aPat pattern for type names **/
    public void setTypePattern(String aPat)
    {
        try {
            mConfig.setTypePat(aPat);
        }
        catch (RESyntaxException ex) {
            throw new BuildException("Unable to parse typePattern - ", ex);
        }
    }

    /** @param aName header file name **/
    public void setHeaderFile(File aName)
    {
        try {
            mConfig.setHeaderFile(aName.getAbsolutePath());
        }
        catch (IOException ex) {
            throw new BuildException("Unable to read headerfile - ", ex);
        }
    }

    /** @param aFail whether to fail if a violation is found **/
    public void setFailOnViolation(boolean aFail)
    {
        mFailOnViolation = aFail;
    }

    /** @param aNum **/
    public void setHeaderIgnoreLine(int aNum)
    {
        mConfig.setHeaderIgnoreLineNo(aNum);
    }

    /** @param aJavadocScope visibility scope where Javadoc is checked **/
    public void setJavadocScope(String aJavadocScope)
    {
        mConfig.setJavadocScope(Scope.getInstance(aJavadocScope));
    }

    /** @param aIgnore whether to ignore import statements **/
    public void setIgnoreImports(boolean aIgnore)
    {
        mConfig.setIgnoreImports(aIgnore);
    }

    /** @param aIgnore whether to ignore whitespace **/
    public void setIgnoreWhitespace(boolean aIgnore)
    {
        mConfig.setIgnoreWhitespace(aIgnore);
    }

    /** @param aIgnore whether to ignore braces **/
    public void setIgnoreBraces(boolean aIgnore)
    {
        mConfig.setIgnoreBraces(aIgnore);
    }

    /** @param aCacheFile the file to cache which files have been checked **/
    public void setCacheFile(File aCacheFile)
    {
        mConfig.setCacheFile(aCacheFile.getAbsolutePath());
    }

    ////////////////////////////////////////////////////////////////////////////
    // The doers
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Actually checks the files specified. All errors are reported to
     * System.out. Will fail if any errors occurred.
     * @throws BuildException an error occurred
     **/
    public void execute()
        throws BuildException
    {
        // Check for no arguments
        if ((mFileName == null) && (mFileSets.size() == 0)) {
            throw new BuildException("Must specify atleast one of 'file' " +
                                     "or nested 'fileset'.", location);
        }

        // Create the checker
        final int numErrs;
        Checker c = null;
        try {
            c = new Checker(mConfig, System.out);
            AuditListener[] listeners = getListeners();
            for (int i = 0; i < listeners.length; i++) {
                c.addListener(listeners[i]);
            }
            final String[] files = scanFileSets();
            numErrs = c.process(files);
        }
        catch (Exception e) {
            throw new BuildException("Unable to create a Checker", e);
        }
        finally {
            if (c != null) {
                c.destroy();
            }
        }

        if ((numErrs > 0) && mFailOnViolation) {
            throw new BuildException("Got " + numErrs + " errors.", location);
        }
    }

    /**
     * Return the list of listeners set in this task.
     * @return the list of listeners.
     * @throws ClassNotFoundException if an error occurs
     * @throws InstantiationException if an error occurs
     * @throws IllegalAccessException if an error occurs
     * @throws IOException if an error occurs
     */
    protected AuditListener[] getListeners()
        throws ClassNotFoundException, InstantiationException,
        IllegalAccessException, IOException
    {
        // @todo should we add a default plain stdout
        // formatter ?
        if (mFormatters.size() == 0) {
            final Formatter f = new Formatter();
            final FormatterType type = new FormatterType();
            type.setValue(E_PLAIN);
            f.setType(type);
            mFormatters.add(f);
        }

        final AuditListener[] listeners = new AuditListener[mFormatters.size()];
        for (int i = 0; i < listeners.length; i++) {
            final Formatter f = (Formatter) mFormatters.get(i);
            listeners[i] = f.createListener(this);
        }
        return listeners;
    }

    /**
     * returns the list of files (full path name) to process.
     * @return the list of files included via the filesets.
     */
    protected String[] scanFileSets()
    {
        final ArrayList list = new ArrayList();
        if (mFileName != null) {
            // oops we've got an additional one to process, don't
            // forget it. No sweat, it's fully resolved via the setter.
            log("Adding standalone file for audit", Project.MSG_VERBOSE);
            list.add(mFileName);
        }
        for (int i = 0; i < mFileSets.size(); i++) {
            final FileSet fs = (FileSet) mFileSets.get(i);
            final DirectoryScanner ds = fs.getDirectoryScanner(project);
            ds.scan();

            final String[] names = ds.getIncludedFiles();
            log(i + ") Adding " + names.length + " files from directory " +
                ds.getBasedir(), Project.MSG_VERBOSE);

            for (int j = 0; j < names.length; j++) {
                final String pathname =
                    ds.getBasedir() + File.separator + names[j];
                list.add(pathname);
            }
        }

        return (String[]) list.toArray(new String[0]);
    }

    /**
     * Poor mans enumeration for the formatter types.
     * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
     */
    public static class FormatterType
        extends EnumeratedAttribute
    {
        /** my possible values **/
        private static final String[] VALUES = {E_XML, E_PLAIN};

        /** @see EnumeratedAttribute **/
        public String[] getValues()
        {
            return VALUES;
        }
    }

    /**
     * Details about a formatter to be used.
     * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
     */
    public static class Formatter
    {
        /** class name of formatter **/
        private String mClassName = null;
        /** whether formatter users a file **/
        private boolean mUseFile = true;
        /** the file to output to **/
        private File mToFile = null;

        /**
         * Set the type of the formatter.
         * @param aType the type
         */
        public void setType(FormatterType aType)
        {
            final String val = aType.getValue();
            if (E_XML.equals(val)) {
                setClassname(XMLLogger.class.getName());
            }
            else if (E_PLAIN.equals(val)) {
                setClassname(DefaultLogger.class.getName());
            }
            else {
                throw new BuildException("Invalid formatter type: " + val);
            }
        }

        /**
         * Set the class name of the formatter.
         * @param aTo the formatter class name
         */
        public void setClassname(String aTo)
        {
            mClassName = aTo;
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
         * Creates a listener for the formatter.
         * @param aTask the task running
         * @return a listener
         * @throws ClassNotFoundException if an error occurs
         * @throws InstantiationException if an error occurs
         * @throws IllegalAccessException if an error occurs
         * @throws IOException if an error occurs
         */
        public AuditListener createListener(Task aTask)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException
        {
            final Class clazz = Class.forName(mClassName);
            final AuditListener listener = (AuditListener) clazz.newInstance();
            if (listener instanceof Streamable) {
                final Streamable o = (Streamable) listener;
                o.setOutputStream(createOutputStream(aTask));
            }
            return listener;
        }

        /**
         * @return an output stream to log with
         * @param aTask the task to possibly log to
         * @throws IOException if an error occurs
         */
        protected OutputStream createOutputStream(Task aTask)
            throws IOException
        {
            if (mToFile == null) {
                return new LogOutputStream(aTask, Project.MSG_INFO);
            }
            return new FileOutputStream(mToFile);
        }
    }
}
