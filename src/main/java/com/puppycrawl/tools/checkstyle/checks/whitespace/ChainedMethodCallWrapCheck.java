///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks whether method calls specified by identifier pattern are wrapped onto
 * the subsequent line(s). Line wrapping means to insert line breaks in code to make
 * it more readable and adhere to line length standards.
 * </p>
 * <p>
 * Rationale: Long chained method calls are difficult to read and comprehend, and sometimes
 * require horizontal scrolling to read the complete method call chain. Wrapping method calls
 * onto the following line(s) enhances readability.
 * </p>
 * <ul>
 * <li>
 * Property {@code identifierPattern} - Specify method calls to be checked. By default,
 * identifierPattern matches nothing, so no method calls are checked. This property must be
 * set in order to use the check.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code maxCallsInMultiLine} - Specify maximum number of method calls per lines if we
 * are wrapping the method calls. If all the method calls span over a single line then this
 * property has no effect.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * <li>
 * Property {@code maxCallsInSingleLine} - Specify maximum number of method calls if all chained
 * method calls span over a single line. If chained method calls span over multiple lines then
 * this property has no effect.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to use custom identifierPattern to validate assertions:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^assert.*$&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   assertWithMessage("ex").that(1).isEqualTo(1);
 *                         // violation above, '3 method calls on single line, max allowed is 1'
 *   assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // violation, 'Wrap chained method call, max allowed on a line is 1'
 *
 *   assertWithMessage("ex")
 *     .that(1)
 *     .isEqualTo(1);         // ok, every method call is wrapped
 *
 *   Truth.assertWithMessage("ex").that(1).isEqualTo(1); // ok, identifierPattern is different
 *
 *   Truth.assertWithMessage("ex")
 *     .that(1).isEqualTo(1);                            // ok, identifierPattern is different
 * }
 * </pre>
 * <p>
 * To configure the check to use custom identifierPattern to validate Truth's assertions:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^Truth\.assert.*$&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   assertWithMessage("ex").that(1).isEqualTo(1); // ok, identifierPattern is different
 *
 *   assertWithMessage("ex")
 *     .that(1).isEqualTo(1);                      // ok, identifierPattern is different
 *
 *   assertWithMessage("ex")
 *     .that(1)
 *     .isEqualTo(1);                              // ok, identifierPattern is different
 *
 *   Truth.assertWithMessage("ex").that(1).isEqualTo(1);
 *                         // violation above, '3 method calls on single line, max allowed is 1'
 *   Truth.assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // violation, 'Wrap chained method call, max allowed on a line is 1'
 * }
 * </pre>
 * <p>
 * To configure the check to use custom identifierPattern and maxSingleLinesCalls:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^assert.*$&quot; /&gt;
 *  &lt;property name=&quot;maxCallsInSingleLine&quot; value=&quot;3&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   assertWithMessage("ex").that(1).isEqualTo(1);
 *                            // ok above, 3 method calls &lt;= maxCallsInSingleLine
 *
 *   assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // violation, 'Wrap chained method call, max allowed on a line is 1'
 *
 *   assertWithMessage("ex")
 *     .that(1)
 *     .isEqualTo(1);         // ok, every method call is wrapped
 *
 *   Truth.assertWithMessage("ex").that(1).isEqualTo(1);
 *                            // ok above, identifierPattern is different
 *
 *   Truth.assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // ok, identifierPattern is different
 * }
 * </pre>
 * <p>
 * To configure the check to use custom identifierPattern and maxCallsInMultiLine:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^assert.*$&quot; /&gt;
 *  &lt;property name=&quot;maxCallsInMultiLine&quot; value=&quot;2&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   assertWithMessage("ex").that(1).isEqualTo(1);
 *                         // violation above, '3 method calls on single line, max allowed is 1'
 *
 *   assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // ok, 2 method calls on current line &lt;= maxCallsInMultiLine
 *
 *   assertWithMessage("ex")
 *     .that(1)
 *     .isEqualTo(1);         // ok, every method call is wrapped
 *
 *   Truth.assertWithMessage("ex").that(1).isEqualTo(1);
 *                            // ok above, identifierPattern is different
 *
 *   Truth.assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // ok, identifierPattern is different
 * }
 * </pre>
 * <p>
 * To configure the check to use custom identifierPattern, maxSingleLinesCalls
 * and maxCallsInMultiLine:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^assert.*$&quot; /&gt;
 *  &lt;property name=&quot;maxCallsInSingleLine&quot; value=&quot;2&quot; /&gt;
 *  &lt;property name=&quot;maxCallsInMultiLine&quot; value=&quot;2&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *   assertWithMessage("ex").that(1).isEqualTo(1);
 *                         // violation above, '3 method calls on single line, max allowed is 2'
 *
 *   assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // ok, 2 method calls on current line &lt;= maxCallsInMultiLine
 *
 *   assertWithMessage("ex")
 *     .that(1)
 *     .isEqualTo(1);         // ok, every method call is wrapped
 *
 *   Truth.assertWithMessage("ex").that(1).isEqualTo(1);
 *                            // ok above, identifierPattern is different
 *
 *   Truth.assertWithMessage("ex")
 *     .that(1).isEqualTo(1); // ok, identifierPattern is different
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
 * {@code too.many.single.line}
 * </li>
 * <li>
 * {@code wrap.method.call}
 * </li>
 * </ul>
 *
 * @since 10.13
 */
@StatelessCheck
public class ChainedMethodCallWrapCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TOO_MANY_SINGLE_LINE = "too.many.single.line";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WRAP_METHOD_CALL = "wrap.method.call";

    /**
     * Default string regex used by identifierPattern. This does not match any method chain.
     */
    private static final String DEFAULT_PATTERN = "^$";

    /**
     * Relevant parent nodes of {@link TokenTypes#METHOD_CALL}.
     */
    private static final int[] PARENT_OF_METHOD_CALL = {
        TokenTypes.EXPR,
        TokenTypes.METHOD_CALL,
        TokenTypes.LITERAL_CASE,
    };

    /**
     * Nodes that continue the chained method call.
     */
    private static final int[] METHOD_CHAIN_CONTINUATION_NODES = {
        TokenTypes.METHOD_CALL,
        TokenTypes.DOT,
        TokenTypes.INDEX_OP,
    };

    /**
     * Specify method calls to be checked. By default, identifierPattern matches nothing, so no
     * method calls are checked. This property must be set in order to use the check.
     */
    private Pattern identifierPattern = Pattern.compile(DEFAULT_PATTERN);

    /**
     * Specify maximum number of method calls per lines if we are wrapping the method calls.
     * If all the method calls span over a single line then this property has no effect.
     */
    private int maxCallsInMultiLine = 1;

    /**
     * Specify maximum number of method calls if all chained method calls span over a single
     * line. If chained method calls span over multiple lines then this property has no effect.
     */
    private int maxCallsInSingleLine = 1;

    /**
     * Setter to specify method calls to be checked. By default, identifierPattern matches nothing,
     * so no method calls are checked. This property must be set in order to use the check.
     *
     * @param identifierPattern pattern to specify methods to be checked
     * @since 10.13
     */
    public void setIdentifierPattern(Pattern identifierPattern) {
        this.identifierPattern = identifierPattern;
    }

    /**
     * Setter to specify maximum number of method calls per lines if we are wrapping the method
     * calls. If all the method calls span over a single line then this property has no effect.
     *
     * @param maxCallsInMultiLine maximum number of method calls per lines if we are wrapping
     *                            the method calls
     * @since 10.13
     */
    public void setMaxCallsInMultiLine(int maxCallsInMultiLine) {
        this.maxCallsInMultiLine = maxCallsInMultiLine;
    }

    /**
     * Setter to specify maximum number of method calls if all chained method calls span over a
     * single line. If chained method calls span over multiple lines then this property has no
     * effect.
     *
     * @param maxCallsInSingleLine maximum number of method calls if all chained method calls span
     *                             over a single line
     * @since 10.13
     */
    public void setMaxCallsInSingleLine(int maxCallsInSingleLine) {
        this.maxCallsInSingleLine = maxCallsInSingleLine;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.METHOD_CALL,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isTopMostMethodCall(ast)
                && matchesIdentifierPattern(ast)) {
            if (isSingleLineMethodChain(ast)) {
                checkMethodChainingSingleLine(ast);
            }
            else {
                checkMethodChainingMultiLine(ast);
            }
        }
    }

    /**
     * Whether {@code methodCallAst} is the top most method call in the
     * abstract syntax tree.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     * @return {@code true} if {@code methodCallAst} is the top most method call in the
     *         abstract syntax tree
     */
    private static boolean isTopMostMethodCall(DetailAST methodCallAst) {
        boolean result = false;
        DetailAST ancestorAst = methodCallAst.getParent();
        while (!TokenUtil.isOfType(ancestorAst, PARENT_OF_METHOD_CALL)) {
            final DetailAST ancestorAstPrevSibling = ancestorAst.getPreviousSibling();
            if (ancestorAstPrevSibling != null
                    && !TokenUtil.isOfType(ancestorAst, TokenTypes.TYPECAST, TokenTypes.INDEX_OP)) {
                result = true;
                break;
            }
            ancestorAst = ancestorAst.getParent();
        }
        return result || TokenUtil.isOfType(ancestorAst, TokenTypes.EXPR, TokenTypes.LITERAL_CASE);
    }

    /**
     * Whether the chained method calls match the
     * {@link ChainedMethodCallWrapCheck#identifierPattern}.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     * @return {@code true} if the chained method calls match the identifier pattern
     */
    private boolean matchesIdentifierPattern(DetailAST methodCallAst) {
        final String getCompleteMethodChainName =
            FullIdent.createFullIdent(methodCallAst).getText();
        return Pattern.matches(identifierPattern.pattern(), getCompleteMethodChainName);
    }

    /**
     * Checks if a complete chain of method calls spans exactly one line.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     * @return {@code true} if a complete chain of method calls spans exactly one line
     */
    private static boolean isSingleLineMethodChain(DetailAST methodCallAst) {
        DetailAST childAst = methodCallAst;
        while (TokenUtil.isOfType(childAst, METHOD_CHAIN_CONTINUATION_NODES)) {
            childAst = getMethodCallDescendantAst(childAst);
        }
        return TokenUtil.areOnSameLine(childAst, methodCallAst);
    }

    /**
     * Check the number of method calls that span a single line, and log a violation if the number
     * exceeds {@link ChainedMethodCallWrapCheck#maxCallsInSingleLine}.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     */
    private void checkMethodChainingSingleLine(DetailAST methodCallAst) {
        DetailAST childAst = methodCallAst.getFirstChild();
        int methodCallCount = 1;
        while (TokenUtil.isOfType(childAst, METHOD_CHAIN_CONTINUATION_NODES)) {
            if (childAst.getType() == TokenTypes.METHOD_CALL) {
                methodCallCount++;
            }
            childAst = getMethodCallDescendantAst(childAst);
        }
        if (methodCallCount > maxCallsInSingleLine) {
            log(childAst, MSG_TOO_MANY_SINGLE_LINE, methodCallCount, maxCallsInSingleLine);
        }
    }

    /**
     * Check chained method calls spanning over multiple lines, logs violations for every
     * method call exceeding {@link ChainedMethodCallWrapCheck#maxCallsInMultiLine}.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     */
    private void checkMethodChainingMultiLine(DetailAST methodCallAst) {
        DetailAST childAst = methodCallAst.getFirstChild();
        DetailAST currentLineOuterMostMethodAst = methodCallAst;
        int methodCallCount = 1;
        while (TokenUtil.isOfType(childAst, METHOD_CHAIN_CONTINUATION_NODES)) {
            if (childAst.getType() == TokenTypes.METHOD_CALL) {
                final int methodCallLineNo = childAst.getLineNo();
                if (currentLineOuterMostMethodAst.getLineNo() == methodCallLineNo) {
                    methodCallCount++;
                    if (methodCallCount > maxCallsInMultiLine) {
                        log(currentLineOuterMostMethodAst, MSG_WRAP_METHOD_CALL,
                            maxCallsInMultiLine);
                        currentLineOuterMostMethodAst = childAst;
                    }
                }
                else {
                    currentLineOuterMostMethodAst = childAst;
                    methodCallCount = 1;
                }
            }
            childAst = getMethodCallDescendantAst(childAst);
        }
    }

    /**
     * Get the required descendant ast of method call ast.
     *
     * @param ast ast
     * @return required descendant ast of method call ast.
     */
    private static DetailAST getMethodCallDescendantAst(DetailAST ast) {
        DetailAST childAst = ast.getFirstChild();
        while (childAst.getType() == TokenTypes.LPAREN) {
            childAst = childAst.getNextSibling();
            if (!TokenUtil.isOfType(childAst, METHOD_CHAIN_CONTINUATION_NODES)) {
                childAst = getMethodCallDescendantAfterLparen(childAst);
            }
        }
        return childAst;
    }

    /**
     * Get method call descendant after encountering {@link TokenTypes#LPAREN}
     * as the previous sibling of {@code ast}.
     *
     * <p>If node of type {@link TokenTypes#LPAREN} is encountered as a previous sibling, then the
     * next method chain continuation node can be nested under the current node or nested under
     * other siblings or might be one of the next siblings. This method traverses all child nodes of
     * the current sibling node to check whether there is a continuation node or not.
     *
     * @param ast ast
     * @return method call descendant after encountering {@link TokenTypes#LPAREN}
     *         as the previous sibling of {@code ast}
     */
    private static DetailAST getMethodCallDescendantAfterLparen(DetailAST ast) {
        DetailAST result = ast;
        DetailAST childAst = result.getFirstChild();
        while (childAst != null) {
            result = childAst;
            if (TokenUtil.isOfType(childAst, METHOD_CHAIN_CONTINUATION_NODES)) {
                break;
            }
            childAst = childAst.getNextSibling();
        }
        return result;
    }
}
