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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Represents the configuration that checkstyle uses when checking. The
 * configuration is Serializable, however the ClassLoader configuration is
 * lost.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public class Configuration
    implements Serializable
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////////////////////////////////

    /** the set of illegal imports (comma separated package prefixes) **/
    private static final String ILLEGAL_IMPORTS = "sun";
    /** the set of illegal instantiations (comma separated class names) **/
    private static final String ILLEGAL_INSTANTIATIONS = "";

    /** pattern defaults **/
    private static final Map PATTERN_DEFAULTS = new HashMap();
    static {
        PATTERN_DEFAULTS.put(Defn.TODO_PATTERN_PROP, "TODO:");
        PATTERN_DEFAULTS.put(Defn.STATIC_PATTERN_PROP, "^[a-z][a-zA-Z0-9]*$");
        PATTERN_DEFAULTS.put(Defn.CONST_PATTERN_PROP, "^[A-Z](_?[A-Z0-9]+)*$");
        PATTERN_DEFAULTS.put(Defn.MEMBER_PATTERN_PROP, "^[a-z][a-zA-Z0-9]*$");
        PATTERN_DEFAULTS.put(Defn.PUBLIC_MEMBER_PATTERN_PROP,
                             "^f[A-Z][a-zA-Z0-9]*$");
        PATTERN_DEFAULTS.put(Defn.LOCAL_VAR_PATTERN_PROP,
                             "^[a-z][a-zA-Z0-9]*$");
        PATTERN_DEFAULTS.put(Defn.LOCAL_FINAL_VAR_PATTERN_PROP,
                             "^[a-z][a-zA-Z0-9]*$");
        PATTERN_DEFAULTS.put(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP, "^$");
    }

    ////////////////////////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////////////////////////

    /** visibility scope where Javadoc is checked **/
    private Scope mJavadocScope = Scope.PRIVATE;

    /** the header lines to check for **/
    private transient String[] mHeaderLines = {};

    /**
     * class loader to resolve classes with. Needs to be transient as unable
     * to persist.
     **/
    private transient ClassLoader mLoader =
        Thread.currentThread().getContextClassLoader();

    /** the lines in the header to ignore */
    private TreeSet mHeaderIgnoreLineNo = new TreeSet();

    /** where to place right curlies  **/
    private RightCurlyOption mRCurly = RightCurlyOption.SAME;
    /** how to pad parenthesis **/
    private PadOption mParenPadOption = PadOption.NOSPACE;
    /** how to wrap operators **/
    private WrapOpOption mWrapOpOption = WrapOpOption.NL;

    /** set of boolean properties **/
    private final Set mBooleanProps = new HashSet();

    /** map of Set properties **/
    private final Map mStringSetProps = new HashMap();
    {
        setStringSetProperty(Defn.ILLEGAL_IMPORTS_PROP, ILLEGAL_IMPORTS);
        setStringSetProperty(Defn.ILLEGAL_INSTANTIATIONS_PROP,
                             ILLEGAL_INSTANTIATIONS);
    }

    /** map of int properties **/
    private final Map mIntProps = new HashMap();
    {
        mIntProps.put(Defn.MAX_LINE_LENGTH_PROP, new Integer(80));
        mIntProps.put(Defn.MAX_METHOD_LENGTH_PROP, new Integer(150));
        mIntProps.put(Defn.MAX_CONSTRUCTOR_LENGTH_PROP, new Integer(150));
        mIntProps.put(Defn.MAX_FILE_LENGTH_PROP, new Integer(2000));
        mIntProps.put(Defn.MAX_PARAMETERS_PROP, new Integer(7));
        mIntProps.put(Defn.TAB_WIDTH_PROP, new Integer(8));
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

    /** map of all String properties - by default are null **/
    private final Map mStringProps = new HashMap();
    {
        mStringProps.put(Defn.LOCALE_LANGUAGE_PROP,
                         Locale.getDefault().getLanguage());
        mStringProps.put(Defn.LOCALE_COUNTRY_PROP,
                         Locale.getDefault().getCountry());
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
        // Init the special properties
        setHeaderIgnoreLines(aProps.getProperty(Defn.HEADER_IGNORE_LINE_PROP));
        setRCurly(getRightCurlyOptionProperty(
                      aProps, Defn.RCURLY_PROP, RightCurlyOption.SAME, aLog));
        setJavadocScope(
            Scope.getInstance(aProps.getProperty(Defn.JAVADOC_CHECKSCOPE_PROP,
                                                 Scope.PRIVATE.getName())));
        setParenPadOption(getPadOptionProperty(aProps,
                                               Defn.PAREN_PAD_PROP,
                                               PadOption.NOSPACE,
                                               aLog));
        setWrapOpOption(getWrapOpOptionProperty(aProps,
                                                Defn.WRAP_OP_PROP,
                                                WrapOpOption.NL,
                                                aLog));

        // Initialise the general properties
        for (int i = 0; i < Defn.ALL_BOOLEAN_PROPS.length; i++) {
            setBooleanProperty(aProps, Defn.ALL_BOOLEAN_PROPS[i]);
        }

        for (int i = 0; i < Defn.ALL_PATTERN_PROPS.length; i++) {
            setPatternProperty(aProps, Defn.ALL_PATTERN_PROPS[i]);
        }

        for (int i = 0; i < Defn.ALL_INT_PROPS.length; i++) {
            setIntProperty(aProps, aLog, Defn.ALL_INT_PROPS[i]);
        }

        for (int i = 0; i < Defn.ALL_BLOCK_PROPS.length; i++) {
            setBlockOptionProperty(aProps, Defn.ALL_BLOCK_PROPS[i], aLog);
        }

        for (int i = 0; i < Defn.ALL_STRING_PROPS.length; i++) {
            setStringProperty(aProps, Defn.ALL_STRING_PROPS[i]);
        }

        for (int i = 0; i < Defn.ALL_LCURLY_PROPS.length; i++) {
            setLeftCurlyOptionProperty(aProps, Defn.ALL_LCURLY_PROPS[i], aLog);
        }

        for (int i = 0; i < Defn.ALL_STRING_SET_PROPS.length; i++) {
            setStringSetProperty(aProps, Defn.ALL_STRING_SET_PROPS[i]);
        }
    }

    /**
     * Creates a new <code>Configuration</code> instance.
     * @throws IllegalStateException if an error occurs
     */
    public Configuration()
    {
        try {
            for (int i = 0; i < Defn.ALL_PATTERN_PROPS.length; i++) {
                setPatternProperty(
                    Defn.ALL_PATTERN_PROPS[i],
                    (String) PATTERN_DEFAULTS.get(Defn.ALL_PATTERN_PROPS[i]));
            }
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
        mLoader = Thread.currentThread().getContextClassLoader();
        mRegexps = new HashMap();

        // load the file to re-read the lines
        loadFiles();

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

    /**
     * Loads the files specified by properties.
     * @throws IOException if an error occurs
     */
    void loadFiles()
        throws IOException
    {
        loadHeaderFile();
    }


    ////////////////////////////////////////////////////////////////////////////
    // Setters
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Set the class loader for locating classes.
     * @param aLoader the class loader
     */
    public void setClassLoader(ClassLoader aLoader)
    {
        mLoader = aLoader;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////

    /** @return a Properties object representing the current configuration.
     * The returned object can be used to recreate the configuration.
     * Tip: used on a default object returns all the default objects. */
    public Properties getProperties()
    {
        final Properties retVal = new Properties();

        Utils.addSetString(retVal, Defn.HEADER_IGNORE_LINE_PROP,
                           mHeaderIgnoreLineNo);
        Utils.addObjectString(retVal, Defn.RCURLY_PROP, mRCurly.toString());
        Utils.addObjectString(retVal, Defn.JAVADOC_CHECKSCOPE_PROP,
                              mJavadocScope.getName());
        Utils.addObjectString(retVal, Defn.PAREN_PAD_PROP,
                              mParenPadOption.toString());
        Utils.addObjectString(retVal, Defn.WRAP_OP_PROP,
                              mWrapOpOption.toString());

        for (int i = 0; i < Defn.ALL_BOOLEAN_PROPS.length; i++) {
            final String key = Defn.ALL_BOOLEAN_PROPS[i];
            retVal.put(key, String.valueOf(getBooleanProperty(key)));
        }

        for (int i = 0; i < Defn.ALL_PATTERN_PROPS.length; i++) {
            final String key = Defn.ALL_PATTERN_PROPS[i];
            Utils.addObjectString(retVal, key, getPatternProperty(key));
        }

        for (int i = 0; i < Defn.ALL_INT_PROPS.length; i++) {
            final String key = Defn.ALL_INT_PROPS[i];
            Utils.addObjectString(retVal, key,
                                  Integer.toString(getIntProperty(key)));
        }

        for (int i = 0; i < Defn.ALL_BLOCK_PROPS.length; i++) {
            final String key = Defn.ALL_BLOCK_PROPS[i];
            Utils.addObjectString(retVal, key, getBlockOptionProperty(key));
        }

        for (int i = 0; i < Defn.ALL_STRING_PROPS.length; i++) {
            final String key = Defn.ALL_STRING_PROPS[i];
            Utils.addObjectString(retVal, key, getStringProperty(key));
        }

        for (int i = 0; i < Defn.ALL_LCURLY_PROPS.length; i++) {
            final String key = Defn.ALL_LCURLY_PROPS[i];
            Utils.addObjectString(retVal, key, getLeftCurlyOptionProperty(key));
        }

        for (int i = 0; i < Defn.ALL_STRING_SET_PROPS.length; i++) {
            final String key = Defn.ALL_STRING_SET_PROPS[i];
            Utils.addSetString(retVal, key, getStringSetProperty(key));
        }

        return retVal;
    }

    /** @return the class loader **/
    ClassLoader getClassLoader()
    {
        return mLoader;
    }

    /** @return locale language to report messages  **/
    String getLocaleLanguage()
    {
        return getStringProperty(Defn.LOCALE_LANGUAGE_PROP);
    }

    /** @return locale country to report messages  **/
    String getLocaleCountry()
    {
        return getStringProperty(Defn.LOCALE_COUNTRY_PROP);
    }

    /** @return pattern to match to-do lines **/
    String getTodoPat()
    {
        return getPatternProperty(Defn.TODO_PATTERN_PROP);
    }

    /** @return regexp to match to-do lines **/
    RE getTodoRegexp()
    {
        return getRegexpProperty(Defn.TODO_PATTERN_PROP);
    }

    /** @return pattern to match static variables **/
    String getStaticPat()
    {
        return getPatternProperty(Defn.STATIC_PATTERN_PROP);
    }

    /** @return regexp to match static variables **/
    RE getStaticRegexp()
    {
        return getRegexpProperty(Defn.STATIC_PATTERN_PROP);
    }

    /** @return pattern to match static final variables **/
    String getStaticFinalPat()
    {
        return getPatternProperty(Defn.CONST_PATTERN_PROP);
    }

    /** @return regexp to match static final variables **/
    RE getStaticFinalRegexp()
    {
        return getRegexpProperty(Defn.CONST_PATTERN_PROP);
    }

    /** @return pattern to match member variables **/
    String getMemberPat()
    {
        return getPatternProperty(Defn.MEMBER_PATTERN_PROP);
    }

    /** @return regexp to match member variables **/
    RE getMemberRegexp()
    {
        return getRegexpProperty(Defn.MEMBER_PATTERN_PROP);
    }

    /** @return pattern to match public member variables **/
    String getPublicMemberPat()
    {
        return getPatternProperty(Defn.PUBLIC_MEMBER_PATTERN_PROP);
    }

    /** @return regexp to match public member variables **/
    RE getPublicMemberRegexp()
    {
        return getRegexpProperty(Defn.PUBLIC_MEMBER_PATTERN_PROP);
    }

    /** @return pattern to match local variables **/
    String getLocalVarPat()
    {
        return getPatternProperty(Defn.LOCAL_VAR_PATTERN_PROP);
    }

    /** @return regexp to match local variables **/
    RE getLocalVarRegexp()
    {
        return getRegexpProperty(Defn.LOCAL_VAR_PATTERN_PROP);
    }

    /** @return pattern to match local final variables **/
    String getLocalFinalVarPat()
    {
        return getPatternProperty(Defn.LOCAL_FINAL_VAR_PATTERN_PROP);
    }

    /** @return regexp to match local final variables **/
    RE getLocalFinalVarRegexp()
    {
        return getRegexpProperty(Defn.LOCAL_FINAL_VAR_PATTERN_PROP);
    }

    /** @return the maximum line length **/
    int getMaxLineLength()
    {
        return getIntProperty(Defn.MAX_LINE_LENGTH_PROP);
    }

    /** @return the maximum method length **/
    int getMaxMethodLength()
    {
        return getIntProperty(Defn.MAX_METHOD_LENGTH_PROP);
    }

    /** @return the maximum number parameters**/
    int getMaxParameters()
    {
        return getIntProperty(Defn.MAX_PARAMETERS_PROP);
    }

    /** @return the maximum constructor length **/
    int getMaxConstructorLength()
    {
        return getIntProperty(Defn.MAX_CONSTRUCTOR_LENGTH_PROP);
    }

    /** @return the maximum file length **/
    int getMaxFileLength()
    {
        return getIntProperty(Defn.MAX_FILE_LENGTH_PROP);
    }

    /** @return whether to allow tabs **/
    boolean isAllowTabs()
    {
        return getBooleanProperty(Defn.ALLOW_TABS_PROP);
    }

    /** @return distance between tab stops */
    int getTabWidth()
    {
        return getIntProperty(Defn.TAB_WIDTH_PROP);
    }

    /** @return whether to allow protected data **/
    boolean isAllowProtected()
    {
        return getBooleanProperty(Defn.ALLOW_PROTECTED_PROP);
    }

    /** @return whether to allow package data **/
    boolean isAllowPackage()
    {
        return getBooleanProperty(Defn.ALLOW_PACKAGE_PROP);
    }

    /** @return whether to allow having no author tag **/
    boolean isAllowNoAuthor()
    {
        return getBooleanProperty(Defn.ALLOW_NO_AUTHOR_PROP);
    }

    /** @return whether to require having version tag */
    boolean isRequireVersion()
    {
        return getBooleanProperty(Defn.REQUIRE_VERSION_PROP);
    }

    /** @return visibility scope where Javadoc is checked **/
    Scope getJavadocScope()
    {
        return mJavadocScope;
    }

    /** @return whether javadoc package documentation is required */
    boolean isRequirePackageHtml()
    {
        return getBooleanProperty(Defn.REQUIRE_PACKAGE_HTML_PROP);
    }

    /** @return whether to process imports **/
    boolean isIgnoreImports()
    {
        return getBooleanProperty(Defn.IGNORE_IMPORTS_PROP);
    }

    /** @return whether to check unused @throws **/
    boolean isCheckUnusedThrows()
    {
        return getBooleanProperty(Defn.JAVADOC_CHECK_UNUSED_THROWS_PROP);
    }

    /** @return Set of pkg prefixes that are illegal in import statements */
    Set getIllegalImports()
    {
        return getStringSetProperty(Defn.ILLEGAL_IMPORTS_PROP);
    }

    /** @return Set of classes where calling a constructor is illegal */
    Set getIllegalInstantiations()
    {
        return getStringSetProperty(Defn.ILLEGAL_INSTANTIATIONS_PROP);
    }

    /** @return pattern to exclude from line lengh checking **/
    String getIgnoreLineLengthPat()
    {
        return getPatternProperty(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP);
    }

    /** @return regexp to exclude from line lengh checking **/
    RE getIgnoreLineLengthRegexp()
    {
        return getRegexpProperty(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP);
    }

    /** @return whether to ignore checks for whitespace **/
    boolean isIgnoreWhitespace()
    {
        return getBooleanProperty(Defn.IGNORE_WHITESPACE_PROP);
    }

    /** @return whether to ignore checks for whitespace after casts **/
    boolean isIgnoreCastWhitespace()
    {
        return getBooleanProperty(Defn.IGNORE_CAST_WHITESPACE_PROP);
    }

    /** @return whether to ignore checks for braces **/
    boolean isIgnoreBraces()
    {
        return getBooleanProperty(Defn.IGNORE_BRACES_PROP);
    }

    /** @return whether to ignore 'public' keyword in interface definitions **/
    boolean isIgnorePublicInInterface()
    {
        return getBooleanProperty(Defn.IGNORE_PUBLIC_IN_INTERFACE_PROP);
    }

    /** @return whether to ignore max line length for import statements **/
    boolean isIgnoreImportLength()
    {
        return getBooleanProperty(Defn.IGNORE_IMPORT_LENGTH_PROP);
    }

    /** @return the header lines to check for **/
    String[] getHeaderLines()
    {
        return mHeaderLines;
    }


    /** @return if lines in header file are regular expressions */
    boolean getHeaderLinesRegexp()
    {
        return getBooleanProperty(Defn.HEADER_LINES_REGEXP_PROP);
    }

    /**
     * @param aLineNo a line number
     * @return if <code>aLineNo</code> is one of the ignored header lines.
     */
    boolean isHeaderIgnoreLineNo(int aLineNo)
    {
        return mHeaderIgnoreLineNo.contains(new Integer(aLineNo));
    }

    /** @return the File of the cache file **/
    String getCacheFile()
    {
        return getStringProperty(Defn.CACHE_FILE_PROP);
    }

    /**
     * Sets a String Set property. It the aFrom String is parsed for Strings
     * separated by ",".
     *
     * @param aName name of the property to set
     * @param aFrom the String to parse
     */
    private void setStringSetProperty(String aName, String aFrom)
    {
        final Set s = new TreeSet();
        final StringTokenizer tok = new StringTokenizer(aFrom, ",");
        while (tok.hasMoreTokens()) {
            s.add(tok.nextToken());
        }
        mStringSetProps.put(aName, s);
    }

    /**
     * @param aJavadocScope visibility scope where Javadoc is checked
     */
    private void setJavadocScope(Scope aJavadocScope)
    {
        mJavadocScope = aJavadocScope;
    }

    /**
     * Set the boolean property.
     * @param aName name of the property. Should be defined in Defn.
     * @param aTo the value to set
     */
    private void setBooleanProperty(String aName, boolean aTo)
    {
        if (aTo) {
            mBooleanProps.add(aName);
        }
        else {
            mBooleanProps.remove(aName);
        }
    }

    /**
     * Set the String property.
     * @param aName name of the property. Should be defined in Defn.
     * @param aTo the value to set
     */
    private void setStringProperty(String aName, String aTo)
    {
        mStringProps.put(aName, aTo);
    }

    /**
     * Attempts to load the contents of a header file
     * @throws FileNotFoundException if an error occurs
     * @throws IOException if an error occurs
     */
    private void loadHeaderFile()
        throws FileNotFoundException, IOException
    {
        final String fname = getStringProperty(Defn.HEADER_FILE_PROP);
        // Handle a missing property, or an empty one
        if ((fname == null) || (fname.trim().length() == 0)) {
            return;
        }

        // load the file
        final LineNumberReader lnr =
            new LineNumberReader(new FileReader(fname));
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
    private void setHeaderIgnoreLines(String aList)
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

    /** @return the left curly placement option for methods **/
    LeftCurlyOption getLCurlyMethod()
    {
        return getLeftCurlyOptionProperty(Defn.LCURLY_METHOD_PROP);
    }

    /** @return the left curly placement option for types **/
    LeftCurlyOption getLCurlyType()
    {
        return getLeftCurlyOptionProperty(Defn.LCURLY_TYPE_PROP);
    }

    /** @return the left curly placement option for others **/
    LeftCurlyOption getLCurlyOther()
    {
        return getLeftCurlyOptionProperty(Defn.LCURLY_OTHER_PROP);
    }

    /** @return the right curly placement option **/
    RightCurlyOption getRCurly()
    {
        return mRCurly;
    }

    /** @param aTo set the right curly placement option **/
    private void setRCurly(RightCurlyOption aTo)
    {
        mRCurly = aTo;
    }

    /** @return the try block option **/
    BlockOption getTryBlock()
    {
        return getBlockOptionProperty(Defn.TRY_BLOCK_PROP);
    }

    /** @return the catch block option **/
    BlockOption getCatchBlock()
    {
        return getBlockOptionProperty(Defn.CATCH_BLOCK_PROP);
    }

    /** @return the finally block option **/
    BlockOption getFinallyBlock()
    {
        return getBlockOptionProperty(Defn.FINALLY_BLOCK_PROP);
    }

    /** @return the parenthesis padding option **/
    PadOption getParenPadOption()
    {
        return mParenPadOption;
    }

    /** @param aTo set the parenthesis option **/
    private void setParenPadOption(PadOption aTo)
    {
        mParenPadOption = aTo;
    }

    /** @return the wrapping on operator option **/
    WrapOpOption getWrapOpOption()
    {
        return mWrapOpOption;
    }

    /** @param aTo set the wrap on operator option **/
    private void setWrapOpOption(WrapOpOption aTo)
    {
        mWrapOpOption = aTo;
    }

    /** @return the base directory **/
    String getBasedir()
    {
        return getStringProperty(Defn.BASEDIR_PROP);
    }

    /**
     * Set an integer property.
     * @param aName name of the property to set
     * @param aTo the value to set
     */
    private void setIntProperty(String aName, int aTo)
    {
        mIntProps.put(aName, new Integer(aTo));
    }

    /**
     * Set an pattern property.
     * @param aName name of the property to set
     * @param aPat the value to set
     * @throws RESyntaxException if an error occurs
     */
    private void setPatternProperty(String aName, String aPat)
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
    private void setBlockOptionProperty(String aName, BlockOption aTo)
    {
        mBlockProps.put(aName, aTo);
    }

    /**
     * Set an LeftCurlyOption property.
     * @param aName name of the property to set
     * @param aTo the value to set
     */
    private void setLeftCurlyOptionProperty(String aName, LeftCurlyOption aTo)
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
     * @throws RESyntaxException if an error occurs
     */
    private void setPatternProperty(Properties aProps, String aName)
        throws RESyntaxException
    {
        setPatternProperty(
            aName,
            aProps.getProperty(aName, (String) PATTERN_DEFAULTS.get(aName)));
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
     * @return an String property
     * @param aName the name of the String property to get
     */
    private String getStringProperty(String aName)
    {
        return (String) mStringProps.get(aName);
    }
    /**
     * Set the value of an integer property. If the property is not defined
     *    or cannot be parsed, then a default value is used.
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     */
    private void setIntProperty(Properties aProps,
                                PrintStream aLog,
                                String aName)
    {
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            try {
                final int val = Integer.parseInt(strRep);
                setIntProperty(aName, val);
            }
            catch (NumberFormatException nfe) {
                aLog.println(
                    "Unable to parse "
                        + aName
                        + " property with value "
                        + strRep
                        + ", defaulting to "
                        + getIntProperty(aName)
                        + ".");
            }
        }
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
     * @param aProps the properties set to use
     * @param aLog where to log errors to
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     *
     * @return the value of a WrapOpOption property. If the property is not
     *    defined or cannot be decoded, then a default value is returned.
     */
    private static WrapOpOption getWrapOpOptionProperty(
        Properties aProps,
        String aName,
        WrapOpOption aDefault,
        PrintStream aLog)
    {
        WrapOpOption retVal = aDefault;
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            retVal = WrapOpOption.decode(strRep);
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

    /**
     * @param aName name of the Set property
     * @return return whether a specified property is set
     */
    private Set getStringSetProperty(String aName)
    {
        return (Set) mStringSetProps.get(aName);
    }

    /**
     * Set a Set property from a property set.
     * @param aProps the properties set to extract property from
     * @param aName name of the property to extract
     */
    private void setStringSetProperty(Properties aProps, String aName)
    {
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            setStringSetProperty(aName, strRep);
        }
    }

    /**
     * Set a string property from a property set.
     * @param aProps the properties set to extract property from
     * @param aName name of the property to extract
     */
    private void setStringProperty(Properties aProps, String aName)
    {
        final String str = aProps.getProperty(aName);
        if (str != null) {
            setStringProperty(aName, aProps.getProperty(aName));
        }
    }
}
