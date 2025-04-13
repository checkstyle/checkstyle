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

///
// Test case file for checkstyle.
// Created: 2002
///

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenFieldReorder
{


    public InputHiddenFieldReorder()
    {
        int hidden = 0; // violation, ''hidden' hides a field'
    }

    public InputHiddenFieldReorder(int hidden) //parameter shadows field
    // violation above, ''hidden' hides a field'
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
    // violation above, ''hidden' hides a field'
    {
    }

    public class Inner
    {
//        private int innerHidden = 0;

        public Inner()
        {
            int innerHidden = 0; //shadows field // violation, ''innerHidden' hides a field'
        }

        public Inner(int innerHidden) //shadows field // violation, ''innerHidden' hides a field'
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
            // violation above, ''innerHidden' hides a field'
            int hidden //parameter shadows outer field // violation, ''hidden' hides a field'
        )
        {
        }

        {
            int innerHidden = 0;//shadows inner field // violation, ''innerHidden' hides a field'
            int hidden = 0; //shadows outer field // violation, ''hidden' hides a field'
        }
        private int innerHidden = 0;
    }

    {
        int hidden = 0;//shadows field // violation, ''hidden' hides a field'
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
            int hidden = 0; // violation, ''hidden' hides a field'
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
    HiddenEnum1(int hidden) // violation, ''hidden' hides a field'
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

    int hidden;
    static int hiddenStatic;
}
