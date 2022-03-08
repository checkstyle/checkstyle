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
import com.puppycrawl.tools.checkstyle.utils.TypeDeclDesc;

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

    /** Keeps TypeDeclDesc objects for all inner classes. */
    private Map<String, TypeDeclDesc> innerClasses;

    /** Keeps TypeDeclDesc objects for stack of declared classes. */
    private Deque<TypeDeclDesc> classes;

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
                packageName = CheckUtil.extractQualifiedName(ast.getFirstChild().getNextSibling());
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
                    for (TypeDeclDesc typeDeclDesc : classes) {
                        if (doesNameOfClassMatchAnonymousInnerClassName(ast, typeDeclDesc)) {
                            typeDeclDesc.registerAnonymousInnerClass();
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
        final String qualifiedClassName = CheckUtil.getQualifiedTypeDeclarationName(
            ast, classes, packageName);
        final TypeDeclDesc currClass = new TypeDeclDesc(qualifiedClassName, classes.size(), ast);
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
            final TypeDeclDesc desc = classes.getFirst();
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
            innerClasses.forEach((qualifiedClassName, typeDeclDesc) -> {
                if (shouldBeDeclaredAsFinal(typeDeclDesc)) {
                    final String className = getClassNameFromQualifiedName(qualifiedClassName);
                    log(typeDeclDesc.getTypeDeclAst(), MSG_KEY, className);
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
    private static boolean shouldBeDeclaredAsFinal(TypeDeclDesc desc) {
        return desc.isWithPrivateCtor()
                && !(desc.isDeclaredAsAbstract()
                    || desc.isWithAnonymousInnerClass())
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
                                                           TypeDeclDesc currentClass) {
        final String superClassName = getSuperClassName(currentClass.getTypeDeclAst());
        if (superClassName != null) {
            final TypeDeclDesc nearest =
                    getNearestClassWithSameName(superClassName, qualifiedClassName);
            if (nearest == null) {
                Optional.ofNullable(innerClasses.get(superClassName))
                        .ifPresent(TypeDeclDesc::registerNestedSubclass);
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
     * @param superClsName name of the super class
     * @return true if there is another class with same name.
     * @noinspection CallToStringConcatCanBeReplacedByOperator
     */
    private TypeDeclDesc getNearestClassWithSameName(String className, String superClsName) {
        final String dotAndClassName = PACKAGE_SEPARATOR.concat(className);
        return innerClasses.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith(dotAndClassName))
                .map(Map.Entry::getValue)
                .min((fir, sec) -> {
                    int diff = Integer.compare(
                        CheckUtil.typeDeclNameMatchingCount(superClsName, sec.getQualifiedName()),
                        CheckUtil.typeDeclNameMatchingCount(superClsName, fir.getQualifiedName()));
                    if (diff == 0) {
                        diff = Integer.compare(fir.getDepth(), sec.getDepth());
                    }
                    return diff;
                })
                .orElse(null);
    }

    /**
     * Check if class name matches with anonymous inner class name.
     *
     * @param ast current ast.
     * @param typeDeclDesc class to match.
     * @return true if current class name matches anonymous inner
     *         class name.
     */
    private static boolean doesNameOfClassMatchAnonymousInnerClassName(DetailAST ast,
                                                               TypeDeclDesc typeDeclDesc) {
        final String[] className = typeDeclDesc.getQualifiedName().split("\\.");
        return ast.getFirstChild().getText().equals(className[className.length - 1]);
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
}
