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
package com.puppycrawl.tools.checkstyle;

import java.util.Stack;

import org.apache.regexp.RE;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Verifier of Java rules. Each rule verifier takes the form of
 * <code>void verifyXXX(args)</code>. The implementation must not throw any
 * exceptions.
 * <P>
 * Line numbers start from 1, column numbers start for 0.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class Verifier
{
    // {{{ Data declarations

    ////////////////////////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////////////////////////

    /** stack tracking the type of block currently in **/
    private final Stack mInInterface = new Stack();

    /** tracks the level of block definitions for methods **/
    private int mMethodBlockLevel = 0;

    /** the messages being logged **/
    private final LocalizedMessages mMessages;

    /** the lines of the file being checked **/
    private String[] mLines;

    /** configuration for checking **/
    private final Configuration mConfig;

    // }}}

    // {{{ Constructors
    ////////////////////////////////////////////////////////////////////////////
    // Constructor methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs the object.
     * @param aConfig the configuration to use for checking
     **/
    Verifier(Configuration aConfig)
    {
        mConfig = aConfig;
        mMessages = new LocalizedMessages(mConfig.getTabWidth());
    }

    // }}}

    // {{{ Interface verifier methods
    ////////////////////////////////////////////////////////////////////////////
    // Interface Verifier methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the ordered list of error messages.
     * @return the list of messages
     **/
    LocalizedMessage[] getMessages()
    {
        return mMessages.getMessages();
    }

    /** Resets the verifier. Use before processing a file. **/
    void reset()
    {
        mLines = null;
        mInInterface.clear();
        mMessages.reset();
        mMethodBlockLevel = 0;
    }

    /**
     * Sets the lines for the file being checked.
     * @param aLines the lines of the file
     **/
    void setLines(String[] aLines)
    {
        mLines = aLines;
        mMessages.setLines(mLines);
    }

    /**
     * Verify that no whitespace after an AST.
     * @param aAST the AST to check
     **/
    void verifyNoWSAfter(MyCommonAST aAST)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aAST.getLineNo() - 1];
        final int after = aAST.getColumnNo() + aAST.getText().length();
        if ((after >= line.length())
            || Character.isWhitespace(line.charAt(after)))
        {
            mMessages.add(aAST.getLineNo(), after,
                          "ws.followed", aAST.getText());
        }
    }


    /**
     * Verify that no whitespace before an AST.
     * @param aAST the AST to check
     **/
    void verifyNoWSBefore(MyCommonAST aAST)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        if ((before < 0) || Character.isWhitespace(line.charAt(before))) {
            mMessages.add(aAST.getLineNo(), before,
                          "ws.preceeded", aAST.getText());
        }
    }

    /**
     * Verify that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aConstruct the construct being checked
     */
    void verifyWSAfter(int aLineNo, int aColNo, MyToken aConstruct)
    {
        verifyWSAfter(aLineNo, aColNo, aConstruct, "");
    }

    /**
     * Verify that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aConstruct the construct being checked
     * @param aAllow other character to allow apart from whitespace
     */
    void verifyWSAfter(int aLineNo, int aColNo,
                       MyToken aConstruct, String aAllow)
    {
        checkWSAfter(aLineNo, aColNo, aConstruct, aAllow);
    }


    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     **/
    void reportCPPComment(int aStartLineNo, int aStartColNo)
    {
        final String cmt = mLines[aStartLineNo - 1].substring(aStartColNo);
        if (mConfig.getTodoRegexp().match(cmt)) {
            mMessages.add(aStartLineNo, "todo.match", mConfig.getTodoPat());
        }
    }

    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    void reportCComment(int aStartLineNo, int aStartColNo,
                        int aEndLineNo, int aEndColNo)
    {
        final String[] cc = extractCComment(aStartLineNo, aStartColNo,
                                            aEndLineNo, aEndColNo);

        // Check for to-do comments
        for (int i = 0; i < cc.length; i++) {
            if (mConfig.getTodoRegexp().match(cc[i])) {
                mMessages.add(aStartLineNo + i, "todo.match",
                              mConfig.getTodoPat());
            }
        }
    }


    /**
     * Report the name of the package the file is in.
     * @param aName the name of the package
     **/
    void reportPackageName(LineText aName)
    {
    }


    /**
     * Report the location of an import.
     * @param aLineNo the line number
     * @param aType the type imported
     **/
    void reportImport(int aLineNo, String aType)
    {
    }


    /**
     * Report the location of an import using a ".*".
     * @param aLineNo the line number
     * @param aPkg the package imported
     **/
    void reportStarImport(int aLineNo, String aPkg)
    {
    }


    /**
     * Report that the parser is entering a block that is associated with a
     * class or interface. Must match up the call to this method with a call
     * to the reportEndBlock().
     * @param aScope the Scope of the type block
     * @param aIsInterface indicates if the block is for an interface
     * @param aType the name of the type
     */
    void reportStartTypeBlock(Scope aScope,
                              boolean aIsInterface,
                              MyCommonAST aType)
    {
        mInInterface.push(aIsInterface ? Boolean.TRUE : Boolean.FALSE);
    }


    /**
     * Report that the parser is leaving a type block.
     * @param aNamed is this a named type block
     */
    void reportEndTypeBlock(boolean aNamed)
    {
        mInInterface.pop();
    }


    /**
     * Report that the parser is entering a block associated with method or
     * constructor.
     **/
    void reportStartMethodBlock()
    {
        mMethodBlockLevel++;
    }


    /**
     * Report that the parser is leaving a block associated with method or
     * constructor.
     **/
    void reportEndMethodBlock()
    {
        mMethodBlockLevel--;
    }

    /**
     * Verify an operator. Checks include that the operator is surrounded by
     * whitespace, and that the operator follows the rules about whether to
     * be at the end of a line.
     * @param aLineNo number of line to check
     * @param aColNo column where the text ends
     * @param aText the text to check
     */
    void verifyOpEnd(int aLineNo, int aColNo, String aText)
    {
        verifyOpBegin(aLineNo, aColNo - aText.length(), aText);
    }

    /**
     * Verify an operator. Checks include that the operator is surrounded by
     * whitespace, and that the operator follows the rules about whether to
     * be at the end of a line.
     * @param aLineNo number of line to check
     * @param aColNo column where the text starts
     * @param aText the text to check
     */
    void verifyOpBegin(int aLineNo, int aColNo, String aText)
    {
        final WrapOpOption wOp = mConfig.getWrapOpOption();

        if (wOp != WrapOpOption.IGNORE) {

            // Check if rest of line is whitespace, and not just the operator
            // by itself. This last bit is to handle the operator on a line by
            // itself.
            if (wOp == WrapOpOption.NL
                && !aText.equals(mLines[aLineNo - 1].trim())
                && (mLines[aLineNo - 1].substring(aColNo + aText.length() - 1)
                    .trim().length() == 0))
            {
                mMessages.add(aLineNo, aColNo - 1, "line.new", aText);
            }
            else if (wOp == WrapOpOption.EOL
                     && Utils.whitespaceBefore(aColNo - 1, mLines[aLineNo - 1]))
            {
                mMessages.add(aLineNo, aColNo - 1, "line.previous", aText);
            }
        }
    }

    // }}}

    // {{{ Private methods
    ////////////////////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the specified C comment as a String array.
     * @return C comment as a array
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    private String[] extractCComment(int aStartLineNo, int aStartColNo,
                                     int aEndLineNo, int aEndColNo)
    {
        String[] retVal;
        if (aStartLineNo == aEndLineNo) {
            retVal = new String[1];
            retVal[0] = mLines[aStartLineNo - 1].substring(aStartColNo,
                                                           aEndColNo + 1);
        }
        else {
            retVal = new String[aEndLineNo - aStartLineNo + 1];
            retVal[0] = mLines[aStartLineNo - 1].substring(aStartColNo);
            for (int i = aStartLineNo; i < aEndLineNo; i++) {
                retVal[i - aStartLineNo + 1] = mLines[i];
            }
            retVal[retVal.length - 1] =
                mLines[aEndLineNo - 1].substring(0, aEndColNo + 1);
        }
        return retVal;
    }

    /** @return whether currently in an interface block **/
    private boolean inInterfaceBlock()
    {
        return (!mInInterface.empty()
                && Boolean.TRUE.equals(mInInterface.peek()));
    }

    /** @return whether currently in a method block **/
    private boolean inMethodBlock()
    {
        return (mMethodBlockLevel > 0);
    }

    /**
     * Checks that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aConstruct the construct being checked
     * @param aAllow characters to allow as well as whitespace
     */
    void checkWSAfter(int aLineNo, int aColNo,
                      MyToken aConstruct, String aAllow)
    {
        if (mConfig.isIgnoreWhitespace()
            || ((MyToken.CAST == aConstruct)
                && mConfig.isIgnoreCastWhitespace()))
        {
            return;
        }

        final String line = mLines[aLineNo - 1];
        if ((aColNo < line.length())
            && !Character.isWhitespace(line.charAt(aColNo))
            && (aAllow.indexOf(line.charAt(aColNo)) == -1))
        {
            mMessages.add(aLineNo, aColNo,
                          "ws.notFollowed", aConstruct.getText());
        }
    }
}
