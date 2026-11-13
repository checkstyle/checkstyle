package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionTrickySwitch {

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
            /* // warn */
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
                        // warn
                    int b = 10;
                default:
                 // comment
            }

        }
    }
}
