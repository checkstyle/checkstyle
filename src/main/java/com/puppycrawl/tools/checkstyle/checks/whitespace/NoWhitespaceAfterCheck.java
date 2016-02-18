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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that there is no whitespace after a token.
 * More specifically, it checks that it is not followed by whitespace,
 * or (if linebreaks are allowed) all characters on the line after are
 * whitespace. To forbid linebreaks after a token, set property
 * allowLineBreaks to false.
 * </p>
  * <p> By default the check will check the following operators:
 *  {@link TokenTypes#ARRAY_INIT ARRAY_INIT},
 *  {@link TokenTypes#BNOT BNOT},
 *  {@link TokenTypes#DEC DEC},
 *  {@link TokenTypes#DOT DOT},
 *  {@link TokenTypes#INC INC},
 *  {@link TokenTypes#LNOT LNOT},
 *  {@link TokenTypes#UNARY_MINUS UNARY_MINUS},
 *  {@link TokenTypes#UNARY_PLUS UNARY_PLUS},
 *  {@link TokenTypes#TYPECAST TYPECAST},
 *  {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR},
 *  {@link TokenTypes#INDEX_OP INDEX_OP}.
 * </p>
 * <p>
 * The check processes
 * {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR},
 * {@link TokenTypes#INDEX_OP INDEX_OP}
 * specially from other tokens. Actually it is checked that there is
 * no whitespace before this tokens, not after them.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NoWhitespaceAfter"/&gt;
 * </pre>
 * <p> An example of how to configure the check to forbid linebreaks after
 * a {@link TokenTypes#DOT DOT} token is:
 * </p>
 * <pre>
 * &lt;module name="NoWhitespaceAfter"&gt;
 *     &lt;property name="tokens" value="DOT"/&gt;
 *     &lt;property name="allowLineBreaks" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @author lkuehne
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 * @author attatrol
 */
public class NoWhitespaceAfterCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.followed";

    /** Whether whitespace is allowed if the AST is at a linebreak. */
    private boolean allowLineBreaks = true;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.ARRAY_INIT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.DOT,
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ARRAY_INIT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.DOT,
            TokenTypes.TYPECAST,
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
        };
    }

    /**
     * Control whether whitespace is flagged at linebreaks.
     * @param allowLineBreaks whether whitespace should be
     *     flagged at linebreaks.
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST whitespaceFollowedAst = getWhitespaceFollowedNode(ast);

        final int whitespaceColumnNo = getPositionAfter(whitespaceFollowedAst);
        final int whitespaceLineNo = whitespaceFollowedAst.getLineNo();

        if (hasTrailingWhitespace(ast, whitespaceColumnNo, whitespaceLineNo)) {
            log(whitespaceLineNo, whitespaceColumnNo,
                MSG_KEY, whitespaceFollowedAst.getText());
        }
    }

    /**
     * For a visited ast node returns node that should be checked
     * for not being followed by whitespace.
     * @param ast
     *        , visited node.
     * @return node before ast.
     */
    private static DetailAST getWhitespaceFollowedNode(DetailAST ast) {
        final DetailAST whitespaceFollowedAst;
        switch (ast.getType()) {
            case TokenTypes.TYPECAST:
                whitespaceFollowedAst = ast.findFirstToken(TokenTypes.RPAREN);
                break;
            case TokenTypes.ARRAY_DECLARATOR:
                whitespaceFollowedAst = getArrayDeclaratorPreviousElement(ast);
                break;
            case TokenTypes.INDEX_OP:
                whitespaceFollowedAst = getIndexOpPreviousElement(ast);
                break;
            default:
                whitespaceFollowedAst = ast;
        }
        return whitespaceFollowedAst;
    }

    /**
     * Gets position after token (place of possible redundant whitespace).
     * @param ast Node representing token.
     * @return position after token.
     */
    private static int getPositionAfter(DetailAST ast) {
        final int after;
        //If target of possible redundant whitespace is in method definition.
        if (ast.getType() == TokenTypes.IDENT
                && ast.getNextSibling() != null
                && ast.getNextSibling().getType() == TokenTypes.LPAREN) {
            final DetailAST methodDef = ast.getParent();
            final DetailAST endOfParams = methodDef.findFirstToken(TokenTypes.RPAREN);
            after = endOfParams.getColumnNo() + 1;
        }
        else {
            after = ast.getColumnNo() + ast.getText().length();
        }
        return after;
    }

    /**
     * Checks if there is unwanted whitespace after the visited node.
     * @param ast
     *        , visited node.
     * @param whitespaceColumnNo
     *        , column number of a possible whitespace.
     * @param whitespaceLineNo
     *        , line number of a possible whitespace.
     * @return true if whitespace found.
     */
    private boolean hasTrailingWhitespace(DetailAST ast,
        int whitespaceColumnNo, int whitespaceLineNo) {
        final boolean result;
        final int astLineNo = ast.getLineNo();
        final String line = getLine(astLineNo - 1);
        if (astLineNo == whitespaceLineNo && whitespaceColumnNo < line.length()) {
            result = Character.isWhitespace(line.charAt(whitespaceColumnNo));
        }
        else {
            result = !allowLineBreaks;
        }
        return result;
    }

    /**
     * Returns proper argument for getPositionAfter method, it is a token after
     * {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}, in can be {@link TokenTypes#RBRACK
     * RBRACK}, {@link TokenTypes#IDENT IDENT} or an array type definition (literal).
     * @param ast
     *        , {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR} node.
     * @return previous node by text order.
     */
    private static DetailAST getArrayDeclaratorPreviousElement(DetailAST ast) {
        final DetailAST previousElement;
        final DetailAST firstChild = ast.getFirstChild();
        if (firstChild.getType() == TokenTypes.ARRAY_DECLARATOR) {
            // second or higher array index
            previousElement = firstChild.findFirstToken(TokenTypes.RBRACK);
        }
        else {
            // first array index, is preceded with identifier or type
            final DetailAST parent = getFirstNonArrayDeclaratorParent(ast);
            switch (parent.getType()) {
                // generics
                case TokenTypes.TYPE_ARGUMENT:
                    final DetailAST wildcard = parent.findFirstToken(TokenTypes.WILDCARD_TYPE);
                    if (wildcard == null) {
                        // usual generic type argument like <char[]>
                        previousElement = getTypeLastNode(ast);
                    }
                    else {
                        // constructions with wildcard like <? extends String[]>
                        previousElement = getTypeLastNode(ast.getFirstChild());
                    }
                    break;
                // 'new' is a special case with its own subtree structure
                case TokenTypes.LITERAL_NEW:
                    previousElement = getTypeLastNode(parent);
                    break;
                // mundane array declaration, can be either java style or C style
                case TokenTypes.TYPE:
                    previousElement = getPreviousNodeWithParentOfTypeAst(ast, parent);
                    break;
                // i.e. boolean[].class
                case TokenTypes.DOT:
                    previousElement = getTypeLastNode(ast);
                    break;
                // java 8 method reference
                case TokenTypes.METHOD_REF:
                    final DetailAST ident = getIdentLastToken(ast);
                    if (ident == null) {
                        //i.e. int[]::new
                        previousElement = ast.getFirstChild();
                    }
                    else {
                        previousElement = ident;
                    }
                    break;
                default:
                    throw new IllegalStateException("unexpected ast syntax" + parent);
            }
        }
        return previousElement;
    }

    /**
     * Gets previous node for {@link TokenTypes#INDEX_OP INDEX_OP} token
     * for usage in getPositionAfter method, it is a simplified copy of
     * getArrayDeclaratorPreviousElement method.
     * @param ast
     *        , {@link TokenTypes#INDEX_OP INDEX_OP} node.
     * @return previous node by text order.
     */
    private static DetailAST getIndexOpPreviousElement(DetailAST ast) {
        final DetailAST result;
        final DetailAST firstChild = ast.getFirstChild();
        if (firstChild.getType() == TokenTypes.INDEX_OP) {
            // second or higher array index
            result = firstChild.findFirstToken(TokenTypes.RBRACK);
        }
        else {
            final DetailAST ident = getIdentLastToken(ast);
            if (ident == null) {
                // construction like ((byte[]) pixels)[0]
                result = ast.findFirstToken(TokenTypes.RPAREN);
            }
            else {
                result = ident;
            }
        }
        return result;
    }

    /**
     * Get node that owns {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR} sequence.
     * @param ast
     *        , {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR} node.
     * @return owner node.
     */
    private static DetailAST getFirstNonArrayDeclaratorParent(DetailAST ast) {
        DetailAST parent = ast.getParent();
        while (parent.getType() == TokenTypes.ARRAY_DECLARATOR) {
            parent = parent.getParent();
        }
        return parent;
    }

    /**
     * Searches parameter node for a type node.
     * Returns it or its last node if it has an extended structure.
     * @param ast
     *        , subject node.
     * @return type node.
     */
    private static DetailAST getTypeLastNode(DetailAST ast) {
        DetailAST result = ast.findFirstToken(TokenTypes.TYPE_ARGUMENTS);
        if (result == null) {
            result = getIdentLastToken(ast);
            if (result == null) {
                //primitive literal expected
                result = ast.getFirstChild();
            }
        }
        else {
            result = result.findFirstToken(TokenTypes.GENERIC_END);
        }
        return result;
    }

    /**
     * Finds previous node by text order for an array declarator,
     * which parent type is {@link TokenTypes#TYPE TYPE}.
     * @param ast
     *        , array declarator node.
     * @param parent
     *        , its parent node.
     * @return previous node by text order.
     */
    private static DetailAST getPreviousNodeWithParentOfTypeAst(DetailAST ast, DetailAST parent) {
        final DetailAST previousElement;
        final DetailAST ident = getIdentLastToken(parent.getParent());
        final DetailAST lastTypeNode = getTypeLastNode(ast);
        // sometimes there are ident-less sentences
        // i.e. "(Object[]) null", but in casual case should be
        // checked whether ident or lastTypeNode has preceding position
        // determining if it is java style or C style
        if (ident == null || ident.getLineNo() > ast.getLineNo()) {
            previousElement = lastTypeNode;
        }
        else if (ident.getLineNo() < ast.getLineNo()) {
            previousElement = ident;
        }
        //ident and lastTypeNode lay on one line
        else {
            if (ident.getColumnNo() > ast.getColumnNo()
                || lastTypeNode.getColumnNo() > ident.getColumnNo()) {
                previousElement = lastTypeNode;
            }
            else {
                previousElement = ident;
            }
        }
        return previousElement;
    }

    /**
     * Gets leftmost token of identifier.
     * @param ast
     *        , token possibly possessing an identifier.
     * @return leftmost token of identifier.
     */
    private static DetailAST getIdentLastToken(DetailAST ast) {
        // single identifier token as a name is the most common case
        DetailAST result = ast.findFirstToken(TokenTypes.IDENT);
        if (result == null) {
            final DetailAST dot = ast.findFirstToken(TokenTypes.DOT);
            // method call case
            if (dot == null) {
                final DetailAST methodCall = ast.findFirstToken(TokenTypes.METHOD_CALL);
                if (methodCall != null) {
                    result = methodCall.findFirstToken(TokenTypes.RPAREN);
                }
            }
            // qualified name case
            else {
                if (dot.findFirstToken(TokenTypes.DOT) == null) {
                    result = dot.getFirstChild().getNextSibling();
                }
                else {
                    result = dot.findFirstToken(TokenTypes.IDENT);
                }
            }
        }
        return result;
    }

}
