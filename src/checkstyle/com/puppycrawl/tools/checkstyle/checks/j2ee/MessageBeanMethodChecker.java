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
 * Checks the methods of a message bean.
 * Reference: Enterprise JavaBeansTM Specification,Version 2.0, section 15.7.
 * @author Rick Giles
 */
public class MessageBeanMethodChecker
    extends BeanMethodChecker
{
    /**
     * Constructs a <code>MessageBeanMethodChecker</code>.
     * @param aCheck the message bean check.
     */
    public MessageBeanMethodChecker(MessageBeanCheck aCheck)
    {
        super(aCheck);
    }

    /** true if the message bean has an ejbCreate() method */
    private boolean mHasEjbCreate;

    /**
     * {@inheritDoc}
     */
    public void checkMethods(DetailAST aAST)
    {
        mHasEjbCreate = false;

        super.checkMethods(aAST);

        if (!mHasEjbCreate) {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();
            final String arg = "Message bean '" + name + "'";
            log(aAST, "missingmethod.bean",
                new Object[] {arg, "ejbCreate()"});
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void checkCreateMethod(DetailAST aMethodAST)
    {
        final DetailAST nameAST = aMethodAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (name.equals("ejbCreate")) {
            super.checkCreateMethod(aMethodAST);
            mHasEjbCreate = true;

            // the return type must be void
            if (!Utils.isVoid(aMethodAST)) {
                logName(aMethodAST, "nonvoidmethod.bean", new Object[] {});
            }

            // the method must have no parameters
            final DetailAST paramAST =
                aMethodAST.findFirstToken(TokenTypes.PARAMETERS);
            final int paramCount = paramAST.getChildCount();
            if (paramCount != 0) {
                logName(aMethodAST, "paramcount.bean", new Object[] {"0"});
            }
        }
    }
}
