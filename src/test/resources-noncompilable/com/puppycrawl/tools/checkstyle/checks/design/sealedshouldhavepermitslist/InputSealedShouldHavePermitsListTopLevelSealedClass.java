/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public sealed class InputSealedShouldHavePermitsListTopLevelSealedClass { // violation
     final class B extends InputSealedShouldHavePermitsListTopLevelSealedClass {}
     final class C extends InputSealedShouldHavePermitsListTopLevelSealedClass {}
     final class D { }
}

sealed class corrected permits corrected.B, corrected.C {
    final class B extends corrected { }
    final class C extends corrected { }
    final class D { }
}
