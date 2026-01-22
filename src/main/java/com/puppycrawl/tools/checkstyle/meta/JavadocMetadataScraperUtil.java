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

package com.puppycrawl.tools.checkstyle.meta;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Class for scraping module metadata from the corresponding class' class-level javadoc.
 */
public final class JavadocMetadataScraperUtil {

    /** Regular expression for detecting ANTLR tokens(for e.g. CLASS_DEF). */
    private static final Pattern TOKEN_TEXT_PATTERN = Pattern.compile("([A-Z_]{2,})+");

    /**
     * Private utility constructor.
     */
    private JavadocMetadataScraperUtil() {
    }

    /**
     * Performs a depth-first traversal of the subtree starting at {@code startNode}
     * and ending at {@code endNode}, and constructs the concatenated text of all nodes
     * in that range, ignoring {@code JavadocToken} texts.
     *
     * @param startNode the node where traversal begins (inclusive)
     * @param endNode the node where traversal ends (inclusive)
     * @return the constructed text from the specified subtree range
     */
    public static String constructSubTreeText(DetailNode startNode,
                                               DetailNode endNode) {
        DetailNode curNode = startNode;
        final StringBuilder result = new StringBuilder(1024);

        while (curNode != null) {
            if (isContentToWrite(curNode)) {
                String childText = curNode.getText();

                if (isInsideCodeInlineTag(curNode)) {
                    childText = adjustCodeInlineTagChildToHtml(curNode);
                }

                result.append(childText);
            }

            DetailNode toVisit = curNode.getFirstChild();
            while (curNode != endNode && toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
            }

            curNode = toVisit;
        }
        return result.toString().trim();
    }

    /**
     * Checks whether the given node is inside a {@code @code} Javadoc inline tag.
     *
     * @param node the node to check
     * @return true if the node is inside a {@code @code} inline tag, false otherwise
     */
    private static boolean isInsideCodeInlineTag(DetailNode node) {
        return node.getParent() != null
                && node.getParent().getType() == JavadocCommentsTokenTypes.CODE_INLINE_TAG;
    }

    /**
     * Checks whether selected Javadoc node is considered as something to write.
     *
     * @param detailNode javadoc node to check.
     * @return whether javadoc node is something to write.
     */
    private static boolean isContentToWrite(DetailNode detailNode) {

        return detailNode.getType() != JavadocCommentsTokenTypes.LEADING_ASTERISK
            && (detailNode.getType() == JavadocCommentsTokenTypes.TEXT
            || !TOKEN_TEXT_PATTERN.matcher(detailNode.getText()).matches());
    }

    /**
     * Adjusts certain child of {@code @code} Javadoc inline tag to its analogous html format.
     *
     * @param codeChild {@code @code} child to convert.
     * @return converted {@code @code} child element, otherwise just the original text.
     */
    public static String adjustCodeInlineTagChildToHtml(DetailNode codeChild) {

        return switch (codeChild.getType()) {
            case JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_END -> "</code>";
            case JavadocCommentsTokenTypes.TAG_NAME -> "";
            case JavadocCommentsTokenTypes.JAVADOC_INLINE_TAG_START -> "<code>";
            default -> codeChild.getText().trim();
        };
    }

    /**
     * Returns the first child node of the given parent that matches the provided {@code tokenType}.
     *
     * @param node the parent node
     * @param tokenType the token type to match
     * @return an {@link Optional} containing the first matching child node,
     *         or an empty {@link Optional} if none is found
     */
    private static Optional<DetailNode> getFirstChildOfType(DetailNode node, int tokenType) {
        return JavadocUtil.getAllNodesOfType(node, tokenType).stream().findFirst();
    }

    /**
     * Checks whether the first child {@code JavadocTokenType.TEXT} node matches given pattern.
     *
     * @param ast parent javadoc node
     * @param pattern pattern to match
     * @return true if one of child text nodes matches pattern
     */
    public static boolean isChildNodeTextMatches(DetailNode ast, Pattern pattern) {
        return getFirstChildOfType(ast, JavadocCommentsTokenTypes.TEXT)
                .map(DetailNode::getText)
                .map(pattern::matcher)
                .map(Matcher::matches)
                .orElse(Boolean.FALSE);
    }
}
