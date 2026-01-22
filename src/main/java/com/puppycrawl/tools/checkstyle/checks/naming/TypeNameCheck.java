///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that type names conform to a specified pattern.
 * </div>
 *
 * @since 3.0
 */
public class TypeNameCheck
    extends AbstractAccessControlNameCheck {

    /**
     * Default pattern for type name.
     */
    public static final String DEFAULT_PATTERN = "^[A-Z][a-zA-Z0-9]*$";

    /**
     * Creates a new {@code TypeNameCheck} instance.
     */
    public TypeNameCheck() {
        super(DEFAULT_PATTERN);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    /**
     * Setter to control if check should apply to package-private members.
     *
     * @param applyTo new value of the property.
     * @propertySince 5.0
     */
    @Override
    public final void setApplyToPackage(boolean applyTo) {
        super.setApplyToPackage(applyTo);
    }

    /**
     * Setter to control if check should apply to private members.
     *
     * @param applyTo new value of the property.
     * @propertySince 5.0
     */
    @Override
    public final void setApplyToPrivate(boolean applyTo) {
        super.setApplyToPrivate(applyTo);
    }

    /**
     * Setter to control if check should apply to protected members.
     *
     * @param applyTo new value of the property.
     * @propertySince 5.0
     */
    @Override
    public final void setApplyToProtected(boolean applyTo) {
        super.setApplyToProtected(applyTo);
    }

    /**
     * Setter to control if check should apply to public members.
     *
     * @param applyTo new value of the property.
     * @propertySince 5.0
     */
    @Override
    public final void setApplyToPublic(boolean applyTo) {
        super.setApplyToPublic(applyTo);
    }

}
