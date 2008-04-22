////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Checks that all packages have a package documentation. See the documentation
 * for more information.
 * @author Oliver Buurn
 */
public class JavadocPackageCheck extends AbstractFileSetCheck
{
    /** Indicates if allow legacy "package.html" file to be used. */
    private boolean mAllowLegacy;

    /**
     * Creates a new instance.
     */
    public JavadocPackageCheck()
    {
        // java, not html!
        // The rule is: Every JAVA file should have a package.html sibling
        setFileExtensions(new String[]{"java"});
    }

    /** {@inheritDoc} */
    public void process(List<File> aFiles)
    {
        final List<File> javaFiles = filter(aFiles);
        final Set<File> directories = getParentDirs(javaFiles);
        for (File dir : directories) {
            // Check for the preferred file.
            final MessageDispatcher dispatcher = getMessageDispatcher();
            final File packageInfo = new File(dir, "package-info.java");
            final File packageHtml = new File(dir, "package.html");
            final String path;

            if (packageInfo.exists()) {
                path = packageInfo.getPath();
                dispatcher.fireFileStarted(path);
                if (packageHtml.exists()) {
                    log(0, "javadoc.legacyPackageHtml");
                }
            }
            else if (mAllowLegacy && packageHtml.exists()) {
                path = packageHtml.getPath();
                dispatcher.fireFileStarted(path);
            }
            else {
                path = packageInfo.getPath();
                dispatcher.fireFileStarted(path);
                log(0, "javadoc.packageInfo");
            }
            fireErrors(path);
            dispatcher.fireFileFinished(path);
        }
    }

    /**
     * Returns the set of directories for a set of files.
     * @param aFiles s set of files
     * @return the set of parent directories of the given files
     */
    protected final Set<File> getParentDirs(List<File> aFiles)
    {
        final Set<File> directories = Sets.newHashSet();
        for (File element : aFiles) {
            final File f = element.getAbsoluteFile();
            if (f.getName().endsWith(".java")) {
                final File dir = f.getParentFile();
                directories.add(dir); // duplicates are handled automatically
            }
        }
        return directories;
    }

    /**
     * Indicates whether to allow support for the legacy <i>package.html</i>
     * file.
     * @param aAllowLegacy whether to allow support.
     */
    public void setAllowLegacy(boolean aAllowLegacy)
    {
        mAllowLegacy = aAllowLegacy;
    }
}
