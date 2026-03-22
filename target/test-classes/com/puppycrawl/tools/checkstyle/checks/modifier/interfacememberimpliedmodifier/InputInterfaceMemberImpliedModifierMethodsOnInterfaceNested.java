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

public interface InputInterfaceMemberImpliedModifierMethodsOnInterfaceNested {

    public static interface NestedInterface {

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

    }

}
