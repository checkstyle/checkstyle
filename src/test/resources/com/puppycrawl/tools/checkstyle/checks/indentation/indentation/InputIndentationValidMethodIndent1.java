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
public class InputIndentationValidMethodIndent1 extends Object { //indent:0 exp:0

    // method with rcurly on same line //indent:4 exp:4
    public String method1() { //indent:4 exp:4
        return "hi"; //indent:8 exp:>=8
    } //indent:4 exp:4

    // method with a bunch of params //indent:4 exp:4
    public int method2(int x, int y, int w, int h) //indent:4 exp:4
    { //indent:4 exp:4
        return 1; //indent:8 exp:8
    } //indent:4 exp:4

    // params on multiple lines //indent:4 exp:4
    public void method4(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:8
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        int i = 4 +  //indent:8 exp:8
            4; //indent:12 exp:>=12

        i += 5; //indent:8 exp:8
        i += 5  //indent:8 exp:8
            + 4; //indent:12 exp:>=12
        if (test)  //indent:8 exp:8
        { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } else { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        for (int j=0;j<10; j++) { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8

        myfunc2(10, 10, 10, //indent:8 exp:8
            myfunc3(11, 11, //indent:12 exp:>=12
                11, 11), //indent:16 exp:>=16
            10, 10, //indent:12 exp:>=12
            10); //indent:12 exp:>=12

        myfunc3(11, 11, Integer. //indent:8 exp:8
                getInteger("mytest").intValue(), //indent:16 exp:>=12
            11); //indent:12 exp:>=12

        myfunc3( //indent:8 exp:8
            1, //indent:12 exp:>=12
            2,  //indent:12 exp:>=12
                3,  //indent:16 exp:>=12
                4); //indent:16 exp:>=12
    } //indent:4 exp:4

    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) { //indent:4 exp:4
    } //indent:4 exp:4

    private int myfunc3(int a, int b, int c, int d) { //indent:4 exp:4
        return 1; //indent:8 exp:8
    } //indent:4 exp:4

    void method6() { //indent:4 exp:4
        System.identityHashCode("methods are: " + Arrays.asList( //indent:8 exp:8
            new String[] {"method"}).toString()); //indent:12 exp:>=12

        System.identityHashCode("methods are: " + Arrays.asList( //indent:8 exp:8
            new String[] {"method"} //indent:12 exp:>=12
        ).toString()); //indent:8 exp:8

        System.identityHashCode("methods are: " + Arrays.asList( //indent:8 exp:8
            new String[] {"method"}).toString() //indent:12 exp:>=12
        ); //indent:8 exp:8

        myfunc2(3, 4, 5,  //indent:8 exp:8
            6, 7, 8, 9); //indent:12 exp:>=12

        myfunc2(3, 4, method2(3, 4, 5, 6) + 5,  //indent:8 exp:8
            6, 7, 8, 9); //indent:12 exp:>=12

        System.identityHashCode("methods are: " +  //indent:8 exp:8
            Arrays.asList( //indent:12 exp:>=12
                new String[] {"method"}).toString()); //indent:16 exp:>=16

        System.identityHashCode("methods are: "  //indent:8 exp:8
            + Arrays.asList( //indent:12 exp:>=12
                new String[] {"method"}).toString()); //indent:16 exp:>=16

        String blah = (String) System.getProperty( //indent:8 exp:8
            new String("type")); //indent:12 exp:>=12

        System.identityHashCode(method1() + "mytext"  //indent:8 exp:8
            + " at indentation level not at correct indentation, "  //indent:12 exp:>=12
            + method1()); //indent:12 exp:>=12

        System.identityHashCode( //indent:8 exp:8
            method1() + "mytext"  //indent:12 exp:>=12
                + " at indentation level not at correct indentation, "  //indent:16 exp:>=12
                + method1()); //indent:16 exp:>=12

        String.CASE_INSENSITIVE_ORDER.toString() //indent:8 exp:8
            .equals("blah"); //indent:12 exp:>=12
    } //indent:4 exp:4
} //indent:0 exp:0
