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
 * Represents the options for placing the right curly brace '}'.
 *
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @version $Id: RightCurlyOption.java,v 1.4 2002-11-03 17:47:37 rickgiles Exp $
 */
public final class RightCurlyOption
    extends AbstractOption
{
    /** represents placing the brace alone on a line **/
    public static final RightCurlyOption ALONE = new RightCurlyOption("alone");
    /** represents placing the brace on the same line **/
    public static final RightCurlyOption SAME = new RightCurlyOption("same");

    /**
     * Creates a new <code>RightCurlyOption</code> instance.
     * @param aStrRep the string representation
     */
    private RightCurlyOption(String aStrRep)
    {
       super(aStrRep);
    }
}
