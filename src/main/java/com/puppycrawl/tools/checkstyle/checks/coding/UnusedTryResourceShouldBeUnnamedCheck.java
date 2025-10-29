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
import java.util.List;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
 * @since 12.2.0
 */
@FileStatefulCheck
public class UnusedTryResourceShouldBeUnnamedCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TRY_RESOURCE = "unused.try.resource";

    /**
     * Keeps track of the try resources in a block.
     */
    private final Deque<TryResourceDetails> tryResources = new ArrayDeque<>();

    /**
     * Keeps track of the try resource nesting level.
     */
    private int nestingLevel;

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
            nestingLevel++;
            final DetailAST resourceSpec = ast.findFirstToken(TokenTypes.RESOURCE_SPECIFICATION);
            if (resourceSpec != null) {
                final DetailAST resourcesDefinition =
                        resourceSpec.findFirstToken(TokenTypes.RESOURCES);
                DetailAST resource = resourcesDefinition.findFirstToken(TokenTypes.RESOURCE);
                while (resource != null) {
                    if (resource.getType() == TokenTypes.RESOURCE) {
                        final TryResourceDetails tryResource =
                                new TryResourceDetails(resource, nestingLevel);
                        tryResources.push(tryResource);
                    }
                    resource = resource.getNextSibling();
                }
            }
        }
        else if (isTryResourceIdentifierCandidate(ast)) {
            final Optional<TryResourceDetails> usedResource = tryResources.stream()
                    .filter(resource -> resource.getName().equals(ast.getText()))
                    .findFirst();
            usedResource.ifPresent(tryResources::remove);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getParent().getType() == TokenTypes.LITERAL_TRY) {
            final List<TryResourceDetails> unusedTryResource = tryResources.stream()
                    .filter(
                            resource -> {
                                return resource.getNestingLevel() == nestingLevel
                                        && !"_".equals(resource.getName());
                            }
                    )
                    .toList();

            unusedTryResource.forEach(resource -> {
                log(resource.getResourceDefinition(),
                        MSG_UNUSED_TRY_RESOURCE,
                        resource.getName());
            });
            nestingLevel--;
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
        return identifierAst.getParent().getType() != TokenTypes.RESOURCE;
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
         * The nesting level of the try resource.
         */
        private final int nestingLevel;

        /**
         * Create a new try resource instance.
         *
         * @param resource ast of type {@link TokenTypes#RESOURCE}
         * @param nestingLevel nesting the level of the try resource.
         */
        private TryResourceDetails(DetailAST resource, int nestingLevel) {
            resourceDefinition = resource;
            name = extractResourceName(resource);
            this.nestingLevel = nestingLevel;
        }

        /**
         * Extracts the resource name from the AST node.
         * Attempts to find the identifier at the current level first,
         * then searches for the first child if not found.
         *
         * @param resource the AST node representing the resource
         * @return the resource name, or null if no identifier is found
         */
        private static String extractResourceName(DetailAST resource) {
            final String name;
            final DetailAST identifier = resource.findFirstToken(TokenTypes.IDENT);

            if (identifier != null) {
                name = identifier.getText();
            }
            else {
                final DetailAST firstChild = resource.getFirstChild();
                final DetailAST childIdentifier = firstChild.findFirstToken(TokenTypes.IDENT);
                name = childIdentifier.getText();
            }
            return name;
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
         * Get the resource definition token of the try resource
         * represented by ast of type {@link TokenTypes#RESOURCE}.
         *
         * @return the ast of type {@link TokenTypes#RESOURCE}
         */
        private DetailAST getResourceDefinition() {
            return resourceDefinition;
        }

        /**
         * Get the nesting level of the try resource.
         *
         * @return the nesting level of the try resource.
         */
        private int getNestingLevel() {
            return nestingLevel;
        }
    }
}
