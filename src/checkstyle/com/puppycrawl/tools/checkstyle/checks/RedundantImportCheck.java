////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>
 * Checks for imports that are redundant. An import statement is
 * considered redundant if:
 * </p>
 *<ul>
 *  <li>It is a duplicate of another import. This is, when a class is imported
 *  more than once.</li>
 *  <li>The class imported is from the <code>java.lang</code> package.
 *  For example importing <code>java.lang.String</code>.</li>
 *  <li>The class imported is from the same package.</li>
 *</ul>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RedundantImport"/&gt;
 * </pre>
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class RedundantImportCheck
    extends AbstractImportCheck
{
    /** name of package in file */
    private String mPkgName;
    /** set of the imports */
    private final Set mImports = new HashSet();

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        mPkgName = null;
        mImports.clear();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.IMPORT, TokenTypes.PACKAGE_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            final DetailAST nameAST = (DetailAST) aAST.getFirstChild();
            mPkgName = FullIdent.createFullIdent(nameAST).getText();
        }
        else {
            final FullIdent imp = getImportText(aAST);
            if (fromPackage(imp.getText(), "java.lang")) {
                log(aAST.getLineNo(), aAST.getColumnNo(), "import.lang",
                    imp.getText());
            }
            else if (fromPackage(imp.getText(), mPkgName)) {
                log(aAST.getLineNo(), aAST.getColumnNo(), "import.same",
                    imp.getText());
            }
            // Check for a duplicate import
            final Iterator it = mImports.iterator();
            while (it.hasNext()) {
                final FullIdent full = (FullIdent) it.next();
                if (imp.getText().equals(full.getText())) {
                    log(aAST.getLineNo(),
                        aAST.getColumnNo(),
                        "import.duplicate",
                        new Integer(full.getLineNo()),
                        imp.getText());
                }
            }

            mImports.add(imp);
        }
    }

    /**
     * Determines if an import statement is for types from a specified package.
     * @param aImport the import name
     * @param aPkg the package name
     * @return whether from the package
     */
    private static boolean fromPackage(String aImport, String aPkg)
    {
        boolean retVal = false;
        if (aPkg == null) {
            // If not package, then check for no package in the import.
            retVal = (aImport.indexOf('.') == -1);
        }
        else {
            final int index = aImport.lastIndexOf('.');
            if (index != -1) {
                final String front = aImport.substring(0, index);
                retVal = front.equals(aPkg);
            }
        }
        return retVal;
    }
}
