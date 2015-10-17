package com.puppycrawl.tools.checkstyle.checks.coding;

////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2002
////////////////////////////////////////////////////////////////////////////////

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenField
{
    private int hidden = 0;
    
    public InputHiddenField()
    {
        int hidden = 0; //shadows field
    }
    
    public InputHiddenField(int hidden) //parameter shadows field
    {
    }
    
    public void shadow()
    {
        int hidden = 0; //shadows field
    }
    
    public void shadowFor()
    {
        for (int hidden = 0; hidden < 1; hidden++) { //shadows field
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
            int innerHidden = 0; //shadows field
        }
    
        public Inner(int innerHidden) //shadows field
        {
        }
        
        private void innerShadow()
        {
            int innerHidden = 0; //shadows inner field
            int hidden = 0; //shadows outer field
        }
        
        private void innerShadowFor()
        {
            for (int innerHidden = 0; innerHidden < 1; innerHidden++) {
            }
            //shadows outer field
            for (int hidden = 0; hidden < 1; hidden++) {
            }
        }
        
        private void shadowParam(
            int innerHidden, //parameter shadows inner field
            int hidden //parameter shadows outer field
        )
        {
        }
        
        {
            int innerHidden = 0;//shadows inner field
            int hidden = 0; //shadows outer field
        }
    }

    {
        int hidden = 0;//shadows field
    }       
}
    
interface NothingHidden
{
    public static int notHidden = 0;
    
    // not an error
    public void noShadow(int notHidden);
}

/** tests ignoring the parameter of a property setter method */
class PropertySetter
{
    private int prop;
    
    /** setter */
    public void setProp(int prop)
    {
        this.prop = prop;
    }

    /** error - incorrect method name */
    public void setprop(int prop)
    {
        this.prop = prop;
    }

    /** error - more than one parameter */
    public void setProp(int prop, int extra)
    {
        this.prop = prop;
    }
}

/** tests a non-void method */
class PropertySetter2
{
    private int prop;
    
    /** error - not a void method */
    public int setProp(int prop)
    {
        this.prop = prop;
        return 0;
    }
}

/** tests for static fields */
class StaticFields
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
class StaticMethods
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

enum HiddenEnum
{
    A(129),
    B(283),
    C(1212)
    {
        /**
         * Should not be flagged as error as we don't check
         * hidden class level fields
         */
        int hidden;

        public void doSomething()
        {
            //Should be flagged as hiding enum constant member
            int hidden = 0;
        }
    };

    int hidden;
    static int hiddenStatic;

    /**
     * ctor parameter hides member
     */
    HiddenEnum(int hidden)
    {
    }

    public void doSomething()
    {
        //Should be flagged as hiding static member
        int hidden = 0;
    }

    public static void doSomethingStatic()
    {
        //Should be flagged as hiding static member
        int hiddenStatic = 0;
    }
}

// we should ignore this if user wants (ignoreAbstractMethods is true)
abstract class InputHiddenFieldBug1084512 {
    String x;
    public abstract void methodA(String x);
}

class Bug3370946 {
    private int xAxis;

    public void setxAxis(int xAxis) {
        this.xAxis = xAxis;
    }
}

/** tests chain-setter */
class PropertySetter3
{
    private int prop;

    /** 
     * if setterCanReturnItsClass == false then 
     *     error - not a void method
     * 
     * if setterCanReturnItsClass == true then 
     *     success as it is then considered to be a setter  
     */
    public PropertySetter3 setProp(int prop)
    {
        this.prop = prop;
        return this;
    }
}

/** tests setters (both regular and the chain one) on the enum */ 
enum PropertySetter4 {
    INSTANCE;
    
    private int prop;
    private int prop2;
    
    public void setProp(int prop) {
        this.prop = prop;
    }

    /** 
     * if setterCanReturnItsClass == false then 
     *     error - not a void method
     * 
     * if setterCanReturnItsClass == true then 
     *     success as it is then considered to be a setter  
     */
    public PropertySetter4 setProp2(int prop2)
    {
        this.prop2 = prop2;
        return this;
    }
}

/** Tests setter for one letter field (issue #730). */
class OneLetterField
{
    int i;

    void setI(int i)
    {
        this.i = i;
    }
}
