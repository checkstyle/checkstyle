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

package com.puppycrawl.tools.checkstyle.checks.header;

import java.util.Arrays;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Checks the header of the source against a header file that contains a
 * {@link java.util.regex.Pattern regular expression}
 * for each line of the source header.
 *
 * @author Lars Kühne
 * @author o_sukhodolsky
 */
public class RegexpHeaderCheck extends AbstractHeaderCheck
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
    public void setMultiLines(int[] aList)
    {
        if ((aList == null) || (aList.length == 0)) {
            mMultiLines = EMPTY_INT_ARRAY;
            return;
        }

        mMultiLines = new int[aList.length];
        System.arraycopy(aList, 0, mMultiLines, 0, aList.length);
        Arrays.sort(mMultiLines);
    }

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        final int headerSize = getHeaderLines().size();
        final int fileSize = aLines.size();

        if (headerSize - mMultiLines.length > fileSize) {
            log(1, "header.missing");
        }
        else {
            int headerLineNo = 0;
            int i;
            for (i = 0; (headerLineNo < headerSize) && (i < fileSize); i++) {
                final String line = aLines.get(i);
                boolean isMatch = isMatch(line, headerLineNo);
                while (!isMatch && isMultiLine(headerLineNo)) {
                    headerLineNo++;
                    isMatch = (headerLineNo == headerSize)
                            || isMatch(line, headerLineNo);
                }
                if (!isMatch) {
                    log(i + 1, "header.mismatch", getHeaderLines().get(
                            headerLineNo));
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
                        log(1, "header.missing");
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
        return mHeaderRegexps.get(aHeaderLineNo).matcher(aLine).find();
    }

    /**
     * @param aLineNo a line number
     * @return if <code>aLineNo</code> is one of the repeat header lines.
     */
    private boolean isMultiLine(int aLineNo)
    {
        return (Arrays.binarySearch(mMultiLines, aLineNo + 1) >= 0);
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
