/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;


public final class InputUnusedPrivateFieldNestedScopes {
public final class Nested {

    private final Object source;

    private final String fileName;


    public Nested(Object source) {
        this(source, null);
    }

    public Nested(Object src, String fileName) {
        if (src == null) {
            throw new IllegalArgumentException("null source");
        }

        source = src;
        this.fileName = fileName;
    }


    public Object getSource() {
        return source;
    }
}
public  class Check{

}
}
