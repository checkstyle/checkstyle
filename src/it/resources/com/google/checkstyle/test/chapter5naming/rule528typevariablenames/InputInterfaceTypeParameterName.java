package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;

public class InputInterfaceTypeParameterName <t>
{
    public <TT> void foo() { }

    <e_e> void foo(int i) {
    }
}

class OtherClass <foo extends Serializable & Cloneable> {

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

class MoreOtherClass <T extends Cloneable> {

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

interface Boo<Input> { // warn
    Input boo();
}

interface FooInterface<T> {
    T foo();
}

interface FooInterface2 {
    Input foo();
}

interface FooInterface3<T2> {
    Input foo();
}

interface FooInterface4<E> {
    Input foo();
}

interface FooInterface5<X> {
    Input foo();
}

interface FooInterface6<RequestT> {
    Input foo();
}

interface FooInterface7<Request> { // warn
    Input foo();
}

interface FooInterface8<TRequest> { // warn
    Input foo();
}

class Input {

}
