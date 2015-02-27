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
public class InputValidCommaIndent { //indent:0 exp:0

    /** Creates a new instance of InputValidCommaIndent */ //indent:4 exp:4
    public InputValidCommaIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    public void method1(int x, int y, int z) { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8
        int i, j = 2, //indent:8 exp:8
            k = 4, //indent:12 exp:>=12
            l, //indent:12 exp:>=12
            m = 4; //indent:12 exp:>=12

        boolean longVarName = true; //indent:8 exp:8
        boolean myotherLongVariableName = false; //indent:8 exp:8
        if (j == 2 || longVarName == true || myotherLongVariableName == true || myotherLongVariableName == false || longVarName == true) { //indent:8 exp:8
        } //indent:8 exp:8

        if ((j == 2 && k == 3) //indent:8 exp:8
              || test) { //indent:14 exp:>=12
            System.out.println("test"); //indent:12 exp:12
        } //indent:8 exp:8


    } //indent:4 exp:4

    public void method1(int a, int x, //indent:4 exp:4
        int y, int z) { //indent:8 exp:>=8
    } //indent:4 exp:4
} //indent:0 exp:0
