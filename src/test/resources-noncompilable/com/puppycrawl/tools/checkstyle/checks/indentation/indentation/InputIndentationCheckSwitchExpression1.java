//non-compiled with javac: Compilable with Java14                        //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

public class InputIndentationCheckSwitchExpression1 {                    //indent:0 exp:0
}                                                                        //indent:0 exp:0

class Scratch                                                            //indent:0 exp:0
{                                                                        //indent:0 exp:0
    public void method(String... args)                                   //indent:4 exp:4
    {                                                                    //indent:4 exp:4
        (switch (args.length) {                                          //indent:8 exp:8
            case 1 -> args[0];                                           //indent:12 exp:12
            case 2 -> args[1];                                           //indent:12 exp:12
            default -> throw new IllegalArgumentException();             //indent:12 exp:12
        }).length();                                                     //indent:8 exp:8
        String s = switch (args.length) {                                //indent:8 exp:8
            case 1 -> args[0];                                           //indent:12 exp:12
            case 2 -> args[1];                                           //indent:12 exp:12
            default -> throw new IllegalArgumentException();             //indent:12 exp:12
        };                                                               //indent:8 exp:8
        s.length();                                                      //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    int something = 1;                                                   //indent:4 exp:4
    int result = switch (something) {                                    //indent:4 exp:4
        case 1 -> {                                                      //indent:8 exp:8
            yield switch (something) {                                   //indent:12 exp:12
                case 2 -> 1;                                             //indent:16 exp:16
                default -> 2;                                            //indent:16 exp:16
            };                                                           //indent:12 exp:12
        }                                                                //indent:8 exp:8
        default -> 3;                                                    //indent:8 exp:8
    };                                                                   //indent:4 exp:4

}                                                                        //indent:0 exp:0
