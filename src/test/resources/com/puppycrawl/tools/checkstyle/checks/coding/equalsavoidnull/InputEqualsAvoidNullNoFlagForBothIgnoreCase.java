/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullNoFlagForBothIgnoreCase {

    public void noFlagForBoth() {
        Object o = new Object();
        String s = "peperoni";
        String s1 = "tasty";

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

        InputEqualsAvoidNullOuterForBoth1 outer = new InputEqualsAvoidNullOuterForBoth1();

        outer.new InputEqualsAvoidNullInnerForBoth().equals("eat pizza and enjoy inner classes");

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

class InputEqualsAvoidNullOuterForBoth1 {
    public class InputEqualsAvoidNullInnerForBoth {
            public boolean equals(Object o) {
                return true;
            }
    }
}
