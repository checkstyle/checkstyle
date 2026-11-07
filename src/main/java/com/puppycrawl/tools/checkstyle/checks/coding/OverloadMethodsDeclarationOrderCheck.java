///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that overloaded methods are grouped together. Overloaded methods have the same
 * name but different signatures where the signature can differ by the number of
 * input parameters or type of input parameters or both.
 * </div>
 *
 * @since 5.8
 */
@StatelessCheck
public class OverloadMethodsDeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "overload.methods.declaration";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_ORDER = "overload.methods.declaration.order";

    /**
     * Separator between the method name and the group identifier in a group key.
     */
    private static final String GROUP_KEY_SEPARATOR = ":";

    /**
     * Identifier of the group for methods whose modifiers match no configured group.
     */
    private static final String DEFAULT_GROUP = "default";

    /**
     * Identifier of the group for methods that have no explicit modifiers.
     */
    private static final String NO_MODIFIERS_GROUP = "noModifiers";

    /**
     * Pattern matching the whitespace that separates modifier names in a group.
     */
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    /**
     * Control whether to order overloaded methods by increasing parameter count.
     */
    private boolean orderByIncreasingParameterCount;

    /**
     * Control how different types of overloaded methods can be grouped by their modifiers.
     */
    private List<String> modifierGroups = new ArrayList<>();

    /**
     * Creates a new {@code OverloadMethodsDeclarationOrderCheck} instance.
     */
    public OverloadMethodsDeclarationOrderCheck() {
        // no code by default
    }

    /**
     * Setter to control whether to enforce order by increasing parameter count (arity) or not.
     *
     * @param orderByIncreasingParameterCount true if order by increasing parameter
     *               count is required.
     * @since 13.7.0
     */
    public void setOrderByIncreasingParameterCount(boolean orderByIncreasingParameterCount) {
        this.orderByIncreasingParameterCount = orderByIncreasingParameterCount;
    }

    /**
     * Setter to control how different types of overloaded methods can be grouped by
     * their modifiers. Each value defines one group by a space separated list of
     * modifier names, for example {@code "static"} or {@code "public static"}.
     * The order of modifiers in a group does not matter. A method belongs to every
     * group whose modifiers are all present in the explicit modifiers of the method,
     * so methods of one name can form several groups, one per group they belong to.
     * Methods whose modifiers match no group belong to the default group, and methods
     * without explicit modifiers always form a separate group of their own.
     *
     * @param modifierGroups the groups of modifiers that create separate groups
     *        of overloaded methods.
     * @since 14.5.0
     */
    public void setModifierGroups(String... modifierGroups) {
        this.modifierGroups = new ArrayList<>(Arrays.asList(modifierGroups));
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
        return new int[] {
            TokenTypes.OBJBLOCK,
            TokenTypes.COMPACT_COMPILATION_UNIT,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int[] objectBlockParentTypes = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.RECORD_DEF,
        };

        if (ast.getType() == TokenTypes.COMPACT_COMPILATION_UNIT
            || TokenUtil.isOfType(ast.getParent().getType(), objectBlockParentTypes)) {
            checkOverloadMethodsGrouping(ast);
        }
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     *
     * @param objectBlock
     *        is a class, interface, enum object block, or a compact
     *        compilation unit for a JEP 512 compact source file.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        DetailAST currentToken = objectBlock.getFirstChild();
        final List<Set<String>> groups = parseModifierGroups();
        final Map<String, Integer> methodIndexMap = new HashMap<>();
        final Map<String, Integer> methodLineNumberMap = new HashMap<>();

        final Map<String, Integer> methodParameterCountMap = new HashMap<>();
        final Map<String, Boolean> methodIsOrderedMap = new HashMap<>();

        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final String methodName =
                        currentToken.findFirstToken(TokenTypes.IDENT).getText();
                final List<String> groupKeys = getGroupKeys(currentToken, methodName, groups);

                final Integer violatedPreviousLine = findViolatedPreviousLine(currentToken,
                        groupKeys, currentIndex, methodIndexMap, methodLineNumberMap);
                if (violatedPreviousLine != null) {
                    log(currentToken, MSG_KEY, violatedPreviousLine);
                }

                if (orderByIncreasingParameterCount
                        && methodParameterCountMap.containsKey(methodName)) {
                    checkMethodOrdering(currentToken, methodName,
                            methodIsOrderedMap, methodParameterCountMap);
                }
                methodIsOrderedMap.putIfAbsent(methodName, Boolean.TRUE);
                for (String groupKey : groupKeys) {
                    methodIndexMap.put(groupKey, currentIndex);
                    methodLineNumberMap.put(groupKey, currentToken.getLineNo());
                }
                methodParameterCountMap.put(methodName, getParameterCount(currentToken));
            }
            currentToken = currentToken.getNextSibling();
        }
    }

    /**
     * Finds the line of the closest previous overload that the method is separated
     * from, among the groups the method belongs to. Overloads of a group are
     * separated when any other method or member is located between them.
     *
     * @param currentMethod the method definition.
     * @param groupKeys the keys of the groups the method belongs to.
     * @param currentIndex the index of the method among the methods of the block.
     * @param methodIndexMap map of group keys to the index of their last method.
     * @param methodLineNumberMap map of group keys to the line of their last method.
     * @return the line of the closest separated previous overload, or null if the
     *         method is not separated from any previous overload of its groups.
     */
    @Nullable
    private static Integer findViolatedPreviousLine(DetailAST currentMethod,
            Collection<String> groupKeys, int currentIndex, Map<String, Integer> methodIndexMap,
            Map<String, Integer> methodLineNumberMap) {
        final int allowedDistance = 1;
        Integer violatedPreviousLine = null;
        for (String groupKey : groupKeys) {
            final Integer previousIndex = methodIndexMap.get(groupKey);
            if (previousIndex != null) {
                final DetailAST previousSibling = currentMethod.getPreviousSibling();
                final boolean isMethod = previousSibling.getType() == TokenTypes.METHOD_DEF;
                if (!isMethod || currentIndex - previousIndex > allowedDistance) {
                    final int previousLine = methodLineNumberMap.get(groupKey);
                    if (violatedPreviousLine == null) {
                        violatedPreviousLine = previousLine;
                    }
                    else {
                        violatedPreviousLine = Math.max(violatedPreviousLine, previousLine);
                    }
                }
            }
        }
        return violatedPreviousLine;
    }

    /**
     * Parses the {@code modifierGroups} property into sets of modifier names,
     * one set per configured group.
     *
     * @return the modifier groups as sets of modifier names.
     */
    private List<Set<String>> parseModifierGroups() {
        return modifierGroups.stream()
                .<Set<String>>map(group -> {
                    return new HashSet<>(
                            Arrays.asList(WHITESPACE_PATTERN.split(group)));
                })
                .toList();
    }

    /**
     * Computes the keys of the groups the method belongs to. A key is the method name
     * combined with the identifier of a group. If no groups are configured, the only
     * key is the method name itself, so all overloads of a name form a single group.
     *
     * @param methodDef the method definition.
     * @param methodName the name of the method.
     * @param groups the modifier groups as sets of modifier names.
     * @return the keys of the groups the method belongs to.
     */
    private static List<String> getGroupKeys(DetailAST methodDef, String methodName,
            List<Set<String>> groups) {
        final List<String> groupKeys = new ArrayList<>();
        if (groups.isEmpty()) {
            groupKeys.add(methodName);
        }
        else {
            final Set<String> modifiers = getExplicitModifiers(methodDef);
            if (modifiers.isEmpty()) {
                groupKeys.add(methodName + GROUP_KEY_SEPARATOR + NO_MODIFIERS_GROUP);
            }
            else {
                for (int index = 0; index < groups.size(); index++) {
                    if (modifiers.containsAll(groups.get(index))) {
                        groupKeys.add(methodName + GROUP_KEY_SEPARATOR + index);
                    }
                }
                if (groupKeys.isEmpty()) {
                    groupKeys.add(methodName + GROUP_KEY_SEPARATOR + DEFAULT_GROUP);
                }
            }
        }
        return groupKeys;
    }

    /**
     * Gets the explicit modifiers of a method. Annotations are not modifiers,
     * and no implicit modifiers are added, so a method declared without any
     * modifier has an empty result even in an interface.
     *
     * @param methodDef the method definition.
     * @return the names of the explicit modifiers of the method.
     */
    private static Set<String> getExplicitModifiers(DetailAST methodDef) {
        final Set<String> modifiers = new HashSet<>();
        final DetailAST modifiersAst = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST modifier = modifiersAst.getFirstChild();
        while (modifier != null) {
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                modifiers.add(modifier.getText());
            }
            modifier = modifier.getNextSibling();
        }
        return modifiers;
    }

    /**
     * Checks that overloaded methods are ordered with increasing parameter count or not.
     *
     * @param currentMethod ast of the method.
     * @param methodName method name.
     * @param methodIsOrderedMap
     *        is a map of method names to booleans indicating whether the method is ordered.
     * @param methodParameterCountMap
     *        is a map of method names to integers indicating the parameter count of the previous
     *        similar method.
     */
    private void checkMethodOrdering(DetailAST currentMethod, String methodName,
        Map<String, Boolean> methodIsOrderedMap, Map<String, Integer> methodParameterCountMap) {

        final int currentParamCount = getParameterCount(currentMethod);
        final boolean isOrdered = methodIsOrderedMap.get(methodName)
                            && currentParamCount >= methodParameterCountMap.get(methodName);
        if (!isOrdered) {
            methodIsOrderedMap.put(methodName, Boolean.FALSE);
            log(currentMethod, MSG_ORDER);
        }
    }

    /**
     * Get the parameter count of a method.
     *
     * @param method the method AST
     * @return the parameter count of the method
     */
    private static int getParameterCount(DetailAST method) {
        final DetailAST params = method.findFirstToken(TokenTypes.PARAMETERS);
        return params.getChildCount(TokenTypes.PARAMETER_DEF);
    }

}
