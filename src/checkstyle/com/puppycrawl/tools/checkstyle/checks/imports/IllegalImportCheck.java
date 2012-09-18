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

package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for imports from a set of illegal packages.
 * By default, the check rejects all <code>sun.*</code> packages
 * since programs that contain direct calls to the <code>sun.*</code> packages
 * are <a href="http://java.sun.com/products/jdk/faq/faq-sun-packages.html">
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
 * <code>java.io.*</code> and <code>java.sql.*</code> is
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
 * @version 1.0
 */
public class IllegalImportCheck
    extends Check
{
    /** list of illegal packages */
    private String[] mIllegalPkgs;

    /**
     * Creates a new <code>IllegalImportCheck</code> instance.
     */
    public IllegalImportCheck()
    {
        setIllegalPkgs(new String[] {"sun"});
    }

    /**
     * Set the list of illegal packages.
     * @param aFrom array of illegal packages
     */
    public void setIllegalPkgs(String[] aFrom)
    {
        mIllegalPkgs = aFrom.clone();
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final FullIdent imp;
        if (aAST.getType() == TokenTypes.IMPORT) {
            imp = FullIdent.createFullIdentBelow(aAST);
        }
        else {
            imp = FullIdent.createFullIdent(
                aAST.getFirstChild().getNextSibling());
        }
        if (isIllegalImport(imp.getText())) {
            log(aAST.getLineNo(),
                aAST.getColumnNo(),
                "import.illegal",
                imp.getText());
        }
    }

    /**
     * Checks if an import is from a package that must not be used.
     * @param aImportText the argument of the import keyword
     * @return if <code>aImportText</code> contains an illegal package prefix
     */
    private boolean isIllegalImport(String aImportText)
    {
        for (String element : mIllegalPkgs) {
            if (aImportText.startsWith(element + ".")) {
                return true;
            }
        }
        return false;
    }
}
