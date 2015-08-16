////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>Checks that a local variable or a parameter does not shadow
 * a field that is defined in the same class.
 * <p>
 * An example of how to configure the check is:
 * <pre>
 * &lt;module name="HiddenField"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it checks variables but not
 * parameters is:
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it ignores the parameter of
 * a setter method is:
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="ignoreSetter" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * A method is recognized as a setter if it is in the following form
 * <pre>
 * ${returnType} set${Name}(${anyType} ${name}) { ... }
 * </pre>
 * where ${anyType} is any primitive type, class or interface name;
 * ${name} is name of the variable that is being set and ${Name} its
 * capitalized form that appears in the method name. By default it is expected
 * that setter returns void, i.e. ${returnType} is 'void'. For example
 * <pre>
 * void setTime(long time) { ... }
 * </pre>
 * Any other return types will not let method match a setter pattern. However,
 * by setting <em>setterCanReturnItsClass</em> property to <em>true</em>
 * definition of a setter is expanded, so that setter return type can also be
 * a class in which setter is declared. For example
 * <pre>
 * class PageBuilder {
 *   PageBuilder setName(String name) { ... }
 * }
 * </pre>
 * Such methods are known as chain-setters and a common when Builder-pattern
 * is used. Property <em>setterCanReturnItsClass</em> has effect only if
 * <em>ignoreSetter</em> is set to true.
 * <p>
 * An example of how to configure the check so that it ignores the parameter
 * of either a setter that returns void or a chain-setter.
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="ignoreSetter" value="true"/&gt;
 *    &lt;property name="setterCanReturnItsClass" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it ignores constructor
 * parameters is:
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="ignoreConstructorParameter" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it ignores variables and parameters
 * named 'test':
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="ignoreFormat" value="^test$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <pre>
 * {@code
 * class SomeClass
 * {
 *     private List&lt;String&gt; test;
 *
 *     private void addTest(List&lt;String&gt; test) // no violation
 *     {
 *         this.test.addAll(test);
 *     }
 *
 *     private void foo()
 *     {
 *         final List&lt;String&gt; test = new ArrayList&lt;&gt;(); // no violation
 *         ...
 *     }
 * }
 * }
 * </pre>
 *
 * @author Dmitri Priimak
 */
public class HiddenFieldCheck
    extends Check {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "hidden.field";

    /** stack of sets of field names,
     * one for each class of a set of nested classes.
     */
    private FieldFrame currentFrame;

    /** pattern for names of variables and parameters to ignore. */
    private Pattern regexp;

    /** controls whether to check the parameter of a property setter method */
    private boolean ignoreSetter;

    /**
     * if ignoreSetter is set to true then this variable controls what
     * the setter method can return By default setter must return void.
     * However, is this variable is set to true then setter can also
     * return class in which is declared.
     */
    private boolean setterCanReturnItsClass;

    /** controls whether to check the parameter of a constructor */
    private boolean ignoreConstructorParameter;

    /** controls whether to check the parameter of abstract methods. */
    private boolean ignoreAbstractMethods;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentFrame = new FieldFrame(null, true, null);
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int type = ast.getType();
        switch (type) {
            case TokenTypes.VARIABLE_DEF:
            case TokenTypes.PARAMETER_DEF:
                processVariable(ast);
                break;

            default:
                visitOtherTokens(ast, type);
        }
    }

    /**
     * Called to process tokens other than {@link TokenTypes#VARIABLE_DEF}
     * and {@link TokenTypes#PARAMETER_DEF}
     *
     * @param ast token to process
     * @param type type of the token
     */
    private void visitOtherTokens(DetailAST ast, int type) {
        //A more thorough check of enum constant class bodies is
        //possible (checking for hidden fields against the enum
        //class body in addition to enum constant class bodies)
        //but not attempted as it seems out of the scope of this
        //check.
        final DetailAST typeMods = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStaticInnerType =
                typeMods != null
                        && typeMods.branchContains(TokenTypes.LITERAL_STATIC);
        final String frameName;

        if (type == TokenTypes.CLASS_DEF || type == TokenTypes.ENUM_DEF) {
            frameName = ast.findFirstToken(TokenTypes.IDENT).getText();
        }
        else {
            frameName = null;
        }
        final FieldFrame frame = new FieldFrame(currentFrame, isStaticInnerType, frameName);

        //add fields to container
        final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        // enum constants may not have bodies
        if (objBlock != null) {
            DetailAST child = objBlock.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.VARIABLE_DEF) {
                    final String name =
                        child.findFirstToken(TokenTypes.IDENT).getText();
                    final DetailAST mods =
                        child.findFirstToken(TokenTypes.MODIFIERS);
                    if (mods.branchContains(TokenTypes.LITERAL_STATIC)) {
                        frame.addStaticField(name);
                    }
                    else {
                        frame.addInstanceField(name);
                    }
                }
                child = child.getNextSibling();
            }
        }
        // push container
        currentFrame = frame;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF
            || ast.getType() == TokenTypes.ENUM_DEF
            || ast.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            //pop
            currentFrame = currentFrame.getParent();
        }
    }

    /**
     * Process a variable token.
     * Check whether a local variable or parameter shadows a field.
     * Store a field for later comparison with local variables and parameters.
     * @param ast the variable token.
     */
    private void processVariable(DetailAST ast) {
        if (!ScopeUtils.inInterfaceOrAnnotationBlock(ast)
            && (ScopeUtils.isLocalVariableDef(ast)
                || ast.getType() == TokenTypes.PARAMETER_DEF)) {
            // local variable or parameter. Does it shadow a field?
            final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            final String name = nameAST.getText();

            if (isStaticOrOnstanceField(ast, name)
                && !isMatchingRegexp(name)
                && !isIgnoredSetterParam(ast, name)
                && !isIgnoredConstructorParam(ast)
                && !isIgnoredParamOfAbstractMethod(ast)) {
                log(nameAST, MSG_KEY, name);
            }
        }
    }

    /**
     * check for static or instance field.
     * @param ast token
     * @param name identifier of token
     * @return true if static or instance field
     */
    private boolean isStaticOrOnstanceField(DetailAST ast, String name) {
        return currentFrame.containsStaticField(name)
            || !inStatic(ast) && currentFrame.containsInstanceField(name);
    }

    /**
     * check name by regExp
     * @param name string value to check
     * @return true is regexp is matching
     */
    private boolean isMatchingRegexp(String name) {
        return regexp != null && regexp.matcher(name).find();
    }

    /**
     * Determines whether an AST node is in a static method or static
     * initializer.
     * @param ast the node to check.
     * @return true if ast is in a static method or a static block;
     */
    private static boolean inStatic(DetailAST ast) {
        DetailAST parent = ast.getParent();
        while (parent != null) {
            switch (parent.getType()) {
                case TokenTypes.STATIC_INIT:
                    return true;
                case TokenTypes.METHOD_DEF:
                    final DetailAST mods =
                        parent.findFirstToken(TokenTypes.MODIFIERS);
                    return mods.branchContains(TokenTypes.LITERAL_STATIC);
                default:
                    parent = parent.getParent();
            }
        }
        return false;
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of a
     * setter method, where the property setter method for field 'xyz' has
     * name 'setXyz', one parameter named 'xyz', and return type void
     * (default behavior) or return type is name of the class in which
     * such method is declared (allowed only if
     * {@link #setSetterCanReturnItsClass(boolean)} is called with
     * value <em>true</em>)
     *
     * @param ast the AST to check.
     * @param name the name of ast.
     * @return true if ast should be ignored because check property
     * ignoreSetter is true and ast is the parameter of a setter method.
     */
    private boolean isIgnoredSetterParam(DetailAST ast, String name) {
        if (ast.getType() == TokenTypes.PARAMETER_DEF && ignoreSetter) {
            final DetailAST parametersAST = ast.getParent();
            final DetailAST methodAST = parametersAST.getParent();
            if (parametersAST.getChildCount() == 1
                && methodAST.getType() == TokenTypes.METHOD_DEF
                && isSetterMethod(methodAST, name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if a specific method identified by methodAST and a single
     * variable name aName is a setter. This recognition partially depends
     * on mSetterCanReturnItsClass property.
     *
     * @param aMethodAST AST corresponding to a method call
     * @param aName name of single parameter of this method.
     * @return true of false indicating of method is a setter or not.
     */
    private boolean isSetterMethod(DetailAST aMethodAST, String aName) {
        final String methodName =
            aMethodAST.findFirstToken(TokenTypes.IDENT).getText();
        boolean isSetterMethod = false;

        if (methodName.equals("set" + capitalize(aName))) {
            // method name did match set${Name}(${anyType} ${aName})
            // where ${Name} is capitalized version of ${aName}
            // therefore this method is potentially a setter
            final DetailAST typeAST = aMethodAST.findFirstToken(TokenTypes.TYPE);
            final String returnType = typeAST.getFirstChild().getText();
            if (typeAST.branchContains(TokenTypes.LITERAL_VOID)
                || setterCanReturnItsClass && currentFrame.embeddedIn(returnType)) {
                // this method has signature
                //
                //     void set${Name}(${anyType} ${name})
                //
                // and therefore considered to be a setter
                //
                // or
                //
                // return type is not void, but it is the same as the class
                // where method is declared and and mSetterCanReturnItsClass
                // is set to true
                isSetterMethod = true;
            }
        }

        return isSetterMethod;
    }

    /**
     * Capitalizes a given property name the way we expect to see it in
     * a setter name.
     * @param name a property name
     * @return capitalized property name
     */
    private static String capitalize(final String name) {
        String setterName = name;
        // we should not capitalize the first character if the second
        // one is a capital one, since according to JavBeans spec
        // setXYzz() is a setter for XYzz property, not for xYzz one.
        if (name.length() == 1 || !Character.isUpperCase(name.charAt(1))) {
            setterName = name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
        }
        return setterName;
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of a
     * constructor.
     * @param ast the AST to check.
     * @return true if ast should be ignored because check property
     * ignoreConstructorParameter is true and ast is a constructor parameter.
     */
    private boolean isIgnoredConstructorParam(DetailAST ast) {
        boolean result = false;
        if (ast.getType() == TokenTypes.PARAMETER_DEF
            && ignoreConstructorParameter) {
            final DetailAST parametersAST = ast.getParent();
            final DetailAST constructorAST = parametersAST.getParent();
            result = constructorAST.getType() == TokenTypes.CTOR_DEF;
        }
        return result;
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of an
     * abstract method.
     * @param ast the AST to check.
     * @return true if ast should be ignored because check property
     * ignoreAbstactMethods is true and ast is a parameter of abstract
     * methods.
     */
    private boolean isIgnoredParamOfAbstractMethod(DetailAST ast) {
        boolean result = false;
        if (ast.getType() == TokenTypes.PARAMETER_DEF
            && ignoreAbstractMethods) {
            final DetailAST method = ast.getParent().getParent();
            if (method.getType() == TokenTypes.METHOD_DEF) {
                final DetailAST mods = method.findFirstToken(TokenTypes.MODIFIERS);
                result = mods.branchContains(TokenTypes.ABSTRACT);
            }
        }
        return result;
    }

    /**
     * Set the ignore format to the specified regular expression.
     * @param format a <code>String</code> value
     */
    public void setIgnoreFormat(String format) {
        regexp = Utils.createPattern(format);
    }

    /**
     * Set whether to ignore the parameter of a property setter method.
     * @param ignoreSetter decide whether to ignore the parameter of
     * a property setter method.
     */
    public void setIgnoreSetter(boolean ignoreSetter) {
        this.ignoreSetter = ignoreSetter;
    }

    /**
     * Controls if setter can return only void (default behavior) or it
     * can also return class in which it is declared.
     *
     * @param aSetterCanReturnItsClass if true then setter can return
     *        either void or class in which it is declared. If false then
     *        in order to be recognized as setter method (otherwise
     *        already recognized as a setter) must return void.  Later is
     *        the default behavior.
     */
    public void setSetterCanReturnItsClass(
        boolean aSetterCanReturnItsClass) {
        setterCanReturnItsClass = aSetterCanReturnItsClass;
    }

    /**
     * Set whether to ignore constructor parameters.
     * @param ignoreConstructorParameter decide whether to ignore
     * constructor parameters.
     */
    public void setIgnoreConstructorParameter(
        boolean ignoreConstructorParameter) {
        this.ignoreConstructorParameter = ignoreConstructorParameter;
    }

    /**
     * Set whether to ignore parameters of abstract methods.
     * @param ignoreAbstractMethods decide whether to ignore
     * parameters of abstract methods.
     */
    public void setIgnoreAbstractMethods(
        boolean ignoreAbstractMethods) {
        this.ignoreAbstractMethods = ignoreAbstractMethods;
    }

    /**
     * Holds the names of static and instance fields of a type.
     * @author Rick Giles
     * Describe class FieldFrame
     * @author Rick Giles
     */
    private static class FieldFrame {
        /** name of the frame, such name of the class or enum declaration */
        private final String frameName;

        /** is this a static inner type */
        private final boolean staticType;

        /** parent frame. */
        private final FieldFrame parent;

        /** set of instance field names */
        private final Set<String> instanceFields = Sets.newHashSet();

        /** set of static field names */
        private final Set<String> staticFields = Sets.newHashSet();

        /**
         * Creates new frame.
         * @param parent parent frame.
         * @param staticType is this a static inner type (class or enum).
         * @param frameName name associated with the frame, which can be a
         */
        public FieldFrame(FieldFrame parent, boolean staticType, String frameName) {
            this.parent = parent;
            this.staticType = staticType;
            this.frameName = frameName;
        }

        /**
         * Is this frame for static inner type.
         * @return is this field frame for static inner type.
         */
        boolean isStaticType() {
            return staticType;
        }

        /**
         * Adds an instance field to this FieldFrame.
         * @param field  the name of the instance field.
         */
        public void addInstanceField(String field) {
            instanceFields.add(field);
        }

        /**
         * Adds a static field to this FieldFrame.
         * @param field  the name of the instance field.
         */
        public void addStaticField(String field) {
            staticFields.add(field);
        }

        /**
         * Determines whether this FieldFrame contains an instance field.
         * @param field the field to check.
         * @return true if this FieldFrame contains instance field field.
         */
        public boolean containsInstanceField(String field) {
            return instanceFields.contains(field)
                    || parent != null
                    && !isStaticType()
                    && parent.containsInstanceField(field);

        }

        /**
         * Determines whether this FieldFrame contains a static field.
         * @param field the field to check.
         * @return true if this FieldFrame contains static field field.
         */
        public boolean containsStaticField(String field) {
            return staticFields.contains(field)
                    || parent != null
                    && parent.containsStaticField(field);
        }

        /**
         * Getter for parent frame.
         * @return parent frame.
         */
        public FieldFrame getParent() {
            return parent;
        }

        /**
         * Check if current frame is embedded in class or enum with
         * specific name.
         *
         * @param classOrEnumName name of class or enum that we are looking
         * for in the chain of field frames.
         *
         * @return true if current frame is embedded in class or enum
         * with name classOrNameName
         */
        private boolean embeddedIn(String classOrEnumName) {
            FieldFrame currentFrame = this;
            while (currentFrame != null) {
                if (Objects.equal(currentFrame.frameName, classOrEnumName)) {
                    return true;
                }
                currentFrame = currentFrame.parent;
            }
            return false;
        }
    }
}
