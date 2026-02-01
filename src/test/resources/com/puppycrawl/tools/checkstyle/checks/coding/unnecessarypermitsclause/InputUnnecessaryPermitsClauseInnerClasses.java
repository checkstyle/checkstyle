/*
UnnecessaryPermitsClause


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarypermitsclause;

public class InputUnnecessaryPermitsClauseInnerClasses {

    // violation below 'permits clause unnecessary for 'SealedOuter1', all subclasses in same file.'
    sealed class SealedOuter1 permits InnerA1, InnerB1 {

        final class InnerA1 extends SealedOuter1 {
        }

        final class InnerB1 extends SealedOuter1 {
        }
    }

    sealed class SealedOuter2 permits InnerA2, InnerB2 {

        final class InnerA2 extends SealedOuter2 {
        }
    }

// violation below 'permits clause unnecessary for 'SealedInterface', all subclasses in same file.'
    sealed interface SealedInterface permits Impl1, Impl2 {

        final class Impl1 implements SealedInterface {
        }

        final class Impl2 implements SealedInterface {
        }
    }

    sealed class SealedOuter3 {

        final class InnerA3 extends SealedOuter3 {
        }
    }


    // violation below 'permits clause unnecessary for 'SealedOuter4', all subclasses in same file.'
    sealed class SealedOuter4 permits A4, B4, C4 {

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
