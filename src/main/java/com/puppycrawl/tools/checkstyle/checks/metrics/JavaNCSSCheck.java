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
 *
 * <p>
 * Here is a breakdown of what exactly is counted and not counted:
 * </p>
 * <div class="wrapper">
 * <table>
 * <caption>JavaNCSS metrics</caption>
 * <thead><tr><th>Structure</th><th>NCSS Count</th><th>Notes</th></tr></thead>
 * <tbody>
 * <tr><td>Package declaration</td><td>1</td>
 * <td>Counted at the terminating semicolon.</td></tr>
 * <tr><td>Import declaration</td><td>1</td>
 * <td>Each single, static, or wildcard import counts as 1.</td></tr>
 * <tr><td>Class, Interface, Annotation ({@code @interface})</td><td>1</td>
 * <td>Counted at the opening curly brace of the body.</td></tr>
 * <tr><td>Method, Constructor</td><td>1</td>
 * <td>Counted at the declaration.</td></tr>
 * <tr><td>Static initializer, Instance initializer</td><td>1</td>
 * <td>Both {@code static {}} and bare {@code {}} initializer blocks count as 1.</td></tr>
 * <tr><td>Annotation type member</td><td>1</td>
 * <td>Each method-like member declaration inside {@code @interface} counts as 1.
 * A standalone {@code ;} inside {@code @interface} also counts as 1.</td></tr>
 * <tr><td>Variable declaration</td><td>1</td>
 * <td>1 per statement regardless of how many variables are declared on that line.
 * {@code int x, y;} counts as 1.</td></tr>
 * <tr><td>{@code if}</td><td>1</td>
 * <td>The {@code if} keyword counts as 1.</td></tr>
 * <tr><td>{@code else}, {@code else if}</td><td>1</td>
 * <td>The {@code else} keyword counts as 1, separate from the {@code if} count.</td></tr>
 * <tr><td>{@code while}, {@code do}, {@code for}</td><td>1</td>
 * <td>The keyword header counts as 1.</td></tr>
 * <tr><td>{@code switch}</td><td>1</td>
 * <td>The {@code switch} keyword counts as 1.</td></tr>
 * <tr><td>{@code case}, {@code default}</td><td>1</td>
 * <td>Every case and default label adds 1.</td></tr>
 * <tr><td>{@code try}</td><td>0</td>
 * <td>The {@code try} keyword itself does not count.</td></tr>
 * <tr><td>{@code catch}</td><td>1</td>
 * <td>Each catch block counts as 1.</td></tr>
 * <tr><td>{@code finally}</td><td>1</td>
 * <td>The finally block counts as 1.</td></tr>
 * <tr><td>{@code synchronized}</td><td>1</td>
 * <td>The synchronized statement counts as 1.</td></tr>
 * <tr><td>{@code return}, {@code break}, {@code continue}, {@code throw}</td><td>1</td>
 * <td>Each counts as 1.</td></tr>
 * <tr><td>{@code assert}</td><td>1</td>
 * <td>Each assert statement counts as 1, with or without a message expression.</td></tr>
 * <tr><td>Labeled statement</td><td>1</td>
 * <td>{@code label: statement} counts as 1.</td></tr>
 * <tr><td>Explicit constructor invocation</td><td>1</td>
 * <td>{@code this()} or {@code super()} calls inside a constructor body
 * each count as 1.</td></tr>
 * <tr><td>Expression statements (assignments, method calls)</td><td>1</td>
 * <td>Statement-level expressions terminated by {@code ;} count as 1.
 * A method call inside a {@code return} does not add an extra count.</td></tr>
 * <tr><td>Empty blocks {}</td><td>0</td>
 * <td>Empty curly braces do not increase the count.</td></tr>
 * <tr><td>Empty statements ;</td><td>0</td>
 * <td>Standalone semicolons outside of {@code @interface} do not increase
 * the count.</td></tr>
 * </tbody>
 * </table>
 * </div>
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

        // count expressions only if they are direct child to a slist (method
        // body, for loop...)
        // or direct child of label,if,else,do,while,for
        final int parentType = ast.getParent().getType();
        return switch (parentType) {
            case TokenTypes.SLIST, TokenTypes.LABELED_STAT, TokenTypes.LITERAL_FOR,
                 TokenTypes.LITERAL_DO,
                 TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE -> {
                // don't count if or loop conditions
                final DetailAST prevSibling = ast.getPreviousSibling();
                yield prevSibling == null
                        || prevSibling.getType() != TokenTypes.LPAREN;
            }
            default -> false;
        };
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
        /* package */ void increment() {
            count++;
        }

        /**
         * Gets the counters value.
         *
         * @return the counter
         */
        /* package */ int getCount() {
            return count;
        }

    }

}
