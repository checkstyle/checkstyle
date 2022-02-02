////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
import java.util.Objects;
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
 * <p>
 * Checks the distance between declaration of variable and its first usage.
 * Note : Variable declaration/initialization statements are not counted while calculating length.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowedDistance} - Specify distance between declaration
 * of variable and its first usage. Values should be greater than 0.
 * Type is {@code int}.
 * Default value is {@code 3}.
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
 * <li>
 * Property {@code ignoreFinal} - Allow to ignore variables with a 'final' modifier.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check with default config:
 * </p>
 * <pre>
 * &lt;module name=&quot;VariableDeclarationUsageDistance&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *   public void foo1() {
 *     int num;        // violation, distance = 4
 *     final int PI;   // OK, final variables not checked
 *     System.out.println("Statement 1");
 *     System.out.println("Statement 2");
 *     System.out.println("Statement 3");
 *     num = 1;
 *     PI = 3.14;
 *   }
 *
 *   public void foo2() {
 *     int a;          // OK, used in different scope
 *     int b;          // OK, used in different scope
 *     int count = 0;  // OK, used in different scope
 *
 *     {
 *       System.out.println("Inside inner scope");
 *       a = 1;
 *       b = 2;
 *       count++;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * Check can detect a block of initialization methods. If a variable is used in
 * such a block and there are no other statements after variable declaration, then distance = 1.
 * </p>
 * <p>Case #1:</p>
 * <pre>
 * int minutes = 5;
 * Calendar cal = Calendar.getInstance();
 * cal.setTimeInMillis(timeNow);
 * cal.set(Calendar.SECOND, 0);
 * cal.set(Calendar.MILLISECOND, 0);
 * cal.set(Calendar.HOUR_OF_DAY, hh);
 * cal.set(Calendar.MINUTE, minutes);
 * </pre>
 * <p>
 * The distance for the variable "minutes" is 1 even
 * though this variable is used in the fifth method's call.
 * </p>
 * <p>Case #2:</p>
 * <pre>
 * int minutes = 5;
 * Calendar cal = Calendar.getInstance();
 * cal.setTimeInMillis(timeNow);
 * cal.set(Calendar.SECOND, 0);
 * cal.set(Calendar.MILLISECOND, 0);
 * <i>System.out.println(cal);</i>
 * cal.set(Calendar.HOUR_OF_DAY, hh);
 * cal.set(Calendar.MINUTE, minutes);
 * </pre>
 * <p>
 * The distance for the variable "minutes" is 6 because there is one more expression
 * (except the initialization block) between the declaration of this variable and its usage.
 * </p>
 * <p>
 * To configure the check to set allowed distance:
 * </p>
 * <pre>
 * &lt;module name=&quot;VariableDeclarationUsageDistance&quot;&gt;
 *   &lt;property name=&quot;allowedDistance&quot; value=&quot;4&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *   public void foo1() {
 *     int num;        // OK, distance = 4
 *     final int PI;   // OK, final variables not checked
 *     System.out.println("Statement 1");
 *     System.out.println("Statement 2");
 *     System.out.println("Statement 3");
 *     num = 1;
 *     PI = 3.14;
 *   }
 *
 *   public void foo2() {
 *     int a;          // OK, used in different scope
 *     int b;          // OK, used in different scope
 *     int count = 0;  // OK, used in different scope
 *
 *     {
 *       System.out.println("Inside inner scope");
 *       a = 1;
 *       b = 2;
 *       count++;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to ignore certain variables:
 * </p>
 * <pre>
 * &lt;module name=&quot;VariableDeclarationUsageDistance&quot;&gt;
 *   &lt;property name=&quot;ignoreVariablePattern&quot; value=&quot;^num$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * This configuration ignores variables named "num".
 * </p>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *   public void foo1() {
 *     int num;        // OK, variable ignored
 *     final int PI;   // OK, final variables not checked
 *     System.out.println("Statement 1");
 *     System.out.println("Statement 2");
 *     System.out.println("Statement 3");
 *     num = 1;
 *     PI = 3.14;
 *   }
 *
 *   public void foo2() {
 *     int a;          // OK, used in different scope
 *     int b;          // OK, used in different scope
 *     int count = 0;  // OK, used in different scope
 *
 *     {
 *       System.out.println("Inside inner scope");
 *       a = 1;
 *       b = 2;
 *       count++;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to force validation between scopes:
 * </p>
 * <pre>
 * &lt;module name=&quot;VariableDeclarationUsageDistance&quot;&gt;
 *   &lt;property name=&quot;validateBetweenScopes&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *   public void foo1() {
 *     int num;        // violation, distance = 4
 *     final int PI;   // OK, final variables not checked
 *     System.out.println("Statement 1");
 *     System.out.println("Statement 2");
 *     System.out.println("Statement 3");
 *     num = 1;
 *     PI = 3.14;
 *   }
 *
 *   public void foo2() {
 *     int a;          // OK, distance = 2
 *     int b;          // OK, distance = 3
 *     int count = 0;  // violation, distance = 4
 *
 *     {
 *       System.out.println("Inside inner scope");
 *       a = 1;
 *       b = 2;
 *       count++;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to check final variables:
 * </p>
 * <pre>
 * &lt;module name=&quot;VariableDeclarationUsageDistance&quot;&gt;
 *   &lt;property name=&quot;ignoreFinal&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *   public void foo1() {
 *     int num;        // violation, distance = 4
 *     final int PI;   // violation, distance = 5
 *     System.out.println("Statement 1");
 *     System.out.println("Statement 2");
 *     System.out.println("Statement 3");
 *     num = 1;
 *     PI = 3.14;
 *   }
 *
 *   public void foo2() {
 *     int a;          // OK, used in different scope
 *     int b;          // OK, used in different scope
 *     int count = 0;  // OK, used in different scope
 *
 *     {
 *       System.out.println("Inside inner scope");
 *       a = 1;
 *       b = 2;
 *       count++;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
     */
    public void setAllowedDistance(int allowedDistance) {
        this.allowedDistance = allowedDistance;
    }

    /**
     * Setter to define RegExp to ignore distance calculation for variables listed in this pattern.
     *
     * @param pattern a pattern.
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
     */
    public void setValidateBetweenScopes(boolean validateBetweenScopes) {
        this.validateBetweenScopes = validateBetweenScopes;
    }

    /**
     * Setter to allow to ignore variables with a 'final' modifier.
     *
     * @param ignoreFinal
     *        Defines if ignore variables with 'final' modifier or not.
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

        while (result
                && !isUsedVariableDeclarationFound
                && currentSiblingAst != null) {
            switch (currentSiblingAst.getType()) {
                case TokenTypes.EXPR:
                    final DetailAST methodCallAst = currentSiblingAst.getFirstChild();

                    if (methodCallAst.getType() == TokenTypes.METHOD_CALL) {
                        final String instanceName =
                            getInstanceName(methodCallAst);
                        // method is called without instance
                        if (instanceName.isEmpty()) {
                            result = false;
                        }
                        // differs from previous instance
                        else if (!instanceName.equals(initInstanceName)) {
                            if (initInstanceName.isEmpty()) {
                                initInstanceName = instanceName;
                            }
                            else {
                                result = false;
                            }
                        }
                    }
                    else {
                        // is not method call
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
     *
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
                    dist = getDistToVariableUsageInChildNode(currentAst, variableIdentAst, dist);
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
     * Returns the distance to variable usage for in the child node.
     *
     * @param childNode child node.
     * @param varIdent variable variable identifier.
     * @param currentDistToVarUsage current distance to the variable usage.
     * @return the distance to variable usage for in the child node.
     */
    private static int getDistToVariableUsageInChildNode(DetailAST childNode, DetailAST varIdent,
                                                         int currentDistToVarUsage) {
        DetailAST examineNode = childNode;
        if (examineNode.getType() == TokenTypes.LABELED_STAT) {
            examineNode = examineNode.getFirstChild().getNextSibling();
        }

        int resultDist = currentDistToVarUsage;
        switch (examineNode.getType()) {
            case TokenTypes.VARIABLE_DEF:
                resultDist++;
                break;
            case TokenTypes.SLIST:
                resultDist = 0;
                break;
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.LITERAL_SWITCH:
                if (isVariableInOperatorExpr(examineNode, varIdent)) {
                    resultDist++;
                }
                else {
                    // variable usage is in inner scope
                    // reset counters, because we can't determine distance
                    resultDist = 0;
                }
                break;
            default:
                if (examineNode.findFirstToken(TokenTypes.SLIST) == null) {
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
                variableUsageAst =
                        Objects.requireNonNullElse(exprWithVariableUsage, blockWithVariableUsage);
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
        while (currentStatementAst != null
                && currentStatementAst.getType() != TokenTypes.RCURLY) {
            if (currentStatementAst.getFirstChild() != null) {
                if (isChild(currentStatementAst, variableAst)) {
                    variableUsageExpressions.add(currentStatementAst);
                }
                // If expression doesn't contain variable and this variable
                // hasn't been met yet, then distance + 1.
                else if (variableUsageExpressions.isEmpty()
                        && currentStatementAst.getType() != TokenTypes.VARIABLE_DEF) {
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

            if (currentNodeType == TokenTypes.SLIST) {
                firstNodeInsideBlock = currentNode.getFirstChild();
            }
            else if (currentNodeType != TokenTypes.EXPR) {
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

            // If IF block doesn't include ELSE then analyze variable usage
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
        final DetailAST currentNode = getFirstCaseGroupOrSwitchRule(block);
        final List<DetailAST> variableUsageExpressions =
                new ArrayList<>();

        // Checking variable usage inside all CASE_GROUP and SWITCH_RULE ast's.
        TokenUtil.forEachChild(block, currentNode.getType(), node -> {
            final DetailAST lastNodeInCaseGroup =
                node.getLastChild();
            if (isChild(lastNodeInCaseGroup, variable)) {
                variableUsageExpressions.add(lastNodeInCaseGroup);
            }
        });

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
     * Helper method for getFirstNodeInsideSwitchBlock to return the first CASE_GROUP or
     * SWITCH_RULE ast.
     *
     * @param block the switch block to check.
     * @return DetailAST of the first CASE_GROUP or SWITCH_RULE.
     */
    private static DetailAST getFirstCaseGroupOrSwitchRule(DetailAST block) {
        return Optional.ofNullable(block.findFirstToken(TokenTypes.CASE_GROUP))
            .orElseGet(() -> block.findFirstToken(TokenTypes.SWITCH_RULE));
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
        final DetailAST openingBracket =
                operator.findFirstToken(TokenTypes.LPAREN);

        // Get EXPR between brackets
        DetailAST exprBetweenBrackets = openingBracket.getNextSibling();

        // Look if variable is in operator expression
        while (exprBetweenBrackets.getType() != TokenTypes.RPAREN) {
            if (isChild(exprBetweenBrackets, variable)) {
                isVarInOperatorDeclaration = true;
                break;
            }
            exprBetweenBrackets = exprBetweenBrackets.getNextSibling();
        }

        // Variable may be met in ELSE declaration
        // So, check variable usage in these declarations.
        if (!isVarInOperatorDeclaration && operator.getType() == TokenTypes.LITERAL_IF) {
            final DetailAST elseBlock = operator.getLastChild();

            if (elseBlock.getType() == TokenTypes.LITERAL_ELSE) {
                // Get IF followed by ELSE
                final DetailAST firstNodeInsideElseBlock = elseBlock.getFirstChild();

                if (firstNodeInsideElseBlock.getType() == TokenTypes.LITERAL_IF) {
                    isVarInOperatorDeclaration =
                        isVariableInOperatorExpr(firstNodeInsideElseBlock, variable);
                }
            }
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

}
