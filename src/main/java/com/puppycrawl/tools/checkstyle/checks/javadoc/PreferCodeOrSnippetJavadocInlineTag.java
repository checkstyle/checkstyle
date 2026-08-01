package com.puppycrawl.tools.checkstyle.checks.javadoc;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

public class PreferCodeOrSnippetJavadocInlineTag extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SINGLE_LINE = "prefer.code.javadoc.singleline.tag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MULTI_LINE = "prefer.code.javadoc.multiline.tag";

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.HTML_ELEMENT,
        };
    }

    @Override
    public int[] getAcceptableJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode node) {
        if (isCodeOrPreTag(node)) {
            if (isSingleLineTag(node)) {
                log(node, MSG_KEY_SINGLE_LINE, getHtmlTagName(node));
            }
            else {
                log(node, MSG_KEY_MULTI_LINE, getHtmlTagName(node));
            }
        }
    }

    /**
     * Checks if the tag is code or pre tag.
     * @param node the node to check
     * @return {@code true} if the tag is code or pre tag, {@code false} otherwise
     */
    private boolean isCodeOrPreTag(DetailNode node) {
        final String tagName = getHtmlTagName(node);
        return "code".equals(tagName) || "pre".equals(tagName);
    }

    /**
     * Checks if the tag is single-line.
     * @param node the node to check
     * @return {@code true} if the tag is single-line, {@code false} otherwise
     */
    private boolean isSingleLineTag(DetailNode node) {
        DetailNode startOfTag = node.getFirstChild();
        while (startOfTag.getNextSibling().getType() != JavadocCommentsTokenTypes.HTML_TAG_END) {
            startOfTag = startOfTag.getNextSibling();
        }
        return startOfTag.getLineNumber() == node.getLineNumber();
    }

    /**
     * Gets the tag name from an HTML_ELEMENT node.
     *
     * @param htmlElement the HTML_ELEMENT node
     * @return the tag name (e.g., "code", "a")
     */
    @Nullable
    private static String getHtmlTagName(DetailNode htmlElement) {
        String result = null;
        final DetailNode htmlTagStart = JavadocUtil.findFirstToken(
            htmlElement, JavadocCommentsTokenTypes.HTML_TAG_START);
        if (htmlTagStart != null) {
            final DetailNode tagName = JavadocUtil.findFirstToken(
                htmlTagStart, JavadocCommentsTokenTypes.TAG_NAME);
            result = tagName.getText();
        }
        return result;
    }

}
