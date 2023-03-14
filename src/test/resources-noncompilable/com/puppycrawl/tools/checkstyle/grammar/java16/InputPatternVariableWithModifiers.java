//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.grammar.java16;

public class InputPatternVariableWithModifiers {
    static void method1(Object args) {
        Object o = args;
        if (o instanceof final String[] s){
        }
        else if (o instanceof final Integer[] i) {
        }
        else if (o instanceof final Character[] c){
        }
        else if (o instanceof final Double[] d){
        }
    }

    static void method2(Object args) {
        Object o = args;
        if (o instanceof String[] s) {
            s = new String[]{"modified"};
            System.out.println(s[0]);
        }
    }
}
