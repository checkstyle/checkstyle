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
}
