////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.util.Set;
import java.util.HashSet;
import java.io.File;

import com.puppycrawl.tools.checkstyle.api.FileSetCheck;

/**
 * Provides common functionality for many FileSetChecks.
 * TODO: maybe this should be a public class and go into the api package?
 * TODO: shouldn't be public if it stays in this package
 * @author lkuehne
 */
public abstract class AbstractFileSetCheck implements FileSetCheck
{
    /**
     * Does nothing.
     * @see FileSetCheck
     */
    public void destroy()
    {
    }

    /**
     * Returns the set of directories for a set of files.
     * @param aFiles s set of files
     * @return the set of parent directories of the given files
     */
    protected Set getParentDirs(File[] aFiles)
    {
        Set directories = new HashSet();
        for (int i = 0; i < aFiles.length; i++) {
            File file = aFiles[i];
            if (file.getName().endsWith(".java")) {
                File dir = file.getParentFile();
                directories.add(dir); // duplicates are handled automatically
            }
        }
        return directories;
    }


}
