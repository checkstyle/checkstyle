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

public interface InputInterfaceMemberImpliedModifierNestedRecords {

    record Implied(int value) {
    }
    // 2 violations 2 lines above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    // violation below 'Implied modifier 'static' should be explicit.'
    public record PublicOnly(int value) {
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static record StaticOnly(int value) {
    }

    public static record Explicit(int value) {
    }

    @interface ImpliedAnnotation {
    }
    // 2 violations 2 lines above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    public static @interface ExplicitAnnotation {
    }

    class Holder {
        record NotInInterface(int value) {
        }
    }
    // 2 violations 4 lines above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'
}
