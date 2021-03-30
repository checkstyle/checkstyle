/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationNewWithTernaryOp { //indent:0 exp:0
    public Integer foo() { //indent:4 exp:4
        boolean flag = true; //indent:8 exp:8
        Integer result = flag ? //indent:8 exp:8
                new Integer(1) : //indent:16 exp:16
                new Integer(2); //indent:16 exp:16
        return flag ? //indent:8 exp:8
                new Integer(1) : new Integer(2); //indent:16 exp:16
    } //indent:4 exp:4

    public void method() { //indent:4 exp:4
        int num1 = 10; //indent:8 exp:8
        int num2 = 20; //indent:8 exp:8
        int res=(num1>num2) ? //indent:8 exp:8
                (num1+num2): //indent:16 exp:16
                new Integer(2); //indent:16 exp:16
    } //indent:4 exp:4
    public void method2() { //indent:4 exp:4
        int x=69;  //indent:8 exp:8
        int y=89;  //indent:8 exp:8
        int z=79;  //indent:8 exp:8
        int largestNumber= (x < y) ? //indent:8 exp:8
                (new Integer(2) < z ? //indent:16 exp:16
                x : //indent:16 exp:16
                new Integer(2)) : (y > z ? //indent:16 exp:16
                new Integer(2) : //indent:16 exp:16
                        new Integer(3)); //indent:24 exp:16 warn
    } //indent:4 exp:4
} //indent:0 exp:0

