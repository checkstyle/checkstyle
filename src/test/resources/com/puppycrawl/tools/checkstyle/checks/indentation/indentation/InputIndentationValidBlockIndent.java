package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.Closeable; //indent:0 exp:0
import java.util.stream.Stream; //indent:0 exp:0

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
public class InputIndentationValidBlockIndent { //indent:0 exp:0

    /** Creates a new instance of InputValidBlockIndent */ //indent:4 exp:4
    public InputIndentationValidBlockIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    public void method1() { //indent:4 exp:4

        { } //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8
        { //indent:8 exp:8
            int var = 3; //indent:12 exp:12

            var += 3; //indent:12 exp:12
        } //indent:8 exp:8


        {  int var = 5; } //indent:8 exp:8

        { //indent:8 exp:8
            int var = 3; //indent:12 exp:12

            var += 3; //indent:12 exp:12

            { //indent:12 exp:12
                int innerVar = 4; //indent:16 exp:16

                innerVar += var; //indent:16 exp:16
            } //indent:12 exp:12
        } //indent:8 exp:8

    } //indent:4 exp:4

    static { int var = 4; } //indent:4 exp:4


    static { //indent:4 exp:4
        int var = 4;  //indent:8 exp:8
    } //indent:4 exp:4

    static  //indent:4 exp:4
    { //indent:4 exp:4
        int var = 4;  //indent:8 exp:8
    } //indent:4 exp:4

    { int var = 4; } //indent:4 exp:4


    { //indent:4 exp:4
        int var = 4;  //indent:8 exp:8
    } //indent:4 exp:4

    { //indent:4 exp:4
        int var = 4;  //indent:8 exp:8
    } //indent:4 exp:4


} //indent:0 exp:0

class bug1251988 //indent:0 exp:0
{ //indent:0 exp:0
    private int a; //indent:4 exp:4

    // non static initializer //indent:4 exp:4
    { //indent:4 exp:4
        if (a == 1) //indent:8 exp:8
        { //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

class bug1260079 //indent:0 exp:0
{ //indent:0 exp:0
    public bug1260079() //indent:4 exp:4
    { //indent:4 exp:4
        new Thread() //indent:8 exp:8
        { //indent:8 exp:8
            public void run() //indent:12 exp:12
            { //indent:12 exp:12
                System.identityHashCode("ran"); //indent:16 exp:16
            } //indent:12 exp:12
        }.start(); //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

class bug1336737 { //indent:0 exp:0
    private static enum Command { //indent:4 exp:4
        IMPORT("import"), //indent:8 exp:8
        LIST("list"); //indent:8 exp:8
        private final String c; //indent:8 exp:8
        Command(String c) { this.c = c; } //indent:8 exp:8
        public String toString() { return c; } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
