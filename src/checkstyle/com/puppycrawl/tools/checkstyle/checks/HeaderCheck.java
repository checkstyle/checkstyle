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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Checks the header of the source against a fixed header file.
 * </p>
 * <p>
 * Rationale: In most projects each file must have a fixed header,
 * since usually the header contains copyright information.
 * </p>
 * <p> The header contents are specified in the file identified by property
 * headerFile.
 * </p>
 * <p>Property ignoreLines specifies the line numbers to ignore when
 * matching lines in a header file.
 * The property type is a comma-separated list of integers and defaults to an
 * empty list.
 * </p>
 *
 * <p>This property is very useful for supporting headers that contain copyright
 * dates. For example, consider the following header:</p>
 *
 * <pre>
 * line 1: ////////////////////////////////////////////////////////////////////
 * line 2: // checkstyle:
 * line 3: // Checks Java source code for adherence to a set of rules.
 * line 4: // Copyright (C) 2001  Oliver Burn
 * line 5: ////////////////////////////////////////////////////////////////////
 * </pre>
 *
 * <p>Since the year information will change over time, you can tell checkstyle
 * to ignore line 4 by setting property ignoreLines to <strong>4</strong>.</p>
 * <p>
 * An example of how to configure the check to use header file
 * &quot;java.header&quot; and ignore lines 2, 3, and 4 is:
 * </p>
 * <pre>
 * &lt;module name="Header"&gt;
 *    &lt;property name="headerFile" value="java.header"/&gt;
 *    &lt;property name="ignoreLines" value="2, 3, 4"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Lars Kühne
 */
public class HeaderCheck extends AbstractHeaderCheck
{
    /** empty array to avoid instantiations. */
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    /** the header lines to ignore in the check, sorted. */
    private int[] mIgnoreLines = EMPTY_INT_ARRAY;

    /**
     * @param aLineNo a line number
     * @return if <code>aLineNo</code> is one of the ignored header lines.
     */
    private boolean isIgnoreLine(int aLineNo)
    {
        return (Arrays.binarySearch(mIgnoreLines, aLineNo) >= 0);
    }

    /**
     * Checks if a code line matches the required header line.
     * @param aLineNumber the linenumber to check against the header
     * @return true if and only if the line matches the required header line
     */
    protected boolean isMatch(int aLineNumber)
    {
        final String line = getLines()[aLineNumber];
        // skip lines we are meant to ignore
        return isIgnoreLine(aLineNumber + 1)
            || getHeaderLines()[aLineNumber].equals(line);
    }

    /**
     * Set the lines numbers to ignore in the header check.
     * @param aList comma separated list of line numbers to ignore in header.
     */
    public void setIgnoreLines(int[] aList)
    {
        if (aList == null || aList.length == 0) {
            mIgnoreLines = EMPTY_INT_ARRAY;
            return;
        }

        mIgnoreLines = new int[aList.length];
        System.arraycopy(aList, 0, mIgnoreLines, 0, aList.length);
        Arrays.sort(mIgnoreLines);
    }

    /** {@inheritDoc} */
    public void beginTree(DetailAST aRootAST)
    {
        if (getHeaderLines().length > getLines().length) {
            log(1, "header.missing");
        }
        else {
            for (int i = 0; i < getHeaderLines().length; i++) {
                if (!isMatch(i)) {
                    log(i + 1, "header.mismatch", getHeaderLines()[i]);
                    break; // stop checking
                }
            }
        }
    }
}
