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
 * Checks the create and find methods of a local home interface.
 * @author Rick Giles
 */
public class LocalHomeInterfaceCheck
    extends AbstractInterfaceCheck
{
    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void visitToken(DetailAST aAST)
    {
        if (Utils.hasExtends(aAST, "javax.ejb.EJBLocalHome")) {
            checkMethods(aAST);
        }
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.checks.j2ee.AbstractInterfaceCheck
     */
    protected void checkMethods(DetailAST aAST)
    {
        super.checkMethods(aAST);

        // a home interface must have a findByPrimaryKey method
        checkFindByPrimaryKey(aAST);
             
        // every method must not throw java.rmi.RemoteException
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.METHOD_DEF) {
                    if (Utils.hasThrows(child, "java.rmi.RemoteException")) {
                        final DetailAST nameAST =
                            child.findFirstToken(TokenTypes.IDENT);
                        final String name = nameAST.getText();
                        log(nameAST.getLineNo(), nameAST.getColumnNo(),
                            "illegalthrows.bean",
                             new Object[] {name, "java.rmi.RemoteException"});
                    }
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
    }
}
