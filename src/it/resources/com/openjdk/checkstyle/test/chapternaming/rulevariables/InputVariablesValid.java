package com.openjdk.checkstyle.test.chapternaming.rulevariables;

// violation first line 'Header mismatch'

import java.util.stream.Stream;

public class InputVariablesValid {

    static int itStatic1 = 2;
    protected static int itStatic2 = 2;
    private static int itStatic3 = 2;
    static int itStatic4 = 2;
    static int itStatic5 = 2;
    private static int itStatic6 = 2;

    public int num1;
    protected int num2;
    int num3;
    private int num4;

    public void method1(int var) {

        try {
            int temp;
            String str;
        } catch (Exception ex) {
            System.out.println("Hello");
        }
    }

    public void method2() {
        final String var = "hello";
        final int myVar = 42;
    }

    public boolean myMethod(String sentence) {
        return Stream.of(sentence.split(" "))
               .map(word -> word.trim())
               .anyMatch(words -> "in".equals(words));
    }

    void foo(Object o1) {
        if (o1 instanceof String string) { }
        if (o1 instanceof Integer num) { }
        if (o1 instanceof Integer nums) { }
    }

    record Rec2(String values) {}

}
