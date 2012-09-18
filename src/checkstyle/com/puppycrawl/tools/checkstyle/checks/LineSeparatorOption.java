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

    /** System default line separators. **/
    SYSTEM(System.getProperty("line.separator"));

    /** the line separator representation */
    private final String mLineSeparator;

    /**
     * Creates a new <code>LineSeparatorOption</code> instance.
     * @param aSep the line separator, e.g. "\r\n"
     */
    private LineSeparatorOption(String aSep)
    {
        mLineSeparator = aSep;
    }

    /**
     * @param aBytes a bytes array to check
     * @return if aBytes is equal to the byte representation
     * of this line separator
     */
    public boolean matches(byte[] aBytes)
    {
        final String s = new String(aBytes);
        return s.equals(mLineSeparator);
    }

    /**
     * @return the length of the file separator,
     * e.g. 1 for CR, 2 for CRLF, ...
     */
    public int length()
    {
        return mLineSeparator.length();
    }
}
