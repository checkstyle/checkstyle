/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = false
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierMethodsOnInterface3 {

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

    public void methodPublic();

    // violation below 'Implied modifier 'public' should be explicit.'
    void method();

}
