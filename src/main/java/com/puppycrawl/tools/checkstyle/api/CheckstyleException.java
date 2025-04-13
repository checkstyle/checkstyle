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

package com.puppycrawl.tools.checkstyle.api;

/**
 * Represents an error condition within Checkstyle.
 *
 * @noinspection CheckedExceptionClass
 * @noinspectionreason CheckedExceptionClass - we require checked exception since we terminate
 *      execution if thrown
 */
public class CheckstyleException extends Exception {

    /** For serialization that will never happen. */
    private static final long serialVersionUID = -3517342299748221108L;

    /**
     * Creates a new {@code CheckstyleException} instance.
     *
     * @param message a {@code String} value
     */
    public CheckstyleException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code CheckstyleException} instance
     * that was caused by another exception.
     *
     * @param message a message that explains this exception
     * @param cause the Exception that is wrapped by this exception
     */
    public CheckstyleException(String message, Throwable cause) {
        super(message, cause);
    }

}
