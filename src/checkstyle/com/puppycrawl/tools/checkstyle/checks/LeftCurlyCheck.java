/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 13/10/2002
 * Time: 20:45:56
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

public abstract class LeftCurlyCheck extends Check
{
    protected LeftCurlyOption mOption = LeftCurlyOption.EOL;
    // TODO: remove hack
    protected int mMaxLineLength = 80;

    public void setOption(String aFromStr)
    {
        mOption = LeftCurlyOption.decode(aFromStr);
    }

    public void setMaxLineLength(int aMaxLineLength)
    {
        mMaxLineLength = aMaxLineLength;
    }

    protected void verifyBrace(final DetailAST aBrace, final DetailAST aStartToken)
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
        else if (mOption == LeftCurlyOption.NL) {
            if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.new", "{");
            }
        }
        else if (mOption == LeftCurlyOption.EOL) {
            if (Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)
                && ((prevLineLen + 2) <= mMaxLineLength))
            {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.previous", "{");
            }
        }
        else if (mOption == LeftCurlyOption.NLOW) {
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
