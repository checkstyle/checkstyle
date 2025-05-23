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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

@StatelessCheck
public class UnnecessaryPermitsClauseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNNECESSARY_PERMITS_CLAUSE = "unnecessary.permits.clause";

    /**
     * Keeps tracks of the number of permitted class, interface and record
     */
    private final Map<SealedClass, Integer> sealedClasses = new HashMap<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        sealedClasses.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST permit = ast.findFirstToken(TokenTypes.PERMITS_CLAUSE);
        if (permit != null) {
            addToSealedClasses(ast, permit);
        }
        final DetailAST extend = ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (extend != null) {
            decFromSealedClasses(extend);
        }
        final DetailAST implement = ast.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        if (implement != null) {
            decFromSealedClassesInterface(implement);
        }
    }

    @Override
    public void finishTree(DetailAST root) {
        for (SealedClass key : sealedClasses.keySet()) {
            if (sealedClasses.get(key) != 0) {
                logViolations(key);
            }
        }
    }

    /**
     * Adds the sealed class ast and number of permitted classes to sealedClasses
     * @param ast ast is a DetailAST of type {@link TokenTypes#CLASS_DEF} or
     * {@link TokenTypes#INTERFACE_DEF}
     * @param permit permit a DetailAST of {@link TokenTypes#PERMITS_CLAUSE}
     */
    private void addToSealedClasses(DetailAST ast, DetailAST permit) {
        DetailAST current = permit.getFirstChild();
        int count = 0;
        while (current != null) {
            if (current.getType() == TokenTypes.IDENT || current.getType() == TokenTypes.DOT) {
                count++;
            }
            current = current.getNextSibling();
        }
        final String name = ast.findFirstToken(TokenTypes.IDENT).getText();
        final SealedClass sealedClass = new SealedClass(permit, name);
        sealedClasses.put(sealedClass, count);
    }

    /**
     * Used to track if the class extends the sealed class
     * @param extend extend is a DetailAST of type {@link TokenTypes#EXTENDS_CLAUSE}
     */
    private void decFromSealedClasses(DetailAST extend) {
        decCountForPermittedClasses(extend.getFirstChild());
    }

    /**
     * Used to track if the interface implements the sealed interface
     * @param implement implement is a DetailAST of type {@link TokenTypes#IMPLEMENTS_CLAUSE}
     */
    private void decFromSealedClassesInterface(DetailAST implement) {
        DetailAST current = implement.getFirstChild();
        while (current != null) {
            if (current.getType() == TokenTypes.IDENT) {
                decCountForPermittedClasses(current);
            }
            current = current.getNextSibling();
        }
    }

    /**
     * Used to change number of permitted classes or interface if it extends sealed class or
     * interface
     * @param ast ast is a DetailAST of type @link TokenTypes#EXTENDS_CLAUSE} or
     * {@link TokenTypes#PERMITS_CLAUSE}
     */
    private void decCountForPermittedClasses(DetailAST ast) {
        final String superClass = ast.getText();
        for (SealedClass sealedClass : sealedClasses.keySet()) {
            if (sealedClass.getName().equals(superClass)) {
                sealedClasses.put(sealedClass, sealedClasses.get(sealedClass) - 1);
            }
        }
    }

    /**
     * Used to log violations
     * @param sealedClass sealedClass is of type SealedClass
     */
    private void logViolations(SealedClass sealedClass) {
        log(sealedClass.getAst(), MSG_UNNECESSARY_PERMITS_CLAUSE);
    }

    /**
     * Used to create an object with instances of DetailAST and that class name
     */
    private class SealedClass {
        private final DetailAST ast;
        private final String name;

        private SealedClass (DetailAST ast, String name) {
            this.ast = ast;
            this.name = name;
        }

        private DetailAST getAst() {
            return ast;
        }

        private String getName() {
            return name;
        }
    }

}
