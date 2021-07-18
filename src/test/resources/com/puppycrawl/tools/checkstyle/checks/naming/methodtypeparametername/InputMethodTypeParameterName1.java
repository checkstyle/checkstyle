/*
MethodTypeParameterName
format = ^foo$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodtypeparametername;

import java.io.Serializable;

public class InputMethodTypeParameterName1 <t>
{
    public <TT> void foo() { } // violation

    <e_e> void foo(int i) { // violation
    }
}

class Other1 <foo extends Serializable & Cloneable> {

    foo getOne() {
        return null;//comment
    }

    <Tfo$o2T extends foo> /*comment*/Tfo$o2T getTwo(Tfo$o2T a) { // violation
        return null;
    }

    <foo extends Runnable> foo getShadow() {
        return null;
    }

    static class Junk <foo> {
        <_fo extends foo> void getMoreFoo() { // violation
        }
    }
}

class MoreOther1 <T extends Cloneable> {

    <E extends T> void getMore() { // violation
        new Other() {
            <T> void getMoreFoo() { // violation
            }
        };

//        Other o = new Other() {
//            <EE> void getMoreFoo() {
//            }
//        };
    }
}

interface Boo1<Input> {
    Input boo();
}

interface FooInterface1<T> {
    T foo();
}

interface FooInterface3 {
    Input foo();
}

class Input1 {

}
