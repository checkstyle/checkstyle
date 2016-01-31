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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks that package names conform to a format specified
 * by the format property. The format is a
 * {@link Pattern regular expression}
 * and defaults to
 * <strong>^[a-z]+(\.[a-zA-Z_][a-zA-Z_0-9_]*)*$</strong>.
 * </p>
 * <p>
 * The default format has been chosen to match the requirements in the
 * <a
 * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html">
 * Java Language specification</a> and the Sun coding conventions.
 * However both underscores and uppercase letters are rather uncommon,
 * so most projects should probably use
 * <strong>^[a-z]+(\.[a-z][a-z0-9]*)*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="PackageName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for package names that begin with
 * {@code com.puppycrawl.tools.checkstyle} is:
 * </p>
 * <pre>
 * &lt;module name="PackageName"&gt;
 *    &lt;property name="format"
 *              value="^com\.puppycrawl\.tools\.checkstyle(\.[a-zA-Z_][a-zA-Z_0-9]*)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Oliver Burn
 */
public class PackageNameCheck
    extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "name.invalidPattern";

    /** The format string of the regexp. */
    // Uppercase letters seem rather uncommon, but they're allowed in
    // http://docs.oracle.com/javase/specs/
    //  second_edition/html/packages.doc.html#40169
    private String format = "^[a-z]+(\\.[a-zA-Z_][a-zA-Z0-9_]*)*$";
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
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
        final FullIdent full = FullIdent.createFullIdent(nameAST);
        if (!regexp.matcher(full.getText()).find()) {
            log(full.getLineNo(),
                full.getColumnNo(),
                MSG_KEY,
                full.getText(),
                format);
        }
    }
}
