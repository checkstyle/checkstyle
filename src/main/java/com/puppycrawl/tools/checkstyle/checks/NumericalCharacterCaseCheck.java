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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that all numerical literal prefixes, infixes, and suffixes are written
 * using lowercase letters (for example {@code 0x}, {@code 0b}, {@code e},
 * {@code p}, {@code f}, and {@code d}).
 * This convention follows the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-literals">
 * OpenJDK Style Guide</a>.
 * </div>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class NumericalCharacterCaseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "numerical.literal";

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
        return new int[] {TokenTypes.NUM_LONG, TokenTypes.NUM_INT,
            TokenTypes.NUM_FLOAT, TokenTypes.NUM_DOUBLE,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String text = ast.getText();
        final boolean isHex = text.startsWith("0x");
        if (text.startsWith("0X") || text.startsWith("0B") || text.contains("P")) {
            log(ast, MSG_KEY);
        }
        else if (!isHex && text.contains("E")) {
            log(ast, MSG_KEY);
        }
        else if ((text.endsWith("F") || text.endsWith("D")) && (!isHex || text.contains("p"))) {
            log(ast, MSG_KEY);
        }
    }
}
