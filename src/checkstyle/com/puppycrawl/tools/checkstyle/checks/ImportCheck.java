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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

/**
 * Abstract base class that provides functionality that is used in import
 * checks.
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public abstract class ImportCheck
    extends Check
{
    /** key to store name of import as */
    private static final String TEXT_KEY = "name";

    /**
     * Return the name of the import associated with a specifed DetailAST.
     *
     * @param aAST the node containing the import
     * @return a <code>String</code> value
     */
    protected String getImportText(DetailAST aAST)
    {
        String text = (String) getTokenContext().get(TEXT_KEY);
        if (text != null) {
            return text;
        }

        final StringBuffer buf = new StringBuffer();
        extractIdent(buf, (DetailAST) aAST.getFirstChild());
        text = buf.toString();
        getTokenContext().put(TEXT_KEY, text);
        return text;
    }

    /**
     * Fills in the name of an import.
     *
     * @param aBuf the StringBuffer to add the name to
     * @param aAST the node to operate on
     */
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
        else if ((aAST.getType() == JavaTokenTypes.IDENT)
                 || (aAST.getType() == JavaTokenTypes.STAR))
        {
            aBuf.append(aAST.getText());
        }
        else {
            System.out.println("********* Got the string " + aAST.getText());
            aBuf.append(aAST.getText());
        }
    }
}
