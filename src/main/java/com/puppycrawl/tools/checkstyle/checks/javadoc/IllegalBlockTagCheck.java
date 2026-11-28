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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Detects a user-defined Javadoc block tag and reports a violation when the tag
 * is present with text that does not match {@code tagTextPattern}.
 * With the default pattern {@code ^$} (same as {@code Regexp} format default;
 * matches only empty content), any non-empty text of the configured tag is a
 * violation. No violation is reported when there is no Javadoc or when
 * {@code tag} is not configured.
 * </div>
 *
 * @since 13.10.0
 */
@StatelessCheck
public class IllegalBlockTagCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_ILLEGAL_PATTERN = "illegalblocktag.illegalPattern";

    /** Pattern that matches only empty content (Regexp format default). */
    private static final Pattern MATCH_NOTHING = CommonUtil.createPattern("^$");

    /** Specify the regexp that tag content is allowed to match. */
    private Pattern tagTextPattern = MATCH_NOTHING;

    /** Specify the name of tag. */
    @Nullable
    private String tag;

    /**
     * Creates a new {@code IllegalBlockTagCheck} instance.
     */
    public IllegalBlockTagCheck() {
        // no code by default
    }

    /**
     * Setter to specify the name of tag.
     *
     * @param tag tag to check
     * @since 13.10.0
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Setter to specify the regexp that tag content is allowed to match.
     * Content that does not match is treated as illegal.
     *
     * @param pattern a {@code Pattern} value
     * @since 13.10.0
     */
    public void setTagTextPattern(Pattern pattern) {
        tagTextPattern = pattern;
    }

    /**
     * Setter to control when to print violations if the Javadoc being examined by this check
     * violates the tight html rules defined at
     * <a href="https://checkstyle.org/writing-javadoc-checks.html#Tight-HTML_rules">
     *     Tight-HTML Rules</a>.
     *
     * @param shouldReportViolation value to which the field shall be set to
     * @since 13.10.0
     * @propertySince 13.10.0
     */
    @Override
    public void setViolateExecutionOnNonTightHtml(boolean shouldReportViolation) {
        super.setViolateExecutionOnNonTightHtml(shouldReportViolation);
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST javadocComment = findJavadoc(ast);
        if (javadocComment != null) {
            super.visitToken(javadocComment);
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String tagName = "@" + JavadocUtil.getTagName(ast);
        if (tagName.equals(tag)) {
            final String content = getTagContent(ast);
            if (!tagTextPattern.matcher(content).find()) {
                log(ast, MSG_ILLEGAL_PATTERN, JavadocUtil.getTagName(ast));
            }
        }
    }

    /**
     * Returns the raw content of the tag.
     *
     * @param javadocBlockTagNode The node representing a Javadoc block tag.
     *       This node must be of type {@link JavadocCommentsTokenTypes#JAVADOC_BLOCK_TAG}
     * @return The raw content of the tag.
     */
    private static String getTagContent(DetailNode javadocBlockTagNode) {
        final DetailNode tagNodeNextSibling = JavadocUtil.findFirstToken(
            javadocBlockTagNode.getFirstChild(),
            JavadocCommentsTokenTypes.TAG_NAME).getNextSibling();

        final int stringBuilderCapacity = 128;
        final StringBuilder rawTextBuilder = new StringBuilder(stringBuilderCapacity);
        if (tagNodeNextSibling != null) {
            // DFS to extract texts of all leaf nodes
            final Deque<DetailNode> stack = new ArrayDeque<>();
            stack.push(tagNodeNextSibling);

            while (!stack.isEmpty()) {
                final DetailNode currentNode = stack.pop();

                // append text if node is a leaf
                if (currentNode.getFirstChild() == null) {
                    rawTextBuilder.append(currentNode.getText());
                }

                final DetailNode nextSibling = currentNode.getNextSibling();
                final DetailNode firstChild = currentNode.getFirstChild();

                if (nextSibling != null) {
                    stack.push(nextSibling);
                }
                if (firstChild != null) {
                    stack.push(firstChild);
                }
            }
        }

        return rawTextBuilder.toString().stripLeading();
    }

    /**
     * Finds the Javadoc comment attached to a structural AST node.
     *
     * <p>The comment can be a child of the node itself or of the token that starts the
     * definition, such as its modifiers, an annotation, its type or its type parameters.
     * Only a comment that is a Javadoc in a valid position is returned, so a leading block
     * comment such as a license header is skipped. The search stops at the definition's
     * name, so comments of nested members or in a body are never taken.</p>
     *
     * @param ast the structural node (e.g., CLASS_DEF, METHOD_DEF)
     * @return the Javadoc block comment if found, or null
     */
    @Nullable
    private static DetailAST findJavadoc(DetailAST ast) {
        DetailAST javadoc = null;
        DetailAST child = ast.getFirstChild();
        while (child.getType() != TokenTypes.IDENT) {
            final DetailAST found = findLastJavadoc(child);
            if (found != null) {
                javadoc = found;
            }
            child = child.getNextSibling();
        }
        return javadoc;
    }

    /**
     * Finds the last Javadoc comment in a subtree of a definition header.
     *
     * @param node the root of the subtree
     * @return the last Javadoc block comment in the subtree, or null
     */
    @Nullable
    private static DetailAST findLastJavadoc(DetailAST node) {
        DetailAST javadoc = null;
        if (node.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            if (JavadocUtil.isJavadocComment(node)) {
                javadoc = node;
            }
        }
        else {
            DetailAST child = node.getFirstChild();
            while (child != null) {
                final DetailAST found = findLastJavadoc(child);
                if (found != null) {
                    javadoc = found;
                }
                child = child.getNextSibling();
            }
        }
        return javadoc;
    }

}
