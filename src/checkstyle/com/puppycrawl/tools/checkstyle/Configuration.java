package com.puppycrawl.tools.checkstyle;

import java.lang.*;
import java.util.Properties;
import org.apache.regexp.RE;

/**
 * Represents the configuration that checkstyle uses when checking.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class Configuration
    implements Defn
{
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

    /** pattern to match type names **/
    private String mTypePat;
    /** regexp to match type names **/
    private RE mTypeRegexp;

    /** the maximum line length **/
    private int mMaxLineLength;
    /** whether to allow tabs **/
    private boolean mAllowTabs;
    /** whether to allow protected data **/
    private boolean mAllowProtected;
    /** whether to allow having no author tag **/
    private boolean mAllowNoAuthor;
    /** whether to relax javadoc checking **/
    private boolean mRelaxJavadoc;
    /** whether to process imports **/
    private boolean mCheckImports;
    /** the header lines to check for **/
    private String[] mHeaderLines;
    /** line number to ignore in header **/
    private int mHeaderIgnoreLineNo;

    ////////////////////////////////////////////////////////////////////////////
    // Constructors
    ////////////////////////////////////////////////////////////////////////////

    Configuration(Properties aProps)
    {
//          aProps.getProperty(PARAMETER_PATTERN_PROP,
//                             PARAMETER_PATTERN),
//              aProps.getProperty(STATIC_PATTERN_PROP,
//                                 STATIC_PATTERN),
//                  aProps.getProperty(CONST_PATTERN_PROP,
//                                     CONST_PATTERN),
//                  aProps.getProperty(MEMBER_PATTERN_PROP,
//                                     MEMBER_PATTERN),
//                  aProps.getProperty(TYPE_PATTERN_PROP,
//                                     TYPE_PATTERN),
//                  getIntProperty(aProps, MAX_LINE_LENGTH_PROP, MAX_LINE_LENGTH),
//                  getAllowTabs(aProps),
//                  getAllowProtected(aProps),
//                  getAllowNoAuthor(aProps),
//                  getRelaxJavadoc(aProps),
//                  getCheckImports(aProps),
//                  getHeaderLines(aProps),
//                  getIntProperty(aProps, HEADER_IGNORE_LINE_PROP, -1));
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

    /** @return whether to process imports **/
    boolean isCheckImports()
    {
        return mCheckImports;
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

    ////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////

    /** @param aParamPat pattern to match parameters **/
    String getParamPat(String aParamPat)
    {
        return mParamPat;
    }

    /** @param  regexp to match parameters **/
    RE getParamRegexp()
    {
        return mParamRegexp;
    }

    /** @param  pattern to match static variables **/
    String getStaticPat()
    {
        return mStaticPat;
    }

    /** @param  regexp to match static variables **/
    RE getStaticRegexp()
    {
        return mStaticRegexp;
    }

    /** @param  pattern to match static final variables **/
    String getStaticFinalPat()
    {
        return mStaticFinalPat;
    }

    /** @param  regexp to match static final variables **/
    RE getStaticFinalRegexp()
    {
        return mStaticFinalRegexp;
    }

    /** @param  pattern to match member variables **/
    String getMemberPat()
    {
        return mMemberPat;
    }

    /** @param  regexp to match member variables **/
    RE getMemberRegexp()
    {
        return mMemberRegexp;
    }

    /** @param  pattern to match type names **/
    String getTypePat()
    {
        return mTypePat;
    }

    /** @param  regexp to match type names **/
    RE getTypeRegexp()
    {
        return mTypeRegexp;
    }

    /** @param  the maximum line length **/
    int getMaxLineLength()
    {
        return mMaxLineLength;
    }

    /** @param  whether to allow tabs **/
    boolean isAllowTabs()
    {
        return mAllowTabs;
    }

    /** @param  whether to allow protected data **/
    boolean isAllowProtected()
    {
        return mAllowProtected;
    }

    /** @param  whether to allow having no author tag **/
    boolean isAllowNoAuthor()
    {
        return mAllowNoAuthor;
    }

    /** @param  whether to relax javadoc checking **/
    boolean isRelaxJavadoc()
    {
        return mRelaxJavadoc;
    }

    /** @param  whether to process imports **/
    boolean isCheckImports()
    {
        return mCheckImports;
    }

    /** @param  the header lines to check for **/
    String[] getHeaderLines()
    {
        return mHeaderLines;
    }

    /** @param  line number to ignore in header **/
    int getHeaderIgnoreLineNo()
    {
        return mHeaderIgnoreLineNo;
    }
}
