////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import com.google.common.collect.Lists;
import java.util.List;
import java.util.regex.Matcher;

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
    /** Relates StringBuffer positions to line # and column */
    private final List<Integer[]> mCharacters = Lists.newArrayList();
    /** The mMatcher */
    private Matcher mMatcher;

    /**
     * Creates an instance.
     * @param aOptions the options to use.
     */
    public MultilineDetector(DetectorOptions aOptions)
    {
        mOptions = aOptions;
    }

    /**
     * Processes a set of lines looking for matches.
     * @param aLines the lines to process.
     */
    public void processLines(List<String> aLines)
    {
        resetState();
        final StringBuffer sb = new StringBuffer();
        int lineno = 1;
        for (String line : aLines) {
            sb.append(line);
            sb.append('\n');
            for (int j = 0; j < (line.length() + 1); j++) {
                mCharacters.add(new Integer[] {lineno, j});
            }
            lineno++;
        }
        mMatcher = mOptions.getPattern().matcher(sb.toString());
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

        final int startLine = (mCharacters.get(mMatcher.start()))[0].intValue();
        final int startColumn = (mCharacters.get(mMatcher.start()))[1]
                .intValue();
        final int endLine = (mCharacters.get(mMatcher.end() - 1))[0].intValue();
        final int endColumn = (mCharacters.get(mMatcher.end() - 1))[1]
                .intValue();


        if (!mOptions.getSuppressor().shouldSuppress(startLine, startColumn,
                endLine, endColumn))
        {
            mCurrentMatches++;
            if (mCurrentMatches > mOptions.getMaximum()) {
                if ("".equals(mOptions.getMessage())) {
                    mOptions.getReporter().log(startLine, "regexp.exceeded",
                            mMatcher.pattern().toString());
                }
                else {
                    mOptions.getReporter()
                            .log(startLine, mOptions.getMessage());
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
        mCharacters.clear();
    }
}
