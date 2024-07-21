/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public class InputSealedShouldHavePermitsListInnerInterface {
    sealed interface A {} // violation
    final class B implements A {}
    final class C implements A {}
    final class D { } // this can implement A, so as any other class in the compilation unit
    non-sealed class F implements A {}
    static final class G {}
    record R(int x) implements A {}
    enum E implements A {}
}

class InputSealedShouldHavePermitsListInnerInterfaceCorrected {
    sealed interface A permits B, C, F, R, E {} // ok, explicitly declared the permitted subclasses
    final class B implements A { }
    final class C implements A { }
    final class D { }    // this can't implement A
    non-sealed class F implements A {}
    static final class G {}
    record R(int x) implements A {}
    enum E implements A {}
}
