package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierMethodsOnInterfaceNested {

    public static interface NestedInterface {

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

    }

}
