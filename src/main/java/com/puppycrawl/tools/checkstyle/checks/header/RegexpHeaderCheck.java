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

package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks the header of a source file against a header that contains a
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Pattern.html">
 * pattern</a> for each line of the source header.
 * </p>
 * <p>
 * Rationale: In some projects <a href="https://checkstyle.org/config_header.html#Header">
 * checking against a fixed header</a> is not sufficient, e.g. the header might
 * require a copyright line where the year information is not static.
 * </p>
 * <p>
 * For example, consider the following header:
 * </p>
 * <pre>
 * line  1: ^/{71}$
 * line  2: ^// checkstyle:$
 * line  3: ^// Checks Java source code for adherence to a set of rules\.$
 * line  4: ^// Copyright \(C\) \d\d\d\d  Oliver Burn$
 * line  5: ^// Last modification by \$Author.*\$$
 * line  6: ^/{71}$
 * line  7:
 * line  8: ^package
 * line  9:
 * line 10: ^import
 * line 11:
 * line 12: ^/\*\*
 * line 13: ^ \*([^/]|$)
 * line 14: ^ \*&#47;
 * </pre>
 * <p>
 * Lines 1 and 6 demonstrate a more compact notation for 71 '/' characters.
 * Line 4 enforces that the copyright notice includes a four digit year.
 * Line 5 is an example how to enforce revision control keywords in a file header.
 * Lines 12-14 is a template for javadoc (line 13 is so complicated to remove
 * conflict with and of javadoc comment). Lines 7, 9 and 11 will be treated
 * as '^$' and will forcefully expect the line to be empty.
 * </p>
 * <p>
 * Different programming languages have different comment syntax rules,
 * but all of them start a comment with a non-word character.
 * Hence, you can often use the non-word character class to abstract away
 * the concrete comment syntax and allow checking the header for different
 * languages with a single header definition. For example, consider the following
 * header specification (note that this is not the full Apache license header):
 * </p>
 * <pre>
 * line 1: ^#!
 * line 2: ^&lt;\?xml.*&gt;$
 * line 3: ^\W*$
 * line 4: ^\W*Copyright 2006 The Apache Software Foundation or its licensors, as applicable\.$
 * line 5: ^\W*Licensed under the Apache License, Version 2\.0 \(the "License"\);$
 * line 6: ^\W*$
 * </pre>
 * <p>
 * Lines 1 and 2 leave room for technical header lines, e.g. the "#!/bin/sh"
 * line in Unix shell scripts, or the XML file header of XML files.
 * Set the multiline property to "1, 2" so these lines can be ignored for
 * file types where they do no apply. Lines 3 through 6 define the actual header content.
 * Note how lines 2, 4 and 5 use escapes for characters that have special regexp semantics.
 * </p>
 * <p>
 * In default configuration, if header is not specified, the default value
 * of header is set to null and the check does not rise any violations.
 * </p>
 * <ul>
 * <li>
 * Property {@code headerFile} - Specify the name of the file containing the required header.
 * Type is {@code java.net.URI}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code charset} - Specify the character encoding to use when reading the headerFile.
 * Type is {@code java.lang.String}.
 * Default value is {@code the charset property of the parent
 * <a href="https://checkstyle.org/config.html#Checker">Checker</a> module}.
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
 * Property {@code multiLines} - Specify the line numbers to repeat (zero or more times).
 * Type is {@code int[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code fileExtensions} - Specify the file type extension of files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * To configure the check such that no violations arise.
 * Default values of properties are used.
 * </p>
 * <pre>
 * &lt;module name="RegexpHeader"/&gt;
 * </pre>
 * <p>
 * To configure the check to use header file {@code "config/java.header"} and
 * {@code 10} and {@code 13} multi-lines:
 * </p>
 * <pre>
 * &lt;module name="RegexpHeader"&gt;
 *   &lt;property name="headerFile" value="config/java.header"/&gt;
 *   &lt;property name="multiLines" value="10, 13"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to verify that each file starts with the header
 * </p>
 * <pre>
 * ^// Copyright \(C\) (\d\d\d\d -)? 2004 MyCompany$
 * ^// All rights reserved$
 * </pre>
 * <p>
 * without the need for an external header file:
 * </p>
 * <pre>
 * &lt;module name="RegexpHeader"&gt;
 *   &lt;property
 *     name="header"
 *     value="^// Copyright \(C\) (\d\d\d\d -)? 2004 MyCompany$
 *       \n^// All rights reserved$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * For regex containing {@code "\n\n"}
 * </p>
 * <pre>
 * &lt;module name="RegexpHeader"&gt;
 *   &lt;property
 *     name="header"
 *     value="^package .*\n\n.*"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * {@code "\n\n"} will be treated as '^$' and will forcefully expect the line
 * to be empty. For example -
 * </p>
 * <pre>
 * package com.some.package;
 * public class ThisWillFail { }
 * </pre>
 * <p>
 * would fail for the regex above. Expected -
 * </p>
 * <pre>
 * package com.some.package;
 *
 * public class ThisWillPass { }
 * </pre>
 * <p>
 * <u>Note</u>: {@code ignoreLines} property has been removed from this check to simplify it.
 * To make some line optional use "^.*$" regexp for this line.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
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
