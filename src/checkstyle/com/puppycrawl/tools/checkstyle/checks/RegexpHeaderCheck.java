////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
 * <p>
 * Checks the header of the source against a header file that contains a
* <a href="http://jakarta.apache.org/regexp/apidocs/org/apache/regexp/RE.html">
 * regular expression</a>
 * for each line of the source header.
 * </p>
 * <p>
 * Rationale: In some projects checking against a fixed header
 * is not sufficient (see {@link HeaderCheck}), e.g.
 * the header might require a copyright line where the year information
 * is not static.
 * </p>
 *
 * <p>For example, consider the following header file:</p>
 *
 * <pre>
 * line 1: /{71}
 * line 2: // checkstyle:
 * line 3: // Checks Java source code for adherence to a set of rules\.
 * line 4: // Copyright \(C\) \d\d\d\d  Oliver Burn
 * line 5: // Last modification by \$Author.*\$
 * line 6: /{71}
 * </pre>
 *
 * <p>Lines 1 and 6 demonstrate a more compact notation for 71 '/'
 * characters. Line 4 enforces that the copyright notice includes a four digit
 * year. Line 5 is an example how to enforce revision control keywords in a file
 * header.</p>
 * <p>An example of how to configure the check to use header file
 * &quot;java.header&quot; and ignore lines 4 and 5 is:
 * </p>
 * <pre>
 * &lt;module name="RegexpHeader"&gt;
 *    &lt;property name="headerFile" value="java.header"/&gt;
 *    &lt;property name="ignoreLines" value="4, 5"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Lars Kühne
 */
public class RegexpHeaderCheck extends HeaderCheck
{
    /** the compiled regular expressions */
    private RE[] mHeaderRegexps;

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
