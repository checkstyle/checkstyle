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

    // violation below 'Implied modifier 'static' should be explicit.'
    public interface NestedInterfacePublic {
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static interface NestedInterfaceStatic {
    }

    interface NestedInterface {}
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    public static enum NestedEnumPublicStatic {
        TRUE,
        FALSE
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    public enum NestedEnumPublic {
        TRUE,
        FALSE
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static enum NestedEnumStatic {
        TRUE,
        FALSE
    }

    enum NestedEnum {
        TRUE,
        FALSE
    }
    // 2 violations 4 lines above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    public static class NestedClassPublicStatic {
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    public class NestedClassPublic {
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static class NestedClassStatic {}

    class NestedClass {}
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

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
