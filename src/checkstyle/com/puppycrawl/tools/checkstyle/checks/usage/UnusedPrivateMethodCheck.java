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
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>Checks that a private method is used.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="usage.UnusedPrivateMethod"/&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class UnusedPrivateMethodCheck
    extends AbstractUsageCheck

{
    /** Controls if checks skips serialization methods.*/
    private boolean mAllowSerializationMethods;
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public String getErrorKey()
    {
        return "unused.method";
    }

    /**
     * Configure the check to allow (or not) serialization-related methods.
     * @param aFlag new value for allowSerializationMethods value.
     */
    public void setAllowSerializationMethods(boolean aFlag)
    {
        mAllowSerializationMethods = aFlag;
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public boolean mustCheckReferenceCount(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        if ((mods == null)
            || (ScopeUtils.getScopeFromMods(mods) != Scope.PRIVATE))
        {
            return false;
        }

        return !mAllowSerializationMethods
               || !(isWriteObject(aAST) || isReadObject(aAST)
                    || isWriteReplaceOrReadResolve(aAST));
    }

    /**
     * Checks if a given method is writeObject().
     * @param aAST method def to check
     * @return true if this is a writeObject() definition
     */
    private boolean isWriteObject(DetailAST aAST)
    {
        // name is writeObject...
        final DetailAST ident = aAST.findFirstToken(TokenTypes.IDENT);
        if (!"writeObject".equals(ident.getText())) {
            return false;
        }

        // returns void...
        final DetailAST typeAST =
            (DetailAST) aAST.findFirstToken(TokenTypes.TYPE).getFirstChild();
        if (typeAST.getType() != TokenTypes.LITERAL_VOID) {
            return false;
        }

        // should have one parameter...
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        if (params == null || params.getChildCount() != 1) {
            return false;
        }
        // and paramter's type should be java.io.ObjectOutputStream
        final DetailAST type =
            (DetailAST) ((DetailAST) params.getFirstChild())
                .findFirstToken(TokenTypes.TYPE).getFirstChild();
        final String typeName = FullIdent.createFullIdent(type).getText();
        if (!"java.io.ObjectOutputStream".equals(typeName)
            && !"ObjectOutputStream".equals(typeName))
        {
            return false;
        }

        // and, finally, it should throws java.io.IOException
        final DetailAST throwsAST =
            aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST == null || throwsAST.getChildCount() != 1) {
            return false;
        }
        final DetailAST expt = (DetailAST) throwsAST.getFirstChild();
        final String exceptionName = FullIdent.createFullIdent(expt).getText();
        if (!"java.io.IOException".equals(exceptionName)
            && !"IOException".equals(exceptionName))
        {
            return false;
        }

        return true;
    }

    /**
     * Checks if a given method is readObject().
     * @param aAST method def to check
     * @return true if this is a readObject() definition
     */
    private boolean isReadObject(DetailAST aAST)
    {
        // name is readObject...
        final DetailAST ident = aAST.findFirstToken(TokenTypes.IDENT);
        if (!"readObject".equals(ident.getText())) {
            return false;
        }

        // returns void...
        final DetailAST typeAST =
            (DetailAST) aAST.findFirstToken(TokenTypes.TYPE).getFirstChild();
        if (typeAST.getType() != TokenTypes.LITERAL_VOID) {
            return false;
        }

        // should have one parameter...
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        if (params == null || params.getChildCount() != 1) {
            return false;
        }
        // and paramter's type should be java.io.ObjectInputStream
        final DetailAST type =
            (DetailAST) ((DetailAST) params.getFirstChild())
                .findFirstToken(TokenTypes.TYPE).getFirstChild();
        final String typeName = FullIdent.createFullIdent(type).getText();
        if (!"java.io.ObjectInputStream".equals(typeName)
            && !"ObjectInputStream".equals(typeName))
        {
            return false;
        }

        // and, finally, it should throws java.io.IOException
        // and java.lang.ClassNotFoundException
        final DetailAST throwsAST =
            aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST == null || throwsAST.getChildCount() != 3) {
            return false;
        }
        final DetailAST excpt1 = (DetailAST) throwsAST.getFirstChild();
        final String exception1 = FullIdent.createFullIdent(excpt1).getText();
        final String exception2 =
            FullIdent.createFullIdent(throwsAST.getLastChild()).getText();
        if (!"java.io.IOException".equals(exception1)
            && !"IOException".equals(exception1)
            && !"java.io.IOException".equals(exception2)
            && !"IOException".equals(exception2)
            || !"java.lang.ClassNotFoundException".equals(exception1)
            && !"ClassNotFoundException".equals(exception1)
            && !"java.lang.ClassNotFoundException".equals(exception2)
            && !"ClassNotFoundException".equals(exception2))
        {
            return false;
        }

        return true;
    }

    /**
     * Checks if a given method is writeReplace() or readResolve().
     * @param aAST method def to check
     * @return true if this is a writeReplace() definition
     */
    private boolean isWriteReplaceOrReadResolve(DetailAST aAST)
    {
        // name is writeReplace or readResolve...
        final DetailAST ident = aAST.findFirstToken(TokenTypes.IDENT);
        if (!"writeReplace".equals(ident.getText())
            && !"readResolve".equals(ident.getText()))
        {
            return false;
        }

        // returns Object...
        final DetailAST typeAST =
            (DetailAST) aAST.findFirstToken(TokenTypes.TYPE).getFirstChild();
        if (typeAST.getType() != TokenTypes.DOT
            && typeAST.getType() != TokenTypes.IDENT)
        {
            return false;
        }

        // should have no parameters...
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        if (params != null && params.getChildCount() != 0) {
            return false;
        }

        // and, finally, it should throws java.io.ObjectStreamException
        final DetailAST throwsAST =
            aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST == null || throwsAST.getChildCount() != 1) {
            return false;
        }
        final DetailAST excpt = (DetailAST) throwsAST.getFirstChild();
        final String exception = FullIdent.createFullIdent(excpt).getText();
        if (!"java.io.ObjectStreamException".equals(exception)
            && !"ObjectStreamException".equals(exception))
        {
            return false;
        }

        return true;
    }
}
