////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

/**
 * Checks the methods of a local interface.
 * Reference: Enterprise JavaBeansTM Specification,Version 2.1, section 12.2.10.
 * @author Rick Giles
 */
public class LocalInterfaceCheck
    extends AbstractInterfaceCheck
{
    /**
     * Constructs a <code>LocalInterfaceCheck</code>.
     *
     */
    public LocalInterfaceCheck()
    {
        setMethodChecker(new LocalInterfaceMethodChecker(this));
    }
    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public void visitToken(DetailAST aAST)
    {
        if (Utils.hasExtends(aAST, "javax.ejb.EJBLocalObject")) {
            getMethodChecker().checkMethods(aAST);
        }
    }
}
