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
                || test //indent:16 exp:16
                || test) //indent:16 exp:16
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
                && 7 < 8 && 8 < 9 //indent:16 exp:16
                && 10 < 11) { //indent:16 exp:16
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
                test //indent:16 exp:16
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
                && 8 < 9) { //indent:16 exp:16
            System.getProperty("blah"); //indent:12 exp:12
        } else if (8 < 9) { //indent:8 exp:8
            System.getProperty("blah"); //indent:12 exp:12
        } //indent:8 exp:8
        if ((1 == 2 //indent:8 exp:8
                    && test //indent:20 exp:20
                    && test) //indent:20 exp:20
                || 3 == 4 //indent:16 exp:16
                || //indent:16 exp:16
                ( //indent:16 exp:16
                    5 == 6 //indent:20 exp:20
                )) { //indent:16 exp:16
        } //indent:8 exp:8
    } //indent:4 exp:4

    void bar42(String foo, String bar) { //indent:4 exp:4
        if (("A".equals(foo) //indent:8 exp:8
                    || "B".equals(foo) //indent:20 exp:20
                    || "C".equals(foo)) //indent:20 exp:20
                && ("D".equals(bar) //indent:16 exp:16
                    || "E".equals(bar) //indent:20 exp:20
                    || "F".equals(bar) //indent:20 exp:20
                    || "G".equals(bar)) //indent:20 exp:20
                || ("H".equals(foo) //indent:16 exp:16
                    || "I".equals(foo) //indent:20 exp:20
                    || "J".equals(foo)) //indent:20 exp:20
                && "K".equals(bar)) { //indent:16 exp:16
            toString(); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
