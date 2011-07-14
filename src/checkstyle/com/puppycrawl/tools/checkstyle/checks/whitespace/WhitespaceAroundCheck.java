////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
 * Checks that a token is surrounded by whitespace.
 * </p>
 * <p> By default the check will check the following operators:
 *  {@link TokenTypes#LITERAL_ASSERT ASSERT},
 *  {@link TokenTypes#ASSIGN ASSIGN},
 *  {@link TokenTypes#BAND BAND},
 *  {@link TokenTypes#BAND_ASSIGN BAND_ASSIGN},
 *  {@link TokenTypes#BOR BOR},
 *  {@link TokenTypes#BOR_ASSIGN BOR_ASSIGN},
 *  {@link TokenTypes#BSR BSR},
 *  {@link TokenTypes#BSR_ASSIGN BSR_ASSIGN},
 *  {@link TokenTypes#BXOR BXOR},
 *  {@link TokenTypes#BXOR_ASSIGN BXOR_ASSIGN},
 *  {@link TokenTypes#COLON COLON},
 *  {@link TokenTypes#DIV DIV},
 *  {@link TokenTypes#DIV_ASSIGN DIV_ASSIGN},
 *  {@link TokenTypes#EQUAL EQUAL},
 *  {@link TokenTypes#GE GE},
 *  {@link TokenTypes#GT GT},
 *  {@link TokenTypes#LAND LAND},
 *  {@link TokenTypes#LCURLY LCURLY},
 *  {@link TokenTypes#LE LE},
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_RETURN LITERAL_RETURN},
 *  {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED},
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE},
 *  {@link TokenTypes#LOR LOR},
 *  {@link TokenTypes#LT LT},
 *  {@link TokenTypes#MINUS MINUS},
 *  {@link TokenTypes#MINUS_ASSIGN MINUS_ASSIGN},
 *  {@link TokenTypes#MOD MOD},
 *  {@link TokenTypes#MOD_ASSIGN MOD_ASSIGN},
 *  {@link TokenTypes#NOT_EQUAL NOT_EQUAL},
 *  {@link TokenTypes#PLUS PLUS},
 *  {@link TokenTypes#PLUS_ASSIGN PLUS_ASSIGN},
 *  {@link TokenTypes#QUESTION QUESTION},
 *  {@link TokenTypes#RCURLY RCURLY},
 *  {@link TokenTypes#SL SL},
 *  {@link TokenTypes#SLIST SLIST},
 *  {@link TokenTypes#SL_ASSIGN SL_ASSIGN},
 *  {@link TokenTypes#SR SR},
 *  {@link TokenTypes#SR_ASSIGN SR_ASSIGN},
 *  {@link TokenTypes#STAR STAR},
 *  {@link TokenTypes#STAR_ASSIGN STAR_ASSIGN}.
 *  {@link TokenTypes#LITERAL_ASSERT LITERAL_ASSERT}.
 *  {@link TokenTypes#TYPE_EXTENSION_AND TYPE_EXTENSION_AND}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="WhitespaceAround"/&gt;
 * </pre>
 * <p> An example of how to configure the check for whitespace only around
 * assignment operators is:
 * </p>
 * <pre>
 * &lt;module name="WhitespaceAround"&gt;
 *     &lt;property name="tokens"
 *               value="ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,BAND_ASSIGN"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * In addition, this check can be configured to allow empty method and/or
 * constructor bodies.  For example, a method with an empty body might look
 * like:
 * </p>
 * <p>
 * <pre>    public void doSomething(int val) {}</pre>
 * </p>
 * <p>
 * To configure the check to allow empty method blocks use
 * </p>
 * <p>
 * <pre>   &lt;property name="allowEmptyMethods" value="true" /&gt;</pre>
 * </p>
 * <p>
 * To configure the check to allow empty constructor blocks use
 * </p>
 * <p>
 * <pre>   &lt;property name="allowEmptyConstructors" value="true" /&gt;</pre>
 * </p>
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class WhitespaceAroundCheck extends Check
{
    /** Whether or not empty constructor bodies are allowed. */
    private boolean mAllowEmptyCtors;
    /** Whether or not empty method bodies are allowed. */
    private boolean mAllowEmptyMethods;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.ASSIGN,
            TokenTypes.BAND,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.COLON,
            TokenTypes.DIV,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.EQUAL,
            TokenTypes.GE,
            TokenTypes.GT,
            TokenTypes.LAND,
            TokenTypes.LCURLY,
            TokenTypes.LE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LOR,
            TokenTypes.LT,
            TokenTypes.MINUS,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL,
            TokenTypes.PLUS,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.QUESTION,
            TokenTypes.RCURLY,
            TokenTypes.SL,
            TokenTypes.SLIST,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LITERAL_ASSERT,
            TokenTypes.TYPE_EXTENSION_AND,
        };
    }

    /**
     * Sets whether or now empty method bodies are allowed.
     * @param aAllow <code>true</code> to allow empty method bodies.
     */
    public void setAllowEmptyMethods(boolean aAllow)
    {
        mAllowEmptyMethods = aAllow;
    }

    /**
     * Sets whether or now empty constructor bodies are allowed.
     * @param aAllow <code>true</code> to allow empty constructor bodies.
     */
    public void setAllowEmptyConstructors(boolean aAllow)
    {
        mAllowEmptyCtors = aAllow;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final int type = aAST.getType();
        final int parentType = aAST.getParent().getType();

        // Check for CURLY in array initializer
        if (((type == TokenTypes.RCURLY) || (type == TokenTypes.LCURLY))
            && (parentType == TokenTypes.ARRAY_INIT))
        {
            return;
        }

        // Check for import pkg.name.*;
        if ((type == TokenTypes.STAR)
            && (parentType == TokenTypes.DOT))
        {
            return;
        }

        // Check for an SLIST that has a parent CASE_GROUP. It is not a '{'.
        if ((type == TokenTypes.SLIST)
            && (parentType == TokenTypes.CASE_GROUP))
        {
            return;
        }

        //we do not want to check colon for cases and defaults
        if ((type == TokenTypes.COLON)
            && ((parentType == TokenTypes.LITERAL_DEFAULT)
                || (parentType == TokenTypes.LITERAL_CASE)))
        {
            return;
        }

        // Check for allowed empty method or ctor blocks.
        if (emptyMethodBlockCheck(aAST, parentType)
            || emptyCtorBlockCheck(aAST, parentType))
        {
            return;
        }

        final String[] lines = getLines();
        final String line = lines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + aAST.getText().length();

        if ((before >= 0) && !Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(), aAST.getColumnNo(),
                    "ws.notPreceded", aAST.getText());
        }

        if (after >= line.length()) {
            return;
        }

        final char nextChar = line.charAt(after);
        if (!Character.isWhitespace(nextChar)
            // Check for "return;"
            && !((type == TokenTypes.LITERAL_RETURN)
                && (aAST.getFirstChild().getType() == TokenTypes.SEMI))
            // Check for "})" or "};" or "},". Happens with anon-inners
            && !((type == TokenTypes.RCURLY)
                && ((nextChar == ')')
                    || (nextChar == ';')
                    || (nextChar == ','))))
        {
            log(aAST.getLineNo(), aAST.getColumnNo() + aAST.getText().length(),
                    "ws.notFollowed", aAST.getText());
        }
    }

    /**
     * Test if the given <code>DetailAST</code> is part of an allowed empty
     * method block.
     * @param aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         allowed empty method block.
     */
    private boolean emptyMethodBlockCheck(DetailAST aAST, int aParentType)
    {
        return mAllowEmptyMethods
            && emptyBlockCheck(aAST, aParentType, TokenTypes.METHOD_DEF);
    }

    /**
     * Test if the given <code>DetailAST</code> is part of an allowed empty
     * constructor (ctor) block.
     * @param aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         allowed empty constructor block.
     */
    private boolean emptyCtorBlockCheck(DetailAST aAST, int aParentType)
    {
        return mAllowEmptyCtors
            && emptyBlockCheck(aAST, aParentType, TokenTypes.CTOR_DEF);
    }

    /**
     * Test if the given <code>DetailAST</code> is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   public void myMethod(int val) {}</pre>
     * <p>
     * In the above, the method body is an empty block ("{}").
     *
     * @param aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @param aMatch the parent token type we're looking to match.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         empty block contained under a <code>aMatch</code> token type
     *         node.
     */
    private boolean emptyBlockCheck(DetailAST aAST, int aParentType, int aMatch)
    {
        final int type = aAST.getType();
        if (type == TokenTypes.RCURLY) {
            final DetailAST grandParent = aAST.getParent().getParent();
            return (aParentType == TokenTypes.SLIST)
                && (grandParent.getType() == aMatch);
        }

        return (type == TokenTypes.SLIST)
            && (aParentType == aMatch)
            && (aAST.getFirstChild().getType() == TokenTypes.RCURLY);
    }
}
