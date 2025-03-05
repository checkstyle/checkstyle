/*
ClassTypeParameterName
format = ^foo$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

import java.io.Serializable;

public class InputClassTypeParameterName1<t> // violation, Name 't' must match pattern '^foo$'
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

class MoreOther1 <T extends Cloneable> { // violation, Name 'T' must match pattern '^foo$'

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
