//non-compiled with javac: Compilable with Java14                        //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

public class InputIndentationCheckSwitchExpression2 {                    //indent:0 exp:0
}                                                                        //indent:0 exp:0

class Scratch                                                            //indent:0 exp:0
{                                                                        //indent:0 exp:0
    public void method(String... args)                                   //indent:4 exp:4
    {                                                                    //indent:4 exp:4
        (switch (args.length) {                                          //indent:8 exp:8
          case 1 -> args[0];                                             //indent:10 exp:10
          case 2 -> args[1];                                             //indent:10 exp:10
          default -> throw new IllegalArgumentException();               //indent:10 exp:10
        }).length();                                                     //indent:8 exp:8
        String s = switch (args.length) {                                //indent:8 exp:8
          case 1 -> args[0];                                             //indent:10 exp:10
          case 2 -> args[1];                                             //indent:10 exp:10
          default -> throw new IllegalArgumentException();               //indent:10 exp:10
        };                                                               //indent:8 exp:8
        s.length();                                                      //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    int result = switch (something) {                                    //indent:4 exp:4
      case CASE -> {                                                     //indent:6 exp:6
          yield switch (somethingElse) {                                 //indent:10 exp:10
            case CASE -> 1;                                              //indent:12 exp:12
            default -> 2;                                                //indent:12 exp:12
          };                                                             //indent:10 exp:10
      }                                                                  //indent:6 exp:6
      default -> 3;                                                      //indent:6 exp:6
    };                                                                   //indent:4 exp:4
}                                                                        //indent:0 exp:0
