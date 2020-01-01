////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that the at-clause tag is followed by description.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code javadocTokens} - javadoc tokens to check
 * Default value is
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html#PARAM_LITERAL">
 * PARAM_LITERAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html#RETURN_LITERAL">
 * RETURN_LITERAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html#THROWS_LITERAL">
 * THROWS_LITERAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html#EXCEPTION_LITERAL">
 * EXCEPTION_LITERAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/JavadocTokenTypes.html#DEPRECATED_LITERAL">
 * DEPRECATED_LITERAL</a>.
 * </li>
 * </ul>
 * <p>
 * Default configuration that will check {@code @param}, {@code @return},
 * {@code @throws}, {@code @deprecated}:
 * </p>
 * <pre>
 * &lt;module name="NonEmptyAtclauseDescription"/&gt;
 * </pre>
 * <p>
 * To configure the check to validate only {@code @param} and {@code @return} tags:
 * </p>
 * <pre>
 * &lt;module name="NonEmptyAtclauseDescription"&gt;
 *   &lt;property name="javadocTokens" value="PARAM_LITERAL,RETURN_LITERAL"/&gt;
 * &lt;/module&gt;
 * </pre>
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
            JavadocTokenTypes.PARAM_LITERAL,
            JavadocTokenTypes.RETURN_LITERAL,
            JavadocTokenTypes.THROWS_LITERAL,
            JavadocTokenTypes.EXCEPTION_LITERAL,
            JavadocTokenTypes.DEPRECATED_LITERAL,
        };
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        if (isEmptyTag(ast.getParent())) {
            log(ast.getLineNumber(), MSG_KEY, ast.getText());
        }
    }

    /**
     * Tests if at-clause tag is empty.
     * @param tagNode at-clause tag.
     * @return true, if at-clause tag is empty.
     */
    private static boolean isEmptyTag(DetailNode tagNode) {
        final DetailNode tagDescription =
                JavadocUtil.findFirstToken(tagNode, JavadocTokenTypes.DESCRIPTION);
        return tagDescription == null;
    }

}
