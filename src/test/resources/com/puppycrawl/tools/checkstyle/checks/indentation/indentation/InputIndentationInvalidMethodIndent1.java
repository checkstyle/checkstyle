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
public class InputIndentationInvalidMethodIndent1 { //indent:0 exp:0

    /** Creates a new instance of InputInvalidMethodIndent */ //indent:4 exp:4
    public InputIndentationInvalidMethodIndent1() { //indent:4 exp:4
      } //indent:6 exp:4 warn

    // ctor with rcurly on next line //indent:4 exp:4
      public InputIndentationInvalidMethodIndent1(int dummy) //indent:6 exp:4 warn
  { //indent:2 exp:4 warn
      } //indent:6 exp:4 warn

    // method with rcurly on same line //indent:4 exp:4
  public void method() { //indent:2 exp:4 warn
      } //indent:6 exp:4 warn

    // method with rcurly on next line //indent:4 exp:4
    public void method2() //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // method with a bunch of params //indent:4 exp:4
    public int method2(int x, int y, int w, int h) { //indent:4 exp:4
        return 1; //indent:8 exp:8
    } //indent:4 exp:4

    // params on multiple lines //indent:4 exp:4
    public void method2(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:8
    { //indent:4 exp:4
    } //indent:4 exp:4

    // params on multiple lines //indent:4 exp:4
    public void method3(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:8
    { //indent:4 exp:4
        System.getProperty("foo"); //indent:8 exp:8
    } //indent:4 exp:4



    // params on multiple lines //indent:4 exp:4
    public void method4(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:8
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

     public //indent:5 exp:4 warn
     final //indent:5 exp:9 warn
     void //indent:5 exp:9 warn
    method5() //indent:4 exp:9 warn
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) { //indent:4 exp:4
    } //indent:4 exp:4

    private void myMethod() //indent:4 exp:4
    { //indent:4 exp:4
        myfunc2(3, 4, 5, //indent:8 exp:8
          6, 7, 8, 9); //indent:10 exp:12 warn

        myfunc2(3, 4, method2(3, 4, 5, 6) + 5, //indent:8 exp:8
          6, 7, 8, 9); //indent:10 exp:12 warn


//     : this is not illegal, but probably should be //indent:0 exp:0
//        myfunc3(11, 11, Integer. //indent:0 exp:0
//            getInteger("mytest").intValue(), //indent:0 exp:0
//            11); //indent:0 exp:0


        String.CASE_INSENSITIVE_ORDER.toString() //indent:8 exp:8
      .equals("blah"); //indent:6 exp:12 warn


    } //indent:4 exp:4
} //indent:0 exp:0
