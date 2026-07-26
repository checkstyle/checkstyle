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

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;

/**
 * <div>
 * Checks that in Javadoc comments, each API name is linked with
 * {@code {@link}} or {@code {@linkplain}} only on its first occurrence.
 * Subsequent links to the same API name in the same comment are flagged.
 * </div>
 *
 * <p>Rationale: From the OpenJDK style guide, links call attention to
 * themselves by their color and underline in HTML, and by their length
 * in source code doc comments. Linking the same name multiple times
 * is redundant.</p>
 *
 * @since 13.9.0
 */
@StatelessCheck
public class JavadocLinkFirstOccurrenceCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "javadoc.link.first.occurrence";

    /**
     * Creates a new {@code JavadocLinkFirstOccurrenceCheck} instance.
     */
    public JavadocLinkFirstOccurrenceCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final Set<String> linkedNames = new HashSet<>();
        collectLinkViolations(ast, linkedNames);
    }

    /**
     * Recursively walks the Javadoc tree to find {@code {@link}} and
     * {@code {@linkplain}} inline tags and report violations.
     *
     * @param node current AST node
     * @param linkedNames set of API names already linked in this comment
     */
    private void collectLinkViolations(DetailNode node, Set<String> linkedNames) {
        DetailNode child = node.getFirstChild();
        while (child != null) {
            final String apiName = extractApiName(child);
            if (apiName != null && !linkedNames.add(apiName)) {
                log(child, MSG_KEY, apiName);
            }
            collectLinkViolations(child, linkedNames);
            child = child.getNextSibling();
        }
    }

    /**
     * Finds the {@code LINK_INLINE_TAG} or {@code LINKPLAIN_INLINE_TAG}
     * child of a JAVADOC_INLINE_TAG node.
     *
     * @param inlineTag the JAVADOC_INLINE_TAG node
     * @return the link tag child, or null if not found
     */
    private static DetailNode findLinkTagChild(DetailNode inlineTag) {
        DetailNode result = findChildOfType(inlineTag,
                JavadocCommentsTokenTypes.LINK_INLINE_TAG);
        if (result == null) {
            result = findChildOfType(inlineTag,
                    JavadocCommentsTokenTypes.LINKPLAIN_INLINE_TAG);
        }
        return result;
    }

    /**
     * Extracts the simple API name from a {@code {@link}} or
     * {@code {@linkplain}} inline tag.
     *
     * @param inlineTag the JAVADOC_INLINE_TAG node
     * @return the simple API name, or null if no class reference found
     */
    private static String extractApiName(DetailNode inlineTag) {
        final DetailNode linkTag = findLinkTagChild(inlineTag);
        String apiName = null;
        if (linkTag != null) {
            final DetailNode reference = findChildOfType(linkTag,
                    JavadocCommentsTokenTypes.REFERENCE);
            final DetailNode identifier = findChildOfType(reference,
                    JavadocCommentsTokenTypes.IDENTIFIER);
            if (identifier != null) {
                apiName = getSimpleName(identifier.getText());
            }
        }
        return apiName;
    }

    /**
     * Extracts the simple class name from a possibly qualified name.
     * For example, {@code java.util.List} becomes {@code List}.
     *
     * @param qualifiedName the possibly qualified name
     * @return the simple name
     */
    private static String getSimpleName(String qualifiedName) {
        final int lastDotIndex = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(lastDotIndex + 1);
    }

    /**
     * Finds the first child of the given node that matches the specified type.
     *
     * @param node the parent node
     * @param type the token type to search for
     * @return the matching child node, or null if not found
     */
    private static DetailNode findChildOfType(DetailNode node, int type) {
        DetailNode result = null;
        for (DetailNode child = node.getFirstChild();
             child != null; child = child.getNextSibling()) {
            if (child.getType() == type) {
                result = child;
                break;
            }
        }
        return result;
    }

}
