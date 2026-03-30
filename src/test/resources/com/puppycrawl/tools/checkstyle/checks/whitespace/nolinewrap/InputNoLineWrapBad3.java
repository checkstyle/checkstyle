/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

@Deprecated // violation 'should not be line-wrapped'
public class InputNoLineWrapBad3 implements InputNoLineWrapInterfaceBad3 {

    private final String[] field;

    @SafeVarargs // violation 'should not be line-wrapped'
    public InputNoLineWrapBad3(String... field) {
        this.field = field;
    }

    @Override // violation 'should not be line-wrapped'
    public void method() {
    }
}

@Deprecated // violation 'should not be line-wrapped'
enum InputNoLineWrapEnum3 {
    FIRS_TYPE
}

@FunctionalInterface // violation 'should not be line-wrapped'
interface InputNoLineWrapInterfaceBad3 {
    void method();
}

@Deprecated // violation 'should not be line-wrapped'
record InputNoLineWrapRecordBad3(
        InputNoLineWrapEnum3 type
) {
    @Deprecated // violation 'should not be line-wrapped'
    public InputNoLineWrapRecordBad3 {
    }
}
