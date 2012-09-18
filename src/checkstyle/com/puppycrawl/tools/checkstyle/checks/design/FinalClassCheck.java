////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
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
    extends Check
{
    /** Keeps ClassDesc objects for stack of declared classes. */
    private final FastStack<ClassDesc> mClasses = FastStack.newInstance();

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST modifiers = aAST.findFirstToken(TokenTypes.MODIFIERS);

        if (aAST.getType() == TokenTypes.CLASS_DEF) {
            final boolean isFinal = (modifiers != null)
                    && modifiers.branchContains(TokenTypes.FINAL);
            final boolean isAbstract = (modifiers != null)
                    && modifiers.branchContains(TokenTypes.ABSTRACT);
            mClasses.push(new ClassDesc(isFinal, isAbstract));
        }
        else if (!ScopeUtils.inEnumBlock(aAST)) { //ctors in enums don't matter
            final ClassDesc desc = mClasses.peek();
            if ((modifiers != null)
                && modifiers.branchContains(TokenTypes.LITERAL_PRIVATE))
            {
                desc.reportPrivateCtor();
            }
            else {
                desc.reportNonPrivateCtor();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        if (aAST.getType() != TokenTypes.CLASS_DEF) {
            return;
        }

        final ClassDesc desc = mClasses.pop();
        if (!desc.isDeclaredAsFinal()
            && !desc.isDeclaredAsAbstract()
            && desc.hasPrivateCtor()
            && !desc.hasNonPrivateCtor())
        {
            final String className =
                aAST.findFirstToken(TokenTypes.IDENT).getText();
            log(aAST.getLineNo(), "final.class", className);
        }
    }

    /** maintains information about class' ctors */
    private static final class ClassDesc
    {
        /** is class declared as final */
        private final boolean mDeclaredAsFinal;

        /** is class declared as abstract */
        private final boolean mDeclaredAsAbstract;

        /** does class have non-provate ctors */
        private boolean mHasNonPrivateCtor;

        /** does class have private ctors */
        private boolean mHasPrivateCtor;

        /**
         *  create a new ClassDesc instance.
         *  @param aDeclaredAsFinal indicates if the
         *         class declared as final
         *  @param aDeclaredAsAbstract indicates if the
         *         class declared as abstract
         */
        ClassDesc(boolean aDeclaredAsFinal, boolean aDeclaredAsAbstract)
        {
            mDeclaredAsFinal = aDeclaredAsFinal;
            mDeclaredAsAbstract = aDeclaredAsAbstract;
        }

        /** adds private ctor. */
        void reportPrivateCtor()
        {
            mHasPrivateCtor = true;
        }

        /** adds non-private ctor. */
        void reportNonPrivateCtor()
        {
            mHasNonPrivateCtor = true;
        }

        /**
         *  does class have private ctors.
         *  @return true if class has private ctors
         */
        boolean hasPrivateCtor()
        {
            return mHasPrivateCtor;
        }

        /**
         *  does class have non-private ctors.
         *  @return true if class has non-private ctors
         */
        boolean hasNonPrivateCtor()
        {
            return mHasNonPrivateCtor;
        }

        /**
         *  is class declared as final.
         *  @return true if class is declared as final
         */
        boolean isDeclaredAsFinal()
        {
            return mDeclaredAsFinal;
        }

        /**
         *  is class declared as abstract.
         *  @return true if class is declared as final
         */
        boolean isDeclaredAsAbstract()
        {
            return mDeclaredAsAbstract;
        }

        @Override
        public String toString()
        {
            return this.getClass().getName()
                + "["
                + "final=" + mDeclaredAsFinal
                + " abstract=" + mDeclaredAsAbstract
                + " pctor=" + mHasPrivateCtor
                + " ctor=" + mHasNonPrivateCtor
                + "]";
        }
    }
}
