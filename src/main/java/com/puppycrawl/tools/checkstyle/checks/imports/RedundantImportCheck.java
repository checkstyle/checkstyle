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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
public class RedundantImportCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_LANG = "import.lang";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_SAME = "import.same";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_DUPLICATE = "import.duplicate";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WILDCARD = "import.wildcard";

    /** The package that is implicitly imported in every java file. */
    private static final String JAVA_LANG_PACKAGE = "java.lang";

    /** The suffix of a wildcard import statement. */
    private static final String WILDCARD_SUFFIX = ".*";

    /** Set of the imports. */
    private final Set<FullIdent> imports = new HashSet<>();
    /** List of the import statements, to validate them against wildcard imports. */
    private final List<DetailAST> importNodes = new ArrayList<>();
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
        importNodes.clear();
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
            imports.stream().filter(full -> importText.equals(full.getText()))
                .forEach(full -> log(ast, MSG_DUPLICATE, full.getLineNo(), importText));

            imports.add(imp);
            importNodes.add(ast);
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
        final Map<String, String> wildcardImports = new HashMap<>();
        for (DetailAST importNode : importNodes) {
            final String importText = FullIdent.createFullIdentBelow(importNode).getText();
            if (importText.endsWith(WILDCARD_SUFFIX)) {
                wildcardImports.putIfAbsent(getQualifier(importText), importText);
            }
        }
        for (DetailAST importNode : importNodes) {
            final String importText = FullIdent.createFullIdentBelow(importNode).getText();
            if (!importText.endsWith(WILDCARD_SUFFIX)
                    && !isFromPackage(importText, JAVA_LANG_PACKAGE)
                    && (pkgName == null || !isFromPackage(importText, pkgName))) {
                final String wildcardImport = wildcardImports.get(getQualifier(importText));
                if (wildcardImport != null) {
                    log(importNode, MSG_WILDCARD, importText, wildcardImport);
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
        return pkg.equals(getQualifier(importName));
    }

    /**
     * Returns the qualifier of an import statement, that is, the import name
     * without its last segment. For a wildcard import it is the imported
     * package or type, for a single import it is the package or type the
     * imported member belongs to.
     *
     * @param importName the import name
     * @return the qualifier of the import statement
     */
    private static String getQualifier(String importName) {
        final int index = importName.lastIndexOf('.');
        return importName.substring(0, index);
    }

}
