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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Restricts the number of return statements in methods, constructors and lambda expressions
 * (2 by default). Ignores specified methods ({@code equals()} by default).
 * </p>
 * <p>
 * <b>max</b> property will only check returns in methods and lambdas that
 * return a specific value (Ex: 'return 1;').
 * </p>
 * <p>
 * <b>maxForVoid</b> property will only check returns in methods, constructors,
 * and lambdas that have no return type (IE 'return;'). It will only count
 * visible return statements. Return statements not normally written, but
 * implied, at the end of the method/constructor definition will not be taken
 * into account. To disallow "return;" in void return type methods, use a value
 * of 0.
 * </p>
 * <p>
 * Rationale: Too many return points can be indication that code is
 * attempting to do too much or may be difficult to understand.
 * </p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class ReturnCountCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "return.count";

    /** Stack of method contexts. */
    private final Deque<Context> contextStack = new ArrayDeque<>();

    /** The format string of the regexp. */
    private String format = "^equals$";
    /** The regexp to match against. */
    private Pattern regexp = Pattern.compile(format);

    /** Maximum allowed number of return statements. */
    private int max = 2;
    /** Maximum allowed number of return statements for void methods. */
    private int maxForVoid = 1;
    /** Current method context. */
    private Context context;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_RETURN,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_RETURN};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_RETURN,
        };
    }

    /**
     * Set the format to the specified regular expression.
     * @param format a {@code String} value
     * @throws org.apache.commons.beanutils.ConversionException unable to parse format
     */
    public void setFormat(String format) {
        this.format = format;
        regexp = CommonUtils.createPattern(format);
    }

    /**
     * Setter for max property.
     * @param max maximum allowed number of return statements.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Setter for maxForVoid property.
     * @param maxForVoid maximum allowed number of return statements for void methods.
     */
    public void setMaxForVoid(int maxForVoid) {
        this.maxForVoid = maxForVoid;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        context = new Context(false);
        contextStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                visitMethodDef(ast);
                break;
            case TokenTypes.LAMBDA:
                visitLambda();
                break;
            case TokenTypes.LITERAL_RETURN:
                visitReturn(ast);
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.LAMBDA:
                leave(ast);
                break;
            case TokenTypes.LITERAL_RETURN:
                // Do nothing
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Creates new method context and places old one on the stack.
     * @param ast method definition for check.
     */
    private void visitMethodDef(DetailAST ast) {
        contextStack.push(context);
        final DetailAST methodNameAST = ast.findFirstToken(TokenTypes.IDENT);
        final boolean check = !regexp.matcher(methodNameAST.getText()).find();
        context = new Context(check);
    }

    /**
     * Checks number of return statements and restore previous context.
     * @param ast node to leave.
     */
    private void leave(DetailAST ast) {
        context.checkCount(ast);
        context = contextStack.pop();
    }

    /**
     * Creates new lambda context and places old one on the stack.
     */
    private void visitLambda() {
        contextStack.push(context);
        context = new Context(true);
    }

    /**
     * Examines the return statement and tells context about it.
     * @param ast return statement to check.
     */
    private void visitReturn(DetailAST ast) {
        // we can't identify which max to use for lambdas, so we can only assign
        // after the first return statement is seen
        if (ast.getFirstChild().getType() == TokenTypes.SEMI) {
            context.visitLiteralReturn(maxForVoid);
        }
        else {
            context.visitLiteralReturn(max);
        }
    }

    /**
     * Class to encapsulate information about one method.
     * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
     */
    private class Context {
        /** Whether we should check this method or not. */
        private final boolean checking;
        /** Counter for return statements. */
        private int count;
        /** Maximum allowed number of return statements. */
        private Integer maxAllowed;

        /**
         * Creates new method context.
         * @param checking should we check this method or not.
         */
        Context(boolean checking) {
            this.checking = checking;
        }

        /**
         * Increase the number of return statements.
         * @param maxAssigned Maximum allowed number of return statements.
         */
        public void visitLiteralReturn(int maxAssigned) {
            if (maxAllowed == null) {
                maxAllowed = maxAssigned;
            }

            ++count;
        }

        /**
         * Checks if number of return statements in the method are more
         * than allowed.
         * @param ast method def associated with this context.
         */
        public void checkCount(DetailAST ast) {
            if (checking && maxAllowed != null && count > maxAllowed) {
                log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY, count, maxAllowed);
            }
        }
    }
}
