package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

/* Config: default
 */

public class InputUnnecessaryParenthesesIfStatement { // ok

    void method(String sectionName) { // ok
        if ("Content".equals(sectionName) || "Overview".equals(sectionName) // ok
                || (!"AbbreviationAsWordInName".equals(sectionName) // violation
                // violation, unnecessary parenthesis in the line above
                && !"AbstractClassName".equals(sectionName) // ok
        )) { // violation
            System.out.println("sd"); // ok
        }
    }

    private void method() { // ok
        int x, y, z; // ok
        x = 0; // ok
        y = 0; // ok

        z = (x < y) ? x : y; // ok

        if ((x < y) && (x > z)) { // ok
            return; // ok
        }

        if (((x < y) && (x > z))) { // violation, unnecessary parenthesis
            return; // ok
        }

        if (!(x <= y) || (x >= z)) { // ok
            return; // ok
        }

        if ((x == y) || (x != z)) { // ok
            return; // ok
        }

        if (((x == y) || (x != z))) { // violation, unnecessary parenthesis
            return; // ok
        }

        if ((Integer.valueOf(x) instanceof Integer) // ok
                || Integer.valueOf(y) instanceof Integer) { // ok
            return; // ok
        }

    }
    private void check() { // ok
        String sectionName = "Some String"; // ok
        if ("Some content".equals(sectionName) || "Some overview".equals(sectionName) // ok
                || (!"AbbreviationAsWordInName".equals(sectionName) // violation
                // violation, unnecessary parenthesis in the line above
                && !"AbstractClassName".equals(sectionName) // ok
        )) {
            return; // ok
        }

        if (sectionName instanceof String && "Other Overview".equals(sectionName) // ok
                && (!"AbbreviationAsWordInName".equals(sectionName) // ok
                || !"AbstractClassName".equals(sectionName) // ok
        )) { // violation
            return; // ok
        }
    }


}

