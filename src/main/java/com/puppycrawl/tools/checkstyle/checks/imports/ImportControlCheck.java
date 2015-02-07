////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import java.io.File;
import java.net.URI;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.apache.commons.beanutils.ConversionException;

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
public class ImportControlCheck extends Check
{
    /** The root package controller. */
    private PkgControl root;
    /** The package doing the import. */
    private String inPkg;

    /**
     * The package controller for the current file. Used for performance
     * optimisation.
     */
    private PkgControl currentLeaf;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT,
                          TokenTypes.STATIC_IMPORT, };
    }

    @Override
    public void beginTree(final DetailAST rootAST)
    {
        currentLeaf = null;
    }

    @Override
    public void visitToken(final DetailAST ast)
    {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
            final FullIdent full = FullIdent.createFullIdent(nameAST);
            if (root == null) {
                log(nameAST, "import.control.missing.file");
            }
            else {
                inPkg = full.getText();
                currentLeaf = root.locateFinest(inPkg);
                if (currentLeaf == null) {
                    log(nameAST, "import.control.unknown.pkg");
                }
            }
        }
        else if (currentLeaf != null) {
            final FullIdent imp;
            if (ast.getType() == TokenTypes.IMPORT) {
                imp = FullIdent.createFullIdentBelow(ast);
            }
            else {
                // know it is a static import
                imp = FullIdent.createFullIdent(ast
                        .getFirstChild().getNextSibling());
            }
            final AccessResult access = currentLeaf.checkAccess(imp.getText(),
                    inPkg);
            if (!AccessResult.ALLOWED.equals(access)) {
                log(ast, "import.control.disallowed", imp.getText());
            }
        }
    }

    /**
     * Set the parameter for the url containing the import control
     * configuration. It will cause the url to be loaded.
     * @param url the url of the file to load.
     * @throws ConversionException on error loading the file.
     */
    public void setUrl(final String url)
    {
        // Handle empty param
        if ((url == null) || (url.trim().length() == 0)) {
            return;
        }
        final URI uri;
        try {
            uri = URI.create(url);
        }
        catch (final IllegalArgumentException ex) {
            throw new ConversionException("syntax error in url " + url, ex);
        }
        try {
            root = ImportControlLoader.load(uri);
        }
        catch (final CheckstyleException ex) {
            throw new ConversionException("Unable to load " + url, ex);
        }
    }

    /**
     * Set the pnameter for the file containing the import control
     * configuration. It will cause the file to be loaded.
     * @param name the name of the file to load.
     * @throws ConversionException on error loading the file.
     */
    public void setFile(final String name)
    {
        // Handle empty param
        if ((name == null) || (name.trim().length() == 0)) {
            return;
        }

        try {
            root = ImportControlLoader.load(new File(name).toURI());
        }
        catch (final CheckstyleException ex) {
            throw new ConversionException("Unable to load " + name, ex);
        }
    }
}
