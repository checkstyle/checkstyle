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

import com.puppycrawl.tools.checkstyle.JavaTokenTypes;
import com.puppycrawl.tools.checkstyle.LeftCurlyOption;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

public class TypeLeftCurlyCheck
    extends Check
{
    private LeftCurlyOption mOption = LeftCurlyOption.EOL;
    // TODO: remove hack
    private int mMaxLineLength = 80;

    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.INTERFACE_DEF,
                          JavaTokenTypes.CLASS_DEF};
    }

    /** @see Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST brace = (DetailAST)
            Utils.getLastSibling(aAST.getFirstChild())
            .getFirstChild();
        // TODO: should check for modifiers
        final DetailAST startToken =
            (DetailAST) aAST.getFirstChild().getNextSibling();

        final String braceLine = getLines()[brace.getLineNo() - 1];

        // calculate the previous line length without trailing whitespace. Need
        // to handle the case where there is no previous line, cause the line
        // being check is the first line in the file.
        final int prevLineLen = (brace.getLineNo() == 1)
            ? mMaxLineLength
            : Utils.lengthMinusTrailingWhitespace(
                getLines()[brace.getLineNo() - 2]);

        // Check for being told to ignore, or have '{}' which is a special case
        if ((mOption == LeftCurlyOption.IGNORE)
            || ((braceLine.length() > (brace.getColumnNo() + 1))
                && (braceLine.charAt(brace.getColumnNo() + 1) == '}')))
        {
            // ignore
        }
        else if (mOption == LeftCurlyOption.NL) {
            if (!Utils.whitespaceBefore(brace.getColumnNo(), braceLine)) {
                log(brace.getLineNo(), brace.getColumnNo(),
                    "line.new", "{");
            }
        }
        else if (mOption == LeftCurlyOption.EOL) {
            if (Utils.whitespaceBefore(brace.getColumnNo(), braceLine)
                && ((prevLineLen + 2) <= mMaxLineLength))
            {
                log(brace.getLineNo(), brace.getColumnNo(),
                    "line.previous", "{");
            }
        }
        else if (mOption == LeftCurlyOption.NLOW) {
            if (startToken.getLineNo() == brace.getLineNo()) {
                // all ok as on the same line
            }
            else if ((startToken.getLineNo() + 1) == brace.getLineNo()) {
                if (!Utils.whitespaceBefore(brace.getColumnNo(), braceLine)) {
                    log(brace.getLineNo(), brace.getColumnNo(),
                        "line.new", "{");
                }
                else if ((prevLineLen + 2) <= mMaxLineLength) {
                    log(brace.getLineNo(), brace.getColumnNo(),
                        "line.previous", "{");
                }
            }
            else if (!Utils.whitespaceBefore(brace.getColumnNo(), braceLine)) {
                log(brace.getLineNo(), brace.getColumnNo(),
                    "line.new", "{");
            }
        }
    }

    public void setOption(String aFromStr)
    {
        mOption = LeftCurlyOption.decode(aFromStr);
    }

    public void setMaxLineLength(int aMaxLineLength)
    {
        mMaxLineLength = aMaxLineLength;
    }
}
