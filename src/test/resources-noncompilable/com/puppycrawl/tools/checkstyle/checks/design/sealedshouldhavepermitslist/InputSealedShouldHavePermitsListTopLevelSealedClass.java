/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public sealed class InputSealedShouldHavePermitsListTopLevelSealedClass { // violation
    final class B extends InputSealedShouldHavePermitsListTopLevelSealedClass {}
    final class C extends InputSealedShouldHavePermitsListTopLevelSealedClass {}
    final class D { }
    record R(int x) {}
    enum E {}
    static final class F extends InputSealedShouldHavePermitsListTopLevelSealedClass {}
}

sealed class InputSealedShouldHavePermitsListTopLevelSealedClassCorrected
        permits InputSealedShouldHavePermitsListTopLevelSealedClassCorrected.B,
                InputSealedShouldHavePermitsListTopLevelSealedClassCorrected.C,
                InputSealedShouldHavePermitsListTopLevelSealedClassCorrected.F {
    final class B extends InputSealedShouldHavePermitsListTopLevelSealedClassCorrected { }
    final class C extends InputSealedShouldHavePermitsListTopLevelSealedClassCorrected { }
    final class D { }
    record R(int x) {}
    enum E {}
    static final class F extends InputSealedShouldHavePermitsListTopLevelSealedClassCorrected {}
}
