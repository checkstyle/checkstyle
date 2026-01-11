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

package com.puppycrawl.tools.checkstyle.api;

import javax.annotation.Nullable;

/**
 * DetailNode is used to construct tree during parsing Javadoc comments.
 * Contains array of children, parent node and other useful fields.
 *
 * @see com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl
 * @see com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck
 */
public interface DetailNode {

    /**
     * Node type.
     *
     * @return node type.
     * @see JavadocCommentsTokenTypes
     */
    int getType();

    /**
     * Node text.
     *
     * @return node text
     */
    String getText();

    /**
     * Node line number.
     *
     * @return node line number
     */
    int getLineNumber();

    /**
     * Node column number.
     *
     * @return node column number.
     */
    int getColumnNumber();

    /**
     * Parent node.
     *
     * @return parent node.
     */
    DetailNode getParent();

    /**
     * Sibling node.
     *
     * @return sibling node
     */
    DetailNode getNextSibling();

    /**
     * First child node.
     *
     * @return first child node
     */
    DetailNode getFirstChild();

    /**
     * Previous sibling node.
     *
     * @return sibling node
     */
    DetailNode getPreviousSibling();

    /**
     * Returns the first child token that makes a specified type.
     *
     * @param type the token type to match
     * @return the matching token, or null if no match
     */
    @Nullable
    DetailNode findFirstToken(int type);
}
