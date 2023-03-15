///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.util.Set;

import com.puppycrawl.tools.checkstyle.Checker;

/**
 * The following interface should be implemented by each module (inheritor of
 * {@link AbstractCheck}, implementor of {@link FileSetCheck}, or {@link Filter}) which uses
 * external resources of any kind for its configuration. Such modules must declare external
 * resource locations as a set of {@link String} which will be returned from
 * {@link #getExternalResourceLocations}. This allows Checkstyle to invalidate (clear) cache
 * when the content of at least one external configuration resource of the module is changed.
 *
 */
@FunctionalInterface
public interface ExternalResourceHolder {

    /**
     * Returns a set of external configuration resource locations which are used by the module.
     * ATTENTION!
     * If 'getExternalResourceLocations()' return null, there will be
     * {@link NullPointerException} in {@link Checker}.
     * Such behaviour will signal that your module (check or filter) is designed incorrectly.
     * It makes sense to return an empty set from 'getExternalResourceLocations()'
     * only for composite modules like {@link com.puppycrawl.tools.checkstyle.TreeWalker}.
     *
     * @return a set of external configuration resource locations which are used by the module.
     */
    Set<String> getExternalResourceLocations();

}
