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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This check calculates the Non Commenting Source Statements (NCSS) metric for
 * java source files and methods. The check adheres to the <a
 * href="http://www.kclee.com/clemens/java/javancss">JavaNCSS specification
 * </a> and gives the same results as the JavaNCSS tool.
 *
 * <p>The NCSS-metric tries to determine complexity of methods, classes and files
 * by counting the non commenting lines. Roughly said this is (nearly)
 * equivalent to counting the semicolons and opening curly braces.
 *
 * @author Lars Ködderitzsch
 */
public class JavaNCSSCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_METHOD = "ncss.method";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CLASS = "ncss.class";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_FILE = "ncss.file";

    /** Default constant for max file ncss. */
    private static final int FILE_MAX_NCSS = 2000;

    /** Default constant for max file ncss. */
    private static final int CLASS_MAX_NCSS = 1500;

    /** Default constant for max method ncss. */
    private static final int METHOD_MAX_NCSS = 50;

    /** Maximum ncss for a complete source file. */
    private int fileMaximum = FILE_MAX_NCSS;

    /** Maximum ncss for a class. */
    private int classMaximum = CLASS_MAX_NCSS;

    /** Maximum ncss for a method. */
    private int methodMaximum = METHOD_MAX_NCSS;

    /** List containing the stacked counters. */
    private Deque<Counter> counters;

    @Override
    public int[] getDefaultTokens() {
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
    public int[] getRequiredTokens() {
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
    public int[] getAcceptableTokens() {
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
    public void beginTree(DetailAST rootAST) {
        counters = new ArrayDeque<>();

        //add a counter for the file
        counters.push(new Counter());
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int tokenType = ast.getType();

        if (tokenType == TokenTypes.CLASS_DEF
            || tokenType == TokenTypes.METHOD_DEF
            || tokenType == TokenTypes.CTOR_DEF
            || tokenType == TokenTypes.STATIC_INIT
            || tokenType == TokenTypes.INSTANCE_INIT) {
            //add a counter for this class/method
            counters.push(new Counter());
        }

        //check if token is countable
        if (isCountable(ast)) {
            //increment the stacked counters
            for (final Counter counter : counters) {
                counter.increment();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        final int tokenType = ast.getType();
        if (tokenType == TokenTypes.METHOD_DEF
            || tokenType == TokenTypes.CTOR_DEF
            || tokenType == TokenTypes.STATIC_INIT
            || tokenType == TokenTypes.INSTANCE_INIT) {
            //pop counter from the stack
            final Counter counter = counters.pop();

            final int count = counter.getCount();
            if (count > methodMaximum) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_METHOD,
                        count, methodMaximum);
            }
        }
        else if (tokenType == TokenTypes.CLASS_DEF) {
            //pop counter from the stack
            final Counter counter = counters.pop();

            final int count = counter.getCount();
            if (count > classMaximum) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_CLASS,
                        count, classMaximum);
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        //pop counter from the stack
        final Counter counter = counters.pop();

        final int count = counter.getCount();
        if (count > fileMaximum) {
            log(rootAST.getLineNo(), rootAST.getColumnNo(), MSG_FILE,
                    count, fileMaximum);
        }
    }

    /**
     * Sets the maximum ncss for a file.
     *
     * @param fileMaximum
     *            the maximum ncss
     */
    public void setFileMaximum(int fileMaximum) {
        this.fileMaximum = fileMaximum;
    }

    /**
     * Sets the maximum ncss for a class.
     *
     * @param classMaximum
     *            the maximum ncss
     */
    public void setClassMaximum(int classMaximum) {
        this.classMaximum = classMaximum;
    }

    /**
     * Sets the maximum ncss for a method.
     *
     * @param methodMaximum
     *            the maximum ncss
     */
    public void setMethodMaximum(int methodMaximum) {
        this.methodMaximum = methodMaximum;
    }

    /**
     * Checks if a token is countable for the ncss metric.
     *
     * @param ast
     *            the AST
     * @return true if the token is countable
     */
    private static boolean isCountable(DetailAST ast) {
        boolean countable = true;

        final int tokenType = ast.getType();

        //check if an expression is countable
        if (tokenType == TokenTypes.EXPR) {
            countable = isExpressionCountable(ast);
        }
        //check if an variable definition is countable
        else if (tokenType == TokenTypes.VARIABLE_DEF) {
            countable = isVariableDefCountable(ast);
        }
        return countable;
    }

    /**
     * Checks if a variable definition is countable.
     *
     * @param ast the AST
     * @return true if the variable definition is countable, false otherwise
     */
    private static boolean isVariableDefCountable(DetailAST ast) {
        boolean countable = false;

        //count variable definitions only if they are direct child to a slist or
        // object block
        final int parentType = ast.getParent().getType();

        if (parentType == TokenTypes.SLIST
            || parentType == TokenTypes.OBJBLOCK) {
            final DetailAST prevSibling = ast.getPreviousSibling();

            //is countable if no previous sibling is found or
            //the sibling is no COMMA.
            //This is done because multiple assignment on one line are counted
            // as 1
            countable = prevSibling == null
                    || prevSibling.getType() != TokenTypes.COMMA;
        }

        return countable;
    }

    /**
     * Checks if an expression is countable for the ncss metric.
     *
     * @param ast the AST
     * @return true if the expression is countable, false otherwise
     */
    private static boolean isExpressionCountable(DetailAST ast) {
        boolean countable;

        //count expressions only if they are direct child to a slist (method
        // body, for loop...)
        //or direct child of label,if,else,do,while,for
        final int parentType = ast.getParent().getType();
        switch (parentType) {
            case TokenTypes.SLIST :
            case TokenTypes.LABELED_STAT :
            case TokenTypes.LITERAL_FOR :
            case TokenTypes.LITERAL_DO :
            case TokenTypes.LITERAL_WHILE :
            case TokenTypes.LITERAL_IF :
            case TokenTypes.LITERAL_ELSE :
                //don't count if or loop conditions
                final DetailAST prevSibling = ast.getPreviousSibling();
                countable = prevSibling == null
                    || prevSibling.getType() != TokenTypes.LPAREN;
                break;
            default :
                countable = false;
                break;
        }
        return countable;
    }

    /**
     * Class representing a counter.
     *
     * @author Lars Ködderitzsch
     */
    private static class Counter {
        /** The counters internal integer. */
        private int count;

        /**
         * Increments the counter.
         */
        public void increment() {
            count++;
        }

        /**
         * Gets the counters value.
         *
         * @return the counter
         */
        public int getCount() {
            return count;
        }
    }
}
