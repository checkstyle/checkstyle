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

public interface InputInterfaceMemberImpliedModifierMethodsOnInterface {

    public static void methodPublicStatic() {
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static void methodStatic() {
    }

    public default void methodPublicDefault() {
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    default int methodDefault() {
        int foo = 6;
        return foo;
    }

    public abstract void methodPublicAbstract();

    // violation below 'Implied modifier 'public' should be explicit.'
    abstract void methodAbstract();

    // violation below 'Implied modifier 'abstract' should be explicit.'
    public void methodPublic();

    void method();
    // 2 violations above:
    //    'Implied modifier 'abstract' should be explicit.'
    //    'Implied modifier 'public' should be explicit.'

}
