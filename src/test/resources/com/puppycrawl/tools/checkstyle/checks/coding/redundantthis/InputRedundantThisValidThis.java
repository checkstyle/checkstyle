/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisValidThis {
    private String name;
    private List<String> names;

    // 'this' with ::  (method reference)
    public void process() {
        names.forEach(this::display);
    }

    // 'this' with ==  (identity check)
    public boolean isSame(Object obj) {
        return this == obj;
    }

    public void display(String name) {
        System.out.println(name);
    }
}
