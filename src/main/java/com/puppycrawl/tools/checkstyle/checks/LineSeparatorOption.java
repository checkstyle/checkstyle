////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Represents the options for line separator settings.
 *
 * @author lkuehne
 * @see NewlineAtEndOfFileCheck
 */
public enum LineSeparatorOption {
    /** Windows-style line separators. **/
    CRLF("\r\n"),

    /** Mac-style line separators. **/
    CR("\r"),

    /** Unix-style line separators. **/
    LF("\n"),

    /**
     * Matches CR, LF and CRLF line separators.
     * Only the length is used - the actual value is ignored.
     */
    LF_CR_CRLF("##"),

    /** System default line separators. **/
    SYSTEM(System.getProperty("line.separator"));

    /** The line separator representation. */
    private final byte[] lineSeparator;

    /**
     * Creates a new {@code LineSeparatorOption} instance.
     * @param sep the line separator, e.g. "\r\n"
     */
    LineSeparatorOption(String sep) {
        lineSeparator = sep.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * Checks that bytes is equal to the byte representation of this line separator.
     * @param bytes a bytes array to check
     * @return if bytes is equal to the byte representation
     *     of this line separator
     */
    public boolean matches(byte... bytes) {
        if (this == LF_CR_CRLF) {
            // this silently assumes CRLF and ANY have the same length
            // and LF and CR are of length 1
            return CRLF.matches(bytes)
                || LF.matches(Arrays.copyOfRange(bytes, 1, 2))
                || CR.matches(Arrays.copyOfRange(bytes, 1, 2));
        }
        else {
            return Arrays.equals(bytes, lineSeparator);
        }
    }

    /**
     * @return the length of the file separator in bytes,
     *     e.g. 1 for CR, 2 for CRLF, ...
     */
    public int length() {
        return lineSeparator.length;
    }
}
