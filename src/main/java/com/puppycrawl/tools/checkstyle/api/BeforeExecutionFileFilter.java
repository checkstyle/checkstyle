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

/**
 * An interface for before execution file filtering events.
 */
@FunctionalInterface
public interface BeforeExecutionFileFilter {

    /**
     * Determines whether or not a before execution file filtered event is accepted.
     * An accepted file is processed by Checkstyle and its violations are kept in
     * the final report; a rejected file is excluded from processing entirely.
     * In other words, returning {@code true} lets the file (and any resulting
     * violations) reach the report, while returning {@code false} skips the file.
     *
     * @param uri the uri to filter.
     * @return true if the file is accepted and should be processed; false to
     *     exclude the file so its violations never reach the final report.
     */
    boolean accept(String uri);

}
