///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that the block tag is followed by description.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code javadocTokens} - javadoc tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
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
 * To configure the default check that will check {@code @param}, {@code @return},
 * {@code @throws}, {@code @deprecated}:
 * </p>
 * <pre>
 * &lt;module name="NonEmptyAtclauseDescription"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test
 * {
 * &#47;**
 * * Violation for param "b" and at tags "deprecated", "throws".
 * * &#64;param a Some javadoc // OK
 * * &#64;param b
 * * &#64;deprecated
 * * &#64;throws Exception
 * *&#47;
 * public int method(String a, int b) throws Exception
 * {
 * return 1;
 * }
 * }
 * </pre>
 * <p>
 * To configure the check to validate only {@code @param} and {@code @return} tags:
 * </p>
 * <pre>
 * &lt;module name="NonEmptyAtclauseDescription"&gt;
 *   &lt;property name="javadocTokens" value="PARAM_LITERAL,RETURN_LITERAL"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test
 * {
 * &#47;**
 * * Violation for param "b". Tags "deprecated", "throws" are ignored.
 * * &#64;param a Some javadoc // OK
 * * &#64;param b
 * * &#64;deprecated
 * * &#64;throws Exception
 * *&#47;
 * public int method(String a, int b) throws Exception
 * {
 * return 1;
 * }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * <li>
 * {@code non.empty.atclause}
 * </li>
 * </ul>
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
     * Tests if block tag is empty.
     *
     * @param tagNode block tag.
     * @return true, if block tag is empty.
     */
    private static boolean isEmptyTag(DetailNode tagNode) {
        final DetailNode tagDescription =
                JavadocUtil.findFirstToken(tagNode, JavadocTokenTypes.DESCRIPTION);
        return tagDescription == null
            || hasOnlyEmptyText(tagDescription);
    }

    /**
     * Tests if description node is empty (has only new lines and blank strings).
     *
     * @param description description node.
     * @return true, if description node has only new lines and blank strings.
     */
    private static boolean hasOnlyEmptyText(DetailNode description) {
        boolean result = true;
        for (DetailNode child : description.getChildren()) {
            if (child.getType() != JavadocTokenTypes.TEXT
                    || !CommonUtil.isBlank(child.getText())) {
                result = false;
                break;
            }
        }
        return result;
    }

}
