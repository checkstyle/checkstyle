/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierSealedClasses {
    String s = null;
    // 3 violations above:
    //                  'Implied modifier 'final' should be explicit.'
    //                  'Implied modifier 'public' should be explicit.'
    //                  'Implied modifier 'static' should be explicit.'
    void m();
    // 2 violations above:
    //                  'Implied modifier 'abstract' should be explicit.'
    //                  'Implied modifier 'public' should be explicit.'
    sealed interface J permits A {
    // 2 violations above:
    //                  'Implied modifier 'public' should be explicit.'
    //                  'Implied modifier 'static' should be explicit.'
        void m();
        // 2 violations above:
        //                  'Implied modifier 'abstract' should be explicit.'
        //                  'Implied modifier 'public' should be explicit.'
    }
    sealed class B permits A, D { }
    // 2 violations above:
    //                  'Implied modifier 'public' should be explicit.'
    //                  'Implied modifier 'static' should be explicit.'
    non-sealed class D extends B { }
    // 2 violations above:
    //                  'Implied modifier 'public' should be explicit.'
    //                  'Implied modifier 'static' should be explicit.'
}

interface InputInterfaceMemberImpliedModifierSealedClassesCorrected {
    final public static String s = null;
    abstract public void m();
    public  static  sealed interface J permits A {
        abstract public void m();
    }
    public static sealed class B permits D { }
    public static non-sealed class D extends B { }
}

final class A extends InputInterfaceMemberImpliedModifierSealedClasses.B
        implements InputInterfaceMemberImpliedModifierSealedClasses.J,
        InputInterfaceMemberImpliedModifierSealedClassesCorrected.J {

    @Override
    public void m() {}
}
