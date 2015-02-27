package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputValidWhileIndent { //indent:0 exp:0

    /** Creates a new instance of InputValidWhileIndent */ //indent:4 exp:4
    public InputValidWhileIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    private void method1() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        while (test) System.getProperty("foo"); //indent:8 exp:8

        while (test) //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12

        while (test) { //indent:8 exp:8
        } //indent:8 exp:8

        while (test) //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8

        while (test) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        while (test)  { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        while (test)  { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        while (test) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        while (test)  { //indent:8 exp:8
            if (test) { //indent:12 exp:12
                System.getProperty("foo"); //indent:16 exp:16
            } //indent:12 exp:12
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        while (test) //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12

        if (test) { //indent:8 exp:8
            while (test) //indent:12 exp:12
                System.getProperty("foo"); //indent:16 exp:16
        } //indent:8 exp:8

        while (test //indent:8 exp:8
            && 4 < 7 && 8 < 9 //indent:12 exp:12
            && 3 < 4) { //indent:12 exp:12
        } //indent:8 exp:8

    } //indent:4 exp:4

} //indent:0 exp:0
