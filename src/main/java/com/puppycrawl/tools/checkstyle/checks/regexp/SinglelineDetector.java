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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import java.util.List;
import java.util.regex.Matcher;

/**
 * A detector that matches individual lines.
 * @author oliver
 */
class SinglelineDetector {
    /** The detection options to use. */
    private final DetectorOptions options;
    /** Tracks the number of matches. */
    private int currentMatches;

    /**
     * Creates an instance.
     * @param options the options to use.
     */
    public SinglelineDetector(DetectorOptions options) {
        this.options = options;
    }

    /**
     * Processes a set of lines looking for matches.
     * @param lines the lines to process.
     */
    public void processLines(List<String> lines) {
        resetState();
        int lineno = 0;
        for (String line : lines) {
            lineno++;
            checkLine(lineno, line, options.getPattern().matcher(line), 0);
        }
        finish();
    }

    /** Perform processing at the end of a set of lines. */
    private void finish() {
        if (currentMatches < options.getMinimum()) {
            if (options.getMessage().isEmpty()) {
                options.getReporter().log(0, "regexp.minimum",
                        options.getMinimum(), options.getFormat());
            }
            else {
                options.getReporter().log(0, options.getMessage());
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
     * @param lineno the line number of the line to check
     * @param line the line to check
     * @param matcher the matcher to use
     * @param startPosition the position to start searching from.
     */
    private void checkLine(int lineno, String line, Matcher matcher,
            int startPosition) {
        final boolean foundMatch = matcher.find(startPosition);
        if (!foundMatch) {
            return;
        }

        // match is found, check for intersection with comment
        final int startCol = matcher.start(0);
        final int endCol = matcher.end(0);
        // Note that Matcher.end(int) returns the offset AFTER the
        // last matched character, but shouldSuppress()
        // needs column number of the last character.
        // So we need to use (endCol - 1) here.
        if (options.getSuppressor()
                .shouldSuppress(lineno, startCol, lineno, endCol - 1)) {
            if (endCol < line.length()) {
                // check if the expression is on the rest of the line
                checkLine(lineno, line, matcher, endCol);
            }
            return; // end processing here
        }

        currentMatches++;
        if (currentMatches > options.getMaximum()) {
            if (options.getMessage().isEmpty()) {
                options.getReporter().log(lineno, "regexp.exceeded",
                        matcher.pattern().toString());
            }
            else {
                options.getReporter().log(lineno, options.getMessage());
            }
        }
    }
}
