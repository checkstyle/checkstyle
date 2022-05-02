/*
RightCurly
option = ALONE
tokens = CLASS_DEF, METHOD_DEF, LITERAL_IF, LITERAL_ELSE, LITERAL_DO, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestOptionAlone {

    private int a;
    private static int b;
    {  a = 2; } // violation ''}' at column 15 should be alone on a line'
    static { b = 3; } // violation ''}' at column 21 should be alone on a line'

    void method1() {
        Thread t = new Thread() {@Override public void run() {
            int a; int b;} // violation ''}' at column 26 should be alone on a line'
        };
    }

    void method2(java.util.HashSet<String> set) {
        java.util.Map<String, String> map1 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");} // violation ''}' at column 37 should be alone on a line'
        };

        java.util.Map<String, String> map2 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }}; // ok
    }

    void method3() {
        method2(new java.util.HashSet<String>() {{
            add("XZ13s");
            add("AB21/X");
            add("YYLEX");
            add("AR5E");
        }}); // violation ''}' at column 9 should be alone on a line'
    }

    int method4(int a) {
        if (a>2) a*=10; return ++a; } // violation ''}' at column 37 should be alone on a line'

    void method5(int a) {
        while (a > 5) { a--; } // violation ''}' at column 30 should be alone on a line'

        if (a > 4) { a++; } // violation ''}' at column 27 should be alone on a line'

        do {a--;} while (a > 3); // violation ''}' at column 17 should be alone on a line'
        // violation below ''}' at column 53 should be alone on a line'
        for (int i = 1; i < 10; i++) { byte b = 10; }

        if (a < 2) { --a; } else if (a > 3) { a++; } // 2 violations

        java.util.List<String> list = new java.util.ArrayList<>();
        list.stream()
                .filter(e -> {return !e.isEmpty() && !"null".equals(e);} )
                .collect(java.util.stream.Collectors.toList());
    }

    void method6(int a) {
        java.util.Map<String, String> map3 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
        }{}; // 2 violations
        };
    }

    public @interface TestAnnotation {} // violation ''}' at column 39 should be alone on a line'
    // violation below ''}' at column 56 should be alone on a line'
    public @interface TestAnnotation1{ String value(); }

    public @interface TestAnnotation2 {
        String value();} // violation ''}' at column 24 should be alone on a line'

    public @interface TestAnnotation3 {
        String value();
    }

    public @interface TestAnnottation4 { String value();
    }

    interface Interface1
    {
        int i = 1;
        public void meth1(); } // violation ''}' at column 30 should be alone on a line'

    interface Interface2
    { int i = 1; public void meth1(); } // violation ''}' at column 39 should be alone on a line'

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        }} // 2 violations
}
