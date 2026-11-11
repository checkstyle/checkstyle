/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullNoFlag {

    /**
     * methods that should not get flagged
     *
     * @return
     */
    public void noFlagForEquals() {
        Object o = new Object();
        String s = "peperoni";

        o.equals(s += "mushrooms");

        (s = "thin crust").equals("thick crust");

        (s += "garlic").equals("basil");

        ("Chicago Style" + "NY Style").equals("California Style" + "Any Style");

        equals("peppers");

        "onions".equals(o);

        o.equals(new Object());

        o.equals(equals(o));

        equals("yummy");

        new Object().equals("more cheese");

        InputEqualsAvoidNullOuter outer = new InputEqualsAvoidNullOuter();

        outer.new InputEqualsAvoidNullInner().equals("eat pizza and enjoy inner classes");
    }

    /**
     * methods that should not get flagged
     */
    public void noFlagForEqualsIgnoreCase() {
        String s = "peperoni";
        String s1 = "tasty";

        s.equalsIgnoreCase(s += "mushrooms");

        s1.equalsIgnoreCase(s += "mushrooms");

        (s = "thin crust").equalsIgnoreCase("thick crust");

        (s += "garlic").equalsIgnoreCase("basil");

        ("Chicago Style" + "NY Style").equalsIgnoreCase("California Style" + "Any Style");

        "onions".equalsIgnoreCase(s);

        s.equalsIgnoreCase(new String());

        s.equals(s1);

        new String().equalsIgnoreCase("more cheese");

    }
}

class InputEqualsAvoidNullOuter {
    public class InputEqualsAvoidNullInner {
            public boolean equals(Object o) {
                return true;
            }
    }
}
