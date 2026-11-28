///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
 * Represents the options for placing the right curly brace of an expression block.
 *
 */
public enum RightCurlyOfExpressionOption {

    /**
     * Represents the policy that the right curly brace must be on the same line
     * as the previous part of the expression.
     * For example:
     *
     * {@snippet lang="text" :
     * String[] names = new String[] {"One", "Two", };
     * }
     */
    ON_SAME_LINE,

    /**
     * Represents the policy that the right curly brace must be on a new line.
     * For example:
     *
     * {@snippet lang="text" :
     * String[] names = new String[] {"One", "Two",
     * };
     * }
     */
    ON_NEW_LINE,

    /**
     * Represents the policy that the right curly brace must be on a new line,
     * unless the whole expression block is on a single line.
     * For example:
     *
     * {@snippet lang="text" :
     * // Single-line format of block
     * String[] names = new String[] {"One", "Two", };
     *
     * // Multi-line format of block
     * String[] names = new String[] {"One", "Two",
     * };
     * }
     */
    ON_NEW_LINE_OR_SINGLELINE,

}
