/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineUnnecessarySemi {

    int a = 1;;

    class Inner {};

    class Inner2 {};

    interface Inner3 {
    };

    record Inner4() {
    };

    enum Inner5 {
    };

    void method() {
        int j = 1;

        class LocalClass {};
    };

}
