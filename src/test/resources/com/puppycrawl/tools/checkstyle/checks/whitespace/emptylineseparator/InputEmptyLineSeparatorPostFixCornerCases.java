/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorPostFixCornerCases {

    protected void foo1() throws Exception {
        int a = 25; // violation 'There is more than 1 empty line after this line'


        foo(a);
    }

    void foo(int a) {
        System.out.println(a);
    }

    protected void foo() {

        Object[] defaults = new Object[] {
                "String One", // violation 'There is more than 1 empty line after this line'



                "String Two",

        };
    }

    protected void foo2() {
        Object[] defaults = new Object[] {
            "String One", 13, // violation  'There is more than 1 empty line after this line'


            "String Two", // violation above this line.
        };
    }
}
