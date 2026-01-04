///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * <div>
 * Checks that method names conform to the
 * <a href=
 * "https://google.github.io/styleguide/javaguide.html#s5.2.3-method-names">
 * Google Java Style Guide</a> for method naming.
 * </div>
 *
 * <p>
 * This check enforces Google's specific method naming requirements:
 * </p>
 * <ul>
 * <li>Method names must be in lowerCamelCase.</li>
 * <li>Underscores may appear in JUnit test method names to separate logical
 * components.</li>
 * <li>Underscores may be used to separate adjacent numbers (e.g., version
 * numbers like
 * {@code guava33_4_5}), but NOT between letters and digits.</li>
 * </ul>
 *
 * <p>
 * This check has no configurable properties. It is specifically designed to
 * enforce Google Java Style rules:
 * <a href="https://google.github.io/styleguide/javaguide.html#s5.2.3-method-names">
 * 5.2.3 Method Names</a> and
 * <a href="https://google.github.io/styleguide/javaguide.html#s5.3-camel-case">
 * 5.3 Camel case:</a> defined.
 * </p>
 *
 * @since 12.4.0
 */
@StatelessCheck
public class GoogleMethodNameCheck extends AbstractCheck {

    /**
     * A key is pointing to the violation message text in "messages.properties" file.
     */
    public static final String MSG_KEY_FORMAT_REGULAR = "google.method.name.format.regular";

    /**
     * A key is pointing to the violation message text in "messages.properties" file.
     */
    public static final String MSG_KEY_FORMAT_TEST = "google.method.name.format.test";

    /**
     * A key is pointing to the violation message text in "messages.properties" file.
     */
    public static final String MSG_KEY_UNDERSCORE_REGULAR = "google.method.name.underscore.regular";

    /**
     * A key is pointing to the violation message text in "messages.properties" file.
     */
    public static final String MSG_KEY_UNDERSCORE_TEST = "google.method.name.underscore.test";

    /**
     * Pattern for valid regular method names in Google style.
     * Format: lowerCamelCase, optionally followed by numbering suffix.
     *
     * <p>
     * Explanation:
     * <ul>
     * <li>{@code ^(?![a-z]$)} - Negative lookahead: cannot be single lowercase char</li>
     * <li>{@code (?![a-z][A-Z])} - Negative lookahead: cannot be like "fO"</li>
     * <li>{@code [a-z]} - Must start with lowercase</li>
     * <li>{@code [a-z0-9]*} - Followed by lowercase or digits</li>
     * <li>{@code (?:[A-Z][a-z0-9]*)*} - CamelCase humps (uppercase followed by
     * lowercase/digits)</li>
     * <li>{@code $} - End of string (numbering suffix validated separately)</li>
     * </ul>
     */
    private static final Pattern REGULAR_METHOD_NAME_PATTERN = Pattern
            .compile("^(?![a-z]$)(?![a-z][A-Z])[a-z][a-z0-9]*(?:[A-Z][a-z0-9]*)*$");

    /**
     * Pattern for valid test method names in Google style.
     * Each segment between underscores must:
     * <ul>
     * <li>Should be lowerCamelCase</li>
     * <li>Be at least 2 characters long</li>
     * <li>Not start with single lowercase followed by uppercase (e.g., "fO")</li>
     * </ul>
     */
    private static final Pattern TEST_METHOD_NAME_PATTERN = Pattern.compile(
            "^(?![a-z](?:_|$))(?![a-z][A-Z])[a-z][a-z0-9]*(?:[A-Z][a-z0-9]*)*"
            + "(?:_(?![a-z](?:_|$))(?![a-z][A-Z])[a-z][a-z0-9]*(?:[A-Z][a-z0-9]*)*)*$");

    /**
     * Pattern to strip trailing numbering suffix (underscore followed by digits).
     */
    private static final Pattern NUMBERING_SUFFIX_PATTERN = Pattern.compile("(?:_[0-9]+)+$");

    /**
     * Matches invalid underscore usage for regular methods: leading, trailing, double,
     * or between any characters (letter-letter, letter-digit, digit-letter).
     */
    private static final Pattern INVALID_UNDERSCORE_PATTERN_REGULAR =
        Pattern.compile("^_|_$|__|[a-zA-Z]_[a-zA-Z]|[a-zA-Z]_\\d|\\d_[a-zA-Z]");

    /**
     * Matches invalid underscore usage for test methods: leading, trailing, double, or between
     * letter-digit/digit-letter.
     */
    private static final Pattern INVALID_UNDERSCORE_PATTERN_TEST =
        Pattern.compile("^_|_$|__|[a-zA-Z]_\\d|\\d_[a-zA-Z]");

    /**
     * Set of JUnit 5 test annotation names that indicate a test method.
     */
    private static final Set<String> TEST_ANNOTATIONS = Set.of(
            "Test",
            "org.junit.jupiter.api.Test",
            "org.junit.Test",
            "ParameterizedTest",
            "org.junit.jupiter.params.ParameterizedTest",
            "RepeatedTest",
            "org.junit.jupiter.api.RepeatedTest");

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
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!AnnotationUtil.hasOverrideAnnotation(ast)) {
            final DetailAST nameAst = ast.findFirstToken(TokenTypes.IDENT);
            final String methodName = nameAst.getText();

            if (hasTestAnnotation(ast)) {
                validateTestMethodName(nameAst, methodName);
            }
            else {
                validateRegularMethodName(nameAst, methodName);
            }
        }
    }

    /**
     * Checks if the method has any test annotation.
     *
     * @param methodDef the METHOD_DEF AST node
     * @return true if the method has @Test, @ParameterizedTest, or @RepeatedTest annotation.
     */
    private static boolean hasTestAnnotation(DetailAST methodDef) {
        return AnnotationUtil.containsAnnotation(methodDef, TEST_ANNOTATIONS);
    }

    /**
     * Validates a regular (non-test) method name according to Google style.
     *
     * @param nameAst    the IDENT AST node containing the method name
     * @param methodName the method name string
     */
    private void validateRegularMethodName(DetailAST nameAst, String methodName) {
        if (INVALID_UNDERSCORE_PATTERN_REGULAR.matcher(methodName).find()) {
            log(nameAst, MSG_KEY_UNDERSCORE_REGULAR, methodName);
        }
        else {
            final String nameWithoutNumberingSuffix = NUMBERING_SUFFIX_PATTERN
                    .matcher(methodName).replaceAll("");
            if (!REGULAR_METHOD_NAME_PATTERN.matcher(nameWithoutNumberingSuffix).matches()) {
                log(nameAst, MSG_KEY_FORMAT_REGULAR, methodName);
            }
        }
    }

    /**
     * Validates a test method name according to Google style.
     *
     * @param nameAst    the IDENT AST node containing the method name
     * @param methodName the method name string
     */
    private void validateTestMethodName(DetailAST nameAst, String methodName) {

        if (INVALID_UNDERSCORE_PATTERN_TEST.matcher(methodName).find()) {
            log(nameAst, MSG_KEY_UNDERSCORE_TEST, methodName);
        }
        else {

            final String nameWithoutSuffix = NUMBERING_SUFFIX_PATTERN
                    .matcher(methodName).replaceAll("");
            if (!TEST_METHOD_NAME_PATTERN.matcher(nameWithoutSuffix).matches()) {
                log(nameAst, MSG_KEY_FORMAT_TEST, methodName);
            }
        }
    }
}
