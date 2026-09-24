///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for redundant import statements. An import statement is
 * considered redundant if:
 * </div>
 * <ul>
 *   <li>It is a duplicate of another import. This is, when a class or a module
 *   is imported more than once.</li>
 *   <li>The class non-statically imported is from the {@code java.lang}
 *   package, e.g. importing {@code java.lang.String}.</li>
 *   <li>The class non-statically imported is from the same package as the
 *   current package.</li>
 *   <li>A non-static import is covered by a wildcard import from the same
 *   package or type.</li>
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

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_REDUNDANT_WILDCARD = "import.redundantWildcard";

    /** Name of the package implicitly imported by Java. */
    private static final String JAVA_LANG_PACKAGE = "java.lang";

    /** Map of the imports to their AST nodes. */
    private final Map<FullIdent, DetailAST> imports = new HashMap<>();
    /** Set of static and module imports. */
    private final Set<FullIdent> staticAndModuleImports = new HashSet<>();

    /** Name of package in file. */
    private String pkgName;

    /**
     * Creates a new {@code RedundantImportCheck} instance.
     */
    public RedundantImportCheck() {
        // no code by default
    }

    @Override
    public void beginTree(DetailAST aRootAST) {
        pkgName = null;
        imports.clear();
        staticAndModuleImports.clear();
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
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.MODULE_IMPORT,
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
            if (isFromPackage(importText, JAVA_LANG_PACKAGE)) {
                log(ast, MSG_LANG, importText);
            }
            // imports from unnamed package are not allowed,
            // so we are checking SAME rule only for named packages
            else if (pkgName != null && isFromPackage(importText, pkgName)) {
                log(ast, MSG_SAME, importText);
            }
            // Check for a duplicate import
            imports.keySet().stream().filter(full -> importText.equals(full.getText()))
                .forEach(full -> log(ast, MSG_DUPLICATE, full.getLineNo(), importText));

            imports.put(imp, ast);
        }
        else {
            // Check for a duplicate static or module import
            final DetailAST identNode = ast.getLastChild().getPreviousSibling();
            final FullIdent importFullIdent = FullIdent.createFullIdent(identNode);
            final String importText = importFullIdent.getText();

            staticAndModuleImports
                    .stream()
                    .filter(existingImport -> importText.equals(existingImport.getText()))
                    .forEach(existingImport -> {
                        log(ast, MSG_DUPLICATE, existingImport.getLineNo(), importText);
                    });

            staticAndModuleImports.add(importFullIdent);
        }
    }

    @Override
    public void finishTree(DetailAST rootAst) {
        final Map<String, FullIdent> wildcardImports = new HashMap<>();
        for (FullIdent importStatement : imports.keySet()) {
            final String importText = importStatement.getText();
            if (isWildcardImport(importText)) {
                wildcardImports.putIfAbsent(getImportQualifier(importText), importStatement);
            }
        }

        for (Map.Entry<FullIdent, DetailAST> importEntry : imports.entrySet()) {
            final FullIdent importStatement = importEntry.getKey();
            final String importText = importStatement.getText();
            if (!isWildcardImport(importText)
                    && !isFromPackage(importText, JAVA_LANG_PACKAGE)
                    && (pkgName == null || !isFromPackage(importText, pkgName))) {
                final FullIdent wildcardImport =
                    wildcardImports.get(getImportQualifier(importText));
                if (wildcardImport != null) {
                    log(importEntry.getValue(), MSG_REDUNDANT_WILDCARD,
                        importText, wildcardImport.getText());
                }
            }
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
        return pkg.equals(getImportQualifier(importName));
    }

    /**
     * Determines whether an import uses the wildcard form.
     *
     * @param importName the import name
     * @return whether the import uses the wildcard form
     */
    private static boolean isWildcardImport(String importName) {
        return importName.endsWith(".*");
    }

    /**
     * Returns the qualifier of an import name.
     *
     * @param importName the import name
     * @return the import qualifier
     */
    private static String getImportQualifier(String importName) {
        final int index = importName.lastIndexOf('.');
        return importName.substring(0, index);
    }

}
