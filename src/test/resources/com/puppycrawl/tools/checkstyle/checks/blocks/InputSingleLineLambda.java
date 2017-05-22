package com.puppycrawl.tools.checkstyle.checks.blocks;

public class InputSingleLineLambda {
    
    static Runnable r1 = ()->String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");
    static Runnable r2 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r3 = () ->
        String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r4 = () -> {String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");};
    static Runnable r5 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello " +
            "world!");
    static Runnable r6 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello " + "world!");
    static Runnable r7 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello "
            + "world!");
    static MathOperation addition = (int a, int b) ->
           a + b;
    static MathOperation subtraction = (a, b) -> a - b;
    interface MathOperation {
        int operation(int a, int b);
    }
}
=======
    static Runnable r5 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello " +
            "world!");
    static Runnable r6 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello " + "world!");
    static Runnable r7 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello "
            + "world!");
    static MathOperation addition = (int a, int b) ->
           a + b;
    static MathOperation subtraction = (a, b) -> a - b;
    interface MathOperation {
        int operation(int a, int b);
    }
}
>>>>>>> master
