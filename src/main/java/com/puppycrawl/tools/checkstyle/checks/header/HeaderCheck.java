///
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
///

package com.puppycrawl.tools.checkstyle.checks.header;

import java.io.File;
import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that a source file begins with a specified header.
 * Property {@code headerFile} specifies a file that contains the required header.
 * Alternatively, the header specification can be set directly in the
 * {@code header} property without the need for an external file.
 * </div>
 *
 * <p>
 * In default configuration, if header is not specified, the default value
 * of header is set to {@code null} and the check does not rise any violations.
 * </p>
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
 * Property {@code header} - Specify the required header specified inline.
 * Individual header lines must be separated by the string {@code "\n"}
 * (even on platforms with a different line separator).
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code headerFile} - Specify the name of the file containing the required header.
 * Type is {@code java.net.URI}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code ignoreLines} - Specifies the line
 *           numbers to ignore when matching lines in a content of headerFile.
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
public class HeaderCheck extends AbstractHeaderCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING = "header.missing";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISMATCH = "header.mismatch";

    /** Specifies the line numbers to ignore when matching lines in a content of headerFile. */
    private BitSet ignoreLines = new BitSet();

    /**
     * Returns true if lineNo is header lines or false.
     *
     * @param lineNo a line number
     * @return if {@code lineNo} is one of the ignored header lines.
     */
    private boolean isIgnoreLine(int lineNo) {
        return ignoreLines.get(lineNo);
    }

    /**
     * Checks if a code line matches the required header line.
     *
     * @param lineNumber the line number to check against the header
     * @param line the line contents
     * @return true if and only if the line matches the required header line
     */
    private boolean isMatch(int lineNumber, String line) {
        // skip lines we are meant to ignore
        return isIgnoreLine(lineNumber + 1)
            || getHeaderLines().get(lineNumber).equals(line);
    }

    /**
     * Setter to specifies the line numbers
     * to ignore when matching lines in a content of headerFile.
     *
     * @param lines line numbers to ignore in header.
     * @since 3.2
     */
    public void setIgnoreLines(int... lines) {
        ignoreLines = TokenUtil.asBitSet(lines);
    }

    @Override
    protected void processFiltered(File file, FileText fileText) {
        if (getHeaderLines().size() > fileText.size()) {
            log(1, MSG_MISSING);
        }
        else {
            for (int i = 0; i < getHeaderLines().size(); i++) {
                if (!isMatch(i, fileText.get(i))) {
                    log(i + 1, MSG_MISMATCH, getHeaderLines().get(i));
                    break;
                }
            }
        }
    }

    @Override
    protected void postProcessHeaderLines() {
        // no code
    }

}
