package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.awt.event.ActionEvent; //indent:0 exp:0
import java.awt.event.ActionListener; //indent:0 exp:0

import javax.swing.JButton; //indent:0 exp:0

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
  public class InputIndentationInvalidClassDefIndent1 extends Object { //indent:2 exp:0 warn
} //indent:0 exp:0

final class InputIndentationValidClassDefIndent66 extends Object { //indent:0 exp:0

  class foo { } //indent:2 exp:4 warn

      class fooBS { } //indent:6 exp:4 warn

  class foo2 { public int x; } //indent:2 exp:4 warn

    class foo3 {  //indent:4 exp:4
      public  //indent:6 exp:8 warn
            int x;  //indent:12 exp:>=10
    } //indent:4 exp:4

    class foo3b {  //indent:4 exp:4
        public  //indent:8 exp:8
          int x;  //indent:10 exp:>=12 warn
    } //indent:4 exp:4

    class foo4 {  //indent:4 exp:4
      public int x;  //indent:6 exp:8 warn
    } //indent:4 exp:4

    class foo4c {  //indent:4 exp:4
        public int x;  //indent:8 exp:8
      } //indent:6 exp:4 warn

      class foo4b {  //indent:6 exp:4 warn
        public int x;  //indent:8 exp:8
    } //indent:4 exp:4

    private void myMethod() { //indent:4 exp:4
      class localFoo { //indent:6 exp:8 warn

        } //indent:8 exp:8
          class localFoo1 { //indent:10 exp:8 warn

          } //indent:10 exp:8 warn

        class localFoo2 { //indent:8 exp:8
          int x; //indent:10 exp:12 warn

            int func() { return 3; } //indent:12 exp:12
        } //indent:8 exp:8

          new JButton().addActionListener(new ActionListener() //indent:10 exp:8 warn
        { //indent:8 exp:10,14 warn
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12

            } //indent:12 exp:12
        }); //indent:8 exp:10,14 warn

        new JButton().addActionListener(new ActionListener()  //indent:8 exp:8
      { //indent:6 exp:8,12 warn
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12

            } //indent:12 exp:12
      }); //indent:6 exp:8,12 warn

        new JButton().addActionListener(new ActionListener()  //indent:8 exp:8
        { //indent:8 exp:8,12
          public void actionPerformed(ActionEvent e) { //indent:10 exp:12 warn

          } //indent:10 exp:12 warn
        }); //indent:8 exp:8,12

        new JButton().addActionListener(new ActionListener() { //indent:8 exp:8
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12
                int i = 2; //indent:16 exp:16
            } //indent:12 exp:12
        }); //indent:8 exp:8,12

        Object o = new ActionListener()  //indent:8 exp:8
        { //indent:8 exp:8,12
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12

            } //indent:12 exp:12
        }; //indent:8 exp:8,12

        myfunc2(10, 10, 10, //indent:8 exp:8
            myfunc3(11, 11, //indent:12 exp:>=12
                11, 11), //indent:16 exp:>=16
            10, 10, //indent:12 exp:>=12
            10); //indent:12 exp:>=12

    } //indent:4 exp:4

    private void myfunc2(int a, int b, int c, int d, int e, int f, int g) { //indent:4 exp:4
    } //indent:4 exp:4

    private int myfunc3(int a, int b, int c, int d) { //indent:4 exp:4
        return 1; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
