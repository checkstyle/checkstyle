/*
RightCurly
option = \tALONE
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
}
