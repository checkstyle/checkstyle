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

package com.puppycrawl.tools.checkstyle.checks.imports;

/**
 * Represents the policy for the position of module import declarations
 * relative to type and static imports.
 *
 * @see ModuleImportOrderCheck
 */
public enum ModuleImportOrderOption {

    /**
     * Represents the policy that module imports are all at the top.
     * For example:
     *
     * <pre>
     *  import module java.desktop;
     *  import module java.sql;
     *
     *  import java.awt.Button;
     *  import static java.io.File.createTempFile;
     * </pre>
     */
    TOP,

    /**
     * Represents the policy that module imports are all at the bottom.
     * For example:
     *
     * <pre>
     *  import java.awt.Button;
     *  import static java.io.File.createTempFile;
     *
     *  import module java.desktop;
     *  import module java.sql;
     * </pre>
     */
    BOTTOM,

}
