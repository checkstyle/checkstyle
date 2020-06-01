package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyAloneOrSingleLine2 {

    private int a;
    private static int b;
    {  a = 2; }
    static { b = 3; }

    void method1() {
        Thread t = new Thread() {@Override public void run() {
            int a; int b;} //violation
        };
    }

    void method2(java.util.HashSet<String> set) {
        java.util.Map<String, String> map1 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");}  //violation
        };

        java.util.Map<String, String> map2 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }};; //violation
    }

    void method3() {
        method2(new java.util.HashSet<String>() {{
            add("XZ13s");
            add("AB21/X");
            add("YYLEX");
            add("AR5E");
        }});  //violation
    }

    int method4(int a) {
        if (a>2) a*=10; return ++a; }   //violation

    void method5(int a) {
        while (a > 5) { a--; }

        if (a > 4) { a++; }

        do {a--;} while (a > 3);  //NO violation

        for (int i = 1; i < 10; i++) { byte b = 10; }

        if (a < 2) { --a; } else if (a > 3) { a++; }

        java.util.List<String> list = new java.util.ArrayList<>();
        list.stream()
                .filter(e -> {return !e.isEmpty() && !"null".equals(e);} )
                .collect(java.util.stream.Collectors.toList());
    }

    class TestClass4 { }

    class TestClass5 { } { } //violation

    interface Interface1
    {
        int i = 1;
        public void meth1(); } // violation

    interface Interface2
    { int i = 1; public void meth1(); }

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        }} // violation - for both of the right curly braces
}
