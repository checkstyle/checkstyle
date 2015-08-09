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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import antlr.collections.ASTEnumeration;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks the distance between declaration of variable and its first usage.
 * </p>
 * Example #1:
 * <pre>
 *      <code>int count;
 *      a = a + b;
 *      b = a + a;
 *      count = b; // DECLARATION OF VARIABLE 'count'
 *                 // SHOULD BE HERE (distance = 3)</code>
 * </pre>
 * Example #2:
 * <pre>
 *     <code>int count;
 *     {
 *         a = a + b;
 *         count = b; // DECLARATION OF VARIABLE 'count'
 *                    // SHOULD BE HERE (distance = 2)
 *     }</code>
 * </pre>
 *
 * <p>
 * Check can detect a block of initialization methods. If a variable is used in
 * such a block and there is no other statements after this variable then distance=1.
 * </p>
 * <p>
 * <b>Case #1:</b>
 * <pre>
 * int <b>minutes</b> = 5;
 * Calendar cal = Calendar.getInstance();
 * cal.setTimeInMillis(timeNow);
 * cal.set(Calendar.SECOND, 0);
 * cal.set(Calendar.MILLISECOND, 0);
 * cal.set(Calendar.HOUR_OF_DAY, hh);
 * cal.set(Calendar.MINUTE, <b>minutes</b>);
 *
 * The distance for the variable <b>minutes</b> is 1 even
 * though this variable is used in the fifth method's call.
 * </pre>
 *
 * <p>
 * <b>Case #2:</b>
 * <pre>
 * int <b>minutes</b> = 5;
 * Calendar cal = Calendar.getInstance();
 * cal.setTimeInMillis(timeNow);
 * cal.set(Calendar.SECOND, 0);
 * cal.set(Calendar.MILLISECOND, 0);
 * <i>System.out.println(cal);</i>
 * cal.set(Calendar.HOUR_OF_DAY, hh);
 * cal.set(Calendar.MINUTE, <b>minutes</b>);
 *
 * The distance for the variable <b>minutes</b> is 6 because there is one more expression
 * (except the initialization block) between the declaration of this variable and its usage.
 * </pre>
 *
 *
 * There are several additional options to configure the check:
 * <pre>
 * 1. allowedDistance - allows to set a distance
 * between declaration of variable and its first usage.
 * 2. ignoreVariablePattern - allows to set a RegEx pattern for
 * ignoring the distance calculation for variables listed in this pattern.
 * 3. validateBetweenScopes - allows to calculate the distance between
 * declaration of variable and its first usage in the different scopes.
 * 4. ignoreFinal - allows to ignore variables with a 'final' modifier.
 * </pre>
 * ATTENTION!! (Not supported cases)
 * <pre>
 * Case #1:
 * <code>{
 * int c;
 * int a = 3;
 * int b = 2;
 *     {
 *     a = a + b;
 *     c = b;
 *     }
 * }</code>
 *
 * Distance for variable 'a' = 1;
 * Distance for variable 'b' = 1;
 * Distance for variable 'c' = 2.
 * </pre>
 * As distance by default is 1 the Check doesn't raise warning for variables 'a'
 * and 'b' to move them into the block.
 * <pre>
 * Case #2:
 * <code>int sum = 0;
 * for (int i = 0; i &lt; 20; i++) {
 *     a++;
 *     b--;
 *     sum++;
 *     if (sum &gt; 10) {
 *         res = true;
 *     }
 * }</code>
 * Distance for variable 'sum' = 3.
 * </pre>
 * <p>
 * As the distance is more then the default one, the Check raises warning for variable
 * 'sum' to move it into the 'for(...)' block. But there is situation when
 * variable 'sum' hasn't to be 0 within each iteration. So, to avoid such
 * warnings you can use Suppression Filter, provided by Checkstyle, for the
 * whole class.
 * </p>
 *
 * <p>
 * An example how to configure this Check:
 * </p>
 * <pre>
 * &lt;module name="VariableDeclarationUsageDistance"/&gt;
 * </pre>
 * <p>
 * An example of how to configure this Check:
 *  - to set the allowed distance to 4;
 *  - to ignore variables with prefix '^temp';
 *  - to force the validation between scopes;
 *  - to check the final variables;
 * </p>
 * <pre>
 * &lt;module name="VariableDeclarationUsageDistance"&gt;
 *     &lt;property name="allowedDistance" value="4"&gt;
 *     &lt;property name="ignoreVariablePattern" value="^temp.*"&gt;
 *     &lt;property name="validateBetweenScopes" value="true"&gt;
 *     &lt;property name="ignoreFinal" value="false"&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author <a href="mailto:rd.ryly@gmail.com">Ruslan Diachenko</a>
 * @author <a href="mailto:barataliba@gmail.com">Baratali Izmailov</a>
 */
public class VariableDeclarationUsageDistanceCheck extends Check {
    /**
     * Warning message key.
     */
    public static final String MSG_KEY = "variable.declaration.usage.distance";

    /**
     * Warning message key.
     */
    public static final String MSG_KEY_EXT = "variable.declaration.usage.distance.extend";

    /**
     * Default value of distance between declaration of variable and its first
     * usage.
     */
    private static final int DEFAULT_DISTANCE = 3;

    /** Allowed distance between declaration of variable and its first usage. */
    private int allowedDistance = DEFAULT_DISTANCE;

    /**
     * RegExp pattern to ignore distance calculation for variables listed in
     * this pattern.
     */
    private Pattern ignoreVariablePattern = Pattern.compile("");

    /**
     * Allows to calculate distance between declaration of variable and its
     * first usage in different scopes.
     */
    private boolean validateBetweenScopes;

    /** Allows to ignore variables with 'final' modifier. */
    private boolean ignoreFinal = true;

    /**
     * Sets an allowed distance between declaration of variable and its first
     * usage.
     * @param allowedDistance
     *        Allowed distance between declaration of variable and its first
     *        usage.
     */
    public void setAllowedDistance(int allowedDistance) {
        this.allowedDistance = allowedDistance;
    }

    /**
     * Sets RegExp pattern to ignore distance calculation for variables listed in this pattern.
     * @param ignorePattern
     *        Pattern contains ignored variables.
     * @throws org.apache.commons.beanutils.ConversionException
     *         if unable to create Pattern object.
     */
    public void setIgnoreVariablePattern(String ignorePattern) {
        ignoreVariablePattern = Utils.createPattern(ignorePattern);
    }

    /**
     * Sets option which allows to calculate distance between declaration of
     * variable and its first usage in different scopes.
     * @param validateBetweenScopes
     *        Defines if allow to calculate distance between declaration of
     *        variable and its first usage in different scopes or not.
     */
    public void setValidateBetweenScopes(boolean validateBetweenScopes) {
        this.validateBetweenScopes = validateBetweenScopes;
    }

    /**
     * Sets ignore option for variables with 'final' modifier.
     * @param ignoreFinal
     *        Defines if ignore variables with 'final' modifier or not.
     */
    public void setIgnoreFinal(boolean ignoreFinal) {
        this.ignoreFinal = ignoreFinal;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();
        final DetailAST modifiers = ast.getFirstChild();

        if (!(ignoreFinal && modifiers.branchContains(TokenTypes.FINAL)
                || parentType == TokenTypes.OBJBLOCK)) {
            final DetailAST variable = ast.findFirstToken(TokenTypes.IDENT);

            if (!isVariableMatchesIgnorePattern(variable.getText())) {
                final DetailAST semicolonAst = ast.getNextSibling();
                Entry<DetailAST, Integer> entry;
                if (validateBetweenScopes) {
                    entry = calculateDistanceBetweenScopes(semicolonAst, variable);
                }
                else {
                    entry = calculateDistanceInSingleScope(semicolonAst, variable);
                }
                final DetailAST variableUsageAst = entry.getKey();
                final int dist = entry.getValue();
                if (dist > allowedDistance
                        && !isInitializationSequence(variableUsageAst, variable.getText())) {
                    if (ignoreFinal) {
                        log(variable.getLineNo(),
                                MSG_KEY_EXT, variable.getText(), dist, allowedDistance);
                    }
                    else {
                        log(variable.getLineNo(),
                                MSG_KEY, variable.getText(), dist, allowedDistance);
                    }
                }
            }
        }
    }

    /**
     * Get name of instance whose method is called.
     * @param methodCallAst
     *        DetailAST of METHOD_CALL.
     * @return name of instance.
     */
    private static String getInstanceName(DetailAST methodCallAst) {
        final String methodCallName =
                FullIdent.createFullIdentBelow(methodCallAst).getText();
        final int lastDotIndex = methodCallName.lastIndexOf('.');
        String instanceName = "";
        if (lastDotIndex != -1) {
            instanceName = methodCallName.substring(0, lastDotIndex);
        }
        return instanceName;
    }

    /**
     * Processes statements until usage of variable to detect sequence of
     * initialization methods.
     * @param variableUsageAst
     *        DetailAST of expression that uses variable named variableName.
     * @param variableName
     *        name of considered variable.
     * @return true if statements between declaration and usage of variable are
     *         initialization methods.
     */
    private static boolean isInitializationSequence(
            DetailAST variableUsageAst, String variableName) {
        boolean result = true;
        boolean isUsedVariableDeclarationFound = false;
        DetailAST currentSiblingAst = variableUsageAst;
        String initInstanceName = "";

        while (result
                && !isUsedVariableDeclarationFound
                && currentSiblingAst != null) {

            switch (currentSiblingAst.getType()) {

                case TokenTypes.EXPR:
                    final DetailAST methodCallAst = currentSiblingAst.getFirstChild();

                    if (methodCallAst != null
                        && methodCallAst.getType() == TokenTypes.METHOD_CALL) {
                        final String instanceName =
                            getInstanceName(methodCallAst);
                        // method is called without instance
                        if (instanceName.isEmpty()) {
                            result = false;
                        }
                        // differs from previous instance
                        else if (!instanceName.equals(initInstanceName)) {
                            if (!initInstanceName.isEmpty()) {
                                result = false;
                            }
                            else {
                                initInstanceName = instanceName;
                            }
                        }
                    }
                    else { // is not method call
                        result = false;
                    }
                    break;

                case TokenTypes.VARIABLE_DEF:
                    final String currentVariableName = currentSiblingAst
                        .findFirstToken(TokenTypes.IDENT).getText();
                    isUsedVariableDeclarationFound = variableName.equals(currentVariableName);
                    break;

                case TokenTypes.SEMI:
                    break;

                default:
                    result = false;
            }

            currentSiblingAst = currentSiblingAst.getPreviousSibling();
        }

        return result;
    }

    /**
     * Calculates distance between declaration of variable and its first usage
     * in single scope.
     * @param semicolonAst
     *        Regular node of Ast which is checked for content of checking
     *        variable.
     * @param variableIdentAst
     *        Variable which distance is calculated for.
     * @return entry which contains expression with variable usage and distance.
     */
    private static Entry<DetailAST, Integer> calculateDistanceInSingleScope(
            DetailAST semicolonAst, DetailAST variableIdentAst) {
        int dist = 0;
        boolean firstUsageFound = false;
        DetailAST currentAst = semicolonAst;
        DetailAST variableUsageAst = null;

        while (!firstUsageFound && currentAst != null
                && currentAst.getType() != TokenTypes.RCURLY) {
            if (currentAst.getFirstChild() != null) {

                if (isChild(currentAst, variableIdentAst)) {

                    switch (currentAst.getType()) {
                        case TokenTypes.VARIABLE_DEF:
                            dist++;
                            break;
                        case TokenTypes.SLIST:
                            dist = 0;
                            break;
                        case TokenTypes.LITERAL_FOR:
                        case TokenTypes.LITERAL_WHILE:
                        case TokenTypes.LITERAL_DO:
                        case TokenTypes.LITERAL_IF:
                        case TokenTypes.LITERAL_SWITCH:
                            if (isVariableInOperatorExpr(currentAst, variableIdentAst)) {
                                dist++;
                            }
                            else { // variable usage is in inner scope
                                // reset counters, because we can't determine distance
                                dist = 0;
                            }
                            break;
                        default:
                            if (currentAst.branchContains(TokenTypes.SLIST)) {
                                dist = 0;
                            }
                            else {
                                dist++;
                            }
                    }
                    variableUsageAst = currentAst;
                    firstUsageFound = true;
                }
                else if (currentAst.getType() != TokenTypes.VARIABLE_DEF) {
                    dist++;
                }
            }
            currentAst = currentAst.getNextSibling();
        }

        // If variable wasn't used after its declaration, distance is 0.
        if (!firstUsageFound) {
            dist = 0;
        }

        return new SimpleEntry<>(variableUsageAst, dist);
    }

    /**
     * Calculates distance between declaration of variable and its first usage
     * in multiple scopes.
     * @param ast
     *        Regular node of Ast which is checked for content of checking
     *        variable.
     * @param variable
     *        Variable which distance is calculated for.
     * @return entry which contains expression with variable usage and distance.
     */
    private static Entry<DetailAST, Integer> calculateDistanceBetweenScopes(
            DetailAST ast, DetailAST variable) {
        int dist = 0;
        DetailAST currentScopeAst = ast;
        DetailAST variableUsageAst = null;
        while (currentScopeAst != null) {
            final List<DetailAST> variableUsageExpressions = new ArrayList<>();
            DetailAST currentStatementAst = currentScopeAst;
            currentScopeAst = null;
            while (currentStatementAst != null
                    && currentStatementAst.getType() != TokenTypes.RCURLY) {
                if (currentStatementAst.getFirstChild() != null) {
                    if (isChild(currentStatementAst, variable)) {
                        variableUsageExpressions.add(currentStatementAst);
                    }
                    // If expression doesn't contain variable and this variable
                    // hasn't been met yet, than distance + 1.
                    else if (variableUsageExpressions.isEmpty()
                            && currentStatementAst.getType() != TokenTypes.VARIABLE_DEF) {
                        dist++;
                    }
                }
                currentStatementAst = currentStatementAst.getNextSibling();
            }
            // If variable usage exists in a single scope, then look into
            // this scope and count distance until variable usage.
            if (variableUsageExpressions.size() == 1) {
                final DetailAST blockWithVariableUsage = variableUsageExpressions
                        .get(0);
                DetailAST exprWithVariableUsage = null;
                switch (blockWithVariableUsage.getType()) {
                    case TokenTypes.VARIABLE_DEF:
                    case TokenTypes.EXPR:
                        dist++;
                        break;
                    case TokenTypes.LITERAL_FOR:
                    case TokenTypes.LITERAL_WHILE:
                    case TokenTypes.LITERAL_DO:
                        exprWithVariableUsage = getFirstNodeInsideForWhileDoWhileBlocks(
                            blockWithVariableUsage, variable);
                        break;
                    case TokenTypes.LITERAL_IF:
                        exprWithVariableUsage = getFirstNodeInsideIfBlock(
                            blockWithVariableUsage, variable);
                        break;
                    case TokenTypes.LITERAL_SWITCH:
                        exprWithVariableUsage = getFirstNodeInsideSwitchBlock(
                            blockWithVariableUsage, variable);
                        break;
                    case TokenTypes.LITERAL_TRY:
                        exprWithVariableUsage =
                            getFirstNodeInsideTryCatchFinallyBlocks(blockWithVariableUsage,
                                variable);
                        break;
                    default:
                        exprWithVariableUsage = blockWithVariableUsage.getFirstChild();
                }
                currentScopeAst = exprWithVariableUsage;
                if (exprWithVariableUsage != null) {
                    variableUsageAst = exprWithVariableUsage;
                }
                else {
                    variableUsageAst = blockWithVariableUsage;
                }
            }
            // If variable usage exists in different scopes, then distance =
            // distance until variable first usage.
            else if (variableUsageExpressions.size() > 1) {
                dist++;
                variableUsageAst = variableUsageExpressions.get(0);
            }
            // If there's no any variable usage, then distance = 0.
            else {
                variableUsageAst = null;
            }
        }
        return new SimpleEntry<>(variableUsageAst, dist);
    }

    /**
     * Gets first Ast node inside FOR, WHILE or DO-WHILE blocks if variable
     * usage is met only inside the block (not in its declaration!).
     * @param block
     *        Ast node represents FOR, WHILE or DO-WHILE block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) than return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideForWhileDoWhileBlocks(
            DetailAST block, DetailAST variable) {
        DetailAST firstNodeInsideBlock = null;

        if (!isVariableInOperatorExpr(block, variable)) {
            DetailAST currentNode;

            // Find currentNode for DO-WHILE block.
            if (block.getType() == TokenTypes.LITERAL_DO) {
                currentNode = block.getFirstChild();
            }
            // Find currentNode for FOR or WHILE block.
            else {
                // Looking for RPAREN ( ')' ) token to mark the end of operator
                // expression.
                currentNode = block.findFirstToken(TokenTypes.RPAREN);
                if (currentNode != null) {
                    currentNode = currentNode.getNextSibling();
                }
            }

            if (currentNode != null) {
                final int currentNodeType = currentNode.getType();

                if (currentNodeType == TokenTypes.SLIST) {
                    firstNodeInsideBlock = currentNode.getFirstChild();
                }
                else if (currentNodeType != TokenTypes.VARIABLE_DEF
                        && currentNodeType != TokenTypes.EXPR) {
                    firstNodeInsideBlock = currentNode;
                }
            }
        }

        return firstNodeInsideBlock;
    }

    /**
     * Gets first Ast node inside IF block if variable usage is met
     * only inside the block (not in its declaration!).
     * @param block
     *        Ast node represents IF block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) than return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideIfBlock(
            DetailAST block, DetailAST variable) {
        DetailAST firstNodeInsideBlock = null;

        if (!isVariableInOperatorExpr(block, variable)) {
            DetailAST currentNode = block.getLastChild();
            final List<DetailAST> variableUsageExpressions =
                    new ArrayList<>();

            while (currentNode != null
                    && currentNode.getType() == TokenTypes.LITERAL_ELSE) {
                final DetailAST previousNode =
                        currentNode.getPreviousSibling();

                // Checking variable usage inside IF block.
                if (isChild(previousNode, variable)) {
                    variableUsageExpressions.add(previousNode);
                }

                // Looking into ELSE block, get its first child and analyze it.
                currentNode = currentNode.getFirstChild();

                if (currentNode.getType() == TokenTypes.LITERAL_IF) {
                    currentNode = currentNode.getLastChild();
                }
                else if (isChild(currentNode, variable)) {
                    variableUsageExpressions.add(currentNode);
                    currentNode = null;
                }
            }

            // If IF block doesn't include ELSE than analyze variable usage
            // only inside IF block.
            if (currentNode != null
                    && isChild(currentNode, variable)) {
                variableUsageExpressions.add(currentNode);
            }

            // If variable usage exists in several related blocks, then
            // firstNodeInsideBlock = null, otherwise if variable usage exists
            // only inside one block, then get node from
            // variableUsageExpressions.
            if (variableUsageExpressions.size() == 1) {
                firstNodeInsideBlock = variableUsageExpressions.get(0);
            }
        }

        return firstNodeInsideBlock;
    }

    /**
     * Gets first Ast node inside SWITCH block if variable usage is met
     * only inside the block (not in its declaration!).
     * @param block
     *        Ast node represents SWITCH block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) than return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideSwitchBlock(
            DetailAST block, DetailAST variable) {
        DetailAST firstNodeInsideBlock = null;

        if (!isVariableInOperatorExpr(block, variable)) {
            DetailAST currentNode = block
                    .findFirstToken(TokenTypes.CASE_GROUP);
            final List<DetailAST> variableUsageExpressions =
                    new ArrayList<>();

            // Checking variable usage inside all CASE blocks.
            while (currentNode != null
                    && currentNode.getType() == TokenTypes.CASE_GROUP) {
                final DetailAST lastNodeInCaseGroup =
                        currentNode.getLastChild();

                if (isChild(lastNodeInCaseGroup, variable)) {
                    variableUsageExpressions.add(lastNodeInCaseGroup);
                }
                currentNode = currentNode.getNextSibling();
            }

            // If variable usage exists in several related blocks, then
            // firstNodeInsideBlock = null, otherwise if variable usage exists
            // only inside one block, then get node from
            // variableUsageExpressions.
            if (variableUsageExpressions.size() == 1) {
                firstNodeInsideBlock = variableUsageExpressions.get(0);
            }
        }

        return firstNodeInsideBlock;
    }

    /**
     * Gets first Ast node inside TRY-CATCH-FINALLY blocks if variable usage is
     * met only inside the block (not in its declaration!).
     * @param block
     *        Ast node represents TRY-CATCH-FINALLY block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) than return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideTryCatchFinallyBlocks(
            DetailAST block, DetailAST variable) {
        DetailAST currentNode = block.getFirstChild();
        final List<DetailAST> variableUsageExpressions =
                new ArrayList<>();

        // Checking variable usage inside TRY block.
        if (isChild(currentNode, variable)) {
            variableUsageExpressions.add(currentNode);
        }

        // Switch on CATCH block.
        currentNode = currentNode.getNextSibling();

        // Checking variable usage inside all CATCH blocks.
        while (currentNode != null
                && currentNode.getType() == TokenTypes.LITERAL_CATCH) {
            final DetailAST catchBlock = currentNode.getLastChild();

            if (isChild(catchBlock, variable)) {
                variableUsageExpressions.add(catchBlock);
            }
            currentNode = currentNode.getNextSibling();
        }

        // Checking variable usage inside FINALLY block.
        if (currentNode != null) {
            final DetailAST finalBlock = currentNode.getLastChild();

            if (isChild(finalBlock, variable)) {
                variableUsageExpressions.add(finalBlock);
            }
        }

        DetailAST variableUsageNode = null;

        // If variable usage exists in several related blocks, then
        // firstNodeInsideBlock = null, otherwise if variable usage exists
        // only inside one block, then get node from
        // variableUsageExpressions.
        if (variableUsageExpressions.size() == 1) {
            variableUsageNode = variableUsageExpressions.get(0).getFirstChild();
        }

        return variableUsageNode;
    }

    /**
     * Checks if variable is in operator declaration. For instance:
     * <pre>
     * boolean b = true;
     * if (b) {...}
     * </pre>
     * Variable 'b' is in declaration of operator IF.
     * @param operator
     *        Ast node which represents operator.
     * @param variable
     *        Variable which is checked for content in operator.
     * @return true if operator contains variable in its declaration, otherwise
     *         - false.
     */
    private static boolean isVariableInOperatorExpr(
            DetailAST operator, DetailAST variable) {
        boolean isVarInOperatorDeclr = false;
        final DetailAST openingBracket =
                operator.findFirstToken(TokenTypes.LPAREN);

        if (openingBracket != null) {
            // Get EXPR between brackets
            DetailAST exprBetweenBrackets = openingBracket
                    .getNextSibling();

            // Look if variable is in operator expression
            while (exprBetweenBrackets.getType() != TokenTypes.RPAREN) {

                if (isChild(exprBetweenBrackets, variable)) {
                    isVarInOperatorDeclr = true;
                    break;
                }
                exprBetweenBrackets = exprBetweenBrackets.getNextSibling();
            }

            // Variable may be met in ELSE declaration or in CASE declaration.
            // So, check variable usage in these declarations.
            if (!isVarInOperatorDeclr) {
                switch (operator.getType()) {
                    case TokenTypes.LITERAL_IF:
                        final DetailAST elseBlock = operator.getLastChild();

                        if (elseBlock.getType() == TokenTypes.LITERAL_ELSE) {
                            // Get IF followed by ELSE
                            final DetailAST firstNodeInsideElseBlock = elseBlock
                                .getFirstChild();

                            if (firstNodeInsideElseBlock.getType() == TokenTypes.LITERAL_IF) {
                                isVarInOperatorDeclr |=
                                    isVariableInOperatorExpr(
                                        firstNodeInsideElseBlock,
                                            variable);
                            }
                        }
                        break;

                    case TokenTypes.LITERAL_SWITCH:
                        DetailAST currentCaseBlock = operator
                            .findFirstToken(TokenTypes.CASE_GROUP);

                        while (currentCaseBlock != null
                            && currentCaseBlock.getType() == TokenTypes.CASE_GROUP) {
                            final DetailAST firstNodeInsideCaseBlock =
                                currentCaseBlock.getFirstChild();

                            if (isChild(firstNodeInsideCaseBlock,
                                variable)) {
                                isVarInOperatorDeclr = true;
                                break;
                            }
                            currentCaseBlock = currentCaseBlock.getNextSibling();
                        }
                        break;

                    default:
                        // no code
                }
            }
        }

        return isVarInOperatorDeclr;
    }

    /**
     * Checks if Ast node contains given element.
     * @param parent
     *        Node of AST.
     * @param ast
     *        Ast element which is checked for content in Ast node.
     * @return true if Ast element was found in Ast node, otherwise - false.
     */
    private static boolean isChild(DetailAST parent, DetailAST ast) {
        boolean isChild = false;
        final ASTEnumeration astList = parent.findAllPartial(ast);

        while (astList.hasMoreNodes()) {
            final DetailAST astNode = (DetailAST) astList.nextNode();
            DetailAST astParent = astNode.getParent();

            while (astParent != null) {

                if (astParent.equals(parent)
                        && astParent.getLineNo() == parent.getLineNo()) {
                    isChild = true;
                    break;
                }
                astParent = astParent.getParent();
            }
        }

        return isChild;
    }

    /**
     * Checks if entrance variable is contained in ignored pattern.
     * @param variable
     *        Variable which is checked for content in ignored pattern.
     * @return true if variable was found, otherwise - false.
     */
    private boolean isVariableMatchesIgnorePattern(String variable) {
        final Matcher matcher = ignoreVariablePattern.matcher(variable);
        return matcher.matches();
    }
}
