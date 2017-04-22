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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for imports from a set of illegal packages.
 * By default, the check rejects all {@code sun.*} packages
 * since programs that contain direct calls to the {@code sun.*} packages
 * are <a href="http://www.oracle.com/technetwork/java/faq-sun-packages-142232.html">
 * not 100% Pure Java</a>.
 * </p>
 * <p>
 * To reject other packages, set property illegalPkgs to a comma-separated
 * list of the illegal packages.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="IllegalImport"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it rejects packages
 * {@code java.io.*} and {@code java.sql.*} is
 * </p>
 * <pre>
 * &lt;module name="IllegalImport"&gt;
 *    &lt;property name="illegalPkgs" value="java.io, java.sql"/&gt;
 * &lt;/module&gt;
 *
 * Compatible with Java 1.5 source.
 *
 * </pre>
 * @author Oliver Burn
 * @author Lars Kühne
 */
public class IllegalImportCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.illegal";

    /** List of illegal packages. */
    private String[] illegalPkgs;

    /**
     * Creates a new {@code IllegalImportCheck} instance.
     */
    public IllegalImportCheck() {
        setIllegalPkgs("sun");
    }

    /**
     * Set the list of illegal packages.
     * @param from array of illegal packages
     */
    public final void setIllegalPkgs(String... from) {
        illegalPkgs = from.clone();
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final FullIdent imp;
        if (ast.getType() == TokenTypes.IMPORT) {
            imp = FullIdent.createFullIdentBelow(ast);
        }
        else {
            imp = FullIdent.createFullIdent(
                ast.getFirstChild().getNextSibling());
        }
        if (isIllegalImport(imp.getText())) {
            log(ast.getLineNo(),
                ast.getColumnNo(),
                MSG_KEY,
                imp.getText());
        }
    }

    /**
     * Checks if an import is from a package that must not be used.
     * @param importText the argument of the import keyword
     * @return if {@code importText} contains an illegal package prefix
     */
    private boolean isIllegalImport(String importText) {
        boolean isIllegal = false;
        for (String element : illegalPkgs) {
            if (importText.startsWith(element + ".")) {
                isIllegal = true;
                break;
            }
        }
        return isIllegal;
    }
}
