////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.io.File;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <div>
 * Checks that there are no tab characters ({@code '\t'}) in the source code.
 * </div>
 *
 * <p>
 * Rationale:
 * </p>
 * <ul>
 * <li>
 * Developers should not need to configure the tab width of their text editors in order
 * to be able to read source code.
 * </li>
 * <li>
 * From the Apache jakarta coding standards: In a distributed development environment,
 * when the commit messages get sent to a mailing list, they are almost impossible to
 * read if you use tabs.
 * </li>
 * </ul>
 *
 * <p>
 * When the {@code FileTabCharacter} check is used with the default configuration,
 * only the first instance of a tab character is reported.
 * </p>
 *
 * <ul>
 * <li>
 * Property {@code eachLine} - Control whether to report on each line containing a tab,
 * or just the first instance.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code fileExtensions} - Specify the file extensions of the files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code containsTab}
 * </li>
 * <li>
 * {@code file.containsTab}
 * </li>
 * </ul>
 *
 * @since 5.0
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

    /** Control whether to report on each line containing a tab, or just the first instance. */
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
                    log(lineNum, tabPosition, MSG_CONTAINS_TAB);
                }
                else {
                    log(lineNum, tabPosition, MSG_FILE_CONTAINS_TAB);
                    break;
                }
            }
        }
    }

    /**
     * Setter to control whether to report on each line containing a tab, or just the first
     * instance.
     *
     * @param eachLine Whether report on each line containing a tab.
     * @since 5.0
     */
    public void setEachLine(boolean eachLine) {
        this.eachLine = eachLine;
    }

}
