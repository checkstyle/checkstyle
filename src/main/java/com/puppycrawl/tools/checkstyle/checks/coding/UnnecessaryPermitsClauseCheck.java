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

package com.puppycrawl.tools.checkstyle.checks.coding;

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
 * Checks that a {@code permits} clause of a sealed class or interface is not
 * unnecessary, i.e. that it is not possible to omit the clause and have the
 * compiler infer the exact same set of permitted subtypes.
 * </div>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-13.html#jls-13.4.2">
 * Java Language Specification</a> for more information about sealed classes.
 * </p>
 *
 * <p>
 * This Check does not perform full type resolution. A permitted type is
 * treated as local to the file only when the name in the {@code permits}
 * clause can actually refer to a type declared in the same compilation unit:
 * an unqualified name must denote a type declared in the compilation unit, a
 * member type of the sealed type, or a member type of one of its enclosing
 * types, and a qualified name must match the nesting path of a type declared
 * in the compilation unit. An unrelated type that merely shares a simple name
 * with the permitted type is therefore not mistaken for it.
 * </p>
 *
 * @since 14.2.0
 */
@FileStatefulCheck
public class UnnecessaryPermitsClauseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "unnecessary.permits.clause";

    /**
     * A list of every type declaration (class, interface, enum, or record)
     * found in the current compilation unit.
     */
    private final List<DetailAST> typeDefs = new ArrayList<>();

    /**
     * A list of {@link TokenTypes#PERMITS_CLAUSE} nodes found in the current compilation unit.
     */
    private final List<DetailAST> permitsClauseAstList = new ArrayList<>();

    /**
     * A set of simple names of the types declared directly in the compilation
     * unit (top-level types).
     */
    private final Set<String> compilationUnitTypeNames = new HashSet<>();

    /**
     * The direct member type names of each type declaration in the current
     * compilation unit, keyed by the declaration node.
     */
    private final Map<DetailAST, Set<String>> memberTypeNames = new HashMap<>();

    /**
     * A set of nesting paths (for example {@code Outer.Inner}) of every type
     * declared in the current compilation unit, each also present in its
     * package-qualified form when the file declares a package.
     */
    private final Set<String> localTypePaths = new HashSet<>();

    /**
     * Creates a new {@code UnnecessaryPermitsClauseCheck} instance.
     */
    public UnnecessaryPermitsClauseCheck() {
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
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.PERMITS_CLAUSE,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        typeDefs.clear();
        permitsClauseAstList.clear();
        compilationUnitTypeNames.clear();
        memberTypeNames.clear();
        localTypePaths.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF,
                    TokenTypes.INTERFACE_DEF,
                    TokenTypes.ENUM_DEF,
                    TokenTypes.RECORD_DEF -> typeDefs.add(ast);
            default -> permitsClauseAstList.add(ast);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        indexTypeDeclarations(rootAST);
        for (DetailAST permitsClause : permitsClauseAstList) {
            if (isUnnecessary(permitsClause)) {
                log(permitsClause, MSG_KEY);
            }
        }
    }

    /**
     * Builds the lookup structures used to decide whether a permitted type is
     * declared in the current compilation unit, from every type declaration
     * collected during the traversal.
     *
     * @param rootAST the root of the parsed file.
     */
    private void indexTypeDeclarations(DetailAST rootAST) {
        String packageName = null;
        final DetailAST packageDef = rootAST.findFirstToken(TokenTypes.PACKAGE_DEF);
        if (packageDef != null) {
            packageName = getPackageName(packageDef);
        }
        for (DetailAST typeDef : typeDefs) {
            memberTypeNames.put(typeDef, collectMemberTypeNames(typeDef));
            if (TokenUtil.isOfType(typeDef.getParent(), TokenTypes.COMPILATION_UNIT,
                    TokenTypes.COMPACT_COMPILATION_UNIT)) {
                compilationUnitTypeNames.add(getTypeName(typeDef));
            }
            if (isDeclaredAtTypeLevel(typeDef)) {
                final String path = getTypePath(typeDef);
                localTypePaths.add(path);
                if (packageName != null) {
                    localTypePaths.add(packageName + "." + path);
                }
            }
        }
    }

    /**
     * Extracts the package name from a {@code PACKAGE_DEF} node, whose first
     * child is the annotations node rather than the package name itself.
     *
     * @param packageDef the package declaration node.
     * @return the full package name.
     */
    private static String getPackageName(DetailAST packageDef) {
        // the children of PACKAGE_DEF are the annotations node, then the
        // package name itself (a DOT node, or an IDENT for a single segment)
        final DetailAST nameNode = packageDef.getFirstChild().getNextSibling();
        return FullIdent.createFullIdent(nameNode).getText();
    }

    /**
     * Collects the simple names of the types declared directly inside the
     * given type declaration.
     *
     * @param typeDef the type declaration to inspect.
     * @return the simple names of its direct member types.
     */
    private static Set<String> collectMemberTypeNames(DetailAST typeDef) {
        final Set<String> names = new HashSet<>();
        final DetailAST objBlock = typeDef.findFirstToken(TokenTypes.OBJBLOCK);
        DetailAST child = objBlock.getFirstChild();
        while (child != null) {
            if (isTypeDeclaration(child)) {
                names.add(getTypeName(child));
            }
            child = child.getNextSibling();
        }
        return names;
    }

    /**
     * Checks whether the given node is a type declaration (class, interface,
     * enum, or record).
     *
     * @param ast the node to check.
     * @return true if the node is a type declaration.
     */
    private static boolean isTypeDeclaration(DetailAST ast) {
        return TokenUtil.isOfType(ast, TokenTypes.CLASS_DEF,
                TokenTypes.INTERFACE_DEF, TokenTypes.ENUM_DEF,
                TokenTypes.RECORD_DEF);
    }

    /**
     * Extracts the simple name of a type declaration.
     *
     * @param typeDef the type declaration node.
     * @return the simple name of the declared type.
     */
    private static String getTypeName(DetailAST typeDef) {
        return typeDef.findFirstToken(TokenTypes.IDENT).getText();
    }

    /**
     * Finds the declaration that directly encloses the given type declaration
     * through its object block, if any.
     *
     * @param typeDef the type declaration to inspect.
     * @return the enclosing declaration, or null if there is none.
     */
    private static DetailAST getEnclosingDeclaration(DetailAST typeDef) {
        DetailAST enclosing = null;
        final DetailAST parent = typeDef.getParent();
        if (parent.getType() == TokenTypes.OBJBLOCK) {
            enclosing = parent.getParent();
        }
        return enclosing;
    }

    /**
     * Checks whether the given type declaration is a top-level type or a member
     * of another type, as opposed to a local type declared inside a method or
     * initializer body. Only top-level and member types can be named by a
     * {@code permits} clause, so only their nesting paths are indexed.
     *
     * @param typeDef the type declaration to inspect.
     * @return true if the enclosing chain of the declaration reaches the
     *         compilation unit through object blocks only.
     */
    private static boolean isDeclaredAtTypeLevel(DetailAST typeDef) {
        DetailAST parent = typeDef.getParent();
        while (parent.getType() == TokenTypes.OBJBLOCK) {
            parent = parent.getParent().getParent();
        }
        return TokenUtil.isOfType(parent, TokenTypes.COMPILATION_UNIT,
                TokenTypes.COMPACT_COMPILATION_UNIT);
    }

    /**
     * Builds the nesting path of a type declaration, for example
     * {@code Outer.Inner} for a type nested in another type.
     *
     * @param typeDef the type declaration to inspect.
     * @return the nesting path of the declared type.
     */
    private static String getTypePath(DetailAST typeDef) {
        String path = getTypeName(typeDef);
        DetailAST enclosing = getEnclosingDeclaration(typeDef);
        while (enclosing != null) {
            path = getTypeName(enclosing) + "." + path;
            enclosing = getEnclosingDeclaration(enclosing);
        }
        return path;
    }

    /**
     * Collects every simple name that an unqualified type name in the
     * {@code permits} clause of the given sealed type can refer to: the types
     * declared in the compilation unit, the member types of the sealed type,
     * and the member types of each of its enclosing types.
     *
     * @param sealedType the type declaration that owns the permits clause.
     * @return the simple names visible to the permits clause.
     */
    private Set<String> getResolvableNames(DetailAST sealedType) {
        final Set<String> names = new HashSet<>(compilationUnitTypeNames);
        DetailAST current = sealedType;
        while (current != null) {
            names.addAll(memberTypeNames.getOrDefault(current, Set.of()));
            current = getEnclosingDeclaration(current);
        }
        return names;
    }

    /**
     * Determines whether every type named in the given {@code permits} clause
     * is declared somewhere within the same compilation unit, and is therefore redundant.
     *
     * @param permitsClause the {@link TokenTypes#PERMITS_CLAUSE} node to inspect
     * @return {@code true} if the clause is unnecessary
     */
    private boolean isUnnecessary(DetailAST permitsClause) {
        boolean result = true;
        final DetailAST sealedType = permitsClause.getParent();
        DetailAST permittedType = permitsClause.getFirstChild();
        while (permittedType != null) {
            if (isTypeName(permittedType) && !isLocalType(permittedType, sealedType)) {
                result = false;
                break;
            }
            permittedType = permittedType.getNextSibling();
        }
        return result;
    }

    /**
     * Determines whether one entry of a {@code permits} clause names the type
     * declared in the same compilation unit. A qualified name must match the
     * nesting path of a declared type; an unqualified name must be visible
     * from the sealed type, as described by {@link #getResolvableNames}.
     *
     * @param permittedType one entry of the permits clause.
     * @param sealedType    the type declaration that owns the permits clause.
     * @return {@code true} if the entry names a type declared in this file.
     */
    private boolean isLocalType(DetailAST permittedType, DetailAST sealedType) {
        final boolean result;
        if (permittedType.getType() == TokenTypes.DOT) {
            final String fullName = FullIdent.createFullIdent(permittedType).getText();
            result = localTypePaths.contains(fullName);
        }
        else {
            result = getResolvableNames(sealedType).contains(permittedType.getText());
        }
        return result;
    }

    /**
     * Checks whether the given direct child of a {@code permits} clause
     * represents a permitted type name, as opposed to a separating comma.
     *
     * @param ast the node to check
     * @return {@code true} if the node is a (possibly qualified) type name
     */
    private static boolean isTypeName(DetailAST ast) {
        return TokenUtil.isOfType(ast, TokenTypes.IDENT, TokenTypes.DOT);
    }

}
