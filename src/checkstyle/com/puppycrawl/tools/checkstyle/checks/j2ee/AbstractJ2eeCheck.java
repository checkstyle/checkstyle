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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Abstract class for J2ee component checking.
 * @author Rick Giles
 */
public abstract class AbstractJ2eeCheck
    extends Check
{
    /** checks method requirements */
    private MethodChecker mMethodChecker;

    /**
     * Helper method to log a LocalizedMessage for an AST.
     *
     * @param aAST the AST for the message.
     * @param aKey key to locale message format
     * @param aArgs arguments for message
     */
    protected void mylog(DetailAST aAST, String aKey, Object... aArgs)
    {
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        log(nameAST.getLineNo(), nameAST.getColumnNo(), aKey, aArgs);
    }

    /**
     * Helper method to log a LocalizedMessage for an AST.
     * Logs the name, line, and column of the AST.
     *
     * @param aAST the AST for the message.
     * @param aKey key to locale message format
     * @param aArgs arguments for message
     */
    protected void logName(DetailAST aAST, String aKey, Object[] aArgs)
    {
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        final Object[] fullArgs = new Object[aArgs.length + 1];
        System.arraycopy(aArgs, 0, fullArgs, 1, aArgs.length);
        fullArgs[0] = name;
        log(
            nameAST.getLineNo(),
            nameAST.getColumnNo(),
            aKey,
            fullArgs);
    }

    /**
     * Gets the method checker for this component check.
     * @return the method checker for this component check.
     */
    public MethodChecker getMethodChecker()
    {
        return mMethodChecker;
    }

    /**
     * Sets the method checker for this component check.
     * @param aMethodChecker the method checker for this component check.
     */
    public void setMethodChecker(MethodChecker aMethodChecker)
    {
        mMethodChecker = aMethodChecker;
    }
}
