/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 13/10/2002
 * Time: 20:45:56
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Abstract class for checks for Left Curly placement.
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public abstract class LeftCurlyCheck
    extends AbstractOptionCheck
{
    /** TODO: replace this ugly hack **/
    private int mMaxLineLength = 80;

    /**
     * Sets the left curly otion to eol.
     */  
    public LeftCurlyCheck()
    {
        super(LeftCurlyOption.EOL);
    }
    
    /** @see the hack above */
    public void setMaxLineLength(int aMaxLineLength)
    {
        mMaxLineLength = aMaxLineLength;
    }

    /**
     * Verifies that a specified left curly brace is placed correctly
     * according to policy.
     * @param aBrace token for left curly brace
     * @param aStartToken token for start of expression
     */
    protected void verifyBrace(final DetailAST aBrace,
                               final DetailAST aStartToken)
    {
        final String braceLine = getLines()[aBrace.getLineNo() - 1];

        // calculate the previous line length without trailing whitespace. Need
        // to handle the case where there is no previous line, cause the line
        // being check is the first line in the file.
        final int prevLineLen = (aBrace.getLineNo() == 1)
            ? mMaxLineLength
            : Utils.lengthMinusTrailingWhitespace(
                getLines()[aBrace.getLineNo() - 2]);

        // Check for being told to ignore, or have '{}' which is a special case
        if ((braceLine.length() > (aBrace.getColumnNo() + 1))
            && (braceLine.charAt(aBrace.getColumnNo() + 1) == '}'))
        {
            // ignore
        }
        else if (getAbstractOption() == LeftCurlyOption.NL) {
            if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.new", "{");
            }
        }
        else if (getAbstractOption() == LeftCurlyOption.EOL) {
            if (Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)
                && ((prevLineLen + 2) <= mMaxLineLength))
            {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.previous", "{");
            }
        }
        else if (getAbstractOption() == LeftCurlyOption.NLOW) {
            if (aStartToken.getLineNo() == aBrace.getLineNo()) {
                // all ok as on the same line
            }
            else if ((aStartToken.getLineNo() + 1) == aBrace.getLineNo()) {
                if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                    log(aBrace.getLineNo(), aBrace.getColumnNo(),
                        "line.new", "{");
                }
                else if ((prevLineLen + 2) <= mMaxLineLength) {
                    log(aBrace.getLineNo(), aBrace.getColumnNo(),
                        "line.previous", "{");
                }
            }
            else if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.new", "{");
            }
        }
    }
}
