package com.openjdk.checkstyle.test.chapternaming.rulevariables;

// violation first line 'Header mismatch'

import java.util.stream.Stream;

public class InputVariablesInvalid {

    static int ItStatic1 = 2; // violation, 'must match pattern'
    protected static int ItStatic2 = 2; // violation, 'must match pattern'
    private static int ItStatic = 2; // violation, 'must match pattern'
    static int it_static = 2; // violation, 'must match pattern'
    static int It_Static = 2; // violation, 'must match pattern'
    private static int It_Static1 = 2; // violation, 'must match pattern'

    public int NUM1; // violation 'Name 'NUM1' must match pattern'
    protected int NUM2; // violation 'Name 'NUM2' must match pattern'
    int NUM3; // violation 'Name 'NUM3' must match pattern'
    private int NUM4; // violation 'Name 'NUM4' must match pattern'

    public void method1(int Var) { // violation 'Name 'Var' must match pattern'

        try {
            int Temp; // violation 'Name 'Temp' must match pattern'
            String Str; // violation 'Name 'Str' must match pattern'
        } catch (Exception Exr) { // violation 'Name 'Exr' must match pattern'
            System.out.println("Hello");
        }
    }

    public void method2() {
        final String Var = "hello"; // violation 'Name 'Var' must match pattern'
        final int MyVar = 42; // violation 'Name 'MyVar' must match pattern'
    }

    public boolean myMethod(String sentence) {
        return Stream.of(sentence.split(" "))
               .map(word -> word.trim())
               .anyMatch(Word -> "in".equals(Word));
        // violation above 'Name 'Word' must match pattern'
    }

    void foo(Object o1) {
        if (o1 instanceof String STRING) { }
        // violation above, 'Name 'STRING' must match pattern*.'
        if (o1 instanceof Integer num) { }
        if (o1 instanceof Integer num_1) { }
        // violation above, 'Name 'num_1' must match pattern*.'
    }

    record Rec2(String Values) {} // violation, Name must match '^[a-z][a-zA-Z0-9]*$'

}
