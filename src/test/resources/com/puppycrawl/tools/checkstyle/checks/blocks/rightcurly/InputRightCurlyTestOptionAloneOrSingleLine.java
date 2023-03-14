/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = CLASS_DEF, METHOD_DEF, LITERAL_IF, LITERAL_ELSE, LITERAL_DO, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestOptionAloneOrSingleLine {

    private int a;
    private static int b;
    {  a = 2; } // ok
    static { b = 3; } // ok

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
        }};; // violation ''}' at column 9 should be alone on a line'
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
        while (a > 5) { a--; } // ok

        if (a > 4) { a++; } // ok

        do {a--;} while (a > 3); // ok

        for (int i = 1; i < 10; i++) { byte b = 10; } // ok

        if (a < 2) { --a; } else if (a > 3) { a++; } // ok

        java.util.List<String> list = new java.util.ArrayList<>();
        list.stream()
                .filter(e -> {return !e.isEmpty() && !"null".equals(e);} )
                .collect(java.util.stream.Collectors.toList());
    }

    class TestClass4 { }

    class TestClass5 { } { } // violation ''}' at column 24 should be alone on a line'

    interface Interface1
    {
        int i = 1;
        public void meth1(); } // violation ''}' at column 30 should be alone on a line'

    interface Interface2
    { int i = 1; public void meth1(); } // ok

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        }} // 2 violations
}
