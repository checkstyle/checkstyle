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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Checks that all packages have a package documentation. See the documentation
 * for more information.
 * @author Oliver Burn
 */
public class JavadocPackageCheck extends AbstractFileSetCheck
{
    /** Indicates if allow legacy "package.html" file to be used. */
    private boolean mAllowLegacy;
    /** The directories checked. */
    private final Set<File> mDirectoriesChecked = Sets.newHashSet();

    /**
     * Creates a new instance.
     */
    public JavadocPackageCheck()
    {
        // java, not html!
        // The rule is: Every JAVA file should have a package.html sibling
        setFileExtensions(new String[]{"java"});
    }

    @Override
    public void beginProcessing(String aCharset)
    {
        super.beginProcessing(aCharset);
        mDirectoriesChecked.clear();
    }

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        // Check if already processed directory
        final File dir = aFile.getParentFile();
        if (mDirectoriesChecked.contains(dir)) {
            return;
        }
        mDirectoriesChecked.add(dir);

        // Check for the preferred file.
        final File packageInfo = new File(dir, "package-info.java");
        final File packageHtml = new File(dir, "package.html");

        if (packageInfo.exists()) {
            if (packageHtml.exists()) {
                log(0, "javadoc.legacyPackageHtml");
            }
        }
        else if (!mAllowLegacy || !packageHtml.exists()) {
            log(0, "javadoc.packageInfo");
        }
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
