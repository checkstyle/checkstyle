////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

/**
 * Represents a full identifier, including dots, with associated
 * position information.
 *
 * <p>
 * Identifiers such as <code>java.util.HashMap</code> are spread across
 * multiple AST nodes in the syntax tree (three IDENT nodes, two DOT nodes).
 * A FullIdent represents the whole String (excluding any intermediate
 * whitespace), which is often easier to work with in Checks.
 * </p>
 *
 * @author Oliver Burn
 * @see TokenTypes#DOT
 * @see TokenTypes#IDENT
 **/
public final class FullIdent
{
    /** the string **/
    private final StringBuffer mBuffer = new StringBuffer();
    /** the line number **/
    private int mLineNo;
    /** the column number **/
    private int mColNo;

    /** hide default constructor */
    private FullIdent()
    {
    }

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
    private void append(String aText)
    {
        mBuffer.append(aText);
    }

    /**
     * Append the specified token and also recalibrate the first line and
     * column.
     * @param aAST the token to append
     */
    private void append(DetailAST aAST)
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
     * Creates a new FullIdent starting from the child of the specified node.
     * @param aAST the parent node from where to start from
     * @return a <code>FullIdent</code> value
     */
    public static FullIdent createFullIdentBelow(DetailAST aAST)
    {
        return createFullIdent(aAST.getFirstChild());
    }

    /**
     * Recursively extract a FullIdent.
     *
     * @param aFull the FullIdent to add to
     * @param aAST the node to recurse from
     */
    private static void extractFullIdent(FullIdent aFull, DetailAST aAST)
    {
        if (aAST == null) {
            return;
        }

        if (aAST.getType() == TokenTypes.DOT) {
            extractFullIdent(aFull, aAST.getFirstChild());
            aFull.append(".");
            extractFullIdent(
                aFull, aAST.getFirstChild().getNextSibling());
        }
        else {
            aFull.append(aAST);
        }
    }

    @Override
    public String toString()
    {
        return getText() + "[" + getLineNo() + "x" + getColumnNo() + "]";
    }

}
