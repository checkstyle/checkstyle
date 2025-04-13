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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the distance between declaration of variable and its first usage.
 * Note: Any additional variables declared or initialized between the declaration and
 *  the first usage of the said variable are not counted when calculating the distance.
 * </div>
 * <ul>
 * <li>
 * Property {@code allowedDistance} - Specify distance between declaration
 * of variable and its first usage. Values should be greater than 0.
 * Type is {@code int}.
 * Default value is {@code 3}.
 * </li>
 * <li>
 * Property {@code ignoreFinal} - Allow to ignore variables with a 'final' modifier.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code ignoreVariablePattern} - Define RegExp to ignore distance calculation
 * for variables listed in this pattern.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code validateBetweenScopes} - Allow to calculate the distance between
 * declaration of variable and its first usage in the different scopes.
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
 * {@code variable.declaration.usage.distance}
 * </li>
 * <li>
 * {@code variable.declaration.usage.distance.extend}
 * </li>
 * </ul>
 *
 * @since 5.8
 */
@StatelessCheck
public class VariableDeclarationUsageDistanceCheck extends AbstractCheck {

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

    /**
     * Specify distance between declaration of variable and its first usage.
     * Values should be greater than 0.
     */
    private int allowedDistance = DEFAULT_DISTANCE;

    /**
     * Define RegExp to ignore distance calculation for variables listed in
     * this pattern.
     */
    private Pattern ignoreVariablePattern = Pattern.compile("");

    /**
     * Allow to calculate the distance between declaration of variable and its
     * first usage in the different scopes.
     */
    private boolean validateBetweenScopes;

    /** Allow to ignore variables with a 'final' modifier. */
    private boolean ignoreFinal = true;

    /**
     * Setter to specify distance between declaration of variable and its first usage.
     * Values should be greater than 0.
     *
     * @param allowedDistance
     *        Allowed distance between declaration of variable and its first
     *        usage.
     * @since 5.8
     */
    public void setAllowedDistance(int allowedDistance) {
        this.allowedDistance = allowedDistance;
    }

    /**
     * Setter to define RegExp to ignore distance calculation for variables listed in this pattern.
     *
     * @param pattern a pattern.
     * @since 5.8
     */
    public void setIgnoreVariablePattern(Pattern pattern) {
        ignoreVariablePattern = pattern;
    }

    /**
     * Setter to allow to calculate the distance between declaration of
     * variable and its first usage in the different scopes.
     *
     * @param validateBetweenScopes
     *        Defines if allow to calculate distance between declaration of
     *        variable and its first usage in different scopes or not.
     * @since 5.8
     */
    public void setValidateBetweenScopes(boolean validateBetweenScopes) {
        this.validateBetweenScopes = validateBetweenScopes;
    }

    /**
     * Setter to allow to ignore variables with a 'final' modifier.
     *
     * @param ignoreFinal
     *        Defines if ignore variables with 'final' modifier or not.
     * @since 5.8
     */
    public void setIgnoreFinal(boolean ignoreFinal) {
        this.ignoreFinal = ignoreFinal;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();
        final DetailAST modifiers = ast.getFirstChild();

        if (parentType != TokenTypes.OBJBLOCK
                && (!ignoreFinal || modifiers.findFirstToken(TokenTypes.FINAL) == null)) {
            final DetailAST variable = ast.findFirstToken(TokenTypes.IDENT);

            if (!isVariableMatchesIgnorePattern(variable.getText())) {
                final DetailAST semicolonAst = ast.getNextSibling();
                final Entry<DetailAST, Integer> entry;
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
                        log(ast, MSG_KEY_EXT, variable.getText(), dist, allowedDistance);
                    }
                    else {
                        log(ast, MSG_KEY, variable.getText(), dist, allowedDistance);
                    }
                }
            }
        }
    }

    /**
     * Get name of instance whose method is called.
     *
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
     *
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

        while (result && !isUsedVariableDeclarationFound && currentSiblingAst != null) {
            if (currentSiblingAst.getType() == TokenTypes.EXPR
                    && currentSiblingAst.getFirstChild().getType() == TokenTypes.METHOD_CALL) {
                final DetailAST methodCallAst = currentSiblingAst.getFirstChild();
                final String instanceName = getInstanceName(methodCallAst);
                if (instanceName.isEmpty()) {
                    result = false;
                }
                else if (!instanceName.equals(initInstanceName)) {
                    if (initInstanceName.isEmpty()) {
                        initInstanceName = instanceName;
                    }
                    else {
                        result = false;
                    }
                }

            }
            else if (currentSiblingAst.getType() == TokenTypes.VARIABLE_DEF) {
                final String currentVariableName =
                        currentSiblingAst.findFirstToken(TokenTypes.IDENT).getText();
                isUsedVariableDeclarationFound = variableName.equals(currentVariableName);
            }
            else {
                result = currentSiblingAst.getType() == TokenTypes.SEMI;
            }
            currentSiblingAst = currentSiblingAst.getPreviousSibling();
        }
        return result;
    }

    /**
     * Calculates distance between declaration of variable and its first usage
     * in single scope.
     *
     * @param semicolonAst
     *        Regular node of Ast which is checked for content of checking
     *        variable.
     * @param variableIdentAst
     *        Variable which distance is calculated for.
     * @return entry which contains expression with variable usage and distance.
     *         If variable usage is not found, then the expression node is null,
     *         although the distance can be greater than zero.
     */
    private static Entry<DetailAST, Integer> calculateDistanceInSingleScope(
            DetailAST semicolonAst, DetailAST variableIdentAst) {
        int dist = 0;
        boolean firstUsageFound = false;
        DetailAST currentAst = semicolonAst;
        DetailAST variableUsageAst = null;

        while (!firstUsageFound && currentAst != null) {
            if (currentAst.getFirstChild() != null) {
                if (isChild(currentAst, variableIdentAst)) {
                    dist = getDistToVariableUsageInChildNode(currentAst, dist);
                    variableUsageAst = currentAst;
                    firstUsageFound = true;
                }
                else if (currentAst.getType() != TokenTypes.VARIABLE_DEF) {
                    dist++;
                }
            }
            currentAst = currentAst.getNextSibling();
        }

        return new SimpleEntry<>(variableUsageAst, dist);
    }

    /**
     * Returns the distance to variable usage for in the child node.
     *
     * @param childNode child node.
     * @param currentDistToVarUsage current distance to the variable usage.
     * @return the distance to variable usage for in the child node.
     */
    private static int getDistToVariableUsageInChildNode(DetailAST childNode,
                                                         int currentDistToVarUsage) {
        DetailAST examineNode = childNode;
        if (examineNode.getType() == TokenTypes.LABELED_STAT) {
            examineNode = examineNode.getFirstChild().getNextSibling();
        }

        int resultDist = currentDistToVarUsage;

        switch (examineNode.getType()) {
            case TokenTypes.SLIST:
                resultDist = 0;
                break;
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.LITERAL_SWITCH:
                // variable usage is in inner scope, treated as 1 block
                // or in operator expression, then distance + 1
                resultDist++;
                break;
            default:
                if (childNode.findFirstToken(TokenTypes.SLIST) == null) {
                    resultDist++;
                }
                else {
                    resultDist = 0;
                }
        }
        return resultDist;
    }

    /**
     * Calculates distance between declaration of variable and its first usage
     * in multiple scopes.
     *
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
            final Entry<List<DetailAST>, Integer> searchResult =
                    searchVariableUsageExpressions(variable, currentScopeAst);

            currentScopeAst = null;

            final List<DetailAST> variableUsageExpressions = searchResult.getKey();
            dist += searchResult.getValue();

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
                variableUsageAst = blockWithVariableUsage;
            }

            // If there's no any variable usage, then distance = 0.
            else if (variableUsageExpressions.isEmpty()) {
                variableUsageAst = null;
            }
            // If variable usage exists in different scopes, then distance =
            // distance until variable first usage.
            else {
                dist++;
                variableUsageAst = variableUsageExpressions.get(0);
            }
        }
        return new SimpleEntry<>(variableUsageAst, dist);
    }

    /**
     * Searches variable usages starting from specified statement.
     *
     * @param variableAst Variable that is used.
     * @param statementAst DetailAST to start searching from.
     * @return entry which contains list with found expressions that use the variable
     *     and distance from specified statement to first found expression.
     */
    private static Entry<List<DetailAST>, Integer>
        searchVariableUsageExpressions(final DetailAST variableAst, final DetailAST statementAst) {
        final List<DetailAST> variableUsageExpressions = new ArrayList<>();
        int distance = 0;
        DetailAST currentStatementAst = statementAst;
        while (currentStatementAst != null) {
            if (currentStatementAst.getFirstChild() != null) {
                if (isChild(currentStatementAst, variableAst)) {
                    variableUsageExpressions.add(currentStatementAst);
                }
                // If expression hasn't been met yet, then distance + 1.
                else if (variableUsageExpressions.isEmpty()
                        && !isZeroDistanceToken(currentStatementAst.getType())) {
                    distance++;
                }
            }
            currentStatementAst = currentStatementAst.getNextSibling();
        }
        return new SimpleEntry<>(variableUsageExpressions, distance);
    }

    /**
     * Gets first Ast node inside FOR, WHILE or DO-WHILE blocks if variable
     * usage is met only inside the block (not in its declaration!).
     *
     * @param block
     *        Ast node represents FOR, WHILE or DO-WHILE block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) then return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideForWhileDoWhileBlocks(
            DetailAST block, DetailAST variable) {
        DetailAST firstNodeInsideBlock = null;

        if (!isVariableInOperatorExpr(block, variable)) {
            final DetailAST currentNode;

            // Find currentNode for DO-WHILE block.
            if (block.getType() == TokenTypes.LITERAL_DO) {
                currentNode = block.getFirstChild();
            }
            // Find currentNode for FOR or WHILE block.
            else {
                // Looking for RPAREN ( ')' ) token to mark the end of operator
                // expression.
                currentNode = block.findFirstToken(TokenTypes.RPAREN).getNextSibling();
            }

            final int currentNodeType = currentNode.getType();

            if (currentNodeType != TokenTypes.EXPR) {
                firstNodeInsideBlock = currentNode;
            }
        }

        return firstNodeInsideBlock;
    }

    /**
     * Gets first Ast node inside IF block if variable usage is met
     * only inside the block (not in its declaration!).
     *
     * @param block
     *        Ast node represents IF block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) then return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideIfBlock(
            DetailAST block, DetailAST variable) {
        DetailAST firstNodeInsideBlock = null;

        if (!isVariableInOperatorExpr(block, variable)) {
            final Optional<DetailAST> slistToken = TokenUtil
                .findFirstTokenByPredicate(block, token -> token.getType() == TokenTypes.SLIST);
            final DetailAST lastNode = block.getLastChild();
            DetailAST previousNode = lastNode.getPreviousSibling();

            if (slistToken.isEmpty()
                && lastNode.getType() == TokenTypes.LITERAL_ELSE) {

                // Is if statement without '{}' and has a following else branch,
                // then change previousNode to the if statement body.
                previousNode = previousNode.getPreviousSibling();
            }

            final List<DetailAST> variableUsageExpressions = new ArrayList<>();
            if (isChild(previousNode, variable)) {
                variableUsageExpressions.add(previousNode);
            }

            if (isChild(lastNode, variable)) {
                variableUsageExpressions.add(lastNode);
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
     *
     * @param block
     *        Ast node represents SWITCH block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) then return the first Ast node
     *         of this block, otherwise - null.
     */
    private static DetailAST getFirstNodeInsideSwitchBlock(
            DetailAST block, DetailAST variable) {
        final List<DetailAST> variableUsageExpressions =
                getVariableUsageExpressionsInsideSwitchBlock(block, variable);

        // If variable usage exists in several related blocks, then
        // firstNodeInsideBlock = null, otherwise if variable usage exists
        // only inside one block, then get node from
        // variableUsageExpressions.
        DetailAST firstNodeInsideBlock = null;
        if (variableUsageExpressions.size() == 1) {
            firstNodeInsideBlock = variableUsageExpressions.get(0);
        }

        return firstNodeInsideBlock;
    }

    /**
     * Helper method for getFirstNodeInsideSwitchBlock to return all variable
     * usage expressions inside a given switch block.
     *
     * @param block the switch block to check.
     * @param variable variable which is checked for in switch block.
     * @return List of usages or empty list if none are found.
     */
    private static List<DetailAST> getVariableUsageExpressionsInsideSwitchBlock(DetailAST block,
                                                                            DetailAST variable) {
        final Optional<DetailAST> firstToken = TokenUtil.findFirstTokenByPredicate(block, child -> {
            return child.getType() == TokenTypes.SWITCH_RULE
                    || child.getType() == TokenTypes.CASE_GROUP;
        });

        final List<DetailAST> variableUsageExpressions = new ArrayList<>();

        firstToken.ifPresent(token -> {
            TokenUtil.forEachChild(block, token.getType(), child -> {
                final DetailAST lastNodeInCaseGroup = child.getLastChild();
                if (isChild(lastNodeInCaseGroup, variable)) {
                    variableUsageExpressions.add(lastNodeInCaseGroup);
                }
            });
        });

        return variableUsageExpressions;
    }

    /**
     * Gets first Ast node inside TRY-CATCH-FINALLY blocks if variable usage is
     * met only inside the block (not in its declaration!).
     *
     * @param block
     *        Ast node represents TRY-CATCH-FINALLY block.
     * @param variable
     *        Variable which is checked for content in block.
     * @return If variable usage is met only inside the block
     *         (not in its declaration!) then return the first Ast node
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
     *
     * @param operator
     *        Ast node which represents operator.
     * @param variable
     *        Variable which is checked for content in operator.
     * @return true if operator contains variable in its declaration, otherwise
     *         - false.
     */
    private static boolean isVariableInOperatorExpr(
            DetailAST operator, DetailAST variable) {
        boolean isVarInOperatorDeclaration = false;

        DetailAST ast = operator.findFirstToken(TokenTypes.LPAREN);

        // Look if variable is in operator expression
        while (ast.getType() != TokenTypes.RPAREN) {
            if (isChild(ast, variable)) {
                isVarInOperatorDeclaration = true;
                break;
            }
            ast = ast.getNextSibling();
        }

        return isVarInOperatorDeclaration;
    }

    /**
     * Checks if Ast node contains given element.
     *
     * @param parent
     *        Node of AST.
     * @param ast
     *        Ast element which is checked for content in Ast node.
     * @return true if Ast element was found in Ast node, otherwise - false.
     */
    private static boolean isChild(DetailAST parent, DetailAST ast) {
        boolean isChild = false;
        DetailAST curNode = parent.getFirstChild();

        while (curNode != null) {
            if (curNode.getType() == ast.getType() && curNode.getText().equals(ast.getText())) {
                isChild = true;
                break;
            }

            DetailAST toVisit = curNode.getFirstChild();
            while (toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();

                if (curNode == parent) {
                    break;
                }
            }

            curNode = toVisit;
        }

        return isChild;
    }

    /**
     * Checks if entrance variable is contained in ignored pattern.
     *
     * @param variable
     *        Variable which is checked for content in ignored pattern.
     * @return true if variable was found, otherwise - false.
     */
    private boolean isVariableMatchesIgnorePattern(String variable) {
        final Matcher matcher = ignoreVariablePattern.matcher(variable);
        return matcher.matches();
    }

    /**
     * Check if the token should be ignored for distance counting.
     * For example,
     * <pre>
     *     try (final AutoCloseable t = new java.io.StringReader(a);) {
     *     }
     * </pre>
     * final is a zero-distance token and should be ignored for distance counting.
     * <pre>
     *     class Table implements Comparator&lt;Integer&gt;{
     *     }
     * </pre>
     * An inner class may be defined. Both tokens implements and extends
     * are zero-distance tokens.
     * <pre>
     *     public int method(Object b){
     *     }
     * </pre>
     * public is a modifier and zero-distance token. int is a type and
     * zero-distance token.
     *
     * @param type
     *        Token type of the ast node.
     * @return true if it should be ignored for distance counting, otherwise false.
     */
    private static boolean isZeroDistanceToken(int type) {
        return type == TokenTypes.VARIABLE_DEF
                || type == TokenTypes.TYPE
                || type == TokenTypes.MODIFIERS
                || type == TokenTypes.RESOURCE
                || type == TokenTypes.EXTENDS_CLAUSE
                || type == TokenTypes.IMPLEMENTS_CLAUSE;
    }

}
