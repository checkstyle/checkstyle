package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for long constructors.
 *
 * @author Rick Giles
 */
public class ConstructorLengthCheck extends Check
{
    /** the maximum number of lines */
    private int mMax = 150;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.CTOR_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() != TokenTypes.CTOR_DEF) {
            return;
        }

        DetailAST openingBrace = aAST.getLastChild();
        DetailAST closingBrace = openingBrace.getLastChild();
        int constructorBodyStart = openingBrace.getLineNo();
        int constructorBodyEnd = closingBrace.getLineNo();
        int length = constructorBodyEnd - constructorBodyStart + 1;
        if (length > mMax) {
            // TODO: This is old style but shouldn't we use aAST.getLineNo() ?
            log(openingBrace.getLineNo(), "maxLen.constructor",
                    new Integer(length), new Integer(mMax));
        }
    }

    /**
     * @param aLength the maximum length of a Java source file
     */
    public void setMax(int aLength)
    {
        mMax = aLength;
    }

}
