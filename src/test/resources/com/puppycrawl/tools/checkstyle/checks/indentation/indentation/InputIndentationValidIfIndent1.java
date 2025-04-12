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
public class InputIndentationValidIfIndent1 { //indent:0 exp:0

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

    } //indent:4 exp:4

} //indent:0 exp:0
