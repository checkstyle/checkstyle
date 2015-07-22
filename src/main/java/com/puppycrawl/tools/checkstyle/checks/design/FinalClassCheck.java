////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that class which has only private ctors
 * is declared as final.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="FinalClass"/&gt;
 * </pre>
 * @author o_sukhodolsky
 */
public class FinalClassCheck
    extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "final.class";

    /** Keeps ClassDesc objects for stack of declared classes. */
    private final Deque<ClassDesc> classes = new ArrayDeque<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);

        if (ast.getType() == TokenTypes.CLASS_DEF) {
            final boolean isFinal = modifiers.branchContains(TokenTypes.FINAL);
            final boolean isAbstract = modifiers.branchContains(TokenTypes.ABSTRACT);
            classes.push(new ClassDesc(isFinal, isAbstract));
        }
        else if (!ScopeUtils.inEnumBlock(ast)) { //ctors in enums don't matter
            final ClassDesc desc = classes.peek();
            if (modifiers.branchContains(TokenTypes.LITERAL_PRIVATE)) {
                desc.reportPrivateCtor();
            }
            else {
                desc.reportNonPrivateCtor();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() != TokenTypes.CLASS_DEF) {
            return;
        }

        final ClassDesc desc = classes.pop();
        if (!desc.isDeclaredAsFinal()
            && !desc.isDeclaredAsAbstract()
            && desc.isWithPrivateCtor()
            && !desc.isWithNonPrivateCtor()) {
            final String className =
                ast.findFirstToken(TokenTypes.IDENT).getText();
            log(ast.getLineNo(), MSG_KEY, className);
        }
    }

    /** maintains information about class' ctors */
    private static final class ClassDesc {
        /** is class declared as final */
        private final boolean declaredAsFinal;

        /** is class declared as abstract */
        private final boolean declaredAsAbstract;

        /** does class have non-provate ctors */
        private boolean withNonPrivateCtor;

        /** does class have private ctors */
        private boolean withPrivateCtor;

        /**
         *  create a new ClassDesc instance.
         *  @param declaredAsFinal indicates if the
         *         class declared as final
         *  @param declaredAsAbstract indicates if the
         *         class declared as abstract
         */
        ClassDesc(boolean declaredAsFinal, boolean declaredAsAbstract) {
            this.declaredAsFinal = declaredAsFinal;
            this.declaredAsAbstract = declaredAsAbstract;
        }

        /** adds private ctor. */
        void reportPrivateCtor() {
            withPrivateCtor = true;
        }

        /** adds non-private ctor. */
        void reportNonPrivateCtor() {
            withNonPrivateCtor = true;
        }

        /**
         *  does class have private ctors.
         *  @return true if class has private ctors
         */
        boolean isWithPrivateCtor() {
            return withPrivateCtor;
        }

        /**
         *  does class have non-private ctors.
         *  @return true if class has non-private ctors
         */
        boolean isWithNonPrivateCtor() {
            return withNonPrivateCtor;
        }

        /**
         *  is class declared as final.
         *  @return true if class is declared as final
         */
        boolean isDeclaredAsFinal() {
            return declaredAsFinal;
        }

        /**
         *  is class declared as abstract.
         *  @return true if class is declared as final
         */
        boolean isDeclaredAsAbstract() {
            return declaredAsAbstract;
        }
    }
}
