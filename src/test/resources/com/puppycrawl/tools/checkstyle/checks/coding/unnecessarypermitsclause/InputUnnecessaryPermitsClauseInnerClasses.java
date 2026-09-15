/*
UnnecessaryPermitsClause

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseInnerClasses {

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed class SealedOuter1 permits SealedOuter1.InnerA1, SealedOuter1.InnerB1 {

        final class InnerA1 extends SealedOuter1 {
        }

        final class InnerB1 extends SealedOuter1 {
        }
    }

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed interface SealedInterface permits SealedInterface.Impl1, SealedInterface.Impl2 {

        final class Impl1 implements SealedInterface {
        }

        final class Impl2 implements SealedInterface {
        }
    }

    sealed class SealedOuter3 {

        final class InnerA3 extends SealedOuter3 {
        }
    }

    // violation below 'Unnecessary 'permits' clause for classes in the same compilation unit.'
    sealed class SealedOuter4 permits SealedOuter4.A4, SealedOuter4.B4, SealedOuter4.C4 {

        final class A4 extends SealedOuter4 {
        }

        final class B4 extends SealedOuter4 {
        }

        final class C4 extends SealedOuter4 {
        }
    }

    class NormalOuter {

        class InnerNormal {
        }
    }
}
