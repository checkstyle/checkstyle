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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <div>
 * Checks whether the files have a specific line ending (LF or CRLF).
 * </div>
 * @since 12.3.0
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
    public static final String MSG_KEY_WRONG_ENDING_LF = "wrong.line.end";

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_WRONG_ENDING_CRLF = "wrong.line.end.crlf";

    /**
     * Default line separator LF.
     */
    private LineEndingOption lineEnding = LineEndingOption.LF;

    @Override
    protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
        try {
            readAndCheckFile(file);
        } catch (final IOException ignore) {
            log(0, MSG_KEY_UNABLE_OPEN, file.getPath());
        }
    }

    /**
     * Setter to set lineEnding.
     *
     * @param ending string of value LF or CRLF
     * @since 12.3.0
     */
    public void setLineEnding(String ending) {
        lineEnding = Enum.valueOf(LineEndingOption.class,
                ending.trim().toUpperCase(Locale.ENGLISH));
    }

    /**
     * Reads the file provided and checks line endings.
     *
     * @param file the file to be processed
     * @throws IOException When an IO error occurred while reading from the
     *                     file provided
     */
    private void readAndCheckFile(File file) throws IOException {
        byte[] content = Files.readAllBytes(file.toPath());
        int line = 1;
        if (lineEnding == LineEndingOption.LF) {
            for (int index = 0; index < content.length; index++) {
                if (LineEndingOption.LF.matches(content[index])) {
                    if (index > 0 && LineEndingOption.isCarriageReturn(content[index - 1])) {
                        log(line, MSG_KEY_WRONG_ENDING_LF);
                    }
                    line++;
                }
            }
        } else {
            for (int index = 0; index < content.length; index++) {
                if (LineEndingOption.LF.matches(content[index])) {
                    if (index == 0 || !LineEndingOption.isCarriageReturn(content[index - 1])) {
                        log(line, MSG_KEY_WRONG_ENDING_CRLF);
                    }
                    line++;
                }
            }
        }
    }
}
