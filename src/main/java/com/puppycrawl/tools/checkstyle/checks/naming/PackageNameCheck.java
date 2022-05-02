////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that package names conform to a specified pattern.
 * </p>
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
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z]+(\.[a-zA-Z_][a-zA-Z0-9_]*)*$"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="PackageName"/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * package com; // OK
 * package COM; // violation, name 'COM' must match pattern '^[a-z]+(\.[a-zA-Z_][a-zA-Z0-9_]*)*$'
 * package com.checkstyle.checks; // OK
 * package com.A.checkstyle.checks; // OK
 * package com.checkstyle1.checks; // OK
 * package com.checkSTYLE.checks; // OK
 * package com._checkstyle.checks_; // OK
 * </pre>
 * <p>
 * An example of how to configure the check to ensure with packages start with a lowercase letter
 * and only contains lowercase letters or numbers is:
 * </p>
 * <pre>
 * &lt;module name=&quot;PackageName&quot;&gt;
 *   &lt;property name=&quot;format&quot;
 *     value=&quot;^[a-z]+(\.[a-z][a-z0-9]*)*$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * package com; // OK
 * package COM; // violation, name 'COM' must match pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$'
 * package com.checkstyle.checks; // OK
 * package com.A.checkstyle.checks; // violation, name 'com.A.checkstyle' must match
 *                                  // pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$'
 * package com.checkstyle1.checks; // OK
 * package com.checkSTYLE.checks; // violation, name 'com.checkSTYLE.checks' must
 *                                // match pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$'
 * package com._checkstyle.checks_; // violation, name 'com._checkstyle.checks_' must match
 *                                  // pattern '^[a-z]+(\.[a-z][a-z0-9]*)*$'
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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

    /** Specifies valid identifiers. */
    // Uppercase letters seem rather uncommon, but they're allowed in
    // https://docs.oracle.com/javase/specs/
    //  second_edition/html/packages.doc.html#40169
    private Pattern format = Pattern.compile("^[a-z]+(\\.[a-zA-Z_][a-zA-Z0-9_]*)*$");

    /**
     * Setter to specifies valid identifiers.
     *
     * @param pattern the new pattern
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
