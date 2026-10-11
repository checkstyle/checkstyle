package org.checkstyle.suppressionxpathfilter.coding.simplifybooleanreturn;

public class InputXpathSimplifyBooleanReturnAnonymousInnerClass {
    interface Checker {
        boolean check(int value);
    }

    public Checker getChecker() {
        return new Checker() {
            @Override
            public boolean check(int value) {
                if (value > 0) { // warn
                    return true;
                }
                else {
                    return false;
                }
            }
        };
    }
}
