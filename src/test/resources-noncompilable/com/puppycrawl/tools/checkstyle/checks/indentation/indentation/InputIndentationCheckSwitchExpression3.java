//non-compiled with javac: Compilable with Java14                       //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationCheckSwitchExpression3 {                   //indent:0 exp:0
}                                                                       //indent:0 exp:0

class NewSwitch {                                                       //indent:0 exp:0
    String string = "string";                                           //indent:4 exp:4

    void someMethod(String string) {                                    //indent:4 exp:4
        String name = switch (string) {                                 //indent:8 exp:8
            case "string" -> "newSwitch";                               //indent:12 exp:12
            case "JDk"    -> "newJDK";                                  //indent:12 exp:12
            case "newJDk" -> "anotherJDK";                              //indent:12 exp:12
            default       -> "unknownValue";                            //indent:12 exp:12
        };                                                              //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private void testAnonymousClasses() {                               //indent:4 exp:4
        for (int i : new int[]{1, 2}) {                                 //indent:8 exp:8
            check(3, id((switch (i) {                                   //indent:12 exp:12
                case 1:                                                 //indent:16 exp:16
                    yield 1;                                            //indent:20 exp:20
                default:                                                //indent:16 exp:16
                    yield 2;                                            //indent:20 exp:20
            })));                                                       //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    class I {                                                           //indent:4 exp:4
        int method() {                                                  //indent:8 exp:8
            return 0;                                                   //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    int id(int i) {                                                     //indent:4 exp:4
        return i;                                                       //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    void check(int i, int id) {}                                        //indent:4 exp:4

    static class Internal {                                             //indent:4 exp:4

        private String string;                                          //indent:8 exp:8

        void calculateLength(String string) {                           //indent:8 exp:8
            int length = (switch (string) {                             //indent:12 exp:12
                case "string" -> "newSwitch";                           //indent:16 exp:16
                case "JDk"    -> "newJDK";                              //indent:16 exp:16
                default       -> "unknownValue";                        //indent:16 exp:16
            }).length();                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8

        void calculateLength2(String string) {                           //indent:8 exp:8
            int length =                                                //indent:12 exp:12
                    (switch (string) {                                  //indent:20 exp:20
                case "string" -> "newSwitch";                           //indent:16 exp:16
                default       -> "unknownValue";                        //indent:16 exp:16
            }).length();                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0
