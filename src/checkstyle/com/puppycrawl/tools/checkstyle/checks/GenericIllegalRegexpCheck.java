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

package com.puppycrawl.tools.checkstyle.checks;

/**
 * A generic check for code problems, the user can search for any pattern.
 * This is similar to a recursive grep, only that it's integrated in checkstyle.
 *
 * Rationale: This Check can be used to prototype checks and to find common
 * bad pratice such as calling
 * ex.printStacktrace(), System.out.println(), System.exit(), etc.
 *
 * @author lkuehne
 */
public class GenericIllegalRegexpCheck extends AbstractFormatCheck
{
    /**
     * Instantiates an new GenericIllegalRegexpCheck.
     */
    public GenericIllegalRegexpCheck()
    {
        super("$^"); // the empty language
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        final String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {

            final String line = lines[i];
            if (super.getRegexp().match(line)) {
                log(i + 1, "illegal.regexp", getFormat());
            }
        }
    }
}
