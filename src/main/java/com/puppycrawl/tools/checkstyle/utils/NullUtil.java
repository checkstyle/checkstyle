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

package com.puppycrawl.tools.checkstyle.utils;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Utility methods to suppress Checker Framework nullness warnings for cases where
 * null is theoretically possible but practically impossible due to Java grammar rules.
 *
 * <p>Checkstyle only processes compiled Java sources, so certain AST nodes are guaranteed
 * to exist by Java Language Specification. For example, every {@code METHOD_DEF} must have
 * a {@code PARAMETERS} child, every definition must have an {@code IDENT} child, etc.
 *
 * <p>Use this utility for such grammar-guaranteed cases. Do NOT use for genuinely nullable
 * values where null should be handled with proper null checks.
 */
public final class NullUtil {

    /** Stop instances being created. **/
    private NullUtil() {
    }

    /**
     * Assert that a reference is non-null. The method suppresses nullness warnings from
     * the Checker Framework and throws {@link AssertionError} if the argument is null
     * when Java assertions are enabled.
     *
     * @param <T> the type of the reference
     * @param ref a reference of @Nullable type, that is non-null at run time
     * @return the argument, cast to have the type qualifier {@code @NonNull}
     * @throws AssertionError if ref is null and assertions are enabled
     */
    public static <T> @NonNull T notNull(@Nullable T ref) {
        if (ref == null) {
            throw new AssertionError("Misuse of notNull: argument was null");
        }
        return ref;
    }
}
