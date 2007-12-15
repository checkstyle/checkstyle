////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Checks that all packages have a package documentation.
 *
 * @author lkuehne
 */
public class PackageHtmlCheck extends AbstractFileSetCheck
{
    /**
     * Creates a new <code>PackageHtmlCheck</code> instance.
     */
    public PackageHtmlCheck()
    {
        // java, not html!
        // The rule is: Every JAVA file should have a package.html sibling
        setFileExtensions(new String[]{"java"});
    }

    /**
     * Checks that each java file in the fileset has a package.html sibling
     * and fires errors for the missing files.
     * @param aFiles a set of files
     */
    public void process(File[] aFiles)
    {
        final File[] javaFiles = filter(aFiles);
        final Set<File> directories = getParentDirs(javaFiles);
        for (final Iterator<File> it = directories.iterator(); it.hasNext();) {
            final File dir = it.next();
            final File packageHtml = new File(dir, "package.html");
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final String path = packageHtml.getPath();
            dispatcher.fireFileStarted(path);
            if (!packageHtml.exists()) {
                log(0, "javadoc.packageHtml");
                fireErrors(path);
            }
            dispatcher.fireFileFinished(path);
        }
    }

    /**
     * Returns the set of directories for a set of files.
     * @param aFiles s set of files
     * @return the set of parent directories of the given files
     */
    protected final Set<File> getParentDirs(File[] aFiles)
    {
        final Set<File> directories = new HashSet<File>();
        for (int i = 0; i < aFiles.length; i++) {
            final File f = aFiles[i].getAbsoluteFile();
            if (f.getName().endsWith(".java")) {
                final File dir = f.getParentFile();
                directories.add(dir); // duplicates are handled automatically
            }
        }
        return directories;
    }
}
