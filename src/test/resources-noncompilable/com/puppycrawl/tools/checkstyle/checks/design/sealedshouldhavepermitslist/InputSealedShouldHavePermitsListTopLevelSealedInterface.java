/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public sealed interface InputSealedShouldHavePermitsListTopLevelSealedInterface { // violation
     final class B implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
     final class C implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
     final class D { }
}

sealed interface corrected permits corrected.B, corrected.C {
    final class B implements corrected { }
    final class C implements corrected { }
    final class D { }
}
