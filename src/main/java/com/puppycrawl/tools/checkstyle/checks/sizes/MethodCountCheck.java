////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.sizes;

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
        private final EnumMap<Scope, Integer> counts =
            new EnumMap<Scope, Integer>(Scope.class);
        /** indicated is an interface, in which case all methods are public */
        private final boolean inInterface;
        /** tracks the total. */
        private int total;

        /**
         * Creates an interface.
         * @param inInterface indicated if counter for an interface. In which
         *        case, add all counts as public methods.
         */
        MethodCounter(boolean inInterface)
        {
            this.inInterface = inInterface;
        }

        /**
         * Increments to counter by one for the supplied scope.
         * @param scope the scope counter to increment.
         */
        void increment(Scope scope)
        {
            total++;
            if (inInterface) {
                counts.put(Scope.PUBLIC, 1 + value(Scope.PUBLIC));
            }
            else {
                counts.put(scope, 1 + value(scope));
            }
        }

        /**
         * @return the value of a scope counter
         * @param scope the scope counter to get the value of
         */
        int value(Scope scope)
        {
            final Integer value = counts.get(scope);
            return (null == value) ? 0 : value;
        }

        /** @return the total number of methods. */
        int getTotal()
        {
            return total;
        }
    };

    /** default maximum number of methods */
    private static final int DEFAULT_MAX_METHODS = 100;
    /** Maximum private methods. */
    private int maxPrivate = DEFAULT_MAX_METHODS;
    /** Maximum package methods. */
    private int maxPackage = DEFAULT_MAX_METHODS;
    /** Maximum protected methods. */
    private int maxProtected = DEFAULT_MAX_METHODS;
    /** Maximum public methods. */
    private int maxPublic = DEFAULT_MAX_METHODS;
    /** Maximum total number of methods. */
    private int maxTotal = DEFAULT_MAX_METHODS;
    /** Maintains stack of counters, to support inner types. */
    private final FastStack<MethodCounter> counters =
        new FastStack<MethodCounter>();

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if ((TokenTypes.CLASS_DEF == ast.getType())
            || (TokenTypes.INTERFACE_DEF == ast.getType())
            || (TokenTypes.ENUM_CONSTANT_DEF == ast.getType())
            || (TokenTypes.ENUM_DEF == ast.getType()))
        {
            counters.push(new MethodCounter(
                TokenTypes.INTERFACE_DEF == ast.getType()));
        }
        else if (TokenTypes.METHOD_DEF == ast.getType()) {
            raiseCounter(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast)
    {
        if ((TokenTypes.CLASS_DEF == ast.getType())
            || (TokenTypes.INTERFACE_DEF == ast.getType())
            || (TokenTypes.ENUM_CONSTANT_DEF == ast.getType())
            || (TokenTypes.ENUM_DEF == ast.getType()))
        {
            final MethodCounter counter = counters.pop();
            checkCounters(counter, ast);
        }
    }

    /**
     * Determine the visibility modifier and raise the corresponding counter.
     * @param method
     *            The method-subtree from the AbstractSyntaxTree.
     */
    private void raiseCounter(DetailAST method)
    {
        final MethodCounter actualCounter = counters.peek();
        final DetailAST temp = method.findFirstToken(TokenTypes.MODIFIERS);
        final Scope scope = ScopeUtils.getScopeFromMods(temp);
        actualCounter.increment(scope);
    }

    /**
     * Check the counters and report violations.
     * @param counter the method counters to check
     * @param ast to report errors against.
     */
    private void checkCounters(MethodCounter counter, DetailAST ast)
    {
        checkMax(maxPrivate, counter.value(Scope.PRIVATE),
                 "too.many.privateMethods", ast);
        checkMax(maxPackage, counter.value(Scope.PACKAGE),
                 "too.many.packageMethods", ast);
        checkMax(maxProtected, counter.value(Scope.PROTECTED),
                 "too.many.protectedMethods", ast);
        checkMax(maxPublic, counter.value(Scope.PUBLIC),
                 "too.many.publicMethods", ast);
        checkMax(maxTotal, counter.getTotal(), "too.many.methods", ast);
    }

    /**
     * Utility for reporting if a maximum has been exceeded.
     * @param max the maximum allowed value
     * @param value the actual value
     * @param msg the message to log. Takes two arguments of value and maximum.
     * @param ast the AST to associate with the message.
     */
    private void checkMax(int max, int value, String msg, DetailAST ast)
    {
        if (max < value) {
            log(ast.getLineNo(), msg, value, max);
        }
    }

    /**
     * Sets the maximum allowed <code>private</code> methods per type.
     * @param value the maximum allowed.
     */
    public void setMaxPrivate(int value)
    {
        maxPrivate = value;
    }

    /**
     * Sets the maximum allowed <code>package</code> methods per type.
     * @param value the maximum allowed.
     */
    public void setMaxPackage(int value)
    {
        maxPackage = value;
    }

    /**
     * Sets the maximum allowed <code>protected</code> methods per type.
     * @param value the maximum allowed.
     */
    public void setMaxProtected(int value)
    {
        maxProtected = value;
    }

    /**
     * Sets the maximum allowed <code>public</code> methods per type.
     * @param value the maximum allowed.
     */
    public void setMaxPublic(int value)
    {
        maxPublic = value;
    }

    /**
     * Sets the maximum total methods per type.
     * @param value the maximum allowed.
     */
    public void setMaxTotal(int value)
    {
        maxTotal = value;
    }
}
