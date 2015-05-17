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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import java.io.File;
import java.util.List;

/**
 * Implementation of a check that looks that matches across multiple lines in
 * any file type.
 * @author Oliver Burn
 */
public class RegexpMultilineCheck extends AbstractFileSetCheck {
    /** The detection options to use. */
    private DetectorOptions options = new DetectorOptions(Pattern.MULTILINE,
            this);
    /** The detector to use. */
    private MultilineDetector detector;

    @Override
    public void beginProcessing(String charset) {
        super.beginProcessing(charset);
        detector = new MultilineDetector(options);
    }

    @Override
    protected void processFiltered(File file, List<String> lines) {
        detector.processLines(FileText.fromLines(file, lines));
    }

    /**
     * Set the format of the regular expression to match.
     * @param format the format of the regular expression to match.
     */
    public void setFormat(String format) {
        options.setFormat(format);
    }

    /**
     * Set the message to report for a match.
     * @param message the message to report for a match.
     */
    public void setMessage(String message) {
        options.setMessage(message);
    }

    /**
     * Set the minimum number of matches required per file.
     * @param minimum the minimum number of matches required per file.
     */
    public void setMinimum(int minimum) {
        options.setMinimum(minimum);
    }

    /**
     * Set the maximum number of matches required per file.
     * @param maximum the maximum number of matches required per file.
     */
    public void setMaximum(int maximum) {
        options.setMaximum(maximum);
    }

    /**
     * Set whether to ignore case when matching.
     * @param ignore whether to ignore case when matching.
     */
    public void setIgnoreCase(boolean ignore) {
        options.setIgnoreCase(ignore);
    }
}
