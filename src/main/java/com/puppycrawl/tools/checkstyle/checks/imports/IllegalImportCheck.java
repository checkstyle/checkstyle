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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks for imports from a set of illegal packages.
 * </div>
 *
 * <p>
 * Note: By default, the check rejects all {@code sun.*} packages since programs
 * that contain direct calls to the {@code sun.*} packages are
 * <a href="https://www.oracle.com/java/technologies/faq-sun-packages.html">
 * "not guaranteed to work on all Java-compatible platforms"</a>. To reject other
 * packages, set property {@code illegalPkgs} to a list of the illegal packages.
 * </p>
 * <ul>
 * <li>
 * Property {@code illegalClasses} - Specify class names to reject, if <b>regexp</b>
 * property is not set, checks if import equals class name. If <b>regexp</b>
 * property is set, then list of class names will be interpreted as regular expressions.
 * Note, all properties for match will be used.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code illegalPkgs} - Specify packages to reject, if <b>regexp</b>
 * property is not set, checks if import is the part of package. If <b>regexp</b>
 * property is set, then list of packages will be interpreted as regular expressions.
 * Note, all properties for match will be used.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code sun}.
 * </li>
 * <li>
 * Property {@code regexp} - Control whether the {@code illegalPkgs} and
 * {@code illegalClasses} should be interpreted as regular expressions.
 * Type is {@code boolean}.
 * Default value is {@code false}.
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
 * {@code import.illegal}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class IllegalImportCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.illegal";

    /** The compiled regular expressions for packages. */
    private final List<Pattern> illegalPkgsRegexps = new ArrayList<>();

    /** The compiled regular expressions for classes. */
    private final List<Pattern> illegalClassesRegexps = new ArrayList<>();

    /**
     * Specify packages to reject, if <b>regexp</b> property is not set, checks
     * if import is the part of package. If <b>regexp</b> property is set, then
     * list of packages will be interpreted as regular expressions.
     * Note, all properties for match will be used.
     */
    private String[] illegalPkgs;

    /**
     * Specify class names to reject, if <b>regexp</b> property is not set,
     * checks if import equals class name. If <b>regexp</b> property is set,
     * then list of class names will be interpreted as regular expressions.
     * Note, all properties for match will be used.
     */
    private String[] illegalClasses;

    /**
     * Control whether the {@code illegalPkgs} and {@code illegalClasses}
     * should be interpreted as regular expressions.
     */
    private boolean regexp;

    /**
     * Creates a new {@code IllegalImportCheck} instance.
     */
    public IllegalImportCheck() {
        setIllegalPkgs("sun");
    }

    /**
     * Setter to specify packages to reject, if <b>regexp</b> property is not set,
     * checks if import is the part of package. If <b>regexp</b> property is set,
     * then list of packages will be interpreted as regular expressions.
     * Note, all properties for match will be used.
     *
     * @param from illegal packages
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     * @since 3.0
     */
    public final void setIllegalPkgs(String... from) {
        illegalPkgs = from.clone();
        illegalPkgsRegexps.clear();
        for (String illegalPkg : illegalPkgs) {
            illegalPkgsRegexps.add(CommonUtil.createPattern("^" + illegalPkg + "\\..*"));
        }
    }

    /**
     * Setter to specify class names to reject, if <b>regexp</b> property is not
     * set, checks if import equals class name. If <b>regexp</b> property is set,
     * then list of class names will be interpreted as regular expressions.
     * Note, all properties for match will be used.
     *
     * @param from illegal classes
     * @since 7.8
     */
    public void setIllegalClasses(String... from) {
        illegalClasses = from.clone();
        for (String illegalClass : illegalClasses) {
            illegalClassesRegexps.add(CommonUtil.createPattern(illegalClass));
        }
    }

    /**
     * Setter to control whether the {@code illegalPkgs} and {@code illegalClasses}
     * should be interpreted as regular expressions.
     *
     * @param regexp a {@code Boolean} value
     * @since 7.8
     */
    public void setRegexp(boolean regexp) {
        this.regexp = regexp;
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
        return new int[] {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final FullIdent imp;
        if (ast.getType() == TokenTypes.IMPORT) {
            imp = FullIdent.createFullIdentBelow(ast);
        }
        else {
            imp = FullIdent.createFullIdent(
                ast.getFirstChild().getNextSibling());
        }
        final String importText = imp.getText();
        if (isIllegalImport(importText)) {
            log(ast, MSG_KEY, importText);
        }
    }

    /**
     * Checks if an import matches one of the regular expressions
     * for illegal packages or illegal class names.
     *
     * @param importText the argument of the import keyword
     * @return if {@code importText} matches one of the regular expressions
     *         for illegal packages or illegal class names
     */
    private boolean isIllegalImportByRegularExpressions(String importText) {
        boolean result = false;
        for (Pattern pattern : illegalPkgsRegexps) {
            if (pattern.matcher(importText).matches()) {
                result = true;
                break;
            }
        }
        for (Pattern pattern : illegalClassesRegexps) {
            if (pattern.matcher(importText).matches()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if an import is from a package or class name that must not be used.
     *
     * @param importText the argument of the import keyword
     * @return if {@code importText} contains an illegal package prefix or equals illegal class name
     */
    private boolean isIllegalImportByPackagesAndClassNames(String importText) {
        boolean result = false;
        for (String element : illegalPkgs) {
            if (importText.startsWith(element + ".")) {
                result = true;
                break;
            }
        }
        if (illegalClasses != null) {
            for (String element : illegalClasses) {
                if (importText.equals(element)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Checks if an import is from a package or class name that must not be used.
     *
     * @param importText the argument of the import keyword
     * @return if {@code importText} is illegal import
     */
    private boolean isIllegalImport(String importText) {
        final boolean result;
        if (regexp) {
            result = isIllegalImportByRegularExpressions(importText);
        }
        else {
            result = isIllegalImportByPackagesAndClassNames(importText);
        }
        return result;
    }

}
