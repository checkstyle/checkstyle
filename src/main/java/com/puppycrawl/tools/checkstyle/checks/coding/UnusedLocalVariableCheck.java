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
 * Doesn't support
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.30">
 * pattern variables yet</a>.
 * Doesn't check
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-4.html#jls-4.12.3">
 * array components</a> as array
 * components are classified as different kind of variables by
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/index.html">JLS</a>.
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
 *         int k = 12; // violation, assigned and updated but never used
 *         k++;
 *     }
 *
 *     Test(int a) {   // ok as 'a' is a constructor parameter not a local variable
 *         this.a = 12;
 *     }
 *
 *     void method(int b) {
 *         int a = 10;             // violation
 *         int[] arr = {1, 2, 3};  // violation
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
 *         try (BufferedReader reader1 // ok as 'reader1' is a resource and resources are closed
 *                                     // at the end of the statement
 *             = new BufferedReader(new FileReader("abc.txt"))) {
 *         }
 *         try {
 *         } catch (Exception e) {     // ok as e is an exception parameter
 *         }
 *     }
 *
 *     void loops() {
 *         int j = 12;
 *         for (int i = 0; j &lt; 11; i++) { // violation, unused local variable 'i'.
 *         }
 *         for (int p = 0; j &lt; 11; p++)   // ok
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
 * @since 9.3
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
        TokenTypes.LITERAL_FOR,
        TokenTypes.OBJBLOCK,
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
    private final Deque<VariableDesc> variables;

    /**
     * Creates a new {@code UnusedLocalVariableCheck} instance.
     */
    public UnusedLocalVariableCheck() {
        variables = new ArrayDeque<>();
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_FOR,
            TokenTypes.OBJBLOCK,
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
        variables.clear();
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
                if (!variableDesc.isUsed()
                        && !variableDesc.isInstVarInLocalAnonInnerClass()) {
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
        if (shouldCheckIdentTokenNestedUnderDot(dotAst)) {
            checkIdentifierAst(dotAst.findFirstToken(TokenTypes.IDENT));
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#VARIABLE_DEF}.
     *
     * @param varDefAst varDefAst
     */
    private void visitVariableDefToken(DetailAST varDefAst) {
        final boolean isInstanceVarInLocalAnonymousInnerClass =
                isInstVarInLocalAnonInnerClass(varDefAst);
        if (isInstanceVarInLocalAnonymousInnerClass
                || varDefAst.getParent().getType() != TokenTypes.OBJBLOCK) {
            final DetailAST ident = varDefAst.findFirstToken(TokenTypes.IDENT);
            final VariableDesc desc = new VariableDesc(ident.getText(),
                    varDefAst.findFirstToken(TokenTypes.TYPE), findScopeOfVariable(varDefAst),
                    isInstanceVarInLocalAnonymousInnerClass);
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
                && shouldCheckIdentWithMethodRefParent(identAst)) {
            checkIdentifierAst(identAst);
        }
    }

    /**
     * Whether DetailAst node of type {@link TokenTypes#VARIABLE_DEF} is an instance variable
     * in a local anonymous inner class. Though this check is for local variables, storing
     * above-mentioned type of variables is required as they can cast a shadow on local variables
     * present in the outer scope.
     *
     * @param variableDefAst DetailAst node of type {@link TokenTypes#VARIABLE_DEF}
     * @return true if variableDefAst is an instance variable in local anonymous inner class
     */
    private static boolean isInstVarInLocalAnonInnerClass(DetailAST variableDefAst) {
        final DetailAST objBlockAst = variableDefAst.getParent();
        final DetailAST literalNewAst = objBlockAst.getParent();
        final DetailAST grandParent = literalNewAst.getParent();
        return objBlockAst.getType() == TokenTypes.OBJBLOCK
                && literalNewAst.getType() == TokenTypes.LITERAL_NEW
                && TokenUtil.isOfType(TokenTypes.SLIST,
                grandParent.getParent().getParent().getType(),
                grandParent.getParent().getParent().getParent().getType());
    }

    /**
     * Whether an ident with parent node of type {@link TokenTypes#METHOD_REF}
     * should be checked or not.
     *
     * @param identAst identAst
     * @return true if an ident with parent node of type {@link TokenTypes#METHOD_REF}
     *         should be checked or if the parent type is not {@link TokenTypes#METHOD_REF}
     */
    public static boolean shouldCheckIdentWithMethodRefParent(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        boolean result = true;
        if (parent.getType() == TokenTypes.METHOD_REF) {
            result = parent.getFirstChild() == identAst
                    && parent.getLastChild().getType() != TokenTypes.LITERAL_NEW;
        }
        return result;
    }

    /**
     * Whether to check current and nested identifier dotAst or not.
     *
     * @param dotAst dotAst
     * @return true if ident nested under dotAst should be checked
     */
    public static boolean shouldCheckIdentTokenNestedUnderDot(DetailAST dotAst) {

        return !TokenUtil.findFirstTokenByPredicate(dotAst,
                        childAst -> {
                            return TokenUtil.isOfType(childAst,
                                    UNACCEPTABLE_CHILD_OF_DOT);
                        })
                .isPresent();
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
                if (!variableDesc.isInstVarInLocalAnonInnerClass()) {
                    variableDesc.registerAsUsed();
                }
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
        if (TokenUtil.isOfType(parentAst, TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
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
     * operand of a stand-alone increment or decrement.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return true if identAst is used as a left-hand side value
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return isStandAloneIncrementOrDecrement(identAst)
                || parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is used as
     * an operand of a stand-alone increment or decrement.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return true if identAst is used as an operand of stand-alone
     *         increment or decrement
     */
    private static boolean isStandAloneIncrementOrDecrement(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        final DetailAST grandParent = parent.getParent();
        return TokenUtil.isOfType(parent, INCREMENT_AND_DECREMENT_TOKENS)
                && TokenUtil.isOfType(grandParent, TokenTypes.EXPR)
                && !isIncrementOrDecrementVariableUsed(grandParent);
    }

    /**
     * A variable with increment or decrement operator is considered used if it
     * is used as an argument or as an array index or for assigning value
     * to a variable.
     *
     * @param exprAst DetailAst of type {@link TokenTypes#EXPR}
     * @return true if variable nested in exprAst is used
     */
    private static boolean isIncrementOrDecrementVariableUsed(DetailAST exprAst) {
        return TokenUtil.isOfType(exprAst.getParent(),
                TokenTypes.ELIST, TokenTypes.INDEX_OP, TokenTypes.ASSIGN)
                && exprAst.getParent().getParent().getType() != TokenTypes.FOR_ITERATOR;
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
         * {@link TokenTypes#SLIST} or {@link TokenTypes#LITERAL_FOR}
         * which is enclosing the variable.
         */
        private final DetailAST scope;

        /**
         * Is an instance variable in local anonymous inner class.
         */
        private final boolean instVarInLocalAnonInnerClass;

        /**
         * Is the variable used.
         */
        private boolean used;

        /**
         * Create a new VariableDesc instance.
         *
         * @param name name of the variable
         * @param typeAst DetailAst of type {@link TokenTypes#TYPE}
         * @param scope DetailAst of type {@link TokenTypes#SLIST} or
         *              {@link TokenTypes#LITERAL_FOR} which is enclosing the variable
         * @param instVarInLocalAnonInnerClass Is an instance variable in local
         *                                     anonymous inner class.
         */
        /* package */ VariableDesc(String name, DetailAST typeAst, DetailAST scope,
                boolean instVarInLocalAnonInnerClass) {
            this.name = name;
            this.typeAst = typeAst;
            this.scope = scope;
            this.instVarInLocalAnonInnerClass = instVarInLocalAnonInnerClass;
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
         * Get the associated DetailAst node of type {@link TokenTypes#TYPE}.
         *
         * @return the associated DetailAst node of type {@link TokenTypes#TYPE}
         */
        public DetailAST getTypeAst() {
            return typeAst;
        }

        /**
         * Get DetailAst of type {@link TokenTypes#SLIST}
         * or {@link TokenTypes#LITERAL_FOR} which is enclosing the variable
         * i.e. its scope.
         *
         * @return the scope associated with the variable
         */
        public DetailAST getScope() {
            return scope;
        }

        /**
         * Is the variable used or not.
         *
         * @return true if variable is used
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Register the variable as used.
         */
        public void registerAsUsed() {
            used = true;
        }

        /**
         * Is an instance variable in local anonymous inner class.
         *
         * @return true if is an instance variable in local anonymous inner class
         */
        public boolean isInstVarInLocalAnonInnerClass() {
            return instVarInLocalAnonInnerClass;
        }
    }
}
