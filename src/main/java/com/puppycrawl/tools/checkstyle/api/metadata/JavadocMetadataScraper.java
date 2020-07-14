package com.puppycrawl.tools.checkstyle.api.metadata;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;

public class JavadocMetadataScraper extends AbstractJavadocCheck {
    private static final Pattern TAGS_TO_IGNORE = Pattern.compile("([A-Z]+_*)+[A-Z]+");
    private ModuleDetails moduleDetails;
    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
                JavadocTokenTypes.PARAGRAPH,
                JavadocTokenTypes.LI
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        String res = constructSubTreeText(ast);
        final int parentType = getParentType(getBlockCommentAst()); // fix class level javadoc
        // detection
        moduleDetails = new ModuleDetails();
        scrapeContent(ast);
        // xmlwriter.write(moduleDetails);

    }

    public void scrapeContent(DetailNode ast) {

        boolean descriptionDone;
        boolean propertiesDone;
        boolean violationMessagesDone;
        for (DetailNode node : ast.getChildren()) {
            if (node.getType() == JavadocTokenTypes.HTML_ELEMENT) {

            }
            // if type is <p> and desc not found -> fillDesc
            // if type is <ul> and it contains properties -> stop fillDesc and start fillProp
            // if type is </ul> stop fillProp
            // if type is <p> and contains parent -> start fillParent and stop at next </p>
            // then do fillViolationMessgages

        }
    }

//    private String getDescription(DetailNode descriptionNode) {
//
//    }

    private DetailNode getHtmlElementType(DetailNode htmlElement) {
        DetailNode resultNode = null;
        final DetailNode childNode = htmlElement.getChildren()[0];
        if (childNode.getType() == JavadocTokenTypes.HTML_ELEMENT) {
            resultNode = childNode;
        }
//        else if (childNode.getType() == JavadocTokenTypes.HTML_TAG) {
//            resultNode = childNode.getChildren()[0];
//        }
        return resultNode;
    }

    private String constructSubTreeText(DetailNode node) {
        DetailNode detailNode = node;
        Deque<DetailNode> stack = new ArrayDeque<>();
        stack.addFirst(detailNode);
        Set<DetailNode> visited = new HashSet<>();
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            detailNode = stack.getFirst();
            stack.removeFirst();

            if (!visited.contains(detailNode)) {
                final String childText = detailNode.getText();
                if (detailNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
                        && !TAGS_TO_IGNORE.matcher(childText).matches()) {
                    result.insert(0, detailNode.getText());
                }
                visited.add(detailNode);
            }

            for (DetailNode child : detailNode.getChildren()) {
                if (!visited.contains(child)) {
                    stack.addFirst(child);
                }
            }
        }
        return result.toString();
    }

    // add javadoc
    private DetailNode findChildOfType(DetailNode node, int tokenType) {
        return (DetailNode) Arrays.stream(node.getChildren())
                .filter(child -> child.getType() == tokenType);
    }

    /**
     * Returns type of parent node.
     *
     * @param commentBlock child node.
     * @return parent type.
     */
    private static int getParentType(DetailAST commentBlock) {
        final DetailAST parentNode = commentBlock.getParent();
        int result = 0;
        if (parentNode != null) {
            result = parentNode.getType();
            if (result == TokenTypes.TYPE || result == TokenTypes.MODIFIERS) {
                result = parentNode.getParent().getType();
            }
        }
        return result;
    }
}
