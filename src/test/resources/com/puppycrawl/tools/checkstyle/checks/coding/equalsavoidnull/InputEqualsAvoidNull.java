/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNull {

    public boolean equals(Object o) {
    return false;
    }
    // another comment
    /**
     * methods that should get flagged
     * @return
     */
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

    /**
     * methods that should get flagged
     */
    public void flagForEqualsIgnoreCase() {
        String s = "pizza";

        s.equalsIgnoreCase("hot pizza"); // violation 'left .* of .* equalsIgnoreCase'

        s.equalsIgnoreCase(s = "cold pizza"); // violation 'left .* of .* equalsIgnoreCase'

        s.equalsIgnoreCase(((s = "cold pizza"))); // violation 'left .* of .* equalsIgnoreCase'

        s.equalsIgnoreCase("cheese"+"ham"+"sauce");// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase(("cheese"+"ham")+"sauce");// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase((("cheese"+"ham"))+"sauce");// violation 'left.*of.*equalsIgnoreCase'
    }

    /**
     * methods that should get flagged
     */
    public void flagForBoth() {
        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza");

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));

        o.equals("cheese" + "ham" + "sauce");

        o.equals(("cheese" + "ham") + "sauce");

        o.equals((("cheese" + "ham")) + "sauce");

        s.equalsIgnoreCase("hot pizza");// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase(s = "cold pizza");// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase(((s = "cold pizza")));// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase("cheese"+"ham"+"sauce");// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase(("cheese"+"ham")+"sauce");// violation 'left.*of.*equalsIgnoreCase'

        s.equalsIgnoreCase((("cheese"+"ham"))+"sauce");// violation 'left.*of.*equalsIgnoreCase'
    }
}
