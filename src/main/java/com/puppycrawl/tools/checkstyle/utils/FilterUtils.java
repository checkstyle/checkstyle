////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Utility methods for suppression filters.
 *
 * @author Timur Tibeyev.
 */
public final class FilterUtils {

    /** Stop instances being created. **/
    private FilterUtils() {

    }

    /**
     * Checks if suppression source with given fileName exists.
     * @param fileName name of the suppressions file.
     * @return true if suppression file exists, otherwise false
     */
    public static boolean isFileExists(String fileName) {
        boolean suppressionSourceExists = true;
        InputStream sourceInput = null;
        try {
            final URI uriByFilename = CommonUtils.getUriByFilename(fileName);
            final URL url = uriByFilename.toURL();
            sourceInput = url.openStream();
        }
        catch (CheckstyleException | IOException ignored) {
            suppressionSourceExists = false;
        }
        finally {
            final boolean closed = closeQuietlyWithResult(sourceInput);
            suppressionSourceExists = suppressionSourceExists && closed;
        }
        return suppressionSourceExists;
    }

    /**
     * Close input.
     * This method is required till https://github.com/cobertura/cobertura/issues/170
     * @param sourceInput stream to close
     * @return result of close operation
     */
    private static boolean closeQuietlyWithResult(InputStream sourceInput) {
        boolean closed = true;
        if (sourceInput != null) {
            try {
                sourceInput.close();
            }
            catch (IOException ignored) {
                closed = false;
            }
        }
        return closed;
    }
}
