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

import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * Checks for long source files.
 *
 * <p>
 * Rationale: If a source file becomes very long it is hard to understand.
 * Therefore long classes should usually be refactored into several
 * individual classes that focus on a specific task.
 * </p>
 *
 * @author Lars Kühne
 */
public class FileLengthCheck extends Check
{
    /** the maximum number of lines */
    private int mMaxFileLength = 2000;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        final String[] lines = getLines();
        if (lines.length > mMaxFileLength) {
            log(1, "maxLen.file",
                    new Integer(lines.length),
                    new Integer(mMaxFileLength));
        }
    }

    /**
     * @param aLength the maximum length of a Java source file
     */
    public void setMax(int aLength)
    {
        mMaxFileLength = aLength;
    }

}
