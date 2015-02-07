////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import java.util.regex.Matcher;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * A detector that matches across multiple lines.
 * @author oliver
 */
class MultilineDetector
{
    /** The detection options to use. */
    private final DetectorOptions options;
    /** Tracks the number of matches. */
    private int currentMatches;
    /** The matcher */
    private Matcher matcher;
    /** The file text content */
    private FileText text;

    /**
     * Creates an instance.
     * @param options the options to use.
     */
    public MultilineDetector(DetectorOptions options)
    {
        this.options = options;
    }

    /**
     * Processes an entire text file looking for matches.
     * @param text the text to process
     */
    public void processLines(FileText text)
    {
        this.text = text;
        resetState();
        matcher = options.getPattern().matcher(text.getFullText());
        findMatch();
        finish();
    }

    /** recursive method that finds the matches. */
    private void findMatch()
    {
        final boolean foundMatch = matcher.find();
        if (!foundMatch) {
            return;
        }

        final LineColumn start = text.lineColumn(matcher.start());
        final LineColumn end = text.lineColumn(matcher.end());

        if (!options.getSuppressor().shouldSuppress(start.getLine(),
                start.getColumn(), end.getLine(), end.getColumn()))
        {
            currentMatches++;
            if (currentMatches > options.getMaximum()) {
                if ("".equals(options.getMessage())) {
                    options.getReporter().log(start.getLine(),
                            "regexp.exceeded", matcher.pattern().toString());
                }
                else {
                    options.getReporter()
                            .log(start.getLine(), options.getMessage());
                }
            }
        }
        findMatch();
    }
    /** Perform processing at the end of a set of lines. */
    private void finish()
    {
        if (currentMatches < options.getMinimum()) {
            if ("".equals(options.getMessage())) {
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
    private void resetState()
    {
        currentMatches = 0;
    }
}
