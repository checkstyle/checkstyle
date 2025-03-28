//non-compiled with javac: Compilable with Java17                     //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0
                                                                      //indent:70 exp:70
/* Config:                                                            //indent:0 exp:0
 *                                                                  //indent:1 exp:1
 * basicOffset = 4                                                  //indent:1 exp:1
 * braceAdjustment = 0                                              //indent:1 exp:1
 * caseIndent = 4                                                   //indent:1 exp:1
 * throwsIndent = 4                                                 //indent:1 exp:1
 * arrayInitIndent = 4                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                      //indent:1 exp:1
 * forceStrictCondition = true                                      //indent:1 exp:1
 */                                                                 //indent:1 exp:1
public class InputIndentationCheckSingleSwitchStatementsWithoutCurly { //indent:0 exp:0
    void testCorrectIndentation(int obj) {                           //indent:4 exp:4
        switch (obj) {                                              //indent:8 exp:8
            case 0                                                  //indent:12 exp:12
                ->                                                 //indent:16 exp:16
                    System.out.println("Test");                    //indent:20 exp:20
            case 1 ->                                              //indent:12 exp:12
                System.out.println("TEST");                        //indent:16 exp:16
            case 2 -> {                                            //indent:12 exp:12
                System.out.println("Test");                        //indent:16 exp:16
            }                                                      //indent:12 exp:12
        }                                                          //indent:8 exp:8
    }                                                              //indent:4 exp:4

    void testIncorrectIndentation(int obj) {                       //indent:4 exp:4
        switch (obj) {                                             //indent:8 exp:8
            case 1                                                 //indent:12 exp:12
            ->                                                    //indent:12 exp:16 warn
            System.out.println("Test");                            //indent:12 exp:20 warn
        case 2 ->                                                 //indent:8 exp:12 warn
System.out.println("Test");                                        //indent:0 exp:16 warn
        }                                                         //indent:8 exp:8
    }                                                             //indent:4 exp:4

    void testMixedCases(int obj) {                                //indent:4 exp:4
        switch (obj) {                                            //indent:8 exp:8
            case 1 -> System.out.println("TEST");                 //indent:12 exp:12
            case 2                                                //indent:12 exp:12
                -> System.out.println("Test");                    //indent:16 exp:16
            case 3 ->                                             //indent:12 exp:12
                        System.out.println("Test");               //indent:24 exp:16 warn
            case 4 -> {                                           //indent:12 exp:12
                System.out.println("Test");                       //indent:16 exp:16
            System.out.println("Another statement");              //indent:12 exp:16 warn
            }                                                     //indent:12 exp:12
        }                                                         //indent:8 exp:8
    }                                                             //indent:4 exp:4

    private boolean isObjectMethod(Method m) {                    //indent:4 exp:4
        return switch (m.getName()) {                             //indent:8 exp:8
            case "toString" -> m.getReturnType() == String.class; //indent:12 exp:12
            case "hashCode" -> m.getReturnType() == int.class     //indent:12 exp:12
                               && m.getParameterCount() == 0;     //indent:31 exp:31
            case "equals"   ->                                    //indent:12 exp:12
                m.getReturnType() == boolean.class                //indent:16 exp:16
                    && m.getParameterCount() == 1                 //indent:20 exp:20
                    && m.getParameterTypes()[0] == Object.class;  //indent:20 exp:20
            default -> false;                                     //indent:12 exp:12
        };                                                        //indent:8 exp:8
    }                                                             //indent:4 exp:4
}                                                                 //indent:0 exp:0
