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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for checking vertical alignment of continuation lines.
 * Determines if a node's column position aligns with a reference node
 * in the same expression, allowing the indentation check to be suppressed
 * for intentionally aligned code.
 */
public final class VerticalAlignmentHandler {

    /** No-arg constructor to prevent instantiation. */
    private VerticalAlignmentHandler() {
    }

    /**
     * Checks if the given AST node is vertically aligned with a reference
     * expression in the same construct.
     *
     * @param node the node to check
     * @return true if the node's column matches an allowed alignment column
     */
    public static boolean isVerticallyAligned(DetailAST node) {
        Set<Integer> allowedVerticalAlignmentColumns = getAllowedVerticalAlignmentColumns(node);
        return allowedVerticalAlignmentColumns.contains(node.getColumnNo());
    }

    /**
     * Computes the set of column positions that the given node is allowed
     * to align with, based on its AST context.
     *
     * @param node the node to check
     * @return list of allowed alignment column numbers
     */
    private static Set<Integer> getAllowedVerticalAlignmentColumns(DetailAST node) {
        Set<Integer> columns = new HashSet<>();
        Stream.of(
                getAllowedColumnForParameterDefinition(node),
                getAllowedColumnForExpressionInElist(node),
                getAllowedColumnForAnnotationMemberValuePair(node),
            getAllowedColumnForArithmeticBooleanExpression(node),
            getAllowedColumnForTernaryExpression(node),
            getAllowedColumnForChainedCallOrField(node),
            getAllowedColumnForCommaSeparatedVar(node),
            getAllowedColumnForForLoopParts(node),
            getAllowedColumnForExpressionInArrayInit(node)
            ).forEach(columns::addAll);
        return columns;
    }

    private static Set<Integer> getAllowedColumnForParameterDefinition(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        if (findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.PARAMETER_DEF)
                        && isAtSamePosition(node, getTopLeftmostNodeInTree(ancestor))
        ).isPresent()) {
            findAncestorByPredicate(node,
                    ast -> ast.getType() == TokenTypes.PARAMETERS)
                .map(params -> params.findFirstToken(TokenTypes.PARAMETER_DEF))
                .filter(firstParam -> !isAtSamePosition(node,
                        getTopLeftmostNodeInTree(firstParam)))
                .map(paramDef -> getTopLeftmostNodeInTree(paramDef).getColumnNo())
                .ifPresent(result::add);
        }
        return result;
    }

    private static Set<Integer> getAllowedColumnForExpressionInElist(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        if (findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.EXPR)
                        && TokenUtil.isOfType(ancestor.getParent(), TokenTypes.ELIST)
                        && isAtSamePosition(node, getTopLeftmostNodeInTree(ancestor))
        ).isPresent()) {
            findAncestorByPredicate(node,
                    ast -> ast.getType() == TokenTypes.ELIST)
                .map(elist -> elist.findFirstToken(TokenTypes.EXPR))
                .filter(firstExpr -> !isAtSamePosition(node,
                        getTopLeftmostNodeInTree(firstExpr)))
                .map(expr -> getTopLeftmostNodeInTree(expr).getColumnNo())
                .ifPresent(result::add);
        }
        return result;
    }

    private static Set<Integer> getAllowedColumnForAnnotationMemberValuePair(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        if (findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor,
                        TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR)
                        && isAtSamePosition(node, getTopLeftmostNodeInTree(ancestor))
        ).isPresent()) {
            findAncestorByPredicate(node,
                    ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.ANNOTATION)
                ).map(ancestor -> ancestor.findFirstToken(
                        TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR))
                .map(pair -> getTopLeftmostNodeInTree(pair).getColumnNo())
                .ifPresent(result::add);
        }
        return result;
    }

    private static Set<Integer> getAllowedColumnForArithmeticBooleanExpression(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.EXPR)
                        && getTopLeftmostNodeInTree(ancestor).getLineNo()
                                != node.getLineNo()
        ).map(expr -> getTopLeftmostNodeInTree(expr).getColumnNo())
         .ifPresent(result::add);
        return result;
    }

    private static Set<Integer> getAllowedColumnForTernaryExpression(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        Optional<DetailAST> questionOpt = findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.QUESTION)
        );
        if (questionOpt.isPresent()) {
            final DetailAST question = questionOpt.get();
            boolean conditionMet = false;
            if (node == question) {
                conditionMet = true;
            }
            else {
                DetailAST current = node;
                while (current != null && current.getParent() != question) {
                    current = current.getParent();
                }
                if (current != null
                        && isAtSamePosition(node, getTopLeftmostNodeInTree(current))) {
                    conditionMet = true;
                }
            }
            if (conditionMet) {
                final DetailAST firstChild = question.getFirstChild();
                if (firstChild != null) {
                    result.add(
                            getTopLeftmostNodeInTree(firstChild).getColumnNo());
                }
            }
        }
        return result;
    }

    private static Set<Integer> getAllowedColumnForCommaSeparatedVar(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        Optional<DetailAST> varDefOpt = findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.VARIABLE_DEF)
                        && node == ancestor.findFirstToken(TokenTypes.IDENT)
        );
        if (varDefOpt.isPresent()) {
            DetailAST prevSibling = varDefOpt.get().getPreviousSibling();
            if (TokenUtil.isOfType(prevSibling, TokenTypes.COMMA)) {
                DetailAST prevVarDef = prevSibling.getPreviousSibling();
                if (TokenUtil.isOfType(prevVarDef, TokenTypes.VARIABLE_DEF)) {
                    DetailAST first = prevVarDef;
                    while (true) {
                        DetailAST candidatePrev = first.getPreviousSibling();
                        if (TokenUtil.isOfType(candidatePrev, TokenTypes.COMMA)) {
                            DetailAST candidate = candidatePrev.getPreviousSibling();
                            if (TokenUtil.isOfType(candidate, TokenTypes.VARIABLE_DEF)) {
                                first = candidate;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                    DetailAST ident = first.findFirstToken(TokenTypes.IDENT);
                    if (ident != null) {
                        result.add(ident.getColumnNo());
                    }
                }
            }
        }
        return result;
    }

    private static Set<Integer> getAllowedColumnForChainedCallOrField(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        if (TokenUtil.isOfType(node, TokenTypes.DOT)) {
            final DetailAST firstChild = node.getFirstChild();
            if (TokenUtil.isOfType(firstChild,
                    TokenTypes.METHOD_CALL, TokenTypes.DOT)) {
                DetailAST cur = node;
                while (true) {
                    final DetailAST child = cur.getFirstChild();
                    if (child == null) {
                        break;
                    }
                    if (TokenUtil.isOfType(child, TokenTypes.METHOD_CALL)) {
                        final DetailAST grandchild = child.getFirstChild();
                        if (grandchild != null) {
                            cur = grandchild;
                        }
                        else {
                            break;
                        }
                    }
                    else if (TokenUtil.isOfType(child, TokenTypes.DOT)) {
                        cur = child;
                    }
                    else {
                        break;
                    }
                }
                result.add(cur.getColumnNo());
            }
        }
        return result;
    }

    private static Set<Integer> getAllowedColumnForForLoopParts(
            DetailAST node) {
        Set<Integer> result = new HashSet<>();
        findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor,
                        TokenTypes.FOR_CONDITION, TokenTypes.FOR_ITERATOR)
                        && isAtSamePosition(node, getTopLeftmostNodeInTree(ancestor))
        ).ifPresent(part -> {
            DetailAST forNode = part.getParent();
            if (TokenUtil.isOfType(forNode, TokenTypes.LITERAL_FOR)) {
                DetailAST forInit = forNode.findFirstToken(TokenTypes.FOR_INIT);
                if (forInit != null) {
                    result.add(getTopLeftmostNodeInTree(forInit).getColumnNo());
                }
            }
        });
        return result;
    }

    private static Set<Integer> getAllowedColumnForExpressionInArrayInit(
        DetailAST node) {
        Set<Integer> result = new HashSet<>();
        if (findAncestorByPredicate(
            node,
            ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.EXPR)
                && TokenUtil.isOfType(ancestor.getParent(), TokenTypes.ARRAY_INIT)
                && isAtSamePosition(node, getTopLeftmostNodeInTree(ancestor))
        ).isPresent()) {
            findAncestorByPredicate(node,
                ast -> ast.getType() == TokenTypes.ARRAY_INIT)
                .map(arrInit -> arrInit.findFirstToken(TokenTypes.EXPR))
                .filter(firstExpr -> !isAtSamePosition(node,
                        getTopLeftmostNodeInTree(firstExpr)))
                .map(expr -> getTopLeftmostNodeInTree(expr).getColumnNo())
                .ifPresent(result::add);
        }
        return result;
    }

    private static Optional<DetailAST> findAncestorByPredicate(
            DetailAST node, Predicate<DetailAST> predicate) {
        Optional<DetailAST> matchedAncestor = Optional.empty();
        DetailAST current = node;
        while (current != null) {
            if (predicate.test(current)) {
                matchedAncestor = Optional.of(current);
                break;
            }
            current = current.getParent();
        }
        return matchedAncestor;
    }

    /**
     * Checks if two AST nodes occupy the same (line, column) position.
     *
     * @param node1 first node
     * @param node2 second node
     * @return true if both line and column match
     */
    private static boolean isAtSamePosition(DetailAST node1, DetailAST node2) {
        return node1.getColumnNo() == node2.getColumnNo()
                && node1.getLineNo() == node2.getLineNo();
    }

    /**
     * Finds the top-leftmost token in the subtree rooted at the given node.
     * This is the token with the smallest (line, column) pair in the tree.
     *
     * @param root the root of the subtree
     * @return the top-leftmost AST node found
     */
    private static DetailAST getTopLeftmostNodeInTree(DetailAST root) {
        DetailAST topLeftmostNode = root;
        Deque<DetailAST> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            DetailAST current = stack.pop();
            if (current.getLineNo() < topLeftmostNode.getLineNo()
                    || current.getLineNo() == topLeftmostNode.getLineNo()
                    && current.getColumnNo() < topLeftmostNode.getColumnNo()) {
                topLeftmostNode = current;
            }

            DetailAST nextSibling = current.getNextSibling();
            DetailAST firstChild = current.getFirstChild();
            if (nextSibling != null) {
                stack.push(nextSibling);
            }
            if (firstChild != null) {
                stack.push(firstChild);
            }
        }
        return topLeftmostNode;
    }

}
