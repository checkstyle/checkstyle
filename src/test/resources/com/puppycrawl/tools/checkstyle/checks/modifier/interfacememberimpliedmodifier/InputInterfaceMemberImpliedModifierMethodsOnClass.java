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

public abstract class InputInterfaceMemberImpliedModifierMethodsOnClass { // ok

    public static void methodPublicStatic() {
    }

    private static void methodPrivateStatic() {
    }

    static void methodStatic() {
    }

    public void methodPublicDefault() {
    }

    private void methodPrivateDefault() {
    }

    void methodDefault() {
    }

    public abstract void methodPublicAbstract();

    abstract void methodAbstract();

}
