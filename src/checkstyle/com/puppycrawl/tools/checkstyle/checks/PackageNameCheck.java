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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

public class PackageNameCheck
    extends AbstractFormatCheck
{
    /**
     * Creates a new <code>PackageNameCheck</code> instance.
     */
    public PackageNameCheck()
    {
        // Uppercase letters seem rather uncommon, but they're allowed in
        // http://java.sun.com/docs/books/jls/
        //   second_edition/html/packages.doc.html#40169
        super("^[a-z]+(\\.[a-zA-Z_][a-zA-Z_0-9]*)*$");
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.PACKAGE_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST nameAST = (DetailAST) aAST.getFirstChild();
        final StringBuffer buf = new StringBuffer();
        extractIdent(buf, nameAST);
        final String text = buf.toString();

        if (!getRegexp().match(text)) {
            log(nameAST.getLineNo(),
                "name.invalidPattern",
                text,
                getFormat());
        }
    }

    // TODO: refactor to Utils. It should return the text, plus the starting
    // line and column
    private static void extractIdent(StringBuffer aBuf, DetailAST aAST)
    {
        if (aAST == null) {
            System.out.println("CALLED WITH NULL");
            return;
        }

        if (aAST.getType() == JavaTokenTypes.DOT) {
            extractIdent(aBuf, (DetailAST) aAST.getFirstChild());
            aBuf.append(".");
            extractIdent(aBuf,
                         (DetailAST) aAST.getFirstChild().getNextSibling());
        }
        else {
            aBuf.append(aAST.getText());
        }
    }
}
