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
import com.puppycrawl.tools.checkstyle.api.Utils;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks for long lines.
 *
 * <p>
 * Rationale: Long lines are hard to read in printouts or if developers
 * have limited screen space for the source code, e.g. if the IDE displays
 * additional information like project tree, class hierarchy, etc.
 * </p>
 *
 * <p>
 * Note: Support for the special handling of imports in CheckStyle Version 2
 * has been dropped as it is a special case of regexp: The user can set
 * the ignorePattern to "^import" and achieve the same effect.
 * </p>
 *
 * @author Lars Kühne
 */
public class LineLengthCheck extends Check
{
    /** the maximum number of columns in a line */
    private int mMax = 80;

    /** the regexp when long lines are ignored */
    private RE mIgnorePattern;

    public LineLengthCheck()
    {
        setIgnorePattern("^$");
    }

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
            final int realLength = Utils.lengthExpandedTabs(
                line, line.length(), getTabWidth());


            if (realLength > mMax && !mIgnorePattern.match(line)) {
                log(i + 1, "maxLineLen", new Integer(mMax));
            }
        }
    }

    /**
     * @param aLength the maximum length of a line
     */
    public void setMax(int aLength)
    {
        mMax = aLength;
    }

    /**
     * Set the ignore pattern.
     * @param aFormat a <code>String</code> value
     * @throws org.apache.commons.beanutils.ConversionException unable to parse aFormat
     */
    public void setIgnorePattern(String aFormat)
    {
        try {
            mIgnorePattern = Utils.getRE(aFormat);
        }
        catch (RESyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

}
