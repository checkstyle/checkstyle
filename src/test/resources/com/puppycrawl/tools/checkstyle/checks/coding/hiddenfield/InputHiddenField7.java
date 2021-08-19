/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = true
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2002
////////////////////////////////////////////////////////////////////////////////

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenField7
{
    private int hidden = 0;

    public InputHiddenField7()
    {
        int hidden = 0; // violation
    }

    public InputHiddenField7(int hidden) //parameter shadows field // violation
    {
    }

    public void shadow()
    {
        int hidden = 0; //shadows field // violation
    }

    public void shadowFor()
    {
        for (int hidden = 0; hidden < 1; hidden++) { //shadows field // violation
        }
    }

    public void shadowParam(int hidden) //parameter shadows field // violation
    {
    }

    public class Inner
    {
        private int innerHidden = 0;

        public Inner()
        {
            int innerHidden = 0; //shadows field // violation
        }

        public Inner(int innerHidden) //shadows field // violation
        {
        }

        private void innerShadow()
        {
            int innerHidden = 0; //shadows inner field // violation
            int hidden = 0; //shadows outer field // violation
        }

        private void innerShadowFor()
        {
            for (int innerHidden = 0; innerHidden < 1; innerHidden++) { // violation
            }
            //shadows outer field
            for (int hidden = 0; hidden < 1; hidden++) { // violation
            }
        }

        private void shadowParam(
            int innerHidden, //parameter shadows inner field // violation
            int hidden //parameter shadows outer field // violation
        )
        {
        }

        {
            int innerHidden = 0;//shadows inner field // violation
            int hidden = 0; //shadows outer field // violation
        }
    }

    {
        int hidden = 0;//shadows field // violation
    }
}

interface NothingHidden7
{
    public static int notHidden = 0;

    // not a violation
    public void noShadow(int notHidden);
}

/** tests ignoring the parameter of a property setter method */
class PropertySetter17
{
    private int prop;

    /** setter */
    public void setProp(int prop) // violation
    {
        this.prop = prop;
    }

    /** violation - incorrect method name */
    public void setprop(int prop) // violation
    {
        this.prop = prop;
    }

    /** violation - more than one parameter */
    public void setProp(int prop, int extra) // violation
    {
        this.prop = prop;
    }
}

/** tests a non-void method */
class PropertySetter27
{
    private int prop;

    /** violation - not a void method */
    public int setProp(int prop) // violation
    {
        this.prop = prop;
        return 0;
    }
}

/** tests for static fields */
class StaticFields7
{
    private static int hidden;

    public static void staticMethod()
    {
        int hidden; // violation
    }

    public void method()
    {
        int hidden; // violation
    }

    static
    {
        int hidden; // violation
    }

    {
        int hidden; // violation
    }
}

/** tests static methods & initializers */
class StaticMethods7
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
        void useY(int y) { // violation
            y++;
        }
    }
}

enum HiddenEnum17
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
            int hidden = 0; // violation
        }
    };

    int hidden;
    static int hiddenStatic;

    /**
     * ctor parameter hides member
     */
    HiddenEnum17(int hidden) // violation
    {
    }

    public void doSomething()
    {
        //Should be flagged as hiding static member
        int hidden = 0; // violation
    }

    public static void doSomethingStatic()
    {
        //Should be flagged as hiding static member
        int hiddenStatic = 0; // violation
    }
}

// we should ignore this if user wants (ignoreAbstractMethods is true)
abstract class InputHiddenFieldBug10845127 {
    String x;
    public abstract void methodA(String x);
}

class Bug33709467 {
    private int xAxis;

    public void setxAxis(int xAxis) { // violation
        this.xAxis = xAxis;
    }
}

/** tests chain-setter */
class PropertySetter37
{
    private int prop;

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter37 setProp(int prop) // violation
    {
        this.prop = prop;
        return this;
    }
}

/** tests setters (both regular and the chain one) on the enum */
enum PropertySetter47 {
    INSTANCE;

    private int prop;
    private int prop2;

    public void setProp(int prop) { // violation
        this.prop = prop;
    }

    /**
     * if setterCanReturnItsClass == false then
     *     violation - not a void method
     *
     * if setterCanReturnItsClass == true then
     *     success as it is then considered to be a setter
     */
    public PropertySetter47 setProp2(int prop2) // violation
    {
        this.prop2 = prop2;
        return this;
    }
}

/** Tests setter for one letter field (issue #730). */
class OneLetterField7
{
    int i;

    void setI(int i) // violation
    {
        this.i = i;
    }
    enum Inner {}
}

class DuplicateFieldFromPreviousClass7
{
    public void method() {
        int i = 0;
    }
}

class NestedEnum7 {
    enum Test { A, B, C; int i; }

    void method(int i) {}
}
