/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullIgnoreCase {

    public boolean equals(Object o) {
        return false;
    }

    public void flagForEquals() {
        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza")/*comment test*/;

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));

        o.equals("cheese" + "ham" + "sauce");

        o.equals(("cheese" + "ham") + "sauce");

        o.equals((("cheese" + "ham")) + "sauce");
    }

    public void flagForEqualsIgnoreCase() {
        String s = "pizza";

        s.equalsIgnoreCase("hot pizza");

        s.equalsIgnoreCase(s = "cold pizza");

        s.equalsIgnoreCase(((s = "cold pizza")));

        s.equalsIgnoreCase("cheese" + "ham" + "sauce");

        s.equalsIgnoreCase(("cheese" + "ham") + "sauce");

        s.equalsIgnoreCase((("cheese" + "ham")) + "sauce");
    }

    public void flagForBoth() {
        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza");

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));

        o.equals("cheese" + "ham" + "sauce");

        o.equals(("cheese" + "ham") + "sauce");

        o.equals((("cheese" + "ham")) + "sauce");

        s.equalsIgnoreCase("hot pizza");

        s.equalsIgnoreCase(s = "cold pizza");

        s.equalsIgnoreCase(((s = "cold pizza")));

        s.equalsIgnoreCase("cheese" + "ham" + "sauce");

        s.equalsIgnoreCase(("cheese" + "ham") + "sauce");

        s.equalsIgnoreCase((("cheese" + "ham")) + "sauce");
    }

    public void testEqualsStillFlagged() {
        String s = "pizza";
        s.equals("hot pizza"); // violation 'left .* of .* equals'
    }
}
