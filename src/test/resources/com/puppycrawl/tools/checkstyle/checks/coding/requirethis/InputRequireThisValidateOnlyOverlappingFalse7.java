/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.BitSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputRequireThisValidateOnlyOverlappingFalse7 {
}
class Issue6264 {
    private final String hashCode;

    Issue6264() {
        this.hashCode = new String();
    }

    private void hashCodeMethod(String param) {
        hashCode();
    }
}
class Issue7306 {
    List<String> add = new ArrayList<>();
    List<String> singletonList = new ArrayList<>();

    void someMethod() {
        List<String> test = new ArrayList<>();
        test.forEach(this.add::add);
        test.forEach(test::add);
        test.forEach(Collections::singletonList);
        test.forEach(add::add); // violation 'Reference to instance variable 'add' needs "this.".'
    }
}
class AnotherOverlappingFalse {
    private String name;

    public void bind() {
        new Object() {
            @Override
            public String toString() {
                String name = null;

                if ( true ) {
                    name = String.format(Locale.US, name);
                }

                return name;
            }
        };
    }
}
