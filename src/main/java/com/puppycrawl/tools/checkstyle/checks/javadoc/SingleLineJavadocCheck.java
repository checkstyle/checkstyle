////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

/**
 * Checks that a JavaDoc block which can fit on a single line and doesn't
 * contain at-clauses may be substituted by a single line.
 *
 * Default configuration:
 * <pre>
 * &lt;module name=&quot;SingleLineJavadoc&quot;/&gt;
 * </pre>
 *
 * @author baratali
 * @author maxvetrenko
 *
 */
public class SingleLineJavadocCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode aAst)
    {
        if (isSingleLineJavadoc()
                && (hasJavadocTags(aAst) || hasJavadocInlineTags(aAst)))
        {
            log(aAst.getLineNumber(), "singleline.javadoc");
        }
    }

    /**
     * Checks if comment is single line comment.
     *
     * @return true, if comment is single line comment.
     */
    private boolean isSingleLineJavadoc()
    {
        final DetailAST blockCommentStart = getBlockCommentAst();
        final DetailAST blockCommentEnd = blockCommentStart.getLastChild();

        return blockCommentStart.getLineNo() == blockCommentEnd.getLineNo();
    }

    /**
     * Checks if comment has javadoc tags.
     *
     * @param aJavadocRoot javadoc root node.
     * @return true, if comment has javadoc tags.
     */
    private boolean hasJavadocTags(DetailNode aJavadocRoot)
    {
        final DetailNode javadocTagSection =
                JavadocUtils.findFirstToken(aJavadocRoot, JavadocTokenTypes.JAVADOC_TAG);
        return javadocTagSection != null;
    }

    /**
     * Checks if comment has in-line tags tags.
     *
     * @param aJavadocRoot javadoc root node.
     * @return true, if comment has in-line tags tags.
     */
    private boolean hasJavadocInlineTags(DetailNode aJavadocRoot)
    {
        return JavadocUtils.branchContains(aJavadocRoot, JavadocTokenTypes.JAVADOC_INLINE_TAG);
    }
}
