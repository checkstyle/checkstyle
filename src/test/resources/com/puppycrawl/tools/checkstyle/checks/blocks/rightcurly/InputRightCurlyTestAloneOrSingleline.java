/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestAloneOrSingleline {

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
    public InputRightCurlyTestAloneOrSingleline() { this.var1 = 1; }
    public InputRightCurlyTestAloneOrSingleline(int v1, int v2) { this.var1 = v1; this.var2 = v2; }

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
        class A { int a; } var1++; // violation ''}' at column 26 should be alone on a line'
        class B {  }
        if(true) {

        }
        else;
    }

    private void foo16() {
        if (true) { return; } else { }
        if (false) {
        }
    }
    // violation below ''}' at column 42 should be alone on a line'
    void f17() { int var1 = 5; var2 = 6; } private void f18() {int var1 = 5; var2 = 6; }

    private void foo19() {int var1 = 5;
        var2 = 6;} // violation ''}' at column 18 should be alone on a line'

    private String foo20() {
        do { var2 ++; }
        while (var2 < 15);

        while (var1 < 10) { var1++; }
        // violation below ''}' at column 30 should be alone on a line'
        do { var2++; var1++; } while (var2 < 15); return ""+0xCAFEBABE;
    }

    private void foo21() { // violation below ''}' at column 77 should be alone on a line'
        new Object() { @Override protected void finalize() { "".toString(); }};
    }

    void foo22() {
        long startTime = System.nanoTime();
        try {
            int a = 5;
            toString();
        } catch (Exception e) { // violation ''}' at column 9 should be alone on a line'
            throw new RuntimeException(e);
        } finally { toString(); } // violation ''}' at column 9 should be alone on a line'
    }

    void doDoubleBraceInitialization() {
        java.util.Map<String, String> map = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }}; //NO violation
        // violation below ''}' at column 75 should be alone on a line'
        Thread t = new Thread() {@Override public void run() {super.run();}};
        // 2 violations below
        new Object() { @Override protected void finalize() { "".toString(); }  { int a = 5; }};
        // violation below ''}' at column 77 should be alone on a line'
        new Object() { @Override protected void finalize() { "".toString(); }  int b = 10; };
        // 2 violations below
        new Object() { protected void finalize() { hashCode(); }  { int c = 5; } int d = 8; };

        java.util.Map<String, String> map2 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");}  // violation ''}' at column 37 should be alone on a line'
        };

        java.util.Map<String, String> map3 = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");}}; // violation ''}' at column 37 should be alone on a line'

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
        }});  // violation ''}' at column 9 should be alone on a line'

        foo23(new java.util.HashSet<String>() {{
            add("XZ13s");
            add("AB21/X");
            add("YYLEX");
            add("AR5E");
        }});} // 2 violations


    void foo23(java.util.HashSet<String> set) {
    }

    void foo25() {
        for (int i = 0; i < 10; i++) {
            System.identityHashCode("Hello, world!");
        }} // 2 violations

    void foo26() {
        for (int i = 0; i < 10; i++) {
            System.identityHashCode("Hello, world!");}} // 2 violations

    void foo27() {
        for (int i = 0; i < 10; i++) {for (int j = 0; j < 15; j++) {int a;}}} // 3 violations

    private java.util.ArrayList<Integer> foo28(int delta) {
        return new java.util.ArrayList<Integer>() {
            @Override public int size() { return Math.max(0, super.size() + 1);};
        };
    }

    private void foo29() {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } String. // violation ''}' at column 27 should be alone on a line'
                CASE_INSENSITIVE_ORDER.equals("Xe-xe");
    }

    void foo30() {
        if (true) {
            getClass();} // violation ''}' at column 24 should be alone on a line'

        for (int i = 0; i == 0; i++) {
            getClass();} // violation ''}' at column 24 should be alone on a line'

        while (true) {
            getClass();} // violation ''}' at column 24 should be alone on a line'
    }

    public void emptyBlocks() {
        try {
            // comment
        } catch (RuntimeException e) { // violation ''}' at column 9 should be alone on a line'
            new Object();
        } catch (Exception e) { // violation ''}' at column 9 should be alone on a line'
            // comment
        } catch (Throwable e) { // violation ''}' at column 9 should be alone on a line'
        } finally { // violation ''}' at column 9 should be alone on a line'
            // comment
        }

        do {
        } while (true); // violation ''}' at column 9 should be alone on a line'
    }

    public void codeAfterLastRightCurly() {
        while (new Object().equals(new Object())) {
        }; // violation ''}' at column 9 should be alone on a line'
        for (int i = 0; i < 1; i++) { }; // violation ''}' at column 39 should be alone on a line'
    }

    public @interface TestAnnotation {}

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
    { int i = 1; public void meth1(); }
}
