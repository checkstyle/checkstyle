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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.google.common.collect.Maps;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for handlers. Looks up constructor via reflection.
 *
 * @author jrichard
 */
public class HandlerFactory
{
    /** Logger for indentation check */
    private static final Log LOG =
        LogFactory.getLog("com.puppycrawl.tools.checkstyle.checks.indentation");

    /**
     * Registered handlers.
     */
    private final Map<Integer, Constructor<?>> mTypeHandlers =
        Maps.newHashMap();

    /**
     * registers a handler
     *
     * @param aType
     *                type from TokenTypes
     * @param aHandlerClass
     *                the handler to register
     */
    private void register(int aType, Class<?> aHandlerClass)
    {
        try {
            final Constructor<?> ctor = aHandlerClass
                    .getConstructor(new Class[] {IndentationCheck.class,
                        DetailAST.class, // current AST
                        ExpressionHandler.class, // parent
                    });
            mTypeHandlers.put(aType, ctor);
        }
        ///CLOVER:OFF
        catch (final NoSuchMethodException e) {
            throw new RuntimeException("couldn't find ctor for "
                                       + aHandlerClass);
        }
        catch (final SecurityException e) {
            LOG.debug("couldn't find ctor for " + aHandlerClass, e);
            throw new RuntimeException("couldn't find ctor for "
                                       + aHandlerClass);
        }
        ///CLOVER:ON
    }

    /** Creates a HandlerFactory. */
    public HandlerFactory()
    {
        register(TokenTypes.CASE_GROUP, CaseHandler.class);
        register(TokenTypes.LITERAL_SWITCH, SwitchHandler.class);
        register(TokenTypes.SLIST, SlistHandler.class);
        register(TokenTypes.PACKAGE_DEF, PackageDefHandler.class);
        register(TokenTypes.LITERAL_ELSE, ElseHandler.class);
        register(TokenTypes.LITERAL_IF, IfHandler.class);
        register(TokenTypes.LITERAL_TRY, TryHandler.class);
        register(TokenTypes.LITERAL_CATCH, CatchHandler.class);
        register(TokenTypes.LITERAL_FINALLY, FinallyHandler.class);
        register(TokenTypes.LITERAL_DO, DoWhileHandler.class);
        register(TokenTypes.LITERAL_WHILE, WhileHandler.class);
        register(TokenTypes.LITERAL_FOR, ForHandler.class);
        register(TokenTypes.METHOD_DEF, MethodDefHandler.class);
        register(TokenTypes.CTOR_DEF, MethodDefHandler.class);
        register(TokenTypes.CLASS_DEF, ClassDefHandler.class);
        register(TokenTypes.ENUM_DEF, ClassDefHandler.class);
        register(TokenTypes.OBJBLOCK, ObjectBlockHandler.class);
        register(TokenTypes.INTERFACE_DEF, ClassDefHandler.class);
        register(TokenTypes.IMPORT, ImportHandler.class);
        register(TokenTypes.ARRAY_INIT, ArrayInitHandler.class);
        register(TokenTypes.METHOD_CALL, MethodCallHandler.class);
        register(TokenTypes.CTOR_CALL, MethodCallHandler.class);
        register(TokenTypes.LABELED_STAT, LabelHandler.class);
        register(TokenTypes.STATIC_INIT, StaticInitHandler.class);
        register(TokenTypes.INSTANCE_INIT, SlistHandler.class);
        register(TokenTypes.ASSIGN, AssignHandler.class);
        register(TokenTypes.PLUS_ASSIGN, AssignHandler.class);
        register(TokenTypes.MINUS_ASSIGN, AssignHandler.class);
        register(TokenTypes.STAR_ASSIGN, AssignHandler.class);
        register(TokenTypes.DIV_ASSIGN, AssignHandler.class);
        register(TokenTypes.MOD_ASSIGN, AssignHandler.class);
        register(TokenTypes.SR_ASSIGN, AssignHandler.class);
        register(TokenTypes.BSR_ASSIGN, AssignHandler.class);
        register(TokenTypes.SL_ASSIGN, AssignHandler.class);
        register(TokenTypes.BAND_ASSIGN, AssignHandler.class);
        register(TokenTypes.BXOR_ASSIGN, AssignHandler.class);
        register(TokenTypes.BOR_ASSIGN, AssignHandler.class);
        register(TokenTypes.VARIABLE_DEF, MemberDefHandler.class);
        register(TokenTypes.LITERAL_NEW, NewHandler.class);
    }

    /**
     * Returns true if this type (form TokenTypes) is handled.
     *
     * @param aType type from TokenTypes
     * @return true if handler is registered, false otherwise
     */
    public boolean isHandledType(int aType)
    {
        final Set<Integer> typeSet = mTypeHandlers.keySet();
        return typeSet.contains(aType);
    }

    /**
     * Gets list of registered handler types.
     *
     * @return int[] of TokenType types
     */
    public int[] getHandledTypes()
    {
        final Set<Integer> typeSet = mTypeHandlers.keySet();
        final int[] types = new int[typeSet.size()];
        int index = 0;
        for (final Integer val : typeSet) {
            types[index++] = val;
        }

        return types;
    }

    /**
     * Get the handler for an AST.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           ast to handle
     * @param aParent        the handler parent of this AST
     *
     * @return the ExpressionHandler for aAst
     */
    public ExpressionHandler getHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        final ExpressionHandler handler =
            mCreatedHandlers.get(aAst);
        if (handler != null) {
            return handler;
        }

        if (aAst.getType() == TokenTypes.METHOD_CALL) {
            return createMethodCallHandler(aIndentCheck, aAst, aParent);
        }

        ExpressionHandler expHandler = null;
        try {
            final Constructor<?> handlerCtor =
                mTypeHandlers.get(aAst.getType());
            if (handlerCtor != null) {
                expHandler = (ExpressionHandler) handlerCtor.newInstance(
                        aIndentCheck, aAst, aParent);
            }
        }
        ///CLOVER:OFF
        catch (final InstantiationException e) {
            LOG.debug("couldn't instantiate constructor for " + aAst, e);
            throw new RuntimeException("couldn't instantiate constructor for "
                                       + aAst);
        }
        catch (final IllegalAccessException e) {
            LOG.debug("couldn't access constructor for " + aAst, e);
            throw new RuntimeException("couldn't access constructor for "
                                       + aAst);
        }
        catch (final InvocationTargetException e) {
            LOG.debug("couldn't instantiate constructor for " + aAst, e);
            throw new RuntimeException("couldn't instantiate constructor for "
                                       + aAst);
        }
        if (expHandler == null) {
            throw new RuntimeException("no handler for type " + aAst.getType());
        }
        ///CLOVER:ON
        return expHandler;
    }

    /**
     * Create new instance of handler for METHOD_CALL.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           ast to handle
     * @param aParent        the handler parent of this AST
     *
     * @return new instance.
     */
    ExpressionHandler createMethodCallHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        ExpressionHandler theParent = aParent;
        DetailAST ast = aAst.getFirstChild();
        while ((ast != null) && (ast.getType() == TokenTypes.DOT)) {
            ast = ast.getFirstChild();
        }
        if ((ast != null) && isHandledType(ast.getType())) {
            theParent = getHandler(aIndentCheck, ast, theParent);
            mCreatedHandlers.put(ast, theParent);
        }
        return new MethodCallHandler(aIndentCheck, aAst, theParent);
    }

    /** Clears cache of created handlers. */
    void clearCreatedHandlers()
    {
        mCreatedHandlers.clear();
    }

    /** cache for created method call handlers */
    private final Map<DetailAST, ExpressionHandler> mCreatedHandlers =
        Maps.newHashMap();
}
