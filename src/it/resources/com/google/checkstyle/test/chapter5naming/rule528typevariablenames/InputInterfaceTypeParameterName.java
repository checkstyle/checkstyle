package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;

public class InputInterfaceTypeParameterName <T>
{
    public <TT> void foo() { }

    <T> void foo(int i) {
    }
}

class OtherClass <T extends Serializable & Cloneable> {

    T getOne() {
        return null;//comment
    }

    <X extends T> /*comment*/X getTwo(X a) {
        return null;
    }

    <E extends Runnable> E getShadow() {
        return null;
    }

    static class Junk <E> {
        <T extends E> void getMoreFoo() {
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

interface Boo<Input> { // violation 'Interface type name 'Input' must match pattern'
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

interface FooInterface7<Request> { // violation 'Interface type name 'Request' must match pattern'
    Input foo();
}

interface FooInterface8<TRequest> {
    // violation above 'Interface type name 'TRequest' must match pattern'
    Input foo();
}

class Input {

}
