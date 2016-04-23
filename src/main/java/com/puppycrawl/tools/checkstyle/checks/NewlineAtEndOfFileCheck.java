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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;

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
 * @author Christopher Lenz
 * @author lkuehne
 */
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
    protected void processFiltered(File file, List<String> lines) {
        // Cannot use lines as the line separators have been removed!
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            boolean threw = true;
            try {
                if (!endsWithNewline(randomAccessFile)) {
                    log(0, MSG_KEY_NO_NEWLINE_EOF, file.getPath());
                }
                threw = false;
            }
            finally {
                Closeables.close(randomAccessFile, threw);
            }
        }
        catch (final IOException ignored) {
            log(0, MSG_KEY_UNABLE_OPEN, file.getPath());
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
            throw new ConversionException("unable to parse " + lineSeparatorParam,
                iae);
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
        final int len = lineSeparator.length();
        if (randomAccessFile.length() < len) {
            return false;
        }
        randomAccessFile.seek(randomAccessFile.length() - len);
        final byte[] lastBytes = new byte[len];
        final int readBytes = randomAccessFile.read(lastBytes);
        if (readBytes != len) {
            throw new IOException("Unable to read " + len + " bytes, got "
                    + readBytes);
        }
        return lineSeparator.matches(lastBytes);
    }
}
