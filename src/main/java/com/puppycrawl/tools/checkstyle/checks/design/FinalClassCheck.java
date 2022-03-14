////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that a class that has only private constructors and has no descendant
 * classes is declared as final.
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
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.LITERAL_NEW,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        classes = new ArrayDeque<>();
        innerClasses = new HashMap<>();
        packageName = "";
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                packageName = extractQualifiedName(ast.getFirstChild().getNextSibling());
                break;

            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.INTERFACE_DEF:
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

    /**
     * Called to process a type definition.
     *
     * @param ast the token to process
     */
    private void visitClass(DetailAST ast) {
        final String qualifiedClassName = getQualifiedClassName(ast);
        final ClassDesc currClass = new ClassDesc(qualifiedClassName, classes.size(), ast);
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
            final ClassDesc desc = classes.getFirst();
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null) {
                desc.registerNonPrivateCtor();
            }
            else {
                desc.registerPrivateCtor();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            classes.pop();
        }
        if (TokenUtil.isRootNode(ast.getParent())) {
            // First pass: mark all classes that have derived inner classes
            innerClasses.forEach(this::registerNestedSubclassToOuterSuperClasses);
            // Second pass: report violation for all classes that should be declared as final
            innerClasses.forEach((qualifiedClassName, classDesc) -> {
                if (shouldBeDeclaredAsFinal(classDesc)) {
                    final String className = getClassNameFromQualifiedName(qualifiedClassName);
                    log(classDesc.getClassAst(), MSG_KEY, className);
                }
            });
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
                && !desc.isWithNestedSubclass();
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
     * @param qualifiedClassName qualifies class name(with package) of the current class
     * @param currentClass class which outer super class will be informed about nesting subclass
     */
    private void registerNestedSubclassToOuterSuperClasses(String qualifiedClassName,
                                                           ClassDesc currentClass) {
        final String superClassName = getSuperClassName(currentClass.getClassAst());
        if (superClassName != null) {
            final ClassDesc nearest =
                    getNearestClassWithSameName(superClassName, qualifiedClassName);
            if (nearest == null) {
                Optional.ofNullable(innerClasses.get(superClassName))
                        .ifPresent(ClassDesc::registerNestedSubclass);
            }
            else {
                nearest.registerNestedSubclass();
            }
        }
    }

    /**
     * Checks if there is a class with same name.
     *
     * @param className name of the class
     * @param superClassName name of the super class
     * @return true if there is another class with same name.
     * @noinspection CallToStringConcatCanBeReplacedByOperator
     */
    private ClassDesc getNearestClassWithSameName(String className, String superClassName) {
        final String dotAndClassName = PACKAGE_SEPARATOR.concat(className);
        return innerClasses.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith(dotAndClassName))
                .map(Map.Entry::getValue)
                .min((first, second) -> {
                    int diff = Integer.compare(
                            classNameMatchingCount(superClassName, second.getQualifiedName()),
                            classNameMatchingCount(superClassName, first.getQualifiedName()));
                    if (diff == 0) {
                        diff = Integer.compare(first.getDepth(), second.getDepth());
                    }
                    return diff;
                })
                .orElse(null);
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
         *  @param depth class nesting level
         *  @param classAst classAst node
         */
        /* package */ ClassDesc(String qualifiedName, int depth, DetailAST classAst) {
            this.qualifiedName = qualifiedName;
            this.depth = depth;
            this.classAst = classAst;
            final DetailAST modifiers = classAst.findFirstToken(TokenTypes.MODIFIERS);
            declaredAsFinal = modifiers.findFirstToken(TokenTypes.FINAL) != null;
            declaredAsAbstract = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null;
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
         *  Returns class nesting level.
         *
         *  @return class nesting level
         */
        private int getDepth() {
            return depth;
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
