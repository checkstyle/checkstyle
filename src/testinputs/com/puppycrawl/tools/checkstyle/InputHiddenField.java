package com.puppycrawl.tools.checkstyle;

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
        void useX(int y) {
            y++;
        }
    }
}

