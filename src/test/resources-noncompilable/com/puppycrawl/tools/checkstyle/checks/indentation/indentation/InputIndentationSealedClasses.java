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
public class InputIndentationRecordPattern { //indent:0 exp:0
sealed //indent:0 exp:4 warn
 class Test //indent:1 exp:4 warn
     permits A, B { } //indent:5 exp:4 warn

    sealed class A extends Test permits D, E, F { } //indent:4 exp:4

final class D //indent:0 exp:4 warn
        extends A { } //indent:8 exp:4 warn

    sealed //indent:4 exp:4
    class B extends Test //indent:4 exp:8 warn
permits //indent:0 exp:8 warn
            C { } //indent:12 exp:8 warn

    non-sealed //indent:4 exp:4
    class //indent:4 exp:8 warn
    C extends B { } //indent:4 exp:8 warn

non-sealed //indent:0 exp:4 warn
        class E extends A { } //indent:8 exp:4 warn

    sealed class F extends A //indent:4 exp:4
        permits R { } //indent:8 exp:8

    final class R extends F { } //indent:4 exp:4

} //indent:0 exp:0
