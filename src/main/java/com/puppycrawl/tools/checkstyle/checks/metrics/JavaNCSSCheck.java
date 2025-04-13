///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Determines complexity of methods, classes and files by counting
 * the Non Commenting Source Statements (NCSS). This check adheres to the
 * <a href="http://www.kclee.de/clemens/java/javancss/#specification">specification</a>
 * for the <a href="http://www.kclee.de/clemens/java/javancss/">JavaNCSS-Tool</a>
 * written by <b>Chr. Clemens Lee</b>.
 * </div>
 *
 * <p>
 * Roughly said the NCSS metric is calculated by counting the source lines which are
 * not comments, (nearly) equivalent to counting the semicolons and opening curly braces.
 * </p>
 *
 * <p>
 * The NCSS for a class is summarized from the NCSS of all its methods, the NCSS
 * of its nested classes and the number of member variable declarations.
 * </p>
 *
 * <p>
 * The NCSS for a file is summarized from the ncss of all its top level classes,
 * the number of imports and the package declaration.
 * </p>
 *
 * <p>
 * Rationale: Too large methods and classes are hard to read and costly to maintain.
 * A large NCSS number often means that a method or class has too many responsibilities
 * and/or functionalities which should be decomposed into smaller units.
 * </p>
 * <ul>
 * <li>
 * Property {@code classMaximum} - Specify the maximum allowed number of
 * non commenting lines in a class.
 * Type is {@code int}.
 * Default value is {@code 1500}.
 * </li>
 * <li>
 * Property {@code fileMaximum} - Specify the maximum allowed number of
 * non commenting lines in a file including all top level and nested classes.
 * Type is {@code int}.
 * Default value is {@code 2000}.
 * </li>
 * <li>
 * Property {@code methodMaximum} - Specify the maximum allowed number of
 * non commenting lines in a method.
 * Type is {@code int}.
 * Default value is {@code 50}.
 * </li>
 * <li>
 * Property {@code recordMaximum} - Specify the maximum allowed number of
 * non commenting lines in a record.
 * Type is {@code int}.
 * Default value is {@code 150}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code ncss.class}
 * </li>
 * <li>
 * {@code ncss.file}
 * </li>
 * <li>
 * {@code ncss.method}
 * </li>
 * <li>
 * {@code ncss.record}
 * </li>
 * </ul>
 *
 * @since 3.5
 */
// -@cs[AbbreviationAsWordInName] We can not change it as,
// check's name is a part of API (used in configurations).
@FileStatefulCheck
public class JavaNCSSCheck extends AbstractCheck {

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
    public static final String MSG_RECORD = "ncss.record";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_FILE = "ncss.file";

    /** Default constant for max file ncss. */
    private static final int FILE_MAX_NCSS = 2000;

    /** Default constant for max file ncss. */
    private static final int CLASS_MAX_NCSS = 1500;

    /** Default constant for max record ncss. */
    private static final int RECORD_MAX_NCSS = 150;

    /** Default constant for max method ncss. */
    private static final int METHOD_MAX_NCSS = 50;

    /**
     * Specify the maximum allowed number of non commenting lines in a file
     * including all top level and nested classes.
     */
    private int fileMaximum = FILE_MAX_NCSS;

    /** Specify the maximum allowed number of non commenting lines in a class. */
    private int classMaximum = CLASS_MAX_NCSS;

    /** Specify the maximum allowed number of non commenting lines in a record. */
    private int recordMaximum = RECORD_MAX_NCSS;

    /** Specify the maximum allowed number of non commenting lines in a method. */
    private int methodMaximum = METHOD_MAX_NCSS;

    /** List containing the stacked counters. */
    private Deque<Counter> counters;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        counters = new ArrayDeque<>();

        // add a counter for the file
        counters.push(new Counter());
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int tokenType = ast.getType();

        if (tokenType == TokenTypes.CLASS_DEF
            || tokenType == TokenTypes.RECORD_DEF
            || isMethodOrCtorOrInitDefinition(tokenType)) {
            // add a counter for this class/method
            counters.push(new Counter());
        }

        // check if token is countable
        if (isCountable(ast)) {
            // increment the stacked counters
            counters.forEach(Counter::increment);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        final int tokenType = ast.getType();

        if (isMethodOrCtorOrInitDefinition(tokenType)) {
            // pop counter from the stack
            final Counter counter = counters.pop();

            final int count = counter.getCount();
            if (count > methodMaximum) {
                log(ast, MSG_METHOD, count, methodMaximum);
            }
        }
        else if (tokenType == TokenTypes.CLASS_DEF) {
            // pop counter from the stack
            final Counter counter = counters.pop();

            final int count = counter.getCount();
            if (count > classMaximum) {
                log(ast, MSG_CLASS, count, classMaximum);
            }
        }
        else if (tokenType == TokenTypes.RECORD_DEF) {
            // pop counter from the stack
            final Counter counter = counters.pop();

            final int count = counter.getCount();
            if (count > recordMaximum) {
                log(ast, MSG_RECORD, count, recordMaximum);
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // pop counter from the stack
        final Counter counter = counters.pop();

        final int count = counter.getCount();
        if (count > fileMaximum) {
            log(rootAST, MSG_FILE, count, fileMaximum);
        }
    }

    /**
     * Setter to specify the maximum allowed number of non commenting lines
     * in a file including all top level and nested classes.
     *
     * @param fileMaximum
     *            the maximum ncss
     * @since 3.5
     */
    public void setFileMaximum(int fileMaximum) {
        this.fileMaximum = fileMaximum;
    }

    /**
     * Setter to specify the maximum allowed number of non commenting lines in a class.
     *
     * @param classMaximum
     *            the maximum ncss
     * @since 3.5
     */
    public void setClassMaximum(int classMaximum) {
        this.classMaximum = classMaximum;
    }

    /**
     * Setter to specify the maximum allowed number of non commenting lines in a record.
     *
     * @param recordMaximum
     *            the maximum ncss
     * @since 8.36
     */
    public void setRecordMaximum(int recordMaximum) {
        this.recordMaximum = recordMaximum;
    }

    /**
     * Setter to specify the maximum allowed number of non commenting lines in a method.
     *
     * @param methodMaximum
     *            the maximum ncss
     * @since 3.5
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

        // check if an expression is countable
        if (tokenType == TokenTypes.EXPR) {
            countable = isExpressionCountable(ast);
        }
        // check if a variable definition is countable
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

        // count variable definitions only if they are direct child to a slist or
        // object block
        final int parentType = ast.getParent().getType();

        if (parentType == TokenTypes.SLIST
            || parentType == TokenTypes.OBJBLOCK) {
            final DetailAST prevSibling = ast.getPreviousSibling();

            // is countable if no previous sibling is found or
            // the sibling is no COMMA.
            // This is done because multiple assignment on one line are counted
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
        final boolean countable;

        // count expressions only if they are direct child to a slist (method
        // body, for loop...)
        // or direct child of label,if,else,do,while,for
        final int parentType = ast.getParent().getType();
        switch (parentType) {
            case TokenTypes.SLIST:
            case TokenTypes.LABELED_STAT:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.LITERAL_ELSE:
                // don't count if or loop conditions
                final DetailAST prevSibling = ast.getPreviousSibling();
                countable = prevSibling == null
                    || prevSibling.getType() != TokenTypes.LPAREN;
                break;
            default:
                countable = false;
                break;
        }
        return countable;
    }

    /**
     * Checks if a token is a method, constructor, or compact constructor definition.
     *
     * @param tokenType the type of token we are checking
     * @return true if token type is method or ctor definition, false otherwise
     */
    private static boolean isMethodOrCtorOrInitDefinition(int tokenType) {
        return tokenType == TokenTypes.METHOD_DEF
                || tokenType == TokenTypes.COMPACT_CTOR_DEF
                || tokenType == TokenTypes.CTOR_DEF
                || tokenType == TokenTypes.STATIC_INIT
                || tokenType == TokenTypes.INSTANCE_INIT;
    }

    /**
     * Class representing a counter.
     *
     */
    private static final class Counter {

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
