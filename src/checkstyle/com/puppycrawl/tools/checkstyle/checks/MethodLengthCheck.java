package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

/**
 * Checks for long methods.
 *
 * <p>
 * Rationale: If a mthod becomes very long it is hard to understand.
 * Therefore long methods should usually be refactored into several
 * individual methods that focus on a specific task.
 * </p>
 *
 * @author Lars Kühne
 */
public class MethodLengthCheck extends Check
{
    /** the maximum number of lines */
    private int mMax = 150;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.METHOD_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() != JavaTokenTypes.METHOD_DEF) {
            return;
        }

        DetailAST openingBrace = aAST.getLastChild();
        DetailAST closingBrace = openingBrace.getLastChild();
        int methodBodyStart = openingBrace.getLineNo();
        int methodBodyEnd = closingBrace.getLineNo();
        int length = methodBodyEnd - methodBodyStart + 1;
        if (length > mMax) {
            // TODO: This is old style but shouldn'r we use aAST.getLineNo() ?
            log(openingBrace.getLineNo(), "maxLen.method",
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
