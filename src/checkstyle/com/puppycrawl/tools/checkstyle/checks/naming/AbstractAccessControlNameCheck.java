////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Abstract class for checking a class member (field/method)'s name conforms to
 * a format specified by the format property.
 *
 * <p>This class extends {@link AbstractNameCheck} with support for access level
 * restrictions. This allows the check to be configured to be applied to one of
 * the four Java access levels: {@code public}, {@code protected},
 * {@code "package"}, and {@code private}.
 *
 * <p>Level is configured using the following properties:
 * <ol>
 * <li>applyToPublic, default true;
 * <li>applyToProtected, default true;
 * <li>applyToPackage, default true;
 * <li>applyToPrivate, default true;
 * </ol>
 * </p>
 *
 * @author Rick Giles
 * @version 1.0
 */
public abstract class AbstractAccessControlNameCheck
    extends AbstractNameCheck
{
    /** If true, applies the check be public members. */
    private boolean mApplyToPublic = true;

    /** If true, applies the check be protected members. */
    private boolean mApplyToProtected = true;

    /** If true, applies the check be "package" members. */
    private boolean mApplyToPackage = true;

    /** If true, applies the check be private members. */
    private boolean mApplyToPrivate = true;

    /**
     * Creates a new {@code AbstractAccessControlNameCheck} instance.
     *
     * @param aFormat
     *                format to check with
     */
    public AbstractAccessControlNameCheck(String aFormat)
    {
        super(aFormat);
    }

    @Override
    protected boolean mustCheckName(DetailAST aAST)
    {
        return shouldCheckInScope(aAST);
    }

    /**
     * Should we check member with given modifiers.
     *
     * @param aModifiers
     *                modifiers of member to check.
     * @return true if we should check such member.
     */
    protected boolean shouldCheckInScope(DetailAST aModifiers)
    {
        if (aModifiers == null) {
            // if there are no modifiers it is a package-private
            return mApplyToPackage;
        }

        final boolean isPublic = aModifiers
                .branchContains(TokenTypes.LITERAL_PUBLIC);
        final boolean isProtected = aModifiers
                .branchContains(TokenTypes.LITERAL_PROTECTED);
        final boolean isPrivate = aModifiers
                .branchContains(TokenTypes.LITERAL_PRIVATE);
        final boolean isPackage = !(isPublic || isProtected || isPrivate);

        return (mApplyToPublic && isPublic)
                || (mApplyToProtected && isProtected)
                || (mApplyToPackage && isPackage)
                || (mApplyToPrivate && isPrivate);
    }

    /**
     * Sets whether we should apply the check to public members.
     *
     * @param aApplyTo new value of the property.
     */
    public void setApplyToPublic(boolean aApplyTo)
    {
        mApplyToPublic = aApplyTo;
    }

    /** @return true if the check should be applied to public members. */
    public boolean getApplyToPublic()
    {
        return mApplyToPublic;
    }

    /**
     * Sets whether we should apply the check to protected members.
     *
     * @param aApplyTo new value of the property.
     */
    public void setApplyToProtected(boolean aApplyTo)
    {
        mApplyToProtected = aApplyTo;
    }

    /** @return true if the check should be applied to protected members. */
    public boolean getApplyToProtected()
    {
        return mApplyToProtected;
    }

    /**
     * Sets whether we should apply the check to package-private members.
     *
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
     *
     * @param aApplyTo new value of the property.
     */
    public void setApplyToPrivate(boolean aApplyTo)
    {
        mApplyToPrivate = aApplyTo;
    }

    /** @return true if the check should be applied to private members. */
    public boolean getApplyToPrivate()
    {
        return mApplyToPrivate;
    }
}
