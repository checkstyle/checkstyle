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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Set;

/**
 * <p>
 * Checks for imports that are redundant. An import statement is
 * considered redundant if:
 * </p>
 *<ul>
 *  <li>It is a duplicate of another import. This is, when a class is imported
 *  more than once.</li>
 *  <li>The class non-statically imported is from the <code>java.lang</code>
 *  package. For example importing <code>java.lang.String</code>.</li>
 *  <li>The class non-statically imported is from the same package as the
 *  current package.</li>
 *</ul>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RedundantImport"/&gt;
 * </pre>
 *
 * Compatible with Java 1.5 source.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class RedundantImportCheck
    extends Check
{
    /** name of package in file */
    private String mPkgName;
    /** set of the imports */
    private final Set<FullIdent> mImports = Sets.newHashSet();
    /** set of static imports */
    private final Set<FullIdent> mStaticImports = Sets.newHashSet();

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mPkgName = null;
        mImports.clear();
        mStaticImports.clear();
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]
        {TokenTypes.IMPORT,
         TokenTypes.STATIC_IMPORT,
         TokenTypes.PACKAGE_DEF, };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            mPkgName = FullIdent.createFullIdent(
                    aAST.getLastChild().getPreviousSibling()).getText();
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            final FullIdent imp = FullIdent.createFullIdentBelow(aAST);
            if (fromPackage(imp.getText(), "java.lang")) {
                log(aAST.getLineNo(), aAST.getColumnNo(), "import.lang",
                    imp.getText());
            }
            else if (fromPackage(imp.getText(), mPkgName)) {
                log(aAST.getLineNo(), aAST.getColumnNo(), "import.same",
                    imp.getText());
            }
            // Check for a duplicate import
            for (FullIdent full : mImports) {
                if (imp.getText().equals(full.getText())) {
                    log(aAST.getLineNo(), aAST.getColumnNo(),
                            "import.duplicate", full.getLineNo(),
                            imp.getText());
                }
            }

            mImports.add(imp);
        }
        else {
            // Check for a duplicate static import
            final FullIdent imp =
                FullIdent.createFullIdent(
                    aAST.getLastChild().getPreviousSibling());
            for (FullIdent full : mStaticImports) {
                if (imp.getText().equals(full.getText())) {
                    log(aAST.getLineNo(), aAST.getColumnNo(),
                        "import.duplicate", full.getLineNo(), imp.getText());
                }
            }

            mStaticImports.add(imp);
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
