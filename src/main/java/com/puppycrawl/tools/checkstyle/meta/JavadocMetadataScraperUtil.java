///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.meta;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

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
     * Performs a DFS of the subtree with a node as the root and constructs the text of that
     * tree, ignoring JavadocToken texts.
     *
     * @param node root node of subtree
     * @param childLeftLimit the left index of root children from where to scan
     * @param childRightLimit the right index of root children till where to scan
     * @return constructed text of subtree
     */
    public static String constructSubTreeText(DetailNode node, int childLeftLimit,
                                               int childRightLimit) {
        DetailNode detailNode = node;

        final Deque<DetailNode> stack = new ArrayDeque<>();
        stack.addFirst(detailNode);
        final Set<DetailNode> visited = new HashSet<>();
        final StringBuilder result = new StringBuilder(1024);
        while (!stack.isEmpty()) {
            detailNode = stack.removeFirst();

            if (visited.add(detailNode) && isContentToWrite(detailNode)) {
                String childText = detailNode.getText();

                if (detailNode.getParent().getType() == JavadocTokenTypes.JAVADOC_INLINE_TAG) {
                    childText = adjustCodeInlineTagChildToHtml(detailNode);
                }

                result.insert(0, childText);
            }

            for (DetailNode child : detailNode.getChildren()) {
                if (child.getParent().equals(node)
                        && (child.getIndex() < childLeftLimit
                        || child.getIndex() > childRightLimit)) {
                    continue;
                }
                if (!visited.contains(child)) {
                    stack.addFirst(child);
                }
            }
        }
        return result.toString().trim();
    }

    /**
     * Checks whether selected Javadoc node is considered as something to write.
     *
     * @param detailNode javadoc node to check.
     * @return whether javadoc node is something to write.
     */
    private static boolean isContentToWrite(DetailNode detailNode) {

        return detailNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
            && (detailNode.getType() == JavadocTokenTypes.TEXT
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
            case JavadocTokenTypes.JAVADOC_INLINE_TAG_END -> "</code>";
            case JavadocTokenTypes.WS -> "";
            case JavadocTokenTypes.CODE_LITERAL -> codeChild.getText().replace("@", "") + ">";
            case JavadocTokenTypes.JAVADOC_INLINE_TAG_START -> "<";
            default -> codeChild.getText();
        };
    }

    /**
     * Returns the first child node which matches the provided {@code TokenType} and has the
     * children index after the offset value.
     *
     * @param node parent node
     * @param tokenType token type to match
     * @param offset children array index offset
     * @return the first child satisfying the conditions
     */
    private static Optional<DetailNode> getFirstChildOfType(DetailNode node, int tokenType,
                                                            int offset) {
        return Arrays.stream(node.getChildren())
                .filter(child -> child.getIndex() >= offset && child.getType() == tokenType)
                .findFirst();
    }

    /**
     * Checks whether the first child {@code JavadocTokenType.TEXT} node matches given pattern.
     *
     * @param ast parent javadoc node
     * @param pattern pattern to match
     * @return true if one of child text nodes matches pattern
     */
    public static boolean isChildNodeTextMatches(DetailNode ast, Pattern pattern) {
        return getFirstChildOfType(ast, JavadocTokenTypes.TEXT, 0)
                .map(DetailNode::getText)
                .map(pattern::matcher)
                .map(Matcher::matches)
                .orElse(Boolean.FALSE);
    }
}
