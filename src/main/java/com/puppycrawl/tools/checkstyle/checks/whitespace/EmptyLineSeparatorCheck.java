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
 * Checks for blank line separators after package, all import declarations,
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
 * Example of declarations without blank line separator:
 * </p>
 *
 * <pre>
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
 * Example of declarations with blank line separator
 * that is expected by the Check by default:
 * </p>
 *
 * <pre>
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
 * <p> An example how to check blank line after
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
 * @author maxvetrenko
 *
 */
public class EmptyLineSeparatorCheck extends Check
{

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
                if (isTypeField(aAST) && !hasBlankLineAfter(aAST)) {
                    log(nextToken.getLineNo(),
                            "empty.line.separator", nextToken.getText());
                }
                break;
            case TokenTypes.IMPORT:
                if (astType != nextToken.getType()
                    && !hasBlankLineAfter(aAST))
                {
                    log(nextToken.getLineNo(),
                            "empty.line.separator", nextToken.getText());
                }
                break;
            default:
                if (!hasBlankLineAfter(aAST)) {
                    log(nextToken.getLineNo(),
                            "empty.line.separator", nextToken.getText());
                }
            }
        }
    }

    /**
     * Checks if token have blank line after.
     * @param aToken token.
     * @return if token have blank line after.
     */
    private boolean hasBlankLineAfter(DetailAST aToken)
    {
        DetailAST lastToken = aToken.getLastChild().getLastChild();
        if (null == lastToken) {
            lastToken = aToken.getLastChild();
        }
        return aToken.getNextSibling().getLineNo() - lastToken.getLineNo() > 1;
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
