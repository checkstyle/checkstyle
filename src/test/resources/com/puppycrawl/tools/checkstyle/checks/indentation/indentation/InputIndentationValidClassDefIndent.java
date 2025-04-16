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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidClassDefIndent //indent:0 exp:0
    extends java.awt.event.MouseAdapter implements java.awt.event.MouseListener { //indent:4 exp:>=4


} //indent:0 exp:0

final class InputIndentationValidClassDefIndent6 extends Object { //indent:0 exp:0

    class foo { } //indent:4 exp:4


    class foo2 { public int x; } //indent:4 exp:4


    class foo3 {  //indent:4 exp:4
        public  //indent:8 exp:8
        int x;  //indent:8 exp:>=12 warn
    } //indent:4 exp:4


    class foo4 {  //indent:4 exp:4
        public int x;  //indent:8 exp:8
    } //indent:4 exp:4


    private void myMethod() { //indent:4 exp:4
        class localFoo { //indent:8 exp:8

        } //indent:8 exp:8

        class localFoo2 { //indent:8 exp:8
            int x; //indent:12 exp:12

            int func() { return 3; } //indent:12 exp:12
        } //indent:8 exp:8


        //     : this is broken right now: //indent:8 exp:8
        //   1) this is both an expression and an OBJBLOCK //indent:8 exp:8
        //   2) methods aren't yet parsed //indent:8 exp:8
        //   3) only CLASSDEF is handled now, not OBJBLOCK //indent:8 exp:8
        new JButton().addActionListener(new ActionListener()  //indent:8 exp:8
        { //indent:8 exp:8
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12

            } //indent:12 exp:12
        }); //indent:8 exp:8


        new JButton().addActionListener(new ActionListener() { //indent:8 exp:8
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12
                int i = 2; //indent:16 exp:16
            } //indent:12 exp:12
        }); //indent:8 exp:8

        Object o = new ActionListener()  //indent:8 exp:8
        { //indent:8 exp:8
            public void actionPerformed(ActionEvent e) { //indent:12 exp:12

            } //indent:12 exp:12
        }; //indent:8 exp:8

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

    /** The less than or equal operator. */ //indent:4 exp:4
    public static final Operator LT_OR_EQUAL = //indent:4 exp:4
        new Operator( //indent:8 exp:8
            "<=", //indent:12 exp:>=12
            new OperatorHelper() //indent:12 exp:>=12
            { //indent:12 exp:12
                public boolean compare(int value1, int value2) //indent:16 exp:16
                { //indent:16 exp:16
                    return (value1 <= value2); //indent:20 exp:20
                } //indent:16 exp:16

                public boolean co(Comparable<Object> ob1, Comparable<Object> ob2) //indent:16 exp:16
                { //indent:16 exp:16
                    return (ob1.compareTo(ob2) <= 0); //indent:20 exp:20
                } //indent:16 exp:16
            }); //indent:12 exp:12
} //indent:0 exp:0
