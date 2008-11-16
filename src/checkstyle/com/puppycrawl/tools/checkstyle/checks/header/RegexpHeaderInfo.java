////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

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
    private final List<Pattern> mHeaderRegexps = Lists.newArrayList();

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
     * @return a list of non-null patterns,
     * same length as the result of {@link #getHeaderLines()}.
     */
    List<Pattern> geHeaderRegexps()
    {
        return Collections.unmodifiableList(mHeaderRegexps);
    }

    @Override
    protected void postprocessHeaderLines()
    {
        final List<String> headerLines = getHeaderLines();
        mHeaderRegexps.clear();
        for (String line : headerLines) {
            try {
                // TODO: Not sure if cache in Utils is still necessary
                mHeaderRegexps.add(Utils.getPattern(line));
            }
            catch (final PatternSyntaxException ex) {
                throw new ConversionException("line "
                        + (mHeaderRegexps.size() + 1)
                        + " in header specification"
                        + " is not a regular expression");
            }
        }
    }
}
