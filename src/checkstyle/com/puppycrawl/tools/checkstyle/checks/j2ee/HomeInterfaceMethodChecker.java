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
 * Checks the methods of a home interface.
 * @author Rick Giles
 */
public abstract class HomeInterfaceMethodChecker
    extends MethodChecker
{
    /** true if the home interface defines method findByPrimaryKey */
    private boolean mHasFindByPrimaryKey;

    /**
     * Constructs a method checker for a home interface check.
     * @param aCheck the home interface check.
     */
    public HomeInterfaceMethodChecker(AbstractInterfaceCheck aCheck)
    {
        super(aCheck);
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.checks.j2ee.MethodChecker
     */
    public void checkMethods(DetailAST aAST)
    {
        mHasFindByPrimaryKey = false;

        super.checkMethods(aAST);

        if (!mHasFindByPrimaryKey) {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();
            final String arg = "Home interface '" + name + "'";
            log(aAST, "missingmethod.bean",
                new Object[] {arg, "findByPrimaryKey()"});
        }
    }

    /**
     *
     * @see com.puppycrawl.tools.checkstyle.checks.j2ee.MethodChecker
     */
    public void checkMethod(DetailAST aMethodAST)
    {
        // every kind of a home interface has create<METHOD>(...)
        // and find<METHOD>(...) requirements
        final DetailAST nameAST = aMethodAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (name.startsWith("create")) {
            checkCreateMethod(aMethodAST);
        }
        else if (name.startsWith("find")) {
            checkFindMethod(aMethodAST);
        }
    }

    /**
     * Checks find&lt;METHOD&gt;(...) method requirements..
     * @param aMethodAST the finder method definition AST.
     */
    protected void checkFindMethod(DetailAST aMethodAST)
    {
        // there must be only one findByPrimaryKey method with one parameter
        final DetailAST nameAST = aMethodAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if (name.equals("findByPrimaryKey")) {
            if (mHasFindByPrimaryKey) {
                logName(aMethodAST, "findbyprimarykey.bean", new Object[] {});
            }
            mHasFindByPrimaryKey = true;
            final DetailAST paramAST =
                aMethodAST.findFirstToken(TokenTypes.PARAMETERS);
            final int paramCount = paramAST.getChildCount();
            if (paramCount != 1) {
                logName(aMethodAST, "paramcount.bean", new Object[] {"1"});
            }
        }

        // return type is the entity bean’s remote or local interface
        if (Utils.isVoid(aMethodAST)) {
            logName(aMethodAST, "voidmethod.bean", new Object[] {});
        }

        // every find method throws a javax.ejb.FindException
        checkThrows(aMethodAST, "javax.ejb.FinderException");
    }

    /**
      * Checks create&lt;METHOD&gt;(...) method requirements.
      * @param aMethodAST the AST for the method definition.
      */
    protected void checkCreateMethod(DetailAST aMethodAST)
    {
        // return type is the entity bean’s remote or local interface
        if (Utils.isVoid(aMethodAST)) {
            logName(aMethodAST, "voidmethod.bean", new Object[] {});
        }

        // every create method throws a javax.ejb.CreateException
        checkThrows(aMethodAST, "javax.ejb.CreateException");
    }
}
