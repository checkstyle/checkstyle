package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;
import java.util.HashMap;
import java.util.TreeSet;

/*
 * Config:
 * memberModifiers = ""
 */
public class InputIllegalTypeEmptyStringMemberModifiers implements InputIllegalTypeSuper {
    private AbstractClass a = null; // ok
    private NotAnAbstractClass b = null; /*another comment*/

    private com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.AbstractClass
        c = null; //WARNING
    private java.util.List d = null;

    private abstract class AbstractClass {/*one more comment*/}

    private class NotAnAbstractClass {}

    private java.util.TreeSet table1() { return null; } // violation
    private TreeSet table2() { return null; } // violation
    static class SomeStaticClass {

    }

    InputIllegalTypeEmptyStringMemberModifiers(Integer i) {}
    private void table2(Integer i) {}

    private void getInitialContext(java.util.TreeSet v) {} // ok

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

interface InputIllegalTypeSuperEmptyStringMemberModifiers {
    void foo(HashMap<?, ?> buffer); // violation

    HashMap<?, ?> foo(); // violation

    Object bar();
}
