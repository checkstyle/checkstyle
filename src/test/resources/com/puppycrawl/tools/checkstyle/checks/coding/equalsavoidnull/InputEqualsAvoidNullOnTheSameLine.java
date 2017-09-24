package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullOnTheSameLine {

    static {
        String b = "onion";
        String a=b;a.equals("ONION");
    }
}
