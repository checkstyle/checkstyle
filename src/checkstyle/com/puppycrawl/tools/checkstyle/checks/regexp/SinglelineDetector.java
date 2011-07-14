////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import java.util.List;
import java.util.regex.Matcher;

/**
 * A detector that matches individual lines.
 * @author oliver
 */
class SinglelineDetector
{
    /** The detection options to use. */
    private final DetectorOptions mOptions;
    /** Tracks the number of matches. */
    private int mCurrentMatches;

    /**
     * Creates an instance.
     * @param aOptions the options to use.
     */
    public SinglelineDetector(DetectorOptions aOptions)
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
        int lineno = 0;
        for (String line : aLines) {
            lineno++;
            checkLine(lineno, line, mOptions.getPattern().matcher(line), 0);
        }
        finish();
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

    /**
     * Check a line for matches.
     * @param aLineno the line number of the line to check
     * @param aLine the line to check
     * @param aMatcher the matcher to use
     * @param aStartPosition the position to start searching from.
     */
    private void checkLine(int aLineno, String aLine, Matcher aMatcher,
            int aStartPosition)
    {
        final boolean foundMatch = aMatcher.find(aStartPosition);
        if (!foundMatch) {
            return;
        }

        // match is found, check for intersection with comment
        final int startCol = aMatcher.start(0);
        final int endCol = aMatcher.end(0);
        // Note that Matcher.end(int) returns the offset AFTER the
        // last matched character, but shouldSuppress()
        // needs column number of the last character.
        // So we need to use (endCol - 1) here.
        if (mOptions.getSuppressor()
                .shouldSuppress(aLineno, startCol, aLineno, endCol - 1))
        {
            if (endCol < aLine.length()) {
                // check if the expression is on the rest of the line
                checkLine(aLineno, aLine, aMatcher, endCol);
            }
            return; // end processing here
        }

        mCurrentMatches++;
        if (mCurrentMatches > mOptions.getMaximum()) {
            if ("".equals(mOptions.getMessage())) {
                mOptions.getReporter().log(aLineno, "regexp.exceeded",
                        aMatcher.pattern().toString());
            }
            else {
                mOptions.getReporter().log(aLineno, mOptions.getMessage());
            }
        }
    }
}
