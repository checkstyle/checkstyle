/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorMultipleLines4 {

    public void a() {
    }

    // some """Test method name 'test' segment must be more
    // than a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""


    @Test
    void testing_c() {} // violation above ''METHOD_DEF' has more than 1 empty lines before.'

    // getSomeName is inherited and filtered out by NoAttrScope.INHERITED
    // getSomeInt is inherited but overridden here, so NoAttrScope.INHERITED has no effect
    // getSomeLong is inherited and overridden here,
    //      and even with scope INHERITED its @NoAttribute takes precedence

    // isChild overrides nothing so with INHERITED it's not filtered out


    @Override
    public int getSomeInt() { // violation above ''METHOD_DEF' has more than 1 empty lines before.'
        return 43;
    }

    @interface Override{}

    @interface Test{}
}
