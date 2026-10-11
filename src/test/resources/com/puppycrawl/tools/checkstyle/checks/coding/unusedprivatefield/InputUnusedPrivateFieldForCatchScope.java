/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.List;

public class InputUnusedPrivateFieldForCatchScope {

    private int loopVar; // ok, private field is used

    void useLoopVar() {
        for (int loopVar = 0; loopVar < 10; loopVar++) { }
        System.out.println(loopVar);
    }

    private int item; // ok, private field is used

    void useForEachVar(List<Integer> items) {
        for (Integer item : items) { }
        System.out.println(item);
    }

    private int ex; // ok, private field is used

    void useCatchVar() {
        try {
            throw new RuntimeException();
        }
        catch (RuntimeException ex) { }
        System.out.println(ex);
    }

    private int unused; // violation 'Unused private field'

    void unrelatedLoop() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

}
