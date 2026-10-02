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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
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
 * This Check does not perform full type resolution. It determines whether a
 * permitted type is local to the file by comparing simple names against every
 * type declaration (class, interface, enum, or record) found
 * anywhere in the compilation unit, including nested and sibling top-level
 * types. As a result, a permitted type whose simple name coincidentally
 * matches an unrelated local type declaration could, in theory, be
 * misidentified as local. In practice this situation cannot occur in code
 * that compiles, since the Java compiler would not be able to resolve such an
 * ambiguous reference in the {@code permits} clause.
 * </p>
 *
 * @since 14.2.0
 */
@FileStatefulCheck
public class UnnecessaryPermitsClauseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "unnecessary.permits.clause";

    /**
     * A set of simple names of every type declared in the current compilation
     * unit, including nested types.
     */
    private final Set<String> localTypeNames = new HashSet<>();

    /**
     * A list of {@link TokenTypes#PERMITS_CLAUSE} nodes found in the current
     * compilation unit.
     */
    private final List<DetailAST> permitsClauseAstList = new ArrayList<>();

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
        localTypeNames.clear();
        permitsClauseAstList.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CLASS_DEF,
                    TokenTypes.INTERFACE_DEF,
                    TokenTypes.ENUM_DEF,
                    TokenTypes.RECORD_DEF -> {
                final DetailAST nameAst = ast.findFirstToken(TokenTypes.IDENT);
                localTypeNames.add(nameAst.getText());
            }
            default -> permitsClauseAstList.add(ast);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (DetailAST permitsClause : permitsClauseAstList) {
            if (isUnnecessary(permitsClause)) {
                log(permitsClause, MSG_KEY);
            }
        }
    }

    /**
     * Determines whether every type named in the given {@code permits} clause
     * is declared somewhere within the same compilation unit, and is
     * therefore redundant.
     *
     * @param permitsClause the {@link TokenTypes#PERMITS_CLAUSE} node to inspect
     * @return {@code true} if the clause is unnecessary
     */
    private boolean isUnnecessary(DetailAST permitsClause) {
        boolean result = true;
        DetailAST permittedType = permitsClause.getFirstChild();
        while (permittedType != null) {
            if (isTypeName(permittedType)) {
                final String simpleName = getSimpleName(permittedType);
                if (!localTypeNames.contains(simpleName)) {
                    result = false;
                    break;
                }
            }
            permittedType = permittedType.getNextSibling();
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

    /**
     * Extracts the simple (unqualified) name from a type name node, which is
     * either a single {@link TokenTypes#IDENT} or a {@link TokenTypes#DOT}
     * chain representing a qualified name.
     *
     * @param typeName the type name node
     * @return the simple name of the type
     */
    private static String getSimpleName(DetailAST typeName) {
        final String simpleName;
        if (typeName.getType() == TokenTypes.DOT) {
            simpleName = typeName.getLastChild().getText();
        }
        else {
            simpleName = typeName.getText();
        }
        return simpleName;
    }

}
