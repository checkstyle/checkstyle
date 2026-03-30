/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.List;

public class InputRedundantThis2 {

    private int x;
    private String name;

    void method(int x) {
        System.out.println(this.x);
        this.helper();
        // violation above, 'Redundant "this", method 'helper' can be accessed directly.'
        this.x = x;
        this.name = "test";
        // violation above, 'Redundant "this", variable 'name' can be accessed directly.'
        System.out.println(this.name);
        // violation above, 'Redundant "this", variable 'name' can be accessed directly.'
    }

    void helper() {}

    void register(List<InputRedundantThis2> list) {
        list.add(this);   // ok — 'this' required, passing current object
    }

    InputRedundantThis2() {
        this(0, "default");   // ok — constructor chaining
    }

    InputRedundantThis2(int x, String name) {
        this.x = x;
        this.name = name;
    }
}
