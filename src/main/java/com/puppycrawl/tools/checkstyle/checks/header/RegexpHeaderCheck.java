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

package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Checks the header of the source against a header file that contains a
 * {@link Pattern regular expression}
 * for each line of the source header. In default configuration,
 * if header is not specified, the default value of header is set to null
 * and the check does not rise any violations.
 *
 */
@StatelessCheck
public class RegexpHeaderCheck extends AbstractHeaderCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_HEADER_MISSING = "header.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_HEADER_MISMATCH = "header.mismatch";

    /** Empty array to avoid instantiations. */
    private static final int[] EMPTY_INT_ARRAY = new int[0];

    /** Regex pattern for a blank line. **/
    private static final String EMPTY_LINE_PATTERN = "^$";

    /** Compiled regex pattern for a blank line. **/
    private static final Pattern BLANK_LINE = Pattern.compile(EMPTY_LINE_PATTERN);

    /** The compiled regular expressions. */
    private final List<Pattern> headerRegexps = new ArrayList<>();

    /** The header lines to repeat (0 or more) in the check, sorted. */
    private int[] multiLines = EMPTY_INT_ARRAY;

    /**
     * Set the lines numbers to repeat in the header check.
     * @param list comma separated list of line numbers to repeat in header.
     */
    public void setMultiLines(int... list) {
        if (list.length == 0) {
            multiLines = EMPTY_INT_ARRAY;
        }
        else {
            multiLines = new int[list.length];
            System.arraycopy(list, 0, multiLines, 0, list.length);
            Arrays.sort(multiLines);
        }
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        final int headerSize = getHeaderLines().size();
        final int fileSize = fileText.size();

        if (headerSize - multiLines.length > fileSize) {
            log(1, MSG_HEADER_MISSING);
        }
        else {
            int headerLineNo = 0;
            int index;
            for (index = 0; headerLineNo < headerSize && index < fileSize; index++) {
                final String line = fileText.get(index);
                boolean isMatch = isMatch(line, headerLineNo);
                while (!isMatch && isMultiLine(headerLineNo)) {
                    headerLineNo++;
                    isMatch = headerLineNo == headerSize
                            || isMatch(line, headerLineNo);
                }
                if (!isMatch) {
                    log(index + 1, MSG_HEADER_MISMATCH, getHeaderLine(headerLineNo));
                    break;
                }
                if (!isMultiLine(headerLineNo)) {
                    headerLineNo++;
                }
            }
            if (index == fileSize) {
                // if file finished, but we have at least one non-multi-line
                // header isn't completed
                logFirstSinglelineLine(headerLineNo, headerSize);
            }
        }
    }

    /**
     * Returns the line from the header. Where the line is blank return the regexp pattern
     * for a blank line.
     * @param headerLineNo header line number to return
     * @return the line from the header
     */
    private String getHeaderLine(int headerLineNo) {
        String line = getHeaderLines().get(headerLineNo);
        if (line.isEmpty()) {
            line = EMPTY_LINE_PATTERN;
        }
        return line;
    }

    /**
     * Logs warning if any non-multiline lines left in header regexp.
     * @param startHeaderLine header line number to start from
     * @param headerSize whole header size
     */
    private void logFirstSinglelineLine(int startHeaderLine, int headerSize) {
        for (int lineNum = startHeaderLine; lineNum < headerSize; lineNum++) {
            if (!isMultiLine(lineNum)) {
                log(1, MSG_HEADER_MISSING);
                break;
            }
        }
    }

    /**
     * Checks if a code line matches the required header line.
     * @param line the code line
     * @param headerLineNo the header line number.
     * @return true if and only if the line matches the required header line.
     */
    private boolean isMatch(String line, int headerLineNo) {
        return headerRegexps.get(headerLineNo).matcher(line).find();
    }

    /**
     * Returns true if line is multiline header lines or false.
     * @param lineNo a line number
     * @return if {@code lineNo} is one of the repeat header lines.
     */
    private boolean isMultiLine(int lineNo) {
        return Arrays.binarySearch(multiLines, lineNo + 1) >= 0;
    }

    @Override
    protected void postProcessHeaderLines() {
        final List<String> headerLines = getHeaderLines();
        for (String line : headerLines) {
            try {
                if (line.isEmpty()) {
                    headerRegexps.add(BLANK_LINE);
                }
                else {
                    headerRegexps.add(Pattern.compile(line));
                }
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException("line "
                        + (headerRegexps.size() + 1)
                        + " in header specification"
                        + " is not a regular expression", ex);
            }
        }
    }

    /**
     * Validates the {@code header} by compiling it with
     * {@link Pattern#compile(String) } and throws
     * {@link IllegalArgumentException} if {@code header} isn't a valid pattern.
     * @param header the header value to validate and set (in that order)
     */
    @Override
    public void setHeader(String header) {
        if (!CommonUtil.isBlank(header)) {
            if (!CommonUtil.isPatternValid(header)) {
                throw new IllegalArgumentException("Unable to parse format: " + header);
            }
            super.setHeader(header);
        }
    }

}
