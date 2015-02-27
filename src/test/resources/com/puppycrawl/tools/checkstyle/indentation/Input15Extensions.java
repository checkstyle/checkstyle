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
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
@interface MyAnnotation3 { //indent:0 exp:0
    String name(); //indent:4 exp:4
    int version(); //indent:4 exp:4
} //indent:0 exp:0

@MyAnnotation3(name = "ABC", version = 1) //indent:0 exp:0
public class Input15Extensions //indent:0 exp:0
{ //indent:0 exp:0

} //indent:0 exp:0

enum Enum1 //indent:0 exp:0
{ //indent:0 exp:0
    A, B, C; //indent:4 exp:4
    Enum1() {} //indent:4 exp:4
    public String toString() { //indent:4 exp:4
        return ""; //some custom implementation //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

interface TestRequireThisEnum //indent:0 exp:0
{ //indent:0 exp:0
    enum DAY_OF_WEEK //indent:4 exp:4
    { //indent:4 exp:4
        SUNDAY, //indent:8 exp:8
        MONDAY, //indent:8 exp:8
        TUESDAY, //indent:8 exp:8
        WEDNESDAY, //indent:8 exp:8
        THURSDAY, //indent:8 exp:8
        FRIDAY, //indent:8 exp:8
        SATURDAY //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
