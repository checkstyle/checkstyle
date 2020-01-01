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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks the order of
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
 * javadoc block-tags or javadoc tags</a>.
 * </p>
 * <p>
 * Note: Google used the term "at-clauses" for block tags in their guide till 2017-02-28.
 * </p>
 *
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code target} - Specify the list of targets to check at-clauses. Default value is
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>.
 * </li>
 * <li>
 * Property {@code tagOrder} - Specify the order by tags.
 * Default value is
 * {@code @author, @deprecated, @exception, @param, @return, @see, @serial, @serialData, @serialField, @since, @throws, @version}.
 * </li>
 * </ul>
 * <p>
 * Default configuration
 * </p>
 * <pre>
 * &lt;module name=&quot;AtclauseOrder&quot;&gt;
 *   &lt;property name=&quot;tagOrder&quot; value=&quot;&#64;author, &#64;version, &#64;param,
 *   &#64;return, &#64;throws, &#64;exception, &#64;see, &#64;since, &#64;serial,
 *   &#64;serialField, &#64;serialData, &#64;deprecated&quot;/&gt;
 *   &lt;property name=&quot;target&quot; value=&quot;CLASS_DEF, INTERFACE_DEF, ENUM_DEF,
 *   METHOD_DEF, CTOR_DEF, VARIABLE_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 6.0
 */
@StatelessCheck
public class AtclauseOrderCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "at.clause.order";

    /**
     * Default order of atclauses.
     */
    private static final String[] DEFAULT_ORDER = {
        "@author", "@version",
        "@param", "@return",
        "@throws", "@exception",
        "@see", "@since",
        "@serial", "@serialField",
        "@serialData", "@deprecated",
    };

    /**
     * Specify the list of targets to check at-clauses.
     */
    private List<Integer> target = Arrays.asList(
        TokenTypes.CLASS_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.ENUM_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.VARIABLE_DEF
    );

    /**
     * Specify the order by tags.
     */
    private List<String> tagOrder = Arrays.asList(DEFAULT_ORDER);

    /**
     * Setter to specify the list of targets to check at-clauses.
     * @param targets user's targets.
     */
    public void setTarget(String... targets) {
        final List<Integer> customTarget = new ArrayList<>();
        for (String temp : targets) {
            customTarget.add(TokenUtil.getTokenId(temp.trim()));
        }
        target = customTarget;
    }

    /**
     * Setter to specify the order by tags.
     * @param orders user's orders.
     */
    public void setTagOrder(String... orders) {
        final List<String> customOrder = new ArrayList<>();
        for (String order : orders) {
            customOrder.add(order.trim());
        }
        tagOrder = customOrder;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final int parentType = getParentType(getBlockCommentAst());

        if (target.contains(parentType)) {
            checkOrderInTagSection(ast);
        }
    }

    /**
     * Checks order of atclauses in tag section node.
     * @param javadoc Javadoc root node.
     */
    private void checkOrderInTagSection(DetailNode javadoc) {
        int maxIndexOfPreviousTag = 0;

        for (DetailNode node : javadoc.getChildren()) {
            if (node.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                final String tagText = JavadocUtil.getFirstChild(node).getText();
                final int indexOfCurrentTag = tagOrder.indexOf(tagText);

                if (indexOfCurrentTag != -1) {
                    if (indexOfCurrentTag < maxIndexOfPreviousTag) {
                        log(node.getLineNumber(), MSG_KEY, tagOrder.toString());
                    }
                    else {
                        maxIndexOfPreviousTag = indexOfCurrentTag;
                    }
                }
            }
        }
    }

    /**
     * Returns type of parent node.
     * @param commentBlock child node.
     * @return parent type.
     */
    private static int getParentType(DetailAST commentBlock) {
        final DetailAST parentNode = commentBlock.getParent();
        int result = 0;
        if (parentNode != null) {
            result = parentNode.getType();
            if (result == TokenTypes.TYPE || result == TokenTypes.MODIFIERS) {
                result = parentNode.getParent().getType();
            }
        }
        return result;
    }

}
