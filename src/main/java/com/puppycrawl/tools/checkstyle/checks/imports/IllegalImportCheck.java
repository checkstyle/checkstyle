////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks for imports from a set of illegal packages.
 * By default, the check rejects all {@code sun.*} packages
 * since programs that contain direct calls to the {@code sun.*} packages
 * are <a href="http://www.oracle.com/technetwork/java/faq-sun-packages-142232.html">
 * not 100% Pure Java</a>.
 * </p>
 * <p>
 * To reject other packages, set property illegalPkgs to a comma-separated
 * list of the illegal packages.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="IllegalImport"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it rejects packages
 * {@code java.io.*} and {@code java.sql.*} is
 * </p>
 * <pre>
 * &lt;module name="IllegalImport"&gt;
 *    &lt;property name="illegalPkgs" value="java.io, java.sql"/&gt;
 * &lt;/module&gt;
 *
 * Compatible with Java 1.5 source.
 *
 * </pre>
 * @author Oliver Burn
 * @author Lars Kühne
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

    /** List of illegal packages. */
    private String[] illegalPkgs;

    /** List of illegal classes. */
    private String[] illegalClasses;

    /**
     * Whether the packages or class names
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
     * Set the list of illegal packages.
     * @param from array of illegal packages
     * @noinspection WeakerAccess
     */
    public final void setIllegalPkgs(String... from) {
        illegalPkgs = from.clone();
        illegalPkgsRegexps.clear();
        for (String illegalPkg : illegalPkgs) {
            illegalPkgsRegexps.add(CommonUtils.createPattern("^" + illegalPkg + "\\..*"));
        }
    }

    /**
     * Set the list of illegal classes.
     * @param from array of illegal classes
     */
    public void setIllegalClasses(String... from) {
        illegalClasses = from.clone();
        for (String illegalClass : illegalClasses) {
            illegalClassesRegexps.add(CommonUtils.createPattern(illegalClass));
        }
    }

    /**
     * Controls whether the packages or class names
     * should be interpreted as regular expressions.
     * @param regexp a {@code Boolean} value
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
        if (isIllegalImport(imp.getText())) {
            log(ast.getLineNo(),
                ast.getColumnNo(),
                MSG_KEY,
                imp.getText());
        }
    }

    /**
     * Checks if an import matches one of the regular expressions
     * for illegal packages or illegal class names.
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
        if (!result) {
            for (Pattern pattern : illegalClassesRegexps) {
                if (pattern.matcher(importText).matches()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Checks if an import is from a package or class name that must not be used.
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
        if (!result && illegalClasses != null) {
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
