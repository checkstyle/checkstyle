////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import org.apache.commons.beanutils.ConversionException;

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
 * 'crlf' (windows), 'cr' (mac) and 'lf' (unix).
 * </p>
 *
 * @author Christopher Lenz
 * @author lkuehne
 * @version 1.0
 */
public class NewlineAtEndOfFileCheck
    extends AbstractFileSetCheck
{
    /** the line separator to check against. */
    private LineSeparatorOption mLineSeparator = LineSeparatorOption.SYSTEM;

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        // Cannot use aLines as the line separators have been removed!
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(aFile, "r");
            if (!endsWithNewline(randomAccessFile)) {
                log(0, "noNewlineAtEOF", aFile.getPath());
            }
        }
        catch (final IOException e) {
            log(0, "unable.open", aFile.getPath());
        }
        finally {
            Utils.closeQuietly(randomAccessFile);
        }
    }

    /**
     * Sets the line separator to one of 'crlf', 'lf' or 'cr'.
     *
     * @param aLineSeparator The line separator to set
     * @throws IllegalArgumentException If the specified line separator is not
     *         one of 'crlf', 'lf' or 'cr'
     */
    public void setLineSeparator(String aLineSeparator)
    {
        try {
            mLineSeparator =
                Enum.valueOf(LineSeparatorOption.class, aLineSeparator.trim()
                    .toUpperCase());
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + aLineSeparator,
                iae);
        }
    }

    /**
     * Checks whether the content provided by the Reader ends with the platform
     * specific line separator.
     * @param aRandomAccessFile The reader for the content to check
     * @return boolean Whether the content ends with a line separator
     * @throws IOException When an IO error occurred while reading from the
     *         provided reader
     */
    private boolean endsWithNewline(RandomAccessFile aRandomAccessFile)
        throws IOException
    {
        final int len = mLineSeparator.length();
        if (aRandomAccessFile.length() < len) {
            return false;
        }
        aRandomAccessFile.seek(aRandomAccessFile.length() - len);
        final byte lastBytes[] = new byte[len];
        final int readBytes = aRandomAccessFile.read(lastBytes);
        if (readBytes != len) {
            throw new IOException("Unable to read " + len + " bytes, got "
                    + readBytes);
        }
        return mLineSeparator.matches(lastBytes);
    }
}
