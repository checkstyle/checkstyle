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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks methods of entity beans with bean-managed persistence.
 * Reference: Enterprise JavaBeansTM Specification,Version 2.0, Chapter 12
 * @author Rick Giles
 */
public class BeanManagedMethodChecker
    extends EntityBeanMethodChecker
{
    /** true if the bean has method ejbFindByPrimaryKey */
    private boolean mHasEjbFindByPrimaryKey;

    /**
     * Constructs a <code>BeanManagedMethodChecker</code>.
     * @param aCheck the entity bean check.
     */
    public BeanManagedMethodChecker(EntityBeanCheck aCheck)
    {
        super(aCheck);
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.checks.j2ee.MethodChecker
     */
    public void checkMethods(DetailAST aAST)
    {
        mHasEjbFindByPrimaryKey = false;
        super.checkMethods(aAST);
        if (!mHasEjbFindByPrimaryKey) {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();
            final String arg = "Entity bean '" + name + "'";
            log(aAST, "missingmethod.bean",
                new Object[] {arg, "ejbFindByPrimaryKey"});
        }
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.checks.j2ee.MethodChecker
     */
    public void checkMethod(DetailAST aMethodAST)
    {
        super.checkMethod(aMethodAST);

        final DetailAST nameAST = aMethodAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();

        if (name.startsWith("ejbFind")) {
            if (name.equals("ejbFindByPrimaryKey")) {
                mHasEjbFindByPrimaryKey = true;
                // the method must have one parameter
                final DetailAST paramAST =
                    aMethodAST.findFirstToken(TokenTypes.PARAMETERS);
                final int paramCount = paramAST.getChildCount();
                if (paramCount != 1) {
                    logName(aMethodAST, "paramcount.bean", new Object[] {"1"});
                }
            }
            checkFindMethod(aMethodAST);
        }
    }

    /**
      * Checks whether an ejbFind&lt;METHOD&gt;(...) method of an
      * entity bean satisfies requirements.
      * @param aMethodAST the AST for the method definition.
      */
    private void checkFindMethod(DetailAST aMethodAST)
    {
        // the method must not be final
        super.checkMethod(aMethodAST, false);

        // The return type must be the entity bean's primary key type,
        // or a collection of primary keys
        if (Utils.isVoid(aMethodAST)) {
            logName(aMethodAST, "voidmethod.bean", new Object[] {});
        }
    }
}
