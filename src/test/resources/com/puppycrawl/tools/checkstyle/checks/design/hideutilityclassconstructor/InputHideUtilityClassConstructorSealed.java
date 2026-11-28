/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)

*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

public sealed class InputHideUtilityClassConstructorSealed
        permits InputHideUtilityClassConstructorSealed.Circle {
    static int count;

    static InputHideUtilityClassConstructorSealed of() {
        return new Circle();
    }

    public static final class Circle extends InputHideUtilityClassConstructorSealed {
    }
}

sealed class InputHideUtilityClassConstructorSealedSubclass permits Sub {
    public static void foo() {}
}

final class Sub extends InputHideUtilityClassConstructorSealedSubclass {
}
