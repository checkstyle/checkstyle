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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This check calculates the Non Commenting Source Statements (NCSS) metric for
 * java source files and methods. The check adheres to the <a
 * href="http://www.kclee.com/clemens/java/javancss/">JavaNCSS specification
 * </a> and gives the same results as the JavaNCSS tool.
 *
 * The NCSS-metric tries to determine complexity of methods, classes and files
 * by counting the non commenting lines. Roughly said this is (nearly)
 * equivalent to counting the semicolons and opening curly braces.
 *
 * @author Lars Ködderitzsch
 */
public class JavaNCSSCheck extends Check
{
    /** default constant for max file ncss */
    private static final int FILE_MAX_NCSS = 2000;

    /** default constant for max file ncss */
    private static final int CLASS_MAX_NCSS = 1500;

    /** default constant for max method ncss */
    private static final int METHOD_MAX_NCSS = 50;

    /** maximum ncss for a complete source file */
    private int mFileMax = FILE_MAX_NCSS;

    /** maximum ncss for a class */
    private int mClassMax = CLASS_MAX_NCSS;

    /** maximum ncss for a method */
    private int mMethodMax = METHOD_MAX_NCSS;

    /** list containing the stacked counters */
    private FastStack<Counter> mCounters;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_CONTINUE,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.EXPR,
            TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return new int[]{
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_CONTINUE,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.EXPR,
            TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
        };
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mCounters = new FastStack<Counter>();

        //add a counter for the file
        mCounters.push(new Counter());
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final int tokenType = aAST.getType();

        if ((TokenTypes.CLASS_DEF == tokenType)
            || (TokenTypes.METHOD_DEF == tokenType)
            || (TokenTypes.CTOR_DEF == tokenType)
            || (TokenTypes.STATIC_INIT == tokenType)
            || (TokenTypes.INSTANCE_INIT == tokenType))
        {
            //add a counter for this class/method
            mCounters.push(new Counter());
        }

        //check if token is countable
        if (isCountable(aAST)) {
            //increment the stacked counters
            for (final Counter c : mCounters) {
                c.increment();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        final int tokenType = aAST.getType();
        if ((TokenTypes.METHOD_DEF == tokenType)
            || (TokenTypes.CTOR_DEF == tokenType)
            || (TokenTypes.STATIC_INIT == tokenType)
            || (TokenTypes.INSTANCE_INIT == tokenType))
        {
            //pop counter from the stack
            final Counter counter = mCounters.pop();

            final int count = counter.getCount();
            if (count > mMethodMax) {
                log(aAST.getLineNo(), aAST.getColumnNo(), "ncss.method",
                        count, mMethodMax);
            }
        }
        else if (TokenTypes.CLASS_DEF == tokenType) {
            //pop counter from the stack
            final Counter counter = mCounters.pop();

            final int count = counter.getCount();
            if (count > mClassMax) {
                log(aAST.getLineNo(), aAST.getColumnNo(), "ncss.class",
                        count, mClassMax);
            }
        }
    }

    @Override
    public void finishTree(DetailAST aRootAST)
    {
        //pop counter from the stack
        final Counter counter = mCounters.pop();

        final int count = counter.getCount();
        if (count > mFileMax) {
            log(aRootAST.getLineNo(), aRootAST.getColumnNo(), "ncss.file",
                    count, mFileMax);
        }
    }

    /**
     * Sets the maximum ncss for a file.
     *
     * @param aFileMax
     *            the maximum ncss
     */
    public void setFileMaximum(int aFileMax)
    {
        mFileMax = aFileMax;
    }

    /**
     * Sets the maximum ncss for a class.
     *
     * @param aClassMax
     *            the maximum ncss
     */
    public void setClassMaximum(int aClassMax)
    {
        mClassMax = aClassMax;
    }

    /**
     * Sets the maximum ncss for a method.
     *
     * @param aMethodMax
     *            the maximum ncss
     */
    public void setMethodMaximum(int aMethodMax)
    {
        mMethodMax = aMethodMax;
    }

    /**
     * Checks if a token is countable for the ncss metric
     *
     * @param aAST
     *            the AST
     * @return true if the token is countable
     */
    private boolean isCountable(DetailAST aAST)
    {
        boolean countable = true;

        final int tokenType = aAST.getType();

        //check if an expression is countable
        if (TokenTypes.EXPR == tokenType) {
            countable = isExpressionCountable(aAST);
        }
        //check if an variable definition is countable
        else if (TokenTypes.VARIABLE_DEF == tokenType) {
            countable = isVariableDefCountable(aAST);
        }
        return countable;
    }

    /**
     * Checks if a variable definition is countable.
     *
     * @param aAST the AST
     * @return true if the variable definition is countable, false otherwise
     */
    private boolean isVariableDefCountable(DetailAST aAST)
    {
        boolean countable = false;

        //count variable defs only if they are direct child to a slist or
        // object block
        final int parentType = aAST.getParent().getType();

        if ((TokenTypes.SLIST == parentType)
            || (TokenTypes.OBJBLOCK == parentType))
        {
            final DetailAST prevSibling = aAST.getPreviousSibling();

            //is countable if no previous sibling is found or
            //the sibling is no COMMA.
            //This is done because multiple assignment on one line are countes
            // as 1
            countable = (prevSibling == null)
                    || (TokenTypes.COMMA != prevSibling.getType());
        }

        return countable;
    }

    /**
     * Checks if an expression is countable for the ncss metric.
     *
     * @param aAST the AST
     * @return true if the expression is countable, false otherwise
     */
    private boolean isExpressionCountable(DetailAST aAST)
    {
        boolean countable = true;

        //count expressions only if they are direct child to a slist (method
        // body, for loop...)
        //or direct child of label,if,else,do,while,for
        final int parentType = aAST.getParent().getType();
        switch (parentType) {
        case TokenTypes.SLIST :
        case TokenTypes.LABELED_STAT :
        case TokenTypes.LITERAL_FOR :
        case TokenTypes.LITERAL_DO :
        case TokenTypes.LITERAL_WHILE :
        case TokenTypes.LITERAL_IF :
        case TokenTypes.LITERAL_ELSE :
            //don't count if or loop conditions
            final DetailAST prevSibling = aAST.getPreviousSibling();
            countable = (prevSibling == null)
                || (TokenTypes.LPAREN != prevSibling.getType());
            break;
        default :
            countable = false;
            break;
        }
        return countable;
    }

    /**
     * @author Lars Ködderitzsch
     *
     * Class representing a counter,
     */
    private static class Counter
    {
        /** the counters internal integer */
        private int mIvCount;

        /**
         * Increments the counter.
         */
        public void increment()
        {
            mIvCount++;
        }

        /**
         * Gets the counters value
         *
         * @return the counter
         */
        public int getCount()
        {
            return mIvCount;
        }
    }
}
