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

    // 'this' passed as argument to a method
    public void register(List<InputRedundantThisValidThis> list) {
        list.add(this);   // ok, 'this' required, passing current object as argument
    }

    // 'this' returned in builder pattern
    public InputRedundantThisValidThis setName(String name) {
        this.name = name;  // ok, param 'name' shadows field
        return this;       // ok, returning self
    }

    // constructor chaining with this(...)
    public InputRedundantThisValidThis() {
        this("default");  // ok, constructor delegation via this(...)
    }

    public InputRedundantThisValidThis(String name) {
        this.name = name;  // ok, param 'name' shadows field
    }

    public void display(String s) {
        System.out.println(s);
    }

    // qualified 'this' used as constructor parameter type qualifier
    class Inner {
        class Inner2 {
            Inner2(Inner Inner.this) { }
        }
    }
}
