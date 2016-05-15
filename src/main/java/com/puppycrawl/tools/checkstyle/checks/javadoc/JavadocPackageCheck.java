////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.File;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;

/**
 * Checks that all packages have a package documentation. See the documentation
 * for more information.
 * @author Oliver Burn
 */
public class JavadocPackageCheck extends AbstractFileSetCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LEGACY_PACKAGE_HTML = "javadoc.legacyPackageHtml";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PACKAGE_INFO = "javadoc.packageInfo";

    /** The directories checked. */
    private final Set<File> directoriesChecked = Sets.newHashSet();

    /** Indicates if allow legacy "package.html" file to be used. */
    private boolean allowLegacy;

    /**
     * Creates a new instance.
     */
    public JavadocPackageCheck() {
        // java, not html!
        // The rule is: Every JAVA file should have a package.html sibling
        setFileExtensions("java");
    }

    @Override
    public void beginProcessing(String charset) {
        super.beginProcessing(charset);
        directoriesChecked.clear();
    }

    @Override
    protected void processFiltered(File file, List<String> lines) {
        // Check if already processed directory
        final File dir = file.getParentFile();
        if (!directoriesChecked.contains(dir)) {
            directoriesChecked.add(dir);

            // Check for the preferred file.
            final File packageInfo = new File(dir, "package-info.java");
            final File packageHtml = new File(dir, "package.html");

            if (packageInfo.exists()) {
                if (packageHtml.exists()) {
                    log(0, MSG_LEGACY_PACKAGE_HTML);
                }
            }
            else if (!allowLegacy || !packageHtml.exists()) {
                log(0, MSG_PACKAGE_INFO);
            }
        }
    }

    /**
     * Indicates whether to allow support for the legacy <i>package.html</i>
     * file.
     * @param allowLegacy whether to allow support.
     */
    public void setAllowLegacy(boolean allowLegacy) {
        this.allowLegacy = allowLegacy;
    }
}
