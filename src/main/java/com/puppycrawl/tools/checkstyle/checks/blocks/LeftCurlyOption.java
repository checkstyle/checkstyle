////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.blocks;

/**
 * Represents the options for placing the left curly brace <code>'{'</code>.
 *
 * @author Oliver Burn
 * @version 1
 */
public enum LeftCurlyOption
{
    /**
     * Represents the policy for placing the brace at the end of line. For
     * example:
     * <pre>
     * if (condition) {
     *     ...
     * </pre>
     **/
    EOL,

    /**
     * Represents the policy that if the brace will fit on the first line of
     * the statement, taking into account maximum line length, then apply
     * <code>EOL</code> rule. Otherwise apply the <code>NL</code>
     * rule. <code>NLOW</code> is a mnemonic for "new line on wrap".
     *
     * <p> For the example above Checkstyle will enforce:
     *
     * <pre>
     * if (condition) {
     *     ...
     * </pre>
     *
     * But for a statement spanning multiple lines, Checkstyle will enforce:
     *
     * <pre>
     * if (condition1 && condition2 &&
     *     condition3 && condition4)
     * {
     *     ...
     * </pre>
     **/
    NLOW,

    /**
     * Represents the policy that the brace must always be on a new line. For
     * example:
     * <pre>
     * if (condition)
     * {
     *     ...
     * </pre>
     */
    NL;
}
