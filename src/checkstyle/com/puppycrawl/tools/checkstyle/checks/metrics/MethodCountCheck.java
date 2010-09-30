////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.EnumMap;

/**
 * Counts the methods of the type-definition and checks whether this
 * count is higher than the configured limit.
 * @author Alexander Jesse
 * @author Oliver Burn
 */
public final class MethodCountCheck extends Check
{
    /**
     * Marker class used to collect data about the number of methods per
     * class. Objects of this class are used on the Stack to count the
     * methods for each class and layer.
     */
    private static class MethodCounter
    {
        /** Maintains the counts. */
        private final EnumMap<Scope, Integer> mCounts =
            new EnumMap<Scope, Integer>(Scope.class);

        /**
         * Increments to counter by one for the supplied scope.
         * @param aScope the scope counter to increment.
         */
        void increment(Scope aScope)
        {
            mCounts.put(aScope, 1 + value(aScope));
        }

        /**
         * @return the value of a scope counter
         * @param aScope the scope counter to get the value of
         */
        int value(Scope aScope)
        {
            final Integer value = mCounts.get(aScope);
            return (null == value) ? 0 : value;
        }
    };

    /** default maximum number of methods */
    private static final int DEFAULT_MAX_METHODS = 999;
    /** Maximum private methods. */
    private int mMaxPrivate = DEFAULT_MAX_METHODS;
    /** Maximum package methods. */
    private int mMaxPackage = DEFAULT_MAX_METHODS;
    /** Maximum protected methods. */
    private int mMaxProtected = DEFAULT_MAX_METHODS;
    /** Maximum public methods. */
    private int mMaxPublic = DEFAULT_MAX_METHODS;
    /** Maximum total number of methods. */
    private int mMaxTotal = DEFAULT_MAX_METHODS;
    /** Maintains stack of counters, to support inner types. */
    private FastStack<MethodCounter> mCounters =
        new FastStack<MethodCounter>();

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF,
                          TokenTypes.METHOD_DEF, };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if ((TokenTypes.CLASS_DEF == aAST.getType())
            || (TokenTypes.INTERFACE_DEF == aAST.getType()))
        {
            mCounters.push(new MethodCounter());
        }
        else if (TokenTypes.METHOD_DEF == aAST.getType()) {
            raiseCounter(aAST);
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        if ((TokenTypes.CLASS_DEF == aAST.getType())
            || (TokenTypes.INTERFACE_DEF == aAST.getType()))
        {
            final MethodCounter counter = mCounters.pop();
            checkCounters(counter, aAST);
        }
    }

    /**
     * Determine the visibility modifier and raise the corresponding counter.
     * @param aMethod
     *            The method-subtree from the AbstractSyntaxTree.
     */
    private void raiseCounter(DetailAST aMethod)
    {
        final MethodCounter actualCounter = mCounters.peek();
        final DetailAST temp = aMethod.findFirstToken(TokenTypes.MODIFIERS);
        final Scope scope = ScopeUtils.getScopeFromMods(temp);
        actualCounter.increment(scope);
    }

    /**
     * Check the counters and report violations.
     * @param aCounter the method counters to check
     * @param aAst to report errors against.
     */
    private void checkCounters(MethodCounter aCounter, DetailAST aAst)
    {
        checkMax(mMaxPrivate, aCounter.value(Scope.PRIVATE),
                 "too.many.privateMethods", aAst);
        checkMax(mMaxPackage, aCounter.value(Scope.PACKAGE),
                 "too.many.packageMethods", aAst);
        checkMax(mMaxProtected, aCounter.value(Scope.PROTECTED),
                 "too.many.protectedMethods", aAst);
        checkMax(mMaxPublic, aCounter.value(Scope.PUBLIC),
                 "too.many.publicMethods", aAst);
    }

    /**
     * Utility for reporting if a maximum has been exceeded.
     * @param aMax the maximum allowed value
     * @param aValue the actual value
     * @param aMsg the message to log. Takes two arguments of value and maximum.
     * @param aAst the AST to associate with the message.
     */
    private void checkMax(int aMax, int aValue, String aMsg, DetailAST aAst)
    {
        if (aMax < aValue) {
            log(aAst.getLineNo(), aMsg, aValue, aMax);
        }
    }
}
