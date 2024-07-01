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
            /* // odd indentation comment
            *  */
                // violation 2 lines above '.* indentation should be the same level as line 22.'
                foo1();
                /* com */
                break;
            case "5":
                // violation 2 lines below '.* indentation should be the same level as line 27, 29.'
                foo1();
                   // odd indentation comment
            case "6":
                int k = 7;
                // fall through
            case "7":
                // violation 2 lines below '.* indentation should be the same level as line 34, 36.'
                if (true) {}
                   // odd indentation comment
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
      // odd indentation comment
                // violation above '.* indentation should be the same level as line 54.'
                int i;
            }
            case "13": {
                       // some comment in empty case block
            }
            case "14": {
        // odd indentation comment
            // violation above '.* indentation should be the same level as line 62.'
            }
            case "15": {
                // violation 2 lines below '.* indentation should be the same level as line 65.'
                foo1();
                      // odd indentation comment
            }
            case "16": {
                int a;
            }
            // fall through
            case "17": {
                int a;
            }
                // violation below '.* indentation should be the same level as line 72, 77.'
          // odd indentation comment
                case "18": { System.lineSeparator();
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
                    // violation below '.* indentation should be the same level as line 98.'
                        // odd indentation comment
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
                 // violation 2 lines below '.* indentation should be the same .* as line 122, 124.'
                 s.indexOf("no way");
               // odd indentation comment
            case 1:
            case 2:
                i--;
                // no break here
            case 3: { }
            // fall through


        }

        String breaks = ""
        // violation below '.* indentation should be the same level as line 137.'
        // odd indentation comment
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
    // odd indentation comment
        }
        // violation 2 lines above'.* indentation should be the same level as line 150.'
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
                // violation 2 lines below '.* indentation should be the same .* as line 169, 171.'
                int b;
                  // odd indentation comment
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
                // violation 2 lines below'.* indentation should be the same .* as line 213, 215.'
            case 4:
    // odd indentation comment
            case 5:
                // violation 4 lines below 'indentation should be the same level as line 218, 223.'
                // violation 4 lines below 'indentation should be the same level as line 218, 223.'
                // violation 4 lines below 'indentation should be the same level as line 218, 223.'
                s.toString().toString().toString();
                      // odd indentation comment
                    // odd indentation comment
                 // odd indentation comment
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
            // violation 2 lines below '.* indentation should be the same level as line 246, 248.'
            case 4:
      // odd indentation comment
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
