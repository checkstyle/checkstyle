/*
RecordTypeParameterName
format = (default)^[A-Z]$


*/


package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

// violation below 'Name 't' must match pattern'
public record InputRecordTypeParameterName<t>(Integer x, String str) {
    public <TT> void foo() { }

    <e_e> void foo(int i) {
    }
}

// violation below 'Name 'foo' must match pattern'
record OtherOne <foo extends Serializable & Cloneable>
(LinkedHashMap<String, Node> linkedHashMap) {

    foo getOne() {
        return null;//comment
    }

    <Tfo$o2T extends foo> /*comment*/Tfo$o2T getTwo(Tfo$o2T a) {
        return null;
    }

    <foo extends Runnable> foo getShadow() {
        return null;
    }

    // violation below 'Name 'foo' must match pattern'
    static record Junk <foo>() {
        <_fo extends foo> void getMoreFoo() {
        }
    }
}

record MoreOtherOne <T extends Cloneable>(char c, String string) {

    interface Boo<Input> {
        Input boo();
    }

    interface FooInterface<T> {
        T foo();
    }

    interface FooInterface2 {
        Input foo();
    }

    record Input(int x, int y) {

    }
}
