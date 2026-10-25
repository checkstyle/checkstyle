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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Arrays;

/**
 * Defensive array copying for API classes that historically contain implementation code.
 * This helper can be removed together with that implementation in a future API redesign.
 *
 * <p>The separate wrapper preserves the existing PIT call and class exclusions for
 * defensive copying. See <a href="https://github.com/hcoles/pitest/issues/1462">PIT #1462</a>.
 */
final class ApiArrayUtil {

    /** Private constructor for ApiArrayUtil. */
    private ApiArrayUtil() {
    }

    /**
     * Creates a copy of array.
     *
     * @param <T> The type of array
     * @param array Array to create a copy of
     * @param length length of array
     * @return copy of array
     */
    /* package */ static <T> T[] copyOfArray(T[] array, int length) {
        return Arrays.copyOf(array, length);
    }

}
