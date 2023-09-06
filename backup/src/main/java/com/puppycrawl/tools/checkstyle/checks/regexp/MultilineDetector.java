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
import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * A detector that matches across multiple lines.
 */
class MultilineDetector {

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

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EMPTY = "regexp.empty";
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_STACKOVERFLOW = "regexp.StackOverflowError";

    /** The detection options to use. */
    private final DetectorOptions options;
    /** Tracks the number of matches. */
    private int currentMatches;
    /** The matcher. */
    private Matcher matcher;
    /** The file text content. */
    private FileText text;

    /**
     * Creates an instance.
     *
     * @param options the options to use.
     */
    /* package */ MultilineDetector(DetectorOptions options) {
        this.options = options;
    }

    /**
     * Processes an entire text file looking for matches.
     *
     * @param fileText the text to process
     */
    public void processLines(FileText fileText) {
        text = new FileText(fileText);
        resetState();

        final String format = options.getFormat();
        if (format == null || format.isEmpty()) {
            options.getReporter().log(1, MSG_EMPTY);
        }
        else {
            matcher = options.getPattern().matcher(fileText.getFullText());
            findMatch();
            finish();
        }
    }

    /** Method that finds the matches. */
    private void findMatch() {
        try {
            boolean foundMatch = matcher.find();

            while (foundMatch) {
                currentMatches++;
                if (currentMatches > options.getMaximum()) {
                    final LineColumn start = text.lineColumn(matcher.start());
                    if (options.getMessage().isEmpty()) {
                        options.getReporter().log(start.getLine(),
                                MSG_REGEXP_EXCEEDED, matcher.pattern().toString());
                    }
                    else {
                        options.getReporter()
                                .log(start.getLine(), options.getMessage());
                    }
                }
                foundMatch = matcher.find();
            }
        }
        // see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6337993 et al.
        catch (StackOverflowError ignored) {
            // OK http://blog.igorminar.com/2008/05/catching-stackoverflowerror-and-bug-in.html
            // http://programmers.stackexchange.com/questions/
            //        209099/is-it-ever-okay-to-catch-stackoverflowerror-in-java
            options.getReporter().log(1, MSG_STACKOVERFLOW, matcher.pattern().toString());
        }
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

}
