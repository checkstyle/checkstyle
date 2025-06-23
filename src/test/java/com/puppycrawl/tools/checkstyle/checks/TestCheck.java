package com.puppycrawl.tools.checkstyle.checks;

import java.util.*;
import java.util.ArrayList;

public class TestCheck {

    public int counter = 100;

    public static int version = 2;

    public void run() {
        int unusedVar = 42;
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            System.out.println("i = " + i);
        }

        if (counter > 50) {
            if (counter < 200) {
                if (counter != 150) {
                    System.out.println("Deep nesting!");
                }
            }
        }

        System.out.println("Hello world");
        System.out.println("Hello world");

        if (counter > 0) {
            System.out.println("Counter positive");
        } else {
            System.out.println("Counter non-positive");
        }

        try {
            String s = null;
            s.length();
        } catch (Exception e) {
            // Ignored
        }

        boolean isOk = (counter > 0) ? true : false;
        System.out.println("isOk = " + isOk);
    } // <-- this was missing!

    public void emptyMethod() {}

    public void configure(int a, int b, int c, int d, int e, int f) {
        System.out.println("Configure with too many params");
    }

    public boolean alwaysTrue() {
        return true;
    }

    public void boxingExample() {
        Integer boxed = Integer.valueOf(123);
        System.out.println(boxed);
    }
}
