package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Scanner;

/**
 * Test input for GitHub issue #3090.
 * https://github.com/checkstyle/checkstyle/issues/3090
 */
public class InputRightCurlyDoWhile {

    public void foo1() {
        do {
        } while (true);
    }

    public void foo2() {
        int i = 1;
        while (i < 5) {
            System.out.print(i + " ");
            i++;
        }
    }

    public void foo3() {
        int i = 1;
        do {
            i++;
            System.out.print(i + " ");
        } while (i < 5);
    }

    public void foo4() {
        int prog, user;
        prog = (int)(Math.random() * 10) + 1;
        Scanner input = new Scanner(System.in);
        if( input.hasNextInt() ) {
            do {
                user = input.nextInt();
                if(user == prog) {
                    System.out.println("Good!");
                } else {
                    if (user > 0 && user <= 10) {
                        System.out.print("Bad! ");
                        if( prog < user ) {
                            System.out.println("My number is less than yours.");
                        } else {
                            System.out.println("My number is greater than yours.");
                        }
                    } else {
                        System.out.println("Error!");
                    }
                }
            } while( user != prog );
        } else {
            System.out.println("Error!");
        }
        System.out.println("Goodbye!");
    }

    public void foo5() {
        do {
        } // violation
        while (true);
    }

    public void foo6() {
        do {} // violation
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

        } // violation

        while

        (true);
    }

    public void foo9() {
        do {} while (true);
    }
}

