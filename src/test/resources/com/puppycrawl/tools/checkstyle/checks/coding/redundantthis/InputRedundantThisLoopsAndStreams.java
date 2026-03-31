/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.List;

public class InputRedundantThisLoopsAndStreams {
    private int age;
    private List<String> names;

    public void processFor() {
        for (int i = 0; i < 10; i++) {
            this.age = i;
            // violation above, 'Redundant "this", variable 'age' can be accesed directly.'
        }
    }

    public void processForEach() {
        for (String name : names) {
            this.age = 1;
            // violation above, 'Redundant "this", variable 'age' can be accesed directly.'
        }
    }

    public void processForShadow() {
        for (int age = 0; age < 10; age++) {
            this.age = age;
        }
    }

    public void processLambda() {
        names.forEach(n -> this.display(n));
        // violation above, 'Redundant "this", method 'display' can be accesed directly.'
    }

    public void processStream() {
        names.stream()
                .forEach(n -> this.display(n));
        // violation above, 'Redundant "this", method 'display' can be accesed directly.'
    }

    public void processMethodRef() {
        names.forEach(this::display);  // ok — required for method reference
    }

    public void display(String n) {
        System.out.println(n);
    }
}
