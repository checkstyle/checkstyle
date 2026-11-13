/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

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
                        // violation below 'child has incorrect indentation level 24, expected level should be 16.'
                        kind.toString();                                        //indent:24 exp:16 warn
            case "CONSTRUCTOR" ->                                               //indent:12 exp:12
                        // violation below 'child has incorrect indentation level 24, expected level should be 16.'
                        kind.getClass().getSimpleName();                        //indent:24 exp:16 warn
            default -> kind.toString();                                         //indent:12 exp:12
        }).toLowerCase();                                                       //indent:8 exp:8
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0
