////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains constant definitions common to the package.
 **/
public final class Definitions {

    /** Name of resource bundle for Checkstyle. */
    public static final String CHECKSTYLE_BUNDLE =
            "com.puppycrawl.tools.checkstyle.messages";

    /** Name of modules which are not checks, but are internal modules. */
    public static final Set<String> INTERNAL_MODULES = Collections.unmodifiableSet(
            new HashSet<>(Collections.singletonList(
                    "com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper"
    )));

    /**
     * Do no allow {@code Definitions} instances to be created.
     **/
    private Definitions() {
    }

}
