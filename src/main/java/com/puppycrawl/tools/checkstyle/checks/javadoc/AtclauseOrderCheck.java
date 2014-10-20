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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks the order of at-clauses.
 * </p>
 *
 * <p>
 * The check allows to configure itself by using the following properties:
 * </p>
 * <ul>
 * <li>
 * target - allows to specify targets to check at-clauses.
 * </li>
 * <li>
 * tagOrder - allows to specify the order by tags.
 * </li>
 * </ul>
 * <p>
 * Default configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;AtclauseOrderCheck&quot;&gt;
 *     &lt;property name=&quot;tagOrder&quot; value=&quot;&#64;author, &#64;version, &#64;param,
 *     &#64;return, &#64;throws, &#64;exception, &#64;see, &#64;since, &#64;serial,
 *     &#64;serialField, &#64;serialData, &#64;deprecated&quot;/&gt;
 *     &lt;property name=&quot;target&quot; value=&quot;CLASS_DEF, INTERFACE_DEF, ENUM_DEF,
 *     METHOD_DEF, CTOR_DEF, VARIABLE_DEF&quot;/&gt;
 * &lt;/module>
 * </pre>
 *
 * @author max
 *
 */
public class AtclauseOrderCheck extends AbstractJavadocCheck
{

    /**
     * Some javadoc.
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
     * Some javadoc.
     */
    private List<Integer> mTarget = Arrays.asList(
        TokenTypes.CLASS_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.ENUM_DEF,
        TokenTypes.METHOD_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.VARIABLE_DEF
    );

    /**
     * Some javadoc.
     */
    private List<String> mTagOrder = Arrays.asList(DEFAULT_ORDER);

    /**
     * Some javadoc.
     * @param aTarget Some javadoc.
     */
    public void setTarget(String aTarget)
    {
        final List<Integer> customTarget = new ArrayList<Integer>();
        for (String type : aTarget.split(", ")) {
            customTarget.add(TokenTypes.getTokenId(type));
        }
        mTarget = customTarget;
    }

    /**
     * Some javadoc.
     * @param aOrder Some javadoc.
     */
    public void setTagOrder(String aOrder)
    {
        final List<String> customOrder = new ArrayList<String>();
        for (String type : aOrder.split(", ")) {
            customOrder.add(type);
        }
        mTagOrder = customOrder;
    }

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
        final int parentType = getParentType(getBlockCommentAst());

        if (mTarget.contains(parentType)) {
            checkOrderInTagSection(aAst);
        }
    }

    /**
     * Some javadoc.
     * @param aJavadoc Some javadoc.
     */
    private void checkOrderInTagSection(DetailNode aJavadoc)
    {
        int indexOrderOfPreviousTag = 0;
        int indexOrderOfCurrentTag = 0;

        for (DetailNode node : aJavadoc.getChildren()) {
            if (node.getType() == JavadocTokenTypes.JAVADOC_TAG) {
                final String tagText = JavadocUtils.getFirstChild(node).getText();
                indexOrderOfCurrentTag = mTagOrder.indexOf(tagText);

                if (mTagOrder.contains(tagText)
                        && indexOrderOfCurrentTag < indexOrderOfPreviousTag)
                {
                    log(node.getLineNumber(), "at.clause.order", mTagOrder.toString());
                }
                indexOrderOfPreviousTag = indexOrderOfCurrentTag;
            }
        }
    }

    /**
     * Some javadoc.
     * @param aCommentBlock Some javadoc.
     * @return Some javadoc.
     */
    private int getParentType(DetailAST aCommentBlock)
    {
        final DetailAST parentNode = aCommentBlock.getParent();
        int type = parentNode.getType();
        if (type == TokenTypes.TYPE || type == TokenTypes.MODIFIERS) {
            type = parentNode.getParent().getType();
        }
        return type;
    }
}
