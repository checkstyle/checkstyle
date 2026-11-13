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

///////////////////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2002
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenField8
{
    private int hidden = 0;

    public InputHiddenField8()
    {
        int hidden = 0; //shadows field
    }

    public InputHiddenField8(int hidden) //parameter shadows field
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
