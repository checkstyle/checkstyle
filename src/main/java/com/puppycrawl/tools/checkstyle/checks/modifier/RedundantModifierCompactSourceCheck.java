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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for redundant modifiers on methods declared directly in compact source files.
 * </div>
 *
 * <p>The check reports {@code final} because the class implicitly declared by a compact
 * compilation unit is final. It also reports {@code strictfp}, whose floating-point semantics
 * are redundant since Java 17. See <a href="https://openjdk.org/jeps/306">JEP 306</a>.</p>
 *
 * <p>Only methods whose immediate parent is a compact compilation unit are checked. Ordinary
 * and nested declarations remain in the scope of {@link RedundantModifierCheck}.</p>
 *
 * @since 13.9.0
 */
@StatelessCheck
public class RedundantModifierCompactSourceCheck extends AbstractCheck {

    /** A key is pointing to the warning message text in "messages.properties" file. */
    public static final String MSG_KEY = "redundantModifierCompactSource";

    /** Creates a new instance. */
    public RedundantModifierCompactSourceCheck() {
        // No code.
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent().getType() == TokenTypes.COMPACT_COMPILATION_UNIT) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            for (DetailAST child = modifiers.getFirstChild(); child != null;
                    child = child.getNextSibling()) {
                if (TokenUtil.isOfType(child, TokenTypes.FINAL, TokenTypes.STRICTFP)) {
                    log(child, MSG_KEY, child.getText());
                }
            }
        }
    }

}
