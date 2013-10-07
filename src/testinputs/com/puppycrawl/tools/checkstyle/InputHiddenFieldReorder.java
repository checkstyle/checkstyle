package com.puppycrawl.tools.checkstyle;

////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2002
////////////////////////////////////////////////////////////////////////////////

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenFieldReorder
{

    
    public InputHiddenFieldReorder()
    {
        int hidden = 0; //shadows field
    }
    
    public InputHiddenFieldReorder(int hidden) //parameter shadows field
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
//        private int innerHidden = 0;
        
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
        private int innerHidden = 0;
    }

    {
        int hidden = 0;//shadows field
    }
    private int hidden = 0;       
}
    
interface NothingHiddenReorder
{
    public static int notHidden = 0;
    
    // not an error
    public void noShadow(int notHidden);
}

enum HiddenEnum1
{
    A(129),
    B(283),
    C(1212)
    {
        public void doSomething()
        {
            //Should be flagged as hiding enum constant member
            int hidden = 0;
        }

        /**
         * Should not be flagged as error as we don't check
         * hidden class level fields
         */
        int hidden;
    };

    /**
     * ctor parameter hides member
     */
    HiddenEnum1(int hidden)
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

    int hidden;
    static int hiddenStatic;
}
