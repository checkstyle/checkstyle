////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
 * Represents the policy for checking empty line wrapping in blocks.
 */
public enum EmptyLineWrappingInBlockOption {
    /**
     * Represents the policy that the block must have an empty/blank line before/after it.
     * For example:
     * <pre>
     *
     * public void test() {
     *
     *   if(true) {
     *     // Some code here
     *   }
     *
     * }
     *
     * public void test2() {}
     *
     * public void test3() {}
     *
     * </pre>
     */
    EMPTY_LINE,

    /**
     * Represents the policy that a block must not have an empty/blank line before/after it.
     * For example:
     * <pre>
     * public void test() {
     *   if(true) {
     *
     *   }
     * }
     * public void test2() {}
     * public void test3() {}
     * </pre>
     */
    NO_EMPTY_LINE,

    /**
     * Represents the policy that a block can have an empty/blank line or a non empty/blank line.
     * Usually used to generate no violations, as both types are allowed in this.
     * For example:
     * <pre>
     *
     * public void test() {
     *
     *   if(true) {
     *     // Some code here
     *   }
     * }
     *
     * public void test2() {}
     * public void test3() {}
     * </pre>
     */
    EMPTY_LINE_ALLOWED,
}
