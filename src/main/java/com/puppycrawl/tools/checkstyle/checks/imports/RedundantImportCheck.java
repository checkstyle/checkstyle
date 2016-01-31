////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for imports that are redundant. An import statement is
 * considered redundant if:
 * </p>
 *<ul>
 *  <li>It is a duplicate of another import. This is, when a class is imported
 *  more than once.</li>
 *  <li>The class non-statically imported is from the {@code java.lang}
 *  package. For example importing {@code java.lang.String}.</li>
 *  <li>The class non-statically imported is from the same package as the
 *  current package.</li>
 *</ul>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RedundantImport"/&gt;
 * </pre>
 * Compatible with Java 1.5 source.
 *
 * @author Oliver Burn
 */
public class RedundantImportCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LANG = "import.lang";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_SAME = "import.same";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DUPLICATE = "import.duplicate";

    /** Set of the imports. */
    private final Set<FullIdent> imports = Sets.newHashSet();
    /** Set of static imports. */
    private final Set<FullIdent> staticImports = Sets.newHashSet();

    /** Name of package in file. */
    private String pkgName;

    @Override
    public void beginTree(DetailAST aRootAST) {
        pkgName = null;
        imports.clear();
        staticImports.clear();
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]
        {TokenTypes.IMPORT,
         TokenTypes.STATIC_IMPORT,
         TokenTypes.PACKAGE_DEF, };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            pkgName = FullIdent.createFullIdent(
                    ast.getLastChild().getPreviousSibling()).getText();
        }
        else if (ast.getType() == TokenTypes.IMPORT) {
            final FullIdent imp = FullIdent.createFullIdentBelow(ast);
            if (isFromPackage(imp.getText(), "java.lang")) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_LANG,
                    imp.getText());
            }
            // imports from unnamed package are not allowed,
            // so we are checking SAME rule only for named packages
            else if (pkgName != null && isFromPackage(imp.getText(), pkgName)) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_SAME,
                    imp.getText());
            }
            // Check for a duplicate import
            for (FullIdent full : imports) {
                if (imp.getText().equals(full.getText())) {
                    log(ast.getLineNo(), ast.getColumnNo(),
                            MSG_DUPLICATE, full.getLineNo(),
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
                        MSG_DUPLICATE, full.getLineNo(), imp.getText());
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
    private static boolean isFromPackage(String importName, String pkg) {
        // imports from unnamed package are not allowed:
        // http://docs.oracle.com/javase/specs/jls/se7/html/jls-7.html#jls-7.5
        // So '.' must be present in member name and we are not checking for it
        final int index = importName.lastIndexOf('.');
        final String front = importName.substring(0, index);
        return front.equals(pkg);
    }
}
