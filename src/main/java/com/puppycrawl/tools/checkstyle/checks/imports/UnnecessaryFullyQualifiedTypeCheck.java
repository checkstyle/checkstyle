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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for fully qualified type references that are unnecessary because the
 * type could simply be imported and referenced by its simple name.
 * </div>
 *
 * <p>
 * A fully qualified type reference, such as {@code java.util.Map}, is reported
 * as unnecessary unless another type with the same simple name is also used in
 * the file. For example, {@code java.util.Map} is allowed only when a different
 * {@code Map} (e.g. {@code com.example.Map}, a locally declared {@code Map}, or
 * a same-package or {@code java.lang} {@code Map}) is also referenced, since in
 * that case qualification is required to tell the two types apart.
 * </p>
 *
 * <p>
 * Using fully qualified names where they are not needed reduces readability and
 * is inconsistent with common Java style conventions, which favor importing the
 * type instead.
 * </p>
 *
 * <p>
 * The following limitations apply:
 * </p>
 * <ul>
 * <li>
 * To distinguish a package qualified type (such as {@code java.util.Map}) from a
 * reference to a nested type (such as {@code Map.Entry}), the check relies on the
 * common convention that package names start with a lowercase letter. A qualified
 * reference is treated as fully qualified only when the identifier preceding the
 * simple name starts with a lowercase letter.
 * </li>
 * <li>
 * References to nested types via an enclosing type (for example
 * {@code java.util.Map.Entry}) and fully qualified annotations
 * (for example {@code @java.lang.Override}) are not reported.
 * </li>
 * </ul>
 *
 * @since 13.5.0
 */
@FileStatefulCheck
public class UnnecessaryFullyQualifiedTypeCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "unnecessary.fully.qualified.type";

    /** Suffix for the star import. */
    private static final String STAR_IMPORT_SUFFIX = ".*";

    /** Array declarator markers that may trail a type name. */
    private static final String ARRAY_MARKERS = "[]";

    /** Marker used to represent a locally declared type in the identity set. */
    private static final String DECLARED_MARKER = "<declared> ";

    /** Marker used to represent an unqualified type reference in the identity set. */
    private static final String UNQUALIFIED_MARKER = "<unqualified> ";

    /** The {@code java.lang} package, types of which never need to be qualified. */
    private static final String JAVA_LANG_PACKAGE = "java.lang";

    /**
     * Tokens whose direct child is a type reference. A {@link TokenTypes#DOT} or
     * {@link TokenTypes#IDENT} is considered to be in type context when its parent
     * is one of these tokens.
     */
    private static final int[] TYPE_CONTEXT_PARENTS = {
        TokenTypes.TYPE,
        TokenTypes.LITERAL_NEW,
        TokenTypes.EXTENDS_CLAUSE,
        TokenTypes.IMPLEMENTS_CLAUSE,
        TokenTypes.LITERAL_THROWS,
        TokenTypes.TYPE_ARGUMENT,
    };

    /** Maps the simple name of each single-type import to its fully qualified name. */
    private final Map<String, String> importedTypes = new HashMap<>();

    /** Simple names of the types declared in the file. */
    private final Set<String> declaredTypes = new HashSet<>();

    /** Simple names of the types referenced without qualification in the file. */
    private final Set<String> simpleReferences = new HashSet<>();

    /** Fully qualified type references collected while traversing the file. */
    private final List<QualifiedTypeReference> qualifiedReferences = new ArrayList<>();

    /** The package the file belongs to, or {@code null} for the default package. */
    private String packageName;

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
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.DOT,
            TokenTypes.IDENT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        packageName = null;
        importedTypes.clear();
        declaredTypes.clear();
        simpleReferences.clear();
        qualifiedReferences.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF -> processPackage(ast);
            case TokenTypes.IMPORT -> processImport(ast);
            case TokenTypes.DOT -> processDot(ast);
            case TokenTypes.IDENT -> processIdent(ast);
            default -> processTypeDeclaration(ast);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (QualifiedTypeReference reference : qualifiedReferences) {
            if (isUnnecessary(reference.getSimpleName())) {
                log(reference.getDetailAst(), MSG_KEY, reference.getFullName());
            }
        }
    }

    /**
     * Records the package the file belongs to.
     *
     * @param ast the {@link TokenTypes#PACKAGE_DEF} node
     */
    private void processPackage(DetailAST ast) {
        packageName = FullIdent.createFullIdent(
                ast.getLastChild().getPreviousSibling()).getText();
    }

    /**
     * Records a single-type import so that later references can be resolved.
     *
     * @param ast the {@link TokenTypes#IMPORT} node
     */
    private void processImport(DetailAST ast) {
        final String importText = FullIdent.createFullIdentBelow(ast).getText();
        if (!importText.endsWith(STAR_IMPORT_SUFFIX)) {
            importedTypes.put(extractSimpleName(importText), importText);
        }
    }

    /**
     * Collects a fully qualified type reference if the given {@link TokenTypes#DOT}
     * node represents one.
     *
     * @param ast the {@link TokenTypes#DOT} node
     */
    private void processDot(DetailAST ast) {
        if (isInTypeContext(ast)) {
            final String fullName = FullIdent.createFullIdent(ast).getText();
            if (isFullyQualified(fullName)) {
                qualifiedReferences.add(
                    new QualifiedTypeReference(ast, fullName, extractSimpleName(fullName)));
            }
        }
    }

    /**
     * Records an unqualified type reference made through a simple name.
     *
     * @param ast the {@link TokenTypes#IDENT} node
     */
    private void processIdent(DetailAST ast) {
        if (isInTypeContext(ast)) {
            simpleReferences.add(ast.getText());
        }
    }

    /**
     * Records the simple name of a type declared in the file.
     *
     * @param ast a type declaration node
     */
    private void processTypeDeclaration(DetailAST ast) {
        final DetailAST identifier = ast.findFirstToken(TokenTypes.IDENT);
        if (identifier != null) {
            declaredTypes.add(identifier.getText());
        }
    }

    /**
     * Determines whether a type reference with the given simple name is unnecessary.
     * It is unnecessary when this fully qualified reference is the only type with
     * that simple name used in the file, so it could be imported and referenced by
     * its simple name without introducing ambiguity.
     *
     * @param simpleName the simple name of the type
     * @return {@code true} if the fully qualified reference is unnecessary
     */
    private boolean isUnnecessary(String simpleName) {
        final Set<String> identities = new HashSet<>();
        final String imported = importedTypes.get(simpleName);
        if (imported != null) {
            identities.add(imported);
        }
        boolean unqualifiedResolvedToReference = false;
        for (QualifiedTypeReference reference : qualifiedReferences) {
            if (reference.getSimpleName().equals(simpleName)) {
                identities.add(reference.getFullName());
                unqualifiedResolvedToReference = unqualifiedResolvedToReference
                        || resolvesUnqualified(reference.getFullName());
            }
        }
        final boolean declared = declaredTypes.contains(simpleName);
        if (declared) {
            identities.add(DECLARED_MARKER + simpleName);
        }
        // A type referenced by its simple name with no import and no local
        // declaration resolves to a same-package or java.lang type. That type is
        // distinct from this fully qualified reference, and thus requires it to be
        // qualified, unless this reference is itself that same-package or java.lang
        // type (in which case the qualification is simply redundant).
        if (imported == null && !declared
                && simpleReferences.contains(simpleName)
                && !unqualifiedResolvedToReference) {
            identities.add(UNQUALIFIED_MARKER + simpleName);
        }
        return identities.size() == 1;
    }

    /**
     * Determines whether a reference by simple name (without import or local
     * declaration) would resolve to the type denoted by the given fully qualified
     * name. This is the case when that name belongs to the {@code java.lang}
     * package or to the package the file itself belongs to.
     *
     * @param fullName a fully qualified type name
     * @return {@code true} if an unqualified reference would resolve to it
     */
    private boolean resolvesUnqualified(String fullName) {
        final String qualifier = fullName.substring(0, fullName.lastIndexOf('.'));
        return JAVA_LANG_PACKAGE.equals(qualifier) || qualifier.equals(packageName);
    }

    /**
     * Checks whether the given node is a direct child of a type context token.
     *
     * @param ast the node to check
     * @return {@code true} if the node is in type context
     */
    private static boolean isInTypeContext(DetailAST ast) {
        return TokenUtil.isOfType(ast.getParent(), TYPE_CONTEXT_PARENTS);
    }

    /**
     * Determines whether the given qualified name is a package qualified (fully
     * qualified) type reference, as opposed to a reference to a nested type. The
     * check relies on the convention that package names start with a lowercase
     * letter, so the name is treated as fully qualified when the identifier
     * preceding the simple name starts with a lowercase letter.
     *
     * @param name a qualified name
     * @return {@code true} if the name is a fully qualified type reference
     */
    private static boolean isFullyQualified(String name) {
        final String withoutArray = name.replace(ARRAY_MARKERS, "");
        final int simpleNameStart = withoutArray.lastIndexOf('.');
        boolean result = false;
        if (simpleNameStart != -1) {
            final int qualifierStart = withoutArray.lastIndexOf('.', simpleNameStart - 1);
            final char firstQualifierChar = withoutArray.charAt(qualifierStart + 1);
            result = Character.isLowerCase(firstQualifierChar);
        }
        return result;
    }

    /**
     * Extracts the simple name from a qualified name.
     *
     * @param name a qualified name
     * @return the part of the name after the last dot
     */
    private static String extractSimpleName(String name) {
        return name.substring(name.lastIndexOf('.') + 1).replace(ARRAY_MARKERS, "");
    }

    /**
     * Holds the details of a fully qualified type reference.
     */
    private static final class QualifiedTypeReference {

        /** The node where the reference starts, used for logging. */
        private final DetailAST detailAst;

        /** The fully qualified name of the referenced type. */
        private final String fullName;

        /** The simple name of the referenced type. */
        private final String simpleName;

        /**
         * Creates a new reference instance.
         *
         * @param detailAst the node where the reference starts
         * @param fullName the fully qualified name of the referenced type
         * @param simpleName the simple name of the referenced type
         */
        private QualifiedTypeReference(DetailAST detailAst, String fullName, String simpleName) {
            this.detailAst = detailAst;
            this.fullName = fullName;
            this.simpleName = simpleName;
        }

        /**
         * Gets the node where the reference starts.
         *
         * @return the node where the reference starts
         */
        private DetailAST getDetailAst() {
            return detailAst;
        }

        /**
         * Gets the fully qualified name of the referenced type.
         *
         * @return the fully qualified name
         */
        private String getFullName() {
            return fullName;
        }

        /**
         * Gets the simple name of the referenced type.
         *
         * @return the simple name
         */
        private String getSimpleName() {
            return simpleName;
        }

    }

}
