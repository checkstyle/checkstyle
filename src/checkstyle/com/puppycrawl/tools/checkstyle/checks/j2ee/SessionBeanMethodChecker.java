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
 * Checks the methods of a session bean.
 * Reference: Enterprise JavaBeansTM Specification,Version 2.0, section 7.10.2.
 * @author Rick Giles
 */
public class SessionBeanMethodChecker
    extends BeanMethodChecker
{
    /** true if the session bean has an ejbCreate<METHOD>(...) method */
    private boolean mHasEjbCreate;

    /**
     * Creates a method checker for a session bean check.
     * @param aCheck the session bean check.
     */
    public SessionBeanMethodChecker(SessionBeanCheck aCheck)
    {
        super(aCheck);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMethods(DetailAST aAST)
    {
        mHasEjbCreate = false;

        super.checkMethods(aAST);

        // session bean must have an ejbCreate<METHOD>(...) method
        if (!mHasEjbCreate) {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();
            final String arg = "Session bean '" + name + "'";
            log(aAST, "missingmethod.bean", arg, "ejbCreate<METHOD>(...)");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkCreateMethod(DetailAST aMethodAST)
    {
        super.checkCreateMethod(aMethodAST);

        if (Utils.isPublic(aMethodAST)
            && !Utils.isStatic(aMethodAST)
            && !Utils.isFinal(aMethodAST)
            && Utils.isVoid(aMethodAST))
        {
            mHasEjbCreate = true;
        }

        // The return type must be void
        if (!Utils.isVoid(aMethodAST)) {
            log(aMethodAST, "nonvoidmethod.bean");
        }
    }
}
