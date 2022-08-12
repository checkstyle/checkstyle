/*
RightCurly
option = (default)SAME
tokens = OBJBLOCK


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.util.Comparator;
import java.util.List;

public class InputRightCurlyTestObjectBlockSingleLine {

    Object o1 = new Object() {
    }; int i1 = 0; // ok

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
        }; for (int i = 0; i < 10; i++) { // ok
            System.out.println(i);
        }
    }
}
