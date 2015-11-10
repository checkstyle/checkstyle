package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

public class InputCommentsIndentationInSwitchBlock {

    private static void fooSwitch() {
        switch("") {
            case "0": //some comment
            case "1":
                // my comment
                foo1();
                break;
            case "2":
                // my comment
                //comment
                foo1();
                // comment
                break;
            case "3":
            /* com */
                foo1();
                /* com */
                break;
            case "5":
                foo1();
                   // warn
            case "6":
                int k = 7;
                // fall through
            case "7":
                if (true) {}
                   // warn
            case "8":
                break;
            case "9":
                foo1();
                // fall through
            case "10": {
                if (true) {}
                // fall through
            }
            case "11": {
            // fall through
            }
            case "28": {
                // fall through
            }
            case "12": {
      // warn
                int i;
            }
            case "13": {
                       // some comment in empty case block
            }
            case "14": {
        // warn
            }
            case "15": {
                foo1();
                      // warn
            }
            case "16": {
                int a;
            }
            // fall through
            case "17": {
                int a;
            }
              // warn
                case "18": { System.out.println();
                }   // trailing comment
            case "19":
                // comment
            case "20":
            // comment
            case "21":
            default:
                // comment
                break;
        }
    }

    private static void foo1() {
        if (true) {
            switch(1) {
                case 0:

                case 1:
                        // warn
                    int b = 10;
                default:
                 // comment
            }

        }
    }

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

    public void foo7() {
        int a = 2;
        String s = "";
        switch (a) {
            // comment
            // comment
            // comment
            case 1:
            case 2:
                // comment
                // comment
                foo1();
                // comment
            case 3:
                // comment
                // comment
                // comment
            case 4:
    // warn
            case 5:
                s.toString().toString().toString();
                      // warn
                    // warn
                 // warn
            default:
        }
    }

    public void foo8() {
        int a = 2;
        String s = "";
        switch (a) {
            // comment
            // comment
            // comment
            case 1:
            case 2:
                // comment
                // comment
                foo1();
                // comment
            case 3:
                // comment
                // comment
                s.toString().toString().toString();
                // comment
            case 4:
      // warn
            default:
        }
    }

    public void foo9() {
        int a = 5;
        switch (a) {
            case 1:
            case 2:
                // comment
        }
    }

    public void foo10() {
        int a = 5;
        switch (a) {
            case 1:
            default:
                // comment
        }
    }

    public void foo11() {
        int a = 5;
        switch (a) {
            case 1:
            case 2:
                // comment
        }
    }

    public void foo12() {
        int a = 5;
        switch (a) {
            // comment
            case 1:
        }
    }
}
