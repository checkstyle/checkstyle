/*
SimplifyBooleanReturn
tokens = (default)LITERAL_IF


*/

package org.checkstyle.suppressionxpathfilter.coding.simplifybooleanreturn;

public class InputXpathSimplifyBooleanReturnIfBooleanEqualsBoolean {
    public static boolean toTest() {
        boolean even = false;
        if (even == true) { // violation 'Conditional logic can be removed.'
            return false;
        }
        else {
            return true;
        }
    }
}
