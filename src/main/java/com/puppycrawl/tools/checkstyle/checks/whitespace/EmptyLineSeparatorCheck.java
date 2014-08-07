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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 *
 * Checks for empty line separators after header, package, all import declarations,
 * fields, constructors, methods, nested classes,
 * static initializers and instance initializers.
 *
 * <p> By default the check will check the following statements:
 *  {@link TokenTypes#PACKAGE_DEF PACKAGE_DEF},
 *  {@link TokenTypes#IMPORT IMPORT},
 *  {@link TokenTypes#CLASS_DEF CLASS_DEF},
 *  {@link TokenTypes#INTERFACE_DEF INTERFACE_DEF},
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT},
 *  {@link TokenTypes#INSTANCE_INIT INSTANCE_INIT},
 *  {@link TokenTypes#METHOD_DEF METHOD_DEF},
 *  {@link TokenTypes#CTOR_DEF CTOR_DEF},
 *  {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF}.
 * </p>
 *
 * <p>
 * Example of declarations without empty line separator:
 * </p>
 *
 * <pre>
 * ///////////////////////////////////////////////////
 * //HEADER
 * ///////////////////////////////////////////////////
 * package com.puppycrawl.tools.checkstyle.whitespace;
 * import java.io.Serializable;
 * class Foo
 * {
 *     public static final int FOO_CONST = 1;
 *     public void foo() {} //should be separated from previous statement.
 * }
 * </pre>
 *
 * <p> An example of how to configure the check with default parameters is:
 * </p>
 *
 * <pre>
 * &lt;module name="EmptyLineSeparator"/&gt;
 * </pre>
 *
 * <p>
 * Example of declarations with empty line separator
 * that is expected by the Check by default:
 * </p>
 *
 * <pre>
 * ///////////////////////////////////////////////////
 * //HEADER
 * ///////////////////////////////////////////////////
 *
 * package com.puppycrawl.tools.checkstyle.whitespace;
 *
 * import java.io.Serializable;
 *
 * class Foo
 * {
 *     public static final int FOO_CONST = 1;
 *
 *     public void foo() {}
 * }
 * </pre>
 * <p> An example how to check empty line after
 * {@link TokenTypes#VARIABLE_DEF VARIABLE_DEF} and
 * {@link TokenTypes#METHOD_DEF METHOD_DEF}:
 * </p>
 *
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *    &lt;property name="tokens" value="VARIABLE_DEF, METHOD_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * An example how to allow no empty line between fields:
 * </p>
 * <pre>
 * &lt;module name="EmptyLineSeparator"&gt;
 *    &lt;property name="allowNoEmptyLineBetweenFields" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author maxvetrenko
 *
 */
public class EmptyLineSeparatorCheck extends Check
{
    /** */
    private boolean mAllowNoEmptyLineBetweenFields;

    /**
     * Allow no empty line between fields.
     * @param aAllow
     *        User's value.
     */
    public final void setAllowNoEmptyLineBetweenFields(boolean aAllow)
    {
        mAllowNoEmptyLineBetweenFields = aAllow;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST nextToken = aAST.getNextSibling();

        if (nextToken != null && nextToken.getType() != TokenTypes.RCURLY) {
            final int astType = aAST.getType();
            switch (astType) {
            case TokenTypes.VARIABLE_DEF:
                if (isTypeField(aAST) && !hasEmptyLineAfter(aAST)) {
                    if (mAllowNoEmptyLineBetweenFields
                            && nextToken.getType() != TokenTypes.VARIABLE_DEF)
                    {
                        log(nextToken.getLineNo(), "empty.line.separator", nextToken.getText());
                    }
                    else if (!mAllowNoEmptyLineBetweenFields) {
                        log(nextToken.getLineNo(), "empty.line.separator", nextToken.getText());
                    }
                }
                break;
            case TokenTypes.IMPORT:
                if (astType != nextToken.getType() && !hasEmptyLineAfter(aAST)
                    || (aAST.getLineNo() > 1 && !hasEmptyLineBefore(aAST)
                            && aAST.getPreviousSibling() == null))
                {
                    log(nextToken.getLineNo(), "empty.line.separator", nextToken.getText());
                }
                break;
            case TokenTypes.PACKAGE_DEF:
                if (aAST.getLineNo() > 1 && !hasEmptyLineBefore(aAST)) {
                    log(aAST.getLineNo(), "empty.line.separator", aAST.getText());
                }
            default:
                if (!hasEmptyLineAfter(aAST)) {
                    log(nextToken.getLineNo(), "empty.line.separator", nextToken.getText());
                }
            }
        }
    }

    /**
     * Checks if token have empty line after.
     * @param aToken token.
     * @return true if token have empty line after.
     */
    private boolean hasEmptyLineAfter(DetailAST aToken)
    {
        DetailAST lastToken = aToken.getLastChild().getLastChild();
        if (null == lastToken) {
            lastToken = aToken.getLastChild();
        }
        return aToken.getNextSibling().getLineNo() - lastToken.getLineNo() > 1;
    }

    /**
     * Checks if a token has a empty line before.
     * @param aToken token.
     * @return true, if token have empty line before.
     */
    private boolean hasEmptyLineBefore(DetailAST aToken)
    {
        final int lineNo = aToken.getLineNo();
        //  [lineNo - 2] is the number of the previous line because the numbering starts from zero.
        final String lineBefore = getLines()[lineNo - 2];
        return lineBefore.trim().isEmpty();
    }

    /**
     * If variable definition is a type field.
     * @param aVariableDef variable definition.
     * @return true variable definition is a type field.
     */
    private boolean isTypeField(DetailAST aVariableDef)
    {
        final int parentType = aVariableDef.getParent().getParent().getType();
        return parentType == TokenTypes.CLASS_DEF;
    }
}
