/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;



public class InputModifierOrderTypeAnnotationsTwo extends MyClass {
  // Type casts
    public void foo3() {
        String myString = (@TypeAnnotation String) new Object();

    }

    // Type annotations with method arguments
    private void foo4(final @TypeAnnotation String parameterName) { }
}
enum MyEnum {
    @TypeAnnotation A;
}

interface IInterfacable {
    default @TypeAnnotation String foo() {
        return null;
    }
}
