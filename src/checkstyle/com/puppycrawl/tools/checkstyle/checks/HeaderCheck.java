////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.Check;
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks the header of the source against a fixed header file.
 *
 * <p>
 * Rationale: In most projects each file must have a fixed header,
 * since usually the header contains copyright information.
 * </p>
 *
 * @author Lars Kühne
 */
public class HeaderCheck
    extends Check
{
    /** empty array to avoid instantiations */
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    /** the lines of the header file */
    private String[] mHeaderLines = null;

    /** the header lines to ignore in the check, sorted */
    private int[] mIgnoreLines = EMPTY_INT_ARRAY;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        if (mHeaderLines == null) {
            log(1, "Unable to check as missing lines to check.");
            return;
        }

        final String[] lines = getLines();

        if (mHeaderLines.length > lines.length) {
            log(1, "header.missing");
        }
        else {
            for (int i = 0; i < mHeaderLines.length; i++) {
                // skip lines we are meant to ignore
                if (isIgnoreLine(i + 1)) {
                    continue;
                }

                if (!isMatch(i)) {
                    log(i + 1, "header.mismatch", mHeaderLines[i]);
                    break; // stop checking
                }
            }
        }
    }

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
        final String[] lines = getLines();
        return mHeaderLines[aLineNumber].equals(lines[aLineNumber]);
    }

    /**
     * Set the header file to check against.
     * @param aFileName the file that contains the header to check against.
     * @throws ConversionException if the file cannot be loaded
     */
    public void setHeaderFile(String aFileName)
        throws ConversionException
    {
        // Handle empty param
        if ((aFileName == null) || (aFileName.trim().length() == 0)) {
            return;
        }

        // load the file
        try {
            final LineNumberReader lnr =
                    new LineNumberReader(new FileReader(aFileName));
            final ArrayList lines = new ArrayList();
            while (true) {
                final String l = lnr.readLine();
                if (l == null) {
                    break;
                }
                lines.add(l);
            }
            mHeaderLines = (String[]) lines.toArray(new String[0]);
        }
        catch (IOException ex) {
            throw new ConversionException(
                    "unable to load header file " + aFileName, ex);
        }

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

    /**
     * Return the header lines to check against.
     * @return the header lines to check against.
     */
    protected String[] getHeaderLines()
    {
        return mHeaderLines;
    }
}
