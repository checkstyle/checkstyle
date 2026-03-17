/*
MethodTypeParameterName
format = (default)^[A-Z]$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodtypeparametername;

import java.io.Serializable;

public class InputMethodTypeParameterName <t>
{
    public <TT> void foo() { } // violation 'Name 'TT' must match pattern'

    <e_e> void foo(int i) { // violation 'Name 'e_e' must match pattern'
    }
}

class Other <foo extends Serializable & Cloneable> {

    foo getOne() {
        return null;//comment
    }

    <Tfo$o2T extends foo> /*comment*/Tfo$o2T getTwo(Tfo$o2T a) {
        // violation above 'Name 'Tfo\$o2T' must match pattern'
        return null;
    }
    <foo extends Runnable> foo getShadow() { // violation 'Name 'foo' must match pattern'
        return null;
    }

    static class Junk <foo> {
        <_fo extends foo> void getMoreFoo() { // violation 'Name '_fo' must match pattern'
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
