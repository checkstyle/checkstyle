///
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
///

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Factory for handlers. Looks up constructor via reflection.
 *
 */
public class HandlerFactory {

    /**
     * Registered handlers.
     */
    private final Map<Integer, Constructor<?>> typeHandlers = new HashMap<>();

    /** Cache for created method call handlers. */
    private final Map<DetailAST, AbstractExpressionHandler> createdHandlers = new HashMap<>();

    /**
     * Creates a HandlerFactory.
     *
     * @noinspection OverlyCoupledMethod
     * @noinspectionreason OverlyCoupledMethod - complex nature of indentation check
     *      requires this coupling
     */
    public HandlerFactory() {
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
        register(TokenTypes.ANNOTATION_ARRAY_INIT, AnnotationArrayInitHandler.class);
        register(TokenTypes.METHOD_CALL, MethodCallHandler.class);
        register(TokenTypes.CTOR_CALL, MethodCallHandler.class);
        register(TokenTypes.SUPER_CTOR_CALL, MethodCallHandler.class);
        register(TokenTypes.LABELED_STAT, LabelHandler.class);
        register(TokenTypes.STATIC_INIT, StaticInitHandler.class);
        register(TokenTypes.INSTANCE_INIT, SlistHandler.class);
        register(TokenTypes.VARIABLE_DEF, MemberDefHandler.class);
        register(TokenTypes.LITERAL_NEW, NewHandler.class);
        register(TokenTypes.INDEX_OP, IndexHandler.class);
        register(TokenTypes.LITERAL_SYNCHRONIZED, SynchronizedHandler.class);
        register(TokenTypes.LAMBDA, LambdaHandler.class);
        register(TokenTypes.ANNOTATION_DEF, ClassDefHandler.class);
        register(TokenTypes.ANNOTATION_FIELD_DEF, MethodDefHandler.class);
        register(TokenTypes.SWITCH_RULE, SwitchRuleHandler.class);
        register(TokenTypes.LITERAL_YIELD, YieldHandler.class);
        register(TokenTypes.RECORD_DEF, ClassDefHandler.class);
        register(TokenTypes.COMPACT_CTOR_DEF, MethodDefHandler.class);
    }

    /**
     * Registers a handler.
     *
     * @param <T> type of the handler class object.
     * @param type
     *                type from TokenTypes
     * @param handlerClass
     *                the handler to register
     */
    private <T> void register(int type, Class<T> handlerClass) {
        final Constructor<T> ctor = CommonUtil.getConstructor(handlerClass,
                IndentationCheck.class,
                // current AST
                DetailAST.class,
                // parent
                AbstractExpressionHandler.class
        );
        typeHandlers.put(type, ctor);
    }

    /**
     * Returns true if this type (form TokenTypes) is handled.
     *
     * @param type type from TokenTypes
     * @return true if handler is registered, false otherwise
     */
    public boolean isHandledType(int type) {
        final Set<Integer> typeSet = typeHandlers.keySet();
        return typeSet.contains(type);
    }

    /**
     * Gets list of registered handler types.
     *
     * @return int[] of TokenType types
     */
    public int[] getHandledTypes() {
        final Set<Integer> typeSet = typeHandlers.keySet();
        final int[] types = new int[typeSet.size()];
        int index = 0;
        for (final Integer val : typeSet) {
            types[index] = val;
            index++;
        }

        return types;
    }

    /**
     * Get the handler for an AST.
     *
     * @param indentCheck   the indentation check
     * @param ast           ast to handle
     * @param parent        the handler parent of this AST
     *
     * @return the ExpressionHandler for ast
     */
    public AbstractExpressionHandler getHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        final AbstractExpressionHandler resultHandler;
        final AbstractExpressionHandler handler =
            createdHandlers.get(ast);
        if (handler != null) {
            resultHandler = handler;
        }
        else if (ast.getType() == TokenTypes.METHOD_CALL) {
            resultHandler = createMethodCallHandler(indentCheck, ast, parent);
        }
        else {
            final Constructor<?> handlerCtor = typeHandlers.get(ast.getType());
            resultHandler = (AbstractExpressionHandler) CommonUtil.invokeConstructor(
                handlerCtor, indentCheck, ast, parent);
        }
        return resultHandler;
    }

    /**
     * Create new instance of handler for METHOD_CALL.
     *
     * @param indentCheck   the indentation check
     * @param ast           ast to handle
     * @param parent        the handler parent of this AST
     *
     * @return new instance.
     */
    private AbstractExpressionHandler createMethodCallHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        DetailAST astNode = ast.getFirstChild();
        while (astNode.getType() == TokenTypes.DOT) {
            astNode = astNode.getFirstChild();
        }
        AbstractExpressionHandler theParent = parent;
        if (isHandledType(astNode.getType())) {
            theParent = getHandler(indentCheck, astNode, theParent);
            createdHandlers.put(astNode, theParent);
        }
        return new MethodCallHandler(indentCheck, ast, theParent);
    }

    /** Clears cache of created handlers. */
    public void clearCreatedHandlers() {
        createdHandlers.clear();
    }

}
