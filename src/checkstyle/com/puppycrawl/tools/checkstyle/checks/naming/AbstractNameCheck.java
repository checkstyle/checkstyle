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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * Abstract class for checking that names conform to a specified format.
 *
 * @author Rick Giles
 * @version 1.0
 */
public abstract class AbstractNameCheck
    extends AbstractFormatCheck
{
    /**
     * Creates a new <code>AbstractNameCheck</code> instance.
     * @param aFormat format to check with
     */
    public AbstractNameCheck(String aFormat)
    {
        super(aFormat);
    }

    /**
     * Decides whether the name of an AST should be checked against
     * the format regexp.
     * @param aAST the AST to check.
     * @return true if the IDENT subnode of aAST should be checked against
     * the format regexp.
     */
    protected boolean mustCheckName(DetailAST aAST)
    {
        return true;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (mustCheckName(aAST)) {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            if (!getRegexp().matcher(nameAST.getText()).find()) {
                log(nameAST.getLineNo(),
                    nameAST.getColumnNo(),
                    "name.invalidPattern",
                    nameAST.getText(),
                    getFormat());
            }
        }
    }
}
