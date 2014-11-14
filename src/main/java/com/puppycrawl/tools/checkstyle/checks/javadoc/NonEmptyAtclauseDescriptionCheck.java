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

import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

/**
 * Checks that the at-clause tag is followed by description .
 * Default configuration that will check <code>@param</code>, <code>@return</code>,
 * <code>@throws</code>, <code>@deprecated</code> to:
 * <pre>
 * &lt;module name=&quot;NonEmptyAtclauseDescription&quot;/&gt;
 * </pre>
 * <p>
 * To check non-empty at-clause description for tags <code>@throws</code>,
 * <code>@deprecated</code>, use following configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;NonEmptyAtclauseDescription&quot;&gt;
 *     &lt;property name=&quot;target&quot; value=&quot;JAVADOC_TAG_THROWS_LITERAL,
 *     JAVADOC_TAG_DEPRECATED_LITERAL&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author maxvetrenko
 *
 */
public class NonEmptyAtclauseDescriptionCheck extends AbstractJavadocCheck
{

    @Override
    public int[] getDefaultJavadocTokens()
    {
        return new int[] {
            JavadocTokenTypes.PARAM_LITERAL,
            JavadocTokenTypes.RETURN_LITERAL,
            JavadocTokenTypes.THROWS_LITERAL,
            JavadocTokenTypes.DEPRECATED_LITERAL,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode aAst)
    {
        if (isEmptyTag(aAst.getParent())) {
            log(aAst.getLineNumber(), "non.empty.atclause", aAst.getText());
        }
    }

    /**
     * Tests if at-clause tag is empty.
     * @param aTagNode at-clause tag.
     * @return true, if at-clause tag is empty.
     */
    private boolean isEmptyTag(DetailNode aTagNode)
    {
        final DetailNode tagDescription =
                JavadocUtils.findFirstToken(aTagNode, JavadocTokenTypes.DESCRIPTION);
        return tagDescription == null;
    }
}
