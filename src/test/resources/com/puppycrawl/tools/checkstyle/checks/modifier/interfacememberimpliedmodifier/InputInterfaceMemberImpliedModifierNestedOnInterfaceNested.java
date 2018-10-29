package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierNestedOnInterfaceNested {

    public static interface NestedInterface {

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
