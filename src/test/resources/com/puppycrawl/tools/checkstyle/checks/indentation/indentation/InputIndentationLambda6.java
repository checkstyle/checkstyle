/* Config:                                                                    //indent:0 exp:0
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 0                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.function.Consumer; //indent:0 exp:0
import java.util.function.Function; //indent:0 exp:0
import java.util.function.Supplier; //indent:0 exp:0

public class InputIndentationLambda6 { //indent:0 exp:0
    public static void someFunction(SomeUtilClass util) { //indent:4 exp:4
        util.myLambdaUtil("FIRST_ARG", //indent:8 exp:8
                          (string) -> System.out.println(string.trim()), //indent:26 exp:26
                          "SECOND_ARG", //indent:26 exp:26
                          () -> "WHAT WHAT!"); //indent:26 exp:26

        util.myLambdaUtil("FIRST_ARG", //indent:8 exp:8
            (string) -> System.out.println(string.trim()), //indent:12 exp:12
            "SECOND_ARG", //indent:12 exp:12
            () -> "WHAT WHAT!"); //indent:12 exp:12

        util.myLambdaUtil("FIRST_ARG", //indent:8 exp:8
    (string) -> System.out.println(string.trim()), //indent:4 exp:8 warn
    "SECOND_ARG", //indent:4 exp:12 warn
    () -> "WHAT WHAT!"); //indent:4 exp:8 warn

        Function<String, String> someFunction1 = //indent:8 exp:8
                (string) -> { //indent:16 exp:16
                    if (string.contains("abc")) { //indent:20 exp:20
                        return "SWEET!"; //indent:24 exp:24
                    } else if (string.contains("123")) { //indent:20 exp:20
                        return "COOL!"; //indent:24 exp:24
                    } else { //indent:20 exp:20
                        return "BOO!"; //indent:24 exp:24
                    } //indent:20 exp:20
    }; //indent:4 exp:8,16 warn

        Function<String, String> someFunction2 = //indent:8 exp:8
        (string) -> { //indent:8 exp:8
            if (string.contains("abc")) { //indent:12 exp:12
                return "SWEET!"; //indent:16 exp:16
            } else if (string.contains("123")) { //indent:12 exp:12
                return "COOL!"; //indent:16 exp:16
            } else { //indent:12 exp:12
                return "BOO!"; //indent:16 exp:16
            } //indent:12 exp:12
        }; //indent:8 exp:8

        Function<String, String> someFunction3 = //indent:8 exp:8
            (string) -> { //indent:12 exp:12
            if (string.contains("abc")) { //indent:12 exp:12
                return "SWEET!"; //indent:16 exp:16
            } else if (string.contains("123")) { //indent:12 exp:12
                return "COOL!"; //indent:16 exp:16
            } else { //indent:12 exp:12
                return "BOO!"; //indent:16 exp:16
            } //indent:12 exp:12
            }; //indent:12 exp:12

        Function<String, String> someFunction4 = //indent:8 exp:8
        (string) //indent:8 exp:8
    -> { //indent:4 exp:8 warn
            if (string.contains("abc")) { //indent:12 exp:12
                return "SWEET!"; //indent:16 exp:16
            } else if (string.contains("123")) { //indent:12 exp:12
                return "COOL!"; //indent:16 exp:16
            } else { //indent:12 exp:12
                return "BOO!";  //indent:16 exp:16
            } //indent:12 exp:12
        }; //indent:8 exp:8
    } //indent:4 exp:4

    interface SomeUtilClass { //indent:4 exp:4

        void myLambdaUtil(String firstArg, //indent:8 exp:8
                          Consumer<String> firstLambda, //indent:26 exp:26
                          String secondArg, //indent:26 exp:26
                          Supplier<String> secondLambda); //indent:26 exp:26
    }  //indent:4 exp:4
} //indent:0 exp:0
// ok //indent:0 exp:0
