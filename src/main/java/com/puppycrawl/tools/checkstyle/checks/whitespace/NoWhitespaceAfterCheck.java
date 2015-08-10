////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that there is no whitespace after a token.
 * More specifically, it checks that it is not followed by whitespace,
 * or (if linebreaks are allowed) all characters on the line after are
 * whitespace. To forbid linebreaks afer a token, set property
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
 *  {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR},
 *  {@link TokenTypes#UNARY_PLUS UNARY_PLUS}. It also supports the operator
 *  {@link TokenTypes#TYPECAST TYPECAST}.
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
 */
public class NoWhitespaceAfterCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.followed";

    /** Whether whitespace is allowed if the AST is at a linebreak */
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
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST astNode = getPreceded(ast);
        final String line = getLine(ast.getLineNo() - 1);
        final int after = getPositionAfter(astNode);

        if ((after >= line.length() || Character.isWhitespace(line.charAt(after)))
                 && hasRedundantWhitespace(line, after)) {
            log(astNode.getLineNo(), after,
                MSG_KEY, astNode.getText());
        }
    }

    /**
     * Gets possible place where redundant whitespace could be.
     * @param ast Node representing token.
     * @return possible place of redundant whitespace.
     */
    private static DetailAST getPreceded(DetailAST ast) {
        DetailAST preceded;

        switch (ast.getType()) {
            case TokenTypes.TYPECAST:
                preceded = ast.findFirstToken(TokenTypes.RPAREN);
                break;
            case TokenTypes.ARRAY_DECLARATOR:
                preceded = getArrayTypeOrIdentifier(ast);
                break;
            default:
                preceded = ast;
        }
        return preceded;
    }

    /**
     * Gets position after token (place of possible redundant whitespace).
     * @param ast Node representing token.
     * @return position after token.
     */
    private static int getPositionAfter(DetailAST ast) {
        int after;
        //If target of possible redundant whitespace is in method definition
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
     * Gets target place of possible redundant whitespace (array's type or identifier)
     *  after which {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR} is set.
     * @param arrayDeclarator {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return target place before possible redundant whitespace.
     */
    private static DetailAST getArrayTypeOrIdentifier(DetailAST arrayDeclarator) {
        DetailAST typeOrIdent = arrayDeclarator;
        if (isArrayInstantiation(arrayDeclarator)) {
            typeOrIdent = arrayDeclarator.getParent().getFirstChild();
        }
        else if (isMultiDimensionalArray(arrayDeclarator)) {
            if (isCstyleMultiDimensionalArrayDeclaration(arrayDeclarator)) {
                if (arrayDeclarator.getParent().getType() != TokenTypes.ARRAY_DECLARATOR) {
                    typeOrIdent = getArrayIdentifier(arrayDeclarator);
                }
            }
            else {
                DetailAST arrayIdentifier = arrayDeclarator.getFirstChild();
                while (arrayIdentifier != null) {
                    typeOrIdent = arrayIdentifier;
                    arrayIdentifier = arrayIdentifier.getFirstChild();
                }
            }
        }
        else {
            if (isCstyleArrayDeclaration(arrayDeclarator)) {
                typeOrIdent = getArrayIdentifier(arrayDeclarator);
            }
            else {
                if (isArrayUsedAsTypeForGenericBoundedWildcard(arrayDeclarator)) {
                    typeOrIdent = arrayDeclarator.getParent();
                }
                else {
                    typeOrIdent = arrayDeclarator.getFirstChild();
                }
            }
        }
        return typeOrIdent;
    }

    /**
     * Gets array identifier, e.g.:
     * <p>
     * {@code
     * int[] someArray;
     * }
     * </p>
     * <p>
     * someArray is identifier.
     * </p>
     * @param arrayDeclarator {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return array identifier.
     */
    private static DetailAST getArrayIdentifier(DetailAST arrayDeclarator) {
        return arrayDeclarator.getParent().getNextSibling();
    }

    /**
     * Checks if current array is multidimensional.
     * @param arrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if current array is multidimensional.
     */
    private static boolean isMultiDimensionalArray(DetailAST arrayDeclaration) {
        return arrayDeclaration.getParent().getType() == TokenTypes.ARRAY_DECLARATOR
                || arrayDeclaration.getFirstChild().getType() == TokenTypes.ARRAY_DECLARATOR;
    }

    /**
     * Checks if current array declaration is part of array instantiation.
     * @param arrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if current array declaration is part of array instantiation.
     */
    private static boolean isArrayInstantiation(DetailAST arrayDeclaration) {
        return arrayDeclaration.getParent().getType() == TokenTypes.LITERAL_NEW;
    }

    /**
     * Checks if current array is used as type for generic bounded wildcard.
     * <p>
     * E.g. {@code <? extends String[]>} or {@code <? super Object[]>}.
     * </p>
     * @param arrayDeclarator {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if current array is used as type for generic bounded wildcard.
     */
    private static boolean isArrayUsedAsTypeForGenericBoundedWildcard(DetailAST arrayDeclarator) {
        final int firstChildType = arrayDeclarator.getFirstChild().getType();
        return firstChildType == TokenTypes.TYPE_UPPER_BOUNDS
                || firstChildType == TokenTypes.TYPE_LOWER_BOUNDS;
    }

    /**
     * Control whether whitespace is flagged at linebreaks.
     * @param allowLineBreaks whether whitespace should be
     * flagged at linebreaks.
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }

    /**
     * Checks if current array is declared in C style, e.g.:
     * <p>
     * {@code
     * int array[] = { ... }; //C style
     * }
     * </p>
     * <p>
     * {@code
     * int[] array = { ... }; //Java style
     * }
     * </p>
     * @param arrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if array is declared in C style
     */
    private static boolean isCstyleArrayDeclaration(DetailAST arrayDeclaration) {
        boolean result = false;
        final DetailAST identifier = getArrayIdentifier(arrayDeclaration);
        if (identifier != null) {
            final int arrayDeclarationStart = arrayDeclaration.getColumnNo();
            final int identifierEnd = identifier.getColumnNo() + identifier.getText().length();
            result = arrayDeclarationStart == identifierEnd
                     || arrayDeclarationStart > identifierEnd;
        }
        return result;
    }

    /**
     * Works with multidimensional arrays.
     * @param arrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if multidimensional array is declared in C style.
     */
    private static boolean isCstyleMultiDimensionalArrayDeclaration(DetailAST arrayDeclaration) {
        boolean result = false;
        DetailAST parentArrayDeclaration = arrayDeclaration;
        while (parentArrayDeclaration != null) {
            if (parentArrayDeclaration.getParent() != null
                    && parentArrayDeclaration.getParent().getType() == TokenTypes.TYPE) {
                result = isCstyleArrayDeclaration(parentArrayDeclaration);
            }
            parentArrayDeclaration = parentArrayDeclaration.getParent();
        }
        return result;
    }

    /**
     * Checks if current line has redundant whitespace after specified index.
     * @param line line of java source.
     * @param after specified index.
     * @return true if line contains redundant whitespace.
     */
    private boolean hasRedundantWhitespace(String line, int after) {
        boolean result = !allowLineBreaks;
        for (int i = after + 1; !result && i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                result = true;
            }
        }
        return result;
    }
}
