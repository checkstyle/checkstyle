package com.openjdk.checkstyle.test.chapterformatting.rulehorizontalwhitespace;

// violation first line 'Header mismatch'

/** Valid input class for whitespace. */
public class InputHorizontalWhiteSpaceValid {
    int y = 0;
    int a = 4;

    /** Test Method. */
    void example() {
        int b = 0;
        switch (b) {
            case 1: {
                System.out.println("hello");
                break;
            }
            default: {
                break;
            }
        }

        Runnable noop = () -> {};

        try {
        } catch (Exception e) {}

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        for (char item : vowels) {}

        for (int i = 100; i > 10; i--) {}

        if (a < b) {
            System.out.println("True");
        }

        int w = 4;
    }

    /** Test record. */
    record MyRecord3() {
        void method() {
            final int a = 1;
            int b = 1;
            b = 1;
        }
    }

    /** Test Method. */
    void method() {
        outerLoop:
        for (int i = 0; i < 10; i++) {
            int j = 0;
            while (j == 0) {
                break outerLoop;
            }
            j++;
        }
    }
}
