package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

// violation first line 'Header mismatch'

/** Input file for Left Curly check - Invalid. */
public class InputBracesLeftCurlyInValid {

    public void testMethod()
    { // violation, ''{' at column 5 should be on the previous line.'

        if (true)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        } else
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("World");
        }

        for (int i = 0; i < 10; i++)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println(i);
        }

        int temp = 0;
        while (temp == 0)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        }

        do
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        } while (true);
    }

    void testMethod2() {
        int a = 0;
        int b = 10;
        if (a == 0
                && b == 10)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        }
    }

    void testMethod3() {
        try {
            int temp = 0;
        } // violation, 'should be on the same line'
        catch (Exception e) {
        }
        finally {
            System.out.println("hello");
        }

        try {
            System.out.println("hello");
        } // violation, 'should be on the same line'
        catch (RuntimeException ex) {

        } // violation, 'should be on the same line'
        catch (Exception ex) {

        }

        if (true) {
        } // violation, 'should be on the same line'
        else {
        }

        int a = 0;

        if (a == 0) {

        }
        else if (a == 10) {

        }
        else if (a == 1) {

        } // violation, 'should be on the same line'
        else {

        }

        int i = 0;

        do {
            System.out.println("hello");
        } // violation, 'should be on the same line'
        while (i > 0);
    }
}
