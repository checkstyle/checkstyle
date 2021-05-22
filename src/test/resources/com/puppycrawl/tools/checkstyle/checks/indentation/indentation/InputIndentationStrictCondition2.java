/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 4                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = true                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.Serializable; //indent:0 exp:0
import java.lang.Comparable; //indent:0 exp:0
import java.lang.StringBuilder; //indent:0 exp:0
import java.lang.Thread; //indent:0 exp:0
import java.util.Collection; //indent:0 exp:0
import java.util.Map; //indent:0 exp:0

public class InputIndentationStrictCondition2 { //indent:0 exp:0
    void method(Thread foo) { //indent:4 exp:4
        method( //indent:8 exp:8
            new Thread() { //indent:12 exp:12
                public void run() { //indent:16 exp:16
                } //indent:16 exp:16
            } //indent:12 exp:12
        ); //indent:8 exp:8
        } //indent:8 exp:4 warn

    void method2(boolean test) { //indent:4 exp:4
        if ((test //indent:8 exp:8
                || 1 < 2 //indent:16 exp:20 warn
                || 3 < 4) //indent:16 exp:20 warn
                && 5 == 6 //indent:16 exp:16
                && 7 == 8) { //indent:16 exp:16
            System.getProperty("blah");  //indent:12 exp:12
        } //indent:8 exp:8

        if (1 < 2 //indent:8 exp:8
                && ( //indent:16 exp:16
                    test //indent:20 exp:20
                    && 3 < 4) //indent:20 exp:20
                || (5 < 6)) { //indent:16 exp:16
            throw new RuntimeException(); //indent:12 exp:12
        } //indent:8 exp:8

        if (//indent:8 exp:8
                test //indent:16 exp:16
                && 3 < 4 //indent:16 exp:16
                || (5 < 6)) { //indent:16 exp:16
            throw new RuntimeException(); //indent:12 exp:12
        } //indent:8 exp:8

        if (1 < 2 //indent:8 exp:8
                && getSomething( //indent:16 exp:16
                      "test") < 0) { //indent:22 exp:20,32 warn
            throw new RuntimeException(); //indent:12 exp:12
        } //indent:8 exp:8

        if ( //indent:8 exp:8
                test //indent:16 exp:16
            ) { //indent:12 exp:12
            throw new RuntimeException(); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    private //indent:4 exp:4
        < //indent:8 exp:8
            T extends Object //indent:12 exp:12
            & Comparable<? super T> //indent:12 exp:12
            & StrictCondition<? super Comparable<? super Number>, //indent:12 exp:12
                ? extends Map<Long, //indent:16 exp:16
                    Comparable<? super T>>, //indent:20 exp:20
                ? super int[]> //indent:16 exp:16
            & Serializable> T max(Collection<? extends T> coll){ //indent:12 exp:12
        return null;  //indent:8 exp:8
    } //indent:4 exp:4

    private int getSomething(String str) { //indent:4 exp:4
        return new StringBuilder(str).lastIndexOf( //indent:8 exp:8
                new StringBuilder("file") //indent:16 exp:16
                    .insert(0, String.valueOf(new String("str"))) //indent:20 exp:20
                    .replace( //indent:20 exp:20
                        0, //indent:24 exp:24
                        1, //indent:24 exp:24
                        new StringBuilder("otherFile").toString() //indent:24 exp:24
                    ).toString(), //indent:20 exp:20
                new StringBuilder("resource") //indent:16 exp:16
                    .insert(0, String.valueOf(new String("foo"))) //indent:20 exp:20
                    .replace(2, 3, new StringBuilder("bar").toString()) //indent:20 exp:20
                    .toString().length() //indent:20 exp:20
        ); //indent:8 exp:8
    } //indent:4 exp:4

    private void method3(String param1, //indent:4 exp:4
        String param2, //indent:8 exp:8
        String param3) { //indent:8 exp:8
        for (int i = 0; i < 100; //indent:8 exp:8
            i++) { //indent:12 exp:12
            System.getenv(param1); //indent:12 exp:12
    	} //indent:8 exp:8
        if (!param1.equals(param2)) { //indent:8 exp:8
            method3(param3, param3, //indent:12 exp:12
                param1 + param2); //indent:16 exp:16
        } //indent:8 exp:8

        final int loooooooongName = getSomething( //indent:8 exp:8
              "test"); //indent:14 exp:16,49 warn
        final int loooooooongName2 = getSomething( //indent:8 exp:8
                                                  "test"); //indent:50 exp:50
    } //indent:4 exp:4

    @InputIndentationStrictCondition2Annotation(value = {"true", //indent:4 exp:4
            "false"}, //indent:12 exp:12
        value2 = "blub") //indent:8 exp:8
    private void method4(String param1, //indent:4 exp:4
                         String param2) { //indent:25 exp:25
    	throw new RuntimeException(); //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

interface StrictCondition<A, B, C> {} //indent:0 exp:0

@interface InputIndentationStrictCondition2Annotation { //indent:0 exp:0
    String[] value(); //indent:4 exp:4
    String value2(); //indent:4 exp:4
} //indent:0 exp:0
