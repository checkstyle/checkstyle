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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that in Javadoc comments, each API name is linked with
 * {@code {@link}} or {@code {@linkplain}} only on its first occurrence.
 * Subsequent links to the same API name in the same comment are flagged.
 * </div>
 *
 * <p>
 * Rationale: From the
 * <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html">
 * Documentation Comments style guide</a>, links call attention to
 * themselves by their color and underline in HTML, and by their length
 * in source code doc comments. Linking the same name multiple times
 * is redundant.
 * </p>
 *
 * @since 13.10.0
 */
@FileStatefulCheck
public class JavadocLinkFirstOccurrenceCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.link.first.occurrence";

    /**
     * Dot.
     */
    private static final char DOT = '.';

    /**
     * Set of reference keys already seen in the current Javadoc comment.
     */
    private final Set<String> linkedNames = new HashSet<>();

    /**
     * Map of imported simple names to fully qualified names.
     */
    private Map<String, String> importedNames;

    /**
     * Set of star import base packages.
     */
    private Set<String> starImports;

    /**
     * Creates a new {@code JavadocLinkFirstOccurrenceCheck} instance.
     */
    public JavadocLinkFirstOccurrenceCheck() {
        // no code by default
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.BLOCK_COMMENT_BEGIN,
            TokenTypes.IMPORT,
        };
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.LINK_INLINE_TAG,
            JavadocCommentsTokenTypes.LINKPLAIN_INLINE_TAG,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        super.beginTree(rootAST);
        importedNames = new HashMap<>();
        starImports = new HashSet<>();
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        linkedNames.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.IMPORT) {
            handleImport(ast);
        }
        else {
            super.visitToken(ast);
        }
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailNode reference = JavadocUtil.findFirstToken(ast,
                JavadocCommentsTokenTypes.REFERENCE);
        final String originalReference = getNodeText(reference);
        final String resolvedKey = resolveReference(originalReference);
        if (!linkedNames.add(resolvedKey)) {
            log(ast, MSG_KEY, originalReference);
        }
    }

    /**
     * Processes import statements and records imported names.
     *
     * @param ast import node
     */
    private void handleImport(DetailAST ast) {
        final String importText = FullIdent.createFullIdentBelow(ast).getText();
        if (importText.endsWith(".*")) {
            starImports.add(importText.substring(0, importText.length() - 2));
        }
        else {
            final int lastDot = importText.lastIndexOf(DOT);
            final String simple = importText.substring(lastDot + 1);
            importedNames.put(simple, importText);
        }
    }

    /**
     * Resolves a reference text to a canonical key for identity comparison.
     * If the reference contains a simple class name, it is resolved
     * through imports.
     *
     * @param reference the raw reference text
     * @return the resolved identity key
     */
    private String resolveReference(String reference) {
        final int hashIndex = reference.indexOf('#');
        final String className;
        final String memberPart;
        if (hashIndex == -1) {
            className = reference;
            memberPart = "";
        }
        else {
            className = reference.substring(0, hashIndex);
            memberPart = reference.substring(hashIndex);
        }
        final String resolved;
        if (className.contains(".")) {
            resolved = className;
        }
        else {
            resolved = resolveClass(className);
        }
        return resolved + memberPart;
    }

    /**
     * Resolves a simple class name to its fully qualified name
     * through imports.
     *
     * @param name the simple class name
     * @return the resolved fully qualified name
     */
    private String resolveClass(String name) {
        final String importCandidate = importedNames.get(name);
        final String result;
        if (importCandidate != null) {
            result = importCandidate;
        }
        else if (starImports.isEmpty()) {
            result = "java.lang." + name;
        }
        else {
            result = starImports.iterator().next() + DOT + name;
        }
        return result;
    }

    /**
     * Recursively builds the full text of a node by concatenating
     * the text of all its leaf descendants.
     *
     * @param node the node to get text from
     * @return the concatenated text, or null if the node is null
     */
    private static String getNodeText(DetailNode node) {
        final StringBuilder sb = new StringBuilder();
        DetailNode child = node.getFirstChild();
        while (child != null) {
            sb.append(getNodeText(child));
            child = child.getNextSibling();
        }
        final String text;
        if (sb.length() == 0) {
            text = node.getText();
        }
        else {
            text = sb.toString();
        }
        return text;
    }

}
