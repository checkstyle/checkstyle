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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>
 * Checks that there is a newline at the end of each file.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NewlineAtEndOfFile"/&gt;</pre>
 * <p>
 * This will check against the platform-specific default line separator.
 * </p>
 * <p>
 * It is also possible to enforce the use of a specific line-separator across
 * platforms, with the 'lineSeparator' property:
 * </p>
 * <pre>
 * &lt;module name="NewlineAtEndOfFile"&gt;
 *   &lt;property name="lineSeparator" value="lf"/&gt;
 * &lt;/module&gt;</pre>
 * <p>
 * Valid values for the 'lineSeparator' property are 'system' (system default),
 * 'crlf' (windows), 'cr' (mac), 'lf' (unix) and 'lf_cr_crlf' (lf, cr or crlf).
 * </p>
 *
 */
@StatelessCheck
public class NewlineAtEndOfFileCheck
    extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_UNABLE_OPEN = "unable.open";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_NO_NEWLINE_EOF = "noNewlineAtEOF";

    /** The line separator to check against. */
    private LineSeparatorOption lineSeparator = LineSeparatorOption.SYSTEM;

    @Override
    protected void processFiltered(File file, FileText fileText) {
        try {
            readAndCheckFile(file);
        }
        catch (final IOException ignored) {
            log(1, MSG_KEY_UNABLE_OPEN, file.getPath());
        }
    }

    /**
     * Sets the line separator to one of 'crlf', 'lf','cr', 'lf_cr_crlf' or 'system'.
     *
     * @param lineSeparatorParam The line separator to set
     * @throws IllegalArgumentException If the specified line separator is not
     *         one of 'crlf', 'lf', 'cr', 'lf_cr_crlf' or 'system'
     */
    public void setLineSeparator(String lineSeparatorParam) {
        try {
            lineSeparator =
                Enum.valueOf(LineSeparatorOption.class, lineSeparatorParam.trim()
                    .toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("unable to parse " + lineSeparatorParam, iae);
        }
    }

    /**
     * Reads the file provided and checks line separators.
     * @param file the file to be processed
     * @throws IOException When an IO error occurred while reading from the
     *         file provided
     */
    private void readAndCheckFile(File file) throws IOException {
        // Cannot use lines as the line separators have been removed!
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            if (!endsWithNewline(randomAccessFile)) {
                log(1, MSG_KEY_NO_NEWLINE_EOF, file.getPath());
            }
        }
    }

    /**
     * Checks whether the content provided by the Reader ends with the platform
     * specific line separator.
     * @param randomAccessFile The reader for the content to check
     * @return boolean Whether the content ends with a line separator
     * @throws IOException When an IO error occurred while reading from the
     *         provided reader
     */
    private boolean endsWithNewline(RandomAccessFile randomAccessFile)
            throws IOException {
        final boolean result;
        final int len = lineSeparator.length();
        if (randomAccessFile.length() < len) {
            result = false;
        }
        else {
            randomAccessFile.seek(randomAccessFile.length() - len);
            final byte[] lastBytes = new byte[len];
            final int readBytes = randomAccessFile.read(lastBytes);
            if (readBytes != len) {
                throw new IOException("Unable to read " + len + " bytes, got "
                        + readBytes);
            }
            result = lineSeparator.matches(lastBytes);
        }
        return result;
    }

}
