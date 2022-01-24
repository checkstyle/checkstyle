////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks whether method calls specified by identifier pattern are wrapped.
 * </p>
 * <ul>
 * <li>
 * Property {@code identifierPattern} - Specify methods to be checked.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code maxCallsPerLine} - Specify maximum number of method calls per lines if we
 * are wrapping the method calls. If all the method calls span over a single line then this
 * property has no effect.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * <li>
 * Property {@code maxSingleLineCalls} - Specify maximum number of method calls if all chained
 * method calls span over a single line. If chained method calls span over multiple lines then
 * this property has no effect.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to use custom identifierPattern:
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
 *     assertWithSomeMessage("ex").that(1).isEqualTo(1);
 *                      // violation above, ''3' method calls on single line, max allowed is '1''
 *     assertWithSomeMessage("ex")
 *             .that(1).isEqualTo(1); // violation, 'Chained method call should be wrapped'
 * }
 * </pre>
 * <p>
 * To configure the check to use custom identifierPattern and maxSingleLinesCalls:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^assert.*$&quot; /&gt;
 *  &lt;property name=&quot;maxSingleLineCalls&quot; value=&quot;3&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *     assertWithSomeMessage("ex").that(1).isEqualTo(1); // ok
 *     assertWithSomeMessage("ex")
 *             .that(1).isEqualTo(1); // violation, 'Chained method call should be wrapped'
 * }
 * </pre>
 * <p>
 * To configure the check to use custom identifierPattern and maxCallsPerLine:
 * </p>
 * <pre>
 * &lt;module name=&quot;ChainedMethodCallWrap&quot;&gt;
 *  &lt;property name=&quot;identifierPattern&quot; value=&quot;^assert.*$&quot; /&gt;
 *  &lt;property name=&quot;maxCallsPerLine&quot; value=&quot;2&quot; /&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * void method() {
 *     assertWithSomeMessage("ex").that(1).isEqualTo(1);
 *                      // violation above, ''3' method calls on single line, max allowed is '1''
 *     assertWithSomeMessage("ex")
 *             .that(1).isEqualTo(1); // ok
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
 * @since 9.3
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
     * Specify methods to be checked.
     */
    private Pattern identifierPattern = Pattern.compile("^$");

    /**
     * Specify maximum number of method calls per lines if we are wrapping the method calls.
     * If all the method calls span over a single line then this property has no effect.
     */
    private int maxCallsPerLine = 1;

    /**
     * Specify maximum number of method calls if all chained method calls span over a single
     * line. If chained method calls span over multiple lines then this property has no effect.
     */
    private int maxSingleLineCalls = 1;

    /**
     * Setter to specify methods to be checked.
     *
     * @param identifierPattern pattern to specify methods to be checked
     */
    public void setIdentifierPattern(Pattern identifierPattern) {
        this.identifierPattern = identifierPattern;
    }

    /**
     * Setter to specify maximum number of method calls per lines if we are wrapping the method
     * calls. If all the method calls span over a single line then this property has no effect.
     *
     * @param maxCallsPerLine maximum number of method calls per lines if we are wrapping
     *                        the method calls
     */
    public void setMaxCallsPerLine(int maxCallsPerLine) {
        this.maxCallsPerLine = maxCallsPerLine;
    }

    /**
     * Setter to specify maximum number of method calls if all chained method calls span over a
     * single line. If chained method calls span over multiple lines then this property has no
     * effect.
     *
     * @param maxSingleLineCalls maximum number of method calls if all chained method calls span
     *                           over a single line
     */
    public void setMaxSingleLineCalls(int maxSingleLineCalls) {
        this.maxSingleLineCalls = maxSingleLineCalls;
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
        return methodCallAst.getParent().getType() == TokenTypes.EXPR;
    }

    /**
     * Whether the chained method calls match the
     * {@link ChainedMethodCallWrapCheck#identifierPattern}.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     * @return {@code true} if the chained method calls match the identifier pattern
     */
    public boolean matchesIdentifierPattern(DetailAST methodCallAst) {
        final String getCompleteMethodChainName = FullIdent
                .createFullIdent(methodCallAst).getText();
        return Pattern.matches(identifierPattern.pattern() + ".*", getCompleteMethodChainName);
    }

    /**
     * Whether the chained method calls span over a single line.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     * @return {@code true} if the chained method calls span over a single line
     */
    public static boolean isSingleLineMethodChain(DetailAST methodCallAst) {
        DetailAST childAst = methodCallAst.getFirstChild();
        while (childAst.getType() != TokenTypes.IDENT) {
            childAst = childAst.getFirstChild();
        }
        return childAst.getLineNo() == methodCallAst.getLineNo();
    }

    /**
     * Check chained method calls spanning over a single line.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     */
    private void checkMethodChainingSingleLine(DetailAST methodCallAst) {
        DetailAST childAst = methodCallAst.getFirstChild();
        int methodCount = 1;
        while (childAst.getType() != TokenTypes.IDENT) {
            if (childAst.getType() == TokenTypes.METHOD_CALL) {
                methodCount++;
            }
            childAst = childAst.getFirstChild();
        }
        if (methodCount > maxSingleLineCalls) {
            log(childAst, MSG_TOO_MANY_SINGLE_LINE, methodCount, maxSingleLineCalls);
        }
    }

    /**
     * Check chained method calls spanning over multiple lines.
     *
     * @param methodCallAst ast node of type {@link TokenTypes#METHOD_CALL}
     */
    private void checkMethodChainingMultiLine(DetailAST methodCallAst) {
        DetailAST childAst = methodCallAst.getFirstChild();
        DetailAST currentLineOuterMostMethodAst = methodCallAst;
        int methodCount = 1;
        while (childAst.getType() != TokenTypes.IDENT) {
            if (childAst.getType() == TokenTypes.METHOD_CALL) {
                final int methodCallLineNo = childAst.getLineNo();
                if (currentLineOuterMostMethodAst.getLineNo() == methodCallLineNo) {
                    methodCount++;
                    if (methodCount > maxCallsPerLine) {
                        log(currentLineOuterMostMethodAst, MSG_WRAP_METHOD_CALL);
                        currentLineOuterMostMethodAst = childAst;
                    }
                }
                else {
                    currentLineOuterMostMethodAst = childAst;
                    methodCount = 1;
                }
            }
            childAst = childAst.getFirstChild();
        }
    }
}
