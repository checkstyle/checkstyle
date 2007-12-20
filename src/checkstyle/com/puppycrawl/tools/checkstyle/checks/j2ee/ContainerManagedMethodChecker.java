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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks methods of entity beans with container-managed persistence.
 * Reference: Enterprise JavaBeansTM Specification,Version 2.0, Chapter 10
 * @author Rick Giles
 */
public class ContainerManagedMethodChecker
    extends EntityBeanMethodChecker
{
    /**
     * Constructs a ContainerManagedMethodChecker for a entity bean check.
     * @param aCheck the entity bean check.
     */
    public ContainerManagedMethodChecker(EntityBeanCheck aCheck)
    {
        super(aCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMethod(DetailAST aMethodAST)
    {
        super.checkMethod(aMethodAST);

        final DetailAST nameAST = aMethodAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();

        if (name.startsWith("ejbSelect")) {
            checkSelectMethod(aMethodAST);
        }
    }

    /**
      * Checks whether an ejbSelect&lt;METHOD&gt;(...) method of an
      * entity bean satisfies requirements.
      * @param aMethodAST the AST for the method definition.
      */
    protected void checkSelectMethod(DetailAST aMethodAST)
    {
        // must be declared as public
        if (!Utils.isPublic(aMethodAST)) {
            log(aMethodAST, "nonpublic.bean", "Method");
        }
        // The method must be declared as abstract.
        if (!Utils.isAbstract(aMethodAST)) {
            log(aMethodAST, "nonabstract.bean", "Method");
        }
        // The throws clause must define the javax.ejb.FinderException.
        checkThrows(aMethodAST, "javax.ejb.FinderException");
    }

     /**
      * Checks whether an ejbCreate&lt;METHOD&gt;(...) method of an
      * entity bean satisfies requirements.
      * @param aMethodAST the AST for the method definition.
      */
    @Override
    protected void checkCreateMethod(DetailAST aMethodAST)
    {
        super.checkCreateMethod(aMethodAST);

        // The throws clause must define the javax.ejb.CreateException.
        checkThrows(aMethodAST, "javax.ejb.CreateException");
    }
}
