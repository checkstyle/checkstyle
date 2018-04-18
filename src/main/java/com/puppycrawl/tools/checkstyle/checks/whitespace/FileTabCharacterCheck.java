////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import java.io.File;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * Checks to see if a file contains a tab character.
 */
@StatelessCheck
public class FileTabCharacterCheck extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CONTAINS_TAB = "containsTab";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_FILE_CONTAINS_TAB = "file.containsTab";

    /** Indicates whether to report once per file, or for each line. */
    private boolean eachLine;

    @Override
    protected void processFiltered(File file, FileText fileText) {
        int lineNum = 0;
        for (int index = 0; index < fileText.size(); index++) {
            final String line = fileText.get(index);
            lineNum++;
            final int tabPosition = line.indexOf('\t');
            if (tabPosition != -1) {
                if (eachLine) {
                    log(lineNum, tabPosition + 1, MSG_CONTAINS_TAB);
                }
                else {
                    log(lineNum, tabPosition + 1, MSG_FILE_CONTAINS_TAB);
                    break;
                }
            }
        }
    }

    /**
     * Whether report on each line containing a tab.
     * @param eachLine Whether report on each line containing a tab.
     */
    public void setEachLine(boolean eachLine) {
        this.eachLine = eachLine;
    }

}
