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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationInvalidMethodIndent2 { //indent:0 exp:0

    public InputIndentationInvalidMethodIndent2(int dummy, int dummy2) //indent:4 exp:4
    { //indent:4 exp:4
    System.getProperty("foo"); //indent:4 exp:8 warn
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

    void method6a() //indent:4 exp:4
    { //indent:4 exp:4
      boolean test = true; //indent:6 exp:8 warn
      if (test) { //indent:6 exp:8 warn
          System.getProperty("foo"); //indent:10 exp:12 warn
      } //indent:6 exp:8 warn

        System.identityHashCode("methods are: " + //indent:8 exp:8
          Arrays.asList( //indent:10 exp:12 warn
                new String[] {"method"}).toString()); //indent:16 exp:>=14


        System.identityHashCode("methods are: " + //indent:8 exp:8
            Arrays.asList( //indent:12 exp:>=12
              new String[] {"method"}).toString()); //indent:14 exp:>=16 warn

        System.identityHashCode("methods are: " //indent:8 exp:8
          + Arrays.asList( //indent:10 exp:12 warn
                new String[] {"method"}).toString()); //indent:16 exp:>=14

        System.identityHashCode("methods are: " //indent:8 exp:8
            + Arrays.asList( //indent:12 exp:>=12
              new String[] {"method"}).toString()); //indent:14 exp:>=16 warn


        String blah = (String) System.getProperty( //indent:8 exp:8
          new String("type")); //indent:10 exp:12 warn


        String blah1 = (String) System.getProperty( //indent:8 exp:8
          new String("type") //indent:10 exp:12 warn
      ); //indent:6 exp:8 warn

        System.identityHashCode("methods are: " + Arrays.asList( //indent:8 exp:8
            new String[] {"method"}).toString() //indent:12 exp:>=12
      ); //indent:6 exp:8 warn
    } //indent:4 exp:4

    private int myfunc3(int a, int b, int c, int d) { //indent:4 exp:4
        return 1; //indent:8 exp:8
    } //indent:4 exp:4

    private void myFunc() //indent:4 exp:4
        throws Exception //indent:8 exp:8
    { //indent:4 exp:4
    } //indent:4 exp:4

    void method7() { //indent:4 exp:4
        // return incorrectly indented //indent:8 exp:8
    return; //indent:4 exp:8 warn
    } //indent:4 exp:4

    void method8() { //indent:4 exp:4
        // thow incorrectly indented //indent:8 exp:8
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
