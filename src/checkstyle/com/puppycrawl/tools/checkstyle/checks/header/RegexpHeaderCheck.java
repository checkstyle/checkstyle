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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.AbstractHeaderCheck;

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

    /** the header lines to repeat (0 or more) in the check, sorted. */
    private int[] mMultiLines = EMPTY_INT_ARRAY;

    /** the compiled regular expressions */
    private Pattern[] mHeaderRegexps;

    /**
     * @param aLineNo a line number
     * @return if <code>aLineNo</code> is one of the repeat header lines.
     */
    private boolean isMultiLine(int aLineNo)
    {
        return (Arrays.binarySearch(mMultiLines, aLineNo + 1) >= 0);
    }

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

    /**
     * Sets the file that contains the header to check against.
     * @param aFileName the file that contains the header to check against.
     * @throws org.apache.commons.beanutils.ConversionException if the file
     * cannot be loaded or one line is not a regexp.
     */
    public void setHeaderFile(String aFileName)
        throws ConversionException
    {
        super.setHeaderFile(aFileName);
        initHeaderRegexps();
    }

    /**
     * Set the header to check against. Individual lines in the header
     * must be separated by '\n' characters.
     * @param aHeader header content to check against.
     * @throws org.apache.commons.beanutils.ConversionException if the header
     * cannot be loaded or one line is not a regexp.
     */
    public void setHeader(String aHeader)
    {
        super.setHeader(aHeader);
        initHeaderRegexps();
    }

    /**
     * Initializes {@link #mHeaderRegexps} from
     * {@link AbstractHeaderCheck#getHeaderLines()}.
     */
    private void initHeaderRegexps()
    {
        final String[] headerLines = getHeaderLines();
        if (headerLines != null) {
            mHeaderRegexps = new Pattern[headerLines.length];
            for (int i = 0; i < headerLines.length; i++) {
                try {
                    // TODO: Not sure if chache in Utils is still necessary
                    mHeaderRegexps[i] = Utils.getPattern(headerLines[i]);
                }
                catch (PatternSyntaxException ex) {
                    throw new ConversionException(
                            "line " + i + " in header specification"
                            + " is not a regular expression");
                }
            }
        }
    }

    /**
     * Checks if a code line matches the required header line.
     * @param aLineNo the linenumber to check against the header
     * @param aHeaderLineNo the header line number.
     * @return true if and only if the line matches the required header line.
     */
    private boolean isMatch(int aLineNo, int aHeaderLineNo)
    {
        final String line = getLines()[aLineNo];
        return mHeaderRegexps[aHeaderLineNo].matcher(line).find();
    }

    /** {@inheritDoc} */
    public void beginTree(DetailAST aRootAST)
    {
        final int headerSize = getHeaderLines().length;
        final int fileSize = getLines().length;

        if (headerSize - mMultiLines.length > fileSize) {
            log(1, "header.missing");
        }
        else {
            int headerLineNo = 0;
            int i;
            for (i = 0; (headerLineNo < headerSize) && (i < fileSize); i++) {
                boolean isMatch = isMatch(i, headerLineNo);
                while (!isMatch && isMultiLine(headerLineNo)) {
                    headerLineNo++;
                    isMatch = (headerLineNo == headerSize)
                        || isMatch(i, headerLineNo);
                }
                if (!isMatch) {
                    log(i + 1, "header.mismatch",
                        getHeaderLines()[headerLineNo]);
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
}
