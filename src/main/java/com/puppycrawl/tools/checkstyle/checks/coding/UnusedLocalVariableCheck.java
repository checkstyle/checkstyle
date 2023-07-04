///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a local variable is declared and/or assigned, but not used.
 * Doesn't support
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-14.html#jls-14.30">
 * pattern variables yet</a>.
 * Doesn't check
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-4.html#jls-4.12.3">
 * array components</a> as array
 * components are classified as different kind of variables by
 * <a href="https://docs.oracle.com/javase/specs/jls/se17/html/index.html">JLS</a>.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnusedLocalVariable&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *
 *     int a;
 *
 *     {
 *         int k = 12; // violation, assigned and updated but never used
 *         k++;
 *     }
 *
 *     Test(int a) {   // ok as 'a' is a constructor parameter not a local variable
 *         this.a = 12;
 *     }
 *
 *     void method(int b) {
 *         int a = 10;             // violation
 *         int[] arr = {1, 2, 3};  // violation
 *         int[] anotherArr = {1}; // ok
 *         anotherArr[0] = 4;
 *     }
 *
 *     String convertValue(String newValue) {
 *         String s = newValue.toLowerCase(); // violation
 *         return newValue.toLowerCase();
 *     }
 *
 *     void read() throws IOException {
 *         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 *         String s; // violation
 *         while ((s = reader.readLine()) != null) {
 *         }
 *         try (BufferedReader reader1 // ok as 'reader1' is a resource and resources are closed
 *                                     // at the end of the statement
 *             = new BufferedReader(new FileReader("abc.txt"))) {
 *         }
 *         try {
 *         } catch (Exception e) {     // ok as e is an exception parameter
 *         }
 *     }
 *
 *     void loops() {
 *         int j = 12;
 *         for (int i = 0; j &lt; 11; i++) { // violation, unused local variable 'i'.
 *         }
 *         for (int p = 0; j &lt; 11; p++)   // ok
 *             p /= 2;
 *     }
 *
 *     void lambdas() {
 *         Predicate&lt;String&gt; obj = (String str) -&gt; { // ok as 'str' is a lambda parameter
 *             return true;
 *         };
 *         obj.test("test");
 *     }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code unused.local.var}
 * </li>
 * </ul>
 *
 * @since 9.3
 */
@FileStatefulCheck
public class UnusedLocalVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_LOCAL_VARIABLE = "unused.local.var";

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
     * An array of scope tokens.
     */
    private static final int[] SCOPES = {
        TokenTypes.SLIST,
        TokenTypes.LITERAL_FOR,
        TokenTypes.OBJBLOCK,
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
        TokenTypes.VARIABLE_DEF,
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
        TokenTypes.PATTERN_VARIABLE_DEF,
        TokenTypes.METHOD_CALL,
        TokenTypes.TYPE,
    };

    /**
     * An array of blocks in which local anon inner classes can exist.
     */
    private static final int[] CONTAINERS_FOR_ANON_INNERS = {
        TokenTypes.METHOD_DEF,
        TokenTypes.CTOR_DEF,
        TokenTypes.STATIC_INIT,
        TokenTypes.INSTANCE_INIT,
        TokenTypes.COMPACT_CTOR_DEF,
    };

    /** Package separator. */
    private static final String PACKAGE_SEPARATOR = ".";

    /**
     * Keeps tracks of the variables declared in file.
     */
    private final Deque<VariableDesc> variables = new ArrayDeque<>();

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
     * Set of tokens of type {@link UnusedLocalVariableCheck#CONTAINERS_FOR_ANON_INNERS}
     * and {@link TokenTypes#LAMBDA} in some cases.
     */
    private final Set<DetailAST> anonInnerClassHolders = new HashSet<>();

    /**
     * Name of the package.
     */
    private String packageName;

    /**
     * Depth at which a type declaration is nested, 0 for top level type declarations.
     */
    private int depth;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
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
        variables.clear();
        typeDeclarations.clear();
        typeDeclAstToTypeDeclDesc.clear();
        anonInnerAstToTypeDeclDesc.clear();
        anonInnerClassHolders.clear();
        packageName = null;
        depth = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int type = ast.getType();
        if (type == TokenTypes.DOT) {
            visitDotToken(ast, variables);
        }
        else if (type == TokenTypes.VARIABLE_DEF) {
            visitVariableDefToken(ast);
        }
        else if (type == TokenTypes.IDENT) {
            visitIdentToken(ast, variables);
        }
        else if (type == TokenTypes.LITERAL_NEW
                && isInsideLocalAnonInnerClass(ast)) {
            visitLocalAnonInnerClass(ast);
        }
        else if (TokenUtil.isTypeDeclaration(type)) {
            visitTypeDeclarationToken(ast);
        }
        else if (type == TokenTypes.PACKAGE_DEF) {
            packageName = CheckUtil.extractQualifiedName(ast.getFirstChild().getNextSibling());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (TokenUtil.isOfType(ast, SCOPES)) {
            logViolations(ast, variables);
        }
        else if (ast.getType() == TokenTypes.COMPILATION_UNIT) {
            leaveCompilationUnit();
        }
        else if (isNonLocalTypeDeclaration(ast)) {
            depth--;
            typeDeclarations.pop();
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#DOT}.
     *
     * @param dotAst dotAst
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private static void visitDotToken(DetailAST dotAst, Deque<VariableDesc> variablesStack) {
        if (dotAst.getParent().getType() != TokenTypes.LITERAL_NEW
                && shouldCheckIdentTokenNestedUnderDot(dotAst)) {
            checkIdentifierAst(dotAst.findFirstToken(TokenTypes.IDENT), variablesStack);
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#VARIABLE_DEF}.
     *
     * @param varDefAst varDefAst
     */
    private void visitVariableDefToken(DetailAST varDefAst) {
        addLocalVariables(varDefAst, variables);
        addInstanceOrClassVar(varDefAst);
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}.
     *
     * @param identAst identAst
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private static void visitIdentToken(DetailAST identAst, Deque<VariableDesc> variablesStack) {
        final DetailAST parent = identAst.getParent();
        final boolean isMethodReferenceMethodName = parent.getType() == TokenTypes.METHOD_REF
                && parent.getFirstChild() != identAst;
        final boolean isConstructorReference = parent.getType() == TokenTypes.METHOD_REF
                && parent.getLastChild().getType() == TokenTypes.LITERAL_NEW;
        final boolean isNestedClassInitialization =
                TokenUtil.isOfType(identAst.getNextSibling(), TokenTypes.LITERAL_NEW)
                && parent.getType() == TokenTypes.DOT;

        if (isNestedClassInitialization || !isMethodReferenceMethodName
                && !isConstructorReference
                && !TokenUtil.isOfType(parent, UNACCEPTABLE_PARENT_OF_IDENT)) {
            checkIdentifierAst(identAst, variablesStack);
        }
    }

    /**
     * Visit the type declaration token.
     *
     * @param typeDeclAst type declaration ast
     */
    private void visitTypeDeclarationToken(DetailAST typeDeclAst) {
        if (isNonLocalTypeDeclaration(typeDeclAst)) {
            final String qualifiedName = getQualifiedTypeDeclarationName(typeDeclAst);
            final TypeDeclDesc currTypeDecl = new TypeDeclDesc(qualifiedName, depth, typeDeclAst);
            depth++;
            typeDeclarations.push(currTypeDecl);
            typeDeclAstToTypeDeclDesc.put(typeDeclAst, currTypeDecl);
        }
    }

    /**
     * Visit the local anon inner class.
     *
     * @param literalNewAst literalNewAst
     */
    private void visitLocalAnonInnerClass(DetailAST literalNewAst) {
        anonInnerAstToTypeDeclDesc.put(literalNewAst, typeDeclarations.peek());
        anonInnerClassHolders.add(getBlockContainingLocalAnonInnerClass(literalNewAst));
    }

    /**
     * Whether ast node of type {@link TokenTypes#LITERAL_NEW} is a part of a local
     * anonymous inner class.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return true if variableDefAst is an instance variable in local anonymous inner class
     */
    private static boolean isInsideLocalAnonInnerClass(DetailAST literalNewAst) {
        boolean result = false;
        final DetailAST lastChild = literalNewAst.getLastChild();
        if (lastChild != null && lastChild.getType() == TokenTypes.OBJBLOCK) {
            DetailAST currentAst = literalNewAst;
            while (currentAst.getType() != TokenTypes.SLIST) {
                if (TokenUtil.isTypeDeclaration(currentAst.getParent().getType())) {
                    break;
                }
                currentAst = currentAst.getParent();
            }
            result = currentAst.getType() == TokenTypes.SLIST;
        }
        return result;
    }

    /**
     * Traverse {@code variablesStack} stack and log the violations.
     *
     * @param scopeAst ast node of type {@link UnusedLocalVariableCheck#SCOPES}
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void logViolations(DetailAST scopeAst, Deque<VariableDesc> variablesStack) {
        while (!variablesStack.isEmpty() && variablesStack.peek().getScope() == scopeAst) {
            final VariableDesc variableDesc = variablesStack.pop();
            if (!variableDesc.isUsed()
                    && !variableDesc.isInstVarOrClassVar()) {
                final DetailAST typeAst = variableDesc.getTypeAst();
                log(typeAst, MSG_UNUSED_LOCAL_VARIABLE, variableDesc.getName());
            }
        }
    }

    /**
     * We process all the blocks containing local anonymous inner classes
     * separately after processing all the other nodes. This is being done
     * due to the fact the instance variables of local anon inner classes can
     * cast a shadow on local variables.
     */
    private void leaveCompilationUnit() {
        anonInnerClassHolders.forEach(holder -> {
            iterateOverBlockContainingLocalAnonInnerClass(holder, new ArrayDeque<>());
        });
    }

    /**
     * Whether a type declaration is non-local. Annotated interfaces are always non-local.
     *
     * @param typeDeclAst type declaration ast
     * @return true if type declaration is non-local
     */
    private static boolean isNonLocalTypeDeclaration(DetailAST typeDeclAst) {
        return TokenUtil.isTypeDeclaration(typeDeclAst.getType())
                && typeDeclAst.getParent().getType() != TokenTypes.SLIST;
    }

    /**
     * Get the block containing local anon inner class.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return the block containing local anon inner class
     */
    private static DetailAST getBlockContainingLocalAnonInnerClass(DetailAST literalNewAst) {
        DetailAST currentAst = literalNewAst;
        DetailAST result = null;
        while (!TokenUtil.isOfType(currentAst, CONTAINERS_FOR_ANON_INNERS)) {
            if (currentAst.getType() == TokenTypes.LAMBDA
                    && currentAst.getParent()
                    .getParent().getParent().getType() == TokenTypes.OBJBLOCK) {
                result = currentAst;
                break;
            }
            currentAst = currentAst.getParent();
            result = currentAst;
        }
        return result;
    }

    /**
     * Add local variables to the {@code variablesStack} stack.
     * Also adds the instance variables defined in a local anonymous inner class.
     *
     * @param varDefAst ast node of type {@link TokenTypes#VARIABLE_DEF}
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private static void addLocalVariables(DetailAST varDefAst, Deque<VariableDesc> variablesStack) {
        final DetailAST parentAst = varDefAst.getParent();
        final DetailAST grandParent = parentAst.getParent();
        final boolean isInstanceVarInAnonymousInnerClass =
                grandParent.getType() == TokenTypes.LITERAL_NEW;
        if (isInstanceVarInAnonymousInnerClass
                || parentAst.getType() != TokenTypes.OBJBLOCK) {
            final DetailAST ident = varDefAst.findFirstToken(TokenTypes.IDENT);
            final VariableDesc desc = new VariableDesc(ident.getText(),
                    varDefAst.findFirstToken(TokenTypes.TYPE), findScopeOfVariable(varDefAst));
            if (isInstanceVarInAnonymousInnerClass) {
                desc.registerAsInstOrClassVar();
            }
            variablesStack.push(desc);
        }
    }

    /**
     * Add instance variables and class variables to the
     * {@link TypeDeclDesc#instanceAndClassVarStack}.
     *
     * @param varDefAst ast node of type {@link TokenTypes#VARIABLE_DEF}
     */
    private void addInstanceOrClassVar(DetailAST varDefAst) {
        final DetailAST parentAst = varDefAst.getParent();
        if (isNonLocalTypeDeclaration(parentAst.getParent())
                && !isPrivateInstanceVariable(varDefAst)) {
            final DetailAST ident = varDefAst.findFirstToken(TokenTypes.IDENT);
            final VariableDesc desc = new VariableDesc(ident.getText());
            typeDeclAstToTypeDeclDesc.get(parentAst.getParent()).addInstOrClassVar(desc);
        }
    }

    /**
     * Whether instance variable or class variable have private access modifier.
     *
     * @param varDefAst ast node of type {@link TokenTypes#VARIABLE_DEF}
     * @return true if instance variable or class variable have private access modifier
     */
    private static boolean isPrivateInstanceVariable(DetailAST varDefAst) {
        final AccessModifierOption varAccessModifier =
                CheckUtil.getAccessModifierFromModifiersToken(varDefAst);
        return varAccessModifier == AccessModifierOption.PRIVATE;
    }

    /**
     * Get the {@link TypeDeclDesc} of the super class of anonymous inner class.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return {@link TypeDeclDesc} of the super class of anonymous inner class
     */
    private TypeDeclDesc getSuperClassOfAnonInnerClass(DetailAST literalNewAst) {
        TypeDeclDesc obtainedClass = null;
        final String shortNameOfClass = CheckUtil.getShortNameOfAnonInnerClass(literalNewAst);
        if (packageName != null && shortNameOfClass.startsWith(packageName)) {
            final Optional<TypeDeclDesc> classWithCompletePackageName =
                    typeDeclAstToTypeDeclDesc.values()
                    .stream()
                    .filter(typeDeclDesc -> {
                        return typeDeclDesc.getQualifiedName().equals(shortNameOfClass);
                    })
                    .findFirst();
            if (classWithCompletePackageName.isPresent()) {
                obtainedClass = classWithCompletePackageName.get();
            }
        }
        else {
            final List<TypeDeclDesc> typeDeclWithSameName = typeDeclWithSameName(shortNameOfClass);
            if (!typeDeclWithSameName.isEmpty()) {
                obtainedClass = getTheNearestClass(
                        anonInnerAstToTypeDeclDesc.get(literalNewAst).getQualifiedName(),
                        typeDeclWithSameName);
            }
        }
        return obtainedClass;
    }

    /**
     * Add non-private instance and class variables of the super class of the anonymous class
     * to the variables stack.
     *
     * @param obtainedClass super class of the anon inner class
     * @param variablesStack stack of all the relevant variables in the scope
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     */
    private void modifyVariablesStack(TypeDeclDesc obtainedClass,
            Deque<VariableDesc> variablesStack,
            DetailAST literalNewAst) {
        if (obtainedClass != null) {
            final Deque<VariableDesc> instAndClassVarDeque = typeDeclAstToTypeDeclDesc
                    .get(obtainedClass.getTypeDeclAst())
                    .getUpdatedCopyOfVarStack(literalNewAst);
            instAndClassVarDeque.forEach(variablesStack::push);
        }
    }

    /**
     * Checks if there is a type declaration with same name as the super class.
     *
     * @param superClassName name of the super class
     * @return true if there is another type declaration with same name.
     */
    private List<TypeDeclDesc> typeDeclWithSameName(String superClassName) {
        return typeDeclAstToTypeDeclDesc.values().stream()
                .filter(typeDeclDesc -> {
                    return hasSameNameAsSuperClass(superClassName, typeDeclDesc);
                })
                .collect(Collectors.toList());
    }

    /**
     * Whether the qualified name of {@code typeDeclDesc} matches the super class name.
     *
     * @param superClassName name of the super class
     * @param typeDeclDesc type declaration description
     * @return {@code true} if the qualified name of {@code typeDeclDesc}
     *         matches the super class name
     */
    private boolean hasSameNameAsSuperClass(String superClassName, TypeDeclDesc typeDeclDesc) {
        final boolean result;
        if (packageName == null && typeDeclDesc.getDepth() == 0) {
            result = typeDeclDesc.getQualifiedName().equals(superClassName);
        }
        else {
            result = typeDeclDesc.getQualifiedName()
                    .endsWith(PACKAGE_SEPARATOR + superClassName);
        }
        return result;
    }

    /**
     * For all type declarations with the same name as the superclass, gets the nearest type
     * declaration.
     *
     * @param outerTypeDeclName outer type declaration of anonymous inner class
     * @param typeDeclWithSameName typeDeclarations which have the same name as the super class
     * @return the nearest class
     */
    private static TypeDeclDesc getTheNearestClass(String outerTypeDeclName,
            List<TypeDeclDesc> typeDeclWithSameName) {
        return Collections.min(typeDeclWithSameName, (first, second) -> {
            return getTypeDeclarationNameMatchingCountDiff(outerTypeDeclName, first, second);
        });
    }

    /**
     * Get the difference between type declaration name matching count. If the
     * difference between them is zero, then their depth is compared to obtain the result.
     *
     * @param outerTypeDeclName outer type declaration of anonymous inner class
     * @param firstTypeDecl first input type declaration
     * @param secondTypeDecl second input type declaration
     * @return difference between type declaration name matching count
     */
    private static int getTypeDeclarationNameMatchingCountDiff(String outerTypeDeclName,
                                                               TypeDeclDesc firstTypeDecl,
                                                               TypeDeclDesc secondTypeDecl) {
        int diff = Integer.compare(
            CheckUtil.typeDeclarationNameMatchingCount(
                outerTypeDeclName, secondTypeDecl.getQualifiedName()),
            CheckUtil.typeDeclarationNameMatchingCount(
                outerTypeDeclName, firstTypeDecl.getQualifiedName()));
        if (diff == 0) {
            diff = Integer.compare(firstTypeDecl.getDepth(), secondTypeDecl.getDepth());
        }
        return diff;
    }

    /**
     * Get qualified type declaration name from type ast.
     *
     * @param typeDeclAst type declaration ast
     * @return qualified name of type declaration
     */
    private String getQualifiedTypeDeclarationName(DetailAST typeDeclAst) {
        final String className = typeDeclAst.findFirstToken(TokenTypes.IDENT).getText();
        String outerClassQualifiedName = null;
        if (!typeDeclarations.isEmpty()) {
            outerClassQualifiedName = typeDeclarations.peek().getQualifiedName();
        }
        return CheckUtil
            .getQualifiedTypeDeclarationName(packageName, outerClassQualifiedName, className);
    }

    /**
     * Iterate over all the ast nodes present under {@code ast}.
     *
     * @param ast ast
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void iterateOverBlockContainingLocalAnonInnerClass(
            DetailAST ast, Deque<VariableDesc> variablesStack) {
        DetailAST currNode = ast;
        while (currNode != null) {
            customVisitToken(currNode, variablesStack);
            DetailAST toVisit = currNode.getFirstChild();
            while (currNode != ast && toVisit == null) {
                customLeaveToken(currNode, variablesStack);
                toVisit = currNode.getNextSibling();
                currNode = currNode.getParent();
            }
            currNode = toVisit;
        }
    }

    /**
     * Visit all ast nodes under {@link UnusedLocalVariableCheck#anonInnerClassHolders} once
     * again.
     *
     * @param ast ast
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void customVisitToken(DetailAST ast, Deque<VariableDesc> variablesStack) {
        final int type = ast.getType();
        if (type == TokenTypes.DOT) {
            visitDotToken(ast, variablesStack);
        }
        else if (type == TokenTypes.VARIABLE_DEF) {
            addLocalVariables(ast, variablesStack);
        }
        else if (type == TokenTypes.IDENT) {
            visitIdentToken(ast, variablesStack);
        }
        else if (isInsideLocalAnonInnerClass(ast)) {
            final TypeDeclDesc obtainedClass = getSuperClassOfAnonInnerClass(ast);
            modifyVariablesStack(obtainedClass, variablesStack, ast);
        }
    }

    /**
     * Leave all ast nodes under {@link UnusedLocalVariableCheck#anonInnerClassHolders} once
     * again.
     *
     * @param ast ast
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void customLeaveToken(DetailAST ast, Deque<VariableDesc> variablesStack) {
        logViolations(ast, variablesStack);
    }

    /**
     * Whether to check identifier token nested under dotAst.
     *
     * @param dotAst dotAst
     * @return true if ident nested under dotAst should be checked
     */
    private static boolean shouldCheckIdentTokenNestedUnderDot(DetailAST dotAst) {

        return TokenUtil.findFirstTokenByPredicate(dotAst,
                        childAst -> {
                            return TokenUtil.isOfType(childAst,
                                    UNACCEPTABLE_CHILD_OF_DOT);
                        })
                .isEmpty();
    }

    /**
     * Checks the identifier ast.
     *
     * @param identAst ast of type {@link TokenTypes#IDENT}
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private static void checkIdentifierAst(DetailAST identAst, Deque<VariableDesc> variablesStack) {
        for (VariableDesc variableDesc : variablesStack) {
            if (identAst.getText().equals(variableDesc.getName())
                    && !isLeftHandSideValue(identAst)) {
                variableDesc.registerAsUsed();
                break;
            }
        }
    }

    /**
     * Find the scope of variable.
     *
     * @param variableDef ast of type {@link TokenTypes#VARIABLE_DEF}
     * @return scope of variableDef
     */
    private static DetailAST findScopeOfVariable(DetailAST variableDef) {
        final DetailAST result;
        final DetailAST parentAst = variableDef.getParent();
        if (TokenUtil.isOfType(parentAst, TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
            result = parentAst;
        }
        else {
            result = parentAst.getParent();
        }
        return result;
    }

    /**
     * Checks whether the ast of type {@link TokenTypes#IDENT} is
     * used as left-hand side value. An identifier is being used as a left-hand side
     * value if it is used as the left operand of an assignment or as an
     * operand of a stand-alone increment or decrement.
     *
     * @param identAst ast of type {@link TokenTypes#IDENT}
     * @return true if identAst is used as a left-hand side value
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return isStandAloneIncrementOrDecrement(identAst)
                || parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Checks whether the ast of type {@link TokenTypes#IDENT} is used as
     * an operand of a stand-alone increment or decrement.
     *
     * @param identAst ast of type {@link TokenTypes#IDENT}
     * @return true if identAst is used as an operand of stand-alone
     *         increment or decrement
     */
    private static boolean isStandAloneIncrementOrDecrement(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        final DetailAST grandParent = parent.getParent();
        return TokenUtil.isOfType(parent, INCREMENT_AND_DECREMENT_TOKENS)
                && TokenUtil.isOfType(grandParent, TokenTypes.EXPR)
                && !isIncrementOrDecrementVariableUsed(grandParent);
    }

    /**
     * A variable with increment or decrement operator is considered used if it
     * is used as an argument or as an array index or for assigning value
     * to a variable.
     *
     * @param exprAst ast of type {@link TokenTypes#EXPR}
     * @return true if variable nested in exprAst is used
     */
    private static boolean isIncrementOrDecrementVariableUsed(DetailAST exprAst) {
        return TokenUtil.isOfType(exprAst.getParent(),
                TokenTypes.ELIST, TokenTypes.INDEX_OP, TokenTypes.ASSIGN)
                && exprAst.getParent().getParent().getType() != TokenTypes.FOR_ITERATOR;
    }

    /**
     * Maintains information about the variable.
     */
    private static final class VariableDesc {

        /**
         * The name of the variable.
         */
        private final String name;

        /**
         * Ast of type {@link TokenTypes#TYPE}.
         */
        private final DetailAST typeAst;

        /**
         * The scope of variable is determined by the ast of type
         * {@link TokenTypes#SLIST} or {@link TokenTypes#LITERAL_FOR}
         * or {@link TokenTypes#OBJBLOCK} which is enclosing the variable.
         */
        private final DetailAST scope;

        /**
         * Is an instance variable or a class variable.
         */
        private boolean instVarOrClassVar;

        /**
         * Is the variable used.
         */
        private boolean used;

        /**
         * Create a new VariableDesc instance.
         *
         * @param name name of the variable
         * @param typeAst ast of type {@link TokenTypes#TYPE}
         * @param scope ast of type {@link TokenTypes#SLIST} or
         *              {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
         *              which is enclosing the variable
         */
        private VariableDesc(String name, DetailAST typeAst, DetailAST scope) {
            this.name = name;
            this.typeAst = typeAst;
            this.scope = scope;
        }

        /**
         * Create a new VariableDesc instance.
         *
         * @param name name of the variable
         */
        private VariableDesc(String name) {
            this(name, null, null);
        }

        /**
         * Create a new VariableDesc instance.
         *
         * @param name name of the variable
         * @param scope ast of type {@link TokenTypes#SLIST} or
         *              {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
         *              which is enclosing the variable
         */
        private VariableDesc(String name, DetailAST scope) {
            this(name, null, scope);
        }

        /**
         * Get the name of variable.
         *
         * @return name of variable
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
         * which is enclosing the variable i.e. its scope.
         *
         * @return the scope associated with the variable
         */
        public DetailAST getScope() {
            return scope;
        }

        /**
         * Register the variable as used.
         */
        public void registerAsUsed() {
            used = true;
        }

        /**
         * Register the variable as an instance variable or
         * class variable.
         */
        public void registerAsInstOrClassVar() {
            instVarOrClassVar = true;
        }

        /**
         * Is the variable used or not.
         *
         * @return true if variable is used
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Is an instance variable or a class variable.
         *
         * @return true if is an instance variable or a class variable
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
         * A stack of type declaration's instance and static variables.
         */
        private final Deque<VariableDesc> instanceAndClassVarStack;

        /**
         * Create a new TypeDeclDesc instance.
         *
         * @param qualifiedName qualified name
         * @param depth depth of nesting
         * @param typeDeclAst type declaration ast node
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
         * Get the copy of variables in instanceAndClassVar stack with updated scope.
         *
         * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
         * @return copy of variables in instanceAndClassVar stack with updated scope.
         */
        public Deque<VariableDesc> getUpdatedCopyOfVarStack(DetailAST literalNewAst) {
            final DetailAST updatedScope = literalNewAst.getLastChild();
            final Deque<VariableDesc> instAndClassVarDeque = new ArrayDeque<>();
            instanceAndClassVarStack.forEach(instVar -> {
                final VariableDesc variableDesc = new VariableDesc(instVar.getName(),
                        updatedScope);
                variableDesc.registerAsInstOrClassVar();
                instAndClassVarDeque.push(variableDesc);
            });
            return instAndClassVarDeque;
        }

        /**
         * Add an instance variable or class variable to the stack.
         *
         * @param variableDesc variable to be added
         */
        public void addInstOrClassVar(VariableDesc variableDesc) {
            instanceAndClassVarStack.push(variableDesc);
        }
    }
}
