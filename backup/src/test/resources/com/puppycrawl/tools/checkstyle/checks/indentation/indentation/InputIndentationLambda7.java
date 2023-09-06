/* Config:                                                                    //indent:0 exp:0
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.function.Consumer; //indent:0 exp:0
import java.util.function.Function; //indent:0 exp:0
import java.util.function.Supplier; //indent:0 exp:0

public class InputIndentationLambda7 { //indent:0 exp:0
    public static void someFunction(SomeUtilClass util) { //indent:4 exp:4
        util.myLambdaUtil("FIRST_ARG", //indent:8 exp:8
                (string) //indent:16 exp:12 warn
                -> //indent:16 exp:12 warn
            System.out.println(string.trim()), //indent:12 exp:12
                          "SECOND_ARG", //indent:26 exp:12 warn
                         () -> "WHAT WHAT!"); //indent:25 exp:12 warn

        Function<String, String> someFunction1 = //indent:8 exp:8
                (string) -> { //indent:16 exp:12 warn
                    if (string.contains("abc")) { //indent:20 exp:16 warn
                        return "SWEET!"; //indent:24 exp:20 warn
                    } else{ //indent:20 exp:16 warn
                        return "COOL!"; //indent:24 exp:20 warn
                    } //indent:20 exp:16 warn
                }; //indent:16 exp:12 warn

        Function<String, String> someFunction2 = //indent:8 exp:8
                (string) //indent:16 exp:12 warn
                -> { //indent:16 exp:12 warn
                    if (string.contains("abc")) //indent:20 exp:16 warn
                    System.out.println("done"); //indent:20 exp:20
                return "SWEET!"; //indent:16 exp:16
}; //indent:0 exp:12 warn
    } //indent:4 exp:4

    interface SomeUtilClass { //indent:4 exp:4

        void myLambdaUtil(String firstArg, //indent:8 exp:8
            Consumer<String> firstLambda, //indent:12 exp:12
            String secondArg, //indent:12 exp:12
            Supplier<String> secondLambda); //indent:12 exp:12
    }  //indent:4 exp:4
} //indent:0 exp:0
// ok //indent:0 exp:0
