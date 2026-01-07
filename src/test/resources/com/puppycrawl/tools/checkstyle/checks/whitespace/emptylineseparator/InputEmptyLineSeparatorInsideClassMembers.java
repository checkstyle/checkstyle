/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import org.junit.Assert;
import org.junit.Test;

public class InputEmptyLineSeparatorInsideClassMembers {

    public void foo(int a) {}

    @Test
    public void testFoo() { // violation 'There is more than 1 empty line after this line.'


        int a = 10; // violation 'There is more than 1 empty line after this line.'


        // violation 2 lines below 'There is more than 1 empty line after this line.'
        InputEmptyLineSeparatorInsideClassMembers t =
                new InputEmptyLineSeparatorInsideClassMembers();


        // violation 2 lines below 'There is more than 1 empty line after this line.'
        t.foo(10);
        int b = 20;


        Assert.assertFalse(false);
  }
}
