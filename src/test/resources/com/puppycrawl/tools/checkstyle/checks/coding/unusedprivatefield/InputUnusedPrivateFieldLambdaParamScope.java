/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldLambdaParamScope {

    private int shared; // ok, private field is used

    private int unused; // violation 'Unused private field'

    public void first(int shared) {
        Runnable action = () -> {
            System.out.println("run");
        };
        action.run();
        System.out.println(shared);
    }

    public int second() {
        return shared;
    }
}
