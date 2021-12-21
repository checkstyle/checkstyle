////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
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
    };

    /**
     * An array of class and interface and annotated interface tokens
     * and enum.
     */
    private static final int[] CLASS_AND_INTERFACE = {
        TokenTypes.CLASS_DEF,
        TokenTypes.INTERFACE_DEF,
        TokenTypes.ANNOTATION_DEF,
        TokenTypes.ENUM_DEF,
    };

    /** Package separator. */
    private static final String PACKAGE_SEPARATOR = ".";

    /**
     * Keeps tracks of the variables declared in file.
     */
    private final Deque<VariableDesc> variables;

    /**
     * Keeps track of all the classes, interfaces, annotated interfaces
     * present in the file. Collectively in this check, they are addressed as
     * classes. Pops the class out of the stack while leaving the class
     * in visitor pattern.
     */
    private final Deque<ClassDesc> classes;

    /**
     * Maps class AST to their respective ClassDesc objects.
     */
    private final Map<DetailAST, ClassDesc> classAstToClassDesc;

    /**
     * Maps local anonymous inner class to the ClassDesc object of the class
     * containing it.
     */
    private final Map<DetailAST, ClassDesc> anonInnerAstToClassDesc;

    /**
     * Queue of tokens of type {@link UnusedLocalVariableCheck#CONTAINERS_FOR_ANON_INNERS}
     * and {@link TokenTypes#LAMBDA} in some cases.
     */
    private final Queue<DetailAST> anonInnerClassHolders;

    /**
     * Name of the package.
     */
    private String packageName;

    /**
     * Depth at which a class is nested, 0 for top level classes.
     */
    private int depth;

    /**
     * Block containing local anon inner class, keeps updating throughout the check.
     * Acts as a tracker and checks whether the current node being visited is present
     * inside the block containing a local anonymous inner class.
     */
    private DetailAST blockContainingLocalAnonInnerClass;

    /**
     * Creates a new {@code UnusedLocalVariableCheck} instance.
     */
    public UnusedLocalVariableCheck() {
        variables = new ArrayDeque<>();
        classes = new ArrayDeque<>();
        classAstToClassDesc = new HashMap<>();
        anonInnerAstToClassDesc = new HashMap<>();
        anonInnerClassHolders = new ArrayDeque<>();
        blockContainingLocalAnonInnerClass = null;
        packageName = "";
        depth = 0;
    }

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
        // No need to set blockContainingLocalAnonInnerClass to null, if
        // its value gets changed during the check then it is changed back to null
        // inside the check only.
        variables.clear();
        classes.clear();
        classAstToClassDesc.clear();
        anonInnerAstToClassDesc.clear();
        anonInnerClassHolders.clear();
        packageName = "";
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
        else if (TokenUtil.isOfType(ast, CLASS_AND_INTERFACE)) {
            visitClassOrInterfaceToken(ast);
        }
        else if (type == TokenTypes.PACKAGE_DEF) {
            packageName = extractQualifiedName(ast.getFirstChild().getNextSibling());
        }
        else if (type == TokenTypes.LITERAL_NEW
                && isInsideLocalAnonInnerClass(ast)) {
            visitLocalAnonInnerClass(ast);
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
        else if (ast.equals(blockContainingLocalAnonInnerClass)) {
            blockContainingLocalAnonInnerClass = null;
        }
        else if (isNonLocalClassOrInterface(ast)) {
            depth--;
            classes.pop();
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#DOT}.
     *
     * @param dotAst dotAst
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void visitDotToken(DetailAST dotAst, Deque<VariableDesc> variablesStack) {
        if (blockContainingLocalAnonInnerClass == null
                && dotAst.getParent().getType() != TokenTypes.LITERAL_NEW
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
        if (blockContainingLocalAnonInnerClass == null) {
            addLocalVariables(varDefAst, variables);
            addInstanceVarOrClassVar(varDefAst);
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#IDENT}.
     *
     * @param identAst identAst
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void visitIdentToken(DetailAST identAst, Deque<VariableDesc> variablesStack) {
        if (blockContainingLocalAnonInnerClass == null) {
            final DetailAST parentAst = identAst.getParent();
            if (!TokenUtil.isOfType(parentAst, UNACCEPTABLE_PARENT_OF_IDENT)
                    && shouldCheckIdentWithMethodRefParent(identAst)) {
                checkIdentifierAst(identAst, variablesStack);
            }
        }
    }

    /**
     * Visit ast of type {@link TokenTypes#CLASS_DEF}.
     *
     * @param ast ast
     */
    private void visitClassOrInterfaceToken(DetailAST ast) {
        if (isNonLocalClassOrInterface(ast)) {
            final String qualifiedName = getQualifiedClassOrInterfaceName(ast);
            final ClassDesc currClass = new ClassDesc(qualifiedName, depth, ast);
            depth++;
            classes.push(currClass);
            classAstToClassDesc.put(ast, currClass);
        }
    }

    /**
     * Visit the local anon inner class.
     *
     * @param literalNewAst literalNewAst
     */
    private void visitLocalAnonInnerClass(DetailAST literalNewAst) {
        anonInnerAstToClassDesc.put(literalNewAst, classes.peek());
        if (blockContainingLocalAnonInnerClass == null) {
            blockContainingLocalAnonInnerClass = getBlockContainingLocalAnonInnerClass(
                    literalNewAst);
            anonInnerClassHolders.add(blockContainingLocalAnonInnerClass);
        }
    }

    /**
     * Get name of package and class by concatenating the identifier values under
     * {@link TokenTypes#DOT}.
     * Duplicated, will be moved to util after merging
     * <a>https://github.com/checkstyle/checkstyle/pull/10968</a>
     *
     * @param ast ast to extract class name from
     * @return qualified name
     */
    private static String extractQualifiedName(DetailAST ast) {
        return FullIdent.createFullIdent(ast).getText();
    }

    /**
     * Whether DetailAst node of type {@link TokenTypes#LITERAL_NEW} is a part of a local
     * anonymous inner class.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return true if variableDefAst is an instance variable in local anonymous inner class
     */
    private static boolean isInsideLocalAnonInnerClass(DetailAST literalNewAst) {
        boolean result = false;
        final DetailAST lastChild = literalNewAst.getLastChild();
        if (lastChild != null && lastChild.getType() == TokenTypes.OBJBLOCK) {
            DetailAST parentAst = literalNewAst.getParent();
            while (parentAst.getType() != TokenTypes.SLIST) {
                if (parentAst.getType() == TokenTypes.ENUM_CONSTANT_DEF
                        || parentAst.getType() == TokenTypes.OBJBLOCK
                        && TokenUtil.isOfType(parentAst.getParent(), CLASS_AND_INTERFACE)) {
                    break;
                }
                parentAst = parentAst.getParent();
            }
            result = parentAst.getType() == TokenTypes.SLIST;
        }
        return result;
    }

    /**
     * Traverse {@code variablesStack} stack and log the violations.
     *
     * @param ast ast
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void logViolations(DetailAST ast, Deque<VariableDesc> variablesStack) {
        while (!variablesStack.isEmpty() && variablesStack.peek().getScope() == ast) {
            final VariableDesc variableDesc = variablesStack.pop();
            if (blockContainingLocalAnonInnerClass == null
                    && !variableDesc.isUsed()
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
        while (!anonInnerClassHolders.isEmpty()) {
            final DetailAST holder = anonInnerClassHolders.remove();
            iterateOverBlockContainingLocalAnonInnerClass(holder, new ArrayDeque<>());
        }
    }

    /**
     * Whether a class is a non-local class or an interface is a non-local interface.
     * Annotated interfaces are always non-local.
     *
     * @param ast ast
     * @return true if a class is a local class or an interface is a local interface.
     */
    private static boolean isNonLocalClassOrInterface(DetailAST ast) {
        return ast.getParent().getType() != TokenTypes.SLIST
                && TokenUtil.isOfType(ast, CLASS_AND_INTERFACE);
    }

    /**
     * Get the block containing local anon inner class.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return the block containing local anon inner class
     */
    private static DetailAST getBlockContainingLocalAnonInnerClass(DetailAST literalNewAst) {
        DetailAST parentAst = literalNewAst.getParent();
        DetailAST result = null;
        while (!TokenUtil.isOfType(parentAst, CONTAINERS_FOR_ANON_INNERS)) {
            if (parentAst.getType() == TokenTypes.LAMBDA
                    && parentAst.getParent()
                    .getParent().getParent().getType() == TokenTypes.OBJBLOCK) {
                result = parentAst;
                break;
            }
            parentAst = parentAst.getParent();
            result = parentAst;
        }
        return result;
    }

    /**
     * Add local variablesStack to the {@code variablesStack} stack.
     * Also adds the instance variablesStack defined in a local anonymous inner class.
     *
     * @param varDefAst ast node of type {@link TokenTypes#VARIABLE_DEF}
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private static void addLocalVariables(DetailAST varDefAst, Deque<VariableDesc> variablesStack) {
        final DetailAST parentAst = varDefAst.getParent();
        final DetailAST grandParent = parentAst.getParent();
        final boolean isInstanceVarInLocalAnonymousInnerClass =
                grandParent.getType() == TokenTypes.LITERAL_NEW
                        && isInsideLocalAnonInnerClass(grandParent);
        if (isInstanceVarInLocalAnonymousInnerClass
                || parentAst.getType() != TokenTypes.OBJBLOCK) {
            final DetailAST ident = varDefAst.findFirstToken(TokenTypes.IDENT);
            final VariableDesc desc = new VariableDesc(ident.getText(),
                    varDefAst.findFirstToken(TokenTypes.TYPE), findScopeOfVariable(varDefAst));
            if (isInstanceVarInLocalAnonymousInnerClass) {
                desc.registerAsInstVarOrClassVar();
            }
            variablesStack.push(desc);
        }
    }

    /**
     * Add instance variables and class variables to the {@link ClassDesc#instanceAndClassVarStack}.
     *
     * @param varDefAst ast node of type {@link TokenTypes#VARIABLE_DEF}
     */
    private void addInstanceVarOrClassVar(DetailAST varDefAst) {
        final DetailAST parentAst = varDefAst.getParent();
        if (parentAst.getType() == TokenTypes.OBJBLOCK
                && isNonLocalClassOrInterface(parentAst.getParent())
                && !isPrivateInstanceVariable(varDefAst)) {
            final DetailAST ident = varDefAst.findFirstToken(TokenTypes.IDENT);
            final VariableDesc desc = new VariableDesc(ident.getText(),
                    varDefAst.findFirstToken(TokenTypes.TYPE), findScopeOfVariable(varDefAst));
            classAstToClassDesc.get(parentAst.getParent()).addInstOrClassVar(desc);
        }
    }

    /**
     * Whether instance variable or class variable has private access modifier.
     *
     * @param varDefAst ast node of type {@link TokenTypes#VARIABLE_DEF}
     * @return true if instance variable or class variable has private access modifier
     */
    private static boolean isPrivateInstanceVariable(DetailAST varDefAst) {
        return CheckUtil.getAccessModifierFromModifiersToken(
                varDefAst) == AccessModifierOption.PRIVATE;
    }

    /**
     * Get the {@link ClassDesc} of the super class of anonymous inner class.
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return {@link ClassDesc} of the super class of anonymous inner class
     */
    private ClassDesc getSuperClassOfAnonInnerClass(DetailAST literalNewAst) {
        ClassDesc obtainedClass = null;
        final String shortNameOfClass = getShortNameOfAnonInnerClass(literalNewAst);

        final List<ClassDesc> classesWithSameName = classesWithSameName(shortNameOfClass);
        if (!classesWithSameName.isEmpty()) {
            obtainedClass = getTheNearestClass(
                    anonInnerAstToClassDesc.get(literalNewAst).getQualifiedName(),
                    classesWithSameName);
        }
        return obtainedClass;
    }

    /**
     * Get the short name of super class of anonymous inner class.
     * Example-
     * <pre>
     * TestClass.NestedClass obj = new Test().new NestedClass() {};
     * // Short name will be Test.NestedClass
     * </pre>
     *
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     * @return short name of base class of anonymous inner class
     */
    public static String getShortNameOfAnonInnerClass(DetailAST literalNewAst) {
        DetailAST parentAst = literalNewAst.getParent();
        while (TokenUtil.isOfType(parentAst, TokenTypes.LITERAL_NEW, TokenTypes.DOT)) {
            parentAst = parentAst.getParent();
        }
        final DetailAST firstChild = parentAst.getFirstChild();
        return extractQualifiedName(firstChild);
    }

    /**
     * Add non-private instance variables of the super class of the anonymous class
     * to the variables stack.
     *
     * @param obtainedClass super class of the anon inner class
     * @param variablesStack stack of all the relevant variables in the scope
     * @param literalNewAst ast node of type {@link TokenTypes#LITERAL_NEW}
     */
    private void modifyVariablesStack(ClassDesc obtainedClass,
            Deque<VariableDesc> variablesStack,
            DetailAST literalNewAst) {
        if (obtainedClass != null) {
            final Deque<VariableDesc> instAndClassVarDeque = classAstToClassDesc.get(
                    obtainedClass.getClassOrInterfaceAst()).getUpdatedCopyOfVarStack(literalNewAst);
            instAndClassVarDeque.forEach(variablesStack::push);
        }
    }

    /**
     * Checks if there is a class with same name.
     * Duplicated, will be moved to util after merging
     * <a>https://github.com/checkstyle/checkstyle/pull/10968</a>
     *
     * @param className name of the class
     * @return true if there is another class with same name.
     * @noinspection CallToStringConcatCanBeReplacedByOperator
     */
    private List<ClassDesc> classesWithSameName(String className) {
        return classAstToClassDesc.values().stream()
                .filter(classDesc -> {
                    return classDesc.getQualifiedName().endsWith(
                            PACKAGE_SEPARATOR.concat(className));
                })
                .collect(Collectors.toList());
    }

    /**
     * For all classes with the same name as the superclass, finds the nearest one
     * and registers a subclass for it.
     * Duplicated, will be moved to util after merging
     * <a>https://github.com/checkstyle/checkstyle/pull/10968</a>
     *
     * @param outerClassName outer class of anonymous inner class.
     * @param classesWithSameName classes which same name as super class
     * @return the nearest class
     */
    private static ClassDesc getTheNearestClass(String outerClassName,
            List<ClassDesc> classesWithSameName) {
        return Collections.min(classesWithSameName, (first, second) -> {
            int diff = Integer.compare(
                    classNameMatchingCount(outerClassName, second.getQualifiedName()),
                    classNameMatchingCount(outerClassName, first.getQualifiedName()));
            if (diff == 0) {
                diff = Integer.compare(first.getDepth(), second.getDepth());
            }
            return diff;
        });
    }

    /**
     * Calculates and returns the class name matching count.
     *
     * <p>
     * Suppose our pattern class is {@code foo.a.b} and class to be matched is
     * {@code foo.a.ball} then classNameMatchingCount would be calculated by comparing every
     * character, and updating main counter when we hit "."
     * to prevent matching "a.b" with "a.ball". In this case classNameMatchingCount would
     * be equal to 6 and not 7 (b of ball is not counted).
     * </p>
     * Duplicated, will be moved to util after merging
     * <a>https://github.com/checkstyle/checkstyle/pull/10968</a>
     *
     * @param patternClass class against which the given class has to be matched
     * @param classToBeMatched class to be matched
     * @return class name matching count
     */
    private static int classNameMatchingCount(String patternClass, String classToBeMatched) {
        final char packageSeparator = PACKAGE_SEPARATOR.charAt(0);
        final int length = Math.min(classToBeMatched.length(), patternClass.length());
        int result = 0;
        for (int i = 0; i < length && patternClass.charAt(i) == classToBeMatched.charAt(i); ++i) {
            if (patternClass.charAt(i) == packageSeparator) {
                result = i;
            }
        }
        return result;
    }

    /**
     * Get qualified class name from given class Ast.
     * Duplicated, will be moved to util after merging
     * <a>https://github.com/checkstyle/checkstyle/pull/10968</a>
     *
     * @param classAst classAst
     * @return qualified name of class
     */
    private String getQualifiedClassOrInterfaceName(DetailAST classAst) {
        final String className = classAst.findFirstToken(TokenTypes.IDENT).getText();
        String outerClassQualifiedName = null;
        if (!classes.isEmpty()) {
            outerClassQualifiedName = classes.peek().getQualifiedName();
        }
        return getQualifiedClassOrInterfaceName(packageName, outerClassQualifiedName, className);
    }

    /**
     * Get the name of qualified name of class by combining {@code packageName},
     * {@code outerClassQualifiedName} and {@code className}.
     * Duplicated, will be moved to util after merging
     * <a>https://github.com/checkstyle/checkstyle/pull/10968</a>
     *
     * @param packageName packageName
     * @param outerClassQualifiedName outerClassQualifiedName
     * @param className className
     * @return the name of qualified name of class by combining {@code packageName},
     *         {@code outerClassQualifiedName} and {@code className}
     */
    private static String getQualifiedClassOrInterfaceName(String packageName,
            String outerClassQualifiedName, String className) {
        final String qualifiedClassName;

        if (outerClassQualifiedName == null) {
            if (packageName.isEmpty()) {
                qualifiedClassName = className;
            }
            else {
                qualifiedClassName = packageName + PACKAGE_SEPARATOR + className;
            }
        }
        else {
            qualifiedClassName = outerClassQualifiedName + PACKAGE_SEPARATOR + className;
        }
        return qualifiedClassName;
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
     * Visit ast nodes in the tree. We visit all the tokens present under
     * ast node of type {@link UnusedLocalVariableCheck#CONTAINERS_FOR_ANON_INNERS}
     * once again.
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
        else if (type == TokenTypes.LITERAL_NEW
                && isInsideLocalAnonInnerClass(ast)) {
            final ClassDesc obtainedClass = getSuperClassOfAnonInnerClass(ast);
            modifyVariablesStack(obtainedClass, variablesStack, ast);
        }
    }

    /**
     * Leave ast nodes in the tree. We leave all the tokens present under
     * ast node of type {@link UnusedLocalVariableCheck#CONTAINERS_FOR_ANON_INNERS}
     * once again.
     *
     * @param ast ast
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private void customLeaveToken(DetailAST ast, Deque<VariableDesc> variablesStack) {
        if (TokenUtil.isOfType(ast, SCOPES)) {
            logViolations(ast, variablesStack);
        }
    }

    /**
     * Whether an ident with parent node of type {@link TokenTypes#METHOD_REF}
     * should be checked or not.
     *
     * @param identAst identAst
     * @return true if an ident with parent node of type {@link TokenTypes#METHOD_REF}
     *         should be checked or if the parent type is not {@link TokenTypes#METHOD_REF}
     */
    public static boolean shouldCheckIdentWithMethodRefParent(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        boolean result = true;
        if (parent.getType() == TokenTypes.METHOD_REF) {
            result = parent.getFirstChild() == identAst
                    && parent.getLastChild().getType() != TokenTypes.LITERAL_NEW;
        }
        return result;
    }

    /**
     * Whether to check identifier token nested under dotAst.
     *
     * @param dotAst dotAst
     * @return true if ident nested under dotAst should be checked
     */
    public static boolean shouldCheckIdentTokenNestedUnderDot(DetailAST dotAst) {

        return !TokenUtil.findFirstTokenByPredicate(dotAst,
                        childAst -> {
                            return TokenUtil.isOfType(childAst,
                                    UNACCEPTABLE_CHILD_OF_DOT);
                        })
                .isPresent();
    }

    /**
     * Checks the identifier ast.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @param variablesStack stack of all the relevant variables in the scope
     */
    private static void checkIdentifierAst(DetailAST identAst, Deque<VariableDesc> variablesStack) {
        for (VariableDesc variableDesc : variablesStack) {
            if (identAst.getText().equals(variableDesc.getName())
                    && !isLeftHandSideValue(identAst)) {
                if (!variableDesc.isInstVarOrClassVar()) {
                    variableDesc.registerAsUsed();
                }
                break;
            }
        }
    }

    /**
     * Find the scope of variable.
     *
     * @param variableDef DetailAst of type {@link TokenTypes#VARIABLE_DEF}
     * @return scope of variableDef
     */
    private static DetailAST findScopeOfVariable(DetailAST variableDef) {
        final DetailAST result;
        final DetailAST parentAst = variableDef.getParent();
        if (TokenUtil.isOfType(parentAst, TokenTypes.SLIST, TokenTypes.OBJBLOCK)) {
            result = parentAst;
        }
        else {
            final DetailAST grandParent = parentAst.getParent();
            final DetailAST scopeAst = grandParent.findFirstToken(TokenTypes.SLIST);
            if (scopeAst == null) {
                result = grandParent;
            }
            else {
                result = scopeAst;
            }
        }
        return result;
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is
     * used as left-hand side value. An identifier is being used as a left-hand side
     * value if it is used as the left operand of an assignment or as an
     * operand of a stand-alone increment or decrement.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
     * @return true if identAst is used as a left-hand side value
     */
    private static boolean isLeftHandSideValue(DetailAST identAst) {
        final DetailAST parent = identAst.getParent();
        return isStandAloneIncrementOrDecrement(identAst)
                || parent.getType() == TokenTypes.ASSIGN
                && identAst != parent.getLastChild();
    }

    /**
     * Checks whether the DetailAst of type {@link TokenTypes#IDENT} is used as
     * an operand of a stand-alone increment or decrement.
     *
     * @param identAst DetailAst of type {@link TokenTypes#IDENT}
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
     * @param exprAst DetailAst of type {@link TokenTypes#EXPR}
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
         * DetailAst of type {@link TokenTypes#TYPE}.
         */
        private final DetailAST typeAst;

        /**
         * The scope of variable is determined by the DetailAst of type
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
         * @param typeAst DetailAst of type {@link TokenTypes#TYPE}
         * @param scope DetailAst of type {@link TokenTypes#SLIST} or
         *              {@link TokenTypes#LITERAL_FOR} or {@link TokenTypes#OBJBLOCK}
         *              which is enclosing the variable
         */
        /* package */ VariableDesc(String name, DetailAST typeAst, DetailAST scope) {
            this.name = name;
            this.typeAst = typeAst;
            this.scope = scope;
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
         * Get the associated DetailAst node of type {@link TokenTypes#TYPE}.
         *
         * @return the associated DetailAst node of type {@link TokenTypes#TYPE}
         */
        public DetailAST getTypeAst() {
            return typeAst;
        }

        /**
         * Get DetailAst of type {@link TokenTypes#SLIST}
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
        public void registerAsInstVarOrClassVar() {
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
     * Maintains information about the class.
     * FinalClassCheck contains a similar class, if agreed upon, both the classes can
     * be combined and moved to a separate package.
     */
    private static class ClassDesc {

        /**
         * Complete class name with package name and outer class name.
         */
        private final String qualifiedName;

        /**
         * Depth of nesting of class or interface or annotated interface.
         */
        private final int depth;

        /**
         * Ast node of type {@link TokenTypes#CLASS_DEF} or {@link TokenTypes#INTERFACE_DEF}
         * or {@link TokenTypes#ANNOTATION_DEF}.
         */
        private final DetailAST classOrInterfaceAst;

        /**
         * A stack of class's instance and static variables.
         */
        private final Deque<VariableDesc> instanceAndClassVarStack;

        /**
         * Create a new ClassDesc instance.
         *
         * @param qualifiedName qualified name
         * @param depth depth of nesting
         * @param classOrInterfaceAst ast node of type {@link TokenTypes#CLASS_DEF}
         *            or {@link TokenTypes#INTERFACE_DEF} or {@link TokenTypes#ANNOTATION_DEF}.
         */
        /* package */ ClassDesc(String qualifiedName, int depth,
                DetailAST classOrInterfaceAst) {
            this.qualifiedName = qualifiedName;
            this.depth = depth;
            this.classOrInterfaceAst = classOrInterfaceAst;
            instanceAndClassVarStack = new ArrayDeque<>();
        }

        /**
         * Get the complete class name i.e. class name with package name
         * and outer class.
         *
         * @return qualified class name
         */
        public String getQualifiedName() {
            return qualifiedName;
        }

        /**
         * Get the depth of nesting of a class or interface
         * or annotated interface.
         *
         * @return the depth of nesting of a class or interface or
         *         annotated interface
         */
        public int getDepth() {
            return depth;
        }

        /**
         * Get the ast node of type {@link TokenTypes#CLASS_DEF} or {@link TokenTypes#INTERFACE_DEF}
         * or {@link TokenTypes#ANNOTATION_DEF}.
         *
         * @return ast node of the class.
         */
        public DetailAST getClassOrInterfaceAst() {
            return classOrInterfaceAst;
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
                        instVar.getTypeAst(), updatedScope);
                variableDesc.registerAsInstVarOrClassVar();
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
