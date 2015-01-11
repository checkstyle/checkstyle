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
    private String pkgName;
    /** set of the imports */
    private final Set<FullIdent> imports = Sets.newHashSet();
    /** set of static imports */
    private final Set<FullIdent> staticImports = Sets.newHashSet();

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        pkgName = null;
        imports.clear();
        staticImports.clear();
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
    public void visitToken(DetailAST ast)
    {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            pkgName = FullIdent.createFullIdent(
                    ast.getLastChild().getPreviousSibling()).getText();
        }
        else if (ast.getType() == TokenTypes.IMPORT) {
            final FullIdent imp = FullIdent.createFullIdentBelow(ast);
            if (fromPackage(imp.getText(), "java.lang")) {
                log(ast.getLineNo(), ast.getColumnNo(), "import.lang",
                    imp.getText());
            }
            else if (fromPackage(imp.getText(), pkgName)) {
                log(ast.getLineNo(), ast.getColumnNo(), "import.same",
                    imp.getText());
            }
            // Check for a duplicate import
            for (FullIdent full : imports) {
                if (imp.getText().equals(full.getText())) {
                    log(ast.getLineNo(), ast.getColumnNo(),
                            "import.duplicate", full.getLineNo(),
                            imp.getText());
                }
            }

            imports.add(imp);
        }
        else {
            // Check for a duplicate static import
            final FullIdent imp =
                FullIdent.createFullIdent(
                    ast.getLastChild().getPreviousSibling());
            for (FullIdent full : staticImports) {
                if (imp.getText().equals(full.getText())) {
                    log(ast.getLineNo(), ast.getColumnNo(),
                        "import.duplicate", full.getLineNo(), imp.getText());
                }
            }

            staticImports.add(imp);
        }
    }

    /**
     * Determines if an import statement is for types from a specified package.
     * @param importName the import name
     * @param pkg the package name
     * @return whether from the package
     */
    private static boolean fromPackage(String importName, String pkg)
    {
        boolean retVal = false;
        if (pkg == null) {
            // If not package, then check for no package in the import.
            retVal = (importName.indexOf('.') == -1);
        }
        else {
            final int index = importName.lastIndexOf('.');
            if (index != -1) {
                final String front = importName.substring(0, index);
                retVal = front.equals(pkg);
            }
        }
        return retVal;
    }
}
