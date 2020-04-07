package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.HashMap;
import java.util.TreeSet;
//configuration: default
public class InputIllegalType implements InputIllegalTypeSuper {
    private AbstractClass a = null; //WARNING
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.AbstractClass
        c = null; //WARNING
    private java.util.List d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet table1() { return null; } //WARNING
    private TreeSet table2() { return null; } //WARNING
    static class SomeStaticClass {

    }

    InputIllegalType(Integer i) {}
    private void table2(Integer i) {}

    private void getInitialContext(java.util.TreeSet v) {} // ignore method by default

    @Override
    public void foo(HashMap<?, ?> buffer) {} // ignore

    @Override
    public HashMap<?, ?> foo() { //ignore
        return null;
    }

    @Override
    public HashMap<?, ?> bar() { //ignore
        return null;
    }
}

interface InputIllegalTypeSuper {
    void foo(HashMap<?, ?> buffer); //WARNING

    HashMap<?, ?> foo(); //WARNING

    Object bar();
}
