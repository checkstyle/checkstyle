package com.puppycrawl.tools.checkstyle;

public class InputRightCurlyAloneOrSingleline {

    public boolean equals(Object other) { boolean flag = true; return flag; } 

    public int hashCode()
    { 
        int a = 10;
        return 1;
    }

    private void foo()
    { int var1 = 5; var2 = 6; } 

    private void foo1() { return; } 

    private String foo2() { return toString();
    } 

    private void foo3() { ; return; } 

    private int var1;
    private int var2;
    public InputRightCurlyAloneOrSingleline() { this.var1 = 1; } 
    public InputRightCurlyAloneOrSingleline(int var1, int var2) { this.var1 = var1; this.var2 = var2; } 

    private void foo4() { ;; } 

    private void foo5() { ; } 

    private void foo6() {  } 

    private void foo12() {
        try { int i = 5; int b = 10; } 
        catch (Exception e) { } 
    } 

    private void foo13() {
        for (int i = 0; i < 10; i++) { int a = 5; int b = 6; } 

        do
        {
            var1 = 2;
        } 
        while (var2 == 2);
    } 

    static { int a; int b; } 

    { int c; int d;} 

    private void foo14() {
        if (var1 > 0) {
            return;
        } 
    } 

    private void foo15() {
        class A { int a; } var1++; //violation
        class B {  } 
        if(true) {

        } 
        else;
    }

    private void foo16() {
        if (true) { return; } else { } //violation
        if (false) {
        }
    }

    private void foo17() { int var1 = 5; var2 = 6; } private void foo18() {int var1 = 5; var2 = 6; } //violation

    private void foo19() {int var1 = 5;
        var2 = 6;} //violation

    private String foo20() {
        do { var2 ++; }  
        while (var2 < 15);

        while (var1 < 10) { var1++; } 

        do { var2++; var1++; } while (var2 < 15); return ""+0xCAFEBABE; //violation
    }

    private void foo21() {
        new Object() { @Override protected void finalize() { "".toString(); }}; 
    } 

    void foo22() {
        long startTime = System.nanoTime();
        try {
            int a = 5;
            toString();
        } catch (Exception e) { //violation
            throw new RuntimeException(e);
        } finally { toString(); } //violation
    }

    void doDoubleBraceInitialization() {
        java.util.Map<String, String> map = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }}; // it's ok

        Thread t = new Thread() {@Override public void run() {super.run();}};
        new Object() { @Override protected void finalize() { "".toString(); }  { int a = 5; }};
        new Object() { @Override protected void finalize() { "".toString(); }  int b = 10; };
        new Object() { @Override protected void finalize() { "".toString(); }  { int c = 5; } int d = 8; };

        java.util.Map<String, String> map2 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");}  //violation
        };

        java.util.Map<String, String> map3 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");}}; //violation

        java.util.Map<String, String> map4 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }
        };

        foo23(new java.util.HashSet<String>() {{
            add("XZ13s");
            add("AB21/X");
            add("YYLEX");
            add("AR5E");
        }});  //it's ok, can't be formatted better

        foo23(new java.util.HashSet<String>() {{
            add("XZ13s");
            add("AB21/X");
            add("YYLEX");
            add("AR5E");
        }});} //violation


    void foo23(java.util.HashSet<String> set) {
    }

    void foo25() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello, world!");
        }} //violation

    void foo26() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello, world!");}} //violation

    void foo27() {
        for (int i = 0; i < 10; i++) {for (int j = 0; j < 15; j++) {int a;}}} //violation

    private java.util.ArrayList foo28(int delta) {
        return new java.util.ArrayList() {
            @Override public int size() { return Math.max(0, super.size() + 1);};
        };
    }

    private void foo29() {
        boolean flag = true;
        if (flag) {
            System.out.println("heh");
            flag = !flag; } System.err. //violation
            println("Xe-xe");
    }
}
