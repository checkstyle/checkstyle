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

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.FileSetCheck;

/**
 * Checks that all packages have a package documentation.
 * @author lkuehne
 */
public class PackageHtmlCheck implements FileSetCheck
{
    /**
     * Checks that each java file in the fileset has a package.html sibling.
     * @param aFiles a set of files
     * @return the number of missing package.html files
     */
    public int process(File[] aFiles)
    {
        int missing = 0;
        Set directories = new HashSet();
        for (int i = 0; i < aFiles.length; i++) {
            File file = aFiles[i];
            if (file.getName().endsWith(".java")) {
                File dir = file.getParentFile();
                directories.add(dir); // duplicates are handled automatically
            }
        }
        for (Iterator it = directories.iterator(); it.hasNext();) {
            File dir = (File) it.next();
            File packageHtml = new File(dir, "package.html");
            // TODO: fireFileStarted
            if (!packageHtml.exists()) {
                // TODO: log error
                System.out.println("package.html missing");
                missing += 1;
            }
            // TODO: fireFileFinished
        }

        return missing;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.FileSetCheck */
    public void destroy()
    {
    }
}
