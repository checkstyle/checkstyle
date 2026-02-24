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

class InputHiddenFieldBasic
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
