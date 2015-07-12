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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that any combination of String literals with optional
 * assignment is on the left side of an equals() comparison.
 * </p>
 *
 * <p>
 * Rationale: Calling the equals() method on String literals
 * will avoid a potential NullPointerException.  Also, it is
 * pretty common to see null check right before equals comparisons
 * which is not necessary in the below example.
 *
 * For example:
 *
 * <pre>
 *  <code>
 *    String nullString = null;
 *    nullString.equals(&quot;My_Sweet_String&quot;);
 *  </code>
 * </pre>
 * should be refactored to
 *
 * <pre>
 *  <code>
 *    String nullString = null;
 *    &quot;My_Sweet_String&quot;.equals(nullString);
 *  </code>
 * </pre>
 *
 *
 * <p>
 * Limitations: If the equals method is overridden or
 * a covariant equals method is defined and the implementation
 * is incorrect (where s.equals(t) does not return the same result
 * as t.equals(s)) then rearranging the called on object and
 * parameter may have unexpected results
 *
 * <br>
 *
 * Java's Autoboxing feature has an affect
 * on how this check is implemented. Pre Java 5 all IDENT + IDENT
 * object concatenations would not cause a NullPointerException even
 * if null.  Those situations could have been included in this check.
 * They would simply act as if they surrounded by String.valueOf()
 * which would concatenate the String null.
 *
 * <p>
 * The following example will cause a
 * NullPointerException as a result of what autoboxing does.
 * <pre>
 * Integer i = null, j = null;
 * String number = "5"
 * number.equals(i + j);
 * </pre>
 *
 *
 * Since, it is difficult to determine what kind of Object is being
 * concatenated all ident concatenation is considered unsafe.
 *
 * @author Travis Schneeberger
 * version 1.0
 */
public class EqualsAvoidNullCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EQUALS_AVOID_NULL = "equals.avoid.null";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EQUALS_IGNORE_CASE_AVOID_NULL = "equalsIgnoreCase.avoid.null";

    /** Whether to process equalsIgnoreCase() invocations. */
    private boolean ignoreEqualsIgnoreCase;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_CALL};
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
    public void visitToken(final DetailAST methodCall) {
        final DetailAST dot = methodCall.getFirstChild();
        if (dot.getType() != TokenTypes.DOT) {
            return;
        }

        final DetailAST objCalledOn = dot.getFirstChild();
        if (isStringLiteral(objCalledOn)) {
            return;
        }


        final DetailAST method = objCalledOn.getNextSibling();
        final DetailAST expr = dot.getNextSibling().getFirstChild();

        if ("equals".equals(method.getText())
            && containsOneArgument(methodCall) && containsAllSafeTokens(expr)) {
            log(methodCall.getLineNo(), methodCall.getColumnNo(),
                MSG_EQUALS_AVOID_NULL);
        }

        if (!ignoreEqualsIgnoreCase
            && "equalsIgnoreCase".equals(method.getText())
            && containsOneArgument(methodCall) && containsAllSafeTokens(expr)) {
            log(methodCall.getLineNo(), methodCall.getColumnNo(),
                MSG_EQUALS_IGNORE_CASE_AVOID_NULL);
        }
    }

    /**
     * Verify that method call has one argument.
     *
     * @param methodCall METHOD_CALL DetailAST
     * @return true if method call has one argument.
     */
    private static boolean containsOneArgument(DetailAST methodCall) {
        final DetailAST elist = methodCall.findFirstToken(TokenTypes.ELIST);
        return elist.getChildCount() == 1;
    }

    /**
     * checks for calling equals on String literal and
     * anon object which cannot be null
     * Also, checks if calling using strange inner class
     * syntax outter.inner.equals(otherObj) by looking
     * for the dot operator which cannot be improved
     * @param objCalledOn object AST
     * @return if it is string literal
     */
    private boolean isStringLiteral(DetailAST objCalledOn) {
        return objCalledOn.getType() == TokenTypes.STRING_LITERAL
                || objCalledOn.getType() == TokenTypes.LITERAL_NEW
                || objCalledOn.getType() == TokenTypes.DOT;
    }

    /**
     * <p>
     * Looks for all "safe" Token combinations in the argument
     * expression branch.
     * </p>
     *
     * <p>
     * See class documentation for details on autoboxing's affect
     * on this method implementation.
     * </p>
     *
     * @param expr the argument expression
     * @return - true if any child matches the set of tokens, false if not
     */
    private boolean containsAllSafeTokens(final DetailAST expr) {
        DetailAST arg = expr.getFirstChild();

        if (arg.branchContains(TokenTypes.METHOD_CALL)) {
            return false;
        }
        arg = skipVariableAssign(arg);

        //Plus assignment can have ill affects
        //do not want to recommend moving expression
        //See example:
        //String s = "SweetString";
        //s.equals(s += "SweetString"); //false
        //s = "SweetString";
        //(s += "SweetString").equals(s); //true
        //arg = skipVariablePlusAssign(arg);

        //must be just String literals to return true
        return !arg.branchContains(TokenTypes.PLUS_ASSIGN)
                && !arg.branchContains(TokenTypes.IDENT);
    }

    /**
     * Skips over an inner assign portion of an argument expression.
     * @param currentAST current token in the argument expression
     * @return the next relevant token
     */
    private DetailAST skipVariableAssign(final DetailAST currentAST) {
        if (currentAST.getType() == TokenTypes.ASSIGN
                && currentAST.getFirstChild().getType() == TokenTypes.IDENT) {
            return currentAST.getFirstChild().getNextSibling();
        }
        return currentAST;
    }

    /**
     * Whether to ignore checking {@code String.equalsIgnoreCase(String)}.
     * @param newValue whether to ignore checking
     *    {@code String.equalsIgnoreCase(String)}.
     */
    public void setIgnoreEqualsIgnoreCase(boolean newValue) {
        ignoreEqualsIgnoreCase = newValue;
    }
}
