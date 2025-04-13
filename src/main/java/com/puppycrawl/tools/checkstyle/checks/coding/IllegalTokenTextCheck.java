///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Objects;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks specified tokens text for matching an illegal pattern.
 * By default, no tokens are specified.
 * </div>
 * <ul>
 * <li>
 * Property {@code format} - Define the RegExp for illegal pattern.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code ignoreCase} - Control whether to ignore case when matching.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code message} - Define the message which is used to notify about violations;
 * if empty then the default message is used.
 * Type is {@code java.lang.String}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is: {@code ""}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code illegal.token.text}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@StatelessCheck
public class IllegalTokenTextCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.token.text";

    /**
     * Define the message which is used to notify about violations;
     * if empty then the default message is used.
     */
    private String message = "";

    /** The format string of the regexp. */
    private String formatString = "^$";

    /** Define the RegExp for illegal pattern. */
    private Pattern format = Pattern.compile(formatString);

    /** Control whether to ignore case when matching. */
    private boolean ignoreCase;

    @Override
    public int[] getDefaultTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
            TokenTypes.IDENT,
            TokenTypes.COMMENT_CONTENT,
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String text = ast.getText();
        if (format.matcher(text).find()) {
            String customMessage = message;
            if (customMessage.isEmpty()) {
                customMessage = MSG_KEY;
            }
            log(
                ast,
                customMessage,
                formatString);
        }
    }

    /**
     * Setter to define the message which is used to notify about violations;
     * if empty then the default message is used.
     *
     * @param message custom message which should be used
     *                 to report about violations.
     * @since 3.2
     */
    public void setMessage(String message) {
        this.message = Objects.requireNonNullElse(message, "");
    }

    /**
     * Setter to define the RegExp for illegal pattern.
     *
     * @param format a {@code String} value
     * @since 3.2
     */
    public void setFormat(String format) {
        formatString = format;
        updateRegexp();
    }

    /**
     * Setter to control whether to ignore case when matching.
     *
     * @param caseInsensitive true if the match is case-insensitive.
     * @since 3.2
     */
    public void setIgnoreCase(boolean caseInsensitive) {
        ignoreCase = caseInsensitive;
        updateRegexp();
    }

    /**
     * Updates the {@link #format} based on the values from {@link #formatString} and
     * {@link #ignoreCase}.
     */
    private void updateRegexp() {
        final int compileFlags;
        if (ignoreCase) {
            compileFlags = Pattern.CASE_INSENSITIVE;
        }
        else {
            compileFlags = 0;
        }
        format = CommonUtil.createPattern(formatString, compileFlags);
    }

}
