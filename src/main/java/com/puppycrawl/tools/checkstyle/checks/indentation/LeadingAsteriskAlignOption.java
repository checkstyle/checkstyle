////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

/**
 * Represents the policy for checking alignment of leading asterisk in block comments
 * and javadoc.
 *
 * @see LeadingAsteriskAlignCheck
 */
public enum LeadingAsteriskAlignOption {

    /**
     * Represents the policy that the alignment of leading asterisk should be 'left', such that
     * it is placed below '/'.
     * For example:
     *
     * <pre>
     * &#47;**
     * * This is a javadoc.
     * * The asterisk is aligned to the left.
     * *&#47;
     * </pre>
     */
    LEFT,

    /**
     * Represents the policy that the alignment of leading asterisk should be 'right', such that
     * it is places below '*'.
     * For example:
     *
     * <pre>
     * &#47;**
     *  * This is a javadoc.
     *  * The asterisk is aligned to the right.
     *  *&#47;
     * </pre>
     */
    RIGHT,

}
