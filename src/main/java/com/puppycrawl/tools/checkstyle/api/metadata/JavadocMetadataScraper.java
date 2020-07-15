package com.puppycrawl.tools.checkstyle.api.metadata;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
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
    private ModuleDetails moduleDetails;

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[]{
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
        String res = constructSubTreeText(ast, 0, ast.getChildren().length - 1);
        final int parentType = getParentType(getBlockCommentAst()); // fix class level javadoc
        // detection
        moduleDetails = new ModuleDetails();
        scrapeContent(ast);
        // xmlwriter.write(moduleDetails);

    }

    public void scrapeContent(DetailNode ast) {
        // create enum Status for booleans
        boolean descriptionDone = false;
        boolean propertiesDone;
        boolean violationOngoing = false;
        boolean propertyCreationOngoing = false;
        String descriptionText = "";
        if (ast.getType() == JavadocTokenTypes.PARAGRAPH && !descriptionDone) {
            descriptionText += constructSubTreeText(ast, 0, ast.getChildren().length - 1);
        } else if (ast.getType() == JavadocTokenTypes.LI && isPropertyList(ast)) {
            descriptionDone = true;
            propertyCreationOngoing = true;

            List<ModulePropertyDetails> modulePropertyDetailsList = new ArrayList<>();
            modulePropertyDetailsList.add(createProperties(ast));
            // construct list
        } else if (ast.getType() == JavadocTokenTypes.PARAGRAPH && propertyCreationOngoing) {
            propertyCreationOngoing = false;
            // if Violation Messages violationOngoing = true
        }
        else if (ast.getType() == JavadocTokenTypes.LI && violationOngoing) {
            // create violation messages
        }
    }

    private ModulePropertyDetails createProperties(DetailNode nodeLi) {
        ModulePropertyDetails modulePropertyDetails = new ModulePropertyDetails();
        DetailNode propertyNameTag = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, 0);

        DetailNode typeChild = null;
        for (DetailNode child : nodeLi.getChildren()) {
            if (child.getText().matches("\\s.*Type is\\s.*")) {
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

    private String getTextFromTag(DetailNode nodeTag) {
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

    private boolean isPropertyList (DetailNode nodeLi){
        final DetailNode firstTextChildToken = getFirstChildOfType(nodeLi, JavadocTokenTypes.TEXT
                , 0);
        return firstTextChildToken != null
                && PROPERTY_TAG.matcher(firstTextChildToken.getText()).matches();
    }

    // add javadoc
    private DetailNode getFirstChildOfType (DetailNode node, int tokenType, int offset){
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
            if (result == TokenTypes.TYPE || result == TokenTypes.MODIFIERS) {
                result = parentNode.getParent().getType();
            }
        }
        return result;
    }
}
