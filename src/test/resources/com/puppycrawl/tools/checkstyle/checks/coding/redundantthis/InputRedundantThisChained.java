/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisChained {
    private String name;
    private String email;

    public InputRedundantThisChained setName(String name) { this.name  = name;  return this; }

    public InputRedundantThisChained validate() {
        if (this.name.isEmpty())  throw new IllegalStateException("Name required");
        // violation above, 'Redundant "this", variable 'name' can be accessed directly.'

        if (this.email.isEmpty()) throw new IllegalStateException("Email required");
        // violation above, 'Redundant "this", variable 'email' can be accessed directly.'

        return this;
    }

    public void show() {
        System.out.println(this.name + " | " + this.email);
        // 2 violations above:
        // 'Redundant "this", variable 'name' can be accessed directly.'
        // 'Redundant "this", variable 'email' can be accessed directly.'
    }
}
