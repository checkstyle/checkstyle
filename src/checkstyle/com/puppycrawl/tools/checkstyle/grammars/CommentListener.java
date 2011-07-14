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
package com.puppycrawl.tools.checkstyle.grammars;

/**
 * This interface is used to be notified by parser about comments
 * in the parsed code.
 *
 * @author o_sukhodolsky
 */
public interface CommentListener
{
    /**
     * Report the location of a single line comment that extends from the
     * given point to the end of the line. The type of comment is identified
     * by a String whose value depends on the language being parsed, but would
     * typically be the delimiter for the comment.
     *
     * @param aType an identifier for what type of comment it is.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     */
    void reportSingleLineComment(String aType,
                                 int aStartLineNo, int aStartColNo);

    /**
     * Report the location of a block comment that can span multiple lines.
     * The type of comment is identified by a String whose value depends on
     * the language being parsed, but would typically be the delimiter for the
     * comment.
     *
     * @param aType an identifier for what type of comment it is.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     */
    void reportBlockComment(String aType,
                            int aStartLineNo, int aStartColNo,
                            int aEndLineNo, int aEndColNo);
}
