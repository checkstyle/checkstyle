/*
ModifierOrder
modifiersOrder = public, private, protected, static

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public class InputModifierOrderCustomOrderTwo {
    public @Deprecated int a;

    // violation below '@Deprecated' annotation modifier does not precede non-annotation modifiers.
    public @Deprecated void method() { }

    @Deprecated private int c;

    @Deprecated protected int e;

    private @MethodsAnnotation void foo11() {}
    // violation above ''@MethodsAnnotation' annotation modifier.*non-annotation modifiers.'

}

@interface MethodsAnnotation {}
