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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <div>
 * Checks whether files use a specific line-ending sequence
 * ({@code LF}, {@code CRLF}, or {@code CR}).
 * </div>
 *
 * <p>
 * A violation is reported for each line whose actual line ending does not match
 * the configured one.
 * </p>
 *
 * <p>
 * Notes:
 * Files containing mixed line endings may produce multiple violations,
 * one for each non-matching line.
 * </p>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class LineEndingCheck extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_UNABLE_OPEN = "unable.open";

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_WRONG_ENDING = "wrong.line.ending";

    /**
     * Default line ending LF.
     */
    private LineEndingOption lineEnding = LineEndingOption.LF;

    @Override
    protected void processFiltered(File file, FileText fileText) {
        try {
            final byte[] content = Files.readAllBytes(file.toPath());
            checkLineEndings(lineEnding, content);
        }
        catch (final IOException ignore) {
            log(0, MSG_KEY_UNABLE_OPEN, file.getPath());
        }
    }

    /**
     * Setter to set lineEnding.
     *
     * @param ending string of value LF or CRLF
     * @since 13.1.0
     */
    public void setLineEnding(String ending) {
        lineEnding = Enum.valueOf(LineEndingOption.class,
                ending.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Checks that the file content uses the configured line ending
     * and logs a violation for each line that does not match.
     *
     * @param expected the expected line ending
     * @param content the file content as bytes
     */
    private void checkLineEndings(LineEndingOption expected, byte... content) {
        int line = 1;

        for (int index = 0; index < content.length; index++) {
            final byte current = content[index];
            if (LineEndingOption.CR.matches(current)) {
                final boolean nextIsLf = index + 1 < content.length
                        && LineEndingOption.LF.matches(content[index + 1]);
                final LineEndingOption actual;
                if (nextIsLf) {
                    actual = LineEndingOption.CRLF;
                }
                else {
                    actual = LineEndingOption.CR;
                }
                if (actual != expected) {
                    logIncorrectLineEnding(line, actual);
                }
                line++;
            }
            else if (LineEndingOption.LF.matches(current)) {
                final boolean prevIsCr = index > 0
                        && LineEndingOption.CR.matches(content[index - 1]);
                if (!prevIsCr) {
                    final LineEndingOption actual = LineEndingOption.LF;
                    if (actual != expected) {
                        logIncorrectLineEnding(line, actual);
                    }
                    line++;
                }
            }
        }
    }

    /**
     * Logs an incorrect line ending detected at the given line.
     *
     * @param line the line number where the issue was found
     * @param wrongLineEnding the detected incorrect line ending
     */
    private void logIncorrectLineEnding(int line,
                                        LineEndingOption wrongLineEnding) {
        log(line, MSG_KEY_WRONG_ENDING, lineEnding, wrongLineEnding);
    }

}
