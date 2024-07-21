/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public class InputSealedShouldHavePermitsListInnerInerface {
     sealed interface A {} // violation
     final class B implements A {}
     final class C implements A {}
     final class D { } // this can implement A, so as any other class in the compilation unit
}

class corrected {
    sealed interface A permits B, C { } // ok, explicitly declared the permitted subclasses
    final class B implements A { }
    final class C implements A { }
    final class D { }    // this can't implement A
}
