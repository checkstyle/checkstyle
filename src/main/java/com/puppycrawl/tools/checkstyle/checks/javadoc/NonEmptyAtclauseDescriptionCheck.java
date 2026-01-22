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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that the block tag is followed by description.
 * </div>
 *
 * @since 6.0
 */
@StatelessCheck
public class NonEmptyAtclauseDescriptionCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "non.empty.atclause";

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
            JavadocCommentsTokenTypes.DEPRECATED_BLOCK_TAG,
        };
    }

    /**
     * Adds a set of tokens the check is interested in.
     *
     * @param strRep the string representation of the tokens interested in
     * @propertySince 7.3
     * @noinspection RedundantMethodOverride
     * @noinspectionreason Display module's unique property version
     */
    @Override
    public void setJavadocTokens(String... strRep) {
        super.setJavadocTokens(strRep);
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isEmptyTag(ast)) {
            log(ast.getLineNumber(), MSG_KEY);
        }
    }

    /**
     * Tests if block tag is empty.
     *
     * @param tagNode block tag.
     * @return true, if block tag is empty.
     */
    private static boolean isEmptyTag(DetailNode tagNode) {
        final DetailNode tagDescription =
                JavadocUtil.findFirstToken(tagNode, JavadocCommentsTokenTypes.DESCRIPTION);
        return tagDescription == null;
    }

}
