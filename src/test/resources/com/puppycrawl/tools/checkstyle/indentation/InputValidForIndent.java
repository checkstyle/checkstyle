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
public class InputValidForIndent { //indent:0 exp:0

    /** Creates a new instance of InputValidForIndent */ //indent:4 exp:4
    public InputValidForIndent() { //indent:4 exp:4
    } //indent:4 exp:4


    private void method1(int[] indices) //indent:4 exp:4
    { //indent:4 exp:4
        for (int i=0; i<10; i++) //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12

        for (int i=0; i<10; i++) System.getProperty("foo"); //indent:8 exp:8

        for (int i=0; i<10; i++) { //indent:8 exp:8
        } //indent:8 exp:8

        for (int i=0; i<10; i++) //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8

        for (int i=0; i<10; i++) //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        for (int i=0; i<10; i++) //indent:8 exp:8
        { //indent:8 exp:8
            boolean test = true; //indent:12 exp:12
            if (test) { // mixed styles are OK //indent:12 exp:12
                System.getProperty("foo"); //indent:16 exp:16
            } //indent:12 exp:12
        } //indent:8 exp:8

        for ( //indent:8 exp:8
            int i=0; //indent:12 exp:12
            i<10; //indent:12 exp:12
            i++) //indent:12 exp:12
        { //indent:8 exp:8
        } //indent:8 exp:8

        for (int i=0; //indent:8 exp:8
            i<10 && 4<5 //indent:12 exp:12
                && 7<8; //indent:16 exp:16
            i++) //indent:12 exp:12
        { //indent:8 exp:8
        } //indent:8 exp:8

        for (int i=0; i<10 && 4<5 //indent:8 exp:8
                && 7<8; //indent:16 exp:>=12
            i++) { //indent:12 exp:12
        } //indent:8 exp:8

        for (int i=0; i<10 && 4<5 && 7<8; //indent:8 exp:8
            i++) { //indent:12 exp:12
        } //indent:8 exp:8


        for (int i=0; //indent:8 exp:8
            i<10; i++ //indent:12 exp:12
        ) { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        for ( final int index : indices ) { //indent:8 exp:8
            System.err.println(index); //indent:12 exp:12
        } //indent:8 exp:8
        for ( final int index : indices ) //indent:8 exp:8
        { //indent:8 exp:8
            System.err.println(index); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
