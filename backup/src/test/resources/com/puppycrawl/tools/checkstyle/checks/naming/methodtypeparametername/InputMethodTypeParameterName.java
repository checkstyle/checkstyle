/*
MethodTypeParameterName
format = (default)^[A-Z]$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodtypeparametername;

import java.io.Serializable;

public class InputMethodTypeParameterName <t>
{
    public <TT> void foo() { } // violation

    <e_e> void foo(int i) { // violation
    }
}

class Other <foo extends Serializable & Cloneable> {

    foo getOne() {
        return null;//comment
    }

    <Tfo$o2T extends foo> /*comment*/Tfo$o2T getTwo(Tfo$o2T a) { // violation
        return null;
    }

    <foo extends Runnable> foo getShadow() { // violation
        return null;
    }

    static class Junk <foo> {
        <_fo extends foo> void getMoreFoo() { // violation
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
