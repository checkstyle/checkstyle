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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    /** The current instance of {@code IndentationCheck} using this handler. */
    private final IndentationCheck indentCheck;

    /** Cache for getTopLeftmostNodeInTree results. */
    private final Map<DetailAST, DetailAST> topLeftmostCache = new HashMap<>();

    /**
     * Creates a new VerticalAlignmentHandler.
     *
     * @param instance the IndentationCheck instance using this handler
     */
    public VerticalAlignmentHandler(final IndentationCheck instance) {
        indentCheck = instance;
    }

    /**
     * Checks if the given AST node is vertically aligned with a reference
     * expression in the same construct.
     *
     * @param node the node to check
     * @return true if the node's column matches an allowed alignment column
     */
    public boolean isVerticallyAligned(final DetailAST node) {
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
    private Set<Integer> getAllowedVerticalAlignmentColumns(final DetailAST node) {
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
            getAllowedColumnForTryResource(node),
            getAllowedColumnForExpressionInArrayInit(node)
            ).forEach(columns::addAll);
        return columns;
    }

    private Set<Integer> getAllowedColumnForParameterDefinition(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForExpressionInElist(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForAnnotationMemberValuePair(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForArithmeticBooleanExpression(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForTernaryExpression(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForCommaSeparatedVar(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForChainedCallOrField(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForForLoopParts(
            final DetailAST node) {
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

    private Set<Integer> getAllowedColumnForTryResource(
            final DetailAST node) {
        Set<Integer> result = new HashSet<>();
        findAncestorByPredicate(
                node,
                ancestor -> TokenUtil.isOfType(ancestor, TokenTypes.RESOURCE)
                        && isAtSamePosition(node, getTopLeftmostNodeInTree(ancestor))
        ).ifPresent(resource -> {
            DetailAST prevSibling = resource.getPreviousSibling();
            if (TokenUtil.isOfType(prevSibling, TokenTypes.SEMI)) {
                DetailAST prevResource = prevSibling.getPreviousSibling();
                if (TokenUtil.isOfType(prevResource, TokenTypes.RESOURCE)) {
                    DetailAST resources = resource.getParent();
                    if (TokenUtil.isOfType(resources, TokenTypes.RESOURCES)) {
                        DetailAST firstResource =
                                resources.findFirstToken(TokenTypes.RESOURCE);
                        if (firstResource != null) {
                            result.add(getTopLeftmostNodeInTree(firstResource)
                                    .getColumnNo());
                        }
                    }
                }
            }
        });
        return result;
    }

    private Set<Integer> getAllowedColumnForExpressionInArrayInit(
        final DetailAST node) {
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
        final DetailAST node, final Predicate<DetailAST> predicate) {
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
    private static boolean isAtSamePosition(final DetailAST node1, final DetailAST node2) {
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
    private DetailAST getTopLeftmostNodeInTree(final DetailAST root) {
        DetailAST result = topLeftmostCache.get(root);
        if (result == null) {
            DetailAST best = root;
            DetailAST child = root.getFirstChild();
            while (child != null) {
                DetailAST childBest = getTopLeftmostNodeInTree(child);
                if (childBest.getLineNo() < best.getLineNo()
                    || childBest.getLineNo() == best.getLineNo()
                    && childBest.getColumnNo() < best.getColumnNo()) {
                    best = childBest;
                }
                child = child.getNextSibling();
            }
            topLeftmostCache.put(root, best);
            result = best;
        }
        return result;
    }

}
