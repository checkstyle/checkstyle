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

/** tests chain-setter */
public class InputHiddenField6PropertySetter
{
    private int prop;

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public InputHiddenField6PropertySetter setProp(int prop) // violation ''prop' hides a field'
    {
        this.prop = prop;
        return this;
    }
}

/** tests setters (both regular and the chain one) on the enum */
enum PropertySetter46 {
    INSTANCE;

    private int prop;
    private int prop2;

    public void setProp(int prop) { // violation ''prop' hides a field'
        this.prop = prop;
    }

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter46 setProp2(int prop2) // violation ''prop2' hides a field'
    {
        this.prop2 = prop2;
        return this;
    }
}

/** Tests setter for one letter field (issue #730). */
class OneLetterField6
{
    int i;

    void setI(int i) // violation ''i' hides a field'
    {
        this.i = i;
    }
    enum Inner {}
}

class DuplicateFieldFromPreviousClass6
{
    public void method() {
        int i = 0;
    }
}

class NestedEnum6 {
    enum Test { A, B, C; int i; }

    void method(int i) {}
}
