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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidIfIndent { //indent:0 exp:0

    public void emptyIfTest() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        // lcurly on same line  //indent:8 exp:8
        if (test) { //indent:8 exp:8
        } //indent:8 exp:8

        // lcurly on next line //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8

        // lcurly for if and else on same line //indent:8 exp:8
        if (test) { //indent:8 exp:8
        } else { //indent:8 exp:8
        } //indent:8 exp:8

        // lcurly for if and else on same line //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8
        else //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8

        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
        if (test) { //indent:8 exp:8
        } //indent:8 exp:8
        else //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8


        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
        } else //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8

        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
        } else { //indent:8 exp:8
        } //indent:8 exp:8

        // lcurly for if and else on same line -- mixed braces, unnested //indent:8 exp:8
        if (test) { //indent:8 exp:8
        } //indent:8 exp:8
        else { //indent:8 exp:8
        } //indent:8 exp:8

        if (foo() //indent:8 exp:8
            || test //indent:12 exp:12
            || test) //indent:12 exp:12
        { //indent:8 exp:8
        } //indent:8 exp:8

    } //indent:4 exp:4

    /////  same as above, with statements //indent:4 exp:4
    public void populatedIfTest() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = false; //indent:8 exp:8
        // no braces if //indent:8 exp:8
        if (test) //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12

        // no braces if/else //indent:8 exp:8
        if (test) //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        else //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12


        // lcurly on same line, and stmt //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        // lcurly on next line and stmt //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8
        // lcurly for if and else on same line //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else { //indent:8 exp:8
            System. //indent:12 exp:12
                getProperty("blah"); //indent:16 exp:>=16
        } //indent:8 exp:8

        // lcurly for if and else on same line //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8
        else //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8
        else //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8


        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
        if (test) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } else { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        // lcurly for if and else on same line -- mixed braces, unnested //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8
        else { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        if (test) System.getProperty("blah"); //indent:8 exp:8

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

    public void parenIfTest() { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        if (test //indent:8 exp:8
        ) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        if (test //indent:8 exp:8
        ) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        if //indent:8 exp:8
        ( //indent:8 exp:12 warn
            test //indent:12 exp:12
        ) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8

        if (test //indent:8 exp:8
            ) //indent:12 exp:12
        { //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4

    boolean foo() { //indent:4 exp:4
        return true; //indent:8 exp:8
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
