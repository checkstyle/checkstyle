///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * The first module that is run as part of Checkstyle and controls its entire
 * behavior and children.
 */
public interface RootModule extends Configurable {

    /** Cleans up the object. **/
    void destroy();

    /**
     * Processes a set of files.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     *
     * @param files the list of files to be audited.
     * @return the total number of audit events with error severity found
     * @throws CheckstyleException if error condition within Checkstyle occurs
     * @deprecated use {@link #process(List)}
     * @see #destroy()
     */
    int process(List<File> files) throws CheckstyleException;

    /**
     * Processes a set of files.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     *
     * @param files the list of files to be audited.
     * @return the total number of audit events with error severity found
     * @throws CheckstyleException if error condition within Checkstyle occurs
     * @see #destroy()
     */
    int process(Collection<Path> files) throws CheckstyleException;

    /**
     * Add the listener that will be used to receive events from the audit.
     *
     * @param listener the nosy thing
     */
    void addListener(AuditListener listener);

    /**
     * Sets the classloader used to load Checkstyle core and custom module
     * classes when the module tree is being built up.
     * If no custom ModuleFactory is being set for the root module then
     * this module classloader must be specified.
     *
     * @param moduleClassLoader the classloader used to load module classes
     */
    void setModuleClassLoader(ClassLoader moduleClassLoader);

}
