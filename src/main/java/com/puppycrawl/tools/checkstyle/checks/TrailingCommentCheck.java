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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * The check to ensure that requires that comments be the only thing on a line.
 * For the case of {@code //} comments that means that the only thing that should precede
 * it is whitespace. It doesn't check comments if they do not end a line; for example,
 * it accepts the following: <code>Thread.sleep( 10 /*some comment here&#42;/ );</code>
 * Format property is intended to deal with the <code>} // while</code> example.
 * </p>
 * <p>
 * Rationale: Steve McConnell in <cite>Code Complete</cite> suggests that endline
 * comments are a bad practice. An end line comment would be one that is on
 * the same line as actual code. For example:
 * </p>
 * <pre>
 * a = b + c;      // Some insightful comment
 * d = e / f;        // Another comment for this line
 * </pre>
 * <p>
 * Quoting <cite>Code Complete</cite> for the justification:
 * </p>
 * <ul>
 * <li>
 * "The comments have to be aligned so that they do not interfere with the visual
 * structure of the code. If you don't align them neatly, they'll make your listing
 * look like it's been through a washing machine."
 * </li>
 * <li>
 * "Endline comments tend to be hard to format...It takes time to align them.
 * Such time is not spent learning more about the code; it's dedicated solely
 * to the tedious task of pressing the spacebar or tab key."
 * </li>
 * <li>
 * "Endline comments are also hard to maintain. If the code on any line containing
 * an endline comment grows, it bumps the comment farther out, and all the other
 * endline comments will have to bumped out to match. Styles that are hard to
 * maintain aren't maintained...."
 * </li>
 * <li>
 * "Endline comments also tend to be cryptic. The right side of the line doesn't
 * offer much room and the desire to keep the comment on one line means the comment
 * must be short. Work then goes into making the line as short as possible instead
 * of as clear as possible. The comment usually ends up as cryptic as possible...."
 * </li>
 * <li>
 * "A systemic problem with endline comments is that it's hard to write a meaningful
 * comment for one line of code. Most endline comments just repeat the line of code,
 * which hurts more than it helps."
 * </li>
 * </ul>
 * <p>
 * McConnell's comments on being hard to maintain when the size of the line changes
 * are even more important in the age of automated refactorings.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specify pattern for strings allowed before the comment.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is <code>"^[\s});]*$"</code>.
 * </li>
 * <li>
 * Property {@code legalComment} - Define pattern for text allowed in trailing comments.
 * (This pattern will not be applied to multiline comments and the text of
 * the comment will be trimmed before matching.)
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;TrailingComment&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check so it enforces only comment on a line:
 * </p>
 * <pre>
 * &lt;module name=&quot;TrailingComment&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^\\s*$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code trailing.comments}
 * </li>
 * </ul>
 *
 * @noinspection HtmlTagCanBeJavadocTag
 * @since 3.4
 */
@StatelessCheck
public class TrailingCommentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "trailing.comments";

    /**
     * Define pattern for text allowed in trailing comments.
     * (This pattern will not be applied to multiline comments and the text
     * of the comment will be trimmed before matching.)
     */
    private Pattern legalComment;

    /** Specify pattern for strings allowed before the comment. */
    private Pattern format = Pattern.compile("^[\\s});]*$");

    /**
     * Setter to define pattern for text allowed in trailing comments.
     * (This pattern will not be applied to multiline comments and the text
     * of the comment will be trimmed before matching.)
     *
     * @param legalComment pattern to set.
     */
    public void setLegalComment(final Pattern legalComment) {
        this.legalComment = legalComment;
    }

    /**
     * Setter to specify pattern for strings allowed before the comment.
     *
     * @param pattern a pattern
     */
    public final void setFormat(Pattern pattern) {
        format = pattern;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String commentContent = ast.getFirstChild().getText();
        final boolean isCommentLegal;

        if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            isCommentLegal = isCommentMatchLegalPattern(commentContent);
        }
        else {
            // We are looking at C comment (block comment)
            final DetailAST commentEnd = ast.getFirstChild().getNextSibling();
            final String afterComment = getTextAfterBlockComment(commentEnd);
            final boolean isCommentEndOfLine = CommonUtil.isBlank(afterComment);

            if (isCommentEndOfLine) {
                final boolean isMultiLine = commentEnd.getLineNo() > ast.getLineNo();
                // Multiline comment cannot be legal
                isCommentLegal = !isMultiLine && isCommentMatchLegalPattern(commentContent);
            }
            else {
                isCommentLegal = true;
            }
        }

        if (!isCommentLegal && !isBeforeCommentLegal(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Get the text after the end of a block comment.
     *
     * @param commentEndAst the comment end ast
     * @return the text after the end of a block comment
     */
    private String getTextAfterBlockComment(DetailAST commentEndAst) {
        final String commentEndLine = getLine(commentEndAst.getLineNo() - 1);
        final int commentEndColumnNo = commentEndAst.getColumnNo();
        final String afterComment;
        if (commentEndLine.charAt(commentEndColumnNo) == '*') {
            // commentEndColumnNo is pointing at the asterisk of */
            afterComment = commentEndLine.substring(commentEndColumnNo + 2);
        }
        else {
            // commentEndColumnNo is pointing at the space right before */
            afterComment = commentEndLine.substring(commentEndColumnNo + 1 + 2);
        }
        return afterComment;
    }

    /**
     * Check if the text before the comment matches format pattern.
     *
     * @param commentBeginAst comment begin AST
     * @return true if the text before the comment matches format pattern
     */
    private boolean isBeforeCommentLegal(DetailAST commentBeginAst) {
        final String commentBeginLine = getLine(commentBeginAst.getLineNo() - 1);
        final String beforeComment = commentBeginLine.substring(0, commentBeginAst.getColumnNo());
        return format.matcher(beforeComment).find();
    }

    /**
     * Check if commentContent matches legalComment pattern.
     *
     * @param commentContent the content of a comment
     * @return true if commentContent matches legalComment pattern
     */
    private boolean isCommentMatchLegalPattern(String commentContent) {
        return legalComment != null && legalComment.matcher(commentContent.trim()).find();
    }
}
