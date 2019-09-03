package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyAlone {

    private int a;
    private static int b;
    {  a = 2; }  //violation
    static { b = 3; }  //violation

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
        }}; //NO violation
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
        while (a > 5) { a--; }  //violation

        if (a > 4) { a++; }  //violation

        do {a--;} while (a > 3);  //violation

        for (int i = 1; i < 10; i++) { byte b = 10; }  //violation

        if (a < 2) { --a; } else if (a > 3) { a++; } //2 violations

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
        }{}; // violation
        };
    }

    public @interface TestAnnotation {} //violation

    public @interface TestAnnotation1{ String value(); } //violation

    public @interface TestAnnotation2 {
        String value();} //violation

    public @interface TestAnnotation3 {
        String value();
    }

    public @interface TestAnnottation4 { String value();
    }
}
