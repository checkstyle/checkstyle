//non-compiled with javac: Compilable with Java14                       //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationCheckSwitchExpression4 {                   //indent:0 exp:0
    public static void main(String... args) {                           //indent:4 exp:4
        new InputIndentationCheckSwitchExpression4().run();             //indent:8 exp:8
    }                                                                   //indent:4 exp:4
    private void run() {                                                //indent:4 exp:4
        {                                                               //indent:8 exp:8
            int i = 6;                                                  //indent:12 exp:12
            int o = 0;                                                  //indent:12 exp:12
            while (switch (i) {                                         //indent:12 exp:12
                case 1:                                                 //indent:16 exp:16
                    try {                                               //indent:20 exp:20
                        new InputIndentationCheckSwitchExpression4()    //indent:24 exp:24
                                .throwException();                      //indent:32 exp:32
                    } catch (Throwable t) {                             //indent:20 exp:20
                        i = 0;                                          //indent:24 exp:24
                        yield true;                                     //indent:24 exp:24
                    }                                                   //indent:20 exp:20
                case 2:                                                 //indent:16 exp:16
                    try {                                               //indent:20 exp:20
                        new InputIndentationCheckSwitchExpression4()    //indent:24 exp:24
                                .throwException();                      //indent:32 exp:32
                    } catch (Throwable t) {                             //indent:20 exp:20
                        i = 1;                                          //indent:24 exp:24
                        yield true;                                     //indent:24 exp:24
                    }                                                   //indent:20 exp:20
                case 3, 4:                                              //indent:16 exp:16
                    try {                                               //indent:20 exp:20
                        new InputIndentationCheckSwitchExpression4()    //indent:24 exp:24
                                .throwException();                      //indent:32 exp:32
                    } catch (Throwable t) {                             //indent:20 exp:20
                        i--;                                            //indent:24 exp:24
                        if (i == 2 || i == 4) {                         //indent:24 exp:24
                            try {                                       //indent:28 exp:28
                                yield switch (i) {                      //indent:32 exp:32
                                    case 2 -> throw new                 //indent:36 exp:36
                                            InputIndentationCheckSwitchExpression4 //indent:44 exp:44
                                                    .ResultException(true); //indent:52 exp:52
                                    case 4 -> false;                    //indent:36 exp:36
                                    default -> throw                    //indent:36 exp:36
                                            new IllegalStateException(); //indent:44 exp:44
                                };                                      //indent:32 exp:32
                            } catch (InputIndentationCheckSwitchExpression4 //indent:28 exp:28
                                             .ResultException ex) { //indent:45 exp:45
                                yield ex.result; //indent:32 exp:32
                            }                                           //indent:28 exp:28
                        } else {                                        //indent:24 exp:24
                        }                                               //indent:24 exp:24
                    }                                                   //indent:20 exp:20
                default:                                                //indent:16 exp:16
                    try {                                               //indent:20 exp:20
                        new InputIndentationCheckSwitchExpression4()    //indent:24 exp:24
                                .throwException();                      //indent:32 exp:32
                    } catch (Throwable t) {                             //indent:20 exp:20
                        i--;                                            //indent:24 exp:24
                        yield switch (i) {                              //indent:24 exp:24
                            case -1 -> false;                           //indent:28 exp:28
                            case 3 -> true;                             //indent:28 exp:28
                            default -> true;                            //indent:28 exp:28
                        };                                              //indent:24 exp:24
                    }                                                   //indent:20 exp:20
                    throw new AssertionError();                         //indent:20 exp:20
            }) {                                                        //indent:12 exp:12
                o++;                                                    //indent:16 exp:16
            }                                                           //indent:12 exp:12
            if (o != 6 && i >= 0) {                                     //indent:12 exp:12
                throw new IllegalStateException();                      //indent:16 exp:16
            }                                                           //indent:12 exp:12
        }                                                               //indent:8 exp:8
        {                                                               //indent:8 exp:8
            int i = 6;                                                  //indent:12 exp:12
            int o = 0;                                                  //indent:12 exp:12
            if (switch (i) {                                            //indent:12 exp:12
                case 1:                                                 //indent:16 exp:16
                    i = 0;                                              //indent:20 exp:20
                    yield true;                                         //indent:20 exp:20
                case 2:                                                 //indent:16 exp:16
                    i = 1;                                              //indent:20 exp:20
                    yield true;                                         //indent:20 exp:20
                case 3, 4:                                              //indent:16 exp:16
                    i--;                                                //indent:20 exp:20
                    if (i == 2 || i == 4) {                             //indent:20 exp:20
                        yield (switch (i) {                             //indent:24 exp:24
                            case 2 -> 3;                                //indent:28 exp:28
                            case 4 -> 5;                                //indent:28 exp:28
                            default -> throw new IllegalStateException();//indent:28 exp:28
                        }) == i + 1;                                    //indent:24 exp:24
                    } else {                                            //indent:20 exp:20
                        yield true;                                     //indent:24 exp:24
                    }                                                   //indent:20 exp:20
                default:                                                //indent:16 exp:16
                    i--;                                                //indent:20 exp:20
                    yield switch (i) {                                  //indent:20 exp:20
                        case -1 -> false;                               //indent:24 exp:24
                        case 3 -> true;                                 //indent:24 exp:24
                        default -> true;                                //indent:24 exp:24
                    };                                                  //indent:20 exp:20
            }) {                                                        //indent:12 exp:12
                o++;                                                    //indent:16 exp:16
            }                                                           //indent:12 exp:12
            if (o != 1 && i != 5) {                                     //indent:12 exp:12
                throw new IllegalStateException();                      //indent:16 exp:16
            }                                                           //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private void throwException() {                                     //indent:4 exp:4
        throw new RuntimeException();                                   //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private final class ResultException extends RuntimeException {      //indent:4 exp:4
        public final boolean result;                                    //indent:8 exp:8

        public ResultException(boolean result) {                        //indent:8 exp:8
            this.result = result;                                       //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0
