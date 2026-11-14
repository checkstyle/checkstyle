/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public interface InputHiddenField8NothingHidden
{
    public static int notHidden = 0;

    // not a violation
    public void noShadow(int notHidden);
}

/** tests ignoring the parameter of a property setter method */
class PropertySetter18
{
    private int prop;

    /** setter */
    public void setProp(int prop)
    {
        this.prop = prop;
    }

    /** violation - incorrect method name */
    public void setprop(int prop)
    {
        this.prop = prop;
    }

    /** violation - more than one parameter */
    public void setProp(int prop, int extra)
    {
        this.prop = prop;
    }
}

/** tests a non-void method */
class PropertySetter28
{
    private int prop;

    /** violation - not a void method */
    public int setProp(int prop)
    {
        this.prop = prop;
        return 0;
    }
}

/** tests for static fields */
class StaticFields8
{
    private static int hidden;

    public static void staticMethod()
    {
        int hidden;
    }

    public void method()
    {
        int hidden;
    }

    static
    {
        int hidden;
    }

    {
        int hidden;
    }
}

/** tests static methods & initializers */
class StaticMethods8
{
    private int notHidden;

    public static void method()
    {
        // local variables of static methods don't hide instance fields.
        int notHidden;
    }

    static
    {
        // local variables of static initializers don't hide instance fields.
        int notHidden;
    }

    private int x;
    private static int y;
    static class Inner {
        void useX(int x) {
            x++;
        }
        void useY(int y) {
            y++;
        }
    }
}
