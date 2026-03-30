/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.List;

public class InputRedundantThisValidThis {
    private String name;
    private List<String> names;
    private InputRedundantThisValidThis inputRedundantThisValidThis;

    // 'this' with ::  (method reference)
    public void process() {
        names.forEach(this::display);
    }

    // 'this' with ==  (identity check)
    public boolean isSame(Object obj) {
        if (this == inputRedundantThisValidThis) {
            return true;
        }
        return false;
    }

    public boolean isSame2(Object obj) {
        if (this != inputRedundantThisValidThis) {
            return true;
        }
        return false;
    }

    public void display(String name) {
        System.out.println(name);
    }
}
