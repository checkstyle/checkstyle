////
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that package names conform to a specified pattern.
 * </div>
 *
 * <p>
 * The default value of {@code format} for module {@code PackageName} has been chosen to match
 * the requirements in the
 * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.3">
 * Java Language specification</a>
 * and the Sun coding conventions. However, both underscores and uppercase letters are rather
 * uncommon, so most configurations should probably assign value
 * {@code ^[a-z]+(\.[a-z][a-z0-9]*)*$} to {@code format} for module {@code PackageName}.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Control the pattern to match valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z]+(\.[a-zA-Z_]\w*)*$"}.
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
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class PackageNameCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "name.invalidPattern";

    /** Control the pattern to match valid identifiers. */
    // Uppercase letters seem rather uncommon, but they're allowed in
    // https://docs.oracle.com/javase/specs/
    //  second_edition/html/packages.doc.html#40169
    private Pattern format = Pattern.compile("^[a-z]+(\\.[a-zA-Z_]\\w*)*$");

    /**
     * Setter to control the pattern to match valid identifiers.
     *
     * @param pattern the new pattern
     * @since 3.0
     */
    public void setFormat(Pattern pattern) {
        format = pattern;
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
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
        final FullIdent full = FullIdent.createFullIdent(nameAST);
        if (!format.matcher(full.getText()).find()) {
            log(full.getDetailAst(),
                MSG_KEY,
                full.getText(),
                format.pattern());
        }
    }

}
