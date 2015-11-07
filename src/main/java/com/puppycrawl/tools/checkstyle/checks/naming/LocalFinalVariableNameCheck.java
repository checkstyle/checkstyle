////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>
 * Checks that local final variable names conform to a format specified
 * by the format property. A catch parameter is considered to be
 * a local variable.The format is a
 * {@link Pattern regular expression} and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="LocalFinalVariableName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that are only upper case
 * letters and digits is:
 * </p>
 * <pre>
 * &lt;module name="LocalFinalVariableName"&gt;
 *    &lt;property name="format" value="^[A-Z][A-Z0-9]*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class LocalFinalVariableNameCheck
    extends Check {
    /**
     * Message key for invalid pattern error.
     */
    public static final String MSG_INVALID_PATTERN = "name.invalidPattern";

    /** The format string of the regexp. */
    private String format = "^[a-z][a-zA-Z0-9]*$";

    /** The regexp to match against. */
    private Pattern regexp = Pattern.compile(format);

    /**
     * Set the format to the specified regular expression.
     * @param format a {@code String} value
     * @throws org.apache.commons.beanutils.ConversionException unable to parse format
     */
    public final void setFormat(String format) {
        this.format = format;
        regexp = CommonUtils.createPattern(format);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (mustCheckName(ast)) {
            final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            if (!regexp.matcher(nameAST.getText()).find()) {
                log(nameAST.getLineNo(),
                    nameAST.getColumnNo(),
                    MSG_INVALID_PATTERN,
                    nameAST.getText(),
                    format);
            }
        }
    }

    /**
     * Decides whether the name of an AST should be checked against
     * the format regexp.
     * @param ast the AST to check.
     * @return true if the IDENT subnode of ast should be checked against
     *     the format regexp.
     */
    private boolean mustCheckName(DetailAST ast) {
        final DetailAST modifiersAST =
            ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isFinal = modifiersAST.branchContains(TokenTypes.FINAL);
        return isFinal && ScopeUtils.isLocalVariableDef(ast);
    }
}
