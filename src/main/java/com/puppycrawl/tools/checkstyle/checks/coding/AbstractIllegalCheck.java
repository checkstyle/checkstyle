////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

/**
 * Support for checks that look for usage of illegal types.
 * @deprecated Checkstyle will not support abstract checks anymore. Use
 *             {@link AbstractCheck} instead.
 * @author Oliver Burn
 * @noinspection AbstractClassNeverImplemented
 */
@Deprecated
public abstract class AbstractIllegalCheck extends AbstractCheck {
    /** Illegal class names. */
    private final Set<String> illegalClassNames = Sets.newHashSet();

    /**
     * Constructs an object.
     * @param initialNames the initial class names to treat as illegal
     */
    protected AbstractIllegalCheck(final String... initialNames) {
        setIllegalClassNames(initialNames);
    }

    /**
     * Checks if given class is illegal.
     *
     * @param ident
     *            ident to check.
     * @return true if given ident is illegal.
     */
    protected final boolean isIllegalClassName(final String ident) {
        return illegalClassNames.contains(ident);
    }

    /**
     * Set the list of illegal classes.
     *
     * @param classNames
     *            array of illegal exception classes
     */
    public final void setIllegalClassNames(final String... classNames) {
        illegalClassNames.clear();
        for (final String name : classNames) {
            illegalClassNames.add(name);
            final int lastDot = name.lastIndexOf('.');
            if (lastDot > 0 && lastDot < name.length() - 1) {
                final String shortName = name
                        .substring(name.lastIndexOf('.') + 1);
                illegalClassNames.add(shortName);
            }
        }
    }
}
