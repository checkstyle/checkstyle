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

/**
 * Enumeration class for tokens.
 *
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 */
final class MyToken
{
    /** represents a semi-colon **/
    static final MyToken SEMI_COLON = new MyToken("';'");
    /** represents a comma **/
    static final MyToken COMMA = new MyToken("','");
    /** represents a type cast **/
    static final MyToken CAST = new MyToken("cast");

    /** the text to display for the token **/
    private final String mText;

    /**
     * Creates a new <code>MyToken</code> instance.
     * @param aText the text to display for the token
     */
    MyToken(String aText)
    {
        mText = aText;
    }

    /** @return the text to display for the token **/
    String getText()
    {
        return mText;
    }
}
