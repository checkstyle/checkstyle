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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;

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

    /** Regular expression for detecting ANTLR tokens(for e.g. CLASS_DEF). */
    private static final Pattern TOKEN_TEXT_PATTERN = Pattern.compile("([A-Z_]{2,})+");

    /** Regular expression for file separator corresponding to the host OS. */
    private static final Pattern FILE_SEPARATOR_PATTERN =
            Pattern.compile(Pattern.quote(System.getProperty("file.separator")));

    /** Regular expression for quotes. */
    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");

    /** Java file extension. */
    private static final String JAVA_FILE_EXTENSION = ".java";

    /** ModuleDetails instance for each module AST traversal. */
    private ModuleDetails moduleDetails;

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
        // no code, temporarily exists until #17746
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        // no code, temporarily exists until #17746
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        // no code, temporarily exists until #17746
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
    public static int getParentIndexOf(DetailNode node) {
        DetailNode currNode = node;
        while (currNode.getParent().getIndex() != -1) {
            currNode = currNode.getParent();
        }
        return currNode.getIndex();
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
