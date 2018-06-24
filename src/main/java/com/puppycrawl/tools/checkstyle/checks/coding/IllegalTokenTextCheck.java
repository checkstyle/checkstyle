////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks for illegal token text.
 * </p>
 * <p> An example of how to configure the check to forbid String literals
 * containing {@code "a href"} is:
 * </p>
 * <pre>
 * &lt;module name="IllegalTokenText"&gt;
 *     &lt;property name="tokens" value="STRING_LITERAL"/&gt;
 *     &lt;property name="format" value="a href"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check to forbid leading zeros in an
 * integer literal, other than zero and a hex literal is:
 * </p>
 * <pre>
 * &lt;module name="IllegalTokenText"&gt;
 *     &lt;property name="tokens" value="NUM_INT,NUM_LONG"/&gt;
 *     &lt;property name="format" value="^0[^lx]"/&gt;
 *     &lt;property name="ignoreCase" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
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
     * Custom message for report if illegal regexp found
     * ignored if empty.
     */
    private String message = "";

    /** The format string of the regexp. */
    private String formatString = "^$";

    /** The regexp to match against. */
    private Pattern format = Pattern.compile(formatString);

    /** {@code true} if the casing should be ignored. */
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
     * Setter for message property.
     * @param message custom message which should be used
     *                 to report about violations.
     */
    public void setMessage(String message) {
        if (message == null) {
            this.message = "";
        }
        else {
            this.message = message;
        }
    }

    /**
     * Set the format to the specified regular expression.
     * @param format a {@code String} value
     * @throws org.apache.commons.beanutils.ConversionException unable to parse format
     */
    public void setFormat(String format) {
        formatString = format;
        updateRegexp();
    }

    /**
     * Set whether or not the match is case sensitive.
     * @param caseInsensitive true if the match is case insensitive.
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
