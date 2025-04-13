///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.api;

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
     * @see JavadocTokenTypes
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
     * Array of children.
     *
     * @return array of children
     */
    DetailNode[] getChildren();

    /**
     * Parent node.
     *
     * @return parent node.
     */
    DetailNode getParent();

    /**
     * Node index among parent's children.
     *
     * @return index
     */
    int getIndex();

}
