///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * Abstract class for checking a class member (field/method)'s name conforms to
 * a specified pattern.
 *
 * <p>
 * This class extends {@link AbstractNameCheck} with support for access level
 * restrictions. This allows the check to be configured to be applied to one of
 * the four Java access levels: {@code public}, {@code protected},
 * {@code "package"}, and {@code private}.
 * </p>
 *
 * <p>Level is configured using the following properties:
 * <ol>
 * <li>applyToPublic, default true;</li>
 * <li>applyToProtected, default true;</li>
 * <li>applyToPackage, default true;</li>
 * <li>applyToPrivate, default true;</li>
 * </ol>
 *
 *
 */
public abstract class AbstractAccessControlNameCheck
    extends AbstractNameCheck {

    /** If true, applies the check be public members. */
    private boolean applyToPublic = true;

    /** If true, applies the check be protected members. */
    private boolean applyToProtected = true;

    /** If true, applies the check be "package" members. */
    private boolean applyToPackage = true;

    /** If true, applies the check be private members. */
    private boolean applyToPrivate = true;

    /**
     * Creates a new {@code AbstractAccessControlNameCheck} instance.
     *
     * @param format
     *                format to check with
     */
    protected AbstractAccessControlNameCheck(String format) {
        super(format);
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        return shouldCheckInScope(ast.findFirstToken(TokenTypes.MODIFIERS));
    }

    /**
     * Should we check member with given modifiers.
     *
     * @param modifiers
     *                modifiers of member to check.
     * @return true if we should check such member.
     */
    protected boolean shouldCheckInScope(DetailAST modifiers) {
        final boolean isProtected = modifiers
                .findFirstToken(TokenTypes.LITERAL_PROTECTED) != null;
        final boolean isPrivate = modifiers
                .findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
        final boolean isPublic = isPublic(modifiers);

        final boolean isPackage = !(isPublic || isProtected || isPrivate);

        return applyToPublic && isPublic
                || applyToProtected && isProtected
                || applyToPackage && isPackage
                || applyToPrivate && isPrivate;
    }

    /**
     * Checks if given modifiers has public access.
     * There are 2 cases - it is either has explicit modifier, or it is
     * in annotation or interface.
     *
     * @param modifiers - modifiers to check
     * @return true if public
     */
    private static boolean isPublic(DetailAST modifiers) {
        return modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
                || ScopeUtil.isInAnnotationBlock(modifiers)
                || ScopeUtil.isInInterfaceBlock(modifiers)
                    // interface methods can be private
                    && modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null;
    }

    /**
     * Setter to control if check should apply to public members.
     *
     * @param applyTo new value of the property.
     */
    public void setApplyToPublic(boolean applyTo) {
        applyToPublic = applyTo;
    }

    /**
     * Setter to control if check should apply to protected members.
     *
     * @param applyTo new value of the property.
     */
    public void setApplyToProtected(boolean applyTo) {
        applyToProtected = applyTo;
    }

    /**
     * Setter to control if check should apply to package-private members.
     *
     * @param applyTo new value of the property.
     */
    public void setApplyToPackage(boolean applyTo) {
        applyToPackage = applyTo;
    }

    /**
     * Setter to control if check should apply to private members.
     *
     * @param applyTo new value of the property.
     */
    public void setApplyToPrivate(boolean applyTo) {
        applyToPrivate = applyTo;
    }

}
