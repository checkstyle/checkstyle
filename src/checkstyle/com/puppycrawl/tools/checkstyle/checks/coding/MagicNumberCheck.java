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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

import java.util.Arrays;

/**
 * <p>
 * Checks for magic numbers.
 * </p>
 * <p>
 * An example of how to configure the check to ignore
 * numbers 0, 1, 1.5, 2:
 * </p>
 * <pre>
 * &lt;module name="MagicNumber"&gt;
 *    &lt;property name="ignoreNumbers" value="0, 1, 1.5, 2"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @author Lars Kühne
 */
public class MagicNumberCheck extends Check
{
    /**
     * The token types that are allowed in the AST path from the
     * number literal to the enclosing constant definition.
     */
    private static final int[] ALLOWED_PATH_TOKENTYPES = {
        TokenTypes.ASSIGN,
        TokenTypes.ARRAY_INIT,
        TokenTypes.EXPR,
        TokenTypes.UNARY_PLUS,
        TokenTypes.UNARY_MINUS,
        TokenTypes.TYPECAST,
        TokenTypes.ELIST,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.STAR,
    };

    static {
        Arrays.sort(ALLOWED_PATH_TOKENTYPES);
    }

    /** the numbers to ignore in the check, sorted */
    private double[] mIgnoreNumbers = {-1, 0, 1, 2};

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (inIgnoreList(aAST)) {
            return;
        }

        final DetailAST constantDefAST = findContainingConstantDef(aAST);

        if (constantDefAST == null) {
            reportMagicNumber(aAST);
        }
        else {
            DetailAST ast = aAST.getParent();
            while (ast != constantDefAST) {
                final int type = ast.getType();
                if (Arrays.binarySearch(ALLOWED_PATH_TOKENTYPES, type) < 0) {
                    reportMagicNumber(aAST);
                    break;
                }

                ast = ast.getParent();
            }
        }
    }

    /**
     * Finds the constant definition that contains aAST.
     * @param aAST the AST
     * @return the constant def or null if aAST is not
     * contained in a constant definition
     */
    private DetailAST findContainingConstantDef(DetailAST aAST)
    {
        DetailAST varDefAST = aAST;
        while ((varDefAST != null)
                && (varDefAST.getType() != TokenTypes.VARIABLE_DEF)
                && (varDefAST.getType() != TokenTypes.ENUM_CONSTANT_DEF))
        {
            varDefAST = varDefAST.getParent();
        }

        // no containing variable definition?
        if (varDefAST == null) {
            return null;
        }

        // implicit constant?
        if (ScopeUtils.inInterfaceOrAnnotationBlock(varDefAST)
            || (varDefAST.getType() == TokenTypes.ENUM_CONSTANT_DEF))
        {
            return varDefAST;
        }

        // explicit constant
        final DetailAST modifiersAST =
                varDefAST.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiersAST.branchContains(TokenTypes.FINAL)) {
            return varDefAST;
        }

        return null;
    }

    /**
     * Reports aAST as a magic number, includes unary operators as needed.
     * @param aAST the AST node that contains the number to report
     */
    private void reportMagicNumber(DetailAST aAST)
    {
        String text = aAST.getText();
        final DetailAST parent = aAST.getParent();
        DetailAST reportAST = aAST;
        if (parent.getType() == TokenTypes.UNARY_MINUS) {
            reportAST = parent;
            text = "-" + text;
        }
        else if (parent.getType() == TokenTypes.UNARY_PLUS) {
            reportAST = parent;
            text = "+" + text;
        }
        log(reportAST.getLineNo(),
                reportAST.getColumnNo(),
                "magic.number",
                text);
    }

    /**
     * Decides whether the number of an AST is in the ignore list of this
     * check.
     * @param aAST the AST to check
     * @return true if the number of aAST is in the ignore list of this
     * check.
     */
    private boolean inIgnoreList(DetailAST aAST)
    {
        double value = CheckUtils.parseDouble(aAST.getText(), aAST.getType());
        final DetailAST parent = aAST.getParent();
        if (parent.getType() == TokenTypes.UNARY_MINUS) {
            value = -1 * value;
        }
        return (Arrays.binarySearch(mIgnoreNumbers, value) >= 0);
    }

    /**
     * Sets the numbers to ignore in the check.
     * BeanUtils converts numeric token list to double array automatically.
     * @param aList list of numbers to ignore.
     */
    public void setIgnoreNumbers(double[] aList)
    {
        if ((aList == null) || (aList.length == 0)) {
            mIgnoreNumbers = new double[0];
        }
        else {
            mIgnoreNumbers = new double[aList.length];
            System.arraycopy(aList, 0, mIgnoreNumbers, 0, aList.length);
            Arrays.sort(mIgnoreNumbers);
        }
    }
}
