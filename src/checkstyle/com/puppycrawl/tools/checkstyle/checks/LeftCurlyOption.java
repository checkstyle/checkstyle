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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the options for placing the left curly brace '{'.
 *
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @version $Id: LeftCurlyOption.java,v 1.4 2002-11-14 15:59:49 rickgiles Exp $
 */
public final class LeftCurlyOption
    extends AbstractOption
{
    /** maps from a string representation to an option */
    private static final Map STR_TO_OPT = new HashMap();
    
    /** represents placing the brace at the end of line **/
    public static final LeftCurlyOption EOL = new LeftCurlyOption("eol");
    /**
     * represents placing on the end of the line if it fits on the first line,
     * otherwise placing on a new line.
     **/
    public static final LeftCurlyOption NLOW = new LeftCurlyOption("nlow");
    /** represents placing on a new line **/
    public static final LeftCurlyOption NL = new LeftCurlyOption("nl");

    /**
     * Creates a new <code>LeftCurlyOption</code> instance.
     * @param aStrRep the string representation
     */
    private LeftCurlyOption(String aStrRep)
    {
        super(aStrRep);
    }
    
    /** @see com.puppycrawl.tools.checkstyle.checks.AbstractOption */
    protected Map getStrToOpt()
    {
        return STR_TO_OPT;
    }
}
