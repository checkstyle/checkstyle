package com.puppycrawl.tools.checkstyle.api.metadata;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;

public class JavadocMetadataScraper extends AbstractJavadocCheck {
    private static final Pattern TAGS_TO_IGNORE = Pattern.compile("([A-Z]+_*)+[A-Z]+");
    private static final Pattern PROPERTY_TAG = Pattern.compile("\\s*Property\\s*");
    private static final Pattern DEFAULT_VALUE_TAG = Pattern.compile("\\s*Default value is\\s*");
    private static final Pattern DESC_CLEAN = Pattern.compile("-", Pattern.LITERAL);
    private static final Pattern PARENT_TAG = Pattern.compile("\\s*Parent is\\s*");
    private static final Pattern VIOLATION_MESSAGES_TAG =
            Pattern.compile("\\s*Violation Message Keys:\\s*");
    private static final Pattern TYPE_TAG = Pattern.compile("\\s.*Type is\\s.*");
    private final ModuleDetails moduleDetails = new ModuleDetails();
    private ScrapeStatus currentStatus = ScrapeStatus.DESCRIPTION;
    private boolean toScan;
    private String descriptionText = "";

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[]{
                JavadocTokenTypes.PACKAGE_CLASS,
                JavadocTokenTypes.JAVADOC,
                JavadocTokenTypes.PARAGRAPH,
                JavadocTokenTypes.LI,
                JavadocTokenTypes.SINCE_LITERAL
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (toScan) {
            scrapeContent(ast);
        }

        if (ast.getType() == JavadocTokenTypes.JAVADOC
            && getParentType(getBlockCommentAst()) == TokenTypes.CLASS_DEF) {
            toScan = true;
        }
        else if (ast.getType() == JavadocTokenTypes.SINCE_LITERAL) {
            toScan = false;
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        // xmlwriter.write(moduleDetails);
        System.out.println("breakpoint test");
    }

    public void scrapeContent(DetailNode ast) {
        if (ast.getType() == JavadocTokenTypes.PARAGRAPH) {
            if (currentStatus == ScrapeStatus.DESCRIPTION) {
                descriptionText += constructSubTreeText(ast, 0, ast.getChildren().length - 1);
            }
            else if (currentStatus == ScrapeStatus.PROPERTY) {
                currentStatus = ScrapeStatus.VIOLATION_MESSAGES;
            }
            else if (getParentText(ast) != null) {
                moduleDetails.setParent(getParentText(ast));
            }
            else if (isViolationMessagesText(ast)) {
                currentStatus = ScrapeStatus.VIOLATION_MESSAGES;
            }
        }
        else if (ast.getType() == JavadocTokenTypes.LI){
            if (isPropertyList(ast)) {
                currentStatus = ScrapeStatus.PROPERTY;

                moduleDetails.setDescription(descriptionText);
                moduleDetails.addToProperties(createProperties(ast));
            }
            else if (currentStatus == ScrapeStatus.VIOLATION_MESSAGES) {
                moduleDetails.addToViolationMessages(getViolationMessages(ast));
            }
        }

    }

    private static String getViolationMessages(DetailNode nodeLi) {
        return getTextFromTag(getFirstChildOfType(nodeLi, JavadocTokenTypes.JAVADOC_INLINE_TAG, 0));
    }

    private static boolean isViolationMessagesText(DetailNode nodePararaph) {
        boolean result = false;
        if (VIOLATION_MESSAGES_TAG.matcher(getFirstChildOfType(nodePararaph, JavadocTokenTypes.TEXT, 0).getText()).matches()) {
            result = true;
        }
        return result;
    }

    private static String getParentText(DetailNode nodeParagraph) {
        String result = null;
        if (PARENT_TAG.matcher(getFirstChildOfType(nodeParagraph, JavadocTokenTypes.TEXT, 0).getText()).matches()) {
            result = getTextFromTag(getFirstChildOfType(nodeParagraph,
                    JavadocTokenTypes.JAVADOC_INLINE_TAG, 0));
        }
        return result;
    }

    private static ModulePropertyDetails createProperties(DetailNode nodeLi) {
        ModulePropertyDetails modulePropertyDetails = new ModulePropertyDetails();
        DetailNode propertyNameTag = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, 0);

        DetailNode typeChild = null;
        for (DetailNode child : nodeLi.getChildren()) {
            if (TYPE_TAG.matcher(child.getText()).matches()) {
                typeChild = child;
                break;
            }
        }

        String propertyDesc = DESC_CLEAN.matcher(constructSubTreeText(nodeLi, propertyNameTag.getIndex() + 1,
                typeChild.getIndex() - 1)).replaceAll(Matcher.quoteReplacement(""));
        DetailNode propertyTypeTag = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, propertyNameTag.getIndex() + 1);

        DetailNode propertyDefaultValueTag = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, propertyTypeTag.getIndex() + 1);
        if (propertyDefaultValueTag == null) {
            int defaultValueTokensIdx = propertyTypeTag.getIndex();
            DetailNode candidate = nodeLi.getChildren()[defaultValueTokensIdx];
            while (!DEFAULT_VALUE_TAG.matcher(candidate.getText()).matches()) {
                candidate = nodeLi.getChildren()[defaultValueTokensIdx];
                defaultValueTokensIdx++;
            }
            final String tokenText = constructSubTreeText(nodeLi,
                    defaultValueTokensIdx, nodeLi.getChildren().length);
            modulePropertyDetails.setDefaultValue(cleanDefaultTokensText(tokenText));
        }
        else {
            modulePropertyDetails.setDefaultValue(getTextFromTag(propertyDefaultValueTag));
        }

        modulePropertyDetails.setName(getTextFromTag(propertyNameTag));
        modulePropertyDetails.setDescription(propertyDesc.trim());
        modulePropertyDetails.setType(getTextFromTag(propertyTypeTag));
        return modulePropertyDetails;

    }

    private static String cleanDefaultTokensText(String initialText) {
        Set<String> tokens = new HashSet<>();
        Matcher matcher = Pattern.compile("([A-Z]+_*)+[A-Z]+").matcher(initialText);
        while (matcher.find()) {
            tokens.add(matcher.group(0));
        }
        return String.join(",", tokens);
    }

    private static String getTextFromTag(DetailNode nodeTag) {
        return getFirstChildOfType(nodeTag, JavadocTokenTypes.TEXT, 0).getText().trim();
    }

    private static String constructSubTreeText(DetailNode node, int childLeftLimit, int childRightLimit){
        DetailNode detailNode = node;
        DetailNode treeRootNode = node;
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
                if (child.getParent().equals(treeRootNode)
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

    private static boolean isPropertyList(DetailNode nodeLi){
        final DetailNode firstTextChildToken = getFirstChildOfType(nodeLi, JavadocTokenTypes.TEXT
                , 0);
        return firstTextChildToken != null
                && PROPERTY_TAG.matcher(firstTextChildToken.getText()).matches();
    }

    // add javadoc
    private static DetailNode getFirstChildOfType(DetailNode node, int tokenType, int offset){
        return Arrays.stream(node.getChildren())
                .filter(child -> child.getIndex() >= offset && child.getType() == tokenType)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns type of parent node.
     *
     * @param commentBlock child node.
     * @return parent type.
     */
    private static int getParentType (DetailAST commentBlock){
        final DetailAST parentNode = commentBlock.getParent();
        int result = 0;
        if (parentNode != null) {
            result = parentNode.getType();
            if (result == TokenTypes.ANNOTATION) {
                result = parentNode.getParent().getParent().getType();
            }
            else if (result == TokenTypes.MODIFIERS) {
                result = parentNode.getParent().getType();
            }
        }
        return result;
    }

    private enum ScrapeStatus {
        DESCRIPTION,
        PROPERTY,
        VIOLATION_MESSAGES
    }
}
