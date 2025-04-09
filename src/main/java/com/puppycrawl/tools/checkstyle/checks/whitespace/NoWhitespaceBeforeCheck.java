///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;

/**
 * Checks that there is no whitespace before specific tokens.
 */
@StatelessCheck
public class NoWhitespaceBeforeCheck extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "ws.preceded";

    /**
     * Common whitespace patterns to check for.
     */
    private static final List<String> COMMON_WHITESPACE_PATTERNS = Arrays.asList(
        " ;",
        " (",
        "  = ",
        " =  ",
        ". (",
        ".  (",
        "\" ."
    );

    @Override
    protected void processFiltered(File file, FileText fileText) {
        for (int lineNum = 0; lineNum < fileText.size(); lineNum++) {
            if (containsWhitespacePattern(fileText.get(lineNum))) {
                log(lineNum + 1, MSG_KEY);
            }
        }
    }

    /**
     * Checks if the line contains any common whitespace patterns.
     */
    private boolean containsWhitespacePattern(String line) {
        return COMMON_WHITESPACE_PATTERNS.stream().anyMatch(line::contains);
    }
}
