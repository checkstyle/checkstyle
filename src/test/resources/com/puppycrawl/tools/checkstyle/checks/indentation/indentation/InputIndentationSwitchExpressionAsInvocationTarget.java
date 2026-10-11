//                                                                              //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

/* Config:                                                                      //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:     //indent:1 exp:1
 * basicOffset = 4                                                              //indent:1 exp:1
 * braceAdjustment = 0                                                          //indent:1 exp:1
 * caseIndent = 4                                                               //indent:1 exp:1
 * tabWidth = 4                                                                 //indent:1 exp:1
 * lineWrappingIndentation = 4                                                  //indent:1 exp:1
 * forceStrictCondition = false                                                 //indent:1 exp:1
 */                                                                             //indent:1 exp:1
public class InputIndentationSwitchExpressionAsInvocationTarget {               //indent:0 exp:0
    void method1(String[] args) {                                               //indent:4 exp:4
        int len = (switch (args.length) {                                       //indent:8 exp:8
            case 1 -> args[0];                                                  //indent:12 exp:12
            case 2 -> args[1];                                                  //indent:12 exp:12
            default -> throw new IllegalArgumentException();                    //indent:12 exp:12
        }).length();                                                            //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    void method2(String[] args) {                                               //indent:4 exp:4
        String s = switch (args.length) {                                       //indent:8 exp:8
            case 1 -> args[0];                                                  //indent:12 exp:12
            case 2 -> args[1];                                                  //indent:12 exp:12
            default -> throw new IllegalArgumentException();                    //indent:12 exp:12
        };                                                                      //indent:8 exp:8
        s.length();                                                             //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    void method3(int x) {                                                       //indent:4 exp:4
        System.out.println(                                                     //indent:8 exp:8
            switch (x) {                                                        //indent:12 exp:12
                case 1 -> "one";                                                //indent:16 exp:16
                default -> "other";                                             //indent:16 exp:16
            }                                                                   //indent:12 exp:12
        );                                                                      //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    void method4(String[] args) {                                               //indent:4 exp:4
        boolean empty = (switch (args.length) {                                 //indent:8 exp:8
            case 1 -> args[0];                                                  //indent:12 exp:12
            default -> throw new IllegalArgumentException();                    //indent:12 exp:12
        }).isEmpty();                                                           //indent:8 exp:8
    }                                                                           //indent:4 exp:4

    void method5(Object kind) {                                                 //indent:4 exp:4
        String name = (switch (kind.toString()) {                               //indent:8 exp:8
            case "PACKAGE", "MODULE" ->                                         //indent:12 exp:12
                        kind.toString();                                        //indent:24 exp:16 warn
            case "CONSTRUCTOR" ->                                               //indent:12 exp:12
                        kind.getClass().getSimpleName();                        //indent:24 exp:16 warn
            default -> kind.toString();                                         //indent:12 exp:12
        }).toLowerCase();                                                       //indent:8 exp:8
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0
