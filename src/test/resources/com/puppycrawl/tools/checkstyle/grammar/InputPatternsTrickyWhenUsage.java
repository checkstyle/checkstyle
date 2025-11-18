package com.puppycrawl.tools.checkstyle.grammar;

public class InputPatternsTrickyWhenUsage {
    static String m1(Object when) {
        when = new Object();
        return switch (when) {
            case Integer i when i >= 0 -> i.toString();
            default -> "2";
        };
    }

    static void m2(){
        int when = Integer.parseInt("when");
    }

    static String m3(Object x) {
        return switch (x) {
            // this is horrible, but it compiles
            case Integer when when when >= 0 -> when.toString();
            default -> "2";
        };
    }

    @when static class A {
        static class when{}
    }

    static class B<when> {
        when getWhen() {
            return null;
        }
    }
}

@interface when{}
