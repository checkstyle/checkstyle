////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

/**
 * Represents an error condition within Checkstyle.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class CheckstyleException extends Exception
{
    /** For Serialisation that will never happen. */
    private static final long serialVersionUID = -3517342299748221108L;

    /**
     * Creates a new <code>CheckstyleException</code> instance.
     *
     * @param aMessage a <code>String</code> value
     */
    public CheckstyleException(String aMessage)
    {
        super(aMessage);
    }

    /**
     * Creates a new <code>CheckstyleException</code> instance
     * that was caused by another exception.
     *
     * @param aMessage a message that explains this exception
     * @param aCause the Exception that is wrapped by this exception
     */
    public CheckstyleException(String aMessage, Throwable aCause)
    {
        super(aMessage, aCause);
    }
}
