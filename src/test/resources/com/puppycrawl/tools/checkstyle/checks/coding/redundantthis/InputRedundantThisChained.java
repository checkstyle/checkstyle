/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisChained {
    private String name;
    private String email;

    public Chain setName(String name)   { this.name  = name;  return this; }
    public Chain setEmail(String email) { this.email = email; return this; }

    public Chain validate() {
        if (this.name.isEmpty())  throw new IllegalStateException("Name required");
        // violation above, 'Redundant "this", variable 'name' can be accesed directly.'

        if (this.email.isEmpty()) throw new IllegalStateException("Email required");
        // violation above, 'Redundant "this", variable 'email' can be accesed directly.'

        return this;
    }

    public void show() {
        System.out.println(this.name + " | " + this.email);
        // 2 violations above:
        // 'Redundant "this", variable 'name' can be accesed directly.'
        // 'Redundant "this", variable 'email' can be accesed directly.'
    }

    public static void main(String[] args) {
        new Chain()
                .setName("Alice")
                .setEmail("alice@mail.com")
                .validate()
                .show();
    }
}
