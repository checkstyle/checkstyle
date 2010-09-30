////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2004  Oliver Burn
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
    /** default maximum number of methods */
    private static final int DEFAULT_MAX_METHODS = 20;

    /** default allowed number of methods. */
    private int mMax = DEFAULT_MAX_METHODS;

    /**
     * Marker class used to collect data about the number of methods per
     * class. Objects of this class are used on the Stack to count the
     * methods for each class and layer.
     */
    private static class MethodCounter
    {
        private final EnumMap<Scope, Integer> mCounts =
            new EnumMap<Scope, Integer>(Scope.class);

        void increment(Scope aScope)
        {
            mCounts.put(aScope, 1 + value(aScope));
        }

        void clearAll()
        {
            mCounts.clear();
        }

        int value(Scope aScope)
        {
            final Integer value = mCounts.get(aScope);
            return (null == value) ? 0 : value;
        }
    };

    /**
     * To be able to trace also inner-classes correctly we need a stack.
     */
    private FastStack<MethodCounter> mCounters =
        new FastStack<MethodCounter>();

    /**
     * Are methods with package visibility (default) subject to the
     * limitation?
     */
    private boolean mCheckDefaultMethods;

    /**
     * Are methods with private visibility subject to the limitation?
     */
    private boolean mCheckPrivateMethods;

    /**
     * Are methods with protected visibility subject to the limitation?
     */
    private boolean mCheckProtectedMethods;

    /**
     * Are methods with public visibility subject to the limitation?
     */
    private boolean mCheckPublicMethods = true;

    /**
     * Give user a chance to configure max in the config file.
     * @param aMax
     *            the user specified maximum parsed from configuration
     *            property.
     */
    public void setMax(int aMax)
    {
        mMax = aMax;
    }

    /**
     * Give user a chance to configure checkDefaultMethods in the config file.
     * @param aCheckDefaultMethods
     *            <code>true</code> if package default visible methods
     *            should be limited.
     */
    public void setCheckDefaultMethods(boolean aCheckDefaultMethods)
    {
        mCheckDefaultMethods = aCheckDefaultMethods;
    }

    /**
     * Give user a chance to configure checkPrivateMethods in the config file.
     * @param aCheckPrivateMethods
     *            <code>true</code> if private methods should be limited.
     */
    public void setCheckPrivateMethods(boolean aCheckPrivateMethods)
    {
        mCheckPrivateMethods = aCheckPrivateMethods;
    }

    /**
     * Give user a chance to configure checkDefaultMethods in the config file.
     * @param aCheckProtectedMethods
     *            <code>true</code> if protected methods should be limited.
     */
    public void setCheckProtectedMethods(boolean aCheckProtectedMethods)
    {
        mCheckProtectedMethods = aCheckProtectedMethods;
    }

    /**
     * Give user a chance to configure checkDefaultMethods in the config file.
     * @param aCheckPublicMethods
     *            <code>true</code> if private methods should be limited.
     */
    public void setCheckPublicMethods(boolean aCheckPublicMethods)
    {
        mCheckPublicMethods = aCheckPublicMethods;
    }

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
            initTypeDef();
            resetCounters();
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
            checkCounters(aAST);
            cleanupTypeDef();
        }
    }

    /**
     * Called on entering a type-definition, resets all method-counters for
     * the actual type.
     */
    private void resetCounters()
    {
        final MethodCounter actualCounter = mCounters.peek();
        actualCounter.clearAll();
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
     * @param aAst
     *            The type-subtree of the AST(used to extract the line-number
     *            for the error-message.
     */
    private void checkCounters(DetailAST aAst)
    {
        final MethodCounter actualCounter = mCounters.peek();
        if (mCheckDefaultMethods) {
            if (actualCounter.value(Scope.PACKAGE) > mMax) {
                log(aAst.getLineNo(), "too.many.defaultMethods",
                    actualCounter.value(Scope.PACKAGE), mMax);
            }
        }
        if (mCheckPrivateMethods) {
            if (actualCounter.value(Scope.PRIVATE) > mMax) {
                log(aAst.getLineNo(), "too.many.privateMethods",
                    actualCounter.value(Scope.PRIVATE), mMax);
            }
        }
        if (mCheckProtectedMethods) {
            if (actualCounter.value(Scope.PROTECTED) > mMax) {
                log(aAst.getLineNo(), "too.many.protectedMethods",
                    actualCounter.value(Scope.PROTECTED), mMax);
            }
        }
        if (mCheckPublicMethods) {
            if (actualCounter.value(Scope.PUBLIC) > mMax) {
                log(aAst.getLineNo(), "too.many.publicMethods",
                    actualCounter.value(Scope.PUBLIC), mMax);
            }
        }
    }

    /**
     * On entering a type-definition we push a new counter-object to the
     * stack. This will then be used to count this types methods.
     */
    private void initTypeDef()
    {
        mCounters.push(new MethodCounter());
    }

    /**
     * After a type-definition is processed completely the counter-object is
     * removed.
     */
    private void cleanupTypeDef()
    {
        mCounters.pop();
    }
}