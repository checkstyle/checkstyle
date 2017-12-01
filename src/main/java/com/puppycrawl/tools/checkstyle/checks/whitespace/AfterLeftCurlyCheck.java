////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Checks for a blank line at the beginning of a code block.
 *
 * <p> By default this check validates presence of a blank line after the left curly brace.
 * To configure this check to validate absence of any blank lines at the top of the code block,
 * set "blankLine" parameter to {@code false}.
 * </p>
 *
 * <p> No validation is performed for empty code blocks, since this check may conflict with
 * {@link AfterLeftCurlyCheck} if they are configured in the opposite way.
 * </p>
 *
 * <p> The check is compatible with any statement that have a body:
 * {@link TokenTypes#ANNOTATION_ARRAY_INIT ANNOTATION_ARRAY_INIT},
 * {@link TokenTypes#ANNOTATION_DEF ANNOTATION_DEF},
 * {@link TokenTypes#ARRAY_INIT ARRAY_INIT},
 * {@link TokenTypes#CASE_GROUP CASE_GROUP},
 * {@link TokenTypes#CLASS_DEF CLASS_DEF},
 * {@link TokenTypes#CTOR_DEF CTOR_DEF},
 * {@link TokenTypes#ENUM_CONSTANT_DEF ENUM_CONSTANT_DEF},
 * {@link TokenTypes#ENUM_DEF ENUM_DEF},
 * {@link TokenTypes#INSTANCE_INIT INSTANCE_INIT},
 * {@link TokenTypes#INTERFACE_DEF INTERFACE_DEF},
 * {@link TokenTypes#LAMBDA LAMBDA},
 * {@link TokenTypes#LITERAL_CASE LITERAL_CASE},
 * {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 * {@link TokenTypes#LITERAL_DEFAULT LITERAL_DEFAULT},
 * {@link TokenTypes#LITERAL_DO LITERAL_DO},
 * {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 * {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 * {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 * {@link TokenTypes#LITERAL_IF LITERAL_IF},
 * {@link TokenTypes#LITERAL_NEW LITERAL_NEW},
 * {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH},
 * {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED},
 * {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 * {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE},
 * {@link TokenTypes#METHOD_DEF METHOD_DEF},
 * {@link TokenTypes#SLIST SLIST},
 * {@link TokenTypes#STATIC_INIT STATIC_INIT}.
 * </p>
 *
 * <p> By default the check will validate the following statements:
 * {@link TokenTypes#ANNOTATION_DEF ANNOTATION_DEF},
 * {@link TokenTypes#CLASS_DEF CLASS_DEF},
 * {@link TokenTypes#ENUM_CONSTANT_DEF ENUM_CONSTANT_DEF},
 * {@link TokenTypes#ENUM_DEF ENUM_DEF},
 * {@link TokenTypes#INTERFACE_DEF INTERFACE_DEF}.
 * </p>
 *
 * <p> An example of how to configure the check with default parameters is:
 * </p>
 *
 * <pre>
 * &lt;module name="AfterLeftCurly"/&gt;
 * </pre>
 *
 * <p> Example of declarations with a blank line at the top of the block that is expected
 * by the check by default:
 * </p>
 *
 * <pre>
 * interface Foo {
 *
 *     public int method1();
 *
 *     public int method2();
 *
 * }
 * </pre>
 *
 * <p> An example how to check a {@link TokenTypes#CLASS_DEF CLASS_DEF}
 * and an {@link TokenTypes#INTERFACE_DEF INTERFACE_DEF} only:
 * </p>
 *
 * <pre>
 * &lt;module name="AfterLeftCurly"&gt;
 *    &lt;property name="tokens" value="CLASS_DEF, INTERFACE_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * An example how to allow no blank lines after the left curly brace:
 * </p>
 *
 * <pre>
 * &lt;module name="AfterLeftCurly"&gt;
 *    &lt;property name="blankLine" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <pre>
 * class Foo {
 *     public int method() {
 *         return 0;
 *     }
 * }
 * </pre>
 *
 * @author Pavel Bludov
 * @see BeforeRightCurlyCheck
 */
@StatelessCheck
public class AfterLeftCurlyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message blank.line.after.lcurly
     * in "messages.properties" file.
     */
    public static final String MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY = "blank.line.after.lcurly";

    /**
     * A key is pointing to the warning message no.blank.line.after.lcurly
     * in "messages.properties" file.
     */
    public static final String MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY =
        "no.blank.line.after.lcurly";

    /** The policy to enforce presence or absence of a blank line at the top of the block. */
    private boolean blankLine = true;

    /**
     * Sets the policy to enforce presence or absence of a blank line the top of the block.
     * @param value User's value of blankLine.
     */
    public void setBlankLine(boolean value) {
        blankLine = value;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.INTERFACE_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION_ARRAY_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ARRAY_INIT,
            TokenTypes.CASE_GROUP,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.STATIC_INIT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST leftCurly = findLeftCurly(ast);
        if (leftCurly != null) {
            final DetailAST firstChild = leftCurly.getFirstChild();
            final DetailAST nextSibling = leftCurly.getNextSibling();
            final boolean isEmpty = firstChild != null && firstChild.getType() == TokenTypes.RCURLY
                || nextSibling != null && nextSibling.getType() == TokenTypes.RCURLY;

            if (!isEmpty && blankLine != hasBlankLineAfter(leftCurly)) {
                if (blankLine) {
                    log(leftCurly, MSG_SHOULD_BE_BLANK_LINE_AFTER_LCURLY, leftCurly.getText());
                }
                else {
                    log(leftCurly, MSG_SHOULD_BE_NO_BLANK_LINE_AFTER_LCURLY,
                        leftCurly.getText());
                }
            }
        }
    }

    /**
     * Verifies that there is a blank line after the token.
     * @param ast the token to process
     * @return {@code true} if found no other tokens after the {@code ast} on the same line
     *     and a blank line follows it, {@code false} otherwise
     */
    private boolean hasBlankLineAfter(DetailAST ast) {
        final FileContents contents = getFileContents();
        final String line = contents.getLine(ast.getLineNo() - 1);
        final boolean blankAfter = CommonUtils.isBlank(line.substring(ast.getColumnNo() + 1));
        return blankAfter && contents.lineIsBlank(ast.getLineNo());
    }

    /**
     * Finds the left curly brace of a code block associated with the specified token.
     * @param ast the token to process
     * @return the token for the left curly brace if found, {@code null} otherwise
     */
    private static DetailAST findLeftCurly(DetailAST ast) {
        DetailAST leftBrace = null;
        switch (ast.getType()) {
            case TokenTypes.ANNOTATION_ARRAY_INIT:
            case TokenTypes.ARRAY_INIT:
                leftBrace = ast;
                break;
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.CLASS_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.ENUM_CONSTANT_DEF:
            case TokenTypes.LITERAL_NEW:
                final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
                if (objBlock != null) {
                    leftBrace = objBlock.getFirstChild();
                }
                break;
            case TokenTypes.LITERAL_ELSE:
                // Special handling for the "else if" case.
                final DetailAST candidate = ast.getFirstChild();
                if (candidate.getType() == TokenTypes.SLIST) {
                    leftBrace = candidate;
                }
                break;
            case TokenTypes.SLIST:
                // The scope block is a SLIST with another SLIST as the parent.
                if (ast.getParent().getType() == TokenTypes.SLIST) {
                    leftBrace = ast;
                }
                break;
            case TokenTypes.LITERAL_SWITCH:
                // The Switch does not have SLIST unlike the others.
                leftBrace = ast.findFirstToken(TokenTypes.LCURLY);
                break;
            case TokenTypes.CASE_GROUP:
                // The CASE_GROUP is the parent of 'case', 'default' and SLIST parent.
                leftBrace = ast.getLastChild().findFirstToken(TokenTypes.SLIST);
                break;
            case TokenTypes.LITERAL_CASE:
            case TokenTypes.LITERAL_DEFAULT:
                // The 'case' and 'default' are siblings for SLIST parent.
                leftBrace = ast.getParent().getLastChild().findFirstToken(TokenTypes.SLIST);
                break;
            default:
                // All the rest: for/while/if/STATIC_INIT,lambda,etc.
                leftBrace = ast.findFirstToken(TokenTypes.SLIST);
                break;
        }

        return leftBrace;
    }

}
