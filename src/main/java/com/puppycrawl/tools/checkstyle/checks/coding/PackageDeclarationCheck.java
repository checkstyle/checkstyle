////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.io.File;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Ensures there is a package declaration.
 * Optionally checks if directory structure matches package name.
 * Rationale: Classes that live in the null package cannot be
 * imported. Many novice developers are not aware of this.
 * Packages provide logical namespace to classes and should be stored in
 * the form of directory levels to provide physical grouping to your classes.
 * These directories are added to the classpath so that your classes
 * are visible to JVM when it runs the code.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author Oliver Burn
 * @author Vikramaditya Kukreja
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

    /** Line number used to log violation when no AST nodes are present in file. */
    private static final int DEFAULT_LINE_NUMBER = 1;

    /** Is package defined. */
    private boolean defined;

    /** Whether to check for directory and package name match. */
    private boolean matchDirectoryStructure = true;

    /**
     * Set whether to check for directory and package name match.
     * @param matchDirectoryStructure the new value.
     */
    public void setMatchDirectoryStructure(boolean matchDirectoryStructure) {
        this.matchDirectoryStructure = matchDirectoryStructure;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public void beginTree(DetailAST ast) {
        defined = false;
    }

    @Override
    public void finishTree(DetailAST ast) {
        if (!defined) {
            int lineNumber = DEFAULT_LINE_NUMBER;
            if (ast != null) {
                lineNumber = ast.getLineNo();
            }
            log(lineNumber, MSG_KEY_MISSING);
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
                log(fullIdent.getLineNo(), MSG_KEY_MISMATCH, packageName);
            }
        }
    }

    /**
     * Returns the directory name this file is in.
     * @return Directory name.
     */
    private String getDirectoryName() {
        final String fileName = getFileContents().getFileName();
        final int lastSeparatorPos = fileName.lastIndexOf(File.separatorChar);
        return fileName.substring(0, lastSeparatorPos);
    }
}
