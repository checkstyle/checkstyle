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
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import org.checkerframework.checker.formatter.qual.Format;

/**
 * Class for scraping module metadata from the corresponding class' class-level javadoc.
 */
@FileStatefulCheck
public class JavadocMetadataScraper extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DESC_MISSING = "javadocmetadatascraper.description.missing";

    /** Module details store used for testing. */
    private static final Map<String, ModuleDetails> MODULE_DETAILS_STORE = new HashMap<>();

    /** Regular expression for property location in class-level javadocs. */
    private static final Pattern PROPERTY_TAG = Pattern.compile("\\s*Property\\s*");

    /** Regular expression for property type location in class-level javadocs. */
    private static final Pattern TYPE_TAG = Pattern.compile("^ Type is\\s.*");

    /** Regular expression for property validation type location in class-level javadocs. */
    private static final Pattern VALIDATION_TYPE_TAG =
            Pattern.compile("\\s.*Validation type is\\s.*");

    /** Regular expression for property default value location in class-level javadocs. */
    private static final Pattern DEFAULT_VALUE_TAG = Pattern.compile("^ Default value is:*.*");

    /** Regular expression for check example location in class-level javadocs. */
    private static final Pattern EXAMPLES_TAG =
            Pattern.compile("\\s*To configure the (default )?check.*");

    /** Regular expression for module parent location in class-level javadocs. */
    private static final Pattern PARENT_TAG = Pattern.compile("\\s*Parent is\\s*");

    /** Regular expression for module violation messages location in class-level javadocs. */
    private static final Pattern VIOLATION_MESSAGES_TAG =
            Pattern.compile("\\s*Violation Message Keys:\\s*");

    /** Regular expression for detecting ANTLR tokens(for e.g. CLASS_DEF). */
    private static final Pattern TOKEN_TEXT_PATTERN = Pattern.compile("([A-Z_]{2,})+");

    /** Regular expression for removal of @code{-} present at the beginning of texts. */
    private static final Pattern DESC_CLEAN = Pattern.compile("-\\s");

    /** Regular expression for file separator corresponding to the host OS. */
    private static final Pattern FILE_SEPARATOR_PATTERN =
            Pattern.compile(Pattern.quote(System.getProperty("file.separator")));

    /** Regular expression for quotes. */
    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");

    /** Java file extension. */
    private static final String JAVA_FILE_EXTENSION = ".java";

    /**
     * This set contains faulty property default value which should not be written to the XML
     * metadata files.
     */
    private static final Set<String> PROPERTIES_TO_NOT_WRITE = Set.of(
                    "null",
                    "the charset property of the parent <a href=https://checkstyle.org/"
                        + "config.html#Checker>Checker</a> module");

    /**
     * Format for exception message for missing type for check property.
     */
    private static final String PROP_TYPE_MISSING = "Type for property '%s' is missing";

    /**
     * Format for exception message for missing default value for check property.
     */
    @Format({})
    private static final String PROP_DEFAULT_VALUE_MISSING =
        "Default value for property '%s' is missing";

    /** ModuleDetails instance for each module AST traversal. */
    private ModuleDetails moduleDetails;

    /**
     * Boolean variable which lets us know whether violation message section is being scraped
     * currently.
     */
    private boolean scrapingViolationMessageList;

    /**
     * Boolean variable which lets us know whether we should scan and scrape the current javadoc
     * or not. Since we need only class level javadoc, it becomes true at its root and false after
     * encountering {@code JavadocTokenTypes.SINCE_LITERAL}.
     */
    private boolean toScan;

    /** DetailNode pointing to the root node of the class level javadoc of the class. */
    private DetailNode rootNode;

    /**
     * Child number of the property section node, where parent is the class level javadoc root
     * node.
     */
    private int propertySectionStartIdx;

    /**
     * Child number of the example section node, where parent is the class level javadoc root
     * node.
     */
    private int exampleSectionStartIdx;

    /**
     * Child number of the parent section node, where parent is the class level javadoc root
     * node.
     */
    private int parentSectionStartIdx;

    /**
     * Control whether to write XML output or not.
     */
    private boolean writeXmlOutput = true;

    /**
     * Setter to control whether to write XML output or not.
     *
     * @param writeXmlOutput whether to write XML output or not.
     */
    public final void setWriteXmlOutput(boolean writeXmlOutput) {
        this.writeXmlOutput = writeXmlOutput;
    }

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
            toScan = false;
            scrapingViolationMessageList = false;
            propertySectionStartIdx = -1;
            exampleSectionStartIdx = -1;
            parentSectionStartIdx = -1;

            String moduleName = getModuleSimpleName();
            final String checkModuleExtension = "Check";
            if (moduleName.endsWith(checkModuleExtension)) {
                moduleName = moduleName
                        .substring(0, moduleName.length() - checkModuleExtension.length());
            }
            moduleDetails.setName(moduleName);
            moduleDetails.setFullQualifiedName(getPackageName(getFilePath()));
            moduleDetails.setModuleType(getModuleType());
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (toScan) {
            scrapeContent(ast);
        }

        if (ast.getType() == JavadocTokenTypes.JAVADOC) {
            final DetailAST parent = getParent(getBlockCommentAst());
            if (parent.getType() == TokenTypes.CLASS_DEF) {
                rootNode = ast;
                toScan = true;
            }
        }
        else if (ast.getType() == JavadocTokenTypes.SINCE_LITERAL) {
            toScan = false;
        }
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        moduleDetails.setDescription(getDescriptionText());
        if (isTopLevelClassJavadoc()) {
            if (moduleDetails.getDescription().isEmpty()) {
                final String fullQualifiedName = moduleDetails.getFullQualifiedName();
                log(rootAst.getLineNumber(), MSG_DESC_MISSING,
                        fullQualifiedName.substring(fullQualifiedName.lastIndexOf('.') + 1));
            }
            else if (writeXmlOutput) {
                try {
                    XmlMetaWriter.write(moduleDetails);
                }
                catch (TransformerException | ParserConfigurationException ex) {
                    throw new IllegalStateException(
                            "Failed to write metadata into XML file for module: "
                                    + getModuleSimpleName(), ex);
                }
            }
            if (!writeXmlOutput) {
                MODULE_DETAILS_STORE.put(moduleDetails.getFullQualifiedName(), moduleDetails);
            }

        }
    }

    /**
     * Method containing the core logic of scraping. This keeps track and decides which phase of
     * scraping we are in, and accordingly call other subroutines.
     *
     * @param ast javadoc ast
     */
    private void scrapeContent(DetailNode ast) {
        if (ast.getType() == JavadocTokenTypes.PARAGRAPH) {
            if (isParentText(ast)) {
                parentSectionStartIdx = getParentIndexOf(ast);
                moduleDetails.setParent(getParentText(ast));
            }
            else if (isViolationMessagesText(ast)) {
                scrapingViolationMessageList = true;
            }
            else if (exampleSectionStartIdx == -1
                    && isExamplesText(ast)) {
                exampleSectionStartIdx = getParentIndexOf(ast);
            }
        }
        else if (ast.getType() == JavadocTokenTypes.LI) {
            if (isPropertyList(ast)) {
                if (propertySectionStartIdx == -1) {
                    propertySectionStartIdx = getParentIndexOf(ast);
                }
                moduleDetails.addToProperties(createProperties(ast));
            }
            else if (scrapingViolationMessageList) {
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
        if (propertyNameNode.isPresent()) {
            final DetailNode propertyNameTag = propertyNameNode.orElseThrow();
            final String propertyName = getTextFromTag(propertyNameTag);

            final DetailNode propertyType = getFirstChildOfMatchingText(nodeLi, TYPE_TAG)
                .orElseThrow(() -> {
                    return new MetadataGenerationException(String.format(
                        Locale.ROOT, PROP_TYPE_MISSING, propertyName)
                    );
                });
            final String propertyDesc = DESC_CLEAN.matcher(
                    constructSubTreeText(nodeLi, propertyNameTag.getIndex() + 1,
                            propertyType.getIndex() - 1))
                    .replaceAll(Matcher.quoteReplacement(""));

            modulePropertyDetails.setDescription(propertyDesc.trim());
            modulePropertyDetails.setName(propertyName);
            modulePropertyDetails.setType(getTagTextFromProperty(nodeLi, propertyType));

            final Optional<DetailNode> validationTypeNodeOpt = getFirstChildOfMatchingText(nodeLi,
                VALIDATION_TYPE_TAG);
            if (validationTypeNodeOpt.isPresent()) {
                final DetailNode validationTypeNode = validationTypeNodeOpt.orElseThrow();
                modulePropertyDetails.setValidationType(getTagTextFromProperty(nodeLi,
                    validationTypeNode));
            }

            final String defaultValue = getFirstChildOfMatchingText(nodeLi, DEFAULT_VALUE_TAG)
                .map(defaultValueNode -> getPropertyDefaultText(nodeLi, defaultValueNode))
                .orElseThrow(() -> {
                    return new MetadataGenerationException(String.format(
                        Locale.ROOT, PROP_DEFAULT_VALUE_MISSING, propertyName)
                    );
                });
            if (!PROPERTIES_TO_NOT_WRITE.contains(defaultValue)) {
                modulePropertyDetails.setDefaultValue(defaultValue);
            }
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
            tagNode = tagNodeOpt.orElseThrow();
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
        DetailNode detailNode = node;

        final Deque<DetailNode> stack = new ArrayDeque<>();
        stack.addFirst(detailNode);
        final Set<DetailNode> visited = new HashSet<>();
        final StringBuilder result = new StringBuilder(1024);
        while (!stack.isEmpty()) {
            detailNode = stack.removeFirst();

            if (visited.add(detailNode)) {
                final String childText = detailNode.getText();
                if (detailNode.getType() != JavadocTokenTypes.LEADING_ASTERISK
                        && !TOKEN_TEXT_PATTERN.matcher(childText).matches()) {
                    result.insert(0, childText);
                }
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
     * valid non-zero index amongst in the order of {@code propertySectionStartIdx},
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
     * @param defaultValueNode default value node
     * @return default property text
     */
    private static String getPropertyDefaultText(DetailNode nodeLi, DetailNode defaultValueNode) {
        final Optional<DetailNode> propertyDefaultValueTag = getFirstChildOfType(nodeLi,
                JavadocTokenTypes.JAVADOC_INLINE_TAG, defaultValueNode.getIndex() + 1);
        final String result;
        if (propertyDefaultValueTag.isPresent()) {
            result = getTextFromTag(propertyDefaultValueTag.orElseThrow());
        }
        else {
            final String tokenText = constructSubTreeText(nodeLi,
                    defaultValueNode.getIndex(), nodeLi.getChildren().length);
            result = cleanDefaultTokensText(tokenText);
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
        return resultNode.map(JavadocMetadataScraper::getTextFromTag).orElse("");
    }

    /**
     * Get text from {@code JavadocTokenTypes.JAVADOC_INLINE_TAG}.
     *
     * @param nodeTag target javadoc tag
     * @return text contained by the tag
     */
    private static String getTextFromTag(DetailNode nodeTag) {
        return Optional.ofNullable(nodeTag).map(JavadocMetadataScraper::getText).orElse("");
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
     * Get joined text from all text children nodes.
     *
     * @param parentNode parent node
     * @return the joined text of node
     */
    private static String getText(DetailNode parentNode) {
        return Arrays.stream(parentNode.getChildren())
                .filter(child -> child.getType() == JavadocTokenTypes.TEXT)
                .map(node -> QUOTE_PATTERN.matcher(node.getText().trim()).replaceAll(""))
                .collect(Collectors.joining(" "));
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
        DetailAST result = parentNode;
        if (result.getType() == TokenTypes.ANNOTATION) {
            result = parentNode.getParent().getParent();
        }
        else if (result.getType() == TokenTypes.MODIFIERS) {
            result = parentNode.getParent();
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
        return getFirstChildOfType(nodeParagraph, JavadocTokenTypes.JAVADOC_INLINE_TAG, 0)
                .map(JavadocMetadataScraper::getTextFromTag)
                .orElse(null);
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
        final String fullFileName = getFilePath();
        final String[] pathTokens = FILE_SEPARATOR_PATTERN.split(fullFileName);
        final String fileName = pathTokens[pathTokens.length - 1];
        return fileName.substring(0, fileName.length() - JAVA_FILE_EXTENSION.length());
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
        result.addLast(fileName.substring(0, fileName.length() - JAVA_FILE_EXTENSION.length()));
        return String.join(".", result);
    }

    /**
     * Getter method for {@code moduleDetailsStore}.
     *
     * @return map containing module details of supplied checks.
     */
    public static Map<String, ModuleDetails> getModuleDetailsStore() {
        return Collections.unmodifiableMap(MODULE_DETAILS_STORE);
    }

    /** Reset the module detail store of any previous information. */
    public static void resetModuleDetailsStore() {
        MODULE_DETAILS_STORE.clear();
    }

    /**
     * Check if the current javadoc block comment AST corresponds to the top-level class as we
     * only want to scrape top-level class javadoc.
     *
     * @return true if the current AST corresponds to top level class
     */
    private boolean isTopLevelClassJavadoc() {
        final DetailAST parent = getParent(getBlockCommentAst());
        final Optional<DetailAST> className = TokenUtil
                .findFirstTokenByPredicate(parent, child -> {
                    return parent.getType() == TokenTypes.CLASS_DEF
                            && child.getType() == TokenTypes.IDENT;
                });
        return className.isPresent()
                && getModuleSimpleName().equals(className.orElseThrow().getText());
    }

    /**
     * Checks whether the paragraph node corresponds to the example section.
     *
     * @param ast javadoc paragraph node
     * @return true if the section matches the example section marker
     */
    private static boolean isExamplesText(DetailNode ast) {
        return isChildNodeTextMatches(ast, EXAMPLES_TAG);
    }

    /**
     * Checks whether the list item node is part of a property list.
     *
     * @param nodeLi {@code JavadocTokenType.LI} node
     * @return true if the node is part of a property list
     */
    private static boolean isPropertyList(DetailNode nodeLi) {
        return isChildNodeTextMatches(nodeLi, PROPERTY_TAG);
    }

    /**
     * Checks whether the {@code JavadocTokenType.PARAGRAPH} node is referring to the violation
     * message keys javadoc segment.
     *
     * @param nodeParagraph paragraph javadoc node
     * @return true if paragraph node contains the violation message keys text
     */
    private static boolean isViolationMessagesText(DetailNode nodeParagraph) {
        return isChildNodeTextMatches(nodeParagraph, VIOLATION_MESSAGES_TAG);
    }

    /**
     * Checks whether the {@code JavadocTokenType.PARAGRAPH} node is referring to the parent
     * javadoc segment.
     *
     * @param nodeParagraph paragraph javadoc node
     * @return true if paragraph node contains the parent text
     */
    private static boolean isParentText(DetailNode nodeParagraph) {
        return isChildNodeTextMatches(nodeParagraph, PARENT_TAG);
    }

    /**
     * Checks whether the first child {@code JavadocTokenType.TEXT} node matches given pattern.
     *
     * @param ast parent javadoc node
     * @param pattern pattern to match
     * @return true if one of child text nodes matches pattern
     */
    private static boolean isChildNodeTextMatches(DetailNode ast, Pattern pattern) {
        return getFirstChildOfType(ast, JavadocTokenTypes.TEXT, 0)
                .map(DetailNode::getText)
                .map(pattern::matcher)
                .map(Matcher::matches)
                .orElse(Boolean.FALSE);
    }
}
