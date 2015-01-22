////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * <p>Checks that a local variable or a parameter does not shadow
 * a field that is defined in the same class.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="HiddenField"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it checks variables but not
 * parameters is:
 * </p>
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it ignores the parameter of
 * a setter method is:
 * </p>
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="ignoreSetter" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of how to configure the check so that it ignores constructor
 * parameters is:
 * </p>
 * <pre>
 * &lt;module name="HiddenField"&gt;
 *    &lt;property name="ignoreConstructorParameter" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class HiddenFieldCheck
    extends Check
{
    /** stack of sets of field names,
     * one for each class of a set of nested classes.
     */
    private FieldFrame currentFrame;

    /** the regexp to match against */
    private Pattern regexp;

    /** controls whether to check the pnameter of a property setter method */
    private boolean ignoreSetter;

    /** controls whether to check the pnameter of a constructor */
    private boolean ignoreConstructorParameter;

    /** controls whether to check the pnameter of abstract methods. */
    private boolean ignoreAbstractMethods;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST)
    {
        currentFrame = new FieldFrame(null, true);
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        if ((ast.getType() == TokenTypes.VARIABLE_DEF)
            || (ast.getType() == TokenTypes.PARAMETER_DEF))
        {
            processVariable(ast);
            return;
        }

        //A more thorough check of enum constant class bodies is
        //possible (checking for hidden fields against the enum
        //class body in addition to enum constant class bodies)
        //but not attempted as it seems out of the scope of this
        //check.
        final DetailAST typeMods = ast.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStaticInnerType =
                (typeMods != null)
                        && typeMods.branchContains(TokenTypes.LITERAL_STATIC);
        final FieldFrame frame =
                new FieldFrame(currentFrame, isStaticInnerType);

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
    public void leaveToken(DetailAST ast)
    {
        if ((ast.getType() == TokenTypes.CLASS_DEF)
            || (ast.getType() == TokenTypes.ENUM_DEF)
            || (ast.getType() == TokenTypes.ENUM_CONSTANT_DEF))
        {
            //pop
            currentFrame = currentFrame.getParent();
        }
    }

    /**
     * Process a variable token.
     * Check whether a local variable or pnameter shadows a field.
     * Store a field for later comparison with local variables and pnameters.
     * @param ast the variable token.
     */
    private void processVariable(DetailAST ast)
    {
        if (ScopeUtils.inInterfaceOrAnnotationBlock(ast)
            || (!ScopeUtils.isLocalVariableDef(ast)
            && (ast.getType() != TokenTypes.PARAMETER_DEF)))
        {
            // do nothing
            return;
        }
        //local variable or pnameter. Does it shadow a field?
        final DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if ((currentFrame.containsStaticField(name)
             || (!inStatic(ast) && currentFrame.containsInstanceField(name)))
            && ((regexp == null) || (!getRegexp().matcher(name).find()))
            && !isIgnoredSetterParam(ast, name)
            && !isIgnoredConstructorParam(ast)
            && !isIgnoredParamOfAbstractMethod(ast))
        {
            log(nameAST, "hidden.field", name);
        }
    }

    /**
     * Determines whether an AST node is in a static method or static
     * initializer.
     * @param ast the node to check.
     * @return true if ast is in a static method or a static block;
     */
    private static boolean inStatic(DetailAST ast)
    {
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
     * name 'setXyz', one parameter named 'xyz', and return type void.
     * @param ast the AST to check.
     * @param name the name of ast.
     * @return true if ast should be ignored because check property
     * ignoreSetter is true and ast is the parameter of a setter method.
     */
    private boolean isIgnoredSetterParam(DetailAST ast, String name)
    {
        if (ast.getType() != TokenTypes.PARAMETER_DEF
            || !ignoreSetter)
        {
            return false;
        }
        //single pnameter?
        final DetailAST parametersAST = ast.getParent();
        if (parametersAST.getChildCount() != 1) {
            return false;
        }
        //method pnameter, not constructor pnameter?
        final DetailAST methodAST = parametersAST.getParent();
        if (methodAST.getType() != TokenTypes.METHOD_DEF) {
            return false;
        }
        //void?
        final DetailAST typeAST = methodAST.findFirstToken(TokenTypes.TYPE);
        if (!typeAST.branchContains(TokenTypes.LITERAL_VOID)) {
            return false;
        }

        //property setter name?
        final String methodName =
                methodAST.findFirstToken(TokenTypes.IDENT).getText();
        final String expectedName = "set" + capitalize(name);
        return methodName.equals(expectedName);
    }

    /**
     * Capitalizes a given property name the way we expect to see it in
     * a setter name.
     * @param name a property name
     * @return capitalized property name
     */
    private static String capitalize(final String name)
    {
        if (name == null || name.length() == 0) {
            return name;
        }
        // we should not capitalize the first character if the second
        // one is a capital one, since according to Javbeans spec
        // setXYzz() is a setter for XYzz property, not for xYzz one.
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1))) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of a
     * constructor.
     * @param ast the AST to check.
     * @return true if ast should be ignored because check property
     * ignoreConstructorPnameter is true and ast is a constructor parameter.
     */
    private boolean isIgnoredConstructorParam(DetailAST ast)
    {
        if ((ast.getType() != TokenTypes.PARAMETER_DEF)
            || !ignoreConstructorParameter)
        {
            return false;
        }
        final DetailAST parametersAST = ast.getParent();
        final DetailAST constructorAST = parametersAST.getParent();
        return (constructorAST.getType() == TokenTypes.CTOR_DEF);
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of an
     * abstract method.
     * @param ast the AST to check.
     * @return true if ast should be ignored because check property
     * ignoreAbstactMethods is true and ast is a parameter of abstract
     * methods.
     */
    private boolean isIgnoredParamOfAbstractMethod(DetailAST ast)
    {
        if ((ast.getType() != TokenTypes.PARAMETER_DEF)
            || !ignoreAbstractMethods)
        {
            return false;
        }
        final DetailAST method = ast.getParent().getParent();
        if (method.getType() != TokenTypes.METHOD_DEF) {
            return false;
        }
        final DetailAST mods = method.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null) && mods.branchContains(TokenTypes.ABSTRACT));
    }

    /**
     * Set the ignore format to the specified regular expression.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse format
     */
    public void setIgnoreFormat(String format)
        throws ConversionException
    {
        try {
            regexp = Utils.getPattern(format);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }
    }

    /**
     * Set whether to ignore the parameter of a property setter method.
     * @param ignoreSetter decide whether to ignore the parameter of
     * a property setter method.
     */
    public void setIgnoreSetter(boolean ignoreSetter)
    {
        this.ignoreSetter = ignoreSetter;
    }

    /**
     * Set whether to ignore constructor parameters.
     * @param ignoreConstructorParameter decide whether to ignore
     * constructor parameters.
     */
    public void setIgnoreConstructorParameter(
        boolean ignoreConstructorParameter)
    {
        this.ignoreConstructorParameter = ignoreConstructorParameter;
    }

    /**
     * Set whether to ignore parameters of abstract methods.
     * @param ignoreAbstractMethods decide whether to ignore
     * parameters of abstract methods.
     */
    public void setIgnoreAbstractMethods(
        boolean ignoreAbstractMethods)
    {
        this.ignoreAbstractMethods = ignoreAbstractMethods;
    }

    /** @return the regexp to match against */
    public Pattern getRegexp()
    {
        return regexp;
    }

    /**
     * Holds the names of static and instance fields of a type.
     * @author Rick Giles
     * Describe class FieldFrame
     * @author Rick Giles
     * @version Oct 26, 2003
     */
    private static class FieldFrame
    {
        /** is this a static inner type */
        private final boolean staticType;

        /** parent frame. */
        private final FieldFrame parent;

        /** set of instance field names */
        private final Set<String> instanceFields = Sets.newHashSet();

        /** set of static field names */
        private final Set<String> staticFields = Sets.newHashSet();

        /** Creates new frame.
         * @param staticType is this a static inner type (class or enum).
         * @param parent parent frame.
         */
        public FieldFrame(FieldFrame parent, boolean staticType)
        {
            this.parent = parent;
            this.staticType = staticType;
        }

        /** Is this frame for static inner type.
         * @return is this field frame for static inner type.
         */
        boolean isStaticType()
        {
            return staticType;
        }

        /**
         * Adds an instance field to this FieldFrame.
         * @param field  the name of the instance field.
         */
        public void addInstanceField(String field)
        {
            instanceFields.add(field);
        }

        /**
         * Adds a static field to this FieldFrame.
         * @param field  the name of the instance field.
         */
        public void addStaticField(String field)
        {
            staticFields.add(field);
        }

        /**
         * Determines whether this FieldFrame contains an instance field.
         * @param field the field to check.
         * @return true if this FieldFrame contains instance field field.
         */
        public boolean containsInstanceField(String field)
        {
            return instanceFields.contains(field)
                    || !isStaticType()
                    && (parent != null)
                    && parent.containsInstanceField(field);

        }

        /**
         * Determines whether this FieldFrame contains a static field.
         * @param field the field to check.
         * @return true if this FieldFrame contains static field field.
         */
        public boolean containsStaticField(String field)
        {
            return staticFields.contains(field)
                    || (parent != null)
                    && parent.containsStaticField(field);

        }

        /**
         * Getter for parent frame.
         * @return parent frame.
         */
        public FieldFrame getParent()
        {
            return parent;
        }
    }
}
