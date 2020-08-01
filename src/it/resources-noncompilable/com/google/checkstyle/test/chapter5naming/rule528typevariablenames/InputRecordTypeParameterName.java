//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.recordtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

/* Config:
 * pattern = "(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)"
 *
 */
public record InputRecordTypeParameterName<t>(Integer x, String str) { // warn
    public <TT> void foo() { }

    <e_e> void foo(int i) {
    }
}

record Other <foo extends Serializable & Cloneable> // warn
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

    static record Junk <foo>() { // warn
        <_fo extends foo> void getMoreFoo() {
        }
    }
}

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
