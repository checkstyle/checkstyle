////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.io.OutputStream;

/**
 * Mainly use to give an Audit listener the ability to pass
 * an OutputStream without cluttering the original Listener
 * interfaces.
 * <p>
 * This will be used to set the appropriate stream at configuration
 * time (ie, a file, stdout or whatever) and the stream will be
 * closed once the checker is destroyed.
 * <i>
 * (We cannot rely on the finalizer to close the file because it can
 * takes some time and on Window we cannot delete the generated file
 * if there is still a handle on it).
 * </i>
 * Pay attention to keep the original stream so that getOutputStream
 * sends back the setted one (You would me to close stdout or stderr
 * right ?)
 *
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @see AuditListener
 * @see CheckStyleTask
 * @see Checker
 */
public interface Streamable
{
    /**
     * Set a stream to write information to.
     * @param aOS the outputstream to be set.
     */
    void setOutputStream(OutputStream aOS);

    /**
     * @return the stream used to write information to.
     */
    OutputStream getOutputStream();
}
