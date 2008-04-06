package com.puppycrawl.tools.checkstyle.coding;

public class InputEqualsAvoidNull {

    public boolean equals(Object o) {
    return false;
    }

    /**
     * methods that should get flagged
     * @return
     */
    public void flag() {

        Object o = new Object();
        String s = "pizza";

        o.equals("hot pizza");

        o.equals(s = "cold pizza");

        o.equals(((s = "cold pizza")));

        o.equals("cheese" + "ham" + "sauce");

        o.equals(("cheese" + "ham") + "sauce");

        o.equals((("cheese" + "ham")) + "sauce");
    }

    /**
     * methods that should not get flagged
     *
     * @return
     */
    public void noFlag() {
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

        InputEqualsAvoidNullOutter outter = new InputEqualsAvoidNullOutter();

        outter.new InputEqualsAvoidNullInner().equals("eat pizza and enjoy inner classes");
    }

}

class InputEqualsAvoidNullOutter {
    public class InputEqualsAvoidNullInner {
	public boolean equals(Object o) {
	    return true;
	}
    }
}
