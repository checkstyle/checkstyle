////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Checks for redundant modifiers in interface and annotation definitions,
 * final modifier on methods of final classes, inner <code>interface</code>
 * declarations that are declared as <code>static</code>, non public class
 * constructors and enum constructors, nested enum definitions that are declared
 * as <code>static</code>.
 *
 * <p>Interfaces by definition are abstract so the <code>abstract</code>
 * modifier on the interface is redundant.
 *
 * <p>Classes inside of interfaces by definition are public and static,
 * so the <code>public</code> and <code>static</code> modifiers
 * on the inner classes are redundant. On the other hand, classes
 * inside of interfaces can be abstract or non abstract.
 * So, <code>abstract</code> modifier is allowed.
 *
 * <p>Fields in interfaces and annotations are automatically
 * public, static and final, so these modifiers are redundant as
 * well.</p>
 *
 * <p>As annotations are a form of interface, their fields are also
 * automatically public, static and final just as their
 * annotation fields are automatically public and abstract.</p>
 *
 * <p>Enums by definition are static implicit subclasses of java.lang.Enum&#60;E&#62;.
 * So, the <code>static</code> modifier on the enums is redundant. In addition,
 * if enum is inside of interface, <code>public</code> modifier is also redundant.
 *
 * <p>Final classes by definition cannot be extended so the <code>final</code>
 * modifier on the method of a final class is redundant.
 *
 * <p>Public modifier for constructors in non-public non-protected classes
 * is always obsolete: </p>
 *
 * <pre>
 * public class PublicClass {
 *     public PublicClass() {} // OK
 * }
 *
 * class PackagePrivateClass {
 *     public PackagePrivateClass() {} // violation expected
 * }
 * </pre>
 *
 * <p>There is no violation in the following example,
 * because removing public modifier from ProtectedInnerClass
 * constructor will make this code not compiling: </p>
 *
 * <pre>
 * package a;
 * public class ClassExample {
 *     protected class ProtectedInnerClass {
 *         public ProtectedInnerClass () {}
 *     }
 * }
 *
 * package b;
 * import a.ClassExample;
 * public class ClassExtending extends ClassExample {
 *     ProtectedInnerClass pc = new ProtectedInnerClass();
 * }
 * </pre>
 *
 * @author lkuehne
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 * @author <a href="mailto:andreyselkin@gmail.com">Andrei Selkin</a>
 * @author Vladislav Lisetskiy
 */
public class RedundantModifierCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "redundantModifier";

    /**
     * An array of tokens for interface modifiers.
     */
    private static final int[] TOKENS_FOR_INTERFACE_MODIFIERS = {
        TokenTypes.LITERAL_STATIC,
        TokenTypes.ABSTRACT,
    };

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.INTERFACE_DEF) {
            checkInterfaceModifiers(ast);
        }
        else if (ast.getType() == TokenTypes.CTOR_DEF) {
            if (isEnumMember(ast)) {
                checkEnumConstructorModifiers(ast);
            }
            else {
                checkClassConstructorModifiers(ast);
            }
        }
        else if (ast.getType() == TokenTypes.ENUM_DEF) {
            checkEnumDef(ast);
        }
        else if (isInterfaceOrAnnotationMember(ast)) {
            processInterfaceOrAnnotation(ast);
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            processMethods(ast);
        }
    }

    /**
     * Checks if interface has proper modifiers.
     * @param ast interface to check
     */
    private void checkInterfaceModifiers(DetailAST ast) {
        final DetailAST modifiers =
            ast.findFirstToken(TokenTypes.MODIFIERS);

        for (final int tokenType : TOKENS_FOR_INTERFACE_MODIFIERS) {
            final DetailAST modifier =
                    modifiers.findFirstToken(tokenType);
            if (modifier != null) {
                log(modifier.getLineNo(), modifier.getColumnNo(),
                        MSG_KEY, modifier.getText());
            }
        }
    }

    /**
     * Check if enum constructor has proper modifiers.
     * @param ast constructor of enum
     */
    private void checkEnumConstructorModifiers(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        final DetailAST modifier = modifiers.getFirstChild();
        if (modifier != null) {
            log(modifier.getLineNo(), modifier.getColumnNo(),
                    MSG_KEY, modifier.getText());
        }
    }

    /**
     * Checks whether enum has proper modifiers.
     * @param ast enum definition.
     */
    private void checkEnumDef(DetailAST ast) {
        if (isInterfaceOrAnnotationMember(ast)) {
            processInterfaceOrAnnotation(ast);
        }
        else if (ast.getParent() != null) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            final DetailAST staticModifier = modifiers.findFirstToken(TokenTypes.LITERAL_STATIC);
            if (staticModifier != null) {
                log(staticModifier.getLineNo(), staticModifier.getColumnNo(),
                        MSG_KEY, staticModifier.getText());
            }
        }
    }

    /**
     * Do validation of interface of annotation.
     * @param ast token AST
     */
    private void processInterfaceOrAnnotation(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST modifier = modifiers.getFirstChild();
        while (modifier != null) {

            // javac does not allow final or static in interface methods
            // order annotation fields hence no need to check that this
            // is not a method or annotation field

            final int type = modifier.getType();
            if (type == TokenTypes.LITERAL_PUBLIC
                || type == TokenTypes.LITERAL_STATIC
                        && ast.getType() != TokenTypes.METHOD_DEF
                || type == TokenTypes.ABSTRACT
                        && ast.getType() != TokenTypes.CLASS_DEF
                || type == TokenTypes.FINAL
                        && ast.getType() != TokenTypes.CLASS_DEF) {
                log(modifier.getLineNo(), modifier.getColumnNo(),
                        MSG_KEY, modifier.getText());
                break;
            }

            modifier = modifier.getNextSibling();
        }
    }

    /**
     * Process validation ofMethods.
     * @param ast method AST
     */
    private void processMethods(DetailAST ast) {
        final DetailAST modifiers =
                        ast.findFirstToken(TokenTypes.MODIFIERS);
        // private method?
        boolean checkFinal =
            modifiers.branchContains(TokenTypes.LITERAL_PRIVATE);
        // declared in a final class?
        DetailAST parent = ast.getParent();
        while (parent != null) {
            if (parent.getType() == TokenTypes.CLASS_DEF) {
                final DetailAST classModifiers =
                    parent.findFirstToken(TokenTypes.MODIFIERS);
                checkFinal = checkFinal || classModifiers.branchContains(TokenTypes.FINAL);
                parent = null;
            }
            else if (parent.getType() == TokenTypes.LITERAL_NEW) {
                checkFinal = true;
                parent = null;
            }
            else {
                parent = parent.getParent();
            }
        }
        if (checkFinal && !isAnnotatedWithSafeVarargs(ast)) {
            DetailAST modifier = modifiers.getFirstChild();
            while (modifier != null) {
                final int type = modifier.getType();
                if (type == TokenTypes.FINAL) {
                    log(modifier.getLineNo(), modifier.getColumnNo(),
                            MSG_KEY, modifier.getText());
                    break;
                }
                modifier = modifier.getNextSibling();
            }
        }
    }

    /**
     * Check if class constructor has proper modifiers.
     * @param classCtorAst class constructor ast
     */
    private void checkClassConstructorModifiers(DetailAST classCtorAst) {
        final DetailAST classDef = classCtorAst.getParent().getParent();
        if (!isClassPublic(classDef) && !isClassProtected(classDef)) {
            checkForRedundantPublicModifier(classCtorAst);
        }
    }

    /**
     * Checks if given ast has redundant public modifier.
     * @param ast ast
     */
    private void checkForRedundantPublicModifier(DetailAST ast) {
        final DetailAST astModifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST astModifier = astModifiers.getFirstChild();
        while (astModifier != null) {
            if (astModifier.getType() == TokenTypes.LITERAL_PUBLIC) {
                log(astModifier.getLineNo(), astModifier.getColumnNo(),
                        MSG_KEY, astModifier.getText());
            }

            astModifier = astModifier.getNextSibling();
        }
    }

    /**
     * Checks if given class ast has protected modifier.
     * @param classDef class ast
     * @return true if class is protected, false otherwise
     */
    private static boolean isClassProtected(DetailAST classDef) {
        final DetailAST classModifiers =
                classDef.findFirstToken(TokenTypes.MODIFIERS);
        return classModifiers.branchContains(TokenTypes.LITERAL_PROTECTED);
    }

    /**
     * Checks if given class is accessible from "public" scope.
     * @param ast class def to check
     * @return true if class is accessible from public scope,false otherwise
     */
    private static boolean isClassPublic(DetailAST ast) {
        boolean isAccessibleFromPublic = false;
        final boolean isMostOuterScope = ast.getParent() == null;
        final DetailAST modifiersAst = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean hasPublicModifier = modifiersAst.branchContains(TokenTypes.LITERAL_PUBLIC);

        if (isMostOuterScope) {
            isAccessibleFromPublic = hasPublicModifier;
        }
        else {
            final DetailAST parentClassAst = ast.getParent().getParent();

            if (hasPublicModifier || parentClassAst.getType() == TokenTypes.INTERFACE_DEF) {
                isAccessibleFromPublic = isClassPublic(parentClassAst);
            }
        }

        return isAccessibleFromPublic;
    }

    /**
     * Checks if current AST node is member of Enum.
     * @param ast AST node
     * @return true if it is an enum member
     */
    private static boolean isEnumMember(DetailAST ast) {
        final DetailAST parentTypeDef = ast.getParent().getParent();
        return parentTypeDef.getType() == TokenTypes.ENUM_DEF;
    }

    /**
     * Checks if current AST node is member of Interface or Annotation, not of their subnodes.
     * @param ast AST node
     * @return true or false
     */
    private static boolean isInterfaceOrAnnotationMember(DetailAST ast) {
        DetailAST parentTypeDef = ast.getParent();

        if (parentTypeDef != null) {
            parentTypeDef = parentTypeDef.getParent();
        }
        return parentTypeDef != null
                && (parentTypeDef.getType() == TokenTypes.INTERFACE_DEF
                    || parentTypeDef.getType() == TokenTypes.ANNOTATION_DEF);
    }

    /**
     * Checks if method definition is annotated with
     * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/SafeVarargs.html">
     * SafeVarargs</a> annotation
     * @param methodDef method definition node
     * @return true or false
     */
    private static boolean isAnnotatedWithSafeVarargs(DetailAST methodDef) {
        boolean result = false;
        final List<DetailAST> methodAnnotationsList = getMethodAnnotationsList(methodDef);
        for (DetailAST annotationNode : methodAnnotationsList) {
            if ("SafeVarargs".equals(annotationNode.getLastChild().getText())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of annotations on method definition.
     * @param methodDef method definition node
     * @return List of annotations
     */
    private static List<DetailAST> getMethodAnnotationsList(DetailAST methodDef) {
        final List<DetailAST> annotationsList = new ArrayList<>();
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST modifier = modifiers.getFirstChild();
        while (modifier != null) {
            if (modifier.getType() == TokenTypes.ANNOTATION) {
                annotationsList.add(modifier);
            }
            modifier = modifier.getNextSibling();
        }
        return annotationsList;
    }
}
