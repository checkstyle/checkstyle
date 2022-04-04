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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;
import com.puppycrawl.tools.checkstyle.utils.TypeDeclarationDescription;

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

    /**
     * Maps anonymous inner class's {@link TokenTypes#LITERAL_NEW} node to
     * the outer type declaration's fully qualified name.
     */
    private Map<DetailAST, String> anonInnerClassToOuterTypeDecl;

    /** Keeps TypeDeclarationDescription object for stack of declared type descriptions. */
    private Deque<TypeDeclarationDescription> typeDeclarations;

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
        typeDeclarations = new ArrayDeque<>();
        innerClasses = new HashMap<>();
        anonInnerClassToOuterTypeDecl = new HashMap<>();
        packageName = "";
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.PACKAGE_DEF:
                packageName = CheckUtil.extractQualifiedName(ast.getFirstChild().getNextSibling());
                break;

            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.RECORD_DEF:
                final TypeDeclarationDescription description = new TypeDeclarationDescription(
                    getQualifiedTypeDeclarationName(ast), typeDeclarations.size(), ast);
                typeDeclarations.push(description);
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
                    anonInnerClassToOuterTypeDecl.put(ast, typeDeclarations.peek()
                        .getQualifiedName());
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
        final String qualifiedClassName = getQualifiedTypeDeclarationName(ast);
        final ClassDesc currClass = new ClassDesc(qualifiedClassName, typeDeclarations.size(), ast);
        typeDeclarations.push(currClass);
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
            // Can be only of type ClassDesc, preceding if statements guarantee it.
            final ClassDesc desc = (ClassDesc) typeDeclarations.getFirst();
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
        if (TokenUtil.isTypeDeclaration(ast.getType())) {
            typeDeclarations.pop();
        }
        if (TokenUtil.isRootNode(ast.getParent())) {
            anonInnerClassToOuterTypeDecl.forEach(this::registerAnonymousInnerClassToSuperClass);
            // First pass: mark all classes that have derived inner classes
            innerClasses.forEach(this::registerNestedSubclassToOuterSuperClasses);
            // Second pass: report violation for all classes that should be declared as final
            innerClasses.forEach((qualifiedClassName, classDesc) -> {
                if (shouldBeDeclaredAsFinal(classDesc)) {
                    final String className = getClassNameFromQualifiedName(qualifiedClassName);
                    log(classDesc.getTypeDeclarationAst(), MSG_KEY, className);
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
                    || desc.isSuperClassOfAnonymousInnerClass())
                && !desc.isDeclaredAsFinal()
                && !desc.isWithNonPrivateCtor()
                && !desc.isWithNestedSubclass();
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
        final String superClassName = getSuperClassName(currentClass.getTypeDeclarationAst());
        if (superClassName != null) {
            final ClassDesc nearest =
                    getNearestClassWithSameName(superClassName, qualifiedClassName, false);
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
     * Register to the super class of anonymous inner class that the given class is instantiated
     * by an anonymous inner class.
     *
     * @param literalNewAst ast node of {@link TokenTypes#LITERAL_NEW} representing anonymous inner
     *                      class
     * @param outerTypeDeclName Fully qualified name of the outer type declaration of anonymous
     *                          inner class
     */
    private void registerAnonymousInnerClassToSuperClass(DetailAST literalNewAst,
                                                         String outerTypeDeclName) {
        final String superClassName = CheckUtil.getShortNameOfAnonInnerClass(literalNewAst);
        final ClassDesc superClassDesc = getNearestClassWithSameName(
            superClassName, outerTypeDeclName, true);
        if (superClassDesc == null) {
            Optional.ofNullable(innerClasses.get(superClassName))
                .ifPresent(ClassDesc::registerSuperClassOfAnonymousInnerClass);
        }
        else {
            superClassDesc.registerSuperClassOfAnonymousInnerClass();
        }
    }

    /**
     * Checks if there is a class with same name.
     *
     * <p>The parameter {@code shouldSearchAnonSuperClass} exists because if the class
     * being searched for is the super class of anonymous inner class, the rules of evaluation are
     * a bit different, consider the following example-
     * <pre>
     * {@code
     * public class Main {
     *     static class a {
     *         static class b {
     *         }
     *     }
     *
     *     class j {
     *         a.b object = new a.b() { // Object of Main.j.a.b and not of Main.a.b
     *         };
     *
     *         static class a {
     *             static class b {
     *             }
     *         }
     *     }
     * }
     * }
     * </pre>
     * If the parameter wasn't set to {@code true} then the logic would have falsely evaluated
     * {@code Main.a.b} to be the super class of the anonymous inner class. Set this to
     * {@code false} only if the class being searched for is not the super class of anonymous
     * inner class.
     *
     * @param className name of the class
     * @param superOrOuterClassName name of the super class or the outer class
     * @param shouldSearchAnonSuperClass should search for the super class of anonymous inner class
     * @return true if there is another class with same name.
     * @noinspection CallToStringConcatCanBeReplacedByOperator
     */
    private ClassDesc getNearestClassWithSameName(String className, String superOrOuterClassName,
                                                  boolean shouldSearchAnonSuperClass) {
        final String dotAndClassName = PACKAGE_SEPARATOR.concat(className);
        return innerClasses.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith(dotAndClassName))
                .map(Map.Entry::getValue)
                .min((first, second) -> {
                    return getClassDeclarationNameMatchingCountDiff(superOrOuterClassName, first,
                         second, shouldSearchAnonSuperClass);
                })
                .orElse(null);
    }

    /**
     * Get the difference between class declaration name matching count. If the difference between
     * them is zero, then their depth is compared to obtain the result.
     *
     * @param superOrOuterClassName name of the super class or the outer class
     * @param firstClass first input class
     * @param secondClass second input class
     * @param shouldSearchAnonSuperClass should search for the super class of anonymous inner class
     * @return difference between class declaration name matching count
     */
    private static int getClassDeclarationNameMatchingCountDiff(String superOrOuterClassName,
        ClassDesc firstClass, ClassDesc secondClass, boolean shouldSearchAnonSuperClass) {
        int diff;
        if (shouldSearchAnonSuperClass) {
            diff = Integer.compare(
                CheckUtil
                    .typeDeclarationMatchingCountAnonSuperClass(superOrOuterClassName,
                                                                secondClass.getQualifiedName()),
                CheckUtil
                    .typeDeclarationMatchingCountAnonSuperClass(superOrOuterClassName,
                                                                firstClass.getQualifiedName()));
        }
        else {
            diff = Integer.compare(
                CheckUtil.typeDeclarationNameMatchingCount(superOrOuterClassName,
                                                           secondClass.getQualifiedName()),
                CheckUtil.typeDeclarationNameMatchingCount(superOrOuterClassName,
                                                           firstClass.getQualifiedName()));
        }
        if (diff == 0) {
            diff = Integer.compare(firstClass.getDepth(), secondClass.getDepth());
        }
        return diff;
    }

    /**
     * Get qualified type declaration name from given type declaration Ast.
     *
     * @param typeDeclarationAst type declaration for which qualified name is being fetched
     * @return qualified name of a type declaration
     */
    private String getQualifiedTypeDeclarationName(DetailAST typeDeclarationAst) {
        final String className = typeDeclarationAst.findFirstToken(TokenTypes.IDENT).getText();
        String outerTypeDeclarationQualifiedName = null;
        if (!typeDeclarations.isEmpty()) {
            outerTypeDeclarationQualifiedName = typeDeclarations.peek().getQualifiedName();
        }
        return CheckUtil.getQualifiedTypeDeclarationName(packageName,
                                                         outerTypeDeclarationQualifiedName,
                                                         className);
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
            superClassName = CheckUtil.extractQualifiedName(classExtend.getFirstChild());
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

    /**
     * Maintains information about class's ctors. {@link TypeDeclarationDescription} does not
     * maintain information about classes in FinalClassCheck, a subclass called {@link ClassDesc}
     * does that job.
     */
    private static final class ClassDesc extends TypeDeclarationDescription {

        /** Is class declared as final. */
        private final boolean declaredAsFinal;

        /** Is class declared as abstract. */
        private final boolean declaredAsAbstract;

        /** Does class have non-private ctors. */
        private boolean withNonPrivateCtor;

        /** Does class have private ctors. */
        private boolean withPrivateCtor;

        /** Does class have nested subclass. */
        private boolean withNestedSubclass;

        /** Is the class super class of an anonymous inner class. */
        private boolean superClassOfAnonymousInnerClass;

        /**
         *  Create a new ClassDesc instance.
         *
         *  @param qualifiedName qualified class name(with package)
         *  @param depth class nesting level
         *  @param classAst classAst node
         */
        /* package */ ClassDesc(String qualifiedName, int depth, DetailAST classAst) {
            super(qualifiedName, depth, classAst);
            final DetailAST modifiers = classAst.findFirstToken(TokenTypes.MODIFIERS);
            declaredAsFinal = modifiers.findFirstToken(TokenTypes.FINAL) != null;
            declaredAsAbstract = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null;
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
        private void registerSuperClassOfAnonymousInnerClass() {
            superClassOfAnonymousInnerClass = true;
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
         * Is the class super class of an anonymous inner class.
         *
         * @return {@code true} if the class is the super class of an anonymous inner class.
         */
        private boolean isSuperClassOfAnonymousInnerClass() {
            return superClassOfAnonymousInnerClass;
        }

    }
}
