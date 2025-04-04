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

package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the header of a source file against a header that contains a
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Pattern.html">
 * pattern</a> for each line of the source header.
 * </div>
 * <ul>
 * <li>
 * Property {@code charset} - Specify the character encoding to use when reading the headerFile.
 * Type is {@code java.lang.String}.
 * Default value is {@code the charset property of the parent
 * <a href="https://checkstyle.org/config.html#Checker">Checker</a> module}.
 * </li>
 * <li>
 * Property {@code fileExtensions} - Specify the file extensions of the files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code header} - Define the required header specified inline.
 * Individual header lines must be separated by the string {@code "\n"}
 * (even on platforms with a different line separator).
 * For header lines containing {@code "\n\n"} checkstyle will
 * forcefully expect an empty line to exist. See examples below.
 * Regular expressions must not span multiple lines.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code headerFile} - Specify the name of the file containing the required header.
 * Type is {@code java.net.URI}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code multiLines} - Specify the line numbers to repeat (zero or more times).
 * Type is {@code int[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code header.mismatch}
 * </li>
 * <li>
 * {@code header.missing}
 * </li>
 * </ul>
 *
 * @since 6.9
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

    /** Regex pattern for a blank line. **/
    private static final String EMPTY_LINE_PATTERN = "^$";

    /** Compiled regex pattern for a blank line. **/
    private static final Pattern BLANK_LINE = Pattern.compile(EMPTY_LINE_PATTERN);

    /** The compiled regular expressions. */
    private final List<Pattern> headerRegexps = new ArrayList<>();

    /** Specify the line numbers to repeat (zero or more times). */
    private BitSet multiLines = new BitSet();

    /**
     * Setter to specify the line numbers to repeat (zero or more times).
     *
     * @param list line numbers to repeat in header.
     * @since 3.4
     */
    public void setMultiLines(int... list) {
        multiLines = TokenUtil.asBitSet(list);
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        final int headerSize = getHeaderLines().size();
        final int fileSize = fileText.size();

        if (headerSize - multiLines.cardinality() > fileSize) {
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
     *
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
     *
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
     *
     * @param line the code line
     * @param headerLineNo the header line number.
     * @return true if and only if the line matches the required header line.
     */
    private boolean isMatch(String line, int headerLineNo) {
        return headerRegexps.get(headerLineNo).matcher(line).find();
    }

    /**
     * Returns true if line is multiline header lines or false.
     *
     * @param lineNo a line number
     * @return if {@code lineNo} is one of the repeat header lines.
     */
    private boolean isMultiLine(int lineNo) {
        return multiLines.get(lineNo + 1);
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
     * Setter to define the required header specified inline.
     * Individual header lines must be separated by the string {@code "\n"}
     * (even on platforms with a different line separator).
     * For header lines containing {@code "\n\n"} checkstyle will forcefully
     * expect an empty line to exist. See examples below.
     * Regular expressions must not span multiple lines.
     *
     * @param header the header value to validate and set (in that order)
     * @since 5.0
     */
    @Override
    public void setHeader(String header) {
        if (!StringUtils.isBlank(header)) {
            if (!CommonUtil.isPatternValid(header)) {
                throw new IllegalArgumentException("Unable to parse format: " + header);
            }
            super.setHeader(header);
        }
    }

}
