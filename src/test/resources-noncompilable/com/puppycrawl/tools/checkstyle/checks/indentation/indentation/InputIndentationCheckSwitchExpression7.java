//non-compiled with javac: Compilable with Java14                             //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

public class InputIndentationCheckSwitchExpression7 {                         //indent:0 exp:0
}                                                                             //indent:0 exp:0

class WeirdSwitch {                                                           //indent:0 exp:0

    public void switch0(int something) {                                      //indent:4 exp:4
        int result = switch (something) {                                     //indent:8 exp:8
            case 1 -> {                                                       //indent:12 exp:12
                yield                                                         //indent:16 exp:16
                    switch (something) {                                      //indent:20 exp:20
                        case 2 -> 1;                                          //indent:24 exp:24
                        default -> 2;                                         //indent:24 exp:24
                    };                                                        //indent:20 exp:20
            }                                                                 //indent:12 exp:12
            default -> 3;                                                     //indent:12 exp:12
        };                                                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch1(int some) {                                            //indent:4 exp:4
        int sum = 0;                                                          //indent:8 exp:8
        for(int i = switch (some) {                                           //indent:8 exp:8
            case 1 -> 1;                                                      //indent:12 exp:12
            case 2 -> 2;                                                      //indent:12 exp:12
            default -> 0;                                                     //indent:12 exp:12
        }; i < 10; i++) {                                                     //indent:8 exp:8
            sum = sum + i;                                                    //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        return sum;                                                           //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch2(int some) {                                            //indent:4 exp:4
        int sum = 0;                                                          //indent:8 exp:8
        for(int i =                                                           //indent:8 exp:8
            switch (some) {                                                   //indent:12 exp:12
                case 1 -> 1;                                                  //indent:16 exp:16
                case 2 -> 2;                                                  //indent:16 exp:16
                default -> 0;                                                 //indent:16 exp:16
            }; i < 10; i++){                                                  //indent:12 exp:12
            sum = sum + i;                                                    //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        return sum;                                                           //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch3(int some) {                                            //indent:4 exp:4
        int sum = 0;                                                          //indent:8 exp:8
        for(int i = 0; i < 2; i = switch (some) {                             //indent:8 exp:8
            case 1 -> 1;                                                      //indent:12 exp:12
            case 2 -> 2;                                                      //indent:12 exp:12
            default -> 0;                                                     //indent:12 exp:12
        }){                                                                   //indent:8 exp:8
            sum = sum + i;                                                    //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        return sum;                                                           //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch4(int some) {                                            //indent:4 exp:4
        int sum = 0;                                                          //indent:8 exp:8
        for(int i = 0; i < 2; i =                                             //indent:8 exp:8
            switch (some) {                                                   //indent:12 exp:12
                case 1 -> 1;                                                  //indent:16 exp:16
                case 2 -> 2;                                                  //indent:16 exp:16
                default -> 0;                                                 //indent:16 exp:16
            }){                                                               //indent:12 exp:12
            sum = sum + i;                                                    //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        return sum;                                                           //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch5(String some) {                                         //indent:4 exp:4
        int sum = 0;                                                          //indent:8 exp:8
        while (switch (some) {                                                //indent:8 exp:8
            case "1" -> true;                                                 //indent:12 exp:12
            default -> false;                                                 //indent:12 exp:12
        }) {                                                                  //indent:8 exp:8
        }                                                                     //indent:8 exp:8
        while (                                                               //indent:8 exp:8
            switch (some) {                                                   //indent:12 exp:12
                case "1" -> true;                                             //indent:16 exp:16
                default -> false;                                             //indent:16 exp:16
            }) {                                                              //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        return sum;                                                           //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch6(String some) {                                         //indent:4 exp:4
        int i = 0;                                                            //indent:8 exp:8
        do{                                                                   //indent:8 exp:8
            System.out.println(i);                                            //indent:12 exp:12
            i++;                                                              //indent:12 exp:12
        } while(switch (some) {                                               //indent:8 exp:8
            case "2" -> true;                                                 //indent:12 exp:12
            default -> false;                                                 //indent:12 exp:12
        });                                                                   //indent:8 exp:8
        return 0;                                                             //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch7(String some) {                                         //indent:4 exp:4
        int i = 0;                                                            //indent:8 exp:8
        do{                                                                   //indent:8 exp:8
            System.out.println(i);                                            //indent:12 exp:12
            i++;                                                              //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        while(switch (some) {                                                 //indent:8 exp:8
            case "2" -> true;                                                 //indent:12 exp:12
            default -> false;                                                 //indent:12 exp:12
        });                                                                   //indent:8 exp:8
        return 0;                                                             //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch8(String some) {                                         //indent:4 exp:4
        int i = 0;                                                            //indent:8 exp:8
        do{                                                                   //indent:8 exp:8
            System.out.println(i);                                            //indent:12 exp:12
            i++;                                                              //indent:12 exp:12
        }                                                                     //indent:8 exp:8
        while(                                                                //indent:8 exp:8
            switch (some) {                                                   //indent:12 exp:12
                case "2" -> true;                                             //indent:16 exp:16
                default -> false;                                             //indent:16 exp:16
            });                                                               //indent:12 exp:12
        return 0;                                                             //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    public int switch9(String some) {                                         //indent:4 exp:4
        int i = 0;                                                            //indent:8 exp:8
        do{                                                                   //indent:8 exp:8
            System.out.println(i);                                            //indent:12 exp:12
            i++;                                                              //indent:12 exp:12
        } while(                                                              //indent:8 exp:8
            switch (some) {                                                   //indent:12 exp:12
                case "2" -> true;                                             //indent:16 exp:16
                default -> false;                                             //indent:16 exp:16
            });                                                               //indent:12 exp:12
        return 0;                                                             //indent:8 exp:8
    }                                                                         //indent:4 exp:4
}                                                                             //indent:0 exp:0
