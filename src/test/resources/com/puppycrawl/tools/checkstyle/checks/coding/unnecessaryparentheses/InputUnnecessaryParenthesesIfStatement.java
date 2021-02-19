package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

/* Config: default
 */

public class InputUnnecessaryParenthesesIfStatement {

    void method(String sectionName) {
        if ("Content".equals(sectionName) || "Overview".equals(sectionName)
                || (!"AbbreviationAsWordInName".equals(sectionName) // violation, unnecessary
                                                                    // parenthesis
                && !"AbstractClassName".equals(sectionName) // ok
        )) {
            System.out.println("sd");
        }
    }

    private void method() {
        int x, y, z;
        x = 0;
        y = 0;

        z = (x < y) ? x : y; // ok

        if ((x < y) && (x > z)) { // violation, unnecessary parenthesis
            return;
        }

        if (((x < y) && (x > z))) { // violation, unnecessary parenthesis
            return;
        }

        if (!(x <= y) || (x >= z)) { // violation, unnecessary parenthesis
            return;
        }

        if ((x == y) || (x != z)) { // violation, unnecessary parenthesis
            return;
        }

        if (((x == y) || (x != z))) { // violation, unnecessary parenthesis
            return;
        }

        if ((Integer.valueOf(x) instanceof Integer) // violation, unnecessary parenthesis
                || Integer.valueOf(y) instanceof Integer) { // ok
            return;
        }
        if (x == ((y<z) ? y : z) &&
            ((x>y && y>z) || (!(x<z) && y>z))) { // violation, unnecessary parenthesis
            return;
        }
        if((x >= 0 && y <= 9) || (z >= 5 && y <= 5) || (z >= 3 && x <= 7)) { // violation,
            return;                                            // unnecessary parenthesis
        }
    }
    private void check() {
        String sectionName = "Some String";
        if ("Some content".equals(sectionName) || "Some overview".equals(sectionName) // ok
                || (!"AbbreviationAsWordInName".equals(sectionName) // violation, unnecessary
                                                                    // parenthesis
                && !"AbstractClassName".equals(sectionName) // ok
        )) {
            return;
        }

        if (sectionName instanceof String && "Other Overview".equals(sectionName) // ok
                && (!"AbbreviationAsWordInName".equals(sectionName) // ok
                || !"AbstractClassName".equals(sectionName) // ok
        )) {
            return;
        }
    }


}

