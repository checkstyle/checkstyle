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

    public static int fieldPublicStatic = 1; // violation

    public final int fieldPublicFinal = 1; // violation

    public int fieldPublic = 1; // 2 violations

    static final int fieldStaticFinal = 1; // violation

    static int fieldStatic = 1; // 2 violations

    final int fieldFinal = 1; // 2 violations

    int field = 1; // 3 violations

    public static void methodPublicStatic() {
    }

    static void methodStatic() { // violation
    }

    public default void methodPublicDefault() {
    }

    default void methodDefault() { // violation
    }

    public abstract void methodPublicAbstract();

    abstract void methodAbstract(); // violation

    public void methodPublic(); // violation

    void method(); // 2 violations

    public static interface NestedInterfacePublicStatic {
    }

    public interface NestedInterfacePublic { // violation
    }

    static interface NestedInterfaceStatic { // violation
    }

    interface NestedInterface { // 2 violations
    }

}
