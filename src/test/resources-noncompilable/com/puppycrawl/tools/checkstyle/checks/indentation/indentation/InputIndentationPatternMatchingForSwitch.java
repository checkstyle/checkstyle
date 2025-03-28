//non-compiled with javac: Compilable with Java21        //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;//indent:0 exp:0
//indent:0 exp:0
//indent:0 exp:0
/* Config:                                                                  //indent:0 exp:0
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 8                                                         //indent:1 exp:1
 * forceStrictCondition = true                                             //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationPatternMatchingForSwitch {                     //indent:0 exp:0
    record Point(int x, int y) { }                                           //indent:4 exp:4
    record square(Point upperLeft, Point lowerRight) { }                    //indent:4 exp:4

    public void test1() {   //indent:4 exp:4
        Point p = new Point(1, 2); //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case Point(int x, int y) //indent:12 exp:12
            when x > 0 && y > 0 -> System.out.println(x + y); //indent:12 exp:16 warn
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case Point(int x, int y) //indent:12 exp:12
                when x > 0 && y > 0 -> System.out.println(x + y); //indent:16 exp:16
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    public void test2() { //indent:4 exp:4
        Point p = new Point(1, 2); //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case    //indent:12 exp:12
                    Point(int x, int y) //indent:20 exp:20
                when x > 0 && y > 0 -> System.out.println(x + y); //indent:16 exp:16
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case    //indent:12 exp:12
                    Point(int x, int y) //indent:20 exp:20
                    when x > 0 && y > 0 -> System.out.println(x + y); //indent:20 exp:20
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case    //indent:12 exp:12
                    Point(int x, int y) //indent:20 exp:20
                            when x > 0 && y > 0 -> System.out.println(x + y); //indent:28 exp:28
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case    //indent:12 exp:12
                    Point(int x, int y) //indent:20 exp:20
            when x > 0 && y > 0 -> System.out.println(x + y); //indent:12 exp:16 warn
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    public void test3() { //indent:4 exp:4
        Point p = new Point(1, 2); //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case //indent:12 exp:12
                Point(int x, int y) //indent:16 exp:16
                when x > 0 && y > 0 -> System.out.println(x + y); //indent:16 exp:16
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case //indent:12 exp:12
            Point(int x, int y) //indent:12 exp:16 warn
            when x > 0 && y > 0 -> System.out.println(x + y); //indent:12 exp:16 warn
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case //indent:12 exp:12
    Point(int x, int y) //indent:4 exp:16 warn
    when x > 0 && y > 0 -> System.out.println(x + y); //indent:4 exp:16 warn
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case //indent:12 exp:12
                Point(int x, int y) //indent:16 exp:16
                            when x > 0 && y > 0 -> System.out.println(x + y); //indent:28 exp:28
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
        switch (p) { //indent:8 exp:8
            case    //indent:12 exp:12
Point(int x, int y) //indent:0 exp:16 warn
when          //indent:0 exp:16 warn
x > 0 && y > 0          //indent:0 exp:16 warn
->        //indent:0 exp:16 warn
System.out.println(x + y); //indent:0 exp:20 warn
            case Point(int x, int y) -> System.out.println("error"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
}//indent:0 exp:0
