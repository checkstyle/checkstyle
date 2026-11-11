/*
RightCurly
option = \tALONE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestWithAnnotationsDoubleBrace
{
    @SuppressWarnings("")
    void doDoubleBraceInitialization() {
        java.util.Map<String, String> map = new java.util.LinkedHashMap<String, String>() {{
            put("Hello", "World");
            put("first", "second");
            put("polygene", "lubricants");
            put("alpha", "betical");
        }};
        // violation below ''}' at col.* 75 should be alone on a line'
        Thread t = new Thread() {@Override public void run() {super.run();}};
        new Object() { public int hashCode() { return 1; }  { int a = 5; }};
        // 2 violations above:
        // ''}' at column 58 should be alone on a line.'
        // ''}' at column 74 should be alone on a line.'
        new Object() { public int hashCode() { return 1; }  int b = 10; };
        // violation above ''}' at column 58 should be alone on a line'
        new Object() { public int hashCode() { return 1; } { int c = 5; } int d = 8; };
        // 2 violations above:
        // ''}' at column 58 should be alone on a line.'
        // ''}' at column 73 should be alone on a line.'
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
        }});}
    // 2 violations above:
    // ''}' at column 9 should be alone on a line.'
    // ''}' at column 13 should be alone on a line.'

    void foo23(java.util.HashSet<String> set) {
    }
}
