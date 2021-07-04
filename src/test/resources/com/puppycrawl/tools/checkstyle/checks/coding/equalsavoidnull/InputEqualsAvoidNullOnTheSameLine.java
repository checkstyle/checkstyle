/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullOnTheSameLine {

    static {
        String b = "onion";
        String a=b;a.equals("ONION"); // violation
    }

    private String a = "";
    private A b = null;

    public void shouldWarn() {
        a.equals("");A a=b; // violation
    }

    public void shouldNotWarn() {
        A a=b;a.equals("");
    }

    class A {}
}
