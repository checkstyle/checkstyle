/*
RightCurly
option = \tALONE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, \
         STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestWithAnnotationsMethods
{
    private int var1;
    private int var2;
    private int v2;

    @Deprecated
    private void foo16() {
        if (true) { return; } else { }
        // 2 violations above:
        // ''}' at column 29 should be alone on a line.'
        // ''}' at column 38 should be alone on a line.'
        if (false) {
        }

        if (true) { return; } else { }
        // 2 violations above:
        // ''}' at column 29 should be alone on a line.'
        // ''}' at column 38 should be alone on a line.'
    }

    @Deprecated
    void foo17() { int v1 = 5; v2 = 6; } @Deprecated void foo18() {int v1 = 5; v2 = 6; }
    // 2 violations above:
    // ''}' at column 40 should be alone on a line.'
    // ''}' at column 88 should be alone on a line.'
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
        } finally { toString(); }
        // 2 violations above:
        // ''}' at column 9 should be alone on a line.'
        // ''}' at column 33 should be alone on a line.'
    }
}
