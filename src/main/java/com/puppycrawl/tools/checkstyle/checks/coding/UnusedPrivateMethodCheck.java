/// ////////////////////////////////////////////////////////////////////////////////////////////
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
/// ////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <div>
 * Checks that a local method is declared and/or assigned, but not used.
 * Doesn't support
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.30">
 * pattern methods yet</a>.
 * Doesn't check
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-4.html#jls-4.12.3">
 * array components</a> as array
 * components are classified as different kind of methods by
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/index.html">JLS</a>.
 * </div>
 * <ul>
 * <li>
 * Property {@code allowUnnamedMethods} - Allow methods named with a single underscore
 * (known as <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/unnamed-jls.html">
 * unnamed methods</a> in Java 21+).
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unused.local.var}
 * </li>
 * <li>
 * {@code unused.named.local.var}
 * </li>
 * </ul>
 *
 * @since 9.3
 */
@FileStatefulCheck
public class UnusedPrivateMethodCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_LOCAL_METHOD = "unused.local.method";

    /**
     * An array of increment and decrement tokens.
     */
    private static final int[] INCREMENT_AND_DECREMENT_TOKENS = {
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.INC,
            TokenTypes.DEC,
    };

    /**
     * An array of unacceptable children of ast of type {@link TokenTypes#DOT}.
     */
    private static final int[] UNACCEPTABLE_CHILD_OF_DOT = {
            TokenTypes.DOT,
            TokenTypes.METHOD_CALL,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SUPER,
            TokenTypes.LITERAL_CLASS,
            TokenTypes.LITERAL_THIS,
    };

    /**
     * An array of unacceptable parent of ast of type {@link TokenTypes#IDENT}.
     */
    private static final int[] UNACCEPTABLE_PARENT_OF_IDENT = {
            TokenTypes.METHOD_DEF,
            TokenTypes.DOT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.METHOD_CALL,
            TokenTypes.TYPE,
    };

    /**
     * An array of token types that indicate a method is being used within
     * an expression involving increment or decrement operators, or within a switch statement.
     * When a token of one of these types is the parent of an expression, it indicates that the
     * method associated with the increment or decrement operation is being used.
     * Ex:- TokenTypes.LITERAL_SWITCH: Indicates a switch statement. Methods used within the
     * switch expression are considered to be used
     */
    private static final int[] INCREMENT_DECREMENT_VARIABLE_USAGE_TYPES = {
            TokenTypes.ELIST,
            TokenTypes.INDEX_OP,
            TokenTypes.ASSIGN,
            TokenTypes.LITERAL_SWITCH,
    };

    /**
     * Package separator.
     */
    private static final String PACKAGE_SEPARATOR = ".";

    /**
     * Keeps tracks of the methods declared in file.
     */
    private final Deque<MethodDesc> methods = new ArrayDeque<>();
    private final Collection<String> callsText = new ArrayList<>();

    /**
     * Keeps track of all the type declarations present in the file.
     * Pops the type out of the stack while leaving the type
     * in visitor pattern.
     */
    private final Deque<TypeDeclDesc> typeDeclarations = new ArrayDeque<>();

    /**
     * Maps type declaration ast to their respective TypeDeclDesc objects.
     */
    private final Map<DetailAST, TypeDeclDesc> typeDeclAstToTypeDeclDesc = new LinkedHashMap<>();

    /**
     * Maps local anonymous inner class to the TypeDeclDesc object
     * containing it.
     */
    private final Map<DetailAST, TypeDeclDesc> anonInnerAstToTypeDeclDesc = new HashMap<>();

    /**
     * Name of the package.
     */
    private String packageName;

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.DOT,
                TokenTypes.METHOD_DEF,
                TokenTypes.IDENT,
                TokenTypes.SLIST,
                TokenTypes.LITERAL_FOR,
                TokenTypes.OBJBLOCK,
                TokenTypes.CLASS_DEF,
                TokenTypes.INTERFACE_DEF,
                TokenTypes.ANNOTATION_DEF,
                TokenTypes.PACKAGE_DEF,
                TokenTypes.LITERAL_NEW,
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
                TokenTypes.STATIC_INIT,
                TokenTypes.INSTANCE_INIT,
                TokenTypes.COMPILATION_UNIT,
                TokenTypes.LAMBDA,
                TokenTypes.ENUM_DEF,
                TokenTypes.RECORD_DEF,
                TokenTypes.COMPACT_CTOR_DEF,
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
    public void beginTree(DetailAST root) {
        methods.clear();
        typeDeclarations.clear();
        typeDeclAstToTypeDeclDesc.clear();
        anonInnerAstToTypeDeclDesc.clear();
        packageName = null;
    }

    private static DetailAST findScopeOfMethod(DetailAST variableDef) {
        final DetailAST result;
        final DetailAST parentAst = variableDef.getParent();
        if (TokenUtil.isOfType(parentAst, TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
            result = parentAst;
        } else {
            result = parentAst.getParent();
        }
        return result;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            methods.push(new MethodDesc(
                    ast.findFirstToken(TokenTypes.IDENT).getText(),
                    ast.findFirstToken(TokenTypes.TYPE),
                    findScopeOfMethod(ast)));
        }
        try {
            callsText.add(ast.getText());
        } catch (Exception ignored) {
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        Deque<MethodDesc> filteredMethods = methods.stream()
                .filter(method -> Collections.frequency(callsText, method.getName()) == 1)
                .collect(Collectors.toCollection(ArrayDeque::new));
        while (!filteredMethods.isEmpty()) {
            final MethodDesc variableDesc = filteredMethods.pop();
            log(variableDesc.getTypeAst(), MSG_UNUSED_LOCAL_METHOD, variableDesc.getName());
        }
    }



    /**
     * Maintains information about the method.
     */
    private static final class MethodDesc {

        /**
         * The name of the method.
         */
        private final String name;

        /**
         * Ast of type {@link TokenTypes#TYPE}.
         */
        private final DetailAST typeAst;

        /**
         * The scope of method is determined by the ast of type
         * {@link TokenTypes#SLIST} or {@link TokenTypes#LITERAL_FOR}
         * or {@link TokenTypes#OBJBLOCK} which is enclosing the method.
         */
        private final DetailAST scope;

        /**
         * Is an instance method or a class method.
         */
        private boolean instVarOrClassVar;

        /**
         * Is the method used.
         */
        private boolean used;

        /**
         * Create a new MethodDesc instance.
         *
         * @param name    name of the method
         * @param typeAst ast of type {@link TokenTypes#TYPE}
         * @param scope   ast of type {@link TokenTypes#SLIST} or
         *                {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
         *                which is enclosing the method
         */
        private MethodDesc(String name, DetailAST typeAst, DetailAST scope) {
            this.name = name;
            this.typeAst = typeAst;
            this.scope = scope;
        }

        /**
         * Create a new MethodDesc instance.
         *
         * @param name name of the method
         */
        private MethodDesc(String name) {
            this(name, null, null);
        }

        /**
         * Create a new MethodDesc instance.
         *
         * @param name  name of the method
         * @param scope ast of type {@link TokenTypes#SLIST} or
         *              {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
         *              which is enclosing the method
         */
        private MethodDesc(String name, DetailAST scope) {
            this(name, null, scope);
        }

        /**
         * Get the name of method.
         *
         * @return name of method
         */
        public String getName() {
            return name;
        }

        /**
         * Get the associated ast node of type {@link TokenTypes#TYPE}.
         *
         * @return the associated ast node of type {@link TokenTypes#TYPE}
         */
        public DetailAST getTypeAst() {
            return typeAst;
        }

        /**
         * Get ast of type {@link TokenTypes#SLIST}
         * or {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
         * which is enclosing the method i.e. its scope.
         *
         * @return the scope associated with the method
         */
        public DetailAST getScope() {
            return scope;
        }

        /**
         * Register the method as used.
         */
        public void registerAsUsed() {
            used = true;
        }

        /**
         * Register the method as an instance method or
         * class method.
         */
        public void registerAsInstOrClassVar() {
            instVarOrClassVar = true;
        }

        /**
         * Is the method used or not.
         *
         * @return true if method is used
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Is an instance method or a class method.
         *
         * @return true if is an instance method or a class method
         */
        public boolean isInstVarOrClassVar() {
            return instVarOrClassVar;
        }
    }

    /**
     * Maintains information about the type declaration.
     * Any ast node of type {@link TokenTypes#CLASS_DEF} or {@link TokenTypes#INTERFACE_DEF}
     * or {@link TokenTypes#ENUM_DEF} or {@link TokenTypes#ANNOTATION_DEF}
     * or {@link TokenTypes#RECORD_DEF} is considered as a type declaration.
     */
    private static final class TypeDeclDesc {

        /**
         * Complete type declaration name with package name and outer type declaration name.
         */
        private final String qualifiedName;

        /**
         * Depth of nesting of type declaration.
         */
        private final int depth;

        /**
         * Type declaration ast node.
         */
        private final DetailAST typeDeclAst;

        /**
         * A stack of type declaration's instance and static methods.
         */
        private final Deque<MethodDesc> instanceAndClassVarStack;

        /**
         * Create a new TypeDeclDesc instance.
         *
         * @param qualifiedName qualified name
         * @param depth         depth of nesting
         * @param typeDeclAst   type declaration ast node
         */
        private TypeDeclDesc(String qualifiedName, int depth,
                             DetailAST typeDeclAst) {
            this.qualifiedName = qualifiedName;
            this.depth = depth;
            this.typeDeclAst = typeDeclAst;
            instanceAndClassVarStack = new ArrayDeque<>();
        }

        /**
         * Get the complete type declaration name i.e. type declaration name with package name
         * and outer type declaration name.
         *
         * @return qualified class name
         */
        public String getQualifiedName() {
            return qualifiedName;
        }

        /**
         * Get the depth of type declaration.
         *
         * @return the depth of nesting of type declaration
         */
        public int getDepth() {
            return depth;
        }

        /**
         * Get the type declaration ast node.
         *
         * @return ast node of the type declaration
         */
        public DetailAST getTypeDeclAst() {
            return typeDeclAst;
        }

        /**
         * Get the copy of methods in instanceAndClassVar stack with updated scope.
         *
         * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
         * @return copy of methods in instanceAndClassVar stack with updated scope.
         */
        public Deque<MethodDesc> getUpdatedCopyOfVarStack(DetailAST literalNewAst) {
            final DetailAST updatedScope = literalNewAst;
            final Deque<MethodDesc> instAndClassVarDeque = new ArrayDeque<>();
            instanceAndClassVarStack.forEach(instVar -> {
                final MethodDesc variableDesc = new MethodDesc(instVar.getName(),
                        updatedScope);
                variableDesc.registerAsInstOrClassVar();
                instAndClassVarDeque.push(variableDesc);
            });
            return instAndClassVarDeque;
        }

        /**
         * Add an instance method or class method to the stack.
         *
         * @param variableDesc method to be added
         */
        public void addInstOrClassVar(MethodDesc variableDesc) {
            instanceAndClassVarStack.push(variableDesc);
        }
    }
}
