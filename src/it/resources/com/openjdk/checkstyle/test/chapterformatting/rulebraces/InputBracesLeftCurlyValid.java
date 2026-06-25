package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

/** Input file for Valid Left Curly: Valid */
public class InputBracesLeftCurlyValid {
    public void testMethod() {
        if (true) {
            System.out.println("Hello");
        }
        else {
            System.out.println("World");
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }

        int temp = 0;
        while (temp == 0) {
            System.out.println("Hello");
        }

        do {
            System.out.println("Hello");
        } while (true);
    }

    void testMethod2() {
        int a = 0;
        int b = 10;
        if (a == 0
                && b == 10) {
            System.out.println("Hello");
        }
    }
}
