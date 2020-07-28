////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks for redundant modifiers.
 * </p>
 * <p>
 * Rationale: The Java Language Specification strongly discourages the usage
 * of {@code public} and {@code abstract} for method declarations in interface
 * definitions as a matter of style.
 * </p>
 * <p>The check validates:</p>
 * <ol>
 * <li>
 * Interface and annotation definitions.
 * </li>
 * <li>
 * Final modifier on methods of final and anonymous classes.
 * </li>
 * <li>
 * Inner {@code interface} declarations that are declared as {@code static}.
 * </li>
 * <li>
 * Class constructors.
 * </li>
 * <li>
 * Nested {@code enum} definitions that are declared as {@code static}.
 * </li>
 * </ol>
 * <p>
 * Interfaces by definition are abstract so the {@code abstract}
 * modifier on the interface is redundant.
 * </p>
 * <p>Classes inside of interfaces by definition are public and static,
 * so the {@code public} and {@code static} modifiers
 * on the inner classes are redundant. On the other hand, classes
 * inside of interfaces can be abstract or non abstract.
 * So, {@code abstract} modifier is allowed.
 * </p>
 * <p>Fields in interfaces and annotations are automatically
 * public, static and final, so these modifiers are redundant as
 * well.</p>
 *
 * <p>As annotations are a form of interface, their fields are also
 * automatically public, static and final just as their
 * annotation fields are automatically public and abstract.</p>
 *
 * <p>Enums by definition are static implicit subclasses of java.lang.Enum&#60;E&#62;.
 * So, the {@code static} modifier on the enums is redundant. In addition,
 * if enum is inside of interface, {@code public} modifier is also redundant.</p>
 *
 * <p>Enums can also contain abstract methods and methods which can be overridden by the declared
 * enumeration fields.
 * See the following example:</p>
 * <pre>
 * public enum EnumClass {
 *   FIELD_1,
 *   FIELD_2 {
 *     &#64;Override
 *     public final void method1() {} // violation expected
 *   };
 *
 *   public void method1() {}
 *   public final void method2() {} // no violation expected
 * }
 * </pre>
 *
 * <p>Since these methods can be overridden in these situations, the final methods are not
 * marked as redundant even though they can't be extended by other classes/enums.</p>
 * <p>
 * Nested {@code enum} types are always static by default.
 * </p>
 * <p>Final classes by definition cannot be extended so the {@code final}
 * modifier on the method of a final class is redundant.
 * </p>
 * <p>Public modifier for constructors in non-public non-protected classes
 * is always obsolete: </p>
 *
 * <pre>
 * public class PublicClass {
 *   public PublicClass() {} // OK
 * }
 *
 * class PackagePrivateClass {
 *   public PackagePrivateClass() {} // violation expected
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
 *   protected class ProtectedInnerClass {
 *     public ProtectedInnerClass () {}
 *   }
 * }
 *
 * package b;
 * import a.ClassExample;
 * public class ClassExtending extends ClassExample {
 *   ProtectedInnerClass pc = new ProtectedInnerClass();
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RESOURCE">
 * RESOURCE</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="RedundantModifier"/&gt;
 * </pre>
 * <p>
 * To configure the check to check only methods and not variables:
 * </p>
 * <pre>
 * &lt;module name="RedundantModifier"&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code redundantModifier}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
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
        return CommonUtil.EMPTY_INT_ARRAY;
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
            TokenTypes.RESOURCE,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.INTERFACE_DEF) {
            checkInterfaceModifiers(ast);
        }
        else if (ast.getType() == TokenTypes.ENUM_DEF) {
            checkEnumDef(ast);
        }
        else {
            if (ast.getType() == TokenTypes.CTOR_DEF) {
                if (isEnumMember(ast)) {
                    checkEnumConstructorModifiers(ast);
                }
                else {
                    checkClassConstructorModifiers(ast);
                }
            }
            else if (ast.getType() == TokenTypes.METHOD_DEF) {
                processMethods(ast);
            }
            else if (ast.getType() == TokenTypes.RESOURCE) {
                processResources(ast);
            }

            if (isInterfaceOrAnnotationMember(ast)) {
                processInterfaceOrAnnotation(ast);
            }
        }
    }

    /**
     * Checks if interface has proper modifiers.
     *
     * @param ast interface to check
     */
    private void checkInterfaceModifiers(DetailAST ast) {
        final DetailAST modifiers =
            ast.findFirstToken(TokenTypes.MODIFIERS);

        for (final int tokenType : TOKENS_FOR_INTERFACE_MODIFIERS) {
            final DetailAST modifier =
                    modifiers.findFirstToken(tokenType);
            if (modifier != null) {
                log(modifier, MSG_KEY, modifier.getText());
            }
        }
    }

    /**
     * Check if enum constructor has proper modifiers.
     *
     * @param ast constructor of enum
     */
    private void checkEnumConstructorModifiers(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        TokenUtil.findFirstTokenByPredicate(
            modifiers, mod -> mod.getType() != TokenTypes.ANNOTATION
        ).ifPresent(modifier -> log(modifier, MSG_KEY, modifier.getText()));
    }

    /**
     * Checks whether enum has proper modifiers.
     *
     * @param ast enum definition.
     */
    private void checkEnumDef(DetailAST ast) {
        if (isInterfaceOrAnnotationMember(ast)) {
            processInterfaceOrAnnotation(ast);
        }
        else {
            checkForRedundantModifier(ast, TokenTypes.LITERAL_STATIC);
        }
    }

    /**
     * Do validation of interface of annotation.
     *
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
                log(modifier, MSG_KEY, modifier.getText());
                break;
            }

            modifier = modifier.getNextSibling();
        }
    }

    /**
     * Process validation of Methods.
     *
     * @param ast method AST
     */
    private void processMethods(DetailAST ast) {
        final DetailAST modifiers =
                        ast.findFirstToken(TokenTypes.MODIFIERS);
        // private method?
        boolean checkFinal =
            modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null;
        // declared in a final class?
        DetailAST parent = ast.getParent();
        while (parent != null && !checkFinal) {
            if (parent.getType() == TokenTypes.CLASS_DEF) {
                final DetailAST classModifiers =
                    parent.findFirstToken(TokenTypes.MODIFIERS);
                checkFinal = classModifiers.findFirstToken(TokenTypes.FINAL) != null;
                parent = null;
            }
            else if (parent.getType() == TokenTypes.LITERAL_NEW
                    || parent.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
                checkFinal = true;
                parent = null;
            }
            else if (parent.getType() == TokenTypes.ENUM_DEF) {
                checkFinal = modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) != null;
                parent = null;
            }
            else {
                parent = parent.getParent();
            }
        }
        if (checkFinal && !isAnnotatedWithSafeVarargs(ast)) {
            checkForRedundantModifier(ast, TokenTypes.FINAL);
        }

        if (ast.findFirstToken(TokenTypes.SLIST) == null) {
            processAbstractMethodParameters(ast);
        }
    }

    /**
     * Process validation of parameters for Methods with no definition.
     *
     * @param ast method AST
     */
    private void processAbstractMethodParameters(DetailAST ast) {
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);
        TokenUtil.forEachChild(parameters, TokenTypes.PARAMETER_DEF, paramDef -> {
            checkForRedundantModifier(paramDef, TokenTypes.FINAL);
        });
    }

    /**
     * Check if class constructor has proper modifiers.
     *
     * @param classCtorAst class constructor ast
     */
    private void checkClassConstructorModifiers(DetailAST classCtorAst) {
        final DetailAST classDef = classCtorAst.getParent().getParent();
        if (!isClassPublic(classDef) && !isClassProtected(classDef)) {
            checkForRedundantModifier(classCtorAst, TokenTypes.LITERAL_PUBLIC);
        }
    }

    /**
     * Checks if given resource has redundant modifiers.
     *
     * @param ast ast
     */
    private void processResources(DetailAST ast) {
        checkForRedundantModifier(ast, TokenTypes.FINAL);
    }

    /**
     * Checks if given ast has a redundant modifier.
     *
     * @param ast ast
     * @param modifierType The modifier to check for.
     */
    private void checkForRedundantModifier(DetailAST ast, int modifierType) {
        Optional.ofNullable(ast.findFirstToken(TokenTypes.MODIFIERS))
            .ifPresent(modifiers -> {
                TokenUtil.forEachChild(modifiers, modifierType, modifier -> {
                    log(modifier, MSG_KEY, modifier.getText());
                });
            });
    }

    /**
     * Checks if given class ast has protected modifier.
     *
     * @param classDef class ast
     * @return true if class is protected, false otherwise
     */
    private static boolean isClassProtected(DetailAST classDef) {
        final DetailAST classModifiers =
                classDef.findFirstToken(TokenTypes.MODIFIERS);
        return classModifiers.findFirstToken(TokenTypes.LITERAL_PROTECTED) != null;
    }

    /**
     * Checks if given class is accessible from "public" scope.
     *
     * @param ast class def to check
     * @return true if class is accessible from public scope,false otherwise
     */
    private static boolean isClassPublic(DetailAST ast) {
        boolean isAccessibleFromPublic = false;
        final boolean isMostOuterScope = ast.getParent() == null;
        final DetailAST modifiersAst = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean hasPublicModifier =
                modifiersAst.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null;

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
     *
     * @param ast AST node
     * @return true if it is an enum member
     */
    private static boolean isEnumMember(DetailAST ast) {
        final DetailAST parentTypeDef = ast.getParent().getParent();
        return parentTypeDef.getType() == TokenTypes.ENUM_DEF;
    }

    /**
     * Checks if current AST node is member of Interface or Annotation, not of their subnodes.
     *
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
     * Checks if method definition is annotated with.
     * <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/SafeVarargs.html">
     * SafeVarargs</a> annotation
     *
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
     *
     * @param methodDef method definition node
     * @return List of annotations
     */
    private static List<DetailAST> getMethodAnnotationsList(DetailAST methodDef) {
        final List<DetailAST> annotationsList = new ArrayList<>();
        final DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        TokenUtil.forEachChild(modifiers, TokenTypes.ANNOTATION, annotationsList::add);
        return annotationsList;
    }

}
