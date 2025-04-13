////
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

package com.puppycrawl.tools.checkstyle.meta;

/** Enum holding the types of module which can exist. */
public enum ModuleType {

    /** Module is a Checkstyle check. */
    CHECK("check"),

    /** Module is a Checkstyle filter. */
    FILTER("filter"),

    /** Module is a Checkstyle file filter. */
    FILEFILTER("file-filter");

    /** String representation of the module type. */
    private final String label;

    /**
     * Creates a new instance.
     *
     * @param label label of module
     */
    ModuleType(String label) {
        this.label = label;
    }

    /**
     * Get label corresponding to a module type.
     *
     * @return module type label
     */
    public String getLabel() {
        return label;
    }
}
