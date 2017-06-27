package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
class InputIndentationAndroidStyleCorrect //indent:0 exp:0
        extends FooForExtend { //indent:8 exp:8

    String string = foo("fooooooooooooooo", 0, false); //indent:4 exp:4

    String string1 = //indent:4 exp:4
            foo("fooooooooooooooo", 0, false); //indent:12 exp:12

    String foo (String aStr, //indent:4 exp:4
            int aNnum, boolean aFlag) { //indent:12 exp:12

        if (true && true && //indent:8 exp:8
                true && true) { //indent:16 exp:16
            String string2 = foo("fooooooo" //indent:12 exp:12
                    + "oooooooo", 0, false); //indent:20 exp:20
            if (false && //indent:12 exp:12
                    false && false) { //indent:20 exp:20

            } //indent:12 exp:12
        } //indent:8 exp:8
        return "string"; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

class InputIndentationAndroidStyleIncorrect //indent:0 exp:0
   extends FooForExtend { //indent:3 exp:8 warn

   String string = foo("fooooooooooooooo", 0, false); //indent:3 exp:4 warn

    String string1 = //indent:4 exp:4
        foo("fooooooooooooooo", 0, false); //indent:8 exp:12 warn

    String foo (String aStr, //indent:4 exp:4
        int aNnum, boolean aFlag) { //indent:8 exp:12 warn

        if (true && true && //indent:8 exp:8
             true && true) { //indent:13 exp:16 warn

            String string2 = foo("fooooooo" //indent:12 exp:12
                + "oooooooo", 0, false); //indent:16 exp:20 warn
        if (false && //indent:8 exp:12 warn
                  false && false) { //indent:18 exp:>=16

           }  //indent:11 exp:12 warn
        } //indent:8 exp:8
       return "string";  //indent:7 exp:8 warn
    } //indent:4 exp:4
} //indent:0 exp:0

class FooForExtend {} //indent:0 exp:0
