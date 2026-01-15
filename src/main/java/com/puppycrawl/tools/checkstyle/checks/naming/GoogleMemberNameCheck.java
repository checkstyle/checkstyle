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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that member names conform to the
 * <a href=
 * "https://google.github.io/styleguide/javaguide.html#s5.2.5-non-constant-field-names">
 * Google Java Style Guide</a> for non-constant field naming.
 * </div>
 *
 * <p>
 * This check enforces Google's specific member naming requirements:
 * </p>
 * <ul>
 * <li>Member names must start with a lowercase letter and use uppercase letters
 * for word boundaries.</li>
 * <li>Underscores may be used to separate adjacent numbers (e.g., version
 * numbers like {@code guava33_4_5}), but NOT between letters and digits.</li>
 * </ul>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class GoogleMemberNameCheck extends AbstractCheck {

    /**
     * A key is pointing to the violation message text in "messages.properties" file.
     */
    public static final String MSG_KEY_INVALID_FORMAT = "google.member.name.format";

    /**
     * A key pointing to the violation message for invalid underscore usage.
     */
    public static final String MSG_KEY_INVALID_UNDERSCORE = "google.member.name.underscore";

    /**
     * Pattern for valid member names in Google style.
     * Format: lowerCamelCase, optionally followed by numbering suffix.
     *
     * <p>
     * Explanation:
     * <ul>
     * <li>{@code ^(?![a-z]$)} - Negative lookahead: cannot be single lowercase char</li>
     * <li>{@code (?![a-z][A-Z])} - Negative lookahead: cannot be like "fO"</li>
     * <li>{@code [a-z]} - Must start with lowercase</li>
     * <li>{@code [a-z0-9]*} - Followed by lowercase or digits</li>
     * <li>{@code (?:[A-Z][a-z0-9]*)*} - CamelCase humps (uppercase followed by lowercase)</li>
     * <li>{@code $} - End of string (numbering suffix validated separately)</li>
     * </ul>
     */
    private static final Pattern MEMBER_NAME_PATTERN = Pattern
            .compile("^(?![a-z]$)(?![a-z][A-Z])[a-z][a-z0-9]*(?:[A-Z][a-z0-9]*)*$");

    /**
     * Pattern to strip trailing numbering suffix (underscore followed by digits).
     */
    private static final Pattern NUMBERING_SUFFIX_PATTERN = Pattern.compile("(?:_[0-9]+)+$");

    /**
     * Pattern to detect invalid underscore usage: leading, trailing, consecutive,
     * or between letter-letter, letter-digit, or digit-letter combinations.
     */
    private static final Pattern INVALID_UNDERSCORE_PATTERN =
            Pattern.compile("^_|_$|__|[a-zA-Z]_[a-zA-Z]|[a-zA-Z]_\\d|\\d_[a-zA-Z]");

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
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (mustCheckName(ast)) {
            final DetailAST nameAst = NullUtil.notNull(ast.findFirstToken(TokenTypes.IDENT));
            final String memberName = nameAst.getText();

            validateMemberName(nameAst, memberName);
        }
    }

    /**
     * Checks if this field should be validated. Returns true for instance fields
     * and static non-final fields. Constants (static final), local variables,
     * and interface/annotation fields are excluded.
     *
     * @param ast the VARIABLE_DEF AST node
     * @return true if this variable should be checked
     */
    private static boolean mustCheckName(DetailAST ast) {
        final DetailAST modifiersAST = NullUtil.notNull(ast.findFirstToken(TokenTypes.MODIFIERS));
        final boolean isStatic =
            modifiersAST.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
        final boolean isFinal =
            modifiersAST.findFirstToken(TokenTypes.FINAL) != null;

        final boolean isConstant = isStatic && isFinal;

        return !isConstant
            && !ScopeUtil.isInInterfaceOrAnnotationBlock(ast)
            && !ScopeUtil.isLocalVariableDef(ast);
    }

    /**
     * Validates a member name according to Google style.
     *
     * @param nameAst    the IDENT AST node containing the member name
     * @param memberName the member name string
     */
    private void validateMemberName(DetailAST nameAst, String memberName) {
        if (INVALID_UNDERSCORE_PATTERN.matcher(memberName).find()) {
            log(nameAst, MSG_KEY_INVALID_UNDERSCORE, memberName);
        }
        else {
            final String nameWithoutNumberingSuffix = NUMBERING_SUFFIX_PATTERN
                    .matcher(memberName).replaceAll("");
            if (!MEMBER_NAME_PATTERN.matcher(nameWithoutNumberingSuffix).matches()) {
                log(nameAst, MSG_KEY_INVALID_FORMAT, memberName);
            }
        }
    }
}
