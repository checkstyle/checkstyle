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

/** tests chain-setter */
public class InputHiddenField8PropertySetter
{
    private int prop;

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public InputHiddenField8PropertySetter setProp(int prop)
    {
        this.prop = prop;
        return this;
    }
}

/** tests setters (both regular and the chain one) on the enum */
enum PropertySetter48 {
    INSTANCE;

    private int prop;
    private int prop2;

    public void setProp(int prop) {
        this.prop = prop;
    }

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter48 setProp2(int prop2)
    {
        this.prop2 = prop2;
        return this;
    }
}

/** Tests setter for one letter field (issue #730). */
class OneLetterField8
{
    int i;

    void setI(int i)
    {
        this.i = i;
    }
    enum Inner {}
}

class DuplicateFieldFromPreviousClass8
{
    public void method() {
        int i = 0;
    }
}

class NestedEnum8 {
    enum Test { A, B, C; int i; }

    void method(int i) {}
}
