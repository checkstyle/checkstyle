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

/**
 * Checks that the header of the source file is correct.
 *
 * <p>
 * Rationale: In most projects each file must have a fixed header,
 * usually the header contains copyright information.
 * </p>
 *
 * @author Oliver Burn
 */
public class TypeNameCheck
    extends AbstractFormatCheck
{

    /**
     * Creates a new <code>TypeNameCheck</code> instance.
     */
    public TypeNameCheck()
    {
        super("^[A-Z][a-zA-Z0-9]*$");
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.CLASS_DEF,
                          JavaTokenTypes.INTERFACE_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST nameAST =
            (DetailAST) aAST.getFirstChild().getNextSibling();
        if (!getRegexp().match(nameAST.getText())) {
            log(nameAST.getLineNo(),
                nameAST.getColumnNo(),
                "name.invalidPattern",
                nameAST.getText(),
                getFormat());
        }
    }
}
