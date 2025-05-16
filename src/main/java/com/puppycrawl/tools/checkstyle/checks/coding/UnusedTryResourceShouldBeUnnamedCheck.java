///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Ensures that try resources that are not used are declared as an unnamed variable.
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
 *         Follows Java conventions for denoting unused parameters with an underscore ({@code _}).
 *     </li>
 * </ul>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/unnamed-jls.html">
 * Java Language Specification</a> for more information about unnamed variables.
 * </p>
 *
 * <p>
 * <b>Attention</b>: Unnamed variables are available as a preview feature in Java 21,
 * and became an official part of the language in Java 22.
 * This check should be activated only on source code which meets those requirements.
 * </p>
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
 * {@code unused.try.resource}
 * </li>
 * </ul>
 *
 * @since 10.24.0
 */
@FileStatefulCheck
public class UnusedTryResourceShouldBeUnnamedCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TRY_RESOURCE = "unused.try.resource";

    /**
     * Invalid parents of the try resource identifier.
     * These are tokens that can not be parents for a try
     * resource identifier.
     */
    private static final int[] INVALID_TRY_RESOURCE_IDENT_PARENTS = {
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * Keeps track of the try resources in a block.
     */
    private final Deque<TryResourceDetails> tryResources = new ArrayDeque<>();

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
            TokenTypes.SLIST,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        tryResources.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_TRY) {
            final DetailAST resourceSpec = ast.findFirstToken(TokenTypes.RESOURCE_SPECIFICATION);
            if (resourceSpec != null) {
                final TryResourceDetails tryResource = new TryResourceDetails(resourceSpec);
                tryResources.push(tryResource);
            }
        }
        else if (isTryResourceIdentifierCandidate(ast) && !CheckUtil.isLeftHandOfAssignment(ast)) {
            // we do not count reassignment as usage
            tryResources.stream()
                    .filter(resource -> resource.getName().equals(ast.getText()))
                    .findFirst()
                    .ifPresent(TryResourceDetails::registerAsUsed);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (tryResources.peek() != null) {
            if (ast.getType() == TokenTypes.SLIST
                && ast.getParent().getType() == TokenTypes.LITERAL_TRY) {
                final Optional<TryResourceDetails> unusedTryResource =
                        Optional.ofNullable(tryResources.peek())
                                .filter(resource -> !resource.isUsed())
                                .filter(resource -> !"_".equals(resource.getName()));

                unusedTryResource.ifPresent(resource -> {
                    log(resource.getResourceDefinition(),
                            MSG_UNUSED_TRY_RESOURCE,
                            resource.getName());
                });
            }
            if (ast.getType() == TokenTypes.LITERAL_TRY) {
                tryResources.pop();
            }
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}
     * and check if it is a candidate for a try resource.
     *
     * @param identifierAst token representing {@link TokenTypes#IDENT}
     * @return true if the given {@link TokenTypes#IDENT} could be a try resource.
     */
    private static boolean isTryResourceIdentifierCandidate(DetailAST identifierAst) {
        // we should ignore the ident if it is in the exception declaration
        return identifierAst.getParent().getType() != TokenTypes.RESOURCE
            && (!TokenUtil.isOfType(identifierAst.getParent(), INVALID_TRY_RESOURCE_IDENT_PARENTS)
            || CheckUtil.isMethodInvocation(identifierAst));
    }

    /**
     * Maintains information about the try resource.
     */
    private static final class TryResourceDetails {

        /**
         * The name of the try resource.
         */
        private final String name;

        /**
         * Ast of type {@link TokenTypes#RESOURCE} to use it when logging.
         */
        private final DetailAST resourceDefinition;

        /**
         * Is the variable used.
         */
        private boolean used;

        /**
         * Create a new try resource instance.
         *
         * @param enclosingResourceSpec ast of type {@link TokenTypes#RESOURCE_SPECIFICATION}
         */
        private TryResourceDetails(DetailAST enclosingResourceSpec) {
            final DetailAST resourcesDefinition =
                    enclosingResourceSpec.findFirstToken(TokenTypes.RESOURCES);
            resourceDefinition = resourcesDefinition.findFirstToken(TokenTypes.RESOURCE);
            name = resourceDefinition.findFirstToken(TokenTypes.IDENT).getText();
        }

        /**
         * Register the try resource as used.
         */
        private void registerAsUsed() {
            used = true;
        }

        /**
         * Get the name of the try resource.
         *
         * @return the name of the try resource.
         */
        private String getName() {
            return name;
        }

        /**
         * Check if the try resource is used.
         *
         * @return true if the try resource is used.
         */
        private boolean isUsed() {
            return used;
        }

        /**
         * Get the resource definition token of the try resource
         * represented by ast of type {@link TokenTypes#RESOURCE}.
         *
         * @return the ast of type {@link TokenTypes#RESOURCE}
         */
        private DetailAST getResourceDefinition() {
            return resourceDefinition;
        }
    }
}
