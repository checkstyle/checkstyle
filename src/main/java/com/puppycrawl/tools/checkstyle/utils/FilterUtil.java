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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.IOException;
import java.io.InputStream;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Utility methods for suppression filters.
 *
 */
public final class FilterUtil {

    /** Stop instances being created. **/
    private FilterUtil() {
    }

    /**
     * Checks if suppression source with given fileName exists.
     *
     * @param fileName name of the suppressions file.
     * @return true if suppression file exists, otherwise false
     */
    public static boolean isFileExists(String fileName) {
        boolean suppressionSourceExists;
        try (InputStream stream = CommonUtil.getUriByFilename(fileName).toURL().openStream()) {
            suppressionSourceExists = true;
        }
        catch (CheckstyleException | IOException ignored) {
            suppressionSourceExists = false;
        }
        return suppressionSourceExists;
    }

}
