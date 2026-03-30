/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisMethodCall {
    private int x;
    private String name;
    private String email;

    void method(int x) {
        System.out.println(this.x); // ok, param 'x' shadows field
        this.helper();
        // violation above, 'Redundant "this", method 'helper' can be accessed directly.'
        this.x = x; // ok, param 'x' shadows field
        this.name = "test";
        // violation above, 'Redundant "this", field 'name' can be accessed directly.'
        System.out.println(this.name);
        // violation above, 'Redundant "this", field 'name' can be accessed directly.'
    }

    void helper() {}

    public InputRedundantThisMethodCall setName(String name) {
        this.name = name; // ok, param 'name' shadows field
        return this;
    }

    public void validate() {
        if (this.name.isEmpty()) throw new IllegalStateException("Name required");
        // violation above, 'Redundant "this", field 'name' can be accessed directly.'

        if (this.email.isEmpty()) throw new IllegalStateException("Email required");
        // violation above, 'Redundant "this", field 'email' can be accessed directly.'
    }

    public void show() {
        System.out.println(this.name + " | " + this.email);
        // 2 violations above:
        // 'Redundant "this", field 'name' can be accessed directly.'
        // 'Redundant "this", field 'email' can be accessed directly.'
    }
}
