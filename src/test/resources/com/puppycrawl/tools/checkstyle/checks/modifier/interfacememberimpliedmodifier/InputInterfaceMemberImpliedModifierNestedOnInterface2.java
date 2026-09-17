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

    // violation below 'Implied modifier 'static' should be explicit.'
    public interface NestedInterfacePublic {
    }

    static interface NestedInterfaceStatic {
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    interface NestedInterface {
    }

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    public enum NestedEnumPublic {
        TRUE,
        FALSE
    }

    static enum NestedEnumStatic {
        TRUE,
        FALSE
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    enum NestedEnum {
        TRUE,
        FALSE
    }

    public static class NestedClassPublicStatic {
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    public class NestedClassPublic {
    }

    static class NestedClassStatic {
    }

    // violation below 'Implied modifier 'static' should be explicit.'
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
