package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionTrickySwitch2 {
    public void fooDotInCaseBlock() {
        int i = 0;
        String s = "";

        switch (i) {
            case -2:
                // what
                i++;
                // no break here
            case 0:
                // what
                s.indexOf("ignore");
                // no break here
            case -1:
                 // what
                 s.indexOf("no way");
               // warn
            case 1:
            case 2:
                i--;
                // no break here
            case 3: { }
            // fall through


        }

        String breaks = ""
        // warn
            + "</table>"
            // middle
            + ""
        // end
        ;
    }

    public void foo2() {
        int a = 1;
        switch (a) {
            case 1:
            default:
    // warn
        }
    }

    public void foo3() {
        int a = 1;
        switch (a) {
            case 1:
            default:

                // comment
        }
    }

    public void foo4() {
        int a = 1;
        switch (a) {
            case 1:
                int b;
                  // warn
            default:
        }
    }

    public void foo5() {
        int a = 1;
        switch (a) {
            case 1:
                int b;
            // comment
            default:
        }
    }

    public void foo6() {
        int a = 1;
        switch (a) {
            case 1:
                int b;
                // comment
            default:
        }
    }
}
