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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a class which has only private constructors
 * is declared as final. Doesn't check for classes nested in interfaces
 * or annotations, as they are always {@code final} there.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalClass&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * final class MyClass {  // OK
 *   private MyClass() { }
 * }
 *
 * class MyClass { // violation, class should be declared final
 *   private MyClass() { }
 * }
 *
 * class MyClass { // OK, since it has a public constructor
 *   int field1;
 *   String field2;
 *   private MyClass(int value) {
 *     this.field1 = value;
 *     this.field2 = " ";
 *   }
 *   public MyClass(String value) {
 *     this.field2 = value;
 *     this.field1 = 0;
 *   }
 * }
 *
 * interface CheckInterface
 * {
 *   class MyClass { // OK, nested class in interface is always final
 *     private MyClass() {}
 *   }
 * }
 *
 * public @interface Test {
 *   public boolean enabled()
 *   default true;
 *   class MyClass { // OK, class nested in an annotation is always final
 *     private MyClass() { }
 *   }
 * }
 *
 * class TestAnonymousInnerClasses { // OK, class has an anonymous inner class.
 *     public static final TestAnonymousInnerClasses ONE = new TestAnonymousInnerClasses() {
 *
 *     };
 *
 *     private TestAnonymousInnerClasses() {
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
 * {@code final.class}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@FileStatefulCheck
public class FinalClassCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "final.class";

    /**
     * Character separate package names in qualified name of java class.
     */
    private static final String PACKAGE_SEPARATOR = ".";

    /** Keeps ClassDesc objects for all inner classes. */
    private Map<String, ClassDesc> innerClasses;

    /** Keeps ClassDesc objects for stack of declared classes. */
    private Deque<ClassDesc> classes;

    /** Full qualified name of the package. */
    private String packageName;

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
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.LITERAL_NEW,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        classes = new ArrayDeque<>();
        innerClasses = new TreeMap<>();
        packageName = "";
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                packageName = extractQualifiedName(ast.getFirstChild().getNextSibling());
                break;

            case TokenTypes.ENUM_DEF:
            case TokenTypes.RECORD_DEF:
                break;
            case TokenTypes.CLASS_DEF:
                visitClass(ast);
                break;

            case TokenTypes.CTOR_DEF:
                visitCtor(ast);
                break;

            case TokenTypes.LITERAL_NEW:
                if (ast.getFirstChild() != null
                        && ast.getLastChild().getType() == TokenTypes.OBJBLOCK) {
                    for (ClassDesc classDesc : classes) {
                        if (doesNameOfClassMatchAnonymousInnerClassName(ast, classDesc)) {
                            classDesc.registerAnonymousInnerClass();
                        }
                    }
                }
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            classes.pop();
        }
        if (TokenUtil.isRootNode(ast.getParent())) {
            innerClasses.forEach((key, value) -> {
                final DetailAST classAst = value.getClassAst();
                registerNestedSubclassToOuterSuperClasses(classAst, key);
            });
            innerClasses.forEach((key, value) -> {
                if (shouldBeDeclaredAsFinal(value)) {
                    final String className = getClassNameFromQualifiedName(key);
                    log(value.getClassAst(), MSG_KEY, className);
                }
            });
        }
    }

    /**
     * Called to process a type definition.
     *
     * @param ast the token to process
     */
    private void visitClass(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isFinal = modifiers.findFirstToken(TokenTypes.FINAL) != null;
        final boolean isAbstract = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null;

        final String qualifiedClassName = getQualifiedClassName(ast);
        final ClassDesc currClass = new ClassDesc(
                qualifiedClassName, isFinal, isAbstract, classes.size(), ast);
        classes.push(currClass);
        innerClasses.put(qualifiedClassName, currClass);
    }

    /**
     * Called to process a constructor definition.
     *
     * @param ast the token to process
     */
    private void visitCtor(DetailAST ast) {
        if (!ScopeUtil.isInEnumBlock(ast) && !ScopeUtil.isInRecordBlock(ast)) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final ClassDesc desc = classes.peek();
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null) {
                desc.registerNonPrivateCtor();
            }
            else {
                desc.registerPrivateCtor();
            }
        }
    }

    /**
     * Checks whether a class should be declared as final or not.
     *
     * @param desc description of the class
     * @return true if given class should be declared as final otherwise false
     */
    private static boolean shouldBeDeclaredAsFinal(ClassDesc desc) {
        return desc.isWithPrivateCtor()
                && !(desc.isDeclaredAsAbstract()
                    || desc.isWithAnonymousInnerClass())
                && !desc.isDeclaredAsFinal()
                && !desc.isWithNonPrivateCtor()
                && !desc.isWithNestedSubclass()
                && !ScopeUtil.isInInterfaceOrAnnotationBlock(desc.getClassAst());
    }

    /**
     * Get name of class (with qualified package if specified) in {@code ast}.
     *
     * @param ast ast to extract class name from
     * @return qualified name
     */
    private static String extractQualifiedName(DetailAST ast) {
        return FullIdent.createFullIdent(ast).getText();
    }

    /**
     * Register to outer super class of given classAst that
     * given classAst is extending them.
     *
     * @param classAst class which outer super class will be
     *                 informed about nesting subclass
     * @param qualifiedClassName qualifies class name(with package) of the current class
     */
    private void registerNestedSubclassToOuterSuperClasses(DetailAST classAst,
                                                           String qualifiedClassName) {
        final String currentAstSuperClassName = getSuperClassName(classAst);
        if (currentAstSuperClassName != null) {
            final List<ClassDesc> sameNamedClassList = classesWithSameName(
                    currentAstSuperClassName);
            if (sameNamedClassList.isEmpty()) {
                final ClassDesc classDesc = innerClasses.get(currentAstSuperClassName);
                if (classDesc != null) {
                    classDesc.registerNestedSubclass();
                }
            }
            else {
                registerNearestAsSuperClass(qualifiedClassName, sameNamedClassList);
            }
        }
    }

    /**
     * Checks if there is a class with same name.
     *
     * @param className name of the class
     * @return true if there is another class with same name.
     */
    private List<ClassDesc> classesWithSameName(String className) {
        return innerClasses.entrySet().stream()
                .filter(entry -> {
                    return entry.getKey().endsWith(
                            PACKAGE_SEPARATOR + className);
                })
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    /**
     * For all classes with the same name as the superclass, finds the nearest one
     * and registers a subclass for it.
     *
     * @param superClassName current class name
     * @param classesWithSameName classes which same name as super class
     */
    private static void registerNearestAsSuperClass(String superClassName,
                                                    List<ClassDesc> classesWithSameName) {
        final ClassDesc nearest = Collections.min(classesWithSameName, (first, second) -> {
            int diff = Integer.compare(
                    classNameMatchingCount(superClassName, second.getQualifiedName()),
                    classNameMatchingCount(superClassName, first.getQualifiedName()));
            if (diff == 0) {
                diff = Integer.compare(first.getDepth(), second.getDepth());
            }
            return diff;
        });
        nearest.registerNestedSubclass();
    }

    /**
     * Calculates and returns the class name matching count.
     * Eg-
     * <br>
     * Suppose our pattern class is {@code foo.a.b} and class to be matched is
     * {@code foo.a.ball} then classNameMatchingCount would be calculated by comparing every
     * character, and updating main counter when we hit "."
     * to prevent matching "a.b" with "a.ball". In this case classNameMatchingCount would
     * be equal to 6 and not 7 (b of ball is not counted).
     *
     * @param patternClass class against which the given class has to be matched
     * @param classToBeMatched class to be matched
     * @return class name matching count
     */
    private static int classNameMatchingCount(String patternClass, String classToBeMatched) {
        final int minLength = Math.min(classToBeMatched.length(), patternClass.length());
        int counter = 0;
        int countToBeRegistered = 0;
        for (int i = 0; i < minLength; i++) {
            if (patternClass.charAt(i) == classToBeMatched.charAt(i)) {
                counter++;
                if (patternClass.charAt(i) == PACKAGE_SEPARATOR.charAt(0)) {
                    countToBeRegistered = counter;
                }
            }
            else {
                break;
            }
        }
        return countToBeRegistered;
    }

    /**
     * Check if class name matches with anonymous inner class name.
     *
     * @param ast current ast.
     * @param classDesc class to match.
     * @return true if current class name matches anonymous inner
     *         class name.
     */
    private static boolean doesNameOfClassMatchAnonymousInnerClassName(DetailAST ast,
                                                               ClassDesc classDesc) {
        final String[] className = classDesc.getQualifiedName().split("\\.");
        return ast.getFirstChild().getText().equals(className[className.length - 1]);
    }

    /**
     * Get qualified class name from given class Ast.
     *
     * @param classAst class to get qualified class name
     * @return qualified class name of a class
     */
    private String getQualifiedClassName(DetailAST classAst) {
        final String className = classAst.findFirstToken(TokenTypes.IDENT).getText();
        String outerClassQualifiedName = null;
        if (!classes.isEmpty()) {
            outerClassQualifiedName = classes.peek().getQualifiedName();
        }
        return getQualifiedClassName(packageName, outerClassQualifiedName, className);
    }

    /**
     * Calculate qualified class name(package + class name) laying inside given
     * outer class.
     *
     * @param packageName package name, empty string on default package
     * @param outerClassQualifiedName qualified name(package + class) of outer class,
     *                           null if doesn't exist
     * @param className class name
     * @return qualified class name(package + class name)
     */
    private static String getQualifiedClassName(String packageName, String outerClassQualifiedName,
                                                String className) {
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
     * Get super class name of given class.
     *
     * @param classAst class
     * @return super class name or null if super class is not specified
     */
    private static String getSuperClassName(DetailAST classAst) {
        String superClassName = null;
        final DetailAST classExtend = classAst.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (classExtend != null) {
            superClassName = extractQualifiedName(classExtend.getFirstChild());
        }
        return superClassName;
    }

    /**
     * Get class name from qualified name.
     *
     * @param qualifiedName qualified class name
     * @return class name
     */
    private static String getClassNameFromQualifiedName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(PACKAGE_SEPARATOR) + 1);
    }

    /** Maintains information about class' ctors. */
    private static final class ClassDesc {

        /** Corresponding node. */
        private final DetailAST classAst;

        /** Qualified class name(with package). */
        private final String qualifiedName;

        /** Is class declared as final. */
        private final boolean declaredAsFinal;

        /** Is class declared as abstract. */
        private final boolean declaredAsAbstract;

        /** Class nesting level. */
        private final int depth;

        /** Does class have non-private ctors. */
        private boolean withNonPrivateCtor;

        /** Does class have private ctors. */
        private boolean withPrivateCtor;

        /** Does class have nested subclass. */
        private boolean withNestedSubclass;

        /** Does class have anonymous inner class. */
        private boolean withAnonymousInnerClass;

        /**
         *  Create a new ClassDesc instance.
         *
         *  @param qualifiedName qualified class name(with package)
         *  @param declaredAsFinal indicates if the
         *         class declared as final
         *  @param declaredAsAbstract indicates if the
         *         class declared as abstract
         *  @param depth class nesting level
         *  @param classAst classAst node
         */
        /* package */ ClassDesc(String qualifiedName, boolean declaredAsFinal,
                boolean declaredAsAbstract, int depth, DetailAST classAst) {
            this.qualifiedName = qualifiedName;
            this.declaredAsFinal = declaredAsFinal;
            this.declaredAsAbstract = declaredAsAbstract;
            this.depth = depth;
            this.classAst = classAst;
        }

        /**
         * Get qualified class name.
         *
         * @return qualified class name
         */
        private String getQualifiedName() {
            return qualifiedName;
        }

        /**
         * Get the classAst node.
         *
         * @return classAst node
         */
        public DetailAST getClassAst() {
            return classAst;
        }

        /** Adds private ctor. */
        private void registerPrivateCtor() {
            withPrivateCtor = true;
        }

        /** Adds non-private ctor. */
        private void registerNonPrivateCtor() {
            withNonPrivateCtor = true;
        }

        /** Adds nested subclass. */
        private void registerNestedSubclass() {
            withNestedSubclass = true;
        }

        /** Adds anonymous inner class. */
        private void registerAnonymousInnerClass() {
            withAnonymousInnerClass = true;
        }

        /**
         *  Does class have private ctors.
         *
         *  @return true if class has private ctors
         */
        private boolean isWithPrivateCtor() {
            return withPrivateCtor;
        }

        /**
         *  Does class have non-private ctors.
         *
         *  @return true if class has non-private ctors
         */
        private boolean isWithNonPrivateCtor() {
            return withNonPrivateCtor;
        }

        /**
         * Does class have nested subclass.
         *
         * @return true if class has nested subclass
         */
        private boolean isWithNestedSubclass() {
            return withNestedSubclass;
        }

        /**
         *  Is class declared as final.
         *
         *  @return true if class is declared as final
         */
        private boolean isDeclaredAsFinal() {
            return declaredAsFinal;
        }

        /**
         *  Returns class nesting level.
         *
         *  @return class nesting level
         */
        private int getDepth() {
            return depth;
        }

        /**
         *  Is class declared as abstract.
         *
         *  @return true if class is declared as final
         */
        private boolean isDeclaredAsAbstract() {
            return declaredAsAbstract;
        }

        /**
         * Does class have an anonymous inner class.
         *
         * @return true if class has anonymous inner class
         */
        private boolean isWithAnonymousInnerClass() {
            return withAnonymousInnerClass;
        }

    }
}
