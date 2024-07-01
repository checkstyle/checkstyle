//non-compiled with javac: Compilable with Java21                           //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0


/* Config:                                                                  //indent:0 exp:0
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 8                                                         //indent:1 exp:1
 * forceStrictCondition = true                                              //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationRecordPattern {  //indent:0 exp:0
    record ColoredPoint(boolean p, int x, int c) { } //indent:4 exp:4
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { } //indent:4 exp:4

    void test(Object obj) { //indent:4 exp:4
        boolean b = obj instanceof //indent:8 exp:8
                ColoredPoint(_,_,_);  //indent:16 exp:12 warn
        boolean c = obj instanceof //indent:8 exp:8
            ColoredPoint(_,_,_);  //indent:12 exp:12

        boolean bb = obj instanceof //indent:8 exp:8
        ColoredPoint(_,_,_);  //indent:8 exp:12 warn

        if (obj instanceof ColoredPoint(_,_,_)) {} //indent:8 exp:8

        if (obj instanceof //indent:8 exp:8
                ColoredPoint(_,_,_)) {} //indent:16 exp:12 warn
        if (obj instanceof     //indent:8 exp:8
            ColoredPoint(_,_,_)) {} //indent:12 exp:12

        if (obj instanceof  //indent:8 exp:8
        ColoredPoint(_,_,_)) {  } //indent:8 exp:12 warn

        if (obj instanceof //indent:8 exp:8
ColoredPoint(_,_,_)) { }  //indent:0 exp:12 warn
        if (obj instanceof //indent:8 exp:8
ColoredPoint( //indent:0 exp:12 warn
_,  //indent:0 exp:12 warn
_,  //indent:0 exp:12 warn
_)) {} //indent:0 exp:12 warn

        if (obj instanceof //indent:8 exp:8
            ColoredPoint( //indent:12 exp:12
            _,  //indent:12 exp:12
            _,  //indent:12 exp:12
            _)) {} //indent:12 exp:12

        if (obj instanceof ColoredPoint( //indent:8 exp:8
            _,  //indent:12 exp:12
            _,  //indent:12 exp:12
            _)) {} //indent:12 exp:12

        if (obj instanceof //indent:8 exp:8
                Rectangle( //indent:16 exp:12 warn
                ColoredPoint(  //indent:16 exp:12 warn
                        boolean x, //indent:24 exp:12 warn
                int y, //indent:16 exp:12 warn
                        _), //indent:24 exp:12 warn
                ColoredPoint(_,_,_)  //indent:16 exp:12 warn
                ) //indent:16 exp:8 warn
        ) {} //indent:8 exp:8

        if (obj instanceof //indent:8 exp:8
Rectangle(   //indent:0 exp:12 warn
ColoredPoint(   //indent:0 exp:12 warn
        boolean x,  //indent:8 exp:12 warn
        int y,  //indent:8 exp:12 warn
        _),  //indent:8 exp:12 warn
ColoredPoint(_,_,_)  //indent:0 exp:12 warn
        )) {}   //indent:8 exp:12 warn

        if (obj instanceof //indent:8 exp:8
            Rectangle(   //indent:12 exp:12
            ColoredPoint(   //indent:12 exp:12
            boolean x,  //indent:12 exp:12
            int y,  //indent:12 exp:12
            _),  //indent:12 exp:12
            ColoredPoint(_,_,_)  //indent:12 exp:12
            )) {}   //indent:12 exp:8 warn


    }  //indent:4 exp:4

    void testSwitch(Object obj) {  //indent:4 exp:4
        switch (obj) { //indent:8 exp:8
            case Rectangle( //indent:12 exp:12
        ColoredPoint _,  //indent:8 exp:16 warn
        ColoredPoint _) -> System.out.println("Rectangle");  //indent:8 exp:16 warn
            default -> {} //indent:12 exp:12
        } //indent:8 exp:8
        switch (obj) { //indent:8 exp:8
            case Rectangle( //indent:12 exp:12
                ColoredPoint _,  //indent:16 exp:16
                ColoredPoint _) -> System.out.println("Rectangle");  //indent:16 exp:16
            default -> {} //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
}//indent:0 exp:0
