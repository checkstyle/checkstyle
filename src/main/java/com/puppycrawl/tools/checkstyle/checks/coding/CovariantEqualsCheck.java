////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Set;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;

/**
 * <p>Checks that if a class defines a covariant method equals,
 * then it defines method equals(java.lang.Object).
 * Inspired by findbugs,
 * http://findbugs.sourceforge.net/bugDescriptions.html#EQ_SELF_NO_OBJECT
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="CovariantEquals"/&gt;
 * </pre>
 * @author Rick Giles
 */
public class CovariantEqualsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "covariant.equals";

    /** Set of equals method definitions. */
    private final Set<DetailAST> equalsMethods = Sets.newHashSet();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.LITERAL_NEW, TokenTypes.ENUM_DEF, };
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.LITERAL_NEW, TokenTypes.ENUM_DEF, };
    }

    @Override
    public void visitToken(DetailAST ast) {
        equalsMethods.clear();

        // examine method definitions for equals methods
        final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            DetailAST child = objBlock.getFirstChild();
            boolean hasEqualsObject = false;
            while (child != null) {
                if (child.getType() == TokenTypes.METHOD_DEF
                        && CheckUtils.isEqualsMethod(child)) {
                    if (isFirstParameterObject(child)) {
                        hasEqualsObject = true;
                    }
                    else {
                        equalsMethods.add(child);
                    }
                }
                child = child.getNextSibling();
            }

            // report equals method definitions
            if (!hasEqualsObject) {
                for (DetailAST equalsAST : equalsMethods) {
                    final DetailAST nameNode = equalsAST
                            .findFirstToken(TokenTypes.IDENT);
                    log(nameNode.getLineNo(), nameNode.getColumnNo(),
                            MSG_KEY);
                }
            }
        }
    }

    /**
     * Tests whether a method's first parameter is an Object.
     * @param methodDefAst the method definition AST to test.
     *     Precondition: ast is a TokenTypes.METHOD_DEF node.
     * @return true if ast has first parameter of type Object.
     */
    private static boolean isFirstParameterObject(DetailAST methodDefAst) {
        final DetailAST paramsNode = methodDefAst.findFirstToken(TokenTypes.PARAMETERS);

        // parameter type "Object"?
        final DetailAST paramNode =
            paramsNode.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST typeNode = paramNode.findFirstToken(TokenTypes.TYPE);
        final FullIdent fullIdent = FullIdent.createFullIdentBelow(typeNode);
        final String name = fullIdent.getText();
        return "Object".equals(name) || "java.lang.Object".equals(name);
    }
}
