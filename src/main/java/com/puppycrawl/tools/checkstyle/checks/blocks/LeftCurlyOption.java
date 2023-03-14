///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.blocks;

/**
 * Represents the options for placing the left curly brace <code>'{'</code>.
 *
 */
public enum LeftCurlyOption {

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
     * the statement, then apply {@code EOL} rule.
     * Otherwise apply the {@code NL} rule.
     * {@code NLOW} is a mnemonic for "new line on wrap".
     *
     * <p>For the example above Checkstyle will enforce:
     *
     * <pre>
     * if (condition) {
     *     ...
     * </pre>
     *
     * <p>But for a statement spanning multiple lines, Checkstyle will enforce:
     *
     * <pre>
     * if (condition1 &amp;&amp; condition2 &amp;&amp;
     *     condition3 &amp;&amp; condition4)
     * {
     *     ...
     * </pre>
     *
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
    NL,

}
