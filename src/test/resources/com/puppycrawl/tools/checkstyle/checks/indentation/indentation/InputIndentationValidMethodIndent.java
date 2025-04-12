package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.Arrays; //indent:0 exp:0

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
public class InputIndentationValidMethodIndent extends Object { //indent:0 exp:0

    // ctor with rcurly on same line //indent:4 exp:4
    public InputIndentationValidMethodIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    private InputIndentationValidMethodIndent(boolean test) { //indent:4 exp:4
        boolean test2 = true; //indent:8 exp:>=8

        int i = 4 +  //indent:8 exp:8
            4; //indent:12 exp:>=12
    } //indent:4 exp:4


    private InputIndentationValidMethodIndent(boolean test, //indent:4 exp:4
        boolean test2) { //indent:8 exp:>=8
        boolean test3 = true; //indent:8 exp:>=8

        int i = 4 +  //indent:8 exp:8
            4; //indent:12 exp:>=12
    } //indent:4 exp:4


    private InputIndentationValidMethodIndent(boolean test, //indent:4 exp:4
        boolean test2, boolean test3)  //indent:8 exp:>=8
    { //indent:4 exp:4
        boolean test4 = true; //indent:8 exp:8

        int i = 4 +  //indent:8 exp:8
            4; //indent:12 exp:>=12
    } //indent:4 exp:4

    // ctor with rcurly on next line //indent:4 exp:4
    public InputIndentationValidMethodIndent(int dummy) //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // method with rcurly on next line //indent:4 exp:4
    public void method2() //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // params on multiple lines //indent:4 exp:4
    public void method2(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:>=8
    { //indent:4 exp:4
    } //indent:4 exp:4

    // params on multiple lines //indent:4 exp:4
    public void method3(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:>=8
    { //indent:4 exp:4
        System.getProperty("foo"); //indent:8 exp:8
    } //indent:4 exp:4

    // strange IMHO, but I suppose this should be allowed //indent:4 exp:4
    public //indent:4 exp:4
    void //indent:4 exp:8 warn
    method5() { //indent:4 exp:8 warn
    } //indent:4 exp:4

    private int[] getArray() { //indent:4 exp:4
        return new int[] {1}; //indent:8 exp:8
    } //indent:4 exp:4

    private void indexTest() { //indent:4 exp:4
        getArray()[0] = 2; //indent:8 exp:8
    } //indent:4 exp:4

    // the following lines have tabs //indent:4 exp:4
	@SuppressWarnings( //indent:4 exp:4
		value="" //indent:8 exp:8
	) //indent:4 exp:4
	public void testStartOfSequence() { //indent:4 exp:4
	} //indent:4 exp:4
} //indent:0 exp:0
