package com.puppycrawl.tools.checkstyle.checks.naming;

public class InputOverrideAnnotation {

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @SuppressWarnings("")
    public void foo(Object object) {

    }

    public void foo2(Integer aaaa) {}
}
