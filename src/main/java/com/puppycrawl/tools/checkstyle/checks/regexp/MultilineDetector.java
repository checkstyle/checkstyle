///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
public class MultilineDetector {

    /** The detection options to use. */
    private final DetectorOptions options;
    /** The message key for exceeded matches. */
    private final String exceededMessage;
    /** The message key for minimum matches not met. */
    private final String minimumMessage;
    /** The message key for empty format. */
    private final String emptyMessage;
    /** The message key for StackOverflow error. */
    private final String stackOverflowMessage;
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
     * @param exceededMessage the message key for exceeded matches.
     * @param minimumMessage the message key for minimum matches not met.
     * @param emptyMessage the message key for empty format.
     * @param stackOverflowMessage the message key for StackOverflow error.
     */
    /* package */ MultilineDetector(DetectorOptions options,
            String exceededMessage, String minimumMessage,
            String emptyMessage, String stackOverflowMessage) {
        this.options = options;
        this.exceededMessage = exceededMessage;
        this.minimumMessage = minimumMessage;
        this.emptyMessage = emptyMessage;
        this.stackOverflowMessage = stackOverflowMessage;
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
            options.getReporter().log(1, emptyMessage);
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
                                exceededMessage,
                                        matcher.pattern().toString());
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
            // ok http://blog.igorminar.com/2008/05/catching-stackoverflowerror-and-bug-in.html
            // http://programmers.stackexchange.com/questions/
            //        209099/is-it-ever-okay-to-catch-stackoverflowerror-in-java
            options.getReporter().log(1, stackOverflowMessage,
                        matcher.pattern().toString());
        }
    }

    /** Perform processing at the end of a set of lines. */
    private void finish() {
        if (currentMatches < options.getMinimum()) {
            if (options.getMessage().isEmpty()) {
                options.getReporter().log(1, minimumMessage,
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
