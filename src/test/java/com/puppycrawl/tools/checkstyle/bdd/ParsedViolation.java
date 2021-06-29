////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.bdd;

public final class ParsedViolation {

    /** Parsed violation line number. */
    private final int violationLine;

    /** Parsed violation message. */
    private final String violationMessage;

    /**
     * Creates a new {@code ParsedViolation} instance.
     *
     * @param violationLine the violation line number.
     * @param violationMessage the violation message.
     */
    public ParsedViolation(int violationLine, String violationMessage) {
        this.violationLine = violationLine;
        this.violationMessage = violationMessage;
    }

    /**
     * Gets the violation line number.
     *
     * @return the violation line number.
     */
    public int getViolationLine() {
        return violationLine;
    }

    /**
     * Gets the violation message.
     *
     * @return the violation message.
     */
    public String getViolationMessage() {
        return violationMessage;
    }
}
