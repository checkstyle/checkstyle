//non-compiled with javac: Compilable with Java14                       //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationCheckSwitchExpression5 {                   //indent:0 exp:0
    public static void main(String... args) {                           //indent:4 exp:4
        new InputIndentationCheckSwitchExpression5().run();             //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private void run() {                                                //indent:4 exp:4
        {                                                               //indent:8 exp:8
            int o = 0;                                                  //indent:12 exp:12
            for (int i = 6; switch (i) {                               //indent:12 exp:12
                case 1:                                                 //indent:16 exp:16
                    i = 0;                                              //indent:20 exp:20
                    yield true;                                         //indent:20 exp:20
                case 2:                                                 //indent:16 exp:16
                    i = 1;                                              //indent:20 exp:20
                    yield true;                                         //indent:20 exp:20
                case 3, 4:                                              //indent:16 exp:16
                    i--;                                                //indent:20 exp:20
                    if (i == 2 || i == 4) {                             //indent:20 exp:20
                        yield switch (i) {                              //indent:24 exp:24
                            case 2 -> true;                             //indent:28 exp:28
                            case 4 -> false;                            //indent:28 exp:28
                            default -> throw                            //indent:28 exp:28
                                    new IllegalStateException();        //indent:36 exp:36
                        };                                              //indent:24 exp:24
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
            }; ) {                                                     //indent:12 exp:12
                o++;                                                    //indent:16 exp:16
            }                                                           //indent:12 exp:12
            if (o != 6) {                                               //indent:12 exp:12
                throw new IllegalStateException();                      //indent:16 exp:16
            }                                                           //indent:12 exp:12
        }                                                               //indent:8 exp:8
        {                                                               //indent:8 exp:8
            int i = 6;                                                  //indent:12 exp:12
            int o = 0;                                                  //indent:12 exp:12
            do {                                                        //indent:12 exp:12
                o++;                                                    //indent:16 exp:16
            } while (switch (i) {                                       //indent:12 exp:12
                case 1:                                                 //indent:16 exp:16
                    i = 0;                                              //indent:20 exp:20
                    yield true;                                         //indent:20 exp:20
                case 2:                                                 //indent:16 exp:16
                    i = 1;                                              //indent:20 exp:20
                    yield true;                                         //indent:20 exp:20
                case 3, 4:                                              //indent:16 exp:16
                    i--;                                                //indent:20 exp:20
                    if (i == 2 || i == 4) {                             //indent:20 exp:20
                        yield switch (i) {                              //indent:24 exp:24
                            case 2 -> true;                             //indent:28 exp:28
                            case 4 -> false;                            //indent:28 exp:28
                            default -> throw                            //indent:28 exp:28
                                    new IllegalStateException();        //indent:36 exp:36
                        };                                              //indent:24 exp:24
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
            });                                                         //indent:12 exp:12
            if (o != 6 && i >= 0) {                                     //indent:12 exp:12
                throw new IllegalStateException();                      //indent:16 exp:16
            }                                                           //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    static final class ResultException extends RuntimeException {       //indent:4 exp:4
        public final boolean result;                                    //indent:8 exp:8
        public ResultException(boolean result) {                        //indent:8 exp:8
            this.result = result;                                       //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0
