package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                          //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidIfIndent2 { //indent:0 exp:0

    public void populatedIfTest1() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = false; //indent:8 exp:8

        if (test) System.getProperty("blah"); //indent:8 exp:8
        else System.getProperty("foo"); //indent:8 exp:8

        if (test) System.getProperty("blah"); //indent:8 exp:8
        else //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12

        if (test) //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        else System.getProperty("foo"); //indent:8 exp:8

        if (test //indent:8 exp:8
            && 7 < 8 && 8 < 9 //indent:12 exp:12
            && 10 < 11) { //indent:12 exp:12
        } //indent:8 exp:8

        if (test) //indent:8 exp:8
            return; //indent:12 exp:12


        if (test) { //indent:8 exp:8
        } else if (7 < 8) { //indent:8 exp:8
        } else if (8 < 9) { //indent:8 exp:8
        } //indent:8 exp:8

        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else if (7 < 8) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else if (8 < 9) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8


        if (test) //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        else if (7 < 8) //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        else if (8 < 9) //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12


        //     : bother to support this style? //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else //indent:8 exp:8
            if (7 < 8) { //indent:12 exp:12
                System.getProperty("blah"); //indent:16 exp:16
            } else //indent:12 exp:12
                if (8 < 9) { //indent:16 exp:16
                    System.getProperty("blah"); //indent:20 exp:20
                } //indent:16 exp:16

    } //indent:4 exp:4

} //indent:0 exp:0

class FooFoo { //indent:0 exp:0
    void foo42() { //indent:4 exp:4
        boolean test = false; //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else if (7 < 8 //indent:8 exp:8
            && 8 < 9) { //indent:12 exp:12
            System.getProperty("blah"); //indent:12 exp:12
        } else if (8 < 9) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
