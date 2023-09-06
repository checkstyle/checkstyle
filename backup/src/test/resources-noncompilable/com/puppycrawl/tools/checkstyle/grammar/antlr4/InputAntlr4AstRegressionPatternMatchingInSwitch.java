//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternMatchingInSwitch {
    public static void main(String[] args) {
        Object value = 42;
        String result = switch (value) {
            case null -> "It's null...";
            case String s -> "It's a string: " + s;
            case Integer i && i > 50 -> "It's an integer: " + i.toString();
            case Object v -> "It's something else: " + v.toString();
        };
        System.out.println(result);
    }

    record Point(int i, int j) {}
    enum Color { RED, GREEN, BLUE }
    static void typeTester(Object o) {
        switch (o) {
            case null     -> System.out.println("null");
            case String s -> System.out.println("String");
            case Color c  -> System.out.println("Color with " + Color.values().length + " values");
            case Point p  -> System.out.println("Record class: " + p.toString());
            case int[] ia -> System.out.println("Array of ints of length" + ia.length);
            default       -> System.out.println("Something else");
        }
    }
    static void error(Object o) {
        switch(o) {
            case String s -> // dominates below case
                System.out.println("A string: " + s);
            case CharSequence cs ->
                System.out.println("A sequence of length " + cs.length());
            default -> {
                break;
            }
        }
    }
    static void m1(Object o) {
        switch (o) {
            case String s && s.length() > 4:
                System.out.println(s);
                break;
            case Integer i && i > 40:
                System.out.println("Integer");
                break;
            default:
                break;
        }
    }
    static void m2(Object o) {
        switch (o) {
            case String s && s.length() > 4 -> System.out.println(s);
            case Integer i && i > 40 -> System.out.println("Integer");
            default -> {
            }
        }
    }
    static void test1(Object o) {
        switch (o) {
            case Character c -> {
                if (c == 7) {
                    System.out.println("Ding!");
                }
                System.out.println("Character");
            }
            case Integer i && (i > 2) ->
                throw new IllegalStateException("Invalid Integer argument of value " + i);
            default -> {
                break;
            }
        }
    }
    static void test2(Object o) {
        switch (o) {
            case Character c:
                if (c.charValue() == 7) {
                    System.out.print("Ding ");
                }
                if (c.charValue() == 9) {
                    System.out.print("Tab ");
                }
                System.out.println("character");
            default:
                System.out.println();
        }
    }
    static void test3(Object o) {
        switch(o) {
            case null: case String s:
                System.out.println("String, including null");
                break;
            default:
        }
    }
    static void test4(Object o) {
        switch (o) {
            case null, String s -> System.out.println("String, including null");
            default -> System.out.println("something else");
        }
        switch(o) {
            case null: default:
                System.out.println("The rest (including null)");
        }
        switch(o) {
            case null: default:
                System.out.println("The rest (including null)");
        }
        switch(o) {
            case null, default ->
                System.out.println("The rest (including null)");
        }
        // example of parenthesized pattern in instanceof
        if (o instanceof (String s && s.length() > 1)) {
        }
    }
}
