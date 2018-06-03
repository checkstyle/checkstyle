////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * Restricts nested boolean operators (&amp;&amp;, ||, &amp;, | and ^) to
 * a specified depth (default = 3).
 * Note: &amp;, | and ^ are not checked if they are part of constructor or
 * method call because they can be applied to non boolean variables and
 * Checkstyle does not know types of methods from different classes.
 *
 */
@FileStatefulCheck
public final class BooleanExpressionComplexityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "booleanExpressionComplexity";

    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 3;

    /** Stack of contexts. */
    private final Deque<Context> contextStack = new ArrayDeque<>();
    /** Maximum allowed complexity. */
    private int max;
    /** Current context. */
    private Context context = new Context(false);

    /** Creates new instance of the check. */
    public BooleanExpressionComplexityCheck() {
        max = DEFAULT_MAX;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.LAND,
            TokenTypes.BAND,
            TokenTypes.LOR,
            TokenTypes.BOR,
            TokenTypes.BXOR,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.LAND,
            TokenTypes.BAND,
            TokenTypes.LOR,
            TokenTypes.BOR,
            TokenTypes.BXOR,
        };
    }

    /**
     * Setter for maximum allowed complexity.
     * @param max new maximum allowed complexity.
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                visitMethodDef(ast);
                break;
            case TokenTypes.EXPR:
                visitExpr();
                break;
            case TokenTypes.BOR:
                if (!isPipeOperator(ast) && !isPassedInParameter(ast)) {
                    context.visitBooleanOperator();
                }
                break;
            case TokenTypes.BAND:
            case TokenTypes.BXOR:
                if (!isPassedInParameter(ast)) {
                    context.visitBooleanOperator();
                }
                break;
            case TokenTypes.LAND:
            case TokenTypes.LOR:
                context.visitBooleanOperator();
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + ast);
        }
    }

    /**
     * Checks if logical operator is part of constructor or method call.
     * @param logicalOperator logical operator
     * @return true if logical operator is part of constructor or method call
     */
    private static boolean isPassedInParameter(DetailAST logicalOperator) {
        return logicalOperator.getParent().getType() == TokenTypes.EXPR
            && logicalOperator.getParent().getParent().getType() == TokenTypes.ELIST;
    }

    /**
     * Checks if {@link TokenTypes#BOR binary OR} is applied to exceptions
     * in
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20">
     * multi-catch</a> (pipe-syntax).
     * @param binaryOr {@link TokenTypes#BOR binary or}
     * @return true if binary or is applied to exceptions in multi-catch.
     */
    private static boolean isPipeOperator(DetailAST binaryOr) {
        return binaryOr.getParent().getType() == TokenTypes.TYPE;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                leaveMethodDef();
                break;
            case TokenTypes.EXPR:
                leaveExpr(ast);
                break;
            default:
                // Do nothing
        }
    }

    /**
     * Creates new context for a given method.
     * @param ast a method we start to check.
     */
    private void visitMethodDef(DetailAST ast) {
        contextStack.push(context);
        final boolean check = !CheckUtil.isEqualsMethod(ast);
        context = new Context(check);
    }

    /** Removes old context. */
    private void leaveMethodDef() {
        context = contextStack.pop();
    }

    /** Creates and pushes new context. */
    private void visitExpr() {
        contextStack.push(context);
        context = new Context(context.isChecking());
    }

    /**
     * Restores previous context.
     * @param ast expression we leave.
     */
    private void leaveExpr(DetailAST ast) {
        context.checkCount(ast);
        context = contextStack.pop();
    }

    /**
     * Represents context (method/expression) in which we check complexity.
     *
     */
    private class Context {

        /**
         * Should we perform check in current context or not.
         * Usually false if we are inside equals() method.
         */
        private final boolean checking;
        /** Count of boolean operators. */
        private int count;

        /**
         * Creates new instance.
         * @param checking should we check in current context or not.
         */
        Context(boolean checking) {
            this.checking = checking;
            count = 0;
        }

        /**
         * Getter for checking property.
         * @return should we check in current context or not.
         */
        public boolean isChecking() {
            return checking;
        }

        /** Increases operator counter. */
        public void visitBooleanOperator() {
            ++count;
        }

        /**
         * Checks if we violates maximum allowed complexity.
         * @param ast a node we check now.
         */
        public void checkCount(DetailAST ast) {
            if (checking && count > max) {
                final DetailAST parentAST = ast.getParent();

                log(parentAST, MSG_KEY, count, max);
            }
        }

    }

}
