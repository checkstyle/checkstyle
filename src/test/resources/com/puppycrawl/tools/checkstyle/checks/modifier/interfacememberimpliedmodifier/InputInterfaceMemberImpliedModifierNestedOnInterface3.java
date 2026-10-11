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

    public static interface NestedInterfacePublicStatic {}

    public interface NestedInterfacePublic {}

    // violation below 'Implied modifier 'public' should be explicit.'
    static interface NestedInterfaceStatic {}

    // violation below 'Implied modifier 'public' should be explicit.'
    interface NestedInterface {}

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    public enum NestedEnumPublic {
        TRUE,
        FALSE
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static enum NestedEnumStatic {
        TRUE,
        FALSE
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    enum NestedEnum {
        TRUE,
        FALSE
    }

    public static class NestedClassPublicStatic {}

    public class NestedClassPublic {}

    // violation below 'Implied modifier 'public' should be explicit.'
    static class NestedClassStatic {}

    // violation below 'Implied modifier 'public' should be explicit.'
    class NestedClass {}

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
