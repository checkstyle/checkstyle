////
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

package com.puppycrawl.tools.checkstyle.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class which provides OS related utilities.
 */
public final class OsSpecificUtil {

    /**
     * Hiding public and default constructor.
     */
    private OsSpecificUtil() {

    }

    /**
     * Updates the specified directory by resolving symbolic links, ensuring it exists,
     * and creating any necessary parent directories. If the provided path is a symbolic
     * link, it resolves it to the actual directory, throwing an IOException if the
     * resolved path is not a directory. Creates directories if they do not exist.
     *
     * @param directory The path to the directory to be updated.
     * @throws IOException If an I/O error occurs or if the resolved symbolic link is
     *                     not a directory.
     */
    public static void updateDirectory(Path directory) throws IOException {
        Path targetDirectory = directory;

        if (Files.isSymbolicLink(directory)) {
            final Path actualDir = directory.toRealPath();

            if (Files.isDirectory(actualDir)) {
                targetDirectory = actualDir;
            }
            else {
                throw new IOException("Resolved symbolic link "
                    + directory + " is not a directory.");
            }
        }
        Files.createDirectories(targetDirectory);
    }
}
