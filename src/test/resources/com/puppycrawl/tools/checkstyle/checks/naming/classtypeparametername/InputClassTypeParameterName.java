/*
ClassTypeParameterName
format = (default)^[A-Z]$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

import java.io.Serializable;
// violation below 'Name 't' must match pattern'
public class InputClassTypeParameterName<t>
{
    public <TT> void foo() { }

    <e_e> void foo(int i) {
    }
}
// violation below 'Name 'foo' must match pattern '
class Other <foo extends Serializable & Cloneable> {

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
    static class Junk <foo> {
        <_fo extends foo> void getMoreFoo() {
        }
    }
}

class MoreOther <T extends Cloneable> {

    <E extends T> void getMore() {
        new Other() {
            <T> void getMoreFoo() {
            }
        };

//        Other o = new Other() {
//            <EE> void getMoreFoo() {
//            }
//        };
    }
}

interface Boo<Input> {
    Input boo();
}

interface FooInterface<T> {
    T foo();
}

interface FooInterface2 {
    Input foo();
}

class Input {

}
