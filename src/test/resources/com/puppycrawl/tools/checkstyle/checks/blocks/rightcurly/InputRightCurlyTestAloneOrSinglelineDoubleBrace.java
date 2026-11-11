/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestAloneOrSinglelineDoubleBrace {

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
        }};
        // violation below ''}' at column 75 should be alone on a line'
        Thread t = new Thread() {@Override public void run() {super.run();}};
        // 2 violations 3 lines below:
        //  ''}' at column 77 should be alone on a line.'
        //  ''}' at column 93 should be alone on a line.'
        new Object() { @Override protected void finalize() { "".toString(); }  { int a = 5; }};
        // violation below ''}' at column 77 should be alone on a line'
        new Object() { @Override protected void finalize() { "".toString(); }  int b = 10; };
        // 2 violations 3 lines below:
        //  ''}' at column 64 should be alone on a line.'
        //  ''}' at column 80 should be alone on a line.'
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
        }});}
    // 2 violations above:
    // ''}' at column 9 should be alone on a line.'
    // ''}' at column 13 should be alone on a line.'


    void foo23(java.util.HashSet<String> set) {
    }
}
