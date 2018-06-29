package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public class InputInterfaceMemberImpliedModifierNestedOnClassNested {

    interface NestedInterface {

        interface NestedNestedInterface {
        }

        enum NestedNestedEnum {
            TRUE,
            FALSE
        }

        class NestedNestedClass {
        }

    }

}
