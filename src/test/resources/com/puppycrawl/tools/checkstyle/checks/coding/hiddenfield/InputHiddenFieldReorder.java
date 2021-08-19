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
        int hidden = 0; // violation
    }

    public InputHiddenFieldReorder(int hidden) //parameter shadows field // violation
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
//        private int innerHidden = 0;

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
        private int innerHidden = 0;
    }

    {
        int hidden = 0;//shadows field // violation
    }
    private int hidden = 0;
}

interface NothingHiddenReorder
{
    public static int notHidden = 0;

    // not a violation
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
            int hidden = 0; // violation
        }

        /**
         * Should not be flagged as violation as we don't check
         * hidden class level fields
         */
        int hidden;
    };

    /**
     * ctor parameter hides member
     */
    HiddenEnum1(int hidden) // violation
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

    int hidden;
    static int hiddenStatic;
}
