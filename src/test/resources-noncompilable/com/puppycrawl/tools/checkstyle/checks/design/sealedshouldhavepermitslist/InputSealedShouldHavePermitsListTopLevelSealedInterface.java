/*
SealedShouldHavePermitsList

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.design.sealedshouldhavepermitslist;

public sealed interface InputSealedShouldHavePermitsListTopLevelSealedInterface { // violation
    final class B implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
    final class C implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
    final class D { }
    record R(int x) implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
    enum E implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
    static non-sealed class S implements InputSealedShouldHavePermitsListTopLevelSealedInterface {}
}

sealed interface InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected
        permits InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected.B,
                InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected.C,
                InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected.R,
                InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected.E,
                InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected.S {

    final class B implements InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected { }
    final class C implements InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected { }
    final class D { }
    record R(int x) implements InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected {}
    enum E implements InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected {}
    static non-sealed class S implements
            InputSealedShouldHavePermitsListTopLevelSealedInterfaceCorrected {}
}
