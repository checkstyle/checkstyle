/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = false


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierNestedOnInterface3 {

    public static interface NestedInterfacePublicStatic {
    }

    public interface NestedInterfacePublic {
    }

    static interface NestedInterfaceStatic { // violation
    }

    interface NestedInterface { // violation
    }

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    public enum NestedEnumPublic {
        TRUE,
        FALSE
    }

    static enum NestedEnumStatic { // violation
        TRUE,
        FALSE
    }

    enum NestedEnum { // violation
        TRUE,
        FALSE
    }

    public static class NestedClassPublicStatic {
    }

    public class NestedClassPublic {
    }

    static class NestedClassStatic { // violation
    }

    class NestedClass { // violation
    }

    public default boolean methodWithLocalClass(String input) {
        class LocalClass {

            public boolean test(String str) {
                return str.isEmpty();
            }
        }
        LocalClass foo = new LocalClass();
        return foo.test(input);
    }

}
