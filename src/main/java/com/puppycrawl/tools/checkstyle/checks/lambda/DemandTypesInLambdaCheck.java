////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
package com.puppycrawl.tools.checkstyle.checks.lambda;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks if variables in lambda expression have defined types.
 *
 * <p>
 * Rationale: Lambda expressions are easier to read when types are
 * explicitly list.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="DemandTypesInLambdaCheck"/&gt;
 * </pre>
 * @author liscju
 */
public class DemandTypesInLambdaCheck extends Check
{

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ERROR_DEMAND_TYPES_IN_LAMBDA = "lambda.demandtypes.error";

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LAMBDA};
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        final DetailAST lambdaFirstChild = ast.getFirstChild();
        if (lambdaFirstChild.getType() == TokenTypes.IDENT) {
            log(lambdaFirstChild.getLineNo(), lambdaFirstChild.getColumnNo(),
                    MSG_ERROR_DEMAND_TYPES_IN_LAMBDA);
        }
        else {
            //assert lambdaFirstChild.getType() == TokenTypes.LPAREN;
            final DetailAST lambdaParameters = ast.findFirstToken(TokenTypes.PARAMETERS);
            checkParameters(lambdaParameters);
        }
    }

    /**
     * Checks if all parameters in given parameters has defined types
     * @param lambdaParameters parameters(TokenTypes.PARAMETERS)
     */
    private void checkParameters(DetailAST lambdaParameters)
    {
        //assert lambdaParameters.getType() == TokenTypes.PARAMETERS;
        DetailAST iteratingParameter = lambdaParameters.getFirstChild();
        while (iteratingParameter != null) {
            if (iteratingParameter.getType() == TokenTypes.PARAMETER_DEF) {
                checkParameter(iteratingParameter);
            }
            iteratingParameter = iteratingParameter.getNextSibling();
        }
    }

    /**
     * Check if given lambda parameter has defined type
     * @param parameter lambda parameter(TokenTypes.PARAMETER_DEF)
     */
    private void checkParameter(DetailAST parameter)
    {
        //assert parameter.getType() == TokenTypes.PARAMETER_DEF;
        final DetailAST parameterType = parameter.findFirstToken(TokenTypes.TYPE);
        if (!isProperType(parameterType)) {
            log(parameter.getLineNo(), parameter.getColumnNo(), MSG_ERROR_DEMAND_TYPES_IN_LAMBDA);
        }
    }

    /**
     * Checks if type ast is properly defined
     * @param type DetailAST
     * @return true,if it is a valid type
     */
    private static boolean isProperType(DetailAST type)
    {
        return type != null && type.getType() == TokenTypes.TYPE && type.getChildCount() > 0;
    }
}









