/*
RecordTypeParameterName
format = ^foo$


*/


package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

// violation below 'Name 'T' must match pattern'
public record InputRecordTypeParameterNameFoo<T>(Integer x, String str) {
    public <TT> void foo() { }

    <e_e> void foo(int i) {
    }
}

// violation below 'Name 'T' must match pattern'
record Other <T extends Serializable & Cloneable>
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

    static record Junk <foo>() {
        <_fo extends foo> void getMoreFoo() {
        }
    }
}

// violation below 'Name 'T' must match pattern'
record MoreOther <T extends Cloneable>(char c, String string) {

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

enum foo{}
