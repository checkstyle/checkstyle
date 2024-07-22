/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public class InputSealedShouldHavePermitsListInnerClass {
     sealed class A {} // violation
     final class B extends A {}
     final class C extends A {}
     final class D { } // this can extend A, so as any other class in the compilation unit
}

class corrected {
    sealed class A permits B, C { } // ok, explicitly declared the permitted subclasses
    final class B extends A { }
    final class C extends A { }
    final class D { }    // this can't extend A
}
