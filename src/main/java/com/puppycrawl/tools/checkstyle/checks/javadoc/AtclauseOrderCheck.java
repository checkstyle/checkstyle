///
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
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the order of
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
 * javadoc block-tags or javadoc tags</a>.
 * </div>
 *
 * <p>
 * Note: Google used the term "at-clauses" for block tags in their guide till 2017-02-28.
 * </p>
 *
 * <ul>
 * <li>
 * Property {@code tagOrder} - Specify the order by tags.
 * Type is {@code java.lang.String[]}.
 * Default value is
 * {@code @author, @deprecated, @exception, @param, @return, @see, @serial, @serialData, @serialField, @since, @throws, @version}.
 * </li>
 * <li>
 * Property {@code target} - Specify block tags targeted.
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenTypesSet}.
 * Default value is
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>.
 * </li>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code at.clause.order}
 * </li>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.unclosedHtml}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
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
     * Specify block tags targeted.
     */
    @XdocsPropertyType(PropertyType.TOKEN_ARRAY)
    private BitSet target = TokenUtil.asBitSet(
        TokenTypes.CLASS_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.ENUM_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.VARIABLE_DEF,
        TokenTypes.RECORD_DEF,
        TokenTypes.COMPACT_CTOR_DEF
    );

    /**
     * Specify the order by tags.
     */
    private List<String> tagOrder = Arrays.asList(DEFAULT_ORDER);

    /**
     * Setter to specify block tags targeted.
     *
     * @param targets user's targets.
     * @since 6.0
     */
    public void setTarget(String... targets) {
        target = TokenUtil.asBitSet(targets);
    }

    /**
     * Setter to specify the order by tags.
     *
     * @param orders user's orders.
     * @since 6.0
     */
    public void setTagOrder(String... orders) {
        tagOrder = Arrays.asList(orders);
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

        if (target.get(parentType)) {
            checkOrderInTagSection(ast);
        }
    }

    /**
     * Checks order of atclauses in tag section node.
     *
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
     *
     * @param commentBlock child node.
     * @return parent type.
     */
    private static int getParentType(DetailAST commentBlock) {
        final DetailAST parentNode = commentBlock.getParent();
        int result = parentNode.getType();
        if (result == TokenTypes.TYPE || result == TokenTypes.MODIFIERS) {
            result = parentNode.getParent().getType();
        }
        else if (parentNode.getParent() != null
                && parentNode.getParent().getType() == TokenTypes.MODIFIERS) {
            result = parentNode.getParent().getParent().getType();
        }
        return result;
    }

}
