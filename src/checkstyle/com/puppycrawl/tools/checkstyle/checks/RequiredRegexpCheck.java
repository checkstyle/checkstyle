////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import org.apache.regexp.RE;


/**
 * <p>
 * A check that makes sure that a specified pattern exists in the code.
 * </p>
 * <p>
 * An example of how to configure the check to make sure a copyright statement
 * is included in the file (but without requirements on where in the file
 * it should be):
 * </p>
 * <pre>
 * &lt;module name="RequiredRegexp"&gt;
 *    &lt;property name="format" value="This code is copyrighted"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Daniel Grenner
 */
public class RequiredRegexpCheck extends AbstractFormatCheck
{
    /**
     * Instantiates an new GenericIllegalRegexpCheck.
     */
    public RequiredRegexpCheck()
    {
        super("$^"); // the empty language
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        final RE regexp = getRegexp();
        final String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {

            final String line = lines[i];
            if (regexp.match(line)) {
                return;
            }
        }
        log(0, "required.regexp", getFormat());
    }
}

