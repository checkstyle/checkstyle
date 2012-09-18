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
package com.puppycrawl.tools.checkstyle.checks.regexp;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporter;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.regex.Pattern;

/**
 * Options for a detector.
 * @author Oliver Burn
 */
class DetectorOptions
{
    /**
     * Flags to compile a regular expression with.
     * See {@link Pattern#flags()}.
     */
    private final int mCompileFlags;
    /** Used for reporting violations. */
    private final AbstractViolationReporter mReporter;
    /** Format of the regular expression to check for. */
    private String mFormat;
    /** The message to report on detection. If blank, then use the format. */
    private String mMessage = "";
    /** Minimum number of times regular expression should occur in a file. */
    private int mMinimum;
    /** Maximum number of times regular expression should occur in a file. */
    private int mMaximum;
    /** Whether to ignore case when matching. */
    private boolean mIgnoreCase;
    /** Used to determine whether to suppress a detected match. */
    private MatchSuppressor mSuppressor = NeverSuppress.INSTANCE;

    /**
     * Creates an instance.
     * @param aCompileFlags the flags to create the regular expression with.
     * @param aReporter used to report violations.
     */
    public DetectorOptions(int aCompileFlags,
            AbstractViolationReporter aReporter)
    {
        mCompileFlags = aCompileFlags;
        mReporter = aReporter;
    }

    /**
     * The format to use when matching lines.
     * @param aFormat the format to use when matching lines.
     * @return current instance
     */
    public DetectorOptions setFormat(String aFormat)
    {
        mFormat = aFormat;
        return this;
    }

    /**
     * Message to use when reporting a match.
     * @param aMessage message to use when reporting a match.
     * @return current instance.
     */
    public DetectorOptions setMessage(String aMessage)
    {
        mMessage = aMessage;
        return this;
    }

    /**
     * Set the minimum allowed number of detections.
     * @param aMinimum the minimum allowed number of detections.
     * @return current instance
     */
    public DetectorOptions setMinimum(int aMinimum)
    {
        mMinimum = aMinimum;
        return this;
    }

    /**
     * Set the maximum allowed number of detections.
     * @param aMaximum the maximum allowed number of detections.
     * @return current instance
     */
    public DetectorOptions setMaximum(int aMaximum)
    {
        mMaximum = aMaximum;
        return this;
    }

    /**
     * Set the suppressor to use.
     * @param aSup the suppressor to use.
     * @return current instance
     */
    public DetectorOptions setSuppressor(MatchSuppressor aSup)
    {
        mSuppressor = aSup;
        return this;
    }

    /**
     * Set whether to ignore case when matching.
     * @param aIgnore whether to ignore case when matching.
     * @return current instance
     */
    public DetectorOptions setIgnoreCase(boolean aIgnore)
    {
        mIgnoreCase = aIgnore;
        return this;
    }

    /**
     * Format of the regular expression.
     * @return format of the regular expression.
     */
    public String getFormat()
    {
        return mFormat;
    }

    /**
     * The violation reporter to use.
     * @return the violation reporter to use.
     */
    public AbstractViolationReporter getReporter()
    {
        return mReporter;
    }

    /**
     * The message to report errors with.
     * @return the message to report errors with.
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * The minimum number of allowed detections.
     * @return the minimum number of allowed detections.
     */
    public int getMinimum()
    {
        return mMinimum;
    }

    /**
     * The maximum number of allowed detections.
     * @return the maximum number of allowed detections.
     */
    public int getMaximum()
    {
        return mMaximum;
    }

    /**
     * The suppressor to use.
     * @return the suppressor to use.
     */
    public MatchSuppressor getSuppressor()
    {
        return mSuppressor;
    }

    /**
     * Whether to ignore case when matching.
     * @return whether to ignore case when matching.
     */
    public boolean isIgnoreCase()
    {
        return mIgnoreCase;
    }

    /**
     * The pattern to use when matching.
     * @return the pattern to use when matching.
     */
    public Pattern getPattern()
    {
        final int options = (mIgnoreCase) ? mCompileFlags
                | Pattern.CASE_INSENSITIVE : mCompileFlags;
        return Utils.getPattern(mFormat, options);
    }
}
