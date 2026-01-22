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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks the number of other types a given class/record/interface/enum/annotation
 * relies on. Also, the square of this has been shown to indicate the amount
 * of maintenance required in functional programs (on a file basis) at least.
 * </div>
 *
 * <p>
 * This check processes files in the following way:
 * </p>
 * <ol>
 * <li>
 * Iterates over all tokens that might contain type reference.
 * </li>
 * <li>
 * If a class was imported with direct import (i.e. {@code import java.math.BigDecimal}),
 * or the class was referenced with the package name (i.e. {@code java.math.BigDecimal value})
 * and the package was added to the {@code excludedPackages} parameter,
 * the class does not increase complexity.
 * </li>
 * <li>
 * If a class name was added to the {@code excludedClasses} parameter,
 * the class does not increase complexity.
 * </li>
 * </ol>
 *
 * @since 3.4
 */
public final class ClassFanOutComplexityCheck extends AbstractClassCouplingCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "classFanOutComplexity";

    /** Default value of max value. */
    private static final int DEFAULT_MAX = 20;

    /** Creates new instance of this check. */
    public ClassFanOutComplexityCheck() {
        super(DEFAULT_MAX);
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.EXTENDS_CLAUSE,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.ANNOTATION,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.TYPE,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    protected String getLogMessageId() {
        return MSG_KEY;
    }

    /**
     * Setter to specify user-configured regular expressions to ignore classes.
     *
     * @param from array representing regular expressions of classes to ignore.
     * @propertySince 7.7
     * @noinspection RedundantMethodOverride
     * @noinspectionreason Display module's unique property version
     */
    @Override
    public void setExcludeClassesRegexps(Pattern... from) {
        super.setExcludeClassesRegexps(from);
    }

    /**
     * Setter to specify user-configured class names to ignore.
     *
     * @param excludedClasses classes to ignore.
     * @propertySince 5.7
     * @noinspection RedundantMethodOverride
     * @noinspectionreason Display module's unique property version
     */
    @Override
    public void setExcludedClasses(String... excludedClasses) {
        super.setExcludedClasses(excludedClasses);
    }

    /**
     * Setter to specify user-configured packages to ignore.
     *
     * @param excludedPackages packages to ignore.
     * @throws IllegalArgumentException if there are invalid identifiers among the packages.
     * @propertySince 7.7
     * @noinspection RedundantMethodOverride
     * @noinspectionreason Display module's unique property version
     */
    @Override
    public void setExcludedPackages(String... excludedPackages) {
        super.setExcludedPackages(excludedPackages);
    }

}
