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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for redundant import statements. An import statement is
 * considered redundant if:
 * </p>
 * <ul>
 *   <li>It is a duplicate of another import. This is, when a class is imported
 *   more than once.</li>
 *   <li>The class non-statically imported is from the {@code java.lang}
 *   package, e.g. importing {@code java.lang.String}.</li>
 *   <li>The class non-statically imported is from the same package as the
 *   current package.</li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="RedundantImport"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * package Test;
 * import static Test.MyClass.*; // OK, static import
 * import static java.lang.Integer.MAX_VALUE; // OK, static import
 *
 * import Test.MyClass; // violation, imported from the same package as the current package
 * import java.lang.String; // violation, the class imported is from the 'java.lang' package
 * import java.util.Scanner; // OK
 * import java.util.Scanner; // violation, it is a duplicate of another import
 * public class MyClass{ };
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code import.duplicate}
 * </li>
 * <li>
 * {@code import.lang}
 * </li>
 * <li>
 * {@code import.same}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
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
    private final Set<FullIdent> imports = new HashSet<>();
    /** Set of static imports. */
    private final Set<FullIdent> staticImports = new HashSet<>();

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
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT, TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            pkgName = FullIdent.createFullIdent(
                    ast.getLastChild().getPreviousSibling()).getText();
        }
        else if (ast.getType() == TokenTypes.IMPORT) {
            final FullIdent imp = FullIdent.createFullIdentBelow(ast);
            final String importText = imp.getText();
            if (isFromPackage(importText, "java.lang")) {
                log(ast, MSG_LANG, importText);
            }
            // imports from unnamed package are not allowed,
            // so we are checking SAME rule only for named packages
            else if (pkgName != null && isFromPackage(importText, pkgName)) {
                log(ast, MSG_SAME, importText);
            }
            // Check for a duplicate import
            imports.stream().filter(full -> importText.equals(full.getText()))
                .forEach(full -> log(ast, MSG_DUPLICATE, full.getLineNo(), importText));

            imports.add(imp);
        }
        else {
            // Check for a duplicate static import
            final FullIdent imp =
                FullIdent.createFullIdent(
                    ast.getLastChild().getPreviousSibling());
            staticImports.stream().filter(full -> imp.getText().equals(full.getText()))
                .forEach(full -> log(ast, MSG_DUPLICATE, full.getLineNo(), imp.getText()));

            staticImports.add(imp);
        }
    }

    /**
     * Determines if an import statement is for types from a specified package.
     *
     * @param importName the import name
     * @param pkg the package name
     * @return whether from the package
     */
    private static boolean isFromPackage(String importName, String pkg) {
        // imports from unnamed package are not allowed:
        // https://docs.oracle.com/javase/specs/jls/se7/html/jls-7.html#jls-7.5
        // So '.' must be present in member name and we are not checking for it
        final int index = importName.lastIndexOf('.');
        final String front = importName.substring(0, index);
        return pkg.equals(front);
    }

}
