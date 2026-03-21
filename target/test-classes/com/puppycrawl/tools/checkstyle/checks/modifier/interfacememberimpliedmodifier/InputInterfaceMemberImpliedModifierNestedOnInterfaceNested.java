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

        interface NestedNestedInterface { // 2 violations
        }

        enum NestedNestedEnum { // 2 violations
            TRUE,
            FALSE
        }

        class NestedNestedClass { // 2 violations
        }

    }

}
