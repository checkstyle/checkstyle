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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.regexp.RESyntaxException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * An implementation of a ANT task for calling checkstyle. See the documentation
 * of the task for usage.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public class CheckStyleTask
    extends Task
    implements Defn
{
    /** name of file to check **/
    private String mFileName;
    /** contains the filesets to process **/
    private final List mFileSets = new ArrayList();
    /** the properties to pass to the checker **/
    private final Properties mProps = new Properties();

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

    /** @param aFile the file to be checked **/
    public void setFile(File aFile)
    {
        mFileName = aFile.getAbsolutePath();
    }

    /** @param aAllowed whether tabs are allowed **/
    public void setAllowtabs(boolean aAllowed)
    {
        mProps.setProperty(ALLOW_TABS_PROP, "" + aAllowed);
    }

    /** @param aAllowed whether protected data is allowed **/
    public void setAllowprotected(boolean aAllowed)
    {
        mProps.setProperty(ALLOW_PROTECTED_PROP, "" + aAllowed);
    }

    /** @param aAllowed whether allow having no author **/
    public void setAllownoauthor(boolean aAllowed)
    {
        mProps.setProperty(ALLOW_NO_AUTHOR_PROP, "" + aAllowed);
    }

    /** @param aLen max allowed line length **/
    public void setMaxlinelen(int aLen)
    {
        mProps.setProperty(MAX_LINE_LENGTH_PROP, "" + aLen);
    }

    /** @param aPat pattern for member variables **/
    public void setMemberpattern(String aPat)
    {
        mProps.setProperty(MEMBER_PATTERN_PROP, aPat);
    }

    /** @param aPat pattern for parameters **/
    public void setParampattern(String aPat)
    {
        mProps.setProperty(PARAMETER_PATTERN_PROP, aPat);
    }

    /** @param aPat pattern for constant variables **/
    public void setConstpattern(String aPat)
    {
        mProps.setProperty(CONST_PATTERN_PROP, aPat);
    }

    /** @param aPat pattern for static variables **/
    public void setStaticpattern(String aPat)
    {
        mProps.setProperty(STATIC_PATTERN_PROP, aPat);
    }

    /** @param aPat pattern for type names **/
    public void setTypepattern(String aPat)
    {
        mProps.setProperty(TYPE_PATTERN_PROP, aPat);
    }

    /** @param aName header file name **/
    public void setHeaderfile(File aName)
    {
        mProps.setProperty(HEADER_FILE_PROP, aName.getAbsolutePath());
    }

    /** @param aNum **/
    public void setHeaderignoreline(int aNum)
    {
        mProps.setProperty(HEADER_IGNORE_LINE_PROP, "" + aNum);
    }

    /** @param aRelax whether to be relaxed on Javadoc **/
    public void setRelaxJavadoc(boolean aRelax)
    {
        mProps.setProperty(RELAX_JAVADOC_PROP, "" + aRelax);
    }

    /** @param aIgnore whether to ignore import statements **/
    public void setIgnoreImports(boolean aIgnore)
    {
        mProps.setProperty(IGNORE_IMPORTS_PROP, "" + aIgnore);
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
        Checker c;
        try {
            c = new Checker(mProps, System.out);
        }
        catch (RESyntaxException e){
            e.printStackTrace();
            throw new BuildException("Unable to create a Checker", location);
        }

        // Process the files
        int numErrs = 0;
        if (mFileName != null) {
            numErrs += c.process(mFileName);
        }

        final Iterator it = mFileSets.iterator();
        while (it.hasNext()) {
            final FileSet fs = (FileSet) it.next();
            final DirectoryScanner ds = fs.getDirectoryScanner(project);
            numErrs += process(fs.getDir(project).getAbsolutePath(),
                               ds.getIncludedFiles(),
                               c);
        }

        if (numErrs > 0) {
            throw new BuildException("Got " + numErrs + " errors.", location);
        }
    }

    /**
     * Processes the list of files.
     * @return the number of errors found
     * @param aDir absolute path to directory containing files
     * @param aFiles the files to process
     * @param aChecker the checker to process the files with
     **/
    private int process(String aDir, String[] aFiles, Checker aChecker)
    {
        int retVal = 0;
        for (int i = 0; i < aFiles.length; i++) {
            retVal += aChecker.process(aDir + File.separator + aFiles[i]);
        }
        return retVal;
    }
}
