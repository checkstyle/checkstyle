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

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

interface InputInterfaceMemberImpliedModifierPackageScopeInterface {

    public static final int fieldPublicStaticFinal = 1;

    // violation below 'Implied modifier 'final' should be explicit.'
    public static int fieldPublicStatic = 1;

    // violation below 'Implied modifier 'static' should be explicit.'
    public final int fieldPublicFinal = 1;

    public int fieldPublic = 1;
    // 2 violations above:
    //     'Implied modifier 'final' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    // violation below 'Implied modifier 'public' should be explicit.'
    static final int fieldStaticFinal = 1;

    static int fieldStatic = 1;
    // 2 violations above:
    //     'Implied modifier 'final' should be explicit.'
    //     'Implied modifier 'public' should be explicit.'

    final int fieldFinal = 1;
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    int field = 1;
    // 3 violations above:
    //     'Implied modifier 'final' should be explicit.'
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    public static void methodPublicStatic() {
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static void methodStatic() {}

    public default void methodPublicDefault() {}

    // violation below 'Implied modifier 'public' should be explicit.'
    default void methodDefault() {}

    public abstract void methodPublicAbstract();

    // violation below 'Implied modifier 'public' should be explicit.'
    abstract void methodAbstract();

    // violation below 'Implied modifier 'abstract' should be explicit.'
    public void methodPublic();

    void method();
    // 2 violations above:
    //     'Implied modifier 'abstract' should be explicit.'
    //     'Implied modifier 'public' should be explicit.'

    public static interface NestedInterfacePublicStatic {}

    // violation below 'Implied modifier 'static' should be explicit.'
    public interface NestedInterfacePublic {}

    // violation below 'Implied modifier 'public' should be explicit.'
    static interface NestedInterfaceStatic {}

    interface NestedInterface {}
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

}
