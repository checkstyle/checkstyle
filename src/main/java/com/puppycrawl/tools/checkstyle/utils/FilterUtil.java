///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * Utility methods for suppression filters.
 *
 */
public final class FilterUtil {

    /** Stop instances being created. **/
    private FilterUtil() {
    }

    /**
     * Returns {@link FileText} instance created based on the given file name.
     *
     * @param fileName the name of the file.
     * @return {@link FileText} instance.
     * @throws IllegalStateException if the file could not be read.
     */
    public static FileText getFileText(String fileName) {
        final File file = new File(fileName);
        FileText result = null;
        // some violations can be on a directory, instead of a file
        if (!file.isDirectory()) {
            try {
                result = new FileText(file, StandardCharsets.UTF_8.name());
            }
            catch (IOException ex) {
                throw new IllegalStateException("Cannot read source file: " + fileName, ex);
            }
        }

        return result;
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
