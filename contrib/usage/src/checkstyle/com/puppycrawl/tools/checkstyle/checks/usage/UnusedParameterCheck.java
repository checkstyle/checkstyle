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
package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * <p>Checks that a parameter is used.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="usage.UnusedParameter"/&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class UnusedParameterCheck extends AbstractUsageCheck
{
    /** controls checking of catch clause parameter */
    private boolean mIgnoreCatch = true;
    /** controls checking of public/protected/package methods */
    private boolean mIgnoreNonLocal;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.PARAMETER_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public String getErrorKey()
    {
        return "unused.parameter";
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public boolean mustCheckReferenceCount(DetailAST aAST)
    {
        boolean result = false;
        final DetailAST parent = aAST.getParent();
        if (parent != null) {
            if (parent.getType() == TokenTypes.PARAMETERS) {
                final DetailAST grandparent = parent.getParent();
                if (grandparent != null) {
                    result = hasBody(grandparent)
                        && (!mIgnoreNonLocal || isLocal(grandparent));
                }
            }
            else if (parent.getType() == TokenTypes.LITERAL_CATCH) {
                result = !mIgnoreCatch;
            }
        }
        return result;
    }

    /**
     * Determines whether an AST is a method definition with a body, or is
     * a constructor definition.
     * @param aAST the AST to check.
     * @return if AST has a body.
     */
    private boolean hasBody(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.METHOD_DEF) {
            return aAST.branchContains(TokenTypes.SLIST);
        }
        else if (aAST.getType() == TokenTypes.CTOR_DEF) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a given method is local, i.e. either static or private.
     * @param aAST method def for check
     * @return true if a given method is iether static or private.
     */
    private boolean isLocal(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST modifiers =
                aAST.findFirstToken(TokenTypes.MODIFIERS);
            return (modifiers == null)
                || modifiers.branchContains(TokenTypes.LITERAL_STATIC)
                || modifiers.branchContains(TokenTypes.LITERAL_PRIVATE);
        }
        return true;
    }

    /**
     * Control whether unused catch clause parameters are flagged.
     * @param aIgnoreCatch whether unused catch clause parameters
     * should be flagged.
     */
    public void setIgnoreCatch(boolean aIgnoreCatch)
    {
        mIgnoreCatch = aIgnoreCatch;
    }

    /**
     * Controls whether public/protected/paskage methods shouldn't be checked.
     * @param aIgnoreNonLocal whether we should check any other methods
     * except static and private should be checked.
     */
    public void setIgnoreNonLocal(boolean aIgnoreNonLocal)
    {
        mIgnoreNonLocal = aIgnoreNonLocal;
    }

}
