////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Checks the header of the source against a header file that contains a
 * {@link Pattern regular expression}
 * for each line of the source header. In default configuration,
 * if header is not specified, the default value of header is set to null
 * and the check does not rise any violations.
 *
 * @author Lars Kühne
 * @author o_sukhodolsky
 */
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

    /** The compiled regular expressions. */
    private final List<Pattern> headerRegexps = Lists.newArrayList();

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
    protected void processFiltered(File file, List<String> lines) {
        final int headerSize = getHeaderLines().size();
        final int fileSize = lines.size();

        if (headerSize - multiLines.length > fileSize) {
            log(1, MSG_HEADER_MISSING);
        }
        else {
            int headerLineNo = 0;
            int index;
            for (index = 0; headerLineNo < headerSize && index < fileSize; index++) {
                final String line = lines.get(index);
                boolean isMatch = isMatch(line, headerLineNo);
                while (!isMatch && isMultiLine(headerLineNo)) {
                    headerLineNo++;
                    isMatch = headerLineNo == headerSize
                            || isMatch(line, headerLineNo);
                }
                if (!isMatch) {
                    log(index + 1, MSG_HEADER_MISMATCH, getHeaderLines().get(
                            headerLineNo));
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
     * @param lineNo a line number
     * @return if {@code lineNo} is one of the repeat header lines.
     */
    private boolean isMultiLine(int lineNo) {
        return Arrays.binarySearch(multiLines, lineNo + 1) >= 0;
    }

    @Override
    protected void postProcessHeaderLines() {
        final List<String> headerLines = getHeaderLines();
        headerRegexps.clear();
        for (String line : headerLines) {
            try {
                headerRegexps.add(Pattern.compile(line));
            }
            catch (final PatternSyntaxException ex) {
                throw new ConversionException("line "
                        + (headerRegexps.size() + 1)
                        + " in header specification"
                        + " is not a regular expression", ex);
            }
        }
    }

    /**
     * Validates the {@code header} by compiling it with
     * {@link Pattern#compile(String) } and throws
     * {@link PatternSyntaxException} if {@code header} isn't a valid pattern.
     * @param header the header value to validate and set (in that order)
     */
    @Override
    public void setHeader(String header) {
        if (!CommonUtils.isBlank(header)) {
            if (!CommonUtils.isPatternValid(header)) {
                throw new ConversionException("Unable to parse format: " + header);
            }
            super.setHeader(header);
        }
    }

}
