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

package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Ensures that identifies classes that can be effectively declared as final are explicitly
 * marked as final. The following are different types of classes that can be identified:
 * </div>
 * <ol>
 *   <li>
 *       Private classes with no declared constructors.
 *   </li>
 *   <li>
 *       Classes with any modifier, and contains only private constructors.
 *   </li>
 * </ol>
 *
 * <p>
 * Classes are skipped if:
 * </p>
 * <ol>
 *   <li>
 *       Class is Super class of some Anonymous inner class.
 *   </li>
 *   <li>
 *       Class is extended by another class in the same file.
 *   </li>
 * </ol>
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
        innerClasses = new LinkedHashMap<>();
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
                    extractQualifiedTypeName(ast), 0, ast);
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
                    anonInnerClassToOuterTypeDecl
                        .put(ast, typeDeclarations.peek().getQualifiedName());
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
        final String qualifiedClassName = extractQualifiedTypeName(ast);
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
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null) {
                // Can be only of type ClassDesc, preceding if statements guarantee it.
                final ClassDesc desc = (ClassDesc) typeDeclarations.getFirst();
                desc.registerNonPrivateCtor();
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
            innerClasses.forEach(this::registerExtendedClass);
            // Second pass: report violation for all classes that should be declared as final
            innerClasses.forEach((qualifiedClassName, classDesc) -> {
                if (shouldBeDeclaredAsFinal(classDesc)) {
                    final String className = CommonUtil.baseClassName(qualifiedClassName);
                    log(classDesc.getTypeDeclarationAst(), MSG_KEY, className);
                }
            });
        }
    }

    /**
     * Checks whether a class should be declared as final or not.
     *
     * @param classDesc description of the class
     * @return true if given class should be declared as final otherwise false
     */
    private static boolean shouldBeDeclaredAsFinal(ClassDesc classDesc) {
        final boolean shouldBeFinal;

        final boolean skipClass = classDesc.isDeclaredAsFinal()
                    || classDesc.isDeclaredAsAbstract()
                    || classDesc.isSuperClassOfAnonymousInnerClass()
                    || classDesc.isWithNestedSubclass();

        if (skipClass) {
            shouldBeFinal = false;
        }
        else if (classDesc.isHasDeclaredConstructor()) {
            shouldBeFinal = classDesc.isDeclaredAsPrivate();
        }
        else {
            shouldBeFinal = !classDesc.isWithNonPrivateCtor();
        }
        return shouldBeFinal;
    }

    /**
     * Register to outer super class of given classAst that
     * given classAst is extending them.
     *
     * @param qualifiedClassName qualifies class name(with package) of the current class
     * @param currentClass class which outer super class will be informed about nesting subclass
     */
    private void registerExtendedClass(String qualifiedClassName,
                                       ClassDesc currentClass) {
        final String superClassName = getSuperClassName(currentClass.getTypeDeclarationAst());
        if (superClassName != null) {
            final ToIntFunction<ClassDesc> nestedClassCountProvider = classDesc -> {
                return CheckUtil.typeDeclarationNameMatchingCount(qualifiedClassName,
                                                                  classDesc.getQualifiedName());
            };
            getNearestClassWithSameName(superClassName, nestedClassCountProvider)
                .or(() -> Optional.ofNullable(innerClasses.get(superClassName)))
                .ifPresent(ClassDesc::registerNestedSubclass);
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

        final ToIntFunction<ClassDesc> anonClassCountProvider = classDesc -> {
            return getAnonSuperTypeMatchingCount(outerTypeDeclName, classDesc.getQualifiedName());
        };
        getNearestClassWithSameName(superClassName, anonClassCountProvider)
            .or(() -> Optional.ofNullable(innerClasses.get(superClassName)))
            .ifPresent(ClassDesc::registerSuperClassOfAnonymousInnerClass);
    }

    /**
     * Get the nearest class with same name.
     *
     * <p>The parameter {@code countProvider} exists because if the class being searched is the
     * super class of anonymous inner class, the rules of evaluation are a bit different,
     * consider the following example-
     * <pre>
     * {@code
     * public class Main {
     *     static class One {
     *         static class Two {
     *         }
     *     }
     *
     *     class Three {
     *         One.Two object = new One.Two() { // Object of Main.Three.One.Two
     *                                          // and not of Main.One.Two
     *         };
     *
     *         static class One {
     *             static class Two {
     *             }
     *         }
     *     }
     * }
     * }
     * </pre>
     * If the {@link Function} {@code countProvider} hadn't used
     * {@link FinalClassCheck#getAnonSuperTypeMatchingCount} to
     * calculate the matching count then the logic would have falsely evaluated
     * {@code Main.One.Two} to be the super class of the anonymous inner class.
     *
     * @param className name of the class
     * @param countProvider the function to apply to calculate the name matching count
     * @return {@link Optional} of {@link ClassDesc} object of the nearest class with the same name.
     * @noinspection CallToStringConcatCanBeReplacedByOperator
     * @noinspectionreason CallToStringConcatCanBeReplacedByOperator - operator causes
     *      pitest to fail
     */
    private Optional<ClassDesc> getNearestClassWithSameName(String className,
        ToIntFunction<ClassDesc> countProvider) {
        final String dotAndClassName = PACKAGE_SEPARATOR.concat(className);
        final Comparator<ClassDesc> longestMatch = Comparator.comparingInt(countProvider);
        return innerClasses.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith(dotAndClassName))
                .map(Map.Entry::getValue)
                .min(longestMatch.reversed().thenComparingInt(ClassDesc::getDepth));
    }

    /**
     * Extract the qualified type declaration name from given type declaration Ast.
     *
     * @param typeDeclarationAst type declaration for which qualified name is being fetched
     * @return qualified name of a type declaration
     */
    private String extractQualifiedTypeName(DetailAST typeDeclarationAst) {
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
     * Calculates and returns the type declaration matching count when {@code classToBeMatched} is
     * considered to be super class of an anonymous inner class.
     *
     * <p>
     * Suppose our pattern class is {@code Main.ClassOne} and class to be matched is
     * {@code Main.ClassOne.ClassTwo.ClassThree} then type declaration name matching count would
     * be calculated by comparing every character, and updating main counter when we hit "." or
     * when it is the last character of the pattern class and certain conditions are met. This is
     * done so that matching count is 13 instead of 5. This is due to the fact that pattern class
     * can contain anonymous inner class object of a nested class which isn't true in case of
     * extending classes as you can't extend nested classes.
     * </p>
     *
     * @param patternTypeDeclaration type declaration against which the given type declaration has
     *                               to be matched
     * @param typeDeclarationToBeMatched type declaration to be matched
     * @return type declaration matching count
     */
    private static int getAnonSuperTypeMatchingCount(String patternTypeDeclaration,
                                                    String typeDeclarationToBeMatched) {
        final int typeDeclarationToBeMatchedLength = typeDeclarationToBeMatched.length();
        final int minLength = Math
            .min(typeDeclarationToBeMatchedLength, patternTypeDeclaration.length());
        final char packageSeparator = PACKAGE_SEPARATOR.charAt(0);
        final boolean shouldCountBeUpdatedAtLastCharacter =
            typeDeclarationToBeMatchedLength > minLength
                && typeDeclarationToBeMatched.charAt(minLength) == packageSeparator;

        int result = 0;
        for (int idx = 0;
             idx < minLength
                 && patternTypeDeclaration.charAt(idx) == typeDeclarationToBeMatched.charAt(idx);
             idx++) {

            if (idx == minLength - 1 && shouldCountBeUpdatedAtLastCharacter
                || patternTypeDeclaration.charAt(idx) == packageSeparator) {
                result = idx;
            }
        }
        return result;
    }

    /**
     * Maintains information about the type of declaration.
     * Any ast node of type {@link TokenTypes#CLASS_DEF} or {@link TokenTypes#INTERFACE_DEF}
     * or {@link TokenTypes#ENUM_DEF} or {@link TokenTypes#ANNOTATION_DEF}
     * or {@link TokenTypes#RECORD_DEF} is considered as a type declaration.
     * It does not maintain information about classes, a subclass called {@link ClassDesc}
     * does that job.
     */
    private static class TypeDeclarationDescription {

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
        private final DetailAST typeDeclarationAst;

        /**
         * Create an instance of TypeDeclarationDescription.
         *
         * @param qualifiedName Complete type declaration name with package name and outer type
         *                      declaration name.
         * @param depth Depth of nesting of type declaration
         * @param typeDeclarationAst Type declaration ast node
         */
        private TypeDeclarationDescription(String qualifiedName, int depth,
                                          DetailAST typeDeclarationAst) {
            this.qualifiedName = qualifiedName;
            this.depth = depth;
            this.typeDeclarationAst = typeDeclarationAst;
        }

        /**
         * Get the complete type declaration name i.e. type declaration name with package name
         * and outer type declaration name.
         *
         * @return qualified class name
         */
        protected String getQualifiedName() {
            return qualifiedName;
        }

        /**
         * Get the depth of type declaration.
         *
         * @return the depth of nesting of type declaration
         */
        protected int getDepth() {
            return depth;
        }

        /**
         * Get the type declaration ast node.
         *
         * @return ast node of the type declaration
         */
        protected DetailAST getTypeDeclarationAst() {
            return typeDeclarationAst;
        }
    }

    /**
     * Maintains information about the class.
     */
    private static final class ClassDesc extends TypeDeclarationDescription {

        /** Is class declared as final. */
        private final boolean declaredAsFinal;

        /** Is class declared as abstract. */
        private final boolean declaredAsAbstract;

        /** Is class contains private modifier. */
        private final boolean declaredAsPrivate;

        /** Does class have implicit constructor. */
        private final boolean hasDeclaredConstructor;

        /** Does class have non-private ctors. */
        private boolean withNonPrivateCtor;

        /** Does class have nested subclass. */
        private boolean withNestedSubclass;

        /** Whether the class is the super class of an anonymous inner class. */
        private boolean superClassOfAnonymousInnerClass;

        /**
         *  Create a new ClassDesc instance.
         *
         *  @param qualifiedName qualified class name(with package)
         *  @param depth class nesting level
         *  @param classAst classAst node
         */
        private ClassDesc(String qualifiedName, int depth, DetailAST classAst) {
            super(qualifiedName, depth, classAst);
            final DetailAST modifiers = classAst.findFirstToken(TokenTypes.MODIFIERS);
            declaredAsFinal = modifiers.findFirstToken(TokenTypes.FINAL) != null;
            declaredAsAbstract = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null;
            declaredAsPrivate = modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
            hasDeclaredConstructor =
                    classAst.getLastChild().findFirstToken(TokenTypes.CTOR_DEF) == null;
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
         * Whether the class is the super class of an anonymous inner class.
         *
         * @return {@code true} if the class is the super class of an anonymous inner class.
         */
        private boolean isSuperClassOfAnonymousInnerClass() {
            return superClassOfAnonymousInnerClass;
        }

        /**
         * Does class have implicit constructor.
         *
         * @return true if class have implicit constructor
         */
        private boolean isHasDeclaredConstructor() {
            return hasDeclaredConstructor;
        }

        /**
         * Does class is private.
         *
         * @return true if class is private
         */
        private boolean isDeclaredAsPrivate() {
            return declaredAsPrivate;
        }
    }
}
