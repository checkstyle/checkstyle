/*
SimplifyBooleanReturn
tokens = (default)LITERAL_IF


*/

package org.checkstyle.suppressionxpathfilter.coding.simplifybooleanreturn;

public class InputXpathSimplifyBooleanReturnAnonymousInnerClass {
    interface Checker {
        boolean check(int value);
    }

    public Checker getChecker() {
        return new Checker() {
            @Override
            public boolean check(int value) {
                if (value > 0) { // violation 'Conditional logic can be removed.'
                    return true;
                }
                else {
                    return false;
                }
            }
        };
    }
}
