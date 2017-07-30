////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This Check highlights variable definition statements where <a href=
 * "http://docs.oracle.com/javase/7/docs/technotes/guides/language/type-inference-generic-instance-creation.html">
 * diamond operator</a> could be used.<br>
 * <b>Rationale</b>: using diamond operator (introduced in Java 1.7) leads to shorter code<br>
 * and better code readability. It is suggested by Oracle that the diamond primarily using<br>
 * for variable declarations.<br><br>
 * E.g. of statements:
 *
 * <p><b>Without diamond operator:</b><br>
 * {@code
 * Map&lt;String, Map&lt;String, Integer&gt;&gt; someMap =
 *     new HashMap&lt;String, Map&lt;String, Integer&gt;&gt;();
 * }<br>
 * <b>With diamond operator:</b><br>
 * {@code
 * Map&lt;String, Map&lt;String, Integer&gt;&gt; someMap = new HashMap&lt;&gt;();
 * }</p>
 *
 * @author AustinShalit
 */
public class DiamondOperatorForVariableDefinitionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "diamond.operator.for.variable.definition";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.LITERAL_NEW};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST newNode) {

        if (newNode.branchContains(TokenTypes.TYPE_ARGUMENT)
            && !newNode.branchContains(TokenTypes.WILDCARD_TYPE)
            && !newNode.branchContains(TokenTypes.OBJBLOCK)) {

            log(newNode, MSG_KEY);
        }
    }
}
