/*
RedundantThis
checkMethods=true
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.List;

public class InputRedundantThisValidThis {
    private String name;
    private InputRedundantThisValidThis inputRedundantThisValidThis;

    public boolean valid(Object obj) {
        if (this == inputRedundantThisValidThis) {
            return true;
        }
        return false;
    }

    public void register(List<InputRedundantThisValidThis> list) {
        list.add(this);
    }

    public InputRedundantThisValidThis() {
        this("default");
    }

    public InputRedundantThisValidThis(String name) {
        this.name = name;
    }

    class Inner {
        class Inner2 {
            Inner2(Inner Inner.this) { }
        }
    }
}
