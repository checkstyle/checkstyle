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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that exception throw statements in catch blocks use context variables.
 * </div>
 *
 * <p>
 * Rationale: When catching an exception and throwing a new exception, it is
 * recommended to include contextual information in the new exception constructor
 * (such as method parameters, local variables declared outside the catch block, or fields).
 * Failing to include context makes troubleshooting and debugging production issues
 * significantly harder.
 * </p>
 *
 * @since 14.4.0
 */
@StatelessCheck
public class ExceptionThrowShouldUseContextCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "exception.throw.should.use.context";

    /**
     * Creates a new {@code ExceptionThrowShouldUseContextCheck} instance.
     */
    public ExceptionThrowShouldUseContextCheck() {
        // no code by default
    }

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
            TokenTypes.LITERAL_CATCH,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST paramDef = ast.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST paramIdent = paramDef.findFirstToken(TokenTypes.IDENT);
        final String catchParamName = paramIdent.getText();
        final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);
        final List<DetailAST> throwStatements = new ArrayList<>();
        collectThrowStatements(slist, throwStatements);
        for (DetailAST throwAst : throwStatements) {
            checkThrowStatement(throwAst, catchParamName, ast);
        }
    }

    /**
     * Checks if a throw statement throws a new exception without using context variables.
     *
     * @param throwAst throw statement ast
     * @param catchParamName name of the catch parameter
     * @param catchAst enclosing catch ast
     */
    private void checkThrowStatement(DetailAST throwAst, String catchParamName,
                                     DetailAST catchAst) {
        final DetailAST expr = throwAst.getFirstChild();
        final DetailAST thrownExpr = expr.getFirstChild();
        if (thrownExpr.getType() == TokenTypes.LITERAL_NEW) {
            final Set<String> contextVars = collectContextVariables(catchAst,
                    catchParamName);
            if (!contextVars.isEmpty()) {
                final DetailAST elist = thrownExpr.findFirstToken(TokenTypes.ELIST);
                if (!containsAnyVariable(elist, contextVars, catchParamName)) {
                    log(throwAst, MSG_KEY);
                }
            }
        }
    }

    /**
     * Recursively collects all throw statements in a block, skipping nested catches,
     * classes, records, interfaces, object blocks, and lambdas.
     *
     * @param node AST node to search
     * @param result list to collect throw statements into
     */
    private static void collectThrowStatements(DetailAST node, List<DetailAST> result) {
        for (DetailAST child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            final int type = child.getType();
            if (type == TokenTypes.LITERAL_THROW) {
                result.add(child);
            }
            else if (type != TokenTypes.LITERAL_CATCH
                    && type != TokenTypes.CLASS_DEF
                    && type != TokenTypes.RECORD_DEF
                    && type != TokenTypes.INTERFACE_DEF
                    && type != TokenTypes.LAMBDA) {
                collectThrowStatements(child, result);
            }
        }
    }

    /**
     * Checks if an AST node contains any identifier from the variable set, excluding a given name.
     *
     * @param node AST node to search
     * @param variables set of variable names
     * @param excludedName identifier name to exclude
     * @return true if any variable is found
     */
    private static boolean containsAnyVariable(DetailAST node, Set<String> variables,
                                               String excludedName) {
        boolean result = false;
        for (DetailAST child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            final boolean isContextIdent = child.getType() == TokenTypes.IDENT
                    && !excludedName.equals(child.getText())
                    && variables.contains(child.getText());
            if (isContextIdent || containsAnyVariable(child, variables, excludedName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Collects all available context variables in scope: parameters, local variables outside
     * the catch block, applicable enclosing class fields, and variables initialized with context
     * inside the catch block.
     *
     * @param catchAst catch AST node
     * @param catchParamName catch parameter name
     * @return set of available context variable names
     */
    private static Set<String> collectContextVariables(DetailAST catchAst,
                                                       String catchParamName) {
        final Set<String> contextVars = new HashSet<>();
        final List<DetailAST> methodScopes = findEnclosingMethodScopes(catchAst);
        for (DetailAST methodScope : methodScopes) {
            collectParameters(methodScope, contextVars);
            collectLocalVariablesOutsideCatch(methodScope, catchAst, contextVars, catchParamName);
        }
        final DetailAST enclosingClass = findEnclosingClassLike(catchAst);
        final boolean isStaticMethod = isStaticScope(methodScopes);
        collectFields(enclosingClass, contextVars, isStaticMethod);
        if (!isStaticMethod && isInnerClass(enclosingClass)) {
            final DetailAST outerClass = findEnclosingClassLike(enclosingClass);
            collectFields(outerClass, contextVars, false);
        }
        collectCatchVariablesWithContext(catchAst, contextVars, catchParamName);
        return contextVars;
    }

    /**
     * Finds all enclosing method-like scopes (methods, constructors, or lambdas).
     *
     * @param node starting AST node
     * @return list of enclosing method-like AST nodes
     */
    private static List<DetailAST> findEnclosingMethodScopes(DetailAST node) {
        final List<DetailAST> scopes = new ArrayList<>();
        DetailAST current = node.getParent();
        while (current.getType() != TokenTypes.OBJBLOCK) {
            final int type = current.getType();
            if (type == TokenTypes.METHOD_DEF
                    || type == TokenTypes.CTOR_DEF
                    || type == TokenTypes.COMPACT_CTOR_DEF) {
                scopes.add(current);
                break;
            }
            if (type == TokenTypes.LAMBDA) {
                scopes.add(current);
            }
            current = current.getParent();
        }
        return scopes;
    }

    /**
     * Checks if any of the enclosing method scopes is declared static.
     *
     * @param methodScopes method-like scopes
     * @return true if any enclosing scope is static
     */
    private static boolean isStaticScope(Iterable<DetailAST> methodScopes) {
        boolean result = false;
        for (DetailAST scope : methodScopes) {
            final DetailAST modifiers = scope.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers != null && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Collects parameters from a method, constructor, or lambda.
     *
     * @param methodAst method-like AST node
     * @param contextVars set to add parameter names to
     */
    private static void collectParameters(DetailAST methodAst, Set<String> contextVars) {
        if (methodAst.getType() == TokenTypes.COMPACT_CTOR_DEF) {
            collectCompactCtorParameters(methodAst, contextVars);
        }
        else {
            collectStandardParameters(methodAst, contextVars);
        }
    }

    /**
     * Collects parameters from a compact constructor in a record.
     *
     * @param compactCtorDef compact constructor AST node
     * @param contextVars set to add parameter names to
     */
    private static void collectCompactCtorParameters(DetailAST compactCtorDef,
                                                     Set<String> contextVars) {
        final DetailAST recordDef = compactCtorDef.getParent().getParent();
        final DetailAST recordComponents =
                recordDef.findFirstToken(TokenTypes.RECORD_COMPONENTS);
        addRecordComponents(recordComponents, contextVars);
    }

    /**
     * Adds record component names to context variables.
     *
     * @param recordComponents record components AST node
     * @param contextVars set to add component names to
     */
    private static void addRecordComponents(DetailAST recordComponents,
                                            Set<String> contextVars) {
        for (DetailAST comp = recordComponents.getFirstChild();
             comp != null;
             comp = comp.getNextSibling()) {
            if (comp.getType() == TokenTypes.RECORD_COMPONENT_DEF) {
                final DetailAST ident = comp.findFirstToken(TokenTypes.IDENT);
                contextVars.add(ident.getText());
            }
        }
    }

    /**
     * Collects parameters from a standard method, constructor, or lambda.
     *
     * @param methodAst method-like AST node
     * @param contextVars set to add parameter names to
     */
    private static void collectStandardParameters(DetailAST methodAst,
                                                  Set<String> contextVars) {
        final DetailAST parameters = methodAst.findFirstToken(TokenTypes.PARAMETERS);
        if (parameters != null) {
            for (DetailAST param = parameters.getFirstChild();
                 param != null;
                 param = param.getNextSibling()) {
                if (param.getType() == TokenTypes.PARAMETER_DEF) {
                    final DetailAST ident = param.findFirstToken(TokenTypes.IDENT);
                    contextVars.add(ident.getText());
                }
            }
        }
        else {
            final DetailAST singleParam = methodAst.findFirstToken(TokenTypes.IDENT);
            contextVars.add(singleParam.getText());
        }
    }

    /**
     * Recursively collects local variables within a method, skipping the catch AST node
     * and nested types.
     *
     * @param node AST node to search
     * @param catchAst catch AST node to skip
     * @param contextVars set to add variable names to
     * @param catchParamName catch parameter name to exclude
     */
    private static void collectLocalVariablesOutsideCatch(DetailAST node, DetailAST catchAst,
                                                          Set<String> contextVars,
                                                          String catchParamName) {
        for (DetailAST child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            if (child.equals(catchAst)) {
                continue;
            }
            processLocalVariableDef(child, contextVars, catchParamName);
            final int type = child.getType();
            if (type != TokenTypes.CLASS_DEF
                    && type != TokenTypes.RECORD_DEF
                    && type != TokenTypes.INTERFACE_DEF) {
                collectLocalVariablesOutsideCatch(child, catchAst, contextVars, catchParamName);
            }
        }
    }

    /**
     * Collects variables declared within the catch block whose initializers reference
     * an already identified context variable.
     *
     * @param catchAst catch AST node
     * @param contextVars set of known context variables
     * @param catchParamName catch parameter name
     */
    private static void collectCatchVariablesWithContext(DetailAST catchAst,
                                                         Set<String> contextVars,
                                                         String catchParamName) {
        final DetailAST slist = catchAst.findFirstToken(TokenTypes.SLIST);
        for (DetailAST child = slist.getFirstChild(); child != null;
             child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.VARIABLE_DEF) {
                final DetailAST assign = child.findFirstToken(TokenTypes.ASSIGN);
                if (assign != null && containsAnyVariable(assign, contextVars,
                        catchParamName)) {
                    final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                    contextVars.add(ident.getText());
                }
            }
        }
    }

    /**
     * Processes a child node and adds any defined local variable name to context variables.
     *
     * @param child AST node to inspect
     * @param contextVars set to add variable names to
     * @param catchParamName catch parameter name to exclude
     */
    private static void processLocalVariableDef(DetailAST child, Set<String> contextVars,
                                                String catchParamName) {
        final int type = child.getType();
        if (type == TokenTypes.VARIABLE_DEF || type == TokenTypes.PATTERN_VARIABLE_DEF) {
            final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
            if (!catchParamName.equals(ident.getText())) {
                contextVars.add(ident.getText());
            }
        }
        else if (type == TokenTypes.RESOURCE) {
            final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
            if (ident != null) {
                contextVars.add(ident.getText());
            }
        }
    }

    /**
     * Checks if an enclosing class is a non-static inner class.
     *
     * @param classDef class AST node
     * @return true if the class is a non-static inner class
     */
    private static boolean isInnerClass(DetailAST classDef) {
        return classDef.getType() == TokenTypes.CLASS_DEF
                && classDef.getParent().getType() == TokenTypes.OBJBLOCK
                && classDef.findFirstToken(TokenTypes.MODIFIERS)
                        .findFirstToken(TokenTypes.LITERAL_STATIC) == null;
    }

    /**
     * Finds the nearest enclosing class, record, interface, or enum.
     *
     * @param node starting AST node
     * @return enclosing type AST node, or null if none
     */
    private static DetailAST findEnclosingClassLike(DetailAST node) {
        DetailAST current = node.getParent();
        while (current.getType() != TokenTypes.CLASS_DEF
                && current.getType() != TokenTypes.RECORD_DEF
                && current.getType() != TokenTypes.INTERFACE_DEF
                && current.getType() != TokenTypes.ENUM_DEF) {
            current = current.getParent();
        }
        return current;
    }

    /**
     * Collects field names from an enclosing type's object block, filtering out instance
     * fields when within a static scope.
     *
     * @param classDef enclosing type AST node
     * @param contextVars set to add field names to
     * @param isStaticMethod true if enclosing method is static
     */
    private static void collectFields(DetailAST classDef, Set<String> contextVars,
                                      boolean isStaticMethod) {
        final DetailAST objBlock = classDef.findFirstToken(TokenTypes.OBJBLOCK);
        for (DetailAST child = objBlock.getFirstChild();
             child != null;
             child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.VARIABLE_DEF) {
                final DetailAST modifiers = child.findFirstToken(TokenTypes.MODIFIERS);
                final boolean isStaticField =
                        modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
                if (!isStaticMethod || isStaticField) {
                    final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                    contextVars.add(ident.getText());
                }
            }
        }
    }

}
