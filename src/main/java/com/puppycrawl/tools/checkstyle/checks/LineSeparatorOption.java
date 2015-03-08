////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
package com.puppycrawl.tools.checkstyle.checks;

/**
 * Represents the options for line separator settings.
 *
 * @author lkuehne
 * @see NewlineAtEndOfFileCheck
 */
public enum LineSeparatorOption
{
    /** Windows-style line separators. **/
    CRLF("\r\n"),

    /** Mac-style line separators. **/
    CR("\r"),

    /** Unix-style line separators. **/
    LF("\n"),

    /** System default platform independent line separators. **/
    SYSTEM("\n");

    /** the line separator representation */
    private final String lineSeparator;

    /**
     * Creates a new <code>LineSeparatorOption</code> instance.
     * @param sep the line separator, e.g. "\r\n"
     */
    private LineSeparatorOption(String sep)
    {
        lineSeparator = sep;
    }

    /**
     * @param bytes a bytes array to check
     * @return if bytes is equal to the byte representation
     * of this line separator
     */
    public boolean matches(byte[] bytes)
    {
        final String s = new String(bytes);
        return s.equals(lineSeparator);
    }

    /**
     * @return the length of the file separator,
     * e.g. 1 for CR, 2 for CRLF, ...
     */
    public int length()
    {
        return lineSeparator.length();
    }
}
