////
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that one blank line before the block tag if it is present in Javadoc.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
 * Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.tag.line.before}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
 *
 * @since 8.36
 */
@StatelessCheck
public class RequireEmptyLineBeforeBlockTagGroupCheck extends AbstractJavadocCheck {

    /**
     * The key in "messages.properties" for the message that describes a tag in javadoc
     * requiring an empty line before it.
     */
    public static final String MSG_JAVADOC_TAG_LINE_BEFORE = "javadoc.tag.line.before";

    /**
     * Case when space separates the tag and the asterisk like in the below example.
     * <pre>
     *  /**
     *   * &#64;param noSpace there is no space here
     * </pre>
     */
    private static final List<Integer> ONLY_TAG_VARIATION_1 = Arrays.asList(
        JavadocTokenTypes.WS,
        JavadocTokenTypes.LEADING_ASTERISK,
        JavadocTokenTypes.NEWLINE);

    /**
     * Case when no space separates the tag and the asterisk like in the below example.
     * <pre>
     *  /**
     *   *&#64;param noSpace there is no space here
     * </pre>
     */
    private static final List<Integer> ONLY_TAG_VARIATION_2 = Arrays.asList(
        JavadocTokenTypes.LEADING_ASTERISK,
        JavadocTokenTypes.NEWLINE);

    /**
     * Returns only javadoc tags so visitJavadocToken only receives javadoc tags.
     *
     * @return only javadoc tags.
     */
    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC_TAG,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    /**
     * Logs when there is no empty line before the tag.
     *
     * @param tagNode the at tag node to check for an empty space before it.
     */
    @Override
    public void visitJavadocToken(DetailNode tagNode) {
        // No need to filter token because overridden getDefaultJavadocTokens ensures that we only
        // receive JAVADOC_TAG DetailNode.
        if (!isAnotherTagBefore(tagNode)
            && !isOnlyTagInWholeJavadoc(tagNode)
            && hasInsufficientConsecutiveNewlines(tagNode)) {
            log(tagNode.getLineNumber(),
                MSG_JAVADOC_TAG_LINE_BEFORE,
                tagNode.getChildren()[0].getText());
        }
    }

    /**
     * Returns true when there is a javadoc tag before the provided tagNode.
     *
     * @param tagNode the javadoc tag node, to look for more tags before it.
     * @return true when there is a javadoc tag before the provided tagNode.
     */
    private static boolean isAnotherTagBefore(DetailNode tagNode) {
        boolean found = false;
        DetailNode currentNode = JavadocUtil.getPreviousSibling(tagNode);
        while (currentNode != null) {
            if (currentNode.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                found = true;
                break;
            }
            currentNode = JavadocUtil.getPreviousSibling(currentNode);
        }
        return found;
    }

    /**
     * Returns true when there are is only whitespace and asterisks before the provided tagNode.
     * When javadoc has only a javadoc tag like {@literal @} in it, the JAVADOC_TAG in a JAVADOC
     * detail node will always have 2 or 3 siblings before it. The parse tree looks like:
     * <pre>
     * JAVADOC[3x0]
     * |--NEWLINE[3x0] : [\n]
     * |--LEADING_ASTERISK[4x0] : [ *]
     * |--WS[4x2] : [ ]
     * |--JAVADOC_TAG[4x3] : [@param T The bar.\n ]
     * </pre>
     * Or it can also look like:
     * <pre>
     * JAVADOC[3x0]
     * |--NEWLINE[3x0] : [\n]
     * |--LEADING_ASTERISK[4x0] : [ *]
     * |--JAVADOC_TAG[4x3] : [@param T The bar.\n ]
     * </pre>
     * We do not include the variation
     * <pre>
     *  /**&#64;param noSpace there is no space here
     * </pre>
     * which results in the tree
     * <pre>
     * JAVADOC[3x0]
     * |--JAVADOC_TAG[4x3] : [@param noSpace there is no space here\n ]
     * </pre>
     * because this one is invalid. We must recommend placing a blank line to separate &#64;param
     * from the first javadoc asterisks.
     *
     * @param tagNode the at tag node to check if there is nothing before it
     * @return true if there is no text before the tagNode
     */
    private static boolean isOnlyTagInWholeJavadoc(DetailNode tagNode) {
        final List<Integer> previousNodeTypes = new ArrayList<>();
        DetailNode currentNode = JavadocUtil.getPreviousSibling(tagNode);
        while (currentNode != null) {
            previousNodeTypes.add(currentNode.getType());
            currentNode = JavadocUtil.getPreviousSibling(currentNode);
        }
        return ONLY_TAG_VARIATION_1.equals(previousNodeTypes)
            || ONLY_TAG_VARIATION_2.equals(previousNodeTypes);
    }

    /**
     * Returns true when there are not enough empty lines before the provided tagNode.
     *
     * <p>Iterates through the previous siblings of the tagNode looking for empty lines until
     * there are no more siblings or it hits something other than asterisk, whitespace or newline.
     * If it finds at least one empty line, return true. Return false otherwise.</p>
     *
     * @param tagNode the tagNode to check if there are sufficient empty lines before it.
     * @return true if there are not enough empty lines before the tagNode.
     */
    private static boolean hasInsufficientConsecutiveNewlines(DetailNode tagNode) {
        int count = 0;
        DetailNode currentNode = JavadocUtil.getPreviousSibling(tagNode);
        while (currentNode != null
            && (currentNode.getType() == JavadocTokenTypes.NEWLINE
            || currentNode.getType() == JavadocTokenTypes.WS
            || currentNode.getType() == JavadocTokenTypes.LEADING_ASTERISK)) {
            if (currentNode.getType() == JavadocTokenTypes.NEWLINE) {
                count++;
            }
            currentNode = JavadocUtil.getPreviousSibling(currentNode);
        }

        return count <= 1;
    }
}
