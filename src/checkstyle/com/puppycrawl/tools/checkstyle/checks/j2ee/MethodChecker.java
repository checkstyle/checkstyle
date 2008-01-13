////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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
 * Root class for method checks for a client Check.
 * @author Rick Giles
 */
public abstract class MethodChecker
{
    /** the client Check */
    private final AbstractJ2eeCheck mCheck;

    /**
     * Constructs a <code>MethodChecker</code>.
     * @param aCheck the client Check.
     */
    public MethodChecker(AbstractJ2eeCheck aCheck)
    {
        mCheck = aCheck;
    }

    /**
     * Gets the client Check.
     * @return the client Check.
     */
    protected AbstractJ2eeCheck getCheck()
    {
        return mCheck;
    }

    /**
     * Checks that the methods of a component satisfy requirements.
     * @param aAST the AST for the component definition.
     */
    public void checkMethods(DetailAST aAST)
    {
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = (DetailAST) objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.METHOD_DEF) {
                    checkMethod(child);
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
    }

    /**
     * Checks whether a method satisfies component requirements.
     * @param aMethodAST the AST for the method definition.
     */
    public abstract void checkMethod(DetailAST aMethodAST);

    /**
     * Checks whether a method satisfies public, non-static, and final
     * requirements.
     * @param aMethodAST AST for the method definition.
     * @param aAllowFinal true if the method may be final.
     */
    public void checkMethod(
        DetailAST aMethodAST,
        boolean aAllowFinal)
    {
        // must be declared as public
        if (!Utils.isPublic(aMethodAST)) {
            logName(aMethodAST, "nonpublicmethod.bean");
        }

        // declared as final?
        if (!aAllowFinal && Utils.isFinal(aMethodAST)) {
            logName(aMethodAST, "illegalmodifiermethod.bean", "final");
        }

        // must not be declared as static
        if (Utils.isStatic(aMethodAST)) {
            logName(aMethodAST, "illegalmodifiermethod.bean", "static");
        }
    }

    /**
     * Checks that the throws clause of a method definition includes an
     * Exception.
     * @param aMethodAST the AST for the method definition.
     * @param aException the name of the Exception to check.
     */
    protected void checkThrows(DetailAST aMethodAST, String aException)
    {
        if (!Utils.hasThrows(aMethodAST, aException)) {
            logName(aMethodAST, "missingthrows.bean",
                new Object[] {aException});
        }
    }

    /**
     * Checks that the throws clause of a method definition does no include an
     * Exception.
     * @param aMethodAST the AST for the method definition.
     * @param aException the name of the Exception to check.
     */
    protected void checkNotThrows(DetailAST aMethodAST, String aException)
    {
        if (Utils.hasThrows(aMethodAST, aException)) {
            logName(aMethodAST, "hasthrows.bean", aException);
        }
    }

    /**
     * Logs an error message for a method.
     * @param aMethodAST the AST for the method definition.
     * @param aKey key for message.
     * @param aArgs message arguments.
     */
    protected void log(DetailAST aMethodAST, String aKey, Object... aArgs)
    {
        mCheck.mylog(aMethodAST, aKey, aArgs);
    }

    /**
     * Logs an error message for a method, including the method name.
     * @param aMethodAST the AST for the method definition.
     * @param aKey key for message.
     * @param aArgs message arguments.
     */
    protected void logName(DetailAST aMethodAST, String aKey, Object... aArgs)
    {
        mCheck.logName(aMethodAST, aKey, aArgs);
    }
}
