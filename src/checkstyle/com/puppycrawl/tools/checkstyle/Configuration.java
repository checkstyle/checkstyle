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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

/**
 * Represents the configuration that checkstyle uses when checking.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class Configuration
    implements Defn
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////////////////////////////////

    /** the pattern to match against parameter names **/
    private static final String PARAMETER_PATTERN = "^a[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against static names **/
    private static final String STATIC_PATTERN = "^s[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against constant names **/
    private static final String CONST_PATTERN = "^[A-Z]([A-Z0-9_]*[A-Z0-9])?$";
    /** the pattern to match against member names **/
    private static final String MEMBER_PATTERN = "^m[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against public member names **/
    private static final String PUBLIC_MEMBER_PATTERN = "^f[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against type names **/
    private static final String TYPE_PATTERN = "^[A-Z][a-zA-Z0-9]*$";
    /** The maximum line length **/
    private static final int MAX_LINE_LENGTH = 80;
    /** the maximum method length **/
    private static final int MAX_METHOD_LENGTH = 150;
    /** the maximum constructor length **/
    private static final int MAX_CONSTRUCTOR_LENGTH = 150;
    /** the maximum file length **/
    private static final int MAX_FILE_LENGTH = 2000;

    ////////////////////////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////////////////////////

    /** pattern to match parameters **/
    private String mParamPat;
    /** regexp to match parameters **/
    private RE mParamRegexp;

    /** pattern to match static variables **/
    private String mStaticPat;
    /** regexp to match static variables **/
    private RE mStaticRegexp;

    /** pattern to match static final variables **/
    private String mStaticFinalPat;
    /** regexp to match static final variables **/
    private RE mStaticFinalRegexp;

    /** pattern to match member variables **/
    private String mMemberPat;
    /** regexp to match member variables **/
    private RE mMemberRegexp;

    /** pattern to match public member variables **/
    private String mPublicMemberPat;
    /** regexp to match public member variables **/
    private RE mPublicMemberRegexp;

    /** pattern to match type names **/
    private String mTypePat;
    /** regexp to match type names **/
    private RE mTypeRegexp;

    /** the maximum line length **/
    private int mMaxLineLength = MAX_LINE_LENGTH;
    /** the maximum method length **/
    private int mMaxMethodLength = MAX_METHOD_LENGTH;
    /** the maximum constructor length **/
    private int mMaxConstructorLength = MAX_CONSTRUCTOR_LENGTH;
    /** the maximum file length **/
    private int mMaxFileLength = MAX_FILE_LENGTH;
    /** whether to allow tabs **/
    private boolean mAllowTabs = false;
    /** whether to allow protected data **/
    private boolean mAllowProtected = false;
    /** whether to allow having no author tag **/
    private boolean mAllowNoAuthor = false;
    /** whether to relax javadoc checking **/
    private boolean mRelaxJavadoc = false;
    /** whether to ignore javadoc checking **/
    private boolean mIgnoreJavadoc = false;
    /** whether to ignore imports **/
    private boolean mIgnoreImports = false;
    /** whether to ignore whitespace **/
    private boolean mIgnoreWhitespace = false;
    /** whether to ignore braces **/
    private boolean mIgnoreBraces = false;
    /** name of the cache file **/
    private String mCacheFile = null;
    /** whether to ignore max line length of import statements **/
    private boolean mIgnoreImportLength = false;

    /** the header lines to check for **/
    private String[] mHeaderLines = {};
    /** line number to ignore in header **/
    private int mHeaderIgnoreLineNo = -1;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new <code>Configuration</code> instance.
     *
     * @param aProps where to extract configuration parameters from
     * @param aLog where to log errors to
     * @throws RESyntaxException if an error occurs
     * @throws FileNotFoundException if an error occurs
     * @throws IOException if an error occurs
     */
    Configuration(Properties aProps, PrintStream aLog)
        throws RESyntaxException, FileNotFoundException, IOException
    {
        setParamPat(aProps.getProperty(PARAMETER_PATTERN_PROP,
                                       PARAMETER_PATTERN));
        setStaticPat(aProps.getProperty(STATIC_PATTERN_PROP,
                                        STATIC_PATTERN));
        setStaticFinalPat(aProps.getProperty(CONST_PATTERN_PROP,
                                             CONST_PATTERN));
        setMemberPat(aProps.getProperty(MEMBER_PATTERN_PROP,
                                        MEMBER_PATTERN));
        setPublicMemberPat(aProps.getProperty(PUBLIC_MEMBER_PATTERN_PROP,
                                              PUBLIC_MEMBER_PATTERN));
        setTypePat(aProps.getProperty(TYPE_PATTERN_PROP,
                                      TYPE_PATTERN));
        setMaxLineLength(getIntProperty(
            aProps, aLog, MAX_LINE_LENGTH_PROP, MAX_LINE_LENGTH));
        setMaxMethodLength(getIntProperty(
            aProps, aLog, MAX_METHOD_LENGTH_PROP, MAX_METHOD_LENGTH));
        setMaxConstructorLength(getIntProperty(
            aProps, aLog, MAX_CONSTRUCTOR_LENGTH_PROP, MAX_CONSTRUCTOR_LENGTH));
        setMaxFileLength(getIntProperty(
            aProps, aLog, MAX_FILE_LENGTH_PROP, MAX_FILE_LENGTH));

        setAllowTabs(getBooleanProperty(aProps, ALLOW_TABS_PROP, mAllowTabs));
        setAllowProtected(
            getBooleanProperty(aProps, ALLOW_PROTECTED_PROP, mAllowProtected));
        setAllowNoAuthor(
            getBooleanProperty(aProps, ALLOW_NO_AUTHOR_PROP, mAllowNoAuthor));
        setRelaxJavadoc(
            getBooleanProperty(aProps, RELAX_JAVADOC_PROP, mRelaxJavadoc));
        setIgnoreJavadoc(
            getBooleanProperty(aProps, IGNORE_JAVADOC_PROP, mIgnoreJavadoc));
        setIgnoreImports(
            getBooleanProperty(aProps, IGNORE_IMPORTS_PROP, mIgnoreImports));
        setIgnoreWhitespace(
            getBooleanProperty(aProps,
                               IGNORE_WHITESPACE_PROP,
                               mIgnoreWhitespace));
        setIgnoreBraces(getBooleanProperty(aProps,
                                           IGNORE_BRACES_PROP,
                                           mIgnoreBraces));
        setCacheFile(aProps.getProperty(CACHE_FILE_PROP));
        setIgnoreImportLength(getBooleanProperty(
            aProps, IGNORE_IMPORT_LENGTH_PROP, mIgnoreImportLength));
        setHeaderIgnoreLineNo(
            getIntProperty(aProps, aLog, HEADER_IGNORE_LINE_PROP,
                           mHeaderIgnoreLineNo));

        final String fname = aProps.getProperty(HEADER_FILE_PROP);
        if (fname != null) {
            setHeaderFile(fname);
        }

    }

    /**
     * Creates a new <code>Configuration</code> instance.
     *
     * @throws IllegalStateException if an error occurs
     */
    Configuration()
        throws IllegalStateException
    {
        try {
            setParamPat(PARAMETER_PATTERN);
            setStaticPat(STATIC_PATTERN);
            setStaticFinalPat(CONST_PATTERN);
            setMemberPat(MEMBER_PATTERN);
            setPublicMemberPat(PUBLIC_MEMBER_PATTERN);
            setTypePat(TYPE_PATTERN);
        }
        catch (RESyntaxException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage());
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////

    /** @return pattern to match parameters **/
    String getParamPat()
    {
        return mParamPat;
    }

    /** @return regexp to match parameters **/
    RE getParamRegexp()
    {
        return mParamRegexp;
    }

    /** @return pattern to match static variables **/
    String getStaticPat()
    {
        return mStaticPat;
    }

    /** @return regexp to match static variables **/
    RE getStaticRegexp()
    {
        return mStaticRegexp;
    }

    /** @return pattern to match static final variables **/
    String getStaticFinalPat()
    {
        return mStaticFinalPat;
    }

    /** @return regexp to match static final variables **/
    RE getStaticFinalRegexp()
    {
        return mStaticFinalRegexp;
    }

    /** @return pattern to match member variables **/
    String getMemberPat()
    {
        return mMemberPat;
    }

    /** @return regexp to match member variables **/
    RE getMemberRegexp()
    {
        return mMemberRegexp;
    }

    /** @return pattern to match public member variables **/
    String getPublicMemberPat()
    {
        return mPublicMemberPat;
    }

    /** @return regexp to match public member variables **/
    RE getPublicMemberRegexp()
    {
        return mPublicMemberRegexp;
    }

    /** @return pattern to match type names **/
    String getTypePat()
    {
        return mTypePat;
    }

    /** @return regexp to match type names **/
    RE getTypeRegexp()
    {
        return mTypeRegexp;
    }

    /** @return the maximum line length **/
    int getMaxLineLength()
    {
        return mMaxLineLength;
    }

    /** @return the maximum method length **/
    int getMaxMethodLength()
    {
        return mMaxMethodLength;
    }

    /** @return the maximum constructor length **/
    int getMaxConstructorLength()
    {
        return mMaxConstructorLength;
    }

    /** @return the maximum file length **/
    int getMaxFileLength()
    {
        return mMaxFileLength;
    }

    /** @return whether to allow tabs **/
    boolean isAllowTabs()
    {
        return mAllowTabs;
    }

    /** @return whether to allow protected data **/
    boolean isAllowProtected()
    {
        return mAllowProtected;
    }

    /** @return whether to allow having no author tag **/
    boolean isAllowNoAuthor()
    {
        return mAllowNoAuthor;
    }

    /** @return whether to relax javadoc checking **/
    boolean isRelaxJavadoc()
    {
        return mRelaxJavadoc;
    }

    /** @return whether to ignore javadoc checking **/
    boolean isIgnoreJavadoc()
    {
        return mIgnoreJavadoc;
    }

    /** @return whether to process imports **/
    boolean isIgnoreImports()
    {
        return mIgnoreImports;
    }

    /** @return whether to ignore checks for whitespace **/
    boolean isIgnoreWhitespace()
    {
        return mIgnoreWhitespace;
    }

    /** @return whether to ignore checks for braces **/
    boolean isIgnoreBraces()
    {
        return mIgnoreBraces;
    }

    /** @return whether to ignore max line length for import statements **/
    boolean isIgnoreImportLength()
    {
        return mIgnoreImportLength;
    }

    /** @return the header lines to check for **/
    String[] getHeaderLines()
    {
        return mHeaderLines;
    }

    /** @return line number to ignore in header **/
    int getHeaderIgnoreLineNo()
    {
        return mHeaderIgnoreLineNo;
    }

    /** @return the name of the cache file **/
    String getCacheFile()
    {
        return mCacheFile;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Setters
    ////////////////////////////////////////////////////////////////////////////

    /**
     * @param aParamPat pattern to match parameters
     * @throws RESyntaxException if an error occurs
     */
    void setParamPat(String aParamPat)
        throws RESyntaxException
    {
        mParamRegexp = new RE(aParamPat);
        mParamPat = aParamPat;
    }

    /**
     * @param aStaticPat pattern to match static variables
     * @throws RESyntaxException if an error occurs
     */
    void setStaticPat(String aStaticPat)
        throws RESyntaxException
    {
        mStaticRegexp = new RE(aStaticPat);
        mStaticPat = aStaticPat;
    }

    /**
     * @param aStaticFinalPat pattern to match static final variables
     * @throws RESyntaxException if an error occurs
     */
    void setStaticFinalPat(String aStaticFinalPat)
        throws RESyntaxException
    {
        mStaticFinalRegexp = new RE(aStaticFinalPat);
        mStaticFinalPat = aStaticFinalPat;
    }

    /**
     * @param aMemberPat pattern to match member variables
     * @throws RESyntaxException if an error occurs
     */
    void setMemberPat(String aMemberPat)
        throws RESyntaxException
    {
        mMemberRegexp = new RE(aMemberPat);
        mMemberPat = aMemberPat;
    }

    /**
     * @param aPublicMemberPat pattern to match public member variables
     * @throws RESyntaxException if an error occurs
     */
    void setPublicMemberPat(String aPublicMemberPat)
        throws RESyntaxException
    {
        mPublicMemberRegexp = new RE(aPublicMemberPat);
        mPublicMemberPat = aPublicMemberPat;
    }

    /**
     * @param aTypePat pattern to match type names
     * @throws RESyntaxException if an error occurs
     */
    void setTypePat(String aTypePat)
        throws RESyntaxException
    {
        mTypeRegexp = new RE(aTypePat);
        mTypePat = aTypePat;
    }

    /**
     * @param aMaxLineLength the maximum line length
     */
    void setMaxLineLength(int aMaxLineLength)
    {
        mMaxLineLength = aMaxLineLength;
    }

    /**
     * @param aMaxMethodLength the maximum method length
     */
    void setMaxMethodLength(int aMaxMethodLength)
    {
        mMaxMethodLength = aMaxMethodLength;
    }

    /**
     * @param aMaxConstructorLength the maximum constructor length
     */
    void setMaxConstructorLength(int aMaxConstructorLength)
    {
        mMaxConstructorLength = aMaxConstructorLength;
    }

    /**
     * @param aMaxFileLength the maximum file length
     */
    void setMaxFileLength(int aMaxFileLength)
    {
        mMaxFileLength = aMaxFileLength;
    }

    /**
     * @param aIgnoreImportLength whether to allow tabs
     */
    void setIgnoreImportLength(boolean aIgnoreImportLength)
    {
        mIgnoreImportLength = aIgnoreImportLength;
    }

    /**
     * @param aAllowTabs whether to allow tabs
     */
    void setAllowTabs(boolean aAllowTabs)
    {
        mAllowTabs = aAllowTabs;
    }

    /**
     * @param aAllowProtected whether to allow protected data
     */
    void setAllowProtected(boolean aAllowProtected)
    {
        mAllowProtected = aAllowProtected;
    }

    /**
     * @param aAllowNoAuthor whether to allow having no author tag
     */
    void setAllowNoAuthor(boolean aAllowNoAuthor)
    {
        mAllowNoAuthor = aAllowNoAuthor;
    }

    /**
     * @param aRelaxJavadoc whether to relax javadoc checking
     */
    void setRelaxJavadoc(boolean aRelaxJavadoc)
    {
        mRelaxJavadoc = aRelaxJavadoc;
    }

    /**
     * @param aIgnoreJavadoc whether to ignore javadoc checking
     */
    void setIgnoreJavadoc(boolean aIgnoreJavadoc)
    {
        mIgnoreJavadoc = aIgnoreJavadoc;
    }

    /**
     * @param aIgnoreImports whether to process imports
     */
    void setIgnoreImports(boolean aIgnoreImports)
    {
        mIgnoreImports = aIgnoreImports;
    }

    /**
     * @param aTo whether to ignore checks for whitespace
     */
    void setIgnoreWhitespace(boolean aTo)
    {
        mIgnoreWhitespace = aTo;
    }

    /**
     * @param aTo whether to ignore checks for braces
     */
    void setIgnoreBraces(boolean aTo)
    {
        mIgnoreBraces = aTo;
    }

    /**
     * @param aFileName the header lines to check for
     * @throws FileNotFoundException if an error occurs
     * @throws IOException if an error occurs
     */
    void setHeaderFile(String aFileName)
        throws FileNotFoundException, IOException
    {
        final LineNumberReader lnr =
            new LineNumberReader(new FileReader(aFileName));
        final ArrayList lines = new ArrayList();
        while (true) {
            final String l = lnr.readLine();
            if (l == null) {
                break;
            }
            lines.add(l);
        }
        mHeaderLines = (String[]) lines.toArray(new String[0]);
    }

    /**
     * @param aHeaderIgnoreLineNo line number to ignore in header
     */
    void setHeaderIgnoreLineNo(int aHeaderIgnoreLineNo)
    {
        mHeaderIgnoreLineNo = aHeaderIgnoreLineNo;
    }

    /**
     * @param aCacheFile name of cache file
     */
    void setCacheFile(String aCacheFile)
    {
        mCacheFile = aCacheFile;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     *
     * @return the value of an integer property. If the property is not defined
     *    or cannot be parsed, then a default value is returned.
     */
    private static int getIntProperty(Properties aProps, PrintStream aLog,
                                      String aName, int aDefault)
    {
        int retVal = aDefault;
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            try {
                retVal = Integer.parseInt(strRep);
            }
            catch (NumberFormatException nfe) {
                aLog.println("Unable to parse " + aName +
                             " property with value " + strRep +
                             ", defaulting to " + aDefault + ".");
            }
        }
        return retVal;
    }

    /**
     * @param aProps the properties set to use
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     * @return the value of an boolean property. If the property is not defined
     *    or cannot be parsed, then a default value is returned.
     */
    private static boolean getBooleanProperty(Properties aProps,
                                              String aName,
                                              boolean aDefault)
    {
        boolean retVal = aDefault;
        String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            strRep = strRep.toLowerCase().trim();
            retVal = strRep.equals("true") || strRep.equals("yes") ||
                strRep.equals("on");
        }
        return retVal;
    }
}
