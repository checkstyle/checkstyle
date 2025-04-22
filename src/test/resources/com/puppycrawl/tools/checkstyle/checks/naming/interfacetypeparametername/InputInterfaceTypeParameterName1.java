/*
InterfaceTypeParameterName
format = ^foo$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.interfacetypeparametername;

import java.io.Serializable;

public class InputInterfaceTypeParameterName1<t>
{
    public <TT> void foo() { }

    <e_e> void foo(int i) {
    }
}

class Other1 <foo extends Serializable & Cloneable> {

    foo getOne() {
        return null;//comment
    }

    <Tfo$o2T extends foo> /*comment*/Tfo$o2T getTwo(Tfo$o2T a) {
        return null;
    }

    <foo extends Runnable> foo getShadow() {
        return null;
    }

    static class Junk <foo> {
        <_fo extends foo> void getMoreFoo() {
        }
    }
}

class MoreOther1 <T extends Cloneable> {

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

interface Boo1<Input> { // violation 'Name 'Input' must match pattern'
    Input boo();
}

interface FooInterface1<T> { // violation 'Name 'T' must match pattern'
    T foo();
}

interface FooInterface3 {
    Input foo();
}

class Input1 {

}
