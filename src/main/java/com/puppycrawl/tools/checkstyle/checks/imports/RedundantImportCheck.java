////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
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
    /** List of star imports. */
    private final List<FullIdent> starImports = new ArrayList<>();

    /** Name of package in file. */
    private String pkgName;

    @Override
    public void beginTree(DetailAST aRootAST) {
        pkgName = null;
        imports.clear();
        staticImports.clear();
        starImports.clear();
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
            if (isFromPackage(imp.getText(), "java.lang")) {
                log(ast, MSG_LANG, imp.getText());
            }
            // imports from unnamed package are not allowed,
            // so we are checking SAME rule only for named packages
            else if (pkgName != null && isFromPackage(imp.getText(), pkgName)) {
                log(ast, MSG_SAME, imp.getText());
            }
            // Check for a duplicate import
            imports.stream()
                .filter(full -> imp.getText().equals(full.getText()))
                .forEach(full -> log(ast, MSG_DUPLICATE, full.getLineNo(), imp.getText()));

            imports.add(imp);

            if (isStarImport(imp)) {
                starImports.add(imp);
            }
        }
        else {
            // Check for a duplicate static import
            final FullIdent imp =
                FullIdent.createFullIdent(
                    ast.getLastChild().getPreviousSibling());
            staticImports.stream().filter(full -> imp.getText().equals(full.getText()))
                .forEach(full -> log(ast,
                    MSG_DUPLICATE, full.getLineNo(), imp.getText()));

            staticImports.add(imp);
        }
    }

    @Override
    public void finishTree(DetailAST ast) {

        // check for types already imported by star imports
        // we should run this only if there is only 1 star import in file
        // 0 star imports -- there will be no violations
        // > 1 star imports -- false positives are possible
        if (starImports.size() == 1) {
            final FullIdent starImp = starImports.get(0);

            imports.stream()
                .filter(imp -> !isStarImport(imp))
                .filter(imp -> isDuplicatedByStarImport(starImp, imp))
                .forEach(imp -> {
                    log(
                        imp.getLineNo(),
                        imp.getColumnNo() - imp.getText().length(),
                        MSG_DUPLICATE,
                        starImp.getLineNo(),
                        imp.getText()
                    );
                });
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
        // https://docs.oracle.com/javase/specs/jls/se7/html/jls-7.html#jls-7.5
        // So '.' must be present in member name and we are not checking for it
        final int index = importName.lastIndexOf('.');
        final String front = importName.substring(0, index);
        return front.equals(pkg);
    }

    /**
     * Determines if an import statement is a star import.
     * @param imp the import
     * @return whether imp is a start import
     */
    private static boolean isStarImport(FullIdent imp) {
        return imp.getText().endsWith(".*");
    }

    /**
     * Determines if a package imported by {@code imp} can be imported using {@code starImp}.
     * @param starImp the star import
     * @param imp the import
     * @return whether package imported by {@code imp} can be imported using {@code starImp}
     */
    private static boolean isDuplicatedByStarImport(FullIdent starImp, FullIdent imp) {
        final String impText = imp.getText();
        final String starImpText = starImp.getText();

        final int impLastDotIndex = impText.lastIndexOf('.'); // there is at least one '.' in every import
        final int starImpLastDotIndex = starImpText.lastIndexOf('.'); // there is at least one '.' in every import

        // the import is duplicated by star import if their prefixes before last dot are equal.
        return impLastDotIndex == starImpLastDotIndex
            && impText.regionMatches(0, starImpText, 0, impLastDotIndex);

    }
}
