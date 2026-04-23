///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that there are no import statements that use the {@code *} notation by default.
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
 * Property {@code maxAllowed} can be used to allow a limited number of wildcard imports
 * per file.
 * </p>
 *
 * <p>
 * Notes:
 * Note that property {@code excludes} is not recursive, subpackages of excluded
 * packages are not automatically excluded.
 * </p>
 *
 * @since 3.0
 */
@FileStatefulCheck
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

    /** Maximum number of wildcard imports allowed per file. */
    private int maxAllowed;

    /** Number of wildcard imports seen in the current file. */
    private int starImportCount;

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

    @Override
    public void beginTree(DetailAST rootAST) {
        starImportCount = 0;
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

    /**
     * Setter to specify the maximum number of wildcard imports allowed per file.
     *
     * @param maxAllowed the maximum number of wildcard imports allowed per file.
     * @since 13.4.1
     */
    public void setMaxAllowed(int maxAllowed) {
        this.maxAllowed = maxAllowed;
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final DetailAST startingDot;
        if (ast.getType() == TokenTypes.IMPORT) {
            startingDot = ast.getFirstChild();
        }
        else {
            startingDot = ast.getFirstChild().getNextSibling();
        }
        final FullIdent name = FullIdent.createFullIdent(startingDot);
        final String importText = name.getText();

        if (importText.endsWith(STAR_IMPORT_SUFFIX)) {
            if (maxAllowed > 0) {
                starImportCount++;
                if (starImportCount > maxAllowed) {
                    log(startingDot, MSG_KEY, importText);
                }
            }
            else if (ast.getType() == TokenTypes.IMPORT) {
                if (!allowClassImports && !excludes.contains(importText)) {
                    log(startingDot, MSG_KEY, importText);
                }
            }
            else if (!allowStaticMemberImports && !excludes.contains(importText)) {
                log(startingDot, MSG_KEY, importText);
            }
        }
    }

}
