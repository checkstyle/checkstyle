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
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Header info for regexp based checks,
 * adds the multilines property and holds the compiled regexps.
 *
 * @author lkuehne
 */
final class RegexpHeaderInfo extends HeaderInfo
{
    /** empty array to avoid instantiations. */
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    /** the compiled regular expressions */
    private Pattern[] mHeaderRegexps;

    /** the header lines to repeat (0 or more) in the check, sorted. */
    private int[] mMultiLines = EMPTY_INT_ARRAY;

    /**
     * Set the lines numbers to repeat in the header check.
     * @param aList comma separated list of line numbers to repeat in header.
     */
    void setMultiLines(int[] aList)
    {
        if ((aList == null) || (aList.length == 0)) {
            mMultiLines = EMPTY_INT_ARRAY;
            return;
        }

        mMultiLines = new int[aList.length];
        System.arraycopy(aList, 0, mMultiLines, 0, aList.length);
        Arrays.sort(mMultiLines);
    }

    /**
     * Returns the lines numbers to repeat in the header check.
     * @return line numbers to repeat in header.
     */
    int[] getMultLines()
    {
        return mMultiLines;
    }


    /**
     * Returns the compiled regexps from {@link #getHeaderLines()}.
     *
     * @return an array of non-null patterns,
     * same legth as the result of {@link #getHeaderLines()}.
     */
    Pattern[] geHeaderRegexps()
    {
        return mHeaderRegexps;
    }

    /**
     * Initializes {@link #mHeaderRegexps} from
     * {@link HeaderInfo#getHeaderLines()}.
     */
    protected void postprocessHeaderLines()
    {
        final String[] headerLines = getHeaderLines();
        if (headerLines != null) {
            mHeaderRegexps = new Pattern[headerLines.length];
            for (int i = 0; i < headerLines.length; i++) {
                try {
                    // TODO: Not sure if cache in Utils is still necessary
                    mHeaderRegexps[i] = Utils.getPattern(headerLines[i]);
                }
                catch (final PatternSyntaxException ex) {
                    throw new ConversionException(
                            "line " + (i + 1) + " in header specification"
                            + " is not a regular expression");
                }
            }
        }
    }

}
