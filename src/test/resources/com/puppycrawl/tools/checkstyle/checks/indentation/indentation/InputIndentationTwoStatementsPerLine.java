package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;//indent:0 exp:0

public class InputIndentationTwoStatementsPerLine {//indent:0 exp:0
    int var6 = 5; int var7 = 6, //indent:4 exp:4
        var8 = 5; //indent:8 exp:8

    public void method() { //indent:4 exp:4
        long_lined_label: if (true //indent:8 exp:8
            && true) {} //indent:12 exp:12
    } //indent:4 exp:4
    /* package-private */ static final void //indent:4 exp:4
        method2() {} //indent:8 exp:8
}//indent:0 exp:0
