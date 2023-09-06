/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = false
violateImpliedStaticNested = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierNestedOnInterface2 {

    public static interface NestedInterfacePublicStatic {
    }

    public interface NestedInterfacePublic { // violation
    }

    static interface NestedInterfaceStatic {
    }

    interface NestedInterface { // violation
    }

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    public enum NestedEnumPublic { // violation
        TRUE,
        FALSE
    }

    static enum NestedEnumStatic {
        TRUE,
        FALSE
    }

    enum NestedEnum { // violation
        TRUE,
        FALSE
    }

    public static class NestedClassPublicStatic {
    }

    public class NestedClassPublic { // violation
    }

    static class NestedClassStatic {
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
