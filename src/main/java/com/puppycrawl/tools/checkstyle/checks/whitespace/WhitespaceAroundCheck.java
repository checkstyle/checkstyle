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
 * Checks that a token is surrounded by whitespace.
 *
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
 *  {@link TokenTypes#DO_WHILE DO_WHILE},
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
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH},
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
 *  {@link TokenTypes#STAR_ASSIGN STAR_ASSIGN},
 *  {@link TokenTypes#LITERAL_ASSERT LITERAL_ASSERT},
 *  {@link TokenTypes#TYPE_EXTENSION_AND TYPE_EXTENSION_AND}.
 *
 * <p>
 * An example of how to configure the check is:
 *
 * <pre>
 * &lt;module name="WhitespaceAround"/&gt;
 * </pre>
 *
 * <p> An example of how to configure the check for whitespace only around
 * assignment operators is:
 *
 * <pre>
 * &lt;module name="WhitespaceAround"&gt;
 *     &lt;property name="tokens"
 *               value="ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,BAND_ASSIGN"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * In addition, this check can be configured to allow empty methods, types,
 * for, while, do-while loops and constructor bodies.
 * For example:
 *
 * <p>
 * <pre><code>
 * public MyClass() {}      // empty constructor
 * public void func() {}    // empty method
 * public interface Foo {} // empty interface
 * public class Foo {} // empty class
 * public enum Foo {} // empty enum
 * MyClass c = new MyClass() {}; // empty anonymous class
 * while (i = 1) {} // empty while loop
 * for (int i = 1; i &gt; 1; i++) {} // empty for loop
 * do {} while (i = 1); // empty do-while loop
 * public @interface Beta {} // empty annotation type
 * </code></pre>
 *
 * <p>
 * To configure the check to allow empty method blocks use
 *
 * <p>
 * <pre>   &lt;property name="allowEmptyMethods" value="true" /&gt;</pre>
 *
 * <p>
 * To configure the check to allow empty constructor blocks use
 *
 * <p>
 * <pre>   &lt;property name="allowEmptyConstructors" value="true" /&gt;</pre>
 *
 * <p>
 * To configure the check to allow empty type blocks use
 *
 * <p>
 * <pre>   &lt;property name="allowEmptyTypes" value="true" /&gt;</pre>
 *
 * <p>
 * To configure the check to allow empty loop blocks use
 *
 * <p>
 * <pre>   &lt;property name="allowEmptyLoops" value="true" /&gt;</pre>
 *
 *
 * <p>
 * Also, this check can be configured to ignore the colon in an enhanced for
 * loop. The colon in an enhanced for loop is ignored by default
 *
 * <p>
 * To configure the check to ignore the colon
 *
 * <p>
 * <pre>   &lt;property name="ignoreEnhancedForColon" value="true" /&gt;</pre>
 *
 *
 * @author Oliver Burn
 * @author maxvetrenko
 * @version 1.0
 */
public class WhitespaceAroundCheck extends Check
{
    /** Whether or not empty constructor bodies are allowed. */
    private boolean mAllowEmptyCtors;
    /** Whether or not empty method bodies are allowed. */
    private boolean mAllowEmptyMethods;
    /** Whether or not empty classes, enums and interfaces are allowed*/
    private boolean mAllowEmptyTypes;
    /** Whether or not empty loops are allowed*/
    private boolean mAllowEmptyLoops;
    /** whether or not to ignore a colon in a enhanced for loop */
    private boolean mIgnoreEnhancedForColon = true;

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
            TokenTypes.DO_WHILE,
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
            TokenTypes.LITERAL_SWITCH,
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
     * Sets whether or not empty method bodies are allowed.
     * @param aAllow <code>true</code> to allow empty method bodies.
     */
    public void setAllowEmptyMethods(boolean aAllow)
    {
        mAllowEmptyMethods = aAllow;
    }

    /**
     * Sets whether or not empty constructor bodies are allowed.
     * @param aAllow <code>true</code> to allow empty constructor bodies.
     */
    public void setAllowEmptyConstructors(boolean aAllow)
    {
        mAllowEmptyCtors = aAllow;
    }

    /**
     * Sets whether or not to ignore the whitespace around the
     * colon in an enhanced for loop.
     * @param aIgnore <code>true</code> to ignore enhanced for colon.
     */
    public void setIgnoreEnhancedForColon(boolean aIgnore)
    {
        mIgnoreEnhancedForColon = aIgnore;
    }

    /**
     * Sets whether or not empty type bodies are allowed.
     * @param aAllow <code>true</code> to allow empty type bodies.
     */
    public void setAllowEmptyTypes(boolean aAllow)
    {
        mAllowEmptyTypes = aAllow;
    }

    /**
     * Sets whether or not empty loop bodies are allowed.
     * @param aAllow <code>true</code> to allow empty loops bodies.
     */
    public void setAllowEmptyLoops(boolean aAllow)
    {
        mAllowEmptyLoops = aAllow;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final int currentType = aAST.getType();
        final int parentType = aAST.getParent().getType();

        // Check for CURLY in array initializer
        if (((currentType == TokenTypes.RCURLY)
                || (currentType == TokenTypes.LCURLY))
            && ((parentType == TokenTypes.ARRAY_INIT)
                || (parentType == TokenTypes.ANNOTATION_ARRAY_INIT)))
        {
            return;
        }

        // Check for import pkg.name.*;
        if ((currentType == TokenTypes.STAR)
            && (parentType == TokenTypes.DOT))
        {
            return;
        }

        // Check for an SLIST that has a parent CASE_GROUP. It is not a '{'.
        if ((currentType == TokenTypes.SLIST)
            && (parentType == TokenTypes.CASE_GROUP))
        {
            return;
        }

        if ((currentType == TokenTypes.COLON)) {
            //we do not want to check colon for cases and defaults
            if (parentType == TokenTypes.LITERAL_DEFAULT
                || parentType == TokenTypes.LITERAL_CASE)
            {
                return;
            }
            else if (parentType == TokenTypes.FOR_EACH_CLAUSE
                && this.mIgnoreEnhancedForColon)
            {
                return;
            }
        }

        // Checks if empty methods, ctors or loops are allowed.
        if (isEmptyMethodBlock(aAST, parentType)
                || isEmptyCtorBlock(aAST, parentType)
                || isEmptyLoop(aAST, parentType))
        {
            return;
        }

        // Checks if empty classes, interfaces or enums are allowed
        if (mAllowEmptyTypes && (isEmptyType(aAST, parentType))) {
            return;
        }

        final String line = getLine(aAST.getLineNo() - 1);
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
            && !((currentType == TokenTypes.LITERAL_RETURN)
                && (aAST.getFirstChild().getType() == TokenTypes.SEMI))
            // Check for "})" or "};" or "},". Happens with anon-inners
            && !((currentType == TokenTypes.RCURLY)
                && ((nextChar == ')')
                    || (nextChar == ';')
                    || (nextChar == ',')
                    || (nextChar == '.'))))
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
    private boolean isEmptyMethodBlock(DetailAST aAST, int aParentType)
    {
        return mAllowEmptyMethods
            && isEmptyBlock(aAST, aParentType, TokenTypes.METHOD_DEF);
    }

    /**
     * Test if the given <code>DetailAST</code> is part of an allowed empty
     * constructor (ctor) block.
     * @param aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         allowed empty constructor block.
     */
    private boolean isEmptyCtorBlock(DetailAST aAST, int aParentType)
    {
        return mAllowEmptyCtors
            && isEmptyBlock(aAST, aParentType, TokenTypes.CTOR_DEF);
    }

    /**
     *
     * @param aAST aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         allowed empty loop block.
     */
    private boolean isEmptyLoop(DetailAST aAST, int aParentType)
    {
        return mAllowEmptyLoops
            && (isEmptyBlock(aAST, aParentType, TokenTypes.LITERAL_FOR)
                    || isEmptyBlock(aAST,
                            aParentType, TokenTypes.LITERAL_WHILE)
                            || isEmptyBlock(aAST,
                                    aParentType, TokenTypes.LITERAL_DO));
    }

    /**
     * Test if the given <code>DetailAST</code> is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   class Foo {}</pre>
     * </p>
     *
     * @param aAST aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         empty block contained under a <code>aMatch</code> token type
     *         node.
     */
    private boolean isEmptyType(DetailAST aAST, int aParentType)
    {
        final int type = aAST.getType();
        if ((type == TokenTypes.RCURLY || type == TokenTypes.LCURLY)
                && aParentType == TokenTypes.OBJBLOCK)
        {
            final DetailAST typeNode = aAST.getParent().getParent();
            final int matchType = typeNode.getType();
            if (matchType == TokenTypes.CLASS_DEF
                    || matchType == TokenTypes.INTERFACE_DEF
                    || matchType == TokenTypes.ENUM_DEF
                    || matchType == TokenTypes.LITERAL_NEW
                    || matchType == TokenTypes.ANNOTATION_DEF)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if a given <code>DetailAST</code> is part of an empty block.
     * An example empty block might look like the following
     * <p>
     * <pre>   public void myMethod(int val) {}</pre>
     * </p>
     * In the above, the method body is an empty block ("{}").
     *
     * @param aAST the <code>DetailAST</code> to test.
     * @param aParentType the token type of <code>aAST</code>'s parent.
     * @param aMatch the parent token type we're looking to match.
     * @return <code>true</code> if <code>aAST</code> makes up part of an
     *         empty block contained under a <code>aMatch</code> token type
     *         node.
     */
    private boolean isEmptyBlock(DetailAST aAST, int aParentType, int aMatch)
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
