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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import java.io.File;
import java.util.List;

/**
 * Checks to see if a file contains a tab character.
 * @author oliverb
 */
public class FileTabCharacterCheck extends AbstractFileSetCheck
{
    /** Indicates whether to report once per file, or for each line. */
    private boolean mEachLine;

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        int lineNum = 0;
        for (final String line : aLines) {
            lineNum++;
            final int tabPosition = line.indexOf('\t');
            if (tabPosition != -1) {
                if (mEachLine) {
                    log(lineNum, tabPosition + 1, "containsTab");
                }
                else {
                    log(lineNum, tabPosition + 1, "file.containsTab");
                    break;
                }
            }
        }
    }

    /**
     * Whether report on each line containing a tab.
     * @param aEachLine Whether report on each line containing a tab.
     */
    public void setEachLine(boolean aEachLine)
    {
        mEachLine = aEachLine;
    }
}
