////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * A module factory creates Objects from a given name.
 * It's purpose is to map the shortnames like
 * <code>AvoidStarImport</code> to full classnames like
 * <code>com.puppycrawl.tools.checkstyle.checks.AvoidStarImportCheck</code>.
 * A ModuleFactory can implement this name resolution by using naming
 * conventions, fallback strategies, etc.
 *
 * @author lkuehne
 */
public interface ModuleFactory
{
    /**
     * Creates a new instance of a class from a given name.
     * If the provided module name is a class name an instance of that class
     * is returned. If the name is not a class name the ModuleFactory uses
     * heuristics to find the corresponding class.
     *
     * @param aName the name of the module, might be a shortname
     * @return the created module
     * @throws CheckstyleException if no module can be instantiated from aName
     */
    Object createModule(String aName) throws CheckstyleException;
}
