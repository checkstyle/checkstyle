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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.regex.Pattern;

/**
 * Represents an import rules for a specific file. Only the file name is
 * considered and only files processed by TreeWalker. The file's
 * extension is ignored.
 */
class FileImportControl extends AbstractImportControl {
    /** The name for the file. */
    private final String name;
    /** The regex pattern for exact matches - only not null if regex is true. */
    private final Pattern patternForExactMatch;
    /** If this file name represents a regular expression. */
    private final boolean regex;

    /**
     * Construct a file node.
     *
     * @param parent the parent node.
     * @param name the name of the file.
     * @param regex flags interpretation of name as regex pattern.
     */
    /* package */ FileImportControl(PkgImportControl parent, String name, boolean regex) {
        super(parent, MismatchStrategy.DELEGATE_TO_PARENT);
        this.regex = regex;
        this.name = name;
        patternForExactMatch = createPatternForExactMatch(name);
    }

    /**
     * Creates a Pattern from {@code expression}.
     *
     * @param expression a self-contained regular expression matching the full
     *     file name exactly.
     * @return a Pattern.
     */
    private static Pattern createPatternForExactMatch(String expression) {
        return Pattern.compile(expression);
    }

    @Override
    public AbstractImportControl locateFinest(String forPkg, String forFileName) {
        AbstractImportControl finestMatch = null;
        // Check if we are a match.
        if (matchesExactly(forPkg, forFileName)) {
            finestMatch = this;
        }
        return finestMatch;
    }

    @Override
    protected boolean matchesExactly(String pkg, String fileName) {
        final boolean result;
        if (fileName == null) {
            result = false;
        }
        else if (regex) {
            result = patternForExactMatch.matcher(fileName).matches();
        }
        else {
            result = name.equals(fileName);
        }
        return result;
    }
}
