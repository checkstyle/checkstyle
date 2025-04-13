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
 * <div>
 * Checks for redundant modifiers.
 * </div>
 *
 * <p>
 * Rationale: The Java Language Specification strongly discourages the usage
 * of {@code public} and {@code abstract} for method declarations in interface
 * definitions as a matter of style.
 * </p>
 *
 * <p>The check validates:</p>
 * <ol>
 * <li>
 * Interface and annotation definitions.
 * </li>
 * <li>
 * Final modifier on methods of final and anonymous classes.
 * </li>
 * <li>
 * Type declarations nested under interfaces that are declared as {@code public} or {@code static}.
 * </li>
 * <li>
 * Class constructors.
 * </li>
 * <li>
 * Nested {@code enum} definitions that are declared as {@code static}.
 * </li>
 * <li>
 * {@code record} definitions that are declared as {@code final} and nested
 * {@code record} definitions that are declared as {@code static}.
 * </li>
 * <li>
 * {@code strictfp} modifier when using JDK 17 or later. See reason at
 * <a href="https://openjdk.org/jeps/306">JEP 306</a>
 * </li>
 * <li>
 * {@code final} modifier on unnamed variables when using JDK 22 or later.
 * </li>
 * </ol>
 *
 * <p>
 * interfaces by definition are abstract so the {@code abstract} modifier is redundant on them.
 * </p>
 *
 * <p>Type declarations nested under interfaces by definition are public and static,
 * so the {@code public} and {@code static} modifiers on nested type declarations are redundant.
 * On the other hand, classes inside of interfaces can be abstract or non abstract.
 * So, {@code abstract} modifier is allowed.
 * </p>
 *
 * <p>Fields in interfaces and annotations are automatically
 * public, static and final, so these modifiers are redundant as
 * well.</p>
 *
 * <p>As annotations are a form of interface, their fields are also
 * automatically public, static and final just as their
 * annotation fields are automatically public and abstract.</p>
 *
 * <p>A record class is implicitly final and cannot be abstract, these restrictions emphasize
 * that the API of a record class is defined solely by its state description, and
 * cannot be enhanced later by another class. Nested records are implicitly static. This avoids an
 * immediately enclosing instance which would silently add state to the record class.
 * See <a href="https://openjdk.org/jeps/395">JEP 395</a> for more info.</p>
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
 *
 * <p>
 * Nested {@code enum} types are always static by default.
 * </p>
 *
 * <p>Final classes by definition cannot be extended so the {@code final}
 * modifier on the method of a final class is redundant.
 * </p>
 *
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
 * Property {@code jdkVersion} - Set the JDK version that you are using.
 * Old JDK version numbering is supported (e.g. 1.8 for Java 8)
 * as well as just the major JDK version alone (e.g. 8) is supported.
 * This property only considers features from officially released
 * Java versions as supported. Features introduced in preview releases are not considered
 * supported until they are included in a non-preview release.
 * Type is {@code java.lang.String}.
 * Default value is {@code "22"}.
 * </li>
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
 * RESOURCE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PATTERN_VARIABLE_DEF">
 * PATTERN_VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAMBDA">
 * LAMBDA</a>.
 * </li>
 * </ul>
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

    /**
     *  Constant for jdk 22 version number.
     */
    private static final int JDK_22 = 22;

    /**
     *  Constant for jdk 17 version number.
     *
     */
    private static final int JDK_17 = 17;

    /**
     * Set the JDK version that you are using.
     * Old JDK version numbering is supported (e.g. 1.8 for Java 8)
     * as well as just the major JDK version alone (e.g. 8) is supported.
     * This property only considers features from officially released
     * Java versions as supported. Features introduced in preview releases are not considered
     * supported until they are included in a non-preview release.
     *
     */
    private int jdkVersion = JDK_22;

    /**
     * Setter to set the JDK version that you are using.
     * Old JDK version numbering is supported (e.g. 1.8 for Java 8)
     * as well as just the major JDK version alone (e.g. 8) is supported.
     * This property only considers features from officially released
     * Java versions as supported. Features introduced in preview releases are not considered
     * supported until they are included in a non-preview release.
     *
     * @param jdkVersion the Java version
     * @since 10.18.0
     */
    public void setJdkVersion(String jdkVersion) {
        final String singleVersionNumber;
        if (jdkVersion.startsWith("1.")) {
            singleVersionNumber = jdkVersion.substring(2);
        }
        else {
            singleVersionNumber = jdkVersion;
        }

        this.jdkVersion = Integer.parseInt(singleVersionNumber);
    }

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
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ANNOTATION_DEF:
                checkInterfaceModifiers(ast);
                break;
            case TokenTypes.ENUM_DEF:
                checkForRedundantModifier(ast, TokenTypes.LITERAL_STATIC);
                break;
            case TokenTypes.CTOR_DEF:
                checkConstructorModifiers(ast);
                break;
            case TokenTypes.METHOD_DEF:
                processMethods(ast);
                break;
            case TokenTypes.RESOURCE:
                processResources(ast);
                break;
            case TokenTypes.RECORD_DEF:
                checkForRedundantModifier(ast, TokenTypes.FINAL, TokenTypes.LITERAL_STATIC);
                break;
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.PATTERN_VARIABLE_DEF:
                checkUnnamedVariables(ast);
                break;
            case TokenTypes.LITERAL_CATCH:
                checkUnnamedVariables(ast.findFirstToken(TokenTypes.PARAMETER_DEF));
                break;
            case TokenTypes.LAMBDA:
                processLambdaParameters(ast);
                break;
            case TokenTypes.CLASS_DEF:
            case TokenTypes.ANNOTATION_FIELD_DEF:
                break;
            default:
                throw new IllegalStateException("Unexpected token type: " + ast.getType());
        }

        if (isInterfaceOrAnnotationMember(ast)) {
            processInterfaceOrAnnotation(ast);
        }

        if (jdkVersion >= JDK_17) {
            checkForRedundantModifier(ast, TokenTypes.STRICTFP);
        }
    }

    /**
     * Process lambda parameters.
     *
     * @param lambdaAst node of type {@link TokenTypes#LAMBDA}
     */
    private void processLambdaParameters(DetailAST lambdaAst) {
        final DetailAST lambdaParameters = lambdaAst.findFirstToken(TokenTypes.PARAMETERS);
        if (lambdaParameters != null) {
            TokenUtil.forEachChild(lambdaParameters, TokenTypes.PARAMETER_DEF,
                    this::checkUnnamedVariables);
        }
    }

    /**
     * Check if the variable is unnamed and has redundant final modifier.
     *
     * @param ast node of type {@link TokenTypes#VARIABLE_DEF}
     *     or {@link TokenTypes#PATTERN_VARIABLE_DEF}
     *     or {@link TokenTypes#PARAMETER_DEF}
     */
    private void checkUnnamedVariables(DetailAST ast) {
        if (jdkVersion >= JDK_22 && isUnnamedVariable(ast)) {
            checkForRedundantModifier(ast, TokenTypes.FINAL);
        }
    }

    /**
     * Check if the variable is unnamed.
     *
     * @param ast node of type {@link TokenTypes#VARIABLE_DEF}
     *     or {@link TokenTypes#PATTERN_VARIABLE_DEF}
     *     or {@link TokenTypes#PARAMETER_DEF}
     * @return true if the variable is unnamed
     */
    private static boolean isUnnamedVariable(DetailAST ast) {
        return "_".equals(ast.findFirstToken(TokenTypes.IDENT).getText());
    }

    /**
     * Check modifiers of constructor.
     *
     * @param ctorDefAst ast node of type {@link TokenTypes#CTOR_DEF}
     */
    private void checkConstructorModifiers(DetailAST ctorDefAst) {
        if (isEnumMember(ctorDefAst)) {
            checkEnumConstructorModifiers(ctorDefAst);
        }
        else {
            checkClassConstructorModifiers(ctorDefAst);
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
        DetailAST parent = ast;
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
     * @param modifierTypes The modifiers to check for.
     */
    private void checkForRedundantModifier(DetailAST ast, int... modifierTypes) {
        Optional.ofNullable(ast.findFirstToken(TokenTypes.MODIFIERS))
            .ifPresent(modifiers -> {
                for (DetailAST childAst = modifiers.getFirstChild();
                     childAst != null; childAst = childAst.getNextSibling()) {
                    if (TokenUtil.isOfType(childAst, modifierTypes)) {
                        log(childAst, MSG_KEY, childAst.getText());
                    }
                }
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
        final DetailAST modifiersAst = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean hasPublicModifier =
                modifiersAst.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null;

        if (TokenUtil.isRootNode(ast.getParent())) {
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
        parentTypeDef = parentTypeDef.getParent();
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
