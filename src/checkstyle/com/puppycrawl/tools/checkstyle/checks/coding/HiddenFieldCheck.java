////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
    private FieldFrame mCurrentFrame;

    /** the regexp to match against */
    private Pattern mRegexp;

    /** controls whether to check the parameter of a property setter method */
    private boolean mIgnoreSetter;

    /** controls whether to check the parameter of a constructor */
    private boolean mIgnoreConstructorParameter;

    /** controls whether to check the parameter of abstract methods. */
    private boolean mIgnoreAbstractMethods;

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
    public void beginTree(DetailAST aRootAST)
    {
        mCurrentFrame = new FieldFrame(null, true);
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if ((aAST.getType() == TokenTypes.VARIABLE_DEF)
            || (aAST.getType() == TokenTypes.PARAMETER_DEF))
        {
            processVariable(aAST);
            return;
        }

        //A more thorough check of enum constant class bodies is
        //possible (checking for hidden fields against the enum
        //class body in addition to enum constant class bodies)
        //but not attempted as it seems out of the scope of this
        //check.
        final DetailAST typeMods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isStaticInnerType =
                (typeMods != null)
                        && typeMods.branchContains(TokenTypes.LITERAL_STATIC);
        final FieldFrame frame =
                new FieldFrame(mCurrentFrame, isStaticInnerType);

        //add fields to container
        final DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
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
        mCurrentFrame = frame;
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        if ((aAST.getType() == TokenTypes.CLASS_DEF)
            || (aAST.getType() == TokenTypes.ENUM_DEF)
            || (aAST.getType() == TokenTypes.ENUM_CONSTANT_DEF))
        {
            //pop
            mCurrentFrame = mCurrentFrame.getParent();
        }
    }

    /**
     * Process a variable token.
     * Check whether a local variable or parameter shadows a field.
     * Store a field for later comparison with local variables and parameters.
     * @param aAST the variable token.
     */
    private void processVariable(DetailAST aAST)
    {
        if (ScopeUtils.inInterfaceOrAnnotationBlock(aAST)
            || (!ScopeUtils.isLocalVariableDef(aAST)
            && (aAST.getType() != TokenTypes.PARAMETER_DEF)))
        {
            // do nothing
            return;
        }
        //local variable or parameter. Does it shadow a field?
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        final String name = nameAST.getText();
        if ((mCurrentFrame.containsStaticField(name)
             || (!inStatic(aAST) && mCurrentFrame.containsInstanceField(name)))
            && ((mRegexp == null) || (!getRegexp().matcher(name).find()))
            && !isIgnoredSetterParam(aAST, name)
            && !isIgnoredConstructorParam(aAST)
            && !isIgnoredParamOfAbstractMethod(aAST))
        {
            log(nameAST, "hidden.field", name);
        }
    }

    /**
     * Determines whether an AST node is in a static method or static
     * initializer.
     * @param aAST the node to check.
     * @return true if aAST is in a static method or a static block;
     */
    private static boolean inStatic(DetailAST aAST)
    {
        DetailAST parent = aAST.getParent();
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
     * @param aAST the AST to check.
     * @param aName the name of aAST.
     * @return true if aAST should be ignored because check property
     * ignoreSetter is true and aAST is the parameter of a setter method.
     */
    private boolean isIgnoredSetterParam(DetailAST aAST, String aName)
    {
        if (aAST.getType() != TokenTypes.PARAMETER_DEF
            || !mIgnoreSetter)
        {
            return false;
        }
        //single parameter?
        final DetailAST parametersAST = aAST.getParent();
        if (parametersAST.getChildCount() != 1) {
            return false;
        }
        //method parameter, not constructor parameter?
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
        final String expectedName = "set" + capitalize(aName);
        return methodName.equals(expectedName);
    }

    /**
     * Capitalizes a given property name the way we expect to see it in
     * a setter name.
     * @param aName a property name
     * @return capitalized property name
     */
    private static String capitalize(final String aName)
    {
        if (aName == null || aName.length() == 0) {
            return aName;
        }
        // we should not capitalize the first character if the second
        // one is a capital one, since according to JavaBeans spec
        // setXYzz() is a setter for XYzz property, not for xYzz one.
        if (aName.length() > 1 && Character.isUpperCase(aName.charAt(1))) {
            return aName;
        }
        return aName.substring(0, 1).toUpperCase() + aName.substring(1);
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of a
     * constructor.
     * @param aAST the AST to check.
     * @return true if aAST should be ignored because check property
     * ignoreConstructorParameter is true and aAST is a constructor parameter.
     */
    private boolean isIgnoredConstructorParam(DetailAST aAST)
    {
        if ((aAST.getType() != TokenTypes.PARAMETER_DEF)
            || !mIgnoreConstructorParameter)
        {
            return false;
        }
        final DetailAST parametersAST = aAST.getParent();
        final DetailAST constructorAST = parametersAST.getParent();
        return (constructorAST.getType() == TokenTypes.CTOR_DEF);
    }

    /**
     * Decides whether to ignore an AST node that is the parameter of an
     * abstract method.
     * @param aAST the AST to check.
     * @return true if aAST should be ignored because check property
     * ignoreAbstactMethods is true and aAST is a parameter of abstract
     * methods.
     */
    private boolean isIgnoredParamOfAbstractMethod(DetailAST aAST)
    {
        if ((aAST.getType() != TokenTypes.PARAMETER_DEF)
            || !mIgnoreAbstractMethods)
        {
            return false;
        }
        final DetailAST method = aAST.getParent().getParent();
        if (method.getType() != TokenTypes.METHOD_DEF) {
            return false;
        }
        final DetailAST mods = method.findFirstToken(TokenTypes.MODIFIERS);
        return ((mods != null) && mods.branchContains(TokenTypes.ABSTRACT));
    }

    /**
     * Set the ignore format to the specified regular expression.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setIgnoreFormat(String aFormat)
        throws ConversionException
    {
        try {
            mRegexp = Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /**
     * Set whether to ignore the parameter of a property setter method.
     * @param aIgnoreSetter decide whether to ignore the parameter of
     * a property setter method.
     */
    public void setIgnoreSetter(boolean aIgnoreSetter)
    {
        mIgnoreSetter = aIgnoreSetter;
    }

    /**
     * Set whether to ignore constructor parameters.
     * @param aIgnoreConstructorParameter decide whether to ignore
     * constructor parameters.
     */
    public void setIgnoreConstructorParameter(
        boolean aIgnoreConstructorParameter)
    {
        mIgnoreConstructorParameter = aIgnoreConstructorParameter;
    }

    /**
     * Set whether to ignore parameters of abstract methods.
     * @param aIgnoreAbstractMethods decide whether to ignore
     * parameters of abstract methods.
     */
    public void setIgnoreAbstractMethods(
        boolean aIgnoreAbstractMethods)
    {
        mIgnoreAbstractMethods = aIgnoreAbstractMethods;
    }

    /** @return the regexp to match against */
    public Pattern getRegexp()
    {
        return mRegexp;
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
        private final boolean mStaticType;

        /** parent frame. */
        private final FieldFrame mParent;

        /** set of instance field names */
        private final Set<String> mInstanceFields = Sets.newHashSet();

        /** set of static field names */
        private final Set<String> mStaticFields = Sets.newHashSet();

        /** Creates new frame.
         * @param aStaticType is this a static inner type (class or enum).
         * @param aParent parent frame.
         */
        public FieldFrame(FieldFrame aParent, boolean aStaticType)
        {
            mParent = aParent;
            mStaticType = aStaticType;
        }

        /** Is this frame for static inner type.
         * @return is this field frame for static inner type.
         */
        boolean isStaticType()
        {
            return mStaticType;
        }

        /**
         * Adds an instance field to this FieldFrame.
         * @param aField  the name of the instance field.
         */
        public void addInstanceField(String aField)
        {
            mInstanceFields.add(aField);
        }

        /**
         * Adds a static field to this FieldFrame.
         * @param aField  the name of the instance field.
         */
        public void addStaticField(String aField)
        {
            mStaticFields.add(aField);
        }

        /**
         * Determines whether this FieldFrame contains an instance field.
         * @param aField the field to check.
         * @return true if this FieldFrame contains instance field aField.
         */
        public boolean containsInstanceField(String aField)
        {
            return mInstanceFields.contains(aField)
                    || !isStaticType()
                    && (mParent != null)
                    && mParent.containsInstanceField(aField);

        }

        /**
         * Determines whether this FieldFrame contains a static field.
         * @param aField the field to check.
         * @return true if this FieldFrame contains static field aField.
         */
        public boolean containsStaticField(String aField)
        {
            return mStaticFields.contains(aField)
                    || (mParent != null)
                    && mParent.containsStaticField(aField);

        }

        /**
         * Getter for parent frame.
         * @return parent frame.
         */
        public FieldFrame getParent()
        {
            return mParent;
        }
    }
}
