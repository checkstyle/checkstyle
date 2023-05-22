///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.util.regex.Matcher;

import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * A detector that matches individual lines.
 */
class SinglelineDetector {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_REGEXP_EXCEEDED = "regexp.exceeded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_REGEXP_MINIMUM = "regexp.minimum";

    /** The detection options to use. */
    private final DetectorOptions options;
    /** Tracks the number of matches. */
    private int currentMatches;

    /**
     * Creates an instance.
     *
     * @param options the options to use.
     */
    /* package */ SinglelineDetector(DetectorOptions options) {
        this.options = options;
    }

    /**
     * Processes a set of lines looking for matches.
     *
     * @param fileText {@link FileText} object contains the lines to process.
     */
    public void processLines(FileText fileText) {
        resetState();
        int lineNo = 0;
        for (int index = 0; index < fileText.size(); index++) {
            final String line = fileText.get(index);
            lineNo++;
            checkLine(lineNo, line, options.getPattern().matcher(line), 0);
        }
        finish();
    }

    /** Perform processing at the end of a set of lines. */
    private void finish() {
        if (currentMatches < options.getMinimum()) {
            if (options.getMessage().isEmpty()) {
                options.getReporter().log(1, MSG_REGEXP_MINIMUM,
                        options.getMinimum(), options.getFormat());
            }
            else {
                options.getReporter().log(1, options.getMessage());
            }
        }
    }

    /**
     * Reset the state of the detector.
     */
    private void resetState() {
        currentMatches = 0;
    }

    /**
     * Check a line for matches.
     *
     * @param lineNo the line number of the line to check
     * @param line the line to check
     * @param matcher the matcher to use
     * @param startPosition the position to start searching from.
     */
    private void checkLine(int lineNo, String line, Matcher matcher,
            int startPosition) {
        final boolean foundMatch = matcher.find(startPosition);
        if (foundMatch) {
            // match is found, check for intersection with comment
            final int startCol = matcher.start(0);
            final int endCol = matcher.end(0);
            // Note that Matcher.end(int) returns the offset AFTER the
            // last matched character, but shouldSuppress()
            // needs column number of the last character.
            // So we need to use (endCol - 1) here.
            if (options.getSuppressor()
                    .shouldSuppress(lineNo, startCol, lineNo, endCol - 1)) {
                checkLine(lineNo, line, matcher, endCol);
            }
            else {
                currentMatches++;
                if (currentMatches > options.getMaximum()) {
                    if (options.getMessage().isEmpty()) {
                        options.getReporter().log(lineNo, MSG_REGEXP_EXCEEDED,
                                matcher.pattern().toString());
                    }
                    else {
                        options.getReporter().log(lineNo, options.getMessage());
                    }
                }
            }
        }
    }

}
