////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>
 * Checks that class which has only private ctors
 * is declared as final. Doesn't check for classes nested in interfaces
 * or annotations, as they are always <code>final</code> there.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="FinalClass"/&gt;
 * </pre>
 * @author o_sukhodolsky
 */
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

    /** Keeps ClassDesc objects for stack of declared classes. */
    private Deque<ClassDesc> classes = new ArrayDeque<>();

    /** Full qualified name of the package. */
    private String packageName;

    /** Represents current depth level that appropriates level of nesting level. */
    private int currentDepthLevel;

    /** Set of class names that contains in extend clauses. */
    private Set<String> superClasses = new HashSet<>();

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF, TokenTypes.PACKAGE_DEF};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        classes.clear();
        superClasses.clear();
        packageName = "";
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);

        switch (ast.getType()) {

            case TokenTypes.PACKAGE_DEF:
                packageName = extractQualifiedName(ast);
                break;

            case TokenTypes.CLASS_DEF:
                currentDepthLevel++;
                final ClassDesc classDesc = registerClassDesc(ast, modifiers);
                classes.push(classDesc);
                break;

            case TokenTypes.CTOR_DEF:
                registerClassConstructors(ast, modifiers);
                break;

            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            currentDepthLevel--;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        determineParentClasses();
        for (ClassDesc desc : classes) {
            if (shouldBeDeclaredAsFinal(desc)) {
                final String qualifiedName = desc.getQualifiedName();
                final String className = getClassNameFromQualifiedName(qualifiedName);
                log(desc.astNode.getLineNo(), MSG_KEY, className);
            }
        }
    }

    /**
     * Get name of class(with qualified package if specified) in extend clause.
     * @param classExtend extend clause to extract class name
     * @return super class name
     */
    private static String extractQualifiedName(DetailAST classExtend) {
        final String className;

        if (classExtend.findFirstToken(TokenTypes.IDENT) == null) {
            // Name specified with packages, have to traverse DOT
            final DetailAST firstChild = classExtend.findFirstToken(TokenTypes.DOT);
            final List<String> qualifiedNameParts = new LinkedList<>();

            qualifiedNameParts.add(0, firstChild.findFirstToken(TokenTypes.IDENT).getText());
            DetailAST traverse = firstChild.findFirstToken(TokenTypes.DOT);
            while (traverse != null) {
                qualifiedNameParts.add(0, traverse.findFirstToken(TokenTypes.IDENT).getText());
                traverse = traverse.findFirstToken(TokenTypes.DOT);
            }
            className = String.join(PACKAGE_SEPARATOR, qualifiedNameParts);
        }
        else {
            className = classExtend.findFirstToken(TokenTypes.IDENT).getText();
        }

        return className;
    }

    /**
     * Get qualified class name from given class Ast.
     * @param classAst class to get qualified class name
     * @return qualified class name of a class
     */
    private String getQualifiedClassName(DetailAST classAst) {
        final String className = classAst.findFirstToken(TokenTypes.IDENT).getText();
        String outerClassQualifiedName = null;
        for (ClassDesc desc : classes) {
            if (desc.depthLevel == currentDepthLevel - 1) {
                outerClassQualifiedName = desc.getQualifiedName();
                break;
            }
        }
        return getQualifiedClassName(packageName, outerClassQualifiedName, className);
    }

    /**
     * Calculate qualified class name(package + class name) laying inside given
     * outer class.
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
     * @param classAst class
     * @return super class name or null if super class is not specified
     */
    private static String getSuperClassName(DetailAST classAst) {
        String superClassName = null;
        final DetailAST classExtend = classAst.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
        if (classExtend != null) {
            superClassName = extractQualifiedName(classExtend);
        }
        return superClassName;
    }

    /**
     * Checks if given super class name in extend clause match super class qualified name.
     * @param superClassQualifiedName super class qualified name (with package)
     * @param superClassInExtendClause name in extend clause
     * @return true if given super class name in extend clause match super class qualified name,
     *         false otherwise
     */
    private static boolean doesNameInExtendMatchSuperClassName(String superClassQualifiedName,
                                                               String superClassInExtendClause) {
        String superClassNormalizedName = superClassQualifiedName;
        if (!superClassInExtendClause.contains(PACKAGE_SEPARATOR)) {
            superClassNormalizedName = getClassNameFromQualifiedName(superClassQualifiedName);
        }
        return superClassNormalizedName.equals(superClassInExtendClause);
    }

    /**
     * Get class name from qualified name.
     * @param qualifiedName qualified class name
     * @return class name
     */
    private static String getClassNameFromQualifiedName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(PACKAGE_SEPARATOR) + 1);
    }

    /**
     * Registers class description from ast node.
     * @param ast AST node that link to class declaration token
     * @param modifiers node that link  to first token of modifiers
     * @return Class description
     */
    private ClassDesc registerClassDesc(DetailAST ast, DetailAST modifiers) {
        final String superClass = getSuperClassName(ast);
        if (superClass != null) {
            superClasses.add(superClass);
        }
        final String qualifiedClassName = getQualifiedClassName(ast);
        final boolean isFinal = modifiers.branchContains(TokenTypes.FINAL);
        final boolean isAbstract = modifiers.branchContains(TokenTypes.ABSTRACT);
        final ClassDesc classDesc = new ClassDesc(qualifiedClassName, isFinal, isAbstract);
        classDesc.astNode = ast;
        classDesc.depthLevel = currentDepthLevel;
        return classDesc;
    }

    /**
     * Register class constructors.
     * @param ast AST node that link to class declaration token
     * @param modifiers node that link  to first token of modifiers
     */
    private void registerClassConstructors(DetailAST ast, DetailAST modifiers) {
        if (!ScopeUtils.isInEnumBlock(ast)) {
            final ClassDesc desc = classes.peek();
            if (modifiers.branchContains(TokenTypes.LITERAL_PRIVATE)) {
                desc.registerPrivateCtor();
            }
            else {
                desc.registerNonPrivateCtor();
            }
        }
    }

    /**
     * Check that class should be declared as final or not.
     * @param desc class description
     * @return true if class should be declared as final
     */
    private boolean shouldBeDeclaredAsFinal(ClassDesc desc) {
        final boolean hasOnlyPrivateCtors = desc.isWithPrivateCtor()
            && !desc.isWithNonPrivateCtor();
        final boolean canBeFinal = !desc.isDeclaredAsAbstract()
            && !desc.isDeclaredAsParent();
        return hasOnlyPrivateCtors
            && canBeFinal
            && !desc.isDeclaredAsFinal()
            && !ScopeUtils.isInInterfaceOrAnnotationBlock(desc.astNode);
    }

    /** Determine parent classes from superclasses names set. */
    private void determineParentClasses() {
        for (String className : superClasses) {
            for (ClassDesc desc : classes) {
                if (desc.qualifiedName.endsWith(PACKAGE_SEPARATOR + className)
                    || desc.qualifiedName.equals(className)) {
                    desc.registerAsParentClass();
                }
            }
        }
    }

    /** Maintains information about class' ctors. */
    private static final class ClassDesc {
        /** Qualified class name(with package). */
        private final String qualifiedName;

        /** Is class declared as final. */
        private final boolean declaredAsFinal;

        /** Is class declared as abstract. */
        private final boolean declaredAsAbstract;

        /** Does class have non-private ctors. */
        private boolean withNonPrivateCtor;

        /** Does class have private ctors. */
        private boolean withPrivateCtor;

        /** Does class have subclasses. */
        private boolean declaredAsParent;

        /**
         * Level of nesting of current class.
         */
        private int depthLevel;

        /** Node of AST that store the class declaration token. */
        private DetailAST astNode;

        /**
         *  Create a new ClassDesc instance.
         *  @param qualifiedName qualified class name(with package)
         *  @param declaredAsFinal indicates if the
         *         class declared as final
         *  @param declaredAsAbstract indicates if the
         *         class declared as abstract
         */
        ClassDesc(String qualifiedName, boolean declaredAsFinal, boolean declaredAsAbstract) {
            this.qualifiedName = qualifiedName;
            this.declaredAsFinal = declaredAsFinal;
            this.declaredAsAbstract = declaredAsAbstract;
        }

        /**
         * Get qualified class name.
         * @return qualified class name
         */
        private String getQualifiedName() {
            return qualifiedName;
        }

        /** Adds private ctor. */
        private void registerPrivateCtor() {
            withPrivateCtor = true;
        }

        /** Adds non-private ctor. */
        private void registerNonPrivateCtor() {
            withNonPrivateCtor = true;
        }

        /**
         *  Does class have private ctors.
         *  @return true if class has private ctors
         */
        private boolean isWithPrivateCtor() {
            return withPrivateCtor;
        }

        /**
         *  Does class have non-private ctors.
         *  @return true if class has non-private ctors
         */
        private boolean isWithNonPrivateCtor() {
            return withNonPrivateCtor;
        }

        /**
         *  Is class declared as final.
         *  @return true if class is declared as final
         */
        private boolean isDeclaredAsFinal() {
            return declaredAsFinal;
        }

        /**
         *  Is class declared as abstract.
         *  @return true if class is declared as final
         */
        private boolean isDeclaredAsAbstract() {
            return declaredAsAbstract;
        }

        /** Has subclasses. */
        private void registerAsParentClass() {
            declaredAsParent = true;
        }

        /**
         * Does it have subclasses.
         * @return true if class has subclasses
         */
        private boolean isDeclaredAsParent() {
            return declaredAsParent;
        }
    }
}
