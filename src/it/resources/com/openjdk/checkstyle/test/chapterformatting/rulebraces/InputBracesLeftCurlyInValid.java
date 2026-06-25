package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

/** Input file for Left Curly check - Invalid */
public class InputBracesLeftCurlyInValid {

    public void testMethod()
    { // violation, ''{' at column 5 should be on the previous line.'
        if (true)
        { // violation, ''{' at column 9 should be on the previous line.'
            System.out.println("Hello");
        }
        else
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
}
