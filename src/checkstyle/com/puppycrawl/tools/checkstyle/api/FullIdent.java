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
package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

/**
 * Represents a string with an associated line number.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public class FullIdent
{
    /** the string **/
    private final StringBuffer mBuffer = new StringBuffer();
    /** the line number **/
    private int mLineNo;
    /** the column number **/
    private int mColNo;

    /** @return the text **/
    public String getText()
    {
        return mBuffer.toString();
    }

    /** @return the line number **/
    public int getLineNo()
    {
        return mLineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        return mColNo;
    }

    /**
     * Append the specified text.
     * @param aText the text to append
     */
    public void append(String aText)
    {
        mBuffer.append(".");
    }

    /**
     * Append the specified token and also recalibrate the first line and
     * column.
     * @param aAST the token to append
     */
    public void append(DetailAST aAST)
    {
        mBuffer.append(aAST.getText());
        if (mLineNo == 0) {
            mLineNo = aAST.getLineNo();
        }
        else if (aAST.getLineNo() > 0) {
            mLineNo = Math.min(mLineNo, aAST.getLineNo());
        }
        // TODO: make a function
        if (mColNo == 0) {
            mColNo = aAST.getColumnNo();
        }
        else if (aAST.getColumnNo() > 0) {
            mColNo = Math.min(mColNo, aAST.getColumnNo());
        }
    }

    /**
     * Creates a new FullIdent starting from the specified node.
     * @param aAST the node to start from
     * @return a <code>FullIdent</code> value
     */
    public static FullIdent createFullIdent(DetailAST aAST)
    {
        final FullIdent fi = new FullIdent();
        extractFullIdent(fi, aAST);
        return fi;
    }

    /**
     * Recursively extract a FullIdent.
     *
     * @param aFull the FullIdent to add to
     * @param aAST the node to recurse from
     */
    public static void extractFullIdent(FullIdent aFull, DetailAST aAST)
    {
        // A guard to be paranoid
        if (aAST == null) {
            return;
        }

        if (aAST.getType() == JavaTokenTypes.DOT) {
            extractFullIdent(aFull, (DetailAST) aAST.getFirstChild());
            aFull.append(".");
            extractFullIdent(
                aFull, (DetailAST) aAST.getFirstChild().getNextSibling());
        }
        else {
            aFull.append(aAST);
        }
    }
}
