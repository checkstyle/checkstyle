////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.InvalidObjectException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

/**
 * Represents the configuration that checkstyle uses when checking.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public class Configuration
    implements Serializable
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////////////////////////////////

    /** the pattern to match against todo lines **/
    private static final String TODO_PATTERN = "TODO:";
    /** the pattern to match against parameter names **/
    private static final String PARAMETER_PATTERN = "^[a-z][a-zA-Z0-9]*$";
    /** the pattern to match against static names **/
    private static final String STATIC_PATTERN = "^[a-z][a-zA-Z0-9]*$";
    /** the pattern to match against constant names **/
    private static final String CONST_PATTERN = "^[A-Z](_?[A-Z0-9]+)*$";
    /** the pattern to match against member names **/
    private static final String MEMBER_PATTERN = "^[a-z][a-zA-Z0-9]*$";
    /** the pattern to match against public member names **/
    private static final String PUBLIC_MEMBER_PATTERN = "^f[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against type names **/
    private static final String TYPE_PATTERN = "^[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against method local variables **/
    private static final String LOCAL_VAR_PATTERN = "^[a-z][a-zA-Z0-9]*$";
    /** the pattern to match against method names **/
    private static final String METHOD_PATTERN = "^[a-z][a-zA-Z0-9]*$";
    /** the pattern to exclude from line length checks **/
    private static final String IGNORE_LINE_LENGTH_PATTERN = "^$";
    /** the maximum line length **/
    private static final int MAX_LINE_LENGTH = 80;
    /** the maximum method length **/
    private static final int MAX_METHOD_LENGTH = 150;
    /** the maximum constructor length **/
    private static final int MAX_CONSTRUCTOR_LENGTH = 150;
    /** the maximum file length **/
    private static final int MAX_FILE_LENGTH = 2000;
    /** the set of illegal imports (comma separated package prefixes) **/
    private static final String ILLEGAL_IMPORTS = "sun";
    /** the set of illegal instantiations (comma separated class names) **/
    private static final String ILLEGAL_INSTANTIATIONS = "";
    /** the number of spaces that are represented by one tab character **/
    private static final int TAB_WIDTH = 8;

    ////////////////////////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////////////////////////

    /** visibility scope where Javadoc is checked **/
    private Scope mJavadocScope = Scope.PRIVATE;
    /** illegal imports **/
    private final HashSet mIllegalImports = new HashSet();
    /** illegal instantiations **/
    private final HashSet mIllegalInstantiations = new HashSet();
    /** name of the cache file **/
    private String mCacheFile = null;

    /** the header lines to check for **/
    private String[] mHeaderLines = {};
    /** line numbers to ignore in header **/
    private TreeSet mHeaderIgnoreLineNo = new TreeSet();

    /** where to place right curlies  **/
    private RightCurlyOption mRCurly = RightCurlyOption.SAME;

    /** how to pad parenthesis **/
    private PadOption mParenPadOption = PadOption.NOSPACE;

    /** set of boolean properties **/
    private final Set mBooleanProps = new HashSet();

    /** map of int properties **/
    private final Map mIntProps = new HashMap();
    {
        // Set up all the default values
        mIntProps.put(Defn.MAX_LINE_LENGTH_PROP, new Integer(MAX_LINE_LENGTH));
        mIntProps.put(Defn.MAX_METHOD_LENGTH_PROP,
                      new Integer(MAX_METHOD_LENGTH));
        mIntProps.put(Defn.MAX_CONSTRUCTOR_LENGTH_PROP,
                      new Integer(MAX_CONSTRUCTOR_LENGTH));
        mIntProps.put(Defn.MAX_FILE_LENGTH_PROP, new Integer(MAX_FILE_LENGTH));
        mIntProps.put(Defn.TAB_WIDTH_PROP, new Integer(TAB_WIDTH));
    }

    /** map of all the pattern properties **/
    private final Map mPatterns = new HashMap();
    /** map of all the corresponding RE objects for pattern properties **/
    private transient Map mRegexps = new HashMap();
    /** map of all the BlockOption properties **/
    private final Map mBlockProps = new HashMap();
    {
        mBlockProps.put(Defn.TRY_BLOCK_PROP, BlockOption.STMT);
        mBlockProps.put(Defn.CATCH_BLOCK_PROP, BlockOption.TEXT);
        mBlockProps.put(Defn.FINALLY_BLOCK_PROP, BlockOption.STMT);
    }
    /** map of all the LeftCurlyOption properties **/
    private final Map mLCurliesProps = new HashMap();
    {
        mLCurliesProps.put(Defn.LCURLY_METHOD_PROP, LeftCurlyOption.EOL);
        mLCurliesProps.put(Defn.LCURLY_TYPE_PROP, LeftCurlyOption.EOL);
        mLCurliesProps.put(Defn.LCURLY_OTHER_PROP, LeftCurlyOption.EOL);
    }



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
    public Configuration(Properties aProps, PrintStream aLog)
        throws RESyntaxException, FileNotFoundException, IOException
    {
        setPatternProperty(aProps, Defn.TODO_PATTERN_PROP, TODO_PATTERN);
        setPatternProperty(aProps, Defn.PARAMETER_PATTERN_PROP,
                           PARAMETER_PATTERN);
        setPatternProperty(aProps, Defn.STATIC_PATTERN_PROP, STATIC_PATTERN);
        setPatternProperty(aProps, Defn.CONST_PATTERN_PROP, CONST_PATTERN);
        setPatternProperty(aProps, Defn.MEMBER_PATTERN_PROP, MEMBER_PATTERN);
        setPatternProperty(aProps, Defn.PUBLIC_MEMBER_PATTERN_PROP,
                           PUBLIC_MEMBER_PATTERN);
        setPatternProperty(aProps, Defn.TYPE_PATTERN_PROP, TYPE_PATTERN);
        setPatternProperty(aProps, Defn.LOCAL_VAR_PATTERN_PROP,
                           LOCAL_VAR_PATTERN);
        setPatternProperty(aProps, Defn.METHOD_PATTERN_PROP, METHOD_PATTERN);
        setPatternProperty(aProps, Defn.IGNORE_LINE_LENGTH_PATTERN_PROP,
                           IGNORE_LINE_LENGTH_PATTERN);
        setIntProperty(aProps, aLog, Defn.MAX_LINE_LENGTH_PROP,
                       MAX_LINE_LENGTH);
        setIntProperty(aProps, aLog, Defn.MAX_METHOD_LENGTH_PROP,
                       MAX_METHOD_LENGTH);
        setIntProperty(aProps, aLog, Defn.MAX_CONSTRUCTOR_LENGTH_PROP,
                       MAX_CONSTRUCTOR_LENGTH);
        setIntProperty(aProps, aLog, Defn.MAX_FILE_LENGTH_PROP,
                       MAX_FILE_LENGTH);

        setBooleanProperty(aProps, Defn.ALLOW_TABS_PROP);
        setIntProperty(aProps, aLog, Defn.TAB_WIDTH_PROP, TAB_WIDTH);
        setBooleanProperty(aProps, Defn.ALLOW_PROTECTED_PROP);
        setBooleanProperty(aProps, Defn.ALLOW_PACKAGE_PROP);
        setBooleanProperty(aProps, Defn.ALLOW_NO_AUTHOR_PROP);
        setJavadocScope(
            Scope.getInstance(aProps.getProperty(Defn.JAVADOC_CHECKSCOPE_PROP,
                                                 Scope.PRIVATE.getName())));
        setBooleanProperty(aProps, Defn.REQUIRE_PACKAGE_HTML_PROP);
        setBooleanProperty(aProps, Defn.IGNORE_IMPORTS_PROP);
        setIllegalImports(
            aProps.getProperty(Defn.ILLEGAL_IMPORTS_PROP, ILLEGAL_IMPORTS));
        setIllegalInstantiations(
            aProps.getProperty(Defn.ILLEGAL_INSTANTIATIONS_PROP,
                               ILLEGAL_INSTANTIATIONS));
        setBooleanProperty(aProps, Defn.IGNORE_WHITESPACE_PROP);
        setBooleanProperty(aProps, Defn.IGNORE_CAST_WHITESPACE_PROP);
        setBooleanProperty(aProps, Defn.IGNORE_OP_WRAP_PROP);
        setBooleanProperty(aProps, Defn.IGNORE_BRACES_PROP);
        setBooleanProperty(aProps, Defn.IGNORE_LONG_ELL_PROP);
        setBooleanProperty(aProps, Defn.IGNORE_PUBLIC_IN_INTERFACE_PROP);
        setCacheFile(aProps.getProperty(Defn.CACHE_FILE_PROP));
        setBooleanProperty(aProps, Defn.IGNORE_IMPORT_LENGTH_PROP);
        setHeaderIgnoreLines(aProps.getProperty(Defn.HEADER_IGNORE_LINE_PROP));
        setBooleanProperty(aProps, Defn.HEADER_LINES_REGEXP_PROP);

        final String fname = aProps.getProperty(Defn.HEADER_FILE_PROP);
        if (fname != null) {
            setHeaderFile(fname);
        }

        setLeftCurlyOptionProperty(aProps, Defn.LCURLY_METHOD_PROP, aLog);
        setLeftCurlyOptionProperty(aProps, Defn.LCURLY_TYPE_PROP, aLog);
        setLeftCurlyOptionProperty(aProps, Defn.LCURLY_OTHER_PROP, aLog);
        setRCurly(getRightCurlyOptionProperty(
                      aProps, Defn.RCURLY_PROP, RightCurlyOption.SAME, aLog));
        setBlockOptionProperty(aProps, Defn.TRY_BLOCK_PROP, aLog);
        setBlockOptionProperty(aProps, Defn.CATCH_BLOCK_PROP, aLog);
        setBlockOptionProperty(aProps, Defn.FINALLY_BLOCK_PROP, aLog);
        setParenPadOption(getPadOptionProperty(aProps,
                                               Defn.PAREN_PAD_PROP,
                                               PadOption.NOSPACE,
                                               aLog));
    }

    /**
     * Creates a new <code>Configuration</code> instance.
     * @throws IllegalStateException if an error occurs
     */
    public Configuration()
    {
        setIllegalImports(ILLEGAL_IMPORTS);
        setIllegalInstantiations(ILLEGAL_INSTANTIATIONS);
        try {
            setPatternProperty(Defn.TODO_PATTERN_PROP, TODO_PATTERN);
            setPatternProperty(Defn.PARAMETER_PATTERN_PROP, PARAMETER_PATTERN);
            setPatternProperty(Defn.STATIC_PATTERN_PROP, STATIC_PATTERN);
            setPatternProperty(Defn.CONST_PATTERN_PROP, CONST_PATTERN);
            setPatternProperty(Defn.MEMBER_PATTERN_PROP, MEMBER_PATTERN);
            setPatternProperty(Defn.PUBLIC_MEMBER_PATTERN_PROP,
                               PUBLIC_MEMBER_PATTERN);
            setPatternProperty(Defn.TYPE_PATTERN_PROP, TYPE_PATTERN);
            setPatternProperty(Defn.LOCAL_VAR_PATTERN_PROP, LOCAL_VAR_PATTERN);
            setPatternProperty(Defn.METHOD_PATTERN_PROP, METHOD_PATTERN);
            setPatternProperty(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP,
                               IGNORE_LINE_LENGTH_PATTERN);
        }
        catch (RESyntaxException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage());
        }
    }

    /**
     * Extend default deserialization to initialize the RE member variables.
     *
     * @param aStream the ObjectInputStream that contains the serialized data
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object
     *     could not be found
     */
    private void readObject(ObjectInputStream aStream)
        throws IOException, ClassNotFoundException
    {
        // initialize the serialized fields
        aStream.defaultReadObject();

        // initialize the transient fields
        mRegexps = new HashMap();
        try {
            // Loop on the patterns creating the RE's
            final Iterator keys = mPatterns.keySet().iterator();
            while (keys.hasNext()) {
                final String k = (String) keys.next();
                mRegexps.put(k, new RE((String) mPatterns.get(k)));
            }
        }
        catch (RESyntaxException ex) {
            // This should never happen, as the serialized regexp patterns
            // somehow must have passed a setPattern() method.
            throw new InvalidObjectException(
                "invalid regular expression syntax");
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////

    /** @return pattern to match todo lines **/
    public String getTodoPat()
    {
        return getPatternProperty(Defn.TODO_PATTERN_PROP);
    }

    /** @return regexp to match todo lines **/
    public RE getTodoRegexp()
    {
        return getRegexpProperty(Defn.TODO_PATTERN_PROP);
    }

    /** @return pattern to match parameters **/
    public String getParamPat()
    {
        return getPatternProperty(Defn.PARAMETER_PATTERN_PROP);
    }

    /** @return regexp to match parameters **/
    public RE getParamRegexp()
    {
        return getRegexpProperty(Defn.PARAMETER_PATTERN_PROP);
    }

    /** @return pattern to match static variables **/
    public String getStaticPat()
    {
        return getPatternProperty(Defn.STATIC_PATTERN_PROP);
    }

    /** @return regexp to match static variables **/
    public RE getStaticRegexp()
    {
        return getRegexpProperty(Defn.STATIC_PATTERN_PROP);
    }

    /** @return pattern to match static final variables **/
    public String getStaticFinalPat()
    {
        return getPatternProperty(Defn.CONST_PATTERN_PROP);
    }

    /** @return regexp to match static final variables **/
    public RE getStaticFinalRegexp()
    {
        return getRegexpProperty(Defn.CONST_PATTERN_PROP);
    }

    /** @return pattern to match member variables **/
    public String getMemberPat()
    {
        return getPatternProperty(Defn.MEMBER_PATTERN_PROP);
    }

    /** @return regexp to match member variables **/
    public RE getMemberRegexp()
    {
        return getRegexpProperty(Defn.MEMBER_PATTERN_PROP);
    }

    /** @return pattern to match public member variables **/
    public String getPublicMemberPat()
    {
        return getPatternProperty(Defn.PUBLIC_MEMBER_PATTERN_PROP);
    }

    /** @return regexp to match public member variables **/
    public RE getPublicMemberRegexp()
    {
        return getRegexpProperty(Defn.PUBLIC_MEMBER_PATTERN_PROP);
    }

    /** @return pattern to match type names **/
    public String getTypePat()
    {
        return getPatternProperty(Defn.TYPE_PATTERN_PROP);
    }

    /** @return regexp to match type names **/
    public RE getTypeRegexp()
    {
        return getRegexpProperty(Defn.TYPE_PATTERN_PROP);
    }

    /** @return pattern to match local variables **/
    public String getLocalVarPat()
    {
        return getPatternProperty(Defn.LOCAL_VAR_PATTERN_PROP);
    }

    /** @return regexp to match local variables **/
    public RE getLocalVarRegexp()
    {
        return getRegexpProperty(Defn.LOCAL_VAR_PATTERN_PROP);
    }

    /** @return pattern to match method names **/
    public String getMethodPat()
    {
        return getPatternProperty(Defn.METHOD_PATTERN_PROP);
    }

    /** @return regexp to match method names **/
    public RE getMethodRegexp()
    {
        return getRegexpProperty(Defn.METHOD_PATTERN_PROP);
    }

    /** @return the maximum line length **/
    public int getMaxLineLength()
    {
        return getIntProperty(Defn.MAX_LINE_LENGTH_PROP);
    }

    /** @return the maximum method length **/
    public int getMaxMethodLength()
    {
        return getIntProperty(Defn.MAX_METHOD_LENGTH_PROP);
    }

    /** @return the maximum constructor length **/
    public int getMaxConstructorLength()
    {
        return getIntProperty(Defn.MAX_CONSTRUCTOR_LENGTH_PROP);
    }

    /** @return the maximum file length **/
    public int getMaxFileLength()
    {
        return getIntProperty(Defn.MAX_FILE_LENGTH_PROP);
    }

    /** @return whether to allow tabs **/
    public boolean isAllowTabs()
    {
        return getBooleanProperty(Defn.ALLOW_TABS_PROP);
    }

    /** @return distance between tab stops */
    public int getTabWidth()
    {
        return getIntProperty(Defn.TAB_WIDTH_PROP);
    }

    /** @return whether to allow protected data **/
    public boolean isAllowProtected()
    {
        return getBooleanProperty(Defn.ALLOW_PROTECTED_PROP);
    }

    /** @return whether to allow package data **/
    public boolean isAllowPackage()
    {
        return getBooleanProperty(Defn.ALLOW_PACKAGE_PROP);
    }

    /** @return whether to allow having no author tag **/
    public boolean isAllowNoAuthor()
    {
        return getBooleanProperty(Defn.ALLOW_NO_AUTHOR_PROP);
    }

    /** @return visibility scope where Javadoc is checked **/
    public Scope getJavadocScope()
    {
        return mJavadocScope;
    }

    /** @return whether javadoc package documentation is required */
    public boolean isRequirePackageHtml()
    {
        return getBooleanProperty(Defn.REQUIRE_PACKAGE_HTML_PROP);
    }

    /** @return whether to process imports **/
    public boolean isIgnoreImports()
    {
        return getBooleanProperty(Defn.IGNORE_IMPORTS_PROP);
    }

    /** @return whether to check unused @throws **/
    public boolean isEnableCheckUnusedThrows()
    {
        return getBooleanProperty(Defn.JAVADOC_CHECK_UNUSED_THROWS_PROP);
    }

    /** @return Set of pkg prefixes that are illegal in import statements */
    public Set getIllegalImports()
    {
        return mIllegalImports;
    }

    /** @return Set of classes where calling a constructor is illegal */
    public Set getIllegalInstantiations()
    {
        return mIllegalInstantiations;
    }

    /** @return pattern to exclude from line lengh checking **/
    public String getIgnoreLineLengthPat()
    {
        return getPatternProperty(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP);
    }

    /** @return regexp to exclude from line lengh checking **/
    public RE getIgnoreLineLengthRegexp()
    {
        return getRegexpProperty(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP);
    }

    /** @return whether to ignore checks for whitespace **/
    public boolean isIgnoreWhitespace()
    {
        return getBooleanProperty(Defn.IGNORE_WHITESPACE_PROP);
    }

    /** @return whether to ignore checks for whitespace after casts **/
    public boolean isIgnoreCastWhitespace()
    {
        return getBooleanProperty(Defn.IGNORE_CAST_WHITESPACE_PROP);
    }

    /** @return whether to ignore checks for operator wrapping **/
    public boolean isIgnoreOpWrap()
    {
        return getBooleanProperty(Defn.IGNORE_OP_WRAP_PROP);
    }

    /** @return whether to ignore checks for braces **/
    public boolean isIgnoreBraces()
    {
        return getBooleanProperty(Defn.IGNORE_BRACES_PROP);
    }

    /** @return whether to ignore long 'L' **/
    public boolean isIgnoreLongEll()
    {
        return getBooleanProperty(Defn.IGNORE_LONG_ELL_PROP);
    }

    /** @return whether to ignore 'public' keyword in interface definitions **/
    public boolean isIgnorePublicInInterface()
    {
        return getBooleanProperty(Defn.IGNORE_PUBLIC_IN_INTERFACE_PROP);
    }

    /** @return whether to ignore max line length for import statements **/
    public boolean isIgnoreImportLength()
    {
        return getBooleanProperty(Defn.IGNORE_IMPORT_LENGTH_PROP);
    }

    /** @return the header lines to check for **/
    public String[] getHeaderLines()
    {
        return mHeaderLines;
    }


    /** @return if lines in header file are regular expressions */
    public boolean getHeaderLinesRegexp()
    {
        return getBooleanProperty(Defn.HEADER_LINES_REGEXP_PROP);
    }

    /**
     * @param aLineNo a line number
     * @return if <code>aLineNo</code> is one of the ignored header lines.
     */
    public boolean isHeaderIgnoreLineNo(int aLineNo)
    {
        return mHeaderIgnoreLineNo.contains(new Integer(aLineNo));
    }

    /** @return the name of the cache file **/
    public String getCacheFile()
    {
        return mCacheFile;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Setters
    ////////////////////////////////////////////////////////////////////////////

    /**
     * @param aPkgPrefixList comma separated list of package prefixes
     */
    public void setIllegalImports(String aPkgPrefixList)
    {
        mIllegalImports.clear();
        final StringTokenizer st = new StringTokenizer(aPkgPrefixList, ",");
        while (st.hasMoreTokens()) {
            mIllegalImports.add(st.nextToken());
        }
    }

    /**
     * @param aClassList comma separated list of fully qualified class names
     */
    public void setIllegalInstantiations(String aClassList)
    {
        mIllegalInstantiations.clear();
        final StringTokenizer st = new StringTokenizer(aClassList, ",");
        while (st.hasMoreTokens()) {
            mIllegalInstantiations.add(st.nextToken());
        }
    }

    /**
     * @param aJavadocScope visibility scope where Javadoc is checked
     */
    public void setJavadocScope(Scope aJavadocScope)
    {
        mJavadocScope = aJavadocScope;
    }

    /**
     * Set the boolean property.
     * @param aName name of the property. Should be defined in Defn.
     * @param aTo the value to set
     */
    public void setBooleanProperty(String aName, boolean aTo)
    {
        if (aTo) {
            mBooleanProps.add(aName);
        }
        else {
            mBooleanProps.remove(aName);
        }
    }

    /**
     * @param aFileName the header lines to check for
     * @throws FileNotFoundException if an error occurs
     * @throws IOException if an error occurs
     */
    public void setHeaderFile(String aFileName)
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
     * @param aList comma separated list of line numbers to ignore in header.
     */
    public void setHeaderIgnoreLines(String aList)
    {
        mHeaderIgnoreLineNo.clear();

        if (aList == null) {
            return;
        }

        final StringTokenizer tokens = new StringTokenizer(aList, ",");
        while (tokens.hasMoreTokens()) {
            final String ignoreLine = tokens.nextToken();
            mHeaderIgnoreLineNo.add(new Integer(ignoreLine));
        }
    }

    /**
     * @param aCacheFile name of cache file
     */
    public void setCacheFile(String aCacheFile)
    {
        mCacheFile = aCacheFile;
    }

    /** @return the left curly placement option for methods **/
    public LeftCurlyOption getLCurlyMethod()
    {
        return getLeftCurlyOptionProperty(Defn.LCURLY_METHOD_PROP);
    }

    /** @return the left curly placement option for types **/
    public LeftCurlyOption getLCurlyType()
    {
        return getLeftCurlyOptionProperty(Defn.LCURLY_TYPE_PROP);
    }

    /** @return the left curly placement option for others **/
    public LeftCurlyOption getLCurlyOther()
    {
        return getLeftCurlyOptionProperty(Defn.LCURLY_OTHER_PROP);
    }

    /** @return the right curly placement option **/
    public RightCurlyOption getRCurly()
    {
        return mRCurly;
    }

    /** @param aTo set the right curly placement option **/
    public void setRCurly(RightCurlyOption aTo)
    {
        mRCurly = aTo;
    }

    /** @return the try block option **/
    public BlockOption getTryBlock()
    {
        return getBlockOptionProperty(Defn.TRY_BLOCK_PROP);
    }

    /** @return the catch block option **/
    public BlockOption getCatchBlock()
    {
        return getBlockOptionProperty(Defn.CATCH_BLOCK_PROP);
    }

    /** @return the finally block option **/
    public BlockOption getFinallyBlock()
    {
        return getBlockOptionProperty(Defn.FINALLY_BLOCK_PROP);
    }

    /** @return the parenthesis padding option **/
    public PadOption getParenPadOption()
    {
        return mParenPadOption;
    }

    /** @param aTo set the parenthesis option **/
    public void setParenPadOption(PadOption aTo)
    {
        mParenPadOption = aTo;
    }

    /**
     * Set an integer property.
     * @param aName name of the property to set
     * @param aTo the value to set
     */
    public void setIntProperty(String aName, int aTo)
    {
        mIntProps.put(aName, new Integer(aTo));
    }

    /**
     * Set an pattern property.
     * @param aName name of the property to set
     * @param aPat the value to set
     * @throws RESyntaxException if an error occurs
     */
    public void setPatternProperty(String aName, String aPat)
        throws RESyntaxException
    {
        // Set the regexp first, incase cannot create the RE
        mRegexps.put(aName, new RE(aPat));
        mPatterns.put(aName, aPat);
    }

    /**
     * Set an BlockOption property.
     * @param aName name of the property to set
     * @param aTo the value to set
     */
    public void setBlockOptionProperty(String aName, BlockOption aTo)
    {
        mBlockProps.put(aName, aTo);
    }

    /**
     * Set an LeftCurlyOption property.
     * @param aName name of the property to set
     * @param aTo the value to set
     */
    public void setLeftCurlyOptionProperty(String aName, LeftCurlyOption aTo)
    {
        mLCurliesProps.put(aName, aTo);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * @return the BlockOption for a specified property.
     * @param aName name of the property to get
     */
    private LeftCurlyOption getLeftCurlyOptionProperty(String aName)
    {
        return (LeftCurlyOption) mLCurliesProps.get(aName);
    }

    /**
     * @return the BlockOption for a specified property.
     * @param aName name of the property to get
     */
    private BlockOption getBlockOptionProperty(String aName)
    {
        return (BlockOption) mBlockProps.get(aName);
    }

    /**
     * Set the value of an pattern property. If the property is not defined
     *    then a default value is used.
     * @param aProps the properties set to use
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     * @throws RESyntaxException if an error occurs
     */
    private void setPatternProperty(Properties aProps,
                                    String aName,
                                    String aDefault)
        throws RESyntaxException
    {
        setPatternProperty(aName, aProps.getProperty(aName, aDefault));
    }

    /**
     * @return the pattern for specified property
     * @param aName the name of the property
     */
    private String getPatternProperty(String aName)
    {
        return (String) mPatterns.get(aName);
    }

    /**
     * @return the regexp for specified property
     * @param aName the name of the property
     */
    private RE getRegexpProperty(String aName)
    {
        return (RE) mRegexps.get(aName);
    }

    /**
     * @return an integer property
     * @param aName the name of the integer property to get
     */
    private int getIntProperty(String aName)
    {
        return ((Integer) mIntProps.get(aName)).intValue();
    }

    /**
     * Set the value of an integer property. If the property is not defined
     *    or cannot be parsed, then a default value is used.
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     */
    private void setIntProperty(Properties aProps, PrintStream aLog,
                                String aName, int aDefault)
    {
        int val = aDefault;
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            try {
                val = Integer.parseInt(strRep);
            }
            catch (NumberFormatException nfe) {
                aLog.println("Unable to parse " + aName
                             + " property with value " + strRep
                             + ", defaulting to " + aDefault + ".");
            }
        }
        setIntProperty(aName, val);
    }

    /**
     * Set the value of a LeftCurlyOption property.
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     */
    private void setLeftCurlyOptionProperty(Properties aProps,
                                            String aName,
                                            PrintStream aLog)
    {
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            final LeftCurlyOption opt = LeftCurlyOption.decode(strRep);
            if (opt == null) {
                aLog.println("Unable to parse " + aName
                             + " property with value " + strRep
                             + ", leaving as "
                             + getLeftCurlyOptionProperty(aName) + ".");
            }
            else {
                setLeftCurlyOptionProperty(aName, opt);
            }
        }
    }

    /**
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     *
     * @return the value of a RightCurlyOption property. If the property is not
     *    defined or cannot be decoded, then a default value is returned.
     */
    private static RightCurlyOption getRightCurlyOptionProperty(
        Properties aProps,
        String aName,
        RightCurlyOption aDefault,
        PrintStream aLog)
    {
        RightCurlyOption retVal = aDefault;
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            retVal = RightCurlyOption.decode(strRep);
            if (retVal == null) {
                aLog.println("Unable to parse " + aName
                             + " property with value " + strRep
                             + ", defaulting to " + aDefault + ".");
            }
        }
        return retVal;
    }

    /**
     * Set the value of a BlockOption property.
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     */
    private void setBlockOptionProperty(Properties aProps,
                                        String aName,
                                        PrintStream aLog)
    {
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            final BlockOption opt = BlockOption.decode(strRep);
            if (opt == null) {
                aLog.println("Unable to parse " + aName
                             + " property with value " + strRep
                             + ", leaving as " + getBlockOptionProperty(aName)
                             + ".");
            }
            else {
                setBlockOptionProperty(aName, opt);
            }
        }
    }

    /**
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     *
     * @return the value of a PadOption property. If the property is not
     *    defined or cannot be decoded, then a default value is returned.
     */
    private static PadOption getPadOptionProperty(
        Properties aProps,
        String aName,
        PadOption aDefault,
        PrintStream aLog)
    {
        PadOption retVal = aDefault;
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            retVal = PadOption.decode(strRep);
            if (retVal == null) {
                aLog.println("Unable to parse " + aName
                             + " property with value " + strRep
                             + ", defaulting to " + aDefault + ".");
            }
        }
        return retVal;
    }

    /**
     * @param aName name of the boolean property
     * @return return whether a specified property is set
     */
    private boolean getBooleanProperty(String aName)
    {
        return mBooleanProps.contains(aName);
    }

    /**
     * Set a boolean property from a property set.
     * @param aProps the properties set to extract property from
     * @param aName name of the property to extract
     */
    private void setBooleanProperty(Properties aProps, String aName)
    {
        String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            strRep = strRep.toLowerCase().trim();
            if (strRep.equals("true") || strRep.equals("yes")
                || strRep.equals("on"))
            {
                setBooleanProperty(aName, true);
            }
        }
    }
}
