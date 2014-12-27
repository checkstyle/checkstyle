////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
 * @version 1.0
 */
public class NoWhitespaceAfterCheck extends Check
{
    /** Whether whitespace is allowed if the AST is at a linebreak */
    private boolean mAllowLineBreaks = true;

    @Override
    public int[] getDefaultTokens()
    {
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
    public int[] getAcceptableTokens()
    {
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
    public void visitToken(DetailAST aAST)
    {
        DetailAST ast = aAST;
        if (aAST.getType() == TokenTypes.ARRAY_DECLARATOR
                 || aAST.getType() == TokenTypes.TYPECAST)
        {
            ast = getPreceded(aAST);
        }

        final String line = getLine(aAST.getLineNo() - 1);
        final int after = getPositionAfter(ast);

        if ((after >= line.length() || Character.isWhitespace(line.charAt(after)))
                 && hasRedundantWhitespace(line, after))
        {
            log(ast.getLineNo(), after,
                "ws.followed", ast.getText());
        }
    }

    /**
     * Gets possible place where redundant whitespace could be.
     * @param aArrayOrTypeCast {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     *  or {@link TokenTypes#TYPECAST TYPECAST}.
     * @return possible place of redundant whitespace.
     */
    private static DetailAST getPreceded(DetailAST aArrayOrTypeCast)
    {
        DetailAST preceded = aArrayOrTypeCast;
        switch (aArrayOrTypeCast.getType()) {
        case TokenTypes.TYPECAST:
            preceded = aArrayOrTypeCast.findFirstToken(TokenTypes.RPAREN);
            break;
        case TokenTypes.ARRAY_DECLARATOR:
            preceded = getArrayTypeOrIdentifier(aArrayOrTypeCast);
            break;
        default:
            throw new IllegalStateException(aArrayOrTypeCast.toString());
        }
        return preceded;
    }

    /**
     * Gets position after token (place of possible redundant whitespace).
     * @param aAST Node representing token.
     * @return position after token.
     */
    private static int getPositionAfter(DetailAST aAST)
    {
        int after;
        //If target of possible redundant whitespace is in method definition
        if (aAST.getType() == TokenTypes.IDENT
                && aAST.getNextSibling() != null
                && aAST.getNextSibling().getType() == TokenTypes.LPAREN)
        {
            final DetailAST methodDef = aAST.getParent();
            final DetailAST endOfParams = methodDef.findFirstToken(TokenTypes.RPAREN);
            after = endOfParams.getColumnNo() + 1;
        }
        else {
            after = aAST.getColumnNo() + aAST.getText().length();
        }
        return after;
    }

    /**
     * Gets target place of possible redundant whitespace (array's type or identifier)
     *  after which {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR} is set.
     * @param aArrayDeclarator {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return target place before possible redundant whitespace.
     */
    private static DetailAST getArrayTypeOrIdentifier(DetailAST aArrayDeclarator)
    {
        DetailAST typeOrIdent = aArrayDeclarator;
        if (isArrayInstantiation(aArrayDeclarator)) {
            typeOrIdent = aArrayDeclarator.getParent().getFirstChild();
        }
        else if (isMultiDimensionalArray(aArrayDeclarator)) {
            if (isCstyleMultiDimensionalArrayDeclaration(aArrayDeclarator)) {
                if (aArrayDeclarator.getParent().getType() != TokenTypes.ARRAY_DECLARATOR) {
                    typeOrIdent = getArrayIdentifier(aArrayDeclarator);
                }
            }
            else {
                DetailAST arrayIdentifier = aArrayDeclarator.getFirstChild();
                while (arrayIdentifier != null) {
                    typeOrIdent = arrayIdentifier;
                    arrayIdentifier = arrayIdentifier.getFirstChild();
                }
            }
        }
        else {
            if (isCstyleArrayDeclaration(aArrayDeclarator)) {
                typeOrIdent = getArrayIdentifier(aArrayDeclarator);
            }
            else {
                typeOrIdent = aArrayDeclarator.getFirstChild();
            }
        }
        return typeOrIdent;
    }

    /**
     * Gets array identifier, e.g.:
     * <p>
     * <code>
     * int[] someArray;
     * <code>
     * </p>
     * <p>
     * someArray is identifier.
     * </p>
     * @param aArrayDeclarator {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return array identifier.
     */
    private static DetailAST getArrayIdentifier(DetailAST aArrayDeclarator)
    {
        return aArrayDeclarator.getParent().getNextSibling();
    }

    /**
     * Checks if current array is multidimensional.
     * @param aArrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if current array is multidimensional.
     */
    private static boolean isMultiDimensionalArray(DetailAST aArrayDeclaration)
    {
        return aArrayDeclaration.getParent().getType() == TokenTypes.ARRAY_DECLARATOR
                || aArrayDeclaration.getFirstChild().getType() == TokenTypes.ARRAY_DECLARATOR;
    }

    /**
     * Checks if current array declaration is part of array instantiation.
     * @param aArrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if current array declaration is part of array instantiation.
     */
    private static boolean isArrayInstantiation(DetailAST aArrayDeclaration)
    {
        return aArrayDeclaration.getParent().getType() == TokenTypes.LITERAL_NEW;
    }

    /**
     * Control whether whitespace is flagged at linebreaks.
     * @param aAllowLineBreaks whether whitespace should be
     * flagged at linebreaks.
     */
    public void setAllowLineBreaks(boolean aAllowLineBreaks)
    {
        mAllowLineBreaks = aAllowLineBreaks;
    }

    /**
     * Checks if current array is declared in C style, e.g.:
     * <p>
     * <code>
     * int array[] = { ... }; //C style
     * </code>
     * </p>
     * <p>
     * <code>
     * int[] array = { ... }; //Java style
     * </code>
     * </p>
     * @param aArrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if array is declared in C style
     */
    private static boolean isCstyleArrayDeclaration(DetailAST aArrayDeclaration)
    {
        boolean result = false;
        final DetailAST identifier = getArrayIdentifier(aArrayDeclaration);
        if (identifier != null) {
            final int arrayDeclarationStart = aArrayDeclaration.getColumnNo();
            final int identifierEnd = identifier.getColumnNo() + identifier.getText().length();
            result = arrayDeclarationStart == identifierEnd
                     || arrayDeclarationStart > identifierEnd;
        }
        return result;
    }

    /**
     * Works with multidimensional arrays.
     * @param aArrayDeclaration {@link TokenTypes#ARRAY_DECLARATOR ARRAY_DECLARATOR}
     * @return true if multidimensional array is declared in C style.
     */
    private static boolean isCstyleMultiDimensionalArrayDeclaration(DetailAST aArrayDeclaration)
    {
        boolean result = false;
        DetailAST parentArrayDeclaration = aArrayDeclaration;
        while (parentArrayDeclaration != null) {
            if (parentArrayDeclaration.getParent() != null
                    && parentArrayDeclaration.getParent().getType() == TokenTypes.TYPE)
            {
                result = isCstyleArrayDeclaration(parentArrayDeclaration);
            }
            parentArrayDeclaration = parentArrayDeclaration.getParent();
        }
        return result;
    }

    /**
     * Checks if current line has redundant whitespace after specified index.
     * @param aLine line of java source.
     * @param aAfter specified index.
     * @return true if line contains redundant whitespace.
     */
    private boolean hasRedundantWhitespace(String aLine, int aAfter)
    {
        boolean result = !mAllowLineBreaks;
        for (int i = aAfter + 1; !result && (i < aLine.length()); i++) {
            if (!Character.isWhitespace(aLine.charAt(i))) {
                result = true;
            }
        }
        return result;
    }
}
