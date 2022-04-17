/*
RightCurly
option = ALONE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestWithAnnotations
{

    @Deprecated
    @Override
    public boolean equals(Object other) { boolean flag = true; return flag; }
    // violation above ''}' at column 77 should be alone on a line'
    @Override
    public String toString() { String s = "toString"; return s; }
    // violation above ''}' at column 65 should be alone on a line'
    @Override
    @SuppressWarnings("unused")
    public int hashCode()
    {
        int a = 10;
        return 1;
    }

    @SuppressWarnings("unused")
    private void foo2() { int a = 9; return; }
    // violation above ''}' at column 46 should be alone on a line'
    @SuppressWarnings("unused")
    private void foo3()
    { int var1 = 5; var2 = 6; } // violation ''}' at column 31 should be alone on a line'

    @Deprecated
    private void foo4() { return; } // violation ''}' at column 35 should be alone on a line'

    @SuppressWarnings("unused")
    private int foo5() { return 1; } // violation ''}' at column 36 should be alone on a line'

    @SuppressWarnings("unused")
    private String foo6() { return toString();
    }
    // violation below ''}' at column 73 should be alone on a line'
    private String foo7() { String s = toString(); return s.toString(); }

    private void foo8() { ; return; } // violation ''}' at column 37 should be alone on a line'

    private int var1; private int v1;
    private int var2; private int v2;
    @SuppressWarnings("unused")
    public InputRightCurlyTestWithAnnotations() { this.var1 = 1; }
    // violation above ''}' at column 66 should be alone on a line'
    @SuppressWarnings("unused")
    public InputRightCurlyTestWithAnnotations(int v1, int v2) {this.var1 = v1; this.var2 = v2; }
    // violation above ''}' at column 96 should be alone on a line'
    @SuppressWarnings("unused")
    private void foo9() { ;; } // violation ''}' at column 30 should be alone on a line'

    @SuppressWarnings("unused")
    private void foo10() { ; } // violation ''}' at column 30 should be alone on a line'

    @SuppressWarnings("unused")
    private void foo11() {  } // violation ''}' at column 29 should be alone on a line'

    @SuppressWarnings("unused")
    private void foo12() {
        try { int i = 5; int b = 10; } // violation ''}' at column 38 should be alone on a line'
        catch (Exception e) { } // violation ''}' at column 31 should be alone on a line'
    }

    @Deprecated
    @SuppressWarnings("unused")
    private void foo13() {
        for (int i = 0; i < 10; i++) { int a = 5; int b = 6; }
        // violation above ''}' at column 62 should be alone on a line'
        do
        {
            var1 = 2;
        }
        while (var2 == 2);
    }

    static { int a; int b; } // violation ''}' at column 28 should be alone on a line'

    static { int a; } // violation ''}' at column 21 should be alone on a line'

    { int c; int d;} // violation ''}' at column 20 should be alone on a line'

    { int c; } // violation ''}' at column 14 should be alone on a line'

    @Deprecated
    private void foo14() {
        if (var1 > 0) {
            return;
        }
    }

    @Deprecated
    private void foo15() {
        class A { int a; } var1++; // violation ''}' at column 26 should be alone on a line'
        class B {  } // violation ''}' at column 20 should be alone on a line'
        if(true) {

        }
        else;
    }

    @Deprecated
    private void foo16() {
        if (true) { return; } else { } // 2 violations
        if (false) {
        }

        if (true) { return; } else { } // 2 violations
    }

    @Deprecated
    void foo17() { int v1 = 5; v2 = 6; } @Deprecated void foo18() {int v1 = 5; v2 = 6; }
    // 2 violations above
    private void foo19() {int var1 = 5;
        var2 = 6;} // violation ''}' at column 18 should be alone on a line'

    @SuppressWarnings("Hello, world!")
    private String foo20() {
        do { var2 ++; } // violation ''}' at column 23 should be alone on a line'
        while (var2 < 15);

        while (var1 < 10) { var1++; } // violation ''}' at column 37 should be alone on a line'
        // violation below ''}' at column 30 should be alone on a line'
        do { var2++; var1++; } while (var2 < 15); return ""+0xCAFEBABE;
    }

    private void foo21() { // violation below ''}' at column 77 should be alone on a line'
        new Object() { @Override protected void finalize() { "".toString(); }};
    }

    @SuppressWarnings("All")
    void foo22() {
        long startTime = System.nanoTime();
        try {
            int a = 5;
            toString();
        } catch (Exception e) { // violation ''}' at column 9 should be alone on a line'
            throw new RuntimeException(e);
        } finally { toString(); } // 2 violations
    }

    @SuppressWarnings("")
    void doDoubleBraceInitialization() {
        java.util.Map<String, String> map = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }}; //NO violation
        // violation below ''}' at col.* 75 should be alone on a line'
        Thread t = new Thread() {@Override public void run() {super.run();}};
        new Object() { public int hashCode() { return 1; }  { int a = 5; }}; // 2 violations
        new Object() { public int hashCode() { return 1; }  int b = 10; };
        // violation above ''}' at column 58 should be alone on a line'
        new Object() { public int hashCode() { return 1; } { int c = 5; } int d = 8; };
        // 2 violations above
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
            put("alpha", "betical");}};  // violation ''}' at column 37 should be alone on a line'

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
        }}); // violation ''}' at column 9 should be alone on a line'

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
        }; // violation above ''}' at column 76 should be alone on a line'
    }

    private void foo29() {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } String. // violation ''}' at column 27 should be alone on a line'
                CASE_INSENSITIVE_ORDER.equals("Xe-xe");
    }

    public void testMethod() {}; // violation ''}' at column 31 should be alone on a line'

    public void testMethod1() {
    }; // violation ''}' at column 5 should be alone on a line'

    public class TestClass {}; // violation ''}' at column 29 should be alone on a line'

    public class TestClass1 {
    }; // violation ''}' at column 5 should be alone on a line'

    public class TestClass2 {
        public TestClass2() {}; // violation ''}' at column 30 should be alone on a line'

        public TestClass2(String someValue) {
        }; // violation ''}' at column 9 should be alone on a line'
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

    public @interface TestAnnnotation5 {
        String someValue(); }; // violation ''}' at column 29 should be alone on a line'

    public @interface TestAnnotation6 {}; // violation ''}' at column 40 should be alone on a line'

    public @interface TestAnnotation7 {
        String someValue();
    }; // violation ''}' at column 5 should be alone on a line'

    public @interface TestAnnotation8 { String someValue();
    }; // violation ''}' at column 5 should be alone on a line'

    public @interface TestAnnotation9 { String someValue(); };
    // violation above ''}' at column 61 should be alone on a line'
}
