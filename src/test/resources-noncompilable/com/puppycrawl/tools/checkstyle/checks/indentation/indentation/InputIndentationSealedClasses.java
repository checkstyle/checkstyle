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
public class InputIndentationSealedClasses { //indent:0 exp:0
sealed //indent:0 exp:4 warn
 class Test //indent:1 exp:4 warn
     permits A, B { } //indent:5 exp:4 warn

    sealed //indent:4 exp:4
    class Test2 //indent:4 exp:8 warn
    permits X, Y, FF { } //indent:4 exp:8 warn

    sealed //indent:4 exp:4
        class Test3 //indent:8 exp:8
        permits W, Z { } //indent:8 exp:8

    sealed class A extends Test permits D, DD, E, F, EE, O { } //indent:4 exp:4

final class D //indent:0 exp:4 warn
        extends A { } //indent:8 exp:4 warn

    final class DD //indent:4 exp:4
    extends A { } //indent:4 exp:8 warn

    final class O //indent:4 exp:4
        extends A { } //indent:8 exp:8

    sealed //indent:4 exp:4
    class B extends Test //indent:4 exp:8 warn
permits //indent:0 exp:8 warn
            C, CC { } //indent:12 exp:8 warn

    sealed //indent:4 exp:4
        class FF extends Test2 //indent:8 exp:8
        permits //indent:8 exp:8
        T { } //indent:8 exp:8

    non-sealed //indent:4 exp:4
    class //indent:4 exp:8 warn
    C extends B { } //indent:4 exp:8 warn

    non-sealed //indent:4 exp:4
        class //indent:8 exp:8
        CC extends B { } //indent:8 exp:8

non-sealed //indent:0 exp:4 warn
        class E extends A { } //indent:8 exp:4 warn

    non-sealed //indent:4 exp:4
        class EE extends A { } //indent:8 exp:8

    sealed class F extends A //indent:4 exp:4
        permits R { } //indent:8 exp:8

    final class R extends F { } //indent:4 exp:4

    final class X extends Test2 { } //indent:4 exp:4
    final class Y extends Test2 { } //indent:4 exp:4
    final class W extends Test3 { } //indent:4 exp:4
    final class Z extends Test3 { } //indent:4 exp:4
    final class T extends FF { } //indent:4 exp:4

} //indent:0 exp:0
