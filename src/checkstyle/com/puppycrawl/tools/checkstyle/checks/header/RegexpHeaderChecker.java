////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

package com.puppycrawl.tools.checkstyle.checks.header;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Isolates the check funtionality in RegexpHeaderCheck in an external class.
 * This makes it easier to use the funtionality both in a Java
 * {@link com.puppycrawl.tools.checkstyle.api.Check} and in
 * a {@link com.puppycrawl.tools.checkstyle.api.FileSetCheck}.
 *
 * @author lk
 */
class RegexpHeaderChecker
{
    /** the lines of the header file. */
    private final String[] mHeaderLines;

    /** the compiled regular expressions */
    private Pattern[] mHeaderRegexps;

    /** the header lines to repeat (0 or more) in the check, sorted. */
    private int[] mMultiLines;

    /** A monitor for the violations that are detected. */
    private final HeaderViolationMonitor mViolationObserver;

    /**
     * Creates a new instance.
     *
     * @param aRegexpHeaderInfo check parameters
     * @param aViolationObserver error reporting strategy object
     */
    RegexpHeaderChecker(
            RegexpHeaderInfo aRegexpHeaderInfo,
            HeaderViolationMonitor aViolationObserver)
    {
        mHeaderLines = aRegexpHeaderInfo.getHeaderLines();
        mHeaderRegexps = aRegexpHeaderInfo.geHeaderRegexps();
        mMultiLines = aRegexpHeaderInfo.getMultLines();
        mViolationObserver = aViolationObserver;
    }

    /**
     * Checks the lines of an individual file against the
     * {@link #getHeaderLines() header lines}.
     *
     * @param aLines the lines of an individual file
     */
    void checkLines(final String[] aLines)
    {
        final int headerSize = mHeaderLines.length;
        final int fileSize = aLines.length;

        if (headerSize - mMultiLines.length > fileSize) {
            mViolationObserver.reportHeaderMissing();
        }
        else {
            int headerLineNo = 0;
            int i;
            for (i = 0; (headerLineNo < headerSize) && (i < fileSize); i++) {
                final String line = aLines[i];
                boolean isMatch = isMatch(line, headerLineNo);
                while (!isMatch && isMultiLine(headerLineNo)) {
                    headerLineNo++;
                    isMatch = (headerLineNo == headerSize)
                        || isMatch(line, headerLineNo);
                }
                if (!isMatch) {
                    mViolationObserver.reportHeaderMismatch(
                            i + 1, mHeaderLines[headerLineNo]);
                    break; // stop checking
                }
                if (!isMultiLine(headerLineNo)) {
                    headerLineNo++;
                }
            }
            if (i == fileSize) {
                // if file finished, but we have at least one non-multi-line
                // header isn't completed
                for (; headerLineNo < headerSize; headerLineNo++) {
                    if (!isMultiLine(headerLineNo)) {
                        mViolationObserver.reportHeaderMissing();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if a code line matches the required header line.
     * @param aLine the code line
     * @param aHeaderLineNo the header line number.
     * @return true if and only if the line matches the required header line.
     */
    private boolean isMatch(String aLine, int aHeaderLineNo)
    {
        return mHeaderRegexps[aHeaderLineNo].matcher(aLine).find();
    }

    /**
     * @param aLineNo a line number
     * @return if <code>aLineNo</code> is one of the repeat header lines.
     */
    private boolean isMultiLine(int aLineNo)
    {
        return (Arrays.binarySearch(mMultiLines, aLineNo + 1) >= 0);
    }


}
