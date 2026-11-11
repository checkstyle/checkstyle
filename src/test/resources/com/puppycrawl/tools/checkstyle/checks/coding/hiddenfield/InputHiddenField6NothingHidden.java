/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = true
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public interface InputHiddenField6NothingHidden
{
    public static int notHidden = 0;


    public void noShadow(int notHidden);
}

/** tests ignoring the parameter of a property setter method */
class PropertySetter16
{
    private int prop;

    /** setter */
    public void setProp(int prop) // violation ''prop' hides a field'
    {
        this.prop = prop;
    }

    /** violation - incorrect method name */
    public void setprop(int prop) // violation ''prop' hides a field'
    {
        this.prop = prop;
    }

    /** violation - more than one parameter */
    public void setProp(int prop, int extra) // violation ''prop' hides a field'
    {
        this.prop = prop;
    }
}

/** tests a non-void method */
class PropertySetter26
{
    private int prop;

    /** violation - not a void method */
    public int setProp(int prop) // violation ''prop' hides a field'
    {
        this.prop = prop;
        return 0;
    }
}

/** tests for static fields */
class StaticFields6
{
    private static int hidden;

    public static void staticMethod()
    {
        int hidden; // violation ''hidden' hides a field'
    }

    public void method()
    {
        int hidden; // violation ''hidden' hides a field'
    }

    static
    {
        int hidden; // violation ''hidden' hides a field'
    }

    {
        int hidden; // violation ''hidden' hides a field'
    }
}

/** tests static methods & initializers */
class StaticMethods6
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
        void useY(int y) { // violation ''y' hides a field'
            y++;
        }
    }
}
