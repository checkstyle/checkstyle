////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
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
 */
public class MagicNumberCheck extends Check
{
    /** octal radix */
    private static final int BASE_8 = 8;

    /** decimal radix */
    private static final int BASE_10 = 10;

    /** hex radix */
    private static final int BASE_16 = 16;

    /** the numbers to ignore in the check, sorted */
    private float[] mIgnoreNumbers = {-1, 0, 1, 2};

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (!inIgnoreList(aAST) && !isConstantDefinition(aAST)) {
            log(aAST.getLineNo(),
                aAST.getColumnNo(),
                "magic.number",
                aAST.getText());
        }
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
        final float value = parseFloat(aAST.getText(), aAST.getType());
        return (Arrays.binarySearch(mIgnoreNumbers, value) >= 0);
    }

    /**
     * Returns the value represented by the specified string of the specified
     * type. Returns 0 for types other than float, double, int, and long.
     * @param aText the string to be parsed.
     * @param aType the token type of the text. Should be a constant of
     * {@link com.puppycrawl.tools.checkstyle.api.TokenTypes}.
     * @return the float value represented by the string argument.
     */
    private float parseFloat(String aText, int aType)
    {
        float result = 0;
        if (aType == TokenTypes.NUM_FLOAT) {
            result = Float.parseFloat(aText);
        }
        if (aType == TokenTypes.NUM_DOUBLE) {
            result = (float) Double.parseDouble(aText);
        }
        else {
            int radix = BASE_10;
            if (aText.startsWith("0x") || aText.startsWith("0X")) {
                radix = BASE_16;
                aText = aText.substring(2);
            }
            else if (aText.charAt(0) == '0') {
                radix = BASE_8;
                aText = aText.substring(1);
            }
            if (aType == TokenTypes.NUM_INT) {
                if (aText.length() > 0) {
                    result = (float) Integer.parseInt(aText, radix);
                }
            }
            else if (aType == TokenTypes.NUM_LONG) {
                // Long.parseLong requires that the text ends with neither 'L'
                // nor 'l'.
                if ((aText.endsWith("L")) || (aText.endsWith("l"))) {
                    aText = aText.substring(0, aText.length() - 1);
                }
                if (aText.length() > 0) {
                    result = (float) Long.parseLong(aText, radix);
                }
            }
        }
        return result;
    }

    /**
     * Decides whether the number of an AST is the RHS of a constant
     * definition.
     * @param aAST the AST to check.
     * @return true if the number of aAST is the RHS of a constant definition.
     */
    private boolean isConstantDefinition(DetailAST aAST)
    {
        DetailAST parent = aAST.getParent();

        //expression?
        if ((parent == null) || (parent.getType() != TokenTypes.EXPR)) {
            return false;
        }

        //assignment?
        parent = parent.getParent();
        if ((parent == null) || (parent.getType() != TokenTypes.ASSIGN)) {
            return false;
        }

        //variable definition?
        parent = parent.getParent();
        if ((parent == null) || (parent.getType() != TokenTypes.VARIABLE_DEF)) {
            return false;
        }

        //final?
        final DetailAST modifiersAST =
            parent.findFirstToken(TokenTypes.MODIFIERS);
        return modifiersAST.branchContains(TokenTypes.FINAL);
    }

    /**
     * Sets the numbers to ignore in the check.
     * BeanUtils converts numeric token list to float array automatically.
     * @param aList list of numbers to ignore.
     */
    public void setIgnoreNumbers(float[] aList)
    {
        if ((aList == null) || (aList.length == 0)) {
            mIgnoreNumbers = new float[0];
        }
        else {
            mIgnoreNumbers = new float[aList.length];
            System.arraycopy(aList, 0, mIgnoreNumbers, 0, aList.length);
            Arrays.sort(mIgnoreNumbers);
        }
    }
}
