////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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
 * Checks methods of a remote interface.
 * @author Rick Giles
 */
public class RemoteInterfaceMethodChecker
    extends MethodChecker
{
    /**
     * Constructs a <code>RemoteInterfaceMethodChecker</code> for a
     * remote interface check.
     * @param aCheck the remote interface check.
     */
    public RemoteInterfaceMethodChecker(RemoteInterfaceCheck aCheck)
    {
        super(aCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMethod(DetailAST aMethodAST)
    {
        // every method must throw java.rmi.RemoteException
        checkThrows(aMethodAST, "java.rmi.RemoteException");
    }
}
