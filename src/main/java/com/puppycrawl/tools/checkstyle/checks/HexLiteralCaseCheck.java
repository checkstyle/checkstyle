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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div> Checks that hexadecimal literals are defined using uppercase letters {@code (A-F)}
 * rather than lowercase {@code (a-f)}.
 * This improves readability and avoids confusion with suffixes like {@code f}(float)
 * and {@code d}(double). For example, use {@code 0xFF} instead of {@code 0xff}.
 * All other numerical prefixes, infixes, and suffixes (such as {@code 0x}, {@code 0b},
 * {@code f}, {@code d}) should remain lowercase.
 * This convention follows the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-literals">
 * OpenJDK Style Guide</a>.
 * </div>
 *
 * <p>
 * For example, {@code 0xAF} is valid, but {@code 0xaf} is not.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 *
 * <ul>
 * <li>
 * {@code hex.Literal}
 * </li>
 * </ul>
 *
 * @since 10.27.0
 */
@StatelessCheck
public class HexLiteralCaseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "hex.Literal";
    /**
     * Lowercase hexadecimal characters that are considered violations
     * when used in hexadecimal literals (e.g., 0x1a). Hex digits should be uppercase.
     */
    private final Set<Character> hexChars = Set.of('a', 'b', 'c', 'd', 'e', 'f');

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
        return new int[] {TokenTypes.NUM_LONG, TokenTypes.NUM_INT};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getText().toLowerCase(Locale.ROOT).startsWith("0x")) {
            final char[] charArray = ast.getText().toCharArray();
            final List<Character> characterList = new ArrayList<>(charArray.length);
            for (char letter : charArray) {
                characterList.add(letter);
            }
            final boolean containLowerLetter = characterList.stream()
                    .anyMatch(hexChars::contains);
            if (containLowerLetter) {
                log(ast, MSG_KEY);
            }
        }
    }
}
