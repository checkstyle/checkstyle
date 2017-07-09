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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check that controls what packages can be imported in each package. Useful
 * for ensuring that application layering is not violated. Ideas on how the
 * check can be improved include support for:
 * <ul>
 * <li>
 * Change the default policy that if a package being checked does not
 * match any guards, then it is allowed. Currently defaults to disallowed.
 * </li>
 * </ul>
 *
 * @author Oliver Burn
 */
public class ImportControlCheck extends AbstractCheck implements ExternalResourceHolder {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_FILE = "import.control.missing.file";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNKNOWN_PKG = "import.control.unknown.pkg";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DISALLOWED = "import.control.disallowed";

    /**
     * A part of message for exception.
     */
    private static final String UNABLE_TO_LOAD = "Unable to load ";

    /** Location of import control file. */
    private String fileLocation;

    /** The filepath pattern this check applies to. */
    private Pattern path = Pattern.compile(".*");
    /** Whether to process the current file. */
    private boolean processCurrentFile;

    /** The root package controller. */
    private ImportControl root;
    /** The package doing the import. */
    private String packageName;

    /**
     * The package controller for the current file. Used for performance
     * optimisation.
     */
    private ImportControl currentImportControl;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT,
                          TokenTypes.STATIC_IMPORT, };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentImportControl = null;
        processCurrentFile = path.matcher(getFileContents().getFileName()).find();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (processCurrentFile) {
            if (ast.getType() == TokenTypes.PACKAGE_DEF) {
                if (root == null) {
                    log(ast, MSG_MISSING_FILE);
                }
                else {
                    packageName = getPackageText(ast);
                    currentImportControl = root.locateFinest(packageName);
                    if (currentImportControl == null) {
                        log(ast, MSG_UNKNOWN_PKG);
                    }
                }
            }
            else if (currentImportControl != null) {
                final String importText = getImportText(ast);
                final AccessResult access =
                        currentImportControl.checkAccess(packageName, importText);
                if (access != AccessResult.ALLOWED) {
                    log(ast, MSG_DISALLOWED, importText);
                }
            }
        }
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return Collections.singleton(fileLocation);
    }

    /**
     * Returns package text.
     * @param ast PACKAGE_DEF ast node
     * @return String that represents full package name
     */
    private static String getPackageText(DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
        return FullIdent.createFullIdent(nameAST).getText();
    }

    /**
     * Returns import text.
     * @param ast ast node that represents import
     * @return String that represents importing class
     */
    private static String getImportText(DetailAST ast) {
        final FullIdent imp;
        if (ast.getType() == TokenTypes.IMPORT) {
            imp = FullIdent.createFullIdentBelow(ast);
        }
        else {
            // know it is a static import
            imp = FullIdent.createFullIdent(ast
                    .getFirstChild().getNextSibling());
        }
        return imp.getText();
    }

    /**
     * Set the name for the file containing the import control
     * configuration. It can also be a URL or resource in the classpath.
     * It will cause the file to be loaded.
     * @param uri the uri of the file to load.
     * @throws IllegalArgumentException on error loading the file.
     */
    public void setFile(URI uri) {
        // Handle empty param
        if (uri != null) {
            try {
                root = ImportControlLoader.load(uri);
                fileLocation = uri.toString();
            }
            catch (CheckstyleException ex) {
                throw new IllegalArgumentException(UNABLE_TO_LOAD + uri, ex);
            }
        }
    }

    /**
     * Set the file path pattern that this check applies to.
     * @param pattern the file path regex this check should apply to.
     */
    public void setPath(Pattern pattern) {
        path = pattern;
    }
}
