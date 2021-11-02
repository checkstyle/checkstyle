////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a local variable is declared and/or assigned, but not used.
 * Doesn't check
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.30.1">
 * pattern variables</a>.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnusedLocalVariable&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *
 *     int a;
 *
 *     {
 *         int k = 12; // violation
 *         k++;
 *     }
 *
 *     Test(int a) { // ok as 'a' is a method parameter not a local variable
 *         this.a = 12;
 *     }
 *
 *     void method(int b) {
 *         int a = 10; // violation
 *         int[] arr = {1, 2, 3}; // violation
 *         int[] anotherArr = {1}; // ok
 *         anotherArr[0] = 4;
 *     }
 *
 *     String convertValue(String newValue) {
 *         String s = newValue.toLowerCase(); // violation
 *         return newValue.toLowerCase();
 *     }
 *
 *     void read() throws IOException {
 *         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 *         String s; // violation
 *         while ((s = reader.readLine()) != null) {
 *         }
 *         try (BufferedReader reader1 = new BufferedReader(new FileReader("abc.txt"))) { // ok
 *         }
 *         try {
 *         } catch (Exception e) { // ok as e is an exception parameter
 *         }
 *     }
 *
 *     void loops() {
 *         int j = 12;
 *         for (int i = 0; j &lt; 11; i++) { // violation, unused local variable 'i'.
 *         }
 *         for (int p = 0; j &lt; 11; p++) // ok
 *             p /= 2;
 *     }
 *
 *     void lambdas() {
 *         Predicate&lt;String&gt; obj = (String str) -&gt; { // ok as 'str' is a lambda parameter
 *             return true;
 *         };
 *         obj.test("test");
 *     }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unused.local.var}
 * </li>
 * </ul>
 *
 * @since 9.2
 */
@FileStatefulCheck
public class UnusedLocalVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_LOCAL_VARIABLE = "unused.local.var";

    /**
     * An array of increment and decrement tokens.
     */
    private static final int[] INCREMENT_AND_DECREMENT_TOKENS = {
        TokenTypes.POST_INC,
        TokenTypes.POST_DEC,
        TokenTypes.INC,
        TokenTypes.DEC,
    };

    /**
     * An array of scope tokens.
     */
    private static final int[] SCOPES = {
        TokenTypes.SLIST,
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_WHILE,
    };

    /**
     * An array of unacceptable children of ast of type {@link TokenTypes#DOT}.
     */
    private static final int[] UNACCEPTABLE_CHILD_OF_DOT = {
        TokenTypes.DOT,
        TokenTypes.METHOD_CALL,
        TokenTypes.LITERAL_NEW,
        TokenTypes.LITERAL_SUPER,
        TokenTypes.LITERAL_CLASS,
        TokenTypes.LITERAL_THIS,
    };

    /**
     * An array of unacceptable parent of ast of type {@link TokenTypes#IDENT}.
     */
    private static final int[] UNACCEPTABLE_PARENT_OF_IDENT = {
        TokenTypes.VARIABLE_DEF,
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.PATTERN_VARIABLE_DEF,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * Keeps VariableDesc objects inside stack of declared variables.
     */
    private Deque<VariableDesc> variables;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST root) {
        variables = new ArrayDeque<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int type = ast.getType();
        if (type == TokenTypes.DOT) {
            visitDotToken(ast);
        }
        else if (type == TokenTypes.VARIABLE_DEF) {
            visitVariableDefToken(ast);
        }
        else if (type == TokenTypes.IDENT) {
            visitIdentToken(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast, SCOPES)) {
            while (!variables.isEmpty() && variables.peek().getScope() == ast) {
                final VariableDesc variableDesc = variables.pop();
                if (!variableDesc.isUsed()) {
                    final DetailAST typeAst = variableDesc.getTypeAst();
                    log(typeAst, MSG_UNUSED_LOCAL_VARIABLE, variableDesc.name);
                }
            }
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#DOT}.
     *
     * @param dotAst dotAst
     */
    private void visitDotToken(DetailAST dotAst) {
        if (shouldCheckIdentifierAst(dotAst)) {
            checkIdentifierAst(dotAst.findFirstToken(TokenTypes.IDENT));
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#VARIABLE_DEF}.
     *
     * @param varDefAst varDefAst
     */
    private void visitVariableDefToken(DetailAST varDefAst) {
        if (varDefAst.getParent().getType() != TokenTypes.OBJBLOCK) {
            final DetailAST ident = varDefAst.findFirstToken(TokenTypes.IDENT);
            final VariableDesc desc = new VariableDesc(ident.getText(),
                    varDefAst.findFirstToken(TokenTypes.TYPE), findScopeOfVariable(varDefAst));
            variables.push(desc);
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}.
     *
     * @param identAst identAst
     */
    private void visitIdentToken(DetailAST identAst) {
        final DetailAST parentAst = identAst.getParent();
        if (!TokenUtil.isOfType(parentAst, UNACCEPTABLE_PARENT_OF_IDENT)
                && shouldCheckIdentifierAst(identAst)) {
            checkIdentifierAst(identAst);
        }
    }

    /**
     * Whether to check identifier ast or not.
     *
     * @param ast ast of type {@link TokenTypes#IDENT} or {@link TokenTypes#DOT}
     * @return true if ident ast should be checked else false
     */
    public static boolean shouldCheckIdentifierAst(DetailAST ast) {

        boolean result = false;
        if (ast.getType() == TokenTypes.IDENT) {
            final DetailAST parent = ast.getParent();
            if (parent.getType() != TokenTypes.METHOD_REF
                    || parent.getFirstChild() == ast
                    && parent.getLastChild().getType() != TokenTypes.LITERAL_NEW) {
                result = true;
            }
        }
        else {
            result = !TokenUtil.findFirstTokenByPredicate(ast,
                            childAst -> {
                                return TokenUtil.isOfType(childAst,
                                        UNACCEPTABLE_CHILD_OF_DOT);
                            })
                    .isPresent();
        }
        return result;
    }

    /**
     * Checks the identifier ast.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     */
    private void checkIdentifierAst(DetailAST identAst) {
        for (VariableDesc variableDesc : variables) {
            if (identAst.getText().equals(variableDesc.getName())
                    && !isLeftHandSideValue(identAst)) {
                variableDesc.registerAsUsed();
                break;
            }
        }
    }

    /**
     * Find the scope of variable.
     *
     * @param variableDef DetailAst of type {@link TokenTypes#VARIABLE_DEF}
     * @return scope of variableDef
     */
    private static DetailAST findScopeOfVariable(DetailAST variableDef) {
        final DetailAST result;
        final DetailAST parentAst = variableDef.getParent();
        if (parentAst.getType() == TokenTypes.SLIST) {
            result = parentAst;
        }
        else {
            final DetailAST grandParent = parentAst.getParent();
            final DetailAST scopeAst = grandParent.findFirstToken(TokenTypes.SLIST);
            if (scopeAst == null) {
                result = grandParent;
            }
            else {
                result = scopeAst;
            }
        }
        return result;
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is
     * used as left-hand side value. An identifier is being used as a left-hand side
     * value if it is used as the left operand of an assignment or as an
     * operand of a stand-alone increment
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return True if identAst is used as a left-hand side value otherwise false.
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return isStandAloneIncrement(identAst)
                || parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is used as
     * an operand of a stand-alone increment.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return True if identAst is used as an operand of stand-alone increment otherwise false
     */
    private static boolean isStandAloneIncrement(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        final DetailAST grandParent = parent.getParent();
        return TokenUtil.isOfType(parent, INCREMENT_AND_DECREMENT_TOKENS)
                && TokenUtil.isOfType(grandParent, TokenTypes.EXPR)
                && (!TokenUtil.isOfType(grandParent.getParent(),
                TokenTypes.ELIST, TokenTypes.INDEX_OP, TokenTypes.ASSIGN)
                || grandParent.getParent().getParent().getType() == TokenTypes.FOR_ITERATOR);
    }

    /**
     * Maintains information about the local variable.
     */
    private static final class VariableDesc {

        /**
         * The name of the variable.
         */
        private final String name;

        /**
         * DetailAst of type {@link TokenTypes#TYPE}.
         */
        private final DetailAST typeAst;

        /**
         * The scope of variable is determined by the DetailAst of type
         * {@link TokenTypes#SLIST} which is enclosing the variable.
         */
        private final DetailAST scope;

        /**
         * Is the variable used again.
         */
        private boolean used;

        /**
         * Create a new VariableDesc instance.
         *
         * @param name name of the variable
         * @param typeAst DetailAst of type {@link TokenTypes#TYPE}
         * @param scope {@link VariableDesc#scope}
         */
        /* package */ VariableDesc(String name, DetailAST typeAst, DetailAST scope) {
            this.name = name;
            this.typeAst = typeAst;
            this.scope = scope;
        }

        /**
         * Get the name of variable.
         *
         * @return name of variable
         */
        public String getName() {
            return name;
        }

        /**
         * Get the associated {@link VariableDesc#typeAst}.
         *
         * @return {@link VariableDesc#typeAst}
         */
        public DetailAST getTypeAst() {
            return typeAst;
        }

        /**
         * Get the associated {@link VariableDesc#scope}.
         *
         * @return {@link VariableDesc#scope}.
         */
        public DetailAST getScope() {
            return scope;
        }

        /**
         * Is the variable used again or not.
         *
         * @return true if variable is used again else false;
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Register the variable as used again.
         */
        public void registerAsUsed() {
            used = true;
        }
    }
}
