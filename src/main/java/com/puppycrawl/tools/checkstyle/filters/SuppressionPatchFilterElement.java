////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;

import java.util.List;

/**
 * This filter element is immutable and processes.
 *
 */
public class SuppressionPatchFilterElement implements Filter {

    /** The String of file names. */
    private final String fileName;

    /** The list of line range. */
    private final List<List<Integer>> lineRangeList;

    /**
     * Constructs a {@code SuppressPatchFilterElement} for a
     * file name pattern.
     *
     * @param fileName names of filtered files.
     * @param lineRangeList   list of line range for line number filtering.
     */
    public SuppressionPatchFilterElement(String fileName, List<List<Integer>> lineRangeList) {
        this.fileName = fileName;
        this.lineRangeList = lineRangeList;
    }

    @Override
    public boolean accept(AuditEvent event) {
        return isFileNameMatching(event) && isLineMatching(event);
    }

    /**
     * Is matching by file name.
     *
     * @param event event
     * @return true if it is matching
     */
    private boolean isFileNameMatching(AuditEvent event) {
        return event.getFileName() != null
                && (event.getFileName()).equals(fileName);
    }

    /**
     * Whether line match.
     *
     * @param event event to process.
     * @return true if line and column are matching or not set.
     */
    private boolean isLineMatching(AuditEvent event) {
        boolean result = false;
        if (event.getLocalizedMessage() != null) {
            for (List<Integer> aLineRangeList : lineRangeList) {
                result = aLineRangeList.get(0) < event.getLine()
                        && event.getLine() < aLineRangeList.get(1);
                if (result) {
                    break;
                }
            }
        }
        return result;
    }
}
