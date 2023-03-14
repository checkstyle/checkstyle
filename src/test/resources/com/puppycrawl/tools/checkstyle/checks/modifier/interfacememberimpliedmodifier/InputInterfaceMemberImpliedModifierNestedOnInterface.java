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

public interface InputInterfaceMemberImpliedModifierNestedOnInterface {

    public static interface NestedInterfacePublicStatic {
    }

    public interface NestedInterfacePublic { // violation
    }

    static interface NestedInterfaceStatic { // violation
    }

    interface NestedInterface { // 2 violations
    }

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    public enum NestedEnumPublic { // violation
        TRUE,
        FALSE
    }

    static enum NestedEnumStatic { // violation
        TRUE,
        FALSE
    }

    enum NestedEnum { // 2 violations
        TRUE,
        FALSE
    }

    public static class NestedClassPublicStatic {
    }

    public class NestedClassPublic { // violation
    }

    static class NestedClassStatic { // violation
    }

    class NestedClass { // 2 violations
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
