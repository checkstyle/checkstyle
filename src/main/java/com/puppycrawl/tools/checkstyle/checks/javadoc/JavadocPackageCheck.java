////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.puppycrawl.tools.checkstyle.GlobalStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;

/**
 * <p>
 * Checks that each Java package has a Javadoc file used for commenting.
 * By default it only allows a {@code package-info.java} file,
 * but can be configured to allow a {@code package.html} file.
 * </p>
 * <p>
 * A violation will be reported if both files exist as this is not allowed by the Javadoc tool.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowLegacy} - Allow legacy {@code package.html} file to be used.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code fileExtensions} - Specify the file type extension of files to process.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code .java}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="JavadocPackage"/&gt;
 * </pre>
 * <p>
 * To configure the check to use legacy {@code package.html} file
 * when {@code package-info.java} file is absent:
 * </p>
 * <pre>
 * &lt;module name="JavadocPackage"&gt;
 *   &lt;property name="allowLegacy" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.legacyPackageHtml}
 * </li>
 * <li>
 * {@code javadoc.packageInfo}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@GlobalStatefulCheck
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
    private final Set<File> directoriesChecked = ConcurrentHashMap.newKeySet();

    /** Allow legacy {@code package.html} file to be used. */
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
    protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
        // Check if already processed directory
        final File dir;
        try {
            dir = file.getCanonicalFile().getParentFile();
        }
        catch (IOException ex) {
            throw new CheckstyleException(
                    "Exception while getting canonical path to file " + file.getPath(), ex);
        }
        final boolean isDirChecked = !directoriesChecked.add(dir);
        if (!isDirChecked) {
            // Check for the preferred file.
            final File packageInfo = new File(dir, "package-info.java");
            final File packageHtml = new File(dir, "package.html");

            if (packageInfo.exists()) {
                if (packageHtml.exists()) {
                    log(1, MSG_LEGACY_PACKAGE_HTML);
                }
            }
            else if (!allowLegacy || !packageHtml.exists()) {
                log(1, MSG_PACKAGE_INFO);
            }
        }
    }

    /**
     * Setter to allow legacy {@code package.html} file to be used.
     *
     * @param allowLegacy whether to allow support.
     */
    public void setAllowLegacy(boolean allowLegacy) {
        this.allowLegacy = allowLegacy;
    }

}
