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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.io.File;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Ensures that a class has a package declaration, and (optionally) whether
 * the package name matches the directory name for the source file.
 * </p>
 * <p>
 * Rationale: Classes that live in the null package cannot be imported.
 * Many novice developers are not aware of this.
 * </p>
 * <p>
 * Packages provide logical namespace to classes and should be stored in
 * the form of directory levels to provide physical grouping to your classes.
 * These directories are added to the classpath so that your classes
 * are visible to JVM when it runs the code.
 * </p>
 * <ul>
 * <li>
 * Property {@code matchDirectoryStructure} - Control whether to check for
 * directory and package name match.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;PackageDeclaration&quot;/&gt;
 * </pre>
 * <p>
 * Let us consider the class AnnotationLocationCheck which is in the directory
 * /com/puppycrawl/tools/checkstyle/checks/annotations/
 * </p>
 * <pre>
 * package com.puppycrawl.tools.checkstyle.checks; //Violation
 * public class AnnotationLocationCheck extends AbstractCheck {
 *   //...
 * }
 * </pre>
 * <p>
 * Example of how the check works when matchDirectoryStructure option is set to false.
 * Let us again consider the AnnotationLocationCheck class located at directory
 * /com/puppycrawl/tools/checkstyle/checks/annotations/ along with the following setup,
 * </p>
 * <pre>
 * &lt;module name=&quot;PackageDeclaration&quot;&gt;
 * &lt;property name=&quot;matchDirectoryStructure&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * package com.puppycrawl.tools.checkstyle.checks;  //No Violation
 *
 * public class AnnotationLocationCheck extends AbstractCheck {
 *   //...
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code mismatch.package.directory}
 * </li>
 * <li>
 * {@code missing.package.declaration}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class PackageDeclarationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MISSING = "missing.package.declaration";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MISMATCH = "mismatch.package.directory";

    /** Is package defined. */
    private boolean defined;

    /** Control whether to check for directory and package name match. */
    private boolean matchDirectoryStructure = true;

    /**
     * Setter to control whether to check for directory and package name match.
     *
     * @param matchDirectoryStructure the new value.
     */
    public void setMatchDirectoryStructure(boolean matchDirectoryStructure) {
        this.matchDirectoryStructure = matchDirectoryStructure;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void beginTree(DetailAST ast) {
        defined = false;
    }

    @Override
    public void finishTree(DetailAST ast) {
        if (!defined && ast != null) {
            log(ast, MSG_KEY_MISSING);
        }
    }

    @Override
    public void visitToken(DetailAST ast) {
        defined = true;

        if (matchDirectoryStructure) {
            final DetailAST packageNameAst = ast.getLastChild().getPreviousSibling();
            final FullIdent fullIdent = FullIdent.createFullIdent(packageNameAst);
            final String packageName = fullIdent.getText().replace('.', File.separatorChar);

            final String directoryName = getDirectoryName();

            if (!directoryName.endsWith(packageName)) {
                log(ast, MSG_KEY_MISMATCH, packageName);
            }
        }
    }

    /**
     * Returns the directory name this file is in.
     *
     * @return Directory name.
     */
    private String getDirectoryName() {
        final String fileName = getFilePath();
        final int lastSeparatorPos = fileName.lastIndexOf(File.separatorChar);
        return fileName.substring(0, lastSeparatorPos);
    }

}
