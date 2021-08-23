package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.HashMap;
import java.util.TreeSet;

/*
 * Config: None as a helper class.
 */
public class InputIllegalType implements InputIllegalTypeSuper { // ok helper class
    public abstract class AbstractClass {/*one more comment*/}

    static class SomeStaticClass {
    }

    @Override
    public void foo(HashMap<?, ?> buffer) {}

    @Override
    public HashMap<?, ?> foo() {
        return null;
    }

    @Override
    public HashMap<?, ?> bar() {
        return null;
    }
}

interface InputIllegalTypeSuper {
    void foo(HashMap<?, ?> buffer);

    HashMap<?, ?> foo();

    Object bar();
}
