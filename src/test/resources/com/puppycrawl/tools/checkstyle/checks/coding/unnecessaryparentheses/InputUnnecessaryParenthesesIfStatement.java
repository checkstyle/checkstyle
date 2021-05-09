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

        if ((x < y)           // violation, unnecessary parenthesis
                && (x > z)) { // violation, unnecessary parenthesis
            return;
        }

        if (((x < y)           // violation, unnecessary parenthesis
                && (x > z))) { // violation, unnecessary parenthesis
            return;
        }

        if (!(x <= y)          // ok
                || (x >= z)) { // violation, unnecessary parenthesis
            return;
        }

        if ((x == y)           // violation, unnecessary parenthesis
                || (x != z)) { // violation, unnecessary parenthesis
            return;
        }

        if ((                       // violation, unnecessary parenthesis
                (x == y)            // violation, unnecessary parenthesis
                        || (x != z) // violation, unnecessary parenthesis
        )) {
            return;
        }

        if ((Integer.valueOf(x) instanceof Integer) // violation, unnecessary parenthesis
                || Integer.valueOf(y) instanceof Integer) { // ok
            return;
        }
        if (x == ((y<z) ? y : z) &&
            ((x>y && y>z)                  // violation, unnecessary parenthesis before 'x>y'
                    || (!(x<z) && y>z))) { // violation, unnecessary parenthesis before '!'
            return;
        }
        if ((x >= 0 && y <= 9)            // violation, unnecessary parenthesis
                 || (z >= 5 && y <= 5)    // violation, unnecessary parenthesis
                 || (z >= 3 && x <= 7)) { // violation, unnecessary parenthesis
            return;
        }
        if(x>= 0 && (x<=8 || y<=11) && y>=8) { // ok
            return;
        }
        if((y>=11 && x<=5)            // violation, unnecessary parenthesis
                || (x<=12 && y>=8)) { // violation, unnecessary parenthesis
            return;
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
    private void UnaryAndPostfix() {
        boolean x = true;
        boolean y = true;
        int a = 25;
        if ((++a) >= 54 && x) { // violation, unnecessary parenthesis around '++a'
            return;
        }
        if ((~a) > -27            // violation, unnecessary parenthesis around '~a'
                 && (a-- < 30)) { // violation, unnecessary parenthesis
            return;
        }
        if ((-a) != -27 // violation, unnecessary parenthesis around '-a'
                 && x) {
            return;
        }
    }

    public void checkBooleanStatements() {
        boolean a = true;
        int b = 42;
        int c = 42;
        int d = 32;
        if ((b == c) == a
                && (( // violation, unnecessary parenthesis, the latter one
                                (b==c)==(d>=b)==a!=(c==d))
                || (b<=c)!=a==(c>=d))) {
            return;
        }

        if (( // violation, unnecessary parenthesis
                a!=(b==c) && (a // violation, unnecessary parenthesis, before 'a'
                        && (b==c))) // violation, unnecessary parenthesis, before 'b'
                 || (a || a!=(b<=c))     // ok
                 || (a==(b!=d==(c==b) && a!=(b<=c)))) { // violation, unnecessary parenthesis,
                                                        // after '||'
            return;
        }

        if (a==(b>=c && a==(c==d && d!=b)) // ok
                && a==(c<=d)) { // ok
           return;
        }

        if (a && a==(b<=c)==(a
                && (b<=c))) { // violation, unnecessary parenthesis, before 'b'
            return;
        }

        if (a==(b==c) // ok
                || a!=(b<=c)) { // ok
            return;
        }

        if ((b==0) == (c==d) // ok
                && (Integer.valueOf(d) instanceof Integer) == true) { // ok
            return;
        }
    }


}

