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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that references to instance variables and methods of the present object
 * explicitly avoid unnecessary use of "this", unless required to resolve ambiguity with
 * a shadowed field.
 * </div>
 *
 * <p>
 * Rationale: modern IDEs(e.g. IDEA, ECLIPSE, NetBeans) show what an entity is
 * (class variable, local variable etc.) so there is no need to put redundant
 * "this" keyword.
 * </p>
 *
 * @since 13.11.0
 */
@FileStatefulCheck
public class RedundantThisCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_FIELD = "redundant.this.field";

    /**
     * A key is pointing to the warning message text in "message.properties"
     * file.
     */
    public static final String MSG_KEY_METHOD = "redundant.this.method";

    /**
     * Tracks names (parameters and local variables) that can shadow a field.
     * Pattern variables are intentionally NOT tracked here, since they are
     * flow-scoped rather than lexically scoped; see
     * {@link #isPatternVariableInScope(String, DetailAST)}.
     */
    private final Deque<Set<String>> scopeStack = new ArrayDeque<>();

    /**
     * Control to checking method calls.
     */
    private boolean checkMethods;

    /**
     * Creates a new {@code RedundantThisCheck} instance.
     */
    public RedundantThisCheck() {
        // no code by default
    }

    /**
     * Setter to check whether to check redundant "this" with method call.
     *
     * @param checkMethods should we check method call
     * @since 13.11.0
     */
    public void setCheckMethods(boolean checkMethods) {
        this.checkMethods = checkMethods;
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_THIS,
            TokenTypes.SLIST,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF -> {
                scopeStack.push(new HashSet<>());
                addParametersToScope(NullUtil.notNull(ast.findFirstToken(TokenTypes.PARAMETERS)));
            }
            case TokenTypes.LITERAL_CATCH -> {
                scopeStack.push(new HashSet<>());
                addParametersToScope(ast);
            }
            case TokenTypes.SLIST -> scopeStack.push(new HashSet<>());
            case TokenTypes.VARIABLE_DEF -> {
                final Set<String> currentScope = scopeStack.peek();
                if (currentScope != null) {
                    final String variable =
                        NullUtil.notNull(ast.findFirstToken(TokenTypes.IDENT)).getText();
                    currentScope.add(variable);
                }
            }
            default -> {
                if (ast.getNextSibling() != null) {
                    checkUnnecessaryThis(ast);
                }
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast, TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF, TokenTypes.LITERAL_CATCH, TokenTypes.SLIST)) {
            scopeStack.pop();
        }
    }

    /**
     * Checks if the use of "this" is redundant and logs a violation if so.
     *
     * @param literalThis the {@code LITERAL_THIS} token to check
     */
    private void checkUnnecessaryThis(DetailAST literalThis) {
        final DetailAST nextSibling = literalThis.getNextSibling();
        if (isFieldReference(literalThis)) {
            checkFieldReference(literalThis, nextSibling.getText());
        }
        else if (checkMethods && isMethodCallReference(literalThis)) {
            log(literalThis, MSG_KEY_METHOD, nextSibling.getText());
        }
    }

    /**
     * Determines whether {@code this} is being used as a field reference
     * (as opposed to being compared with {@code ==}/{@code !=}, used as a
     * method reference target, or used as the receiver of a method call).
     *
     * @param literalThis the {@code LITERAL_THIS} token
     * @return {@code true} if this usage refers to a field
     */
    private static boolean isFieldReference(DetailAST literalThis) {
        final DetailAST parent = literalThis.getParent();
        final DetailAST grandParent = parent.getParent();
        return !TokenUtil.isOfType(parent,
                    TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.METHOD_REF)
                && !TokenUtil.isOfType(grandParent, TokenTypes.METHOD_CALL);
    }

    /**
     * Determines whether {@code this} is being used as the receiver of a
     * method call.
     *
     * @param literalThis the {@code LITERAL_THIS} token
     * @return {@code true} if this usage is a method call receiver
     */
    private static boolean isMethodCallReference(DetailAST literalThis) {
        final DetailAST grandParent = literalThis.getParent().getParent();
        return grandParent.getType() == TokenTypes.METHOD_CALL;
    }

    /**
     * Logs a violation for {@code this.varName} unless the field name is
     * currently shadowed by a local name or a pattern variable, in which
     * case the qualifier is necessary.
     *
     * @param literalThis the {@code LITERAL_THIS} token
     * @param varName the field name following "this"
     */
    private void checkFieldReference(DetailAST literalThis, String varName) {
        if (!isShadowedByLocalName(varName)
                && !isPatternVariableInScope(varName, literalThis)) {
            log(literalThis, MSG_KEY_FIELD, varName);
        }
    }

    /**
     * Checks whether the given variable name is shadowed by any parameter or
     * local variable currently in scope, meaning that {@code this.name} is
     * necessary to distinguish the field from the shadowing name.
     *
     * @param name the variable name following "this"
     * @return {@code true} if a shadowing name exists in any enclosing scope
     */
    private boolean isShadowedByLocalName(String name) {
        boolean result = false;
        for (Set<String> scope : scopeStack) {
            if (scope.contains(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Adds all parameters found within {@code parametersNode} to the current
     * (top-of-stack) scope. {@code parametersNode} may be either a
     * {@code PARAMETERS} node (for methods and constructors) or a
     * {@code LITERAL_CATCH} node (for catch clauses); both carry their
     * parameter definitions as direct {@code PARAMETER_DEF} children.
     *
     * @param parametersNode the node whose {@code PARAMETER_DEF} children
     *     should be added to the current scope
     */
    private void addParametersToScope(DetailAST parametersNode) {
        DetailAST child = parametersNode.getFirstChild();
        while (child != null) {
            final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
            if (ident != null) {
                NullUtil.notNull(scopeStack.peek()).add(ident.getText());
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Determines whether a pattern variable with the given name is visible
     * (flow-scoped) at the given usage site. Only the two common, unambiguous
     * cases are supported:
     * <ul>
     * <li>Positive pattern: {@code if (obj instanceof Foo x) \{ ... x ... \}}
     * — {@code x} is visible strictly inside the then-branch.</li>
     * <li>Negated pattern with abrupt then-branch:
     * {@code if (!(obj instanceof Foo x)) \{ return; \} ... x ...} —
     * {@code x} is visible for the remainder of the enclosing block(s),
     * since the then-branch cannot complete normally.</li>
     * </ul>
     * More complex cases (switch patterns, loops, {@code &&}/{@code ||}
     * combinations, nested patterns) are intentionally not analyzed and are
     * treated as "not in scope".
     *
     * @param name the pattern variable name to look for
     * @param usageAst the node at the usage site (walked up from)
     * @return {@code true} if the pattern variable is visible at usageAst
     */
    private static boolean isPatternVariableInScope(String name, DetailAST usageAst) {
        boolean result = false;
        DetailAST current = usageAst;
        while (!result) {
            final DetailAST parent = current.getParent();
            if (parent == null
                    || TokenUtil.isOfType(parent, TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF)) {
                break;
            }
            result = isPatternVariableVisibleAt(parent, current, name);
            current = parent;
        }
        return result;
    }

    /**
     * Checks a single ancestor level encountered while walking up from a
     * usage site, dispatching to the rule that applies to that ancestor's
     * node type.
     *
     * @param ancestor the ancestor node currently being examined
     * @param descendant the child of {@code ancestor} on the path back down
     *     to the original usage site
     * @param name the pattern variable name to look for
     * @return {@code true} if this ancestor makes {@code name} visible
     */
    private static boolean isPatternVariableVisibleAt(
            DetailAST ancestor, DetailAST descendant, String name) {
        boolean result = false;
        if (ancestor.getType() == TokenTypes.LITERAL_IF && isThenBranch(ancestor, descendant)) {
            result = isPositivePatternMatch(ancestor, name);
        }
        else if (ancestor.getType() == TokenTypes.SLIST) {
            result = hasPrecedingNegatedPatternIf(descendant, name);
        }
        else if (ancestor.getType() == TokenTypes.SWITCH_RULE) {
            result = isPatternInCaseLabel(ancestor, name);
        }
        return result;
    }

    /**
     * Checks whether the case label of {@code switchRule} binds a pattern
     * variable with the given name. Only the arrow ("case Type name ->")
     * form is supported; old-style colon case groups and guarded/nested
     * patterns are intentionally not analyzed.
     *
     * @param switchRule the {@code SWITCH_RULE} node
     * @param name the pattern variable name to look for
     * @return {@code true} if the case label binds {@code name}
     */
    private static boolean isPatternInCaseLabel(DetailAST switchRule, String name) {
        final DetailAST caseLabel = switchRule.findFirstToken(TokenTypes.LITERAL_CASE);
        return caseLabel != null && name.equals(patternVariableName(caseLabel));
    }

    /**
     * Checks whether {@code child} is the then-branch statement of the given
     * {@code LITERAL_IF} node.
     *
     * @param literalIf the {@code LITERAL_IF} node
     * @param child the candidate child node
     * @return {@code true} if {@code child} is the then-branch
     */
    private static boolean isThenBranch(DetailAST literalIf, DetailAST child) {
        final DetailAST rparen = literalIf.findFirstToken(TokenTypes.RPAREN);
        return rparen.getNextSibling() == child;
    }

    /**
     * Checks whether the condition of {@code literalIf} is a simple,
     * non-negated {@code instanceof} pattern binding {@code name}.
     *
     * @param literalIf the {@code LITERAL_IF} node
     * @param name the pattern variable name to match
     * @return {@code true} if it is a matching positive pattern
     */
    private static boolean isPositivePatternMatch(DetailAST literalIf, String name) {
        final InstanceOfMatch match = findInstanceOfCore(literalIf);
        return match != null && !match.negated()
                && name.equals(patternVariableName(match.instanceOfNode()));
    }

    /**
     * Scans statements preceding {@code child} within its enclosing block for
     * an {@code if (!(... instanceof Type name))} whose then-branch cannot
     * complete normally, which makes {@code name} visible from {@code child}
     * onward.
     *
     * @param child the statement (or ancestor of it) to scan backward from
     * @param name the pattern variable name to look for
     * @return {@code true} if such a preceding if-statement is found
     */
    private static boolean hasPrecedingNegatedPatternIf(DetailAST child, String name) {
        boolean result = false;
        DetailAST sibling = child.getPreviousSibling();
        while (sibling != null
                && !result) {
            if (sibling.getType() == TokenTypes.LITERAL_IF) {
                final InstanceOfMatch match = findInstanceOfCore(sibling);
                result = match != null && match.negated()
                        && name.equals(patternVariableName(match.instanceOfNode()))
                        && !thenBranchCompletesNormally(sibling);
            }
            sibling = sibling.getPreviousSibling();
        }
        return result;
    }

    /**
     * Finds the inner node of a simple (possibly negated) condition of
     * {@code literalIf}. For a bare {@code instanceof} it returns a
     * non-negated match; for {@code !(...)} it returns a negated match whose
     * node is the unwrapped operand. Anything more complex
     * (e.g. {@code &&}, {@code ||}) is not recognized and yields {@code null}.
     * Callers must verify that the returned node is actually a
     * {@code LITERAL_INSTANCEOF} via {@link #patternVariableName}.
     *
     * @param literalIf the {@code LITERAL_IF} node
     * @return a match describing the inner node and whether it was negated,
     *     or {@code null} if the condition is not a simple or negated expression
     */
    private static InstanceOfMatch findInstanceOfCore(DetailAST literalIf) {
        InstanceOfMatch result = null;
        final DetailAST condition = literalIf.findFirstToken(TokenTypes.EXPR);
        final DetailAST node = unwrapExpr(condition);
        if (node.getType() == TokenTypes.LITERAL_INSTANCEOF) {
            result = new InstanceOfMatch(node, false);
        }
        else if (node.getType() == TokenTypes.LNOT) {
            final DetailAST inner = unwrapExpr(node.getFirstChild());
            result = new InstanceOfMatch(inner, true);
        }
        return result;
    }

    /**
     * Unwraps redundant {@code EXPR} wrapper nodes to reach the underlying
     * operator/operand node.
     *
     * @param ast the node to unwrap
     * @return the first non-{@code EXPR} descendant along the first-child
     *     chain, or {@code null}
     */
    private static DetailAST unwrapExpr(DetailAST ast) {
        DetailAST result = ast;
        boolean unwrapped = true;
        while (unwrapped) {
            if (result.getType() == TokenTypes.EXPR) {
                result = result.getFirstChild();
            }
            else if (result.getType() == TokenTypes.LPAREN) {
                result = result.getNextSibling();
            }
            else {
                unwrapped = false;
            }
        }
        return result;
    }

    /**
     * Extracts the bound name from a node's direct {@code PATTERN_VARIABLE_DEF}
     * child, if present. Used for both, an instanceof pattern (child of
     * {@code LITERAL_INSTANCEOF}) and for a case pattern (child of
     * {@code LITERAL_CASE}), since both declare their pattern variable the
     * same way.
     *
     * @param patternOwner the node that may directly declare a pattern variable
     * @return the pattern variable name, or {@code null} if there is none
     */
    private static String patternVariableName(DetailAST patternOwner) {
        String name = null;
        final DetailAST patternDef =
            patternOwner.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF);
        if (patternDef != null) {
            final DetailAST ident = patternDef.findFirstToken(TokenTypes.IDENT);
            name = ident.getText();
        }
        return name;
    }

    /**
     * Determines, for the common cases this check handles, whether the
     * then-branch of {@code literalIf} can complete normally. A then-branch
     * ending in {@code return}, {@code throw}, {@code break}, or
     * {@code continue} is treated as not completing normally.
     *
     * @param literalIf the {@code LITERAL_IF} node
     * @return {@code true} if the then-branch can complete normally
     */
    private static boolean thenBranchCompletesNormally(DetailAST literalIf) {
        final DetailAST rparen = literalIf.findFirstToken(TokenTypes.RPAREN);
        final DetailAST thenStmt = rparen.getNextSibling();
        return !isAbruptCompletionStatement(thenStmt);
    }

    /**
     * Checks whether {@code stmt} (a statement or block) ends in an abrupt
     * completion statement.
     *
     * @param stmt the statement to check
     * @return {@code true} if it is (or ends in, for a block) an abrupt
     *     completion statement
     */
    private static boolean isAbruptCompletionStatement(DetailAST stmt) {
        DetailAST last = stmt;
        if (stmt.getType() == TokenTypes.SLIST) {
            last = stmt.getLastChild().getPreviousSibling();
        }
        return TokenUtil.isOfType(last, TokenTypes.LITERAL_RETURN, TokenTypes.LITERAL_THROW,
                TokenTypes.LITERAL_CONTINUE, TokenTypes.LITERAL_BREAK);
    }

    /**
     * The result of {@link #findInstanceOfCore(DetailAST)}: the matched
     * {@code LITERAL_INSTANCEOF} node together with whether it appeared
     * negated (wrapped in a single {@code !}).
     *
     * @param instanceOfNode the matched {@code LITERAL_INSTANCEOF} node
     * @param negated whether the instanceof was wrapped in a {@code !}
     */
    private record InstanceOfMatch(DetailAST instanceOfNode, boolean negated) {
    }

}
