package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidMethodIndent { //indent:0 exp:0

    /** Creates a new instance of InputInalidMethodIndent */ //indent:4 exp:4
    public InputInvalidMethodIndent() { //indent:4 exp:4
      } //indent:6 exp:4 warn

    // ctor with rcurly on next line //indent:4 exp:4
      public InputInvalidMethodIndent(int dummy) //indent:6 exp:4 warn
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

   public //indent:3 exp:4 warn
   final //indent:3 exp:7 warn
   void //indent:3 exp:7 warn
     method6() //indent:5 exp:7 warn
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    public InputInvalidMethodIndent(int dummy, int dummy2) //indent:4 exp:4
    { //indent:4 exp:4
    System.getProperty("foo"); //indent:4 exp:8 warn
    } //indent:4 exp:4

    void method6a() //indent:4 exp:4
    { //indent:4 exp:4
      boolean test = true; //indent:6 exp:8 warn
      if (test) { //indent:6 exp:8 warn
          System.getProperty("foo"); //indent:10 exp:12 warn
      } //indent:6 exp:8 warn

        System.out.println("methods are: " + //indent:8 exp:8
          Arrays.asList( //indent:10 exp:12 warn
                new String[] {"method"}).toString()); //indent:16 exp:>=14


        System.out.println("methods are: " + //indent:8 exp:8
            Arrays.asList( //indent:12 exp:>=12
              new String[] {"method"}).toString()); //indent:14 exp:>=14

        System.out.println("methods are: " //indent:8 exp:8
          + Arrays.asList( //indent:10 exp:12 warn
                new String[] {"method"}).toString()); //indent:16 exp:>=14

        System.out.println("methods are: " //indent:8 exp:8
            + Arrays.asList( //indent:12 exp:>=12
              new String[] {"method"}).toString()); //indent:14 exp:>=12


        String blah = (String) System.getProperty( //indent:8 exp:8
          new String("type")); //indent:10 exp:12 warn


        String blah1 = (String) System.getProperty( //indent:8 exp:8
          new String("type") //indent:10 exp:12 warn
      ); //indent:6 exp:8 warn

        System.out.println("methods are: " + Arrays.asList( //indent:8 exp:8
            new String[] {"method"}).toString() //indent:12 exp:>=12
      ); //indent:6 exp:8 warn
    } //indent:4 exp:4


    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) { //indent:4 exp:4
    } //indent:4 exp:4

    private int myfunc3(int a, int b, int c, int d) { //indent:4 exp:4
        return 1; //indent:8 exp:8
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


        System.out.toString() //indent:8 exp:8
      .equals("blah"); //indent:6 exp:12 warn


    } //indent:4 exp:4

    private void myFunc() //indent:4 exp:4
      throws Exception //indent:6 exp:6
    { //indent:4 exp:4
    } //indent:4 exp:4

    void method7() { //indent:4 exp:4
        // return incorrectly indented //indent:8 exp:8
    return; //indent:4 exp:8 warn
    } //indent:4 exp:4

    void method8() { //indent:4 exp:4
        // thow invorrectly indented //indent:8 exp:8
    throw new RuntimeException(""); //indent:4 exp:8 warn
    } //indent:4 exp:4

    public //indent:4 exp:4
int[] //indent:0 exp:8 warn
    method9() //indent:4 exp:8 warn
    { //indent:4 exp:4
        return null; //indent:8 exp:8
    } //indent:4 exp:4

    private int[] getArray() { //indent:4 exp:4
        return new int[] {1}; //indent:8 exp:8
    } //indent:4 exp:4

    private void indexTest() { //indent:4 exp:4
            getArray()[0] = 2; //indent:12 exp:8 warn
    } //indent:4 exp:4

    /* package scope */ void methodWithCommentBefore() {} //indent:4 exp:4
} //indent:0 exp:0
