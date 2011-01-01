////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import antlr.collections.AST;
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
 * </p>
 *
 * <p>
 * Limitations: If the equals method is overridden or
 * a covariant equals method is defined and the implementation
 * is incorrect (where s.equals(t) does not return the same result
 * as t.equals(s)) then rearranging the called on object and
 * parameter may have unexpected results
 *
 * <br/>
 *
 * Java's Autoboxing feature has an affect
 * on how this check is implemented. Pre Java 5 all IDENT + IDENT
 * object concatenations would not cause a NullPointerException even
 * if null.  Those situations could have been included in this check.
 * They would simply act as if they surrounded by String.valueof()
 * which would concatenate the String null.
 * </p>
 * <p>
 * The following example will cause a
 * NullPointerException as a result of what autoboxing does.
 * <pre>
 * Integer i = null, j = null;
 * String number = "5"
 * number.equals(i + j);
 * </pre>
 * </p>
 *
 * Since, it is difficult to determine what kind of Object is being
 * concatenated all ident concatenation is considered unsafe.
 *
 * @author Travis Schneeberger
 * version 1.0
 */
public class EqualsAvoidNullCheck extends Check
{
    /** Whether to process equalsIgnoreCase() invocations. */
    private boolean mIgnoreEqualsIgnoreCase;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_CALL};
    }

    @Override
    public void visitToken(final DetailAST aMethodCall)
    {
        final DetailAST dot = aMethodCall.getFirstChild();
        if (dot.getType() != TokenTypes.DOT) {
            return;
        }

        final DetailAST objCalledOn = dot.getFirstChild();

        //checks for calling equals on String literal and
        //anon object which cannot be null
        //Also, checks if calling using strange inner class
        //syntax outter.inner.equals(otherObj) by looking
        //for the dot operator which cannot be improved
        if ((objCalledOn.getType() == TokenTypes.STRING_LITERAL)
                || (objCalledOn.getType() == TokenTypes.LITERAL_NEW)
                || (objCalledOn.getType() == TokenTypes.DOT))
        {
            return;
        }

        final DetailAST method = objCalledOn.getNextSibling();
        final DetailAST expr = dot.getNextSibling().getFirstChild();

        if ("equals".equals(method.getText())
                || (!mIgnoreEqualsIgnoreCase && "equalsIgnoreCase"
                        .equals(method.getText())))
        {
            if (containsOneArg(expr) && containsAllSafeTokens(expr)) {
                log(aMethodCall.getLineNo(), aMethodCall.getColumnNo(),
                    "equals".equals(method.getText())
                    ? "equals.avoid.null"
                    : "equalsIgnoreCase.avoid.null");
            }
        }
    }

    /**
     * Checks if a method contains no arguments
     * starting at with the argument expression.
     *
     * @param aExpr the argument expression
     * @return true if the method contains no args, false if not
     */
    private boolean containsNoArgs(final AST aExpr)
    {
        return (aExpr == null);
    }

    /**
     * Checks if a method contains multiple arguments
     * starting at with the argument expression.
     *
     * @param aExpr the argument expression
     * @return true if the method contains multiple args, false if not
     */
    private boolean containsMultiArgs(final AST aExpr)
    {
        final AST comma = aExpr.getNextSibling();
        return (comma != null) && (comma.getType() == TokenTypes.COMMA);
    }

    /**
     * Checks if a method contains a single argument
     * starting at with the argument expression.
     *
     * @param aExpr the argument expression
     * @return true if the method contains a single arg, false if not
     */
    private boolean containsOneArg(final AST aExpr)
    {
        return !containsNoArgs(aExpr) && !containsMultiArgs(aExpr);
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
     * @param aExpr the argument expression
     * @return - true if any child matches the set of tokens, false if not
     */
    private boolean containsAllSafeTokens(final DetailAST aExpr)
    {
        DetailAST arg = aExpr.getFirstChild();

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

        if ((arg).branchContains(TokenTypes.PLUS_ASSIGN)
                || (arg).branchContains(TokenTypes.IDENT))
        {
            return false;
        }

        //must be just String literals if got here
        return true;
    }

    /**
     * Skips over an inner assign portion of an argument expression.
     * @param aCurrentAST current token in the argument expression
     * @return the next relevant token
     */
    private DetailAST skipVariableAssign(final DetailAST aCurrentAST)
    {
        if ((aCurrentAST.getType() == TokenTypes.ASSIGN)
                && (aCurrentAST.getFirstChild().getType() == TokenTypes.IDENT))
        {
            return aCurrentAST.getFirstChild().getNextSibling();
        }
        return aCurrentAST;
    }

    /**
     * Whether to ignore checking {@code String.equalsIgnoreCase(String)}.
     * @param aNewValue whether to ignore checking
     *    {@code String.equalsIgnoreCase(String)}.
     */
    public void setIgnoreEqualsIgnoreCase(boolean aNewValue)
    {
        mIgnoreEqualsIgnoreCase = aNewValue;
    }
}
