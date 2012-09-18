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

import java.util.regex.Matcher;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * A detector that matches across multiple lines.
 * @author oliver
 */
class MultilineDetector
{
    /** The detection options to use. */
    private final DetectorOptions mOptions;
    /** Tracks the number of matches. */
    private int mCurrentMatches;
    /** The mMatcher */
    private Matcher mMatcher;
    /** The file text content */
    private FileText mText;

    /**
     * Creates an instance.
     * @param aOptions the options to use.
     */
    public MultilineDetector(DetectorOptions aOptions)
    {
        mOptions = aOptions;
    }

    /**
     * Processes an entire text file looking for matches.
     * @param aText the text to process
     */
    public void processLines(FileText aText)
    {
        mText = aText;
        resetState();
        mMatcher = mOptions.getPattern().matcher(mText.getFullText());
        findMatch();
        finish();
    }

    /** recursive method that finds the matches. */
    private void findMatch()
    {
        final boolean foundMatch = mMatcher.find();
        if (!foundMatch) {
            return;
        }

        final LineColumn start = mText.lineColumn(mMatcher.start());
        final LineColumn end = mText.lineColumn(mMatcher.end());

        if (!mOptions.getSuppressor().shouldSuppress(start.getLine(),
                start.getColumn(), end.getLine(), end.getColumn()))
        {
            mCurrentMatches++;
            if (mCurrentMatches > mOptions.getMaximum()) {
                if ("".equals(mOptions.getMessage())) {
                    mOptions.getReporter().log(start.getLine(),
                            "regexp.exceeded", mMatcher.pattern().toString());
                }
                else {
                    mOptions.getReporter()
                            .log(start.getLine(), mOptions.getMessage());
                }
            }
        }
        findMatch();
    }
    /** Perform processing at the end of a set of lines. */
    private void finish()
    {
        if (mCurrentMatches < mOptions.getMinimum()) {
            if ("".equals(mOptions.getMessage())) {
                mOptions.getReporter().log(0, "regexp.minimum",
                        mOptions.getMinimum(), mOptions.getFormat());
            }
            else {
                mOptions.getReporter().log(0, mOptions.getMessage());
            }
        }
    }

    /**
     * Reset the state of the detector.
     */
    private void resetState()
    {
        mCurrentMatches = 0;
    }
}
