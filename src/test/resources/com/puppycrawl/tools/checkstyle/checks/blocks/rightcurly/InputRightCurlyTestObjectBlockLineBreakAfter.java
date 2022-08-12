/*
RightCurly
option = alone_or_singleline
tokens = OBJBLOCK


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.util.Comparator;
import java.util.List;

public class InputRightCurlyTestObjectBlockLineBreakAfter {

    Object o1 = new Object() {
    }; int i1 = 0; // violation ''}' at column 5 should have line break after.'

    Object o2 = new Object() {
    }; // ok
    int i2 = 0;

    Object o3 = new Object() {}; // ok

    {
        List<Object> list = List.of();
        list.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        }); // ok
    }

    void method() {
        Object o4 = new Object() {
        // violation below ''}' at column 9 should have line break after.'
        }; for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
