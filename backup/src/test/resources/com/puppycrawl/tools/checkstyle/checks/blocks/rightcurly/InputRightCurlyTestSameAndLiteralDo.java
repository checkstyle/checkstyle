/*
RightCurly
option = (default)SAME
tokens = LITERAL_DO


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.util.Scanner;

/*
 * Test input for GitHub issue #3090.
 * https://github.com/checkstyle/checkstyle/issues/3090
 */
public class InputRightCurlyTestSameAndLiteralDo {

    public void foo1() {
        do {
        } while (true);
    }

    public void foo2() {
        int i = 1;
        while (i < 5) {
            String.CASE_INSENSITIVE_ORDER.equals(i + " ");
            i++;
        }
    }

    public void foo3() {
        int i = 1;
        do {
            i++;
            String.CASE_INSENSITIVE_ORDER.equals(i + " ");
        } while (i < 5);
    }

    public void foo4() {
        int prog, user;
        prog = (int)(Math.random() * 10) + 1;
        Scanner input = new Scanner(System.in, "utf-8");
        if( input.hasNextInt() ) {
            do {
                user = input.nextInt();
                if(user == prog) {
                    String.CASE_INSENSITIVE_ORDER.equals("Good!");
                } else {
                    if (user > 0 && user <= 10) {
                        String.CASE_INSENSITIVE_ORDER.equals("Bad! ");
                        if( prog < user ) {
                            String.CASE_INSENSITIVE_ORDER.equals("My number is less than yours.");
                        } else {
                            String.CASE_INSENSITIVE_ORDER.equals("My number is greater than yours");
                        }
                    } else {
                        String.CASE_INSENSITIVE_ORDER.equals("Error!");
                    }
                }
            } while( user != prog );
        } else {
            String.CASE_INSENSITIVE_ORDER.equals("Error!");
        }
        String.CASE_INSENSITIVE_ORDER.equals("Goodbye!");
    }

    public void foo5() {
        do {
        } // violation ''}' at column 9 should be on the same line as .*/while'
        while (true);
    }

    public void foo6() {
        do {} // violation ''}' at column 13 should be on the same line as .*/while'
        while (true);
    }

    public void foo7() {
        do
        {

        } while (true);
    }

    public void foo8() {
        do

        {

        } // violation ''}' at column 9 should be on the same line as .*/while'

        while

        (true);
    }

    public void foo9() {
        do {} while (true);
    }

    public void foo10() {
        do ; while (true);
    }
}

