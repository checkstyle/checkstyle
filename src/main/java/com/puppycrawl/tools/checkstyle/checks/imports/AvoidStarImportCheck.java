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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that there are no import statements that use the {@code *} notation.
 * </div>
 *
 * <p>
 * Rationale: Importing all classes from a package or static
 * members from a class leads to tight coupling between packages
 * or classes and might lead to problems when a new version of a
 * library introduces name clashes.
 * </p>
 *
 * <p>
 * Note that property {@code excludes} is not recursive, subpackages of excluded
 * packages are not automatically excluded.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowClassImports} - Control whether to allow starred class
 * imports like {@code import java.util.*;}.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowStaticMemberImports} - Control whether to allow starred
 * static member imports like {@code import static org.junit.Assert.*;}.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code excludes} - Specify packages where starred class imports are
 * allowed and classes where starred static member imports are allowed.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
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
 * {@code import.avoidStar}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class AvoidStarImportCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.avoidStar";

    /** Suffix for the star import. */
    private static final String STAR_IMPORT_SUFFIX = ".*";

    /**
     * Specify packages where starred class imports are
     * allowed and classes where starred static member imports are allowed.
     */
    private final Set<String> excludes = new HashSet<>();

    /**
     * Control whether to allow starred class imports like
     * {@code import java.util.*;}.
     */
    private boolean allowClassImports;

    /**
     * Control whether to allow starred static member imports like
     * {@code import static org.junit.Assert.*;}.
     */
    private boolean allowStaticMemberImports;

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
        // original implementation checks both IMPORT and STATIC_IMPORT tokens to avoid ".*" imports
        // however user can allow using "import" or "import static"
        // by configuring allowClassImports and allowStaticMemberImports
        // To avoid potential confusion when user specifies conflicting options on configuration
        // (see example below) we are adding both tokens to Required list
        //   <module name="AvoidStarImport">
        //      <property name="tokens" value="IMPORT"/>
        //      <property name="allowStaticMemberImports" value="false"/>
        //   </module>
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    /**
     * Setter to specify packages where starred class imports are
     * allowed and classes where starred static member imports are allowed.
     *
     * @param excludesParam package names/fully-qualifies class names
     *     where star imports are ok
     * @since 3.2
     */
    public void setExcludes(String... excludesParam) {
        for (final String exclude : excludesParam) {
            if (exclude.endsWith(STAR_IMPORT_SUFFIX)) {
                excludes.add(exclude);
            }
            else {
                excludes.add(exclude + STAR_IMPORT_SUFFIX);
            }
        }
    }

    /**
     * Setter to control whether to allow starred class imports like
     * {@code import java.util.*;}.
     *
     * @param allow true to allow false to disallow
     * @since 5.3
     */
    public void setAllowClassImports(boolean allow) {
        allowClassImports = allow;
    }

    /**
     * Setter to control whether to allow starred static member imports like
     * {@code import static org.junit.Assert.*;}.
     *
     * @param allow true to allow false to disallow
     * @since 5.3
     */
    public void setAllowStaticMemberImports(boolean allow) {
        allowStaticMemberImports = allow;
    }

    @Override
    public void visitToken(final DetailAST ast) {
        if (ast.getType() == TokenTypes.IMPORT) {
            if (!allowClassImports) {
                final DetailAST startingDot = ast.getFirstChild();
                logsStarredImportViolation(startingDot);
            }
        }
        else if (!allowStaticMemberImports) {
            // must navigate past the static keyword
            final DetailAST startingDot = ast.getFirstChild().getNextSibling();
            logsStarredImportViolation(startingDot);
        }
    }

    /**
     * Gets the full import identifier.  If the import is a starred import and
     * it's not excluded then a violation is logged.
     *
     * @param startingDot the starting dot for the import statement
     */
    private void logsStarredImportViolation(DetailAST startingDot) {
        final FullIdent name = FullIdent.createFullIdent(startingDot);
        final String importText = name.getText();
        if (importText.endsWith(STAR_IMPORT_SUFFIX) && !excludes.contains(importText)) {
            log(startingDot, MSG_KEY, importText);
        }
    }

}
