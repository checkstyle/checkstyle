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

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.apache.commons.beanutils.ConversionException;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Checks the header of the source against a header file that contains a
 * regular expression for each line of the source header.
 *
 * <p>
 * Rationale: In some projects checking against a fixed header
 * is not sufficient (see {@link HeaderCheck}), e.g.
 * the header might require a copyright line where the year information
 * is not static.
 * </p>
 *
 * <p>
 * TODO: RFE 597676
 * </p>
 *
 * @author Lars Kühne
 */
public class RegexpHeaderCheck extends HeaderCheck
{
    /** the compiled regular expressions */
    private RE[] mHeaderRegexps = null;

    /**
     * Sets the file that contains the header to check against.
     * @param aFileName the file that contains the header to check against.
     * @throws ConversionException if the file cannot be loaded or one line
     * is not a regexp.
     */
    public void setHeaderFile(String aFileName)
        throws ConversionException
    {
        super.setHeaderFile(aFileName);
        final String[] headerLines = getHeaderLines();
        if (headerLines != null) {
            mHeaderRegexps = new RE[headerLines.length];
            for (int i = 0; i < headerLines.length; i++) {
                try {
                    // TODO: Not sure if chache in Utils is still necessary
                    mHeaderRegexps[i] = Utils.getRE(headerLines[i]);
                }
                catch (RESyntaxException ex) {
                    throw new ConversionException(
                            "line " + i + " in header file is not a regexp");
                }
            }
        }
    }


    /** @see HeaderCheck */
    protected boolean isMatch(int aLineNumber)
    {
        final String[] lines = getLines();
        return mHeaderRegexps[aLineNumber].match(lines[aLineNumber]);
    }
}
