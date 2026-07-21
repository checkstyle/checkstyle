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

public interface InputInterfaceMemberImpliedModifierNestedOnInterfaceNested {

    public static interface NestedInterface {

        interface NestedNestedInterface {}
        // 2 violations above:
        //     'Implied modifier 'public' should be explicit.'
        //     'Implied modifier 'static' should be explicit.'

        enum NestedNestedEnum {
            TRUE,
            FALSE
        }
        // 2 violations 4 lines above:
        //     'Implied modifier 'public' should be explicit.'
        //     'Implied modifier 'static' should be explicit.'

        class NestedNestedClass {}
        // 2 violations above:
        //     'Implied modifier 'public' should be explicit.'
        //     'Implied modifier 'static' should be explicit.'

    }

}
