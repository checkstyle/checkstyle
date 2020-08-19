////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.meta;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

@FileStatefulCheck
public class JavadocMetadataScraper extends AbstractJavadocCheck {
    private static final Pattern PROPERTY_TAG = Pattern.compile("\\s*Property\\s*");
    private static final Pattern TYPE_TAG = Pattern.compile("\\s.*Type is\\s.*");
    private static final Pattern VALIDATION_TYPE_TAG =
            Pattern.compile("\\s.*Validation type is\\s.*");
    private static final Pattern DEFAULT_VALUE_TAG = Pattern.compile("\\s*Default value is:*.*");
    private static final Pattern EXAMPLES_TAG =
            Pattern.compile("\\s*To configure the (default )?check.*");
    private static final Pattern PARENT_TAG = Pattern.compile("\\s*Parent is\\s*");
    private static final Pattern VIOLATION_MESSAGES_TAG =
            Pattern.compile("\\s*Violation Message Keys:\\s*");
    private static final Pattern TOKEN_TEXT_PATTERN = Pattern.compile("([A-Z]+_*)+[A-Z]+");
    private static final Pattern DESC_CLEAN = Pattern.compile("-\\s");
    private static final Pattern FILE_SEPARATOR_PATTERN =
            Pattern.compile(Pattern.quote(System.getProperty("file.separator")));
    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");

    private static final Set<String> PROPERTIES_TO_NOT_WRITE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "null",
                    "the charset property of the parent"
    )));

    private ModuleDetails moduleDetails;
    private ScrapeStatus currentStatus;
    private boolean toScan;
    private String descriptionText;
    private DetailNode rootNode;
    private int propertySectionStartIdx;
    private int exampleSectionStartIdx;
    private int parentSectionStartIdx;

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
            JavadocTokenTypes.PARAGRAPH,
            JavadocTokenTypes.LI,
            JavadocTokenTypes.SINCE_LITERAL,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        if (isTopLevelClassJavadoc()) {
            moduleDetails = new ModuleDetails();
            currentStatus = ScrapeStatus.DESCRIPTION;
            toScan = false;
            descriptionText = "";
            propertySectionStartIdx = -1;
            exampleSectionStartIdx = -1;
            parentSectionStartIdx = -1;

            final String filePath = getFileContents().getFileName();
            String moduleName = getModuleSimpleName();
            if (moduleName.contains("Check")) {
                moduleName = moduleName.substring(0, moduleName.indexOf("Check"));
            }
            moduleDetails.setName(moduleName);
            moduleDetails.setFullQualifiedName(getPackageName(filePath));
            moduleDetails.setModuleType(getModuleType());
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (toScan) {
            scrapeContent(ast);
        }

        if (ast.getType() == JavadocTokenTypes.JAVADOC
            && getParent(getBlockCommentAst()).getType() == TokenTypes.CLASS_DEF) {
            rootNode = ast;
            toScan = true;
        }
        else if (ast.getType() == JavadocTokenTypes.SINCE_LITERAL) {
            toScan = false;
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        moduleDetails.setDescription(getDescriptionText());
        if (isTopLevelClassJavadoc()) {
            try {
                XmlMetaWriter.write(moduleDetails);
            }
            catch (TransformerException | ParserConfigurationException ex) {
                throw new IllegalStateException("Failed to write metadata into XML file for "
                        + "module: " + getModuleSimpleName(), ex);
            }
        }
    }

    /**
     * Method containing the core logic of scraping. This keeps track and decides which phase of
     * scraping we are in, and accordingly call other subroutines.
     *
     * @param ast javadoc ast
     */
    public void scrapeContent(DetailNode ast) {
        if (ast.getType() == JavadocTokenTypes.PARAGRAPH) {
            if (isParentText(ast)) {
                currentStatus = ScrapeStatus.PARENT;
                parentSectionStartIdx = getParentIndexOf(ast);
                moduleDetails.setParent(getParentText(ast));
            }
            else if (isViolationMessagesText(ast)) {
                currentStatus = ScrapeStatus.VIOLATION_MESSAGES;
            }
            else if (exampleSectionStartIdx == -1
                    && isExamplesText(ast)) {
                exampleSectionStartIdx = getParentIndexOf(ast);
            }
        }
        else if (ast.getType() == JavadocTokenTypes.LI) {
            if (isPropertyList(ast)) {
                currentStatus = ScrapeStatus.PROPERTY;

                if (propertySectionStartIdx == -1) {
                    propertySectionStartIdx = getParentIndexOf(ast);
                }

                moduleDetails.setDescription(descriptionText);
                moduleDetails.addToProperties(createProperties(ast));
            }
            else if (currentStatus == ScrapeStatus.VIOLATION_MESSAGES) {
                moduleDetails.addToViolationMessages(getViolationMessages(ast));
            }
        }
    }

    /**
     * Create the modulePropertyDetails content.
     *
     * @param nodeLi list item javadoc node
     * @return modulePropertyDetail object for the corresponding property
     */
    private static ModulePropertyDetails createProperties(DetailNode nodeLi) {
        final ModulePropertyDetails modulePropertyDetails = new ModulePropertyDetails();
        final Optional<DetailNode> propertyNameNode = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, 0);
        DetailNode propertyNameTag = null;
        if (propertyNameNode.isPresent()) {
            propertyNameTag = propertyNameNode.get();
        }

        final Optional<DetailNode> propertyTypeNode = getFirstChildOfMatchingText(nodeLi, TYPE_TAG);
        DetailNode propertyType = null;
        if (propertyTypeNode.isPresent()) {
            propertyType = propertyTypeNode.get();
        }

        final String propertyDesc = DESC_CLEAN.matcher(
                constructSubTreeText(nodeLi, propertyNameTag.getIndex() + 1,
                propertyType.getIndex() - 1))
                .replaceAll(Matcher.quoteReplacement(""));

        modulePropertyDetails.setDescription(propertyDesc.trim());
        modulePropertyDetails.setName(getTextFromTag(propertyNameTag));
        final Optional<DetailNode> typeNode = getFirstChildOfMatchingText(nodeLi, TYPE_TAG);
        if (typeNode.isPresent()) {
            modulePropertyDetails.setType(getTagTextFromProperty(nodeLi, typeNode.get()));
        }
        final Optional<DetailNode> validationTypeNodeOpt = getFirstChildOfMatchingText(nodeLi,
                VALIDATION_TYPE_TAG);
        DetailNode validationTypeNode = null;
        if (validationTypeNodeOpt.isPresent()) {
            validationTypeNode = validationTypeNodeOpt.get();
        }

        if (validationTypeNode != null) {
            modulePropertyDetails.setValidationType(getTagTextFromProperty(nodeLi,
                    validationTypeNode));
        }
        final String defaultValue = getPropertyDefaultText(nodeLi);
        if (!PROPERTIES_TO_NOT_WRITE.contains(defaultValue)) {
            modulePropertyDetails.setDefaultValue(defaultValue);
        }
        return modulePropertyDetails;
    }

    /**
     * Get tag text from property data.
     *
     * @param nodeLi javadoc li item node
     * @param propertyMeta property javadoc node
     * @return property metadata text
     */
    private static String getTagTextFromProperty(DetailNode nodeLi, DetailNode propertyMeta) {
        final Optional<DetailNode> tagNodeOpt = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, propertyMeta.getIndex() + 1);
        DetailNode tagNode = null;
        if (tagNodeOpt.isPresent()) {
            tagNode = tagNodeOpt.get();
        }
        return getTextFromTag(tagNode);
    }

    /**
     * Clean up the default token text by removing hyperlinks, and only keeping token type text.
     *
     * @param initialText unclean text
     * @return clean text
     */
    private static String cleanDefaultTokensText(String initialText) {
        final Set<String> tokens = new LinkedHashSet<>();
        final Matcher matcher = TOKEN_TEXT_PATTERN.matcher(initialText);
        while (matcher.find()) {
            tokens.add(matcher.group(0));
        }
        return String.join(",", tokens);
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
    private static String constructSubTreeText(DetailNode node, int childLeftLimit,
                                               int childRightLimit) {
        final StringBuilder result = new StringBuilder(1024);
        DetailNode detailNode = node;
        final Set<DetailNode> visited = new HashSet<>();

        final Deque<DetailNode> stack = new ArrayDeque<>();
        stack.addFirst(detailNode);
        while (!stack.isEmpty()) {
            detailNode = stack.getFirst();
            stack.removeFirst();

            if (!visited.contains(detailNode)) {
                final String childText = detailNode.getText();
                if (detailNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
                        && !TOKEN_TEXT_PATTERN.matcher(childText).matches()) {
                    result.insert(0, detailNode.getText());
                }
                visited.add(detailNode);
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
     * Create the description text with starting index as 0 and ending index would be the first
     * valid non zero index amongst in the order of {@code propertySectionStartIdx},
     * {@code exampleSectionStartIdx} and {@code parentSectionStartIdx}.
     *
     * @return description text
     */
    private String getDescriptionText() {
        final int descriptionEndIdx;
        if (propertySectionStartIdx > -1) {
            descriptionEndIdx = propertySectionStartIdx;
        }
        else if (exampleSectionStartIdx > -1) {
            descriptionEndIdx = exampleSectionStartIdx;
        }
        else {
            descriptionEndIdx = parentSectionStartIdx;
        }
        return constructSubTreeText(rootNode, 0, descriptionEndIdx - 1);
    }

    /**
     * Create property default text, which is either normal property value or list of tokens.
     *
     * @param nodeLi list item javadoc node
     * @return default property text
     */
    private static String getPropertyDefaultText(DetailNode nodeLi) {
        final String result;
        final Optional<DetailNode> defaultValueNodeOpt = getFirstChildOfMatchingText(nodeLi,
                DEFAULT_VALUE_TAG);
        DetailNode defaultValueNode = null;
        if (defaultValueNodeOpt.isPresent()) {
            defaultValueNode = defaultValueNodeOpt.get();
        }
        final Optional<DetailNode> propertyDefaultValueTagNode = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, defaultValueNode.getIndex() + 1);
        DetailNode propertyDefaultValueTag = null;
        if (propertyDefaultValueTagNode.isPresent()) {
            propertyDefaultValueTag = propertyDefaultValueTagNode.get();
        }
        if (propertyDefaultValueTag == null) {
            final String tokenText = constructSubTreeText(nodeLi,
                    defaultValueNode.getIndex(), nodeLi.getChildren().length);
            result = cleanDefaultTokensText(tokenText);
        }
        else {
            result = getTextFromTag(propertyDefaultValueTag);
        }
        return result;
    }

    /**
     * Get the violation message text for a specific key from the list item.
     *
     * @param nodeLi list item javadoc node
     * @return violation message key text
     */
    private static String getViolationMessages(DetailNode nodeLi) {
        final Optional<DetailNode> resultNode = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, 0);
        String result = "";
        if (resultNode.isPresent()) {
            result = getTextFromTag(resultNode.get());
        }
        return result;
    }

    /**
     * Get text from {@code JavadocTokenTypes.JAVADOC_INLINE_TAG}.
     *
     * @param nodeTag target javadoc tag
     * @return text contained by the tag
     */
    private static String getTextFromTag(DetailNode nodeTag) {
        String result = "";
        if (nodeTag != null) {
            final Optional<DetailNode> resultNode = getFirstChildOfType(
                    nodeTag, JavadocTokenTypes.TEXT, 0);
            if (resultNode.isPresent()) {
                result = QUOTE_PATTERN
                        .matcher(resultNode.get().getText().trim()).replaceAll("");
            }
        }
        return result;
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
     * Get first child of parent node matching the provided pattern.
     *
     * @param node parent node
     * @param pattern pattern to match against
     * @return the first child node matching the condition
     */
    private static Optional<DetailNode> getFirstChildOfMatchingText(DetailNode node,
                                                                    Pattern pattern) {
        return Arrays.stream(node.getChildren())
                .filter(child -> pattern.matcher(child.getText()).matches())
                .findFirst();
    }

    /**
     * Returns parent node, removing modifier/annotation nodes.
     *
     * @param commentBlock child node.
     * @return parent node.
     */
    private static DetailAST getParent(DetailAST commentBlock) {
        final DetailAST parentNode = commentBlock.getParent();
        DetailAST result = null;
        if (parentNode != null) {
            result = parentNode;
            if (result.getType() == TokenTypes.ANNOTATION) {
                result = parentNode.getParent().getParent();
            }
            else if (result.getType() == TokenTypes.MODIFIERS) {
                result = parentNode.getParent();
            }
        }
        return result;
    }

    /**
     * Traverse parents until we reach the root node (@code{JavadocTokenTypes.JAVADOC})
     * child and return its index.
     *
     * @param node subtree child node
     * @return root node child index
     */
    private static int getParentIndexOf(DetailNode node) {
        DetailNode currNode = node;
        while (currNode.getParent().getIndex() != -1) {
            currNode = currNode.getParent();
        }
        return currNode.getIndex();
    }

    /**
     * Get module parent text from paragraph javadoc node.
     *
     * @param nodeParagraph paragraph javadoc node
     * @return parent text
     */
    private static String getParentText(DetailNode nodeParagraph) {
        String result = null;
        final Optional<DetailNode> resultNode = getFirstChildOfType(nodeParagraph,
                JavadocTokenTypes.TEXT, 0);
        if (resultNode.isPresent()
                && PARENT_TAG.matcher(resultNode.get().getText()).matches()) {
            final Optional<DetailNode> childNode = getFirstChildOfType(nodeParagraph,
                    JavadocTokenTypes.JAVADOC_INLINE_TAG, 0);
            if (childNode.isPresent()) {
                result = getTextFromTag(childNode.get());
            }
        }
        return result;
    }

    /**
     * Get module type(check/filter/filefilter) based on file name.
     *
     * @return module type
     */
    private ModuleType getModuleType() {
        final String simpleModuleName = getModuleSimpleName();
        final ModuleType result;
        if (simpleModuleName.endsWith("FileFilter")) {
            result = ModuleType.FILEFILTER;
        }
        else if (simpleModuleName.endsWith("Filter")) {
            result = ModuleType.FILTER;
        }
        else {
            result = ModuleType.CHECK;
        }
        return result;
    }

    /**
     * Extract simple file name from the whole file path name.
     *
     * @return simple module name
     */
    private String getModuleSimpleName() {
        final String fullFileName = getFileContents().getFileName();
        final String[] pathTokens = FILE_SEPARATOR_PATTERN.split(fullFileName);
        final String fileName = pathTokens[pathTokens.length - 1];
        return fileName.substring(0, fileName.length() - 5);
    }

    /**
     * Retrieve package name of module from the absolute file path.
     *
     * @param filePath absolute file path
     * @return package name
     */
    private static String getPackageName(String filePath) {
        final Deque<String> result = new ArrayDeque<>();
        final String[] filePathTokens = FILE_SEPARATOR_PATTERN.split(filePath);
        for (int i = filePathTokens.length - 1; i >= 0; i--) {
            if ("java".equals(filePathTokens[i]) || "resources".equals(filePathTokens[i])) {
                break;
            }
            result.addFirst(filePathTokens[i]);
        }
        final String fileName = result.removeLast();
        result.addLast(fileName.substring(0, fileName.length() - 5));
        return String.join(".", result);
    }

    /**
     * Check if the current javadoc block comment AST corresponds to the top-level class as we
     * only want to scrape top-level class javadoc.
     *
     * @return true if the current AST corresponds to top level class
     */
    public boolean isTopLevelClassJavadoc() {
        final DetailAST parent = getParent(getBlockCommentAst());
        final Optional<DetailAST> className = TokenUtil
                .findFirstTokenByPredicate(parent, child -> {
                    return parent.getType() == TokenTypes.CLASS_DEF
                            && child.getType() == TokenTypes.IDENT;
                });
        return className.isPresent()
                && getModuleSimpleName().equals(className.get().getText());
    }

    /**
     * Checks whether the paragraph node corresponds to the example section.
     *
     * @param ast javadoc paragraph node
     * @return true if the section matches the example section marker
     */
    private static boolean isExamplesText(DetailNode ast) {
        final Optional<DetailNode> resultNode = getFirstChildOfType(ast, JavadocTokenTypes.TEXT, 0);
        return resultNode.isPresent()
                && EXAMPLES_TAG.matcher(resultNode.get().getText()).matches();
    }

    /**
     * Checks whether the list item node is part of a property list.
     *
     * @param nodeLi {@code JavadocTokenType.LI} node
     * @return true if the node is part of a property list
     */
    private static boolean isPropertyList(DetailNode nodeLi) {
        final Optional<DetailNode> firstTextChildToken =
                getFirstChildOfType(nodeLi, JavadocTokenTypes.TEXT, 0);
        return firstTextChildToken.isPresent()
                && PROPERTY_TAG.matcher(firstTextChildToken.get().getText()).matches();
    }

    /**
     * Checks whether the {@code JavadocTokenType.PARAGRAPH} node is referring to the violation
     * message keys javadoc segment.
     *
     * @param nodeParagraph paragraph javadoc node
     * @return true if paragraph node contains the violation message keys text
     */
    private static boolean isViolationMessagesText(DetailNode nodeParagraph) {
        final Optional<DetailNode> resultNode = getFirstChildOfType(nodeParagraph,
                JavadocTokenTypes.TEXT, 0);
        return resultNode.isPresent()
                && VIOLATION_MESSAGES_TAG.matcher(resultNode.get().getText()).matches();
    }

    /**
     * Checks whether the {@code JavadocTokenType.PARAGRAPH} node is referring to the parent
     * javadoc segment.
     *
     * @param nodeParagraph paragraph javadoc node
     * @return true if paragraph node contains the parent text
     */
    private static boolean isParentText(DetailNode nodeParagraph) {
        final Optional<DetailNode> resultNode = getFirstChildOfType(nodeParagraph,
                JavadocTokenTypes.TEXT, 0);
        return resultNode.isPresent()
                && PARENT_TAG.matcher(resultNode.get().getText()).matches();
    }

    /**
     * Status used to keep track in which phase(for e.g. creating description/violation message
     * keys) of scraping we are in, while processing the tokens.
     */
    private enum ScrapeStatus {
        DESCRIPTION,
        PROPERTY,
        PARENT,
        VIOLATION_MESSAGES
    }
}
