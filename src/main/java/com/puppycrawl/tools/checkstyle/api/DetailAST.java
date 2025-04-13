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
 * An interface of Checkstyle's AST nodes for traversing trees generated from the
 * Java code. The main purpose of this interface is to abstract away ANTLR
 * specific classes from API package so other libraries won't require it.
 *
 * @see <a href="https://www.antlr.org/">ANTLR Website</a>
 */
public interface DetailAST {

    /**
     * Returns the number of child nodes one level below this node. That is,
     * does not recurse down the tree.
     *
     * @return the number of child nodes
     */
    int getChildCount();

    /**
     * Returns the number of direct child tokens that have the specified type.
     *
     * @param type the token type to match
     * @return the number of matching token
     */
    int getChildCount(int type);

    /**
     * Returns the parent token.
     *
     * @return the parent token
     */
    DetailAST getParent();

    /**
     * Gets the text of this AST.
     *
     * @return the text.
     */
    String getText();

    /**
     * Gets the type of this AST.
     *
     * @return the type.
     */
    int getType();

    /**
     * Gets line number.
     *
     * @return the line number
     */
    int getLineNo();

    /**
     * Gets column number.
     *
     * @return the column number
     */
    int getColumnNo();

    /**
     * Gets the last child node.
     *
     * @return the last child node
     */
    DetailAST getLastChild();

    /**
     * Checks if this branch of the parse tree contains a token
     * of the provided type.
     *
     * @param type a TokenType
     * @return true if and only if this branch (including this node)
     *     contains a token of type {@code type}.
     * @deprecated
     *      Usage of this method is no longer accepted. We encourage
     *      traversal of subtrees to be written per the needs of each check
     *      to avoid unintended side effects.
     * @noinspection DeprecatedIsStillUsed, RedundantSuppression
     * @noinspectionreason DeprecatedIsStillUsed - Method used in unit testing
     * @noinspectionreason RedundantSuppression - Inspections shows false positive for
     *      redundant suppression, see
     *      <a href="https://github.com/checkstyle/checkstyle/issues/12359">here</a>
     *      for more details.
     */
    @Deprecated(since = "8.43")
    boolean branchContains(int type);

    /**
     * Returns the previous sibling or null if no such sibling exists.
     *
     * @return the previous sibling or null if no such sibling exists.
     */
    DetailAST getPreviousSibling();

    /**
     * Returns the first child token that makes a specified type.
     *
     * @param type the token type to match
     * @return the matching token, or null if no match
     */
    DetailAST findFirstToken(int type);

    /**
     * Get the next sibling in line after this one.
     *
     * @return the next sibling or null if none.
     */
    DetailAST getNextSibling();

    /**
     * Get the first child of this AST.
     *
     * @return the first child or null if none.
     */
    DetailAST getFirstChild();

    /**
     * Get number of children of this AST.
     *
     * @return the number of children.
     * @deprecated This method will be removed in a future release.
     *             Use {@link #getChildCount()} instead.
     */
    @Deprecated(since = "8.30")
    int getNumberOfChildren();

    /**
     * Returns whether this AST has any children.
     *
     * @return {@code true} if this AST has any children.
     */
    boolean hasChildren();
}
