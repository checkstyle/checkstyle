////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import antlr.CommonAST;
import antlr.Token;

/**
 * <B>WARNING: This class is not part of the official Checkstyle API and should
 * not be used! It is declared <code>public</code> because it must be created by
 * the ANTLR classes.</b>
 * <p>
 * An extension of the CommonAST that records the line and column number.
 * The idea was taken from http://www.jguru.com/jguru/faq/view.jsp?EID=62654.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
public class MyCommonAST
    extends CommonAST
{
    /** the line number **/
    private int mLineNo = 0;
    /** the column number **/
    private int mColumnNo = 0;

    /** @see antlr.CommonAST **/
    public void initialize(Token aTok)
    {
        super.initialize(aTok);
        mLineNo = aTok.getLine();
        mColumnNo = aTok.getColumn() - 1; // expect columns to start @ 0
    }

    /** @return the line number **/
    public int getLineNo()
    {
        return mLineNo;
    }

    /** @return the column number **/
    public int getColumnNo()
    {
        return mColumnNo;
    }
}
