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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the Javadoc comments for type definitions. By default, does
 * not check for author or version tags. The scope to verify is specified using the {@code Scope}
 * class and defaults to {@code Scope.PRIVATE}. To verify another scope, set property
 * scope to one of the {@code Scope} constants. To define the format for an author
 * tag or a version tag, set property authorFormat or versionFormat respectively to a
 * <a href="https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html">
 * pattern</a>.
 * </div>
 *
 * <p>
 * Does not perform checks for author and version tags for inner classes,
 * as they should be redundant because of outer class.
 * </p>
 *
 * <p>
 * Does not perform checks for type definitions that do not have any Javadoc comments.
 * </p>
 *
 * <p>
 * Error messages about type parameters and record components for which no param tags are present
 * can be suppressed by defining property {@code allowMissingParamTags}.
 * </p>
 *
 * @since 3.0
 */
@StatelessCheck
public class JavadocTypeCheck
    extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNKNOWN_TAG = "javadoc.unknownTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_TAG_FORMAT = "type.tagFormat";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_TAG = "type.missingTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TAG = "javadoc.unusedTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TAG_GENERAL = "javadoc.unusedTagGeneral";

    /** Open angle bracket literal. */
    private static final String OPEN_ANGLE_BRACKET = "<";

    /** Close angle bracket literal. */
    private static final String CLOSE_ANGLE_BRACKET = ">";

    /** Space literal. */
    private static final String SPACE = " ";

    /** Javadoc tag token literal. */
    private static final String JAVADOC_TAG_TOKEN = "@";

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;
    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;
    /** Specify the pattern for {@code @author} tag. */
    private Pattern authorFormat;
    /** Specify the pattern for {@code @version} tag. */
    private Pattern versionFormat;
    /**
     * Control whether to ignore violations when a class has type parameters but
     * does not have matching param tags in the Javadoc.
     */
    private boolean allowMissingParamTags;
    /** Control whether to ignore violations when a Javadoc tag is not recognised. */
    private boolean allowUnknownTags;

    /**
     * Specify annotations that allow skipping validation at all.
     * Only short names are allowed, e.g. {@code Generated}.
     */
    private Set<String> allowedAnnotations = Set.of("Generated");

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     * @since 3.0
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     * @since 3.4
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Setter to specify the pattern for {@code @author} tag.
     *
     * @param pattern a pattern.
     * @since 3.0
     */
    public void setAuthorFormat(Pattern pattern) {
        authorFormat = pattern;
    }

    /**
     * Setter to specify the pattern for {@code @version} tag.
     *
     * @param pattern a pattern.
     * @since 3.0
     */
    public void setVersionFormat(Pattern pattern) {
        versionFormat = pattern;
    }

    /**
     * Setter to control whether to ignore violations when a class has type parameters but
     * does not have matching param tags in the Javadoc.
     *
     * @param flag a {@code Boolean} value
     * @since 4.0
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * Setter to control whether to ignore violations when a Javadoc tag is not recognised.
     *
     * @param flag a {@code Boolean} value
     * @since 5.1
     */
    public void setAllowUnknownTags(boolean flag) {
        allowUnknownTags = flag;
    }

    /**
     * Setter to specify annotations that allow skipping validation at all.
     * Only short names are allowed, e.g. {@code Generated}.
     *
     * @param userAnnotations user's value.
     * @since 8.15
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Set.of(userAnnotations);
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailAST blockCommentAst = getBlockCommentAst();
        final DetailAST parentAst = getParentAst(blockCommentAst);

        if (isTypeDefinition(parentAst) && shouldCheck(parentAst)) {
            final List<DetailNode> blockTags = getBlockTags(ast);

            if (!allowUnknownTags) {
                checkUnknownTags(blockTags);
            }

            if (ScopeUtil.isOuterMostType(parentAst)) {
                // don't check author/version for inner classes
                checkTag(parentAst, blockTags, JavadocTagInfo.AUTHOR.getName(),
                        authorFormat);
                checkTag(parentAst, blockTags, JavadocTagInfo.VERSION.getName(),
                        versionFormat);
            }

            final List<String> typeParamNames =
                CheckUtil.getTypeParameterNames(parentAst);
            final List<String> recordComponentNames =
                getRecordComponentNames(parentAst);

            if (!allowMissingParamTags) {

                typeParamNames.forEach(typeParamName -> {
                    checkTypeParamTag(parentAst, blockTags, typeParamName);
                });

                recordComponentNames.forEach(componentName -> {
                    checkComponentParamTag(parentAst, blockTags, componentName);
                });
            }

            checkUnusedParamTags(blockTags, typeParamNames, recordComponentNames);
        }
    }

    /**
     * Checks if the given AST node is a type definition token.
     *
     * @param ast the AST node to check.
     * @return true if the node is a type definition.
     */
    private static boolean isTypeDefinition(DetailAST ast) {
        return ast.getType() == TokenTypes.INTERFACE_DEF
                || ast.getType() == TokenTypes.CLASS_DEF
                || ast.getType() == TokenTypes.ENUM_DEF
                || ast.getType() == TokenTypes.ANNOTATION_DEF
                || ast.getType() == TokenTypes.RECORD_DEF;
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(DetailAST ast) {
        return ScopeUtil.getSurroundingScope(ast)
            .map(surroundingScope -> {
                return surroundingScope.isIn(scope)
                    && (excludeScope == null || !surroundingScope.isIn(excludeScope))
                    && !AnnotationUtil.containsAnnotation(ast, allowedAnnotations);
            })
            .orElse(Boolean.FALSE);
    }

    /**
     * Returns the parent AST node (the Java element) for the block comment.
     *
     * @param blockCommentAst the block comment AST.
     * @return the parent Java element AST, or null if not found.
     */
    private static DetailAST getParentAst(DetailAST blockCommentAst) {
        final DetailAST parentNode = blockCommentAst.getParent();
        DetailAST result = parentNode;
        if (parentNode.getType() == TokenTypes.TYPE
            || parentNode.getType() == TokenTypes.MODIFIERS) {
            result = parentNode.getParent();
        }
        else if (parentNode.getParent() != null
            && parentNode.getParent().getType() == TokenTypes.MODIFIERS) {
            result = parentNode.getParent().getParent();
        }
        return result;
    }

    /**
     * Gets all block tag nodes from the javadoc AST.
     *
     * @param javadocRoot the JAVADOC_CONTENT root node.
     * @return list of JAVADOC_BLOCK_TAG nodes.
     */
    private static List<DetailNode> getBlockTags(DetailNode javadocRoot) {
        final List<DetailNode> blockTags = new ArrayList<>();
        DetailNode child = javadocRoot.getFirstChild();
        while (child != null) {
            if (child.getType() == JavadocCommentsTokenTypes.JAVADOC_BLOCK_TAG) {
                blockTags.add(child);
            }
            child = child.getNextSibling();
        }
        return blockTags;
    }

    /**
     * Checks for unknown/unrecognised tags in the javadoc.
     * A tag is considered unknown if it is a {@code CUSTOM_BLOCK_TAG} and its name
     * is not one of the standard recognised Javadoc tag names.
     *
     * @param blockTags list of JAVADOC_BLOCK_TAG nodes.
     */
    private void checkUnknownTags(Iterable<DetailNode> blockTags) {
        for (final DetailNode blockTag : blockTags) {
            final DetailNode firstChild = blockTag.getFirstChild();
            if (firstChild.getType() == JavadocCommentsTokenTypes.CUSTOM_BLOCK_TAG) {
                final String tagName = getTagName(firstChild);
                if (!JavadocTagInfo.isValidName(tagName)) {
                    final DetailNode tagNameNode =
                        JavadocUtil.findFirstToken(firstChild, JavadocCommentsTokenTypes.TAG_NAME);
                    log(tagNameNode.getLineNumber(), blockTag.getColumnNumber(),
                            MSG_UNKNOWN_TAG, tagName);
                }
            }
        }
    }

    /**
     * Gets the tag name from a block tag's specific child node
     * (e.g., PARAM_BLOCK_TAG, AUTHOR_BLOCK_TAG, CUSTOM_BLOCK_TAG).
     *
     * @param tagNode the specific block tag child node.
     * @return the tag name, or null if not found.
     */
    private static String getTagName(DetailNode tagNode) {
        final String tagName;
        if (tagNode.getType() == JavadocCommentsTokenTypes.AUTHOR_BLOCK_TAG) {
            tagName = JavadocTagInfo.AUTHOR.getName();
        }
        else if (tagNode.getType() == JavadocCommentsTokenTypes.VERSION_BLOCK_TAG) {
            tagName = JavadocTagInfo.VERSION.getName();
        }
        else {
            final DetailNode tagNameNode =
                JavadocUtil.findFirstToken(tagNode, JavadocCommentsTokenTypes.TAG_NAME);
            tagName = tagNameNode.getText();
        }
        return tagName;
    }

    /**
     * Gets the description text from a block tag node.
     *
     * @param tagNode the specific block tag child node
     *                (e.g. AUTHOR_BLOCK_TAG, VERSION_BLOCK_TAG).
     * @return the description text, or empty string if no description found.
     */
    private static String getTagDescription(DetailNode tagNode) {
        String description = "";
        final DetailNode descriptionNode = JavadocUtil.findFirstToken(
                tagNode, JavadocCommentsTokenTypes.DESCRIPTION);

        if (descriptionNode != null) {
            description = collectAllText(descriptionNode).trim();
        }

        return description;
    }

    /**
     * Recursively collects all text from a detail node tree.
     * NEWLINE nodes are converted to a single space to avoid merging words from
     * different lines, while LEADING_ASTERISK nodes are skipped.
     *
     * @param node the node to collect text from.
     * @return concatenated text of all TEXT leaf nodes.
     */
    private static String collectAllText(DetailNode node) {
        final StringBuilder builder = new StringBuilder(256);
        collectAllText(node, builder);
        return builder.toString();
    }

    /**
     * Helper method that appends all text from a detail node tree into the given builder.
     * NEWLINE nodes are converted to a single space to avoid merging words from
     * different lines, while LEADING_ASTERISK nodes are ignored.
     *
     * @param node the node to collect text from.
     * @param builder the StringBuilder to append text into.
     */
    private static void collectAllText(DetailNode node, StringBuilder builder) {
        DetailNode child = node.getFirstChild();
        while (child != null) {
            final int childType = child.getType();
            if (childType == JavadocCommentsTokenTypes.TEXT) {
                builder.append(child.getText());
            }
            else if (childType == JavadocCommentsTokenTypes.NEWLINE) {
                builder.append(' ');
            }
            else if (childType != JavadocCommentsTokenTypes.LEADING_ASTERISK) {
                collectAllText(child, builder);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Verifies that a type definition has a required tag.
     *
     * @param ast the AST node for the type definition.
     * @param blockTags block tag nodes from the Javadoc comment.
     * @param tagName the required tag name.
     * @param formatPattern regexp for the tag value.
     */
    private void checkTag(DetailAST ast, Iterable<DetailNode> blockTags, String tagName,
                          Pattern formatPattern) {
        if (formatPattern != null) {
            boolean hasTag = false;

            for (final DetailNode blockTag : blockTags) {
                final DetailNode specificTag = blockTag.getFirstChild();
                final String currentTagName = getTagName(specificTag);
                if (tagName.equals(currentTagName)) {
                    hasTag = true;
                    final String description = getTagDescription(specificTag);
                    if (!formatPattern.matcher(description).find()) {
                        log(ast, MSG_TAG_FORMAT, JAVADOC_TAG_TOKEN + tagName,
                            formatPattern.pattern());
                    }
                }
            }
            if (!hasTag) {
                log(ast, MSG_MISSING_TAG, JAVADOC_TAG_TOKEN + tagName);
            }
        }
    }

    /**
     * Verifies that a record definition has the specified param tag for
     * the specified record component name.
     *
     * @param ast the AST node for the record definition.
     * @param blockTags block tag nodes from the Javadoc comment.
     * @param recordComponentName the name of the record component
     */
    private void checkComponentParamTag(DetailAST ast,
                                        Collection<DetailNode> blockTags,
                                        String recordComponentName) {
        final boolean found = blockTags.stream()
            .filter(tag -> isParamBlockTag(tag) && !isTypeParamTag(tag))
            .anyMatch(tag -> recordComponentName.equals(getParamName(tag)));
        if (!found) {
            log(ast, MSG_MISSING_TAG, JavadocTagInfo.PARAM.getText()
                + SPACE + recordComponentName);
        }
    }

    /**
     * Verifies that a type definition has the specified param tag for
     * the specified type parameter name.
     *
     * @param ast the AST node for the type definition.
     * @param blockTags block tag nodes from the Javadoc comment.
     * @param typeParamName the name of the type parameter
     */
    private void checkTypeParamTag(DetailAST ast,
                                   Collection<DetailNode> blockTags, String typeParamName) {
        final boolean found = blockTags.stream()
            .filter(tag -> isParamBlockTag(tag) && isTypeParamTag(tag))
            .anyMatch(tag -> typeParamName.equals(getParamName(tag)));
        if (!found) {
            log(ast, MSG_MISSING_TAG, JavadocTagInfo.PARAM.getText()
                + SPACE + OPEN_ANGLE_BRACKET + typeParamName + CLOSE_ANGLE_BRACKET);
        }
    }

    /**
     * Checks for unused param tags for type parameters and record components.
     *
     * @param blockTags block tag nodes from the Javadoc comment
     * @param typeParamNames names of type parameters
     * @param recordComponentNames record component names in this definition
     */
    private void checkUnusedParamTags(
        List<DetailNode> blockTags,
        List<String> typeParamNames,
        List<String> recordComponentNames) {

        for (final DetailNode blockTag : blockTags) {
            if (isParamBlockTag(blockTag)) {
                final String paramName = getParamName(blockTag);
                final boolean isTypeParam = isTypeParamTag(blockTag);
                final boolean found;
                final String displayName;
                if (isTypeParam) {
                    found = typeParamNames.contains(paramName);
                    displayName = OPEN_ANGLE_BRACKET + paramName + CLOSE_ANGLE_BRACKET;
                }
                else {
                    found = recordComponentNames.contains(paramName);
                    displayName = paramName;
                }
                if (!found) {
                    if (displayName.isEmpty() || "<>".equals(displayName)) {
                        log(blockTag.getLineNumber(), blockTag.getColumnNumber(),
                                MSG_UNUSED_TAG_GENERAL);
                    }
                    else {
                        log(blockTag.getLineNumber(), blockTag.getColumnNumber(),
                            MSG_UNUSED_TAG, JavadocTagInfo.PARAM.getText(), displayName);
                    }
                }
            }
        }
    }

    /**
     * Checks if a JAVADOC_BLOCK_TAG node represents a {@code @param} tag.
     *
     * @param blockTag the JAVADOC_BLOCK_TAG node.
     * @return true if it is a {@code @param} tag.
     */
    private static boolean isParamBlockTag(DetailNode blockTag) {
        final DetailNode firstChild = blockTag.getFirstChild();
        return firstChild.getType() == JavadocCommentsTokenTypes.PARAM_BLOCK_TAG;
    }

    /**
     * Checks if a JAVADOC_BLOCK_TAG node represents a type parameter {@code @param} tag,
     * i.e. {@code @param <T>}. A type parameter tag is identified by its
     * {@code PARAMETER_NAME} token starting and ending with angle brackets.
     *
     * @param blockTag the JAVADOC_BLOCK_TAG node.
     * @return true if it is a type parameter {@code @param} tag.
     */
    private static boolean isTypeParamTag(DetailNode blockTag) {
        final DetailNode paramTag = blockTag.getFirstChild();

        final DetailNode paramNameNode =
            JavadocUtil.findFirstToken(paramTag, JavadocCommentsTokenTypes.PARAMETER_NAME);
        return paramNameNode != null
            && paramNameNode.getText().trim().startsWith(OPEN_ANGLE_BRACKET)
            && paramNameNode.getText().trim().endsWith(CLOSE_ANGLE_BRACKET);
    }

    /**
     * Gets the parameter name from a {@code @param} tag.
     * The name is taken from the {@code PARAMETER_NAME} node and trimmed; if it is
     * a type parameter (e.g. {@code <T>}), the surrounding angle brackets are removed
     * so that only the identifier (e.g. {@code T}) is returned.
     *
     * @param blockTag the JAVADOC_BLOCK_TAG node containing a PARAM_BLOCK_TAG.
     * @return the parameter name from the {@code @param} tag.
     */
    private static String getParamName(DetailNode blockTag) {
        String name = "";
        final DetailNode paramTag = blockTag.getFirstChild();

        final DetailNode paramNameNode =
            JavadocUtil.findFirstToken(paramTag, JavadocCommentsTokenTypes.PARAMETER_NAME);
        if (paramNameNode != null) {
            name = paramNameNode.getText().trim();
            if (name.startsWith(OPEN_ANGLE_BRACKET) && name.endsWith(CLOSE_ANGLE_BRACKET)) {
                name = name.substring(1, name.length() - 1);
            }
        }
        return name;
    }

    /**
     * Collects the record components in a record definition.
     *
     * @param node the possible record definition ast.
     * @return the record components in this record definition.
     */
    private static List<String> getRecordComponentNames(DetailAST node) {
        final DetailAST components = node.findFirstToken(TokenTypes.RECORD_COMPONENTS);
        final List<String> componentList = new ArrayList<>();

        if (components != null) {
            TokenUtil.forEachChild(components,
                TokenTypes.RECORD_COMPONENT_DEF, component -> {
                    final DetailAST ident = component.findFirstToken(TokenTypes.IDENT);
                    componentList.add(ident.getText());
                });
        }

        return componentList;
    }
}
