/*
HiddenField
ignoreFormat = ^i.*$
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/** tests ignoring the parameter of a property setter method */
class InputHiddenField3PropertySetter
{
    private int prop;

    /** setter */
    public void setProp(int prop) // violation, ''prop' hides a field'
    {
        this.prop = prop;
    }

    /** violation - incorrect method name */
    public void setprop(int prop) // violation, ''prop' hides a field'
    {
        this.prop = prop;
    }

    /** violation - more than one parameter */
    public void setProp(int prop, int extra) // violation, ''prop' hides a field'
    {
        this.prop = prop;
    }
}

/** tests a non-void method */
class PropertySetter23
{
    private int prop;

    /** violation - not a void method */
    public int setProp(int prop) // violation, ''prop' hides a field'
    {
        this.prop = prop;
        return 0;
    }
}

/** tests chain-setter */
class PropertySetter33
{
    private int prop;

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter33 setProp(int prop) // violation, ''prop' hides a field'
    {
        this.prop = prop;
        return this;
    }
}

/** tests setters (both regular and the chain one) on the enum */
enum PropertySetter43 {
    INSTANCE;

    private int prop;
    private int prop2;

    public void setProp(int prop) { // violation, ''prop' hides a field'
        this.prop = prop;
    }

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter43 setProp2(int prop2) // violation, ''prop2' hides a field'
    {
        this.prop2 = prop2;
        return this;
    }
}

