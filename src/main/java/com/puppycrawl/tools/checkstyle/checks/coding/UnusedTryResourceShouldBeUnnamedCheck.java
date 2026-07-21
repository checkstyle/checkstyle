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

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Ensures that try-with-resources resource variables that are not used
 * are declared as an unnamed variable.
 * </div>
 *
 * <p>
 * Rationale:
 * </p>
 * <ul>
 *     <li>
 *         Improves code readability by clearly indicating which resources are unused.
 *     </li>
 *     <li>
 *         Follows Java conventions for denoting unused variables with an underscore
 *         ({@code _}).
 *     </li>
 * </ul>
 *
 * <p>
 * Only declared resources inside the try-with-resources parentheses are checked
 * (i.e. {@code var a = lock()} or {@code AutoCloseable a = lock()}).
 * Resources that are referenced but not declared inside the try
 * (e.g. {@code try (releaser) { }}) are never flagged, because those resources
 * cannot be replaced with {@code _}.
 * </p>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/unnamed-jls.html">
 * Java Language Specification</a> for more information about unnamed variables.
 * </p>
 *
 * <p>
 * <b>Attention</b>: This check should be activated only on source code
 * that is compiled by jdk21 or higher;
 * unnamed variables came out as a preview feature in Java 21 and
 * became a standard part of the language in Java 22.
 * </p>
 *
 * @since 13.5.0
 */
@FileStatefulCheck
public class UnusedTryResourceShouldBeUnnamedCheck extends AbstractCheck {

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_UNUSED_TRY_RESOURCE = "unused.try.resource";

    /**
     * The unnamed variable identifier introduced in Java 21.
     */
    private static final String UNNAMED_VARIABLE_IDENTIFIER = "_";

    /**
     * Parent token types for an {@link TokenTypes#IDENT} that indicate the identifier
     * is <em>not</em> a plain variable reference and should therefore be excluded from
     * "used" detection.
     */
    private static final int[] INVALID_RESOURCE_IDENT_PARENTS = {
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * A stack of per-try resource-detail lists.
     */
    private final Deque<Deque<TryResourceDetails>> tryResources = new ArrayDeque<>();

    /**
     * Creates a new {@code UnusedTryResourceShouldBeUnnamedCheck} instance.
     */
    public UnusedTryResourceShouldBeUnnamedCheck() {
        // no code by default
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
            TokenTypes.LITERAL_TRY,
            TokenTypes.IDENT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        tryResources.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_TRY) {
            tryResources.push(collectTrackedResources(ast));
        }
        else if (isResourceUsageCandidate(ast)
                && !isShadowedByCatchParameter(ast)) {
            tryResources.stream()
                .flatMap(Deque::stream)
                .filter(resource -> resource.getName().equals(ast.getText()))
                .findFirst()
                .ifPresent(TryResourceDetails::registerAsUsed);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_TRY) {
            final Deque<TryResourceDetails> resources = tryResources.peek();
            for (TryResourceDetails resource : resources) {
                if (!resource.isUsed()) {
                    log(resource.getIdentToken(),
                            MSG_UNUSED_TRY_RESOURCE,
                            resource.getName());
                }
            }
            tryResources.pop();
        }
    }

    /**
     * Collects all tracked resources from the {@code RESOURCE_SPECIFICATION} of a
     * try-with-resources statement.
     *
     * @param tryAst the {@link TokenTypes#LITERAL_TRY} token
     * @return a deque of {@link TryResourceDetails} for trackable resources;
     *         never {@code null}, but may be empty for plain try statements
     */
    private static Deque<TryResourceDetails> collectTrackedResources(DetailAST tryAst) {
        final Deque<TryResourceDetails> resources = new ArrayDeque<>();
        final DetailAST resourceSpec =
                tryAst.findFirstToken(TokenTypes.RESOURCE_SPECIFICATION);
        if (resourceSpec != null) {
            final DetailAST resourcesNode =
                    resourceSpec.findFirstToken(TokenTypes.RESOURCES);

            TokenUtil.forEachChild(resourcesNode, TokenTypes.RESOURCE, child -> {
                final boolean isDeclared = child.findFirstToken(TokenTypes.TYPE) != null;
                if (isDeclared) {
                    final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                    if (!UNNAMED_VARIABLE_IDENTIFIER.equals(ident.getText())) {
                        resources.addLast(new TryResourceDetails(ident));
                    }
                }
            });
        }
        return resources;
    }

    /**
     * Determines whether an {@link TokenTypes#IDENT} token is a candidate for being
     * a <em>use</em> of a tracked try resource.
     *
     * @param identAst the {@link TokenTypes#IDENT} token to inspect
     * @return {@code true} if the token could represent a reference to a resource variable
     */
    private static boolean isResourceUsageCandidate(DetailAST identAst) {
        return !isResourceDeclarationIdent(identAst)
                && (!TokenUtil.isOfType(identAst.getParent(), INVALID_RESOURCE_IDENT_PARENTS)
                        || isObjectReferenceInDot(identAst));
    }

    /**
     * Returns {@code true} when {@code identAst} is shadowed by a catch parameter
     * of an immediately enclosing {@link TokenTypes#LITERAL_CATCH} block.
     *
     * @param identAst the {@link TokenTypes#IDENT} token to inspect
     * @return {@code true} if a catch parameter with the same name is in scope
     */
    private static boolean isShadowedByCatchParameter(DetailAST identAst) {
        boolean shadowed = false;
        DetailAST ancestor = identAst;
        while (ancestor != null) {
            if (ancestor.getType() == TokenTypes.LITERAL_CATCH) {
                final DetailAST paramDef =
                        ancestor.findFirstToken(TokenTypes.PARAMETER_DEF);
                final DetailAST paramIdent =
                        paramDef.findFirstToken(TokenTypes.IDENT);
                shadowed = paramIdent.getText().equals(identAst.getText());
                break;
            }
            ancestor = ancestor.getParent();
        }
        return shadowed;
    }

    /**
     * Returns {@code true} when {@code identAst} is the variable-name token inside a
     * {@link TokenTypes#RESOURCE} node (i.e. the declaration site, not a use).
     *
     * @param identAst the {@link TokenTypes#IDENT} token
     * @return {@code true} if this IDENT is the name in a resource declaration/reference
     */
    private static boolean isResourceDeclarationIdent(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.RESOURCE
            && parent.findFirstToken(TokenTypes.TYPE) != null;
    }

    /**
     * Returns {@code true} when {@code identAst} is the <em>first</em> child of a
     * {@link TokenTypes#DOT} node, meaning it is the object reference in an expression
     * such as {@code a.close()} — a genuine use of the variable.
     *
     * @param identAst the {@link TokenTypes#IDENT} token
     * @return {@code true} if the IDENT is the left-hand operand of a dot expression
     */
    private static boolean isObjectReferenceInDot(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return parent.getType() == TokenTypes.DOT
                && identAst.equals(parent.getFirstChild());
    }

    /**
     * Maintains tracking information about a single try-with-resources resource.
     */
    private static final class TryResourceDetails {

        /** The name of the resource variable. */
        private final String name;

        /**
         * The {@link TokenTypes#IDENT} token for the variable name.
         * Used as the violation position.
         */
        private final DetailAST identToken;

        /** Whether the resource has been referenced within the try scope. */
        private boolean used;

        /**
         * Creates a new instance tracking the resource whose name-token is
         * {@code identToken}.
         *
         * @param identToken the {@link TokenTypes#IDENT} token for the resource name
         */
        private TryResourceDetails(DetailAST identToken) {
            name = identToken.getText();
            this.identToken = identToken;
        }

        /**
         * Marks this resource as having been referenced (used) in the try scope.
         */
        private void registerAsUsed() {
            used = true;
        }

        /**
         * Returns the name of the resource variable.
         *
         * @return variable name
         */
        private String getName() {
            return name;
        }

        /**
         * Returns the {@link TokenTypes#IDENT} token used to report violations.
         *
         * @return IDENT token
         */
        private DetailAST getIdentToken() {
            return identToken;
        }

        /**
         * Returns whether this resource has been referenced in the try scope.
         *
         * @return {@code true} if used
         */
        private boolean isUsed() {
            return used;
        }
    }

}
