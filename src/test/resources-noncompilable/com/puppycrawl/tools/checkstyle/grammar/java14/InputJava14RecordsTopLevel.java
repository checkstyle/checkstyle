//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

public record InputJava14RecordsTopLevel(Integer i, String s, Character c) {

    public static final int I = 42;

    public InputJava14RecordsTopLevel {
        System.out.println(s);
    }

    protected InputJava14RecordsTopLevel(Integer i) {
        this(i, "string", 'c');
    }

    private InputJava14RecordsTopLevel(String s) {
        this(I);
    }

    InputJava14RecordsTopLevel(Character c) {
        this("my string");
    }

    public InputJava14RecordsTopLevel(Integer i, Character c) {
        this(I, "my string", 'g');
    }

    InputJava14RecordsTopLevel(Character c, String s) {
        this(I, c);
        System.out.println(s);
    }

    public static void main(String... args) {
        System.out.println("Top level record here!");
    }

}
