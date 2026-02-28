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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that all the setter methods in a non-abstract class whose
 * class name ends with Check/FilterJavadoc contain since block tags.
 * </div>
 *
 * <p>
 * Only performs checks on classes whose class name ends with Check/Filter.
 * </p>
 *
 * <p>
 * Checks if setter methods have since block tag.
 * </p>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class SetterSinceTagCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MISSING_SINCE_TAG = "setter.missing.sinceTag";

    /** Suffix that qualifies a class as a Checkstyle Check. */
    private static final String CHECK_SUFFIX = "Check";

    /** Suffix that qualifies a class as a Checkstyle Filter. */
    private static final String FILTER_SUFFIX = "Filter";

    /** The prefix that identifies a setter method by name. */
    private static final String SETTER_PREFIX = "set";

    /** The {@code @since} Javadoc tag name. */
    private static final String SINCE_TAG = "@since";

    /** The {@code @propertySince} Javadoc tag name (without {@code @}). */
    private static final String PROPERTY_SINCE_TAG = "@propertySince";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(final DetailAST ast) {
        if (isInsideQualifyingClass(ast) && isSetter(ast)) {
            checkForSinceTag(ast);
        }
    }

    /**
     * Determines whether the given METHOD_DEF node resides inside a class
     * that is non-abstract and whose name ends with {@code Check} or {@code Filter}.
     *
     * @param methodDef the METHOD_DEF AST node
     * @return {@code true} if the enclosing class qualifies
     */
    private static boolean isInsideQualifyingClass(final DetailAST methodDef) {

        boolean result = true;
        final DetailAST objBlock = methodDef.getParent();
        final DetailAST classDef = objBlock.getParent();

        if (classDef.getType() != TokenTypes.CLASS_DEF) {
            result = false;
        }
        else {
            final DetailAST modifiers = classDef.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.findFirstToken(TokenTypes.ABSTRACT) != null) {
                result = false;
            }
            else {
                // The class name must end with "Check" or "Filter"
                final String className = classDef
                        .findFirstToken(TokenTypes.IDENT)
                        .getText();
                result = className.endsWith(CHECK_SUFFIX)
                        || className.endsWith(FILTER_SUFFIX);
            }
        }

        return result;
    }

    /**
     * Determines whether the given METHOD_DEF node represents a setter method.
     * A setter must:
     * <ul>
     *   <li>be {@code public}</li>
     *   <li>have a name starting with {@code set}</li>
     *   <li>return {@code void}</li>
     *   <li>have exactly one parameter</li>
     * </ul>
     *
     * @param methodDef the METHOD_DEF AST node
     * @return {@code true} if the method is a setter
     */
    private static boolean isSetter(final DetailAST methodDef) {

        boolean result = true;
        boolean hasOverride = false;

        // Must be public
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);

        // Skip @Override methods - iterate all annotations
        DetailAST annotation = modifiers.findFirstToken(TokenTypes.ANNOTATION);
        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            final DetailAST ident = annotation.findFirstToken(TokenTypes.IDENT);
            if (ident != null && "Override".equals(ident.getText())) {
                hasOverride = true;
            }
            annotation = annotation.getNextSibling();
        }
        if (hasOverride) {
            result = false;
        }
        else if (modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) == null) {
            result = false;
        }
        else {
            // Name must start with "set"
            final String methodName = methodDef
                    .findFirstToken(TokenTypes.IDENT)
                    .getText();
            if (!methodName.startsWith(SETTER_PREFIX)) {
                result = false;
            }
            else {
                // Return type must be void
                final DetailAST returnType = methodDef.findFirstToken(TokenTypes.TYPE);
                if (returnType.findFirstToken(TokenTypes.LITERAL_VOID) == null) {
                    result = false;
                }
                else {
                    // Must have exactly one parameter
                    final DetailAST parameters = methodDef.findFirstToken(TokenTypes.PARAMETERS);
                    result = parameters.getChildCount(TokenTypes.PARAMETER_DEF) == 1;
                }
            }
        }
        return result;
    }

    /**
     * Checks that the Javadoc comment preceding the given method contains a
     * {@code @since} tag. Logs a violation if it is absent or if there is no
     * Javadoc comment at all.
     *
     * @param methodDef the METHOD_DEF AST node to check
     */
    private void checkForSinceTag(final DetailAST methodDef) {
        final DetailAST javadocComment = getPrecedingJavadocComment(methodDef);

        if (!hasSinceTag(javadocComment)) {
            log(methodDef, MSG_MISSING_SINCE_TAG,
                    methodDef.findFirstToken(TokenTypes.IDENT).getText());
        }
    }

    /**
     * Finds the nearest preceding {@code BLOCK_COMMENT_BEGIN} sibling of the
     * given node that looks like a Javadoc comment (starts with {@code /**}).
     *
     * @param methodDef the METHOD_DEF AST node
     * @return the {@code BLOCK_COMMENT_BEGIN} node, or {@code null} if not found
     */
    private static DetailAST getPrecedingJavadocComment(final DetailAST methodDef) {
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        final DetailAST blockComment = modifiers.findFirstToken(TokenTypes.BLOCK_COMMENT_BEGIN);
        DetailAST result = null;
        if (blockComment == null) {
            result = null;
        }
        else if (isJavadocComment(blockComment)) {
            result = blockComment;
        }
        return result;
    }

    /**
     * Returns {@code true} if the {@code BLOCK_COMMENT_BEGIN} node represents
     * a Javadoc comment, i.e. its first child text starts with {@code *}.
     * (Javadoc comments begin with {@code /**}, so after {@code /*} the next
     * token is a COMMENT_CONTENT starting with {@code *}.)
     *
     * @param blockComment the BLOCK_COMMENT_BEGIN AST node
     * @return {@code true} if this is a Javadoc comment
     */
    private static boolean isJavadocComment(final DetailAST blockComment) {
        final DetailAST content = blockComment.getFirstChild();
        return content.getText().startsWith("*");
    }

    /**
     * Returns {@code true} if the supplied {@code BLOCK_COMMENT_BEGIN} node
     * contains at least one {@code @since} tag anywhere in its text.
     *
     * @param blockComment the BLOCK_COMMENT_BEGIN AST node
     * @return {@code true} if a {@code @since} tag is present
     */
    private static boolean hasSinceTag(final DetailAST blockComment) {
        boolean result = false;
        if (blockComment == null) {
            result = false;
        }
        else {
            DetailAST child = blockComment.getFirstChild();
            while (child != null) {
                if (child.getText().contains(SINCE_TAG)
                        || child.getText().contains(PROPERTY_SINCE_TAG)) {
                    result = true;
                    break;
                }
                child = child.getNextSibling();
            }
        }
        return result;
    }
}
