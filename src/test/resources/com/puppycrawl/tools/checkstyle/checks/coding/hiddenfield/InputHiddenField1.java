/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

///
// Test case file for checkstyle.
// Created: 2002
///

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenField1
{
    private int hidden = 0;

    public InputHiddenField1()
    {
        int hidden = 0;  // violation, ''hidden' hides a field'
    }

    public InputHiddenField1(int hidden) //parameter shadows field
    {
    }

    public void shadow()
    {
        int hidden = 0; //shadows field // violation, ''hidden' hides a field'
    }

    public void shadowFor()
    {
        for (int hidden = 0; hidden < 1; hidden++) { //shadows field
        // violation above, ''hidden' hides a field'
        }
    }

    public void shadowParam(int hidden) //parameter shadows field
    {
    }

    public class Inner
    {
        private int innerHidden = 0;

        public Inner()
        {
            int innerHidden = 0; //shadows field // violation, ''innerHidden' hides a field'
        }

        public Inner(int innerHidden) //shadows field
        {
        }

        private void innerShadow()
        {
            int innerHidden = 0; //shadows inner field // violation, ''innerHidden' hides a field'
            int hidden = 0; //shadows outer field // violation, ''hidden' hides a field'
        }

        private void innerShadowFor()
        {
            for (int innerHidden = 0; innerHidden < 1; innerHidden++) {
            // violation above, ''innerHidden' hides a field'
            }
            //shadows outer field
            for (int hidden = 0; hidden < 1; hidden++) { // violation, ''hidden' hides a field'
            }
        }

        private void shadowParam(
            int innerHidden, //parameter shadows inner field
            int hidden //parameter shadows outer field
        )
        {
        }

        {
            int innerHidden = 0;//shadows inner field // violation, ''innerHidden' hides a field'
            int hidden = 0; //shadows outer field // violation, ''hidden' hides a field'
        }
    }

    {
        int hidden = 0;//shadows field // violation, ''hidden' hides a field'
    }
}

interface NothingHidden1
{
    public static int notHidden = 0;

    // not a violation
    public void noShadow(int notHidden);
}

/** tests ignoring the parameter of a property setter method */
class PropertySetter11
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
class PropertySetter21
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
class StaticFields1
{
    private static int hidden;

    public static void staticMethod()
    {
        int hidden; // violation, ''hidden' hides a field'
    }

    public void method()
    {
        int hidden; // violation, ''hidden' hides a field'
    }

    static
    {
        int hidden; // violation, ''hidden' hides a field'
    }

    {
        int hidden; // violation, ''hidden' hides a field'
    }
}

/** tests static methods & initializers */
class StaticMethods1
{
    private int notHidden;

    public static void method()
    {
        // local variables of static methods don't hide instance fields.
        int notHidden;
    }

    static
    {
        // local variables of static initializers don't hide instance fields
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

enum HiddenEnum11
{
    A(129),
    B(283),
    C(1212)
    {
        /**
         * Should not be flagged as violation as we don't check
         * hidden class level fields
         */
        int hidden;

        public void doSomething()
        {
            //Should be flagged as hiding enum constant member
            int hidden = 0; // violation, ''hidden' hides a field'
        }
    };

    int hidden;
    static int hiddenStatic;

    /**
     * ctor parameter hides member
     */
    HiddenEnum11(int hidden)
    {
    }

    public void doSomething()
    {
        //Should be flagged as hiding static member
        int hidden = 0; // violation, ''hidden' hides a field'
    }

    public static void doSomethingStatic()
    {
        //Should be flagged as hiding static member
        int hiddenStatic = 0; // violation, ''hiddenStatic' hides a field'
    }
}

// we should ignore this if user wants (ignoreAbstractMethods is true)
abstract class InputHiddenFieldBug10845121 {
    String x;
    public abstract void methodA(String x);
}

class Bug33709461 {
    private int xAxis;

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }
}

/** tests chain-setter */
class PropertySetter31
{
    private int prop;

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter31 setProp(int prop)
    {
        this.prop = prop;
        return this;
    }
}

/** tests setters (both regular and the chain one) on the enum */
enum PropertySetter41 {
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
    public PropertySetter41 setProp2(int prop2)
    {
        this.prop2 = prop2;
        return this;
    }
}

/** Tests setter for one letter field (issue #730). */
class OneLetterField1
{
    int i;

    void setI(int i)
    {
        this.i = i;
    }
    enum Inner {}
}

class DuplicateFieldFromPreviousClass1
{
    public void method() {
        int i = 0;
    }
}

class NestedEnum1 {
    enum Test { A, B, C; int i; }

    void method(int i) {}
}
