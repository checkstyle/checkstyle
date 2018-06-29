package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierNestedOnInterface {

    public static interface NestedInterfacePublicStatic {
    }

    public interface NestedInterfacePublic {
    }

    static interface NestedInterfaceStatic {
    }

    interface NestedInterface {
    }

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    public enum NestedEnumPublic {
        TRUE,
        FALSE
    }

    static enum NestedEnumStatic {
        TRUE,
        FALSE
    }

    enum NestedEnum {
        TRUE,
        FALSE
    }

    public static class NestedClassPublicStatic {
    }

    public class NestedClassPublic {
    }

    static class NestedClassStatic {
    }

    class NestedClass {
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
