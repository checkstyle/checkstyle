/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThis2 {

    private int x;
    private String name;

    void method(int x) {
        System.out.println(this.x);
        this.helper();
        // violation above, 'Redundant "this", method 'helper' can be accesed directly.'
        this.x = x;
        this.name = "test";
        // violation above, 'Redundant "this", variable 'name' can be accesed directly.'
        System.out.println(this.name);
        // violation above, 'Redundant "this", variable 'name' can be accesed directly.'
    }

    void helper() {}

    void register(Registry r) {
        r.add(this);
    }

    InputRedundantThis2() {
        this(0, "default");
    }

    InputRedundantThis2(int x, String name) {
        this.x = x;
        this.name = name;
    }
}
