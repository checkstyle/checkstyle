package com.puppycrawl.tools.checkstyle.checks;

import java.util.*;

public class TestCheck {
    // Bad: magic number
    private static final int x = 42;

    // Bad: unused variable
    private String unusedField;

    // Bad: not following naming conventions
    public void MyMethod() {
        // Bad: empty catch block
        try {
            String s = null;
            s.toString(); // Bad: will throw NPE
        } catch (Exception e) {
        }

        // Bad: unused local variable
        int unused = 5;

        // Bad: hardcoded string that should be constant
        String message = "Hello World";

        // Bad: missing braces on if statement
        if (x > 0)
            System.out.println(message);

        String veryLongLine = "This is an extremely long line that exceeds the typical line length limit of 80 or 120 characters which is generally considered bad practice";
    }

    // Bad: missing javadoc
    public int calculate(int a,int b){  // Bad: missing spaces around parameters
        return a+b;  // Bad: missing spaces around operator
    }
}