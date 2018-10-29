package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

interface InputInterfaceMemberImpliedModifierPackageScopeInterface {

    public static final int fieldPublicStaticFinal = 1;

    public static int fieldPublicStatic = 1;

    public final int fieldPublicFinal = 1;

    public int fieldPublic = 1;

    static final int fieldStaticFinal = 1;

    static int fieldStatic = 1;

    final int fieldFinal = 1;

    int field = 1;

    public static void methodPublicStatic() {
    }

    static void methodStatic() {
    }

    public default void methodPublicDefault() {
    }

    default void methodDefault() {
    }

    public abstract void methodPublicAbstract();

    abstract void methodAbstract();

    public void methodPublic();

    void method();

    public static interface NestedInterfacePublicStatic {
    }

    public interface NestedInterfacePublic {
    }

    static interface NestedInterfaceStatic {
    }

    interface NestedInterface {
    }

}
