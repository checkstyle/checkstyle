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

public class InputInterfaceMemberImpliedModifierNestedOnClassNested {

    interface NestedInterface {

        interface NestedNestedInterface { // violation
        }

        enum NestedNestedEnum { // violation
            TRUE,
            FALSE
        }

        class NestedNestedClass { // violation
        }

    }

}
