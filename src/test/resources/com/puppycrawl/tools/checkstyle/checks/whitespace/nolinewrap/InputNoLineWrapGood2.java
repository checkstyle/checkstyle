/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

@Deprecated
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
interface InterfaceNoLineWrapGood2 {
    void method();
}

@Deprecated
enum EnumNoLineWrapGood2 {
    VALUE
}

@Deprecated
record RecordNoLineWrapGood2(InputNoLineWrapEnum3 field) {
    @Deprecated
    public RecordNoLineWrapGood2 {}
}
