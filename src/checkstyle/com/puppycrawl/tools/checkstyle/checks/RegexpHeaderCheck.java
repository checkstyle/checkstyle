////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * Checks the header of the source against a header file that contains a
* <a href="http://jakarta.apache.org/regexp/apidocs/org/apache/regexp/RE.html">
 * regular expression</a>
 * for each line of the source header.
 * </p>
 * <p>
 * Rationale: In some projects checking against a fixed header
 * is not sufficient (see {@link HeaderCheck}), e.g.
 * the header might require a copyright line where the year information
 * is not static.
 * </p>
 *
 * <p>For example, consider the following header file:</p>
 *
 * <pre>
 * line 1: ^/{71}$
 * line 2: ^// checkstyle:$
 * line 3: ^// Checks Java source code for adherence to a set of rules\.$
 * line 4: ^// Copyright \(C\) \d\d\d\d  Oliver Burn$
 * line 5: ^// Last modification by \$Author.*\$$
 * line 6: ^/{71}$
 * </pre>
 *
 * <p>Lines 1 and 6 demonstrate a more compact notation for 71 '/'
 * characters. Line 4 enforces that the copyright notice includes a four digit
 * year. Line 5 is an example how to enforce revision control keywords in a file
 * header. All lines start from ^ (line start symbol) and end with $ (line end)
 * to force matching regexp with complete line in the source file.</p>
 * <p>An example of how to configure the check to use header file
 * &quot;java.header&quot; is:
 * </p>
 * <pre>
 * &lt;module name="RegexpHeader"&gt;
 *    &lt;property name="headerFile" value="java.header"/&gt;
 * &lt;/module&gt;
 * </pre>
 *    <p class="body">
 *    To configure the check to use header file <code
 *    >&quot;java.header&quot;</code> and <code
 *    >10</code> and  <code>13</code> muli-lines:
 *    </p>
 *    <pre class="body">
 * &lt;module name=&quot;RegexpHeader&quot;&gt;
 *   &lt;property name=&quot;headerFile&quot; value=&quot;java.header&quot;/&gt;
 *   &lt;property name=&quot;multiLines&quot; value=&quot;10, 13&quot;/&gt;
 *&lt;/module&gt;
 *     </pre>
 * <p><u>Note</u>: ignoreLines property has been removed from this check to
 * simplify it. The regular expression &quot;^.*$&quot; can be used to ignore a
 * line.
 * </p>
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
    private RE[] mHeaderRegexps;

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
        if (aList == null || aList.length == 0) {
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
     * @throws ConversionException if the file cannot be loaded or one line
     * is not a regexp.
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
     * @throws ConversionException if the header cannot be loaded or one line
     * is not a regexp.
     */
    public void setHeader(String aHeader)
    {
        super.setHeader(aHeader);
        initHeaderRegexps();
    }

    /** Initializes {@link #mHeaderRegexps} from {@link #mHeaderLines}. */
    private void initHeaderRegexps()
    {
        final String[] headerLines = getHeaderLines();
        if (headerLines != null) {
            mHeaderRegexps = new RE[headerLines.length];
            for (int i = 0; i < headerLines.length; i++) {
                try {
                    // TODO: Not sure if chache in Utils is still necessary
                    mHeaderRegexps[i] = Utils.getRE(headerLines[i]);
                }
                catch (RESyntaxException ex) {
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
        return mHeaderRegexps[aHeaderLineNo].match(line);
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
            for (i = 0; headerLineNo < headerSize && i < fileSize; i++) {
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
