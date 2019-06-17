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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that lambda usage can be replaced with method reference.
 * </p>
 * <p>
 * Rationale: method references are easier to read.
 * See <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html">link</a>
 * for more info.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="PreferMethodReference"/&gt;
 * </pre>
 * <p>Example of violations:</p>
 * <pre>
 * Runnable r = () -&gt; System.out.println(); // violation
 * BiConsumer&lt;Long, Long&gt; c = (a, b) -&gt; Long.sum(a,b); // violation
 * </pre>
 * <p>
 * Note: This check is not type-aware, so some cases cannot be detected, e.g.
 * </p>
 * <pre>
 * Function&lt;String, Object&gt; f1 =
 *     a -&gt; (Object) "string".concat(a); // no violation because of cast
 * </pre>
 *
 * @since 8.23
 */
@StatelessCheck
public class PreferMethodReferenceCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_METHOD_REF = "method.reference";

    /**
     * List of excluded tokens that should not appear in statement list.
     *
     * @see #shouldCheck(DetailAST)
     */
    private static final List<Integer> EXCLUDED_STATEMENTS_TOKEN_TYPES = Arrays.asList(
        TokenTypes.EMPTY_STAT,
        TokenTypes.LITERAL_DO,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_RETURN,
        TokenTypes.LITERAL_SWITCH,
        TokenTypes.LITERAL_THROW,
        TokenTypes.LITERAL_TRY,
        TokenTypes.LITERAL_WHILE
    );

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
        return new int[] {TokenTypes.LAMBDA};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<String> args = getLambdaArgs(ast);
        final DetailAST expr = ast.getLastChild();
        final boolean hasViolation = checkExpr(expr, args);
        if (hasViolation) {
            log(ast, MSG_METHOD_REF);
        }
    }

    /**
     * Extracts lambda arguments.
     *
     * @param ast lambda parameters root
     * @return list of lambda args names
     */
    private static List<String> getLambdaArgs(DetailAST ast) {
        final List<String> result = new ArrayList<>();
        if (ast.getFirstChild().getType() == TokenTypes.IDENT) {
            result.add(ast.getFirstChild().getText());
        }
        else {
            final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
            TokenUtil.forEachChild(params, TokenTypes.PARAMETER_DEF,
                node -> result.add(node.findFirstToken(TokenTypes.IDENT).getText()));
        }
        return result;
    }

    /**
     * Checks if this expression can be replaced with method reference.
     *
     * @param expr token to check
     * @param args lambda arguments
     * @return true if can be replaced, false otherwise
     */
    private static boolean checkExpr(DetailAST expr, List<String> args) {
        final DetailAST child = expr.getFirstChild();
        final boolean result;
        switch (child.getType()) {
            case TokenTypes.METHOD_CALL:
                result = checkMethodCall(child, args);
                break;
            case TokenTypes.LITERAL_NEW:
                result = matchesCtorArgs(child, args) || matchesArrayInitializer(child, args);
                break;
            case TokenTypes.SLIST:
            case TokenTypes.LITERAL_RETURN:
                result = child.getFirstChild().getType() != TokenTypes.SEMI
                    && checkExpr(child.getFirstChild(), args);
                break;
            default:
                result = shouldCheck(expr)
                    && checkExpr(expr.findFirstToken(TokenTypes.EXPR), args);
                break;
        }
        return result;
    }

    /**
     * Check that method call can be replaced with method reference.
     * Two options:
     * <ul>
     * <li>Method invocation target is not lambda args and its args are same as in lambda</li>
     * <li>Method invocation target is first lambda arg and its args are rest of lambda args</li>
     * </ul>
     *
     * @param ast method call to check
     * @param args lambda args
     * @return true if method can be replaced with method reference, false otherwise.
     */
    private static boolean checkMethodCall(DetailAST ast, List<String> args) {
        final DetailAST elist = ast.findFirstToken(TokenTypes.ELIST);
        final boolean matchesMethodArgs = hasSameArgs(args, elist)
            && Collections.disjoint(args, getMethodInvocationIdents(ast));
        final boolean matchesFirstArgCall = !args.isEmpty()
            && isCallOnArg(ast, args.get(0))
            && hasSameArgs(args.subList(1, args.size()), elist);
        return matchesMethodArgs || matchesFirstArgCall;
    }

    /**
     * Check that ast can be replaced with constructor reference.
     *
     * @param ast token to check
     * @param args lambda arguments
     * @return true if token can be replaced, false otherwise
     */
    private static boolean matchesCtorArgs(DetailAST ast, List<String> args) {
        final DetailAST elist = ast.findFirstToken(TokenTypes.ELIST);
        return elist != null && !isAnonClass(ast) && hasSameArgs(args, elist);
    }

    /**
     * Check that ast can be replaced with array init reference.
     *
     * @param ast token to check
     * @param args lambda arguments
     * @return true if token can be replaced, false otherwise
     */
    private static boolean matchesArrayInitializer(DetailAST ast, List<String> args) {
        final DetailAST arrDecl = ast.findFirstToken(TokenTypes.ARRAY_DECLARATOR);
        return arrDecl != null && args.size() == 1 && isArgUsedInArrayInit(arrDecl, args.get(0));
    }

    /**
     * Checks if given ast should be checked.
     * Ast should have only one child expr, and have no other tokens from
     * {@link #EXCLUDED_STATEMENTS_TOKEN_TYPES} list.
     *
     * @param ast token to check
     * @return true if there is only one child expression, false otherwise
     */
    private static boolean shouldCheck(DetailAST ast) {
        return ast.getChildCount(TokenTypes.EXPR) == 1
            && ast.getChildCount(TokenTypes.SEMI) == 1
            && !TokenUtil.findFirstTokenByPredicate(ast,
                expr -> EXCLUDED_STATEMENTS_TOKEN_TYPES.contains(expr.getType())).isPresent();
    }

    /**
     * Checks that given ast arg list is same as {@code args}.
     *
     * @param args lambda args
     * @param ast arg list of element
     * @return true if arguments are same, false otherwise.
     */
    private static boolean hasSameArgs(List<String> args, DetailAST ast) {
        boolean onlyIdents = true;
        final List<String> idents = new ArrayList<>();
        for (DetailAST node = ast.getFirstChild();
             node != null;
             node = node.getNextSibling()) {
            if (node.getType() == TokenTypes.EXPR
                && node.findFirstToken(TokenTypes.IDENT) != null) {
                idents.add(node.findFirstToken(TokenTypes.IDENT).getText());
            }
            else if (node.getType() != TokenTypes.COMMA) {
                onlyIdents = false;
                break;
            }
        }
        return onlyIdents && idents.equals(args);
    }

    /**
     * Get unique non-method idents from method invocation.
     *
     * @param ast lambda token
     * @return set of non-method idents.
     */
    private static Set<String> getMethodInvocationIdents(DetailAST ast) {
        return Optional.ofNullable(ast.findFirstToken(TokenTypes.DOT))
            .map(PreferMethodReferenceCheck::getExprIdents)
            .orElse(Collections.emptySet());
    }

    /**
     * Check that method call expression is equal to {@code arg}.
     *
     * @param ast lambda token
     * @param arg lambda argument name
     * @return true if method is called on first lambda arg, false otherwise.
     */
    private static boolean isCallOnArg(DetailAST ast, String arg) {
        final DetailAST dot = ast.findFirstToken(TokenTypes.DOT);
        return dot != null
            && dot.getChildCount(TokenTypes.IDENT) == 2
            && arg.equals(dot.findFirstToken(TokenTypes.IDENT).getText());
    }

    /**
     * Checks that given new object creation is anon class.
     *
     * @param ast token to check
     * @return true if it is anon class, false otherwise
     */
    private static boolean isAnonClass(DetailAST ast) {
        return ast.getLastChild().getType() == TokenTypes.OBJBLOCK;
    }

    /**
     * Check first array init matches given {@code name} and there are no more init expressions.
     *
     * @param declarator array declarator to check
     * @param name expr ident to compare against
     * @return {@code true} if first array init expression is {@link TokenTypes#IDENT} and
     *     same as {@code name} and there are no more expressions (if array is multidimensional),
     * {@code false} otherwise
     */
    private static boolean isArgUsedInArrayInit(DetailAST declarator, String name) {
        final DetailAST nestedDecl = declarator.findFirstToken(TokenTypes.ARRAY_DECLARATOR);
        final DetailAST expr = declarator.findFirstToken(TokenTypes.EXPR);
        boolean result = false;
        if (nestedDecl == null && expr != null) {
            final DetailAST ident = expr.findFirstToken(TokenTypes.IDENT);
            result = ident != null && name.equals(ident.getText());
        }
        else if (nestedDecl != null && expr == null) {
            result = isArgUsedInArrayInit(nestedDecl, name);
        }
        return result;
    }

    /**
     * Get unique non-method idents.
     *
     * @param ast parent node to collect idents from
     * @return non-method idents from ast subtree
     */
    private static Set<String> getExprIdents(DetailAST ast) {
        final Set<String> result = new HashSet<>();
        DetailAST node = ast.getFirstChild();
        while (node != null) {
            if (node.getType() == TokenTypes.IDENT && !isMethodIdent(node)) {
                result.add(node.getText());
            }
            else {
                result.addAll(getExprIdents(node));
            }
            node = node.getNextSibling();
        }
        return result;
    }

    /**
     * Check if ast is a method ident.
     * Either it is last child of {@link TokenTypes#DOT}<br/>
     * or has {@link TokenTypes#ELIST} as next sibling
     * or is a part of method reference.
     *
     * @param node ident to check
     * @return true if method ident, false otherwise
     */
    private static boolean isMethodIdent(DetailAST node) {
        final DetailAST nextSibling = node.getNextSibling();
        final boolean result;
        if (nextSibling == null) {
            final int parentType = node.getParent().getType();
            result = parentType == TokenTypes.DOT || parentType == TokenTypes.METHOD_REF;
        }
        else {
            result = nextSibling.getType() == TokenTypes.ELIST;
        }
        return result;
    }
}
