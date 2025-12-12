/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

public class InputNoLineWrapGood2 implements InputNoLineWrapInterfaceBad3 {

    private String[] field;

    @SafeVarargs
    public InputNoLineWrapGood2(String... field) {
        this.field = field;
    }

    @Override
    public void method() {}
}

@FunctionalInterface
interface MethodInterfaceGood2 {
    void method();
}

@Deprecated
enum TypesGood2 {
    FIRS_TYPE
}

@Deprecated
record TypeHolderGood2(
        InputNoLineWrapEnum3 type
) {
    @Deprecated
    public TypeHolderGood2 {}
}
