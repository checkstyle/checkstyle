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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that parameters for methods, constructors, catch and for-each blocks are final.
 * Interface, abstract, and native methods are not checked: the final keyword
 * does not make sense for interface, abstract, and native method parameters as
 * there is no code that could modify the parameter.
 * </p>
 * <p>
 * Rationale: Changing the value of parameters during the execution of the method's
 * algorithm can be confusing and should be avoided. A great way to let the Java compiler
 * prevent this coding style is to declare parameters final.
 * </p>
 * <ul>
 * <li>
 * Property {@code ignorePrimitiveTypes} - Ignore primitive types as parameters.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check to enforce final parameters for methods and constructors:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalParameters&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check to enforce final parameters only for constructors:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalParameters&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;CTOR_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to allow ignoring
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
 * primitive datatypes</a> as parameters:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalParameters&quot;&gt;
 *   &lt;property name=&quot;ignorePrimitiveTypes&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 3.0
 */
@StatelessCheck
public class FinalParametersCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "final.parameter";

    /**
     * Contains
     * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">
     * primitive datatypes</a>.
     */
    private final Set<Integer> primitiveDataTypes = Collections.unmodifiableSet(
        Arrays.stream(new Integer[] {
            TokenTypes.LITERAL_BYTE,
            TokenTypes.LITERAL_SHORT,
            TokenTypes.LITERAL_INT,
            TokenTypes.LITERAL_LONG,
            TokenTypes.LITERAL_FLOAT,
            TokenTypes.LITERAL_DOUBLE,
            TokenTypes.LITERAL_BOOLEAN,
            TokenTypes.LITERAL_CHAR, })
        .collect(Collectors.toSet()));

    /**
     * Ignore primitive types as parameters.
     */
    private boolean ignorePrimitiveTypes;

    /**
     * Setter to ignore primitive types as parameters.
     * @param ignorePrimitiveTypes true or false.
     */
    public void setIgnorePrimitiveTypes(boolean ignorePrimitiveTypes) {
        this.ignorePrimitiveTypes = ignorePrimitiveTypes;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.FOR_EACH_CLAUSE,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // don't flag interfaces
        final DetailAST container = ast.getParent().getParent();
        if (container.getType() != TokenTypes.INTERFACE_DEF) {
            if (ast.getType() == TokenTypes.LITERAL_CATCH) {
                visitCatch(ast);
            }
            else if (ast.getType() == TokenTypes.FOR_EACH_CLAUSE) {
                visitForEachClause(ast);
            }
            else {
                visitMethod(ast);
            }
        }
    }

    /**
     * Checks parameters of the method or ctor.
     * @param method method or ctor to check.
     */
    private void visitMethod(final DetailAST method) {
        final DetailAST modifiers =
            method.findFirstToken(TokenTypes.MODIFIERS);

        // ignore abstract and native methods
        if (modifiers.findFirstToken(TokenTypes.ABSTRACT) == null
                && modifiers.findFirstToken(TokenTypes.LITERAL_NATIVE) == null) {
            final DetailAST parameters =
                method.findFirstToken(TokenTypes.PARAMETERS);
            DetailAST child = parameters.getFirstChild();
            while (child != null) {
                // children are PARAMETER_DEF and COMMA
                if (child.getType() == TokenTypes.PARAMETER_DEF) {
                    checkParam(child);
                }
                child = child.getNextSibling();
            }
        }
    }

    /**
     * Checks parameter of the catch block.
     * @param catchClause catch block to check.
     */
    private void visitCatch(final DetailAST catchClause) {
        checkParam(catchClause.findFirstToken(TokenTypes.PARAMETER_DEF));
    }

    /**
     * Checks parameter of the for each clause.
     * @param forEachClause for each clause to check.
     */
    private void visitForEachClause(final DetailAST forEachClause) {
        checkParam(forEachClause.findFirstToken(TokenTypes.VARIABLE_DEF));
    }

    /**
     * Checks if the given parameter is final.
     * @param param parameter to check.
     */
    private void checkParam(final DetailAST param) {
        if (param.findFirstToken(TokenTypes.MODIFIERS).findFirstToken(TokenTypes.FINAL) == null
                && !isIgnoredParam(param)
                && !CheckUtil.isReceiverParameter(param)) {
            final DetailAST paramName = param.findFirstToken(TokenTypes.IDENT);
            final DetailAST firstNode = CheckUtil.getFirstNode(param);
            log(firstNode,
                MSG_KEY, paramName.getText());
        }
    }

    /**
     * Checks for skip current param due to <b>ignorePrimitiveTypes</b> option.
     * @param paramDef {@link TokenTypes#PARAMETER_DEF PARAMETER_DEF}
     * @return true if param has to be skipped.
     */
    private boolean isIgnoredParam(DetailAST paramDef) {
        boolean result = false;
        if (ignorePrimitiveTypes) {
            final DetailAST parameterType = paramDef
                .findFirstToken(TokenTypes.TYPE).getFirstChild();
            if (primitiveDataTypes.contains(parameterType.getType())) {
                result = true;
            }
        }
        return result;
    }

}
