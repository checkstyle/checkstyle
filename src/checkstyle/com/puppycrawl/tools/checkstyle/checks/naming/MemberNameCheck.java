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
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that instance variable names conform to a format specified
 * by the format property. The format is a
 * {@link java.util.regex.Pattern regular expression}
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MemberName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * &quot;m&quot;, followed by an upper case letter, and then letters and
 * digits is:
 * </p>
 * <pre>
 * &lt;module name="MemberName"&gt;
 *    &lt;property name="format" value="^m[A-Z][a-zA-Z0-9]*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class MemberNameCheck
    extends AbstractNameCheck
{
    /** Should we apply check to public.*/
    private boolean mApplyToPublic = true;
    /** Should we apply check to protected.*/
    private boolean mApplyToProtected = true;
    /** Should we apply check to package.*/
    private boolean mApplyToPackage = true;
    /** Should we apply check to private.*/
    private boolean mApplyToPrivate = true;

    /** Creates a new <code>MemberNameCheck</code> instance. */
    public MemberNameCheck()
    {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    protected final boolean mustCheckName(DetailAST aAST)
    {
        final DetailAST modifiersAST =
            aAST.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStatic = (modifiersAST != null)
            && modifiersAST.branchContains(TokenTypes.LITERAL_STATIC);

        return (!isStatic && !ScopeUtils.inInterfaceOrAnnotationBlock(aAST)
            && !ScopeUtils.isLocalVariableDef(aAST))
            && (modifiersAST != null)
            && shouldCheckInScope(modifiersAST);
    }

    /**
     * Should we check member with given modifiers.
     * @param aModifiers modifiers of member to check.
     * @return true if we should check such member.
     */
    private boolean shouldCheckInScope(DetailAST aModifiers)
    {
        if (aModifiers == null) {
            // if there are no modifaiers it is a package-private
            return mApplyToPackage;
        }

        final boolean isPublic =
            aModifiers.branchContains(TokenTypes.LITERAL_PUBLIC);
        final boolean isProtected =
            aModifiers.branchContains(TokenTypes.LITERAL_PROTECTED);
        final boolean isPrivate =
            aModifiers.branchContains(TokenTypes.LITERAL_PRIVATE);
        final boolean isPackage = !(isPublic || isProtected || isPrivate);

        return (mApplyToPublic && isPublic)
            || (mApplyToProtected && isProtected)
            || (mApplyToPackage && isPackage) || (mApplyToPrivate && isPrivate);
    }

    /**
     * Sets whether we should apply the check to public members.
     * @param aApplyTo new value of the property.
     */
    public void setApplyToPublic(boolean aApplyTo)
    {
        mApplyToPublic = aApplyTo;
    }

    /** @return true if the check should be applied to public members.*/
    public boolean getApplyToPublic()
    {
        return mApplyToPublic;
    }

    /**
     * Sets whether we should apply the check to protected members.
     * @param aApplyTo new value of the property.
     */
    public void setApplyToProtected(boolean aApplyTo)
    {
        mApplyToProtected = aApplyTo;
    }

    /** @return true if the check should be applied to protected members.*/
    public boolean getApplyToProtected()
    {
        return mApplyToProtected;
    }

    /**
     * Sets whether we should apply the check to package-private members.
     * @param aApplyTo new value of the property.
     */
    public void setApplyToPackage(boolean aApplyTo)
    {
        mApplyToPackage = aApplyTo;
    }

    /**
     * @return true if the check should be applied to package-private members.
     */
    public boolean getApplyToPackage()
    {
        return mApplyToPackage;
    }

    /**
     * Sets whether we should apply the check to private members.
     * @param aApplyTo new value of the property.
     */
    public void setApplyToPrivate(boolean aApplyTo)
    {
        mApplyToPrivate = aApplyTo;
    }

    /** @return true if the check should be applied to private members.*/
    public boolean getApplyToPrivate()
    {
        return mApplyToPrivate;
    }
}
