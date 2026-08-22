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

import javax.annotation.Nullable;

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
 * <li>
 * On-demand (wildcard) imports, such as {@code import java.util.*;}, are checked
 * against classes reachable on this check's own class loader. This reliably
 * detects collisions from JDK packages (for example a fully qualified reference
 * to a third-party {@code Observer} type being left alone because
 * {@code import java.util.*;} also brings a different {@code java.util.Observer}
 * into scope), but on-demand imports of third-party or project-local packages
 * cannot be resolved this way and may still be flagged incorrectly.
 * </li>
 * </ul>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class UnnecessaryFullyQualifiedTypeCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "unnecessary.fully.qualified.type";

    /** Array declarator markers that may trail a type name. */
    private static final String ARRAY_MARKERS = "[]";

    /** Marker used to represent a locally declared type in the identity set. */
    private static final String DECLARED_MARKER = "<declared> ";

    /** Marker used to represent an unqualified type reference in the identity set. */
    private static final String UNQUALIFIED_MARKER = "<unqualified> ";

    /** The {@code java.lang} package, types of which never need to be qualified. */
    private static final String JAVA_LANG_PACKAGE = "java.lang";

    /** Suffix identifying an on-demand (wildcard) import, such as {@code java.util.*}. */
    private static final String ON_DEMAND_IMPORT_SUFFIX = ".*";

    /** Separator used between components of a fully qualified name. */
    private static final String DOT = ".";

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

    /**
     * Tokens that declare a type. An {@link TokenTypes#IDENT} whose parent is one of
     * these is the name of a type declared in the file.
     */
    private static final int[] TYPE_DECLARATION_PARENTS = {
        TokenTypes.CLASS_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.ENUM_DEF,
        TokenTypes.ANNOTATION_DEF,
        TokenTypes.RECORD_DEF,
    };

    /** Maps the simple name of each single-type import to its fully qualified name. */
    private final Map<String, String> importedTypes = new HashMap<>();

    /** Simple names of the types declared in the file. */
    private final Set<String> declaredTypes = new HashSet<>();

    /** Simple names of the types referenced without qualification in the file. */
    private final Set<String> simpleReferences = new HashSet<>();

    /** Fully qualified type references collected while traversing the file. */
    private final List<QualifiedTypeReference> qualifiedReferences = new ArrayList<>();

    /**
     * Packages imported on-demand (wildcard), e.g. {@code java.util} for
     * {@code import java.util.*;}.
     */
    private final Set<String> onDemandImportPackages = new HashSet<>();

    /** The package the file belongs to, or {@code null} for the default package. */
    @Nullable
    private String packageName;

    /**
     * Creates new {@code UnnecessaryFullyQualifiedTypeCheck} instance.
     */
    public UnnecessaryFullyQualifiedTypeCheck() {
        // no code by default
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
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.DOT,
            TokenTypes.IDENT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        packageName = null;
        onDemandImportPackages.clear();
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
            default -> processIdent(ast);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (QualifiedTypeReference reference : qualifiedReferences) {
            if (isUnnecessary(reference.simpleName())) {
                log(reference.detailAst(), MSG_KEY, reference.fullName());
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
     * Records an import by its simple name so that later references can be resolved.
     * On-demand (wildcard) imports do not have a single simple name, so the
     * imported package is recorded separately instead.
     *
     * @param ast the {@link TokenTypes#IMPORT} node
     */
    private void processImport(DetailAST ast) {
        final String importText = FullIdent.createFullIdentBelow(ast).getText();
        if (importText.endsWith(ON_DEMAND_IMPORT_SUFFIX)) {
            onDemandImportPackages.add(
                importText.substring(0, importText.length() - ON_DEMAND_IMPORT_SUFFIX.length()));
        }
        else {
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
            final String fullName = buildFullName(ast).replace(ARRAY_MARKERS, "");
            if (isFullyQualified(fullName)) {
                qualifiedReferences.add(
                        new QualifiedTypeReference(ast, fullName, extractSimpleName(fullName)));
            }
        }
    }

    /**
     * Reconstructs the fully qualified name represented by a {@link TokenTypes#DOT}
     * node. An ordinary qualified name is a chain of {@code DOT} nodes each with two
     * children (a qualifier and a simple name), which {@link FullIdent} reconstructs
     * directly. A type-use annotation embedded in the qualified name (for example
     * {@code pkg.@Nullable Type}, permitted by JSR 308) instead produces a
     * {@code DOT} node with three children: the qualifier, an
     * {@link TokenTypes#ANNOTATIONS} node for the embedded annotation, and the
     * simple name. {@link FullIdent} only ever inspects the first two children of a
     * {@code DOT} node, so in that shape the simple name would otherwise be silently
     * dropped; this method detects that shape and reconstructs the name using the
     * qualifier and the actual third child instead.
     *
     * @param ast the {@link TokenTypes#DOT} node
     * @return the fully qualified name the node represents
     */
    private static String buildFullName(DetailAST ast) {
        final DetailAST qualifier = ast.getFirstChild();
        final DetailAST secondChild = qualifier.getNextSibling();
        final String result;
        if (secondChild.getType() == TokenTypes.ANNOTATIONS) {
            final DetailAST simpleNameIdent = secondChild.getNextSibling();
            result = FullIdent.createFullIdent(qualifier).getText()
                    + DOT + simpleNameIdent.getText();
        }
        else {
            result = FullIdent.createFullIdent(ast).getText();
        }
        return result;
    }

    /**
     * Records a reference made through a simple name. The name is either an
     * unqualified type reference or the name of a type declared in the file.
     *
     * @param ast the {@link TokenTypes#IDENT} node
     */
    private void processIdent(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        if (TokenUtil.isOfType(parent, TYPE_CONTEXT_PARENTS)) {
            simpleReferences.add(ast.getText());
        }
        else if (TokenUtil.isOfType(parent, TYPE_DECLARATION_PARENTS)) {
            declaredTypes.add(ast.getText());
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
        return collectIdentities(simpleName).size() == 1;
    }

    /**
     * Collects every distinct identity claiming the given simple name in the file:
     * a direct import, each fully qualified reference sharing that simple name, a
     * locally declared type, an unqualified reference resolving elsewhere, and a
     * type reachable via an on-demand import. A simple name is unambiguous, and
     * therefore its fully qualified reference unnecessary, exactly when this set
     * has a single member.
     *
     * @param simpleName the simple name to collect identities for
     * @return the set of distinct identities claiming this simple name
     */
    private Set<String> collectIdentities(String simpleName) {
        final Set<String> identities = new HashSet<>();
        final String imported = importedTypes.get(simpleName);
        if (imported != null) {
            identities.add(imported);
        }
        if (declaredTypes.contains(simpleName)) {
            identities.add(DECLARED_MARKER + simpleName);
        }
        addQualifiedReferenceIdentities(simpleName, identities);
        if (imported == null) {
            final boolean unqualifiedResolvedToReference =
                    resolvesToQualifiedReference(simpleName);
            addImplicitIdentities(simpleName, unqualifiedResolvedToReference, identities);
        }
        return identities;
    }

    /**
     * Adds an identity for each fully qualified reference sharing the given simple
     * name.
     *
     * @param simpleName the simple name to match references against
     * @param identities the identity set to add to
     */
    private void addQualifiedReferenceIdentities(String simpleName, Set<String> identities) {
        for (QualifiedTypeReference reference : qualifiedReferences) {
            if (reference.simpleName().equals(simpleName)) {
                identities.add(reference.fullName());
            }
        }
    }

    /**
     * Checks whether any fully qualified reference with the given simple name
     * resolves to the same type as an unqualified reference would.
     *
     * @param simpleName the simple name to check
     * @return {@code true} if an unqualified reference resolves to a matching
     *     qualified type
     */
    private boolean resolvesToQualifiedReference(String simpleName) {
        boolean resolved = false;
        for (QualifiedTypeReference reference : qualifiedReferences) {
            if (reference.simpleName().equals(simpleName)
                    && resolvesUnqualified(reference.fullName())) {
                resolved = true;
                break;
            }
        }
        return resolved;
    }

    /**
     * Adds identities that are not established by an explicit import: an
     * unqualified reference resolving to a same-package or {@code java.lang} type,
     * and a type reachable via an on-demand import. Only relevant when no direct
     * import of this simple name exists, since a direct import always takes
     * priority over an on-demand import.
     *
     * @param simpleName the simple name to check
     * @param unqualifiedResolvedToReference whether a same-simple-name qualified
     *     reference already resolves to what an unqualified use would mean
     * @param identities the identity set to add to
     */
    private void addImplicitIdentities(String simpleName,
                                       boolean unqualifiedResolvedToReference,
                                       Set<String> identities) {
        if (!unqualifiedResolvedToReference && simpleReferences.contains(simpleName)) {
            identities.add(UNQUALIFIED_MARKER + simpleName);
        }
        final String onDemandFullName = resolveViaOnDemandImport(simpleName);
        if (onDemandFullName != null) {
            identities.add(onDemandFullName);
        }
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
     * Resolves a simple name against the file's on-demand imports, returning the
     * fully qualified name of the class it resolves to, if any. Resolution is
     * attempted only against classes reachable on this check's own class loader,
     * so it is reliable for JDK packages but cannot detect collisions caused by
     * third-party or project-local on-demand imports.
     *
     * @param simpleName the simple name to look for
     * @return the fully qualified name the simple name resolves to via an
     *     on-demand import, or {@code null} if none of the on-demand imported
     *     packages contain a class of that name
     */
    @Nullable
    private String resolveViaOnDemandImport(String simpleName) {
        String resolved = null;
        for (String onDemandPackage : onDemandImportPackages) {
            try {
                final String candidate = onDemandPackage + DOT + simpleName;
                Class.forName(candidate, false, null);
                resolved = candidate;
                break;
            }
            catch (ClassNotFoundException | LinkageError ignored) {
                // this package does not contain a class of this name; keep checking
            }
        }
        return resolved;
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
     * @param name a qualified name containing at least one dot, as produced from
     *     a {@link TokenTypes#DOT} node
     * @return {@code true} if the name is a fully qualified type reference
     */
    private static boolean isFullyQualified(String name) {
        final int simpleNameStart = name.lastIndexOf('.');
        final int qualifierStart = name.lastIndexOf('.', simpleNameStart - 1);
        final char firstQualifierChar = name.charAt(qualifierStart + 1);
        return Character.isLowerCase(firstQualifierChar);
    }

    /**
     * Extracts the simple name from a qualified name.
     *
     * @param name a qualified name
     * @return the part of the name after the last dot
     */
    private static String extractSimpleName(String name) {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    /**
     * Holds the details of a fully qualified type reference.
     *
     * @param detailAst the node where the reference starts, used for logging
     * @param fullName the fully qualified name of the referenced type
     * @param simpleName the simple name of the referenced type
     */
    private record QualifiedTypeReference(DetailAST detailAst, String fullName, String simpleName) {
    }

}
