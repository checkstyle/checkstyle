////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.j2ee;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that a SessionBean implementation satisfies SessionBean
 * requirements:
 * <ul>
 * <li>The class is defined as <code>public</code>.</li>
 * <li>The class cannot be defined as <code>abstract</code> or
 * <code>final</code>.</li>
 * <li>It implements one or more <code>ejbCreate</code> methods.</li>
 * <li>It contains a <code>public</code> constructor with no parameters.</li>
 * <li>It must not define the <code>finalize</code> method.</li>
</ul>
 * @author Rick Giles
 */
public class SessionBeanCheck
    extends AbstractBeanCheck
{
    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void visitToken(DetailAST aAST)
    {
        if (Utils.hasImplements(aAST, "javax.ejb.SessionBean")
            && !Utils.isAbstract(aAST))
        {
            checkBean(aAST, "Session");
            if (!Utils.hasPublicMethod(aAST, "ejbCreate", true)) {
                final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
                log(
                    nameAST.getLineNo(),
                    nameAST.getColumnNo(),
                    "missingmethod.bean",
                    new Object[] {"Session", "ejbCreate"});
            }
        }
    }
}
