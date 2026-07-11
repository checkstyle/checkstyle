package com.openjdk.checkstyle.test.chapterformatting.rulebraces;

// violation first line 'Header mismatch*'

/** Input file for Valid Left Curly: Valid. */
public class InputBracesLeftCurlyValid {
    public void testMethod() {
        if (true) {
            System.out.println("Hello");
        } else {
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

    void testMethod3() {
        try {
            int temp = 0;
        } catch (Exception e) {

        }
        finally {
            System.out.println("hello");
        }

        try {

        }
        finally {
            System.out.println("hello");
        }

        int a = 0;

        if (a == 0) {

        }
        else if (a == 10) {

        } else {

        }

        int[][] matrix = new int[10][10];
        for (int[] row : matrix) {
            int sum = 0;
            for (int val : row) {
                sum += val;
            }
        }

        int i = 0;

        do {
            System.out.println("hello");
        } while (i > 0);
    }
}
