/*
RedundantModifier


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public sealed interface InputRedundantModifierSealedClasses permits A {
   final public static String s = null;
   // 3 violations above:
   //                  'Redundant 'final' modifier'
   //                  'Redundant 'public' modifier'
   //                  'Redundant 'static' modifier'
   abstract public void m();
   // 2 violations above:
   //                  'Redundant 'abstract' modifier'
   //                  'Redundant 'public' modifier'
   public  static  sealed interface J permits A {
   // 2 violations above:
   //                  'Redundant 'public' modifier'
   //                  'Redundant 'static' modifier'
        abstract public void m();
        // 2 violations above:
        //                  'Redundant 'abstract' modifier'
        //                  'Redundant 'public' modifier'
   }
   public static sealed class B permits D { }
   // 2 violations above:
   //                  'Redundant 'public' modifier'
   //                  'Redundant 'static' modifier'
   public static non-sealed class D extends B { }
   // 2 violations above:
   //                  'Redundant 'public' modifier'
   //                  'Redundant 'static' modifier'
}

interface InputRedundantModifierSealedClassesCorrected {
    String s = null;
    void m();
    sealed interface J permits A {
        void m();
    }
    sealed class B permits A, D { }
    non-sealed class D extends B { }
}

final class A extends InputRedundantModifierSealedClassesCorrected.B
        implements InputRedundantModifierSealedClasses.J,
        InputRedundantModifierSealedClassesCorrected.J,
        InputRedundantModifierSealedClasses {

    @Override
    public void m() {}
}
