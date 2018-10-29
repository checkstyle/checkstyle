package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierMethodsOnInterface {

    public static void methodPublicStatic() {
    }

    static void methodStatic() {
    }

    public default void methodPublicDefault() {
    }

    default int methodDefault() {
        int foo = 6;
        return foo;
    }

    public abstract void methodPublicAbstract();

    abstract void methodAbstract();

    public void methodPublic();

    void method();

}
