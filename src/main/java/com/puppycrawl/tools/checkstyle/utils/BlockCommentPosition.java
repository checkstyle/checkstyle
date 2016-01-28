////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Utility class that has methods to check javadoc comment position in java file.
 * @author bizmailov
 *
 */
public final class BlockCommentPosition {

    /**
     * Forbid new instances.
     */
    private BlockCommentPosition() {
    }

    /**
     * Node is on class definition.
     * @param blockComment DetailAST
     * @return true if node is before class
     */
    public static boolean isOnClass(DetailAST blockComment) {
        return isOnPlainToken(blockComment, TokenTypes.CLASS_DEF, TokenTypes.LITERAL_CLASS)
                || isOnTokenWithModifiers(blockComment, TokenTypes.CLASS_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.CLASS_DEF);
    }

    /**
     * Node is on interface definition.
     * @param blockComment DetailAST
     * @return true if node is before interface
     */
    public static boolean isOnInterface(DetailAST blockComment) {
        return isOnPlainToken(blockComment, TokenTypes.INTERFACE_DEF, TokenTypes.LITERAL_INTERFACE)
                || isOnTokenWithModifiers(blockComment, TokenTypes.INTERFACE_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.INTERFACE_DEF);
    }

    /**
     * Node is on enum definition.
     * @param blockComment DetailAST
     * @return true if node is before enum
     */
    public static boolean isOnEnum(DetailAST blockComment) {
        return isOnPlainToken(blockComment, TokenTypes.ENUM_DEF, TokenTypes.ENUM)
                || isOnTokenWithModifiers(blockComment, TokenTypes.ENUM_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.ENUM_DEF);
    }

    /**
     * Node is on annotation definition.
     * @param blockComment DetailAST
     * @return true if node is before annotation
     */
    public static boolean isOnAnnotationDef(DetailAST blockComment) {
        return isOnPlainToken(blockComment, TokenTypes.ANNOTATION_DEF, TokenTypes.AT)
                || isOnTokenWithModifiers(blockComment, TokenTypes.ANNOTATION_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.ANNOTATION_DEF);
    }

    /**
     * Node is on method declaration.
     * @param blockComment DetailAST
     * @return true if node is before method
     */
    public static boolean isOnMethod(DetailAST blockComment) {
        return isOnPlainClassMember(blockComment, TokenTypes.METHOD_DEF)
                || isOnTokenWithModifiers(blockComment, TokenTypes.METHOD_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.METHOD_DEF);
    }

    /**
     * Node is on field declaration.
     * @param blockComment DetailAST
     * @return true if node is before field
     */
    public static boolean isOnField(DetailAST blockComment) {
        return isOnPlainClassMember(blockComment, TokenTypes.VARIABLE_DEF)
                || isOnTokenWithModifiers(blockComment, TokenTypes.VARIABLE_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.VARIABLE_DEF);
    }

    /**
     * Node is on constructor.
     * @param blockComment DetailAST
     * @return true if node is before constructor
     */
    public static boolean isOnConstructor(DetailAST blockComment) {
        return isOnPlainToken(blockComment, TokenTypes.CTOR_DEF, TokenTypes.IDENT)
                || isOnTokenWithModifiers(blockComment, TokenTypes.CTOR_DEF)
                || isOnTokenWithAnnotation(blockComment, TokenTypes.CTOR_DEF);
    }

    /**
     * Node is on enum constant.
     * @param blockComment DetailAST
     * @return true if node is before enum constant
     */
    public static boolean isOnEnumConstant(DetailAST blockComment) {
        final boolean isOnPlainConst = blockComment.getParent() != null
                && blockComment.getParent().getType() == TokenTypes.ENUM_CONSTANT_DEF
                && getPrevSiblingSkipComments(blockComment).getType() == TokenTypes.ANNOTATIONS
                && getPrevSiblingSkipComments(blockComment).getChildCount() == 0;
        final boolean isOnConstWithAnnotation = !isOnPlainConst && blockComment.getParent() != null
                && blockComment.getParent().getType() == TokenTypes.ANNOTATION
                && blockComment.getParent().getParent().getParent().getType()
                    == TokenTypes.ENUM_CONSTANT_DEF;
        return isOnPlainConst || isOnConstWithAnnotation;
    }

    /**
     * Checks that block comment is on specified token without any modifiers.
     * @param blockComment block comment start DetailAST
     * @param parentTokenType parent token type
     * @param nextTokenType next token type
     * @return true if block comment is on specified token without modifiers
     */
    private static boolean isOnPlainToken(DetailAST blockComment,
            int parentTokenType, int nextTokenType) {
        return blockComment.getParent() != null
                && blockComment.getParent().getType() == parentTokenType
                && getPrevSiblingSkipComments(blockComment).getChildCount() == 0
                && getNextSiblingSkipComments(blockComment).getType() == nextTokenType;
    }

    /**
     * Checks that block comment is on specified token with modifiers.
     * @param blockComment block comment start DetailAST
     * @param tokenType parent token type
     * @return true if block comment is on specified token with modifiers
     */
    private static boolean isOnTokenWithModifiers(DetailAST blockComment, int tokenType) {
        return blockComment.getParent() != null
                && blockComment.getParent().getType() == TokenTypes.MODIFIERS
                && blockComment.getParent().getParent().getType() == tokenType
                && getPrevSiblingSkipComments(blockComment) == null;
    }

    /**
     * Checks that block comment is on specified token with annotation.
     * @param blockComment block comment start DetailAST
     * @param tokenType parent token type
     * @return true if block comment is on specified token with annotation
     */
    private static boolean isOnTokenWithAnnotation(DetailAST blockComment, int tokenType) {
        return blockComment.getParent() != null
                && blockComment.getParent().getType() == TokenTypes.ANNOTATION
                && getPrevSiblingSkipComments(blockComment.getParent()) == null
                && blockComment.getParent().getParent().getType() == TokenTypes.MODIFIERS
                && blockComment.getParent().getParent().getParent().getType() == tokenType
                && getPrevSiblingSkipComments(blockComment) == null;
    }

    /**
     * Checks that block comment is on specified class member without any modifiers.
     * @param blockComment block comment start DetailAST
     * @param memberType parent token type
     * @return true if block comment is on specified token without modifiers
     */
    private static boolean isOnPlainClassMember(DetailAST blockComment, int memberType) {
        return blockComment.getParent() != null
                && blockComment.getParent().getType() == TokenTypes.TYPE
                && blockComment.getParent().getParent().getType() == memberType
                // previous parent sibling is always TokenTypes.MODIFIERS
                && blockComment.getParent().getPreviousSibling().getChildCount() == 0;
    }

    /**
     * Get next sibling node skipping any comment nodes.
     * @param node current node
     * @return next sibling
     */
    private static DetailAST getNextSiblingSkipComments(DetailAST node) {
        DetailAST result = node.getNextSibling();
        while (result.getType() == TokenTypes.SINGLE_LINE_COMMENT
                || result.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            result = result.getNextSibling();
        }
        return result;
    }

    /**
     * Get previous sibling node skipping any comments.
     * @param node current node
     * @return previous sibling
     */
    private static DetailAST getPrevSiblingSkipComments(DetailAST node) {
        DetailAST result = node.getPreviousSibling();
        while (result != null
                && (result.getType() == TokenTypes.SINGLE_LINE_COMMENT
                || result.getType() == TokenTypes.BLOCK_COMMENT_BEGIN)) {
            result = result.getPreviousSibling();
        }
        return result;
    }
}
