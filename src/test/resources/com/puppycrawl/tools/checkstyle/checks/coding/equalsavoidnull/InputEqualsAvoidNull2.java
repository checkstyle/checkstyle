/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNull2 {
    void foo() {
        String s = "";
        s.equals(s + s); // violation 'left .* of .* equals'
        s.equals("a" + "b"); // violation 'left .* of .* equals'
        s.equals(getInt() + s); // violation 'left .* of .* equals'
        s.equals(getInt() + getInt());
        s.endsWith("a");
        if (!s.equals("Hi[EOL]"+System.getProperty(""))) // violation 'left.*of.*equals'
            foo();
    }

    int getInt() {
        return 0;
    }

    public void flagForEquals() {

        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza")/*comment test*/;

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));
    }
}
