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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for unnecessary permits clauses in sealed classes and interfaces.
 * </div>
 *
 * <p>
 * In the Java programming language, a sealed type may specify a {@code permits}
 * clause to explicitly list all allowed subclasses. The {@code permits} clause is
 * optional if all permitted types are nested or declared within the same file.
 * </p>
 *
 * <p>
 * This check tracks:
 * <ul>
 *   <li>the permitted types listed in the {@code permits} clause of a sealed type</li>
 *   <li>all subclasses/implementations found in the same file</li>
 * </ul>
 * If all permitted types are found in the same compilation unit, a violation is logged.
 * </p>
 *
 * @since 13.1.0
 */

@FileStatefulCheck
public class UnnecessaryPermitsClauseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PERMIT = "unnecessary.permit.clause";

    /** Map to store sealed type information. */
    private final Map<String, SealedTypeInfo> mapSealedClass = new HashMap<>();

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
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        mapSealedClass.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST permit = ast.findFirstToken(TokenTypes.PERMITS_CLAUSE);
        final String className = ast.findFirstToken(TokenTypes.IDENT).getText();
        if (permit != null) {
            final SealedTypeInfo sealedTypeInfo = mapSealedClass.computeIfAbsent(className,
                    SealedTypeInfo::new);
            sealedTypeInfo.setPermitsAst(permit);
            DetailAST child = permit.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.IDENT) {
                    sealedTypeInfo.addPermittedType(child.getText());
                }
                child = child.getNextSibling();
            }
        }

        final DetailAST extend = ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (extend != null) {
            markFoundClasses(className, extend);
        }

        final DetailAST implemet = ast.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        if (implemet != null) {
            markFoundClasses(className, implemet);
        }
    }

    /**
     * Marks classes found in the file that extend or implement a sealed type.
     * Iterates through the AST node's children to find identifier nodes representing
     * superclasses or interfaces, then records the current class as a found subtype.
     *
     * @param className the name of the class being examined
     * @param currentAst the AST node to traverse
     *     (typically EXTENDS_CLAUSE or IMPLEMENTS_CLAUSE)
     */
    private void markFoundClasses(String className, DetailAST currentAst) {
        DetailAST child = currentAst.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.IDENT) {
                final String superName = child.getText();
                final SealedTypeInfo sealedTypeInfo = mapSealedClass.computeIfAbsent(
                        superName, SealedTypeInfo::new);
                sealedTypeInfo.markFoundType(className);
            }
            child = child.getNextSibling();
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        for (SealedTypeInfo sealedTypeInfo : mapSealedClass.values()) {
            if (sealedTypeInfo.isPermitsUnnecessary()) {
                log(sealedTypeInfo.getPermitsAst(), MSG_PERMIT, sealedTypeInfo.getSealedName());
            }
        }
    }

    /**
     * Holds information about a sealed type and its permitted subtypes.
     * This class tracks both the explicitly permitted types in the permits clause
     * and the actual subtypes found in the same file.
     */
    private static final class SealedTypeInfo {

        /** Name of the sealed class or interface. */
        private final String sealedName;

        /** AST node of PERMITS_CLAUSE. */
        private DetailAST permitsAst;

        /** All types listed in permits clause. */
        private final Set<String> permittedTypes = new HashSet<>();

        /** Types found in this file that extend / implement this sealed type. */
        private final Set<String> foundTypes = new HashSet<>();

        /**
         * Package-private constructor.
         * This class is an internal helper used only by
         * {@link UnnecessaryPermitsClauseCheck}.
         *
         * @param sealedName the name of the sealed class or interface
         */
        private SealedTypeInfo(String sealedName) {
            this.sealedName = sealedName;
        }

        /**
         * Sets the AST node for the permits clause.
         *
         * @param permitsAst the AST node representing the permits clause
         */
        private void setPermitsAst(DetailAST permitsAst) {
            this.permitsAst = permitsAst;
        }

        /**
         * Adds a type name to the list of permitted types
         * from the permits clause.
         *
         * @param name the name of the permitted type
         */
        private void addPermittedType(String name) {
            permittedTypes.add(name);
        }

        /**
         * Marks a type as found in the file
         * (extends/implements this sealed type).
         *
         * @param name the name of the type found in the file
         */
        private void markFoundType(String name) {
            foundTypes.add(name);
        }

        /**
         * Checks if the permits clause is unnecessary.
         * A permits clause is unnecessary when all permitted types are found
         * in the same file, making the clause redundant.
         *
         * @return true if all permitted types were found in the file,
         *     false otherwise
         */
        private boolean isPermitsUnnecessary() {
            return !permittedTypes.isEmpty() && foundTypes.containsAll(permittedTypes);
        }

        /**
         * Gets the AST node for the permits clause.
         *
         * @return the AST node representing the permits clause.
         */
        private DetailAST getPermitsAst() {
            return permitsAst;
        }

        /**
         * Gets the name of the sealed type.
         *
         * @return the name of the sealed class or interface
         */
        private String getSealedName() {
            return sealedName;
        }
    }
}
