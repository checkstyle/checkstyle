/*
SingleSpaceSeparator
validateComments = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.singlespaceseparator;

import java.util.ArrayList;
import java.util.List;

public class InputSingleSpaceSeparatorNoErrors {

    int count; //long indentation - OK
    String text = "             ";

    private void foo(int i) {
        if (i > 10) {
            if (bar()) {
                i++;
                foo(i);
            }
        }
    }

    private boolean bar() {
        List<Double> list = new ArrayList<>();
        return Math.random() < 0.5;
    }
}
