////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Checks the header of the source against a fixed header file.
 * In default configuration,if header is not specified,
 * the default value of header is set to null
 * and the check does not rise any violations.
 *
 * @author Lars Kühne
 */
public class HeaderCheck extends AbstractHeaderCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING = "header.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISMATCH = "header.mismatch";

    /** empty array to avoid instantiations. */
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    /** the header lines to ignore in the check, sorted. */
    private int[] ignoreLines = EMPTY_INT_ARRAY;

    /**
     * @param lineNo a line number
     * @return if {@code lineNo} is one of the ignored header lines.
     */
    private boolean isIgnoreLine(int lineNo) {
        return Arrays.binarySearch(ignoreLines, lineNo) >= 0;
    }

    /**
     * Checks if a code line matches the required header line.
     * @param lineNumber the line number to check against the header
     * @param line the line contents
     * @return true if and only if the line matches the required header line
     */
    protected boolean isMatch(int lineNumber, String line) {
        // skip lines we are meant to ignore
        return isIgnoreLine(lineNumber + 1)
            || getHeaderLines().get(lineNumber).equals(line);
    }

    /**
     * Set the lines numbers to ignore in the header check.
     * @param list comma separated list of line numbers to ignore in header.
     */
    public void setIgnoreLines(int... list) {
        if (list.length == 0) {
            ignoreLines = EMPTY_INT_ARRAY;
            return;
        }

        ignoreLines = new int[list.length];
        System.arraycopy(list, 0, ignoreLines, 0, list.length);
        Arrays.sort(ignoreLines);
    }

    @Override
    protected void processFiltered(File file, List<String> lines) {
        if (getHeaderLines().size() > lines.size()) {
            log(1, MSG_MISSING);
        }
        else {
            for (int i = 0; i < getHeaderLines().size(); i++) {
                if (!isMatch(i, lines.get(i))) {
                    log(i + 1, MSG_MISMATCH, getHeaderLines().get(i));
                    break; // stop checking
                }
            }
        }
    }
}
